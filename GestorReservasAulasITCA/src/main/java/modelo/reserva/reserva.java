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
import modelo.enumeraciones.EstadoReserva;
import interfaz.Validable;
import excepciones.ReservaException;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class reserva implements Validable {
    protected String id;
    protected aula aula;
    protected LocalDate fecha;
    protected LocalTime horaInicio;
    protected LocalTime horaFin;
    protected String responsable;
    protected String descripcion;
    protected EstadoReserva estado;

    public reserva(String id, aula aula, LocalDate fecha, LocalTime horaInicio, 
                  LocalTime horaFin, String responsable, String descripcion) {
        this.id = id;
        this.aula = aula;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.responsable = responsable;
        this.descripcion = descripcion;
        this.estado = EstadoReserva.ACTIVA;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public aula getAula() { return aula; }
    public void setAula(aula aula) { this.aula = aula; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public EstadoReserva getEstado() { return estado; }
    public void setEstado(EstadoReserva estado) { this.estado = estado; }

    @Override
    public boolean validar() throws ReservaException {
        if (horaInicio.isAfter(horaFin)) {
            throw new ReservaException("La hora de inicio no puede ser despues de la hora de fin");
        }
        if (fecha.isBefore(LocalDate.now())) {
            throw new ReservaException("No se pueden hacer reservas en fechas pasadas");
        }
        return true;
    }

    public abstract String getTipoReserva();

    @Override
    public String toString() {
        return String.format("Reserva %s [%s] - Aula: %s - Fecha: %s %s-%s - Responsable: %s", 
                id, getTipoReserva(), aula.getCodigo(), fecha, horaInicio, horaFin, responsable);
    }
}
