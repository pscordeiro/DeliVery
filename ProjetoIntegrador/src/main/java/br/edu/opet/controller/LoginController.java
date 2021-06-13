package br.edu.opet.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.opet.entity.model.Login;

@ManagedBean
public class LoginController {
		
	public String logar(Login login) {
		
		if(login.buscarUsuario(login)) {
			
			HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
					.getExternalContext().getSession(false);
			session.setAttribute("idf_usuario", login.getUsuario().getIdf_Usuario());
			session.setAttribute("idf_tipo_usuario", login.getUsuario().getIdf_Tipo_Usuario());
			
			if(login.getUsuario().getIdf_Tipo_Usuario() == 2) {
				return "/sala-adm/lista-pedidos.xhtml";
			}
			else {
				return "/home-logada.xhtml";
			}		
		}
		else {
			//login nao existe
		}
		
		return null;
		
	}

}
