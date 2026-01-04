package br.com.alura.screenmatch.exercices.secao01.Exercicio05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Exercicio05 {
    public static void main(String[] args) {
        List<String> emails = Arrays.asList("TESTE@EXEMPLO.COM", "exemplo@Java.com ", "Usuario@teste.Com");
        System.out.println(converterEmails(emails));
        // Saída: ["teste@exemplo.com", "exemplo@java.com", "usuario@teste.com"]
    }

    public static List<String> converterEmails(List<String> emails) {
        return emails.stream()
                .map(email -> email.toLowerCase().trim())
                .collect(Collectors.toList());
    }
}
