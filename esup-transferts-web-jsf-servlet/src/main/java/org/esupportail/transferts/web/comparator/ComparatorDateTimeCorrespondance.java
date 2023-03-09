/**
 * 
 */
package org.esupportail.transferts.web.comparator;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.Correspondance;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * @author cleprous
 *
 */
public class ComparatorDateTimeCorrespondance implements Comparator<Correspondance>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = 1545052575014067760L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(ComparatorDateTimeCorrespondance.class);

	/**
	 * Constructor.
	 */
	public ComparatorDateTimeCorrespondance() {
		super();
	}

	public int compare(Correspondance p, Correspondance q) {
		Date pDate = p.getDateSaisie();
		Date qDate =q.getDateSaisie();
		if (pDate.compareTo(qDate) < 0)
		{
			if (logger.isDebugEnabled())
				logger.debug("===>date1 is before date2<===");
			return 1;
		}
		else if (pDate.compareTo(qDate) > 0)
		{
			if (logger.isDebugEnabled())
				logger.debug("===>date1 is after date2<===");
			return -1;
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("===>date1 is equal to date2<===");
			return 0;
		}
	}

}
