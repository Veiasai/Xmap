import React, {Component} from 'react';
import {Layout, Row, Col} from 'antd';
import {BrowserRouter as Router, Route, Control} from 'react-keeper'
import "./Manage.css"
import {view as SideBar} from "../../SideBar";
import {view as BuildingOverview} from "../../BuildingOverview";
import {view as PathsInBuilding} from "../../PathsInBuilding";
import {view as NodesInBuilding} from "../../NodesInBuilding";
import {view as DataSetsInBuilding} from "../../DataSetsInBuilding";
import {view as ManageMessages} from "../../ManageMessages";
import {inject, observer} from "mobx-react/index";
import {message} from "antd/lib/index";

const {Header, Sider, Content} = Layout;

@inject(['UserData'])
@observer
class Manage extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
        if(this.UserData.isLogin === false)
        {
            Control.go('/')
        }
    }

    render() {
        return (
            <Layout className='mainManagePage'>
                <Sider>
                    <SideBar/>
                </Sider>
                <Layout>
                    <Content>
                        <Row className="App" type="flex" justify="space-around" align="middle">
                            <Col span={16}>
                                <Route path={"/>"} component={BuildingOverview}/>
                                <Route path={"/PathsInBuilding"} component={PathsInBuilding}/>
                                <Route path={"/NodesInBuilding"} component={NodesInBuilding}/>
                                <Route path={"/DataSetsInBuilding"} component={DataSetsInBuilding}/>
                                <Route path={"/ManageMessages"} component={ManageMessages}/>
                            </Col>
                        </Row>
                    </Content>
                </Layout>
            </Layout>
        )
    }
}

export default Manage;
