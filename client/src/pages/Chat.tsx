import {MessageArea} from "../components/MessageArea.tsx";
import {useCallback, useEffect, useRef, useState} from "react";
import {MessageInput} from "../components/MessageInput.tsx";
import {notification, Spin } from "antd";
import {useAuth} from "../components/auth/useAuth.ts";
import {useNavigate} from "react-router";
import {type IncomingMessage, MessageType, type OutgoingMessage} from "../generated/proto-types.ts";

export const Chat = () => {
    const navigate = useNavigate();
    const authContext = useAuth();

    const socketRef = useRef<WebSocket | null>(null);
    const [isConnected, setIsConnected] = useState<boolean>(false);

    const [messages, setMessages] = useState<IncomingMessage[]>([]);

    const appendMessage = useCallback((message: IncomingMessage) => {
        setMessages(prev => [...prev, message]);
    }, []);

    const sendMessage = useCallback((message: OutgoingMessage) => {
        const asIncomingMessage: IncomingMessage = {
            type: message.type,
            senderId: authContext.userId as string,
            senderName: authContext.userName as string,
            targetId: message.targetId,
            message: message.message,
            timestamp: message.timestamp
        };
        if (message.type !== MessageType.AUTH)
            appendMessage(asIncomingMessage);
        socketRef.current?.send(JSON.stringify(message));
    }, [appendMessage, authContext.userId, authContext.userName]);

    useEffect(() => {
        const socket = new WebSocket("ws://localhost:8081/chat");
        socketRef.current = socket;

        socket.onopen = () => {
            setIsConnected(true);
            const authMessage: OutgoingMessage = {
                type: MessageType.AUTH, targetId: null,
                message: authContext.token as string, timestamp: new Date()
            };
            sendMessage(authMessage);
            console.log("Connected")
        };

        socket.onmessage = (event) => {
            console.log("Received data: " + event.data);
            const incomingMessage = JSON.parse(event.data);
            appendMessage(incomingMessage);
        };

        socket.onerror = (error) => {
            setIsConnected(false);
            console.error("An error occured in the websocket connection");
            console.error(error);
        }

        socket.onclose = () => {
            setIsConnected(false);
            console.log("Disconnected");
        };

        return () => {
            socket.close();
        }
    }, [appendMessage, authContext.token, sendMessage]);

    useEffect(() => {
        if (!authContext.isAuthenticated) {
            notification.error({
                title: "Token expired",
                placement: "topRight"
            })
            navigate("/login");
        }
    }, [authContext.isAuthenticated, authContext.token, navigate]);

    if (!authContext.isAuthenticated) {
        return;
    }

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