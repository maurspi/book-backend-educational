package com.maurspi.book_backend.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.contracts.LibroCargaContract;
import com.maurspi.book_backend.services.LibroCargaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LibroCargaController implements LibroCargaContract  {
    private final LibroCargaService libroCargaService;

    @Override
    public ResponseEntity<String> subir(MultipartFile file) {
        libroCargaService.cargar(file);

        return ResponseEntity.ok("Archivo procesado correctamente.");
    }


}
