package br.edu.opet.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.opet.entity.model.Usuario;

@ManagedBean
@SessionScoped
public class UsuarioLogadoController {
    
    private Usuario user;
    private Boolean usuarioLogado;
    
    private static UsuarioLogadoController instance;

    @PostConstruct
    public void inicializa()
    {
        user = new Usuario();
        usuarioLogado = Boolean.FALSE;
        instance = this;
    }
    
    public static UsuarioLogadoController getInstance() throws Exception
    {
        if(instance == null)
        {
            throw new Exception("Não há usuario logado no sistema, Oh my god!");
        }
        return instance;
    }
    
    public void logout()
    {
        this.user = null;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
    }
    
    public String fazerLogin()
    {
        try {
            Usuario u = new Usuario().buscarUsuario(user);           
            if(u == null)
            {
               //Erro usuario ou senha incorretos
            	return null;
            }
            else
            {
                usuarioLogado = Boolean.TRUE;
                user = u;
                
                if(u.getIdf_Tipo_Usuario() == 2) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/lista-pedidos.xhtml");
                    return "../ProjetoIntegrador/index.xhtml";
                }
                else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/index.xhtml");
                    return "../ProjetoIntegrador/index.xhtml";
                }

            }
            
        } catch (Exception e) {
            //"Erro ao efetuar login, tente novamente."
        	return null;
        }
        
		
    }
    
    public String getNomeUsuario() throws IOException
    {
        if(usuarioLogado)
        {
            return user.getNme_Pessoa();
        }
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/login.xhtml");
        return "";
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }
    
}