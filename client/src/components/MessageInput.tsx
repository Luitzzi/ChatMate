import {Button, Flex, Input, theme} from "antd";
import {useState} from "react";

export type MessageInputProps = {
    onSend: (message: string) => void;
}

export const MessageInput = ({onSend}: MessageInputProps) => {
    const {token} = theme.useToken();
    const [input, setInput] = useState<string>();
    const handleSend = () => {
        if (input !== undefined)
            onSend(input);
    }

    return (
        <Flex gap={"medium"} style={{
            borderTop: `1px solid ${token.colorBorder}`,
            padding: 12,
            background: token.colorBgContainer,
        }}>
            <Input.TextArea
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onPressEnter={() => handleSend()}
                placeholder={"Type a message..."}
            />
            <Button type={"primary"} onClick={() => handleSend()}>
                Send
            </Button>
        </Flex>
    )
}