import type {UUID} from "uuidv7";

export type Message = {
    senderId: UUID;
    sender: string;
    message: string;
}