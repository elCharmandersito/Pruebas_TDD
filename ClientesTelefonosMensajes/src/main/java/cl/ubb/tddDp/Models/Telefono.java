package cl.ubb.tddDp.Models;

public class Telefono {

    private String numero;
    private boolean movil;

    public Telefono(String numero, boolean movil) {
        this.numero = numero;
        this.movil = movil;
    }

    public String getNumero() {
        return numero;
    }

    public boolean isMovil() {
        return false;
    }

    public void sendMensaje(String numFonoDest, String msg) {

    }

    public String[][] getAllMensajesEnviados() {
        return null;
    }

    public String[] getMensajeEnviado(int posicion) {
        return null;
    }

}
