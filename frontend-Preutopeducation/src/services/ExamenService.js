import axios from 'axios';

const REPORTE_API_URL = "http://localhost:8080/examen";

class ReporteService {

    getReportet(rut) {
        return axios.get(REPORTE_API_URL+rut); // Usa una cadena de consulta para enviar 'rut'
    }
}

export default new ExamenService()