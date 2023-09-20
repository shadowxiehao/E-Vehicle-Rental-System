/* eslint-disable react-hooks/rules-of-hooks */
// import { FileProtectOutlined } from '@ant-design/icons';
import { Button, Modal, Form, Input, Select, InputNumber } from 'antd';
import React, { useEffect, useState } from "react";


export const UpdateModal = (props) => {
    const [form] = Form.useForm();
    const [confirmLoading, setConfirmLoading] = useState(false);
    const [open, setOpen] = useState(false);
    // const showModal = () => {
    //     setOpen(true);
    // };
    const handleOk = () => {
        form.validateFields().then( (values) => {
            if(values) {
                props.onUpdate({...values});
            }
        });
    };

    const handleCancel = () => {
        props.onCancel();
    };


    // useEffect( ()=> {
    //     setOpen(props.visible);
    //     console.log(props.visible)
    // },[]);

    // const handleCancel = () => {
    //     console.log('Clicked cancel button');
    //     // setIsModalVisible(false);
    //     props.onCancel();
    // };


    return (
        <>
            <Modal
                title="Update information"
                open={props.visible}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}
            >
                <Form
                    form={form}
                    layout="vertical"
                    labelAlign='left'
                    name="update_information"
                >
                    <Form.Item
                        name="position_lat"
                        label="Latitude:"
                        rules={[{ required: true }]}
                    >
                        <Input placeholder={"input current latitude"} />
                    </Form.Item>
                    <Form.Item
                        name="position_lon"
                        label="Longitude:"
                        rules={[{ required: true }]}
                    >
                        <Input placeholder={"input current longitude"} />
                    </Form.Item>
                    <Form.Item
                        name="battery"
                        label="Battery:"
                        rules={[{ required: true }]}
                    >
                        <InputNumber name="battery" placeholder={"update current battery"} />
                    </Form.Item>
                    <Form.Item
                        name="maintenance"
                        label="Maintenance:"
                    >
                        <Input placeholder={"input yes or no"} />
                    </Form.Item>
                    <Form.Item
                        name="status"
                        label="Status:"
                    >
                        <Select
                            showSearch
                            placeholder="Select a status"
                            // onChange={onClickStatus}
                            // onSearch={onSearch}
                            options={[
                                {
                                    value: -1,
                                    label: 'under maintenance',
                                },
                                {
                                    value: 0,
                                    label: 'standby',
                                },
                                {
                                    value: 1,
                                    label: 'in use',
                                },
                                {
                                    value: 2,
                                    label: 'parking',
                                },
                                {
                                    value: 3,
                                    label: 'reported',
                                },
                                {
                                    value: 4,
                                    label: 'defective',
                                },
                            ]}
                        />
                        
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
};
