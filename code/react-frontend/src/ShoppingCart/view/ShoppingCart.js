import React, {Component} from 'react';
import {List, Button} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ShoppingCart.css"
import $ from 'jquery'
const QRCode = require('qrcode.react');


@inject(['UserData'])
@observer
class ShoppingCart extends Component {
    constructor(props) {
        super(props);

    }

    qrcode () {
        let qrcode = new QRCode('qrcode', {
            width: 100,
            height: 100, // 高度
            text: '56663159' // 二维码内容
            // render: 'canvas' // 设置渲染方式（有两种方式 table和canvas，默认是canvas）
            // background: '#f0f'
            // foreground: '#ff0'
        })
        console.log(qrcode)
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
        this.qrcode();
    }
    render() {
        return (

            <div>
                <div id="qrcode">二维码位置</div>
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
