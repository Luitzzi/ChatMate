import {BrowserRouter, Navigate, Route, Routes} from "react-router";
import {RootLayout} from "./layouts/RootLayout.tsx";
import {RouteNotFound} from "./pages/RouteNotFound.tsx";
import {Chat} from "./pages/Chat.tsx";
import {Login} from "./pages/Login.tsx";
import {useAuth} from "./components/auth/useAuth.ts";
import {Register} from "./pages/Register.tsx";

export const AllRoutes = () => {
    const authContext = useAuth();

    return (
        <BrowserRouter>
            <Routes>
                <Route element={<RootLayout />}>
                    <Route path={"/"} element={
                        authContext.token == null ? <Navigate to={"/login"} /> : <Chat />} />
                    <Route path={"/login"} element={<Login />} />
                    <Route path={"/register"} element={<Register />} />
                    <Route path={"/chat"} element={<Chat />} />
                    <Route path={"/*"} element={<RouteNotFound />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
};