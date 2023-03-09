/**
 * 
 */

package org.esupportail.transferts.utils;
/**
 * Calcule la cle OPI
 *
 * @author AIT KARRA Farid
 */
public class RneModuleBase36 {

	/**
	 * Genere un modulo base 36 du code RNE
	 * @param String RNE
	 */
	public static String genereCle(String rne) 
	{
		String tabBase36[] = {"0", "1", "2" , "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G",
				"H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		String cle = "";

			String test = rne.substring(0, rne.length()-1);
			int test2 = Integer.parseInt(test); 
			for(int i=0;i<5;i++)	
			{
				int test3 = test2 / 36;
				int test4 = test2 % 36;

				test2  = test3;
				cle = tabBase36[test4] + cle;
			}
		return cle;
	}
}
