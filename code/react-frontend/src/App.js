import React, {Component} from 'react';
import {Layout, Row, Col} from 'antd';
import './App.css';
import {BrowserRouter as Router, Route} from 'react-keeper'
import {view as Login} from './Login';
import {view as ManageBuildings} from './ManageBuildings';
import {view as Manage} from './Manage';
import {view as ShoppingCart} from "./ShoppingCart"
import {view as Navbar} from "./Navbar";
import {view as SendMessage} from "./Components/SendMessage";


class App extends Component {
    render() {
        return (
            <Router>
                <div>
                    <Navbar/>
                    <Route path={"/>"} component={Login}/>
                    <Route path={"/ManageBuildings"} component={ManageBuildings}/>
                    <Route path={"/Manage"} component={Manage}/>
                    <Route path={"/ShoppingCart"} component={ShoppingCart}/>
                </div>
            </Router>
        );
    }
}

export default App;
