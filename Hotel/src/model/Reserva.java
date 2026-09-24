package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Reserva {
    private int codigoReserva;
    private String fechaRealizacion;
    private String fechaEntrada;
    private String fechaSalida;
    private String estado;
    private String metodoPago;
    private double valorTotal;

    // Relación con Huesped
    private Huesped huesped;

    // Una reserva puede incluir una o más habitaciones
    private List<Habitacion> habitaciones;

    // Servicios utilizados durante la estadía
    private List<ServicioAdicional> serviciosAdicionales;

    /**
     * Constructor de la clase Reserva.
     * @param codigoReserva código de la reserva
     * @param fechaRealizacion fecha en la que se realizó la reserva
     * @param fechaEntrada fecha de entrada al hotel
     * @param fechaSalida fecha de salida del hotel
     * @param estado estado actual de la reserva
     * @param valorTotal valor total de la reserva
     * @param huesped huésped que realiza la reserva
     * @param metodoPago método de pago utilizado
     */
    public Reserva(int codigoReserva, String fechaRealizacion, String fechaEntrada,
                   String fechaSalida, String estado, Double valorTotal,
                   Huesped huesped, String metodoPago) {
        this.codigoReserva = codigoReserva;
        this.fechaRealizacion = fechaRealizacion;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.estado = estado;
        this.metodoPago = metodoPago;
        this.valorTotal = valorTotal;
        this.huesped = huesped;
        this.habitaciones = new ArrayList<>();
        this.serviciosAdicionales = new ArrayList<>();
    }

    public int getCodigoReserva() { return codigoReserva; }
    public void setCodigoReserva(int codigoReserva) { this.codigoReserva = codigoReserva; }
    public String getFechaRealizacion() { return fechaRealizacion; }
    public void setFechaRealizacion(String fechaRealizacion) { this.fechaRealizacion = fechaRealizacion; }
    public String getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(String fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public String getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(String fechaSalida) { this.fechaSalida = fechaSalida; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public Huesped getHuesped() { return huesped; }
    public void setHuesped(Huesped huesped) { this.huesped = huesped; }
    public List<Habitacion> getHabitaciones() { return habitaciones; }
    public void setHabitaciones(List<Habitacion> habitaciones) { this.habitaciones = habitaciones; }
    public List<ServicioAdicional> getServiciosAdicionales() { return serviciosAdicionales; }
    public void setServiciosAdicionales(List<ServicioAdicional> serviciosUtilizados) { this.serviciosAdicionales = serviciosUtilizados; }

    /**
     * Calcula el valor total de la reserva.
     * Suma el precio de cada habitación por el número de noches
     * y agrega el precio de los servicios utilizados.
     * Las fechas deben estar en formato dd/MM/yyyy.
     * @return valor total calculado
     */
    public double calcularValorTotal() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate entrada = LocalDate.parse(fechaEntrada, formato);
        LocalDate salida = LocalDate.parse(fechaSalida, formato);
        long noches = ChronoUnit.DAYS.between(entrada, salida);

        if (noches < 0) {
            throw new IllegalArgumentException("La fecha de salida no puede ser anterior a la fecha de entrada.");
        }

        double totalHabitaciones = 0;
        for (Habitacion habitacion : habitaciones) {
            totalHabitaciones += habitacion.getPrecioPorNoche() * noches;
        }

        double totalServicios = 0;
        for (ServicioAdicional servicio : serviciosAdicionales) {
            totalServicios += servicio.getPrecio();
        }

        valorTotal = totalHabitaciones + totalServicios;
        return valorTotal;
    }

    /**
     * Agrega una habitación a la reserva.
     * Solo se agrega si no es null, está disponible y no estaba agregada.
     * @param habitacion habitación que se desea agregar
     * @return true si fue agregada; false en caso contrario
     */
    public boolean agregarHabitacion(Habitacion habitacion) {
        if (habitacion == null) return false;
        if (!habitacion.getEstado().equalsIgnoreCase("Disponible")) return false;
        if (habitaciones.contains(habitacion)) return false;

        habitaciones.add(habitacion);
        return true;
    }

    /**
     * Agrega un servicio adicional a la reserva.
     * Solo se agrega si el servicio existe y está disponible.
     * @param servicio servicio que se desea agregar
     * @return true si fue agregado; false en caso contrario
     */
    public boolean agregarServicio(ServicioAdicional servicio) {
        if (servicio == null) return false;
        if (!servicio.isDisponible()) return false;

        serviciosAdicionales.add(servicio);
        return true;
    }

    /**
     * Actualiza el estado de la reserva.
     * Estados permitidos: Pendiente, Confirmada, En curso,
     * Finalizada y Cancelada.
     * @param estado nuevo estado de la reserva
     * @return true si el estado es válido; false si no lo es
     */
    public boolean actualizarEstado(String estado) {
        if (estado == null) return false;

        if (estado.equalsIgnoreCase("Pendiente")
                || estado.equalsIgnoreCase("Confirmada")
                || estado.equalsIgnoreCase("En curso")
                || estado.equalsIgnoreCase("Finalizada")
                || estado.equalsIgnoreCase("Cancelada")) {
            this.estado = estado;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "codigoReserva=" + codigoReserva +
                ", fechaRealizacion='" + fechaRealizacion + '\'' +
                ", fechaEntrada='" + fechaEntrada + '\'' +
                ", fechaSalida='" + fechaSalida + '\'' +
                ", estado='" + estado + '\'' +
                ", metodoPago='" + metodoPago + '\'' +
                ", valorTotal=" + valorTotal +
                ", huesped=" + huesped +
                ", habitaciones=" + habitaciones +
                ", serviciosAdicionales=" + serviciosAdicionales +
                '}';
    }
}