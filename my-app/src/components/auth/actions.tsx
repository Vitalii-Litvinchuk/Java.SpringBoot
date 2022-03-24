import { Dispatch } from "react";
import { AuthAction, AuthActionTypes, IUser } from "./types";
import jwt from "jwt-decode";

export const UserFromToken = (token: string, dispatch: Dispatch<AuthAction>) => {
    const user = jwt(token) as IUser;
    dispatch({
        type: AuthActionTypes.Token,
        user: { id: user.id, username: user.username }
    });
}