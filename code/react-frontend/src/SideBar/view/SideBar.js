import React, { Component } from 'react';
import {Menu} from 'antd';
import {Link, Control} from 'react-keeper'
import "./SideBar.css"

class SideBar extends Component
{
    constructor()
    {
        super();
    }

    render()
    {
        return(
            <Menu
                mode="inline"
                defaultSelectedKeys={[Control.path]}
                style={{ height: '100%', borderRight: 0 }}
            >
                <Menu.Item key="1"><Link to='/Manage'>信息概览</Link></Menu.Item>
                <Menu.Item key="2"><Link to='/Manage/PathsInBuilding'>路线管理</Link></Menu.Item>
                <Menu.Item key="3"><Link to='/Manage/NodesInBuilding'>点位管理</Link></Menu.Item>
                <Menu.Item key="4"><Link to='/Manage/ManageMessages'>公告管理</Link></Menu.Item>
                <Menu.Item key="5"><Link to='/Manage/ShoppingCart'>点位导出</Link></Menu.Item>
            </Menu>
        )
    }
}

export default SideBar;
