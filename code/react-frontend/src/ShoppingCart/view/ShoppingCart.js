import React, {Component} from 'react';
import {List, Button} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ShoppingCart.css"

const QRCode = require('qrcode.react');


@inject(['UserData'])
@observer
class ShoppingCart extends Component {
    constructor(props) {
        super(props);

    }
    toUtf8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    }
    generateQrCode(){

    }
    render() {
        return (

            <div>
                <List
                    header={<div>要导出二维码的点位</div>}
                    footer={<Button onClick={() =>this.generateQrCode()}>批量生成</Button>}
                    bordered
                    dataSource={this.props.UserData.qrNodeList}
                    renderItem={item => (<List.Item>{item}</List.Item>)}
                />
            </div>
        )
    }
}

export default ShoppingCart;
