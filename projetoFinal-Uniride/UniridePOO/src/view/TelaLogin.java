package view;

import controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaLogin {

    private Stage palco;
    private LoginController controller;

    public TelaLogin(Stage palco) {
        this.palco = palco;
        this.controller = new LoginController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // Cabeçalho
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Acessar UniRide");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        // Formulário
        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(20));

        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite seu e-mail");
        formulario.add(lblEmail, 0, 0);
        formulario.add(txtEmail, 1, 0);

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        formulario.add(lblSenha, 0, 1);
        formulario.add(txtSenha, 1, 1);

        // Botões
        HBox caixaBotoes = new HBox(20);
        caixaBotoes.setAlignment(Pos.CENTER);
        caixaBotoes.setPadding(new Insets(20, 0, 0, 0));

        Button btnVoltar = new Button("Voltar");
        Button btnEntrar = new Button("Entrar");

        btnVoltar.setOnAction(evento -> controller.voltarParaTelaSobre());

        btnEntrar.setOnAction(evento -> {
            controller.autenticar(txtEmail.getText(), txtSenha.getText());
        });

        caixaBotoes.getChildren().addAll(btnVoltar, btnEntrar);

        VBox centroDaTela = new VBox(10);
        centroDaTela.setAlignment(Pos.CENTER);
        centroDaTela.getChildren().addAll(formulario, caixaBotoes);

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(centroDaTela);

        return new Scene(layoutPrincipal, 800, 600);
    }
}