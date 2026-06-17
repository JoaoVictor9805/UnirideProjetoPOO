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

public class CadastroMotoristaController {

    private Stage palco;
    private boolean isLogado;
    private final String CAMINHO_ARQUIVO = "src/dao/motoristas.txt";

    public CadastroMotoristaController(Stage palco, boolean isLogado) {
        this.palco = palco;
        this.isLogado = isLogado;
    }

    public void salvarMotorista(String nome, String email, String senha, String nascimento, String cpf, String vencimentoCnh, String registroCnh) {

        // 1. VALIDAÇÃO DE CAMPOS VAZIOS
        if (nome.trim().isEmpty() || email.trim().isEmpty() || senha.trim().isEmpty() ||
                nascimento.trim().isEmpty() || cpf.trim().isEmpty() ||
                vencimentoCnh.trim().isEmpty() || registroCnh.trim().isEmpty()) {

            exibirAlerta("Campos Obrigatórios!", "Todos os campos do motorista devem ser preenchidos.");
            return;
        }

        // 2. VALIDAÇÃO DO EMAIL (Deve conter '@')
        if (!email.contains("@")) {
            exibirAlerta("E-mail Inválido!", "O e-mail digitado deve conter o caractere '@'.");
            return;
        }

        // Criamos as variáveis locais que vão guardar as datas formatadas para o TXT
        String nascimentoFormatado = nascimento;
        String vencimentoFormatado = vencimentoCnh;

        try {
            // O DatePicker envia como "yyyy-MM-dd", que é o padrão nativo do LocalDate.parse()
            LocalDate dataNascimento = LocalDate.parse(nascimento.trim());
            LocalDate hoje = LocalDate.now();

            // 3. VALIDAÇÃO DA IDADE (Maior de 18 anos baseada no dia de hoje)
            int idade = Period.between(dataNascimento, hoje).getYears();
            if (idade < 18) {
                exibirAlerta("Idade Insuficiente!", "O motorista deve ser maior de 18 anos para se cadastrar.");
                return;
            }

            // 4. VALIDAÇÃO DO VENCIMENTO DA CNH (Pelo menos 18/06/2026)
            LocalDate dataVencimento = LocalDate.parse(vencimentoCnh.trim());
            LocalDate dataMinimaCnh = LocalDate.of(2026, 6, 18);

            if (dataVencimento.isBefore(dataMinimaCnh)) {
                exibirAlerta("CNH Vencida ou Inválida!", "O vencimento da CNH deve ser a partir de 18/06/2026.");
                return;
            }

            // PADRONIZAÇÃO: Transforma o "yyyy-MM-dd" da tela para "dd/MM/yyyy" para salvar bonito no TXT
            DateTimeFormatter padraoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            nascimentoFormatado = dataNascimento.format(padraoBr);
            vencimentoFormatado = dataVencimento.format(padraoBr);

        } catch (DateTimeParseException e) {
            exibirAlerta("Data Inválida!", "Houve um erro ao processar as datas selecionadas.");
            return;
        }


        // --- SE PASSOU POR TODAS AS VALIDAÇÕES, SALVA NO ARQUIVO ---
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (arquivo.getParentFile() != null) {
            arquivo.getParentFile().mkdirs();
        }

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = nome.trim() + " | " + email.trim() + " | " + senha.trim() + " | " + nascimento.trim() + " | " + cpf.trim() + " | " + vencimentoCnh.trim() + " | " + registroCnh.trim();
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Motorista cadastrado com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar o motorista no arquivo motoristas.txt");
            e.printStackTrace();
        }

        // Lógica de roteamento
        if (this.isLogado) {
            view.TelaGetMotoristas telaLista = new view.TelaGetMotoristas(this.palco);
            this.palco.setScene(telaLista.desenharTela());
        } else {
            view.TelaSobre telaSobre = new view.TelaSobre(this.palco);
            this.palco.setScene(telaSobre.desenharTela());
        }
    }

    // Metodo auxiliar criado para evitar ficar repetindo o código do bloco Alert toda hora
    private void exibirAlerta(String cabecalho, String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}