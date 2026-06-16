package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Carro;

import java.io.*;
import java.util.Scanner;

public class GetCarroController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/veiculos.txt";

    public GetCarroController(Stage palco){
        this.palco = palco;
    }

    public ObservableList<Carro> carregarCarros(){
        ObservableList<Carro> lista = FXCollections.observableArrayList();
        File arquivo  = new File(CAMINHO_ARQUIVO);

        if(!arquivo.exists()) return lista;
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split("\\|");
                if (dados.length == 4) {
                    Carro carro = new Carro(dados[0], dados[1], dados[2].trim(), dados[3].trim());
                    lista.add(carro);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao carregar os dados");
            e.printStackTrace();
        }
        return lista;
    }

}
