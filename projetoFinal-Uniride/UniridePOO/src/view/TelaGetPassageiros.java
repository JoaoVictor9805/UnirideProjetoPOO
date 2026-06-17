package view;

import controller.GetPassageirosController;
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
import model.Passageiro;

// Importações para manipular e formatar a data
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaGetPassageiros {
    private Stage palco;
    private GetPassageirosController controller;
    private TableView<Passageiro> table = new TableView<>();

    public TelaGetPassageiros(Stage palco) {
        this.palco = palco;
        this.controller = new GetPassageirosController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Passageiros Cadastrados");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Colunas
        TableColumn<Passageiro, String> nome = new TableColumn<>("Nome");
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Passageiro, String> email = new TableColumn<>("E-mail");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Passageiro, String> senha = new TableColumn<>("Senha");
        senha.setCellValueFactory(new PropertyValueFactory<>("senha"));

        // ==========================================
        // COLUNA NASCIMENTO COM FORMATAÇÃO dd/MM/yyyy
        // ==========================================
        TableColumn<Passageiro, String> nascimento = new TableColumn<>("Nascimento");
        nascimento.setCellValueFactory(new PropertyValueFactory<>("nascimento"));

        nascimento.setCellFactory(coluna -> new TableCell<Passageiro, String>() {
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

        // Coluna de Ações (Apenas Consultar)
        TableColumn<Passageiro, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Passageiro, Void>, TableCell<Passageiro, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Passageiro, Void> call(final TableColumn<Passageiro, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);

                        btnConsultar.setOnAction(e -> {
                            Passageiro selecionado = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarPassageiro telaEdicao = new TelaEditarPassageiro(TelaGetPassageiros.this.palco, selecionado, numLinha);
                            TelaGetPassageiros.this.palco.setScene(telaEdicao.desenharTela());
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

        table.getColumns().addAll(nome, email, senha, nascimento, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhum passageiro encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        // Preenchendo dados
        ObservableList<Passageiro> dados = controller.carregarPassageiros();
        table.setItems(dados);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Novo");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroPassageiro cadastro = new TelaCadastroPassageiro(this.palco, true);
            this.palco.setScene(cadastro.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}