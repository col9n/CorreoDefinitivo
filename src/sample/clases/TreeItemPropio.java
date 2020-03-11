package sample.clases;


import javafx.scene.control.TreeItem;

import javax.mail.Folder;

public class TreeItemPropio extends TreeItem<String> {

    private String nombre;
    private CuentaCorreo cuenta;
    private Folder folder;

    public TreeItemPropio(String nombre, CuentaCorreo cuenta, Folder folder) {
        super(nombre);
        this.nombre = nombre;
        this.cuenta = cuenta;
        this.folder = folder;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public CuentaCorreo getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaCorreo cuenta) {
        this.cuenta = cuenta;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
}
