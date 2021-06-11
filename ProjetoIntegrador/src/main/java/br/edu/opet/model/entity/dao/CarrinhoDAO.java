package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Endereco;
import br.edu.opet.entity.model.Produto;
import br.edu.opet.util.conexao;

public class CarrinhoDAO {
	
	protected boolean adicionarAoCarrinho(Produto prod, Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("INSERT INTO PI_Carrinho(Idf_Produto, Quantidade,  Valor_Produto) "
					+ "VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
					
			stmt.setInt(1,prod.getIdf_Produto());
			stmt.setInt(2,prod.getQuantidade());
			stmt.setDouble(3,prod.getValor_Produto());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected == 1){
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int Idf_carrinho = rs.getInt(1);
				car.setIdf_Carrinho(Idf_carrinho);
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
	protected boolean adicionarAoCarrinhoExistente(Produto prod, int Idf_Carrinho) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("INSERT INTO PI_Carrinho");
					
			stmt.setString(1,us.getNum_CPF());


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
