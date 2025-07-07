package com.aluracursos.LiterAlura.modelo;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name ="tabla_autor")

public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeFallecimiento;

    //private Libros libros;
//
//    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Libros> libros = new ArrayList<>();

    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libros> libros = new ArrayList<>();

    //Constructores

    public Autores(){}

    public Autores (DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDeFallecimiento = datosAutor.fechaDeNacimiento();

    }

    //Creando getteres y setters (se hace despues de crear los constructores


    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }

    public Long getId() {return Id;}
    public void setId(Long id) {Id = id;}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeFallecimiento() {
        return fechaDeFallecimiento;
    }

    public void setFechaDeFallecimiento(String fechaDeFallecimiento) {
        this.fechaDeFallecimiento = fechaDeFallecimiento;
    }


}





