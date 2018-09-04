import React, {Component} from 'react';
import {Form, Icon, Input, Button, message} from 'antd';
import "./Login.css"
import {Control} from 'react-keeper'
import {inject, observer} from "mobx-react/index";
import {httpHead} from '../../Consts'
import Qrcode from 'qrcode.react'


const FormItem = Form.Item;

@inject(['UserData'])
@observer
class Login extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }
    componentDidMount(){
        this.refresh();
    }
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                this.Login(values);
            }
        });
    };
    refresh = async ()=>{
        try{
            this.UserData.qrcodeCount++;
            let count = this.UserData.qrcodeCount;
            let url = httpHead + '/login/qrcode'
            let res = await fetch(url,
                {
                    method: "GET",
                    mode: 'cors',
                });
            let json = await res.json();
            this.UserData.qrcode = json;
            url = httpHead + '/login/check?token=' + json.token
            res = await fetch(url,{
                    method: "GET",
                    mode: 'cors',
                });
            
            json = await res.json();
            
            if (count !== this.UserData.qrcodeCount)
                return;
            
            if (json.code === 200 && json.state === 1) {
                this.UserData.Login({authorId: json.userId});
            }else if (!this.UserData.isLogin){
                message.error("登录过时") // 信息失效
                this.UserData.qrcode = null;
            }
        }catch(e){

        }
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className={'login'}>
                {/*<Form onSubmit={this.handleSubmit} className="login-form">*/}
                    {/*<FormItem>*/}
                        {/*{getFieldDecorator('authorId', {*/}
                            {/*rules: [{required: true, message: 'Please input your username!'}],*/}
                        {/*})(*/}
                            {/*<Input placeholder="管理员ID"/>*/}
                        {/*)}*/}
                    {/*</FormItem>*/}
                    {/*<FormItem>*/}
                        {/*<Button type="primary" htmlType="submit" className="login-form-button">*/}
                            {/*登录*/}
                        {/*</Button>*/}
                    {/*</FormItem>*/}
                {/*</Form>*/}
                <div>
                    {this.UserData.qrcode === null ?
                        (
                            <div>
                                <div className={'hintMessage'}>二维码已失效，请点击按钮刷新</div>
                                <Button onClick={this.refresh}>刷新</Button>
                            </div>) :
                        (
                            <div>
                                <Qrcode size={300} value={JSON.stringify(this.UserData.qrcode)}/>
                                <div className={'hintMessage'}>请用小程序扫码登录</div>
                            </div>)}
                </div>

            </div>
        );
    }
}

export default Form.create()(Login);
