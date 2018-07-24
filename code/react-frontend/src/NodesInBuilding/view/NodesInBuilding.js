import React, {Component} from 'react';
import {List, message, Avatar, Spin} from 'antd';
import {inject, observer} from "mobx-react/index";
import reqwest from 'reqwest';
import InfiniteScroll from 'react-infinite-scroller';
import "./NodesInBuilding.css"
import {httpHead, imgHead} from "../../Consts";

@inject(['UserData'])
@observer
class NodesInBuilding extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
        this.getData((res) => {
            console.log(res.nodes);
            this.UserData.currentNodeList = res.nodes;
            console.log(this.UserData.currentNodeList);
            message.success('获取点位成功')
        });
    }

    state = {
        loading: false,
        hasMore: true,
        skip: 0,
        limit: 10,
    }

    getData = (callback) => {
        reqwest({
            url: httpHead + '/nodes?buildingId=' + this.UserData.currentBuilding.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit,
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
        let data = this.UserData.currentNodeList;
        this.setState({
            loading: true,
        });
        if (data.length >= this.UserData.currentBuilding.nodesSum) {
            message.warning('没有更多点位了');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        this.getData((res) => {
            if (this.state.skip === this.UserData.currentNodeList.length) {
                data = data.concat(res.results.nodes);
                this.UserData.currentNodeList = data;
                this.setState({
                    skip: this.UserData.currentNodeList.length,
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
                        dataSource={this.UserData.currentNodeList}
                        renderItem={item => (
                            <List.Item key={item.id}>
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size='large'
                                        src={imgHead + item.img}/>}
                                    title={item.name}
                                />
                                <div></div>
                            </List.Item>
                        )}
                    >
                        {this.state.loading && this.state.hasMore && (
                            <div className="demo-loading-container">
                                <Spin/>
                            </div>
                        )}
                    </List>
                </InfiniteScroll>
            </div>
        )
    }
}

export default NodesInBuilding;
