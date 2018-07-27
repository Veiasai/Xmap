import React, {Component} from 'react';
import {Button, List, message, Avatar, Spin, Icon, Drawer} from 'antd';
import {inject, observer} from "mobx-react/index";
import InfiniteScroll from 'react-infinite-scroller';
import "./DataSetsInBuilding.css"
import reqwest from "reqwest";
import {httpHead, imgHead} from "../../Consts";
import {Modal} from "antd/lib/index";

const confirm = Modal.confirm;
const antIcon = <Icon type="loading" style={{fontSize: 24}} spin/>;
message.config({
    top: 100,
    duration: 1,
    maxCount: 3,
});
@inject(['UserData'])
@observer
class DataSetsInBuilding extends Component {
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
        dataSetDisplay: {},
        dataSetType: {},
        dataSetRestDetail: [],
        dataSetId:{}
    };

    showDrawer = (item) => {
        console.log(item)
        this.dataSet = item;
        this.getDataSetDetail(item)
        this.setState({
            drawerVisible: true,
            dataSetDisplay: item,
            dataSetType: item.type,
            dataSetId:item.id
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

    getDataSetDetail = async (item) => {
        const url = httpHead + '/' + item.type + 's?dataSetId=' + item.id + '&skip=' + this.state.skip + '&limit=' + this.state.limit;
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();
            if (json.code === 200) {
                if (item.type === "node") {
                    this.UserData.currentDataSetDetail = json.nodes;
                }
                else {
                    this.UserData.currentDataSetDetail = json.paths;
                }
                console.log(this.UserData.currentDataSetDetail);
                if (JSON.stringify(this.UserData.currentDataSetDetail) !== '[]') {
                    message.success('查询成功');
                }
                else {
                    message.success('数据组内无数据');
                }
            }
            else if (json.code === 404) {
                message.error(item.type + '不存在');
            }
        }
        catch (e) {
            console.log(e)
        }

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
    deleteDataSet = async (item) => {       //item 为数据组
        const url = httpHead + '/building/admin/dataset?buildingId=' + this.UserData.currentBuilding.id + '&adminId=' + this.UserData.userID + '&dataSetId=' + item.id;
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
                let temp = this.UserData.currentDataSetList.filter((i) => {
                    return i.id !== item.id;
                });
                this.UserData.currentDataSetList = temp;
                // this.UserData.currentBuilding.nodeSum -= 1;
                // this.setState({skip: this.UserData.currentMessageList.length});
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
    deleteDataSetDetail = async (item) => {     //item 为数据组内的点位或路线
        const url = httpHead + '/building/admin/dataset/detail?buildingId=' + this.UserData.currentBuilding.id + '&adminId=' + this.UserData.userID + '&dataSetId=' + this.dataSet.id + '&Id=' + item.id;
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
                let temp = this.UserData.currentDataSetDetail.filter((i) => {
                    return i.id !== item.id;
                });
                this.UserData.currentDataSetDetail = temp;
                // this.UserData.currentBuilding.nodeSum -= 1;
                // this.setState({skip: this.UserData.currentMessageList.length});
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
            title: '确认删除数据组' + item.name + '？',
            okText: '确认',
            okType: 'danger',
            cancelText: '取消',
            onOk: () => {
                this.deleteDataSet(item);
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    };

    showDeleteConfirmDS(item) {
        confirm({
            title: '确认删除' + item.name + '?',
            okText: '确认',
            okType: 'danger',
            cancelText: '取消',
            onOk: () => {
                this.deleteDataSetDetail(item);
            },
            onCancel() {
                console.log('Cancel');
            },
        });
    };

    arrayMinus(arr1, arr2) {
        var arr3 = new Array();
        for (var i = 0; i < arr1.length; i++) {
            var flag = true;
            for (var j = 0; j < arr2.length; j++) {
                if (arr1[i].id === arr2[j].id)
                    flag = false;
            }
            if (flag)
                arr3.push(arr1[i]);
        }
        ;
        return arr3;
    }

    showChildrenDrawer = () => {
        this.setState({
            childrenDrawer: true,
        });
        if (this.state.dataSetType === "node") {
            var arr3 = this.arrayMinus(this.UserData.currentNodeList, this.UserData.currentDataSetDetail);
            this.UserData.dataSetRestDetail = arr3;
            console.log(this.UserData.dataSetRestDetail);
        }
        else if (this.state.dataSetType === "path") {
            var arr3 = this.arrayMinus(this.UserData.currentPathList, this.UserData.currentDataSetDetail);
            this.UserData.dataSetRestDetail = arr3;
            console.log(this.UserData.dataSetRestDetail);
        }

    };

    onChildrenDrawerClose = () => {
        this.setState({
            childrenDrawer: false,
        });
    };

    addToDataSet = async (item) => {
        let temp = this.UserData.dataSetRestDetail.filter((i) => {
            return i.id !== item.id;
        });
        this.UserData.dataSetRestDetail = temp;
        this.UserData.currentDataSetDetail.push(item);
        try {
            const url = httpHead + '/building/admin/dataset/detail?buildingId=' + this.UserData.currentBuilding.id + '&adminId=' + this.UserData.userID + '&dataSetId=' +this.state.dataSetId+ '&Id='+item.id;
            const response = await fetch(url,
                {
                    method: "PUT",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();

            if (json.code === 200) {
                // this.UserData.currentBuilding.nodeSum -= 1;
                // this.setState({skip: this.UserData.currentMessageList.length});
                message.success('添加成功');
            }
            else if (json.code === 404) {
                message.error('不存在');
            }
            else if (json.code === 403) {
                message.error('没有授权');
            }
        }
        catch (e) {
            console.log(e)
        }
    }

    render() {
        return (
            <div className="demo-infinite-container">
                <InfiniteScroll     //数据组
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
                                    <a onClick={() => this.showDrawer(item)}>详细信息</a>]}
                            >
                                <List.Item.Meta
                                    avatar={<Avatar
                                        size='large'
                                    />}
                                    title={item.name}
                                    description={'类型 ：' + item.type}
                                />
                                <div></div>
                            </List.Item>
                        )}
                    >
                        {this.state.loading && this.state.hasMore && (
                            <div className="demo-loading-container">
                                <Spin indicator={antIcon}/>
                            </div>
                        )}
                    </List>
                </InfiniteScroll>
                <Drawer
                    title={this.state.dataSetDisplay.name}
                    placement="right"
                    closable={false}
                    onClose={this.onClose}
                    visible={this.state.drawerVisible}
                >
                    <InfiniteScroll
                        initialLoad={false}
                        pageStart={0}
                        loadMore={this.handleInfiniteOnLoad}
                        hasMore={!this.state.loading && this.state.hasMore}
                        useWindow={false}
                    >
                        <List
                            dataSource={this.UserData.currentDataSetDetail.toJS()}      //数据组中点位或路线
                            renderItem={item => (
                                <List.Item
                                    key={item.id}
                                    actions={[
                                        <Icon onClick={() => this.showDeleteConfirmDS(item)} type="delete"/>]}
                                >
                                    <List.Item.Meta
                                        avatar={<Avatar
                                            size='large'
                                            src={imgHead + item.img}
                                        />}
                                        title={item.name}
                                    />
                                    <div></div>
                                </List.Item>
                            )}
                        >
                            {this.state.loading && this.state.hasMore && (
                                <div className="demo-loading-container">
                                    <Spin indicator={antIcon}/>
                                </div>
                            )}
                        </List>
                        <Icon onClick={this.showChildrenDrawer}
                              style={{textAlign: 'right', fontSize: 20, color: '#08c'}}
                              type="plus"/>
                    </InfiniteScroll>
                    <Drawer
                        title={this.state.dataSetType + "列表"}       //二级抽屉  剩余点位或路线(可添加到数据组）
                        width={320}
                        closable={false}
                        onClose={this.onChildrenDrawerClose}
                        visible={this.state.childrenDrawer}
                    >
                        <List
                            dataSource={this.UserData.dataSetRestDetail}
                            renderItem={item => (
                                <List.Item
                                    key={item.id}
                                    actions={[
                                        <Icon onClick={() => this.addToDataSet(item)} type="folder-add"/>,
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
                                    <Spin indicator={antIcon}/>
                                </div>
                            )}
                        </List>
                        <div
                            style={{
                                position: 'absolute',
                                bottom: 0,
                                width: '100%',
                                borderTop: '1px solid #e8e8e8',
                                padding: '10px 16px',
                                textAlign: 'right',
                                left: 0,
                                background: '#fff',
                                borderRadius: '0 0 4px 4px',
                            }}
                        >
                            <Button
                                style={{
                                    marginRight: 8,
                                }}
                                onClick={this.onChildrenDrawerClose}
                            >
                                Cancel
                            </Button>
                            {/*<Button onClick={this.onChildrenDrawerClose} type="primary">*/}
                                {/*Submit*/}
                            {/*</Button>*/}
                        </div>
                    </Drawer>
                </Drawer>
            </div>
        )
    }
}

export default DataSetsInBuilding;
