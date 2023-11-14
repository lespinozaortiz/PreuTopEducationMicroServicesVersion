import axios from 'axios';

const EXAMEN_API_URL = 'http://localhost:8080/examen';

class ExamenService {
  cargarNotasDesdeCSV(archivo) {
    const formData = new FormData();
    formData.append('archivo', archivo);

    return axios.post(`${EXAMEN_API_URL}/cargar_notas`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  }

  generarReporteEstudiante(rutEstudiante) {
    return axios.get(`${EXAMEN_API_URL}/generar_reporte/${rutEstudiante}`);
  }
}

export default new ExamenService();
