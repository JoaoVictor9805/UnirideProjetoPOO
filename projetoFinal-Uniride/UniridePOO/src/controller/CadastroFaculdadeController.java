package controller;

import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroFaculdadeController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/faculdades.txt";

    public CadastroFaculdadeController(Stage palco) {
        this.palco = palco;
    }

    public void salvarFaculdade(String nome, String cidade, String turno) {
        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = nome + " | " + cidade + " | " + turno; // Monta a linha no formato: "nome | cidade | turno"
            bw.write(linha); // Escreve a linha no arquivo e avança para a próxima
            bw.newLine();
            bw.flush(); // força a gravação imediata em disco

            System.out.println("Faculdade cadastrada com sucesso: " + linha); // Confirma no console (útil para debug)

        } catch (IOException e) {
            // Trata erros de I/O (ex.: sem permissão de escrita)
            System.err.println("Erro ao salvar a faculdade no arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        // Após salvar, redireciona para a tela de listagem de Faculdades
        view.TelaGetFaculdades telaLista = new view.TelaGetFaculdades(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}