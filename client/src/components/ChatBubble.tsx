import {Flex, theme, Typography} from "antd";
import type {IncomingMessage} from "../generated/proto-types.ts";
import {useAuth} from "./auth/useAuth.ts";

const { Text } = Typography;

export type ChatBubbleProps = {
    message: IncomingMessage;
    printSenderName: boolean;
}

export const ChatBubble = ({message, printSenderName}: ChatBubbleProps) => {
    const {token} = theme.useToken();
    const {userId} = useAuth();

    const getMessageContent = () => {
        return (
            <>
            {
                printSenderName &&
                <Text
                    style={{color: token.colorPrimary, marginLeft: "5px", marginRight: "5px"}}>
                    {message.senderName}
                </Text>
            }
            <Text
                style={{
                    maxWidth: "70%",
                    width: "fit-content",
                    padding: "5px",
                    border: `1px solid ${token.colorBorder}`,
                    borderRadius: token.borderRadius,
                    wordBreak: "break-word",
                    whiteSpace: "pre-wrap"
                }}>
                {message.message}
            </Text>
            </>
        );
    }

    if (message.senderId === userId) {
        return (
            <Flex vertical align={"flex-end"} style={{marginRight: "5px", marginTop: "5px"}}>
                {getMessageContent()}
            </Flex>
        );
    }
    return (
        <Flex vertical align={"flex-start"} style={{marginLeft: "5px", marginTop: "5px"}}>
            {getMessageContent()}
        </Flex>
    )
}