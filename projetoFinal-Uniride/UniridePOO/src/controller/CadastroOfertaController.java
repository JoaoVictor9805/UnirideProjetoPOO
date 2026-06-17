package controller;

import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroOfertaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/ofertas.txt";
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CadastroOfertaController(Stage palco) {
        this.palco = palco;
    }

    public void salvarOferta(String titulo, String localPartida, String localDestino,
                              LocalDate data, String precoTexto, String vagasTexto) {

        if (titulo.isBlank() || localPartida.isBlank() || localDestino.isBlank()
                || data == null || precoTexto.isBlank() || vagasTexto.isBlank()) {
            mostrarAlerta("Erro de Validação", "Todos os campos devem ser preenchidos.");
            return;
        }

        double preco;
        try {
            preco = Double.parseDouble(precoTexto.replace(",", ".").trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Preço inválido. Use apenas números, ex: 15.50");
            return;
        }

        int vagas;
        try {
            vagas = Integer.parseInt(vagasTexto.trim());
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Formato", "Número de vagas inválido.");
            return;
        }

        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = titulo + " | " + localPartida + " | " + localDestino
                    + " | " + data.format(FORMATO_DATA)
                    + " | " + String.format("%.2f", preco)
                    + " | " + vagas;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Oferta cadastrada com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar a oferta no arquivo ofertas.txt");
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
