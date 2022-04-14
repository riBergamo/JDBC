package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.jdbc.dao.ProdutoDAO;
import br.com.alura.jdbc.modelo.Produto;

public class TestaInsercaoComListagemEProduto {

	public static void main(String[] args) throws SQLException {
		
		Produto comoda = new Produto("CÔMODA", "CÔMODA VERTICAL");
		Produto videoGame = new Produto("VIDEO GAME", "XBOX ONE S");
		
		try(Connection connection = new ConnectionFactory().recuperarConexao()) {
			
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(comoda);
			produtoDao.salvar(videoGame);
			
			List<Produto> lista = produtoDao.listar();
			lista.stream().forEach(lp -> System.out.println(lp));;			
						
		}
	}
}
