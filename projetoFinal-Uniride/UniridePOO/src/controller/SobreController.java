package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TelaCadastroPassageiro; // Importando a nova tela

public class SobreController {

    private Stage palcoAtual; // O palcoAtual é uma ponte de controle. O Controller guarda a referência da janela física do Windows/Mac para que, quando o usuário clicar em um botão, ele tenha o poder de acessar essa janela e trocar o conteúdo de dentro dela.

    public SobreController(Stage palco) {
        this.palcoAtual = palco;
    }

    public void abrirTelaLogin() {
        System.out.println("O usuário quer fazer Login. Trocando para a tela de Login...");
        view.TelaLogin telaLogin = new view.TelaLogin(this.palcoAtual);
        this.palcoAtual.setScene(telaLogin.desenharTela());
    }

    public void abrirTelaCadastroPassageiro() {
        System.out.println("O usuário quer fazer Cadastro como Passageiro. (Preparando para trocar de tela...)");
        TelaCadastroPassageiro telaCadastro = new TelaCadastroPassageiro(this.palcoAtual);
        Scene cenaCadastro = telaCadastro.desenharTela();
        this.palcoAtual.setScene(cenaCadastro);
    }

    public void abrirTelaCadastroMotorista() {
        System.out.println("O usuário quer fazer Cadastro como Motorista. Trocando a tela...");
        view.TelaCadastroMotorista telaMotorista = new view.TelaCadastroMotorista(this.palcoAtual);
        this.palcoAtual.setScene(telaMotorista.desenharTela());
    }
}