import * as React from "react";
import { Carousel, Col, Row } from "antd";
import CropperModal from '../common/CropperModal';
import http, { urlBackend } from '../../http_common';

const UploadingPage: React.FC = () => {
    const [images, setImages] = React.useState<Array<string>>([]);

    const handleSelected = async (base64: string) => {
        const imgName = await http.post<string>("image/add", { base64 });
        setImages([...images, `${urlBackend}image/get/${imgName.data}`]);
    };

    const dataImages = images.map((item, key) => {
        return (
            <div key={key} >
                <img src={item} alt="images" width="100%" />
            </div>
        );
    });
    return (
        <>
            <h1>Загрузка фоток</h1>
            <Row gutter={[8, 8]}>
                <Col md={4}>
                    <div>
                        <CropperModal onSelected={handleSelected}
                            aspectRation={16 / 9} />
                    </div>
                </Col>
                <Col md={7}>
                    <Carousel autoplay dotPosition="top" arrows >
                        {dataImages}
                    </Carousel>
                </Col>
            </Row >
        </>
    );
};

export default UploadingPage;