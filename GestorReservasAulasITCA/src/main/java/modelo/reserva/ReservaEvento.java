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
import modelo.enumeraciones.TipoEvento;
import excepciones.ReservaException;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaEvento extends reserva {
    private TipoEvento tipoEvento;
    private int asistentesEsperados;
    private boolean requiereCatering;

    public ReservaEvento(String id, aula aula, LocalDate fecha, LocalTime horaInicio, 
                        LocalTime horaFin, String responsable, String descripcion,
                        TipoEvento tipoEvento, int asistentesEsperados, boolean requiereCatering) {
        super(id, aula, fecha, horaInicio, horaFin, responsable, descripcion);
        this.tipoEvento = tipoEvento;
        this.asistentesEsperados = asistentesEsperados;
        this.requiereCatering = requiereCatering;
    }

    // Getters y Setters
    public TipoEvento getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(TipoEvento tipoEvento) { this.tipoEvento = tipoEvento; }

    public int getAsistentesEsperados() { return asistentesEsperados; }
    public void setAsistentesEsperados(int asistentesEsperados) { this.asistentesEsperados = asistentesEsperados; }

    public boolean isRequiereCatering() { return requiereCatering; }
    public void setRequiereCatering(boolean requiereCatering) { this.requiereCatering = requiereCatering; }

    @Override
    public boolean validar() throws ReservaException {
        super.validar();
        
        if (asistentesEsperados > aula.getCapacidad()) {
            throw new ReservaException("La cantidad de asistentes excede la capacidad del aula");
        }
        
        if (tipoEvento == TipoEvento.CONFERENCIA && aula.getTipo() != TipoAula.AUDITORIO) {
            throw new ReservaException("Las conferencias deben realizarse en auditorios");
        }
        
        return true;
    }

    @Override
    public String getTipoReserva() {
        return "EVENTO (" + tipoEvento + ")";
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" - Asistentes: %d - Catering: %s", 
                asistentesEsperados, requiereCatering ? "Si" : "No");
    }
}
