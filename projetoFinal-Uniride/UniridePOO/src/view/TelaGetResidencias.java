package view;

import controller.GetResidenciaController;
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
import model.Residencia;

public class TelaGetResidencias {
    private Stage palco;
    private GetResidenciaController controller;
    private TableView<Residencia> table =  new TableView<>();

    public TelaGetResidencias(Stage palco) {
        this.palco = palco;
        this.controller = new GetResidenciaController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Residências cadastradas");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Residencia,String> cidade = new TableColumn<>("Cidade");
        cidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        TableColumn<Residencia,String> cep = new TableColumn<>("CEP");
        cep.setCellValueFactory(new PropertyValueFactory<>("cep"));

        TableColumn<Residencia,String> rua = new TableColumn<>("Rua");
        rua.setCellValueFactory(new PropertyValueFactory<>("rua"));

        TableColumn<Residencia,String> bairro = new TableColumn<>("Bairro");
        bairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));

        TableColumn<Residencia,String> numero = new TableColumn<>("Número");
        numero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<Residencia,String> complemento = new TableColumn<>("Complemento");
        complemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));

        TableColumn<Residencia,String> tipoResidencia = new TableColumn<>("Tipo de Residencia");
        tipoResidencia.setCellValueFactory(new PropertyValueFactory<>("tipoResidencia"));

        TableColumn<Residencia, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Residencia,Void>, TableCell<Residencia,Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Residencia, Void> call(final TableColumn<Residencia, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                        btnConsultar.setOnAction(e -> {
                            Residencia residenciaSelecionada = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarResidencia telaEdicao = new TelaEditarResidencia(TelaGetResidencias.this.palco, residenciaSelecionada, numLinha);
                            TelaGetResidencias.this.palco.setScene(telaEdicao.desenharTela());
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

        table.getColumns().addAll(cidade, cep, rua, bairro, numero, complemento, tipoResidencia, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        Label labelSemDados = new Label("Nenhum dado encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Residencia> dadosResidencias = controller.carregarResidencias();
        table.setItems(dadosResidencias);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar nova residência");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroResidencia residencia = new TelaCadastroResidencia(this.palco);
            this.palco.setScene(residencia.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}