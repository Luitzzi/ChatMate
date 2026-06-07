export const MessageType = {
    GLOBAL: "GLOBAL",
    GROUP: "GROUP",
    PRIVATE: "PRIVATE",
    AUTH: "AUTH"
} as const;

export type MessageType = typeof MessageType[keyof typeof MessageType];

export type IncomingMessage = {
    type: MessageType;
    senderId: string;
    senderName: string;
    receiverId: string | null;
    message: string;
    timestamp: Date
}

export type OutgoingMessage = {
    type: MessageType;
    receiverId: string | null;
    message: string;
    timestamp: Date;
}