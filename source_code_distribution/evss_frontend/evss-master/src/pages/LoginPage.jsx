import { Button, Form, Input, InputNumber, Select} from 'antd';
import axios from 'axios';
import React,{useState, useEffect} from 'react';
import "../styles/login.css";
import { userCheck } from "../apis/allApis";
import { useNavigate } from 'react-router-dom';

const layout = {
    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 16,
    },
};

export const LoginPage = () => {
    const [passwordVisible, setPasswordVisible] = useState(false);
    // const onFinish = (values) => {
    //     console.log(values);
    // };
    const [form] = Form.useForm();

    const navigate = useNavigate();

    const checkUser = () => {
        let form_id = form.getFieldValue("id");
        let id = "test@operator.com"; 
        let password = "2bb0pndcjmfe93ofgtko6h1nip";
        let form_password = form.getFieldValue("password");
        console.log(form_id, form_password);
        let flag;
        userCheck({ id: id, password: password }).then( (res) => {
            console.log(res);
        })
        //console.log(flag);
        // userCheck({id: id, password:password}).then((res) => {
            
        // })
        // if (flag["result"] === true) {
        //     navigate("/trace");
        // } else {
        //     navigate("/");
        // }
        navigate("/trace");
    }

    return (
        <div className='background'>
            <div className='login-container'>
                <Form 
                    {...layout} 
                    name="nest-messages" 
                    // onFinish={onFinish} 
                    >
                    <Form.Item
                        name="id"
                        label="ID"
                        rules={[
                            {
                                required: true,
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    {/* <Form.Item
                        name="email"
                        label="Email"
                        rules={[
                            {
                                type: 'email',
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item> */}
                    <Form.Item
                        name="password"
                        label="Password"
                        rules={[
                            {
                                required: true,
                            },
                        ]}

                    >
                        <Input.Password
                            placeholder="input password"
                            visibilityToggle={{
                                visible: passwordVisible,
                                onVisibleChange: setPasswordVisible,
                            }}
                        />
                        
                    </Form.Item>
                    <Form.Item name="role" label="Role">
                        <Select
                            showSearch
                            // onChange={onClickStatus}
                            // onSearch={onSearch}
                            options={[
                                {
                                    value: 0,
                                    label: 'operator',
                                },
                                {
                                    value: 1,
                                    label: 'manager',
                                },

                            ]}
                        />
                    </Form.Item>
                    {/* <Form.Item name={['user', 'working targets']} label="Daily Targets">
                        <Input.TextArea />
                    </Form.Item> */}
                    <Form.Item
                        name="submit"
                        wrapperCol={{
                            ...layout.wrapperCol,
                            offset: 8,
                        }}
                    >
                        <Button 
                            type="primary" 
                            htmlType="submit"
                            onClick={() => checkUser()}
                        >
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        </div>
        
        
    );
};