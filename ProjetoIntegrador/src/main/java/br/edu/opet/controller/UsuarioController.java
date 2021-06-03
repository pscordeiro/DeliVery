package br.edu.opet.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.opet.entity.Usuario;

@ManagedBean
@RequestScoped
public class UsuarioController {
			
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
	
	public String salvar(Usuario us) {
		boolean retorno = false;
		
		if(us.getIdf_Usuario() == 0)
			retorno = us.inserir();
		else
			retorno = us.atualizar();
		
		if(retorno) {
			mensagem = "Salvo com sucesso !";	
			return "/usuario/cadusuario-sucesso.xhtml";
		}	
		else {
			mensagem = "Erro ao salvar";	
			return "/usuario/cadusuario-falha.xhtml";
		}
		
	}
	
	public String inserir() {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("usuario", new Usuario());
		return "/usuario/cad-perfil-usuario.xhtml"; 
	}
	
	public String atualizar(Usuario usuario) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
		return "/usuario/cad-perfil-usuario.xhtml"; 
	}
	
	public String excluir(Usuario us) {		
		if(us.deletar()) {
			//mensagem = "Deletado com sucesso !";
			return "index.xhtml"; 
		}
		else {
			mensagem = "Falha ao deletar !";
			//Criar uma tela com erro ao deletar usuario
			return "index.xhtml";
		}
	}

		
}
