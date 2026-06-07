import {type ReactNode, useState} from "react";
import { AuthContext } from "./AuthContext";

export type AuthContextType = {
    token: string | null;
    isAuthenticated: boolean;
    userId: string | null;
    userName: string | null;
    setToken: (jwt: string) => void;
    setUserId: (id: string) => void;
    setUserName: (name: string) => void;
    logout: () => void;
}

export type AuthProviderProps = {
    children: ReactNode;
}

export const AuthProvider = ({children}: AuthProviderProps) =>{
    const [token, setToken] = useState<string | null>(null);
    const [userId, setUserId] = useState<string | null>(null);
    const [userName, setUserName] = useState<string | null>(null);

    const logout = () => {
        setToken(null);
    }

    return (
        <AuthContext.Provider
            value={{
                token,
                isAuthenticated: token !== null,
                userId,
                userName,
                setToken,
                setUserId,
                setUserName,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}