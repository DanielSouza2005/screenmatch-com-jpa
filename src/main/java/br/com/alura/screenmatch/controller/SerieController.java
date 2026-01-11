package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.DadosSerieDTO;
import br.com.alura.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<DadosSerieDTO> obterSeries() {
        return serieRepository.findAll()
                .stream()
                .map(s -> new DadosSerieDTO(
                        s.getId(), s.getTitulo(), s.getTotalTemporadas(),
                        s.getAvaliacao(), s.getGenero(), s.getAtores(),
                        s.getImagem(), s.getSinopse()))
                .collect(Collectors.toList());
    }
}
