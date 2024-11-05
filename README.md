# gestion_hotelera
Trabajo práctico en grupo.  Universidad Tecnológica Nacional, Mar del Plata
UML diagram: https://drive.google.com/file/d/1h1cFOmklX779Gvo-TJU82N4HqMNciffV/view?usp=sharing

Bloques de proyecto:

1)
- Gestión de usuarios: 
Administrar el acceso al sistema para empleados y clientes, métodos de login y logout, métodos para actualizar la contraseña (por defecto la contraseña es DNI, pero después del primer inicio de sesión debe cambiarla y login es email). Después de agregar un nuevo empleado al sistema o crear reservas para un cliente, se crea una contraseña por defecto (número de DNI). La nueva contraseña no puede ser el número del DNI. Después de eliminar un empleado del sistema, el acceso al sistema se bloquea. El cliente puede iniciar sesión en el sistema solo si tiene una reserva activa. Las contraseñas se almacenan en el archivo en forma cifrada.
- Gestion de Hotel:
Agregar un nuevo empleado, eliminar un empleado del sistema, cambiarlo. Agregar/eliminar/editar datos de habitaciones. Costo de reservas. Sólo el Administrador puede manejar los datos de Hotel.
- Gestion de archivos:
  El sistema de almacenamiento de datos está estructurado de la siguiente manera: cuando se inicia el programa, los datos de los archivos se cargan en colecciones. Mientras el programa se está ejecutando, toda la interacción con los datos ocurre en colecciones (agregar/eliminar/editar/controlar duplicados). Una vez que se cierra el programa, todos los datos se envían desde las colecciones a los archivos. Los datos starán disponibles la próxima vez que los inicies.
    
2)
- Gestión de reservas: 
Implementación de lógica de gestión de habitaciones y reservas.
Métodos de check-in, check-out y consulta de disponibilidad de habitaciones. Manejo de excepciones. 
Gestión del almacenamiento de información similar al almacenamiento de datos de los empleados (se utiliza GestiónArchivos).

3)
- Interfaz de usuario:
Desarrollo de una interfaz para la interacción con los usuarios:
Interfaz de consola o interfaz gráfica de usuario (GUI).
Creación de métodos de login, registro y reserva de salas.
Integración de la interfaz de usuario con la lógica de negocio.
Procesar solicitudes de usuarios y mostrar información.
Hay que hacer 3 varios de menu: para admin, recepcionista, cliente. Al usuario se le mostrará el menú que corresponde a su rol de usuario (ADMINISTRADOR, CLIENTE, RECEPCIONISTA). 

Integración general
Al finalizar cada parte:
Reuniones periódicas para discutir el progreso y la integración de componentes.
Un repositorio de código común pero cada uno tiene su propia rama.
Prepare documentación que explique cómo interactúa cada componente con los demás.
