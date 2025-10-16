package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.Comparator;
import java.util.Scanner;

public class Principal {
    private Scanner input = new Scanner(System.in);

    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu() {
        System.out.println("""
                -==== OPÇÕES ====-
                - Carro
                - Moto
                - Caminhão
                """);
        System.out.print("Digite uma das opções para consultar os valores: ");
        String opcao = input.nextLine();
        String endereco;

        if (opcao.toUpperCase().contains("CARR")) {
            endereco = ENDERECO_BASE + "carros/marcas";
        } else if (opcao.toUpperCase().contains("MOT")) {
            endereco = ENDERECO_BASE + "motos/marcas";
        } else if (opcao.toUpperCase().contains("CAM")){
            endereco = ENDERECO_BASE + "caminhoes/marcas";
        } else {
            System.out.println("Opção Invalida!");
            return;
        }

        var json = consumoApi.obterDados(endereco);
        var marcas = conversor.obterListaDados(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.print("\nDigite o código da marca desejada: ");
        var codicoMarca = input.nextLine();

        endereco += "/" + codicoMarca + "/modelos";
        json = consumoApi.obterDados(endereco);

        var modeloLista = conversor.obterDados(json, Modelos.class); //pois o Modelo já está representado como uma Lista
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);
    }
}
