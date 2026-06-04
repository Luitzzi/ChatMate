import {NavBar} from "../components/NavBar.tsx";
import {Outlet} from "react-router";
import {Flex, Layout, theme} from "antd";
import {Content} from "antd/es/layout/layout";

export const RootLayout = () => {
    const {token} = theme.useToken();

    return (
        <Flex justify={"center"}>
            <Layout
                style={{
                    height: "93vh",
                    width: "100%",
                    maxWidth: "900px",
                    marginTop: "5px",

                    background: token.colorBgContainer,
                    border: `1px solid ${token.colorBorder}`,
            }}>
                <NavBar />
                <Content>
                    <Outlet />
                </Content>
            </Layout>
        </Flex>
    )
}