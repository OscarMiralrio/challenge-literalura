package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer anioDeCumpleanios,
        @JsonAlias("death_year") Integer anioDeFallecimieto
) {
}
