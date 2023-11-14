import axios from 'axios';

const REPORTE_API_URL = "http://localhost:8080/examen";

class ExamenService {

    getReportet(rut) {
        return axios.get(REPORTE_API_URL+rut); 
    }
}

export default new ExamenService()