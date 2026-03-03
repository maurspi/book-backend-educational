package com.maurspi.book_backend.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.contracts.LibroCargaContract;
import com.maurspi.book_backend.api.response.LibroResponse;
import com.maurspi.book_backend.services.LibroCargaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LibroCargaController implements LibroCargaContract  {
    private final LibroCargaService libroCargaService;

    @Override // Asegúrate de actualizar también tu LibroCargaContract (ver paso 3)
    public ResponseEntity<List<LibroResponse>> subir(MultipartFile file) {
        
        // 1. Recibimos la lista del servicio
        List<LibroResponse> respuestaDelCore = libroCargaService.cargar(file);

        // 2. La metemos en el body del OK (código 200)
        return ResponseEntity.ok(respuestaDelCore);
    }


}
