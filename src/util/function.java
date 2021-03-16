package util;

import java.awt.List;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class function {

	public static String obterSeVazio(String valor) {
		return (valor != null && !valor.isEmpty()) ? valor : "";
	}
	
	public static String obterSeVazio(String valor, String retorno) {
    	return (valor != null && !valor.isEmpty()) ? valor : retorno;
    }
	
	public static String toDate(String data, String mascara) {
		//mascara  "yyyy-MM-dd HH:mm:ss"
		final String FORMATO = mascara;
		
		DateFormat formatter = new SimpleDateFormat(FORMATO);
		
		try {
			Date d;
			d = formatter.parse(data);
			return formatter.format(d);
		} catch (ParseException | NullPointerException f) {
			return "";
		}
		
	
	}
	
	public static String toDate(String data, String mascaraOld,  String mascaraNew) {
		//mascara  "yyyy-MM-dd HH:mm:ss"
		
		DateFormat formatter = new SimpleDateFormat(mascaraOld);
		
		try {
			Date d = formatter.parse(data);
			formatter = new SimpleDateFormat(mascaraNew);
			
			return formatter.format(d);
		} catch (ParseException | NullPointerException f) {
			return "";
		}
		
	
	}
		
	public static String toDate(Timestamp dataAntiga, String mascara) {
	   SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	   try {
		   	  String data = dataAntiga.toString();
	          Date novaData = inputFormat.parse(data);
	          inputFormat = new SimpleDateFormat(mascara);
	          return inputFormat.format(novaData);
	    } catch (ParseException | NullPointerException f) {
	          return "";
	    }

	}
	
	public static String addHoraData(String data) {
		if(data != null && !data.isEmpty()) {
			return data+" 00:00:00";
		}
		return data;
	}
	
	public static Float minValue(ArrayList<Float> lista) {
		float min=999;
		
		for(Float lst : lista) {
			if(min > lst)
				min=lst;
		}
		
		return min;
	}
	
	public static Float maxValue(ArrayList<Float> lista) {
		float max=-999;
		
		for(Float lst : lista) {
			if(max < lst)
				max=lst;
		}
		
		return max;
	}
	
	
	public static String getDate(String formato) {
		//yyyy-MM-dd HH:mm:ss.S
		Date d = new Date();
		DateFormat dateFormat = new SimpleDateFormat(formato); 
		return dateFormat.format(d); 
	}
	
	public static String getDate() {
		//yyyy-MM-dd HH:mm:ss.S
		return getDate("dd-MM-yyyy"); 
	}
	
	
	public static String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		   try {

		     mDigest = MessageDigest.getInstance("MD5");

		     byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

		     StringBuffer sb = new StringBuffer();
		     for (byte b : valorMD5){
		            sb.append(Integer.toHexString((b & 0xFF) |
		            0x100).substring(1,3));
		     }

		     return sb.toString();

		   } catch (NoSuchAlgorithmException e) {
		     e.printStackTrace();
		     return null;
		   } catch (UnsupportedEncodingException e) {
		     e.printStackTrace();
		     return null;
		  }
	}
	
	public static String initCap(String s) {
		return s.substring(0,1).toUpperCase() + s.substring(1).toLowerCase();
	}
	
}
