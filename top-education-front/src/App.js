import React from 'react';
import './App.css';
import { BrowserRouter, Route, Routes} from 'react-router-dom';
import IngresoComponent from './components/IngresoComponent';
import EstudiantesComponent from './components/EstudiantesComponent';
import GenerarCuotasComponent from './components/GenerarCuotasComponent';
import VerCuotaComponent from './components/VerCuotaComponent';
import SubirNotasComponent from './components/SubirNotasComponent';
function App() {
  return (
    <div>
      <BrowserRouter>
        <div className="container">
          <Routes>
            <Route path="/ingreso-estudiante" element={<IngresoComponent />} />
            <Route path="/estudiantes" element={<EstudiantesComponent />} />
            <Route path="/generar-cuota" element={<GenerarCuotasComponent />} />
            <Route path="/vercuota/:rut" element={<VerCuotaComponent />} />
            <Route path="/subirnotas" element={<SubirNotasComponent />} />
            
          </Routes>
        </div>
      </BrowserRouter>
    </div>
  );
}
export default App;
