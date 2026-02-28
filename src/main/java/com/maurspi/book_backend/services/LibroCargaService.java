package com.maurspi.book_backend.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.request.LibroRequest;
import com.maurspi.book_backend.dtos.LibroCsvDTO;
import com.maurspi.book_backend.integration.CoreBookClient;
import com.maurspi.book_backend.mappers.LibroMapper;
import com.maurspi.book_backend.utils.ExcelUtils;
import com.maurspi.book_backend.validators.impl.LibroValidator;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor // 2. Lombok genera el constructor con los argumentos 'final'
@Service
public class LibroCargaService {

    private final LibroValidator libroValidator;
    private final LibroMapper libroMapper;
    private final CoreBookClient coreBookClient;

    public ResponseEntity<String> cargar (MultipartFile file){ //leer csv cargado
        /*
        -----Flujo de carga -----

            ..1: traigo 'lista' con datos en seco
            ..2: paso la 'lista' por el validador
            ..3: dicha lista validada la inserto en una nueva lista con el mapeo y pasa a ser una listaTerminada
            ..4: envío la listaTerminada al core para su persistencia en la base.
        */
        
        /*1*/List<LibroCsvDTO> lista = ExcelUtils.convertirCsvALista(file,LibroCsvDTO.class);

        /*2*/libroValidator.validar(lista);

        /*3*/List<LibroRequest> listaTerminada =  libroMapper.mapearADto(lista);
        
        /*4*/coreBookClient.guardarLibros(listaTerminada);
    
        return ResponseEntity.ok("Proceso finalizado. Se cargaron " + listaTerminada.size() + " libros.");
    }
}
