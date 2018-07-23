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
        Control.go('./Manage')
    }


    render()
    {
        return(
            <div className={'building'}>
                <Card
                    style={{ width: 300 }}
                    hoverable={true}
                    onClick={() => this.handleClick()}
                >
                    <p className={'buildingName'}>{this.props.building.name}</p>
                </Card>
            </div>
            
        )
    }
}

export default Building;
