import React, { Component } from 'react';
import {} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ShoppingCart.css"

@inject(['UserData'])
@observer
class ShoppingCart extends Component
{
     constructor(props) {
        super(props);
    }
    
    render()
    {
        return(
            <div>
            
            </div>
        )
    }
}

export default ShoppingCart;
