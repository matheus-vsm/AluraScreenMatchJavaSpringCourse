package br.com.alura.screenmatch.model;

public enum Categoria {
    ACAO("Action", "Ação"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy", "Comédia"),
    DRAMA("Drama", "Drama"),
    CRIME("Crime", "Crime");

    private String categoriaOmdb;
    private String categoriaPortugues;

    Categoria(String categoriaOmdb, String categoriaPortugues) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    // O fromString serve para converter o nome da categoria que vem da API externa como "Comedy" para o enum interno para "Comedia"
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) { // percorre todas as constantes definidas no enum Categoria
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) { // compara o texto recebido com o nome em inglês da categoria (ignorando maiúsculas/minúsculas)
                return categoria; // se encontrar correspondência, retorna a constante do enum (ex: "Comedy" -> Categoria.COMEDIA)
            }
        }
        // se nenhuma correspondência for encontrada, lança uma exceção informando o erro
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }

    public static Categoria fromPortugues(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        // se nenhuma correspondência for encontrada, lança uma exceção informando o erro
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
}
