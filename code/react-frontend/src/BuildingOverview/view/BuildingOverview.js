import React, {Component} from 'react';
import {Card, Input, Row, Col} from 'antd';
import {inject, observer} from "mobx-react/index";
import {view as SendMessage} from '../../Components/SendMessage'
import "./BuildingOverview.css"

@inject(['UserData'])
@observer
class BuildingOverview extends Component {
    constructor(props) {
        super(props);

    }


    render() {
        return (
            <div className={'buildingOverview'}>
                <div className={'Overview'}>
                    <Row>
                        <div>
                            {this.props.UserData.currentBuilding.name + '信息概览'}
                        </div>
                    </Row>
                    <Row>
                        <Col>
                            <Card
                                style={{width: 300}}
                                title={this.props.UserData.currentBuilding.name + '现有路线总数'}
                                hoverable={true}
                            >
                                {this.props.UserData.currentBuilding.pathSum}
                            </Card>
                        </Col>
                        <Col>
                            <Card
                                style={{width: 300}}
                                title={this.props.UserData.currentBuilding.name + '现有点位总数'}
                                hoverable={true}
                            >
                                {this.props.UserData.currentBuilding.nodeSum}
                            </Card>
                        </Col>
                    </Row>

                </div>
                <div className={'sendMessage'}>
                    <SendMessage/>
                </div>
            </div>
        )
    }
}

export default BuildingOverview;