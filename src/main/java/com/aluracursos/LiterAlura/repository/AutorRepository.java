package com.aluracursos.LiterAlura.repository;

import com.aluracursos.LiterAlura.modelo.Autores;
import com.aluracursos.LiterAlura.modelo.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autores, Long> {
    Optional<Autores> findByNombre(String nombre);

    // Agregando nuevos métodos
    List<Autores> findByFechaDeNacimientoLessThanEqualAndFechaDeFallecimientoGreaterThanEqual(String añoNacimiento, String añoFallecimiento);
    List<Autores> findAllByOrderByNombreAsc();

    // NUEVO: Metodo con JOIN FETCH para cargar los libros
    @Query("SELECT DISTINCT a FROM Autores a LEFT JOIN FETCH a.libros " +
            "WHERE a.fechaDeNacimiento <= :ano AND a.fechaDeFallecimiento >= :ano")
    List<Autores> findAutoresVivosEnAnioConLibros(@Param("ano") String ano);
}

