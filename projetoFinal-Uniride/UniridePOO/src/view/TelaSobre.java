package view;

import controller.SobreController;           // Importa a classe que você criou para lidar com os cliques e regras da tela.

// --- GEOMETRIA E ALINHAMENTO ---
import javafx.geometry.Insets;               // Define as margens internas (padding), evitando que os itens grudem nas bordas.
import javafx.geometry.Pos;                  // Define posições e alinhamentos (ex: Pos.CENTER, Pos.CENTER_LEFT).

// --- O NÚCLEO DA TELA ---
import javafx.scene.Scene;                   // A "Cena". É o conteiner que guarda o conteúdo visual que vai aparecer na janela.
import javafx.stage.Stage;                   // O "Palco". É a janela física do sistema operacional (com botões de fechar, minimizar, etc).

// --- COMPONENTES INTERATIVOS ---
import javafx.scene.control.Button;          // Cria botões clicáveis na interface (ex: Login, Cadastro).
import javafx.scene.control.Label;           // Cria textos estáticos na tela (ex: títulos, descrições, nome da logo).

// --- IMAGENS ---
import javafx.scene.image.Image;             // Carrega o arquivo da imagem em si (os bytes) da memória do computador.
import javafx.scene.image.ImageView;         // A "moldura" visual. É o componente que pega a Image e exibe na tela.

// --- GERENCIADORES DE LAYOUT (CAIXAS) ---
import javafx.scene.layout.BorderPane;       // Layout que divide a tela em 5 áreas exatas: Topo, Fundo, Direita, Esquerda e Centro.
import javafx.scene.layout.HBox;             // Caixa Horizontal. Empilha todos os itens lado a lado.
import javafx.scene.layout.VBox;             // Caixa Vertical. Empilha todos os itens um embaixo do outro.
import javafx.scene.layout.Priority;         // Define regras de crescimento (ex: permitir que a logo cresça e empurre os botões).

// --- TEXTOS E TIPOGRAFIA ---
import javafx.scene.text.Font;               // Permite alterar o tipo e o tamanho da letra (ex: Arial tamanho 24).
import javafx.scene.text.TextAlignment;      // Controla o alinhamento de blocos de texto longo (ex: centralizar o parágrafo de descrição).


public class TelaSobre {

    private Stage palco;
    private SobreController controller;

    public TelaSobre(Stage palco) {
        this.palco = palco;
        this.controller = new SobreController(palco);
    }

