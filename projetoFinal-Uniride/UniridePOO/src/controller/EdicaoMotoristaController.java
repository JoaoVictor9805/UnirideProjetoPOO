package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class EdicaoMotoristaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/motoristas.txt";

    public EdicaoMotoristaController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarMotorista(int indiceLinha, String novoNome, String novoEmail, String novaSenha, String novoNascimento, String novoCpf, String novoVencimento, String novoRegistro) {
        // 1. VALIDAÇÃO DE CAMPOS VAZIOS
        if (novoNome.trim().isEmpty() || novoEmail.trim().isEmpty() || novaSenha.trim().isEmpty() ||
                novoNascimento.trim().isEmpty() || novoCpf.trim().isEmpty() ||
                novoVencimento.trim().isEmpty() || novoRegistro.trim().isEmpty()) {

            exibirAlerta("Campos Obrigatórios!", "Todos os campos do motorista devem ser preenchidos.");
            return;
        }

        // 2. VALIDAÇÃO DO EMAIL (Deve conter '@')
        if (!novoEmail.contains("@")) {
            exibirAlerta("E-mail Inválido!", "O e-mail digitado deve conter o caractere '@'.");
            return;
        }

        // Criamos as variáveis locais que vão guardar as datas formatadas para o TXT
        String nascimentoFormatado = novoNascimento;
        String vencimentoFormatado = novoVencimento;

        try {
            // O DatePicker envia como "yyyy-MM-dd", que é o padrão nativo do LocalDate.parse()
            LocalDate dataNascimento = LocalDate.parse(novoNascimento.trim());
            LocalDate hoje = LocalDate.now();

            // 3. VALIDAÇÃO DA IDADE (Maior de 18 anos baseada no dia de hoje)
            int idade = Period.between(dataNascimento, hoje).getYears();
            if (idade < 18) {
                exibirAlerta("Idade Insuficiente!", "O motorista deve ser maior de 18 anos para se cadastrar.");
                return;
            }

            // 4. VALIDAÇÃO DO VENCIMENTO DA CNH (Pelo menos 18/06/2026)
            LocalDate dataVencimento = LocalDate.parse(novoVencimento.trim());
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

        File arquivo = new File(CAMINHO_ARQUIVO);

        // Montando a nova linha com os 7 campos padronizados
        String novaLinha = novoNome + " | " + novoEmail + " | " + novaSenha + " | " + novoNascimento + " | " + novoCpf + " | " + novoVencimento + " | " + novoRegistro;

        List<String> linhasDoArquivo = new ArrayList<>();

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhasDoArquivo.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Substitui a linha antiga pela nova usando o índice
        if (indiceLinha >= 0 && indiceLinha < linhasDoArquivo.size()) {
            linhasDoArquivo.set(indiceLinha, novaLinha);
        }

        try (FileWriter fw = new FileWriter(arquivo, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String l : linhasDoArquivo) {
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
            System.out.println("Arquivo de motoristas atualizado com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo de motoristas");
            e.printStackTrace();
        }

        // Retorna para a tela de listagem
        view.TelaGetMotoristas telaLista = new view.TelaGetMotoristas(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirMotorista(int indiceLinha) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        List<String> linhasDoArquivo = new ArrayList<>();

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            int contador = 0;
            // Lê todas as linhas, mas ignora a linha que tem o índice igual ao que queremos excluir
            while ((linha = br.readLine()) != null) {
                if (contador != indiceLinha) {
                    linhasDoArquivo.add(linha);
                }
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter fw = new FileWriter(arquivo, false);
             BufferedWriter bw = new BufferedWriter(fw)) {
            // Regrava o arquivo, agora sem a linha excluída
            for (String l : linhasDoArquivo) {
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
            System.out.println("Motorista excluído com sucesso do índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao excluir arquivo");
            e.printStackTrace();
        }

        // Retorna para a tela de listagem
        view.TelaGetMotoristas telaLista = new view.TelaGetMotoristas(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    private void exibirAlerta(String cabecalho, String conteudo) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Aviso");
        alerta.setHeaderText(cabecalho);
        alerta.setContentText(conteudo);
        alerta.showAndWait();
    }
}