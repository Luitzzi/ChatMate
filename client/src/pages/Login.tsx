import {Button, Flex, Form, Image, Input, notification, Space, Spin} from "antd";
import FormItem from "antd/es/form/FormItem";

import loginIllustration from '../assets/login-image.png';
import type {LoginReply, LoginRequest} from "../generated/api-types.ts";
import {useAuth} from "../components/auth/useAuth.ts";
import {useState} from "react";
import {useNavigate} from "react-router";

type LoginFormValues = LoginRequest;

export const Login = () => {
    const {setToken, setUserId, setUserName} = useAuth();
    const navigate = useNavigate();
    const [loginLoading, setLoginLoading] = useState<boolean>(false);

    const onLogin = async (values: LoginFormValues) => {
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
                throw new Error(response.statusText);
            const reply: LoginReply = await response.json();
            setToken(reply.authToken);
            setUserId(reply.userId);
            setUserName(values.username);
            notification.success({title: "Login successful"});
            navigate("/chat");
        } catch (error) {
            notification.error({title: "Login failed", description: "Please try agian"});
            console.error("Login failed:", error);
        } finally {
            setLoginLoading(false);
        }
    }

    const onRegister = () => {
        navigate("/register");
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
                    <h1>Login</h1>
                    <Form
                    name={"login"}
                    onFinish={onLogin}
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
                            <Input.Password />
                        </FormItem>
                        <Flex justify={"space-between"}>
                            <FormItem>
                                <Button onClick={onRegister}>
                                    Register
                                </Button>
                            </FormItem>

                            <FormItem>
                                <Button type={"primary"} htmlType={"submit"}>
                                    Submit
                                </Button>
                            </FormItem>
                        </Flex>
                    </Form>
                </Flex>
            }
        </div>
    )
}