package com.alura.literalura.mapper;

public interface IConvierteDatos {
    <T> T dataMapper(String json, Class<T> clase);
}
