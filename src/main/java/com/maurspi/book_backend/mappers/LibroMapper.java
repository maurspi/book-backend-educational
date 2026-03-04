package com.maurspi.book_backend.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.maurspi.book_backend.api.request.LibroRequest;
import com.maurspi.book_backend.dtos.LibroCsvDTO;
/*
Patrón Adapter -> En esta clase conecto 2 interfaces incompatibles, para luego mandar LibroRequest al CORE.
*/
@Component
public class LibroMapper implements IMapperCsv<LibroCsvDTO, LibroRequest> {

    @Override
    public List<LibroRequest> mapearADto(List<LibroCsvDTO> entrada) {
        List<LibroRequest> datosSalida =  new ArrayList<>();

        for(LibroCsvDTO csv : entrada){
           
            LibroRequest request = LibroRequest.builder() //-> Patrón Builder para armar el objeto limpio

                .isbn(csv.getIsbn()) //El core luego administra la PK con su id
                .titulo(csv.getTitulo())
                .autor(csv.getAutor())
                .editorial(csv.getEditorial())

                .build();

                datosSalida.add(request);
        }
            return datosSalida;

    }

}
