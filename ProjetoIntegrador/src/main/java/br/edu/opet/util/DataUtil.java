package br.edu.opet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static Date strToDate (String dt) {
		try {
			return sdf.parse(dt);
		}
		catch(ParseException e){
			return null;
		}
	}
	
	public static String dateToStr (Date date) {
		return sdf.format(date);
	}
	
}