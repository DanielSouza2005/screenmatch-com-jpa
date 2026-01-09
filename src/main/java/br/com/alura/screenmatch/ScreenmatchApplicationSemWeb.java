//package br.com.alura.screenmatch;
//
//import br.com.alura.screenmatch.principal.Principal;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScreenmatchApplicationSemWeb implements CommandLineRunner {
//
//    private final Principal principal;
//
//    public ScreenmatchApplicationSemWeb(Principal principal) {
//        this.principal = principal;
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(ScreenmatchApplicationSemWeb.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        principal.exibeMenu();
//    }
//}
