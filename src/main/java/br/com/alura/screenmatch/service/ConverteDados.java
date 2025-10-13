package br.com.alura.screenmatch.service;

import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper(); //propriedade de conversao

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }
}
