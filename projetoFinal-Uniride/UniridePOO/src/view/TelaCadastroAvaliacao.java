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

public class TelaCadastroAvaliacao {
    private Stage palco;
    private controller.CadastroAvaliacaoController controller;

    public TelaCadastroAvaliacao(Stage palco) {
        this.palco      = palco;
        this.controller = new controller.CadastroAvaliacaoController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Avaliação");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblUsuario = new Label("Usuário:");
        ComboBox<String> cbUsuario = new ComboBox<>();
        cbUsuario.setItems(controller.carregarUsuarios());
        formulario.add(lblUsuario, 0, 0);
        formulario.add(cbUsuario, 1, 0);

        Label lblNota = new Label("Nota:");
        final ToggleGroup grupoNota = new ToggleGroup();

        RadioButton rbNota0 = new RadioButton("0"); rbNota0.setToggleGroup(grupoNota);
        RadioButton rbNota1 = new RadioButton("1"); rbNota1.setToggleGroup(grupoNota);
        RadioButton rbNota2 = new RadioButton("2"); rbNota2.setToggleGroup(grupoNota);
        RadioButton rbNota3 = new RadioButton("3"); rbNota3.setToggleGroup(grupoNota);
        RadioButton rbNota4 = new RadioButton("4"); rbNota4.setToggleGroup(grupoNota);
        RadioButton rbNota5 = new RadioButton("5"); rbNota5.setToggleGroup(grupoNota);
        rbNota5.setSelected(true);

        HBox grupoRadio = new HBox(10, rbNota0, rbNota1, rbNota2, rbNota3, rbNota4, rbNota5);
        formulario.add(lblNota, 0, 1);
        formulario.add(grupoRadio, 1, 1);

        Label lblComentario = new Label("Avaliação:");
        TextField txtComentario = new TextField();
        txtComentario.setPromptText("Escreva sua avaliação...");
        formulario.add(lblComentario, 0, 2);
        formulario.add(txtComentario, 1, 2);

        Button btnSalvar   = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");

        HBox linhaBotoes = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linhaBotoes, 0, 3);

        btnSalvar.setOnAction(e -> {
            String usuario = cbUsuario.getValue();

            Toggle tgNota = grupoNota.getSelectedToggle();
            String nota   = (tgNota != null) ? ((RadioButton) tgNota).getText() : "";

            controller.salvarAvaliacao(usuario, nota, txtComentario.getText());
        });

        btnCancelar.setOnAction(e -> {
            TelaGetAvaliacoes lista = new TelaGetAvaliacoes(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}
