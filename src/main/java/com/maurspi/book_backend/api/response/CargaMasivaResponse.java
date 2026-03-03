package com.maurspi.book_backend.api.response;

import java.util.List;

import lombok.Data;

@Data
public class CargaMasivaResponse {
    private List<LibroResponse> insertados;
    private List<LibroResponse> existentes;

    private String mensaje;
}
