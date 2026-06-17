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

public class EdicaoPassageiroController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/passageiros.txt";

    public EdicaoPassageiroController(Stage palco) {
        this.palco = palco;
    }

    public void atualizarPassageiro(int indiceLinha, String novoNome, String novoEmail, String novaSenha, String novoNascimento) {

        // 1. VALIDAÇÃO DE CAMPOS VAZIOS
        if (novoNome.trim().isEmpty() || novoEmail.trim().isEmpty() || novaSenha.trim().isEmpty() ||
                novoNascimento.trim().isEmpty()) {

            exibirAlerta("Campos Obrigatórios!", "Todos os campos do passageiro devem ser preenchidos.");
            return;
        }

        // 2. VALIDAÇÃO DO EMAIL (Deve conter '@')
        if (!novoEmail.contains("@")) {
            exibirAlerta("E-mail Inválido!", "O e-mail digitado deve conter o caractere '@'.");
            return;
        }

        // Criamos as variáveis locais que vão guardar as datas formatadas para o TXT
        String nascimentoFormatado = novoNascimento;

        try {
            // O DatePicker envia como "yyyy-MM-dd", que é o padrão nativo do LocalDate.parse()
            LocalDate dataNascimento = LocalDate.parse(novoNascimento.trim());
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
        String novaLinha = novoNome + " | " + novoEmail + " | " + novaSenha + " | " + novoNascimento;

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
            System.out.println("Arquivo atualizado com sucesso no índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao atualizar arquivo");
            e.printStackTrace();
        }

        view.TelaGetPassageiros telaLista = new view.TelaGetPassageiros(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }

    public void excluirPassageiro(int indiceLinha) {
        File arquivo = new File(CAMINHO_ARQUIVO);
        List<String> linhasDoArquivo = new ArrayList<>();

        try (FileReader fr = new FileReader(arquivo);
             BufferedReader br = new BufferedReader(fr)) {
            String linha;
            int contador = 0;
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
            for (String l : linhasDoArquivo) {
                bw.write(l);
                bw.newLine();
            }
            bw.flush();
            System.out.println("Passageiro excluído com sucesso do índice: " + indiceLinha);
        } catch (IOException e) {
            System.out.println("Erro ao excluir arquivo");
            e.printStackTrace();
        }

        view.TelaGetPassageiros telaLista = new view.TelaGetPassageiros(this.palco);
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