package br.edu.opet.entity.model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.model.entity.dao.PedidoDAO;

@ManagedBean
@RequestScoped
public class Pedido extends PedidoDAO {
	
	private int Idf_Pedido;
	private Date DataHoraPedido;
	private int Idf_Operacao;
	private int Idf_Situacao;
	private int Idf_Usuario;
	private PedidoItem pedidoItem = new PedidoItem();
	private Usuario usuario = new Usuario();
	private Endereco endereco = new Endereco();
		
	public int getIdf_Pedido() {
		return Idf_Pedido;
	}
	public void setIdf_Pedido(int idf_Pedido) {
		Idf_Pedido = idf_Pedido;
	}
	public Date getDataHoraPedido() {
		return DataHoraPedido;
	}
	public void setDataHoraPedido(Date dataHoraPedido) {
		DataHoraPedido = dataHoraPedido;
	}
	public int getIdf_Operacao() {
		return Idf_Operacao;
	}
	public void setIdf_Operacao(int idf_Operacao) {
		Idf_Operacao = idf_Operacao;
	}
	public int getIdf_Situacao() {
		return Idf_Situacao;
	}
	public void setIdf_Situacao(int idf_Situacao) {
		Idf_Situacao = idf_Situacao;
	}
	public int getIdf_Usuario() {
		return Idf_Usuario;
	}
	public void setIdf_Usuario(int idf_Usuario) {
		Idf_Usuario = idf_Usuario;
	}
	
	public PedidoItem getPedidoItem() {
		return pedidoItem;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}
	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public void setPedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem = pedidoItem;
	}
	public ArrayList<Pedido> listar() {
		return super.listarPedidos();
	}
	public boolean salvarPedido(Pedido ped, Carrinho car) {
		return super.salvarPedido(ped, car);
	}
	public boolean inserirPedido(Pedido ped, Connection conn) {
		return super.inserirPedido(ped, conn);
	}
}
