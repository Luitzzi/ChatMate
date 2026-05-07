import {BrowserRouter, Route, Routes} from "react-router";
import {RootLayout} from "./layouts/RootLayout.tsx";
import {RouteNotFound} from "./pages/RouteNotFound.tsx";
import {Chat} from "./pages/Chat.tsx";

export const AllRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<RootLayout />}>
                    <Route path={"/"} element={<Chat />} />
                    <Route path={"/*"} element={<RouteNotFound />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
};