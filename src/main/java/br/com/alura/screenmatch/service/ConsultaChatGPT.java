package br.com.alura.screenmatch.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConsultaChatGPT {

    private static final String API_KEY = System.getenv("OPENAI_API_KEY");
    private static final String URL = "https://api.openai.com/v1/chat/completions";

    public static String obterTraducao(String texto) {
        try {
            OkHttpClient client = new OkHttpClient();
            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> body = Map.of(
                    "model", "gpt-4o-mini",
                    "messages", List.of(
                            Map.of(
                                    "role", "system",
                                    "content",
                                    """
                                            Você é um tradutor profissional.
                                            Traduza o texto fornecido para português do Brasil.
                                            
                                            Regras obrigatórias:
                                            - Não continue a história
                                            - Não complete frases
                                            - Não interprete o conteúdo
                                            - Não adicione explicações
                                            - Traduza SOMENTE o texto recebido
                                            - Se o texto terminar incompleto, traduza exatamente até onde o texto existir.                                            
                                            """
                            ),
                            Map.of(
                                    "role", "user",
                                    "content", "Traduza o texto abaixo:\n\n<<<" + texto + ">>>"
                            )
                    ),
                    "temperature", 0.0
            );

            RequestBody requestBody = RequestBody.create(
                    mapper.writeValueAsString(body),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url(URL)
                    .addHeader("Authorization", "Bearer " + API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            String json = response.body().string();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Erro da OpenAI: " + json);
            }

            return mapper
                    .readTree(json)
                    .get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (IOException e) {
            throw new RuntimeException("Erro ao consultar ChatGPT", e);
        }
    }
}
