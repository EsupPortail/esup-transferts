package org.esupportail.transferts.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 * 
 */

public class GestionDate {

	public GestionDate() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * Formater une date
	 */
	public static String formatterDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public static String formatterDateHeure(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(date);
	}	

	/*
	 * Ajouter/retrancher des mois à une date
	 * Pour retrancher des jours, il faut fournir un paramètre négatif au nombre de jours.
	 */
	public static Date ajouterMois(Date date, int nbMois) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, nbMois);
		return cal.getTime();
	}

	/*
	 * Ajouter/retrancher des jours à une date
	 */
	public static Date ajouterJour(Date date, int nbJour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, nbJour);
		return cal.getTime();
	}	

	/*
	 * Ajouter/retrancher des heures à une date
	 * Pour retrancher des années, il faut fournir un paramètre négatif au nombre d'années.
	 */
	public static Date ajouterHeure(Date date, int nbHeure) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, nbHeure);
		return cal.getTime();
	}
	
	public static boolean verificationDateCompriseEntre2Dates(Date min, Date max, Date date){
		return !(date.before(min) || date.after(max));
	}
	
}
