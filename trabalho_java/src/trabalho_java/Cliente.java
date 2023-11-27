//Trabalho Feito por Felipe Congio Albino e Danilo Lopes Fiuza , HT301410X E HT3014371

package trabalho_java;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import oracle.jdbc.OracleResultSet;

public class Cliente {
	
	public void menu() throws SQLException {
		Scanner tec = new Scanner(System.in);
		int resposta = 0;
		 while(resposta!=5) {
			 System.out.println("Menu");
			 System.out.println("Inserir - 1");
			 System.out.println("Listar - 2");
			 System.out.println("Deletar - 3");
			 System.out.println("Atualizar - 4");
			 System.out.println("Sair - 5");
			 resposta = tec.nextInt();

			 switch(resposta) {
			 	
			 	case 1:
			 		inserir();
			 		break;
			 	case 2:
			 		listar();
			 		break;
			 	case 3:
			 		deletar();
			 		break;
			 	case 4:
			 		atualizar();
			 		break;
			 	case 5:
			 		break;
			 }
			 
			 	
			
		}
	}
	
	private void inserir() throws SQLException {
		Scanner tec = new Scanner(System.in);
		Conexao c = new Conexao();
			Connection con = c.conectar();
			PreparedStatement stmt = con.prepareStatement( "insert into cliente(cpf,nome,email) values (?,?,?)");
			System.out.println("cpf:");
			int cpf= tec.nextInt();
			PreparedStatement stmtCpf = con.prepareStatement("SELECT * from cliente where cpf="+cpf);
			ResultSet rs = stmtCpf.executeQuery();
			while(rs.next()) {
				if(cpf == rs.getInt("cpf")) {
					System.out.println("Ja existe este cpf!Escolha outro: ");
					inserir();
				}
			}
			System.out.println("nome:");
			String nome = tec.next();
			
			System.out.println("email:");
			String email = tec.next();
			tec.nextLine();
			
			
			stmt.setInt(1, cpf);
			stmt.setString(2, nome);
			stmt.setString(3, email);
			stmt.executeUpdate();
			System.out.print("\n Inserido! Pressione qualquer Tecla e Enter para voltar para o menu");
			tec.nextLine();
	}
	private void listar() throws SQLException {
		Conexao c = new Conexao();
			Connection con = c.conectar();
			PreparedStatement stmt = con.prepareStatement("SELECT * from cliente");
			ResultSet rs = stmt.executeQuery();
			int nPessoa = 1;
			while(rs.next()){
				System.out.println("\nPessoa "+nPessoa);
				System.out.print("\nCPF: "+rs.getInt("cpf"));
				System.out.print(" || Nome: "+ rs.getString("nome"));
				System.out.print(" || Email: "+rs.getString("email"));
				nPessoa++;
			}
	}
	private void deletar() throws SQLException {
		Scanner tec = new Scanner(System.in);
		Conexao c = new Conexao();
		Connection con = c.conectar();
		listar();
		System.out.print("\nInsira o CPF da Pessoa que vai ser Excluida: ");
		int cpf = tec.nextInt();
		PreparedStatement stmtCpf = con.prepareStatement("SELECT * from cliente where cpf="+cpf);
		ResultSet rs = stmtCpf.executeQuery();
		boolean deleted = false;
		while(rs.next()) {
			if(cpf == rs.getInt("cpf")) {
				PreparedStatement stmt = con.prepareStatement("DELETE FROM cliente WHERE cpf = "+cpf);
				stmt.executeQuery();
				
				deleted = true;
			}
		}
		if(deleted) {
			System.out.println("\nPessoa Excluida com Sucesso! Pressione Qualquer Tecla e ENTER para voltar");
			tec.next();
		}
		else {
			System.out.println("Nao ha pessoas com este CPF, Pressione Qualquer Tecla e Enter para continuar");
			tec.next();
			deletar();
		}
		
	}
	private void atualizar() throws SQLException {
		
		Scanner tec = new Scanner(System.in);
		Conexao c = new Conexao();
		Connection con = c.conectar();
		
		listar();
		
		System.out.print("\nInsira o CPF da Pessoa a ser Atualizada: ");
		int cpf = tec.nextInt();
		
		PreparedStatement stmtCpf = con.prepareStatement("SELECT * from cliente where cpf="+cpf);
		ResultSet rs = stmtCpf.executeQuery();
		
		boolean updated = false;
		
		while(rs.next()) {
			if(cpf == rs.getInt("cpf")) {
				PreparedStatement stmt = con.prepareStatement("UPDATE cliente SET cpf=?, nome=?,email=? WHERE cpf=?");
				
				System.out.println("nome:");
				String nome = tec.next();
				
				System.out.println("email:");
				String email = tec.next();
				tec.nextLine();
				
				stmt.setString(4,String.valueOf(cpf));
				stmt.setInt(1,cpf);
				stmt.setString(2,nome);
				stmt.setString(3, email);
				
				stmt.executeQuery();
				
				updated = true;
			}
		}
		if(updated) {
			System.out.println("\nPessoa Atualizada com Sucesso! Pressione Qualquer Tecla e ENTER para voltar");
			tec.next();
		}
		else {
			System.out.println("Nao ha pessoas com este CPF, Pressione Qualquer Tecla e Enter para continuar");
			tec.next();
			atualizar();
		}

		
	}
}
