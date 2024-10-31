import Excepciones.EmpleadoNoEncontradoException;
import Gestion.GestionAcceso;
import Gestion.GestionArchivo;
import Gestion.GestionEmpleado;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {

        GestionEmpleado gestionEmpleado = new GestionEmpleado();
        GestionArchivo gestionArchivo = new GestionArchivo(gestionEmpleado);
        GestionAcceso gestionAcceso = new GestionAcceso(gestionEmpleado);

        // cargar empleados desde archivo JSON
        gestionArchivo.cargarEmpleadosDesdeArchivo();
        gestionArchivo.cargarLoginContrasenaDesdeArchivo();

        //gestionEmpleado.cargarEmpleadoAColeccion();

        System.out.println("Verificar login y contrasena: ");
        System.out.println(gestionAcceso.verificarLoginContrasena("hello@gmail.com", "12345"));

        // guardar empleados y login y contrasenas en archivos JSON
        gestionArchivo.guardarEmpleados();
        gestionArchivo.guardarLoginContrasena();
        gestionArchivo.guardarLoginContrasena();

    }
}

