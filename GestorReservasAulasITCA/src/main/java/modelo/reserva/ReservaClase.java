/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.reserva;

/**
 *
 * @author camix
 */
import modelo.aula.aula;
import modelo.enumeraciones.TipoAula;
import excepciones.ReservaException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaClase extends reserva {
    private String materia;
    private String grupo;
    private int cantidadEstudiantes;

    public ReservaClase(String id, aula aula, LocalDate fecha, LocalTime horaInicio, 
                       LocalTime horaFin, String responsable, String descripcion,
                       String materia, String grupo, int cantidadEstudiantes) {
        super(id, aula, fecha, horaInicio, horaFin, responsable, descripcion);
        this.materia = materia;
        this.grupo = grupo;
        this.cantidadEstudiantes = cantidadEstudiantes;
    }

    // Getters y Setters
    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public int getCantidadEstudiantes() { return cantidadEstudiantes; }
    public void setCantidadEstudiantes(int cantidadEstudiantes) { 
        this.cantidadEstudiantes = cantidadEstudiantes; 
    }

    @Override
    public boolean validar() throws ReservaException {
        super.validar();
        
        if (cantidadEstudiantes > aula.getCapacidad()) {
            throw new ReservaException("La cantidad de estudiantes excede la capacidad del aula");
        }
        
        if (aula.getTipo() != TipoAula.TEORICA && aula.getTipo() != TipoAula.LABORATORIO) {
            throw new ReservaException("Las clases solo pueden realizarse en aulas teoricas o laboratorios");
        }
        
        return true;
    }

    @Override
    public String getTipoReserva() {
        return "CLASE";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - Materia: %s - Grupo: %s - Estudiantes: %d", 
                materia, grupo, cantidadEstudiantes);
    }
}
