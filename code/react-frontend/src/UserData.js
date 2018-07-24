import {observable, action} from 'mobx';
import React from 'react';

class UserData{
    init = false;

    @observable
    isLogin = false;
    @observable
    userID ='';
    @observable
    buildingList = [];
    @observable
    currentBuilding = {
        id: 'ba974511-df18-4b7d-a8e0-9257b79b2bb8',
        name: '交通大学闵行校区-软件学院',
    };
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