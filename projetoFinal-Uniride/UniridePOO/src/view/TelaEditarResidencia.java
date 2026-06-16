package view;

import controller.EdicaoCarroController;
import controller.EdicaoResidenciaController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Residencia;

public class TelaEditarResidencia {
    private Stage palco;
    private EdicaoResidenciaController controller;
    private Residencia residenciaOriginal;
    private int indiceLinha;

    public TelaEditarResidencia(Stage palco, Residencia residencia, int indiceLinha) {
        this.palco = palco;
        this.residenciaOriginal = residencia;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoResidenciaController(palco);
    }

    public Scene desenharTela(){
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Consulta de residência");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));


        Label lblCidade = new Label("Cidade:");
        TextField txtCidade = new TextField(residenciaOriginal.getCidade());
        formulario.add(lblCidade, 0, 0);
        formulario.add(txtCidade, 1, 0);

        Label lblCep = new Label("CEP:");
        TextField txtCep = new TextField(residenciaOriginal.getCep());
        txtCep.setTextFormatter(new TextFormatter<>(change -> change.getText().matches("\\d*") ? change : null));
        formulario.add(lblCep, 0, 1);
        formulario.add(txtCep, 1, 1);

        Label lblRua = new Label("Rua:");
        TextField txtRua = new TextField(residenciaOriginal.getRua());
        formulario.add(lblRua, 0, 2);
        formulario.add(txtRua, 1, 2);

        Label lblBairro = new Label("Bairro:");
        TextField txtBairro = new TextField(residenciaOriginal.getBairro());
        formulario.add(lblBairro, 0, 3);
        formulario.add(txtBairro, 1, 3);

        Label lblNumero = new Label("Numero:");
        TextField txtNumero = new TextField(residenciaOriginal.getNumero());
        txtNumero.setTextFormatter(new TextFormatter<>(change -> change.getText().matches("\\d*") ? change : null));
        formulario.add(lblNumero, 0, 4);
        formulario.add(txtNumero, 1, 4);

        Label lblComplemento = new Label("Complemento:");
        TextField txtComplemento = new TextField(residenciaOriginal.getComplemento());
        formulario.add(lblComplemento, 0, 5);
        formulario.add(txtComplemento, 1, 5);

        Label lblTipoResidencia = new Label("Tipo de residência:");
        ComboBox<String> comboTipoResidencia = new ComboBox<>(FXCollections.observableArrayList("Casa", "Prédio", "Local de trabalho"));
        comboTipoResidencia.setValue(residenciaOriginal.getTipoResidencia());
        formulario.add(lblTipoResidencia, 0, 6);
        formulario.add(comboTipoResidencia, 1, 6);

        Button btnSalvar = new Button("Salvar Alterações");
        Button btnExcluir = new Button("Excluir");
        Button btnCancelar = new Button("Cancelar");
        HBox linha = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linha, 0, 7);

        btnSalvar.setOnAction(e -> {
            controller.atualizarResidencia(indiceLinha, txtCidade.getText(), txtCep.getText(), txtRua.getText(), txtBairro.getText(), txtNumero.getText(), txtComplemento.getText(),comboTipoResidencia.getValue());
        });

        btnExcluir.setOnAction(e -> {
            controller.excluirResidencia(indiceLinha);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetResidencias lista = new TelaGetResidencias(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}