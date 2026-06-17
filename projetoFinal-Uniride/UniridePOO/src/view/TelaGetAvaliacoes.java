package view;

import controller.GetAvaliacaoController;
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
import model.Avaliacao;

public class TelaGetAvaliacoes {
    private Stage palco;
    private GetAvaliacaoController controller;
    private TableView<Avaliacao> table = new TableView<>();

    public TelaGetAvaliacoes(Stage palco) {
        this.palco = palco;
        this.controller = new GetAvaliacaoController(palco);
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #2c3e50;");
        layout.setPadding(new Insets(20));

        Label titulo = new Label("Avaliações cadastradas");
        titulo.setFont(new Font("Arial", 24));
        titulo.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        TableColumn<Avaliacao,String> usuario = new TableColumn<>("Usuário");
        usuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Avaliacao,String> nota = new TableColumn<>("Nota");
        nota.setCellValueFactory(new PropertyValueFactory<>("nota"));

        TableColumn<Avaliacao,String> comentario = new TableColumn<>("Avaliação");
        comentario.setCellValueFactory(new PropertyValueFactory<>("comentario"));

        TableColumn<Avaliacao, Void> acoes = new TableColumn<>("Ações");
        Callback<TableColumn<Avaliacao,Void>, TableCell<Avaliacao,Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Avaliacao, Void> call(final TableColumn<Avaliacao, Void> param) {
                return new TableCell<>() {
                    private final Button btnConsultar = new Button("Consultar");
                    private final HBox painelBotoes = new HBox(10, btnConsultar);

                    {
                        painelBotoes.setAlignment(Pos.CENTER);
                        btnConsultar.setOnAction(e -> {
                            Avaliacao avaliacaoSelecionada = getTableView().getItems().get(getIndex());
                            int numLinha = getIndex();
                            TelaEditarAvaliacao telaEdicao = new TelaEditarAvaliacao(TelaGetAvaliacoes.this.palco, avaliacaoSelecionada, numLinha);
                            TelaGetAvaliacoes.this.palco.setScene(telaEdicao.desenharTela());
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

        table.getColumns().addAll(usuario, nota, comentario, acoes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Label labelSemDados = new Label("Nenhum dado encontrado");
        labelSemDados.setFont(new Font("Arial", 20));
        table.setPlaceholder(labelSemDados);

        ObservableList<Avaliacao> dadosAvaliacoes = controller.carregarAvaliacoes();
        table.setItems(dadosAvaliacoes);

        HBox caixaBotoesMenu = new HBox(15);
        caixaBotoesMenu.setAlignment(Pos.CENTER);

        Button btnVoltar = new Button("Voltar para o Menu");
        Button btnCadastrar = new Button("Cadastrar Nova");

        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        btnCadastrar.setOnAction(evento -> {
            TelaCadastroAvaliacao avaliacao = new TelaCadastroAvaliacao(this.palco);
            this.palco.setScene(avaliacao.desenharTela());
        });

        caixaBotoesMenu.getChildren().addAll(btnVoltar, btnCadastrar);

        layout.getChildren().addAll(titulo, table, caixaBotoesMenu);

        return new Scene(layout, 800, 600);
    }
}