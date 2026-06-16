package model;

public class Carro {
    private String marca;
    private String modelo;
    private String ano;
    private String tipoCarroTexto;

    public Carro(String marca, String modelo, String ano, String tipoCarroTexto) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.tipoCarroTexto = tipoCarroTexto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipoCarroTexto() {
        return tipoCarroTexto;
    }

    public void setTipoCarroTexto(String tipoCarroTexto) {
        this.tipoCarroTexto = tipoCarroTexto;
    }
}
