import {Button, Flex, Input, theme} from "antd";
import {useState} from "react";
import {MessageType, type OutgoingMessage} from "../generated/proto-types.ts";

export type MessageInputProps = {
    onSend: (message: OutgoingMessage) => void;
}

export const MessageInput = ({onSend}: MessageInputProps) => {
    const {token} = theme.useToken();
    const [input, setInput] = useState<string>();
    const handleSend = () => {
        if (input !== undefined)
            onSend({
                type: MessageType.GLOBAL,
                receiverId: null,
                message: input,
                timestamp: new Date()
            } satisfies OutgoingMessage);
        setInput("");
    }

    return (
        <Flex gap={"medium"} align={"center"} style={{
            borderTop: `1px solid ${token.colorBorder}`,
            padding: 12,
            background: token.colorBgContainer,
        }}>
            <Input.TextArea
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onPressEnter={(e) => {
                    e.preventDefault();
                    handleSend();
                }}
                placeholder={"Type a message..."}
            />
            <Button type={"primary"} onClick={() => handleSend()}>
                Send
            </Button>
        </Flex>
    )
}