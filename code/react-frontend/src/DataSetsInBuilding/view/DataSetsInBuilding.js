import React, { Component } from 'react';
import {List, message, Avatar, Spin, Icon, Drawer} from 'antd';
import {inject, observer} from "mobx-react/index";
import InfiniteScroll from 'react-infinite-scroller';
import "./DataSetsInBuilding.css"
import reqwest from "reqwest";
import {httpHead, imgHead} from "../../Consts";
import {Modal} from "antd/lib/index";

const confirm = Modal.confirm;
const antIcon = <Icon type="loading" style={{ fontSize: 24 }} spin />;

@inject(['UserData'])
@observer
class DataSetsInBuilding extends Component
{
     constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
         this.getData((res) => {
             this.UserData.currentDataSetList = res.dataSets;
             message.success('获取数据组成功')
             console.log(res.dataSets);
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
            url: httpHead + '/dataset?buildingId=' + this.UserData.currentBuilding.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit,
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
        let data = this.UserData.currentDataSetList;
        this.setState({
            loading: true,
        });
        // if (data.length >= this.UserData.currentBuilding.pathSum) {      //dataSetSum
        //     message.warning('没有更多路径了');
        //     this.setState({
        //         hasMore: false,
        //         loading: false,
        //     });
        //     return;
        // }
        this.getData((res) => {
            if (this.state.skip === this.UserData.currentDataSetList.length) {
                data = data.concat(res.results.dataSets);
                this.UserData.currentDataSetList = data;
                this.setState({
                    skip: this.UserData.currentDataSetList.length,
                    loading: false,
                });
            }
        });
    };
    deleteDataSet = async (item) => {
        const url = httpHead + '/building/admin/dataset?buildingId='+this.UserData.currentBuilding.id+'&adminId='+this.UserData.userID+'&dataSetId='+item.id;
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
                let temp = this.UserData.currentDataSetList.filter((i) =>{
                    return i.id !== item.id;
                });
                this.UserData.currentDataSetList = temp;
                this.UserData.currentBuilding.nodeSum -= 1;
                 this.setState({skip: this.UserData.currentMessageList.length});
                message.success('删除成功');
            }
            else if (json.code === 404) {
                message.error('点位不存在');
            }
            else if (json.code === 403) {
                message.error('没有授权');
            }
        }
        catch (e) {
            console.log(e)
        }
    };
    showDeleteConfirm(item) {
        confirm({
            title: '确认删除数据组？',
            okText: '确认',
            okType: 'danger',
            cancelText: '取消',
            onOk:() => {
                this.deleteDataSet(item);
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    };
    render()
    {
        return(
            <div className="demo-infinite-container">
                <InfiniteScroll
                    initialLoad={false}
                    pageStart={0}
                    loadMore={this.handleInfiniteOnLoad}
                    hasMore={!this.state.loading && this.state.hasMore}
                    useWindow={false}
                >
                    <List
                        dataSource={this.UserData.currentDataSetList.toJS()}
                        renderItem={item => (
                            <List.Item
                                key={item.id}
                                actions={[
                                    <Icon onClick={() => this.showDeleteConfirm(item)} type="delete"/>,
                                    <a onClick={() => this.showDrawer(item)}>查看数据组详细信息</a>]}
                            >
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size='large'
                                        />}
                                    title={item.name}
                                    description={'类型 ：'+item.type}
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

export default DataSetsInBuilding;
