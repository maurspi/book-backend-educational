package com.maurspi.book_backend.utils;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.bean.CsvToBeanBuilder;

public class ExcelUtils {

    public static <T> List<T> convertirCsvALista(MultipartFile file, Class<T> claseDto) {
        if (file.isEmpty()) {
            return Collections.emptyList(); // Si viene vacío no hago nada
        }

        try (Reader reader = new InputStreamReader(file.getInputStream())) {
            
            return new CsvToBeanBuilder<T>(reader)
                    .withType(claseDto)
                    .withIgnoreLeadingWhiteSpace(true)
                    .withSeparator(';') // Ojo: Excel en español usa punto y coma. Si falla, prueba con ','
                    .build()
                    .parse();
            
        } catch (Exception e) { // CORRECCIÓN: Capturamos Exception (que incluye IOException)
            throw new RuntimeException("Error al parsear el archivo CSV: " + e.getMessage());
        }
    }
}