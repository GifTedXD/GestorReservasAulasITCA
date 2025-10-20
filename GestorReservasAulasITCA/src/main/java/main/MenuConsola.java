/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author camix
 */
import modelo.aula.aula;
import modelo.reserva.*;
import modelo.enumeraciones.*;
import servicios.GestorAulas;
import servicios.GestorReservas;
import excepciones.AulaException;
import excepciones.ReservaException;
import utilidades.UtilidadesFecha;
import utilidades.UtilidadesArchivo;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class MenuConsola {
    private GestorAulas gestorAulas;
    private GestorReservas gestorReservas;
    private Scanner scanner;

    public MenuConsola() {
        this.gestorAulas = new GestorAulas();
        this.gestorReservas = new GestorReservas(gestorAulas);
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== GESTOR DE RESERVAS DE AULAS ITCA ===");
            System.out.println("1. Gestion de Aulas");
            System.out.println("2. Gestion de Reservas");
            System.out.println("3. Reportes");
            System.out.println("4. Guardar Datos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1 -> mostrarMenuAulas();
                case 2 -> mostrarMenuReservas();
                case 3 -> mostrarMenuReportes();
                case 4 -> guardarDatos();
                case 5 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción invalida");
            }
        } while (opcion != 5);
    }

    private void mostrarMenuAulas() {
        int opcion;
        do {
            System.out.println("\n=== GESTION DE AULAS ===");
            System.out.println("1. Listar Aulas");
            System.out.println("2. Registrar Aula");
            System.out.println("3. Modificar Aula");
            System.out.println("4. Buscar Aula");
            System.out.println("5. Volver al Menú Principal");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1 -> listarAulas();
                case 2 -> registrarAula();
                case 3 -> modificarAula();
                case 4 -> buscarAula();
                case 5 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 5);
    }

    private void mostrarMenuReservas() {
        int opcion;
        do {
            System.out.println("\n=== GESTION DE RESERVAS ===");
            System.out.println("1. Listar Reservas Activas");
            System.out.println("2. Registrar Reserva");
            System.out.println("3. Modificar Reserva");
            System.out.println("4. Cancelar Reserva");
            System.out.println("5. Buscar Reserva por Responsable");
            System.out.println("6. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1 -> listarReservasActivas();
                case 2 -> registrarReserva();
                case 3 -> modificarReserva();
                case 4 -> cancelarReserva();
                case 5 -> buscarReservaPorResponsable();
                case 6 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 6);
    }

    private void mostrarMenuReportes() {
        int opcion;
        do {
            System.out.println("\n=== REPORTES ===");
            System.out.println("1. Top 3 Aulas Mas Reservadas");
            System.out.println("2. Ocupacion por Tipo de Aula");
            System.out.println("3. Distribucion por Tipo de Reserva");
            System.out.println("4. Exportar Reportes");
            System.out.println("5. Volver al Menu Principal");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerEntero();
            
            switch (opcion) {
                case 1 -> generarReporteTopAulas();
                case 2 -> generarReporteOcupacion();
                case 3 -> generarReporteDistribucion();
                case 4 -> exportarReportes();
                case 5 -> System.out.println("Volviendo al menu principal...");
                default -> System.out.println("Opcion invalida");
            }
        } while (opcion != 5);
    }

    // Métodos de implementación para cada funcionalidad
    private void listarAulas() {
        System.out.println("\n=== LISTA DE AULAS ===");
        List<aula> aulas = gestorAulas.listarAulas();
        if (aulas.isEmpty()) {
            System.out.println("No hay aulas registradas.");
        } else {
            aulas.forEach(System.out::println);
        }
    }

    private void registrarAula() {
        try {
            System.out.println("\n=== REGISTRAR NUEVA AULA ===");
            System.out.print("Codigo: ");
            String codigo = scanner.nextLine();
            
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine();
            
            System.out.print("Capacidad: ");
            int capacidad = leerEntero();
            
            System.out.println("Tipos de Aula:");
            for (TipoAula tipo : TipoAula.values()) {
                System.out.println((tipo.ordinal() + 1) + ". " + tipo);
            }
            System.out.print("Seleccione tipo: ");
            int tipoIndex = leerEntero() - 1;
            TipoAula tipo = TipoAula.values()[tipoIndex];
            
            System.out.print("Equipamiento: ");
            String equipamiento = scanner.nextLine();
            
            aula nuevaAula = new aula(codigo, nombre, capacidad, tipo, equipamiento);
            gestorAulas.registrarAula(nuevaAula);
            System.out.println("Aula registrada exitosamente.");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarReserva() {
        try {
            System.out.println("\n=== REGISTRAR NUEVA RESERVA ===");
            System.out.println("Tipos de Reserva:");
            System.out.println("1. Clase");
            System.out.println("2. Practica");
            System.out.println("3. Evento");
            System.out.print("Seleccione tipo: ");
            int tipoReserva = leerEntero();
            
            // Datos comunes
            System.out.print("ID de Reserva: ");
            String id = scanner.nextLine();
            
            System.out.print("Codigo del Aula: ");
            String codigoAula = scanner.nextLine();
            aula aula = gestorAulas.buscarAulaPorCodigo(codigoAula);
            if (aula == null) {
                System.out.println("Aula no encontrada.");
                return;
            }
            
            System.out.print("Fecha (dd/MM/yyyy): ");
            LocalDate fecha = UtilidadesFecha.parseFecha(scanner.nextLine());
            
            System.out.print("Hora de inicio (HH:mm): ");
            LocalTime horaInicio = LocalTime.parse(scanner.nextLine());
            
            System.out.print("Hora de fin (HH:mm): ");
            LocalTime horaFin = LocalTime.parse(scanner.nextLine());
            
            System.out.print("Responsable: ");
            String responsable = scanner.nextLine();
            
            System.out.print("Descripcion: ");
            String descripcion = scanner.nextLine();
            
            reserva reserva;
            
            switch (tipoReserva) {
                case 1 -> { // Clase
                    System.out.print("Materia: ");
                    String materia = scanner.nextLine();
                    
                    System.out.print("Grupo: ");
                    String grupo = scanner.nextLine();
                    
                    System.out.print("Cantidad de Estudiantes: ");
                    int estudiantes = leerEntero();
                    
                    reserva = new ReservaClase(id, aula, fecha, horaInicio, horaFin, 
                                              responsable, descripcion, materia, grupo, estudiantes);
                }
                case 2 -> { // Práctica
                    System.out.print("Equipo Necesario: ");
                    String equipo = scanner.nextLine();
                    
                    System.out.print("Supervisor: ");
                    String supervisor = scanner.nextLine();
                    
                    reserva = new ReservaPractica(id, aula, fecha, horaInicio, horaFin,
                                                 responsable, descripcion, equipo, supervisor);
                }
                case 3 -> { // Evento
                    System.out.println("Tipos de Evento:");
                    for (TipoEvento tipo : TipoEvento.values()) {
                        System.out.println((tipo.ordinal() + 1) + ". " + tipo);
                    }
                    System.out.print("Seleccione tipo: ");
                    int tipoEventoIndex = leerEntero() - 1;
                    TipoEvento tipoEvento = TipoEvento.values()[tipoEventoIndex];
                    
                    System.out.print("Asistentes Esperados: ");
                    int asistentes = leerEntero();
                    
                    System.out.print("Requiere Catering (S/N): ");
                    boolean catering = scanner.nextLine().equalsIgnoreCase("S");
                    
                    reserva = new ReservaEvento(id, aula, fecha, horaInicio, horaFin,
                                               responsable, descripcion, tipoEvento, asistentes, catering);
                }
                default -> {
                    System.out.println("Tipo de reserva invalido.");
                    return;
                }
            }
            
            gestorReservas.registrarReserva(reserva);
            System.out.println("Reserva registrada exitosamente.");
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void generarReporteTopAulas() {
        System.out.println("\n=== TOP 3 AULAS MAS RESERVADAS ===");
        List<aula> topAulas = gestorReservas.obtenerTopAulasMasReservadas(3);
        if (topAulas.isEmpty()) {
            System.out.println("No hay datos suficientes para generar el reporte.");
        } else {
            for (int i = 0; i < topAulas.size(); i++) {
                System.out.println((i + 1) + ". " + topAulas.get(i).getNombre() + 
                                 " (" + topAulas.get(i).getCodigo() + ")");
            }
        }
    }

    private void generarReporteOcupacion() {
        System.out.println("\n=== OCUPACION POR TIPO DE AULA ===");
        String reporte = gestorReservas.generarReporteOcupacionPorTipo();
        System.out.println(reporte);
    }

    private void generarReporteDistribucion() {
        System.out.println("\n=== DISTRIBUCION POR TIPO DE RESERVA ===");
        String reporte = gestorReservas.generarReporteDistribucionPorTipoReserva();
        System.out.println(reporte);
    }

    private void guardarDatos() {
        try {
            UtilidadesArchivo.guardarAulas(gestorAulas);
            UtilidadesArchivo.guardarReservas(gestorReservas);
            System.out.println("Datos guardados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    private void exportarReportes() {
        try {
            StringBuilder reporteCompleto = new StringBuilder();
            reporteCompleto.append("TOP 3 AULAS MAS RESERVADAS:\n");
            List<aula> topAulas = gestorReservas.obtenerTopAulasMasReservadas(3);
            for (int i = 0; i < topAulas.size(); i++) {
                reporteCompleto.append((i + 1) + ". " + topAulas.get(i).getNombre() + "\n");
            }
            
            reporteCompleto.append("\nOCUPACION POR TIPO DE AULA:\n");
            reporteCompleto.append(gestorReservas.generarReporteOcupacionPorTipo());
            
            reporteCompleto.append("\n\nDISTRIBUCION POR TIPO DE RESERVA:\n");
            reporteCompleto.append(gestorReservas.generarReporteDistribucionPorTipoReserva());
            
            UtilidadesArchivo.exportarReporte(reporteCompleto.toString());
            System.out.println("Reportes exportados exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al exportar reportes: " + e.getMessage());
        }
    }

    // Métodos auxiliares (implementar según necesidad)
    private void modificarAula() { /* Implementar */ }
    private void buscarAula() { /* Implementar */ }
    private void listarReservasActivas() { /* Implementar */ }
    private void modificarReserva() { /* Implementar */ }
    private void cancelarReserva() { /* Implementar */ }
    private void buscarReservaPorResponsable() { /* Implementar */ }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Por favor, ingrese un número válido: ");
            }
        }
    }
}
