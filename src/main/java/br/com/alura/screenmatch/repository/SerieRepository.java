package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.enums.Categoria;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<List<Serie>> findByAtoresContainingIgnoreCase(String nomeAtor);
    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();
    Optional<List<Serie>> findByGenero(Categoria categoria);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao ORDER BY s.avaliacao DESC")
    Optional<List<Serie>> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);
}
