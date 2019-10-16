package com.example.parsejson;

import java.util.ArrayList;
import java.util.List;

public class Itens {
	
	private int pecaId; 
	private String pecaDescricao;
	private List<Enderecos> enderecos = new ArrayList();
	private CodigoMercadoCar codigoMercadoCar;
	private int quantidade;
	private String codigoFabricante;
	
	public int getPecaID() {
		return pecaId;
	}
	public void setPecaID(int pecaId) {
		this.pecaId = pecaId;
	}
	public String getPecaDescricao() {
		return pecaDescricao;
	}
	public void setPecaDescricao(String pecaDescricao) {
		this.pecaDescricao = pecaDescricao;
	}
	public List getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Enderecos> enderecos) {
		enderecos = enderecos;
	}
		public CodigoMercadoCar getCdMcar() {
		return codigoMercadoCar;
	}
	public void setCdMcar(CodigoMercadoCar codigoMercadoCar) {
		this.codigoMercadoCar = codigoMercadoCar; 
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public String getCodigoFabricante() {
		return codigoFabricante;
	}
	public void setCodigoFabricante(String codigoFabricante) {
		this.codigoFabricante = codigoFabricante;
	}
	

}
