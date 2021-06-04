package br.edu.opet.controller;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

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
	
	Carrinho car = new Carrinho();
	
	//adiciona produto no carrinho
	public String adicionarCarrinho(Produto prod){
		//se estiver logado
		if(car.getIdf_Carrinho() != 0) {
			if(car.adicionarAoCarrinhoExistente(prod, car)) {
				//toast de adicionado ao carrinho
			}
			else {
				//erro ao adicionar ao carrinho
			}
		}
		else {
			if(car.adicionarAoCarrinho(prod)) {
				//toast de adicionado ao carrinho
			}
		}
		//else de logar o usuario pra poder adicionar ao carrinho
		return null;
	}
	

}
