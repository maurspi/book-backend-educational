package com.maurspi.book_backend.mappers;

import java.util.List;

public interface IMapperCsv <T, R>{ // ENTRADA = CSV -> SALIDA LA REQUEST AL CORE.

    List <R> mapearADto(List<T> entrada);
}
