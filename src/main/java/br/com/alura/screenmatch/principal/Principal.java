package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.BreakIterator;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    Scanner input = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t="; //variaveis que nunca serao modificadas
    private final String API_KEY = "&apikey=6585022c";
    private List<DadosSerie> dadosSeries = new ArrayList<>();
    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        var opcao = -1;

        while (opcao != 0) {
            var menu = """
                    
                    1 - Buscar Séries
                    2 - Buscar Episódios
                    3 - Listar Séries Buscadas
                    4 - Listar Série por Título
                    5 - Listar Séries por Ator
                    6 - Listar as Top 5 Séries
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = input.nextInt();
            input.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    listarSeriePorTitulo();
                    break;
                case 5:
                    listarSeriesPorAtor();
                    break;
                case 6:
                    listarTop5Series();
                    break;
                case 0:
                    System.out.println("\nSaindo...");
                    break;
                default:
                    System.out.println("\nOpção inválida");
            }
        }
    }

    private void listarSeriesBuscadas() {
        System.out.println();
        series = repositorio.findAll();
//        series = dadosSeries.stream()
//                        .map(d -> new Serie(d))
//                                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void listarSeriePorTitulo() {
        System.out.print("\nEscolha uma serie pelo nome: ");
        var nomeSerie = input.nextLine();
        Optional<Serie> serieBuscada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBuscada.isPresent()) {
            System.out.println("Dados da Série: " + serieBuscada.get());
        } else {
            System.out.println("Série Não Encontrada!");
        }
    }

    private void listarSeriesPorAtor() {
        System.out.print("\nDigite o nome do ator: ");
        var nomeAtor = input.nextLine();
        System.out.print("Avaliações a partir de que valor: ");
        var avaliacao = input.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("\nSéries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " - Avaliação: " + s.getAvaliacao()));
    }

    private void listarTop5Series() {
        List<Serie> serieTop5 = repositorio.findTop5ByOrderByAvaliacaoDesc();
        serieTop5.forEach(s ->
                System.out.println("Avaliação: " + s.getAvaliacao() + " - " + s.getTitulo()));
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.print("\nDigite o nome da série para busca: ");
        var nomeSerie = input.nextLine();
        var json = consumoApi.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
//        DadosSerie dadosSerie = getDadosSerie(); // busca a serie na api
        listarSeriesBuscadas();
        System.out.print("\nEscolha uma serie pelo nome: ");
        var nomeSerie = input.nextLine();

//        Optional<Serie> serie = series.stream()
//                .filter(s -> s.getTitulo().toUpperCase().contains(nomeSerie.toUpperCase()))
//                .findFirst();
        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serie.isPresent()) {
            var serieEncontrada = serie.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumoApi.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);

            // Cria um Stream a partir da lista 'temporadas'
            List<Episodio> episodios = temporadas.stream()
                    // Para cada temporada 'd', abre (achata) o Stream de episódios dessa temporada
                    .flatMap(d -> d.episodios().stream()
                            // Mapeia cada episódio 'e' para um novo objeto Episodio, passando o número da temporada 'd.numero()' e o próprio episódio 'e'
                            .map(e -> new Episodio(d.numero(), e)))
                    // Coleta todos os objetos Episodio gerados em uma lista final
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Serie não encontrada!");
        }
    }
}
