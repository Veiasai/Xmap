import React, {Component} from 'react';
import {Form, Icon, Input, Button, message,Row,Col} from 'antd';
import "./SendMessage.css"
import {Control} from 'react-keeper'
import {inject, observer} from "mobx-react/index";
import {httpHead} from '../../../Consts'

const FormItem = Form.Item;
const { TextArea } = Input;

@inject(['UserData'])
@observer
class SendMessage extends Component {
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of form: ', values);
                this.sendMessage(values);
            }
        });
    };

    sendMessage = async (values) => {
        const url = httpHead + '/message?buildingId=' + this.UserData.currentBuilding.id + '&authorId=' + this.UserData.userID;
        let user = {
            title: {},
            content: {},
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
                    body: JSON.stringify(user),
                });
            const json = await response.json();

            if (json.code === 200) {
                message.success('发布成功')
                Control.go('/Manage');
            }
            else{
                message.error('出错了')
            }
        }
        catch (e) {
            console.log(e)
        }
    };

    render() {
        const {getFieldDecorator} = this.props.form;
        return (
            <div className={'sendMessage'}>
                <Form style={{width: '80%',alignItems:'left'}} onSubmit={this.handleSubmit} className="sendMessageForm">
                    <FormItem>
                        {getFieldDecorator('title', {
                            rules: [{required: true, message: '请输入标题'}],
                        })(
                            <Input style={{width: '20%'}} placeholder='标题'/>
                        )}
                    </FormItem>
                    <FormItem>
                        {getFieldDecorator('content', {
                            rules: [{required: true, message: '请输入消息内容！'}],
                        })(
                            <TextArea placeholder="请输入消息内容" minLength={'10'} maxLength={'140'} autosize={{ minRows: 2, maxRows: 3 }} />
                        )}
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" className="sendMessageFormButton">
                            发布消息
                        </Button>
                    </FormItem>
                </Form>
            </div>
        );
    }
}

export default Form.create()(SendMessage);
