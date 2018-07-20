import React, { Component } from 'react';
import {Card, Icon, Avatar} from 'antd';
import {inject, observer} from "mobx-react/index";
import {Control} from 'react-keeper'
import {httpHead} from '../../../Consts'
import "./Building.css"


@inject(['UserData'])
@observer
class Building extends Component
{
    constructor(props){
        super(props);
        this.UserData = this.props.UserData;
    }

    handleClick(){
        this.UserData.currentBuilding = this.props.building;
        this.getBuildingPathList();
        this.getBuildingNodeList();
        this.getBuildingMessageList();
        Control.go('./Manage')
    }

    getBuildingPathList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentPathList = json.pathList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    }

    getBuildingNodeList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentNoteList = json.nodeList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    }

    getBuildingMessageList = async () => {
        const url = httpHead + '';
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                    body: this.UserData.currentBuilding.ID,
                });
            const json = await response.json();

            if (json.code === 200) {
                this.UserData.currentMessageList = json.messageList;
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    }


    render()
    {
        return(
            <div className={'building'}>
                <Card
                    style={{ width: 300 }}
                    title={this.props.building.name}
                    hoverable={true}
                    onClick={() => this.handleClick()}
                >
                    {this.props.building.address}
                </Card>
            </div>
            
        )
    }
}

export default Building;
