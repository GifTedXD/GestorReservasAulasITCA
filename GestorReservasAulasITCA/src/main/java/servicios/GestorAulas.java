/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

/**
 *
 * @author camix
 */
import modelo.aula.aula;
import modelo.enumeraciones.TipoAula;
import excepciones.AulaException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorAulas {
    private List<aula> aulas;

    public GestorAulas() {
        this.aulas = new ArrayList<>();
        cargarAulasEjemplo();
    }

    private void cargarAulasEjemplo() {
        aulas.add(new aula("A101", "Aula Teórica 101", 40, TipoAula.TEORICA, "Proyector, Pizarra"));
        aulas.add(new aula("L201", "Laboratorio Computación", 25, TipoAula.LABORATORIO, "Computadoras, Proyector"));
        aulas.add(new aula("A301", "Auditorio Principal", 100, TipoAula.AUDITORIO, "Sonido, Video, Micrófonos"));
        aulas.add(new aula("A102", "Aula Teórica 102", 35, TipoAula.TEORICA, "Pizarra"));
        aulas.add(new aula("L202", "Laboratorio Electrónica", 20, TipoAula.LABORATORIO, "Instrumentos, Computadoras"));
    }

    public void registrarAula(aula aula) throws AulaException {
        if (buscarAulaPorCodigo(aula.getCodigo()) != null) {
            throw new AulaException("Ya existe un aula con el código: " + aula.getCodigo());
        }
        aulas.add(aula);
    }

    public aula buscarAulaPorCodigo(String codigo) {
        return aulas.stream()
                .filter(a -> a.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    public List<aula> listarAulas() {
        return new ArrayList<>(aulas);
    }

    public List<aula> listarAulasPorTipo(TipoAula tipo) {
        return aulas.stream()
                .filter(a -> a.getTipo() == tipo)
                .collect(Collectors.toList());
    }

    public void modificarAula(String codigo, aula aulaModificada) throws AulaException {
        aula aulaExistente = buscarAulaPorCodigo(codigo);
        if (aulaExistente == null) {
            throw new AulaException("No se encontro el aula con código: " + codigo);
        }
        
        aulaExistente.setNombre(aulaModificada.getNombre());
        aulaExistente.setCapacidad(aulaModificada.getCapacidad());
        aulaExistente.setTipo(aulaModificada.getTipo());
        aulaExistente.setEquipamiento(aulaModificada.getEquipamiento());
        aulaExistente.setDisponible(aulaModificada.isDisponible());
    }

    public boolean eliminarAula(String codigo) {
        return aulas.removeIf(a -> a.getCodigo().equalsIgnoreCase(codigo));
    }
}
