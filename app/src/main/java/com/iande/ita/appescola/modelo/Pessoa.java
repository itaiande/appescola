package com.iande.ita.appescola.modelo;

import java.io.Serializable;

/**
 * Created by Usuario on 01/11/2016.
 */

public class Pessoa implements Serializable {
    private int cod;
    private String email;
    private String nomeCompleto;
    private String nomeUsuario;
    private String senha;
    private boolean emailVerificado;
    private String token;
    private String biografia;


    public Pessoa(){
    }


    public Pessoa(String email, String nomeCompleto, String senha) {
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
    }

    public Pessoa(int cod, String email, String nomeCompleto, String senha,
                  boolean emailVerificado, String biografia){
        this.cod = cod;
        this.email = email;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.emailVerificado = emailVerificado;
        this.biografia = biografia;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getCod(){
        return this.cod;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSenha(){
        return this.senha;
    }

    public boolean isEmailVerificado(){
        return this.emailVerificado;
    }

    public String getBiografia(){
        return this.biografia;
    }

    public String getToken() {
        return token;
    }

    public void setCod(int cod){
        this.cod = cod;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setEmailVerificado(boolean emailVerificado) {
        this.emailVerificado = emailVerificado;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString(){
        return this.email;
    }

}
