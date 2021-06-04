package br.edu.opet.entity.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.model.entity.dao.ProdutoDAO;

@ManagedBean
@RequestScoped
public class Produto extends ProdutoDAO {
	
	private int Idf_Produto;
	private String Desc_Produto;
	private double Valor_Produto;
	private Estoque estoque;
	private int quantidade;
	
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
	public double getValor_Produto() {
		return Valor_Produto;
	}
	public void setValor_Produto(double valor_Produto) {
		Valor_Produto = valor_Produto;
	}	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Estoque getEstoque() {
		return estoque;
	}
	public void setEstoque(Estoque estoque) {
		this.estoque = estoque;
	}
	public ArrayList<Produto> listar() {
		return super.listarProdutos();
	}

}
