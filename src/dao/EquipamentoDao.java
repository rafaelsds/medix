package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Equipamento;

public class EquipamentoDao extends Dao {

	public void update(Equipamento d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Equipamento SET ds_equipamento = ?, ds_mac_circuito = ?,	temp_maxima = ?,"
							+ "	temp_minima = ?, umidade_maxima = ?, umidade_minima = ?, nr_seq_localizacao = ?, ie_situacao = ?, ie_manutencao = ? where nr_sequencia = ?");

			pstm.setString(1, d.getDescricao());
			pstm.setString(2,d.getMacCircuito());
			pstm.setFloat(3, d.getTempMaxima());
			pstm.setFloat(4, d.getTempMinima());
			pstm.setFloat(5, d.getUmidadeMaxima()); 
			pstm.setFloat(6, d.getUmidadeMinima());
			pstm.setInt(7, d.getIdLocalizacao());
			pstm.setString(8, d.getSituacao());
			pstm.setString(9, d.getManutencao());
			pstm.setInt(10, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean existe(Equipamento d) {
		boolean achou = false;
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Equipamento where nr_sequencia = ? ");
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
	
	public int insert(Equipamento d) {
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Equipamento(nr_sequencia, ds_equipamento, ds_mac_circuito,temp_maxima,temp_minima,umidade_minima, umidade_maxima,nr_seq_localizacao, ie_situacao, ie_manutencao) values (?,?,?,?,?,?,?,?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setString(2, d.getDescricao());
			pstm.setString(3,d.getMacCircuito());
			pstm.setFloat(4, d.getTempMaxima());
			pstm.setFloat(5, d.getTempMinima());
			pstm.setFloat(6, d.getUmidadeMinima());
			pstm.setFloat(7, d.getUmidadeMaxima());
			pstm.setInt(8, d.getIdLocalizacao());
			pstm.setString(9, d.getSituacao());
			pstm.setString(10, d.getManutencao());
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
			PreparedStatement pstm = conexao.prepareStatement("delete from Equipamento where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Equipamento> getAll() {

		List<Equipamento> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select a.*,b.ds_localizacao from Equipamento a join localizacao b on(a.nr_seq_localizacao = b.nr_sequencia) order by a.nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Equipamento d = new Equipamento();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setTempMinima(rs.getFloat("temp_minima"));
				d.setTempMaxima(rs.getFloat("temp_maxima"));
				d.setUmidadeMaxima(rs.getFloat("umidade_maxima"));
				d.setUmidadeMinima(rs.getFloat("umidade_minima"));
				d.setDescricao(rs.getString("ds_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setManutencao(rs.getString("ie_manutencao"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	public Equipamento getEquipamento(int id) {
		Equipamento d = new Equipamento();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Equipamento where nr_sequencia = ?");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setTempMinima(rs.getFloat("temp_minima"));
				d.setTempMaxima(rs.getFloat("temp_maxima"));
				d.setUmidadeMaxima(rs.getFloat("umidade_maxima"));
				d.setUmidadeMinima(rs.getFloat("umidade_minima"));
				d.setDescricao(rs.getString("ds_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setManutencao(rs.getString("ie_manutencao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}

	public Equipamento getEquipamento(String atributo, String valorParam) {
		Equipamento d = new Equipamento();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Equipamento where "+atributo+" = ?");

			pstm.setString(1, valorParam);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setTempMinima(rs.getFloat("temp_minima"));
				d.setTempMaxima(rs.getFloat("temp_maxima"));
				d.setUmidadeMaxima(rs.getFloat("umidade_maxima"));
				d.setUmidadeMinima(rs.getFloat("umidade_minima"));
				d.setDescricao(rs.getString("ds_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setManutencao(rs.getString("ie_manutencao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}
	
	public List<Equipamento> getAll(String paramWhere) {
		
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);

		List<Equipamento> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Equipamento a "+paramWhere+"  order by nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Equipamento d = new Equipamento();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setMacCircuito(rs.getString("ds_mac_circuito"));
				d.setTempMinima(rs.getFloat("temp_minima"));
				d.setTempMaxima(rs.getFloat("temp_maxima"));
				d.setUmidadeMaxima(rs.getFloat("umidade_maxima"));
				d.setUmidadeMinima(rs.getFloat("umidade_minima"));
				d.setDescricao(rs.getString("ds_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setManutencao(rs.getString("ie_manutencao"));
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public int obterSeq() {
		int seq = 0;

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Equipamento_seq')seq");

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