package model;

import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int codigoReserva;
    private String fechaRealizacion;
    private String fechaEntrada;
    private String fechaSalida;
    private String estado;
    private List<String> metodosPago;
    private double valorTotal;

    // Relación con Huesped
    private Huesped huesped;

    // Una reserva puede incluir una o más habitaciones
    private List<Habitacion> habitaciones;

    // Servicios utilizados durante la estadía
    private List<ServicioAdicional> serviciosAdicionales;

    /**
     * Constructor de la clase Reserva
     * @param codigoReserva de la Reserva
     * @param fechaRealizacion de la Reserva
     * @param fechaEntrada de la Reserva
     * @param fechaSalida de la Reserva
     * @param estado de la Reserva
     * @param valorTotal de la Reserva
     * @param huesped de la Reserva
     */
    public Reserva(int codigoReserva, String fechaRealizacion, String fechaEntrada, String fechaSalida, String estado, Double valorTotal, Huesped huesped) {
        this.codigoReserva = codigoReserva;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.metodosPago = new ArrayList<>();
        this.valorTotal = valorTotal;
        this.huesped = huesped;
        this.habitaciones = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
    }

    public int getCodigoReserva() {
        return codigoReserva;
    }

    public void setCodigoReserva(int codigoReserva) {
        this.codigoReserva = codigoReserva;
    }

    public String getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(String fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getMetodosPago() {
        return metodosPago;
    }

    public void setMetodosPago(List<String> metodosPago) {
        this.metodosPago = metodosPago;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        this.huesped = huesped;
    }

    public List<Habitacion> getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(List<Habitacion> habitaciones) {
        this.habitaciones = habitaciones;
    }

    public List<ServicioAdicional> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public void setServiciosAdicionales(List<ServicioAdicional> serviciosUtilizados) {
        this.serviciosAdicionales = serviciosUtilizados;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "codigoReserva=" + codigoReserva +
                ", fechaRealizacion='" + fechaRealizacion + '\'' +
                ", fechaEntrada='" + fechaEntrada + '\'' +
                ", fechaSalida='" + fechaSalida + '\'' +
                ", estado='" + estado + '\'' +
                ", metodosPago=" + metodosPago +
                ", valorTotal=" + valorTotal +
                ", huesped=" + huesped +
                ", habitaciones=" + habitaciones +
                ", serviciosAdicionales=" + serviciosAdicionales +
                '}';
    }
}