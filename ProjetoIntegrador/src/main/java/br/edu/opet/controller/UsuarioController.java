package br.edu.opet.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.opet.entity.Endereco;
import br.edu.opet.entity.Usuario;

@ManagedBean
@RequestScoped
public class UsuarioController {
	
	public UsuarioController() {}
	
	private String mensagem = "";
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
	public List<Usuario> listar(){
		Usuario us = new Usuario();
		return us.listar();	
	}

	public String salvar(Usuario us, Endereco end) {
		boolean retorno = false;
		
		if(us.getIdf_Usuario() == 0)
			retorno = us.inserir(end);
		else
			retorno = us.atualizar();
		
		if(retorno)
			mensagem = "Salvo com sucesso !";
			
		else
			mensagem = "Erro ao salvar";	
					
		return "/usuario/listar.xhtml"; 						
	}
	
	public String inserir() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("usuario", new Usuario());
		return "/usuario/inserir.xhtml"; 
	}
	
	public String atualizar(Usuario usuario) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
		return "/usuario/inserir.xhtml"; 
	}
	
	public String excluir(Usuario us) {		
		if(us.deletar()) {
			mensagem = "Deletado com sucesso !";
			//Criar um return/boasvindas
			return "/usuario/listar.xhtml"; 
		}
		else {
			mensagem = "Falha ao deletar !";
			//Criar uma tela com erro ao criar usuario
			return "/usuario/listar.xhtml";
		}
	}
		
}
