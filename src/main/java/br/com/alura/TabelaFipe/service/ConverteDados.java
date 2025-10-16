package br.com.alura.TabelaFipe.service;

import org.springframework.boot.env.SpringApplicationJsonEnvironmentPostProcessor;
import tools.jackson.databind.ObjectMapper;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }
}
