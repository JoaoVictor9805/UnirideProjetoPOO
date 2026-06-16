package model;

public class Faculdade {
    private String nome;
    private String cidade;
    private String turno;


    public Faculdade(String nome, String cidade, String turno) {
        this.nome   = nome;
        this.cidade = cidade;
        this.turno  = turno;
    }

    public String getNome() {
        return nome;
    }

    public String getCidade() {
        return cidade;
    }

    public String getTurno() {
        return turno;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}