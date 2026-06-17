package model;

import java.time.LocalDate;

public class Solicitacao {
    private String titulo;
    private String localPartida;
    private String localDestino;
    private LocalDate dataPartida;

    public Solicitacao (String titulo, String localPartida, String localDestino, LocalDate dataPartida){
        this.titulo = titulo;
        this.localPartida = localPartida;
        this.localDestino = localDestino;
        this.dataPartida = dataPartida;
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

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public void setLocalPartida(String localPartida) {
        this.localPartida = localPartida;
    }
    public void setDataHoraPartida(LocalDate dataHoraPartida) {
        this.dataPartida = dataHoraPartida;
    }
}
