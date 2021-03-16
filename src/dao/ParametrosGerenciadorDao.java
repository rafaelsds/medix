package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.GerenciadorAlertaConfig;
import model.Localizacao;
import util.function;

public class ParametrosGerenciadorDao extends Dao {

	public void update(GerenciadorAlertaConfig d) {
		try {
			Connection conexao = getConexao();

			PreparedStatement pstm = conexao
					.prepareStatement("Update parametros_gerenciador SET ds_servidor_smtp = ?, nr_porta_serv_email = ?,	"
							+ "	ie_situacao = ?, ds_senha_email = ?, ds_nome_exibicao_email = ?, ds_email = ?, ds_bot = ?, ds_token = ?, ie_situacao_bot = ?, nr_min_atualizacao = ?, ie_situacao_gerenciador = ?  where nr_sequencia = ?");

			pstm.setString(1, d.getServidorSmtp());
			pstm.setInt(2, d.getPortaEmail());
			pstm.setString(3, d.getSituacaoEmail());
			pstm.setString(4, d.getSenhaEmail());
			pstm.setString(5, d.getNomeExibicaoEmail());
			pstm.setString(6, d.getEnderecoEmail());
			pstm.setString(7, d.getDescricaoBot());
			pstm.setString(8, d.getTokenBot());
			pstm.setString(9, d.getSituacaoBot());
			pstm.setInt(10, d.getFrequenciaAtualizacao());
			pstm.setString(11, d.getSituacaoGerenciador());
			pstm.setInt(12, d.getId());
			pstm.execute();
			pstm.close();
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	
	public GerenciadorAlertaConfig getAll(String paramWhere) {
		
		paramWhere="where "+(paramWhere == null || paramWhere.isEmpty() ? "1=1 " : paramWhere);
		GerenciadorAlertaConfig d = new GerenciadorAlertaConfig();

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select * from parametros_gerenciador a "+paramWhere);

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				d.setId(rs.getInt("nr_sequencia"));
				d.setEnderecoEmail(function.obterSeVazio(rs.getString("ds_email")));
				d.setNomeExibicaoEmail(function.obterSeVazio(rs.getString("ds_nome_exibicao_email")));
				d.setSituacaoEmail(function.obterSeVazio(rs.getString("ie_situacao")));
				d.setSituacaoGerenciador(function.obterSeVazio(rs.getString("ie_situacao_gerenciador")));
				d.setServidorSmtp(function.obterSeVazio(rs.getString("ds_servidor_smtp")));
				d.setSenhaEmail(function.obterSeVazio(rs.getString("ds_senha_email")));
				d.setPortaEmail(rs.getInt("nr_porta_serv_email"));
				d.setDescricaoBot(rs.getString("ds_bot"));
				d.setTokenBot(rs.getString("ds_token"));
				d.setSituacaoBot(function.obterSeVazio(rs.getString("ie_situacao_bot")));
				d.setFrequenciaAtualizacao(rs.getInt("nr_min_atualizacao"));
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public String getBotToken(){
		String token="";

		try {
			Connection conexao = getConexao();
			PreparedStatement pstm = conexao.prepareStatement("Select ds_token from parametros_gerenciador a where ie_situacao_bot = 'A'");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				token = rs.getString("ds_token");
			}

			pstm.close();
			conexao.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return token;
	}


}