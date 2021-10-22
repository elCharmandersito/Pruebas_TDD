package cl.ubb.tddDp.Models;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Mensaje {

    private String texto;
    private LocalDate fecha;
    private String numFonoDest;

    public Mensaje(String texto, String numFonoDest) {
        this.texto = texto;
        this.numFonoDest = numFonoDest;
    }

    public String getTexto() {
        return texto;
    }

    public Date getFecha() {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.of(fecha.getYear(), fecha.getMonthValue(), fecha.getDayOfMonth());
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        return date;
    }

    public String getNumFonoDest() {
        return numFonoDest;
    }
}
