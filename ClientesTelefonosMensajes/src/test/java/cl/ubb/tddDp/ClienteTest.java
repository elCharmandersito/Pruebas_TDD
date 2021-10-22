package cl.ubb.tddDp;

import cl.ubb.tddDp.Exceptions.*;
import cl.ubb.tddDp.Models.Cliente;
import cl.ubb.tddDp.Models.Telefono;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClienteTest {

    @Mock
    Telefono telefono;

    Cliente cliente;

    @Test
    @DisplayName("Test 1")
    void SiEnviaMensajePorUnTelefonoQueExiteYEsMovilDebePoderHacerlo() throws SendSMSErrorException, PhoneNumberNoFoundException, EmptyListException  {
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(true);

        // Act
        cliente.sendMensaje("969942351", "987843312", "Hola Mundo");

        // Assert
        verify(telefono).getNumero();
        verify(telefono).isMovil();
        verify(telefono).sendMensaje("987843312", "Hola Mundo");
    }

    @Test
    @DisplayName("Test 2")
    void SiEnviaMensajePorUnTelefonoQueExiteYNoEsMovilDebeLanzarSendSMSErrorException() {
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("422463334");
        when(telefono.isMovil()).thenReturn(false);

        // Act + Assert
        assertThrows(SendSMSErrorException.class,
                () -> cliente.sendMensaje("422463334", "990885500", "Hola Mundo"));
    }

    @Test
    @DisplayName("Test 3")
    void SiEnviaMensajePorUnTelefonoQueExiteYNoEsMovilDebeLanzarPhoneNumberNoFoundException() {
        // Arrange
        cliente = new Cliente("Patricio");
        Telefono telefono2 = new Telefono("958585858", false);

        cliente.addTelefono(telefono);
        when(telefono.getNumero()).thenReturn(telefono2.getNumero());

        //Act + Assert
        assertThrows(PhoneNumberNoFoundException.class,
                () -> cliente.sendMensaje("422463334", "990885500" , "Hola Mundo"));
    }

    @Test
    @DisplayName("Test 4")
    void SiEnviaMensajePorUnTelefonoYElClienteNoTieneTelefonosDebeLanzarEmptyListException() {
        // Arrange
        cliente = new Cliente("Patricio");

        // Act + Assert
        assertThrows(EmptyListException.class,
                () -> cliente.sendMensaje("422463334", "990885500", "Hola Mundo"));
    }

    @Test
    @DisplayName("Test 5")
    void SiSolicitaMensajesDeUnTelefonoQueExisteEsMovilYPoseeMensajesDebePoderRecibirLaLista() throws PhoneNumberNoFoundException, GetSMSErrorException, SendSMSErrorException, EmptyListException {
        // Arrange
        cliente = new Cliente("Pepe");
        cliente.addTelefono(telefono);

        String [][] mensajesEnviados = new String[2][3];
        mensajesEnviados[0][0] = "987843312";
        mensajesEnviados[0][1] = "Hola Mundo";
        mensajesEnviados[0][2] = "07/10/2021";
        mensajesEnviados[1][0] = "988854576";
        mensajesEnviados[1][1] = "Otro Saludo";
        mensajesEnviados[1][2] = "07/10/2021";

        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(true);
        when(telefono.getAllMensajesEnviados()).thenReturn(mensajesEnviados);

        // Act
        cliente.getAllMensajesEnviadosDe("969942351");

        // Assert
        verify(telefono).getNumero();
        verify(telefono).isMovil();
        verify(telefono).getAllMensajesEnviados();
    }

    @Test
    @DisplayName("Test 6")
    void SiSolicitaMensajeDeUnTelefonoQueNoExisteDebeLanzarPhoneNumberNoFoundException() {
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("958585858");

        // Act + Assert
        assertThrows(PhoneNumberNoFoundException.class,
                () -> cliente.getAllMensajesEnviadosDe("969942351"));
    }

    @Test
    @DisplayName("Test 7")
    void SiSolicitaMensajeDeUnTelefonoQueExisteYNoPoseeMensajesEnviadosDebeLanzarGetSMSErrorException() {
        // Arrange
        cliente = new Cliente("Pepe");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(true);
        when(telefono.getAllMensajesEnviados()).thenReturn(null);

        // Act + Assert
        assertThrows(GetSMSErrorException.class,
                () -> cliente.getAllMensajesEnviadosDe("969942351"));
    }

    @Test
    @DisplayName("Test 8")
    void SiSolicitaMensajeDeUnTelefonoQueExisteYNoEsMovilDebeLanzarSendSMSErrorException() {
        // Arrange
        cliente = new Cliente("Pepe");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("422463334");
        when(telefono.isMovil()).thenReturn(false);

        // Act + Assert
        assertThrows(SendSMSErrorException.class,
                () -> cliente.getAllMensajesEnviadosDe("422463334"));
    }

    @Test
    @DisplayName("Test 9")
    void SiSolicitaMensajeYNoPoseeTelefonosDebeLanzarEmptyListException() {
        // Arrange
        cliente = new Cliente("Pepe");

        // Act + Asser
        assertThrows(EmptyListException.class,
                () -> cliente.getAllMensajesEnviadosDe("422463334"));
    }

    @Test
    @DisplayName("Test 10")
    void siElClientePoseeUnTelefonoMovilYTieneAsociadoUnMensajeDebeRetornarElPrimerMensaje() throws MessageNotFoundException, EmptyListException, SendSMSErrorException, PhoneNumberNoFoundException {
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        String [] msg = {"969942351", "Hola Mundo", "07/10/2021"};

        // Act
        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(true);
        when(telefono.getMensajeEnviado(1)).thenReturn(msg);

        // Assert
        assertEquals("Hola Mundo", cliente.getMensajeEnviadoDe("969942351",1)[1]);
    }

    @Test
    @DisplayName("Test 11")
    void siElClientePoseeUnTelefonoMovilYNoTieneAsociadoUnMensajeDebeLanzarMessageNotfoundException() {
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        // Act
        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(true);

        // Assert
        assertThrows(MessageNotFoundException.class,
                () -> cliente.getMensajeEnviadoDe("969942351", 1));
    }

    @Test
    @DisplayName("Test 12")
    void SiSolicitaMensajeDeUnTelefonoDadoYNoEsMovilDebeLanzarSendSMSErrorException(){
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        // Act
        when(telefono.getNumero()).thenReturn("969942351");
        when(telefono.isMovil()).thenReturn(false);

        // Assert
        assertThrows(SendSMSErrorException.class,
                () -> cliente.getMensajeEnviadoDe("969942351", 1));
    }

    @Test
    @DisplayName("Test 13")
    void SiSolicitaMensajeDeUnTelefonoQueNoEsDelClienteDebeLanzarPhoneNumberNoFoundException(){
        // Arrange
        cliente = new Cliente("Patricio");
        cliente.addTelefono(telefono);

        when(telefono.getNumero()).thenReturn("958585858");

        // Act + Assert
        assertThrows(PhoneNumberNoFoundException.class,
                () -> cliente.getMensajeEnviadoDe("969942351", 1));
    }

    @Test
    @DisplayName("Test 14")
    void SiSolicitaMensajeEspecificoYNoPoseeTelefonosDebeLanzarEmptyListException(){
        // Arrange
        cliente = new Cliente("Patricio");

        // Act + Assert
        assertThrows(EmptyListException.class,
                () -> cliente.getMensajeEnviadoDe("969942351", 1));
    }
}