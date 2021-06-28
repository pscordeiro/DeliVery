package br.edu.opet.controller;

import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Pedido;

@ManagedBean
@SessionScoped
public class PedidoController{
	
	//salva pedido
	public String inserirPedido(Carrinho car, UsuarioLogadoController ULC) {
		
		Pedido ped = new Pedido();
		int Idf_usuario = ULC.getUser().getIdf_Usuario();
				
		ped.setIdf_Usuario(Idf_usuario);
		if(ped.salvarPedido(ped,car)) {
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/usuario/pedido-sucesso.xhtml");
			} catch (IOException e) {
				System.out.println(e);
			}
			return "";
		}
		else {
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/usuario/pedido-falha.xhtml");
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		return "";			
	}

}
