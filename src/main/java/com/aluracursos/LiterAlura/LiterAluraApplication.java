package com.aluracursos.LiterAlura;

import com.aluracursos.LiterAlura.modelo.Datos;
import com.aluracursos.LiterAlura.modelo.DatosLibro;
import com.aluracursos.LiterAlura.principal.Principal;
import com.aluracursos.LiterAlura.repository.AutorRepository;
import com.aluracursos.LiterAlura.repository.LibrosRepository;
import com.aluracursos.LiterAlura.service.ConsumoAPI;
import com.aluracursos.LiterAlura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {
	@Autowired
	private LibrosRepository repositoryLibro;

	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		var consumoApi1 = new ConsumoAPI();
//		var json = consumoApi.obtenerDatos("https://gutendex.com/books/");
//		System.out.println(json);
//
//		ConvierteDatos conversor = new ConvierteDatos();
//		var datos= conversor.obtenerDatos(json, Datos.class);
//		System.out.println(datos);
		Principal principal= new Principal(repositoryLibro, repositorioAutor);
		principal.muestraElMenu();
	}
}

