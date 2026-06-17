package controller;

import javafx.stage.Stage;
import model.Carro;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EdicaoCarroController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/veiculos.txt";

    public EdicaoCarroController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarCarro(int indiceLinha, String novaMarca, String novaModelo, String novoAno, String novoTipo) {
        if (novaMarca.trim().isEmpty() || novaModelo.trim().isEmpty() || novoAno.trim().isEmpty()) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campos Obrigatórios!");
            alerta.setContentText("A marca, o modelo e o ano do veículo não podem ficar em branco.");
            alerta.showAndWait();
            return;
        }
        int anoInt = Integer.parseInt(novoAno);
        if (anoInt < 1900 || anoInt > 2026){
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campo inválido!");
            alerta.setContentText("Ano acima de 1900 e abaixo de 2027");
            alerta.showAndWait();
            return;
        }
        File arquivo = new File(CAMINHO_ARQUIVO);
        String novaLinha = novaMarca + " | " + novaModelo + " | " + novoAno + " | " + novoTipo;

        List<String> linhasDoArquivo = new ArrayList<>();

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasDoArquivo.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (indiceLinha >= 0 && indiceLinha < linhasDoArquivo.size()) {
            linhasDoArquivo.set(indiceLinha, novaLinha);
        }

        try (FileWriter fw = new FileWriter(arquivo, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String l : linhasDoArquivo) {
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
            System.out.println("Arquivo atualizado com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo");
            e.printStackTrace();
        }

        view.TelaGetVeiculos telaLista = new view.TelaGetVeiculos(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirCarro(int indiceLinha) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        List<String> linhasDoArquivo = new ArrayList<>();
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            int contador = 0;
            while ((linha = br.readLine()) != null) {
                if (contador != indiceLinha) {
                    linhasDoArquivo.add(linha);
                }
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (FileWriter fw = new FileWriter(arquivo, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String l : linhasDoArquivo) {
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.TelaGetVeiculos telaLista = new view.TelaGetVeiculos(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}