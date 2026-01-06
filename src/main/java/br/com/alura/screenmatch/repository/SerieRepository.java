package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.enums.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);
    Optional<List<Serie>> findByAtoresContainingIgnoreCase(String nomeAtor);
    Optional<List<Serie>> findTop5ByOrderByAvaliacaoDesc();
    Optional<List<Serie>> findByGenero(Categoria categoria);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :avaliacao ORDER BY s.avaliacao DESC")
    Optional<List<Serie>> seriesPorTemporadaEAvaliacao(int totalTemporadas, double avaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoEpisodio% ")
    Optional<List<Episodio>> episodiosPorTrecho(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT :qtdTopEpisodio")
    Optional<List<Episodio>> topEpisodiosPorSerie(Serie serie, int qtdTopEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie AND e.dataLancamento >= :dataAnoDigitado")
    Optional<List<Episodio>> episodiosPorSerieEAno(Serie serie, LocalDate dataAnoDigitado);
}
