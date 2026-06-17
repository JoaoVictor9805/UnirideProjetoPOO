package controller;

import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EdicaoResidenciaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/residencias.txt";

    public EdicaoResidenciaController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarResidencia(int indiceLinha, String cidade, String cep, String rua, String bairro, String numero, String complemento, String tipoResidencia) {
        if (cidade.trim().isEmpty() || cep.trim().isEmpty() || rua.trim().isEmpty() || numero.trim().isEmpty()) {
            javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Campos Obrigatórios!");
            alerta.setContentText("Cidade, CEP, rua, bairro e número não podem ficar em branco.");
            alerta.showAndWait();
            return;
        }
        File arquivo = new File(CAMINHO_ARQUIVO);
        String novaLinha = cidade + " | " + cep + " | " + rua + " | " + bairro + " | " + numero + " | " + complemento + " | " + tipoResidencia;

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
            System.out.println("Arquivo residencia.txt atualizado com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo");
            e.printStackTrace();
        }

        view.TelaGetResidencias telaLista = new view.TelaGetResidencias(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirResidencia(int indiceLinha) {
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
        view.TelaGetResidencias telaLista = new view.TelaGetResidencias(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}