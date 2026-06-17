package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Avaliacao;

import java.io.*;

public class GetAvaliacaoController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/avaliacoes.txt";

    public GetAvaliacaoController(Stage palco){
        this.palco = palco;
    }

    public ObservableList<Avaliacao> carregarAvaliacoes(){
        ObservableList<Avaliacao> lista = FXCollections.observableArrayList();
        File arquivo  = new File(CAMINHO_ARQUIVO);

        if(!arquivo.exists()) return lista;
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split("\\|");
                if (dados.length == 3) {
                    Avaliacao avaliacao = new Avaliacao(dados[0].trim(), dados[1].trim(), dados[2].trim());
                    lista.add(avaliacao);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao carregar os dados");
            e.printStackTrace();
        }
        return lista;
    }

}