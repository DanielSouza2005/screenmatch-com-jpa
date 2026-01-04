package br.com.alura.screenmatch.service;

public interface IConverteDados {
    <T> T  converterDados(String json, Class<T> classe);
}
