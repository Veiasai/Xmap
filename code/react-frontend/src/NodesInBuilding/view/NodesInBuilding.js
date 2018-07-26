import React, {Component} from 'react';
import {List, message, Avatar, Spin, Icon, Modal} from 'antd';
import {inject, observer} from "mobx-react/index";
import reqwest from 'reqwest';
import InfiniteScroll from 'react-infinite-scroller';
import "./NodesInBuilding.css"
import {httpHead, imgHead} from "../../Consts";

const confirm = Modal.confirm;
const antIcon = <Icon type="loading" style={{ fontSize: 24 }} spin />;


@inject(['UserData'])
@observer
class NodesInBuilding extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
        this.getData((res) => {
            this.UserData.currentNodeList = res.nodes;
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
        if (data.length >= this.UserData.currentBuilding.nodeSum) {
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
    };

    deleteNode = async (item) => {
        const url = httpHead + '/building/admin/node?buildingId='+this.UserData.currentBuilding.id+'&adminId='+this.UserData.userID+'&nodeId='+item.id;
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
                let temp = this.UserData.currentNodeList.filter((i) =>{
                    return i.id !== item.id;
                    });
                this.UserData.currentNodeList = temp;
                this.UserData.currentBuilding.nodeSum -= 1;
                this.setState({skip: this.UserData.currentNodeList.length});
                message.success('删除成功');
            }
            else if (json.code === 204) {
                message.error('点位不存在');
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
                this.deleteNode(item);
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    };

    addExportList(item){
      let temp = this.UserData.qrNodeList;
      temp.push(item);
      this.UserData.qrNodeList = temp;
      message.success('添加成功');
    };

    editNode(item){

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
                            <List.Item
                                key={item.id}
                                actions={[
                                    <Icon onClick={() => this.showDeleteConfirm(item)} type="delete"/>,
                                    <Icon onClick={() => this.addExportList(item)} type="folder-add"/>,
                                    <Icon onClick={() => this.editNode(item)} type="edit"/>,
                                ]}
                            >
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size='large'
                                        src={imgHead + item.img}/>}
                                    title={item.name}
                                />
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
            </div>
        )
    }
}

export default NodesInBuilding;
