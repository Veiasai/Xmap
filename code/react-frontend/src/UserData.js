import {observable, action} from 'mobx';
import {message} from 'antd'
import React, { Component } from 'react';
import {Control} from "react-keeper";

class UserData{
    init = false;

    @observable
    isLogin = false;
    @observable
    userID ='';
    @observable
    buildingList = [];
    @observable
    currentBuilding = null;
    @observable
    currentPathList = [];
    @observable
    currentNoteList = [];
    @observable
    currentMessageList = [];
    //
    // @action.bound
    // init() {
    //     if (!this.notinit) {
    //         this.notinit = true;
    //     }
    // }

}

export default new UserData();