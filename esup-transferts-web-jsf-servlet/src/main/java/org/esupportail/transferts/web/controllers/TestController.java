package org.esupportail.transferts.web.controllers;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.EtudiantRef;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.TrDepartementDTO;
import org.esupportail.transferts.domain.beans.WsPub;
import org.esupportail.transferts.web.comparator.ComparatorSelectItem;
import org.esupportail.transferts.web.dataModel.OdfDataModel;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestController extends AbstractContextAwareController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1084603907906407867L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(TestController.class);
	private EtudiantRef etu;
	private transient List<SelectItem> listeEtablissements = null;
	private transient List<SelectItem> listeTypesDiplome = null;
	private transient List<SelectItem> listeAnneesEtude = null;
	private transient List<SelectItem> listeLibellesDiplome = null;
	private List<OffreDeFormationsDTO> listeLibellesEtape;	
	private boolean deptVide = true;	
	private boolean typesDiplomeVide = true;
	private boolean typesDiplomeAutreVide = true;
	private boolean libelleDiplomeVide = true;
	private boolean libelleEtapeVide = true;	
	private boolean AnneeEtudeVide = true;
	private boolean composanteVide = true;
	private String codeComposante;
	private List<SelectItem> listeComposantes;
	private String codTypDip;
	private Integer codeNiveau;		
	private String codeDiplome;
	private OffreDeFormationsDTO currentOdf;
	private transient OdfDataModel odfDataModel;
	private String source;
	private boolean choixDuVoeuParComposanteByPartenaire;


	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}

	public String goToTest()
	{
		if (logger.isDebugEnabled())
			logger.debug("===>goToTest<===");
		setSource("D");
		this.initialise();
		return "goToTest";
	}

	public String goToTest2()
	{
		if (logger.isDebugEnabled())
			logger.debug("===>goToTest2<===");
		setSource("A");
		this.initialise();
		return "goToTest";
	}	

	public void initialise()
	{
		if (logger.isDebugEnabled())
			logger.debug("===>initialise()<===");
		etu = new EtudiantRef();
		currentOdf = new OffreDeFormationsDTO();
		setDeptVide(true);	
		setTypesDiplomeVide(true);
		setTypesDiplomeAutreVide(true);		
		setAnneeEtudeVide(true);
		setLibelleDiplomeVide(true);
		setLibelleEtapeVide(true);
		setListeLibellesEtape(null);
		setComposanteVide(true);
	}

	public String goToTestSansReset()
	{
		String retour=null;
		if (logger.isDebugEnabled())
			logger.debug("goToTestSansReset");
		if(currentOdf!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("(currentOdf --> "+currentOdf.toString());
			etu.getTransferts().setOdf(currentOdf);
			if (logger.isDebugEnabled())
				logger.debug("(etu.getTransferts().getOdf().toString() --> "+etu.getTransferts().getOdf().toString());
			retour="goToTest";
		}
		else
		{
			String summary = "NULL";
			String detail = "NULL";
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));	
			//retour="goToTest";
		}
		return retour;
	}


	public void resetGeneral()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("public void resetGeneral() --> "+ this.etu.getTransferts().getDept());
		}

		setLibelleEtapeVide(true);
		setAnneeEtudeVide(true);
		setLibelleDiplomeVide(true);		

		if(this.etu.getTransferts().getDept() !=null && !"".equals(this.etu.getTransferts().getDept()))
		{
			setDeptVide(false);
			this.etu.getTransferts().setRne(null);			
			setTypesDiplomeVide(true);
			setTypesDiplomeAutreVide(true);	
		}
		else
		{
			setDeptVide(true);
			this.listeEtablissements=null;
		}
	}

	public void resetTypeDiplome()
	{
		if (logger.isDebugEnabled()) {
			logger.debug("===>public void resetTypeDiplome()<===");
			logger.debug("this.etu.getTransferts().getRne()===>" + this.etu.getTransferts().getRne() + "<===");
		}
		currentOdf=null;
		etu.getTransferts().setOdf(currentOdf);		
		setLibelleEtapeVide(true);
		setAnneeEtudeVide(true);
		setLibelleDiplomeVide(true);

		WsPub wp;

		if(!getSessionController().getRne().equals(this.etu.getTransferts().getRne()))
			wp = getDomainService().getWsPubByRneAndAnnee(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee());
		else
			wp=null;

        if (logger.isDebugEnabled())
		    logger.debug("wp===>"+wp+"<===");

		if(wp!=null)
				this.setChoixDuVoeuParComposanteByPartenaire(wp.isChoixDuVoeuParComposante());
		else
			this.setChoixDuVoeuParComposanteByPartenaire(getSessionController().isChoixDuVoeuParComposante());

		boolean partenaire = false;
		List<WsPub> listeEtablissementsPartenaires = getDomainService().getWsPubByAnnee(getSessionController().getCurrentAnnee());
		
		if(listeEtablissementsPartenaires!=null)
		{
			for(WsPub eu : listeEtablissementsPartenaires)
			{
				if(this.etu.getTransferts().getRne().equals(eu.getRne()))
					partenaire = true;
			}
		}

		if(this.etu.getTransferts().getRne() !=null && !"".equals(this.etu.getTransferts().getRne()) && partenaire==true)
		{
			setTypesDiplomeVide(false);
			setCodTypDip(null);
			setTypesDiplomeAutreVide(true);
			this.getListeTypesDiplome();  
			if(this.getListeTypesDiplome()==null)
			{
				String summary = getString("ERREUR.ODF_INDISPONIBLE");
				String detail = getString("ERREUR.ODF_INDISPONIBLE");
				Severity severity=FacesMessage.SEVERITY_ERROR;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));	
			}			
		}
		else
		{
			if(getSessionController().getRne().equals(this.etu.getTransferts().getRne())){
				String summary = "Vous devez définir votre établissement en tant que partenaire afin de visualiser votre offre de formation";
				String detail = "Vous devez définir votre établissement en tant que partenaire afin de visualiser votre offre de formation";
				Severity severity=FacesMessage.SEVERITY_WARN;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
			}

			setTypesDiplomeVide(true);
			setTypesDiplomeAutreVide(false);
			this.listeTypesDiplome=null;
		}
	}

	public void resetAnneeEtude()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetAnneeEtude()");		

		setTypesDiplomeAutreVide(true);
		if(getCodTypDip() !=null && !"".equals(getCodTypDip()))
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setCodeNiveau(null);
			setLibelleEtapeVide(true);
			setAnneeEtudeVide(false);

			if(this.isChoixDuVoeuParComposanteByPartenaire())
				setComposanteVide(true);
			else
				setLibelleDiplomeVide(true);


			this.getListeAnneesEtude();
		}
		else
		{
			setAnneeEtudeVide(true);
			setLibelleDiplomeVide(true);
			this.listeTypesDiplome=null;
			this.setListeAnneesEtude(null);
			this.setListeLibellesDiplome(null);
			this.setListeLibellesEtape(null);
		}
	}	

	public void resetComposante()
	{
		if (logger.isDebugEnabled())
			logger.debug("public void resetComposante()");
		setTypesDiplomeVide(false);
		setAnneeEtudeVide(false);
		setCodeDiplome(null);
		setLibelleEtapeVide(true);	
		setComposanteVide(false);
		setCodeComposante(null);
		setLibelleDiplomeVide(true); 
	}	

	public void resetLibelleDiplome()
	{
        if (logger.isDebugEnabled())
		    logger.debug("===>public void resetLibelleDiplome()<===");
		setTypesDiplomeAutreVide(true);
		if(getCodeNiveau() !=null)
		{
			setDeptVide(false);
			setTypesDiplomeVide(false);
			setAnneeEtudeVide(false);
			setCodeDiplome(null);
			setLibelleEtapeVide(true);			
			setLibelleDiplomeVide(false);
			this.getListeLibellesDiplome(); 
		}
		else
		{
			setLibelleDiplomeVide(true);
			this.listeLibellesDiplome=null;
			this.listeLibellesEtape=null;
		}
	}

	public void resetLibelleEtape()
	{
        if (logger.isDebugEnabled())
		    logger.debug("===>public void resetLibelleEtape()<===");
		if(this.isChoixDuVoeuParComposanteByPartenaire())
		{
			if(getCodeComposante() !=null && !"".equals(getCodeComposante()))
			{
				setComposanteVide(false);
				setTypesDiplomeAutreVide(true);
				setTypesDiplomeVide(false);				
				setDeptVide(false);
				setAnneeEtudeVide(false);
				setLibelleDiplomeVide(false);
				setLibelleEtapeVide(false);
				odfDataModel=null;
				currentOdf=new OffreDeFormationsDTO();
				setListeLibellesEtape(this.getListeLibellesEtape());
			}
			else
			{
				setLibelleEtapeVide(true);
				this.listeLibellesEtape=null;
			}			
		}
		else
		{
			if(getCodeDiplome() !=null && !"".equals(getCodeDiplome()))
			{
				setTypesDiplomeAutreVide(true);
				setTypesDiplomeVide(false);				
				setDeptVide(false);
				setAnneeEtudeVide(false);
				setLibelleDiplomeVide(false);
				setLibelleEtapeVide(false);
				odfDataModel=null;
				currentOdf=new OffreDeFormationsDTO();
				setListeLibellesEtape(this.getListeLibellesEtape());
			}
			else
			{
				setLibelleEtapeVide(true);
				this.listeLibellesEtape=null;
			}			
		}
	}	

	public List<SelectItem> getListeDepartements() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeDepartements()");
			logger.debug("getDomainServiceScolarite().getListeDepartements();");
		}
		List<SelectItem> listeDepartements = new ArrayList<SelectItem>();
		List<TrDepartementDTO> listeDepartementDTO = getDomainServiceScolarite().getListeDepartements();	
		if(listeDepartementDTO!=null)
		{
			for(TrDepartementDTO dDTO : listeDepartementDTO)
			{			
				SelectItem option = new SelectItem(dDTO.getCodeDept(), dDTO.getLibDept()+" ("+dDTO.getCodeDept()+")");
				listeDepartements.add(option);
			}	
			Collections.sort(listeDepartements,new ComparatorSelectItem());
		}
		else
		{
			SelectItem option = new SelectItem("", "");
			listeDepartements.add(option);	
		}
		return listeDepartements;		
	}

	public List<SelectItem> getListeEtablissements() {
		if(!isDeptVide()) {
			listeEtablissements = getDomainServiceDTO().getListeEtablissements(getSource(), null, getTypesEtablissementListSplit(),
					etu.getTransferts().getDept(), getSessionController().getAjoutEtablissementManuellement(), "," ,getSessionController().isActivEtablissementManuellement());
			Collections.sort(listeEtablissements, new ComparatorSelectItem());
		}
		return listeEtablissements;
	}

	public List<SelectItem> getListeTypesDiplome() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeTypesDiplome()");
			logger.debug("getDomainService().getOdfTypesDiplomeByRneAndAnnee(etu.getTransferts().getRne(), getSessionController().getCurrentAnnee());");
		}
		if(!isTypesDiplomeVide())
		{
			if(listeTypesDiplome==null)
				logger.debug("if(listeTypesDiplome==null) --> " + listeTypesDiplome);		
			listeTypesDiplome = new ArrayList<SelectItem>();
			//			List<TypeDiplome> listeTypesDiplomeDTO = getDomainService().getTypesDiplomeByRneAndAnnee(etu.getTransferts().getRne(), getSessionController().getCurrentAnnee().toString());
			Map<String, String> listeTypesDiplomeDTO = getDomainService().getOdfTypesDiplomeByRneAndAnnee(etu.getTransferts().getRne(), 
					getSessionController().getCurrentAnnee(), 
					true,
					getSource());			

			if(listeTypesDiplomeDTO!=null && !listeTypesDiplomeDTO.isEmpty())
			{
				if (logger.isDebugEnabled()) {
					logger.debug("listeTypesDiplomeDTO : "+listeTypesDiplomeDTO);
				}

//				for (String mapKey : listeTypesDiplomeDTO.keySet()) {
//					// utilise ici hashMap.get(mapKey) pour acc�der aux valeurs
//					SelectItem option = new SelectItem(mapKey, listeTypesDiplomeDTO.get(mapKey));
//					listeTypesDiplome.add(option);
//				}

				for (Map.Entry<String,String> entry : listeTypesDiplomeDTO.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					SelectItem option = new SelectItem(key, value);
					listeTypesDiplome.add(option);
				}

				Collections.sort(listeTypesDiplome,new ComparatorSelectItem());
				return listeTypesDiplome;
			}
			else
				return null;
		}
		return listeTypesDiplome;
	}			

	public EtudiantRef getEtu() {
		return etu;
	}

	public void setEtu(EtudiantRef etu) {
		this.etu = etu;
	}

	public boolean isDeptVide() {
		return deptVide;
	}

	public void setDeptVide(boolean deptVide) {
		this.deptVide = deptVide;
	}

	public boolean isTypesDiplomeVide() {
		return typesDiplomeVide;
	}

	public void setTypesDiplomeVide(boolean typesDiplomeVide) {
		this.typesDiplomeVide = typesDiplomeVide;
	}

	public boolean isTypesDiplomeAutreVide() {
		return typesDiplomeAutreVide;
	}

	public void setTypesDiplomeAutreVide(boolean typesDiplomeAutreVide) {
		this.typesDiplomeAutreVide = typesDiplomeAutreVide;
	}

	public boolean isLibelleEtapeVide() {
		return libelleEtapeVide;
	}

	public void setLibelleEtapeVide(boolean libelleEtapeVide) {
		this.libelleEtapeVide = libelleEtapeVide;
	}

	public boolean isAnneeEtudeVide() {
		return AnneeEtudeVide;
	}

	public void setAnneeEtudeVide(boolean anneeEtudeVide) {
		AnneeEtudeVide = anneeEtudeVide;
	}

	public boolean isLibelleDiplomeVide() {
		return libelleDiplomeVide;
	}

	public void setLibelleDiplomeVide(boolean libelleDiplomeVide) {
		this.libelleDiplomeVide = libelleDiplomeVide;
	}

	public List<SelectItem> getListeAnneesEtude() {
		if (logger.isDebugEnabled())
		{
			logger.debug("public List<SelectItem> getListeAnneesEtude()");
			logger.debug("###################################### --> "+this.etu.getTransferts().getRne()+"-----"+getSessionController().getCurrentAnnee()+"-----"+getCodTypDip()+"-----"+true+"-----D");
		}
		listeAnneesEtude = new ArrayList<SelectItem>();
		Map<Integer, String> listeAnneesEtudeDTO = getDomainService().getAnneesEtudeByRneAndAnneeAndCodTypDip(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(), true, "D");

		if(listeAnneesEtudeDTO!=null && !listeAnneesEtudeDTO.isEmpty())
		{
			if (logger.isDebugEnabled())
				logger.debug("listeAnneesEtudeDTO : "+listeAnneesEtudeDTO);

//			for(Integer mapKey : listeAnneesEtudeDTO.keySet())
//			{
//				SelectItem option = new SelectItem(mapKey, listeAnneesEtudeDTO.get(mapKey));
//				listeAnneesEtude.add(option);
//			}

			for (Map.Entry<Integer,String> entry : listeAnneesEtudeDTO.entrySet()) {
				Integer key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeAnneesEtude.add(option);
			}

			Collections.sort(listeAnneesEtude,new ComparatorSelectItem());
			return listeAnneesEtude;
		}
		else
			return null;
	}		

	public void setListeAnneesEtude(List<SelectItem> listeAnneesEtude) {
		this.listeAnneesEtude = listeAnneesEtude;
	}

	public List<SelectItem> getListeLibellesDiplome() {
		if (logger.isDebugEnabled()) {
			logger.debug("public List<SelectItem> getListeLibellesDiplome()");
			logger.debug("getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(),etu.getTransferts().getCodTypDip(), etu.getTransferts().getCodeNiveau());");
		}
		listeLibellesDiplome = new ArrayList<SelectItem>();
		Map<String, String> listeLibellesDiplomeDTO = getDomainService().getLibellesDiplomeByRneAndAnneeAndCodTypDipAndcodeNiveau(etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(), getCodeNiveau(), true, getSource());
		if(listeLibellesDiplomeDTO!=null && !listeLibellesDiplomeDTO.isEmpty())
		{
			if (logger.isDebugEnabled()) {
				logger.debug("listeLibellesDiplomeDTO : "+listeLibellesDiplomeDTO);
			}

//			for(String mapKey : listeLibellesDiplomeDTO.keySet())
//			{
//				SelectItem option = new SelectItem(mapKey, listeLibellesDiplomeDTO.get(mapKey));
//				listeLibellesDiplome.add(option);
//			}

			for (Map.Entry<String,String> entry : listeLibellesDiplomeDTO.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeLibellesDiplome.add(option);
			}

			Collections.sort(listeLibellesDiplome,new ComparatorSelectItem());
			return listeLibellesDiplome;
		}
		else
			return null;
	}	

	public void setListeLibellesDiplome(List<SelectItem> listeLibellesDiplome) {
		this.listeLibellesDiplome = listeLibellesDiplome;
	}

	public List<OffreDeFormationsDTO> getListeLibellesEtape() {
				if (logger.isDebugEnabled())
				{
					logger.debug("public List<SelectItem> getListeLibellesEtape()");
					logger.debug("(etu.getTransferts() --> "+etu.getTransferts().getRne() +"-----"+ getSessionController().getCurrentAnnee() +"-----"+ getCodTypDip() +"-----"+ getCodeNiveau() +"-----"+ getCodeDiplome());
				}
		if(this.isChoixDuVoeuParComposanteByPartenaire())
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("public List<SelectItem> getListeLibellesEtape()");
				logger.debug("(etu.getTransferts() --> "+this.etu.getTransferts().getRne() +"-----"+ getSessionController().getCurrentAnnee() +"-----"+ getCodTypDip() +"-----"+ getCodeNiveau() +"-----"+ getCodeComposante()+"-----"+getSource());
				logger.debug("(getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(currentEtudiant.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(),  getCodeNiveau(), getCodeComposante(), getSource())");			
			}
			return getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodeComposante(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(),  getCodeNiveau(), getCodeComposante(), getSource());			
		}
		else
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("public List<SelectItem> getListeLibellesEtape()");
				logger.debug("(etu.getTransferts() --> "+this.etu.getTransferts().getRne() +"-----"+ getSessionController().getCurrentAnnee() +"-----"+ getCodTypDip() +"-----"+ getCodeNiveau() +"-----"+ getCodeDiplome()+"-----"+getSource());
				logger.debug("(getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip --> "+getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(),  getCodeNiveau(), getCodeDiplome(),getSource()).size());
			}
			return getDomainService().getVersionEtapeByRneAndAnneeAndCodTypDipAndcodeNiveauAndCodDip(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip(),  getCodeNiveau(), getCodeDiplome(),getSource());
		}	
	}	

	public void setListeLibellesEtape(List<OffreDeFormationsDTO> listeLibellesEtape) {
		this.listeLibellesEtape = listeLibellesEtape;
	}

	public OffreDeFormationsDTO getCurrentOdf() {
		return currentOdf;
	}

	public void setCurrentOdf(OffreDeFormationsDTO currentOdf) {
		this.currentOdf = currentOdf;
	}

	public OdfDataModel getOdfDataModel() {
		if(listeLibellesEtape!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("################################## if(odfs!=null) ########################################");
			if(odfDataModel==null)
			{
				if (logger.isDebugEnabled())
					logger.debug("################################## if(odfDataModel==null) ########################################");
				odfDataModel = new OdfDataModel(listeLibellesEtape);
			}
			return odfDataModel;
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("################################## if(odfs==null) ########################################");
			return new OdfDataModel();
		}
	}

	public void setOdfDataModel(OdfDataModel odfDataModel) {
		this.odfDataModel = odfDataModel;
	}

	public String getCodTypDip() {
		return codTypDip;
	}

	public void setCodTypDip(String codTypDip) {
		this.codTypDip = codTypDip;
	}

	public Integer getCodeNiveau() {
		return codeNiveau;
	}

	public void setCodeNiveau(Integer codeNiveau) {
		this.codeNiveau = codeNiveau;
	}

	public String getCodeDiplome() {
		return codeDiplome;
	}

	public void setCodeDiplome(String codeDiplome) {
		this.codeDiplome = codeDiplome;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isComposanteVide() {
		return composanteVide;
	}

	public void setComposanteVide(boolean composanteVide) {
		this.composanteVide = composanteVide;
	}

	public String getCodeComposante() {
		return codeComposante;
	}

	public void setCodeComposante(String codeComposante) {
		this.codeComposante = codeComposante;
	}

	public List<SelectItem> getListeComposantes() {
		if (logger.isDebugEnabled())
			logger.debug("getListeComposantes");

		listeComposantes = new ArrayList<SelectItem>();
		Map<String, String> listeComposantesDTO = getDomainService().getOdfComposanteByRneAndAnneeAndCodTypDip(this.etu.getTransferts().getRne(), getSessionController().getCurrentAnnee(), getCodTypDip());
		if(listeComposantesDTO!=null && !listeComposantesDTO.isEmpty())
		{
			if (logger.isDebugEnabled()) {
				logger.debug("listeComposantesDTO : "+listeComposantesDTO);
			}
			for (Map.Entry<String,String> entry : listeComposantesDTO.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				SelectItem option = new SelectItem(key, value);
				listeComposantes.add(option);
			}
			Collections.sort(listeComposantes,new ComparatorSelectItem());
			return listeComposantes;
		}
		else
			return null;
	}	

	public void setListeComposantes(List<SelectItem> listeComposantes) {
		this.listeComposantes = listeComposantes;
	}

	public boolean isChoixDuVoeuParComposanteByPartenaire() {
		return choixDuVoeuParComposanteByPartenaire;
	}

	public void setChoixDuVoeuParComposanteByPartenaire(boolean choixDuVoeuParComposanteByPartenaire) {
		this.choixDuVoeuParComposanteByPartenaire = choixDuVoeuParComposanteByPartenaire;
	}
}