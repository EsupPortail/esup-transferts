/**
 * 
 */

package org.esupportail.transferts.utils;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
/**
 * Verification du numero BEA
 *
 * @author AIT KARRA Farid
 */
public class CheckBEA23 {
	
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(CheckBEA23.class);

	// Fonction verifiant le BEA en provenance base 23
	// entree : le bea
	// sortie : 0 si OK
	//	        1 erreur dans le format
	//	        2 cle invalide, cle secrete utilis�e
	//	        3 cle invalide, <> cle calcule
	public static Integer verifie(String numero) 
	{
		// decomposition du BEA
		numero = numero.toUpperCase();
		String lettreCle = numero.substring(10, numero.length());
		String extract_bea = numero.substring(0, 10);
//		Integer modulo_bea = Integer.parseInt(extract_bea) % 23;
		Long modulo_bea = Long.parseLong(extract_bea) % 23;
		Integer k = Integer.parseInt(modulo_bea.toString());
		// generation de notre alphabet de 23 caracteres sans la lettre I,O Q
		// avec l'index demarrant a 1		
		String alphabet_23[] = {"A", "B", "C", "D", "E", "F", "G","H", "J", "K", "L", "M", "N", "P", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};		
		//Calcul de la cle
		String cleCalc = alphabet_23[k];

		if (logger.isDebugEnabled())
		{
			logger.debug("############### numero ############ --> "+ numero);
			logger.debug("############### numero.length() ############ --> "+ numero.length());
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
			logger.debug("############### extract_bea ############ --> "+ extract_bea);
//			logger.debug("############### Integer.parseInt(extract_bea) ############ --> "+ Integer.parseInt(extract_bea));
			logger.debug("############### Integer.parseInt(extract_bea) ############ --> "+ Long.parseLong(extract_bea));
			logger.debug("############### modulo_bea ############ --> "+ modulo_bea);
			logger.debug("############### cleCalc ############ --> "+ cleCalc);
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
		}
		
		if (!lettreCle.equals(cleCalc))
		{
			// bea faux cle invalide
			if (logger.isDebugEnabled())			
				logger.debug("############### bea faux cle invalidee return 3 ############");			
			return 3;
		}			
		return 0;
	}
}
