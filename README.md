## LiterAlura - Aplicación de Búsqueda de Libros
https://img.shields.io/badge/Java-17-red
https://img.shields.io/badge/Spring_Boot-3.1-green
https://img.shields.io/badge/API-Gutendex-blue

LiterAlura es una aplicación Java con Spring Boot que te permite buscar y explorar libros utilizando la API pública de Gutendex.

## CARACTERÍSTICAS PRINCIPALES

1. Búsqueda de libros por título
2. Listado de libros registrados
3. Listado de autores registrados
4. Filtrado de libros por idioma
5. Búsqueda de autores vivos en un año específico

Persistencia de datos con JPA y H2 Database

## Tecnologías utilizadas
Java 17
Spring Boot 3.1
Spring Data JPA
H2 Database (base de datos en memoria)
Gutendex API (fuente de datos de libros)

## CÓMO EJECUTAR EL PROYECTO

Clona el repositorio:
bash
git clone https://github.com/Sara-Hurtado-Li/LiterAlura.git
Navega al directorio del proyecto:

bash
cd LiterAlura
Ejecuta la aplicación:

bash
./mvnw spring-boot:run
Accede al menú principal en la consola.

## USO DE LA APLICACIÓN
Menú Principal:
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar libros por idioma
5. Autores vivos en un año
0. Salir
   
# Funcionalidades detalladas:

# Buscar libro por título:
Ingresa el nombre de un libro para buscarlo en la API de Gutendex.
Los resultados se guardan automáticamente en la base de datos.

# Listar libros registrados:
Muestra todos los libros almacenados en la base de datos.
Incluye título, autor, idiomas y número de descargas.

# Listar autores registrados:
Muestra todos los autores almacenados en la base de datos.
Incluye fechas de nacimiento/fallecimiento y cantidad de libros.

# Listar libros por idioma:
Filtra libros por código de idioma (es, en, fr, pt, etc.).
Muestra los libros que coincidan con el idioma especificado

# Autores vivos en un año:
Busca autores que estaban vivos en un año específico.
Muestra información del autor y sus libros

## Estructura del proyecto
text
src/
├── main/
│   ├── java/com/aluracursos/LiterAlura/
│   │   ├── modelo/       # Entidades (Autores, Libros)
│   │   ├── repository/   # Repositorios JPA
│   │   ├── service/      # Lógica de API y conversión
│   │   ├── principal/    # Lógica del menú
│   │   └── LiterAluraApplication.java
│   └── resources/
│       └── application.properties
└── test/                 # Pruebas (opcional)


## Notas adicionales

La aplicación usa una base de datos H2 en memoria (los datos se pierden al cerrar la aplicación).
Para producción, considera cambiar a MySQL o PostgreSQL modificando application.properties.

La API de Gutendex proporciona libros de dominio público.
