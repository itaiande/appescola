package com.iande.ita.appescola.modelo;

import java.util.ArrayList;

/**
 * Created by Roberlandio on 12/11/2016.
 */
public class Estado {
    private String sigla;
    private String nome;
    private ArrayList<String> cidades;


    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<String> getCidades() {
        return cidades;
    }

    public void setCidades(ArrayList<String> cidade) {
        this.cidades = cidades;
    }
}
