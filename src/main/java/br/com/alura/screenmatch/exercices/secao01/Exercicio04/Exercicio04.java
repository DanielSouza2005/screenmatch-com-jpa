package br.com.alura.screenmatch.exercices.secao01.Exercicio04;

public class Exercicio04 {
    public static void main(String[] args) {
        System.out.println(ehPalindromo("socorram me subi no onibus em marrocos")); // Saída: true
        System.out.println(ehPalindromo("Java"));
    }

    public static boolean ehPalindromo(String palavra) {
        return palavra.replace(" ", "").equalsIgnoreCase(
                new StringBuilder(palavra).reverse().toString().replace(" ", "")
        );
    }
}
