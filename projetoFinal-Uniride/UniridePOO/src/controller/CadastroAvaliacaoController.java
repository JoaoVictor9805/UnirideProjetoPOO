package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroAvaliacaoController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/avaliacoes.txt";
    private final String CAMINHO_USUARIOS = "src/dao/passageiros.txt";

    public CadastroAvaliacaoController(Stage palco) {
        this.palco = palco;
    }

    public ObservableList<String> carregarUsuarios() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        File arquivo = new File(CAMINHO_USUARIOS);

        if (!arquivo.exists()) return lista;
        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                lista.add(dados[0].trim());
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os usuários");
            e.printStackTrace();
        }
        return lista;
    }

    public void salvarAvaliacao(String usuario, String nota, String comentario) {
        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = usuario + " | " + nota + " | " + comentario;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Avaliação cadastrada com sucesso: " + linha);

        } catch (IOException e) {
            System.err.println("Erro ao salvar a avaliação no arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        view.TelaGetAvaliacoes telaLista = new view.TelaGetAvaliacoes(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}