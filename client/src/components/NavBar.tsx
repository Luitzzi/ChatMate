import {Avatar, Flex, theme} from "antd";
import {Link} from "react-router";

export const NavBar = () => {
    const {token} = theme.useToken();
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
                  }}>
                ChatMate
            </Link>
            <Avatar style={{marginRight: "10px"}}/>
        </Flex>
    )
}