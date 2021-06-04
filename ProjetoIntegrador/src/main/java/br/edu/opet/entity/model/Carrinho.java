package br.edu.opet.entity.model;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.model.entity.dao.CarrinhoDAO;

@ManagedBean
@RequestScoped
public class Carrinho extends CarrinhoDAO{
	
	private int Idf_Carrinho;
	private int Idf_Produto;
	private int Quantidade;
	private int Idf_Usuario;
	private List<Produto> alProdutosCarrinho;
		
	public int getIdf_Carrinho() {
		return Idf_Carrinho;
	}

	public void setIdf_Carrinho(int idf_Carrinho) {
		Idf_Carrinho = idf_Carrinho;
	}

	public int getIdf_Produto() {
		return Idf_Produto;
	}

	public void setIdf_Produto(int idf_Produto) {
		Idf_Produto = idf_Produto;
	}

	public int getQuantidade() {
		return Quantidade;
	}

	public void setQuantidade(int quantidade) {
		Quantidade = quantidade;
	}

	public int getIdf_Usuario() {
		return Idf_Usuario;
	}

	public void setIdf_Usuario(int idf_Usuario) {
		Idf_Usuario = idf_Usuario;
	}

	public List<Produto> getAlProdutosCarrinho() {
		return alProdutosCarrinho;
	}

	public void setAlProdutosCarrinho(List<Produto> alProdutosCarrinho) {
		this.alProdutosCarrinho = alProdutosCarrinho;
	}

	public boolean adicionarAoCarrinho(Produto prod) {
		return super.adicionarAoCarrinho(prod);
	}
	
	public boolean adicionarAoCarrinhoExistente(Produto prod, Carrinho car) {
		return super.adicionarAoCarrinhoExistente(prod, car);
	}
}
