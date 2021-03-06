import {observable, action} from 'mobx';
import React from 'react';
import {httpHead} from './Consts'
import {message} from 'antd';
import {Control} from 'react-keeper'
import moment from 'moment'

class UserData {
    init = false;

    @observable
    isLogin = false;
    @observable
    userID = "";
    @observable
    buildingList = [];
    @observable
    currentBuilding = {};
    @observable
    currentPathList = [];
    @observable
    currentNodeList = [];
    @observable
    currentDataSetList = [];
    @observable
    currentMessageList = [];
    @observable
    currentDataSetDetail=[];
    @observable
    dataSetRestDetail=[];
    @observable
    qrNodeList = [];
    @observable
    qrcode = null; // 扫码登录信息
    @observable
    qrcodeCount = 0;
    @observable
    SystemManager = false;

    @action
    Login = async (values) => {
        /*const url = httpHead + '/building/admin/login?authorId='+values.authorId;
        let user = {
            authorId: {},
        }
        user = {...values};
        try {
            const response = await fetch(url,
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();
            if (json.code === 200) {
                this.userID = values.authorId;
                let date = moment().format("YYYYMMDD");
                window.localStorage.setItem("user", values.authorId);
                window.localStorage.setItem("date", date);
                this.isLogin = true;
                message.success('登陆成功');
                this.getBuildingList(values);
                Control.go('/ManageBuildings', {name: 'React-Keeper'})
            }
            else if (json.code === 404) {
                message.error('无效管理员ID')
            }
        }
        catch (e) {
            console.log(e)
        }*/
        this.userID = values.authorId;
        let date = moment().format("YYYYMMDD");
        window.localStorage.setItem("user", values.authorId);
        window.localStorage.setItem("date", date);
        this.isLogin = true;
        message.success('登陆成功');
        values.system = 0;
        if (this.userID === "oEzSZ5SSno_wfs3QZO8-9exppN6o"){
            this.SystemManager = true;
            values.system = 1;
        }
        this.getBuildingList(values);
        Control.go('/ManageBuildings', {name: 'React-Keeper'})
    };

    @action
    getBuildingList = async (values) => {
        let url = httpHead + '/building/admin/buildingandcount?adminId='+values.authorId+'&system='+values.system;
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
                if (json.countSums.length === 0){
                    message.success('您未管理任何建筑');
                    return;
                }
                this.buildingList = json.countSums.map((item)=>{
                    let building=  item.building;
                    delete item.building;
                    return {...building,...item}
                });
                console.log(this.buildingList.toJS());
                message.success('获取建筑列表成功')
                console.log(json.countSums)
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    };

    @action
    logout= ()=>{
        window.localStorage.clear();
        this.isLogin = false;
        this.userID = null;
    }

}

export default new UserData();