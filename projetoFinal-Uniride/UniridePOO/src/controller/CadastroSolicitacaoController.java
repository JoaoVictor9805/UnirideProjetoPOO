package controller;

import javafx.stage.Stage;
import javafx.scene.control.Alert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastroSolicitacaoController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/solicitacoes.txt";
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CadastroSolicitacaoController(Stage palco) {
        this.palco = palco;
    }

    public void salvarSolicitacao(String titulo, String localPartida,
                                   String localDestino, LocalDate data) {

        if (titulo.isBlank() || localPartida.isBlank()
                || localDestino.isBlank() || data == null) {
            mostrarAlerta("Erro de Validação", "Todos os campos devem ser preenchidos.");
            return;
        }

        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = titulo + " | " + localPartida + " | "
                    + localDestino + " | " + data.format(FORMATO_DATA);
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Solicitação cadastrada com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar a solicitação no arquivo solicitacoes.txt");
            e.printStackTrace();
        }

        view.TelaGetSolicitacoes telaLista = new view.TelaGetSolicitacoes(this.palco);
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
