package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroPassageiroController {

    private Stage palco;
    private boolean isLogado; // Guarda o estado do usuário
    private final String CAMINHO_ARQUIVO = "src/dao/passageiros.txt";

    // Construtor agora exige saber se está logado
    public CadastroPassageiroController(Stage palco, boolean isLogado) {
        this.palco = palco;
        this.isLogado = isLogado;
    }

    public void salvarPassageiro(String nome, String email, String senha, String nascimento) {

        // 1. VALIDAÇÃO DE CAMPOS VAZIOS
        if (nome.trim().isEmpty() || email.trim().isEmpty() || senha.trim().isEmpty() ||
                nascimento.trim().isEmpty()) {

            exibirAlerta("Campos Obrigatórios!", "Todos os campos do passageiro devem ser preenchidos.");
            return;
        }

        // 2. VALIDAÇÃO DO EMAIL (Deve conter '@')
        if (!email.contains("@")) {
            exibirAlerta("E-mail Inválido!", "O e-mail digitado deve conter o caractere '@'.");
            return;
        }

        // Criamos as variáveis locais que vão guardar as datas formatadas para o TXT
        String nascimentoFormatado = nascimento;

        try {
            // O DatePicker envia como "yyyy-MM-dd", que é o padrão nativo do LocalDate.parse()
            LocalDate dataNascimento = LocalDate.parse(nascimento.trim());
            LocalDate hoje = LocalDate.now();

            // 3. VALIDAÇÃO DA IDADE (Maior de 18 anos baseada no dia de hoje)
            int idade = Period.between(dataNascimento, hoje).getYears();
            if (idade < 18) {
                exibirAlerta("Idade Insuficiente!", "O passageiro deve ser maior de 18 anos para se cadastrar.");
                return;
            }

            // PADRONIZAÇÃO: Transforma o "yyyy-MM-dd" da tela para "dd/MM/yyyy" para salvar bonito no TXT
            DateTimeFormatter padraoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            nascimentoFormatado = dataNascimento.format(padraoBr);

        } catch (DateTimeParseException e) {
            exibirAlerta("Data Inválida!", "Houve um erro ao processar as datas selecionadas.");
            return;
        }


        File arquivo = new File(CAMINHO_ARQUIVO);

        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = nome + " | " + email + " | " + senha + " | " + nascimento;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Passageiro cadastrado com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar o passageiro");
            e.printStackTrace();
        }

        // Lógica de roteamento inteligente
        if (this.isLogado) {
            view.TelaGetPassageiros telaLista = new view.TelaGetPassageiros(this.palco);
            this.palco.setScene(telaLista.desenharTela());
        } else {
            view.TelaSobre telaSobre = new view.TelaSobre(this.palco);
            this.palco.setScene(telaSobre.desenharTela());
        }
    }

    private void exibirAlerta(String cabecalho, String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}



