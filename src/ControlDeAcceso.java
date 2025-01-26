import java.util.HashMap;
import java.util.List;

/**
 * Clase que gestiona las políticas de control de acceso en el sistema.
 * Define qué acciones están permitidas para cada rol y verifica permisos.
 */

public class ControlDeAcceso {
    private HashMap<String, List<String>> politicas;
    /**
     * Constructor que inicializa el sistema de control de acceso.
     */
    public ControlDeAcceso() {
        politicas = new HashMap<>();
        inicializarPoliticas();
    }
    /**
     * Inicializa las políticas de acceso con roles predeterminados y sus permisos.
     */
    private void inicializarPoliticas() {
        politicas.put("Administrador", List.of("verRegistros", "modificarConfiguracion", "accederPublico"));
        politicas.put("Usuario", List.of("verRegistros", "accederPublico"));
        politicas.put("Invitado", List.of("accederPublico"));
    }
    /**
     * Verifica si un rol tiene permiso para realizar una acción específica.
     *
     * @param rol Rol del usuario.
     * @param accion Acción que se desea realizar.
     * @return true si el rol tiene permiso, false en caso contrario.
     */
    public boolean verificarPermisos(String rol, String accion) {
        List<String> permisos = politicas.get(rol);
        if (permisos != null && permisos.contains(accion)) {
            return true;
        }
        return false;
    }
}
