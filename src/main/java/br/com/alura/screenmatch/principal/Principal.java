package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.service.ConsumoApi;

import java.util.Scanner;

public class Principal {
    Scanner input = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private final String ENDERECO = "https://www.omdbapi.com/?t"; //variaveis que nunca serao modificadas
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.print("Digite o nome da série: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }
}
