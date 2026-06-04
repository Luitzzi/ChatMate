export type UUID = string & { readonly __brand: "UUID" };

export type Message = {
    senderId: UUID;
    sender: string;
    message: string;
}