import React, {Component} from 'react';
import {List, message, Avatar, Spin, Icon, Drawer} from 'antd';
import InfiniteScroll from 'react-infinite-scroller';
import {inject, observer} from "mobx-react/index";
import "./PathsInBuilding.css"
import {httpHead, imgHead} from "../../Consts";
import reqwest from "reqwest";

const antIcon = <Icon type="loading" style={{ fontSize: 24 }} spin />;

@inject(['UserData'])
@observer
class PathsInBuilding extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
        this.getData((res) => {
            this.UserData.currentPathList = res.paths;
            message.success('获取路线成功')
            console.log(res.paths);
        });
    }

    state = {
        loading: false,
        hasMore: true,
        skip: 0,
        limit: 10,
        drawerVisible: false,
        pathDisplay: {},
    };

    showDrawer = (item) => {
        console.log(item)
        this.setState({
            drawerVisible: true,
            pathDisplay: item,
        });
    };

    onClose = () => {
        this.setState({
            drawerVisible: false,
        });
    };

    getData = (callback) => {
        reqwest({
            url: httpHead + '/paths?buildingId=' + this.UserData.currentBuilding.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit,
            type: 'json',
            method: 'get',
            mode: 'cors',
            contentType: 'application/json',
            success: (res) => {
                callback(res);
            },
        });
    };


    handleInfiniteOnLoad = () => {
        let data = this.UserData.currentPathList;
        this.setState({
            loading: true,
        });
        if (data.length >= this.UserData.currentBuilding.pathSum) {
            message.warning('没有更多路径了');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        this.getData((res) => {
            if (this.state.skip === this.UserData.currentPathList.length) {
                data = data.concat(res.results.paths);
                this.UserData.currentPathList = data;
                this.setState({
                    skip: this.UserData.currentPathList.length,
                    loading: false,
                });
            }
        });
    }

    render() {
        return (
            <div className="demo-infinite-container">
                <InfiniteScroll
                    initialLoad={false}
                    pageStart={0}
                    loadMore={this.handleInfiniteOnLoad}
                    hasMore={!this.state.loading && this.state.hasMore}
                    useWindow={false}
                >
                    <List
                        dataSource={this.UserData.currentPathList.toJS()}
                        renderItem={item => (
                            <List.Item
                                key={item.id}
                                actions={[<a onClick={() => this.showDrawer(item)}>查看路线详细信息</a>]}
                            >
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size='large'
                                        src={imgHead + item.img}/>}
                                    title={item.name}
                                    description={'总步数：' + item.steps + '步 共' + item.curves + '个转弯'}
                                />
                                <div></div>
                            </List.Item>
                        )}
                    >
                        {this.state.loading && this.state.hasMore && (
                            <div className="demo-loading-container">
                                <Spin indicator={antIcon} />
                            </div>
                        )}
                    </List>
                </InfiniteScroll>
                <Drawer
                    title={this.state.pathDisplay.name}
                    placement="right"
                    closable={false}
                    onClose={this.onClose}
                    visible={this.state.drawerVisible}
                >

                </Drawer>
            </div>
        )
    }
}

export default PathsInBuilding;
