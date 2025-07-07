package com.aluracursos.LiterAlura.modelo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="TablaLibros")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;

    //@Transient
//
//    @ElementCollection
//    @CollectionTable(name = "libros_idiomas",
//            joinColumns = @JoinColumn(name = "libro_id"))
//    @Column(name = "idioma")
//    private List<String> idiomas = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libros_idiomas",
            joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "idioma")
    private List<String> idiomas = new ArrayList<>();

    private Double numeroDeDescargas;

    @ManyToOne
    @JoinColumn(name = "autores_id")
    private Autores autores;


    //Constructores

    public Libros() {
    }

    public Libros (DatosLibro datosLibro){
        this.titulo = datosLibro.titulo();
        //this.autor = datosLibro.autor();
        this.idiomas = datosLibro.idiomas();
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
    }

    //Creando getteres y setters (se hace despues de crear los constructores


    public Autores getAutores() {return autores;}
    public void setAutores(Autores autores) {this.autores = autores;}

    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }
    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }
    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    //toString --> permite traer toda la información atraves del string
    @Override
    public String toString() {
        return
                "titulo='" + titulo + '\'' +
                //", autor=" + autor +
                ", idiomas=" + idiomas +
                ", numeroDeDescargas=" + numeroDeDescargas ;
    }
}
