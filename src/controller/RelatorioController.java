package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Dao;
import dao.EquipamentoDao;
import model.Equipamento;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

import util.function;

		

@WebServlet( urlPatterns = {"/view/gerarRelatorio"})
public class RelatorioController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private EquipamentoDao equipDao = new EquipamentoDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	
    	Equipamento equip = equipDao.getEquipamento(Integer.valueOf(req.getParameter("idEquipamento")));
    	
    	Map<String, Object> parametros = new HashMap<>();
    	parametros.put("mes_referencia", function.toDate(req.getParameter("mesReferencia"), "yyyy-MM-dd", "MM/yyyy"));
        parametros.put("nr_seq_equipamento", equip.getId());
        parametros.put("ds_equipamento", equip.getDescricao());
    	
        parametros.put("temp_ideal", equip.getTempMinima()+" a "+equip.getTempMaxima()+"Cº");
        parametros.put("umidade_ideal", equip.getUmidadeMinima()+" a "+equip.getUmidadeMaxima()+"%");
        
        gerarRelatorio(req, resp, req.getParameter("relatorio"), parametros);
        
    }


    public void gerarRelatorio(HttpServletRequest req, HttpServletResponse resp, String relatorio, Map<String, Object> parametros) throws IOException{
    	
    	ServletContext context = req.getServletContext(); 
        String jasperCompiled = context.getRealPath("/WEB-INF/reports/"+relatorio+".jasper");
        Dao dao = new Dao();

    	try {

			byte[] bytes = JasperRunManager.runReportToPdf(jasperCompiled, parametros, dao.getConexao());
			
			resp.setContentType("application/pdf");
			resp.setContentLength(bytes.length);
			resp.setHeader("Content-disposition", "inline;filename="+relatorio+function.getDate("ddMMyyyyHHmmss")+".pdf");
			
			ServletOutputStream ouputStream = resp.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();

		} catch (JRException e) {
			e.printStackTrace();
		}finally {
			dao.closeConection();
		}
    }
    

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	

}
