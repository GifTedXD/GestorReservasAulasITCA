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

public class ReservaPractica extends reserva {
    private String equipoNecesario;
    private String supervisor;

    public ReservaPractica(String id, aula aula, LocalDate fecha, LocalTime horaInicio, 
                          LocalTime horaFin, String responsable, String descripcion,
                          String equipoNecesario, String supervisor) {
        super(id, aula, fecha, horaInicio, horaFin, responsable, descripcion);
        this.equipoNecesario = equipoNecesario;
        this.supervisor = supervisor;
    }

    // Getters y Setters
    public String getEquipoNecesario() { return equipoNecesario; }
    public void setEquipoNecesario(String equipoNecesario) { this.equipoNecesario = equipoNecesario; }

    public String getSupervisor() { return supervisor; }
    public void setSupervisor(String supervisor) { this.supervisor = supervisor; }

    @Override
    public boolean validar() throws ReservaException {
        super.validar();
        
        if (aula.getTipo() != TipoAula.LABORATORIO) {
            throw new ReservaException("Las practicas solo pueden realizarse en laboratorios");
        }
        
        if (equipoNecesario != null && !aula.getEquipamiento().toLowerCase().contains(equipoNecesario.toLowerCase())) {
            throw new ReservaException("El aula no cuenta con el equipo necesario: " + equipoNecesario);
        }
        
        return true;
    }

    @Override
    public String getTipoReserva() {
        return "PRACTICA";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - Equipo: %s - Supervisor: %s", 
                equipoNecesario, supervisor);
    }
}
