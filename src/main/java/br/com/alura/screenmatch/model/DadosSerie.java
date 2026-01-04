package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.AtoresDeserializer;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String avaliacao,
        @JsonAlias("Genre") String genero,

        @JsonAlias("Actors")
        @JsonDeserialize(using = AtoresDeserializer.class)
        List<String> atores,

        @JsonAlias("Poster") String imagem,
        @JsonAlias("Plot") String sinopse
) {
}
