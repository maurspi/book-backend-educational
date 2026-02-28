package com.maurspi.book_backend.dtos;

import com.opencsv.bean.CsvBindByName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder              // Para crear objetos fácil en tus tests
@NoArgsConstructor    // 
@AllArgsConstructor   // Necesario para que funcione el @Builder
public class LibroCsvDTO {
    @CsvBindByName(column = "isbn", required=true)
    private String isbn;
    @CsvBindByName(column = "titulo", required=true)
    private String titulo;
    @CsvBindByName(column = "autor", required = true)
    private String autor;
    @CsvBindByName(column = "editorial", required = true)
    private String editorial;
}
