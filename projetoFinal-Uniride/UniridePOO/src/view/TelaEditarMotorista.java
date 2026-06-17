package view;

import controller.EdicaoMotoristaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Motorista;

import java.time.LocalDate;

public class TelaEditarMotorista {
    private Stage palco;
    private EdicaoMotoristaController controller;
    private Motorista motoristaOriginal;
    private int indiceLinha;

    public TelaEditarMotorista(Stage palco, Motorista motorista, int indiceLinha) {
        this.palco = palco;
        this.motoristaOriginal = motorista;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoMotoristaController(palco);
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

        Label tituloTela = new Label("Consulta de Motorista");
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

        // --- 1. NOME ---
        Label lblNome = new Label("Nome Completo:");
        TextField txtNome = new TextField(motoristaOriginal.getNome());
        formulario.add(lblNome, 0, 0);
        formulario.add(txtNome, 1, 0);

        // --- 2. EMAIL ---
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField(motoristaOriginal.getEmail());
        formulario.add(lblEmail, 0, 1);
        formulario.add(txtEmail, 1, 1);

        // --- 3. SENHA ---
        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setText(motoristaOriginal.getSenha());
        formulario.add(lblSenha, 0, 2);
        formulario.add(txtSenha, 1, 2);

        // --- 4. NASCIMENTO ---
        Label lblNascimento = new Label("Data de Nascimento:");
        DatePicker txtNascimento = new DatePicker();
        String dataNascSalva = motoristaOriginal.getNascimento();
        if (dataNascSalva != null && !dataNascSalva.trim().isEmpty() && !dataNascSalva.equals("null")) {
            try {
                txtNascimento.setValue(LocalDate.parse(dataNascSalva.trim()));
            } catch (Exception e) {
                System.out.println("Aviso: Data de nascimento antiga em formato inválido.");
            }
        }
        formulario.add(lblNascimento, 0, 3);
        formulario.add(txtNascimento, 1, 3);

        // --- 5. CPF ---
        Label lblCpf = new Label("CPF (apenas números):");
        TextField txtCpf = new TextField(motoristaOriginal.getCpf());
        // Trava nativa: Aceita apenas entrada numérica
        txtCpf.setTextFormatter(new TextFormatter<>(change -> change.getText().matches("\\d*") ? change : null));
        formulario.add(lblCpf, 0, 4);
        formulario.add(txtCpf, 1, 4);

        // --- 6. VENCIMENTO CNH ---
        Label lblVencimento = new Label("Vencimento da CNH:");
        DatePicker txtVencimento = new DatePicker();
        String dataVencSalva = motoristaOriginal.getVencimentoCnh();
        if (dataVencSalva != null && !dataVencSalva.trim().isEmpty() && !dataVencSalva.equals("null")) {
            try {
                txtVencimento.setValue(LocalDate.parse(dataVencSalva.trim()));
            } catch (Exception e) {
                System.out.println("Aviso: Data de vencimento antiga em formato inválido.");
            }
        }
        formulario.add(lblVencimento, 0, 5);
        formulario.add(txtVencimento, 1, 5);

        // --- 7. REGISTRO CNH ---
        Label lblRegistro = new Label("Nº Registro CNH:");
        TextField txtRegistro = new TextField(motoristaOriginal.getRegistroCnh());
        // Trava nativa: Aceita apenas entrada numérica
        txtRegistro.setTextFormatter(new TextFormatter<>(change -> change.getText().matches("\\d*") ? change : null));
        formulario.add(lblRegistro, 0, 6);
        formulario.add(txtRegistro, 1, 6);

        // ==========================================
        // BOTÕES E AÇÕES
        // ==========================================
        Button btnSalvar = new Button("Salvar Alterações");
        Button btnExcluir = new Button("Excluir");
        Button btnCancelar = new Button("Voltar");
        HBox linhaBotoes = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linhaBotoes, 0, 7);

        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = txtSenha.getText();
            String dataNasc = (txtNascimento.getValue() != null) ? txtNascimento.getValue().toString() : "";
            String cpf = txtCpf.getText();
            String vencimentoCnh = (txtVencimento.getValue() != null) ? txtVencimento.getValue().toString() : "";
            String registroCnh = txtRegistro.getText();

            controller.atualizarMotorista(
                    indiceLinha,
                    nome,
                    email,
                    senha,
                    dataNasc,
                    cpf,
                    vencimentoCnh,
                    registroCnh
            );
        });

        btnExcluir.setOnAction(e -> {
            controller.excluirMotorista(indiceLinha);
        });

        btnCancelar.setOnAction(e -> {
            TelaGetMotoristas lista = new TelaGetMotoristas(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}