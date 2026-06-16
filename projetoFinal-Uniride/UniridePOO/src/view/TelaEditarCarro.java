package view;

import controller.EdicaoCarroController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Carro;

public class TelaEditarCarro {
    private Stage palco;
    private EdicaoCarroController controller;
    private Carro carroOriginal;
    private int indiceLinha; // Guardamos o índice aqui

    // Construtor atualizado para receber o carro E a posição dele na tabela
    public TelaEditarCarro(Stage palco, Carro carro, int indiceLinha) {
        this.palco = palco;
        this.carroOriginal = carro;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoCarroController(palco);
    }

    public Scene desenharTela(){
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Editar Veículo");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblMarca = new Label("Marca:");
        TextField txtMarca = new TextField(carroOriginal.getMarca());
        formulario.add(lblMarca, 0, 0);
        formulario.add(txtMarca, 1, 0);

        Label lblModelo = new Label("Modelo:");
        TextField txtModelo = new TextField(carroOriginal.getModelo());
        formulario.add(lblModelo, 0, 1);
        formulario.add(txtModelo, 1, 1);


        Label lblAno = new Label("Ano:");
        TextField txtAno = new TextField(carroOriginal.getAno());
        txtAno.setTextFormatter(new TextFormatter<>(change -> change.getText().matches("\\d*") ? change : null));
        formulario.add(lblAno, 0, 2);
        formulario.add(txtAno, 1, 2);

        Label lblTipoCarro = new Label("Tipo de Carro:");
        ToggleGroup tipoCarro = new ToggleGroup();
        RadioButton carroSuv = new RadioButton("SUV"); carroSuv.setToggleGroup(tipoCarro);
        RadioButton carroEsportivo = new RadioButton("Esportivo"); carroEsportivo.setToggleGroup(tipoCarro);
        RadioButton carroSeda = new RadioButton("Sedã"); carroSeda.setToggleGroup(tipoCarro);
        RadioButton carroOutro = new RadioButton("Outro"); carroOutro.setToggleGroup(tipoCarro);

        String t = carroOriginal.getTipoCarroTexto();
        if (t.equals("SUV")) carroSuv.setSelected(true);
        else if (t.equals("Esportivo")) carroEsportivo.setSelected(true);
        else if (t.equals("Sedã")) carroSeda.setSelected(true);
        else carroOutro.setSelected(true);

        HBox grupoRadio = new HBox(10, carroSuv, carroEsportivo, carroSeda, carroOutro);
        formulario.add(lblTipoCarro, 0, 3);
        formulario.add(grupoRadio, 1, 3);

        Button btnSalvar = new Button("Salvar Alterações");
        Button btnCancelar = new Button("Cancelar");
        HBox linha = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linha, 0, 4);

        btnSalvar.setOnAction(e -> {
            Toggle tgTipoCarro = tipoCarro.getSelectedToggle();
            String tipoCarroTexto = (tgTipoCarro != null) ? ((RadioButton) tgTipoCarro).getText() : "";

            controller.atualizarCarro(indiceLinha, txtMarca.getText(), txtModelo.getText(), txtAno.getText(), tipoCarroTexto);
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