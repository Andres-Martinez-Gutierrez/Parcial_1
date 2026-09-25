import model.*;

import javax.swing.*;


public class Main {

    static Hotel hotel;

    /**
     * Punto de entrada principal de la aplicación.
     * Inicializa la instancia del hotel, carga los datos de prueba y gestiona el ciclo del menú.
     */
    public static void main(String[] args) {
        hotel = new Hotel(
                "StayPlus",
                "1254899862-1",
                "Cra. 11 # 5-37, Circasia, Quindío",
                "6067583068",
                "www.stayplus.co"
        );

        int opcionPrincipal = 0;
        while (opcionPrincipal != 8) {
            try {
                String entrada = opcionesMenuPrincipal();
                if (entrada == null){
                    break;
                }
                opcionPrincipal = Integer.parseInt(entrada);
                if (opcionPrincipal == 1) registrarHuesped();
                else if (opcionPrincipal == 2) buscarHuesped();
                else if (opcionPrincipal == 3) registrarReserva();
                else if (opcionPrincipal == 4) gestionarReserva();
                else if (opcionPrincipal == 5) registrarHabitacion();
                else if (opcionPrincipal == 6) registrarServicio();
                else if (opcionPrincipal == 7) consultarIngresos();
                else if (opcionPrincipal == 8) JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                else JOptionPane.showMessageDialog(null, "Opción no válida.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Despliega la ventana emergente con el menú principal de opciones del sistema.
     *
     * @return Cadena con la opción elegida por el usuario o null si se cancela la ventana.
     */
    private static String opcionesMenuPrincipal() {
        return JOptionPane.showInputDialog(null, """
                ========== MENÚ PRINCIPAL ==========
                1. Registrar huésped
                2. Buscar huésped por teléfono
                3. Registrar reserva
                4. Gestionar reserva
                5. Registrar habitación
                6. Registrar servicio adicional
                7. Consultar ingresos por fecha
                8. Salir
                Seleccione una opción:
                """);
    }

    // 1. Registrar huésped
    private static void registrarHuesped() {
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre completo del huésped:");
        String documento = JOptionPane.showInputDialog(null, "Ingrese el documento de identidad:");
        int telefono =  Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el teléfono:"));
        String correo = JOptionPane.showInputDialog(null, "Ingrese el correo electrónico:");
        String pais = JOptionPane.showInputDialog(null, "Ingrese el país de procedencia:");
        if (nombre == null || documento == null || telefono == 0 || correo == null || pais == null) return;

        Huesped huesped = new Huesped(nombre, documento, telefono, correo, pais);
        hotel.registrarHuesped(huesped);
        JOptionPane.showMessageDialog(null, "✓ Huésped " + nombre + " registrado exitosamente.");
    }

    /**
     * Solicita un número de teléfono y busca la información del huésped registrado asociado.
     */
    private static void buscarHuesped() {
        int telefono =  Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de teléfono del huésped:"));
        if (!hotel.esNumeroPerfecto(telefono)) return;
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped != null) JOptionPane.showMessageDialog(null, "✓ Huésped encontrado:\n\n" + huesped);
        else JOptionPane.showMessageDialog(null, "✗ No existe un huésped con el teléfono: " + telefono);
    }

    /**
     * Recopila los datos de una reserva y la vincula con un huésped previamente existente.
     */
    private static void registrarReserva() {
        if (hotel.getListaHuespedHotel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay huéspedes registrados.\nPrimero debe registrar un huésped.");
            return;
        }
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el código de la reserva:"));
        String fechaRealizacion = JOptionPane.showInputDialog(null, "Ingrese la fecha de realización (dd/MM/yyyy):");
        String fechaEntrada = JOptionPane.showInputDialog(null, "Ingrese la fecha de entrada (dd/MM/yyyy):");
        String fechaSalida = JOptionPane.showInputDialog(null, "Ingrese la fecha de salida (dd/MM/yyyy):");
        String metodoPago = JOptionPane.showInputDialog(null, "Ingrese el método de pago:\n1. Tarjeta de crédito\n2. Transferencia bancaria\n3. Efectivo");
        if (metodoPago == null) return;
        if (metodoPago.equals("1")) metodoPago = "Tarjeta de crédito";
        else if (metodoPago.equals("2")) metodoPago = "Transferencia bancaria";
        else if (metodoPago.equals("3")) metodoPago = "Efectivo";
        else {
            JOptionPane.showMessageDialog(null, "Método de pago no válido.");
            return;
        }

        int telefono =  Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el teléfono del huésped que realiza la reserva:\n\n" + obtenerListaHuespedes()));
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            JOptionPane.showMessageDialog(null, "✗ No se encontró el huésped.");
            return;
        }

        Reserva reserva = new Reserva(codigo, fechaRealizacion, fechaEntrada, fechaSalida, "Pendiente", 0.0, huesped, metodoPago);
        hotel.getListaReservaHotel().add(reserva);
        JOptionPane.showMessageDialog(null, "✓ Reserva creada exitosamente.\n\nCódigo: " + codigo + "\nEstado: Pendiente");
    }

    /**
     * Muestra el submenú que permite modificar, calcular o consultar el estado de una reserva.
     */
    private static void gestionarReserva() {
        if (hotel.getListaReservaHotel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay reservas registradas.");
            return;
        }
        int opcion = 0;
        while (opcion != 5) {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, """
                    ===== GESTIÓN DE RESERVAS =====
                    1. Agregar habitación
                    2. Agregar servicio adicional
                    3. Calcular valor total
                    4. Actualizar estado
                    5. Volver al menú principal
                    Seleccione una opción:
                    """));
            if (opcion == 1) agregarHabitacionAReserva();
            else if (opcion == 2) agregarServicioAReserva();
            else if (opcion == 3) calcularTotalReserva();
            else if (opcion == 4) actualizarEstadoReserva();
            else if (opcion != 5) JOptionPane.showMessageDialog(null, "Opción no válida.");
        }
    }

