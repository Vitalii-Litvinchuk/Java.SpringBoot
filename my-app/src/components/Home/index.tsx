import { Button, Col, Modal, Row } from "antd";
import Cropper from "cropperjs";
import * as React from "react";

const HomePage: React.FC = () => {

    const [isModalVisible, setIsModalVisible] = React.useState(false);
    const imgRef = React.useRef<HTMLImageElement>(null);
    const prevRef = React.useRef<HTMLImageElement>(null);

    React.useEffect(() => {
    }, []);

    const showModal = async () => {
        await setIsModalVisible(true);
        console.log("Image ref  ", imgRef);
        const cropper = new Cropper(imgRef.current as HTMLImageElement, {
            viewMode: 1,
            aspectRatio: 16 / 9,
            preview: prevRef.current as HTMLImageElement
        });
    };

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <>
            <h1>Головна сторінка</h1>
            <Button type="dashed" className="ant-btn-dangerous"
                onClick={showModal}>
                Open modal</Button>

            <Modal title="Basic Modal"
                visible={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}>
                <p>Some contents...</p>
                <Row>
                    <Col span={18}>
                        <div>
                            <img src="https://static.remove.bg/remove-bg-web/1edb6b547ebc0098fab06852edf91aa18cfebce0/assets/start_remove-c851bdf8d3127a24e2d137a55b1b427378cd17385b01aec6e59d5d4b5f39d2ec.png"
                                ref={imgRef} />
                        </div>
                    </Col>
                    <Col span={6}
                        style={{
                            height: "125px",
                            width: "100%",
                            border: "1px solid silver",
                            overflow: "hidden"
                        }}
                    >
                        <div ref={prevRef}>
                        </div>
                    </Col>
                </Row>
                <p>Some contents...</p>
                <p>Some contents...</p>
            </Modal>
        </>
    );
}

export default HomePage;