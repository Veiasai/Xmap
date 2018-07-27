import {observable, action} from 'mobx';
import React from 'react';

class UserData {
    init = false;

    @observable
    isLogin = false;
    @observable
    userID = 'oEzSZ5RA3PFN5Ho0J0Bz0i66Gp9k';
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
    qrNodeList = [{id: "b", name: "1", img: "1"},
        {id: "a", name: "点位一", img: "2"}];

    //
    // @action.bound
    // init() {
    //     if (!this.notinit) {
    //         this.notinit = true;
    //     }
    // }

}

export default new UserData();