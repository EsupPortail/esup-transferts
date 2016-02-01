/**
 * 
 */
package org.esupportail.transferts.web.comparator;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.faces.model.SelectItem;

import org.esupportail.transferts.domain.beans.AccueilDecision;

/**
 * @author cleprous
 *
 */
public class ComparatorDateTimeAccueilDecision implements Comparator<AccueilDecision>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = 1545052575014067760L;

	/**
	 * Constructor.
	 */
	public ComparatorDateTimeAccueilDecision() {
		super();
	}

	public int compare(AccueilDecision p, AccueilDecision q) {
		Date Pdate = p.getDateSaisie();
		Date Qdate =q.getDateSaisie();
//		return Pdate.compareTo(Qdate) > 0 ? 0 : 1;
//		return p.getDateSaisie().compareTo(q.getDateSaisie())*-1;
		if (Pdate.compareTo(Qdate) < 0)
		{
//			System.out.println("date1 is before date2");
			return 1;
		}
		else if (Pdate.compareTo(Qdate) > 0)
		{
//			System.out.println("date1 is after date2");
			return -1;
		}
		else
		{
//			System.out.println("date1 is equal to date2");
			return 0;
		}		
	}

}