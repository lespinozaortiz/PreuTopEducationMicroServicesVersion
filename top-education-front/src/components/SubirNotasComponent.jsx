import React, { useState } from 'react';
import ExamenService from '../services/ExamenService';

const SubirNotasComponent = () => {
  const [archivoCSV, setArchivoCSV] = useState(null);

  const handleFileChange = (event) => {
    setArchivoCSV(event.target.files[0]);
  };

  const handleUpload = async () => {
    if (archivoCSV) {
      try {
        await ExamenService.cargarNotasDesdeCSV(archivoCSV);
        alert('Notas cargadas exitosamente');
      } catch (error) {
        console.error('Error al cargar notas:', error);
        alert('Error al cargar notas.');
      }
    } else {
      alert('Por favor, selecciona un archivo CSV.');
    }
  };

  return (
    <div>
      <h1>Subir Notas desde CSV</h1>
      <input type="file" accept=".csv" onChange={handleFileChange} />
      <button onClick={handleUpload}>Subir Notas</button>
    </div>
  );
};

export default SubirNotasComponent;
