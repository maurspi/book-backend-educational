package com.maurspi.book_backend.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequest {
    private String isbn;
    private String titulo;
    private String autor;
    private String editorial;
}
