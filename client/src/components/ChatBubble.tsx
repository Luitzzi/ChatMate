import {Flex, theme, Typography} from "antd";
import {type Message} from "./Message.ts";

const { Text } = Typography;

export type ChatBubbleProps = {
    message: Message;
    printSenderName: boolean;
}

export const ChatBubble = ({message, printSenderName}: ChatBubbleProps) => {
    const {token} = theme.useToken();
    return (
        <>
            <Flex vertical style={{marginLeft: "10px", marginTop: "5px"}}>
                {printSenderName &&
                    <Text style={{
                            marginLeft: "5px",
                            color: token.colorPrimary,
                        }}>
                        {message.sender}
                    </Text>}
                <Text style={{
                    maxWidth: "70%",
                    width: "fit-content",
                    padding: "5px",
                    border: `1px solid ${token.colorBorder}`,
                    borderRadius: token.borderRadius,
                    wordBreak: "break-word",
                    whiteSpace: "pre-wrap"}}>
                    {message.message}
                </Text>
            </Flex>
        </>

    )
}