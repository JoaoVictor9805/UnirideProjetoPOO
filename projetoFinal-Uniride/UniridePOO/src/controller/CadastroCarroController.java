package controller;

import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroCarroController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/veiculos.txt";

    public CadastroCarroController(Stage palco) {
        this.palco = palco;
    }

    public void salvarCarro(String marca, String modelo, String ano, String tipoCarroTexto) {
        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = marca + " | " + modelo + " | " + ano + " | " + tipoCarroTexto;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Carro cadastrado com sucesso: " + linha);

        } catch (IOException e) {
            System.err.println("Erro ao salvar o carro no arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        view.TelaGetVeiculos telaLista = new view.TelaGetVeiculos(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}