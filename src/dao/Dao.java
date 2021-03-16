package dao;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class Dao {
	
	private Connection conexao = null;
	
	public static String usuario = "postgres";
	public static String senha = "postgres";
	public static String nomeBancoDados = "postgres";
	public static String ipBanco = "localhost";
	
	public Connection getConexao() {
	
		try {
			ipBanco = InetAddress.getLocalHost().getHostAddress();
		}catch (UnknownHostException e) {
			e.printStackTrace();
			ipBanco = "localhost";
		}
		
		try {
			
			Class.forName("org.postgresql.Driver");
			conexao = DriverManager.getConnection("jdbc:postgresql://"+ipBanco+":5432/" + nomeBancoDados,
					usuario, senha);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conexao;
	}
	
	public void closeConection() {
		try {
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
} 