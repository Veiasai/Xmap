import React, { Component } from 'react';
import {Layout, Row, Col} from 'antd';
import {Control} from 'react-keeper'
import {BrowserRouter as Router, Route} from 'react-keeper'
import "./Manage.css"
import {view as SideBar} from "../../SideBar";
import {view as BuildingOverview}  from "../../BuildingOverview";
import {view as PathsInBuilding}  from "../../PathsInBuilding";
import {view as NodesInBuilding}  from "../../NodesInBuilding";
import {view as ManageMessages}  from "../../ManageMessages";

const { Header, Sider, Content } = Layout;


class Manage extends Component
{
    render()
    {
        return(
            <div className={'mainManagePage'}>
                <Layout>
                        <Router>
                           <div>
                               <SideBar/>
                               <Row className="App" type="flex" justify="space-around" align="middle">
                                   <Col span={16}>
                                       <Route path={"/>"} component={BuildingOverview}/>
                                       <Route path={"/PathsInBuilding"} component={PathsInBuilding}/>
                                       <Route path={"/NodesInBuilding"} component={NodesInBuilding}/>
                                       <Route path={"/ManageMessages"} component={ManageMessages}/>
                                   </Col>
                               </Row>
                           </div>
                        </Router>
                </Layout>
            </div>
        )
    }
}

export default Manage;
