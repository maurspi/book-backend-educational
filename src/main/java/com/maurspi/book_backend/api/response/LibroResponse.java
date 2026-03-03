package com.maurspi.book_backend.api.response;

import lombok.Data;

@Data
public class LibroResponse {
    private String isbn;
    private String autor;
    private String titulo;
}
