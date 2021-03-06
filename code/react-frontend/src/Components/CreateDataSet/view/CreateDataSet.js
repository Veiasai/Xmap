import React, { Component } from 'react';
import {Form, Icon, Input, Button, message,Row,Col} from 'antd';
import {inject, observer} from "mobx-react/index";
import "./CreateDataSet.css"
import {httpHead} from '../../../Consts'

const FormItem = Form.Item;
const { TextArea } = Input;

@inject(['UserData'])
@observer
class CreateDataSet extends Component
{
    constructor(props) {
        super(props);
        this.UserData = this.props.UserData;
    }

    handleSubmit = (e) => {
        e.preventDefault();
        this.props.form.validateFields((err, values) => {
            if (!err) {
                console.log('Received values of  form: ', values);
                this.createDataSet(values);
            }
        });
    };
    createDataSet = async (values) => {
        const url = httpHead + '/dataset?buildingId=' + this.UserData.currentBuilding.id + '&authorId=' + this.UserData.userID;
        let dataset = {
            name: {},
            type: {},
        }
        dataset = {...values};
        if(dataset.type!=='node'&&dataset.type!=='path'){
            message.error("类型不正确")
        }
        else{
            try {
                const response = await fetch(url,
                    {
                        method: "POST",
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        mode: 'cors',
                        body: JSON.stringify(dataset),
                    });
                const json = await response.json();

                if (json.code === 200) {
                    message.success('创建成功');
                    this.UserData.currentDataSetList = [...this.UserData.currentDataSetList,json.dataSet];
                    this.props.form.resetFields()
                }
                else{
                    message.error('出错了')
                }
            }
            catch (e) {
                console.log(e)
            }
        }
    };
    render()
    {
        const {getFieldDecorator} = this.props.form;
        return(
            <div>
                <Form style={{width: '80%',alignItems:'left'}} onSubmit={this.handleSubmit} className="sendMessageForm">
                    <FormItem>
                        {getFieldDecorator('name', {
                            rules: [{required: true, message: '请输入数据组名称'}],
                        })(
                            <Input style={{width: '20%'}} placeholder='数据组名称'/>
                        )}
                    </FormItem>
                    <FormItem>
                        {getFieldDecorator('type', {
                            rules: [{required: true, message: '请输入类型'}],
                        })(
                            <Input style={{width: '20%'}} placeholder='数据组类型'/>
                        )}
                    </FormItem>
                    <FormItem>
                        <Button type="primary" htmlType="submit" className="sendMessageFormButton">
                            创建数据组
                        </Button>
                    </FormItem>
                </Form>
            </div>
        )
    }
}

export default Form.create()(CreateDataSet);
