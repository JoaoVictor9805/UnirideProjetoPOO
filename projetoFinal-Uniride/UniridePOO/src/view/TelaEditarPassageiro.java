package view;

import controller.EdicaoPassageiroController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Passageiro;

import java.time.LocalDate;

public class TelaEditarPassageiro {
    private Stage palco;
    private EdicaoPassageiroController controller;
    private Passageiro passageiroOriginal;
    private int indiceLinha;

    public TelaEditarPassageiro(Stage palco, Passageiro passageiro, int indiceLinha) {
        this.palco = palco;
        this.passageiroOriginal = passageiro;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoPassageiroController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // ==========================================
        // CABEÇALHO
        // ==========================================
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Consulta de Passageiro");
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
        TextField txtNome = new TextField(passageiroOriginal.getNome());
        formulario.add(lblNome, 0, 0);
        formulario.add(txtNome, 1, 0);

        // --- EMAIL ---
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField(passageiroOriginal.getEmail());
        formulario.add(lblEmail, 0, 1);
        formulario.add(txtEmail, 1, 1);

        // --- SENHA ---
        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setText(passageiroOriginal.getSenha());
        formulario.add(lblSenha, 0, 2);
        formulario.add(txtSenha, 1, 2);

        // --- NASCIMENTO ---
        Label lblNascimento = new Label("Data de Nascimento:");
        DatePicker txtNascimento = new DatePicker();

        // Tenta preencher a data convertendo a String salva de volta para LocalDate
        String dataSalva = passageiroOriginal.getNascimento();
        if (dataSalva != null && !dataSalva.trim().isEmpty() && !dataSalva.equals("null")) {
            try {
                txtNascimento.setValue(LocalDate.parse(dataSalva.trim()));
            } catch (Exception e) {
                System.out.println("Aviso: Data antiga em formato inválido ou vazia.");
            }
        }
        formulario.add(lblNascimento, 0, 3);
        formulario.add(txtNascimento, 1, 3);

        // ==========================================
        // BOTÕES E AÇÕES
        // ==========================================
        Button btnSalvar = new Button("Salvar Alterações");
        Button btnExcluir = new Button("Excluir");
        Button btnCancelar = new Button("Voltar");
        HBox linhaBotoes = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linhaBotoes, 0, 4);

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            String dataNasc = (txtNascimento.getValue() != null) ? txtNascimento.getValue().toString() : "";

            controller.atualizarPassageiro(indiceLinha, nome, email, senha, dataNasc);
        });

        // Ação do botão excluir
        btnExcluir.setOnAction(e -> {
            controller.excluirPassageiro(indiceLinha);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetPassageiros lista = new TelaGetPassageiros(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}