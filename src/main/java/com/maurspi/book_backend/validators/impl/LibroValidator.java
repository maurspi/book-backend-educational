package com.maurspi.book_backend.validators.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.maurspi.book_backend.dtos.LibroCsvDTO;
import com.maurspi.book_backend.validators.IValidadorCsv;

@Component //Singleton (gestionado por el contenedor de Spring). Solo existirá una instancia de este validador en memoria, optimizando recursos.

public class LibroValidator  implements IValidadorCsv<LibroCsvDTO>{       //Single Responsibility Principle (SRP)

    @Override
    public void validar(List<LibroCsvDTO> datos) {
        
        //1. Primer nivel de validación de integridad.

        if (datos==null || datos.isEmpty()) {
            throw new RuntimeException("Archivo con datos incorrectos");                     //HACER CLASE DE CATALOGO DE ERRRES
        }
        //throw new UnsupportedOperationException("Unimplemented method 'validar'");
    
        //2. Validac de negocio, es exigible el ISBN y Título para permitir la carga del csv
    
        for(LibroCsvDTO libro : datos){
            if (libro.getIsbn().isEmpty()||libro.getIsbn() == null) {
                throw new RuntimeException("ISBN obligatorio");                //HACER CLASE DE CATALOGO DE ERRRES
            }
            if (libro.getTitulo().isEmpty()||libro.getTitulo() == null) {
                throw new RuntimeException("Nombre obligatorio");                //HACER CLASE DE CATALOGO DE ERRRES
            }            
        }
    
    }
        

}
