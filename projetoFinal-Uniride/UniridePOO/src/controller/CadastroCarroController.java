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
        //alerta do campo em branco! só esse if
        if (marca.trim().isEmpty() || modelo.trim().isEmpty() || ano.trim().isEmpty()) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campos Obrigatórios!");
            alerta.setContentText("A marca, o modelo e o ano do veículo não podem ficar em branco.");
            alerta.showAndWait();
            return;
        }
        int anoInt = Integer.parseInt(ano);
        if (anoInt < 1900 || anoInt > 2026){
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campo inválido!");
            alerta.setContentText("Ano acima de 1900 e abaixo de 2027");
            alerta.showAndWait();
            return;
        }
        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = marca + " | " + modelo + " | " + ano + " | " + tipoCarroTexto;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Carro cadastrado com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar o carro no arquivo veiculos.txt ");
            e.printStackTrace();
        }

        view.TelaGetVeiculos telaLista = new view.TelaGetVeiculos(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}