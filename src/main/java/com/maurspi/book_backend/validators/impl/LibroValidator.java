package com.maurspi.book_backend.validators.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.maurspi.book_backend.dtos.LibroCsvDTO;
import com.maurspi.book_backend.validators.IValidadorCsv;

/**
 * Validador de archivos CSV de libros.
 * 
 * Patrón aplicado: STRATEGY (implementa una interfaz: IValidadorCsv)
 * Principio SOLID: SRP (responsabilidad única: validar, en este caso que traiga datos e isbn.)
 * 
 * @see IValidadorCsv
 */
@Component 
public class LibroValidator  implements IValidadorCsv<LibroCsvDTO>{   
    
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
