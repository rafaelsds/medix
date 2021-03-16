package model;

import org.apache.commons.mail.*;

import dao.ParametrosGerenciadorDao;


public class Email {

	public static void enviar(String destinatario, String assunto, String textoHtml) {
	
		HtmlEmail email = new HtmlEmail();
		GerenciadorAlertaConfig gerenciador = new ParametrosGerenciadorDao().getAll("");
		
		email.setSSLOnConnect(true);
		email.setHostName(gerenciador.getServidorSmtp());
		email.setSslSmtpPort(String.valueOf(gerenciador.getPortaEmail()));
		email.setAuthenticator( new DefaultAuthenticator(gerenciador.getEnderecoEmail() , gerenciador.getSenhaEmail() ) );
		try {
		    email.setFrom(gerenciador.getEnderecoEmail(), gerenciador.getNomeExibicaoEmail());
		    email.setDebug(true); 
		    email.setSubject(assunto);
		     
		    StringBuilder builder = new StringBuilder();
		    builder.append(textoHtml);
		    email.setHtmlMsg( builder.toString() );
		    email.addTo(destinatario);
		    email.send();
		    
			/*
			 * builder.append("<h1>Titulo</h1>"); builder.
			 * append("<p>Lorem ipsum dolor sit amet, <b>consectetur adipiscing elit</b>. Duis nec aliquam tortor. Sed dignissim dolor ac est consequat egestas. Praesent adipiscing dolor in consectetur fringilla.</p>"
			 * ); builder.
			 * append("<a href=\"http://wwww.botecodigital.info\">Boteco Digital</a> <br> "
			 * ); builder.
			 * append("<img src=\"http://www.botecodigital.info/wp-content/themes/boteco/img/logo.png\">"
			 * );
			 */
		} catch (EmailException e) {
		    e.printStackTrace();
		} 
	}
	
}
