import React, { useEffect, useState } from "react";
import { Form, Input, Button, Table, Select, Tooltip, message, Space, InputNumber } from "antd"; import {
    QuestionCircleOutlined,
} from "@ant-design/icons";

import "../../styles/updatepage.css";


import { getAllData, sendNewStatus, updateNewLocation, updateNewMaintainance, updateNewStatus } from "../../apis/allApis.js";

export const UpdatePage = () => {
    const [form] = Form.useForm();
    const [cursn, setCursn] = useState();
    const [dataSource, setDataSource] = useState([]);

    const [status, setStatus] = useState();
    const [latitude, setLatitude] = useState();
    const [longitude, setLongitude] = useState();
    const [sn, setSn] = useState();



    //access data for table
    const prepareTableData = async () => {
        let curid = 0;
        let cursn = "";
        let curtype = "";
        let curlongitude = "";
        let curlatitude = "";
        let curbattery = 0;
        let curstatus = -1;
        let curproblem = "-------";
        let data = [];
        let rawData = await getAllData();
        console.log("rawData", rawData);
        Object.keys(rawData).map((key) => {
            curid = rawData[key]["id"];
            cursn = rawData[key]["sn"];
            if (rawData[key]["type"] === 1) {
                curtype = "scooter";
            } else {
                curtype = "bike";
            }
            curbattery = rawData[key]["battery"];
            if (rawData[key]["tickets"].length !== 0){
                curproblem = rawData[key]["tickets"][0]["content"];
            }


            if (rawData[key]["location"]) {
                //location is a string with latitude and longitude split by ,
                let temploc = rawData[key]["location"].split(",");
                curlatitude = temploc[0];
                curlongitude = temploc[1];
            }
            //else  location is null --> lat = lon = ""

            curstatus = rawData[key]["status"];
            
            let dataObj = {
                id: curid,
                sn: cursn,
                type: curtype,
                problem: curproblem,
                position: positionComponents(cursn,curlatitude, curlongitude),
                battery: batteryComponents(cursn,curbattery),
                maintainance: maintainanceComponents,
                status: statusComponents(cursn,curstatus),
            }
            data.push(dataObj);
        });
        //generate dataSource for Table
        setDataSource(data);
    };


    useEffect(() => {
        prepareTableData();
        console.log(sn);
    }, []);



    //click event functions
    const onFinish = () => {
        message.success('Submit success!');
    };

    const onFinishFailed = () => {
        message.error('Submit failed!');
    };

    //for new position submitting
    const onClickPosition = () => {
        let longitude = form.getFieldValue("position_lon");
        let latitude = form.getFieldValue("position_lat");
        updateNewLocation({ "sn": cursn, "lat": latitude, "lon": longitude });
    };

    const onClickBattery = () => {
        let bat = form.getFieldValue("battery");
        updateNewStatus({ "sn": cursn, "battery": bat });
    };

    const onClickMaintainance = () => {
        updateNewMaintainance()
    };

    const onClickStatus = (value) => {
        updateNewStatus({"sn":cursn, "status":value});
    };

    const positionComponents = (cursn, curlatitude, curlongitude) => {
        return (
            <Form
                form={form}
                layout="vertical"
                onFinish={onFinish}
                onFinishFailed={onFinishFailed}
                autoComplete="off"
            >
                <Form.Item
                    name={"position_lat" + cursn}
                    label="Latitude:"
                    // rules={[{ required: true }, { type: 'string', min: 6 }]}
                >
                    <Input placeholder={curlatitude} />
                </Form.Item>
                <Form.Item
                    name={"position_lon"+cursn}
                    label="Longitude:"
                    // rules={[{ required: true }, { type: 'string', min: 6 }]}
                >
                    <Input placeholder={curlongitude} />
                </Form.Item>
                <Form.Item>
                    <Space>
                        <Button onClick={onClickPosition} type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Space>
                </Form.Item>
            </Form>
        );
    }; 

    const batteryComponents = (cursn,curbattery) => {
        return (
        <Form
            form={form}
            layout="vertical"
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
        >
            <Form.Item
                name={"battery"+cursn}
                rules={[{ required: true }]}
            >
                    <InputNumber name="battery" placeholder={curbattery} />
            </Form.Item>
            <Form.Item>
                <Space>
                    <Button type="primary" htmlType="submit" onClick={onClickBattery}>
                        Submit
                    </Button>
                </Space>
            </Form.Item>
        </Form>
        );
    };


    const maintainanceComponents = (
        <Space>
            <Button onClick={onClickMaintainance}>Start</Button>
            <Button onClick={onClickMaintainance}>End</Button>
        </Space>
    );

    const statusComponents = (cursn, curstatus) => {
        return (
                <Select
                    showSearch
                    placeholder="Select a status"
                    defaultValue={curstatus}
                    onChange={onClickStatus}
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
            );
        
    };
   

    
    // const simulateDataSource = [
    //     {
    //         id: 1,
    //         sn: "AABBCC47923",
    //         type: "scooter",
    //         problem: "Cannot lock this scooter",
    //         position: positionComponents(55.22213,-4.231313),
    //         battery: batteryComponents(22),
    //         maintainance: maintainanceComponents,
    //         status: statusComponents,
    //     },
    //     {
    //         id: 2,
    //         sn: "AABBCC47923",
    //         type: "scooter",
    //         problem: "Cannot lock this scooter",
    //         position: positionComponents(54.74353, -4.34242),
    //         battery: batteryComponents(90),
    //         maintainance: maintainanceComponents,
    //         status: statusComponents,
    //     },
    // ]

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
            width: "2%",
            align: "center",
        },
        {
            title: 'Serial Number',
            dataIndex: 'sn',
            key: 'sn',
            width: "4%",
            align: "center",
        },
        {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            width: "5%",
            align: "center",
        },
        {
            title: 'Problem',
            dataIndex: 'problem',
            key: 'problem',
            width: "10%",
            align: "center",
        },
        {
            title: 'Position',
            dataIndex: 'position',
            key: 'address',
            width: "10%",
            align: "center",
        },
        {
            title: 'Battery',
            dataIndex: 'battery',
            key: 'battery',
            width: "10%",
            align: "center",
        },
        {
            // title: 'Maintainance',
            title: (
                <div>
                    <span>Maintainance</span>
                    <Tooltip title="create a maintainance record">
                        <QuestionCircleOutlined />
                    </Tooltip>
                </div>
            ),
            dataIndex: 'maintainance',
            key: 'maintainance',
            width: "10%",
            align: "center",
        },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status',
            width: "10%",
            align: "center",
        },
    ];
    return ( 
        <div className="table-container">
            <Table 
                // bordered={true}
                size={"middle"}
                dataSource={dataSource} 
                columns={columns}
                onRow={record => {
                    setCursn(record.sn);
                }}
                rowKey="key"
                // onChange={rowKey => {
                //     //setRowKey(rowKey);
                // }} 
                pagination={{
                    defaultPageSize:3
                }}
                scroll={true}
            >

            </Table>
        </div>
        // <Form form={form}>
        //     <Form.List>
        //         { (fields, { add, remove}) => {
        //             return (
        //         <Table 
        //             // bordered={true}
        //             size={"middle"}
        //             dataSource={dataSource} 
        //             columns={columns}
        //             onRow={record => {
        //                 setCursn(record.sn);
        //             }}
        //             rowKey="key"
        //             // onChange={rowKey => {
        //             //     //setRowKey(rowKey);
        //             // }} 
        //             pagination={{
        //                 defaultPageSize:3
        //             }}
        //             scroll={true}
        //         >
        //         </Table>
        //         )
        //     }}
        //     </Form.List>
        // </Form>
    )
}