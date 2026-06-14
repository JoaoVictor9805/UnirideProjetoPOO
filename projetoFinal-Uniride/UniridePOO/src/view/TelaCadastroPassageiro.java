package view;

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

public class TelaCadastroPassageiro {

    private Stage palco;
    // Agora usando o Controller exclusivo dessa tela!
    private controller.CadastroPassageiroController controller;

    // Construtor
    public TelaCadastroPassageiro(Stage palco) {
        this.palco = palco;
        this.controller = new controller.CadastroPassageiroController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // ==========================================
        // 1. O CABEÇALHO
        // ==========================================
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Passageiro");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        // ==========================================
        // 2. O FORMULÁRIO (GridPane)
        // ==========================================
        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(15);
        formulario.setPadding(new Insets(20));

        // Campo Nome
        Label lblNome = new Label("Nome Completo:");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome");
        formulario.add(lblNome, 0, 0);
        formulario.add(txtNome, 1, 0);

        // Campo Email
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@uniride.com");
        formulario.add(lblEmail, 0, 1);
        formulario.add(txtEmail, 1, 1);

        // Campo Senha
        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Mínimo 6 caracteres");
        formulario.add(lblSenha, 0, 2);
        formulario.add(txtSenha, 1, 2);

        // Campo Data de Nascimento
        Label lblNascimento = new Label("Data de Nascimento:");
        DatePicker txtNascimento = new DatePicker();
        txtNascimento.setPromptText("Selecione a data");
        formulario.add(lblNascimento, 0, 3);
        formulario.add(txtNascimento, 1, 3);

        // ==========================================
        // 3. OS BOTÕES
        // ==========================================
        HBox caixaBotoes = new HBox(20);
        caixaBotoes.setAlignment(Pos.CENTER);
        caixaBotoes.setPadding(new Insets(20, 0, 0, 0));

        Button btnVoltar = new Button("Voltar");
        Button btnCadastrar = new Button("Confirmar Cadastro");

        // Ações mandando os comandos para o Controller
        btnVoltar.setOnAction(evento -> controller.voltarParaTelaSobre());

        btnCadastrar.setOnAction(evento -> {
            // Coleta os dados da tela e manda pro Cérebro
            controller.processarCadastroPassageiro(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtSenha.getText(),
                    txtNascimento.getValue()
            );
        });

        caixaBotoes.getChildren().addAll(btnVoltar, btnCadastrar);

        // ==========================================
        // 4. JUNTANDO TUDO
        // ==========================================
        VBox centroDaTela = new VBox(10);
        centroDaTela.setAlignment(Pos.CENTER);
        centroDaTela.getChildren().addAll(formulario, caixaBotoes);

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(centroDaTela);

        return new Scene(layoutPrincipal, 800, 600);
    }
}