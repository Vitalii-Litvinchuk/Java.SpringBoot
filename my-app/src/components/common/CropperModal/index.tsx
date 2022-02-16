import * as React from "react";
import { Modal, Col, Row } from "antd";
import Cropper from "cropperjs";
import { ICropperProps } from "./types";
import { urlBackend } from '../../../http_common';

const CropperModal: React.FC<ICropperProps> = ({
    onSelected,
    aspectRation = 1 / 1
}) => {
    const [visible, setVisible] = React.useState(false);

    const imgRef = React.useRef<HTMLImageElement>(null);
    const prevRef = React.useRef<HTMLImageElement>(null);
    const [cropperObj, setCropperObj] = React.useState<Cropper>();

    //вибір файла
    const handleChangeFile = async (e: any) => {
        const file = (e.target.files as FileList)[0];
        if (file) {
            const url = URL.createObjectURL(file);

            await setVisible(true);
            console.log("Image ref ", imgRef);
            let cropper = cropperObj;
            if (!cropper) {
                cropper = new Cropper(imgRef.current as HTMLImageElement, {
                    aspectRatio: aspectRation,
                    viewMode: 1,
                    preview: prevRef.current as HTMLImageElement,
                });
            }
            cropper.replace(url);
            e.target.value = "";
            setCropperObj(cropper);
        }
    };

    const handleOk = async () => {
        const base64 = cropperObj?.getCroppedCanvas().toDataURL() as string;
        await setVisible(false);
        onSelected(base64);
    };
    return (
        <>
            <label htmlFor="uploading">
                <img src={`${urlBackend}image/get/noimage.jpg`} alt="" width="100%" style={{ cursor: "pointer" }} />
            </label>

            <input
                id="uploading"
                style={{ display: "none" }}
                type="file"
                onChange={handleChangeFile}
            />

            <Modal
                title="Modal 1000px width"
                centered
                visible={visible}
                onOk={handleOk}
                onCancel={() => setVisible(false)}
                width={1000}
            >
                <Row gutter={[8, 8]}>
                    <Col md={18} xs={24}>
                        <div style={{ maxHeight: "600px" }}>
                            <img
                                src="https://vovalohika.tk/images/1200_gntox1zw.ipw.jpeg"
                                width="100%"
                                ref={imgRef}
                            />
                        </div>
                    </Col>
                    <Col md={6} xs={24}>
                        <div
                            ref={prevRef}
                            style={{
                                height: "150px",
                                border: "1px solid silver",
                                overflow: "hidden",
                            }}
                        ></div>
                    </Col>
                </Row>
            </Modal>
        </>
    );
};

export default CropperModal;