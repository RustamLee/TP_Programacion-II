# Hotel Management System

UTN (Universidad Tecnológica Nacional), Mar del Plata

UML diagram: https://drive.google.com/file/d/1h1cFOmklX779Gvo-TJU82N4HqMNciffV/view?usp=sharing

This project is a comprehensive hotel management system designed to manage various aspects of hotel operations, including employee and client management, room reservations, and access control. The system is built using Java and utilizes object-oriented programming (OOP) principles to ensure modularity and maintainability.

## Key Features

- **Employee and Client Management:** Allows for the creation, modification, and deletion of employee and client records. Employees are categorized into roles such as administrators and receptionists, while clients are managed separately.
- **Room Reservations:** Clients can reserve rooms based on availability. Rooms are categorized by size and type, and their status can be dynamically updated.
- **Access Control:** A login system ensures that only authorized personnel can access specific features based on their roles.
- **Data Persistence:** Data is stored in files, ensuring persistence across application sessions.

## Technical Details

- **Programming Language:** Java
- **Libraries Used:** Gson for JSON serialization and deserialization
- **Design Patterns:** OOP principles are extensively used, with the potential for future integration of design patterns like Singleton or Factory.
- **Version Control:** GitHub for collaborative development and version control.

## Project Structure

- **Data Models:** Core entities like `Cliente`, `Empleado`, `Habitacion`, `Reserva`, and `HistoriaReservas`.
- **Managers:** Classes like `GestorEmpleados`, `GestorClientes`, `GestorHabitaciones`, `GestorReservas`, and `GestorAccesos` handle data management and operations.
- **Access System:** Managed by the `GestorAccesos` class for login and access control functionality.
- **Menu and Interface:** The `Menu` class implements the user interface for console interaction.

## Challenges and Solutions

One of the main challenges was managing data persistence without overloading the system with serialization and deserialization. The solution was to load all data into memory at startup, update it during runtime, and save it back to JSON files at the end of the program. This approach mitigates the frequency of serialization but introduces risks such as data loss in case of system failure. For a small educational project, this solution is acceptable, but in real-world scenarios, periodic autosaving, backup creation, or database integration could address these issues.

## Successful Implementations

The role-based access control system is a key feature. Upon user login, the system determines the user’s role and displays a menu corresponding to their role, ensuring that users only have access to features relevant to them. This improves both security and usability.

## Team Effort

This project was developed by a team of three students. The tasks were divided, and code was reviewed and integrated through GitHub.
