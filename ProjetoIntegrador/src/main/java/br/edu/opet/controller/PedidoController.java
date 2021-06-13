package br.edu.opet.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Pedido;

@ManagedBean
@RequestScoped
public class PedidoController {
	
	//salva pedido
	public String inserirPedido(Carrinho car) {
		Pedido ped = new Pedido();
		if(ped.salvarPedido(ped,car)) {
			return "pedido-sucesso.xhtml";
		}
		else {
			return "";
		}			
	}

}
