package sample.clases;

import java.time.LocalDate;
import java.util.Date;

public class Tarea {
    private LocalDate fecha;
    private int hora;
    private int min;
    private String textoTarea;
    private Boolean realizado;

    public Tarea(LocalDate fecha,int hora,int min, String textoTarea,Boolean realizado) {
        this.fecha = fecha;
        this.textoTarea = textoTarea;
        this.hora=hora;
        this.min=min;
        this.realizado=realizado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getHora() { return hora; }

    public void setHora(int hora) { this.hora = hora; }

    public int getMin() { return min; }

    public void setMin(int min) { this.min = min; }

    public String getTextoTarea() {
        return textoTarea;
    }

    public void setTextoTarea(String textoTarea) {
        this.textoTarea = textoTarea;
    }

    public Boolean getRealizado() {
        return realizado;
    }

    public void setRealizado(Boolean realizado) {
        this.realizado = realizado;
    }
}
