package sample.clases;




import org.apache.commons.mail.util.MimeMessageParser;
import org.jsoup.Jsoup;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class Correo   {

    private Message mensaje;

    public Correo(Message mensaje) {
        this.mensaje = mensaje;
    }


    public String getAsunto(){
        String sub=null;
        try {
            sub = mensaje.getSubject();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return sub;
    }

    public Date getFecha(){
        Date sub=null;
        try {
            sub =  mensaje.getSentDate();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return sub;
    }

    public  String getRemitente(){
        Address[] sub=null;
        try {
            sub=mensaje.getFrom();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return String.valueOf(sub[0]);
    }




    public Message getMensaje() {
        return mensaje;
    }

    public void setMensaje(Message mensaje) {
        this.mensaje = mensaje;
    }

    public String readHtmlContent(Correo correo) throws Exception {
        MimeMessage message=(MimeMessage) correo.getMensaje();
        return new MimeMessageParser(message).parse().getHtmlContent();
    }

    public String readPlainContent(Correo correo) throws Exception {
        MimeMessage message=(MimeMessage) correo.getMensaje();
        return new MimeMessageParser(message).parse().getPlainContent();
    }

    public String paraCombo(){
        String a= null;
        try {
            a = this.getRemitente()+" ,"+mensaje.getReceivedDate().toString();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return a;
    }

    public String getContent() throws Exception {
        String resultado = "";
        MimeMessageParser parser = new MimeMessageParser((MimeMessage) mensaje);
        parser.parse();

        if (mensaje.isMimeType("text/plain")) {
            resultado = parser.getPlainContent();
        } else if (mensaje.isMimeType("multipart/*")) {
            resultado = parser.getHtmlContent();
        } else if (mensaje.isMimeType(" text/html")) {
            resultado = parser.getHtmlContent();

        }

        return resultado;
    }


    public String getTextoContenido(Correo m) {

        try {
            return Jsoup.parse(m.getContent()).text();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
