package sample.clases;

import java.util.Date;

public class MensajeInforme {
    private String asunto;
    private String remitente;
    private Date date;
    private String contenido;
    private String carpeta;
    private String cuenta;

    public MensajeInforme(String asunto, String remitente, Date date, String contenido,String carpeta,String cuenta) {
        this.asunto = asunto;
        this.remitente = remitente;
        this.date = date;
        this.contenido = contenido;
        this.carpeta = carpeta;
        this.cuenta = cuenta;
    }

    public MensajeInforme(String asunto, String remitente, Date date, String contenido,String carpeta) {
        this.asunto = asunto;
        this.remitente = remitente;
        this.date = date;
        this.contenido = contenido;
        this.carpeta = carpeta;
    }

    public MensajeInforme(String asunto, String remitente, Date date, String contenido) {
        this.asunto = asunto;
        this.remitente = remitente;
        this.date = date;
        this.contenido = contenido;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
}
