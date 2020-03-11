package sample.logica;


import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.util.MailSSLSocketFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.stage.FileChooser;
import modelos.Tarea;
import sample.clases.Correo;
import sample.clases.CuentaCorreo;
import sample.clases.MensajeInforme;
import sample.clases.TreeItemPropio;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Properties;

public class Logica {

    private static Logica INSTANCE = null;


    private ObservableList<Correo> listaCorreos;
    private ObservableList<CuentaCorreo> listaCuentas;
    private ObservableList<Tarea> listaTarea;
    private ObservableList<MensajeInforme> listaMensaje;


    private Logica() {
        listaCorreos = FXCollections.observableArrayList();
        listaCuentas = FXCollections.observableArrayList();
        listaTarea = FXCollections.observableArrayList();
        listaMensaje = FXCollections.observableArrayList();

    }

    public static Logica getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Logica();

        return INSTANCE;
    }


    public ObservableList<Correo> getListaCorreos() {
        return listaCorreos;
    }

    public ObservableList<CuentaCorreo> getListaCuentas() {
        return listaCuentas;
    }

    public ObservableList<Tarea> getListaTarea() {
        return listaTarea;
    }

    public ObservableList<MensajeInforme> getListaMensajeInforme() {
        return listaMensaje;
    }

    public void añadirMensaje(MensajeInforme mensaje) {
        listaMensaje.add(mensaje);
    }

    public void borrarMensaje(MensajeInforme mensaje) {
        listaMensaje.remove(mensaje);
    }

    public void añadirCuenta(CuentaCorreo cuenta) {
        listaCuentas.add(cuenta);
    }

    public void borrarCuenta(CuentaCorreo cuenta) {
        listaCuentas.remove(cuenta);
    }

    public void añadirTarea(Tarea tarea) {
        listaTarea.add(tarea);
    }

    public void borrarTarea(Tarea tarea) {
        listaTarea.remove(tarea);
    }


    public void cargarListaCorreos(Store store, Folder a) {

        try {


            Folder folder = (IMAPFolder) store.getFolder(a.getFullName()); // This doesn't work for other email account
            if (!folder.isOpen() && folder.getType() == 3) {

                folder.open(Folder.READ_WRITE);
            }

            if (folder.getType() == 3) {

                Message[] messages = folder.getMessages();
                Correo correo;
                for (int b = 0; b < messages.length; b++) {
                    correo = new Correo(messages[b]);
                    listaCorreos.add(correo);
                }
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Store cargarStore(String user, String pass) {
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flags.Flag flag = null;
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", user, pass);

            CuentaCorreo cuentaCorreo = new CuentaCorreo(user, pass, store);
            listaCuentas.add(cuentaCorreo);
            cargarTreeView();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return store;
    }

    public void desconectarStore(Store store) {
        try {
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Folder[] getCarpetasCorreo(Store store) {
        Folder[] a = null;
        try {
            a = store.getDefaultFolder().list();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void verTodasLasCarpetas(Folder[] a, TreeItemPropio rootTreeItem, CuentaCorreo cuentaCorreo) {
        try {
            TreeItemPropio rootItem = null;
            for (Folder itemFolder : a) {

                rootItem = new TreeItemPropio(itemFolder.getName(), cuentaCorreo, itemFolder); //Con full nombre para poder cargar las carpetas
                rootTreeItem.getChildren().add(rootItem);

                if (itemFolder.getType() == Folder.HOLDS_FOLDERS) {
                    verTodasLasCarpetas(itemFolder.list(), rootItem, cuentaCorreo);
                }
            }

        } catch (MessagingException e) {
            e.printStackTrace();


        }
    }

    public TreeItem cargarTreeView() {
        TreeItem nodoPadre = new TreeItem("Lista de cuentas de correo");

        for (int b = 0; b < Logica.getInstance().getListaCuentas().size(); b++) {
            nodoPadre.setExpanded(true);

            TreeItemPropio rootItem = new TreeItemPropio(Logica.getInstance().getListaCuentas().get(b).getUser(), null, null);
            Logica.getInstance().verTodasLasCarpetas(Logica.getInstance().getCarpetasCorreo(Logica.getInstance().getListaCuentas().get(b).getStore()), rootItem, Logica.getInstance().getListaCuentas().get(b));
            nodoPadre.getChildren().add(rootItem);
        }
        return nodoPadre;


    }

    public boolean compruebaConexion(String user, String pass) {
        IMAPFolder folder = null;
        Store store = null;
        String subject = null;
        Flags.Flag flag = null;
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);

            store = session.getStore("imaps");
            store.connect("imap.googlemail.com", user, pass);
            return true;
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean enviarCorreo(CuentaCorreo cuentaCorreo, String to, String cc, String asunto, String cuerpo) {
        Session session = getSession(cuentaCorreo);
        //Prepare email message
        Message message = prepareMessage(session, cuentaCorreo.getUser(), to, asunto, cuerpo, cc);
        if (message != null) {
            try {
                //Send mail
                Transport.send(message);
                System.out.println("Message sent successfully");
                return true;
            } catch (MessagingException ex) {
                //System.out.println(ex.getMessage());
            }
        }
        return false;
    }

    public Message prepareMessage(Session session, String from, String to, String asunto, String cuerpo, String cc) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(asunto);
            message.setContent(cuerpo, "text/html");
            //message.setReplyTo(cc); hacer un array de cuentas a enviar
            return message;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;

    }


    public Session getSession(CuentaCorreo cuentaCorreo) {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        properties.put("mail.imaps.ssl.trust", "*");
        properties.put("mail.imaps.ssl.socketFactory", sf);
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");


        //Your gmail address
        String myAccountEmail = cuentaCorreo.getUser();
        //Your gmail password
        String password = cuentaCorreo.getPass();
        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        return session;
    }


    public void borrarCorreo(Correo correo, Folder folder) {
        Message[] m = new Message[]{correo.getMensaje()};
        Folder trash = null;
        try {
            trash = folder.getStore().getFolder("[Gmail]/Papelera");
            folder.copyMessages(m, trash);
            folder.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void grabarDatos() {
        File datos = new File("datos/datosCorreo.txt");
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(datos));
            for (CuentaCorreo cuentaCorreo : Logica.getInstance().getListaCuentas()) {
                dataOutputStream.writeUTF(cuentaCorreo.getUser());
                dataOutputStream.writeUTF(cuentaCorreo.getPass());

            }
            dataOutputStream.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    public void cargarDatos() {
        File datos = new File("datos/datosCorreo.txt");
        try {
            DataInputStream dataInputStream = new DataInputStream(new FileInputStream(datos));


            while (dataInputStream.available() > 0) {
                for (int a = 0; a < 2; a++) {
                    String user = dataInputStream.readUTF();
                    String pass = dataInputStream.readUTF();
                    Logica.getInstance().cargarStore(user, pass);

                }


            }
            dataInputStream.close();
        } catch (EOFException e) {
        } catch (IOException e) {
        }

    }

    public void grabarDatosTareas() {
        File datos = new File("datos/datostareas.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(datos));
            for (Tarea tarea : Logica.getInstance().getListaTarea())
            {
                oos.writeObject(tarea);
            }
            oos.close();  // Se cierra al terminar.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cargarDatosTarea() {
        File datos = new File("datos/datostareas.txt");
        try {
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(datos));
            Tarea a=(Tarea)oos.readObject();
            while (a!=null)
            {
                Logica.getInstance().añadirTarea(a);
                a=(Tarea)oos.readObject();
            }
            oos.close();


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (EOFException ex) {
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public ObservableList<Correo> cargarCorreoInbox() {
        ObservableList<Correo> comboLista = listaCorreos = FXCollections.observableArrayList();
        if (Logica.getInstance().getListaCuentas().size() >= 0) {
            CuentaCorreo cuentaCorreo = Logica.getInstance().getListaCuentas().get(0);
            Store store = cuentaCorreo.getStore();

            try {


                Folder folder = (IMAPFolder) store.getFolder("Inbox"); // This doesn't work for other email account
                if (!folder.isOpen() && folder.getType() == 3) {

                    folder.open(Folder.READ_WRITE);
                }

                if (folder.getType() == 3) {

                    Message[] messages = folder.getMessages();
                    Correo correo;
                    for (int b = 0; b < messages.length; b++) {
                        correo = new Correo(messages[b]);
                        comboLista.add(correo);
                    }
                }
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

        return comboLista;
    }

    public ArrayList<MensajeInforme> imprimirCuenta(Store store,String user) {

        ArrayList<MensajeInforme> list=new ArrayList<MensajeInforme>();
        try {
            Folder[] a = store.getDefaultFolder().list();
            for (Folder itemFolder : a) {
                //System.out.println(itemFolder.getFullName());
                cargarCarpetasInformesGmail(itemFolder,list,user);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cargarCarpetasInformesGmail(Folder itemFolder,ArrayList<MensajeInforme> list,String user) {
        try {
            if (!itemFolder.isOpen() && itemFolder.getType() == 3) {

                itemFolder.open(Folder.READ_WRITE);

                Message[] messages = itemFolder.getMessages();
                Correo correo;
                for (int b = 0; b < messages.length; b++) {
                    correo = new Correo(messages[b]);

                    list.add(new MensajeInforme(correo.getAsunto(),correo.getRemitente(),correo.getFecha(),correo.getTextoContenido(correo),itemFolder.getFullName(),user));

                }

            } else {
                for (Folder folder : itemFolder.list()) {
                    cargarCarpetasInformesGmail(folder,list,user);
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public File getFile() {
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        FileChooser fileChooser=new FileChooser();
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showSaveDialog(null);
    }

}