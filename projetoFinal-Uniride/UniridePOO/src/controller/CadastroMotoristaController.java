package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TelaSobre;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class CadastroMotoristaController {

    private Stage palcoAtual;

    // DEFINIDO: Caminho apontando para o arquivo de motoristas dentro do pacote 'dao'
    private final String CAMINHO_ARQUIVO = "src/dao/motoristas.txt";

    public CadastroMotoristaController(Stage palco) {
        this.palcoAtual = palco;
    }

    public void processarCadastroMotorista(String nome, String email, String senha, LocalDate nascimento,
                                           String cpf, LocalDate dataVencimento, String numeroRegistro) {

        // ==========================================
        // 1. EXIBIÇÃO NO TERMINAL (DEBUG)
        // ==========================================
        System.out.println("--- DADOS RECEBIDOS NO CONTROLLER DO MOTORISTA ---");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);
        System.out.println("Nascimento: " + nascimento);
        System.out.println("CPF: " + cpf);
        System.out.println("Vencimento CNH: " + dataVencimento);
        System.out.println("Nº Registro CNH: " + numeroRegistro);
        System.out.println("---------------------------------------------------");
        System.out.println("CÉREBRO: Preparando para gravar no pacote 'dao'...");

        // ==========================================
        // 2. VALIDAÇÃO DOS 7 CAMPOS
        // ==========================================
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || nascimento == null ||
                cpf.isEmpty() || dataVencimento == null || numeroRegistro.isEmpty()) {

            System.out.println("ERRO: Todos os campos (incluindo dados da CNH e CPF) são obrigatórios!\n");
            return; // Interrompe a gravação se houver campos vazios
        }

        // Unindo todos os dados na linha que vai para o arquivo de texto
        String linhaDados = nome + ";" + email + ";" + senha + ";" + nascimento + ";" +
                cpf + ";" + dataVencimento + ";" + numeroRegistro;

        // Garantindo que a pastinha física 'src/dao' exista no computador
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        // ==========================================
        // 3. PERSISTÊNCIA EM ARQUIVO (Modo Append)
        // ==========================================
        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(linhaDados);
            bw.newLine(); // Pula linha para o próximo motorista

            System.out.println("SUCESSO: Motorista persistido em: " + arquivo.getAbsolutePath() + "\n");

            // Cadastro concluído com sucesso, volta para a tela inicial
            voltarParaTelaSobre();

        } catch (IOException e) {
            System.out.println("ERRO CRÍTICO: Não foi possível salvar o arquivo de motoristas!");
            e.printStackTrace();
        }
    }

    public void voltarParaTelaSobre() {
        TelaSobre telaSobre = new TelaSobre(this.palcoAtual);
        Scene cenaSobre = telaSobre.desenharTela();
        this.palcoAtual.setScene(cenaSobre);
    }
}