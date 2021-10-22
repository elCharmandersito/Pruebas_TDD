package cl.ubb.tddDp.Models;

import cl.ubb.tddDp.Exceptions.*;

import java.util.ArrayList;

public class Cliente {

    private String nombre;

    private ArrayList<Telefono> telefonos = new ArrayList<Telefono>();

    public Cliente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void addTelefono(Telefono telefono) {
        telefonos.add(telefono);
    }

    public void sendMensaje(String numFonoOrigen, String numFonoDest, String msg) throws SendSMSErrorException, PhoneNumberNoFoundException, EmptyListException {
        Telefono telefono = null;

        if(telefonos.size() == 0){
            throw new EmptyListException();
        }

        for (int i = 0 ; i < telefonos.size() ; i++) {
            if (numFonoOrigen.equalsIgnoreCase(telefonos.get(i).getNumero())) {
                telefono = telefonos.get(i);
            }
        }

        if (telefono != null) {
            if (telefono.isMovil()) {
                telefono.sendMensaje(numFonoDest, msg);
            } else {
                throw new SendSMSErrorException();
            }
        } else {
            throw new PhoneNumberNoFoundException();
        }
    }

    public String[][] getAllMensajesEnviadosDe(String numFono) throws PhoneNumberNoFoundException, GetSMSErrorException, SendSMSErrorException, EmptyListException {
        Telefono telefono = null;
        int i = 0;

        if (telefonos.size() == 0) {
            throw new EmptyListException();
        }

        while (i < telefonos.size() && telefono == null) {
            if (telefonos.get(i).getNumero().equalsIgnoreCase(numFono)) {
                telefono = telefonos.get(i);
            }
            i++;
        }

        if (telefono != null) {
            if (telefono.isMovil()) {
                String [][] mensajesEnviados = telefono.getAllMensajesEnviados();
                if (mensajesEnviados != null) {
                    return mensajesEnviados;
                } else {
                    throw new GetSMSErrorException();
                }
            } else {
                throw new SendSMSErrorException();
            }
        } else {
            throw new PhoneNumberNoFoundException();
        }
    }

    public String[] getMensajeEnviadoDe(String numFono, int posicion) throws EmptyListException, PhoneNumberNoFoundException, SendSMSErrorException, MessageNotFoundException {
        Telefono telefono = null;
        int i = 0;

        if (telefonos.size() != 0) {
            while (i < telefonos.size() && telefono == null) {
                if (telefonos.get(i).getNumero().equalsIgnoreCase(numFono)) {
                    telefono = telefonos.get(i);
                }
                i++;
            }

            if (telefono != null) {
                if (telefono.isMovil()) {
                    String [] msgEnviado = telefono.getMensajeEnviado(posicion);
                    if (msgEnviado != null) {
                        return msgEnviado;
                    } else {
                        throw new MessageNotFoundException();
                    }
                } else {
                    throw new SendSMSErrorException();
                }
            } else {
                throw new PhoneNumberNoFoundException();
            }
        } else {
            throw new EmptyListException();
        }
    }
}