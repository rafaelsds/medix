package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Localizacao;

public class LocalizacaoDao extends Dao {

	public void update(Localizacao d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Localizacao SET ds_Localizacao = ?,	"
							+ "	ie_situacao = ? where nr_sequencia = ?");

			pstm.setString(1, d.getLocalizacao());
			pstm.setString(2, d.getSituacao());
			pstm.setInt(3, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean existe(Localizacao d) {
		boolean achou = false;
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Localizacao where nr_sequencia = ? ");
			pstm.setInt(1, d.getId());
			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				achou = true;
			}
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return achou;
	}
	
	public int insert(Localizacao d) {
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Localizacao(nr_sequencia, ds_Localizacao, ie_situacao) values (?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setString(2, d.getLocalizacao());
			pstm.setString(3, d.getSituacao());
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
			PreparedStatement pstm = conexao.prepareStatement("delete from Localizacao where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Localizacao> getAll() {

		List<Localizacao> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Localizacao order by nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Localizacao d = new Localizacao();
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
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

	
	public List<Localizacao> getAll(String paramWhere) {
		return getAll(paramWhere,null);
	}
	
	public List<Localizacao> getAll(String paramWhere, String orderBy) {
		
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		orderBy="order by "+(orderBy == null || orderBy.isEmpty() ? " 1 asc " : orderBy);
		
		List<Localizacao> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Localizacao a "+paramWhere + orderBy);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Localizacao d = new Localizacao();
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
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
	
	public Localizacao getLocalizacao(int id) {
		Localizacao d = new Localizacao();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Localizacao where nr_sequencia = ?");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				d.setSituacao(rs.getString("ie_situacao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public Localizacao getLocalizacao(String atributo, String valorParam) {
		Localizacao d = new Localizacao();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Localizacao where "+atributo+" = ?");

			pstm.setString(1, valorParam);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
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
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Localizacao_seq')seq");

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

}