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

public class TelaCadastroFaculdade {
    private Stage palco;
    private controller.CadastroFaculdadeController controller;

    public TelaCadastroFaculdade(Stage palco) {
        this.palco      = palco;
        this.controller = new controller.CadastroFaculdadeController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Faculdade");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15); // espaço horizontal entre as colunas
        formulario.setVgap(12); // espaço vertical entre as linhas
        formulario.setPadding(new Insets(15));

        Label lblNome = new Label("Nome da faculdade:");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Ex.: PUC PR, UFPR, UTFPR..."); // texto de dica
        formulario.add(lblNome, 0, 0); // coluna 0, linha 0
        formulario.add(txtNome, 1, 0); // coluna 1, linha 0

        Label lblCidade = new Label("Cidade:");
        TextField txtCidade = new TextField();
        txtCidade.setPromptText("Ex.: Curitiba, São Paulo..."); // texto de dica
        formulario.add(lblCidade, 0, 1); // coluna 0, linha 1
        formulario.add(txtCidade, 1, 1); // coluna 1, linha 1

        Label lblTurno = new Label("Turno:");
        final ToggleGroup grupoTurno = new ToggleGroup();

        RadioButton rbMatutino   = new RadioButton("Matutino");
        rbMatutino.setToggleGroup(grupoTurno);

        RadioButton rbVespertino = new RadioButton("Vespertino");
        rbVespertino.setToggleGroup(grupoTurno);

        RadioButton rbNoturno    = new RadioButton("Noturno");
        rbNoturno.setToggleGroup(grupoTurno);

        RadioButton rbIntegral   = new RadioButton("Integral");
        rbIntegral.setToggleGroup(grupoTurno);
        rbIntegral.setSelected(true);

        HBox grupoRadio = new HBox(10, rbMatutino, rbVespertino, rbNoturno, rbIntegral);
        formulario.add(lblTurno,  0, 2);
        formulario.add(grupoRadio, 1, 2);

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        HBox linhaBotoes = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linhaBotoes, 0, 3); // ocupa a linha 3 do formulário

        btnSalvar.setOnAction(e -> {
            String nome   = txtNome.getText();
            String cidade = txtCidade.getText();

            Toggle tgTurno = grupoTurno.getSelectedToggle();
            String turno   = (tgTurno != null) ? ((RadioButton) tgTurno).getText() : "";

            controller.salvarFaculdade(nome, cidade, turno);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetFaculdades lista = new TelaGetFaculdades(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);       // header escuro no topo
        layoutPrincipal.setCenter(formulario); // formulário centralizado

        return new Scene(layoutPrincipal, 800, 600);
    }
}