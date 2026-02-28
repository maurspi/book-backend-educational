package com.maurspi.book_backend.mappers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.maurspi.book_backend.api.request.LibroRequest;
import com.maurspi.book_backend.dtos.LibroCsvDTO;

@Component
public class LibroMapper implements IMapperCsv<LibroCsvDTO, LibroRequest> {

    @Override
    public List<LibroRequest> mapearADto(List<LibroCsvDTO> entrada) {
        List<LibroRequest> datosSalida =  new ArrayList<>();

        for(LibroCsvDTO csv : entrada){
            //Patrón Builder para armar el objeto limpio
            LibroRequest request = LibroRequest.builder()
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
