//package br.com.alura.screenmatch.principal;
//
//import br.com.alura.screenmatch.enums.Categoria;
//import br.com.alura.screenmatch.model.DadosSerie;
//import br.com.alura.screenmatch.model.DadosTemporada;
//import br.com.alura.screenmatch.model.Episodio;
//import br.com.alura.screenmatch.model.Serie;
//import br.com.alura.screenmatch.repository.SerieRepository;
//import br.com.alura.screenmatch.service.ConsumoApi;
//import br.com.alura.screenmatch.service.ConverteDados;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDate;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//@Component
//public class Principal {
//
//    private final Scanner leitura = new Scanner(System.in);
//    private final ConsumoApi consumo = new ConsumoApi();
//    private final ConverteDados conversor = new ConverteDados();
//    private final String ENDERECO = "https://www.omdbapi.com/?t=";
//    private final String API_KEY = "&apikey=15fd6310";
//    private final List<DadosSerie> dadosSeriesBuscadas = new ArrayList<>();
//    private List<Serie> seriesCadastradas = new ArrayList<>();
//    private Optional<Serie> serieBusca;
//
//    @Autowired
//    private SerieRepository serieRepository;
//
//    public void exibeMenu() {
//        int opcao = -1;
//
//        while (opcao != 0) {
//            String menu = """
//                    1 - Buscar séries
//                    2 - Buscar episódios
//                    3 - Listar séries buscadas
//                    4 - Buscar série por título
//                    5 - Buscar série por ator
//                    6 - TOP 5 Séries
//                    7 - Buscar séries por categoria
//                    8 - Buscar séries por quantidade máxima de temporadas e avaliação mínima
//                    9 - Buscar episódios por trecho
//                    10 - Buscar Top Episódios por Série
//                    11 - Buscar Episódios a partir de um ano \n
//                    0 - Sair
//                    """;
//
//            System.out.println(menu);
//            opcao = leitura.nextInt();
//            leitura.nextLine();
//
//            switch (opcao) {
//                case 1 -> buscarSerieWeb();
//                case 2 -> buscarEpisodioPorSerie();
//                case 3 -> listarSeriesBuscadas();
//                case 4 -> buscarSeriePorTitulo();
//                case 5 -> buscarSeriesPorAtor();
//                case 6 -> buscarTop5Series();
//                case 7 -> buscarSeriesPorCategoria();
//                case 8 -> buscarSeriesPorTotalTemporadasEAvaliacao();
//                case 9 -> buscarEpisodiosPorTrecho();
//                case 10 -> buscarTopEpisodiosPorSerie();
//                case 11 -> buscarEpisodiosAposAno();
//                case 0 -> System.out.println("Saindo...");
//                default -> System.out.println("Opção inválida");
//            }
//        }
//    }
//
//    private void buscarSerieWeb() {
//        DadosSerie dados = getDadosSerie();
//
//        Serie serie = new Serie(dados);
//        serieRepository.save(serie);
//
//        System.out.println(dados);
//    }
//
//    private DadosSerie getDadosSerie() {
//        System.out.println("Digite o nome da série para busca");
//        String nomeSerie = leitura.nextLine();
//        String json = consumo.consultarDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
//        return conversor.converterDados(json, DadosSerie.class);
//    }
//
//    private void buscarEpisodioPorSerie() {
//        listarSeriesBuscadas();
//        System.out.println("Escolha uma série pelo nome:");
//        String nomeSerie = leitura.nextLine();
//
//        Optional<Serie> serieDigitada = seriesCadastradas.stream()
//                .filter(s -> s.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
//                .findFirst();
//
//        if (serieDigitada.isPresent()) {
//            Serie serieEncontrada = serieDigitada.get();
//
//            List<DadosTemporada> temporadas = IntStream
//                    .rangeClosed(1, serieEncontrada.getTotalTemporadas())
//                    .mapToObj(numeroTemporada -> {
//                        String json = consumo.consultarDados(
//                                ENDERECO + serieEncontrada.getTitulo().replace(" ", "+")
//                                        + "&season=" + numeroTemporada
//                                        + API_KEY
//                        );
//                        return conversor.converterDados(json, DadosTemporada.class);
//                    })
//                    .toList();
//
//            temporadas.forEach(System.out::println);
//
//            List<Episodio> episodios = temporadas.stream()
//                    .flatMap(d -> d.episodios().stream()
//                            .map(e -> new Episodio(d.numero(), e)))
//                    .collect(Collectors.toList());
//
//            serieEncontrada.setEpisodios(episodios);
//            serieRepository.save(serieEncontrada);
//        } else {
//            System.out.println("Série não encontrada!");
//        }
//    }
//
//    private void listarSeriesBuscadas() {
//        seriesCadastradas = serieRepository.findAll();
//
//        seriesCadastradas.stream()
//                .sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//    }
//
//    private void buscarSeriePorTitulo() {
//        System.out.println("Escolha uma série por nome: ");
//        String nomeSerie = leitura.nextLine();
//
//        serieBusca = serieRepository.findByTituloContainingIgnoreCase(nomeSerie);
//
//        if (serieBusca.isPresent()) {
//            System.out.println("Dados da série: " + serieBusca.get());
//        } else {
//            System.out.println("Série não encontrada!");
//        }
//    }
//
//    private void buscarSeriesPorAtor() {
//        System.out.println("Digite o nome de um ator de uma série: ");
//        String nomeAtor = leitura.nextLine();
//
//        Optional<List<Serie>> seriesBuscada = serieRepository.findByAtoresContainingIgnoreCase(nomeAtor);
//
//        if (seriesBuscada.isPresent()) {
//            seriesBuscada.get().forEach(System.out::println);
//        } else {
//            System.out.println("Nenhuma série com esse autor foi encontrada!");
//        }
//    }
//
//    private void buscarTop5Series() {
//        Optional<List<Serie>> seriesBuscada = serieRepository.findTop5ByOrderByAvaliacaoDesc();
//
//        if (seriesBuscada.isPresent()) {
//            seriesBuscada.get().forEach(System.out::println);
//        } else {
//            System.out.println("Nenhuma série com esse autor foi encontrada!");
//        }
//    }
//
//    private void buscarSeriesPorCategoria() {
//        System.out.println("Digite o nome de uma categoria/gênero: ");
//        String nomeCategoria = leitura.nextLine();
//
//        Categoria categoria = Categoria.fromPortuguese(nomeCategoria);
//        Optional<List<Serie>> seriesBuscada = serieRepository.findByGenero(categoria);
//
//        if (seriesBuscada.isPresent()) {
//            seriesBuscada.get().forEach(System.out::println);
//        } else {
//            System.out.println("Nenhuma série com essa categoria foi encontrada!");
//        }
//    }
//
//    private void buscarSeriesPorTotalTemporadasEAvaliacao() {
//        System.out.println("Digite uma quantidade máxima de temporadas: ");
//        int qtdeMaximaTemporadas = leitura.nextInt();
//        leitura.nextLine();
//
//        System.out.println("Digite uma avaliação mínima que a Série deve possuir: ");
//        double avaliacaoMinima = leitura.nextDouble();
//        leitura.nextLine();
//
//        Optional<List<Serie>> seriesBuscada = serieRepository.
//                seriesPorTemporadaEAvaliacao(qtdeMaximaTemporadas, avaliacaoMinima);
//
//        if (seriesBuscada.isPresent()) {
//            seriesBuscada.get().forEach(System.out::println);
//        } else {
//            System.out.println("Nenhuma série com esses critérios foi encontrada!");
//        }
//    }
//
//    private void buscarEpisodiosPorTrecho() {
//        System.out.println("Digite um trecho de um episódio: ");
//        String trechoEpisodio = leitura.nextLine();
//
//        Optional<List<Episodio>> episodiosEncontrados = serieRepository.episodiosPorTrecho(trechoEpisodio);
//        if (episodiosEncontrados.isPresent()) {
//            episodiosEncontrados.get().forEach(e -> System.out.printf(
//                    "Série: %s, Temporada %s - Episódio %s - %s \n", e.getSerie().getTitulo(), e.getTemporada(),
//                    e.getNumeroEpisodio(), e.getTitulo()
//            ));
//        } else {
//            System.out.println("Nenhum episódio foi encontrado!");
//        }
//    }
//
//    private void buscarTopEpisodiosPorSerie() {
//        buscarSeriePorTitulo();
//
//        if (serieBusca.isPresent()) {
//            System.out.println("Digite quantos episódios vão aparecer: ");
//            int qtdTopEpisodio = leitura.nextInt();
//            leitura.nextLine();
//
//            Serie serie = serieBusca.get();
//            Optional<List<Episodio>> topEpisodios = serieRepository.topEpisodiosPorSerie(serie, qtdTopEpisodio);
//
//            if (topEpisodios.isPresent()) {
//                topEpisodios.get().forEach(e -> System.out.printf(
//                        "Série: %s, Temporada %s - Episódio %s - %s Avaliação %s \n", e.getSerie().getTitulo(), e.getTemporada(),
//                        e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()
//                ));
//            } else {
//                System.out.println("Nenhum episódio foi encontrado!");
//            }
//
//        }
//    }
//
//    private void buscarEpisodiosAposAno() {
//        buscarSeriePorTitulo();
//
//        if (serieBusca.isPresent()) {
//            System.out.println("Digite o ano mínimo de lançamento dos episódios: ");
//            int ano = leitura.nextInt();
//            leitura.nextLine();
//
//            Serie serie = serieBusca.get();
//            LocalDate dataAnoDigitado = LocalDate.of(ano, 1, 1);
//
//            Optional<List<Episodio>> episodiosAno = serieRepository.episodiosPorSerieEAno(serie, dataAnoDigitado);
//            if (episodiosAno.isPresent()) {
//                episodiosAno.get().forEach(e -> System.out.printf(
//                        "Série: %s, Temporada %s - Episódio %s - %s Avaliação %s - Data Lançamento %s \n", e.getSerie().getTitulo(), e.getTemporada(),
//                        e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao(), e.getDataLancamento()
//                ));
//            } else {
//                System.out.println("Nenhum episódio foi encontrado!");
//            }
//        }
//    }
//}