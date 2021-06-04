package br.edu.opet.entity.model;

import java.sql.Connection;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.opet.model.entity.dao.EnderecoDAO;

@ManagedBean
@SessionScoped
public class Endereco extends EnderecoDAO {
	
	
	private int Idf_Endereco;
	private String Desc_Logradouro;
	private String Num_CEP;
	private String Num_Endereco;
	private String Desc_Complemento;
	private String Des_Bairro;
	private int Idf_Cidade;
	private String Nme_Cidade;
	private String Sig_Estado;
	
	public int getIdf_Endereco() {
		return Idf_Endereco;
	}
	public void setIdf_Endereco(int idf_Endereco) {
		Idf_Endereco = idf_Endereco;
	}
	public String getDesc_Logradouro() {
		return Desc_Logradouro;
	}
	public void setDesc_Logradouro(String desc_Logradouro) {
		Desc_Logradouro = desc_Logradouro;
	}
	public String getNum_CEP() {
		return Num_CEP;
	}
	public void setNum_CEP(String num_CEP) {
		Num_CEP = num_CEP;
	}
	public String getNum_Endereco() {
		return Num_Endereco;
	}
	public void setNum_Endereco(String num_Endereco) {
		Num_Endereco = num_Endereco;
	}
	public String getDesc_Complemento() {
		return Desc_Complemento;
	}
	public void setDesc_Complemento(String desc_Complemento) {
		Desc_Complemento = desc_Complemento;
	}
	public String getDes_Bairro() {
		return Des_Bairro;
	}
	public void setDes_Bairro(String des_Bairro) {
		Des_Bairro = des_Bairro;
	}
	public int getIdf_Cidade() {
		return Idf_Cidade;
	}
	public void setIdf_Cidade(int idf_Cidade) {
		Idf_Cidade = idf_Cidade;
	}
	
	public String getNme_Cidade() {
		return Nme_Cidade;
	}
	public void setNme_Cidade(String nme_Cidade) {
		Nme_Cidade = nme_Cidade;
	}
		
	public String getSig_Estado() {
		return Sig_Estado;
	}
	public void setSig_Estado(String sig_Estado) {
		Sig_Estado = sig_Estado;
	}
	
	public boolean inserirEndereco(Endereco end, Connection conn) {
		return super.inserir(end, conn);
	}
	
	public boolean atualizarEndereco(Connection conn, Endereco end) {
		return super.atualizar(end, conn);
	}
	
	public ArrayList<Endereco> listar() {
		return super.listar();
	}
	
}
