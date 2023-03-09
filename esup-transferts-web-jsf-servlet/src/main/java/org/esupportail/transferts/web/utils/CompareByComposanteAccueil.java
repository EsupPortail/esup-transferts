/**
 * 
 */
package org.esupportail.transferts.web.utils;

import org.esupportail.transferts.domain.beans.EtudiantRef;

import java.util.Comparator;

/**
 * @author Farid AITKARRA
 * copyleft
 */
public class CompareByComposanteAccueil implements Comparator<EtudiantRef>{

	public int compare(EtudiantRef etu1, EtudiantRef etu2) 
	{
        if (etu1.getTransferts().getOdf().getCodeComposante().compareTo(etu2.getTransferts().getOdf().getCodeComposante()) > 0) {
            return 1;
        }
        if (etu1.getTransferts().getOdf().getCodeComposante().compareTo(etu2.getTransferts().getOdf().getCodeComposante()) < 0) {
            return -1;
        }
        // at this point all a.b,c,d are equal... so return "equal"
        return 0;
	}
	
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

}
