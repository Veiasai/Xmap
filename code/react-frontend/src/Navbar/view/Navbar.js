import React, {Component} from 'react';
import {Menu} from 'antd';
import "./Navbar.css"

class Navbar extends Component {
    render() {
        return (
            <Menu
                theme="dark"
                mode="horizontal"
                style={{lineHeight: '64px'}}
            >
                <Menu.Item key="1">首页</Menu.Item>
            </Menu>
        )
    }
}

export default Navbar;
