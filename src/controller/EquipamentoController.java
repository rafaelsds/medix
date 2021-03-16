package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.EquipamentoDao;
import model.Equipamento;
import util.function;


 
@WebServlet( urlPatterns = {"/view/equipamento"})
public class EquipamentoController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private EquipamentoDao dao = new EquipamentoDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("equipamentoParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					Equipamento f = obterRegistro(req);
					
					if(id != null && !id.isEmpty()) { //Update
						f.setId(Integer.parseInt(id));
						dao.update(f);
						
					}else { //Insert
						dao.insert(f);
					}
					break;
					
				case "del":
					if(id != null && !id.isEmpty())
						dao.delete(Integer.valueOf(id));
					break;
					
				case "alt":
					if(id != null && !id.isEmpty()) {
						Equipamento registro = dao.getEquipamento(Integer.valueOf(id));
						session.setAttribute("equipamentoParam", registro);

					}
					break;
			}
		}
		
		req.getRequestDispatcher("equipamento.jsp").forward(req,resp);
		
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    public Equipamento obterRegistro(HttpServletRequest req) {
    	Equipamento f = new Equipamento();

		f.setDescricao(function.obterSeVazio(req.getParameter("descricao")));
		
		if(!function.obterSeVazio(req.getParameter("idlocalizacao")).isEmpty())
			f.setIdLocalizacao(Integer.parseInt(function.obterSeVazio(req.getParameter("idlocalizacao"))));
		
		
		f.setMacCircuito(function.obterSeVazio(req.getParameter("macCircuito")));
		f.setSituacao(function.obterSeVazio(req.getParameter("situacao")));
		f.setManutencao(function.obterSeVazio(req.getParameter("manutencao")));
		
		if(!function.obterSeVazio(req.getParameter("tempMaxima")).isEmpty()) {
			f.setTempMaxima(Float.parseFloat(function.obterSeVazio(req.getParameter("tempMaxima"))));
		}else {
			f.setTempMaxima(0f);
		}
		
		if(!function.obterSeVazio(req.getParameter("tempMinima")).isEmpty()) {
			f.setTempMinima(Float.parseFloat(function.obterSeVazio(req.getParameter("tempMinima"))));
		}else {
			f.setTempMinima(0f);
		}
		
		if(!function.obterSeVazio(req.getParameter("umidadeMaxima")).isEmpty()) {
			f.setUmidadeMaxima(Float.parseFloat(function.obterSeVazio(req.getParameter("umidadeMaxima"))));
	    }else {
			f.setUmidadeMaxima(0f);
		}
    
		if(!function.obterSeVazio(req.getParameter("umidadeMinima")).isEmpty()) {
			f.setUmidadeMinima(Float.parseFloat(function.obterSeVazio(req.getParameter("umidadeMinima"))));
	    }else {
			f.setUmidadeMinima(0f);
		}		
		return f;
    }
    
    public String obterSeValor(String valor) {
    	return (valor != null && !valor.isEmpty()) ? valor : "";
    }
    
}
