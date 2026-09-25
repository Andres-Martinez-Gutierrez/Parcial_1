package model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String nombreComercial;
    private String nit;
    private String direccion;
    private String telefono;
    private String paginaWeb;

    // El hotel administra estos registros
    private List<Huesped> listaHuespedHotel;
    private List<Habitacion> listaHabitacionHotel;
    private List<Reserva> listaReservaHotel;

    /**
     * Constructor de la clase Hotel.
     *
     * @param nombreComercial nombre comercial del hotel
     * @param nit NIT del hotel
     * @param direccion dirección del hotel
     * @param telefono teléfono del hotel
     * @param paginaWeb página web del hotel
     */
    public Hotel(String nombreComercial, String nit, String direccion, String telefono, String paginaWeb) {
        this.nombreComercial = nombreComercial;
        this.nit = nit;
        this.direccion = direccion;
        this.telefono = telefono;
        this.paginaWeb = paginaWeb;
        this.listaHuespedHotel = new ArrayList<>();
        this.listaHabitacionHotel = new ArrayList<>();
        this.listaReservaHotel = new ArrayList<>();
    }

    /**
     * Busca un huésped utilizando su número de teléfono.
     * Recorre la lista de huéspedes y compara el teléfono recibido
     * con el teléfono registrado de cada huésped.
     *
     * @param telefono número de teléfono que se desea buscar
     * @return el huésped encontrado o null si no existe
     */
    public Huesped buscarHuespedPorTelefono(int telefono) {
        for (Huesped huesped : huespedes) {
            if (huesped.getTelefono() == telefono) {
                return huesped;
            }
        }
        return null;
    }

    /**
     * Determina si un número es perfecto.
     * Un número perfecto es igual a la suma de sus divisores propios,
     * sin incluir el mismo número.
     *
     * @param numero número que se desea comprobar
     * @return true si el número es perfecto, false en caso contrario
     */
    public boolean esNumeroPerfecto(int numero) {
        if (numero <= 1) {
            return false;
        }

        int suma = 0;

        for (int i = 1; i <= numero / 2; i++) {
            if (numero % i == 0) {
                suma += i;
            }
        }

        return suma == numero;
    }

    public void registrarHuesped(Huesped huesped) {
        getHuespedes().add(huesped);
    }

    public void registrarhabitacion(Habitacion habitacion) {
        getHabitaciones().add(habitacion);
    }

    /**
     * Calcula los ingresos correspondientes a las reservas realizadas
     * en una fecha determinada.
     * Recorre las reservas, compara la fecha de realización y acumula
     * el valor total de las reservas que coincidan.
     *
     * @param fecha fecha que se desea consultar
     * @return valor total acumulado de las reservas de esa fecha
     */
    public double calcularIngresosPorFecha(String fecha) {
        double total = 0;

        for (Reserva reserva : listaReservaHotel) {
            if (reserva.getFechaRealizacion().equals(fecha)) {
                total += reserva.getValorTotal();
            }
        }

        return total;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPaginaWeb() {
        return paginaWeb;
    }

    public void setPaginaWeb(String paginaWeb) {
        this.paginaWeb = paginaWeb;
    }

    public List<Huesped> getListaHuespedHotel() {
        return listaHuespedHotel;
    }

    public void setListaHuespedHotel(List<Huesped> listaHuespedHotel) {
        this.listaHuespedHotel = listaHuespedHotel;
    }

    public List<Habitacion> getListaHabitacionHotel() {
        return listaHabitacionHotel;
    }

    public void setListaHabitacionHotel(List<Habitacion> listaHabitacionHotel) {
        this.listaHabitacionHotel = listaHabitacionHotel;
    }

    public List<Reserva> getListaReservaHotel() {
        return listaReservaHotel;
    }

    public void setListaReservaHotel(List<Reserva> listaReservaHotel) {
        this.listaReservaHotel = listaReservaHotel;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "nombreComercial='" + nombreComercial + '\'' +
                ", nit=" + nit +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", paginaWeb='" + paginaWeb + '\'' +
                ", huespedes=" + listaHuespedHotel +
                ", habitaciones=" + listaHabitacionHotel +
                ", reservas=" + listaReservaHotel +
                '}';
    }
}
