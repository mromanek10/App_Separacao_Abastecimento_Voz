package com.example.parsejson;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {
	 
    private String nome;
    private String sobrenome;
    private int idade;
 
    private List telefones = new ArrayList();
 
    public void setTelefones(List telefones) {
        this.telefones = telefones;
    }
 
    public List  getTelefones() {
        return telefones;
    }
 
    public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
 
    public String getSobrenome() {
        return sobrenome;
    }
 
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
 
    public int getIdade() {
        return idade;
    }
 
    public void setIdade(int idade) {
        this.idade = idade;
    }
}