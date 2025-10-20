/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo.aula;

/**
 *
 * @author camix
 */
import modelo.enumeraciones.TipoAula;

public class aula {
    private String codigo;
    private String nombre;
    private int capacidad;
    private TipoAula tipo;
    private String equipamiento;
    private boolean disponible;

    public aula(String codigo, String nombre, int capacidad, TipoAula tipo, String equipamiento) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.tipo = tipo;
        this.equipamiento = equipamiento;
        this.disponible = true;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }

    public TipoAula getTipo() { return tipo; }
    public void setTipo(TipoAula tipo) { this.tipo = tipo; }

    public String getEquipamiento() { return equipamiento; }
    public void setEquipamiento(String equipamiento) { this.equipamiento = equipamiento; }

    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }

    @Override
    public String toString() {
        return String.format("Aula [%s] %s - %s - Capacidad: %d - Equipamiento: %s", 
                codigo, nombre, tipo, capacidad, equipamiento);
    }
}
