import {MessageArea} from "../components/MessageArea.tsx";
import {useState} from "react";
import {MessageInput} from "../components/MessageInput.tsx";
import {theme} from "antd";
import type {UUID} from "uuidv7";
import type {Message} from "../components/Message.ts";

export type User = {
    id: UUID;
    username: string;
}

export type ChatProps = {
    user: User;
}

export const Chat = () => {
    const {token} = theme.useToken();
    const [messages, setMessages] = useState<Message[]>([]);
    const appendMessage = (message: Message) => {
        setMessages(prev => [...prev, message]);
    }

    return (
        <div
            style={{
            height: "90vh",
            width: "100%",
            maxWidth: "900px",
            display: "flex",
            flexDirection: "column",

            background: token.colorBgContainer,
            border: `1px solid ${token.colorBorder}`,
            overflow: 'hidden',
        }}>
            <MessageArea messages={messages} />
            <MessageInput onSend={appendMessage} />
        </div>
    )
}