/**
 * 
 */

package org.esupportail.transferts.utils;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * Verification du numero INE
 *
 * @author AIT KARRA Farid
 */
public class CheckNNE36 {

	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(CheckBEA23.class);	
	
	// Fonction verifiant le NNE en provenance des etablissements.
	// entree : le nne
	// sortie : 0 si OK
	//	        1 erreur dans le format
	//	        2 cle invalide, cle secrete utilis�e
	//	        3 cle invalide, <> cle calcule
	public static Integer verifie(String numero) 
	{
		// decomposition de l'INE
		numero = numero.toUpperCase();
		String lettreCle = numero.substring(10, numero.length());
		String extract_nne = numero.substring(0, 10);
		char tabCar [] = extract_nne.toCharArray();
		Integer test=0;
		for(int i=0 ; i<9; i++)
		{
			test = test+BaseConverterUtil.fromBase36(Character.toString(tabCar[i]))*6;
			if (logger.isDebugEnabled())
			{
				logger.debug("############### Character.toString(tabCar[i]) ############ --> "+ Character.toString(tabCar[i]));
				logger.debug("############### BaseConverterUtil.fromBase36(Character.toString(tabCar[i])) ############ --> "+ BaseConverterUtil.fromBase36(Character.toString(tabCar[i])));	
				logger.debug("############### test ############ --> "+ test);
			}
		}
		String lastChar = extract_nne.substring(extract_nne.length()-1, extract_nne.length());
		if (logger.isDebugEnabled())
			logger.debug("############### lastChar ############ --> "+ lastChar);

		lastChar = BaseConverterUtil.toBase10(BaseConverterUtil.fromBase36(lastChar));
		if (logger.isDebugEnabled())
			logger.debug("############### lastChar ############ --> "+ lastChar);
		
		test = test+Integer.parseInt(lastChar);
		String test2 = String.valueOf(test);

		String cleCalc = test2.substring(test2.length()-1, test2.length());


		if (logger.isDebugEnabled())
		{
			logger.debug("############### cleCalc ############ --> "+ cleCalc);
			logger.debug("############### lettreCle ############ --> "+ lettreCle);			
			logger.debug("############### numero ############ --> "+ numero);
			logger.debug("############### numero.length() ############ --> "+ numero.length());
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
			logger.debug("############### extract_bea ############ --> "+ extract_nne);
			logger.debug("############### cleCalc ############ --> "+ cleCalc);
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
		}
		
		if (!lettreCle.equals(cleCalc))
		{
			// ine faux cle invalide
			if (logger.isDebugEnabled())			
				logger.debug("############### ine faux cle invalidee return 3 ############");	
			return 3;
		}			
		return 0;
	}	
	
	public static String calculCLeIne(String numero) 
	{
		// decomposition du BEA
		numero = numero.toUpperCase();
		String lettreCle = numero.substring(10, numero.length());
		String extract_nne = numero.substring(0, 10);
		char tabCar [] = extract_nne.toCharArray();
		Integer test=0;
		for(int i=0 ; i<9; i++)
		{
			test = test+BaseConverterUtil.fromBase36(Character.toString(tabCar[i]))*6;
			if (logger.isDebugEnabled())
			{
				logger.debug("############### Character.toString(tabCar[i]) ############ --> "+ Character.toString(tabCar[i]));
				logger.debug("############### BaseConverterUtil.fromBase36(Character.toString(tabCar[i])) ############ --> "+ BaseConverterUtil.fromBase36(Character.toString(tabCar[i])));	
				logger.debug("############### test ############ --> "+ test);
			}
		}
		String lastChar = extract_nne.substring(extract_nne.length()-1, extract_nne.length());
		if (logger.isDebugEnabled())
			logger.debug("############### lastChar ############ --> "+ lastChar);

		lastChar = BaseConverterUtil.toBase10(BaseConverterUtil.fromBase36(lastChar));
		if (logger.isDebugEnabled())
			logger.debug("############### lastChar ############ --> "+ lastChar);
		
		test = test+Integer.parseInt(lastChar);
		String test2 = String.valueOf(test);

		String cleCalc = test2.substring(test2.length()-1, test2.length());


		if (logger.isDebugEnabled())
		{
			logger.debug("############### cleCalc ############ --> "+ cleCalc);
			logger.debug("############### lettreCle ############ --> "+ lettreCle);			
			logger.debug("############### numero ############ --> "+ numero);
			logger.debug("############### numero.length() ############ --> "+ numero.length());
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
			logger.debug("############### extract_bea ############ --> "+ extract_nne);
			logger.debug("############### cleCalc ############ --> "+ cleCalc);
			logger.debug("############### lettreCle ############ --> "+ lettreCle);
		}
		
//		if (!lettreCle.equals(cleCalc))
//		{
//			// ine faux cle invalide
//			if (logger.isDebugEnabled())			
//				logger.debug("############### ine faux cle invalidee return 3 ############");	
//			return null;
//		}			
		return cleCalc;
	}	
}
