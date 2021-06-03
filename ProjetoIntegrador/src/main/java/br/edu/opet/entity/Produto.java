package br.edu.opet.entity;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class Produto {
	
	private int Idf_Produto;
	private String Desc_Produto;
	private float Valor_Produto;
	
	public int getIdf_Produto() {
		return Idf_Produto;
	}
	public void setIdf_Produto(int idf_Produto) {
		Idf_Produto = idf_Produto;
	}
	public String getDesc_Produto() {
		return Desc_Produto;
	}
	public void setDesc_Produto(String desc_Produto) {
		Desc_Produto = desc_Produto;
	}
	public float getValor_Produto() {
		return Valor_Produto;
	}
	public void setValor_Produto(float valor_Produto) {
		Valor_Produto = valor_Produto;
	}	

}
