package view;

import controller.EdicaoSolicitacaoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Solicitacao;

public class TelaEditarSolicitacao {
    private Stage palco;
    private EdicaoSolicitacaoController controller;
    private Solicitacao solicitacaoOriginal;
    private int indiceLinha;

    public TelaEditarSolicitacao(Stage palco, Solicitacao solicitacao, int indiceLinha) {
        this.palco = palco;
        this.solicitacaoOriginal = solicitacao;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoSolicitacaoController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Consulta de Solicitação");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField(solicitacaoOriginal.getTitulo());
        formulario.add(lblTitulo, 0, 0);
        formulario.add(txtTitulo, 1, 0);

        Label lblPartida = new Label("Local de Partida:");
        TextField txtPartida = new TextField(solicitacaoOriginal.getLocalPartida());
        formulario.add(lblPartida, 0, 1);
        formulario.add(txtPartida, 1, 1);

        Label lblDestino = new Label("Local de Destino:");
        TextField txtDestino = new TextField(solicitacaoOriginal.getLocalDestino());
        formulario.add(lblDestino, 0, 2);
        formulario.add(txtDestino, 1, 2);

        Label lblData = new Label("Data:");
        DatePicker datePicker = new DatePicker(solicitacaoOriginal.getDataPartida());
        formulario.add(lblData, 0, 3);
        formulario.add(datePicker, 1, 3);

        Button btnSalvar   = new Button("Salvar Alterações");
        Button btnExcluir  = new Button("Excluir");
        Button btnCancelar = new Button("Voltar");
        HBox linha = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linha, 0, 4);

        btnSalvar.setOnAction(e ->
                controller.atualizarSolicitacao(
                        indiceLinha,
                        txtTitulo.getText(),
                        txtPartida.getText(),
                        txtDestino.getText(),
                        datePicker.getValue()
                )
        );

        btnExcluir.setOnAction(e ->
                controller.excluirSolicitacao(indiceLinha)
        );

        btnCancelar.setOnAction(e -> {
            TelaGetSolicitacoes lista = new TelaGetSolicitacoes(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}
