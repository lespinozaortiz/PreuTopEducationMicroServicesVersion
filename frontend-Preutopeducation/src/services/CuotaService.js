import axios from 'axios';

const CUOTAS_API_URL = "http://localhost:8080/cuota/";

class CuotaService {

    getCuotasByRut(rut) {
        return axios.get(CUOTAS_API_URL+rut); // Usa una cadena de consulta para enviar 'rut'
    }

    createCuotas(cuota){
        return axios.post(CUOTAS_API_URL+"crear", cuota);
    }
    pagarCuota(idCuota){
        return axios.post(CUOTAS_API_URL+"pagar/"+idCuota, idCuota);
    }
    cobrarCuotas(){
        return axios.post(CUOTAS_API_URL+"cobrar");
    }
}

export default new CuotaService()