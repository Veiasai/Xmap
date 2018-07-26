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
                <Menu.Item key="/Manage"><Link to='/Manage'>信息概览</Link></Menu.Item>
                <Menu.Item key="/Manage/PathsInBuilding"><Link to='/Manage/PathsInBuilding'>路线管理</Link></Menu.Item>
                <Menu.Item key="/Manage/NodesInBuilding"><Link to='/Manage/NodesInBuilding'>点位管理</Link></Menu.Item>
                <Menu.Item key="/Manage/DataSetInBuilding"><Link to='/Manage/DataSetsInBuilding'>数据组管理</Link></Menu.Item>
                <Menu.Item key="/Manage/ManageMessages"><Link to='/Manage/ManageMessages'>公告管理</Link></Menu.Item>

            </Menu>
        )
    }
}

export default SideBar;
