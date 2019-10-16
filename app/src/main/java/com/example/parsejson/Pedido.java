package com.example.parsejson;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	
	private int numero;
    private List<Itens> itens = new ArrayList();
   
    
	public List<Itens> getItens() {
		return itens;
	}
	public void setItens(List<Itens> itens) {
		this.itens = itens;
	}    
    
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}

}
