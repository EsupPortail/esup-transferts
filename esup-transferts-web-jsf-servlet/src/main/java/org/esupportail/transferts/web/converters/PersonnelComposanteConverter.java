package org.esupportail.transferts.web.converters;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.PersonnelComposante;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class PersonnelComposanteConverter implements Converter{

	/**
	 * 
	 */
	private static final Logger logger = new LoggerImpl(PersonnelComposanteConverter.class);	
	
	public String getAsString(FacesContext context, UIComponent component, Object value) 
	{
		return  String.valueOf(((PersonnelComposante) value).getUid())+"|"+
		String.valueOf(((PersonnelComposante) value).getDisplayName())+"|"+
		String.valueOf(((PersonnelComposante) value).getMailPersonnel())+"|"+
		String.valueOf(((PersonnelComposante) value).getCodeComposante())+"|"+
		String.valueOf(((PersonnelComposante) value).getSource()+"|"+
		String.valueOf(((PersonnelComposante) value).getLibelleComposante())+"|"+
		String.valueOf(((PersonnelComposante) value).getTypePersonnel())+"|"+
		String.valueOf(((PersonnelComposante) value).getAnnee())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitSuppression())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitEditionPdf())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitAvis())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitAvisDefinitif())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitDecision())+"|"+
		String.valueOf(((PersonnelComposante) value).getDroitDeverrouiller())+"|"+
		String.valueOf(((PersonnelComposante) value).getAlertMailDemandeTransfert())+"|"+
		String.valueOf(((PersonnelComposante) value).getAlertMailSva()));
	}
	
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
		PersonnelComposante category = new PersonnelComposante();
		//		      category.setId(Integer.parseInt(value));
		if (logger.isDebugEnabled())
			logger.debug("##################### tokens #####################--> " + value);
		String[] tokens = value.split("\\|");
		if (logger.isDebugEnabled())
		{
			logger.debug("##################### tokens[0] #####################--> " + tokens[0]);
			logger.debug("##################### tokens[1] #####################--> " + tokens[1]);
			logger.debug("##################### tokens[2] #####################--> " + tokens[2]);
			logger.debug("##################### tokens[3] #####################--> " + tokens[3]);
			logger.debug("##################### tokens[4] #####################--> " + tokens[4]);		
			logger.debug("##################### tokens[5] #####################--> " + tokens[5]);	
			logger.debug("##################### tokens[6] #####################--> " + tokens[6]);	
			logger.debug("##################### tokens[7] #####################--> " + tokens[7]);	
			logger.debug("##################### tokens[8] #####################--> " + tokens[8]);	
			logger.debug("##################### tokens[9] #####################--> " + tokens[9]);
			logger.debug("##################### tokens[10] #####################--> " + tokens[10]);	
			logger.debug("##################### tokens[11] #####################--> " + tokens[11]);
			logger.debug("##################### tokens[12] #####################--> " + tokens[12]);
			logger.debug("##################### tokens[13] #####################--> " + tokens[13]);
			logger.debug("##################### tokens[13] #####################--> " + tokens[14]);
			logger.debug("##################### tokens[13] #####################--> " + tokens[15]);

		}
		category.setUid(tokens[0]);
		category.setDisplayName(tokens[1]);
		category.setMailPersonnel(tokens[2]);
		category.setCodeComposante(tokens[3]);
		category.setSource(tokens[4]);
		category.setLibelleComposante(tokens[5]);
		category.setTypePersonnel(Integer.parseInt(tokens[6]));
		category.setAnnee(Integer.parseInt(tokens[7]));
		category.setDroitSuppression(tokens[8]);
		category.setDroitEditionPdf(tokens[9]);
		category.setDroitAvis(tokens[10]);
		category.setDroitAvisDefinitif(tokens[11]);
		category.setDroitDecision(tokens[12]);
		category.setDroitDeverrouiller(tokens[13]);
		category.setAlertMailDemandeTransfert(tokens[14]);
		category.setAlertMailSva(tokens[15]);
//		category.setCodeComposante(value);
		return category;
	}



}
