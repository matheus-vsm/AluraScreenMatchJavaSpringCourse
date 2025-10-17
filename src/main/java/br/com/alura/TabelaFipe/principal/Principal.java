package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.model.Modelos;
import br.com.alura.TabelaFipe.model.Veiculo;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        } else if (opcao.toUpperCase().contains("CAM")) {
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

        System.out.print("\nDigite um trecho do nome do veículo desejado: ");
        var trecho = input.nextLine();
        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(d -> d.nome().toUpperCase().contains(trecho.toUpperCase()))
                .collect(Collectors.toList());
        System.out.println("\nModelos filtrados:");
        modelosFiltrados.forEach(System.out::println);

        System.out.print("\nDigite o código do modelo para consultar os valores: ");
        var codigoModelo = input.nextLine();

        endereco += "/" + codigoModelo + "/anos";
        json = consumoApi.obterDados(endereco);

        List<Dados> anos = conversor.obterListaDados(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();
        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endereco + "/" + anos.get(i).codigo();
            json = consumoApi.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos com avaliações por ano: ");
        veiculos.forEach(System.out::println);
    }
}
