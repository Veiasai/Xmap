import React, { Component } from 'react';
import {Form, Icon, Input, Button, message} from 'antd';
import "./Login.css"
import {Control} from 'react-keeper'
import {inject, observer} from "mobx-react/index";

const FormItem = Form.Item;

@inject(['UserData'])
@observer
class Login extends Component
{
    constructor(props) {
        super(props);
        this.Userdata = this.props.Userdata;
    }
    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                this.Login(values);

            }
        });
    }

    Login = async (values) =>
    {
        const url = '';
        let user = {
            name:{},
            password:{}
        }
        user ={...values};
        const  response = await fetch(url,
            {
                method: "POST",
                headers:{
                    'Content-Type':'application/json',
                },
                mode: 'cors',
                body: JSON.stringify(user),
            });
        const json = await response.json();
        if(json.code == 100)
        {
            message.error('用户被禁止登陆')
        }
    }

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className={'login'}>
                <Form onSubmit={this.handleSubmit} className="login-form">
                    <FormItem>
                        {getFieldDecorator('name', {
                            rules: [{required: true, message: 'Please input your username!'}],
                        })(
                            <Input   placeholder="管理员用户名"/>
                        )}
                    </FormItem>
                    <FormItem>
                        {getFieldDecorator('password', {
                            rules: [{required: true, message: 'Please input your Password!'}],
                        })(
                            <Input type="password"
                                   placeholder="管理员密码"/>
                        )}
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" className="login-form-button">
                            登录
                        </Button>
                    </FormItem>
                </Form>
            </div>
        );
    }
}

export default Form.create()(Login);
