import {Button, Result} from "antd";
import {useNavigate} from "react-router";

export const RouteNotFound = () => {
    const navigate = useNavigate();

    return (
        <Result
            status="404"
            title="404 Not found"
            subTitle="Sorry, the page you visited does not exist."
            extra={
                <Button type="primary" onClick={() => navigate("/")}>
                    Back Home
                </Button>
            }
        />
    );
}