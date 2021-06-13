package br.edu.opet.entity.model;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.opet.model.entity.dao.CarrinhoDAO;

@ManagedBean
@SessionScoped
public class Carrinho extends CarrinhoDAO{
	
	private int Idf_Carrinho;
	private Produto Prod_Carrinho;
	private double Sub_Total;
	private int Total_Itens;
	private double Total_Carrinho;

	public int getIdf_Carrinho() {
		return Idf_Carrinho;
	}

	public void setIdf_Carrinho(int idf_Carrinho) {
		Idf_Carrinho = idf_Carrinho;
	}	

	public Produto getProd_Carrinho() {
		return Prod_Carrinho;
	}

	public void setProd_Carrinho(Produto prod_Carrinho) {
		Prod_Carrinho = prod_Carrinho;
	}

	public double getSub_Total() {
		return Sub_Total;
	}

	public void setSub_Total(double sub_Total) {
		Sub_Total = sub_Total;
	}
	
	public int getTotal_Itens() {
		return Total_Itens;
	}

	public void setTotal_Itens(int total_Itens) {
		Total_Itens = total_Itens;
	}
	
	public double getTotal_Carrinho() {
		return Total_Carrinho;
	}

	public void setTotal_Carrinho(double total_Carrinho) {
		Total_Carrinho = total_Carrinho;
	}

	public boolean adicionarAoCarrinho(Produto prod, Carrinho car) {
		return super.adicionarAoCarrinho(prod, car);
	}
	
	public boolean adicionarAoCarrinhoExistente(Produto prod, Carrinho car) {
		return super.adicionarAoCarrinhoExistente(prod, car);
	}
	
	public ArrayList<Carrinho> listarCarrinho(Carrinho car) {
		return super.listarCarrinho(car);
	}
	
	public ArrayList<Carrinho> listarCarrinhoTotal(Carrinho car) {
		return super.listarCarrinhoTotal(car);
	}

	public boolean verItensCarrinho(Produto prod, Carrinho car) {
		return super.verItemCarrinho(prod, car);
	}
	
	public boolean apagarItemCarrinho(Carrinho car) {
		return super.removerDoCarrinho(car);
	}

	public boolean apagarCarrinho(Carrinho car) {
		return super.apagarCarrinho(car);	
	}
	
}