    /**
     * Asigna una habitación disponible registrada en el hotel a la reserva seleccionada.
     */
    private static void agregarHabitacionAReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        if (hotel.getListaHabitacionHotel().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay habitaciones registradas.");
            return;
        }
        int numero = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de habitación:"));
        Habitacion encontrada = null;
        for (Habitacion huesped : hotel.getListaHabitacionHotel())
            if (huesped.getNumero() == numero) {
                encontrada = huesped;
                break;
            }
        if (encontrada == null) {
            JOptionPane.showMessageDialog(null, "✗ Habitación no encontrada.");
            return;
        }
        boolean agregada = reserva.agregarHabitacion(encontrada);
        JOptionPane.showMessageDialog(null, agregada ? "✓ Habitación agregada a la reserva." : "✗ No fue posible agregarla. Verifique que esté disponible y no repetida.");
    }

    /**
     * Asigna un servicio adicional disponible de la lista del hotel a la reserva seleccionada.
     */
    private static void agregarServicioAReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        if (reserva.getListaServiciAdicional().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay servicios registrados.");
            return;
        }
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el código del servicio:\n\n" + obtenerListaServicios()));
        ServicioAdicional encontrado = null;
        for (ServicioAdicional adicional : reserva.getListaServiciAdicional())
            if (adicional.getCodigo() == codigo) {
                encontrado = adicional;
                break;
            }
        if (encontrado == null) {
            JOptionPane.showMessageDialog(null, "✗ Servicio no encontrado.");
            return;
        }
        boolean agregado = reserva.agregarServicio(encontrado);
        JOptionPane.showMessageDialog(null, agregado ? "✓ Servicio agregado a la reserva." : "✗ No fue posible agregar el servicio. Verifique que esté disponible.");
    }

    /**
     * Invoca el cálculo del costo total de una reserva según sus noches de estadía y servicios.
     */
    private static void calcularTotalReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        try {
            double total = reserva.calcularValorTotal();
            JOptionPane.showMessageDialog(null, "════════════════════════════════\nVALOR TOTAL DE LA RESERVA\n════════════════════════════════\n\nReserva: " + reserva.getCodigoReserva() + "\nValor total: $" + total);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "✗ Error al calcular. Verifique que las fechas tengan formato dd/MM/yyyy.");
        }
    }

    /**
     * Actualiza el estado actual de la reserva seleccionada.
     */
    private static void actualizarEstadoReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        String opcion = JOptionPane.showInputDialog(null, "Ingrese el nuevo estado:\n1. Pendiente\n2. Confirmada\n3. En curso\n4. Finalizada\n5. Cancelada");
        if (opcion == null) return;
        String estado;
        if (opcion.equals("1")) estado = "Pendiente";
        else if (opcion.equals("2")) estado = "Confirmada";
        else if (opcion.equals("3")) estado = "En curso";
        else if (opcion.equals("4")) estado = "Finalizada";
        else if (opcion.equals("5")) estado = "Cancelada";
        else {
            JOptionPane.showMessageDialog(null, "✗ Estado no válido.");
            return;
        }
        boolean actualizado = reserva.actualizarEstado(estado);
        JOptionPane.showMessageDialog(null, actualizado ? "✓ Estado actualizado.\nNuevo estado: " + estado : "✗ No se pudo actualizar.");
    }

    /**
     * Solicita los atributos requeridos y registra una nueva habitación en el hotel.
     */
    private static void registrarHabitacion() {
        int numero = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de habitación:"));
        int piso = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el piso:"));
        String tipo = JOptionPane.showInputDialog(null, "Ingrese el tipo:\n1. Individual\n2. Doble\n3. Suite");
        if (tipo.equals("1")) tipo = "Individual";
        else if (tipo.equals("2")) tipo = "Doble";
        else if (tipo.equals("3")) tipo = "Suite";
        else {
            JOptionPane.showMessageDialog(null, "Tipo no válido.");
            return;
        }
        int capacidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese la capacidad máxima:"));
        double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio por noche:"));
        Habitacion habitacion = new Habitacion(numero, piso, tipo, capacidad, precio, "Disponible");
        hotel.registrarhabitacion(habitacion);
        JOptionPane.showMessageDialog(null, "✓ Habitación " + numero + " registrada exitosamente.");
    }

    /**
     * Solicita los atributos requeridos y registra un nuevo servicio adicional en el hotel.
     */
    private static void registrarServicio() {
        Reserva reserva = seleccionarReserva();
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el código del servicio:"));
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del servicio:");
        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción del servicio:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio del servicio:"));
        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, precio, descripcion, true);
        assert reserva != null;
        reserva.getListaServiciAdicional().add(servicio);
        JOptionPane.showMessageDialog(null, "✓ Servicio '" + nombre + "' registrado exitosamente.");
    }

    /**
     * Consulta y despliega la suma de los ingresos acumulados para las reservas en una fecha dada.
     */
    private static void consultarIngresos() {
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de realización (dd/MM/yyyy):");
        if (fecha == null || fecha.isEmpty()) return;
        double ingresos = hotel.calcularIngresosPorFecha(fecha);
        JOptionPane.showMessageDialog(null, "════════════════════════════════\nINGRESOS POR FECHA\n════════════════════════════════\n\nFecha: " + fecha + "\nIngresos: $" + ingresos);
    }

    /**
     * Muestra las reservas registradas y retorna el objeto {@link Reserva} seleccionado según su código.
     *
     * @return La reserva hallada o null si no existe.
     */
    private static Reserva seleccionarReserva() {
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, obtenerListaReservas() + "\nIngrese el código de la reserva:"));
        for (Reserva reserva : hotel.getListaReservaHotel()) if (reserva.getCodigoReserva() == codigo) return reserva;
        JOptionPane.showMessageDialog(null, "✗ Reserva no encontrada.");
        return null;
    }

    /**
     * Construye un listado descriptivo con los códigos y estados de las reservas registradas.
     *
     * @return Cadena formateada para su visualización.
     */
    private static String obtenerListaReservas() {
        StringBuilder lista = new StringBuilder("RESERVAS REGISTRADAS\n====================\n");
        for (Reserva reserva : hotel.getListaReservaHotel())
            lista.append("Código: ").append(reserva.getCodigoReserva()).append(" | Estado: ").append(reserva.getEstado()).append("\n");
        return lista.toString();
    }

    /**
     * Construye un listado descriptivo con los teléfonos de los huéspedes registrados.
     *
     * @return Cadena formateada para su visualización.
     */
    private static String obtenerListaHuespedes() {
        StringBuilder lista = new StringBuilder("HUÉSPEDES REGISTRADOS\n=====================\n");
        for (Huesped huesped : hotel.getListaHuespedHotel()) lista.append("- ").append(huesped.getTelefono()).append("\n");
        return lista.toString();
    }

    /**
     * Construye un listado descriptivo con los códigos y nombres de los servicios registrados.
     *
     * @return Cadena formateada para su visualización.
     */
    private static String obtenerListaServicios() {
        Reserva reserva = seleccionarReserva();
        StringBuilder lista = new StringBuilder("SERVICIOS REGISTRADOS\n=====================\n");
        assert reserva != null;
        for (ServicioAdicional adicional : reserva.getListaServiciAdicional())
            lista.append("Código: ").append(adicional.getCodigo()).append(" | ").append(adicional.getNombre()).append("\n");
        return lista.toString();
    }
}