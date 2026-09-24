import model.*;

import javax.swing.*;

public class Main {

    static Hotel hotel;

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
                String entrada = opcionesMenu();
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

    private static String opcionesMenu() {
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
        String telefono = JOptionPane.showInputDialog(null, "Ingrese el teléfono:");
        String correo = JOptionPane.showInputDialog(null, "Ingrese el correo electrónico:");
        String pais = JOptionPane.showInputDialog(null, "Ingrese el país de procedencia:");
        if (nombre == null || documento == null || telefono == null || correo == null || pais == null) return;

        Huesped huesped = new Huesped(nombre, documento, telefono, correo, pais);
        hotel.getHuespedes().add(huesped);
        JOptionPane.showMessageDialog(null, "✓ Huésped " + nombre + " registrado exitosamente.");
    }

    // 2. Buscar huésped por teléfono
    private static void buscarHuesped() {
        String telefono = JOptionPane.showInputDialog(null, "Ingrese el número de teléfono del huésped:");
        if (telefono == null || telefono.isEmpty()) return;
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped != null) JOptionPane.showMessageDialog(null, "✓ Huésped encontrado:\n\n" + huesped);
        else JOptionPane.showMessageDialog(null, "✗ No existe un huésped con el teléfono: " + telefono);
    }

    // 3. Registrar reserva
    private static void registrarReserva() {
        if (hotel.getHuespedes().isEmpty()) {
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

        String telefono = JOptionPane.showInputDialog(null, "Ingrese el teléfono del huésped que realiza la reserva:\n\n" + obtenerListaHuespedes());
        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono);
        if (huesped == null) {
            JOptionPane.showMessageDialog(null, "✗ No se encontró el huésped.");
            return;
        }

        Reserva reserva = new Reserva(codigo, fechaRealizacion, fechaEntrada, fechaSalida, "Pendiente", 0.0, huesped, metodoPago);
        hotel.getReservas().add(reserva);
        JOptionPane.showMessageDialog(null, "✓ Reserva creada exitosamente.\n\nCódigo: " + codigo + "\nEstado: Pendiente");
    }

    // 4. Gestionar reserva
    private static void gestionarReserva() {
        if (hotel.getReservas().isEmpty()) {
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

    // 4.1 Agregar habitación
    private static void agregarHabitacionAReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        if (hotel.getHabitaciones().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay habitaciones registradas.");
            return;
        }
        int numero = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el número de habitación:"));
        Habitacion encontrada = null;
        for (Habitacion h : hotel.getHabitaciones())
            if (h.getNumero() == numero) {
                encontrada = h;
                break;
            }
        if (encontrada == null) {
            JOptionPane.showMessageDialog(null, "✗ Habitación no encontrada.");
            return;
        }
        boolean agregada = reserva.agregarHabitacion(encontrada);
        JOptionPane.showMessageDialog(null, agregada ? "✓ Habitación agregada a la reserva." : "✗ No fue posible agregarla. Verifique que esté disponible y no repetida.");
    }

    // 4.2 Agregar servicio
    private static void agregarServicioAReserva() {
        Reserva reserva = seleccionarReserva();
        if (reserva == null) return;
        if (hotel.getServiciosAdicionales().isEmpty()) {
            JOptionPane.showMessageDialog(null, "✗ No hay servicios registrados.");
            return;
        }
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el código del servicio:\n\n" + obtenerListaServicios()));
        ServicioAdicional encontrado = null;
        for (ServicioAdicional s : hotel.getServiciosAdicionales())
            if (s.getCodigo() == codigo) {
                encontrado = s;
                break;
            }
        if (encontrado == null) {
            JOptionPane.showMessageDialog(null, "✗ Servicio no encontrado.");
            return;
        }
        boolean agregado = reserva.agregarServicio(encontrado);
        JOptionPane.showMessageDialog(null, agregado ? "✓ Servicio agregado a la reserva." : "✗ No fue posible agregar el servicio. Verifique que esté disponible.");
    }

    // 4.3 Calcular valor total
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

    // 4.4 Actualizar estado
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

    // 5. Registrar habitación
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
        hotel.getHabitaciones().add(habitacion);
        JOptionPane.showMessageDialog(null, "✓ Habitación " + numero + " registrada exitosamente.");
    }

    // 6. Registrar servicio
    private static void registrarServicio() {
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el código del servicio:"));
        String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del servicio:");
        String descripcion = JOptionPane.showInputDialog(null, "Ingrese la descripción del servicio:");
        double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio del servicio:"));
        ServicioAdicional servicio = new ServicioAdicional(codigo, nombre, precio, descripcion, true);
        hotel.getServiciosAdicionales().add(servicio);
        JOptionPane.showMessageDialog(null, "✓ Servicio '" + nombre + "' registrado exitosamente.");
    }

    // 7. Consultar ingresos por fecha
    private static void consultarIngresos() {
        String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha de realización (dd/MM/yyyy):");
        if (fecha == null || fecha.isEmpty()) return;
        double ingresos = hotel.calcularIngresosPorFecha(fecha);
        JOptionPane.showMessageDialog(null, "════════════════════════════════\nINGRESOS POR FECHA\n════════════════════════════════\n\nFecha: " + fecha + "\nIngresos: $" + ingresos);
    }

    private static Reserva seleccionarReserva() {
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, obtenerListaReservas() + "\nIngrese el código de la reserva:"));
        for (Reserva r : hotel.getReservas()) if (r.getCodigoReserva() == codigo) return r;
        JOptionPane.showMessageDialog(null, "✗ Reserva no encontrada.");
        return null;
    }

    private static String obtenerListaReservas() {
        StringBuilder lista = new StringBuilder("RESERVAS REGISTRADAS\n====================\n");
        for (Reserva r : hotel.getReservas())
            lista.append("Código: ").append(r.getCodigoReserva()).append(" | Estado: ").append(r.getEstado()).append("\n");
        return lista.toString();
    }

    private static String obtenerListaHuespedes() {
        StringBuilder lista = new StringBuilder("HUÉSPEDES REGISTRADOS\n=====================\n");
        for (Huesped h : hotel.getHuespedes()) lista.append("- ").append(h.getTelefono()).append("\n");
        return lista.toString();
    }

    private static String obtenerListaServicios() {
        StringBuilder lista = new StringBuilder("SERVICIOS REGISTRADOS\n=====================\n");
        for (ServicioAdicional s : hotel.getServiciosAdicionales())
            lista.append("Código: ").append(s.getCodigo()).append(" | ").append(s.getNombre()).append("\n");
        return lista.toString();
    }
}