package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Endereco;
import br.edu.opet.entity.model.Produto;
import br.edu.opet.util.conexao;

public class CarrinhoDAO {
	
	protected boolean adicionarAoCarrinho(Produto prod) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("INSERT INTO PI_Carrinho() VALUES(?,?,?,?)");
					
			stmt.setString(1,us.getNum_CPF());
			stmt.setString(2,us.getNme_Pessoa());
			stmt.setInt(3,us.getIdf_Sexo());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected == 1){
				conn.commit();
				stmt.close();
				conn.close();
				return true;
			}
			else {
				conn.rollback();
				stmt.close();
				conn.close();
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}

		}
		return false;	
	}
	protected boolean adicionarAoCarrinhoExistente(Produto prod, Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("");
					
			stmt.setString(1,us.getNum_CPF());
			stmt.setString(2,us.getNme_Pessoa());
			stmt.setInt(3,us.getIdf_Sexo());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected == 1){
				conn.commit();
				stmt.close();
				conn.close();
				return true;
			}
			else {
				conn.rollback();
				stmt.close();
				conn.close();
				return false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}

		}
		return false;	
	}

}
