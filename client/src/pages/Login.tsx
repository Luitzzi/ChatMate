import {Button, Flex, Form, Image, Input, Spin} from "antd";
import FormItem from "antd/es/form/FormItem";

import loginIllustration from '../assets/login-image.png';
import type {LoginRequest} from "../generated/api-types.ts";
import {useAuth} from "../components/auth/useAuth.ts";
import {useState} from "react";
import {useNavigate} from "react-router";

type LoginFormValues = LoginRequest;

export const Login = () => {
    const {setToken} = useAuth();
    const navigate = useNavigate();
    const [loginLoading, setLoginLoading] = useState<boolean>(false);

    const login = async (values: LoginFormValues) => {
        try {
            setLoginLoading(true);
            const response = await fetch(
                "http://localhost:8080/api/auth/login",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(values)
                }
            );

            if (!response.ok)
                throw new Error("Login failed: " + response.statusText);
            const token: string = await response.text();
            console.log("Token: " + token);
            setToken(token);
            navigate("/chat");
        } catch (error) {
            console.error("Login failed:", error);
        } finally {
            setLoginLoading(false);
        }
    }

    return (
        <div style={{backgroundColor: "#fdfdfd"}}>
            {loginLoading ?
                <Spin /> :
                <Flex
                    vertical
                    align={"center"}
                >
                    <Image
                    width={"500px"}
                    src={loginIllustration}
                    preview={false}
                    />
                    <Form
                    name={"login"}
                    onFinish={login}
                    >
                     <FormItem
                         label={"Username"}
                         name={"username"}
                         rules={[{required: true, message: "Please input your username!"}]}
                     >
                         <Input />
                     </FormItem>
                     <FormItem
                         label={"Password"}
                         name={"password"}
                         rules={[{required: true, message: "Please input your password!"}]}
                     >
                         <Input />
                     </FormItem>
                     <FormItem>
                         <Button type={"primary"} htmlType={"submit"}>
                             Submit
                         </Button>
                     </FormItem>
                    </Form>
                </Flex>
            }
        </div>
    )
}