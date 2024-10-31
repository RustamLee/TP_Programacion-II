package Interfaces;
// Realizamos el cifrado a través de la interfaz, porque en el futuro puede ser necesario cifrarlo de diferentes maneras.
// Por ejemplo, cifrar datos sobre empleados que trabajan con información más confidencial requiere más seguridad.

public interface ICifradorContrasena {

    public String cifrarContrasena (String contrasena);
}
