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

public class TelaCadastroPassageiro {

    private Stage palco;
    private boolean isLogado;
    private controller.CadastroPassageiroController controller;

    public TelaCadastroPassageiro(Stage palco, boolean isLogado) {
        this.palco = palco;
        this.isLogado = isLogado;
        this.controller = new controller.CadastroPassageiroController(palco, isLogado);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // ==========================================
        // CABEÇALHO
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
        // FORMULÁRIO
        // ==========================================
        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        // --- NOME ---
        Label lblNome = new Label("Nome Completo:");
        TextField txtNome = new TextField();
        txtNome.setPromptText("Digite seu nome");
        formulario.add(lblNome, 0, 0);
        formulario.add(txtNome, 1, 0);

        // --- EMAIL ---
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("exemplo@uniride.com");
        formulario.add(lblEmail, 0, 1);
        formulario.add(txtEmail, 1, 1);

        // --- SENHA ---
        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite sua senha");
        formulario.add(lblSenha, 0, 2);
        formulario.add(txtSenha, 1, 2);

        // --- NASCIMENTO ---
        Label lblNascimento = new Label("Data de Nascimento:");
        DatePicker txtNascimento = new DatePicker();
        txtNascimento.setPromptText("Selecione a data");
        formulario.add(lblNascimento, 0, 3);
        formulario.add(txtNascimento, 1, 3);

        // ==========================================
        // BOTÕES E AÇÕES
        // ==========================================
        Button btnSalvar = new Button("Salvar");
        Button btnCancelar = new Button("Cancelar");
        HBox linhaBotoes = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linhaBotoes, 1, 4);

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            // Pega a data diretamente do DatePicker (se nulo, envia vazio)
            String dataSelecionada = (txtNascimento.getValue() != null) ? txtNascimento.getValue().toString() : "";

            controller.salvarPassageiro(nome, email, senha, dataSelecionada);
        });

        // Roteamento inteligente ao cancelar
        btnCancelar.setOnAction(e -> {
            if (this.isLogado) {
                TelaGetPassageiros lista = new TelaGetPassageiros(this.palco);
                this.palco.setScene(lista.desenharTela());
            } else {
                TelaSobre telaSobre = new TelaSobre(this.palco);
                this.palco.setScene(telaSobre.desenharTela());
            }
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}