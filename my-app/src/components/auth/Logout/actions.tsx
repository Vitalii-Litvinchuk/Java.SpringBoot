import { Dispatch } from 'react'
import http from '../../../http_common'
import { AuthAction, AuthActionTypes } from '../types'
import { ILogoutResponse } from './types'

export const LogoutUser = () => {
    return async (dispatch: Dispatch<AuthAction>) => {
        http.post<ILogoutResponse>('/auth/logout').then(x => {
            let token = `${x.data}`;
            if (token) {
                localStorage.token = token;
                dispatch({
                    type: AuthActionTypes.Logout
                })
            }
        });
        return Promise.resolve();
    }
} 