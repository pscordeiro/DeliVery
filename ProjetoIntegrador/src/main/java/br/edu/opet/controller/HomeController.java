package br.edu.opet.controller;

import java.io.IOException;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Produto;

@ManagedBean
@RequestScoped
public class HomeController {
		
	private String mensagem = "";
	
	public String getMensagem() {
		return mensagem;
	}	
	
	public List<Produto> listarProdutos(){
		Produto prod = new Produto();
		return prod.listar();	
	}
		
	//adiciona produto no carrinho
	public String adicionarCarrinho(Produto prod, Carrinho car, int quantidade){
		
		prod.setQuantidade(quantidade);
		if(car.getIdf_Carrinho() != 0 && car != null) {
			if(car.verItensCarrinho(prod, car)) {
				//produto já está no carrinho, quantidade aumentada
				mensagem = "Produto já está no carrinho, quantidade incrementada";
				return "";
			}
			else if(car.adicionarAoCarrinhoExistente(prod, car)) {
				//toast de adicionado ao carrinho
				mensagem = "Produto adicionado ao carrinho";
				return "";
			}
			else {
				//erro ao adicionar ao carrinho
				mensagem = "Erro ao tentar adicionar produto no carrinho";
				return "";
			}
		}
		else {
			Carrinho carrinho = new Carrinho();
			if(car.adicionarAoCarrinho(prod, carrinho)) {
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
						.getExternalContext().getSession(false);
				session.setAttribute("carrinho", carrinho);
				//toast de adicionado ao carrinho
				mensagem = "Produto adicionado ao carrinho";
				return "/ProjetoIntegrador/index.xhtml";
			}
			else {
				//erro ao adicionar ao carrinho
				mensagem = "Erro ao tentar adicionar produto no carrinho";
				return "/ProjetoIntegrador/index.xhtml";
			}
		}
	}
	
	public List<Carrinho> listarCarrinho(Carrinho carrinho){	
		return carrinho.listarCarrinho(carrinho);		
	}
	
	public List<Carrinho> listarCarrinhoTotal(Carrinho carrinho){		
		return carrinho.listarCarrinhoTotal(carrinho);
	}
	
	public boolean verificarCarrinhoVazio(Carrinho carrinho) {
		List<Carrinho> alCarrinho = listarCarrinho(carrinho);		
		if(alCarrinho.size() == 0) {		
			return true;				
		}
		return false;
	}
	
	public boolean verificarProdutoNovo(Produto prod) {
		//if produto is novo return true se nao return false		
		return true;
	}
	
	public String verCarrinho(Carrinho carrinho, UsuarioLogadoController user){

	    try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/ProjetoIntegrador/usuario/carrinho.xhtml");
		} catch (IOException e) {
			System.out.println(e);
		}
		return "";	

	}
	
	public String apagarItemCarrinho(Carrinho carrinho){
		
		if(carrinho.apagarItemCarrinho(carrinho)) {
			//toast de sucesso
			mensagem = "Produto removido do carrinho";
			return "/ProjetoIntegrador/usuario/carrinho.xhtml";	
		}
		else {
			//toast de falha
			mensagem = "Falha ao remover produto";
			return "/ProjetoIntegrador/usuario/carrinho.xhtml";	
		}
		
	}
	
}
