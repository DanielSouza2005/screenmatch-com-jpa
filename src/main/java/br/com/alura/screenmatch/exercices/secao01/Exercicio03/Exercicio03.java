package br.com.alura.screenmatch.exercices.secao01.Exercicio03;

public class Exercicio03 {
    public static void main(String[] args) {
        System.out.println(obterPrimeiroEUltimoNome("  João Carlos Silva   ")); // Saída: "João Silva"
        System.out.println(obterPrimeiroEUltimoNome("Maria   ")); // Saída: "Maria"
    }

    public static String obterPrimeiroEUltimoNome(String nomeCompleto) {
        if (nomeCompleto == null || nomeCompleto.isBlank()) {
            return "";
        }

        String[] partes = nomeCompleto.trim().split("\\s+");

        if (partes.length == 1) {
            return partes[0];
        }

        return partes[0] + " " + partes[partes.length - 1];
    }
}
