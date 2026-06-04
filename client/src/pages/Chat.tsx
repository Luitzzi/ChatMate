import {MessageArea} from "../components/MessageArea.tsx";
import {useCallback, useEffect, useRef, useState} from "react";
import {MessageInput} from "../components/MessageInput.tsx";
import {notification, theme} from "antd";
import {uuidv7} from "uuidv7";
import type {Message, UUID} from "../components/Message.ts";

export type User = {
    id: UUID;
    username: string;
}

export type ChatProps = {
    host: string;
    port: number;
}

export const Chat = ({host, port}: ChatProps) => {
    const socketRef = useRef<WebSocket | null>(null);
    const [messages, setMessages] = useState<Message[]>([]);

    const appendMessage = useCallback((message: Message) => {
        setMessages(prev => [...prev, message]);
    }, []);

    const sendMessage = useCallback((message: Message) => {
        if (socketRef.current !== null && socketRef.current.readyState == WebSocket.OPEN) {
            appendMessage(message);
            socketRef.current?.send(JSON.stringify(message));
        } else {
            notification.error({
                title: `Could not send message ${message.message}`,
                placement: "topRight"
            })
        }
    }, [appendMessage]);

    return (
        <div
            style={{
            height: "100%",
            width: "100%",
            maxWidth: "900px",
            display: "flex",
            flexDirection: "column",

            overflow: 'hidden',
        }}>
            <MessageArea messages={messages} />
            <MessageInput onSend={sendMessage} />
        </div>
    )
}