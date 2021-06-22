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
	
	//pessoa cai na tela login de adm (login é opcional mas eu acho que vai dar boa)
	//loga e vai pra lista de pedidos
	//pedido pode ser marcado como confirmado ou cancelado
	//pode ir pra tela para cadastrar novo produto
	//na tela de cadastrar produto pode voltar para sala adm ou finalizar
	//finalizando vai pra tela de sucesso e pode voltar pra sala adm
	//pode também listar os usuarios cadastrados no banco
	
	private String mensagem = "";
	
	public String getMensagem() {
		return mensagem;
	}
			
	public List<Pedido> listarPedidos(){
		Pedido ped = new Pedido();
		return ped.listar();	
	}
	
	public List<PedidoItem> listarItens(int Idf_Pedido){
		PedidoItem pedItem = new PedidoItem();
		return pedItem.listarItens(Idf_Pedido);	
	}
	
	public String cadastrarProduto(Produto prod) {
		
		if(prod.cadastrarProduto()) {
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
