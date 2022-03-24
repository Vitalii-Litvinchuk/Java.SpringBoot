import { Dispatch } from "react";
import { AuthAction } from "../types";
import { ILoginModel, ILoginResponse } from "./types";
import http from '../../../http_common';
import { UserFromToken } from "../actions";

export const UserLogin = (model: ILoginModel, setError: (error: string) => void) => {
    return async (dispatch: Dispatch<AuthAction>) => {
        let token: string = "";
        http.post<ILoginResponse>('/auth/login', model).then(x => {
            token = `${x.data}`;
            if (token) {
                localStorage.token = token;
                UserFromToken(token, dispatch);
            };
        }, error => {
            if (`${error}`.includes("400"))
                setError("Помилка перевірки на робота, спробуте ще раз");
            else if (`${error}`.includes("Error: Network Error"))
                setError("Помилка сервера, спробуйте пізніше")
            else if (`${error}`.includes("401"))
                setError("Користувача не існує або неправильний пароль");
            else setError("Error");
        });
        return Promise.resolve();
    }
}
