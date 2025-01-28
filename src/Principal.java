import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase principal que gestiona el sistema de control de acceso.
 * Permite crear usuarios, seleccionar usuarios y acciones,
 * y aplicar políticas de seguridad.
 */

public class Principal {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final HashMap<String, Usuario> USUARIOS = new HashMap<>();
    private static final ControlDeAcceso CONTROL = new ControlDeAcceso();

    /**
     * Método principal que inicia el programa.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de control de acceso.");
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerEntero();
            switch (opcion) {
                case 1: crearUsuarios();
                    break;
                case 2: elegirUsuario();
                    break;
                case 3: System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    return;
                default: System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    /**
     * Muestra el menú principal con las opciones disponibles.
     */
    private static void mostrarMenuPrincipal() {
        System.out.println("\nMenú Principal:");
        System.out.println("1. Crear Usuarios");
        System.out.println("2. Elegir Usuario y Acción");
        System.out.println("3. Salir");
        System.out.print("Ingrese su opción: ");
    }

    /**
     * Permite crear múltiples usuarios.
     * Pregunta cuántos usuarios se crearán, solicita nombres y roles,
     * y almacena los usuarios en un HashMap.
     */
    private static void crearUsuarios() {
        System.out.print("¿Cuántos usuarios desea crear? ");
        int cantidad = leerEntero();
        for (int i = 0; i < cantidad; i++) {
            System.out.println("Creando el usuario #" + (i + 1));
            String nombre = ponerNombre();
            String rol = ponerRol();
            Usuario nuevoUsuario = new Usuario(nombre, rol);
            USUARIOS.put(nombre, nuevoUsuario);
            System.out.println("Usuario creado: " + nuevoUsuario);
        }
    }

    /**
     * Solicita al usuario que ingrese un nombre.
     *
     * @return El nombre ingresado por el usuario.
     */
    public static String ponerNombre(){
        System.out.println("Ingrese el nombre del usuario: ");
        return SCANNER.nextLine();
    }

    /**
     * Solicita al usuario que seleccione un rol y retorna el nombre del rol.
     *
     * @return El rol seleccionado (Administrador, Usuario, Invitado).
     */
    public static String ponerRol(){
        System.out.println("Seleccione el rol del usuario:");
        System.out.println("1. Administrador");
        System.out.println("2. Usuario");
        System.out.println("3. Invitado");
        System.out.print("Ingrese el número del rol: ");
        int opcionRol = leerEntero();

        return switch (opcionRol) {
            case 1 -> "Administrador";
            case 2 -> "Usuario";
            case 3 -> "Invitado";
            default -> {
                System.out.println("Rol no válido. Asignando como Invitado por defecto.");
                yield "Invitado";
            }
        };
    }

    /**
     * Permite seleccionar un usuario ya creado del HashMap
     * y realizar acciones basadas en las políticas de seguridad.
     */
    private static void elegirUsuario() {
        if (USUARIOS.isEmpty()) {
            System.out.println("No hay usuarios creados. Cree usuarios primero.");
            return;
        }

        System.out.println("\nUsuarios disponibles:");
        USUARIOS.forEach((nombre, usuario) -> System.out.println("- " + nombre + " (" + usuario.getRol() + ")"));
        System.out.print("Ingrese el nombre del usuario a seleccionar: ");
        String nombre = SCANNER.nextLine();
        Usuario usuarioSeleccionado = USUARIOS.get(nombre);
        if (usuarioSeleccionado == null) {
            System.out.println("Usuario no encontrado. Intenta de nuevo.");
            return;
        }

        elegirAccion(usuarioSeleccionado);
    }

    /**
     * Permite seleccionar una acción específica para un usuario.
     *
     * @param usuarioSeleccionado El usuario seleccionado para realizar la acción.
     */
    private static void elegirAccion(Usuario usuarioSeleccionado) {
        while (true) {
            System.out.println("\nSeleccione una acción:");
            System.out.println("1. Ver registros");
            System.out.println("2. Modificar configuración");
            System.out.println("3. Acceder a información pública");
            System.out.println("4. Regresar al menú principal");

            System.out.print("Ingrese el número de la acción: ");
            int opcionAccion = leerEntero();

            String accion;
            switch (opcionAccion) {
                case 1: accion = "verRegistros";
                    break;
                case 2: accion = "modificarConfiguracion";
                    break;
                case 3: accion = "accederPublico";
                    break;
                case 4: System.out.println("Regresando al menú principal.\n");
                    return;
                default: System.out.println("Opción no válida. Intenta de nuevo.");
                    continue;
            }

            System.out.println("Usuario: " + usuarioSeleccionado.getNombre() + " - Rol: " + usuarioSeleccionado.getRol() +
                    " intenta realizar la acción: " + accion);

            if (CONTROL.verificarPermisos(usuarioSeleccionado.getRol(), accion)) {
                System.out.println("Acceso permitido.\n");
            } else {
                System.out.println("Acceso denegado.\n");
            }
        }
    }

    /**
     * Lee un número entero desde la entrada estándar.
     * Maneja excepciones en caso de que la entrada no sea válida.
     * Asegura que no queden saltos de línea en el buffer.
     *
     * @return El número entero ingresado por el usuario.
     */
    private static int leerEntero() {
        while (true) {
            try {
                int numero = SCANNER.nextInt();
                SCANNER.nextLine(); // Consumir el salto de línea después del número
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
                SCANNER.nextLine(); // Limpiar el buffer
            }
        }
    }
}
