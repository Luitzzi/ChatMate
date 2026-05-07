import {AllRoutes} from "./AllRoutes.tsx";

import {ConfigProvider} from "antd";
import {theme} from "./styles/theme.ts";

function App() {
  return (
      <ConfigProvider theme={theme}>
          <AllRoutes />
      </ ConfigProvider>
  )
}

export default App