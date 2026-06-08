import {useState} from "react";
import type {RegistrationRequest} from "../generated/api-types.ts";
import {Button, Flex, Form, Image, Input, notification, Spin} from "antd";
import {useNavigate} from "react-router";
import loginIllustration from "../assets/login-image.png";
import FormItem from "antd/es/form/FormItem";

type RegistrationFormValues = RegistrationRequest;

export const Register = () => {
    const navigate = useNavigate();
    const [registrationLoading, setRegistrationLoading] = useState<boolean>(false);

    const register = async (values: RegistrationFormValues) => {
        try {
            setRegistrationLoading(true);
            const response = await fetch(
                "http://localhost:8080/api/auth/register",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(values)
                }
            );

            if (!response.ok) {
                throw new Error(response.statusText);
            } else {
                notification.success({title: "Registration successful"});
                navigate("/login");
            }
        } catch (error) {
            notification.error({title: "Registration failed"});
            console.error("Registration failed " + error);
        } finally {
            setRegistrationLoading(true);
        }
    };

    return (
        <div style={{backgroundColor: "#fdfdfd"}}>
            {registrationLoading ?
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
                    <h1>Register</h1>
                    <Form
                        name={"login"}
                        onFinish={register}
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

                        <FormItem>
                            <Button type={"primary"} htmlType={"submit"}>
                                Register
                            </Button>
                        </FormItem>
                    </Form>
                </Flex>
            }
        </div>
    )
}