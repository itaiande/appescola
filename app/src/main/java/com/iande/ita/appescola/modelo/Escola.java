package com.iande.ita.appescola.modelo;

import java.io.Serializable;

/**
 * Created by Roberlandio on 03/11/2016.
 */
public class Escola implements Serializable{
    private long codEscola;
    private String nome;
    private String email;
    private Endereco endereco;
    private String esferaAdministrativa;

    public Escola() {
    }

    public Escola(long codEscola, String nome, String email) {
        this.codEscola = codEscola;
        this.nome = nome;
        this.email = email;
    }

    public long getCodEscola() {
        return codEscola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCodEscola(long codEscola) {
        this.codEscola = codEscola;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEsferaAdministrativa() {
        return esferaAdministrativa;
    }

    public void setEsferaAdministrativa(String esferaAdministrativa) {
        this.esferaAdministrativa = esferaAdministrativa;
    }

    @Override
    public String toString() {
        return nome;
    }


}
