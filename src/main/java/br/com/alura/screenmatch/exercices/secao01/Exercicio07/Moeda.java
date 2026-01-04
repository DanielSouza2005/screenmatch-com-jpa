package br.com.alura.screenmatch.exercices.secao01.Exercicio07;

public enum Moeda {
    REAL(1.0),
    DOLAR(5.1),
    EURO(5.5);

    private final double cotacao;

    Moeda(double cotacao) {
        this.cotacao = cotacao;
    }

    public double getCotacao() {
        return cotacao;
    }

    public double converterPara(double valor) {
        return valor / this.cotacao;
    }
}
