package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Passageiro;

import java.io.*;

public class GetPassageirosController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/passageiros.txt";

    public GetPassageirosController(Stage palco){
        this.palco = palco;
    }

    public ObservableList<Passageiro> carregarPassageiros(){
        ObservableList<Passageiro> lista = FXCollections.observableArrayList();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if(!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                // Lendo e quebrando no |
                String[] dados = linha.split("\\|");
                if (dados.length >= 4) {
                    // O .trim() é vital aqui para remover os espaços em volta do |
                    Passageiro passageiro = new Passageiro(dados[0].trim(), dados[1].trim(), dados[2].trim(), dados[3].trim());
                    lista.add(passageiro);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao carregar os dados de passageiros");
            e.printStackTrace();
        }
        return lista;
    }

}