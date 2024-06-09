package com.alura.literalura.principal;

import com.alura.literalura.conexion.ConsumoAPI;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class Principal {

    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private Scanner teclado = new Scanner(System.in);
    private final String URL_BASE = "http://gutendex.com/books";
    private final String URL_COMPLEMENT = "?search=";

    public void getDatosLibros(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var titulo = teclado.nextLine();
        var json = consumoAPI.obtenerDatos2(URL_BASE+URL_COMPLEMENT+titulo.toLowerCase().replace(" ","%20"));
        System.out.println(json);
    }

}
