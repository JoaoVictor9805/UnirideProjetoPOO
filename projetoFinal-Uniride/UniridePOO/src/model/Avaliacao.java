package model;

public class Avaliacao {
    private String usuario;
    private String nota;
    private String comentario;

    public Avaliacao(String usuario, String nota, String comentario) {
        this.usuario    = usuario;
        this.nota       = nota;
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}