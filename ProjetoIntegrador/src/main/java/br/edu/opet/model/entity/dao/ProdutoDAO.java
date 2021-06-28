package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.opet.entity.model.Produto;
import br.edu.opet.util.conexao;

public class ProdutoDAO {
	
	protected ArrayList<Produto> listarProdutos() {
		
		ArrayList<Produto> alProduto = new ArrayList<Produto>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
			 ("SELECT PIP.Idf_Produto ,Desc_Produto, Valor_Produto, Quantidade, Url_Produto "
	 		+ "FROM PI_Produtos PIP "
	 		+ "JOIN PI_Estoque PIE on PIE.Idf_Produto = PIP.Idf_Produto "
	 		+ "WHERE Quantidade > 0 AND Flg_Inativo = 0");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Produto prod = new Produto(); 
				prod.setIdf_Produto(rs.getInt("Idf_Produto"));
				prod.setDesc_Produto(rs.getString("Desc_Produto"));
				prod.setValor_Produto(rs.getDouble("Valor_Produto"));
				prod.setUrl_Produto(rs.getString("Url_Produto"));
				prod.setQuantidade(rs.getInt("Quantidade"));

				alProduto.add(prod);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alProduto;
			
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return null;
			}

		}
		return alProduto;	
	}
	protected boolean cadastrarProduto(Produto prod) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
			
			stmt = conn. prepareStatement("INSERT INTO PI_Produtos(Desc_Produto, Valor_Produto, Flg_Inativo, Url_Produto) "
					+ "VALUES(?,?,0,?)",Statement.RETURN_GENERATED_KEYS);
					
			stmt.setString(1,prod.getDesc_Produto());
			stmt.setDouble(2,prod.getValor_Produto());
			stmt.setString(3,prod.getUrl_Produto());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected == 1){	
				
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int id = rs.getInt(1);
				prod.setIdf_Produto(id);
				
				if(inserirEstoque(prod, conn)) {
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
	
	protected boolean inserirEstoque(Produto prod, Connection conn) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn. prepareStatement("INSERT INTO PI_Estoque(Idf_Produto, Quantidade) VALUES(?,?)");
				
			stmt.setInt(1,prod.getIdf_Produto());
			stmt.setInt(2,prod.getQuantidade());
		
			int rowAffected = stmt.executeUpdate();
			
			if(rowAffected == 1){
				stmt.close();
				return true;
			}
			else {
				stmt.close();
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e1) {
				return false;
			}
		}
		return false;	
	}
	
}
