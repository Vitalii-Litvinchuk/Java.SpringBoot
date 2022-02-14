import "cropperjs/dist/cropper.min.css";
import * as React from 'react';
import Cropper from "cropperjs";
import {
    Button, Modal,
    Row, Col
} from 'antd';
import { ApiJson } from "../../http_common";

const HomePage: React.FC = () => {
    const [isModalVisible, setIsModalVisible] = React.useState(false);
    const imgRef = React.useRef<HTMLImageElement>(null);
    const prevRef = React.useRef<HTMLImageElement>(null);
    const [cropperObj, setCropperObj] = React.useState<Cropper>();
    const [imageView, setImageView] = React.useState<string>("https://www.securityindustry.org/wp-content/uploads/sites/3/2018/05/noimage.png");


    // React.useEffect(()=>{
    // },[]);

    const handleChangeFile = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = (e.target.files as FileList)[0];
        if (file) {
            const url = URL.createObjectURL(file);

            await setIsModalVisible(true);
            console.log("Image ref ", imgRef);
            let cropper = cropperObj;
            if (!cropper) {
                cropper = new Cropper(imgRef.current as HTMLImageElement, {
                    aspectRatio: 1 / 1,
                    viewMode: 1,
                    preview: prevRef.current as HTMLImageElement,
                });
            }
            cropper.replace(url);
            setCropperObj(cropper);
            e.target.value = "";
        }
    };

    // postFile(fileToUpload: File): Observable < boolean > {
    //     const endpoint = 'your-destination-url';
    //     const formData: FormData = new FormData();
    //     formData.append('fileKey', fileToUpload, fileToUpload.name);
    //     return this.httpClient
    //         .post(endpoint, formData, { headers: yourHeadersConfig })
    //         .map(() => { return true; })
    //         .catch((e) => this.handleError(e));
    // }

    const handleOk = async () => {
        const base64 = cropperObj?.getCroppedCanvas().toDataURL() as string;
        console.log("Cropper data: ", base64);
        setImageView(base64);
        setIsModalVisible(false);

        let formData = new FormData();

        // if (e.target.files)
        //     formData.append("file", e.target.files[0]);
        if (imgRef.current)
            formData.append("base64", base64);
        await ApiJson().post("/image/add", formData);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };
    return (
        <>
            {/* <h1>Головна сторінка</h1> */}
            <label htmlFor="uploading">
                <input
                    id="uploading"
                    name="uploading"
                    className="d-none"
                    type="file"
                    onChange={handleChangeFile}
                    hidden={true}
                />
                <img src={imageView} width="250" />
            </label>

            <Modal
                title="Basic Modal"
                visible={isModalVisible}
                onOk={handleOk}
                onCancel={handleCancel}
            >
                <Row>
                    <Col span={18}>
                        <div>
                            <img
                                width="100%"
                                ref={imgRef}
                            />
                        </div>
                    </Col>
                    <Col span={6}>
                        <div
                            ref={prevRef}
                            style={{
                                height: "100px",
                                border: "1px solid silver",
                                overflow: "hidden",
                            }}
                        >
                            {" "}
                        </div>
                    </Col>
                </Row>

            </Modal>
        </>
    );
}
export default HomePage;