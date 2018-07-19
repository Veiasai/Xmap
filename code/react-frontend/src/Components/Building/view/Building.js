import React, { Component } from 'react';
import {Card, Icon, Avatar} from 'antd';
import "./Building.css"

const { Meta } = Card

class Building extends Component
{
    constructor(props){
        super(props);
    }


    render()
    {
        return(
            <div className={'building'}>
                <Card
                    style={{ width: 300 }}
                    cover={<img alt="example" src="https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png" />}
                    actions={[<Icon type="setting" />, <Icon type="edit" />, <Icon type="ellipsis" />]}
                >
                    <Meta
                        title={this.props.name}
                        description="This is the description"
                    />
                </Card>
            </div>
            
        )
    }
}

export default Building;
