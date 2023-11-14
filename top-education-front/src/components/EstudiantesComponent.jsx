import React, { useEffect, useState } from 'react';
import EstudianteService from '../services/EstudianteService';

const EstudiantesComponent = () => {
  const [estudiantes, setEstudiantes] = useState([]);

  useEffect(() => {
    const fetchEstudiantes = async () => {
      try {
        const response = await EstudianteService.getEstudiantes();
        setEstudiantes(response.data);
      } catch (error) {
        console.error('Error al obtener estudiantes:', error);
      }
    };

    fetchEstudiantes();
  }, []);

  return (
    <div>
      <h1>Lista de Estudiantes</h1>
      <table className="table table-striped table-bordered">
        <thead className="table-light">
          <tr>
            <th>Nombres</th>
            <th>RUT</th>
            <th>Apellidos</th>
            <th>Fecha de nacimiento</th>
            <th>Tipo colegio procedencia</th>
            <th>Nombre colegio</th>
            <th>Año egreso del colegio</th>
          </tr>
        </thead>
        <tbody>
          {estudiantes.map(estudiante => (
            <tr key={estudiante.rut}>
              <td>{estudiante.nombres}</td>
              <td>{estudiante.rut}</td>
              <td>{estudiante.apellidos}</td>
              <td>{estudiante.fecha_nacimiento}</td>
              <td>{estudiante.tipo_colegio_proc}</td>
              <td>{estudiante.nombre_colegio}</td>
              <td>{estudiante.egreso}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default EstudiantesComponent;

