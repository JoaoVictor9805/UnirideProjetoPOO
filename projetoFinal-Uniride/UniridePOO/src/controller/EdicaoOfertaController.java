package controller;

import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EdicaoOfertaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/ofertas.txt";
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public EdicaoOfertaController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarOferta(int indiceLinha, String novoTitulo, String novoLocalPartida,
                                 String novoLocalDestino, LocalDate novaData,
                                 String novoPrecoTexto, String novasVagasTexto) {

        if (novaData == null) {
            mostrarAlerta("Erro de Validação", "A data deve ser preenchida.");
            return;
        }

        double novoPreco;
        try {
            novoPreco = Double.parseDouble(novoPrecoTexto.replace(",", ".").trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Preço inválido. Use apenas números, ex: 15.50");
            return;
        }

        int novasVagas;
        try {
            novasVagas = Integer.parseInt(novasVagasTexto.trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Número de vagas inválido.");
            return;
        }

        String novaLinha = novoTitulo + " | " + novoLocalPartida + " | " + novoLocalDestino
                + " | " + novaData.format(FORMATO_DATA)
                + " | " + String.format("%.2f", novoPreco)
                + " | " + novasVagas;

        File arquivo = new File(CAMINHO_ARQUIVO);
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
            System.out.println("Oferta atualizada com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo de ofertas.");
            e.printStackTrace();
        }

        view.TelaGetOfertas telaLista = new view.TelaGetOfertas(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirOferta(int indiceLinha) {
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

        view.TelaGetOfertas telaLista = new view.TelaGetOfertas(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
