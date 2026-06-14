package controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.TelaMenuCRUDS;
import view.TelaSobre;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LoginController {

    private Stage palcoAtual;
    private final String ARQUIVO_PASSAGEIROS = "src/dao/passageiros.txt";
    private final String ARQUIVO_MOTORISTAS = "src/dao/motoristas.txt";

    public LoginController(Stage palco) {
        this.palcoAtual = palco;
    }

    public void autenticar(String email, String senha) {
        System.out.println("--- TENTATIVA DE LOGIN ---");
        System.out.println("Email digitado: " + email);
        System.out.println("--------------------------");

        if (email.isEmpty() || java.util.Objects.isNull(senha) || senha.isEmpty()) {
            System.out.println("LOGIN REJEITADO: Preencha todos os campos.");
            return;
        }

        // 1. Procura primeiro no arquivo de passageiros
        System.out.println("Buscando em passageiros.txt...");
        if (buscarUsuarioNoArquivo(ARQUIVO_PASSAGEIROS, email, senha)) {
            System.out.println("LOGIN SUCESSO: Passageiro autenticado!");
            irParaMenuCRUDS();
            return;
        }

        // 2. Se não achou, procura no arquivo de motoristas
        System.out.println("Não encontrado em passageiros. Buscando em motoristas.txt...");
        if (buscarUsuarioNoArquivo(ARQUIVO_MOTORISTAS, email, senha)) {
            System.out.println("LOGIN SUCESSO: Motorista autenticado!");
            irParaMenuCRUDS();
            return;
        }

        // 3. Se não achou em nenhum dos dois
        System.out.println("LOGIN REJEITADO: Usuário ou senha incorretos.");
    }

    // Método auxiliar com o algoritmo de leitura sequencial do arquivo
    private boolean buscarUsuarioNoArquivo(String caminhoArquivo, String emailBuscado, String senhaBuscada) {
        File arquivo = new File(caminhoArquivo);

        // Se o arquivo ainda nem existe (nenhum cadastro feito), finge que não achou
        if (!arquivo.exists()) {
            return false;
        }

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {

            String linha;
            // Lê o arquivo linha por linha até o final
            while ((linha = br.readLine()) != null) {
                // Quebra a linha nos pontos e vírgulas
                String[] dados = linha.split(";");

                // Garantir que a linha tem dados suficientes (nome, email, senha...)
                if (dados.length > 2) {
                    String emailSalvo = dados[1]; // O email é o segundo item (índice 1)
                    String senhaSalva = dados[2]; // A senha é o terceiro item (índice 2)

                    // Se bater o email E a senha exatamente, achamos!
                    if (emailSalvo.equalsIgnoreCase(emailBuscado) && senhaSalva.equals(senhaBuscada)) {
                        return true;
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + caminhoArquivo);
            e.printStackTrace();
        }

        return false; // Se percorreu o arquivo inteiro e não achou
    }

    private void irParaMenuCRUDS() {
        TelaMenuCRUDS menu = new TelaMenuCRUDS(this.palcoAtual);
        Scene cenaMenu = menu.desenharTela();
        this.palcoAtual.setScene(cenaMenu);
    }

    public void voltarParaTelaSobre() {
        TelaSobre telaSobre = new TelaSobre(this.palcoAtual);
        Scene cenaSobre = telaSobre.desenharTela();
        this.palcoAtual.setScene(cenaSobre);
    }
}