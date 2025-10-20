/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author camix
 */
import modelo.reserva.reserva;
import modelo.aula.aula;
import modelo.enumeraciones.EstadoReserva;
import excepciones.ReservaException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GestorReservas {
    private List<reserva> reservas;
    private GestorAulas gestorAulas;

    public GestorReservas(GestorAulas gestorAulas) {
        this.reservas = new ArrayList<>();
        this.gestorAulas = gestorAulas;
    }

    public void registrarReserva(reserva reserva) throws ReservaException {
        // Validar la reserva
        reserva.validar();
        
        // Verificar conflictos de horario
        if (existeConflictoHorario(reserva)) {
            throw new ReservaException("Conflicto de horario: el aula ya esta reservada en ese horario");
        }
        
        // Verificar que el aula esté disponible
        if (!reserva.getAula().isDisponible()) {
            throw new ReservaException("El aula no esta disponible para reservas");
        }
        
        reservas.add(reserva);
    }

    private boolean existeConflictoHorario(reserva nuevaReserva) {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
                .filter(r -> r.getAula().getCodigo().equals(nuevaReserva.getAula().getCodigo()))
                .filter(r -> r.getFecha().equals(nuevaReserva.getFecha()))
                .anyMatch(r -> seSolapan(r.getHoraInicio(), r.getHoraFin(), 
                                       nuevaReserva.getHoraInicio(), nuevaReserva.getHoraFin()));
    }

    private boolean seSolapan(LocalTime inicio1, LocalTime fin1, LocalTime inicio2, LocalTime fin2) {
        return !(fin1.isBefore(inicio2) || fin2.isBefore(inicio1));
    }

    public reserva buscarReservaPorId(String id) {
        return reservas.stream()
                .filter(r -> r.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public List<reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    public List<reserva> listarReservasActivas() {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
                .collect(Collectors.toList());
    }

    public List<reserva> buscarReservasPorResponsable(String responsable) {
        return reservas.stream()
                .filter(r -> r.getResponsable().toLowerCase().contains(responsable.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<reserva> buscarReservasPorAula(String codigoAula) {
        return reservas.stream()
                .filter(r -> r.getAula().getCodigo().equalsIgnoreCase(codigoAula))
                .collect(Collectors.toList());
    }

    public void modificarReserva(String id, reserva reservaModificada) throws ReservaException {
        reserva reservaExistente = buscarReservaPorId(id);
        if (reservaExistente == null) {
            throw new ReservaException("No se encontro la reserva con ID: " + id);
        }
        
        // Remover temporalmente para verificar conflictos
        reservas.remove(reservaExistente);
        
        try {
            registrarReserva(reservaModificada);
        } catch (ReservaException e) {
            // Si hay conflicto, restaurar la reserva original
            reservas.add(reservaExistente);
            throw e;
        }
    }

    public void cancelarReserva(String id) throws ReservaException {
        reserva reserva = buscarReservaPorId(id);
        if (reserva == null) {
            throw new ReservaException("No se encontro la reserva con ID: " + id);
        }
        reserva.setEstado(EstadoReserva.CANCELADA);
    }

    // Métodos para reportes
    public List<aula> obtenerTopAulasMasReservadas(int topN) {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
                .collect(Collectors.groupingBy(reserva::getAula, 
                         Collectors.summingLong(r -> Duration.between(r.getHoraInicio(), r.getHoraFin()).toHours())))
                .entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(topN)
                .map(e -> e.getKey())
                .collect(Collectors.toList());
    }

    public String generarReporteOcupacionPorTipo() {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
                .collect(Collectors.groupingBy(r -> r.getAula().getTipo(), 
                         Collectors.counting()))
                .entrySet().stream()
                .map(e -> String.format("%s: %d reservas", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
    }

    public String generarReporteDistribucionPorTipoReserva() {
        return reservas.stream()
                .filter(r -> r.getEstado() == EstadoReserva.ACTIVA)
                .collect(Collectors.groupingBy(reserva::getTipoReserva, 
                         Collectors.counting()))
                .entrySet().stream()
                .map(e -> String.format("%s: %d reservas", e.getKey(), e.getValue()))
                .collect(Collectors.joining("\n"));
    }
}
