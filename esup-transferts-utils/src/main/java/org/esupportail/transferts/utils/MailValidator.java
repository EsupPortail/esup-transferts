/**
 * 
 */
package org.esupportail.transferts.utils;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.esupportail.commons.services.i18n.I18nUtils;

/**
 * @author Farid AIT KARRA : farid.aitkarra@univ-artois.fr
 */
public class MailValidator implements Validator {

	public static final String REGEX_MAIL = "^(\\w([-]?[.]?[\\w]*\\w)*@(\\w[-\\w]*\\w\\.)+[a-zA-Z]{2,9})$";
	
	/**
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(@SuppressWarnings("unused") FacesContext fc, @SuppressWarnings("unused") UIComponent uic, Object o)
	throws ValidatorException {
		String value = (String) o;
		value=value.trim();
		if(!value.matches(REGEX_MAIL))
		{
//			String summary = I18nUtils.createI18nService().getString("ERREUR.MAIL");
//			String detail = I18nUtils.createI18nService().getString("ERREUR.MAIL");
			String summary = "L'adresse mail est incorrecte";
			String detail = "L'adresse mail est incorrecte";			
			Severity severity=FacesMessage.SEVERITY_ERROR;
			throw new ValidatorException(new FacesMessage(severity,summary, detail));			
		}
	}

}