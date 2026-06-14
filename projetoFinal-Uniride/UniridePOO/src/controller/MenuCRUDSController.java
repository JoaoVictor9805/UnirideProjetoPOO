package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.*;

public class MenuCRUDSController {

    private Stage palcoAtual;

    public MenuCRUDSController(Stage palco) {
        this.palcoAtual = palco;
    }

    // Método do botão Sair do header
    public void fazerLogout() {
        System.out.println("Saindo do sistema... Voltando para a Tela Sobre.");
        TelaSobre telaSobre = new TelaSobre(this.palcoAtual);
        this.palcoAtual.setScene(telaSobre.desenharTela());
    }

    // Métodos de navegação para as 8 telas de gerenciamento
    public void abrirTelaGetMotoristas() {
        this.palcoAtual.setScene(new TelaGetMotoristas(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetPassageiros() {
        this.palcoAtual.setScene(new TelaGetPassageiros(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetOfertas() {
        this.palcoAtual.setScene(new TelaGetOfertas(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetSolicitacoes() {
        this.palcoAtual.setScene(new TelaGetSolicitacoes(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetVeiculos() {
        this.palcoAtual.setScene(new TelaGetVeiculos(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetFaculdades() {
        this.palcoAtual.setScene(new TelaGetFaculdades(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetResidencias() {
        this.palcoAtual.setScene(new TelaGetResidencias(this.palcoAtual).desenharTela());
    }

    public void abrirTelaGetAvaliacoes() {
        this.palcoAtual.setScene(new TelaGetAvaliacoes(this.palcoAtual).desenharTela());
    }
}