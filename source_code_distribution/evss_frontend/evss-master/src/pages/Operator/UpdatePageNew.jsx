import React, { useEffect, useState } from "react";
import { Form, Input, Button, Table, Select, Tooltip, message, Space, InputNumber } from "antd"; 
import {
    QuestionCircleOutlined,
    EditTwoTone 
} from "@ant-design/icons";

import "../../styles/updatepage.css";
import {  UpdateModal } from "../../components/UpdateModal";
import { CreateModal } from "../../components/CreateModal";


import { addNewVehicle, getAllData, updateBattery, updateNewLocation, updateNewMaintainance, updateNewStatus } from "../../apis/allApis.js";

export const UpdatePageNew = () => {
    const [form] = Form.useForm();
    const [cursn, setCursn] = useState();
    const [dataSource, setDataSource] = useState([]);

    const [status, setStatus] = useState();

    const [isModalVisible, setIsModalVisible] = useState(false);
    const [isCreateVisible, setIsCreateVisible] = useState(false);


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
            if (rawData[key]["tickets"].length !== 0) {
                curproblem = rawData[key]["tickets"][0]["content"];
            }


            if (rawData[key]["location"]) {
                //location is a string with latitude and longitude split by ,
                let temploc = rawData[key]["location"].split(",");
                curlatitude = temploc[0];
                curlongitude = temploc[1];
            }
            //else  location is null --> lat = lon = ""

            switch (rawData[key]["status"]) {
                case -1:
                    curstatus = "under maintenance";
                    break;
                case 0:
                    curstatus = "standby";
                    break;
                case 1:
                    curstatus = "in use";
                    break;
                case 2:
                    curstatus = "parking";
                    break;
                case 3:
                    curstatus = "reported";
                    break;
                case 4:
                    curstatus = "defective";
                    break;
                default:
                    curstatus = "";
            }
            // curstatus = rawData[key]["status"];

            let dataObj = {
                id: curid,
                sn: cursn,
                type: curtype,
                problem: curproblem,
                position: curlatitude+","+curlongitude,
                battery: curbattery,
                // maintainance: maintainanceComponents,
                status: curstatus,
            }
            data.push(dataObj);
        });
        //generate dataSource for Table
        setDataSource(data);
    };


    useEffect(() => {
        prepareTableData();
    }, []);

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
        updateNewStatus({ "sn": cursn, "status": value });
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


    //about Modal
    const updateOnClick = (record, text) => {
        console.log("click click", text.sn);
        setCursn(text.sn);
        setIsModalVisible(true);
    }

    const handleCancel = () => {
        setIsModalVisible(false);
        console.log("current sn is!!!!!!:", cursn);
    };

    const onUpdate = (values) => {
        setIsModalVisible(false); 
        console.log("current sn is!!!!!!:", cursn);
        //process form data to axios access
        let longitude = values.position_lon;
        let latitude = values.position_lat;
        let battery = values.battery;
        let maintenance = values.maintenance;
        let status = values.status;
        updateNewLocation({ "sn": cursn, "lat": latitude, "lon": longitude });
        updateBattery({ "sn": cursn, "battery": battery });
        updateNewStatus({ "sn": cursn, "status": status });
        if ( maintenance === "yes") {
            updateNewMaintainance({"sn": cursn});
        }   
        //re-render with new data
        prepareTableData();
    };

    //关于创建
    const createOnClick = () => {
        setIsCreateVisible(true);
    }

    const onCreateVehicle = (values) => {
        setIsCreateVisible(false);
        //process form data to axios access
        let sn = values.sn;
        let type = values.type;
        addNewVehicle({ "sn": sn, "type": type });
    }

    const onCreateCancel = () => {
        console.log('Clicked cancel button');
        setIsCreateVisible(false);
    };

    const columns = [
        {
            title: 'ID',
            dataIndex: 'id',
            key: 'id',
            width: "5%",
            align: "center",
        },
        {
            title: 'Serial Number',
            dataIndex: 'sn',
            key: 'sn',
            width: "10%",
            align: "center",
        },
        {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            width: "10%",
            align: "center",
        },
        {
            title: 'Problem',
            dataIndex: 'problem',
            key: 'problem',
            width: "20%",
            align: "center",
        },
        {
            title: 'Position',
            dataIndex: 'position',
            key: 'address',
            width: "15%",
            align: "center",
        },
        {
            title: 'Battery',
            dataIndex: 'battery',
            key: 'battery',
            width: "10%",
            align: "center",
        },
        // {
        //     // title: 'Maintainance',
        //     title: (
        //         <div>
        //             <span>Maintainance</span>
        //             <Tooltip title="create a maintainance record">
        //                 <QuestionCircleOutlined />
        //             </Tooltip>
        //         </div>
        //     ),
        //     dataIndex: 'maintainance',
        //     key: 'maintainance',
        //     width: "10%",
        //     align: "center",
        // },
        {
            title: 'Status',
            dataIndex: 'status',
            key: 'status',
            width: "10%",
            align: "center",
        },
        {
            title: 'Operation',
            dataIndex: "operation",
            key: "operation",
            width: "10%",
            align: "center",
            render: (record, text) => (
                <Space>
                    <Tooltip title="update information of this vehicle">
                        <EditTwoTone
                            onClick={() => updateOnClick(record, text)}
                        >
                        </EditTwoTone>
                    </Tooltip>
                    {/* <Button onClick={() => updateOnClick(text)} style={{background:"skyblue"}}>Update</Button> */}
                    {/* <Button style={{ background: "skyblue" }}>Delete</Button> */}
                </Space>

            )
        },
    ];


    return (
    
        <div className="table-container">
            <Button 
                style={{width:"400px", background:"skyblue", margin:"10px"}}
                onClick={() => createOnClick()}
            >
                Add a new Vehicle
            </Button>
            <Table
                bordered={true}
                // size={"middle"}
                dataSource={dataSource}
                columns={columns}
                // onRow={record => {
                //     setCursn(record.sn);
                // }}
                rowKey="key"
                // onChange={rowKey => {
                //     //setRowKey(rowKey);
                // }} 
                pagination={{
                    defaultPageSize: 10
                }}
                scroll={true}
            >

            </Table>
            {isModalVisible && (
                <UpdateModal
                    visible={isModalVisible}
                    onCancel={handleCancel}
                    onUpdate={onUpdate}
                >
                </UpdateModal>
            )}

            {isModalVisible && (
                <UpdateModal
                    visible={isModalVisible}
                    onCancel={handleCancel}
                    onUpdate={onUpdate}
                >
                </UpdateModal>
            )}

            {isCreateVisible && (
                <CreateModal
                    visible={isCreateVisible}
                    onCancel={onCreateCancel}
                    onCreate={onCreateVehicle}
                >
                </CreateModal>
            )}
        </div>
    )
}