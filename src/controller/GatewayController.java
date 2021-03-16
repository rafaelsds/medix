package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GatewayDao;
import model.Gateway;
import util.function;

 
@WebServlet( urlPatterns = {"/view/gateway"})
public class GatewayController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private GatewayDao dao = new GatewayDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("gatewayParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					Gateway f = obterRegistro(req);
					
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
						Gateway registro = dao.getGateway(Integer.valueOf(id));
						session.setAttribute("gatewayParam", registro);

					}
					break;
			}
		}
		
		req.getRequestDispatcher("gateway.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    public Gateway obterRegistro(HttpServletRequest req) {
    	Gateway f = new Gateway();

		f.setDescricao(function.obterSeVazio(req.getParameter("descricao")));
		f.setLocalizacao(function.obterSeVazio(req.getParameter("localizacao")));
		f.setMacCircuito(function.obterSeVazio(req.getParameter("macCircuito")));
		f.setSituacao(function.obterSeVazio(req.getParameter("situacao")));
		
		return f;
    }
    
    public String obterSeValor(String valor) {
    	return (valor != null && !valor.isEmpty()) ? valor : "";
    }
    

}