    public Scene desenharTela() {
        BorderPane layoutPrincipal = new BorderPane();        // O BorderPane é um layout clássico de janelas de computador: ele divide a tela em Topo (Norte), Baixo (Sul), Esquerda (Oeste), Direita (Leste) e Centro.

        // ==========================================
        // ÁREA 1: O CABEÇALHO (Ficará no TOPO)
        // ==========================================

        HBox header = new HBox(20);                         // O HBox enfileira as coisas na horizontal. O '20' é o espaço entre elas.
        header.setPadding(new Insets(15));                  // Margem interna para não grudar nas bordas
        header.setStyle("-fx-background-color: #2c3e50;");     // Cor de fundo azul escuro
        header.setAlignment(Pos.CENTER_LEFT);                  // Empurra os itens para a esquerda. O CENTER dessa propriedade significa que o item ficará centralizado na vertical (no meio da altura da barra azul).

        Label logoTexto = new Label("UniRide");              // TEXTO DA LOGO
        logoTexto.setFont(new Font("Arial", 24));
        logoTexto.setStyle("-fx-text-fill: white;");            // Letra branca

        logoTexto.setMaxWidth(Double.MAX_VALUE);                // A palavra Double (com 'D' maiúsculo) é a classe "mestre" do Java que gerencia todos os números decimais do seu programa. Dentro dessa classe mestre, o Java guarda uma constante chamada MAX_VALUE. Ela representa literalmente o maior número positivo que a memória do computador consegue processar no Java.
        HBox.setHgrow(logoTexto, Priority.ALWAYS);

        Button btnLoginHeader = new Button("Login");
        btnLoginHeader.setOnAction(evento -> controller.abrirTelaLogin());

        header.getChildren().addAll(logoTexto, btnLoginHeader);          // Coloca o logo dentro da caixa do cabeçalho

        // ==========================================
        // ÁREA 2: O CONTEÚDO (Ficará no CENTRO)
        // ==========================================

        VBox corpoDaPagina = new VBox(20);              // O VBox enfileira as coisas na vertical (uma embaixo da outra). O 20 é o espaço (gap) em pixels entre um elemento e o próximo.
        corpoDaPagina.setPadding(new Insets(40));
        corpoDaPagina.setAlignment(Pos.TOP_CENTER);        // Ele empurra o conteúdo lá para o topo da tela (TOP), mas garante que tudo fique perfeitamente no meio (CENTER) na horizontal.


        Label tituloSobre = new Label("Sobre o UniRide");
        tituloSobre.setFont(new Font("Arial", 28));

        Label textoDescricao = new Label(
                "O UniRide é a plataforma definitiva de caronas para estudantes universitários.\n" +
                        "Conectamos quem precisa ir para o campus com quem tem um lugar sobrando no carro."
        );
        textoDescricao.setTextAlignment(TextAlignment.CENTER);  // Centraliza o parágrafo na horizontal
        textoDescricao.setFont(new Font("Arial", 16));

        // A IMAGEM
        ImageView imagemApp = new ImageView();      // A classe ImageView é a "moldura" que vai para a tela.
        try {
            Image imagem = new Image("file:carona.png");        // A classe Image lê o arquivo bruto do disco rígido. Tenta abrir a imagem "carona.png". Ela deve estar na raiz do projeto

            imagemApp.setImage(imagem);
            imagemApp.setFitWidth(400);                            // Força a largura máxima de 400 pixels
            imagemApp.setPreserveRatio(true);                      // Impede que a imagem fique esticada/esmagada
        } catch (Exception e) {
            System.out.println("Imagem carona.png não encontrada. A tela abrirá sem ela.");    // Se a imagem não for encontrada, ele apenas ignora sem explodir o programa

        }

        // ==========================================
        // NOVO: DIVIDINDO A TELA EM DUAS METADES (PASSAGEIRO E MOTORISTA)
        // ==========================================

        // --- 1. METADE ESQUERDA (PASSAGEIRO) ---
        VBox ladoPassageiro = new VBox(15);         // Empilha na vertical com 15px de espaço
        ladoPassageiro.setAlignment(Pos.CENTER);       // Centraliza tudo dentro desta metade

        Label tituloPassageiro = new Label("Sou Passageiro");
        tituloPassageiro.setFont(new Font("Arial", 18));
        tituloPassageiro.setStyle("-fx-font-weight: bold;");

        // O BOTÃO
        Button btnCadastroPassageiro = new Button("Cadastro");

        // Conectando com o Controller (você precisará criar esses métodos novos lá)
        btnCadastroPassageiro.setOnAction(evento -> controller.abrirTelaCadastroPassageiro());

        ladoPassageiro.getChildren().addAll(tituloPassageiro, btnCadastroPassageiro);

        // --- 2. METADE DIREITA (MOTORISTA) ---
        VBox ladoMotorista = new VBox(15);
        ladoMotorista.setAlignment(Pos.CENTER);

        Label tituloMotorista = new Label("Sou Motorista");
        tituloMotorista.setFont(new Font("Arial", 18));
        tituloMotorista.setStyle("-fx-font-weight: bold;");

        Button btnCadastroMotorista = new Button("Cadastro");

        btnCadastroMotorista.setOnAction(evento -> controller.abrirTelaCadastroMotorista());

        ladoMotorista.getChildren().addAll(tituloMotorista, btnCadastroMotorista);

        // --- 3. O CONTAINER PRINCIPAL (A CAIXA MÃE) ---
        HBox containerDividido = new HBox(80);          // O '80' é o espaço vazio entre o lado esquerdo e o direito
        containerDividido.setAlignment(Pos.CENTER);        // Centraliza a caixa mãe no meio da tela
        containerDividido.setPadding(new Insets(20, 0, 0, 0));  // Dá um respiro de 20px em cima para desgrudar da imagem

        HBox.setHgrow(ladoPassageiro, Priority.ALWAYS);    // O truque para dividir 50/50: Permite que ambas as metades cresçam igualmente
        HBox.setHgrow(ladoMotorista, Priority.ALWAYS);

        containerDividido.getChildren().addAll(ladoPassageiro, ladoMotorista);    // Coloca os dois lados lado a lado dentro da caixa mãe


        // --- 4. ADICIONANDO AO CORPO DA PÁGINA ---
        corpoDaPagina.getChildren().addAll(tituloSobre, textoDescricao, imagemApp, containerDividido);


        // ==========================================
        // ÁREA 3: JUNTANDO TUDO NO BORDERPANE
        // ==========================================

        layoutPrincipal.setTop(header);             // Põe o HBox lá em cima
        layoutPrincipal.setCenter(corpoDaPagina);   // Põe o VBox no meio do espaço restante

        return new Scene(layoutPrincipal, 800, 600); // Retorna o pacote completo, formatado para uma janela de tamanho 800x600.
    }
}