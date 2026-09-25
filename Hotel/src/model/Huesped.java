package model;

import java.util.ArrayList;
import java.util.List;

public class Huesped {
    private String nombreCompleto;
    private String documentoIdentidad;
    private int telefono;
    private String correoElectronico;
    private String paisProcedencia;

    // Relación: un huésped puede tener múltiples reservas
    private List<Reserva> reservas;

    /**
     * Constructor de la clase Huesped
     * @param nombreCompleto del Huesped
     * @param documentoIdentidad del Huesped
     * @param telefono del Huesped
     * @param paisProcedencia del Huesped
     * @param correoElectronico del Huesped
     */
    public Huesped(String nombreCompleto, String documentoIdentidad, int telefono, String paisProcedencia, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.telefono = telefono;
        this.reservas = new ArrayList<>();
        this.paisProcedencia = paisProcedencia;
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDocumentoIdentidad() {
        return documentoIdentidad;
    }

    public void setDocumentoIdentidad(String documentoIdentidad) {
        this.documentoIdentidad = documentoIdentidad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getPaisProcedencia() {
        return paisProcedencia;
    }

    public void setPaisProcedencia(String paisProcedencia) {
        this.paisProcedencia = paisProcedencia;
    }

    @Override
    public String toString() {
        return "Huesped{" +
                "nombreCompleto='" + nombreCompleto + '\'' +
                ", documentoIdentidad=" + documentoIdentidad +
                ", telefono='" + telefono + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                ", paisProcedencia='" + paisProcedencia + '\'' +
                ", reservas=" + reservas +
                '}';
    }
}
