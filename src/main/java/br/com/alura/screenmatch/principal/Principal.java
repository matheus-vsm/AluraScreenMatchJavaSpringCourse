package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosEpidodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner input = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t="; //variaveis que nunca serao modificadas
    private final String API_KEY = "&apikey=6585022c";

    public void exibeMenu(){
        System.out.print("Digite o nome da série: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);

        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++){
            json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + i + API_KEY);
            DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        // cria um Stream a partir da coleção 'temporadas'
        List<DadosEpidodio> dadosEpidodios = temporadas.stream()
                // para cada temporada (t), obtém seu Stream de episódios e "achata" todos em um único Stream de episódios
                .flatMap(t -> t.episodios().stream())
                        .collect(Collectors.toList()); //permite modificações futuras na lista, MUTÁVEL
                        // .toList(); //não permite modificações, forma uma lista IMUTÁVEL

        System.out.println("\nTop 5 Episódios:");
        dadosEpidodios.stream() // cria um Stream a partir da lista 'dadosEpidodios'
                .filter(e -> !e.avalicacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpidodio::avalicacao).reversed()) // ordena os episódios pelo valor retornado por 'avalicacao' (do menor para o maior), e .reversed() inverte para do maior para o menor
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList()); // Para cada episódio 'd', cria um novo objeto Episodio com o número da temporada e o dadosEpisodio
        episodios.forEach(System.out::println);
    }
}
