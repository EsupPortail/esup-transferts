/**
 * 
 */
package org.esupportail.transferts.web.comparator;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.EtudiantRef;
import org.primefaces.model.SortOrder;

import java.util.Comparator;

public class ComparatorEtudiantRef implements Comparator<EtudiantRef> {

    /**
     * A logger.
     */
    private final Logger logger = new LoggerImpl(this.getClass());
    private String sortField;
    private SortOrder sortOrder;
   
    public ComparatorEtudiantRef(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

	//@Override
	public int compare(EtudiantRef etu1, EtudiantRef etu2) {
        try {
            Object value1 = EtudiantRef.class.getField(this.sortField).get(etu1);
            Object value2 = EtudiantRef.class.getField(this.sortField).get(etu2);

            int value = ((Comparable)value1).compareTo(value2);
           
            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        }
        catch(Exception e) {
            logger.error(e);
//            throw new RuntimeException();
            return 0;
        }
	}
}


