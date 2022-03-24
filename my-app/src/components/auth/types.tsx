import internal from "stream";

export enum AuthActionTypes {
    Token = "Token",
    Logout = "Logout",
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

export interface TokenValid {
    valid: boolean | null
}

//////////////

export interface ITokenAction {
    type: AuthActionTypes.Token,
    user: IUser,
}

export interface ILogoutAction {
    type: AuthActionTypes.Logout
}

export type AuthAction = ITokenAction | ILogoutAction;