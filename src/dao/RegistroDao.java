package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Email;
import model.Equipamento;
//import model.Fornecedor;
import model.Registro;

public class RegistroDao extends Dao {

	public void update(Registro d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update Registro SET nr_seq_equipamento = ?, temperatura = ?,	umidade = ?,"
							+ "	dt_modificacao = ?, ds_observacao = ?, nm_usuario_modif = ?, ie_desvio_temp = ?, ie_desvio_umidade = ? where nr_sequencia = ?");
		
			pstm.setInt(1, d.getIdEquipamento());
			pstm.setFloat(2, d.getTemperatura());
			pstm.setFloat(3, d.getUmidade());
			pstm.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			pstm.setString(5, d.getObservacao());
			pstm.setString(6, d.getUsuarioModificacao());
			pstm.setString(7, d.getDesvioTemp());
			pstm.setString(8, d.getDesvioUmidade());
			pstm.setInt(9, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean existe(Registro d) {
		boolean achou = false;
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from Registro where nr_sequencia = ? ");
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

	public int insert(Registro d) {
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(
					"Insert into Registro(nr_sequencia, nr_seq_equipamento, temperatura,umidade,dt_registro, dt_modificacao,ds_observacao,nm_usuario,nm_usuario_modif, ie_desvio_temp, ie_desvio_umidade) values (?,?,?,?,?,?,?,?,?,?,?)");
			pstm.setInt(1, obterSeq());
			pstm.setInt(2, d.getIdEquipamento());
			pstm.setFloat(3, d.getTemperatura());
			pstm.setFloat(4, d.getUmidade());
			pstm.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pstm.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			pstm.setString(7, d.getObservacao());
			pstm.setString(8, d.getUsuarioRegistro());
			pstm.setString(9, d.getUsuarioModificacao());
			
			pstm.setString(10, d.getDesvioTemp());
			pstm.setString(11, d.getDesvioUmidade());
			
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
			PreparedStatement pstm = conexao.prepareStatement("delete from Registro where nr_sequencia = ? ");
			pstm.setInt(1, id);
			pstm.execute();
			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Registro> getAll() {

		List<Registro> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(	"SELECT a.*, b.ds_equipamento " + 
																"from Registro a " + 
																"JOIN equipamento b ON(b.nr_sequencia = a.nr_seq_equipamento) " + 
																"order BY a.nr_sequencia");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Registro d = new Registro();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setUsuarioModificacao(rs.getString("nm_usuario_modif"));
				d.setUsuarioRegistro(rs.getString("nm_usuario"));
				d.setObservacao(rs.getString("ds_observacao"));
				d.setUmidade(rs.getFloat("umidade"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setDtRegistro(rs.getTimestamp("dt_registro"));
				d.setDtModificacao(rs.getTimestamp("dt_modificacao"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
				
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	
	public List<Registro> getAll(String paramWhere, String orderBy) {

		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		orderBy="order by "+(orderBy == null || orderBy.isEmpty() ? " 1 asc " : orderBy);
		
		List<Registro> lista = new ArrayList<>();
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(	"SELECT a.*, b.ds_equipamento, c.ds_localizacao " + 
																"from Registro a " + 
																"JOIN equipamento b ON(b.nr_sequencia = a.nr_seq_equipamento) " + 
																"JOIN localizacao c on(b.nr_seq_localizacao = c.nr_sequencia) "+
																paramWhere +
																orderBy);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Registro d = new Registro();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setUsuarioModificacao(rs.getString("nm_usuario_modif"));
				d.setUsuarioRegistro(rs.getString("nm_usuario"));
				d.setObservacao(rs.getString("ds_observacao"));
				d.setUmidade(rs.getFloat("umidade"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setDtRegistro(rs.getTimestamp("dt_registro"));
				d.setDtModificacao(rs.getTimestamp("dt_modificacao"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
				
				lista.add(d);
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	public List<Registro> getRegistroEquipHora(int idEquipP) {
		return getRegistroEquipHora(idEquipP, 2);
	}
	
	public List<Registro> getRegistroEquipHora(int idEquipP, int hrFinalP) {
		
		int hrFinal = (hrFinalP == 0 ? 2 : hrFinalP);
		List<Registro> lista = new ArrayList<>();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(	
					"SELECT * FROM("+
					"SELECT TO_CHAR(a.dt_registro,'hh24:mi')hr_registro," + 
					"		 a.dt_registro," + 
					"		 a.temperatura," + 
					"		 a.umidade," + 
					"		 a.ie_desvio_temp," + 
					"		 a.ie_desvio_umidade," + 
					"		 b.ds_equipamento," + 
					"		 b.umidade_minima," + 
					"		 b.umidade_maxima," + 
					"		 b.temp_minima," + 
					"		 b.temp_maxima" + 
					" FROM registro a" + 
					" JOIN equipamento b ON(a.nr_seq_equipamento = b.nr_sequencia)" + 
					" WHERE b.nr_sequencia = "+idEquipP+ 
					" and to_timestamp(TO_CHAR(a.dt_registro,'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss') >= CURRENT_TIMESTAMP(0) - INTERVAL '"+hrFinal+" hour'"+
					" order by a.dt_registro DESC" + 
					" LIMIT 13)X"+
					" order by dt_registro");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Registro d = new Registro();

				d.setUmidade(rs.getFloat("umidade"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setDtRegistro(rs.getTimestamp("dt_registro"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
				d.setTempMaxima(rs.getFloat("temp_maxima"));
				d.setTempMinima(rs.getFloat("temp_minima"));
				d.setUmidadeMaxima(rs.getFloat("umidade_maxima"));
				d.setUmidadeMinima(rs.getFloat("umidade_minima"));
				 
				lista.add(d);
				
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	
	public Registro getRegistro(int id) {
		Registro d = new Registro();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement(	"SELECT a.*, b.ds_equipamento " + 
																"from Registro a " + 
																"JOIN equipamento b ON(b.nr_sequencia = a.nr_seq_equipamento) " +
																"WHERE a.nr_sequencia = ?"+
																"order BY a.nr_sequencia");

			pstm.setInt(1, id);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setUsuarioModificacao(rs.getString("nm_usuario_modif"));
				d.setUsuarioRegistro(rs.getString("nm_usuario"));
				d.setObservacao(rs.getString("ds_observacao"));
				d.setUmidade(rs.getFloat("umidade"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setDtRegistro(rs.getTimestamp("dt_registro"));
				d.setDtModificacao(rs.getTimestamp("dt_modificacao"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}
	
	
	public Registro getUltimoRegistroEquip(int idEquipamento) {
		Registro d = new Registro();
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("select a.nr_seq_equipamento," + 
					" 	b.ds_equipamento," + 
					" 	a.ie_desvio_umidade," + 
					" 	a.ie_desvio_temp," + 
					" 	a.umidade," + 
					" 	a.temperatura," + 
					" 	a.nr_sequencia" + 
					" from registro a" + 
					" join equipamento b on(a.nr_seq_equipamento = b.nr_sequencia) " + 
					" where a.nr_sequencia = " + 
					"(" + 
					"	select max(w.nr_sequencia) " + 
					"	from registro w" + 
					"	where w.nr_seq_equipamento = ?" + 
					"	and to_timestamp(TO_CHAR(w.dt_registro,'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss') >= CURRENT_TIMESTAMP(0) - INTERVAL '10 min'" + 
					")");

			pstm.setInt(1, idEquipamento);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setUmidade(rs.getFloat("umidade"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return d;
	}
	

	public List<Registro> getRegistroDesvioMinuto(String paramWhere, int minutosP) {
		List<Registro> lista = new ArrayList<>();
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("select a.nr_seq_equipamento," + 
					" 	b.ds_equipamento," + 
					" 	a.ie_desvio_umidade," + 
					" 	a.ie_desvio_temp," + 
					" 	a.umidade," + 
					" 	a.temperatura," + 
					" 	a.nr_sequencia," + 
					"   obter_dados_tabela('LOCALIZACAO','DS_LOCALIZACAO','NR_SEQUENCIA = '||b.nr_seq_localizacao)ds_localizacao "+  
					" from registro a" + 
					" join equipamento b on(a.nr_seq_equipamento = b.nr_sequencia) " + 
					" where a.nr_sequencia = " + 
					"(" + 
					"	select max(registro.nr_sequencia) " + 
					"	from registro " +
					"   join equipamento on(registro.nr_seq_equipamento = equipamento.nr_sequencia) "+
					  paramWhere+ 
					" and to_timestamp(TO_CHAR(registro.dt_registro,'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss') >= CURRENT_TIMESTAMP(0) - INTERVAL '"+minutosP+" min'" + 
					")"+
					" and (a.ie_desvio_temp = 'S' or a.ie_desvio_umidade = 'S')"+
					" and b.ie_manutencao = 'N' and b.ie_situacao = 'A'");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Registro d = new Registro();
				d.setId(rs.getInt("nr_sequencia"));
				d.setIdEquipamento(rs.getInt("nr_seq_equipamento"));
				d.setDesvioTemp(rs.getString("ie_desvio_temp"));
				d.setDesvioUmidade(rs.getString("ie_desvio_umidade"));
				d.setNomeEquipamento(rs.getString("ds_equipamento"));
				d.setTemperatura(rs.getFloat("temperatura"));
				d.setUmidade(rs.getFloat("umidade"));
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
	
	
	public List<Equipamento> getEquipParadoMinuto(String paramWhere, int minutosP) {
		List<Equipamento> lista = new ArrayList<>();
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		
		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("select a.nr_sequencia nr_seq_equipamento, "+
					" a.ds_equipamento, a.nr_seq_localizacao, b.ds_localizacao"+
					" from equipamento a "+
					" join localizacao b on(a.nr_seq_localizacao = b.nr_sequencia)"+
					" where a.ie_situacao = 'A' "+
					" and a.ie_manutencao = 'N' "+
					" and exists"+
					" ("+
					"   select 1"+
				    " 	from equipamento "+
						paramWhere+
					"   and equipamento.nr_sequencia = a.nr_sequencia"+
					" )"+
					" and not exists "+
					"( "+
					"	select 1 "+
					"	from registro "+
					"   join equipamento on(registro.nr_seq_equipamento = equipamento.nr_sequencia) "+
						paramWhere+ 	
					"	and registro.nr_seq_equipamento = a.nr_sequencia "+
					"	and to_timestamp(TO_CHAR(registro.dt_registro,'dd/mm/yyyy hh24:mi:ss'),'dd/mm/yyyy hh24:mi:ss') >= CURRENT_TIMESTAMP(0) - INTERVAL '"+minutosP+" min' "+
					")");
			
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Equipamento d = new Equipamento();
				d.setId(rs.getInt("nr_seq_equipamento"));
				d.setDescricao(rs.getString("ds_equipamento"));
				d.setLocalizacao(rs.getString("ds_localizacao"));
				d.setIdLocalizacao(rs.getInt("nr_seq_localizacao"));
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
			PreparedStatement pstm = conexao.prepareStatement("SELECT nextval('Registro_seq')seq");

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