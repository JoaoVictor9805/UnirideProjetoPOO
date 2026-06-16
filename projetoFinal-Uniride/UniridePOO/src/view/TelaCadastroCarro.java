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

public class TelaCadastroCarro {
    private Stage palco;
    private controller.CadastroCarroController controller;

    public TelaCadastroCarro(Stage palco) {
        this.palco = palco;
        this.controller = new controller.CadastroCarroController(palco);
    }

    public Scene desenharTela(){
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Veículo");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblMarca = new Label("Marca do carro:");
        TextField txtMarca = new TextField();
        txtMarca.setPromptText("Digite a marca");
        formulario.add(lblMarca, 0, 0);
        formulario.add(txtMarca, 1, 0);

        Label lblModelo = new Label("Modelo:");
        TextField txtModelo = new TextField();
        txtModelo.setPromptText("Digite o modelo do carro");
        formulario.add(lblModelo, 0, 1);
        formulario.add(txtModelo, 1, 1);

        Label lblAno = new Label("Ano:");
        TextField txtAno = new TextField();
        txtAno.setPromptText("Digite o ano do carro");
        txtAno.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*")){
                return change;
            }
            return null;
        }));
        formulario.add(lblAno, 0, 2);
        formulario.add(txtAno, 1, 2);


        Label lblTipoCarro = new Label(("Tipo do carro:"));
        final ToggleGroup tipoCarro = new ToggleGroup();

        RadioButton carroSuv = new RadioButton("SUV");
        carroSuv.setToggleGroup(tipoCarro);

        RadioButton carroEsportivo = new RadioButton("Esportivo");
        carroEsportivo.setToggleGroup(tipoCarro);

        RadioButton carroSeda = new RadioButton("Sedã");
        carroSeda.setToggleGroup(tipoCarro);

        RadioButton carroOutro = new RadioButton("Outro");
        carroOutro.setToggleGroup(tipoCarro);
        carroOutro.setSelected(true);

        HBox grupoRadio = new HBox(10, carroSuv, carroEsportivo, carroSeda, carroOutro);
        formulario.add(lblTipoCarro, 0, 3);
        formulario.add(grupoRadio, 1, 3);

        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        HBox linha = new HBox(9,  btnSalvar, btnCancelar);
        formulario.add(linha, 0, 4);

        btnSalvar.setOnAction(e -> {
            String marca = txtMarca.getText();
            String modelo = txtModelo.getText();
            String ano = txtAno.getText();
            Toggle tgTipoCarro = tipoCarro.getSelectedToggle();
            String tipoCarroTexto = (tgTipoCarro != null) ? ((RadioButton) tgTipoCarro).getText() : "";
            controller.salvarCarro(marca, modelo, ano, tipoCarroTexto);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetVeiculos lista = new TelaGetVeiculos(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}