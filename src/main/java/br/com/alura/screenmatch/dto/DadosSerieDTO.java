package br.com.alura.screenmatch.dto;

import br.com.alura.screenmatch.enums.Categoria;

public record DadosSerieDTO(
        Long id,
        String titulo,
        Integer totalTemporadas,
        Double avaliacao,
        Categoria genero,
        String atores,
        String poster,
        String sinopse
) {
}
