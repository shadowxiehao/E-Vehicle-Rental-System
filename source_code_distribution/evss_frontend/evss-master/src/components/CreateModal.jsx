import { Button, Modal, Form, Input, Select, InputNumber } from 'antd';
import React, { useEffect, useState } from "react";


export const CreateModal = (props) => {
    const [form] = Form.useForm();
    const [confirmLoading, setConfirmLoading] = useState(false);
    // const showModal = () => {
    //     setOpen(true);
    // };
    const handleOk = () => {
        form.validateFields().then((values) => {
            if (values) {
                props.onCreate({ ...values });
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
                title="Add a new vehicle"
                open={props.visible}
                onOk={handleOk}
                confirmLoading={confirmLoading}
                onCancel={handleCancel}
            >
                <Form
                    form={form}
                    layout="vertical"
                    labelAlign='left'
                    name="create_information"
                >
                    <Form.Item
                        name="sn"
                        label="Serial Number:"
                        rules={[{ required: true }]}
                    >
                        <Input placeholder={"input a sn like AABBCC445566"} />
                    </Form.Item>
                    <Form.Item
                        name="type"
                        label="Type:"
                    >
                        <Select
                            showSearch
                            placeholder="Select a type"
                            // onChange={onClickStatus}
                            // onSearch={onSearch}
                            options={[
                                {
                                    value: 1,
                                    label: 'scooter',
                                },
                                {
                                    value: 2,
                                    label: 'bike',
                                },
                            ]}
                        />

                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
};
