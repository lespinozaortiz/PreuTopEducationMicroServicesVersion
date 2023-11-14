import React, { useState, useEffect } from 'react';
import EstudianteService from '../services/EstudianteService';
import CuotaService from '../services/CuotaService';

const GenerarCuotas = () => {
  const [estudiantes, setEstudiantes] = useState([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState('');
  const [generating, setGenerating] = useState(false);

  useEffect(() => {
    // Obtener la lista de estudiantes al cargar el componente
    fetchEstudiantes();
  }, []);

  const fetchEstudiantes = async () => {
    try {
      const response = await EstudianteService.getEstudiantesCuota();
      setEstudiantes(response.data);
    } catch (error) {
      console.error('Error al obtener la lista de estudiantes:', error);
    }
  };

  const handleEstudianteChange = (e) => {
    setSelectedEstudiante(e.target.value);
  };

  const handleGenerarCuotas = async () => {
    try {
      setGenerating(true);
      await CuotaService.generarCuotasParaEstudiante(selectedEstudiante);
      // Después de generar cuotas, volver a cargar la lista de estudiantes
      fetchEstudiantes();
    } catch (error) {
      console.error('Error al generar cuotas:', error);
    } finally {
      setGenerating(false);
    }
  };

  return (
    <div>
      <h1>Generar Cuotas</h1>
      <div>
        <label>Seleccionar Estudiante:</label>
        <select onChange={handleEstudianteChange} value={selectedEstudiante}>
          <option value="">Seleccionar...</option>
          {estudiantes.map((estudiante) => (
            <option key={estudiante.rut} value={estudiante.rut}>
              {estudiante.nombres} {estudiante.apellidos}
            </option>
          ))}
        </select>
      </div>
      <div>
        <button onClick={handleGenerarCuotas} disabled={!selectedEstudiante || generating}>
          {generating ? 'Generando Cuotas...' : 'Generar Cuotas'}
        </button>
      </div>
    </div>
  );
};

export default GenerarCuotas;
