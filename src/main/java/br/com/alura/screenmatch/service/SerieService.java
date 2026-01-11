package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.dto.DadosSerieDTO;
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

    private List<DadosSerieDTO> converterDadosLista(List<Serie> series) {
        return series.stream()
                .map(s -> new DadosSerieDTO(
                        s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getGenero(), s.getAtores(),
                        s.getPoster(), s.getSinopse()))
                .collect(Collectors.toList());
    }

    private DadosSerieDTO converterDados(Serie serie) {
        return new DadosSerieDTO(serie.getId(), serie.getTitulo(), serie.getTotalTemporadas(),
                serie.getAvaliacao(), serie.getGenero(), serie.getAtores(),
                serie.getPoster(), serie.getSinopse());
    }

    public List<DadosSerieDTO> obterTodasAsSeries() {
        return converterDadosLista(serieRepository.findAll());
    }

    public List<DadosSerieDTO> obterTop5Series() {
        return converterDadosLista(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    public List<DadosSerieDTO> obterTop5Lancamentos() {
        return converterDadosLista(serieRepository.encontrarEpisodiosMaisRecentes());
    }

    public DadosSerieDTO obterSeriePorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        return serie.map(this::converterDados).orElse(null);
    }
}
