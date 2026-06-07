export interface LoginRequest {
    username: string;
    password: string;
}

export interface LoginReply {
    authToken: string;
    userId: string;
}