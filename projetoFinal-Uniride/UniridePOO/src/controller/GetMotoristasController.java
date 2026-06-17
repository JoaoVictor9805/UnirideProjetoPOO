package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Motorista;

import java.io.*;

public class GetMotoristasController {

    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/motoristas.txt";

    public GetMotoristasController(Stage palco){
        this.palco = palco;
    }

    public ObservableList<Motorista> carregarMotoristas(){
        ObservableList<Motorista> lista = FXCollections.observableArrayList();
        File arquivo = new File(CAMINHO_ARQUIVO);

        if(!arquivo.exists()) return lista;

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                // Lê usando o separador Pipe (|)
                String[] dados = linha.split("\\|");

                // Agora verificamos se tem os 7 campos do Motorista
                if (dados.length >= 7) {
                    Motorista motorista = new Motorista(
                            dados[0].trim(), dados[1].trim(), dados[2].trim(), dados[3].trim(),
                            dados[4].trim(), dados[5].trim(), dados[6].trim()
                    );
                    lista.add(motorista);
                }
            }
        } catch (IOException e){
            System.out.println("Erro ao carregar os dados de motoristas");
            e.printStackTrace();
        }
        return lista;
    }
}