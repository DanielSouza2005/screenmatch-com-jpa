package br.com.alura.screenmatch.exercices.secao01.Exercicio02;

import java.util.Optional;

public class Exercicio02 {
    public static void main(String[] args) {
        System.out.println(processaNumero(Optional.of(5))); // Saída: Optional[25]
        System.out.println(processaNumero(Optional.of(-3))); // Saída: Optional.empty
        System.out.println(processaNumero(Optional.of(0))); // Saída: Optional[0]
        System.out.println(processaNumero(Optional.empty())); // Saída: Optional.empty
    }

    public static Optional<Integer> processaNumero(Optional<Integer> numero) {
        return numero.filter(n -> n >= 0).map(n -> n * n);
    }
}
