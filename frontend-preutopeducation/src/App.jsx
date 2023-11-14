import React from 'react';
import './App.css';
import actualizarcuota from './components/actualizarcuota';

import { BrowserRouter, Route, Routes} from 'react-router-dom';

function App() {
  return (
    <div>
      <BrowserRouter>
        <div className="container">
          <Routes>
            <Route path="/actualizarcuota" element={<actualizarcuota/>} />
            <Route path="/cuotas" element={<cuotas />} />
            <Route path="/editarestudiante" element={<editarestudiante />} />
            <Route path="/estudiantes" element={<estudiantes />} />
            <Route path="/generarcuota" element={<generarcuota />} />
            <Route path="/ingresoestudiante" element={<ingresoestudiante />} />
            <Route path="/inicio" element={<inicio />} />
            <Route path="/reportes" element={<reportes />} />
            <Route path="/subirnotas" element={<subirnotas />} />
            <Route path="/vercuotas" element={<vercuotas />} />
            <Route path="/verreporte" element={<verreporte />} />
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;