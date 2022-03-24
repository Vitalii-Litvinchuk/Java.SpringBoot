import { Dispatch } from "react";
import { AuthAction } from "../types";
import { IRegisterModel, IRegisterResponse } from "./types";
import http from '../../../http_common'
import { UserFromToken } from "../actions";

export const UserRegister = (model: IRegisterModel, setError: (error: string) => void) => {
    return async (dispatch: Dispatch<AuthAction>) => {
        let token: string = "";
        const shortModel = { email: model.email, password: model.password, username: model.name, token: model.token }
        http.post<IRegisterResponse>('/auth/register', shortModel).then(x => {
            token = `${x.data}`;
            if (token) {
                localStorage.token = token;
                UserFromToken(token, dispatch);
            }
        }, error => {
            console.log(error);
            if (`${error}`.includes("400"))
                setError("Помилка перевірки на робота, спробуте ще раз");
            else if (`${error}`.includes("Error: Network Error"))
                setError("Помилка сервера, спробуйте пізніше")
            else if (`${error}`.includes("409"))
                setError("Користувач з такою поштою уже існує");
            else setError("Error");
        });
        return Promise.resolve();
    }
}