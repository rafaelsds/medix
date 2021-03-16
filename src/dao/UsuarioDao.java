package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Usuario;

public class UsuarioDao extends Dao {

	public void update(Usuario d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Usuario SET dt_nascimento = ?, nm_usuario = ?,	nm_pessoa = ?,"
							+ "	ds_senha = ?,ds_email = ?, ds_papel_usuario = ?, nr_telefone = ?, ie_situacao = ? where nr_sequencia = ?");

			pstm.setTimestamp(1, d.getDataNascimento());
			pstm.setString(2, d.getLogin());
			pstm.setString(3, d.getNomeUsuario());
			pstm.setString(4, d.getSenha());
			pstm.setString(5, d.getEmail());
			pstm.setString(6, d.getPapelUsuario());
			pstm.setString(7, d.getTelefone());
			pstm.setString(8, d.getSituacao());
			pstm.setInt(9, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void updateUserIdTelegram(int id, int idUserTelegram) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Usuario SET id_user_telegram = ? where nr_sequencia = ?");

			pstm.setInt(1, idUserTelegram);
			pstm.setInt(2, id);
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insert(Usuario d) {
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Usuario(nr_sequencia, dt_nascimento, nm_usuario,nm_pessoa,ds_senha,ds_email, ds_papel_usuario, nr_telefone, ie_situacao) values (?,?,?,?,?,?,?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setTimestamp(2, d.getDataNascimento());
			pstm.setString(3, d.getLogin());
			pstm.setString(4, d.getNomeUsuario());
			pstm.setString(5, d.getSenha());
			pstm.setString(6, d.getEmail());
			pstm.setString(7, d.getPapelUsuario());
			pstm.setString(8, d.getTelefone());
			pstm.setString(9, d.getSituacao());
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 1;

	}

	public void delete(int id) {

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("delete from Usuario where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Usuario> getAll() {

		List<Usuario> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Usuario order by nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Usuario d = new Usuario();
				d.setId(rs.getInt("nr_sequencia"));
				d.setDataNascimento(rs.getTimestamp("dt_nascimento"));
				d.setEmail(rs.getString("ds_email"));
				d.setLogin(rs.getString("nm_usuario"));
				d.setNomeUsuario(rs.getString("nm_pessoa"));
				d.setSenha(rs.getString("ds_senha"));
				d.setPapelUsuario(rs.getString("ds_papel_usuario"));
				d.setTelefone(rs.getString("nr_telefone"));
				d.setSituacao(rs.getString("ie_situacao"));
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	
	public List<Usuario> getAll(String paramWhere) {
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		List<Usuario> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Usuario a "+paramWhere);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Usuario d = new Usuario();
				d.setId(rs.getInt("nr_sequencia"));
				d.setDataNascimento(rs.getTimestamp("dt_nascimento"));
				d.setEmail(rs.getString("ds_email"));
				d.setLogin(rs.getString("nm_usuario"));
				d.setNomeUsuario(rs.getString("nm_pessoa"));
				d.setSenha(rs.getString("ds_senha"));
				d.setPapelUsuario(rs.getString("ds_papel_usuario"));
				d.setTelefone(rs.getString("nr_telefone"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setIdUserTelegram(rs.getInt("id_user_telegram"));;
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	public Usuario getUsuario(int id) {
		Usuario d = new Usuario();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Usuario where nr_sequencia = ?");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setDataNascimento(rs.getTimestamp("dt_nascimento"));
				d.setEmail(rs.getString("ds_email"));
				d.setLogin(rs.getString("nm_usuario"));
				d.setNomeUsuario(rs.getString("nm_pessoa"));
				d.setSenha(rs.getString("ds_senha"));
				d.setPapelUsuario(rs.getString("ds_papel_usuario"));
				d.setTelefone(rs.getString("nr_telefone"));
				d.setSituacao(rs.getString("ie_situacao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public Usuario getUsuario(String paramWhere) {
		
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		
		Usuario d = new Usuario();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Usuario a where "+paramWhere);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setDataNascimento(rs.getTimestamp("dt_nascimento"));
				d.setEmail(rs.getString("ds_email"));
				d.setLogin(rs.getString("nm_usuario"));
				d.setNomeUsuario(rs.getString("nm_pessoa"));
				d.setSenha(rs.getString("ds_senha"));
				d.setPapelUsuario(rs.getString("ds_papel_usuario"));
				d.setTelefone(rs.getString("nr_telefone"));
				d.setSituacao(rs.getString("ie_situacao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}
	
	public int obterSeq() {
		int seq = 0;

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Usuario_seq')seq");

			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				seq = rs.getInt("seq");
			}
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return seq;
	}
	
	public Usuario login(String usuario, String senha) {

		Usuario u = new Usuario();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Usuario where ie_situacao = 'A' and nm_usuario = ? and ds_senha = ?");

			pstm.setString(1, usuario);
			pstm.setString(2, senha);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				u.setId(rs.getInt("nr_sequencia"));
				u.setEmail(rs.getString("ds_email"));
				u.setPapelUsuario(rs.getString("ds_papel_usuario"));
				u.setLogin(rs.getString("nm_usuario"));
				pstm.close();
				conexao.close();
				return u;
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}