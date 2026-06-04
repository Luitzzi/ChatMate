import {type ReactNode, useState} from "react";
import { AuthContext } from "./AuthContext";

export type AuthContextType = {
    token: string | null;
    isAuthenticated: boolean;
    setToken: (jwt: string) => void;
    logout: () => void;
}

export type AuthProviderProps = {
    children: ReactNode;
}

export const AuthProvider = ({children}: AuthProviderProps) =>{
    const [token, setToken] = useState<string | null>(null);

    const logout = () => {
        setToken(null);
    }

    return (
        <AuthContext.Provider
            value={{
                token,
                isAuthenticated: token !== null,
                setToken,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    )
}