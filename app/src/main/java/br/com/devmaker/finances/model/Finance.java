package br.com.devmaker.finances.model;

import java.io.Serializable;

/**
 * Created by Dev_Maker on 10/05/2016.
 */
public class Finance implements Serializable {
    private Double valor;
    private String nome;
    private Integer id;

    public Finance(Double valor, String nome) {
        this.valor = valor;
        this.nome = nome;
    }

    public Finance() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
