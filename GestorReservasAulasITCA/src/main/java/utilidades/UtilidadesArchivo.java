/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

/**
 *
 * @author camix
 */
import modelo.aula.aula;
import modelo.reserva.reserva;
import servicios.GestorAulas;
import servicios.GestorReservas;
import java.io.*;
import java.util.List;

public class UtilidadesArchivo {
    private static final String ARCHIVO_AULAS = "aulas.csv";
    private static final String ARCHIVO_RESERVAS = "reservas.csv";
    private static final String ARCHIVO_REPORTES = "reportes.txt";

    public static void guardarAulas(GestorAulas gestorAulas) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_AULAS))) {
            writer.println("codigo,nombre,capacidad,tipo,equipamiento,disponible");
            for (aula aula : gestorAulas.listarAulas()) {
                writer.printf("%s,%s,%d,%s,%s,%b%n",
                        aula.getCodigo(),
                        aula.getNombre(),
                        aula.getCapacidad(),
                        aula.getTipo(),
                        aula.getEquipamiento(),
                        aula.isDisponible());
            }
        }
    }

    public static void guardarReservas(GestorReservas gestorReservas) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_RESERVAS))) {
            writer.println("id,tipo,aula_codigo,fecha,hora_inicio,hora_fin,responsable,descripcion,estado");
            for (reserva reserva : gestorReservas.listarReservas()) {
                // Nota: Para simplificar, no guardamos los atributos específicos de cada tipo
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s,%s%n",
                        reserva.getId(),
                        reserva.getTipoReserva(),
                        reserva.getAula().getCodigo(),
                        reserva.getFecha(),
                        reserva.getHoraInicio(),
                        reserva.getHoraFin(),
                        reserva.getResponsable(),
                        reserva.getDescripcion(),
                        reserva.getEstado());
            }
        }
    }

    public static void exportarReporte(String contenido) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ARCHIVO_REPORTES, true))) {
            writer.println("=== REPORTE GENERADO: " + java.time.LocalDateTime.now() + " ===");
            writer.println(contenido);
            writer.println("=============================================");
            writer.println();
        }
    }
}
