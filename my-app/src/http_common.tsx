import axios from "axios";

export const urlBackend = "http://localhost:8082/";

export default axios.create({
    baseURL: urlBackend,
    headers: {
        "Content-type": "application/json"
    }
});

export const ApiMultipart = () =>
    axios.create({
        baseURL: urlBackend,
        headers: {
            "Content-type": "multipart/form-data",
        },
    });
