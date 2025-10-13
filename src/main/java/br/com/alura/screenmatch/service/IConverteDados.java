package br.com.alura.screenmatch.service;

public interface IConverteDados {
    //<T> → declara um parâmetro de tipo genérico.
    //Isso significa que o metodo pode trabalhar com qualquer tipo de dado (por ex, Usuario, Endereco, Produto, etc.).
    //T → é o tipo de retorno, o mesmo tipo que você passou como argumento de tipo.
    <T> T obterDados(String json, Class<T> classe);
}
