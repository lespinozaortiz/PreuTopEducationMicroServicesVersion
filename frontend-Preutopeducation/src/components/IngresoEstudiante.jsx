// IngresoEstudiante.jsx

import React, { useState } from 'react';
import EstudianteService from '../services/EstudianteService';

const ingresoestudiante = () => {
    const [estudiante, setEstudiante] = useState({
        nombres: '',
        apellidos: '',
        rutDigits: '',
        fecha_nacimiento: '',
        tipo_colegio: '',
        nombre_colegio: '',
        año_egreso: '',
        tipo_pago: '',
    });

    const handleChange = (e) => {
        setEstudiante({
            ...estudiante,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            await EstudianteService.registrarEstudiante(estudiante);
            alert('Estudiante registrado exitosamente');
            setEstudiante({
                nombres: '',
                apellidos: '',
                rutDigits: '',
                fecha_nacimiento: '',
                tipo_colegio: '',
                nombre_colegio: '',
                año_egreso: '',
                tipo_pago: '',
            });
        } catch (error) {
            console.error('Error al registrar estudiante:', error);
            alert('Error al registrar estudiante');
        }
    };

    return (
        <div>
            <h1>Ingreso de Estudiantes</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombres:</label>
                    <input
                        type="text"
                        name="nombres"
                        value={estudiante.nombres}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Apellidos:</label>
                    <input
                        type="text"
                        name="apellidos"
                        value={estudiante.apellidos}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>RUT (Dígitos):</label>
                    <input
                        type="text"
                        name="rutDigits"
                        value={estudiante.rutDigits}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Fecha de nacimiento:</label>
                    <input
                        type="date"
                        name="fecha_nacimiento"
                        value={estudiante.fecha_nacimiento}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Tipo Colegio Procedencia:</label>
                    <select
                        name="tipo_colegio"
                        value={estudiante.tipo_colegio}
                        onChange={handleChange}
                        required
                    >
                        <option value="Municipal">Municipal</option>
                        <option value="Subvencionado">Subvencionado</option>
                        <option value="Privado">Privado</option>
                    </select>
                </div>
                <div>
                    <label>Nombre Colegio:</label>
                    <input
                        type="text"
                        name="nombre_colegio"
                        value={estudiante.nombre_colegio}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Año Egreso:</label>
                    <input
                        type="number"
                        name="año_egreso"
                        value={estudiante.año_egreso}
                        onChange={handleChange}
                        required
                    />
                </div>
                <div>
                    <label>Tipo de Pago:</label>
                    <select
                        name="tipo_pago"
                        value={estudiante.tipo_pago}
                        onChange={handleChange}
                        required
                    >
                        <option value="Contado">Contado</option>
                        <option value="Cuotas">Cuotas</option>
                    </select>
                </div>
                <div>
                    <button type="submit">Registrar Estudiante</button>
                </div>
            </form>
        </div>
    );
};

export default ingresoestudiante;
