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