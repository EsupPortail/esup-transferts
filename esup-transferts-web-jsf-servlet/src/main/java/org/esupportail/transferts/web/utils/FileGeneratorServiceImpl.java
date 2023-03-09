package org.esupportail.transferts.web.utils;

import fr.univ.rennes1.cri.services.export.SpreadsheetColumn;
import fr.univ.rennes1.cri.services.export.SpreadsheetObject;
import fr.univ.rennes1.cri.services.export.SpreadsheetService;
import fr.univ.rennes1.cri.utils.ReflectHelper;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.transferts.domain.beans.EtudiantRefExcel;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.PersonnelComposante;
import org.springframework.beans.factory.InitializingBean;

import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Farid AIT KARRA
 *
 */
public class FileGeneratorServiceImpl implements Serializable, InitializingBean, FileGeneratorService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(FileGeneratorServiceImpl.class);


	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
	}


	public void exportXlsODF(OffreDeFormationsDTO[] selectedOdfs, String typeExport, String fileName, List<String> colonnesChoisies) 
	{
		List<SpreadsheetColumn> colonnes = new ArrayList<SpreadsheetColumn>();
		// colonnes partie convention
		for (OdfColonneEnum s : OdfColonneEnum.values()) {
			// definition des valeurs
			Map<Integer, String> vOdf = new HashMap<Integer, String>();
			int cpt = 1;
			//			for (OffreDeFormationsDTO odf : selectedOdfs) {
			if(selectedOdfs!=null)
			{
				for (int i=0;i<selectedOdfs.length;i++) 
				{				
					String endResult = "";
					try {
						// methode reflectHelper, prend objet, recupere le getter de la propertie
						// pour un Boolean , ajouter une methode get
						Object result = ReflectHelper.resultExpression(s.getNameProperty(), selectedOdfs[i]);

						if (result != null) {
							endResult = result.toString();
						} 
					} catch (NullPointerException e) {
						logger.error(e);
                        if (logger.isDebugEnabled())
						    logger.debug("la colonne " + s.getKeyLabel() + "pour la propriete " + s.getNameProperty() + "est vide (property null)");
					}
					vOdf.put(cpt, endResult);

					cpt++;
				}
			}
			// definition de toutes les colonnes
			I18nService service = (I18nService) BeanUtils.getBean("i18nService");
			SpreadsheetColumn cConvention = new SpreadsheetColumn(service.getString(s.getKeyLabel()), vOdf);
			colonnes.add(cConvention);
		}
		SpreadsheetObject sso = new SpreadsheetObject(colonnes);
		generate(sso, typeExport, fileName);
	}		

	/**
	 * @see org.esupportail.pstage.services.application.FileGeneratorService#conventionFile(java.util.List, java.lang.String, java.lang.String, java.util.List)
	 */
	public void conventionFile(final List<EtudiantRefExcel> listeEtudiants, final String typeExport, final String filename, final List<String> colonnesChoisies) {
		List<SpreadsheetColumn> colonnes = new ArrayList<SpreadsheetColumn>();
		// colonnes partie convention
		for (EtudiantColonneEnum s : EtudiantColonneEnum.values()) {
			// definition des valeurs
			Map<Integer, String> vEtudiant = new HashMap<Integer, String>();
			int cpt = 1;
			for (EtudiantRefExcel etudiant : listeEtudiants) {
				String endResult = "";
				try {
					// methode reflectHelper, prend objet, recupere le getter de la propertie
					// pour un Boolean , ajouter une methode get
					Object result = ReflectHelper.resultExpression(s.getNameProperty(), etudiant);

					if (result != null) {
						endResult = result.toString();
					} 
				} catch (NullPointerException e) {
					logger.error(e);
                    if (logger.isDebugEnabled())
					    logger.debug("la colonne " + s.getKeyLabel() + "pour la propriete " + s.getNameProperty() + "est vide (property null)");
				}
				vEtudiant.put(cpt, endResult);

				cpt++;
			}
			// definition de toutes les colonnes
			I18nService service = (I18nService) BeanUtils.getBean("i18nService");
			SpreadsheetColumn cConvention = new SpreadsheetColumn(service.getString(s.getKeyLabel()), vEtudiant);
			colonnes.add(cConvention);
		}


		SpreadsheetObject sso = new SpreadsheetObject(colonnes);
		generate(sso, typeExport, filename);
	}	

	/**
	 * @see org.esupportail.pstage.services.application.FileGeneratorService#conventionFile(java.util.List, java.lang.String, java.lang.String, java.util.List)
	 */
	public void conventionFileAccueil(final List<EtudiantRefExcel> listeEtudiants, final String typeExport, final String filename, final List<String> colonnesChoisies) {
		List<SpreadsheetColumn> colonnes = new ArrayList<SpreadsheetColumn>();
		// colonnes partie convention
		for (EtudiantAccueilColonneEnum s : EtudiantAccueilColonneEnum.values()) {
			// definition des valeurs
			Map<Integer, String> vEtudiant = new HashMap<Integer, String>();
			int cpt = 1;
			for (EtudiantRefExcel etudiant : listeEtudiants) {
				String endResult = "";
				try {
					// methode reflectHelper, prend objet, recupere le getter de la propertie
					// pour un Boolean , ajouter une methode get
					Object result = ReflectHelper.resultExpression(s.getNameProperty(), etudiant);

					if (result != null) {
						endResult = result.toString();
					} 
				} catch (NullPointerException e) {
					logger.error(e);
                    if (logger.isDebugEnabled())
					    logger.debug("la colonne " + s.getKeyLabel() + "pour la propriete " + s.getNameProperty() + "est vide (property null)");
				}
				vEtudiant.put(cpt, endResult);

				cpt++;
			}
			// definition de toutes les colonnes
			I18nService service = (I18nService) BeanUtils.getBean("i18nService");
			SpreadsheetColumn cConvention = new SpreadsheetColumn(service.getString(s.getKeyLabel()), vEtudiant);
			colonnes.add(cConvention);
		}


		SpreadsheetObject sso = new SpreadsheetObject(colonnes);
		generate(sso, typeExport, filename);
	}		

	public void generate(SpreadsheetObject sso, String typeExport,
			String filename) {
		if (logger.isDebugEnabled()) {
			logger.debug("enter generate() method");
		}
		if (typeExport != null) {
			String type = typeExport.toUpperCase();
			if (logger.isDebugEnabled()) {
				logger.debug("type = " + type);
			}
			SpreadsheetService service = 
					(SpreadsheetService) BeanUtils.getBean("export" + type + "Service");
			if (logger.isDebugEnabled()) {
				logger.debug("service = " + service.getClass().getCanonicalName());
			}
			byte[] data = service.datasToBytes(sso);
			PDFUtils.setDownLoadAndSend(data, FacesContext.getCurrentInstance(),
					"application/vnd.ms-excel",	filename);	
		}
	}

	public void opiFile(final List<String> listeOpi, final String typeExport, final String filename) {
		generateOpi(listeOpi, typeExport, filename);
	}	

	public void generateOpi(List<String> listeOpi, String typeExport,
			String filename) {
		if (logger.isDebugEnabled()) {
			logger.debug("enter generate() method");
		}
		if (typeExport != null) {
			String type = typeExport.toUpperCase();
			if (logger.isDebugEnabled()) {
				logger.debug("type = " + type);
			}

			String test="";
			int nb=1;
			if (listeOpi != null && !listeOpi.isEmpty()) 
			{
				for(int i=0;i<listeOpi.size();i++)
				{
					String chaine=listeOpi.get(i);

					if (logger.isDebugEnabled())
						logger.debug("test.length() --> "+chaine);
					if(i==0)
					{
						test += chaine;
					}
					else
					{
						test +="\n"+chaine;
						nb++;
					}
					if (logger.isDebugEnabled()) {
						logger.debug("test.length() --> "+chaine.length());
					}				
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("test --> "+test);				
				logger.debug("test.length() --> "+test.length());
				logger.debug("nb --> "+nb);
			}
			Charset iso88591charset = Charset.forName("ISO-8859-1");
			byte[] data;
			data = test.getBytes(iso88591charset);
			PDFUtils.setDownLoadAndSend(data, FacesContext.getCurrentInstance(), "text/plain",	filename);	
		}
	}


	public void exportXlsPersonnelsComposantesDepart(List<PersonnelComposante> persComp, String typeExport, String filename, List<String> colonnesChoisies) 
	{
		List<SpreadsheetColumn> colonnes = new ArrayList<SpreadsheetColumn>();
		for (PersonnelsComposantesColonneEnumDepart s : PersonnelsComposantesColonneEnumDepart.values()) {
			// definition des valeurs
			Map<Integer, String> vEtudiant = new HashMap<Integer, String>();
			int cpt = 1;
			if(persComp!=null)
			{
				for (PersonnelComposante pers : persComp) {
					String endResult = "";
					try {
						// methode reflectHelper, prend objet, recupere le getter de la propertie
						// pour un Boolean , ajouter une methode get
						Object result = ReflectHelper.resultExpression(s.getNameProperty(), pers);

						if (result != null) {
							endResult = result.toString();
						} 
					} catch (NullPointerException e) {
						logger.error(e);
                        if (logger.isDebugEnabled())
						    logger.debug("la colonne " + s.getKeyLabel()	+ "pour la propriete " + s.getNameProperty() + "est vide (property null)");
					}
					vEtudiant.put(cpt, endResult);

					cpt++;
				}
			}
			// definition de toutes les colonnes
			I18nService service = (I18nService) BeanUtils.getBean("i18nService");
			SpreadsheetColumn cConvention = new SpreadsheetColumn(service.getString(s.getKeyLabel()), vEtudiant);
			colonnes.add(cConvention);
		}


		SpreadsheetObject sso = new SpreadsheetObject(colonnes);
		generate(sso, typeExport, filename);
	}

	public void exportXlsPersonnelsComposantesArrivee(List<PersonnelComposante> persComp, String typeExport, String filename, List<String> colonnesChoisies) 
	{
		List<SpreadsheetColumn> colonnes = new ArrayList<SpreadsheetColumn>();
		// colonnes partie convention
		for (PersonnelsComposantesColonneEnumArrivee s : PersonnelsComposantesColonneEnumArrivee.values()) {
			// definition des valeurs
			Map<Integer, String> vEtudiant = new HashMap<Integer, String>();
			int cpt = 1;
			if(persComp!=null)
			{
				for (PersonnelComposante pers : persComp) 
				{
					String endResult = "";
					try {
						// methode reflectHelper, prend objet, recupere le getter de la propertie
						// pour un Boolean , ajouter une methode get
						Object result = ReflectHelper.resultExpression(s.getNameProperty(), pers);

						if (result != null) {
							endResult = result.toString();
						} 
					} catch (NullPointerException e) {
						logger.error(e);
                        if (logger.isDebugEnabled())
						    logger.debug("la colonne " + s.getKeyLabel()+ "pour la propriete " + s.getNameProperty() + "est vide (property null)");
					}
					vEtudiant.put(cpt, endResult);

					cpt++;
				}
			}
			// definition de toutes les colonnes
			I18nService service = (I18nService) BeanUtils.getBean("i18nService");
			SpreadsheetColumn cConvention = new SpreadsheetColumn(service.getString(s.getKeyLabel()), vEtudiant);
			colonnes.add(cConvention);
		}


		SpreadsheetObject sso = new SpreadsheetObject(colonnes);
		generate(sso, typeExport, filename);
	}	
}
