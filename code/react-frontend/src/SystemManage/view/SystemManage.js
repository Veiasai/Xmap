import React, { Component } from 'react';
import {Divider, Table, Popconfirm, Tabs, } from 'antd';
import {inject, observer} from "mobx-react/index";
import {httpHead} from '../../Consts'
import "./SystemManage.css"


const TabPane = Tabs.TabPane;

@inject(['UserData'])
@observer
class SystemManage extends Component
{
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
        this.state={
            dataSource: [],
            admin: [],
        }
        this.admincol = [{
            title: 'UserId',
            dataIndex: 'UserId',
            key: 'UserId',
          }, {
            title: 'Action',
            key: 'action',
            render: (text, record) => {
                return (
                    this.state.admin.length >= 1
                      ? (
                          <span>
                        <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete2(record.key)}>
                          <a href="javascript:;">Delete</a>
                        </Popconfirm>
                          </span>
                      ) : null
                  );
            }
          }];   
        this.columns = [{
            title: 'UserId',
            dataIndex: 'UserId',
            key: 'UserId',
          }, {
            title: 'Building',
            dataIndex: 'Building',
            key: 'Building',
            render: (text, record) => (
                <span>
                    {text.name}
                </span>
            )
          }, {
            title: 'Action',
            key: 'action',
            render: (text, record) => {
                return (
                    this.state.dataSource.length >= 1
                      ? (
                          <span>
         <Popconfirm title="Sure to accept?" onConfirm={() => this.handleAccpet(record.key)}>
                          <a href="javascript:;">Accpet</a>
                        </Popconfirm>
                        <Divider type="vertical" />
                        <Popconfirm title="Sure to refuse?" onConfirm={() => this.handleDelete(record.key)}>
                          <a href="javascript:;">Refuse</a>
                        </Popconfirm>
                          </span>
                      ) : null
                  );
            }
          }];        
        this.getApply();
        this.getAdmin();
    }
    async getAdmin(){
        try{
            let url = httpHead + '/building/admin/admin?buildingId=' + this.UserData.currentBuilding.id
            let res = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
                console.log(res);
                let json = await res.json();
                let data = json.buildingAdmins.map((item, i)=>{
                    return {UserId: item.id, key: i}
                })
                this.setState({admin: data});
                console.log(json)
            }catch(e){
                console.log(e);
            }
            
    }
    async getApply(){
        try{
        let url = httpHead + '/systemadmin/apply?_sign=123456&BuildingId=' + this.UserData.currentBuilding.id + '&limit=20'
        let res = await fetch(url,
            {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json',
                },
                mode: 'cors',
            });
            console.log(res);
            let json = await res.json();
            let data = json.apply.map((item, i)=>{
                let temp = item['nodes(r)'];
                return {Building: temp[0], UserId: temp[1].id, key: i}
            })
            this.setState({dataSource: data});
            console.log(json)
        }catch(e){
            console.log(e);
        }
        
    }

    handleAccpet = (key) => {
        const dataSource = [...this.state.dataSource];
        let item = dataSource[key];
        let values = {buildingId: item.Building.id, authorId: item.UserId, _sign: '123456', refuse: false}
        this.handleApply(values);
        this.setState({ dataSource: dataSource.filter(item => item.key !== key) });
    }

    handleDelete = (key) => {
        const dataSource = [...this.state.dataSource];
        let item = dataSource[key];
        let values = {buildingId: item.Building.id, authorId: item.UserId, refuse: true}
        this.handleApply(values);
        this.setState({ dataSource: dataSource.filter(item => item.key !== key) });
    }

    handleDelete2 = (key) => {
        const admin = [...this.state.admin];
        let item = admin[key];
        let values = {buildingId: this.UserData.currentBuilding.id, authorId: item.UserId}
        this.delete(values);
        this.setState({ admin: admin.filter(item => item.key !== key) });
    }

    delete = async (values)=>{
        try{
            let url = httpHead + '/systemadmin/buildingadmin?_sign=123456&buildingId=' + values.buildingId
            + '&authorId=' + values.authorId;
            let res = await fetch(url,
            {
                method: "DELETE",
                headers: {
                    'Content-Type': 'application/json',
                },
                mode: 'cors',
            });
            let json = await res.json();
            console.log(json)
        }catch(e){
            console.log(e);
        }
    }
    
    handleApply = async (values)=>{
        try{
            let url = httpHead + '/systemadmin/handleapply?_sign=123456&buildingId=' + values.buildingId
            + '&authorId=' + values.authorId + '&refuse=' + values.refuse;
            let res = await fetch(url,
            {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json',
                },
                mode: 'cors',
            });
            let json = await res.json();
            console.log(json)
        }catch(e){
            console.log(e);
        }
    }

    render()
    {
        return(
            <Tabs defaultActiveKey="1">
    <TabPane tab="管理员名单" key="1"><Table columns={this.admincol} dataSource={this.state.admin} /></TabPane>
    <TabPane tab="申请名单" key="2"><Table columns={this.columns} dataSource={this.state.dataSource} /></TabPane>
    </Tabs>
        )
    }
}

export default SystemManage;
