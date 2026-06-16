package controller;

import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CadastroResidenciaController {
    private Stage palco;
    private final String CAMINHO_ARQUIVO = "src/dao/residencias.txt";

    public CadastroResidenciaController(Stage palco) {
        this.palco = palco;
    }

    public void salvarResidencia(String cidade, String cep, String rua, String bairro, String numero, String complemento, String tipoResidencia) {
        File arquivo = new File(CAMINHO_ARQUIVO);

        try (FileWriter fw = new FileWriter(arquivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            String linha = cidade + " | " + cep + " | " + rua + " | " + bairro + " | " + numero + " | " + complemento + " | " + tipoResidencia;
            bw.write(linha);
            bw.newLine();
            bw.flush();

            System.out.println("Residência cadastrada com sucesso: " + linha);

        } catch (IOException e) {
            System.out.println("Erro ao salvar a residência no arquivo residencias.txt ");
            e.printStackTrace();
        }

        view.TelaGetResidencias telaLista = new view.TelaGetResidencias(this.palco);
        this.palco.setScene(telaLista.desenharTela());
    }
}