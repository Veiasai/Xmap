import React, {Component} from 'react';
import {List, Button, Slider, InputNumber,  Row, Col} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./ShoppingCart.css"
import JsZip from 'jszip'
import  FileSaver from 'file-saver'
import {view as Qrcode} from '../../Components/Qrcode'
import html2canvas from 'html2canvas'
import {Control} from "react-keeper";



@inject(['UserData'])
@observer
class ShoppingCart extends Component {
    constructor(props) {
        super(props);
        if(this.props.UserData.isLogin === false)
        {
            Control.go('/')
        }
        this.state= {
            size: 128,
        }
    }

    onChange = (value) => {
        this.setState({
            size: value,
        });
    }

    async generateQrCode(){
        let nodes = this.props.UserData.qrNodeList;
        let len = nodes.length;
        let imgs = [];
        let zip = new JsZip();
        let img = zip.folder("images")
        for (let i = 0;i<len;i++){
            //let temp = (<Qrcode data={nodes[i]}/>);
            console.log(this.refs)
            let temp = this.refs[nodes[i].id];
            console.log(temp);
            let res = await html2canvas(temp);
            let url = res.toDataURL();
            res.toBlob((blob)=>{
                img.file(nodes[i].name+ ".png", blob)
                if (i === len - 1){
                    zip.generateAsync({type:"blob"}).then((content) =>{
                        // see FileSaver.js
                        FileSaver.saveAs(content, "images.zip");
                    });
                }
            });
            // img.file(nodes[i].name, url)
            imgs.push(url);
        }
        console.log(imgs);
    }
    render() {
        return (
            <div>
                <div id="qrcode" >二维码位置</div>
                <Row>
                    <Col span={6}>
                        <text>调整二维码大小</text>
                    </Col>
                    <Col span={12}>
                        <Slider min={64} max={512} onChange={this.onChange} value={this.state.size} />
                    </Col>
                    <Col span={4}>
                        <InputNumber
                            min={64}
                            max={512}
                            style={{ marginLeft: 16 }}
                            value={this.state.size}
                            onChange={this.onChange}
                        />
                    </Col>
                </Row>
                <Row>
                    <Button onClick={() =>this.generateQrCode()}>批量生成</Button>
                </Row>
                {this.props.UserData.qrNodeList.map((item)=>{
                    return (<div  width={this.state.size*3} height={this.state.size*3} ref={item.id} style={{display:'inline-block', width:'auto', height:'auto', padding:'5px'}}>
                        <Qrcode data={item} size={this.state.size}/>
                    </div>)
                })}
                </div>
        )
    }
}

export default ShoppingCart;

/* <List
                    header={<div>要导出二维码的点位</div>}
                    footer={<Button onClick={() =>this.generateQrCode()}>批量生成</Button>}
                    bordered
                    dataSource={this.props.UserData.qrNodeList}
                    renderItem={(item) => {return (<div ref="a"><Qrcode  data={item}/></div>)}}
                />
                */