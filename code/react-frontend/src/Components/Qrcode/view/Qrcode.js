import React, { Component } from 'react';

import Qrcode from 'qrcode.react'
import "./Qrcode.css"

class PathList extends Component
{
    constructor(props){
        super(props);
    }
    render()
    {
        return(
            <div style={{display:'inline-block', width:'auto', height:'auto', padding:'5px'}}>
                <text>{this.props.data.name}</text>
                <br/>
                <Qrcode size={this.props.size} value={JSON.stringify(this.props.data)}/>
            </div>
        )
    }
}

export default PathList;
