import {observable, action} from 'mobx';
import {message} from 'antd'
import React, { Component } from 'react';
import {Control} from "react-keeper";

class UserData{
    @observable
    isLogin = false;
    init = false;
    @observable
    user ='';
    //
    // @action.bound
    // init() {
    //     if (!this.notinit) {
    //         this.notinit = true;
    //     }
    // }

}

export default new UserData();