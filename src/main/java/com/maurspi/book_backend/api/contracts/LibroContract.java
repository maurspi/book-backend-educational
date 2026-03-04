package com.maurspi.book_backend.api.contracts;


import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.response.CargaMasivaResponse;

@RequestMapping("/api/libros")
public interface LibroContract {

    /**
     * Contrato para la subida de archivos CSV.
     * Define que este endpoint consume 'multipart/form-data' (archivos).
     */
    @PostMapping(value = "/subir", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<CargaMasivaResponse> subir(@RequestParam("file") MultipartFile file);

    @DeleteMapping(value = "/eliminar")
    ResponseEntity<Void>eliminarTodos();
}