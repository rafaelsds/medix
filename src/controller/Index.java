package controller;

import model.Telegram;
import model.Usuario;
import util.function;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import dao.UsuarioDao;
  
@WebServlet(name = "Index", urlPatterns = {"/index","/index.html","/auth"})
public class Index extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	
        if (request.getParameter("bLogin") != null) {
        	
            String login = request.getParameter("login");
            String senha = function.convertStringToMd5(request.getParameter("senha"));
                          		
        	UsuarioDao dao = new UsuarioDao();
        	Usuario u = dao.login(login.toLowerCase(), senha.toLowerCase().trim());
        	            
            if (u != null && u.getId() >0) {   
            	            	
                request.getSession().setAttribute("usuarioLogado", u.getLogin().toLowerCase().trim());
                request.getSession().setAttribute("papelUsuarioLogado", u.getPapelUsuario().trim());
                
                response.sendRedirect("/medix/view/dashboard.jsp");
                return;
            }else {
                session.setAttribute("loginIncorreto", "S");
            }
                
        }else if(request.getParameter("bSetBase")!=null) {
        	Dao.ipBanco = request.getParameter("ipBase");
        	Dao.nomeBancoDados = request.getParameter("baseDados");
        	Dao.usuario = request.getParameter("usuarioBase");
        	Dao.senha = request.getParameter("senhaBase");
        	
        	new GerenciadorTarefas().init();
        	
        }
       
        RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
        rd.forward(request, response);
        request.getSession().invalidate();
        
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
  
    @Override
    public String getServletInfo() {
        return "Short description";
    }
  
}