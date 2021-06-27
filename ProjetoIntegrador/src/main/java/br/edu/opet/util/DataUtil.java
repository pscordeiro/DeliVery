package br.edu.opet.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdfDataAndHour = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private static SimpleDateFormat sdfSoAsHour = new SimpleDateFormat("hh:mm:ss aa");
	private static SimpleDateFormat sdfSoDate = new SimpleDateFormat("dd/MM/yyyy");

	public static Date strToDate (String dt) {
		try {
			return sdf.parse(dt);
		}
		catch(ParseException e){
			return null;
		}
	}
	
	public static Date strToDate2 (String dt) {
		try {
			return sdf2.parse(dt);
		}
		catch(ParseException e){
			return null;
		}
	}
	
	public static String dateToStr (Date date) {
		return sdf.format(date);
	}
	
	public static Date strToDateHour (String dt) {
		try {
			return sdfDataAndHour.parse(dt);
		}
		catch(ParseException e){
			return null;
		}
	}
	
	public static String dateToStrHour (Date date) {
		return sdfDataAndHour.format(date);
	}	
	
	public static String SqlDateToDateAndHour (Timestamp time) {
		return sdfDataAndHour.format(time);
	}	
	
	public static String SqlDateToHour (Timestamp time) {
		return sdfSoAsHour.format(time);
	}	
	public static String SqlDateToDate (Timestamp time) {
		return sdfSoDate.format(time);
	}
	
	
}