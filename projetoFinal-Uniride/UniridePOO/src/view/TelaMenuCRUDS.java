package view;

import controller.MenuCRUDSController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TelaMenuCRUDS {

    private Stage palco;
    private MenuCRUDSController controller;

    public TelaMenuCRUDS(Stage palco) {
        this.palco = palco;
        this.controller = new MenuCRUDSController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();

        // ==========================================
        // 1. O CABEÇALHO (Logo na esquerda, Sair na direita)
        // ==========================================
        HBox header = new HBox();
        header.setPadding(new Insets(15, 20, 15, 20));
        header.setStyle("-fx-background-color: #2c3e50;");
        header.setAlignment(Pos.CENTER_LEFT);

        Label logoTexto = new Label("UniRide");
        logoTexto.setFont(new Font("Arial", 24));
        logoTexto.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        // Truque do espaço em branco para empurrar o Sair para o canto
        logoTexto.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(logoTexto, Priority.ALWAYS);

        Button btnSair = new Button("Sair");
        btnSair.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        btnSair.setOnAction(evento -> controller.fazerLogout());

        header.getChildren().addAll(logoTexto, btnSair);

        // ==========================================
        // 2. O CORPO (Os 8 Botões em Grid 2x4)
        // ==========================================
        VBox corpoDaPagina = new VBox(30);
        corpoDaPagina.setAlignment(Pos.CENTER);
        corpoDaPagina.setPadding(new Insets(40));

        Label tituloMenu = new Label("Painel de Gerenciamento");
        tituloMenu.setFont(new Font("Arial", 26));

        GridPane gridBotoes = new GridPane();
        gridBotoes.setAlignment(Pos.CENTER);
        gridBotoes.setHgap(30); // Espaço horizontal entre as colunas
        gridBotoes.setVgap(20); // Espaço vertical entre as linhas

        // Criando os botões usando o método auxiliar
        Button btnMotoristas = criarBotaoMenu("Motoristas");
        Button btnPassageiros = criarBotaoMenu("Passageiros");
        Button btnOfertas = criarBotaoMenu("Ofertas de carona");
        Button btnSolicitacoes = criarBotaoMenu("Solicitações de carona");
        Button btnVeiculos = criarBotaoMenu("Veículos");
        Button btnFaculdades = criarBotaoMenu("Faculdades");
        Button btnResidencias = criarBotaoMenu("Residências");
        Button btnAvaliacoes = criarBotaoMenu("Avaliações");

        // Conectando as ações
        btnMotoristas.setOnAction(e -> controller.abrirTelaGetMotoristas());
        btnPassageiros.setOnAction(e -> controller.abrirTelaGetPassageiros());
        btnOfertas.setOnAction(e -> controller.abrirTelaGetOfertas());
        btnSolicitacoes.setOnAction(e -> controller.abrirTelaGetSolicitacoes());
        btnVeiculos.setOnAction(e -> controller.abrirTelaGetVeiculos());
        btnFaculdades.setOnAction(e -> controller.abrirTelaGetFaculdades());
        btnResidencias.setOnAction(e -> controller.abrirTelaGetResidencias());
        btnAvaliacoes.setOnAction(e -> controller.abrirTelaGetAvaliacoes());

        // Adicionando no Grid (Componente, Coluna, Linha)
        gridBotoes.add(btnMotoristas, 0, 0);
        gridBotoes.add(btnPassageiros, 1, 0);
        gridBotoes.add(btnOfertas, 0, 1);
        gridBotoes.add(btnSolicitacoes, 1, 1);
        gridBotoes.add(btnVeiculos, 0, 2);
        gridBotoes.add(btnFaculdades, 1, 2);
        gridBotoes.add(btnResidencias, 0, 3);
        gridBotoes.add(btnAvaliacoes, 1, 3);

        corpoDaPagina.getChildren().addAll(tituloMenu, gridBotoes);

        layoutPrincipal.setTop(header);
        layoutPrincipal.setCenter(corpoDaPagina);

        return new Scene(layoutPrincipal, 800, 600);
    }

    // Metodo auxiliar para padronizar o tamanho e visual de todos os botões do menu
    private Button criarBotaoMenu(String texto) {
        Button botao = new Button(texto);
        botao.setPrefSize(250, 50); // Largura x Altura fixas para todos ficarem iguais
        botao.setFont(new Font("Arial", 14));
        return botao;
    }
}