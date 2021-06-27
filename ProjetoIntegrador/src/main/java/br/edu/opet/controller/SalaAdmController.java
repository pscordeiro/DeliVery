package br.edu.opet.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import br.edu.opet.entity.model.Pedido;
import br.edu.opet.entity.model.PedidoItem;
import br.edu.opet.entity.model.Produto;
import br.edu.opet.entity.model.Usuario;

@ManagedBean
@RequestScoped
public class SalaAdmController {
		
	private String mensagem = "";
	
	public String getMensagem() {
		return mensagem;
	}
			
	public List<Pedido> listarPedidos(){
		Pedido ped = new Pedido();
		return ped.listar();	
	}
	
	public boolean verificarListaPedidoVazia(){
		Pedido ped = new Pedido();
		List<Pedido> alPedido = ped.listar();	
		if(alPedido.size() == 0) {		
			return true;				
		}
		return false;		
	}
	
	public List<PedidoItem> listarItens(int Idf_Pedido){
		PedidoItem pedItem = new PedidoItem();
		return pedItem.listarItens(Idf_Pedido);	
	}
	
	public Double TotalPedido(int Idf_Pedido) {
		PedidoItem pedItem = new PedidoItem();
		return pedItem.TotalPedido(Idf_Pedido, pedItem);	
	}
	
	public String pedidoFinalizado(Pedido ped) {
		ped.finalizarPedido(ped);
		return "";
	}
	
	public String pedidoCancelado(Pedido ped) {
		ped.cancelarPedido(ped);
		return "";
	}
	
	public String cadastrarProduto(Produto prod) {
		
		if(prod.cadastrarProduto(prod)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/cadproduto-sucesso.xhtml");
			} catch (IOException e) {
				System.out.println(e);
			}
			return "";
		}
		else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/cadproduto-falha.xhtml");
			} catch (IOException e) {
				System.out.println(e);
			}
			return "";
		}
	}
	
	public String excluir(Usuario us) {		
		if(us.deletarUsuario()) {
			mensagem = "Deletado com sucesso !";
			return "/ProjetoIntegrador/usuario/cadusuario-sucesso.xhtml"; 
		}
		else {
			mensagem = "Falha ao deletar !";
			//Criar uma tela com erro ao criar usuario
			return "/ProjetoIntegrador/usuario/cadusuario-erro.xhtml";
		}
	}
	
	public String redirectToCadProduto() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/cadastrar-produto.xhtml");
		} catch (IOException e) {
			System.out.println(e);
		}
		return "";
	}
	
	public String redirectToListarClientes() {
	    try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/listar-usuarios.xhtml");
		} catch (IOException e) {
			System.out.println(e);
		}
		return "";
	}
	
	public String redirectToSalaAdm() {
	    try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/listar-pedidos.xhtml");
		} catch (IOException e) {
			System.out.println(e);
		}
		return "";
	}

}
