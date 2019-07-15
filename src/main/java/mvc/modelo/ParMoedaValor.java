package mvc.modelo;

import java.io.Serializable;

public class ParMoedaValor implements Serializable{
    private String moeda;
    private double valor;

    public String getMoeda() {
        return moeda;
    }

    public void setMoeda(String moeda) {
        this.moeda = moeda;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
