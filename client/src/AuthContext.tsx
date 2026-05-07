// auth/AuthContext.tsx
import { createContext, useContext, useState } from 'react';
import type {User} from "./pages/Chat.tsx";

type AuthContextType = {
    user: User | null;
    login: (username: string) => void;
    logout: () => void;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }) => {
    const [username, setUsername] = useState<string | null>(null);

    const login = (name: string) => setUsername(name);
    const logout = () => setUsername(null);

    return (
        <AuthContext.Provider value={{ username, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error('useAuth must be used inside AuthProvider');
    return ctx;
};