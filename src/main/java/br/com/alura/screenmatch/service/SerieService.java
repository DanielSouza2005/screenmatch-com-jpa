package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.DadosEpisodioDTO;
import br.com.alura.screenmatch.dto.DadosSerieDTO;
import br.com.alura.screenmatch.enums.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    private List<DadosSerieDTO> converterDadosSerieLista(List<Serie> series) {
        return series.stream()
                .map(s -> new DadosSerieDTO(
                        s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getGenero(), s.getAtores(),
                        s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    private DadosSerieDTO converterDadosSerie(Serie serie) {
        return new DadosSerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(),
                serie.getAvaliacao(), serie.getGenero(), serie.getAtores(),
                serie.getPoster(), serie.getSinopse());
    }

    private List<DadosEpisodioDTO> converterDadosEpisodioLista(List<Episodio> episodios) {
        return episodios.stream()
                .map(e -> new DadosEpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                .collect(Collectors.toList());
    }

    public List<DadosSerieDTO> obterTodasAsSeries() {
        return converterDadosSerieLista(serieRepository.findAll());
    }

    public List<DadosSerieDTO> obterTop5Series() {
        return converterDadosSerieLista(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<DadosSerieDTO> obterTop5Lancamentos() {
        return converterDadosSerieLista(serieRepository.encontrarEpisodiosMaisRecentes());
    }

    public DadosSerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        return serie.map(this::converterDadosSerie).orElse(null);
    }

    public List<DadosEpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new DadosEpisodioDTO(e.getTemporada(), e.getNumeroEpisodio(), e.getTitulo()))
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public List<DadosEpisodioDTO> obterEpisodiosPorTemporada(Long id, Integer nroTemporada) {
        List<Episodio> episodios = serieRepository.encontrarEpisodiosPorTemporada(id, nroTemporada);

        return converterDadosEpisodioLista(episodios);
    }

    public List<DadosSerieDTO> obterSeriesPorCategoria(String categoria) {
        Categoria categoriaConvertida = Categoria.fromPortuguese(categoria);
        return converterDadosSerieLista(serieRepository.findByGenero(categoriaConvertida));
    }

    public List<DadosEpisodioDTO> obterTopEpisodiosPorSerie(Long id) {
        List<Episodio> episodios = serieRepository.encontrarTop5EpisodiosPorSerie(id);

        return converterDadosEpisodioLista(episodios);
    }
}
