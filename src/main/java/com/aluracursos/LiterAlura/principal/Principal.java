package com.aluracursos.LiterAlura.principal;

import com.aluracursos.LiterAlura.modelo.*;
import com.aluracursos.LiterAlura.repository.AutorRepository;
import com.aluracursos.LiterAlura.repository.LibrosRepository;
import com.aluracursos.LiterAlura.service.ConsumoAPI;
import com.aluracursos.LiterAlura.service.ConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/";

    private LibrosRepository repositorioLibro;
    private AutorRepository repositorioAutor;


    public Principal(LibrosRepository libroRepository, AutorRepository autorRepository) {
        this.repositorioLibro = libroRepository;
        this.repositorioAutor = autorRepository;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n******* MENÚ PRINCIPAL *******
                    1. Buscar libro por título
                    2. Listar libros
                    3. Listar autores
                    4. Listar libros por idioma
                    5. Listar autores vivos en un determinado año
                    0. Salir
                    Seleccione la opción que desea realizar:
                    """;

            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;

                case 2:
                    listarLibrosRegistrados();
                    break;

                case 3:
                    listarAutoresRegistrados();
                    break;

                case 4:
                    listarLibrosPorIdioma();
                    break;

                case 5:
                    listarAutoresVivosEnAnio();
                    break;

                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }


    private void buscarLibroPorTitulo() {
        System.out.println("\nIngrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ", "+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        Optional<DatosLibro> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            System.out.println("\n******* INFORMACIÓN DEL LIBRO *******");
            DatosLibro libro = libroBuscado.get();
            mostrarInformacionLibro(libro);


        } else {
            System.out.println("Libro no encontrado!!");
        }
    }

//**************************************************************

    private void mostrarInformacionLibro(DatosLibro libro) {
        System.out.println("Título del libro: " + libro.titulo());

        // Verificar si el libro ya existe en la base de datos
        Optional<Libros> libroExistente = repositorioLibro.findByTitulo(libro.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("\n¡ADVERTENCIA! Este libro ya está registrado en la base de datos.");
            System.out.println(" --------Información del libro existente------------:");

            Libros libroGuardado = libroExistente.get();
            System.out.println("Autor: " + (libroGuardado.getAutores() != null ? libroGuardado.getAutores().getNombre() : "Desconocido"));
            System.out.println("Idiomas: " + (libroGuardado.getIdiomas() != null ? String.join(", ", libroGuardado.getIdiomas()) : "No especificado"));
            System.out.println("Número de descargas: " + libroGuardado.getNumeroDeDescargas());
            return; // Salir del metodo sin intentar guardar de nuevo
        }

        if (!libro.autor().isEmpty()) {
            DatosAutor autorData = libro.autor().get(0);

            // Buscar si el autor ya existe
            Optional<Autores> autorExistente = repositorioAutor.findByNombre(autorData.nombre());

            Autores autor;
            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                // Crear nuevo autor
                autor = new Autores();
                autor.setNombre(autorData.nombre());
                autor.setFechaDeNacimiento(autorData.fechaDeNacimiento());
                autor.setFechaDeFallecimiento(autorData.fechaDeFallecimiento());
                repositorioAutor.save(autor);
            }

            // Crear el libro
            Libros nuevoLibro = new Libros(libro);
            nuevoLibro.setTitulo(libro.titulo());
            nuevoLibro.setIdiomas(libro.idiomas());
            nuevoLibro.setNumeroDeDescargas(libro.numeroDeDescargas());
            nuevoLibro.setAutores(autor);

            // Guardar el libro
            repositorioLibro.save(nuevoLibro);

            // Actualizar la lista de libros del autor
            autor.getLibros().add(nuevoLibro);
            repositorioAutor.save(autor);

            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());
        }

        if (libro.idiomas() != null && !libro.idiomas().isEmpty()) {
            System.out.println("Idiomas: " + String.join(", ", libro.idiomas()));
        } else {
            System.out.println("Idiomas: No especificado");
        }

        System.out.println("Número de descargas: " + libro.numeroDeDescargas());
    }

//***********************************************************************************

    private void listarLibrosRegistrados() {
        System.out.println("\n******* LIBROS REGISTRADOS *******");
        // MODIFICANDO: Usamos el nuevo metodo findAllWithIdiomas()
        List<Libros> libros = repositorioLibro.findAllWithIdiomas();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        libros.forEach(libro -> {
            System.out.println("\nTítulo: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutores() != null ? libro.getAutores().getNombre() : "Desconocido"));
            // MODIFICADO: Manejo seguro de idiomas nulos
            System.out.println("Idiomas: " + (libro.getIdiomas() != null ? String.join(", ", libro.getIdiomas()) : "No especificado"));
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("****************** LIBRO ****************** ");
        });
    }

    private void listarAutoresRegistrados() {
        System.out.println("\n******* AUTORES REGISTRADOS *******");
        List<Autores> autores = repositorioAutor.findAllByOrderByNombreAsc();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        autores.forEach(autor -> {
            System.out.println("\nAutor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaDeFallecimiento());
            // MODIFICADO: Usamos countByAutores en lugar de autor.getLibros().size()
            System.out.println("Libros registrados: " + repositorioLibro.countByAutores(autor));
            System.out.println("****************** AUTOR ******************");
        });
    }

//*********************************************************************************************

    private void listarLibrosPorIdioma() {
        System.out.println("\nIngrese el código de idioma a buscar (ej: es, en, fr, pt):");
        var idioma = teclado.nextLine().toLowerCase();

        List<Libros> libros = repositorioLibro.findByIdiomasContaining(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma '" + idioma + "'.");
            return;
        }

        System.out.println("\n******* LIBROS EN IDIOMA " + idioma.toUpperCase() + " *******");
        libros.forEach(libro -> {
            System.out.println("\nTítulo: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutores() != null ? libro.getAutores().getNombre() : "Desconocido"));
            System.out.println("Idiomas: " + (libro.getIdiomas() != null ? String.join(", ", libro.getIdiomas()) : "No especificado"));
            System.out.println("Descargas: " + libro.getNumeroDeDescargas());
            System.out.println("****************** LIBRO ******************");
        });
    }

//*********************************************************************************************
    private void listarAutoresVivosEnAnio() {
        System.out.println("\nIngrese el año para buscar autores vivos:");
        var ano = teclado.nextLine();

        // Consulta que carga autores con sus libros
        List<Autores> autores = repositorioAutor.findAutoresVivosEnAnioConLibros(ano);

        if (autores.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año " + ano + ".");
            return;
        }

        System.out.println("\n******* AUTORES VIVOS EN " + ano + " *******");
        autores.forEach(autor -> {
            System.out.println("\nAutor: " + autor.getNombre());
            System.out.println("Nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fallecimiento: " + autor.getFechaDeFallecimiento());

            // Mostrar los títulos de los libros
            if (!autor.getLibros().isEmpty()) {
                System.out.println("Libros escritos:");
                autor.getLibros().forEach(libro ->
                        System.out.println("  - " + libro.getTitulo())
                );
            } else {
                System.out.println("No hay libros registrados para este autor.");
            }

            //System.out.println("Total de libros: " + autor.getLibros().size());
            System.out.println("Libros registrados: " + autor.getLibros().size());
            System.out.println("****************** AUTOR ******************");
        });
    }

}







