import React, {Component} from 'react';
import {} from 'antd';
import "./ManageBuildings.css"
import {view as Building} from '../../Components/Building'
import {inject, observer} from "mobx-react/index";
import {Control} from "react-keeper";
import {httpHead} from "../../Consts";

@inject(['UserData'])
@observer
class ManageBuildings extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }
l

    render() {
        return (
            <div>
                <div>我管理的建筑</div>
                { this.props.UserData.buildingList.map((item,index) => {
                    return (
                        <div key={index}>
                            <Building building={item} />
                        </div>
                    )
                })}

            </div>
        )
    }
}

export default ManageBuildings;
