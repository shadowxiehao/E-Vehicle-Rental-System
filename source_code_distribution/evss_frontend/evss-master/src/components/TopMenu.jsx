import React, { useEffect, useState } from "react";
import { Menu, Dropdown } from "antd";
import { Link, useLocation, withRouter } from "react-router-dom";
import Logo from "../assets/logo7.png";
import {
    AimOutlined,
    UserOutlined,
    BarsOutlined,
} from "@ant-design/icons";
import "../styles/topmenu.css";

const { SubMenu } = Menu;

export const TopMenu = () => {
    const [activeKey, setActiveKey] = useState("");
    const { pathname } = useLocation();

    const onClickItem = (event) => {
        const { key } = event;
        setActiveKey(key);
    }

    const floatUserMenu = (
        <Menu
            theme='dark'
            mode="vertical"
        >
            <Menu.Item icon={<UserOutlined></UserOutlined>}>Sign Out</Menu.Item>
        </Menu>
    );

    useEffect(() => {
        console.info(pathname.split("/")[1]);
        setActiveKey(pathname.split("/")[1]);
    }, [pathname]);

    return (
        <div className="top_menu_container">
            <div className="top_menu">
                <Menu
                    theme="dark"
                    mode="horizontal"
                    selectedKeys={[activeKey]}
                    onClick={onClickItem}
                    style={{
                        height: "55px",
                        width: "100%",
                        display: "flex",
                        flexDirection: "row",
                        alignItems: "center",
                        position: "relative",
                    }}
                >
                    <Menu.Item
                        key="picture"
                        style={{ width: "150px", padding: "0px" }}
                    >
                        <img src={Logo} style={{ width: "150px", height: "55px" }} alt="pic"></img>
                    </Menu.Item>
                    <Menu.Item key="trace" icon={<AimOutlined />}>
                        <Link to="trace">Trace</Link>
                    </Menu.Item>
                    <Menu.Item key="update" icon={<BarsOutlined />}>
                        <Link to="update">Update</Link>
                    </Menu.Item>
                </Menu>
            </div>
            <div className="top-menu-user">
                <Menu
                    theme="dark"
                    mode="horizontal"
                    onClick={onClickItem}
                    style={{
                        height: "55px",
                        //width: "10%",
                        display: "flex",
                        flexDirection: "row",
                        alignItems: "center",
                        position: "relative",
                    }}
                >
                    <Dropdown overlay={floatUserMenu}>
                        <SubMenu
                            key="userInfo"
                            title="operatorA"
                            icon={<UserOutlined></UserOutlined>}
                        >
                            <Link to="login"></Link>
                        </SubMenu>
                    </Dropdown>
                </Menu>
            </div>
        </div>

    )
};
//TopMenu.propTypes = {
//     location: PropTypes.object.isRequired,
// };
// export default withRouter(TopMenu); 