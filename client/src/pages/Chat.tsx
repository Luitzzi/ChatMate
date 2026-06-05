import {MessageArea} from "../components/MessageArea.tsx";
import {useCallback, useEffect, useRef, useState} from "react";
import {MessageInput} from "../components/MessageInput.tsx";
import {notification, Spin } from "antd";
import {useAuth} from "../components/auth/useAuth.ts";
import {useNavigate} from "react-router";
import {type Message, MessageType} from "../generated/proto-types.ts";

export const Chat = () => {
    const navigate = useNavigate();
    const authContext = useAuth();
    if (authContext.token === null) {
        notification.error({
            title: "Token expired",
            placement: "topRight"
        })
        navigate("/login");
    }

    const socketRef = useRef<WebSocket | null>(null);
    const [isConnected, setIsConnected] = useState<boolean>(false);

    const [messages, setMessages] = useState<Message[]>([]);

    const appendMessage = useCallback((message: Message) => {
        setMessages(prev => [...prev, message]);
    }, []);

    const sendMessage = (message: Message) => {
        socketRef.current?.send(JSON.stringify(message));
    }

    useEffect(() => {
        const socket = new WebSocket("ws://localhost:8081/chat");
        socketRef.current = socket;

        socket.onopen = () => {
            setIsConnected(true);
            const authMessage: Message = {
                type: MessageType.AUTH, receiverId: null,
                message: authContext.token as string, timestamp: new Date()
            };
            sendMessage(authMessage);
            console.log("Connected")
        };

        socket.onmessage = (event) => {
            console.log("Received data: " + event.data)
        };

        socket.onerror = (error) => {
            setIsConnected(false);
            console.error(error);
        }

        socket.onclose = () => {
            setIsConnected(false);
            console.log("Disconnected");
        };

        return () => {
            socket.close();
        }
    }, [authContext.token]);

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
            {
                isConnected ?
                <MessageArea messages={messages} /> :
                <Spin />
            }
            <MessageInput onSend={sendMessage} />
        </div>
    )
}