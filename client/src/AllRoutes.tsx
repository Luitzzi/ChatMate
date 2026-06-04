import {BrowserRouter, Route, Routes} from "react-router";
import {RootLayout} from "./layouts/RootLayout.tsx";
import {RouteNotFound} from "./pages/RouteNotFound.tsx";
import {Chat} from "./pages/Chat.tsx";
import {Login} from "./pages/Login.tsx";

export const AllRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<RootLayout />}>
                    <Route path={"/login"} element={<Login />} />
                    <Route path={"/chat"} element={<Chat host={"localhost"} port={8080}/>} />
                    <Route path={"/*"} element={<RouteNotFound />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
};