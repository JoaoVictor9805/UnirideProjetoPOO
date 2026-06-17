package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;

public class TelaCadastroOferta {
    private Stage palco;
    private controller.CadastroOfertaController controller;

    public TelaCadastroOferta(Stage palco) {
        this.palco = palco;
        this.controller = new controller.CadastroOfertaController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Oferta");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();
        txtTitulo.setPromptText("Digite o título da oferta");
        formulario.add(lblTitulo, 0, 0);
        formulario.add(txtTitulo, 1, 0);

        Label lblPartida = new Label("Local de Partida:");
        TextField txtPartida = new TextField();
        txtPartida.setPromptText("Digite o local de partida");
        formulario.add(lblPartida, 0, 1);
        formulario.add(txtPartida, 1, 1);

        Label lblDestino = new Label("Local de Destino:");
        TextField txtDestino = new TextField();
        txtDestino.setPromptText("Digite o local de destino");
        formulario.add(lblDestino, 0, 2);
        formulario.add(txtDestino, 1, 2);

        Label lblData = new Label("Data:");
        DatePicker datePicker = new DatePicker();
        formulario.add(lblData, 0, 3);
        formulario.add(datePicker, 1, 3);

        Label lblPreco = new Label("Preço (R$):");
        TextField txtPreco = new TextField();
        txtPreco.setPromptText("Ex: 15.50");
        txtPreco.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("[\\d.,]*")) {
                return change;
            }
            return null;
        }));
        formulario.add(lblPreco, 0, 4);
        formulario.add(txtPreco, 1, 4);

        Label lblVagas = new Label("Vagas disponíveis:");
        TextField txtVagas = new TextField();
        txtVagas.setPromptText("Ex: 3");
        txtVagas.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")) {
                return change;
            }
            return null;
        }));
        formulario.add(lblVagas, 0, 5);
        formulario.add(txtVagas, 1, 5);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        HBox linha = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linha, 0, 6);

        btnSalvar.setOnAction(e -> {
            String titulo = txtTitulo.getText();
            String partida = txtPartida.getText();
            String destino = txtDestino.getText();
            LocalDate data = datePicker.getValue();
            String preco = txtPreco.getText();
            String vagas = txtVagas.getText();
            controller.salvarOferta(titulo, partida, destino, data, preco, vagas);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetOfertas lista = new TelaGetOfertas(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}
