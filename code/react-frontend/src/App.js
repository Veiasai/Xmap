import React, {Component} from 'react';
import {Row,Col} from 'antd';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-keeper'
import {view as Login} from './Login';
import {view as ManageBuildings} from './ManageBuildings';
import {view as Manage} from './Manage';
import {view as Navbar} from "./Navbar";
import {view as SendMessage} from "./Components/SendMessage";



class App extends Component {
    render() {
        return (
            <div>
                <Router>
                    <div>
                        <Navbar/>
                        <Row className="App" type="flex" justify="space-around" align="middle">
                            <Col span={24}>
                                <Route path={"/>"} component={Login}/>
                                <Route path={"/ManageBuildings"} component={ManageBuildings}/>
                                <Route path={"/Manage>"} component={Manage}/>
                            </Col>
                        </Row>
                    </div>
                </Router>
            </div>
        );
    }
}

export default App;
