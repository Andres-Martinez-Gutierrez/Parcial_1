package model;

public class ServicioAdicional {
    private int codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;


    /**
     * Constructor de la clase ServicioAdicional
     * @param codigo del servicio adicional
     * @param nombre del servicio adicional
     * @param precio del servicio adicional
     * @param descripcion del servicio adicional
     * @param disponible del servicio adicional
     */
    public ServicioAdicional(int codigo, String nombre, double precio, String descripcion, boolean disponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.disponible = disponible;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return "ServicioAdicional{" +
                "codigo=" + codigo +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", disponible=" + disponible +
                '}';
    }
}