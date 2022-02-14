import axios from "axios";

export const ApiJson = () => {
    let api = axios.create({
        baseURL: "http://localhost:8082/",
        headers: {
            "Content-type": "application/json"
        }
    });
    return api;
}

export const ApiMultipart = () => {
    let api = axios.create({
        baseURL: "http://localhost:8082/",
        headers: {
            "Content-type": "multipart/form-data",
        },
    });
    return api;
}
