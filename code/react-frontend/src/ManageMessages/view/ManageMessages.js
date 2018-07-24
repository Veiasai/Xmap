import React, {Component} from 'react';
import {List, message, Icon, Spin} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ManageMessages.css"
import {httpHead, imgHead} from "../../Consts";
import reqwest from "reqwest";
import InfiniteScroll from 'react-infinite-scroller';
import {view as SendMessage} from '../../Components/SendMessage'

const IconText = ({type, text}) => (
    <span>
    <Icon type={type} style={{marginRight: 8}}/>
        {text}
  </span>
);

@inject(['UserData'])
@observer
class ManageMessages extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData
        this.getData((res) => {
            this.UserData.currentMessageList = res.messages;
            message.success('获取信息成功')
            console.log(this.UserData.currentBuilding.messageSum);
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
            url: httpHead + '/message?buildingId=' + this.UserData.currentBuilding.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit,
            type: 'json',
            method: 'get',
            mode: 'cors',
            contentType: 'application/json',
            success: (res) => {
                callback(res);
            },
        });
    }



    handleInfiniteOnLoad = () => {
        let data = this.UserData.currentMessageList;
        this.setState({
            loading: true,
        });
        if (data.length >= this.UserData.currentBuilding.messageSum) {
            message.warning('没有更多信息了');
            this.setState({
                hasMore: false,
                loading: false,
            });
            return;
        }
        this.getData((res) => {
            if (this.state.skip === this.UserData.currentMessageList.length) {
                data = data.concat(res.results.messages);
                this.UserData.currentMessageList = data;
                this.setState({
                    skip: this.UserData.currentMessageList.length,
                    loading: false,
                });
            }
        });
    };

    deleteMessage(messageId){

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
                        itemLayout="vertical"
                        dataSource={this.UserData.currentMessageList}
                        renderItem={item => (
                            <List.Item
                                key={item.id}
                                actions={[<IconText type="delete" text="删除"/>]}
                            >
                                <List.Item.Meta
                                    title={item.title}
                                    description={item.date}
                                />
                                {item.content}
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
