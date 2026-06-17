package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Solicitacao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GetSolicitacaoController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/solicitacoes.txt";
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public GetSolicitacaoController(Stage palco) {
        this.palco = palco;
    }

    public ObservableList<Solicitacao> carregarSolicitacoes() {
        ObservableList<Solicitacao> lista = FXCollections.observableArrayList();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split("\\|");
                if (dados.length == 4) {
                    try {
                        String titulo       = dados[0].trim();
                        String localPartida = dados[1].trim();
                        String localDestino = dados[2].trim();
                        LocalDate data      = LocalDate.parse(dados[3].trim(), FORMATO_DATA);

                        Solicitacao solicitacao = new Solicitacao(titulo, localPartida, localDestino, data);
                        lista.add(solicitacao);
                    } catch (DateTimeParseException e) {
                        System.out.println("Linha inválida ignorada: " + linha);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados de solicitações.");
            e.printStackTrace();
        }

        return lista;
    }
}
