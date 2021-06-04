package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.opet.entity.model.Estoque;
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
			 ("SELECT PIP.Idf_Produto ,Desc_Produto, Valor_Produto, Quantidade "
	 		+ "FROM PI_Produtos PIP "
	 		+ "JOIN PI_Estoque PIE on PIE.Idf_Produto = PIP.Idf_Produto "
	 		+ "WHERE Quantidade > 0");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Produto prod = new Produto(); 
				Estoque est = new Estoque();
				prod.setIdf_Produto(rs.getInt("Idf_Produto"));
				prod.setDesc_Produto(rs.getString("Desc_Produto"));
				prod.setValor_Produto(rs.getDouble("Valor_Produto"));
				est.setQuantidade(rs.getInt("Quantidade"));
				prod.setEstoque(est);
				
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


}
