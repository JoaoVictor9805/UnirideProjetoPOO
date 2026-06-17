package view;

import controller.GetOfertaController;
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
import model.Oferta;

import java.time.format.DateTimeFormatter;

public class TelaGetOfertas {
    private Stage palco;
    private GetOfertaController controller;
    private TableView<Oferta> table = new TableView<>();
    private final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TelaGetOfertas(Stage palco) {
        this.palco = palco;
        this.controller = new GetOfertaController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Ofertas cadastradas");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Oferta, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Oferta, String> colPartida = new TableColumn<>("Partida");
        colPartida.setCellValueFactory(new PropertyValueFactory<>("localPartida"));

        TableColumn<Oferta, String> colDestino = new TableColumn<>("Destino");
        colDestino.setCellValueFactory(new PropertyValueFactory<>("localDestino"));

        TableColumn<Oferta, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getDataPartida() != null
                                ? data.getValue().getDataPartida().format(FORMATO_DATA)
                                : ""));

        TableColumn<Oferta, Double> colPreco = new TableColumn<>("Preço (R$)");
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));

        TableColumn<Oferta, Integer> colVagas = new TableColumn<>("Vagas");
        colVagas.setCellValueFactory(new PropertyValueFactory<>("vagas"));

        TableColumn<Oferta, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Oferta, Void>, TableCell<Oferta, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Oferta, Void> call(final TableColumn<Oferta, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                        btnConsultar.setOnAction(e -> {
                            Oferta ofertaSelecionada = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarOferta telaEdicao = new TelaEditarOferta(TelaGetOfertas.this.palco, ofertaSelecionada, numLinha);
                            TelaGetOfertas.this.palco.setScene(telaEdicao.desenharTela());
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

        table.getColumns().addAll(colTitulo, colPartida, colDestino, colData, colPreco, colVagas, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhum dado encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Oferta> dadosOfertas = controller.carregarOfertas();
        table.setItems(dadosOfertas);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Novo");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroOferta oferta = new TelaCadastroOferta(this.palco);
            this.palco.setScene(oferta.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}
