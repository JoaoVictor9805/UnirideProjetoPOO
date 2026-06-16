package view;

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

public class TelaCadastroResidencia {
    private Stage palco;
    private controller.CadastroResidenciaController controller;

    public TelaCadastroResidencia(Stage palco) {
        this.palco = palco;
        this.controller = new controller.CadastroResidenciaController(palco);
    }

    public Scene desenharTela(){
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Residências");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblCidade = new Label("Cidade:");
        TextField txtCidade = new TextField();
        txtCidade.setPromptText("Digite a cidade");
        formulario.add(lblCidade, 0, 0);
        formulario.add(txtCidade, 1, 0);

        Label lblCep = new Label("CEP:");
        TextField txtCep = new TextField();
        txtCep.setPromptText("Digite o CEP da rua(somente números)");
        txtCep.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")){
                return change;
            }
            return null;
        }));
        formulario.add(lblCep, 0, 1);
        formulario.add(txtCep, 1, 1);

        Label lblRua = new Label("Rua:");
        TextField txtRua = new TextField();
        txtRua.setPromptText("Digite a Rua");
        formulario.add(lblRua, 0, 2);
        formulario.add(txtRua, 1, 2);

        Label lblBairro = new Label("Bairro:");
        TextField txtBairro = new TextField();
        txtBairro.setPromptText("Digite o bairro");
        formulario.add(lblBairro, 0, 3);
        formulario.add(txtBairro, 1, 3);

        Label lblNumero = new Label("Número:");
        TextField txtNumero = new TextField();
        txtNumero.setPromptText("Digite o número da residência(somente números)");
        txtNumero.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")){
                return change;
            }
            return null;
        }));
        formulario.add(lblNumero, 0, 4);
        formulario.add(txtNumero, 1, 4);

        Label lblComplemento = new Label("Complemento:");
        TextField txtComplemento = new TextField();
        txtComplemento.setPromptText("Pontos de referência(Ex: padaria, nome do condomínio, academia)");
        formulario.add(lblComplemento, 0, 5);
        formulario.add(txtComplemento, 1, 5);

        Label lblTipoResidencia = new Label("Tipo de residência:");
        ComboBox<String> comboTipoResidencia = new ComboBox<>(FXCollections.observableArrayList("Casa", "Prédio", "Local de trabalho"));
        comboTipoResidencia.getSelectionModel().selectFirst();
        formulario.add(lblTipoResidencia, 0, 6);
        formulario.add(comboTipoResidencia, 1, 6);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        HBox linha = new HBox(9,  btnSalvar, btnCancelar);
        formulario.add(linha, 0, 7);

        btnSalvar.setOnAction(e -> {
            String cidade = txtCidade.getText();
            String cep = txtCep.getText();
            String rua = txtRua.getText();
            String  bairro = txtBairro.getText();
            String numero = txtNumero.getText();
            String complemento = txtComplemento.getText();
            String tipoResidencia = comboTipoResidencia.getValue();
            controller.salvarResidencia(cidade, cep, rua, bairro, numero, complemento, tipoResidencia);
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