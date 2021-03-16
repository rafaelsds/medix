package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AlertaDao;
import model.Alerta;
import model.GerenciadorAlertaConfig;
import model.Localizacao;
import util.function;

 
@WebServlet( urlPatterns = {"/view/gerenciador-alertas"})
public class AlertaController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private AlertaDao dao = new AlertaDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("alertaParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					Alerta f = obterRegistro(req);
					
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
						Alerta registro = dao.getAlerta(Integer.valueOf(id));
						session.setAttribute("alertaParam", registro);

					}
					break;
					
			}
		}
		
		req.getRequestDispatcher("gerenciador-alertas.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    public Alerta obterRegistro(HttpServletRequest req) {
    	Alerta f = new Alerta();

    	f.setIdEquipamento(Integer.parseInt(function.obterSeVazio(req.getParameter("idEquipamento"))));
    	f.setIdUsuario(Integer.parseInt(function.obterSeVazio(req.getParameter("idUsuario"))));
    	f.setIdLocalizacao(Integer.parseInt(function.obterSeVazio(req.getParameter("idLocalizacao"))));
		f.setSituacao(function.obterSeVazio(req.getParameter("situacao")));
		f.setMonitorarUmidade(function.obterSeVazio(req.getParameter("monitorarUmidade")));
		f.setMonitorarTemp(function.obterSeVazio(req.getParameter("monitorarTemp")));
		f.setAlertaTelegram(function.obterSeVazio(req.getParameter("alertaTelegram"),"N"));
		f.setAlertaEmail(function.obterSeVazio(req.getParameter("alertaEmail"),"N"));	
		return f;
    }
    
    
    

}
