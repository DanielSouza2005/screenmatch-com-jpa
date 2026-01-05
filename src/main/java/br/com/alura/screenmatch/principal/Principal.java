package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final ConsumoApi consumo = new ConsumoApi();
    private final ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=15fd6310";
    private final List<DadosSerie> dadosSeriesBuscadas = new ArrayList<>();
    private List<Serie> seriesCadastradas = new ArrayList<>();

    @Autowired
    private SerieRepository serieRepository;

    public void exibeMenu() {
        int opcao = -1;

        while (opcao != 0) {
            String menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar séries buscadas \n
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1 -> buscarSerieWeb();
                case 2 -> buscarEpisodioPorSerie();
                case 3 -> listarSeriesBuscadas();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();

        Serie serie = new Serie(dados);
        serieRepository.save(serie);

        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();
        String json = consumo.consultarDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        return conversor.converterDados(json, DadosSerie.class);
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome:");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> serieDigitada = seriesCadastradas.stream()
                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if (serieDigitada.isPresent()) {
            Serie serieEncontrada = serieDigitada.get();

            List<DadosTemporada> temporadas = IntStream
                    .rangeClosed(1, serieEncontrada.getTotalTemporadas())
                    .mapToObj(numeroTemporada -> {
                        String json = consumo.consultarDados(
                                ENDERECO + serieEncontrada.getTitulo().replace(" ", "+")
                                        + "&season=" + numeroTemporada
                                        + API_KEY
                        );
                        return conversor.converterDados(json, DadosTemporada.class);
                    })
                    .toList();

            temporadas.forEach(System.out::println);

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            serieRepository.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada!");
        }
    }

    private void listarSeriesBuscadas() {
        seriesCadastradas = serieRepository.findAll();

        seriesCadastradas.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}