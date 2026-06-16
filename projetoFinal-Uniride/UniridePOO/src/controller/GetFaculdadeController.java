package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Faculdade;

import java.io.*;

public class GetFaculdadeController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/faculdades.txt";

    public GetFaculdadeController(Stage palco) {
        this.palco = palco;
    }

    public ObservableList<Faculdade> carregarFaculdades() {
        ObservableList<Faculdade> lista = FXCollections.observableArrayList();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()) return lista;
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split("\\|");
                if (dados.length == 3) {
                    Faculdade faculdade = new Faculdade(
                            dados[0].trim(),
                            dados[1].trim(),
                            dados[2].trim()
                    );
                    lista.add(faculdade);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar as faculdades: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }
}