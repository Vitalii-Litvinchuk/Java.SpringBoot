import './App.css';
import HomePage from './components/Home';
import { Routes, Route } from 'react-router-dom';
import DefaultLayout from './components/containers/DefaultLayout';
import UploadingPage from './components/Uploading';

function App() {
  return (
    <Routes>
      <Route path='/' element={<DefaultLayout />}>
        <Route index element={<HomePage />}></Route>
        <Route path='/uploading' element={<UploadingPage />}></Route>
      </Route>
    </Routes>
  );
}

export default App;
