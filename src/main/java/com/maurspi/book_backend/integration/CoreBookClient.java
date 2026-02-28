package com.maurspi.book_backend.integration;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maurspi.book_backend.api.request.LibroRequest;



/**
 * Cliente Feign para comunicarse con el microservicio Core.
 * Patrón: Proxy Declarativo.
 */
// @FeignClient: Indica a Spring que cree un bean proxy para esta interfaz.
// name = "book-core": Identificador lógico del servicio (útil para Eureka/Consul).
// url = "${book-core.url}": Externalizamos la URL en el application.properties (Buenas prácticas: 12-Factor App).

@FeignClient(name="book-core", url="${book-core-url}")
public interface CoreBookClient {
 /**
     * Envía un lote de libros al Core.
     * Mapea la llamada Java a una petición HTTP POST.
     * * @param libros Lista de libros transformados (Requests limpios).
     * @return void (o podrías retornar un DTO de respuesta con IDs generados).
     */
    
    @PostMapping("/libros/batch")
    void guardarLibros(@RequestBody List<LibroRequest> libros);
    
}
