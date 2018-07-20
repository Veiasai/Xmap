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
                this.Login(values);
            }
        });
    };

    Login = async (values) => {
        const url = httpHead + '';
        let user = {
            name: {},
            message: {},
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
                    body: values.name,
                });
            const json = await response.json();

            if (json.code === 200) {

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
            <div className={'sendMessage'}>
                <Form style={{width: '80%',alignItems:'left'}} onSubmit={this.handleSubmit} className="sendMessageForm">
                    <Row>
                        <FormItem>
                            {getFieldDecorator('name', {
                                rules: [{required: true, message: '请输入发布人！'}],
                            })(
                                <Input style={{width: '20%'}} placeholder="发布人"/>
                            )}
                        </FormItem>
                    </Row>
                    <FormItem>
                        {getFieldDecorator('message', {
                            rules: [{required: true, message: '请输入消息内容！'}],
                        })(
                            <TextArea placeholder="请输入消息内容" minlength={'10'} maxlength={'140'} autosize={{ minRows: 2, maxRows: 3 }} />
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
