package br.edu.opet.entity;

import java.util.ArrayList;

import br.edu.opet.model.entity.dao.PedidoDAO;

public class PedidoItem extends PedidoDAO {
	
	private int Idf_Pedido;
	private int Idf_Produto;
	private int Quantidade;
	private int Valor_Produto;
	private int Valor_Total;
	private Produto produto;
	
	public int getIdf_Pedido() {
		return Idf_Pedido;
	}
	public void setIdf_Pedido(int idf_Pedido) {
		Idf_Pedido = idf_Pedido;
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
	public int getValor_Produto() {
		return Valor_Produto;
	}
	public void setValor_Produto(int valor_Produto) {
		Valor_Produto = valor_Produto;
	}
	public int getValor_Total() {
		return Valor_Total;
	}
	public void setValor_Total(int valor_Total) {
		Valor_Total = valor_Total;
	}
	public Produto getProduto() {
		return produto;
	}
	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	public ArrayList<PedidoItem> listarItens(int Idf_Pedido) {
		return super.listarItensPedido(Idf_Pedido);
	}
	
}
