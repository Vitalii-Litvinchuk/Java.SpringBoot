import './App.css';
import HomePage from './components/Home';
import { Routes, Route } from 'react-router-dom';
import DefaultLayout from './components/containers/DefaultLayout';
import UploadingPage from './components/Uploading';
import LoginPage from './components/auth/Login';
import RegisterPage from './components/auth/Register';
import { useTypedSelector } from './hooks/useTypedSelector';
import { Navigate } from "react-router-dom";

function App() {
  const { isAuth } = useTypedSelector(state => state.auth);
  return (
    <Routes>
      <Route path='/' element={<DefaultLayout />}>
        <Route index element={<HomePage />} />
        {!isAuth ?
          (<>
            <Route path='/login' element={<LoginPage />} />
            <Route path='/register' element={<RegisterPage />} />
          </>
          ) : <></>}
        <Route path='/uploading' element={<UploadingPage />} />
      </Route>
      <Route path='*' element={<Navigate to="/" />} />
    </Routes>
  );
}

export default App;
