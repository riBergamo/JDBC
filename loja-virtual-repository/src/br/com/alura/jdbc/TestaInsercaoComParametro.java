package br.com.alura.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {
	public static void main(String[] args) throws SQLException {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		try(Connection con = connectionFactory.recuperarConexao()) {//try com recursos: garante que oq eu abri entre parenteses se feche sem precisar dar .close()
			con.setAutoCommit(false);
		
			//try com recursos 
			try (PreparedStatement stm = 
				con.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)"
						, Statement.RETURN_GENERATED_KEYS)) {
				
				adicionarVariavel("SMARTTV", "SMARTTV 45 POLEGADAS", stm);
				adicionarVariavel("RÁDIO", "RÁDIO DE BATERIA", stm);
		
				con.commit();//isso quando o AutoCommit esta "desligado"
 
			} catch (Exception e) {
		
				e.printStackTrace();
				System.out.println("Rollback executado");
				con.rollback();//retorna o banco de dados ao estado anterior caso de uma exception
			
			}
		}	
	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);
		
		if (nome.equals("RÁDIO")) {
			throw new RuntimeException("Não foi possivel adicionar o produto, Sinto muito!");
		}
		
		stm.execute();
		
		
		try (ResultSet rst = stm.getGeneratedKeys()) {//try com recursos 
			while (rst.next()) {
				Integer id = rst.getInt(1);
				System.out.println("o id criado foi: " + id);
			}
		}
	}
}
