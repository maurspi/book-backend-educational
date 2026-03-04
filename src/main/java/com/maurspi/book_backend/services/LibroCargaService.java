package com.maurspi.book_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.maurspi.book_backend.api.request.LibroRequest;
import com.maurspi.book_backend.api.response.CargaMasivaResponse;
import com.maurspi.book_backend.dtos.LibroCsvDTO;
import com.maurspi.book_backend.integration.CoreBookClient;
import com.maurspi.book_backend.mappers.LibroMapper;
import com.maurspi.book_backend.utils.ExcelUtils;
import com.maurspi.book_backend.validators.impl.LibroValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LibroCargaService {

    private final LibroValidator libroValidator;
    private final LibroMapper libroMapper;
    private final CoreBookClient coreBookClient;

    // 1. CAMBIAMOS EL TIPO DE RETORNO AQUÍ
    public CargaMasivaResponse cargar(MultipartFile file) {
        /*
         * -----Flujo de carga -----
         * ..1: traigo 'lista' con datos en seco
         * ..2: paso la 'lista' por el validador
         * ..3: dicha lista validada la inserto en una nueva lista con el mapeo y pasa a
         * ser una listaTerminada
         * ..4: envío la listaTerminada al core para su persistencia y capturo lo que me
         * devuelve.
         */

        /* 1 */ List<LibroCsvDTO> lista = ExcelUtils.convertirCsvALista(file, LibroCsvDTO.class);

        /* 2 */ libroValidator.validar(lista);

        /* 3 */ List<LibroRequest> listaTerminada = libroMapper.mapearADto(lista);

        // Con feign envio los datos al core
        /* 4 */ CargaMasivaResponse reporteCarga = coreBookClient.guardarLibros(listaTerminada);

        return reporteCarga;
    }

    public void eliminarTodos() {
        coreBookClient.eliminarTodosCore();
    }
}