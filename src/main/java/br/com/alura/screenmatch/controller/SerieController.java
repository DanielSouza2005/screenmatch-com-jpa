package br.com.alura.screenmatch.controller;

import br.com.alura.screenmatch.dto.DadosEpisodioDTO;
import br.com.alura.screenmatch.dto.DadosSerieDTO;
import br.com.alura.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping
    public List<DadosSerieDTO> obterTodasAsSeries() {
        return serieService.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<DadosSerieDTO> obterTop5Series() {
        return serieService.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<DadosSerieDTO> obterTop5Lancamentos() {
        return serieService.obterTop5Lancamentos();
    }

    @GetMapping("/{id}")
    public DadosSerieDTO obterSeriePorId(@PathVariable Long id) {
        return serieService.obterSeriePorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<DadosEpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return serieService.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{nroTemporada}")
    public List<DadosEpisodioDTO> obterTodasTemporadas(@PathVariable Long id,
                                                       @PathVariable Integer nroTemporada) {
        return serieService.obterEpisodiosPorTemporada(id, nroTemporada);
    }

    @GetMapping("/{id}/temporadas/top")
    public List<DadosEpisodioDTO> obterTopEpisodiosPorSerie(@PathVariable Long id) {
        return serieService.obterTopEpisodiosPorSerie(id);
    }

    @GetMapping("/categoria/{categoria}")
    public List<DadosSerieDTO> obterSeriesPorCategoria(@PathVariable String categoria) {
        return serieService.obterSeriesPorCategoria(categoria);
    }
}
