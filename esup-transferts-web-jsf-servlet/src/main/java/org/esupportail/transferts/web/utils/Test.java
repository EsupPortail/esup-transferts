package org.esupportail.transferts.web.utils;

import org.esupportail.transferts.utils.CheckNNE36;
import org.esupportail.transferts.utils.GestionDate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {

	public static void main(String[] args) {
		Test.testDate();
		System.out.println("-------------------------------------------");
		Date min = GestionDate.ajouterMois(new Date(), -1);
		Date max = GestionDate.ajouterMois(new Date(), 1);
		Date mtn = new Date();
		System.out.println("date min===>"+min+"<===");
		System.out.println("date max===>"+max+"<===");
		System.out.println("date maintenant===>"+mtn+"<===");
		boolean tmp = GestionDate.verificationDateCompriseEntre2Dates(min, max, mtn);
		System.out.println("verificationDateCompriseEntre2Dates===>"+tmp+"<===");
		System.out.println("-------------------------------------------");
		Test.calculCLeIne("0DDG5R0001");
		System.out.println("-------------------------------------------");
		Test.calculCLeIne("0DDG5Z0002");
		ajouterZeroDevantLong((long) 100);

	}

	public static void testDate(){
		Date oldDate = new Date(); // oldDate == current time
		Date newDate = new Date(oldDate.getTime() + TimeUnit.HOURS.toMillis(2)); // Adds 2 hours
		System.out.println("===>"+newDate.toString()+"<===");

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR); //A vérifier!!!!
		System.out.println("===>"+year+"<===");
	}

	public static void calculCLeIne(String nne)
	{
		System.out.println("INE sans clé===>"+nne+"<===");
		System.out.println("Clé INE===>"+ CheckNNE36.calculCLeIne(nne)+"<===");
	}

	public static void ajouterZeroDevantLong(Long l){
		NumberFormat nf = new DecimalFormat("0000");
		System.out.println( nf.format(l) );
	}
}
