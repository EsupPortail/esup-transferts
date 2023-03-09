/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.dao.DaoService;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.transferts.utils.Fonctions;
import org.springframework.beans.factory.InitializingBean;

import javax.faces.model.SelectItem;
import java.util.*;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2016
 *
 */
public class DomainServiceDTOImpl implements DomainServiceDTO, InitializingBean {

	/**
	 * For Serialize.
	 */
	private static final long serialVersionUID = 55622012345653456L;
	/**
	 * For Logging.
	 */
	private static final Logger logger = new LoggerImpl(DomainServiceDTOImpl.class);
	private DomainServiceScolarite domainServiceScolarite;
	private DaoService daoService;

	@Override
	public void afterPropertiesSet() throws Exception {
		// nothing to do yet.
	}

	public DomainServiceScolarite getDomainServiceScolarite() {
		return domainServiceScolarite;
	}

	public void setDomainServiceScolarite(DomainServiceScolarite domainServiceScolarite) {
		this.domainServiceScolarite = domainServiceScolarite;
	}

	public DaoService getDaoService() {
		return daoService;
	}

	public void setDaoService(DaoService daoService) {
		this.daoService = daoService;
	}

	@Override
	public List<SelectItem> getListeEtablissements(String source, String rneAppli, List<String> typeEtab, String dept, String stringAsSplit, String split, boolean parametreActif) {
		if (logger.isDebugEnabled()) {
			logger.debug("DomainServiceDTOImpl===>getListeEtablissements(String typeListTypeEtabASplit, String dept, String stringAsSplit, String split, boolean parametreActif)<===");
			logger.debug("DomainServiceDTOImpl===>source===>" +source+"<===");
			logger.debug("DomainServiceDTOImpl===>rneAppli===>" +rneAppli+"<===");
			logger.debug("DomainServiceDTOImpl===>typeEtab===>" +typeEtab+"<===");
			logger.debug("DomainServiceDTOImpl===>dept===>" +dept+"<===");
			logger.debug("DomainServiceDTOImpl===>stringAsSplit===>" +stringAsSplit+"<===");
			logger.debug("DomainServiceDTOImpl===>split===>" +split+"<===");
			logger.debug("DomainServiceDTOImpl===>parametreActif===>" +parametreActif+"<===");
			logger.debug("DomainServiceDTOImpl===>getDomainServiceScolarite()===>" +getDomainServiceScolarite()+"<===");
		}
		List<SelectItem> listeEtablissements = new ArrayList<SelectItem>();
		Map <String, String> maps=null;

		if(parametreActif)
			maps = Fonctions.stringSplitToMap(stringAsSplit, split, parametreActif);
		if(maps!=null) {
			for (Map.Entry<String, String> entry : maps.entrySet()) {
				if (logger.isDebugEnabled())
					logger.debug("foreach maps===>" + entry.getKey() + "/" + entry.getValue() + "<===");
			}
		}

		for (String typesEtablissementSplit : typeEtab)
		{
			List<TrEtablissementDTO> etablissementDTO = getDomainServiceScolarite().getListeEtablissements(typesEtablissementSplit, dept);
			if (etablissementDTO != null)
			{
				for (TrEtablissementDTO eDTO : etablissementDTO)
				{
					if (logger.isDebugEnabled())
						logger.debug("etablissementDTO : " + etablissementDTO);

						/*Début ajout des établissements manuellement*/
					if(maps!=null)
						maps.remove(eDTO.getCodeEtb());
						/*Fin ajout des établissements manuellement*/

					if (source!=null && !eDTO.getCodeEtb().equals(rneAppli)) {
						SelectItem option = new SelectItem(eDTO.getCodeEtb(), eDTO.getLibEtb());
						listeEtablissements.add(option);
					}
				}
			} else {
				if (logger.isDebugEnabled())
					logger.debug("etablissementDTO == null");
			}
		}

			/*Début ajout des établissements manuellement*/
		if(maps!=null) {
			for (Map.Entry<String, String> entry : maps.entrySet()) {
				if (logger.isDebugEnabled()) {
					logger.debug("entry.getKey()===>" + entry.getKey() + "<===");
					logger.debug("dept===>" + dept + "<===");
				}

				if (entry.getKey().contains(dept)) {
					if (logger.isDebugEnabled())
						logger.debug("foreach maps après remove===>" + entry.getKey() + "/" + entry.getValue() + "<===");
					SelectItem option = new SelectItem(entry.getKey(), entry.getValue());
					listeEtablissements.add(option);
				}
			}
		}
			/*Fin ajout des établissements manuellement*/
		return listeEtablissements;
	}

	@Override
	public List<SelectItem> getListeBacOuEqu() {
		if (logger.isDebugEnabled())
			logger.debug("getDomainServiceDTOImpl().recupererBacOuEquWS()");

		List<SelectItem> listeBac = new ArrayList<SelectItem>();
		List<TrBac> listeBacDTO = getDomainServiceScolarite().recupererBacOuEquWS(null);
		if(listeBacDTO!=null)
		{
			for(TrBac bacDTO : listeBacDTO)
			{
				SelectItem option = new SelectItem(bacDTO.getCodeBac(),bacDTO.getLibBac());
				listeBac.add(option);
			}
//			Collections.sort(listeBac,new ComparatorSelectItem());
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listeBac.add(option);
		}
		return listeBac;
	}


}
