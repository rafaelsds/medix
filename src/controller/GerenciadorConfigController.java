package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ParametrosGerenciadorDao;
import model.GerenciadorAlertaConfig;
import util.function;

 
@WebServlet( urlPatterns = {"/view/gerenciador-config"})
public class GerenciadorConfigController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private ParametrosGerenciadorDao dao = new ParametrosGerenciadorDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					GerenciadorAlertaConfig f = obterRegistro(req);
					
					if(id != null && !id.isEmpty()) { //Update
						f.setId(Integer.parseInt(id));
						dao.update(f);
					}
					break;
					
			}
		}
		
		req.getRequestDispatcher("gerenciador-config.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    public GerenciadorAlertaConfig obterRegistro(HttpServletRequest req) {
    	GerenciadorAlertaConfig f = new GerenciadorAlertaConfig();

    	
		f.setEnderecoEmail(function.obterSeVazio(req.getParameter("enderecoEmail")));
		f.setNomeExibicaoEmail(function.obterSeVazio(req.getParameter("nomeExibicaoEmail")));
		f.setPortaEmail(Integer.parseInt(req.getParameter("portaEmail")));
		f.setSenhaEmail(function.obterSeVazio(req.getParameter("senhaEmail")));
		f.setServidorSmtp(function.obterSeVazio(req.getParameter("servidorSmtp")));
		f.setSituacaoEmail(function.obterSeVazio(req.getParameter("situacaoEmail")));
		f.setSituacaoGerenciador(function.obterSeVazio(req.getParameter("situacaoGerenciador")));
		f.setSituacaoBot(function.obterSeVazio(req.getParameter("situacaoBot")));
		f.setDescricaoBot(function.obterSeVazio(req.getParameter("descricaoBot")));
		f.setTokenBot(function.obterSeVazio(req.getParameter("tokenBot")));
		f.setFrequenciaAtualizacao(Integer.parseInt(req.getParameter("freqAtualizacao")));
		
		return f;
    }
    
    public String obterSeValor(String valor) {
    	return (valor != null && !valor.isEmpty()) ? valor : "";
    }
    

}
