import { Outlet } from "react-router";
import HeaderMenu from "./Header";

import { Layout, Breadcrumb } from 'antd';

const { Header, Content, Footer } = Layout;

const DefaultLayout = () => {
    return (
        <Layout className="layout">
            <Header >
                <HeaderMenu />
            </Header>
            <Content style={{ padding: '0 50px' }}>
                <Breadcrumb style={{ margin: '16px 0' }}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <div className="site-layout-content">
                    <Outlet />
                </div>
            </Content>
            <Footer style={{ textAlign: 'center' }}>Ant Design ©2018 Created by Ant UED</Footer>
        </Layout>
    );
}

export default DefaultLayout;