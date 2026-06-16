package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Residencia;

import java.io.*;

public class GetResidenciaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/residencias.txt";

    public GetResidenciaController(Stage palco){
        this.palco = palco;
    }

    public ObservableList<Residencia> carregarResidencias(){
        ObservableList<Residencia> lista = FXCollections.observableArrayList();
        File arquivo  = new File(CAMINHO_ARQUIVO);

        if(!arquivo.exists()) return lista;
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split("\\|");
                if (dados.length == 7) {
                    Residencia residencia = new Residencia(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], dados[6]);
                    lista.add(residencia);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao carregar os dados");
            e.printStackTrace();
        }
        return lista;
    }

}
