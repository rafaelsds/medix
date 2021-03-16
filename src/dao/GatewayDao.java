package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Gateway;

public class GatewayDao extends Dao {

	public void update(Gateway d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Gateway SET ds_Gateway = ?, ds_mac_circuito = ?,	"
							+ "	ie_situacao = ?, ds_localizacao = ? where nr_sequencia = ?");

			pstm.setString(1, d.getDescricao());
			pstm.setString(2,d.getMacCircuito());
			pstm.setString(3, d.getSituacao());
			pstm.setString(4, d.getLocalizacao());
			pstm.setInt(5, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean existe(Gateway d) {
		boolean achou = false;
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Gateway where nr_sequencia = ? ");
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
	
	public int insert(Gateway d) {
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Gateway(nr_sequencia, ds_Gateway, ds_mac_circuito,ds_localizacao,ie_situacao) values (?,?,?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setString(2, d.getDescricao());
			pstm.setString(3,d.getMacCircuito());
			pstm.setString(4, d.getLocalizacao());
			pstm.setString(5, d.getSituacao());
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
			PreparedStatement pstm = conexao.prepareStatement("delete from Gateway where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Gateway> getAll() {

		List<Gateway> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Gateway order by nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Gateway d = new Gateway();
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setDescricao(rs.getString("ds_Gateway"));
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Gateway getGateway(int id) {
		Gateway d = new Gateway();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Gateway where nr_sequencia = ?");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setDescricao(rs.getString("ds_Gateway"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public Gateway getGateway(String atributo, String valorParam) {
		Gateway d = new Gateway();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Gateway where "+atributo+" = ?");

			pstm.setString(1, valorParam);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setDescricao(rs.getString("ds_Gateway"));
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
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Gateway_seq')seq");

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