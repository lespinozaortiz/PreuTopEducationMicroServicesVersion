import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import CuotaService from '../services/CuotaService';

const VerCuotaComponent = () => {
  const { rut } = useParams();
  const [cuotas, setCuotas] = useState([]);
  const [cuotaSeleccionada, setCuotaSeleccionada] = useState(null);

  useEffect(() => {
    // Obtener la lista de cuotas al cargar el componente
    if (rut !== undefined) {
      fetchCuotas();
    } else {
      console.error('rut es undefined');
    }
  }, [rut]);

  const fetchCuotas = async () => {
    try {
      const response = await CuotaService.obtenerCuotasPorRutEstudiante(rut);
      setCuotas(response.data);
    } catch (error) {
      console.error('Error al obtener la lista de cuotas:', error);
    }
  };

  const handlePagarCuota = async () => {
    if (cuotaSeleccionada) {
      try {
        const idCuota = cuotaSeleccionada.idcuota;
        await CuotaService.pagarCuota(idCuota, rut);
        // Actualizar el estado de la cuota después de pagar
        fetchCuotas();
        setCuotaSeleccionada(null);
        alert('Cuota pagada con éxito.');
      } catch (error) {
        console.error('Error al pagar la cuota:', error);
        alert('Error al pagar la cuota.');
      }
    } else {
      alert('Selecciona una cuota para pagar.');
    }
  };

  return (
    <div>
      <h1>Listar Cuotas y Registrar Pago</h1>
      {cuotas.length > 0 ? (
        <table>
          <thead>
            <tr>
              <th>ID de Cuota</th>
              <th>Fecha de Pago</th>
              <th>Monto</th>
              <th>Estado</th>
              <th>Plazo Inicio</th>
              <th>Plazo Final</th>
              <th>Interés</th>
              <th>Descuento</th>
              <th>Seleccionar</th>
            </tr>
          </thead>
          <tbody>
            {cuotas.map((cuota) => (
              <tr key={cuota.idcuota}>
                <td>{cuota.idcuota}</td>
                <td>{cuota.fecha_pago}</td>
                <td>{cuota.monto}</td>
                <td>{cuota.estado}</td>
                <td>{cuota.plazo_inicio}</td>
                <td>{cuota.plazo_final}</td>
                <td>{cuota.interes}</td>
                <td>{cuota.descuento}</td>
                <td>
                  <input
                    type="radio"
                    name="cuotaSeleccionada"
                    onChange={() => setCuotaSeleccionada(cuota)}
                  />
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      ) : (
        <p>No hay cuotas registradas para este estudiante.</p>
      )}

      <button onClick={handlePagarCuota}>Pagar Cuota Seleccionada</button>
    </div>
  );
};

export default VerCuotaComponent;


