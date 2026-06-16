package view;

import controller.GetFaculdadeController;
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
import model.Faculdade;

public class TelaGetFaculdades {
    private Stage palco;
    private GetFaculdadeController controller;
    private TableView<Faculdade> table = new TableView<>();

    public TelaGetFaculdades(Stage palco) {
        this.palco      = palco;
        this.controller = new GetFaculdadeController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;"); // fundo escuro — igual ao Veículos
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Faculdades cadastradas");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Faculdade, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Faculdade, String> colCidade = new TableColumn<>("Cidade");
        colCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        TableColumn<Faculdade, String> colTurno = new TableColumn<>("Turno");
        colTurno.setCellValueFactory(new PropertyValueFactory<>("turno"));

        TableColumn<Faculdade, Void> colAcoes = new TableColumn<>("Ações");

        Callback<TableColumn<Faculdade, Void>, TableCell<Faculdade, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Faculdade, Void> call(final TableColumn<Faculdade, Void> param) {

                return new TableCell<>() {
                    private final Button btnEditar  = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox painelBotoes = new HBox(10, btnEditar, btnExcluir);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                        btnExcluir.setOnAction(e -> {
                            Faculdade faculdadeSelecionada = getTableView().getItems().get(getIndex());
                            controller.excluirFaculdade(faculdadeSelecionada, getTableView().getItems());
                        });

                        btnEditar.setOnAction(e -> {
                            Faculdade faculdadeSelecionada = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarFaculdade telaEdicao = new TelaEditarFaculdade(
                                    TelaGetFaculdades.this.palco,
                                    faculdadeSelecionada,
                                    numLinha
                            );
                            TelaGetFaculdades.this.palco.setScene(telaEdicao.desenharTela());
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

        colAcoes.setCellFactory(cellFactory);

        table.getColumns().addAll(colNome, colCidade, colTurno, colAcoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhuma faculdade encontrada");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Faculdade> dadosFaculdades = controller.carregarFaculdades();
        table.setItems(dadosFaculdades);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar    = new Button("Voltar para o Menu");

        Button btnCadastrar = new Button("Cadastrar Nova");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroFaculdade cadastro = new TelaCadastroFaculdade(this.palco);
            this.palco.setScene(cadastro.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}