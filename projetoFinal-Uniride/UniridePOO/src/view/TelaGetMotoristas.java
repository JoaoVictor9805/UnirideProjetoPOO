package view;

import controller.GetMotoristasController;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Motorista;

// Importações para manipular e formatar as datas
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaGetMotoristas {

    private Stage palco;
    private GetMotoristasController controller;
    private TableView<Motorista> table = new TableView<>();

    public TelaGetMotoristas(Stage palco) {
        this.palco = palco;
        this.controller = new GetMotoristasController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Motoristas Cadastrados");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // --- Configuração das Colunas ---
        TableColumn<Motorista, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Motorista, String> email = new TableColumn<>("E-mail");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Motorista, String> senha = new TableColumn<>("Senha");
        senha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        // ==========================================
        // COLUNA NASCIMENTO COM FORMATAÇÃO dd/MM/yyyy
        // ==========================================
        TableColumn<Motorista, String> nascimento = new TableColumn<>("Nascimento");
        nascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));

        nascimento.setCellFactory(coluna -> new TableCell<Motorista, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.trim().isEmpty() || item.equals("null")) {
                    setText(null);
                } else {
                    try {
                        LocalDate data = LocalDate.parse(item.trim());
                        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        setText(data.format(formatador));
                    } catch (Exception e) {
                        setText(item);
                    }
                }
            }
        });

        TableColumn<Motorista, String> cpf = new TableColumn<>("CPF");
        cpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));

        // ==========================================
        // COLUNA VALIDADE CNH COM FORMATAÇÃO dd/MM/yyyy
        // ==========================================
        TableColumn<Motorista, String> vencimento = new TableColumn<>("Validade CNH");
        vencimento.setCellValueFactory(new PropertyValueFactory<>("vencimentoCnh"));

        vencimento.setCellFactory(coluna -> new TableCell<Motorista, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.trim().isEmpty() || item.equals("null")) {
                    setText(null);
                } else {
                    try {
                        LocalDate data = LocalDate.parse(item.trim());
                        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        setText(data.format(formatador));
                    } catch (Exception e) {
                        setText(item);
                    }
                }
            }
        });

        TableColumn<Motorista, String> registro = new TableColumn<>("Registro CNH");
        registro.setCellValueFactory(new PropertyValueFactory<>("registroCnh"));

        // --- Coluna de Ações (Apenas Consultar) ---
        TableColumn<Motorista, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Motorista, Void>, TableCell<Motorista, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Motorista, Void> call(final TableColumn<Motorista, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);

                        btnConsultar.setOnAction(e -> {
                            Motorista selecionado = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarMotorista telaEdicao = new TelaEditarMotorista(TelaGetMotoristas.this.palco, selecionado, numLinha);
                            TelaGetMotoristas.this.palco.setScene(telaEdicao.desenharTela());
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(painelBotoes);
                        }
                    }
                };
            }
        };

        acoes.setCellFactory(cellFactory);

        // Adiciona todas as colunas na tabela
        table.getColumns().addAll(nome, email, senha, nascimento, cpf, vencimento, registro, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhum motorista encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        // Preenchendo dados
        ObservableList<Motorista> dados = controller.carregarMotoristas();
        table.setItems(dados);

        // Botões inferiores
        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Novo");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroMotorista cadastro = new TelaCadastroMotorista(this.palco, true);
            this.palco.setScene(cadastro.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 1000, 600);
    }
}