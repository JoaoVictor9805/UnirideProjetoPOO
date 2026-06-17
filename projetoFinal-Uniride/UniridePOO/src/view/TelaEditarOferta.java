package view;

import controller.EdicaoOfertaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Oferta;

public class TelaEditarOferta {
    private Stage palco;
    private EdicaoOfertaController controller;
    private Oferta ofertaOriginal;
    private int indiceLinha;

    public TelaEditarOferta(Stage palco, Oferta oferta, int indiceLinha) {
        this.palco = palco;
        this.ofertaOriginal = oferta;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoOfertaController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Consulta de Oferta");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField(ofertaOriginal.getTitulo());
        formulario.add(lblTitulo, 0, 0);
        formulario.add(txtTitulo, 1, 0);

        Label lblPartida = new Label("Local de Partida:");
        TextField txtPartida = new TextField(ofertaOriginal.getLocalPartida());
        formulario.add(lblPartida, 0, 1);
        formulario.add(txtPartida, 1, 1);

        Label lblDestino = new Label("Local de Destino:");
        TextField txtDestino = new TextField(ofertaOriginal.getLocalDestino());
        formulario.add(lblDestino, 0, 2);
        formulario.add(txtDestino, 1, 2);

        Label lblData = new Label("Data:");
        DatePicker datePicker = new DatePicker(ofertaOriginal.getDataPartida());
        formulario.add(lblData, 0, 3);
        formulario.add(datePicker, 1, 3);

        Label lblPreco = new Label("Preço (R$):");
        TextField txtPreco = new TextField(String.format("%.2f", ofertaOriginal.getPreco()));
        txtPreco.setTextFormatter(new TextFormatter<>(change ->
                change.getText().matches("[\\d.,]*") ? change : null));
        formulario.add(lblPreco, 0, 4);
        formulario.add(txtPreco, 1, 4);

        Label lblVagas = new Label("Vagas disponíveis:");
        TextField txtVagas = new TextField(String.valueOf(ofertaOriginal.getVagas()));
        txtVagas.setTextFormatter(new TextFormatter<>(change ->
                change.getText().matches("\\d*") ? change : null));
        formulario.add(lblVagas, 0, 5);
        formulario.add(txtVagas, 1, 5);

        Button btnSalvar   = new Button("Salvar Alterações");
        Button btnExcluir  = new Button("Excluir");
        Button btnCancelar = new Button("Voltar");
        HBox linha = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linha, 0, 6);

        btnSalvar.setOnAction(e ->
                controller.atualizarOferta(
                        indiceLinha,
                        txtTitulo.getText(),
                        txtPartida.getText(),
                        txtDestino.getText(),
                        datePicker.getValue(),
                        txtPreco.getText(),
                        txtVagas.getText()
                )
        );

        btnExcluir.setOnAction(e ->
                controller.excluirOferta(indiceLinha)
        );

        btnCancelar.setOnAction(e -> {
            TelaGetOfertas lista = new TelaGetOfertas(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}
