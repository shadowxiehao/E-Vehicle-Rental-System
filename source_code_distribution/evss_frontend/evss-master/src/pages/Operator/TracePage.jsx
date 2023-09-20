import React, { useState, useEffect } from 'react';
import { Menu, Layout, Button, Table } from "antd";
import { useLocation, Route, Routes, Link } from 'react-router-dom';
import {
    CarOutlined,
    RocketOutlined,
    GlobalOutlined,
    TableOutlined,
    TabletTwoTone,
} from "@ant-design/icons";
import "../../styles/tracepage.css";
import 'antd/dist/antd.css';
import  TraceMap  from "../../components/TraceMap.jsx";

import { getAllData } from "../../apis/allApis.js";

import axios from "axios";

const { Header, Sider, Content } = Layout;

const curPresent = {
    height: "60px",
    color: "white",
    fontSize: "15px",
    display: "flex",
    flexDirection: "row",
    alignItems: "center",
}
export const TracePage = () => {
    const [menuItem, setMenuItem] = useState("All");
    const [menuKey, setMenuKey] = useState("all");
    // const { pathname } = useLocation();
    const [activeKey, setActiveKey] = useState("");
    const [mapData, setMapData] = useState([]);
    const [bikeData, setBikeData] = useState([]);
    const [scooterData, setScooterData] = useState([]);

    const [mesData, setMesData] = useState([]);
    const [bikeMesData, setBikeMesData] = useState([]);
    const [scooterMesData, setScooterMesData] = useState([]);

//packaged method has some problems, use "async + await" because it takes long time 
//to request data from server, only rawData were obtained could later steps go
    const prepareMapData = async () => {
        let resultAll = [];
        let resultBike = [];
        let resultScooter = [];
        let mesAll = [];
        let mesBike = [];
        let mesScooter = [];
        let rawData = await getAllData();
        console.log("rawData", rawData);
        Object.keys(rawData).map((key) => {
            if (rawData[key]["location"]) {
                //location is a string with latitude and longitude split by ,
                let temploc = rawData[key]["location"].split(",");
                resultAll.push({ "latitude": parseFloat(temploc[0]), "longitude": parseFloat(temploc[1]) });
                mesAll.push({ "id": rawData[key]["id"], 
                              "battery": rawData[key]["battery"],
                              "sn": rawData[key]["sn"],
                            });
                console.log(resultAll);
                if (rawData[key]["type"] === 1) { //scooters
                    resultScooter.push({ "latitude": parseFloat(temploc[0]), "longitude": parseFloat(temploc[1]) });
                    mesScooter.push({
                        "id": rawData[key]["id"],
                        "battery": rawData[key]["battery"],
                        "sn": rawData[key]["sn"],
                    });
                } 
                else if (rawData[key]["type"] === 2) {
                    resultBike.push({ "latitude": parseFloat(temploc[0]), "longitude": parseFloat(temploc[1]) });
                    mesBike.push({
                        "id": rawData[key]["id"],
                        "battery": rawData[key]["battery"],
                        "sn": rawData[key]["sn"],
                    });
                }

            }
        });
        // setMapData(resultAll);
        // setMesData(mesAll);
        
        // setBikeData(resultBike);
        // setScooterData(resultScooter);
        // setMapData(resultAll);

        // setBikeMesData(mesBike);
        // setScooterMesData(mesScooter);
        // setMesData(mesAll);
        if (menuKey === "all") {
            setMapData(resultAll);
            setMesData(mesAll);
        } 
        else if (menuKey === "scooters") {
            setMapData(resultScooter);
            setMesData(mesScooter);
        }
        else if (menuKey === "bikes") {
            setMapData(resultBike);
            setMesData(mesBike);
        }
        else {
            setMapData(resultAll);
            setMesData(mesAll);
        }
    };


    useEffect( () => {
        prepareMapData();
    }, []);

    // useEffect( () => {
    //     let data = {};
    //     let resultAll = [];
    //     let resultBike = [];
    //     let resultScooter = [];
    //     getLocationData().then( (res) => {
    //         data = res.data;
    //     }).then( () => {
    //         //process data from server, distill location information from all messages of vehicle
    //         console.log("location data", data);
    //         Object.keys(data).forEach((item) => {
    //             let str = item.location;
    //             if (str) {
    //                 let a = str.split(",");
    //                 console.log(a);
    //             }
    //         })
    //     })
    // },[]);

    //prepare data for this map
    // useEffect( () => {
    //     axios.post("/vehicle/find", { headers: { "Content-Type": "application/json" } }).then( (res) => {
    //         console.log(res.data);
    //     },
    //     error => {
    //         console.log("request failed!!", error);
    //     }
    //     )
    // },[]);


    const getCurrentMenuItem = (obj) => {
        console.info("getCurrentMenuItem", obj.key);
        switch (obj["key"]) {
            case "scooters":
                setMenuItem("Scooters");
                setMenuKey("scooters");
                break;
            case "bikes":
                setMenuItem("Bikes");
                setMenuKey("bikes");
                break;
            default:
                setMenuItem("all");
                setMenuKey("all");
        }
        setActiveKey(obj.key);
    };


    return (
        <Layout className="container">
            <Sider
                trigger={null}
                collapsible
                // collapsed={isCollapsed}
                className="sider"
                width={"150px"}
            >
                <Menu
                    theme="dark"
                    mode="inline"
                    defaultSelectedKeys={["all"]}
                    selectedKeys={[activeKey]}
                    style={{ width: "150px" }}
                    onClick={getCurrentMenuItem}
                >
                    <Menu.Item key="all" icon={<RocketOutlined />}>All</Menu.Item>
                    <Menu.Item key="scooters" icon={<GlobalOutlined />}>Scooters</Menu.Item>
                    <Menu.Item key="bikes" icon={<TableOutlined />}>Bikes</Menu.Item>
                </Menu>
                {/* <div className='bar'><p>pattern:</p><p> </p></div> */}
                {/* <div className="option">
                    <Link to="/trace/maps">
                        <GlobalOutlined twoToneColor="#eb2f96" />
                    </Link>
                    <Link to="/trace/tables">
                        <TableOutlined twoToneColor="#eb2f96" />
                    </Link>
                </div> */}

            </Sider>
            <Layout>
                <Content className='changing-content'>
                    <TraceMap locations = {mapData} messages = {mesData}></TraceMap>
                </Content>
            </Layout>
        </Layout>
    )
}