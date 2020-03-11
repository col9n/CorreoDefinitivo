package jasperReport;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MensajeFactory {
    public static List<MensajeInforme> createListPersonas()
    {

        List<MensajeInforme> listaMensajes = new ArrayList<>();
        listaMensajes.add(new MensajeInforme("asunto", "remitente",Date.valueOf(LocalDate.now()) ,"contenido","inbox","edu"));
        listaMensajes.add(new MensajeInforme("asunto1", "remitente1",Date.valueOf(LocalDate.now()),"contenido1","inbox","edu"));
        listaMensajes.add(new MensajeInforme("asunto", "remitente",Date.valueOf(LocalDate.now()) ,"contenido","hola","edu"));
        return listaMensajes;
    }
}
