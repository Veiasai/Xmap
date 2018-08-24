import React, {Component} from 'react';
import {Layout, Row, Col, message} from 'antd';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-keeper'
import {inject, observer} from "mobx-react/index";
import moment from 'moment'

import {view as Login} from './Login';
import {view as ManageBuildings} from './ManageBuildings';
import {view as Manage} from './Manage';
import {view as ShoppingCart} from "./ShoppingCart"
import {view as Navbar} from "./Navbar";
import {view as SendMessage} from "./Components/SendMessage";

@inject(['UserData'])
@observer
class App extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }
    componentDidMount(){
        let user = window.localStorage.getItem("user");
        let date = window.localStorage.getItem("date");
        if (user !== null) {
            if (moment().format("YYYYMMDD") === date){
            this.UserData.Login({authorId: user});
            }else{
            window.localStorage.clear();
            message.error("登录信息失效，请重新登录");
            }
        }else{
            window.localStorage.clear();
        }
    }
    render() {
        if (!this.UserData.isLogin){
            return <Login/>
        }else{
            return (
                <Router>
                    <div>
                        <Navbar/>
                        {/* <Route path={"/>"} component={Login}/> */}
                        <Route path={"/ManageBuildings"} component={ManageBuildings}/>
                        <Route path={"/Manage"} component={Manage}/>
                        <Route path={"/ShoppingCart"} component={ShoppingCart}/>
                    </div>
                </Router>
            );
        }
    }
}

export default App;
