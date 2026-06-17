package view;

import controller.GetSolicitacaoController;
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
import model.Solicitacao;

import java.time.format.DateTimeFormatter;

public class TelaGetSolicitacoes {
    private Stage palco;
    private GetSolicitacaoController controller;
    private TableView<Solicitacao> table = new TableView<>();
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaGetSolicitacoes(Stage palco) {
        this.palco = palco;
        this.controller = new GetSolicitacaoController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Solicitações cadastradas");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Solicitacao, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Solicitacao, String> colPartida = new TableColumn<>("Partida");
        colPartida.setCellValueFactory(new PropertyValueFactory<>("localPartida"));

        TableColumn<Solicitacao, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(new PropertyValueFactory<>("localDestino"));

        TableColumn<Solicitacao, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDataPartida() != null
                                ? data.getValue().getDataPartida().format(FORMATO_DATA)
                                : ""));

        TableColumn<Solicitacao, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Solicitacao, Void>, TableCell<Solicitacao, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Solicitacao, Void> call(final TableColumn<Solicitacao, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                        btnConsultar.setOnAction(e -> {
                            Solicitacao solicitacaoSelecionada = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarSolicitacao telaEdicao = new TelaEditarSolicitacao(TelaGetSolicitacoes.this.palco, solicitacaoSelecionada, numLinha);
                            TelaGetSolicitacoes.this.palco.setScene(telaEdicao.desenharTela());
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

        table.getColumns().addAll(colTitulo, colPartida, colDestino, colData, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhum dado encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Solicitacao> dadosSolicitacoes = controller.carregarSolicitacoes();
        table.setItems(dadosSolicitacoes);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Novo");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroSolicitacao solicitacao = new TelaCadastroSolicitacao(this.palco);
            this.palco.setScene(solicitacao.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}
