package br.edu.opet.controller;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
    
	private String mensagem = "";
	
	public String getMensagem() {
		return mensagem;
	}
	
	public boolean temMensagem() {
		if(mensagem.length() > 0)
			return true;
		else
			return false;
	}
    
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
	        mensagem = "";
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
	        FacesContext.getCurrentInstance().addMessage("MessageId", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuário/Senha incorretos ou conta inexistente"));
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuário/Senha incorretos ou conta inexistente"));
	        mensagem = "Usuário/Senha incorretos ou conta inexistente";
	        return "";
	    }
	    else {
	        usuarioLogado = Boolean.TRUE;
	        user = u;
	        if(u.getIdf_Tipo_Usuario() == 2 && redirectUrl != "/ProjetoIntegrador/usuario/carrinho.xhtml") {
	            FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/sala-adm/listar-pedidos.xhtml");
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
        try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/index.xhtml");
		} catch (IOException e) {
			System.out.println(e);
		}
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
    
    public String redirectUsuarioLogado() {
        if(usuarioLogado) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/index.xhtml");
			} catch (IOException e) {
				System.out.println(e);
			}
			return "";        
        }
        else
        	return "";        
    }
    
    public boolean isAdm() {
        if(user.getIdf_Tipo_Usuario() == 2)
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