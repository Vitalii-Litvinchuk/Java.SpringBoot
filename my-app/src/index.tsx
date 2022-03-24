import ReactDOM from 'react-dom';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import App from './App';
import { Provider } from 'react-redux';
import { store } from './store';
import { UserFromToken } from './components/auth/actions';
import { TokenValid } from './components/auth/types';
import jwtDecode from 'jwt-decode';

const token = localStorage.token;
if (token) {
  try {
    if ((jwtDecode(token) as TokenValid).valid) {
      UserFromToken(token, store.dispatch);
    }
  } catch (ex) { localStorage.removeItem("token") }
}

ReactDOM.render(
  <Provider store={store}>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </Provider>,
  document.getElementById('root')
);
