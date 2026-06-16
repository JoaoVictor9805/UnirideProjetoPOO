package view;

import controller.GetCarroController;
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
import model.Carro;

public class TelaGetVeiculos {
    private Stage palco;
    private GetCarroController controller;
    private TableView<Carro> table =  new TableView<>();

    public TelaGetVeiculos(Stage palco) {
        this.palco = palco;
        this.controller = new GetCarroController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Veículos cadastrados");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Carro,String> marca = new TableColumn<>("Marca");
        marca.setCellValueFactory(new PropertyValueFactory<>("marca"));

        TableColumn<Carro,String> modelo = new TableColumn<>("Modelo");
        modelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        TableColumn<Carro,String> ano = new TableColumn<>("Ano");
        ano.setCellValueFactory(new PropertyValueFactory<>("ano"));

        TableColumn<Carro,String> tipoCarroTexto = new TableColumn<>("Tipo Carro");
        tipoCarroTexto.setCellValueFactory(new PropertyValueFactory<>("tipoCarroTexto"));

        TableColumn<Carro, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Carro,Void>, TableCell<Carro,Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Carro, Void> call(final TableColumn<Carro, Void> param) {
                return new TableCell<>() {
                    private final Button btnEditar = new Button("Editar");
                    private final Button btnExcluir = new Button("Excluir");
                    private final HBox painelBotoes = new HBox(10, btnEditar, btnExcluir);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                         btnExcluir.setOnAction(e -> {
                             Carro carroSelecionado = getTableView().getItems().get(getIndex());
                             controller.excluirCarro(carroSelecionado, getTableView().getItems());
                         });

                         btnEditar.setOnAction(e -> {
                             Carro carroSelecionado = getTableView().getItems().get(getIndex());
                             int numLinha = getIndex();
                             TelaEditarCarro telaEdicao = new TelaEditarCarro(TelaGetVeiculos.this.palco, carroSelecionado, numLinha);
                             TelaGetVeiculos.this.palco.setScene(telaEdicao.desenharTela());
                         });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty){
                            setGraphic(null);
                        } else  {
                            setGraphic(painelBotoes);
                        }
                    }
                };
            }
        };

        acoes.setCellFactory(cellFactory);

        table.getColumns().addAll(marca, modelo, ano, tipoCarroTexto, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Label labelSemDados = new Label("Nenhum dado encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Carro> dadosCarros = controller.carregarCarros();
        table.setItems(dadosCarros);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Novo");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroCarro carro = new TelaCadastroCarro(this.palco);
            this.palco.setScene(carro.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}