package br.edu.opet.entity.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.opet.model.entity.dao.LoginDAO;


@ManagedBean
@SessionScoped
public class Login extends LoginDAO{
	
    private String email;
    private String senha;
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

	public boolean buscarUsuario(Login login) {
		return super.buscarUsuario(login);
	}
	
}
