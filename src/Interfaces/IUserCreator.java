package Interfaces;

import Excepciones.ClienteYaExistenteException;

public interface IUserCreator {
    void agregarUsuarioAColeccion() throws ClienteYaExistenteException;
}
