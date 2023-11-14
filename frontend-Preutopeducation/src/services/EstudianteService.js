// EstudianteService.js

import axios from 'axios';

const ESTUDIANTE_API_URL = "http://localhost:8080/estudiante";

class EstudianteService {
    // Obtener todos los estudiantes
    getEstudiantes() {
        return axios.get(`${ESTUDIANTE_API_URL}/estudiantes`);
    }

    // Registrar un nuevo estudiante
    registrarEstudiante(estudiante) {
        return axios.post(`${ESTUDIANTE_API_URL}/ingreso_estudiante`, estudiante);
    }

    // Obtener un estudiante por RUT
    getEstudianteByRut(rut) {
        return axios.get(`${ESTUDIANTE_API_URL}/estudiante/${rut}`);
    }

    // Editar un estudiante por ID
    editarEstudiante(id, estudiante) {
        return axios.post(`${ESTUDIANTE_API_URL}/editar_estudiante/${id}`, estudiante);
    }

    // Eliminar un estudiante por ID
    eliminarEstudiante(id) {
        return axios.get(`${ESTUDIANTE_API_URL}/eliminar_estudiante/${id}`);
    }

    // Obtener estudiantes con tipo de pago "Cuotas"
    getEstudiantesCuota() {
        return axios.get(`${ESTUDIANTE_API_URL}/generar_cuota`);
    }

    // Obtener la cantidad de cuotas de un estudiante por ID
    getCantidadCuotasEstudiante(id) {
        return axios.get(`${ESTUDIANTE_API_URL}/actualizar_cuota/${id}`);
    }

    // Ingresar cuota a un estudiante por ID
    ingresarCuotaEstudiante(id, estudiante) {
        return axios.post(`${ESTUDIANTE_API_URL}/actualizar_cuota/${id}`, estudiante);
    }

    // Obtener estudiantes con tipo de pago "Cuotas"
    getEstudiantesConCuota() {
        return axios.get(`${ESTUDIANTE_API_URL}/cuotas`);
    }
}

export default new EstudianteService();
