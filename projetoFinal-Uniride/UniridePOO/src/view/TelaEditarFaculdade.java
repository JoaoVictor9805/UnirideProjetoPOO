package view;

import controller.EdicaoFaculdadeController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Faculdade;

public class TelaEditarFaculdade {
    private Stage palco;
    private EdicaoFaculdadeController controller;
    private Faculdade faculdadeOriginal;
    private int indiceLinha;

    public TelaEditarFaculdade(Stage palco, Faculdade faculdade, int indiceLinha) {
        this.palco              = palco;
        this.faculdadeOriginal  = faculdade;
        this.indiceLinha        = indiceLinha;
        this.controller         = new EdicaoFaculdadeController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();
        HBox header = new HBox(15);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER);

        Label tituloTela = new Label("Editar Faculdade");
        tituloTela.setFont(new Font("Arial", 22));
        tituloTela.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        header.getChildren().add(tituloTela);

        GridPane formulario = new GridPane();
        formulario.setAlignment(Pos.CENTER);
        formulario.setHgap(15);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        TextField txtNome = new TextField(faculdadeOriginal.getNome());
        formulario.add(new Label("Nome da faculdade:"), 0, 0);
        formulario.add(txtNome, 1, 0);

        TextField txtCidade = new TextField(faculdadeOriginal.getCidade());
        formulario.add(new Label("Cidade:"), 0, 1);
        formulario.add(txtCidade, 1, 1);

        ToggleGroup grupoTurno = new ToggleGroup();
        RadioButton rbMatutino   = new RadioButton("Matutino");
        rbMatutino.setToggleGroup(grupoTurno);
        RadioButton rbVespertino = new RadioButton("Vespertino");
        rbVespertino.setToggleGroup(grupoTurno);
        RadioButton rbNoturno    = new RadioButton("Noturno");
        rbNoturno.setToggleGroup(grupoTurno);
        RadioButton rbIntegral   = new RadioButton("Integral");
        rbIntegral.setToggleGroup(grupoTurno);

        String turnoAtual = faculdadeOriginal.getTurno();
        if (turnoAtual.equals("Matutino"))        rbMatutino.setSelected(true);
        else if (turnoAtual.equals("Vespertino")) rbVespertino.setSelected(true);
        else if (turnoAtual.equals("Noturno"))    rbNoturno.setSelected(true);
        else                                       rbIntegral.setSelected(true);

        HBox grupoRadio = new HBox(10, rbMatutino, rbVespertino, rbNoturno, rbIntegral);
        formulario.add(new Label("Turno:"), 0, 2);
        formulario.add(grupoRadio, 1, 2);

        Button btnSalvar   = new Button("Salvar Alterações");
        Button btnCancelar = new Button("Cancelar");
        HBox linhaBotoes = new HBox(9, btnSalvar, btnCancelar);
        formulario.add(linhaBotoes, 0, 3);

        btnSalvar.setOnAction(e -> {
            Toggle tgTurno = grupoTurno.getSelectedToggle();
            String turno   = (tgTurno != null) ? ((RadioButton) tgTurno).getText() : "";
            controller.atualizarFaculdade(
                    indiceLinha,
                    txtNome.getText(),
                    txtCidade.getText(),
                    turno
            );
        });

        btnCancelar.setOnAction(e -> {
            TelaGetFaculdades lista = new TelaGetFaculdades(this.palco);
            this.palco.setScene(lista.desenharTela());
        });

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(formulario);

        return new Scene(layoutPrincipal, 800, 600);
    }
}