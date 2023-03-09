package org.esupportail.transferts.web.controllers;

import org.apache.commons.lang.ArrayUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.WsPub;
import org.esupportail.transferts.web.dataModel.OdfDataModel;
import org.esupportail.transferts.web.utils.FileGeneratorService;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OdfController extends AbstractContextAwareController {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1084601237906407867L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(OdfController.class);
	private List<OffreDeFormationsDTO> odfs;
	private OffreDeFormationsDTO[] selectedOdfs;
	private transient OdfDataModel odfDataModel;
	private List<String> listFormationsEnDoublons;
	private transient FileGeneratorService fileGeneratorService;
	private List<OffreDeFormationsDTO> filteredODF;  
	private Integer nbOdfs;
	private String filtre;
	private transient List<OffreDeFormationsDTO> list=null;

	public void addMessage() 
	{
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Sofianou===>"+getFiltre()+"<==="));
	}	

	public Integer getNbOdfs() {
		return nbOdfs;
	}

	public void setNbOdfs(Integer nbOdfs) {
		this.nbOdfs = nbOdfs;
	}

	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}

	public void alertPartenaireMAJOdfs()
	{
		String sujet = getString("MAIL.ODF.MAJ.SUJET");
		List<WsPub> listePartenaires = getDomainService().getWsPubByAnnee(getSessionController().getCurrentAnnee());

		String libEtab = getDomainServiceScolarite().getEtablissementByRne(getSessionController().getRne()).getLibOffEtb();

		for(WsPub part : listePartenaires)
		{
			if(!part.getRne().equals(getSessionController().getRne()))
			{
				try {
					String body = getString("MAIL.ODF.MAJ.BODY", libEtab);
					getSmtpService().send(new InternetAddress(part.getMailCorrespondantFonctionnel()), sujet, body, body);
					//					tmp += libEtab+" \n ";
				} 
				catch (AddressException e) 
				{
					logger.error(e);
					String summary1 = getString("ERREUR.ENVOI_MAIL");
					String detail1 = getString("ERREUR.ENVOI_MAIL");
					Severity severity1 = FacesMessage.SEVERITY_ERROR;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity1, summary1, detail1));
				}
			}
		}		
		String summary = "Mail d'alerte envoyé aux partenaires";
		String detail = "Mail d'alerte envoyé aux partenaires";
		Severity severity=FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));	
	}

	public  void exportXlsODF()
	{
		if (logger.isDebugEnabled()) 
			logger.debug("exportXslODF()");

		String typeExport = "xls";
		Integer anneeSuivante = getSessionController().getCurrentAnnee()+1;
		String fileName = "Offre_de_formation_" + getSessionController().getCurrentAnnee()+"-"+anneeSuivante + "." + typeExport;

		List<OffreDeFormationsDTO> list = getDomainService().getAllOffreDeFormationByAnneeAndRneAndAtifOuPas(getSessionController().getCurrentAnnee(), getSessionController().getRne());
		if(list!=null && !list.isEmpty())
		{
			this.selectedOdfs = new OffreDeFormationsDTO[list.size()];
			if(logger.isDebugEnabled())
			{
				logger.debug("list --> getSessionController().getCurrentAnnee() : "+getSessionController().getCurrentAnnee());
				logger.debug("list --> list.size() : "+list.size());
			}

			for(int i=0;i<list.size();i++)
			{
				if(logger.isDebugEnabled())
					logger.debug("list --> "+list.get(i).getLibVersionEtape());					
				this.selectedOdfs[i] = list.get(i);
			}
		}

		List<String> colonnesChoisies = new ArrayList<String>();
		getFileGeneratorService().exportXlsODF(selectedOdfs, typeExport, fileName, colonnesChoisies);		
	}

	// combien de doublons dans le tableau passe en parametre ?
	public int getNbDoublons(String[] tab)
	{
		listFormationsEnDoublons = new ArrayList<String>();
		Set<String> hs = new HashSet<String>(); 
		for (String value : tab) 
			if (!hs.add(value)) 
			{
				listFormationsEnDoublons.add(value);
				if(logger.isDebugEnabled())
					logger.debug("Found Duplicate " + value);
			}		
		return listFormationsEnDoublons.size();
	}

	public void verifier()
	{
		String[] tab = new String[selectedOdfs.length]; 
		for(int i=0;i<selectedOdfs.length;i++)
		{
			tab[i] = selectedOdfs[i].getCodeDiplome()+"|"+selectedOdfs[i].getCodeVersionDiplome()+"|"+selectedOdfs[i].getCodeEtape()+"|"+selectedOdfs[i].getCodeVersionEtape();
		}
		if(getNbDoublons(tab)==0)
		{
			String summary = "Aucune formation en doublon";
			String detail = "Aucune formation en doublon";
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));				
		}
		else
		{
			String tmp="Code Diplome : ";
			for(int i=0;i<listFormationsEnDoublons.size();i++)
				tmp+=listFormationsEnDoublons.get(i).substring(0, listFormationsEnDoublons.get(i).indexOf('|'))+" - ";
			String summary = "Liste des doublons : ";
			String detail = tmp;
			if (logger.isDebugEnabled())
				logger.debug("detail des doublons -->"+detail);
			Severity severity=FacesMessage.SEVERITY_WARN;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));				
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("testDoublons");
			logger.debug("selectedOdfs.length --> "+ selectedOdfs.length);	
			logger.debug("tab.length --> "+ tab.length);			
			logger.debug("###################################### nombre de doublons dans ODF "+ getNbDoublons(tab)+" ###############################################");
		}
	}

	public void addOdfs()
	{
		if(getFiltre()!=null && !"decoche".equals(getFiltre()))
		{
			List<OffreDeFormationsDTO> LOdfsLocal = getDomainService().getSelectedOdfs(getSessionController().getCurrentAnnee(),getSessionController().getRne());
			List<OffreDeFormationsDTO> listFormationsInactives = new ArrayList<OffreDeFormationsDTO>();
			List<OffreDeFormationsDTO> listFormationsIdentiques = new ArrayList<OffreDeFormationsDTO>();
			int k=0;

			if(LOdfsLocal!=null && !LOdfsLocal.isEmpty())
			{
				for(OffreDeFormationsDTO local :LOdfsLocal)
				{
					boolean test = false;
					for(OffreDeFormationsDTO odf : selectedOdfs)
					{
						if(local!=null 
								&& local.getAnnee().equals(odf.getAnnee())
								&& local.getRne().equals(odf.getRne()) 
								&& local.getCodeDiplome().equals(odf.getCodeDiplome())
								&& local.getCodeVersionDiplome().equals(odf.getCodeVersionDiplome())
								&& local.getCodeEtape().equals(odf.getCodeEtape())
								&& local.getCodeVersionEtape().equals(odf.getCodeVersionEtape())
								&& local.getCodeCentreGestion().equals(odf.getCodeCentreGestion())
								)
						{
							if (logger.isDebugEnabled())
							{
								logger.debug("odf.getDepart()-->"+odf.getDepart());
								logger.debug("odf.getArrivee()-->"+odf.getArrivee());
							}
							test = true;
							break;
						}
					}
					if(!test)
					{
						local.setActif(0);
						listFormationsInactives.add(local);							
					}	
					else
					{
						if (logger.isDebugEnabled())
						{
							logger.debug("local.getDepart()-->"+local.getDepart());
							logger.debug("local.getArrivee()-->"+local.getArrivee());
						}
						listFormationsIdentiques.add(local);
					}
				}			
			}

			OffreDeFormationsDTO[] tabFormationsInactives = new OffreDeFormationsDTO[listFormationsInactives.size()]; 
			List<OffreDeFormationsDTO> listFormationsSelectionneMoinsCellesIdentiques = new ArrayList<OffreDeFormationsDTO>();

			for(int i=0; i<listFormationsInactives.size();i++)
				tabFormationsInactives[i]=listFormationsInactives.get(i);

			for(int i=0; i<selectedOdfs.length;i++)
			{
				boolean test2=false;
				if(!listFormationsIdentiques.isEmpty())
				{
					for(OffreDeFormationsDTO o : listFormationsIdentiques)
					{
						if(o!=null 
								&& o.getAnnee().equals(selectedOdfs[i].getAnnee())
								&& o.getRne().equals(selectedOdfs[i].getRne()) 
								&& o.getCodeDiplome().equals(selectedOdfs[i].getCodeDiplome())
								&& o.getCodeVersionDiplome().equals(selectedOdfs[i].getCodeVersionDiplome())
								&& o.getCodeEtape().equals(selectedOdfs[i].getCodeEtape())
								&& o.getCodeVersionEtape().equals(selectedOdfs[i].getCodeVersionEtape())
								&& o.getCodeCentreGestion().equals(selectedOdfs[i].getCodeCentreGestion())
								)
						{
							if (logger.isDebugEnabled())
							{
								logger.debug("o.getDepart()-->"+o.getDepart());
								logger.debug("o.getArrivee()-->"+o.getArrivee());							
								logger.debug("selectedOdfs[i].getDepart()-->"+selectedOdfs[i].getDepart());
								logger.debug("selectedOdfs[i].getArrivee()-->"+selectedOdfs[i].getArrivee());
							}
							if(o.getDepart().equals(selectedOdfs[i].getDepart())
									&& o.getArrivee().equals(selectedOdfs[i].getArrivee()))
								test2=true;
							break;
						}

					}
					if(!test2)
					{
						selectedOdfs[i].setActif(1);
						listFormationsSelectionneMoinsCellesIdentiques.add(selectedOdfs[i]);
						k++;					
					}
				}
				else
				{
					selectedOdfs[i].setActif(1);
					listFormationsSelectionneMoinsCellesIdentiques.add(selectedOdfs[i]);
				}
			}

			OffreDeFormationsDTO[] tabFormationsSelectionneMoinsCellesIdentiques = new OffreDeFormationsDTO[listFormationsSelectionneMoinsCellesIdentiques.size()]; 
			for(int i=0;i<listFormationsSelectionneMoinsCellesIdentiques.size();i++)
			{
				tabFormationsSelectionneMoinsCellesIdentiques[i]=listFormationsSelectionneMoinsCellesIdentiques.get(i);
			}

			OffreDeFormationsDTO[] temp3 = (OffreDeFormationsDTO[]) ArrayUtils.addAll(tabFormationsSelectionneMoinsCellesIdentiques, tabFormationsInactives);
			
			if(!getSessionController().isTransfertsAccueil())
			{
				if (logger.isDebugEnabled())
					logger.debug("if(!getSessionController().isTransfertsAccueil())");			
				for(int i=0; i<temp3.length;i++)
					temp3[i].setArrivee("oui");
			}	
			getDomainService().addOdfs(temp3);		
			
			if(getFiltre()!=null && "coche".equals(getFiltre()))
			{
				if (logger.isDebugEnabled())
					logger.debug("public void addOdfs()-----getFiltre()===>"+getFiltre()+"<===");
				goToSynchroOdf();
			}
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("public void addOdfs()===>decoche<===");
			
			for(int i=0; i<selectedOdfs.length;i++)
			{
				selectedOdfs[i].setActif(1);
			}
			getDomainService().addOdfs(selectedOdfs);
			
			if(getFiltre()!=null && "decoche".equals(getFiltre()))
			{
				if (logger.isDebugEnabled())
					logger.debug("public void addOdfs()-----getFiltre()===>"+getFiltre()+"<===");
				goToSynchroOdf();
			}
		}
		String summary = getString("ENREGISTREMENT.ODF");
		String detail = getString("ENREGISTREMENT.ODF");
		Severity severity=FacesMessage.SEVERITY_INFO;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));			
	}	

	public String goToSynchroOdf() 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("goToSynchroOdf");
			logger.debug("public String goToSynchroOdf()-----getFiltre()===>" + getFiltre() + "<===");
		}
		odfs = new ArrayList<OffreDeFormationsDTO>();
		this.odfDataModel = null;
		this.selectedOdfs = null;
		this.filteredODF = null;

		if(selectedOdfs==null || "coche".equals(getFiltre()));
		{
			List<OffreDeFormationsDTO> list = getDomainService().getSelectedOdfs(getSessionController().getCurrentAnnee(), getSessionController().getRne());
			if(list!=null)
			{
				this.selectedOdfs = new OffreDeFormationsDTO[list.size()];
				for(int i=0;i<list.size();i++)
					this.selectedOdfs[i] = list.get(i);
			}
		}
		list = new ArrayList<OffreDeFormationsDTO>();
		this.setOdfs(this.getOdfs());

		return "goToSynchroOdf";
	}	

	public Logger getLogger() {
		return logger;
	}

	public List<OffreDeFormationsDTO> getOdfs() 
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Recuperation de l'offre de formation a partir du WebService Scolarite");
			logger.debug("public List<OffreDeFormationsDTO> getOdfs()-----getFiltre()===>"+getFiltre()+"<===");
		}
		
		if(list!=null)
			if (logger.isDebugEnabled())
				logger.debug("list===>"+list.size()+"<===");

		if(getFiltre()==null || "toutes".equals(getFiltre()))
		{		
			if (logger.isDebugEnabled())
				logger.debug("getFiltre()===>toutes<===");

			list = getDomainServiceScolarite().getOffreDeFormation(getSessionController().getRne(), getSessionController().getCurrentAnnee());
			if(list!=null)
				nbOdfs=list.size();
			else
				nbOdfs=0;

			if(list!=null && this.selectedOdfs!=null && this.selectedOdfs.length>0)
			{
				nbOdfs=list.size();
				for(int i=0;i<this.selectedOdfs.length;i++)
				{
					for(OffreDeFormationsDTO o : list)
					{
						if(o.getAnnee().equals(this.selectedOdfs[i].getAnnee())
								&& o.getRne().equals(this.selectedOdfs[i].getRne())
								&& o.getCodeDiplome().equals(this.selectedOdfs[i].getCodeDiplome())
								&& o.getCodeVersionDiplome().equals(this.selectedOdfs[i].getCodeVersionDiplome())
								&& o.getCodeEtape().equals(this.selectedOdfs[i].getCodeEtape())
								&& o.getCodeVersionEtape().equals(this.selectedOdfs[i].getCodeVersionEtape())
								&& o.getCodeCentreGestion().equals(this.selectedOdfs[i].getCodeCentreGestion()))
						{
							if (logger.isDebugEnabled())
							{
								logger.debug("######################################TEST EGALITE ODF###############################################");
								logger.debug("o.getAnnee()-->"+o.getAnnee());	
								logger.debug("o.getRne()-->"+o.getRne());	
								logger.debug("o.getCodeDiplome()-->"+o.getCodeDiplome());	
								logger.debug("o.getCodeVersionDiplome()-->"+o.getCodeVersionDiplome());	
								logger.debug("o.getCodeEtape()-->"+o.getCodeEtape());	
								logger.debug("o.getCodeVersionEtape()-->"+o.getCodeVersionEtape());	
								logger.debug("o.getCodeCentreGestion()-->"+o.getCodeCentreGestion());	
							}
							o.setDepart(this.selectedOdfs[i].getDepart());
							o.setArrivee(this.selectedOdfs[i].getArrivee());
						}
					}
				}
			}
			else
				if (logger.isDebugEnabled())
					logger.debug("##########################TEST NULL################################");
		}
		else if(getFiltre()!=null && "coche".equals(getFiltre()))
		{
			if (logger.isDebugEnabled())
				logger.debug("getFiltre()===>coche<===");
			
			list = getDomainService().getSelectedOdfs(getSessionController().getCurrentAnnee(), getSessionController().getRne());
			if(list!=null)
				nbOdfs=list.size();
			else
				nbOdfs=0;
			
			if (logger.isDebugEnabled())
				logger.debug("nbOdfs===>"+nbOdfs+"<===");
		}
		else if(getFiltre()!=null && "decoche".equals(getFiltre()))
		{
			if (logger.isDebugEnabled())
				logger.debug("getFiltre()===>decoche<===");
			List<OffreDeFormationsDTO> listeDecoche = new ArrayList<OffreDeFormationsDTO>();

			list = getDomainServiceScolarite().getOffreDeFormation(getSessionController().getRne(), getSessionController().getCurrentAnnee());
			if(list!=null)
				nbOdfs=list.size();
			else
				nbOdfs=0;

			if(list!=null && this.selectedOdfs!=null && this.selectedOdfs.length>0)
			{
				for(int i=0;i<this.selectedOdfs.length;i++)
				{
					for(OffreDeFormationsDTO o : list)
					{
						if(o.getAnnee().equals(this.selectedOdfs[i].getAnnee())
								&& o.getRne().equals(this.selectedOdfs[i].getRne())
								&& o.getCodeDiplome().equals(this.selectedOdfs[i].getCodeDiplome())
								&& o.getCodeVersionDiplome().equals(this.selectedOdfs[i].getCodeVersionDiplome())
								&& o.getCodeEtape().equals(this.selectedOdfs[i].getCodeEtape())
								&& o.getCodeVersionEtape().equals(this.selectedOdfs[i].getCodeVersionEtape())
								&& o.getCodeCentreGestion().equals(this.selectedOdfs[i].getCodeCentreGestion()))
						{
							if (logger.isDebugEnabled())
							{
								logger.debug("######################################TEST EGALITE ODF###############################################");
								logger.debug("o.getAnnee()-->"+o.getAnnee());	
								logger.debug("o.getRne()-->"+o.getRne());	
								logger.debug("o.getCodeDiplome()-->"+o.getCodeDiplome());	
								logger.debug("o.getCodeVersionDiplome()-->"+o.getCodeVersionDiplome());	
								logger.debug("o.getCodeEtape()-->"+o.getCodeEtape());	
								logger.debug("o.getCodeVersionEtape()-->"+o.getCodeVersionEtape());	
								logger.debug("o.getCodeCentreGestion()-->"+o.getCodeCentreGestion());	
							}
							listeDecoche.add(o);
						}
					}
				}
				list.removeAll(listeDecoche);
				nbOdfs=list.size();
				
				if (logger.isDebugEnabled())
					logger.debug("nbOdfs===>"+nbOdfs+"<===");
			}
			else
				if (logger.isDebugEnabled())
					logger.debug("##########################TEST NULL################################");			
		}
		return list;
	}

	public void setOdfs(List<OffreDeFormationsDTO> odfs) {
		this.odfs = odfs;
	}

	public OffreDeFormationsDTO[] getSelectedOdfs() {
		return selectedOdfs;
	}

	public void setSelectedOdfs(OffreDeFormationsDTO[] selectedOdfs) {
		this.selectedOdfs = selectedOdfs;
	}

	public OdfDataModel getOdfDataModel() {
		if(logger.isDebugEnabled() && odfs!=null)
			logger.debug("################################## odfs ######################################## --> "+odfs.size());
		if(odfs!=null)
		{
			if(logger.isDebugEnabled())
				logger.debug("################################## if(odfs!=null) ########################################");
			if(odfDataModel==null)
			{
				if(logger.isDebugEnabled())
					logger.debug("################################## if(odfDataModel==null) ########################################");
				odfDataModel = new OdfDataModel(odfs);
			}
			return odfDataModel;
		}
		else
		{
			if(logger.isDebugEnabled())
				logger.debug("################################## if(odfs==null) ########################################");
			return new OdfDataModel();
		}
	}

	public void setOdfDataModel(OdfDataModel odfDataModel) {
		this.odfDataModel = odfDataModel;
	}

	public List<String> getListFormationsEnDoublons() {
		return listFormationsEnDoublons;
	}

	public void setListFormationsEnDoublons(List<String> listFormationsEnDoublons) {
		this.listFormationsEnDoublons = listFormationsEnDoublons;
	}

	public FileGeneratorService getFileGeneratorService() {
		return fileGeneratorService;
	}

	public void setFileGeneratorService(FileGeneratorService fileGeneratorService) {
		this.fileGeneratorService = fileGeneratorService;
	}

	public List<OffreDeFormationsDTO> getFilteredODF() {
		return filteredODF;
	}

	public void setFilteredODF(List<OffreDeFormationsDTO> filteredODF) {
		this.filteredODF = filteredODF;
	}

	public String getFiltre() {
		return filtre;
	}

	public void setFiltre(String filtre) {
		this.filtre = filtre;
	}
}