package br.edu.opet.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.opet.entity.model.Usuario;

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
	
	public String verPerfil(Usuario usuario) {
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("usuario", usuario);
		return "/usuario/cad-perfil-usuario.xhtml";	
	}
	
	public List<Usuario> listar(){
		Usuario us = new Usuario();
		return us.listarUsuarios();	
	}
	
	public String salvar(Usuario us) {
		boolean retorno = false;
		
		if(us.getIdf_Usuario() == 0)
			retorno = us.inserirUsuarios();
		else
			retorno = us.atualizarUsuario();
		
		if(retorno) {
			mensagem = "Salvo com sucesso !";	
            try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/usuario/cadusuario-sucesso.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
				return "";
			}		
			return "";
		}	
		else {
			try {
				mensagem = "Erro ao salvar";	
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/usuario/cadusuario-falha.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
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
		if(us.deletarUsuario()) {
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
