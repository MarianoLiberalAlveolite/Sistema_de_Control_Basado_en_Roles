/**
 * Clase que representa un usuario en el sistema.
 * Cada usuario tiene un nombre y un rol asociado que determina sus permisos.
 */
public class Usuario {
    private String nombre;
    private String rol;

    /**
     * Constructor para crear un nuevo usuario.
     *
     * @param nombre Nombre del usuario.
     * @param rol Rol asignado al usuario (Administrador, Usuario, Invitado).
     */
    public Usuario(String nombre, String rol) {
        this.nombre = nombre;
        this.rol = rol;
    }

    /**
     * Obtiene el nombre del usuario.
     *
     * @return El nombre del usuario.
     */
    public String getNombre() {return nombre;}

    /**
     * Obtiene el rol del usuario.
     *
     * @return El rol del usuario.
     */
    public String getRol() {return rol;}

    /**
     * Devuelve una representación en formato String del usuario.
     *
     * @return Una cadena con el nombre y el rol del usuario.
     */
    @Override
    public String toString() {
        return "Usuario: Nombre= " + nombre + ", Rol= " + rol + '.';
    }
}
