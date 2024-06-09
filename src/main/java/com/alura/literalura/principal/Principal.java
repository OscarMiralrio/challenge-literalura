package com.alura.literalura.principal;

import com.alura.literalura.conexion.ConsumoAPI;
import com.alura.literalura.mapper.ConvierteDatos;
import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);

    private ConvierteDatos mapper = new ConvierteDatos();

    private final String URL_BASE = "http://gutendex.com/books";
    private final String URL_COMPLEMENT = "?search=";

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void menu(){

        String menu = """
                
                ==================================================
                Elije una opcion a consultar:
                1 - Buscar libro por titulo.
                2 - Mostrar libros registrados.
                3 - Mostrar autores registrados.
                4 - Mostrar autores vivos en un determinado año.
                5 - Mostrar libros por idioma.
                
                0 - Salir
                ==================================================""";
        var opcion = -1;

        while(opcion != 0){
            System.out.println(menu);
            try {
                opcion = teclado.nextInt();
            } catch (InputMismatchException e){
                System.out.println(" :: Opcion Invalida :: \n");
                teclado.nextLine();
                continue;
            }
            teclado.nextLine();

            switch (opcion){
                case 1:
                    saveBookAndAuthor();
                    break;
                case 2:
                    printAllBooks();
                    break;
                case 3:
                    printAllAuthors();
                    break;
                case 4:
                    autoresVivosEnDeterminadoAnio();
                    break;
                case 5:
                    findByIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }

        }

    }

    private Data getDatosLibros(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var titulo = teclado.nextLine();
        var json = consumoAPI.obtenerDatos2(URL_BASE+URL_COMPLEMENT+titulo.toLowerCase().replace(" ","%20"));
        Data dataLibro = mapper.dataMapper(json, Data.class);
        return dataLibro;

    }

    private void saveBookAndAuthor(){
        Data dataLibro = getDatosLibros();
        if(!dataLibro.libros().isEmpty()) {
            Optional<Autor> optAutor = autorRepository.findByNombreContainsIgnoreCase(dataLibro.libros().get(0).autor().get(0).nombre());
            Libro libro = new Libro(dataLibro.libros().get(0));
            if (!optAutor.isPresent()) {
                Autor autor = new Autor(dataLibro.libros().get(0).autor().get(0));
                autorRepository.save(autor);
                optAutor = autorRepository.findByNombreContainsIgnoreCase(autor.getNombre());
            } else {
                optAutor = autorRepository.findByNombreContainsIgnoreCase(dataLibro.libros().get(0).autor().get(0).nombre());
            }
            libro.setAutor(optAutor.get());
            optAutor.get().getLibros().add(libro);
            try {
                libroRepository.save(libro);
            } catch (ConstraintViolationException | DataIntegrityViolationException e) {
                System.err.println(":: El libro ya se encuentra registrado :: \n");
            }
        } else {
            System.err.println(" :: No se encontraron libros :: \n");
        }
    }

    private void printAllBooks() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    private void printAllAuthors() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void autoresVivosEnDeterminadoAnio(){
        System.out.println("Ingresa el año que deseas ver los autores que estuvieron vivos:");
        try {
            var anio = teclado.nextInt();
            teclado.nextLine();
            List<Autor> autores = autorRepository.obtieneAutoresVivosEnUnAnio(anio);
            autores.forEach(System.out::println);
        }catch (InputMismatchException e){
            System.err.println(" :: Año Invalido:: \n");
            teclado.nextLine();
        }
    }

    private void findByIdioma(){
        System.out.println("Escribe la abreviatura del idioma por el que deseas buscar los libros");

        Arrays.stream(Idioma.values()).collect(Collectors.toList()).forEach(
                i -> System.out.println(i.name() + " - " + i.getIdioma())
        );

        try {
            var idioma = teclado.nextLine();
            var language = Idioma.fromString(idioma);
            List<Libro> librosPorIdioma = libroRepository.findByIdioma(language);
            if (librosPorIdioma.isEmpty()){
                System.err.println("No se encontraron libros registrados en ese idioma.");
            }
            librosPorIdioma.forEach(System.out::println);
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
        }

    }

}
