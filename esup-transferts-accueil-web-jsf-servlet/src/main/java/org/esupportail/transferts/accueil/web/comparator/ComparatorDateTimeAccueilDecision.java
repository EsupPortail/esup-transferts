/**
 * 
 */
package org.esupportail.transferts.accueil.web.comparator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.AccueilDecision;

/**
 * @author cleprous
 *
 */
public class ComparatorDateTimeAccueilDecision implements Comparator<AccueilDecision>, Serializable {

    /**
     * A logger.
     */
    private static final Logger logger = new LoggerImpl(ComparatorDateTimeAccueilDecision.class);

	/**
	 * The serialization id. 
	 */
	private final long serialVersionUID = 1545052598714067760L;

	/**
	 * Constructor.
	 */
	public ComparatorDateTimeAccueilDecision() {
		super();
	}

	public int compare(AccueilDecision p, AccueilDecision q) {
		Date Pdate = p.getDateSaisie();
		Date Qdate =q.getDateSaisie();
		if (Pdate.compareTo(Qdate) < 0)
		{
            if (logger.isDebugEnabled())
    			logger.debug("date1 is before date2");
			return 1;
		}
		else if (Pdate.compareTo(Qdate) > 0)
		{
            if (logger.isDebugEnabled())
    			logger.debug("date1 is after date2");
			return -1;
		}
		else
		{
            if (logger.isDebugEnabled())
    			logger.debug("date1 is equal to date2");
			return 0;
		}
	}

}
