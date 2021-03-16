package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.Dao;
import dao.UsuarioDao;
import model.Usuario;
import util.function;

 
@WebServlet( urlPatterns = {"/view/usuario"})
public class UsuarioController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private UsuarioDao dao = new UsuarioDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("usuarioParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
		
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					Usuario f = obterRegistro(req);
					
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
						Usuario registro = dao.getUsuario(Integer.valueOf(id));
						session.setAttribute("usuarioParam", registro);

					}
					break;
			}
		}
		
		req.getRequestDispatcher("usuario.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    public Usuario obterRegistro(HttpServletRequest req) {
    	Usuario f = new Usuario();

    	String dtNasc = function.toDate(function.addHoraData(function.obterSeVazio(req.getParameter("dataNascimento"))),"yyyy-MM-dd HH:mm:ss");
    	
		f.setDataNascimento(dtNasc == null ? null : (dtNasc.isEmpty() ? null : Timestamp.valueOf(dtNasc)));
		f.setEmail(function.obterSeVazio(req.getParameter("email")));
		f.setLogin(function.obterSeVazio(req.getParameter("login")));
		f.setNomeUsuario(function.obterSeVazio(req.getParameter("nomeUsuario")));
		f.setSenha(function.obterSeVazio(req.getParameter("senha")));
		f.setPapelUsuario(function.obterSeVazio(req.getParameter("papelUsuario")));
		f.setTelefone(function.obterSeVazio(req.getParameter("telefone")));
		f.setSituacao(function.obterSeVazio(req.getParameter("situacao")));

    	return f;
    }
    
    public String obterSeValor(String valor) {
    	return (valor != null && !valor.isEmpty()) ? valor : "";
    }
    

}
