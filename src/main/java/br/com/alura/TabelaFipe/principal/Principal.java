package br.com.alura.TabelaFipe.principal;

import br.com.alura.TabelaFipe.model.Dados;
import br.com.alura.TabelaFipe.service.ConsumoApi;
import br.com.alura.TabelaFipe.service.ConverteDados;

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
        Dados dados = conversor.obterDados(json, Dados.class);
    }
}
