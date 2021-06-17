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
    private String redirectUrl;
    private int codRedirect;
    
    private static UsuarioLogadoController instance;

    @PostConstruct
    public void inicializa()
    {
        user = new Usuario();
        usuarioLogado = Boolean.FALSE;
        instance = this;
    }
    
    public String redirectToLogin(int codUrl) throws IOException {
    	
    	setarRedirecionamento(codUrl);

    	if(usuarioLogado)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/index.xhtml");
        	return "";
        }
        else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/login.xhtml");
            return "";
        }
    	
    }
    
    public String fazerLogin() throws IOException
    {
    	Usuario u = new Usuario().buscarUsuario(user);           
	    if(u == null)
	    {
	       //Erro usuario ou senha incorretos
	    	return null;
	    }
	    else {
	        usuarioLogado = Boolean.TRUE;
	        user = u;
        
	        if(u.getIdf_Tipo_Usuario() == 2) {
	            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/lista-pedidos.xhtml");
	            return "";
	        }
	        else {
	        	FacesContext.getCurrentInstance().getExternalContext().redirect(redirectUrl);
	        	return "";
	        }
	    }
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
    
    public String checkUserCredential() throws IOException
    {
    	if(usuarioLogado){
    		
			if(user.getIdf_Tipo_Usuario() == 2) {
			  return "";
			}
			else {
			  //acesso negado;
			  FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/index.xhtml");
			  return "";
			}   		
    	}
    	else {
    		return redirectToLogin(0);
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
    
    public boolean taLogado() {
        if(usuarioLogado)
            return true;
        else
        	return false;    
    }
    
    public Usuario getUser() {
        return user;
    }
    
    public void setUser(Usuario user) {
        this.user = user;
    }
    
    public void setarRedirecionamento(int codRedirect) {
		
    	switch(codRedirect){
			case 0:			
				setRedirectUrl("/ProjetoIntegrador/index.xhtml");
				break;
			case 1:
				setRedirectUrl("/ProjetoIntegrador/usuario/carrinho.xhtml");
				break;
			default:
				setRedirectUrl("/ProjetoIntegrador/index.xhtml");
				break;
		}	
    	
    }

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public int getCodRedirect() {
		return codRedirect;
	}

	public void setCodRedirect(int codRedirect) {
		this.codRedirect = codRedirect;
	}
       
}