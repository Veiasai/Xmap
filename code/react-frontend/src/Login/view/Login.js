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
                this.UserData.userID = json.userId;
                this.UserData.isLogin = true;
                message.success('登陆成功');
                this.getBuildingList({authorId: json.userId});
                Control.go('/ManageBuildings', {name: 'React-Keeper'})
            }else{
                message.error("登录过时") // 信息失效
                this.UserData.qrcode = null;
            }
        }catch(e){

        }
    }

    Login = async (values) => {
        const url = httpHead + '/building/admin/login?authorId='+values.authorId;
        let user = {
            authorId: {},
        }
        user = {...values};
        try {
            const response = await fetch(url,
                {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();
            if (json.code === 200) {
                this.UserData.userID = values.authorId;
                this.UserData.isLogin = true;
                message.success('登陆成功');
                this.getBuildingList(values);
                Control.go('/ManageBuildings', {name: 'React-Keeper'})
            }
            else if (json.code === 404) {
                message.error('无效管理员ID')
            }
        }
        catch (e) {
            console.log(e)
        }
    };

    getBuildingList = async (values) => {
        const url = httpHead + '/building/admin/buildingandcount?adminId='+values.authorId;
        try {
            const response = await fetch(url,
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    mode: 'cors',
                });
            const json = await response.json();

            if (json.code === 200) {
                if (json.countSums.length === 0){
                    message.success('您未管理任何建筑');
                    return;
                }
                this.UserData.buildingList = json.countSums.map((item)=>{
                    let building=  item.building;
                    delete item.building;
                    return {...building,...item}
                });
                console.log(this.UserData.buildingList.toJS());
                message.success('获取建筑列表成功')
                console.log(json.countSums)
            }
            else if (json.code === 404) {
            }
        }
        catch (e) {
            console.log(e)
        }
    };

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className={'login'}>
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <FormItem>
                        {getFieldDecorator('authorId', {
                            rules: [{required: true, message: 'Please input your username!'}],
                        })(
                            <Input placeholder="管理员ID"/>
                        )}
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" className="login-form-button">
                            登录
                        </Button>
                    </FormItem>
                </Form>
                {this.UserData.qrcode === null ? <Button onClick={this.refresh}>刷新</Button> : 
                    <Qrcode size={300} value={JSON.stringify(this.UserData.qrcode)}/>}
          
                
            </div>
        );
    }
}

export default Form.create()(Login);
