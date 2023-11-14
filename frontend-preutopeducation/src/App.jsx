import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes} from 'react-router-dom';
import IngresoEstudiante from './components/IngresoEstudiante';

function App() {
  return (
    <div>
      <BrowserRouter>
        <div className="container">
          <Routes>
            <Route path="/ingreso-estudiante" element={<IngresoEstudiante />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;