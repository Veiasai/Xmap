import React, {Component} from 'react';
import {List, message, Avatar, Spin, Icon, Drawer, Modal} from 'antd';
import InfiniteScroll from 'react-infinite-scroller';
import {inject, observer} from "mobx-react/index";
import "./PathsInBuilding.css"
import {httpHead, imgHead} from "../../Consts";
import reqwest from "reqwest";

const confirm = Modal.confirm;
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
        pathDisplayName:'',
        pathDisplayContent:[],
    };

    showDrawer = (item) => {
        this.setState({
            drawerVisible: true,
            pathDisplayName: item.name,
            pathDisplayContent: item.contents,
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
    };

    deletePath = async (item) => {
        const url = httpHead + '/builiding/admin/path?buildingId='+this.UserData.currentBuilding.id+'&adminId='+this.UserData.userID+'&pathId='+item.id;
        try {
            const response = await fetch(url,
                {
                    method: "DELETE",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();

            if (json.code === 200) {
                let temp = this.UserData.currentPathList.filter((i) =>{
                    return i.id !== item.id;
                });
                this.UserData.currentPathList = temp;
                this.UserData.currentBuilding.pathSum -= 1;
                this.setState({skip: this.UserData.currentPathList.length});
                message.success('删除成功');
            }
            else if (json.code === 204) {
                message.error('路线不存在');
            }
            else if (json.code === 401) {
                message.error('没有授权');
            }
            else if (json.code === 403) {
                message.error('用户被禁止操作');
            }
        }
        catch (e) {
            console.log(e)
        }
    };

    showDeleteConfirm(item) {
        confirm({
            title: '确认删除点位？',
            okText: '确认',
            okType: 'danger',
            cancelText: '取消',
            onOk:() => {
                this.deletePath(item);
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    };

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
                                actions={[<Icon onClick={() => this.showDeleteConfirm(item)} type='delete'/>,<a onClick={() => this.showDrawer(item)}>查看路线详细信息</a>]}
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
                    title={this.state.pathDisplayName}
                    placement="right"
                    closable={false}
                    onClose={this.onClose}
                    visible={this.state.drawerVisible}
                >
                    <List
                        dataSource={this.state.pathDisplayContent}
                        renderItem={item => (
                            <List.Item
                                key={item.id}
                            >
                                <List.Item.Meta
                                    avatar={(item.type !== 'comment')&&(item.type !== 'img')&&(<Avatar
                                        size='large'
                                        icon={(item.type === 'actionL')? 'arrow-left':(item.type === 'actionR'? 'arrow-right':'arrow-up')}/>)}
                                />
                                {(item.type === 'img')&&(<img width={210} height={280} alt="logo" src={imgHead + item.message}/>)}
                                {(item.type !== 'img')&&(item.type !== 'content')&&('向前')+(item.type !== 'img')&&(item.message)+ ((item.type === 'actionL')? '步后左转':(item.type === 'actionR'? '步后右转':(item.type === 'actionS'? '保持直行':'')))}
                            </List.Item>
                        )}
                    >
                    </List>
                </Drawer>
            </div>
        )
    }
}

export default PathsInBuilding;
