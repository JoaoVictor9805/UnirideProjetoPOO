package controller;

import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EdicaoFaculdadeController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/faculdades.txt";

    public EdicaoFaculdadeController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarFaculdade(int indiceLinha, String novoNome, String novaCidade, String novoTurno) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        String novaLinha = novoNome + " | " + novaCidade + " | " + novoTurno;

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
            System.out.println("Faculdade atualizada com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            e.printStackTrace();
        }

        view.TelaGetFaculdades telaLista = new view.TelaGetFaculdades(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}