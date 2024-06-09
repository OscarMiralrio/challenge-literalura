package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){}

    public Autor(String nombre, Integer anioDeNacimiento, Integer anioDeFallecimiento) {
        this.nombre = nombre;
        this.anioDeNacimiento = anioDeNacimiento;
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public Autor(DataAutor dataAutor){
        this.nombre = dataAutor.nombre();
        this.anioDeNacimiento = dataAutor.anioDeCumpleanios();
        this.anioDeFallecimiento = dataAutor.anioDeFallecimieto();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "\n========== Autor =========\n" +
                "Nombre = " + nombre + '\n' +
                "Año de Nacimiento = " + anioDeNacimiento + '\n'+
                "Año de Fallecimiento = " + anioDeFallecimiento +'\n'+
                "Libros = " + libros.stream().map(Libro::getTitulo).collect(Collectors.toList());
    }
}
