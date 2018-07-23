import React, { Component } from 'react';
import { List, message, Avatar, Spin } from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ManageMessages.css"
import {httpHead, imgHead} from "../../Consts";
import reqwest from "reqwest";
import InfiniteScroll from 'react-infinite-scroller';
import {view as SendMessage} from '../../Components/SendMessage'


@inject(['UserData'])
@observer
class ManageMessages extends Component
{
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData
        this.getData((res) => {
            this.UserData.currentMessageList = res.paths;
            console.log(res.paths);
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
            url: httpHead + '/paths?buildingId=' + this.UserData.currentBuilding.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit,
            type: 'json',
            method: 'get',
            mode: 'cors',
            contentType: 'application/json',
            success: (res) => {
                callback(res);
            },
        });
    }

    // componentDidMount() {
    //     this.getData((res) => {
    //         this.UserData.currentMessageList = res;
    //     });
    // }

    handleInfiniteOnLoad = () => {
        let data = this.UserData.currentMessageList;
        this.setState({
            loading: true,
        });
        if (data.length > this.UserData.currentBuilding.pathAmount) {
            message.warning('没有更多信息了');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        this.getData((res) => {
            if(this.state.skip === this.UserData.currentMessageList.length)
            {
                data = data.concat(res.results.paths);
                this.UserData.currentMessageList = data;
                this.setState({
                    skip: this.UserData.currentMessageList.length,
                    loading: false,
                });
            }
        });
    }

    getBuildingNodeList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentNoteList = json.nodeList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
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
                        dataSource={this.UserData.currentMessageList}
                        renderItem={item => (
                            <List.Item key={item.id}>
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size = 'large'
                                        src= {imgHead + item.img} />}
                                    title={item.name}
                                    description={'总步数：' + item.steps + '步 共' + item.curves + '个转弯' }
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
                <SendMessage/>
            </div>

        )
    }
}

export default ManageMessages;
