package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Produto;
import br.edu.opet.util.conexao;

public class CarrinhoDAO {
	
	protected ArrayList<Carrinho> listarCarrinho(Carrinho car) {
		
		ArrayList<Carrinho> alCarrinho = new ArrayList<Carrinho>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
					(" SELECT "
					+ " Idf_Carrinho_Grupo,"
					+ " PIC.Idf_Produto, "
					+ " PIP.Desc_Produto,"
					+ " PIC.Quantidade, "
					+ " PIC.Valor_Produto,"
					+ " (PIC.Quantidade * PIC.Valor_Produto) as SubTotal "
					+ " FROM PI_Carrinho PIC "
					+ " JOIN PI_Produtos PIP ON PIP.Idf_Produto = PIC.Idf_Produto "
					+ " WHERE Idf_Carrinho_Grupo = ?");
			
			stmt.setInt(1,car.getIdf_Carrinho());
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Produto prod = new Produto();
				Carrinho carrinho = new Carrinho();
				
				prod.setIdf_Produto(rs.getInt("Idf_Produto"));
				prod.setDesc_Produto(rs.getString("Desc_Produto"));
				prod.setQuantidade(rs.getInt("Quantidade"));
				prod.setValor_Produto(rs.getDouble("Valor_Produto"));
				carrinho.setSub_Total(rs.getDouble("SubTotal"));
				carrinho.setIdf_Carrinho(rs.getInt("Idf_Carrinho_Grupo"));
				carrinho.setProd_Carrinho(prod);
				
				alCarrinho.add(carrinho);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alCarrinho;
			
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
		return alCarrinho;	
	}
	
	protected ArrayList<Carrinho> listarCarrinhoTotal(Carrinho car) {
		
		ArrayList<Carrinho> alCarrinho = new ArrayList<Carrinho>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
					("SELECT \r\n"
					+ "SUM(quantidade) AS TotalItens,\r\n"
					+ "SUM((PIC.Quantidade * PIC.Valor_Produto)) AS ValorTotalCarrinho,\r\n"
					+ "Idf_Carrinho_Grupo\r\n"
					+ "FROM PI_Carrinho PIC\r\n"
					+ "WHERE Idf_Carrinho_Grupo = ?\r\n"
					+ "GROUP BY Idf_Carrinho_Grupo");
			
			stmt.setInt(1,car.getIdf_Carrinho());
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Carrinho carrinho = new Carrinho();
				carrinho.setTotal_Itens(rs.getInt("TotalItens"));
				carrinho.setTotal_Carrinho(rs.getDouble("ValorTotalCarrinho"));	
				carrinho.setIdf_Carrinho(rs.getInt("Idf_Carrinho_Grupo"));
				alCarrinho.add(carrinho);
				
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alCarrinho;
						
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
			}
			return alCarrinho;
		}
	}
	
	protected boolean adicionarAoCarrinho(Produto prod, Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("INSERT INTO PI_Carrinho(Idf_Produto, Quantidade, Valor_Produto, Flg_Inativo) "
					+ "VALUES(?,?,?,0)",Statement.RETURN_GENERATED_KEYS);
					
			stmt.setInt(1,prod.getIdf_Produto());
			stmt.setInt(2,prod.getQuantidade());
			stmt.setDouble(3,prod.getValor_Produto());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected == 1){
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int Idf_carrinho = rs.getInt(1);
				car.setIdf_Carrinho(Idf_carrinho);
				if(atualizaIdfCarrinhoGrupo(conn, Idf_carrinho)) {
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
	
	protected boolean adicionarAoCarrinhoExistente(Produto prod, Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
			
			stmt = conn. prepareStatement("INSERT INTO PI_Carrinho(Idf_Produto, Quantidade, Valor_Produto, Idf_Carrinho_Grupo, Flg_Inativo)"
					+ "VALUES(?,?,?,?,0)");
					
			stmt.setInt(1,prod.getIdf_Produto());
			stmt.setInt(2,prod.getQuantidade());
			stmt.setDouble(3,prod.getValor_Produto());
			stmt.setInt(4,car.getIdf_Carrinho());
				
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
	
	protected boolean atualizaIdfCarrinhoGrupo(Connection conn, int Idf_Carrinho) {
		
		PreparedStatement stmt = null;

		try {
			
			stmt = conn. prepareStatement("UPDATE PI_Carrinho SET Idf_Carrinho_Grupo = ? WHERE Idf_Carrinho_PK = ?");
			stmt.setInt(1,Idf_Carrinho);
			stmt.setInt(2,Idf_Carrinho);
			
			int rowAffected = stmt.executeUpdate();

			if(rowAffected == 1){
				conn.commit();
				stmt.close();
				return true;			
			}
			else {
				conn.rollback();
				stmt.close();
				return false;
			}
			
		}catch(SQLException e){
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}
		}		
		return false;	
	}
	
	protected boolean removerDoCarrinho(Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
			
			stmt = conn. prepareStatement("DELETE FROM PI_Carrinho WHERE "
					+ " Idf_Carrinho_Grupo = ? AND Idf_Produto = ? ");
					
			stmt.setInt(1,car.getIdf_Carrinho());
			stmt.setInt(2,car.getProd_Carrinho().getIdf_Produto());
				
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
	
	protected boolean verItemCarrinho(Produto prod, Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int existe = 0;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement("SELECT * from PI_Carrinho WHERE Idf_Produto = ? "
					+ "and Idf_Carrinho_Grupo = ? and Flg_Inativo = 0");
			
			stmt.setInt(1,prod.getIdf_Produto());
			stmt.setInt(2,car.getIdf_Carrinho());
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				existe = 1;				
			}
			
			if (existe == 1) {
				if(atualizarQuantidadeItem(conn, prod, car)) {
					rs.close();
					stmt.close();
					conn.close();
					return true;
				}
			}	
			
			rs.close();
			stmt.close();
			conn.close();
			return false;
			
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
	
	protected boolean atualizarQuantidadeItem(Connection conn, Produto prod ,Carrinho car) {
		
		PreparedStatement stmt = null;

		try {
			
			stmt = conn. prepareStatement("UPDATE PI_Carrinho SET QUANTIDADE = (QUANTIDADE + ?)"
					+ "WHERE Idf_Produto = ? and Idf_Carrinho_Grupo = ? and Flg_Inativo = 0");
			stmt.setInt(1,prod.getQuantidade());
			stmt.setInt(2,prod.getIdf_Produto());
			stmt.setInt(3,car.getIdf_Carrinho());

			int rowAffected = stmt.executeUpdate();

			if(rowAffected == 1){
				conn.commit();
				stmt.close();
				return true;			
			}
			else {
				conn.rollback();
				stmt.close();
				return false;
			}
			
		}catch(SQLException e){
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}
		}		
		return false;	
	}

	public boolean apagarCarrinho(Carrinho car) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
				
		try {
			conn = conexao.getConnection(false);
				
			stmt = conn. prepareStatement("DELETE FROM PI_Carrinho WHERE IDF_Carrinho_Grupo = ?");
					
			stmt.setInt(1,car.getIdf_Carrinho());

			int rowAffected = stmt.executeUpdate();
				
			if(rowAffected > 0){
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
