import Excepciones.ClienteNoEncontradoException;
import Excepciones.ClienteYaExistenteException;
import Excepciones.EmpleadoNoEncontradoException;
import Gestion.GestionAcceso;
import Gestion.GestionArchivo;
import Gestion.GestionCliente;
import Gestion.GestionEmpleado;
import Modelo.Menu;
import Modelo.Usuario;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, EmpleadoNoEncontradoException, ClienteNoEncontradoException, ClienteYaExistenteException {

        GestionEmpleado gestionEmpleado = new GestionEmpleado();
        GestionCliente gestionCliente = new GestionCliente();
        GestionAcceso gestionAcceso = new GestionAcceso(gestionEmpleado, gestionCliente);
        GestionArchivo gestionArchivo = new GestionArchivo(gestionAcceso);
        Menu menu = new Menu(gestionAcceso);

        // cargar empleados desde archivo JSON
        gestionArchivo.cargarEmpleadosDesdeArchivo();
        gestionArchivo.cargarLoginContrasenaDesdeArchivo();

        menu.start();

        // guardar empleados y login y contrasenas en archivos JSON
        gestionArchivo.guardarEmpleados();
        gestionArchivo.guardarLoginContrasena();
    }
}

