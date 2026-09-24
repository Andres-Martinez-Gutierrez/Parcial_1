import javax.swing.JOptionPane;
import model.Habitacion;
import model.Hotel;
import model.Huesped;
import model.Reserva;
import model.ServicioAdicional;

public class Main {

    public static void main(String[] args) {
        // 1. Inicialización del Hotel y datos de prueba
        Hotel hotel = new Hotel("Hotel Gran Plaza", "900123456", "Calle 10 #15-20", "3001234567", "www.hotelgranplaza.com");
        cargarDatosPrueba(hotel);

        int opcion = 0;

        // 2. Menú interactivo con JOptionPane
        do {
            String menu = "========== MENÚ SISTEMA HOTELERO ==========\n\n" +
                    "1. Buscar huésped por teléfono\n" +
                    "2. Verificar si un número es perfecto\n" +
                    "3. Calcular ingresos por fecha\n" +
                    "4. Mostrar detalle de reservas\n" +
                    "5. Salir\n\n" +
                    "Seleccione una opción (1-5):";

            String entrada = JOptionPane.showInputDialog(null, menu, "Gestión Hotelera", JOptionPane.QUESTION_MESSAGE);

            // Si el usuario presiona "Cancelar" o cierra la ventana emergente
            if (entrada == null) {
                opcion = 5;
                break;
            }

            try {
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingrese un número entero válido.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            switch (opcion) {
                case 1:
                    String telefono = JOptionPane.showInputDialog(null, "Ingrese el número de teléfono a buscar:", "Buscar Huésped", JOptionPane.QUESTION_MESSAGE);
                    if (telefono != null && !telefono.trim().isEmpty()) {
                        Huesped huesped = hotel.buscarHuespedPorTelefono(telefono.trim());
                        if (huesped != null) {
                            String infoHuesped = "Huésped encontrado:\n\n" +
                                    "Nombre: " + huesped.getNombreCompleto() + "\n" +
                                    "Documento: " + huesped.getDocumentoIdentidad() + "\n" +
                                    "País: " + huesped.getPaisProcedencia() + "\n" +
                                    "Correo: " + huesped.getCorreoElectronico();
                            JOptionPane.showMessageDialog(null, infoHuesped, "Huésped Encontrado", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ningún huésped con el teléfono: " + telefono, "Sin Resultados", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    break;

                case 2:
                    String numTexto = JOptionPane.showInputDialog(null, "Ingrese un número entero para evaluar:", "Número Perfecto", JOptionPane.QUESTION_MESSAGE);
                    if (numTexto != null) {
                        try {
                            int num = Integer.parseInt(numTexto.trim());
                            boolean esPerfecto = hotel.esNumeroPerfecto(num);
                            String respuesta = "El número " + num + (esPerfecto ? " SÍ es un número perfecto." : " NO es un número perfecto.");
                            JOptionPane.showMessageDialog(null, respuesta, "Resultado Número Perfecto", JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Debe ingresar un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;

                case 3:
                    String fecha = JOptionPane.showInputDialog(null, "Ingrese la fecha a consultar (dd/MM/yyyy):", "Consulta de Ingresos", JOptionPane.QUESTION_MESSAGE);
                    if (fecha != null && !fecha.trim().isEmpty()) {
                        double ingresos = hotel.calcularIngresosPorFecha(fecha.trim());
                        JOptionPane.showMessageDialog(null, "Ingresos totales para la fecha (" + fecha + "): $" + ingresos, "Reporte de Ingresos", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case 4:
                    if (hotel.getReservas().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "No hay reservas registradas en el sistema.", "Reservas", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        StringBuilder reporte = new StringBuilder("--- DETALLE DE RESERVAS ---\n\n");
                        for (Reserva r : hotel.getReservas()) {
                            double totalCalculado = r.calcularValorTotal();
                            reporte.append("Código Reserva: ").append(r.getCodigoReserva()).append("\n")
                                    .append("Cliente: ").append(r.getHuesped().getNombreCompleto()).append("\n")
                                    .append("Estado: ").append(r.getEstado()).append("\n")
                                    .append("Total Calculado: $").append(totalCalculado).append("\n")
                                    .append("-----------------------------------\n");
                        }
                        JOptionPane.showMessageDialog(null, reporte.toString(), "Reservas Activas", JOptionPane.INFORMATION_MESSAGE);
                    }
                    break;

                case 5:
                    JOptionPane.showMessageDialog(null, "Cerrando el sistema hotelero...", "Salida", JOptionPane.INFORMATION_MESSAGE);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Ingrese un número entre 1 y 5.", "Error de Opción", JOptionPane.WARNING_MESSAGE);
            }

        } while (opcion != 5);
    }

    /**
     * Carga objetos iniciales en el hotel.
     */
    private static void cargarDatosPrueba(Hotel hotel) {
        Huesped h1 = new Huesped("Carlos Pérez", 10123456, "3109876543", "Colombia", "carlos@mail.com");
        Huesped h2 = new Huesped("María Gómez", 10987654, "3201112233", "Argentina", "maria@mail.com");
        hotel.getHuespedes().add(h1);
        hotel.getHuespedes().add(h2);

        Habitacion hab101 = new Habitacion(101, 1, "Sencilla", 1, 120000.0, "Disponible");
        Habitacion hab102 = new Habitacion(102, 1, "Doble", 2, 200000.0, "Disponible");
        hotel.getHabitaciones().add(hab101);
        hotel.getHabitaciones().add(hab102);

        ServicioAdicional s1 = new ServicioAdicional(1, "Desayuno Buffet", 25000.0, "Desayuno completo", true);
        ServicioAdicional s2 = new ServicioAdicional(2, "Spa y Masaje", 80000.0, "Sesión de spa de 1 hora", true);
        hotel.getServiciosAdicionales().add(s1);
        hotel.getServiciosAdicionales().add(s2);

        Reserva r1 = new Reserva(1001, "25/03/2026", "25/03/2026", "27/03/2026", "Confirmada", 0.0, h1, "Tarjeta");
        r1.agregarHabitacion(hab101);
        r1.agregarServicio(s1);
        r1.calcularValorTotal();

        Reserva r2 = new Reserva(1002, "25/03/2026", "28/03/2026", "30/03/2026", "Pendiente", 0.0, h2, "Efectivo");
        r2.agregarHabitacion(hab102);
        r2.agregarServicio(s2);
        r2.calcularValorTotal();

        hotel.getReservas().add(r1);
        hotel.getReservas().add(r2);
        h1.getReservas().add(r1);
        h2.getReservas().add(r2);
    }
}