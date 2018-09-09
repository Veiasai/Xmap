import React, {Component} from 'react';
import {Menu, Icon, Avatar, Popover} from 'antd';
import {Control, Link} from 'react-keeper'
import "./Navbar.css"

class Navbar extends Component {
    render() {
        return (
            <header>
                <div className='logo'>

                </div>
                <Menu
                    theme="dark"
                    defaultSelectedKeys={[Control.path]}
                    mode="horizontal"
                    style={{lineHeight: '58px'}}
                >
                    <Menu.Item key="/ManageBuildings"><Link to='/ManageBuildings'>我管理的建筑</Link></Menu.Item>
                    <Menu.Item key="/Manage"><Link to='/Manage'>当前建筑信息</Link></Menu.Item>
                    <Menu.Item key="/ShoppingCart"><Link to='/ShoppingCart'><Icon type='shopping-cart'/>批量导出</Link></Menu.Item>
                    <Menu.Item key="/logout" >注销</Menu.Item>
                </Menu>
            </header>
        )
    }
}

export default Navbar;
