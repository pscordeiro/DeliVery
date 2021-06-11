package br.edu.opet.controller;

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
	
	//Tela inicial é a home deslogada listando os produtos
	//Cliente pode ir para o login ou
	//Cliente pode ir adicionando os produtos no carrinho
	//Ao clicar para ir ao carrinho logar
	//Após logar redirecionar para finalizar compra
	//Na tela de login podera ir para o cadastro de novo usuario
	//Na tela de cadastro finalizado poderá voltar para home porém logado
	//Na tela de finalizar compra podera remover ou nao itens adicionados e finalizar redirecionando para tela de sucesso
	//Na tela de pedido realizado podera voltar para home
	
	public List<Produto> listarProdutos(){
		Produto prod = new Produto();
		return prod.listar();	
	}
		
	//adiciona produto no carrinho
	public String adicionarCarrinho(Produto prod, Carrinho car){
		int Idf_Carrinho = car.getIdf_Carrinho();
		if(car.getIdf_Carrinho() != 0) {
			if(car.adicionarAoCarrinhoExistente(prod, Idf_Carrinho)) {
				//toast de adicionado ao carrinho
			}
			else {
				//erro ao adicionar ao carrinho
			}
		}
		else {
			Carrinho carrinho = new Carrinho();
			if(car.adicionarAoCarrinho(prod, carrinho)) {
				int idf_carrinho = carrinho.getIdf_Carrinho();
				HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
						.getExternalContext().getSession(false);
				session.setAttribute("idf_Carrinho", idf_carrinho);
				//toast de adicionado ao carrinho
			}
		}
		return null;
	}
	

}
