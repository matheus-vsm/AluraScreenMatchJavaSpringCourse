package br.com.alura.TabelaFipe.service;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

import java.util.List;

public class ConverteDados implements IConverteDados {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        return mapper.readValue(json, classe);
    }

    @Override
    public <T> List<T> obterListaDados(String json, Class<T> classe) { // Cria um "tipo de coleção" para o Jackson entender que queremos desserializar uma LISTA de objetos do tipo T.
        // 'CollectionType' é uma classe do Jackson que representa um tipo genérico de coleção (como List, Set, etc.)
        CollectionType lista = mapper.getTypeFactory()// 'getTypeFactory()' retorna uma fábrica interna do ObjectMapper responsável por criar tipos complexos, como List<Objeto>, Map<String, Objeto>, etc.
                .constructCollectionType(List.class, classe); //"Quero construir um tipo que representa uma List de objetos do tipo 'classe'".
        return mapper.readValue(json, lista);
    }
}
