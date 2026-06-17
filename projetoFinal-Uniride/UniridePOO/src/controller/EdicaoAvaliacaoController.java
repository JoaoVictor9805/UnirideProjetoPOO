package controller;

import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EdicaoAvaliacaoController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/avaliacoes.txt";

    public EdicaoAvaliacaoController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarAvaliacao(int indiceLinha, String novoUsuario, String novaNota, String novoComentario) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        String novaLinha = novoUsuario + " | " + novaNota + " | " + novoComentario;

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

        view.TelaGetAvaliacoes telaLista = new view.TelaGetAvaliacoes(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirAvaliacao(int indiceLinha) {
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
        view.TelaGetAvaliacoes telaLista = new view.TelaGetAvaliacoes(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}