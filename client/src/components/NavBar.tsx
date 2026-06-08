import {Avatar, Dropdown, Flex, type MenuProps, theme} from "antd";
import {Link, useNavigate} from "react-router";
import {useAuth} from "./auth/useAuth.ts";
import {UserOutlined} from "@ant-design/icons";

export const NavBar = () => {
    const {token} = theme.useToken();
    const navigate = useNavigate();
    const {isAuthenticated, userName} = useAuth();

    const items: MenuProps["items"] = [
        {
            key: "logout",
            label: "logout",
            danger: true,
        },
    ];

    const onClick: MenuProps["onClick"] = ({key}) => {
        switch (key) {
            case "logout":
                navigate("/login");
                break;
        }
    }

    const getAvatar = () => {
        if (isAuthenticated) {
            return (
                <Dropdown menu={{ items, onClick }} trigger={["click"]}>
                    <Avatar
                        style={{marginRight: "10px", backgroundColor: token.colorPrimary,
                            userSelect: "none", cursor: "pointer"}} >
                        {userName?.charAt(0)}
                    </Avatar>
                </Dropdown>);
        } else {
            return (
                <Avatar
                    style={{marginRight: "10px", backgroundColor: token.colorPrimary,
                        userSelect: "none", cursor: "none"}}>
                    <UserOutlined />
                </Avatar>)
        }
    }

    return (
        <Flex
            justify={"space-between"}
            align={"center"}
            style={{
                border: `1px solid ${token.colorBorder}`,
                height: "40px",
            }}>
            <Link to={"/"}
                  style={{
                      height: "90%",
                      width: "100px",
                      marginLeft: "10px",
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",

                      border: `1px solid ${token.colorPrimary}`,
                      borderRadius: token.borderRadius,
                      fontWeight: "bold"
                  }}>
                ChatMate
            </Link>
            {getAvatar()}
        </Flex>
    )
}