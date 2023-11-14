import axios from 'axios';

const CUOTA_API_URL = 'http://localhost:8080/cuota';

class CuotaService {
  verificarEstudiante(rut) {
    return axios.get(`${CUOTA_API_URL}/verificarEstudiante/${rut}`);
  }

  generarCuotasParaEstudiante(rut) {
    return axios.post(`${CUOTA_API_URL}/generar_cuotas?rut=${rut}`);
  }

  obtenerCuotasPorRutEstudiante(rut) {
    return axios.get(`${CUOTA_API_URL}/ver_cuotas/${rut}`);
  }

  pagarCuota(idCuota, rutEstudiante) {
    return axios.post(`${CUOTA_API_URL}/pagar_cuota/${idCuota}/${rutEstudiante}`);
  }
  

  aplicarDescuento() {
    return axios.post(`${CUOTA_API_URL}/aplicar_descuento`);
  }
}

export default new CuotaService();
