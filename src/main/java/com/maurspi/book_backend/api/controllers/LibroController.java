package com.maurspi.book_backend.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.contracts.LibroContract;
import com.maurspi.book_backend.api.response.CargaMasivaResponse;
import com.maurspi.book_backend.services.LibroCargaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LibroController implements LibroContract {
    private final LibroCargaService libroCargaService;

    @Override
    public ResponseEntity<CargaMasivaResponse> subir(MultipartFile file) {

        CargaMasivaResponse reporteCarga = libroCargaService.cargar(file);

        return ResponseEntity.ok(reporteCarga);
    }

    @Override
    public ResponseEntity<Void> eliminarTodos() {
        
        libroCargaService.eliminarTodos();
        return ResponseEntity.noContent().build();        
    }

}
