package com.aluracursos.LiterAlura.repository;

import com.aluracursos.LiterAlura.modelo.Autores;
import com.aluracursos.LiterAlura.modelo.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface LibrosRepository extends JpaRepository<Libros, Long> {
    Optional<Libros> findByTitulo(String titulo);

    // NUEVO: Metodo con JOIN FETCH para cargar idiomas
    @Query("SELECT DISTINCT l FROM Libros l LEFT JOIN FETCH l.idiomas")
    List<Libros> findAllWithIdiomas();

    // NUEVO: Metodo modificado para buscar por idioma
    @Query("SELECT l FROM Libros l LEFT JOIN FETCH l.idiomas WHERE :idioma MEMBER OF l.idiomas")
    List<Libros> findByIdiomasContaining(@Param("idioma") String idioma);

    // NUEVO: Metodo modificado para buscar por autor
    @Query("SELECT l FROM Libros l LEFT JOIN FETCH l.idiomas WHERE LOWER(l.autores.nombre) LIKE LOWER(CONCAT('%', :nombreAutor, '%'))")
    List<Libros> findByAutoresNombreContainingIgnoreCase(@Param("nombreAutor") String nombreAutor);

    // NUEVO: Metodo para contar libros por autor
    int countByAutores(Autores autor);
}

