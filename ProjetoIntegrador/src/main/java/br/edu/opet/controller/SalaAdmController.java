package br.edu.opet.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.entity.model.Pedido;
import br.edu.opet.entity.model.PedidoItem;
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
	
	public String excluir(Usuario us) {		
		if(us.deletarUsuario()) {
			mensagem = "Deletado com sucesso !";
			return "/usuario/cadusuario-sucesso.xhtml"; 
		}
		else {
			mensagem = "Falha ao deletar !";
			//Criar uma tela com erro ao criar usuario
			return "/usuario/cadusuario-erro.xhtml";
		}
	}

}
