package view;

import controller.EdicaoAvaliacaoController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Avaliacao;

public class TelaEditarAvaliacao {
    private Stage palco;
    private EdicaoAvaliacaoController controller;
    private controller.CadastroAvaliacaoController controllerCadastro;
    private Avaliacao avaliacaoOriginal;
    private int indiceLinha;

    public TelaEditarAvaliacao(Stage palco, Avaliacao avaliacao, int indiceLinha) {
        this.palco = palco;
        this.avaliacaoOriginal = avaliacao;
        this.indiceLinha = indiceLinha;
        this.controller = new EdicaoAvaliacaoController(palco);
        this.controllerCadastro = new controller.CadastroAvaliacaoController(palco);
    }

    public Scene desenharTela(){
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Consulta de Avaliação");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        Label lblUsuario = new Label("Usuário:");
        ComboBox<String> cbUsuario = new ComboBox<>();
        cbUsuario.setItems(controllerCadastro.carregarUsuarios());
        cbUsuario.setValue(avaliacaoOriginal.getUsuario());
        formulario.add(lblUsuario, 0, 0);
        formulario.add(cbUsuario, 1, 0);

        Label lblNota = new Label("Nota:");
        ToggleGroup grupoNota = new ToggleGroup();
        RadioButton rbNota0 = new RadioButton("0"); rbNota0.setToggleGroup(grupoNota);
        RadioButton rbNota1 = new RadioButton("1"); rbNota1.setToggleGroup(grupoNota);
        RadioButton rbNota2 = new RadioButton("2"); rbNota2.setToggleGroup(grupoNota);
        RadioButton rbNota3 = new RadioButton("3"); rbNota3.setToggleGroup(grupoNota);
        RadioButton rbNota4 = new RadioButton("4"); rbNota4.setToggleGroup(grupoNota);
        RadioButton rbNota5 = new RadioButton("5"); rbNota5.setToggleGroup(grupoNota);

        String n = avaliacaoOriginal.getNota();
        if (n.equals("0")) rbNota0.setSelected(true);
        else if (n.equals("1")) rbNota1.setSelected(true);
        else if (n.equals("2")) rbNota2.setSelected(true);
        else if (n.equals("3")) rbNota3.setSelected(true);
        else if (n.equals("4")) rbNota4.setSelected(true);
        else rbNota5.setSelected(true);

        HBox grupoRadio = new HBox(10, rbNota0, rbNota1, rbNota2, rbNota3, rbNota4, rbNota5);
        formulario.add(lblNota, 0, 1);
        formulario.add(grupoRadio, 1, 1);

        Label lblComentario = new Label("Avaliação:");
        TextField txtComentario = new TextField(avaliacaoOriginal.getComentario());
        formulario.add(lblComentario, 0, 2);
        formulario.add(txtComentario, 1, 2);

        Button btnSalvar = new Button("Salvar Alterações");
        Button btnExcluir = new Button("Excluir");
        Button btnCancelar = new Button("Voltar");
        HBox linha = new HBox(9, btnSalvar, btnExcluir, btnCancelar);
        formulario.add(linha, 0, 3);

        btnSalvar.setOnAction(e -> {
            Toggle tgNota = grupoNota.getSelectedToggle();
            String nota = (tgNota != null) ? ((RadioButton) tgNota).getText() : "";

            controller.atualizarAvaliacao(indiceLinha, cbUsuario.getValue(), nota, txtComentario.getText());
        });

        btnCancelar.setOnAction(e -> {
            TelaGetAvaliacoes lista = new TelaGetAvaliacoes(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        btnExcluir.setOnAction(e -> {
            controller.excluirAvaliacao(indiceLinha);
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}