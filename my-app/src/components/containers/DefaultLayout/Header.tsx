import { Menu } from "antd";
import { Link } from "react-router-dom";
import { useTypedSelector } from "../../../hooks/useTypedSelector";
import LogoutModal from "../../auth/Logout";

const HeaderMenu = () => {
    const { isAuth } = useTypedSelector(state => state.auth);
    return (
        <Menu theme="dark" mode="horizontal" className="px-5" defaultSelectedKeys={['0']} selectable={false}>
            <Menu.Item className="rounded" key="homepage">
                <Link className="text-decoration-none" to="/">Home page</Link>
            </Menu.Item>
            {isAuth ?
                <Menu.Item className="rounded" style={{ marginLeft: "auto" }} key="logout">
                    <LogoutModal />
                </Menu.Item> :
                <>
                    <Menu.Item className="rounded" style={{ marginLeft: "auto" }} key="login">
                        <Link className="text-decoration-none" to="/login">Login</Link>
                    </Menu.Item>
                    <Menu.Item className="rounded" key="register">
                        <Link className="text-decoration-none" to="/register">Register</Link>
                    </Menu.Item>
                </>}
        </Menu>
    );
};

export default HeaderMenu;