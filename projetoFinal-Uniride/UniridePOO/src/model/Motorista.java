package model;

public class Motorista {
    private String nome;
    private String email;
    private String senha;
    private String nascimento;
    private String cpf;
    private String vencimentoCnh;
    private String registroCnh;

    public Motorista(String nome, String email, String senha, String nascimento, String cpf, String vencimentoCnh, String registroCnh) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.nascimento = nascimento;
        this.cpf = cpf;
        this.vencimentoCnh = vencimentoCnh;
        this.registroCnh = registroCnh;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNascimento() { return nascimento; }
    public void setNascimento(String nascimento) { this.nascimento = nascimento; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getVencimentoCnh() { return vencimentoCnh; }
    public void setVencimentoCnh(String vencimentoCnh) { this.vencimentoCnh = vencimentoCnh; }

    public String getRegistroCnh() { return registroCnh; }
    public void setRegistroCnh(String registroCnh) { this.registroCnh = registroCnh; }
}