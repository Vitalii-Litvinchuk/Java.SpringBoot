import internal from "stream";

export enum AuthActionTypes {
    Token = "Token"
}

//////////////

export interface IUser {
    id: internal,
    username: string,
}

export interface AuthState {
    user: IUser | null,
    isAuth: boolean;
}

//////////////

export interface ITokenAction {
    type: AuthActionTypes.Token,
    user: IUser,
}

export type AuthAction = ITokenAction;