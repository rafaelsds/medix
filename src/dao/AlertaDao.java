package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Alerta;
import model.Gateway;

public class AlertaDao extends Dao {

	public void update(Alerta d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Alerta SET nr_seq_localizacao = ?, nr_seq_equipamento = ?, nr_seq_usuario = ?, ie_monitor_temp = ?, ie_monitor_umidade = ?, 	"
							+ "	ie_situacao = ?, ie_alerta_email = ?, ie_alerta_telegram = ? where nr_sequencia = ?");

			pstm.setInt(1, d.getIdLocalizacao());
			pstm.setInt(2, d.getIdEquipamento());
			pstm.setInt(3, d.getIdUsuario());
			pstm.setString(4, d.getMonitorarTemp());			
			pstm.setString(5, d.getMonitorarUmidade());
			pstm.setString(6, d.getSituacao());
			pstm.setString(7, d.getAlertaEmail());
			pstm.setString(8, d.getAlertaTelegram());
			pstm.setInt(9, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insert(Alerta d) {
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Alerta(nr_sequencia, nr_seq_localizacao, nr_seq_equipamento, nr_seq_usuario, ie_monitor_temp, ie_monitor_umidade, ie_situacao, ie_alerta_email, ie_alerta_telegram) values (?,?,?,?,?,?,?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setInt(2, d.getIdLocalizacao());
			pstm.setInt(3, d.getIdEquipamento());
			pstm.setInt(4, d.getIdUsuario());
			pstm.setString(5, d.getMonitorarTemp());			
			pstm.setString(6, d.getMonitorarUmidade());
			pstm.setString(7, d.getSituacao());
			pstm.setString(8, d.getAlertaEmail());
			pstm.setString(9, d.getAlertaTelegram());
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
			PreparedStatement pstm = conexao.prepareStatement("delete from Alerta where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public List<Alerta> getAll() {
		return getAll("");
	}
	
	public List<Alerta> getAll(String paramWhere) {
		
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		
		List<Alerta> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(" Select a.*, "
															+ " obter_dados_tabela('USUARIO','NM_USUARIO','NR_SEQUENCIA = '||a.nr_seq_usuario)nm_usuario, "
															+ " obter_dados_tabela('EQUIPAMENTO','DS_EQUIPAMENTO','NR_SEQUENCIA = '||a.nr_seq_equipamento)ds_equipamento, "
															+ " obter_dados_tabela('LOCALIZACAO','DS_LOCALIZACAO','NR_SEQUENCIA = '||a.nr_seq_localizacao)ds_localizacao "
															+ " from Alerta a "+paramWhere+" order by a.nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Alerta d = new Alerta();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdUsuario(rs.getInt("nr_seq_usuario"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setMonitorarTemp(rs.getString("ie_monitor_temp"));
				d.setMonitorarUmidade(rs.getString("ie_monitor_umidade"));
				d.setAlertaEmail(rs.getString("ie_alerta_email"));
				d.setAlertaTelegram(rs.getString("ie_alerta_telegram"));
				
				if(rs.getInt("nr_seq_usuario")==0) {
					d.setUsuario("Todos");
				}else {
					d.setUsuario(rs.getString("nm_usuario"));
				}
				
				if(rs.getInt("nr_seq_localizacao")==0) {
					d.setLocalizacao("Todas");
				}else {
					d.setLocalizacao(rs.getString("ds_localizacao"));
				}
				
				if(rs.getInt("nr_seq_equipamento")==0) {
					d.setEquipamento("Todos");
				}else {
					d.setEquipamento(rs.getString("ds_equipamento"));
				}
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}


	public Alerta getAlerta(int id) {
		Alerta d = new Alerta();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Alerta where nr_sequencia = ?");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdUsuario(rs.getInt("nr_seq_usuario"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setSituacao(rs.getString("ie_situacao"));
				d.setMonitorarTemp(rs.getString("ie_monitor_temp"));
				d.setMonitorarUmidade(rs.getString("ie_monitor_umidade"));
				d.setAlertaEmail(rs.getString("ie_alerta_email"));
				d.setAlertaTelegram(rs.getString("ie_alerta_telegram"));
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
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Alerta_seq')seq");

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