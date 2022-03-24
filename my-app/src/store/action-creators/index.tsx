import * as LoginActionCreators from '../../components/auth/Login/actions';
import * as RegisterhActionCreators from '../../components/auth/Register/actions';
import * as AuthActionCreators from '../../components/auth/actions';

export default {
    ...LoginActionCreators,
    ...AuthActionCreators,
    ...RegisterhActionCreators,
}