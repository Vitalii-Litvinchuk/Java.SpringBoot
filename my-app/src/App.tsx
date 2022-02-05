import './App.css';
import HomePage from './components/Home';
import { Routes, Route } from 'react-router-dom';
import DefaultLayout from './components/containers/DefaultLayout';

function App() {
  return (
    <Routes>
      <Route path='/' element={<DefaultLayout />}>
        <Route index element={<HomePage />}></Route>
      </Route>
    </Routes>
  );
}

export default App;
