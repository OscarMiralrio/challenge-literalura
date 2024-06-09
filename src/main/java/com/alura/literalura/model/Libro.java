package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private Integer totalDescargas;

    @ManyToOne
    private Autor autor;

    public Libro(){}

    public Libro(String titulo, String idioma, Integer totalDescargas) {
        this.titulo = titulo;
        this.idioma = Idioma.fromString(idioma);
        this.totalDescargas = totalDescargas;
    }

    public Libro(DataLibro dataLibro){
        this.titulo = dataLibro.titulo();
        this.idioma = Idioma.fromString(dataLibro.idioma().get(0));
        this.totalDescargas = dataLibro.numeroDeDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Integer totalDescargas) {
        this.totalDescargas = totalDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "\n========== Libro ==========\n" +
                "Titulo = " + titulo +
                "\nAutor = " + autor.getNombre() +
                "\nIdioma = " + idioma +
                "\nNúmero de Descargas = " + totalDescargas;
    }
}
