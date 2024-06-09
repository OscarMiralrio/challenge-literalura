package com.alura.literalura.model;

public enum Idioma {

    es("Español"),
    en("Ingles");

    private String idioma;

    Idioma(String idioma){
        this.idioma = idioma;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma : Idioma.values()){
            if (idioma.name().equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }

    public String getIdioma() {
        return idioma;
    }
}
