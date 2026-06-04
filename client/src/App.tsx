import {AllRoutes} from "./AllRoutes.tsx";

import {ConfigProvider} from "antd";
import {theme} from "./styles/theme.ts";
import {AuthProvider} from "./components/auth/AuthProvider.tsx";

function App() {
  return (
      <ConfigProvider theme={theme}>
          <AuthProvider >
             <AllRoutes />
          </AuthProvider>
      </ ConfigProvider>
  )
}

export default App