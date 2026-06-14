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

public class TelaCadastroMotorista {

    private Stage palco;
    private controller.CadastroMotoristaController controller;

    public TelaCadastroMotorista(Stage palco) {
        this.palco = palco;
        this.controller = new controller.CadastroMotoristaController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // ==========================================
        // CABEÇALHO DA TELA
        // ==========================================
        HBox header = new HBox();
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Cadastro de Motorista (UniRide)");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        // ==========================================
        // FORMULÁRIO ALINHADO (GridPane)
        // ==========================================
        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12); // Um espaço um pouco menor para caber tudo confortavelmente
        formulario.setPadding(new Insets(15));

        // 1. Nome
        Label lblNome = new Label("Nome Completo:");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome");
        formulario.add(lblNome, 0, 0);
        formulario.add(txtNome, 1, 0);

        // 2. Email
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@uniride.com");
        formulario.add(lblEmail, 0, 1);
        formulario.add(txtEmail, 1, 1);

        // 3. Senha
        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Mínimo 6 caracteres");
        formulario.add(lblSenha, 0, 2);
        formulario.add(txtSenha, 1, 2);

        // 4. Data de Nascimento
        Label lblNascimento = new Label("Data de Nascimento:");
        DatePicker txtNascimento = new DatePicker();
        txtNascimento.setPromptText("Selecione a data");
        formulario.add(lblNascimento, 0, 3);
        formulario.add(txtNascimento, 1, 3);

        // 5. CPF (Novo Campo)
        Label lblCpf = new Label("CPF:");
        TextField txtCpf = new TextField();
        txtCpf.setPromptText("000.000.000-00");
        formulario.add(lblCpf, 0, 4);
        formulario.add(txtCpf, 1, 4);

        // 6. Data de Vencimento da CNH (Novo Campo)
        Label lblVencimento = new Label("Vencimento da CNH:");
        DatePicker txtVencimento = new DatePicker();
        txtVencimento.setPromptText("Validade da CNH");
        formulario.add(lblVencimento, 0, 5);
        formulario.add(txtVencimento, 1, 5);

        // 7. Número de Registro da CNH (Novo Campo)
        Label lblRegistro = new Label("Nº Registro CNH:");
        TextField txtRegistro = new TextField();
        txtRegistro.setPromptText("Número da habilitação");
        formulario.add(lblRegistro, 0, 6);
        formulario.add(txtRegistro, 1, 6);

        // ==========================================
        // BOTÕES DE AÇÃO
        // ==========================================
        HBox caixaBotoes = new HBox(20);
        caixaBotoes.setAlignment(Pos.CENTER);
        caixaBotoes.setPadding(new Insets(15, 0, 0, 0));

        Button btnVoltar = new Button("Voltar");
        Button btnCadastrar = new Button("Confirmar Cadastro");

        // Ações usando o controller exclusivo do motorista
        btnVoltar.setOnAction(evento -> controller.voltarParaTelaSobre());

        btnCadastrar.setOnAction(evento -> {
            controller.processarCadastroMotorista(
                    txtNome.getText(),
                    txtEmail.getText(),
                    txtSenha.getText(),
                    txtNascimento.getValue(),
                    txtCpf.getText(),
                    txtVencimento.getValue(),
                    txtRegistro.getText()
            );
        });

        caixaBotoes.getChildren().addAll(btnVoltar, btnCadastrar);

        // ==========================================
        // JUNTANDO AS PARTES NO CORPO
        // ==========================================
        VBox centroDaTela = new VBox(10);
        centroDaTela.setAlignment(Pos.CENTER);
        centroDaTela.getChildren().addAll(formulario, caixaBotoes);

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(centroDaTela);

        return new Scene(layoutPrincipal, 800, 600);
    }
}