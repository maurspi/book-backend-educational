# 🌐 Book Backend - Módulo de Procesamiento de Archivos

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Educational](https://img.shields.io/badge/Purpose-Educational-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg)](https://spring.io/projects/spring-boot)

> **📚 Versión Educativa**: Este repositorio contiene código con fines académicos y de aprendizaje.
> Ver [LICENSE](LICENSE) para más detalles sobre el uso educativo.

## 🔗 Repositorio Relacionado

**Core (Lógica de Negocio)**: [book-core-educational](https://github.com/maurspi/book-core-educational)

## 📖 Descripción

**Book Backend** es el módulo de entrada de la aplicación de gestión de biblioteca. Su responsabilidad es procesar archivos CSV con información de libros, validarlos, transformarlos y delegarlos al módulo **Core** para su persistencia.

Este módulo implementa patrones de diseño (Strategy, Adapter, Facade, Proxy) siguiendo el principio de separación de responsabilidades.

---

## 🏗️ Arquitectura

Este módulo actúa como **Gateway** entre el usuario y el Core:

```
Usuario → Backend (Valida/Transforma) → Core (Persiste)
```

### Capas del Backend

```
┌─────────────────────────────────────┐
│         API Layer                   │
│  (Controllers, Contracts)           │
├─────────────────────────────────────┤
│       Service Layer                 │
│  (Orchestration, Validation)        │
├─────────────────────────────────────┤
│     Integration Layer               │
│  (Feign Client - Proxy to Core)    │
├─────────────────────────────────────┤
│      Infrastructure                 │
│  (Mappers, Validators, Utils)       │
└─────────────────────────────────────┘
```

---

## 🎯 Patrones de Diseño Implementados

### Patrones Estructurales
- **Facade**: `LibroCargaService.cargar()` - Orquesta validación, mapeo y envío
- **Proxy**: `CoreBookClient` (Feign) - Proxy declarativo hacia el Core
- **Adapter**: `LibroMapper` - Adapta `LibroCsvDTO` a `LibroRequest`

### Patrones de Comportamiento
- **Strategy**: `IValidadorCsv` / `LibroValidator` - Estrategia de validación intercambiable

### Patrones de Creación
- **Builder**: Utilizado en DTOs vía Lombok `@Builder`
- **Singleton**: Servicios gestionados por Spring (`@Service`, `@Component`)

---

## 🛠️ Tecnologías

- **Java**: 21
- **Spring Boot**: 3.x
- **Spring Cloud OpenFeign**: Cliente HTTP declarativo
- **OpenCSV**: Procesamiento de archivos CSV
- **Lombok**: Reducción de boilerplate
- **Maven**: Gestión de dependencias

---

## 📋 Requisitos Previos

- ☕ Java 21 (JDK 21)
- 📦 Maven 3.8+
- 🔗 Módulo **book-core** corriendo en `http://localhost:8081`

---

## ⚙️ Configuración

### Archivo de Configuración

Edita `src/main/resources/application.yml`:

```yaml
server:
  port: 8080

spring:
  application:
    name: book-backend
  
  servlet:
    multipart:
      max-file-size: 10MB       # Tamaño máximo por archivo
      max-request-size: 10MB    # Tamaño máximo total

# Configuración del Core (Feign)
book-core:
  url: http://localhost:8081/api/v1

# Feign Configuration
feign:
  client:
    config:
      default:
        connectTimeout: 5000    # 5 segundos
        readTimeout: 10000      # 10 segundos

logging:
  level:
    com.maurspi.book_backend: DEBUG
    com.maurspi.book_backend.integration.CoreBookClient: DEBUG
```

---

## 🚀 Instalación y Ejecución

### ⚠️ IMPORTANTE: Orden de Inicio

**Primero inicia el Core, luego el Backend:**

```bash
# Terminal 1: Iniciar Core (puerto 8081)
cd book-core
mvn spring-boot:run

# Terminal 2: Iniciar Backend (puerto 8080)
cd book-backend
mvn spring-boot:run
```

### Opción 1: Usando Maven

```bash
# 1. Clonar el repositorio (si aplica)
git clone <url-del-repositorio>
cd book-backend

# 2. Compilar el proyecto
mvn clean install

# 3. Ejecutar la aplicación
mvn spring-boot:run
```

### Opción 2: Usando Visual Studio Code (Recomendado)

#### Paso 1: Instalar Extensiones Necesarias

En VS Code, instala las siguientes extensiones:

1. **Extension Pack for Java** (Microsoft)
   - ID: `vscjava.vscode-java-pack`

2. **Spring Boot Extension Pack** (VMware)
   - ID: `vmware.vscode-boot-dev-pack`

**Instalación rápida:**
```bash
code --install-extension vscjava.vscode-java-pack
code --install-extension vmware.vscode-boot-dev-pack
```

#### Paso 2: Abrir el Proyecto

```bash
# Desde la terminal
cd /ruta/a/book-backend
code .

# O desde VS Code: File → Open Folder
```

#### Paso 3: Configurar Java 21

```bash
# Verificar versión
java -version  # Debe ser 21.x.x
```

En VS Code: `Ctrl + Shift + P` → `Java: Configure Java Runtime` → Seleccionar Java 21

#### Paso 4: ⚠️ IMPORTANTE - Levantar el Core Primero

**El Backend REQUIERE que el Core esté corriendo.**

```bash
# Opción A: Desde otra ventana de VS Code
# 1. Abrir book-core en otra ventana VS Code
# 2. Run book-core primero (puerto 8081)
# 3. Luego run book-backend (puerto 8080)

# Opción B: Desde terminal
# Terminal 1:
cd ../book-core
mvn spring-boot:run

# Terminal 2:
cd book-backend
mvn spring-boot:run
```

#### Paso 5: Configurar `application.yml`

Abre `src/main/resources/application.yml`:

```yaml
book-core:
  url: http://localhost:8081/api/v1  # Verificar que apunte al Core
```

#### Paso 6: Ejecutar el Backend

##### Opción A: Spring Boot Dashboard

1. Panel **Spring Boot Dashboard**
2. Verás `book-backend` listado
3. Click en **▶️ (Run)** o **🐛 (Debug)**

##### Opción B: Desde el Código

1. Abrir `BookBackendApplication.java`
2. Click en `Run | Debug` sobre el `main()`

##### Opción C: Terminal Integrada

```bash
mvn spring-boot:run
```

#### Paso 7: Verificar Comunicación con el Core

```bash
# Test rápido: Subir CSV de prueba
# Ver sección "Formato de Archivo CSV" más abajo
```

#### 🎯 Atajos Útiles en VS Code

| Atajo | Acción |
|-------|--------|
| ``Ctrl + ` `` | Terminal integrada |
| `F5` | Debug |
| `Shift + F5` | Detener |
| `Ctrl + Shift + P` | Comandos |

#### 🐛 Debugging

1. Pon breakpoint en `LibroCargaService.cargar()`
2. Click **Debug**
3. Usa Postman para subir un CSV
4. El debugger se detendrá en el breakpoint

#### 🔧 Configuración Recomendada

`.vscode/settings.json`:

```json
{
  "java.configuration.updateBuildConfiguration": "automatic",
  "spring-boot.ls.problem.application-properties.PROP_UNKNOWN_PROPERTY": "WARNING",
  "files.exclude": {
    "**/target": true
  },
  "java.saveActions.organizeImports": true,
  "editor.formatOnSave": true
}
```

`.vscode/launch.json`:

```json
{
  "version": "0.2.0",
  "configurations": [
    {
      "type": "java",
      "name": "Spring Boot-BookBackendApplication",
      "request": "launch",
      "cwd": "${workspaceFolder}",
      "mainClass": "com.maurspi.book_backend.BookBackendApplication",
      "projectName": "book-backend",
      "args": "",
      "envFile": "${workspaceFolder}/.env"
    }
  ]
}
```

#### 🆘 Troubleshooting VS Code

##### Error: "Connection refused to localhost:8081"

**Causa**: El Core no está corriendo.

**Solución**:
```bash
# Verifica que el Core esté corriendo
curl http://localhost:8081/api/v1/libros/listar
# Debe retornar [] o lista de libros

# Si no responde, inicia el Core primero
```

##### Error: "Feign timeout"

**Solución**: Aumenta timeout en `application.yml`:
```yaml
feign:
  client:
    config:
      default:
        readTimeout: 30000  # 30 segundos
```

---

### Opción 3: Usando IntelliJ IDEA / Eclipse

1. Importar proyecto como "Maven Project"
2. Esperar a que descargue dependencias
3. **Asegurarse que el Core esté corriendo**
4. Ejecutar la clase `BookBackendApplication.java`

### Opción 3: Usando JAR

```bash
# 1. Compilar
mvn clean package

# 2. Ejecutar el JAR
java -jar target/book-backend-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Endpoints API

Base URL: `http://localhost:8080/api`

### Subir Archivo CSV
```http
POST /libros/subir
Content-Type: multipart/form-data

Form Data:
  file: [archivo libros.csv]
```

**Ejemplo con cURL:**
```bash
curl -X POST http://localhost:8080/api/libros/subir \
  -F "file=@libros.csv"
```

**Ejemplo con Postman:**
1. Método: `POST`
2. URL: `http://localhost:8080/api/libros/subir`
3. Body → form-data
4. Key: `file` (tipo: File)
5. Value: Seleccionar archivo CSV

### Eliminar Todos los Libros
```http
DELETE /libros/eliminar
```

---

## 📄 Formato de Archivo CSV

### Estructura Requerida

El archivo CSV debe tener las siguientes columnas (separadas por `;`):

```csv
isbn;titulo;autor;editorial
978-0-123-45678-9;El Principito;Antoine de Saint-Exupéry;Salamandra
978-0-987-65432-1;1984;George Orwell;Debolsillo
978-0-456-78901-2;Cien Años de Soledad;Gabriel García Márquez;Sudamericana
```

### ⚠️ Reglas de Validación

- **ISBN**: Obligatorio
- **Título**: Obligatorio
- **Autor**: Opcional
- **Editorial**: Opcional
- **Separador**: Punto y coma (`;`)
- **Encoding**: UTF-8
- **Primera línea**: Encabezado (se ignora si tiene nombres de columnas)

### 📋 Archivo de Ejemplo

Archivo: `libros.csv`
```csv
isbn;titulo;autor;editorial
978-0-061-96436-7;To Kill a Mockingbird;Harper Lee;Harper Perennial
978-0-141-43951-8;1984;George Orwell;Penguin Books
978-0-307-47472-7;The Kite Runner;Khaled Hosseini;Riverhead Books
978-0-452-28423-4;The Catcher in the Rye;J.D. Salinger;Little Brown
978-0-7432-7356-5;The Da Vinci Code;Dan Brown;Doubleday
```

---

## 📊 Respuesta de Carga Masiva

### Carga Exitosa

```json
{
  "insertados": [
    {
      "isbn": "978-0-123",
      "titulo": "El Principito",
      "autor": "Saint-Exupéry"
    },
    {
      "isbn": "978-0-456",
      "titulo": "1984",
      "autor": "George Orwell"
    }
  ],
  "existentes": [
    {
      "isbn": "978-0-789",
      "titulo": "Cien Años de Soledad",
      "autor": "García Márquez"
    }
  ],
  "mensaje": "Proceso exitoso. Se insertaron 2 libros."
}
```

### Carga con Errores

```json
{
  "insertados": [],
  "existentes": [
    {
      "isbn": "978-0-123",
      "titulo": "El Principito",
      "autor": "Saint-Exupéry"
    }
  ],
  "mensaje": "No se insertó ningún libro. Todos los registros ya existían."
}
```

---

## 🎓 Conceptos Educativos

### Patrón Facade (Orquestación)

El `LibroCargaService` coordina múltiples operaciones:

```java
@Service
public class LibroCargaService {
    
    public CargaMasivaResponse cargar(MultipartFile file) {
        // Paso 1: Convertir CSV a objetos Java
        List<LibroCsvDTO> lista = ExcelUtils.convertirCsvALista(file, LibroCsvDTO.class);
        
        // Paso 2: Validar datos (Strategy)
        libroValidator.validar(lista);
        
        // Paso 3: Transformar a modelo del Core (Adapter)
        List<LibroRequest> listaTerminada = libroMapper.mapearADto(lista);
        
        // Paso 4: Enviar al Core (Proxy)
        return coreBookClient.guardarLibros(listaTerminada);
    }
}
```

**Beneficio**: El controlador solo ve un método simple, ocultando la complejidad.

### Patrón Strategy (Validación)

Estrategia de validación intercambiable:

```java
public interface IValidadorCsv<T> {
    void validar(List<T> datos);
}

@Component
public class LibroValidator implements IValidadorCsv<LibroCsvDTO> {
    @Override
    public void validar(List<LibroCsvDTO> datos) {
        // Reglas específicas de libros
        if (datos == null || datos.isEmpty()) {
            throw new RuntimeException("Archivo vacío");
        }
        // ... más validaciones
    }
}
```

**Beneficio**: Fácil agregar `AutorValidator`, `EditorialValidator`, etc.

### Patrón Proxy (Feign Client)

Cliente HTTP declarativo hacia el Core:

```java
@FeignClient(name = "book-core", url = "${book-core.url}")
public interface CoreBookClient {
    
    @PostMapping("/api/v1/libros/batch")
    CargaMasivaResponse guardarLibros(@RequestBody List<LibroRequest> libros);
}
```

**Beneficio**: Feign maneja automáticamente:
- Serialización/deserialización JSON
- Timeouts y reintentos
- Manejo de errores HTTP
- Balance de carga (si hay múltiples instancias)

### Patrón Adapter (Mapeo)

Adapta entre modelos incompatibles:

```java
@Component
public class LibroMapper implements IMapperCsv<LibroCsvDTO, LibroRequest> {
    
    @Override
    public List<LibroRequest> mapearADto(List<LibroCsvDTO> entrada) {
        return entrada.stream()
            .map(csv -> LibroRequest.builder()
                .isbn(csv.getIsbn())
                .titulo(csv.getTitulo())
                .autor(csv.getAutor())
                .editorial(csv.getEditorial())
                .build())
            .toList();
    }
}
```

**Beneficio**: Desacopla el formato CSV del contrato con el Core.

---

## 📁 Estructura del Proyecto

```
book-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/maurspi/book_backend/
│   │   │       ├── api/
│   │   │       │   ├── contracts/      # Interfaces de API
│   │   │       │   ├── controllers/    # REST Controllers
│   │   │       │   ├── request/        # DTOs de entrada
│   │   │       │   └── response/       # DTOs de salida
│   │   │       ├── dtos/               # DTOs de CSV
│   │   │       ├── services/           # Lógica de orquestación
│   │   │       ├── validators/         # Patrón Strategy
│   │   │       ├── mappers/            # Patrón Adapter
│   │   │       ├── integration/        # Feign Clients (Proxy)
│   │   │       ├── utils/              # Utilidades (ExcelUtils)
│   │   │       └── BookBackendApplication.java
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-prod.yml
│   │       └── ejemplos/
│   │           └── libros.csv          # Archivo de ejemplo
│   └── test/                           # Tests unitarios
├── pom.xml
└── README.md
```

---

## 🔄 Flujo de Datos Completo

```
┌──────────────┐
│   Usuario    │
└──────┬───────┘
       │ 1. Upload CSV
       ▼
┌──────────────────────────────────────────┐
│          BACKEND (Puerto 8080)           │
├──────────────────────────────────────────┤
│  LibroController.subir()                 │
│         ↓                                │
│  LibroCargaService.cargar()              │
│         ↓                                │
│  ExcelUtils.convertirCsvALista()         │
│    → List<LibroCsvDTO>                   │
│         ↓                                │
│  LibroValidator.validar()  [STRATEGY]    │
│         ↓                                │
│  LibroMapper.mapearADto()  [ADAPTER]     │
│    → List<LibroRequest>                  │
│         ↓                                │
│  CoreBookClient.guardarLibros() [PROXY]  │
│         ↓ HTTP POST                      │
└─────────┼────────────────────────────────┘
          │
          ▼
┌──────────────────────────────────────────┐
│           CORE (Puerto 8081)             │
├──────────────────────────────────────────┤
│  LibroController.subirLote()             │
│         ↓                                │
│  LibroService.guardarLote()              │
│         ↓                                │
│  LibroCreator.crearLibro()  [FACTORY]    │
│         ↓                                │
│  LibroRepository.saveAll()               │
│         ↓                                │
└─────────┼────────────────────────────────┘
          │
          ▼
    ┌──────────┐
    │  BASE    │
    │  DATOS   │
    └──────────┘
```

---

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Test de integración (requiere Core corriendo)
mvn verify
```

---

## 🚀 Despliegue

### Docker Compose (Backend + Core juntos)

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: biblioteca_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"

  book-core:
    build: ./book-core
    ports:
      - "8081:8081"
    depends_on:
      - postgres
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/biblioteca_db
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: password

  book-backend:
    build: ./book-backend
    ports:
      - "8080:8080"
    depends_on:
      - book-core
    environment:
      BOOK_CORE_URL: http://book-core:8081/api/v1
```

```bash
docker-compose up
```

---

## 🆘 Troubleshooting

### Error: "Connection refused to localhost:8081"

**Causa**: El Core no está corriendo o está en otro puerto.

**Solución**:
1. Verifica que el Core esté corriendo: `curl http://localhost:8081/api/v1/libros/listar`
2. Verifica la URL en `application.yml`

### Error: "Failed to parse CSV"

**Causa**: El separador del CSV no coincide.

**Solución**: 
- Si tu CSV usa comas (`,`), cambia en `ExcelUtils.java`:
```java
.withSeparator(',')  // En lugar de ';'
```

### Error: "File size exceeds maximum"

**Causa**: El archivo es muy grande.

**Solución**: Aumenta el límite en `application.yml`:
```yaml
spring:
  servlet:
    multipart:
      max-file-size: 50MB
      max-request-size: 50MB
```

### Error: "Feign timeout"

**Causa**: El Core tarda mucho en responder.

**Solución**: Aumenta el timeout:
```yaml
feign:
  client:
    config:
      default:
        readTimeout: 30000  # 30 segundos
```

---

## 🤝 Integración con Core

Este módulo **REQUIERE** que el módulo **book-core** esté corriendo.

### Verificar Conectividad

```bash
# Health check del Core
curl http://localhost:8081/api/v1/libros/listar

# Debería retornar: [] (lista vacía) o los libros existentes
```

---

## 📚 Recursos Adicionales

- [Spring Cloud OpenFeign](https://spring.io/projects/spring-cloud-openfeign)
- [OpenCSV Documentation](http://opencsv.sourceforge.net/)
- [Multipart File Upload](https://spring.io/guides/gs/uploading-files/)
- [Design Patterns](https://refactoring.guru/design-patterns)

---

## 👨‍💻 Autor

**Mauricio Spina**
- GitHub: [@maurspi](https://github.com/maurspi)
- Proyecto: Sistema de Gestión de Biblioteca con Patrones de Diseño

---

## 📄 Licencia

Este proyecto está bajo la **MIT License - Versión Educativa**.

**Propósito Educativo**: Este código fue desarrollado específicamente para:
- ✅ Aprendizaje de patrones de diseño (Strategy, Adapter, Facade, Proxy)
- ✅ Estudio de arquitectura de microservicios
- ✅ Procesamiento de archivos CSV con validación
- ✅ Integración entre servicios con Feign
- ✅ Proyectos académicos y de investigación
- ✅ Portfolios de estudiantes y desarrolladores

Ver el archivo [LICENSE](LICENSE) para más detalles.

Copyright (c) 2024 Mauricio Spina

---

## 🙏 Reconocimientos

Este proyecto fue desarrollado como trabajo académico para demostrar la
implementación de patrones de diseño en arquitecturas de microservicios con
Spring Boot, específicamente la separación de responsabilidades entre módulos.

**Si utilizas este código en tus proyectos educativos, se agradece la atribución
mencionando al autor original.**

---

## ⚖️ Uso Educativo

Este código está disponible públicamente para que:
- Profesores lo utilicen como material de enseñanza
- Estudiantes lo estudien y aprendan de él
- Desarrolladores lo usen como referencia
- Sea base para proyectos académicos

**Ejemplos de uso permitido:**
- "Inspirado en el proyecto book-backend de Mauricio Spina"
- "Basado en la arquitectura de [link al repo]"
- Incluirlo en presentaciones con atribución

---

**¡Gracias por usar Book Backend!** 🌐✨
