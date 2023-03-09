/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import org.esupportail.transferts.domain.beans.*;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 * 
 */
public interface DomainServiceDTO extends Serializable {

	public List<SelectItem> getListeEtablissements(String source, String rneAppli, List<String> typeEtab, String dept, String stringAsSplit, String split, boolean parametreActif);

	public List<SelectItem> getListeBacOuEqu();
}
