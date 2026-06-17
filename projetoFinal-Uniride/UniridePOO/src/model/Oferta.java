package model;

import java.time.LocalDate;

public class Oferta {
    private String titulo;
    private String localPartida;
    private String localDestino;
    private LocalDate dataPartida;
    private Double preco;
    private int vagas;

    public Oferta (String titulo, String localPartida, String localDestino, LocalDate dataHoraPartida, Double preco, int vagas){
        this.titulo = titulo;
        this.localPartida = localPartida;
        this.localDestino = localDestino;
        this.dataPartida = dataHoraPartida;
        this.preco = preco;
        this.vagas = vagas;
    }
    public String getTitulo() {
        return titulo;
    }


    public String getLocalDestino() {
        return localDestino;
    }

    public String getLocalPartida() {
        return localPartida;
    }

    public LocalDate getDataPartida() {
        return dataPartida;
    }

    public Double getPreco() {
        return preco;
    }

    public int getVagas() {
        return vagas;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public void setLocalPartida(String localPartida) {
        this.localPartida = localPartida;
    }

    public void setDataPartida(LocalDate dataHoraPartida) {
        this.dataPartida = dataHoraPartida;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
}
