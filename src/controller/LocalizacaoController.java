package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.LocalizacaoDao;
import model.Localizacao;
import util.function;

 
@WebServlet( urlPatterns = {"/view/localizacao"})
public class LocalizacaoController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private LocalizacaoDao dao = new LocalizacaoDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("localizacaoParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					Localizacao f = obterRegistro(req);
					
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
						Localizacao registro = dao.getLocalizacao(Integer.valueOf(id));
						session.setAttribute("localizacaoParam", registro);

					}
					break;
			}
		}
		
		req.getRequestDispatcher("localizacao.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    public Localizacao obterRegistro(HttpServletRequest req) {
    	Localizacao f = new Localizacao();

		f.setLocalizacao(function.obterSeVazio(req.getParameter("localizacao")));
		f.setSituacao(function.obterSeVazio(req.getParameter("situacao")));
		
		return f;
    }
    
    public String obterSeValor(String valor) {
    	return (valor != null && !valor.isEmpty()) ? valor : "";
    }
    

}
