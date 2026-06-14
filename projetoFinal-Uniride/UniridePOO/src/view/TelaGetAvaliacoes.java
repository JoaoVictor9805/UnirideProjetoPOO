package view;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaGetAvaliacoes {

    private Stage palco;

    public TelaGetAvaliacoes(Stage palco) {
        this.palco = palco;
    }

    public Scene desenharTela() {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        // Mude este texto nas outras telas ("Tabela de Passageiros", etc)
        Label titulo = new Label("Tabela (Em Breve)");
        titulo.setFont(new Font("Arial", 24));

        Button btnVoltar = new Button("Voltar para o Menu");

        // Ação temporária direta na View para voltar ao menu sem precisar de Controller por enquanto
        btnVoltar.setOnAction(evento -> {
            TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palco);
            this.palco.setScene(menu.desenharTela());
        });

        layout.getChildren().addAll(titulo, btnVoltar);

        return new Scene(layout, 800, 600);
    }
}