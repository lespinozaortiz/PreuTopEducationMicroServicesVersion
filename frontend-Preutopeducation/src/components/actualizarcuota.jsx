// ActualizarCuotaForm.jsx

import React, { useState } from 'react';
import EstudianteService from './EstudianteService'; // Ajusta la ruta según tu estructura de carpetas

const actualizarcuota = () => {
  const [estudiante, setEstudiante] = useState({
    cantidad_cuotas: 0, // Ajusta según tu modelo de datos
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEstudiante({
      ...estudiante,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Lógica para enviar la cantidad de cuotas al servidor
   
    EstudianteService.ingresarCuotaEstudiante(id, estudiante)
      .then((response) => {
        // Maneja la respuesta del servidor según tus necesidades
        console.log(response);
      })
      .catch((error) => {
        // Maneja el error según tus necesidades
        console.error(error);
      });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <h1 className="text-center">Insertar cuotas</h1>
        <label>Inserte cantidad de cuotas deseada :</label>
        <input
          type="number"
          name="cantidad_cuotas"
          value={estudiante.cantidad_cuotas}
          onChange={handleInputChange}
          className="form-control"
          placeholder="Escriba la cantidad de cuotas deseada"
          required
          // Aquí puedes ajustar según tus necesidades
          max={estudiante.tipo_colegio_proc === 'Municipal' ? 10 : (estudiante.tipo_colegio_proc === 'Subvencionado' ? 7 : 4)}
        />
      </div>
      <br />
      <br />
      <div className="box-footer">
        <button type="submit" className="btn btn-success">
          Actualizar estudiante
        </button>
      </div>
    </form>
  );
};

export default actualizarcuota;
