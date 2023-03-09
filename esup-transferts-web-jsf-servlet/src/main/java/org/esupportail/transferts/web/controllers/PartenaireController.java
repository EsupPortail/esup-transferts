package org.esupportail.transferts.web.controllers;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
import org.esupportail.transferts.domain.beans.Parametres;
import org.esupportail.transferts.domain.beans.Versions;
import org.esupportail.transferts.domain.beans.WsPub;
import org.esupportail.transferts.utils.Fonctions;
import org.primefaces.event.RowEditEvent;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("restriction")
public class PartenaireController extends AbstractContextAwareController {

	/**
	 *
	 */
	private static final long serialVersionUID = -1084601237906407867L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(PartenaireController.class);
	private List<WsPub> partenaires;
	private List<WsPub> listePartenaires;
	private WsPub currentPartenaire;
	private WsPub deletePartenaire;

	public PartenaireController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void afterPropertiesSetInternal()
	{
		super.afterPropertiesSetInternal();
	}

	public void addPartenaire()
	{
		currentPartenaire.setAnnee(getSessionController().getCurrentAnnee());
		WsPub part = getDomainService().getWsPubByRneAndAnnee(currentPartenaire.getRne(), getSessionController().getCurrentAnnee());
		if(part!=null)
		{
			String summary = "L'etablissement partenaire existe deja pour l'annee en cours";
			String detail = "L'etablissement partenaire existe deja pour l'annee en cours";
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
		}
		else
		{
			getDomainService().addWsPub(currentPartenaire);
			listePartenaires = null;
			String summary = "Votre nouveau partenaire a bien ete bien enregistre";
			String detail = "Votre nouveau partenaire a bien ete bien enregistre";
			Severity severity=FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));
			currentPartenaire=new WsPub();
		}
	}

	public void updateOdf()
	{
		listePartenaires = null;
		this.getPartenaires();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise a jour reussie", "");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String goToPartenaires()
	{
		if (logger.isDebugEnabled())
			logger.debug("goToPartenaires");
		partenaires = new ArrayList<WsPub>();
		currentPartenaire = new WsPub();
		listePartenaires = null;
		this.getPartenaires();
		return "goToPartenaires";
	}

	public void onEdit(RowEditEvent event) {
		WsPub part = getDomainService().getWsPubByRneAndAnnee(((WsPub) event.getObject()).getRne(), getSessionController().getCurrentAnnee());
		if(part!=null)
		{
			getDomainService().updateWsPub((WsPub) event.getObject());
			if (part.getUrl() != null)
			{
				if (!(part.getRne().equals(getSessionController().getRne())))
				{
					part.setOnline(0);
					part.setSyncOdf(0);
					if (part.getUrl() != null)
					{
						Date d = getDomainService().getDateMaxMajByRneAndAnnee(getSessionController().getCurrentAnnee(), part.getRne());
						if (logger.isDebugEnabled())
							logger.debug("######################### Date Max MAJ ################################" + d);
						if (d != null)
						{
							Object tabReturn[] = Fonctions.appelWSAuth(part.getUrl(),
									part.getIdentifiant(),
									part.getPassword(),
									"org.esupportail.transferts.domain.DomainServiceOpi",
									"getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne",
									"arrayList",
									getSessionController().getTimeOutConnexionWs(),
									d,
									getSessionController().getCurrentAnnee(),
									part.getRne());

							List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
							Integer etatConnexion = (Integer) tabReturn[1];

							if (etatConnexion == 1) {
								part.setOnline(1);
								if (lOdf != null)
								{
									OffreDeFormationsDTO[] tabFormationsMaj = new OffreDeFormationsDTO[lOdf.size()];
									for(int i=0; i<lOdf.size();i++)
										tabFormationsMaj[i]=lOdf.get(i);
									getDomainService().addOdfs(tabFormationsMaj);
									part.setSyncOdf(1);
								} else
								{
									if (logger.isDebugEnabled())
										logger.debug("######################### Auncune Offre de formation a mettre a jour ################################");
									part.setSyncOdf(1);
								}
							}
						}
						else
						{
							Object tabReturn[] = Fonctions.appelWSAuth(part.getUrl(),
									part.getIdentifiant(),
									part.getPassword(),
									"org.esupportail.transferts.domain.DomainServiceOpi",
									"getFormationsByRneAndAnnee",
									"arrayList",
									getSessionController().getTimeOutConnexionWs(),
									part.getRne(),
									getSessionController().getCurrentAnnee());

							List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
							Integer etatConnexion = (Integer) tabReturn[1];

							if (etatConnexion == 1) {
								part.setOnline(1);
								if (lOdf != null)
								{
									OffreDeFormationsDTO[] tabFormationsMaj = new OffreDeFormationsDTO[lOdf.size()];
									for(int i=0; i<lOdf.size();i++)
										tabFormationsMaj[i]=lOdf.get(i);
									getDomainService().addOdfs(tabFormationsMaj);
									part.setSyncOdf(1);
								} else
									part.setSyncOdf(3);
							}
						}
					}
				}
				else
				{
					part.setOnline(1);
					part.setSyncOdf(1);
				}
			}
			else
			{
				part.setOnline(0);
				part.setSyncOdf(0);
			}
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise a jour reussie", ((WsPub) event.getObject()).getRne());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else
		{
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de la mise a jour", ((WsPub) event.getObject()).getRne());
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		if (logger.isDebugEnabled())
			logger.debug("Rechargement du datable...");
		partenaires = new ArrayList<WsPub>();
		listePartenaires = null;
		this.getPartenaires();
	}

	public void onCancel() {
		if(deletePartenaire!=null)
		{
			getDomainService().deleteWsPub(deletePartenaire);
		}
		else
		{
			FacesMessage msg = new FacesMessage("Veuillez contacter l'administrateur pour la suppression", "Suppression PB !!!");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		listePartenaires = null;
		this.getPartenaires();
		deletePartenaire=null;
		FacesMessage msg = new FacesMessage("Veuillez contacter l'administrateur pour la suppression", "Suppression OK !!!");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void setOnCancel(RowEditEvent event) {
		deletePartenaire = getDomainService().getWsPubByRneAndAnnee(((WsPub) event.getObject()).getRne(), getSessionController().getCurrentAnnee());
		FacesMessage msg = new FacesMessage("Veuillez contacter l'administrateur pour la suppression", ((WsPub) event.getObject()).getRne());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Logger getLogger() {
		return logger;
	}

	public boolean testVersionPartenaires(WsPub partenaire){
		boolean testVersion=false;
		Versions versionAppliPartenaires =null;

		Object tabReturn3[] = Fonctions.appelWSAuth(partenaire.getUrl(),
				partenaire.getIdentifiant(),
				partenaire.getPassword(),
				"org.esupportail.transferts.domain.DomainServiceOpi",
				"getVersionByEtat",
				"Object",
				getSessionController().getTimeOutConnexionWs(),
				1);

		if (logger.isDebugEnabled()) {
			logger.debug("partenaire.getLibEtab()==>" + partenaire.getLibEtab() + "<===");
			logger.debug("tabReturn3[0]===>" + tabReturn3[0] + "<===");
			logger.debug("tabReturn3[1]===>" + tabReturn3[1] + "<===");
		}

		Integer etatConnexion3 = (Integer) tabReturn3[1];
		if(etatConnexion3==1) {
			versionAppliPartenaires = (Versions) tabReturn3[0];
			Versions currentVersion = getDomainService().getVersionByEtat(1);
			if(currentVersion!=null && versionAppliPartenaires!=null) {
				if(versionAppliPartenaires.getNumero().equals(currentVersion.getNumero()))
					testVersion = true;
				partenaire.setVersionApplication(versionAppliPartenaires.getNumero());
				getDomainService().updateWsPub(partenaire);
			}else if(versionAppliPartenaires==null){
				partenaire.setVersionApplication("Non disponible");
				getDomainService().updateWsPub(partenaire);
			}
		}else{
			partenaire.setVersionApplication("Non disponible");
			getDomainService().updateWsPub(partenaire);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("versionAppliPartenaires===>" + versionAppliPartenaires + "<===");
			logger.debug("etatConnexion3===>" + etatConnexion3 + "<===");
		}

		return testVersion;
	}

	public List<WsPub> getPartenaires()
	{
		if(listePartenaires==null)
		{
			listePartenaires = getDomainService().getWsPubByAnnee(getSessionController().getCurrentAnnee());
			if(listePartenaires!=null)
			{
				for(WsPub part : listePartenaires)
				{
					part.setOnline(0);
					part.setSyncOdf(0);
					if (part.getUrl() != null) {

						if (this.testVersionPartenaires(part))
						{
							Parametres paramChoixDuVoeuParComposante = null;

							Object tabReturn2[] = Fonctions.appelWSAuth(part.getUrl(),
									part.getIdentifiant(),
									part.getPassword(),
									"org.esupportail.transferts.domain.DomainServiceOpi",
									"getParametreByCode",
									"Object",
									getSessionController().getTimeOutConnexionWs(),
									"choixDuVoeuParComposante");

							if (logger.isDebugEnabled()) {
								logger.debug("tabReturn2[0]===>" + tabReturn2[0] + "<===");
								logger.debug("tabReturn2[1]===>" + tabReturn2[1] + "<===");
							}

							Integer etatConnexion2 = (Integer) tabReturn2[1];
							if (etatConnexion2 == 1) {
								paramChoixDuVoeuParComposante = (Parametres) tabReturn2[0];
								if (part.isChoixDuVoeuParComposante() != paramChoixDuVoeuParComposante.isBool()) {
									part.setChoixDuVoeuParComposante(paramChoixDuVoeuParComposante.isBool());
									getDomainService().updateWsPub(part);
								}
							}

							if (logger.isDebugEnabled()) {
								logger.debug("paramChoixDuVoeuParComposante===>" + paramChoixDuVoeuParComposante + "<===");
								logger.debug("etatConnexion2===>" + etatConnexion2 + "<===");
							}

							Date d = getDomainService().getDateMaxMajByRneAndAnnee(getSessionController().getCurrentAnnee(), part.getRne());
							if (logger.isDebugEnabled())
								logger.debug("######################### Date Max MAJ ################################" + d);

							if (d != null) {
								Object tabReturn[] = Fonctions.appelWSAuth(part.getUrl(),
										part.getIdentifiant(),
										part.getPassword(),
										"org.esupportail.transferts.domain.DomainServiceOpi",
										"getFormationsByMaxDateLocalDifferentDateMaxDistantAndAnneeAndRne",
										"arrayList",
										getSessionController().getTimeOutConnexionWs(),
										d,
										getSessionController().getCurrentAnnee(),
										part.getRne());

								List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
								Integer etatConnexion = (Integer) tabReturn[1];

								if (etatConnexion == 1) {
									part.setOnline(1);
									if (lOdf != null) {
										part.setSyncOdf(2);
									} else
										part.setSyncOdf(1);
								}
							} else {
								Object tabReturn[] = Fonctions.appelWSAuth(part.getUrl(),
										part.getIdentifiant(),
										part.getPassword(),
										"org.esupportail.transferts.domain.DomainServiceOpi",
										"getFormationsByRneAndAnnee",
										"arrayList",
										getSessionController().getTimeOutConnexionWs(),
										part.getRne(),
										getSessionController().getCurrentAnnee());

								List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
								Integer etatConnexion = (Integer) tabReturn[1];

								if (etatConnexion == 1) {
									part.setOnline(1);
									if (lOdf != null) {
										part.setSyncOdf(2);
									} else
										part.setSyncOdf(3);
								}
							}
						}
						else{
							part.setOnline(0);
							part.setSyncOdf(0);
						}
					}
					else
					{
						Parametres p = getDomainService().getParametreByCode("choixDuVoeuParComposante");
						if(p!=null && p.isBool()!=part.isChoixDuVoeuParComposante())
						{
							part.setChoixDuVoeuParComposante(p.isBool());
							getDomainService().updateWsPub(part);
						}else
						{
							part.setChoixDuVoeuParComposante(true);
						}
//						part.setOnline(1);
//						part.setSyncOdf(1);
					}
				}
			}
		}
		return listePartenaires;
	}



	private boolean testUrl(String host) {
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
			conn.setConnectTimeout(getSessionController().getTimeOutConnexionWs());
			conn.connect();
			return conn.getResponseCode() == HttpURLConnection.HTTP_OK;
		} catch (MalformedURLException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("MalformedURLException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return false;
		} catch (IOException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("IOException");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return false;
		}
		catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("Exception");
				logger.debug("host : " + host);
			}
			logger.error(e);
			return false;
		}
	}

	public void setPartenaires(List<WsPub> partenaires) {
		this.partenaires = partenaires;
	}

	public WsPub getCurrentPartenaire() {
		return currentPartenaire;
	}

	public void setCurrentPartenaire(WsPub currentPartenaire) {
		this.currentPartenaire = currentPartenaire;
	}

	public WsPub getDeletePartenaire() {
		return deletePartenaire;
	}

	public void setDeletePartenaire(WsPub deletePartenaire) {
		this.deletePartenaire = deletePartenaire;
	}
}