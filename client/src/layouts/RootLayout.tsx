import {NavBar} from "../components/NavBar.tsx";
import {Outlet} from "react-router";
import {Flex, Layout} from "antd";
import {Content} from "antd/es/layout/layout";

export const RootLayout = () => {
    return (
        <Flex justify={"center"}>
            <Layout
                style={{
                    height: "90vh",
                    width: "100%",
                    maxWidth: "900px",
            }}>
                <NavBar />
                <Content>
                    <Outlet />
                </Content>
            </Layout>
        </Flex>
    )
}