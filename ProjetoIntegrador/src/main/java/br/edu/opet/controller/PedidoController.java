package br.edu.opet.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Pedido;

@ManagedBean
@SessionScoped
public class PedidoController{
	
	//salva pedido
	public String inserirPedido(Carrinho car, UsuarioLogadoController ULC) {
		
		Pedido ped = new Pedido();
		int Idf_usuario = ULC.getUser().getIdf_Usuario();
		
		if(Idf_usuario != 0) {
			//vai logar corno
		}
		
		ped.setIdf_Usuario(Idf_usuario);
		if(ped.salvarPedido(ped,car)) {
			return "pedido-sucesso.xhtml";
		}
		else {
			return "";
		}			
	}

}
