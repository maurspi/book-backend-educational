package com.maurspi.book_backend.validators;

import java.util.List;

public interface IValidadorCsv <T>{ //STRATEGY - En esta interfaz defino las estrategia de validac.
                                    // Principio OCP Open/Close principle: se puede agregar mas firmas sin alterar el resto
                                    // Desacoplamiento: No hay dependencia direct entre el orquestador y la logica de reglas 
    void validar(List<T> datos);
}
