package org.esupportail.transferts.web.scheduler;

import org.esupportail.commons.services.i18n.ResourceBundleMessageSourceI18nServiceImpl;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.transferts.domain.DomainService;
import org.esupportail.transferts.domain.DomainServiceScolarite;
import org.esupportail.transferts.domain.beans.*;
import org.esupportail.transferts.utils.Fonctions;
import org.esupportail.transferts.utils.GestionDate;
import org.esupportail.transferts.web.controllers.AdministrationController;
import org.esupportail.transferts.web.utils.CompareByComposanteAccueil;
import org.esupportail.transferts.web.utils.CompareByComposanteDepart;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusinessManager {

	private Integer currentAnnee;
	private String currentRne;
	private String currentMail;
	private DomainService domainService;
	private DomainServiceScolarite domainServiceScolarite;
	private ResourceBundleMessageSourceI18nServiceImpl i18nService;
	private SmtpService smtpService;
	private boolean majOdfAutoForScheduler;
	private boolean reloadDemandeTransfertsDepartEchecAutoForScheduler;
	private boolean reloadDemandeTransfertsAccueilEchecAutoForScheduler;
	private Integer timeOutConnexionWs;
	private Integer nbJourAvantAlertSilenceVautAccord;
	private Integer nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	private boolean transfertsAccueil;
	private boolean useRelanceDepartPersonnelConcerneSVA;
	private boolean useRelanceAccueilPersonnelConcerneSVA;
	private boolean useRelanceResumeSVA;
	private Logger logger = new LoggerImpl(getClass());

	public void initController(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void initController()<===");
		CodeSizeAnnee csa = getDomainService().getCodeSizeDefaut();
		setCurrentAnnee(csa.getAnnee());

		WsPub wp = getDomainService().getWsPubByRneAndAnnee(this.getCurrentRne(), this.getCurrentAnnee());
		if(wp!=null && !"null".equalsIgnoreCase(wp.getMailCorrespondantFonctionnel()) && !"".equals(wp.getMailCorrespondantFonctionnel()))
			this.setCurrentMail(wp.getMailCorrespondantFonctionnel());
	}

	public void runAction()
	{
        if(logger.isDebugEnabled())
		    logger.debug("===>Déclenchement du scheduler<===");
		this.initController();

		Parametres maj_odf_auto = getDomainService().getParametreByCode("maj_odf_auto");
		if(maj_odf_auto==null)
			majOdfAutoForScheduler=false;
		else
			majOdfAutoForScheduler=maj_odf_auto.isBool();

		Parametres reload_demande_transferts_depart_echec_auto = getDomainService().getParametreByCode("reload_demande_transferts_depart_echec_auto");
		if(reload_demande_transferts_depart_echec_auto==null)
			reloadDemandeTransfertsDepartEchecAutoForScheduler=false;
		else
			reloadDemandeTransfertsDepartEchecAutoForScheduler=reload_demande_transferts_depart_echec_auto.isBool();

		Parametres reload_demande_transferts_accueil_echec_auto = getDomainService().getParametreByCode("reload_demande_transferts_accueil_echec_auto");
		if(reload_demande_transferts_accueil_echec_auto==null)
			reloadDemandeTransfertsAccueilEchecAutoForScheduler=false;
		else
			reloadDemandeTransfertsAccueilEchecAutoForScheduler=reload_demande_transferts_accueil_echec_auto.isBool();

		Parametres relance_depart_sva = getDomainService().getParametreByCode("relance_depart_sva");
		if(relance_depart_sva==null)
			setUseRelanceDepartPersonnelConcerneSVA(false);
		else
			setUseRelanceDepartPersonnelConcerneSVA(relance_depart_sva.isBool());

		Parametres relance_accueil_sva = getDomainService().getParametreByCode("relance_accueil_sva");
		if(relance_accueil_sva==null)
			setUseRelanceAccueilPersonnelConcerneSVA(false);
		else
			setUseRelanceAccueilPersonnelConcerneSVA(relance_accueil_sva.isBool());

		Parametres relance_resume_sva = getDomainService().getParametreByCode("relance_resume_sva");
		if(relance_resume_sva==null)
			setUseRelanceResumeSVA(false);
		else
			setUseRelanceResumeSVA(relance_resume_sva.isBool());

		if(this.getCurrentAnnee()!=null)
		{
			List<EtudiantRef> lEtuAccueil = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "A");
			List<EtudiantRef> lEtuDepart = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "D");

			if(this.isMajOdfAutoForScheduler())
				this.refreshAllPartenaire();

			if(this.isReloadDemandeTransfertsDepartEchecAutoForScheduler())
				this.reloadDemandeTransfertsDepartEchec("D");

			if(this.isReloadDemandeTransfertsAccueilEchecAutoForScheduler())
				this.reloadFeedBackTransfertsAccueilEchec("A");

			if(isUseRelanceDepartPersonnelConcerneSVA() && lEtuDepart!=null && !lEtuDepart.isEmpty())
				this.envoiMail(lEtuDepart, "D");

			if(this.isTransfertsAccueil() && isUseRelanceAccueilPersonnelConcerneSVA() && lEtuAccueil!=null && !lEtuAccueil.isEmpty())
				this.envoiMail(lEtuAccueil, "A");

			if(this.isUseRelanceResumeSVA() && lEtuDepart!=null && !lEtuDepart.isEmpty()) {
				this.envoiMailResume(lEtuDepart, "D");
				if(this.isTransfertsAccueil() && lEtuAccueil!=null && !lEtuAccueil.isEmpty())
					this.envoiMailResume(lEtuAccueil, "A");
			}


		}
	}

	public void relanceDepartPersonnelConcerneSVAManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void relanceDepartPersonnelConcerneSVAManuelle()<===");
		this.initController();
		if(this.getCurrentAnnee()!=null)
		{
			List<EtudiantRef> lEtuDepart = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "D");
			if(lEtuDepart!=null && !lEtuDepart.isEmpty())
				this.envoiMail(lEtuDepart, "D");
		}
	}

	public void relanceAccueilPersonnelConcerneSVAManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void relanceAccueilPersonnelConcerneSVAManuelle()<===");
		this.initController();
		if(this.getCurrentAnnee()!=null)
		{
			List<EtudiantRef> lEtuAccueil = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "A");
			if(lEtuAccueil!=null && !lEtuAccueil.isEmpty())
				this.envoiMail(lEtuAccueil, "A");
		}
	}

	public void relanceResumeSVAManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void relanceResumeSVAManuelle()<===");
		this.initController();

		if(this.getCurrentAnnee()!=null) {
			List<EtudiantRef> lEtuDepart = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "D");
			if (lEtuDepart != null && !lEtuDepart.isEmpty())
				this.envoiMailResume(lEtuDepart, "D");

			List<EtudiantRef> lEtuAccueil = getDomainService().getAllDemandesTransfertsByAnnee(this.getCurrentAnnee(), "A");
			if(this.isTransfertsAccueil() && lEtuAccueil!=null && !lEtuAccueil.isEmpty())
				this.envoiMailResume(lEtuAccueil, "A");
		}
	}

	public void majOdfManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void majOdfManuelle()<===");
		this.initController();
		if(this.getCurrentAnnee()!=null)
			this.refreshAllPartenaire();
	}

	public void reloadDemandeTransfertsDepartEchecManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void reloadDemandeTransfertsDepartEchecManuelle()<===");
		this.initController();
		if(this.getCurrentAnnee()!=null)
			this.reloadDemandeTransfertsDepartEchec("D");
	}

	public void reloadDemandeTransfertsAccueilEchecManuelle(){
        if (logger.isDebugEnabled())
		    logger.debug("===>public void reloadDemandeTransfertsAccueilEchecManuelle()<===");
		this.initController();
		if(this.getCurrentAnnee()!=null)
			this.reloadFeedBackTransfertsAccueilEchec("A");
	}

	private void envoiMailResume(List<EtudiantRef> lEtu, String source)
	{
        if (logger.isDebugEnabled())
		    logger.debug("===>private void envoiMailResume(List<EtudiantRef> lEtu, String source)<===");
		if(lEtu!=null)
		{
			List<EtudiantRef> listeEtudiantRefAlertSilenceVautAccord = new ArrayList<EtudiantRef>();
			List<EtudiantRef> listeEtudiantRefAlertDepassementSilenceVautAccord = new ArrayList<EtudiantRef>();
			Date now = new Date();
			String sujet;
			String body;

			if (logger.isDebugEnabled())
			    logger.debug("lEtu.size()===>"+lEtu.size()+" / "+source+"<===");

			for(EtudiantRef etu : lEtu)
			{
				if(etu.getTransferts().getTemoinTransfertValide()!=2)
				{
					etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), 2));
					etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 42));
					if(now.after(etu.getAlertSilenceVautAccord()) && now.before(etu.getAlertDepassementSilenceVautAccord()))
					{
						listeEtudiantRefAlertSilenceVautAccord.add(etu);
					}
					if(now.after(etu.getAlertDepassementSilenceVautAccord()))
					{
						listeEtudiantRefAlertDepassementSilenceVautAccord.add(etu);
					}
				}
			}

			if(listeEtudiantRefAlertSilenceVautAccord!=null && !listeEtudiantRefAlertSilenceVautAccord.isEmpty())
			{
				if("A".equals(source))
				{
					Collections.sort(listeEtudiantRefAlertSilenceVautAccord, new CompareByComposanteAccueil());
					sujet = "[transferts accueil] Silence vaut accord (délai de 6 semaines dépassés)";
					body = "Liste des des demande de transferts accueil dépassant le délai des 6 semaines : <BR />\r\n";
				}
				else
				{
					Collections.sort(listeEtudiantRefAlertSilenceVautAccord, new CompareByComposanteDepart());
					sujet = "[transferts départ] Silence vaut accord (délai de 6 semaines dépassés)";
					body = "Liste des des demande de transferts départ dépassant le délai des 6 semaines : <BR />\r\n";
				}

				if (logger.isDebugEnabled())
				    logger.debug("===>############################################ listeEtudiantRefAlertSilenceVautAccord #####################################################################<===");

				String libComp="";
				boolean repeat=false;
				for(EtudiantRef etu : listeEtudiantRefAlertSilenceVautAccord)
				{
					if("D".equals(source))
					{
						if("".equals(libComp))
							libComp=etu.getComposante();
						else if(libComp.equals(etu.getComposante()))
							repeat=true;
						else
						{
							libComp=etu.getComposante();
							repeat=false;
						}
					}
					else
					{
						if("".equals(libComp))
							libComp=etu.getTransferts().getOdf().getCodeComposante();
						else if(libComp.equals(etu.getTransferts().getOdf().getCodeComposante()))
							repeat=true;
						else
						{
							libComp=etu.getTransferts().getOdf().getCodeComposante();
							repeat=false;
						}
					}

					if (logger.isDebugEnabled()){
					    logger.debug("libComp===>"+libComp+"<===");
					    logger.debug("etu.getNumeroIne()===>"+etu.getNumeroIne()+"<===");
					    logger.debug("===>#################################################################################################################<===");
					}

					if(!repeat)
						body+="<BR />\r\n"+libComp+"<BR />\r\n";
					body+=etu.getNomPatronymique()+" - "+etu.getPrenom1()+" ("+etu.getNumeroIne()+")<BR />\r\n";
				}

				try {
					getSmtpService().send(new InternetAddress(this.getCurrentMail()), sujet, body, body);
				}
				catch (AddressException e)
				{
					logger.error("===>Echec envoi de mail<===");
					logger.error(e);
				}
			}
			else
			{
				if (logger.isDebugEnabled()){
				    logger.debug("===>Aucun étudiant<===");
				    logger.debug("===>#################################################################################################################<===");
				}
			}

			if(listeEtudiantRefAlertDepassementSilenceVautAccord!=null && !listeEtudiantRefAlertDepassementSilenceVautAccord.isEmpty())
			{
				if("A".equals(source))
				{
					Collections.sort(listeEtudiantRefAlertDepassementSilenceVautAccord, new CompareByComposanteAccueil());
					sujet = "[transferts accueil] Silence vaut accord (délai des 2 mois dépassés)";
					body = "Liste des des demande de transferts accueil dépassant le délai des 2 mois : <BR /><BR />\r\n\r\n";
				}
				else
				{
					Collections.sort(listeEtudiantRefAlertDepassementSilenceVautAccord, new CompareByComposanteDepart());
					sujet = "[transferts départ] Silence vaut accord (délai des 2 mois dépassés)";
					body = "Liste des des demande de transferts départ dépassant le délai des 2 mois : <BR /><BR />\r\n\r\n";
				}

				if (logger.isDebugEnabled())
				    logger.debug("===>################################################## listeEtudiantRefAlertDepassementSilenceVautAccord ###############################################################<===");

				String libComp="";
				boolean repeat=false;
				for(EtudiantRef etu : listeEtudiantRefAlertDepassementSilenceVautAccord)
				{
					if("D".equals(source))
					{
						if("".equals(libComp))
							libComp=etu.getComposante();
						else if(libComp.equals(etu.getComposante()))
							repeat=true;
						else
						{
							libComp=etu.getComposante();
							repeat=false;
						}
					}
					else
					{
						if("".equals(libComp))
							libComp=etu.getTransferts().getOdf().getCodeComposante();
						else if(libComp.equals(etu.getTransferts().getOdf().getCodeComposante()))
							repeat=true;
						else
						{
							libComp=etu.getTransferts().getOdf().getCodeComposante();
							repeat=false;
						}
					}

					if (logger.isDebugEnabled()){
					    logger.debug("libComp===>"+libComp+"<===");
					    logger.debug("etu.getNumeroIne()===>"+etu.getNumeroIne()+"<===");
					    logger.debug("===>#################################################################################################################<===");
					}

					if(!repeat)
						body+="<BR />\r\n"+libComp+"<BR />\r\n";
					body+=etu.getNomPatronymique()+" - "+etu.getPrenom1()+" ("+etu.getNumeroIne()+")<BR />\r\n";
				}
				try {
					getSmtpService().send(new InternetAddress(this.getCurrentMail()), sujet, body, body);
				}
				catch (AddressException e)
				{
					logger.error("===>Echec envoi de mail<===");
					logger.error(e);
				}
			}
			else
			{
				if (logger.isDebugEnabled()){
				    logger.debug("===>Aucun étudiant<===");
				    logger.debug("===>#################################################################################################################<===");
				}
			}
		}
		else
		{
			if("A".equals(source)){
				if (logger.isDebugEnabled())
					logger.debug("[accueil]===>lEtu.size()===>0<===");
			}
			else {
				if (logger.isDebugEnabled())
					logger.debug("[départ]===>lEtu.size()===>0<===");
			}
		}
	}

	private void envoiMail(List<EtudiantRef> lEtu, String source)
	{
        if (logger.isDebugEnabled())
		    logger.debug("private void envoiMail(List<EtudiantRef> lEtu, String source)===>"+lEtu.size()+" / "+source+"<===");
		if(lEtu!=null)
		{
			List<EtudiantRef> listeEtudiantRefAlertSilenceVautAccord = new ArrayList<EtudiantRef>();
			List<EtudiantRef> listeEtudiantRefAlertDepassementSilenceVautAccord = new ArrayList<EtudiantRef>();
			Set listDestinataires=null;
			Date now = new Date();
			String sujet;
			String body = null;

			if (logger.isDebugEnabled())
			    logger.debug("lEtu.size()===>"+lEtu.size()+" / "+source+"<===");

			for(EtudiantRef etu : lEtu)
			{
				if(etu.getTransferts().getTemoinTransfertValide()!=2)
				{
					etu.setAlertDepassementSilenceVautAccord(GestionDate.ajouterMois(etu.getTransferts().getDateDemandeTransfert(), 2));
					etu.setAlertSilenceVautAccord(GestionDate.ajouterJour(etu.getTransferts().getDateDemandeTransfert(), 42));
					if(now.after(etu.getAlertSilenceVautAccord()) && now.before(etu.getAlertDepassementSilenceVautAccord()))
					{
						listeEtudiantRefAlertSilenceVautAccord.add(etu);
					}
					if(now.after(etu.getAlertDepassementSilenceVautAccord()))
					{
						listeEtudiantRefAlertDepassementSilenceVautAccord.add(etu);
					}
				}
			}

			if(listeEtudiantRefAlertSilenceVautAccord!=null && !listeEtudiantRefAlertSilenceVautAccord.isEmpty())
			{
				if("A".equals(source))
				{
					Collections.sort(listeEtudiantRefAlertSilenceVautAccord, new CompareByComposanteAccueil());
					sujet = "[transferts accueil] Silence vaut accord (délai de 6 semaines dépassés)";
				}
				else
				{
					Collections.sort(listeEtudiantRefAlertSilenceVautAccord, new CompareByComposanteDepart());
					sujet = "[transferts départ] Silence vaut accord (délai de 6 semaines dépassés)";
				}

				if (logger.isDebugEnabled())
				    logger.debug("===>############################################ listeEtudiantRefAlertSilenceVautAccord #####################################################################<===");

				String libComp="";
				boolean repeat=false;
				Integer envoiAlert;
				Integer compteurAlert=1;
				for(EtudiantRef etu : listeEtudiantRefAlertSilenceVautAccord)
				{
					if("D".equals(source))
					{
						if("".equals(libComp)) {
							libComp = etu.getComposante();
							envoiAlert=0;
						}
						else if(libComp.equals(etu.getComposante())) {
							repeat = true;
							envoiAlert=2;
						}
						else
						{
							libComp=etu.getComposante();
							repeat=false;
							envoiAlert=1;
						}
					}
					else
					{
						if("".equals(libComp))
						{
							libComp = etu.getTransferts().getOdf().getCodeComposante();
							envoiAlert=0;
						}
						else if(libComp.equals(etu.getTransferts().getOdf().getCodeComposante())) {
							repeat = true;
							envoiAlert=2;
						}
						else
						{
							libComp=etu.getTransferts().getOdf().getCodeComposante();
							repeat=false;
							envoiAlert=1;
						}
					}

					List<PersonnelComposante> lp;

					if(!repeat) {
						if(envoiAlert==1){
							this.envoiMailMasse(listDestinataires, sujet, body);
						}

						listDestinataires=new HashSet(); // on crée notre Set

						if("A".equals(source))
							body = "Liste des des demande de transferts accueil dépassant le délai des 6 semaines : <BR />\r\n";
						else
							body = "Liste des des demande de transferts départ dépassant le délai des 6 semaines : <BR />\r\n";

						body += "<BR />\r\n" + libComp + "<BR />\r\n";

						lp = getDomainService().getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(source, this.getCurrentAnnee(), libComp);

                        if(logger.isDebugEnabled())
						    logger.debug("lp===>"+lp+"<===");

						if(lp!=null && !lp.isEmpty()) {
							for (PersonnelComposante pc : lp) {
								if ("OUI".equalsIgnoreCase(pc.getAlertMailSva()) && pc.getMailPersonnel()!=null && !"".equals(pc.getMailPersonnel()))
									listDestinataires.add(pc.getMailPersonnel()); // on ajoute des string quelconques // oups, je l'ai déja ajouté, la fonction gère l'exception levée, et l'objet n'est pas ajouté
							}
						}
					}

					body+=etu.getNomPatronymique()+" - "+etu.getPrenom1()+" ("+etu.getNumeroIne()+")<BR />\r\n";

                    if (logger.isDebugEnabled()){
                        logger.debug("libComp===>"+libComp+"<===");
                        logger.debug("etu.getNumeroIne()===>"+etu.getNumeroIne()+"<===");
                        logger.debug("repeat===>"+repeat+"<===");
                        logger.debug("envoiAlert===>"+envoiAlert+"<===");
                        logger.debug("listeEtudiantRefAlertSilenceVautAccord===>"+listeEtudiantRefAlertSilenceVautAccord.size()+"<===");
                        logger.debug("compteurAlert===>"+compteurAlert+"<===");
					}

					if(compteurAlert==listeEtudiantRefAlertSilenceVautAccord.size())
						this.envoiMailMasse(listDestinataires, sujet, body);

					compteurAlert++;
				}
			}
			else
			{
				if (logger.isDebugEnabled()){
				    logger.debug("===>Aucun étudiant<===");
				    logger.debug("===>#################################################################################################################<===");
				}
			}

			if(listeEtudiantRefAlertDepassementSilenceVautAccord!=null && !listeEtudiantRefAlertDepassementSilenceVautAccord.isEmpty())
			{
				if("A".equals(source))
				{
					Collections.sort(listeEtudiantRefAlertDepassementSilenceVautAccord, new CompareByComposanteAccueil());
					sujet = "[transferts accueil] Silence vaut accord (délai des 2 mois dépassés)";
				}
				else
				{
					Collections.sort(listeEtudiantRefAlertDepassementSilenceVautAccord, new CompareByComposanteDepart());
					sujet = "[transferts départ] Silence vaut accord (délai des 2 mois dépassés)";
				}

				if (logger.isDebugEnabled())
				    logger.debug("===>################################################## listeEtudiantRefAlertDepassementSilenceVautAccord ###############################################################<===");

				String libComp="";
				boolean repeat=false;
				Integer envoiDepassement;
				Integer compteurDepassement=1;
				for(EtudiantRef etu : listeEtudiantRefAlertDepassementSilenceVautAccord)
				{
					if("D".equals(source))
					{
						if("".equals(libComp))
						{
							libComp = etu.getComposante();
							envoiDepassement=0;
						}
						else if(libComp.equals(etu.getComposante()))
						{
							repeat = true;
							envoiDepassement=2;
						}
						else
						{
							libComp=etu.getComposante();
							repeat=false;
							envoiDepassement=1;
						}
					}
					else
					{
						if("".equals(libComp))
						{
							libComp = etu.getTransferts().getOdf().getCodeComposante();
							envoiDepassement=0;
						}
						else if(libComp.equals(etu.getTransferts().getOdf().getCodeComposante()))
						{
							repeat = true;
							envoiDepassement=2;
						}
						else
						{
							libComp=etu.getTransferts().getOdf().getCodeComposante();
							repeat=false;
							envoiDepassement=1;
						}
					}

					List<PersonnelComposante> lp;

					if(!repeat) {
						if(envoiDepassement==1){
							this.envoiMailMasse(listDestinataires, sujet, body);
						}

						listDestinataires=new HashSet(); // on crée notre Set

						if("A".equals(source))
							body = "Liste des des demande de transferts accueil dépassant le délai des 2 mois : <BR /><BR />\r\n";
						else
							body = "Liste des des demande de transferts départ dépassant le délai des 2 mois : <BR /><BR />\r\n";

						body += "<BR />\r\n" + libComp + "<BR />\r\n";

						lp = getDomainService().getDroitPersonnelComposanteBySourceAndAnneeAndCodeComposante(source, this.getCurrentAnnee(), libComp);

                        if(logger.isDebugEnabled())
						    logger.debug("lp===>"+lp+"<===");

						if(lp!=null && !lp.isEmpty()) {
							for (PersonnelComposante pc : lp) {
								if ("OUI".equalsIgnoreCase(pc.getAlertMailSva()) && pc.getMailPersonnel()!=null && !"".equals(pc.getMailPersonnel()))
									listDestinataires.add(pc.getMailPersonnel()); // on ajoute des string quelconques // oups, je l'ai déja ajouté, la fonction gère l'exception levée, et l'objet n'est pas ajouté
							}
						}
					}

					body+=etu.getNomPatronymique()+" - "+etu.getPrenom1()+" ("+etu.getNumeroIne()+")<BR />\r\n";

                    if (logger.isDebugEnabled()){
                        logger.debug("libComp===>"+libComp+"<===");
                        logger.debug("etu.getNumeroIne()===>"+etu.getNumeroIne()+"<===");
                        logger.debug("repeat===>"+repeat+"<===");
                        logger.debug("envoiDepassement===>"+envoiDepassement+"<===");
                        logger.debug("listeEtudiantRefAlertSilenceVautAccord===>"+listeEtudiantRefAlertDepassementSilenceVautAccord.size()+"<===");
                        logger.debug("compteurDepassement===>"+compteurDepassement+"<===");
					}

					if(compteurDepassement==listeEtudiantRefAlertDepassementSilenceVautAccord.size())
						this.envoiMailMasse(listDestinataires, sujet, body);

					compteurDepassement++;
				}
			}
			else
			{
				if (logger.isDebugEnabled()){
				    logger.debug("===>Aucun étudiant<===");
				    logger.debug("===>#################################################################################################################<===");
				}
			}
		}
		else
		{
			if("A".equals(source)){
				if (logger.isDebugEnabled())
					logger.debug("[accueil]===>lEtu.size()===>0<===");
			}
			else {
				if (logger.isDebugEnabled())
					logger.debug("[départ]===>lEtu.size()===>0<===");
			}
		}
	}

	private void envoiMailMasse(Set listDestinataires, String sujet, String body)
	{
		try {
			if(listDestinataires!=null) {
				if (logger.isDebugEnabled())
					logger.debug("[listDestinataires.size()===>" + listDestinataires.size() + "<===");

				Iterator i = listDestinataires.iterator(); // on crée un Iterator pour parcourir notre HashSet
				while (i.hasNext()) // tant qu'on a un suivant
				{
					String mail = (String) i.next();
					InternetAddress emailAddr = new InternetAddress(mail);
					getSmtpService().send(emailAddr, sujet, body, body);

					if (logger.isDebugEnabled())
						logger.debug("===>#################################################################################################################<===");
				}
			}else
			{
				if (logger.isDebugEnabled())
					logger.debug("BUSINESSMANAGER - envoiMailMasse(Set listDestinataires, String sujet, String body)===>listDestinataires==null<===");
			}
		}
		catch (AddressException e)
		{
			if (logger.isDebugEnabled())
				logger.error("===>Echec envoi de mail<===");
			logger.error(e);
		}
		catch (Exception ex){
			logger.error("===>Echec envoi de mail<===");
			logger.error(ex);
		}
	}

	private void refreshAllPartenaire()
	{
        if (logger.isDebugEnabled())
		    logger.debug("===>private void refreshAllPartenaire()<===");
		List<WsPub> listePartenaires = getDomainService().getWsPubByAnnee(this.getCurrentAnnee());
		if(listePartenaires!=null)
		{
			for(WsPub part : listePartenaires)
			{
				if (!(part.getRne().equals(this.getCurrentRne())))
				{
					part.setOnline(0);
					part.setSyncOdf(0);
					if (part.getUrl() != null)
					{
						Date d = getDomainService().getDateMaxMajByRneAndAnnee(this.getCurrentAnnee(), part.getRne());
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
									this.getTimeOutConnexionWs(),
									d,
									this.getCurrentAnnee(),
									part.getRne());

							List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
							Integer etatConnexion = (Integer) tabReturn[1];

							if (etatConnexion == 1) {
								part.setOnline(1);
								if(lOdf!=null)
								{
									OffreDeFormationsDTO[] tabFormationsMaj = new OffreDeFormationsDTO[lOdf.size()];
									for(int i=0; i<lOdf.size();i++)
										tabFormationsMaj[i]=lOdf.get(i);
									getDomainService().addOdfs(tabFormationsMaj);
									part.setSyncOdf(1);
								}
								else
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
									this.getTimeOutConnexionWs(),
									part.getRne(),
									this.getCurrentAnnee());

							List<OffreDeFormationsDTO> lOdf = (List<OffreDeFormationsDTO>) tabReturn[0];
							Integer etatConnexion = (Integer) tabReturn[1];

                            if (logger.isDebugEnabled())
							    logger.debug("etatConnexion===>"+etatConnexion+"<===");

							if (logger.isDebugEnabled())
								if(lOdf!=null)
									logger.debug("lOdf.size()===>"+lOdf.size()+"<===");

							if (etatConnexion == 1) {
								part.setOnline(1);
								if(lOdf!=null)
								{
									OffreDeFormationsDTO[] tabFormationsMaj = new OffreDeFormationsDTO[lOdf.size()];
									for(int i=0; i<lOdf.size();i++)
										tabFormationsMaj[i]=lOdf.get(i);
									getDomainService().addOdfs(tabFormationsMaj);
									part.setSyncOdf(1);
								}
								else
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
		}
		else
		{
			if (logger.isDebugEnabled())
				logger.debug("===>Aucun établissement partenaire<===");
		}
	}

	private void reloadDemandeTransfertsDepartEchec(String source) {
		if (logger.isDebugEnabled())
		    logger.debug("reloadDemandeTransfertsDepartEchec===>" + source + "<===");
		List<EtudiantRef> listeEtudiantRef = getDomainService().getAllDemandesTransfertsByAnnee(getCurrentAnnee(), source);

		if(listeEtudiantRef!=null && !listeEtudiantRef.isEmpty())
		{
			for (EtudiantRef etudiant : listeEtudiantRef) {
				if (etudiant.getTransferts().getTemoinOPIWs() != null && etudiant.getTransferts().getTemoinOPIWs() == 2) {

                    if(logger.isDebugEnabled())
					    logger.debug("Etudiants concernés===>" + etudiant.getNumeroIne() + "<===");

					WsPub p = getDomainService().getWsPubByRneAndAnnee(etudiant.getTransferts().getRne(), getCurrentAnnee());

					// Appel du WebService de l'universite d'accueil
					if (p != null) {
						EtudiantRef etu = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSourceSansCorrespondance(etudiant.getNumeroEtudiant(), getCurrentAnnee(), etudiant.getSource());
						if (etu != null) {
							etu.setNumeroEtudiant(etu.getNumeroIne());
							etu.getAdresse().setNumeroEtudiant(etu.getNumeroIne());
							etu.getTransferts().setNumeroEtudiant(etu.getNumeroIne());
							InfosAccueil ia = new InfosAccueil();
						/*Initialisation des variables pour TESTS*/
							//					ia.setNumeroEtudiant(numeroEtudiant);
						/*Fin d'initialisation des variables pour TESTS*/
							ia.setNumeroEtudiant(etu.getNumeroIne());
							ia.setAnnee(etu.getAnnee());

							TrResultatVdiVetDTO sessionsResultats = getDomainServiceScolarite().getSessionsResultats(etudiant.getNumeroEtudiant(), "A");
							TrBac bac = getDomainServiceScolarite().getBaccalaureat(etudiant.getNumeroEtudiant());
							TrInfosAdmEtu trInfosAdmEtu = getDomainServiceScolarite().getInfosAdmEtu(etudiant.getNumeroEtudiant());
							TrEtablissementDTO trEtablissementDTO = getDomainServiceScolarite().getEtablissementByRne(getCurrentRne());

							List<SituationUniversitaire> listeSituationUniversitaire = new ArrayList<SituationUniversitaire>();
							if (!sessionsResultats.getEtapes().isEmpty()) {
								int i = 0;
								for (ResultatEtape re : sessionsResultats.getEtapes()) {
									if (logger.isDebugEnabled())
										logger.debug("re.getLibEtape() : " + re.getLibEtape());
									boolean test = true;

									for (ResultatSession rs : re.getSession()) {
										if (logger.isDebugEnabled()) {
											logger.debug("re.getSession().size() : " + re.getSession().size());
											logger.debug("re.getSession() : " + re.getSession());
										}

										if (rs.getResultat() != null && !"".equals(rs.getResultat())) {
											test = false;
											SituationUniversitaire su = new SituationUniversitaire();
											String timestamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
											su.setId(etu.getNumeroIne() + "_" + timestamp + "_P" + i);
											i++;
											su.setLibAccueilAnnee(re.getAnnee());
											su.setLibelle(re.getLibEtape());
											su.setLibAccueilResultat(rs.getLibSession() + " - " + rs.getResultat());
											Integer idAccueilAnnee = 0;
											AccueilAnnee aa = getDomainService().getAccueilAnneeByIdAccueilAnnee(idAccueilAnnee);
											Integer idAccueilResultat = 0;
											AccueilResultat ar = getDomainService().getAccueilResultatByIdAccueilResultat(idAccueilResultat);
											su.setAnnee(aa);
											su.setResultat(ar);
											listeSituationUniversitaire.add(su);
										}
									}
									if (test) {
										SituationUniversitaire su = new SituationUniversitaire();
										String timestamp = new SimpleDateFormat("yyyymmddhhmmss").format(new Date());
										su.setId(etu.getNumeroIne() + "_" + timestamp + "_P" + i);
										i++;
										su.setLibAccueilAnnee(re.getAnnee());
										su.setLibelle(re.getLibEtape());
										su.setLibAccueilResultat("");
										Integer idAccueilAnnee = 0;
										AccueilAnnee aa = getDomainService().getAccueilAnneeByIdAccueilAnnee(idAccueilAnnee);
										Integer idAccueilResultat = 0;
										AccueilResultat ar = getDomainService().getAccueilResultatByIdAccueilResultat(idAccueilResultat);
										su.setAnnee(aa);
										su.setResultat(ar);
										listeSituationUniversitaire.add(su);
									}
								}
							}
							ia.setSituationUniversitaire(listeSituationUniversitaire);
							ia.setFrom_source("P");
							if (bac != null) {
								ia.setAnneeBac(bac.getAnneeObtentionBac());
								ia.setCodeBac(bac.getCodeBac());
							}
							if (trInfosAdmEtu != null)
								ia.setCodePaysNat(trInfosAdmEtu.getCodPayNat());
							ia.setCodeRneUnivDepart(trEtablissementDTO.getCodeEtb());
							ia.setCodeDepUnivDepart(trEtablissementDTO.getCodeDep());
							ia.setValidationOuCandidature(0);
							etu.setAccueil(ia);
							etu.setSource("A");
							etu.getTransferts().setFichier(null);
							etu.getTransferts().setRne(p.getRne());
							etu.getTransferts().setTemoinTransfertValide(0);
							etu.getTransferts().setTemoinOPIWs(null);
							etu.getTransferts().getOdf().setRne(p.getRne());
							etu.setCorrespondances(null);

							Object tabReturn[] = Fonctions.appelWSAuth(p.getUrl(),
									p.getIdentifiant(),
									p.getPassword(),
									"org.esupportail.transferts.domain.DomainServiceOpi",
									"addTransfertOpiToListeTransfertsAccueil",
									"object",
									getTimeOutConnexionWs(),
									etu);

							Integer etatConnexion = (Integer) tabReturn[1];

							if (etatConnexion == 1) {
								etudiant.getTransferts().setTemoinTransfertValide(2);
								etudiant.getTransferts().setTemoinOPIWs(1);
							} else {
								etudiant.getTransferts().setTemoinOPIWs(2);
								etudiant.getTransferts().setTemoinTransfertValide(2);
							}
						} else {
							if (logger.isDebugEnabled())
								logger.debug("Aucun etudiant corresondant a l'INE suivant : " + etudiant.getNumeroIne());
						}
						if (logger.isDebugEnabled())
							logger.debug("getDomainServiceWS : " + p);
					} else {
						etudiant.getTransferts().setTemoinOPIWs(0);
					}
					getDomainService().addDemandeTransferts(etudiant);
				}
			}
		}
	}

	private void reloadFeedBackTransfertsAccueilEchec(String source){
        if (logger.isDebugEnabled())
		    logger.debug("reloadFeedBackTransfertsAccueilEchec===>"+source+"<===");

		List<EtudiantRef> listeEtudiantRef = getDomainService().getAllDemandesTransfertsByAnnee(getCurrentAnnee(), source);

		if(listeEtudiantRef!=null && !listeEtudiantRef.isEmpty()) {
			for (EtudiantRef etudiant : listeEtudiantRef) {
				if (etudiant.getTransferts().getTemoinOPIWs() != null && etudiant.getTransferts().getTemoinOPIWs() == 2) {
					/**
					 * Temoin de retour du transfert accueil
					 * 0 Pas de retour  de l'universite d'accueil
					 * 1 Retour transfert accepté par l'universite d'accueil
					 * 2 Retour transfert refusé par l'universite d'accueil
					 */

                    if(logger.isDebugEnabled())
					    logger.debug("Etudiants concernés===>" + etudiant.getNumeroIne() + "<===");

					Integer feedBackDecision = 0;
					Set<AccueilDecision> lAd = etudiant.getAccueilDecision();
					String decision = "";

					long tableau[] = new long[lAd.size()];
					int i = 0;

					if (logger.isDebugEnabled())
						logger.debug("lAd.size() -->" + lAd.size());

					for (AccueilDecision ad : lAd) {
						tableau[i] = ad.getId();
						i++;
					}

					Arrays.sort(tableau);

					long id = tableau[tableau.length - 1];

					for (int j = 0; j < tableau.length; j++) {
						if (tableau[j] == 0)
							id = 0;
					}

					for (AccueilDecision ad : lAd) {
						if (logger.isDebugEnabled()) {
							logger.debug("2--ad.getEtudiant().getNomPatronymique()===>" + ad.getEtudiant().getNomPatronymique() + "<===");
							logger.debug("2--ad.getId()===>" + ad.getId() + "<===");
							logger.debug("2--ad.getAvis()===>" + ad.getAvis() + "<===");
						}

						if (ad.getId() == id) {
							if (logger.isDebugEnabled()) {
								logger.debug("3--tableau[tableau.length-1]===>" + tableau[tableau.length - 1] + "<===");
								logger.debug("3--ad.getId()===>" + ad.getId() + "<===");
								logger.debug("3--ad.getAvis()===>" + ad.getAvis() + "<===");
							}

							if ("A".equals(ad.getAvis())) {
								decision = "F";
								feedBackDecision = 1;

							} else if ("B".equals(ad.getAvis())) {
								decision = "D";
								feedBackDecision = 2;
							} else {
								decision = "A";
							}
						}
					}

					if (!"A".equals(decision))
					{
						WsPub p = getDomainService().getWsPubByRneAndAnnee(etudiant.getAccueil().getCodeRneUnivDepart(), getCurrentAnnee());
						if (p != null) {
							// Appel du WebService de l'universite de départ
							if (p != null) {
								Integer etatConnexion;
								Object tabReturn[] = Fonctions.appelWSAuth(p.getUrl(),
										p.getIdentifiant(),
										p.getPassword(),
										"org.esupportail.transferts.domain.DomainServiceOpi",
										"addFeedBackFromTransfertAccueilToTransfertDepart",
										"object",
										getTimeOutConnexionWs(),
										etudiant.getNumeroIne(),
										getCurrentAnnee(),
										"D",
										feedBackDecision);

								Integer ret = 2;
								etatConnexion = (Integer) tabReturn[1];

								if (etatConnexion == 1 && tabReturn[0] != null)
									ret = (Integer) tabReturn[0];

                                if (logger.isDebugEnabled()) {
                                    logger.debug("etatConnexion===>" + etatConnexion + "<===");
                                    logger.debug("ret===>" + ret + "<===");
                                }
								etudiant.getTransferts().setTemoinOPIWs(ret);
								getDomainService().addDemandeTransferts(etudiant);

							}
						}
					}
				}
			}
		}
	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public ResourceBundleMessageSourceI18nServiceImpl getI18nService() {
		return i18nService;
	}

	public void setI18nService(ResourceBundleMessageSourceI18nServiceImpl i18nService) {
		this.i18nService = i18nService;
	}

	public SmtpService getSmtpService() {
		return smtpService;
	}

	public void setSmtpService(SmtpService smtpService) {
		this.smtpService = smtpService;
	}

	public Integer getTimeOutConnexionWs() {
		return timeOutConnexionWs;
	}

	public void setTimeOutConnexionWs(Integer timeOutConnexionWs) {
		this.timeOutConnexionWs = timeOutConnexionWs;
	}

	public Integer getCurrentAnnee() {
		return currentAnnee;
	}

	public void setCurrentAnnee(Integer currentAnnee) {
		this.currentAnnee = currentAnnee;
	}

	public Integer getNbJourAvantAlertSilenceVautAccord() {
		return nbJourAvantAlertSilenceVautAccord;
	}

	public void setNbJourAvantAlertSilenceVautAccord(
			Integer nbJourAvantAlertSilenceVautAccord) {
		this.nbJourAvantAlertSilenceVautAccord = nbJourAvantAlertSilenceVautAccord;
	}

	public Integer getNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord() {
		return nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	}

	public void setNbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord(
			Integer nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord) {
		this.nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord = nbMoisAvantAccordSuiteNouvelleLoiSilenceVautAccord;
	}

	public String getCurrentRne() {
		return currentRne;
	}

	public void setCurrentRne(String currentRne) {
		this.currentRne = currentRne;
	}

	public boolean isMajOdfAutoForScheduler() {
		return majOdfAutoForScheduler;
	}

	public void setMajOdfAutoForScheduler(boolean majOdfAutoForScheduler) {
		this.majOdfAutoForScheduler = majOdfAutoForScheduler;
	}

	public String getCurrentMail() {
		return currentMail;
	}

	public void setCurrentMail(String currentMail) {
		this.currentMail = currentMail;
	}

	public boolean isTransfertsAccueil() {
		return transfertsAccueil;
	}

	public void setTransfertsAccueil(boolean transfertsAccueil) {
		this.transfertsAccueil = transfertsAccueil;
	}

	public boolean isReloadDemandeTransfertsDepartEchecAutoForScheduler() {
		return reloadDemandeTransfertsDepartEchecAutoForScheduler;
	}

	public void setReloadDemandeTransfertsDepartEchecAutoForScheduler(boolean reloadDemandeTransfertsDepartEchecAutoForScheduler) {
		this.reloadDemandeTransfertsDepartEchecAutoForScheduler = reloadDemandeTransfertsDepartEchecAutoForScheduler;
	}

	public DomainServiceScolarite getDomainServiceScolarite() {
		return domainServiceScolarite;
	}

	public void setDomainServiceScolarite(DomainServiceScolarite domainServiceScolarite) {
		this.domainServiceScolarite = domainServiceScolarite;
	}

	public boolean isReloadDemandeTransfertsAccueilEchecAutoForScheduler() {
		return reloadDemandeTransfertsAccueilEchecAutoForScheduler;
	}

	public void setReloadDemandeTransfertsAccueilEchecAutoForScheduler(boolean reloadDemandeTransfertsAccueilEchecAutoForScheduler) {
		this.reloadDemandeTransfertsAccueilEchecAutoForScheduler = reloadDemandeTransfertsAccueilEchecAutoForScheduler;
	}

	public boolean isUseRelanceResumeSVA() {
		return useRelanceResumeSVA;
	}

	public void setUseRelanceResumeSVA(boolean useRelanceResumeSVA) {
		this.useRelanceResumeSVA = useRelanceResumeSVA;
	}

	public boolean isUseRelanceDepartPersonnelConcerneSVA() {
		return useRelanceDepartPersonnelConcerneSVA;
	}

	public void setUseRelanceDepartPersonnelConcerneSVA(boolean useRelanceDepartPersonnelConcerneSVA) {
		this.useRelanceDepartPersonnelConcerneSVA = useRelanceDepartPersonnelConcerneSVA;
	}

	public boolean isUseRelanceAccueilPersonnelConcerneSVA() {
		return useRelanceAccueilPersonnelConcerneSVA;
	}

	public void setUseRelanceAccueilPersonnelConcerneSVA(boolean useRelanceAccueilPersonnelConcerneSVA) {
		this.useRelanceAccueilPersonnelConcerneSVA = useRelanceAccueilPersonnelConcerneSVA;
	}
}

