package br.com.alura.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    // metodo estático que recebe um texto (em outro idioma) e retorna sua tradução para o português
    public static String obterTraducao(String texto) {
        // cria um objeto da classe OpenAiService, responsável por se conectar à API da OpenAI
        // (é necessário informar a sua chave de API entre aspas)
        OpenAiService service = new OpenAiService("chave token openai");

        // constrói uma requisição de completions (pedido de resposta de texto) para o modelo GPT
        CompletionRequest requisicao = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct") // define o modelo da OpenAI que será usado para gerar a resposta
                .prompt("traduza o texto a seguir para o portugues: " + texto) // define a instrução (prompt) que será enviada para o modelo
                .maxTokens(1000) // define o número máximo de tokens (palavras/frases) que o modelo pode gerar na resposta
                .temperature(0.7) // controla a “criatividade” do modelo (0 = mais precisa, 1 = mais criativa)
                .build(); // finaliza a construção do objeto requisicao

        // envia a requisição para a API da OpenAI e recebe a resposta
        var resposta = service.createCompletion(requisicao);

        // retorna o texto da primeira opção de resposta gerada pelo modelo
        return resposta.getChoices().get(0).getText();
    }
}

