import Model.accidenteVehicular;
import Model.emergtencia;
import Model.incendio;
import Model.robo;
import java.util.ArrayList;
import java.util.Scanner;
import utils.nivelGravedad;

public class App {
    private static ArrayList<emergtencia> emergencias = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean salir = false;
        
        while (!salir) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    registrarEmergencia();
                    break;
                case 2:
                    listarEmergencias();
                    break;
                case 3:
                    atenderEmergencia();
                    break;
                case 4:
                    finalizarEmergencia();
                    break;
                case 5:
                    mostrarEstadisticas();
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n=== Sistema de Gestión de Emergencias Urbanas ===");
        System.out.println("1. Registrar nueva emergencia");
        System.out.println("2. Listar emergencias");
        System.out.println("3. Atender emergencia");
        System.out.println("4. Finalizar emergencia");
        System.out.println("5. Mostrar estadísticas");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarEmergencia() {
        System.out.println("\n=== Registrar Nueva Emergencia ===");
        System.out.println("Tipos de emergencia disponibles:");
        System.out.println("1. Accidente Vehicular");
        System.out.println("2. Incendio");
        System.out.println("3. Robo");
        System.out.print("Seleccione el tipo de emergencia: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese la ubicación: ");
        String ubicacion = scanner.nextLine();

        System.out.println("Niveles de gravedad:");
        System.out.println("1. BAJA");
        System.out.println("2. MEDIA");
        System.out.println("3. ALTA");
        System.out.print("Seleccione el nivel de gravedad: ");
        int nivel = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el tiempo máximo de respuesta (en minutos): ");
        int tiempoRespuesta = scanner.nextInt();
        scanner.nextLine();

        nivelGravedad gravedad = nivelGravedad.BAJA;
        switch (nivel) {
            case 1:
                gravedad = nivelGravedad.BAJA;
                break;
            case 2:
                gravedad = nivelGravedad.MEDIA;
                break;
            case 3:
                gravedad = nivelGravedad.ALTA;
                break;
        }

        emergtencia nuevaEmergencia = null;
        switch (tipo) {
            case 1:
                nuevaEmergencia = new accidenteVehicular(ubicacion, gravedad, tiempoRespuesta, false);
                break;
            case 2:
                nuevaEmergencia = new incendio(ubicacion, gravedad, tiempoRespuesta, false);
                break;
            case 3:
                nuevaEmergencia = new robo(ubicacion, gravedad, tiempoRespuesta, false);
                break;
        }

        if (nuevaEmergencia != null) {
            emergencias.add(nuevaEmergencia);
            System.out.println("Emergencia registrada exitosamente!");
        } else {
            System.out.println("Error al registrar la emergencia.");
        }
    }

    private static void listarEmergencias() {
        System.out.println("\n=== Lista de Emergencias ===");
        if (emergencias.isEmpty()) {
            System.out.println("No hay emergencias registradas.");
            return;
        }

        for (int i = 0; i < emergencias.size(); i++) {
            emergtencia e = emergencias.get(i);
            System.out.println((i + 1) + ". " + e.toString());
        }
    }

    private static void atenderEmergencia() {
        listarEmergencias();
        if (emergencias.isEmpty()) {
            return;
        }

        System.out.print("Seleccione el número de la emergencia a atender: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < emergencias.size()) {
            emergtencia e = emergencias.get(indice);
            if (!e.isAtendida()) {
                e.tiempoInicioAtencion();
                System.out.println("Emergencia en atención: " + e.getTipo() + " en " + e.getUbicacion());
            } else {
                System.out.println("Esta emergencia ya ha sido atendida.");
            }
        } else {
            System.out.println("Índice no válido.");
        }
    }

    private static void finalizarEmergencia() {
        listarEmergencias();
        if (emergencias.isEmpty()) {
            return;
        }

        System.out.print("Seleccione el número de la emergencia a finalizar: ");
        int indice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (indice >= 0 && indice < emergencias.size()) {
            emergtencia e = emergencias.get(indice);
            if (!e.isAtendida()) {
                e.marcarFinalAtencion();
                System.out.println("Emergencia finalizada. Tiempo de atención: " + 
                                 (e.calcularTiempoAtencion() / 1000) + " segundos");
            } else {
                System.out.println("Esta emergencia ya ha sido finalizada.");
            }
        } else {
            System.out.println("Índice no válido.");
        }
    }

    private static void mostrarEstadisticas() {
        System.out.println("\n=== Estadísticas ===");
        System.out.println("Total de emergencias: " + emergencias.size());
        
        int atendidas = 0;
        int pendientes = 0;
        for (emergtencia e : emergencias) {
            if (e.isAtendida()) {
                atendidas++;
            } else {
                pendientes++;
            }
        }
        
        System.out.println("Emergencias atendidas: " + atendidas);
        System.out.println("Emergencias pendientes: " + pendientes);
    }
}
