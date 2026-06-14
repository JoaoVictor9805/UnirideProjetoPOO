package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TelaSobre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class CadastroPassageiroController {

    private Stage palcoAtual;

    // ALTERADO: Agora aponta para dentro do pacote 'dao' na pasta 'src'
    private final String CAMINHO_ARQUIVO = "src/dao/passageiros.txt";

    public CadastroPassageiroController(Stage palco) {
        this.palcoAtual = palco;
    }

    public void processarCadastroPassageiro(String nome, String email, String senha, LocalDate nascimento) {
        System.out.println("--- DADOS RECEBIDOS NO CONTROLLER DO PASSAGEIRO ---");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);
        System.out.println("Nascimento: " + nascimento);
        System.out.println("---------------------------------------------------");
        System.out.println("CÉREBRO: Preparando para gravar no pacote 'dao'...");

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || nascimento == null) {
            System.out.println("ERRO: Todos os campos são obrigatórios!\n");
            return;
        }

        String linhaDados = nome + ";" + email + ";" + senha + ";" + nascimento;

        // SEGURANÇA EXTRA: Criamos um objeto File para verificar as pastas
        File arquivo = new File(CAMINHO_ARQUIVO);

        // O Java sabe criar arquivos sozinho, mas não sabe criar pastas estruturadas.
        // Esse comando garante que se a pasta 'src/dao' não existir física no PC, ele cria ela primeiro.
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        // Gravando os dados dentro do caminho especificado
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(linhaDados);
            bw.newLine();

            System.out.println("SUCESSO: Dados salvos em: " + arquivo.getAbsolutePath() + "\n");

            voltarParaTelaSobre();

        } catch (IOException e) {
            System.out.println("ERRO CRÍTICO: Não foi possível salvar o arquivo no pacote dao!");
            e.printStackTrace();
        }
    }

    public void voltarParaTelaSobre() {
        TelaSobre telaSobre = new TelaSobre(this.palcoAtual);
        Scene cenaSobre = telaSobre.desenharTela();
        this.palcoAtual.setScene(cenaSobre);
    }
}