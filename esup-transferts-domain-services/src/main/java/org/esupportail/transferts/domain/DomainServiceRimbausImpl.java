///**
// * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
// */
//package org.esupportail.transferts.domain;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Vector;
//
//import org.esupportail.commons.services.logging.Logger;
//import org.esupportail.commons.services.logging.LoggerImpl;
//import org.esupportail.transferts.domain.beans.AdresseRef;
//import org.esupportail.transferts.domain.beans.CGE;
//import org.esupportail.transferts.domain.beans.EtudiantRef;
//import org.esupportail.transferts.domain.beans.IndOpi;
//import org.esupportail.transferts.domain.beans.OffreDeFormationsDTO;
//import org.esupportail.transferts.domain.beans.PersonnelComposante;
//import org.esupportail.transferts.domain.beans.ResultatEtape;
//import org.esupportail.transferts.domain.beans.ResultatSession;
//import org.esupportail.transferts.domain.beans.TrBac;
//import org.esupportail.transferts.domain.beans.TrBlocageDTO;
//import org.esupportail.transferts.domain.beans.TrCommuneDTO;
//import org.esupportail.transferts.domain.beans.TrDepartementDTO;
//import org.esupportail.transferts.domain.beans.TrEtablissementDTO;
//import org.esupportail.transferts.domain.beans.TrInfosAdmEtu;
//import org.esupportail.transferts.domain.beans.TrPaysDTO;
//import org.esupportail.transferts.domain.beans.TrResultatVdiVetDTO;
//import org.esupportail.transferts.domain.beans.Transferts;
//import org.esupportail.transferts.domain.beans.VoeuxIns;
//
//import rimbaustransfert.acces.RimbausTransfertAcces;
//import rimbaustransfert.data.Bac;
//import rimbaustransfert.data.Commune;
//import rimbaustransfert.data.Composante;
//import rimbaustransfert.data.Departement;
//import rimbaustransfert.data.Etablissement;
//import rimbaustransfert.data.OffreFormation;
//import rimbaustransfert.data.Pays;
//import rimbaustransfert.etudiant.Adresse;
//import rimbaustransfert.etudiant.CodeLibelle;
//import rimbaustransfert.etudiant.Coordonnees;
//import rimbaustransfert.etudiant.IdentifiantEtudiant;
//import rimbaustransfert.etudiant.IndBac;
//import rimbaustransfert.etudiant.InfoAdmEtu;
//
///**
// * @author Eric Messeant (Universite de Lille 1) - 2011
// *
// */
//public class DomainServiceRimbausImpl implements DomainServiceScolarite {
//
//	/**
//	 *
//	 */
//	private String user;
//
//	private String password;
//
//	private DomainServiceScolarite dssApo;
//
//	private static final long serialVersionUID = 1L;
//	private List<String> forcerBlocageListSplit = new ArrayList<String>();
//	private static final String codeComposanteInconnue = "N.A";
//
//	/**
//	 * For Logging.
//	 */
//	private static final Logger logger = new LoggerImpl(DomainServiceRimbausImpl.class);
//	//private Transferts transfert;
//
//	private transient RimbausTransfertAcces rta;
//
//	/**
//	 * Constructor.
//	 */
//	public DomainServiceRimbausImpl() {
//		super();
//		rta = new RimbausTransfertAcces();
//	}
//
//	public DomainServiceRimbausImpl(List<String> forcerBlocageListSplit) {
//		this.forcerBlocageListSplit=forcerBlocageListSplit;
//		rta = new RimbausTransfertAcces();
//	}
//
//	public DomainServiceRimbausImpl(List<String> forcerBlocageListSplit, String user, String password) {
//		this.forcerBlocageListSplit=forcerBlocageListSplit;
//		rta = new RimbausTransfertAcces();
//		this.user=user;
//		this.password=password;
//		dssApo= new DomainServiceApogeeImpl(this.forcerBlocageListSplit, user, password);
//	}
//
//
//
//	@Override
//	public EtudiantRef getCurrentEtudiant(String supannEtuId) {
//		EtudiantRef etudiant = new EtudiantRef();
//		AdresseRef adresse = new AdresseRef();
//		Transferts transfert = new Transferts();
//		if (logger.isDebugEnabled()) {
//			logger.debug("Je suis dans le WS RIMBAUS");
//		}
//		etudiant.setFrom("WSRIMBAUS");
//		// etudiant.setEduPersonAffiliation(eduPersonAffiliation);
//		etudiant.setNumeroEtudiant(supannEtuId);
//
//		// Recuperation des infos de l'etudiant dans Apogee
//		InfoAdmEtu infoAdmEtuDTO;
//		try {
//			infoAdmEtuDTO = rta.recupererInfosAdmEtu(etudiant
//					.getNumeroEtudiant());
//			CodeLibelle[] listeBlocagesDTO = infoAdmEtuDTO.getListeBlocages();
//			Coordonnees coordonneesDTO = rta.recupererAdressesEtudiant(etudiant
//					.getNumeroEtudiant());
//			Adresse adresseFixe = coordonneesDTO.getAdresseFixe();
//			Commune communeDTO = adresseFixe.getCommune();
//			Pays paysDTO = adresseFixe.getPays();
//			Pays nationaliteDTO = infoAdmEtuDTO.getNationalite();
//			//IndBac[] IndBacDTO = infoAdmEtuDTO.getListeBacs();
//
//			if (listeBlocagesDTO != null) {
//				if (listeBlocagesDTO.length == 0) {
//					etudiant.setInterdit(false);
//					if (logger.isDebugEnabled()) {
//						logger.debug("Interdit a FALSE");
//					}
//				} else if (listeBlocagesDTO.length > 0) {
//					etudiant.setInterdit(true);
//					// etudiant.setListeBlocagesDTO(listeBlocagesDTO);
//					for (CodeLibelle b : listeBlocagesDTO) {
//						etudiant.getListeBlocagesDTO().add(
//								new TrBlocageDTO(b.getCode(), b.getLibelle()));
//					}
//					if (logger.isDebugEnabled()) {
//						logger.debug("Interdit a TRUE");
//					}
//				}
//			} else {
//				etudiant.setInterdit(false);
//				if (logger.isDebugEnabled()) {
//					logger.debug("Interdit a FALSE");
//				}
//			}
//
//			// Etat civil
//			etudiant.setNumeroIne(infoAdmEtuDTO.getNumeroINE() + ""
//					+ infoAdmEtuDTO.getCleINE());
//			etudiant.setNomPatronymique(infoAdmEtuDTO.getNomPatronymique());
//			etudiant.setNomUsuel(infoAdmEtuDTO.getNomUsuel());
//			etudiant.setPrenom1(infoAdmEtuDTO.getPrenom1());
//			etudiant.setPrenom2(infoAdmEtuDTO.getPrenom2());
//			etudiant.setDateNaissance(infoAdmEtuDTO.getDateNaissance()
//					.getTime());
//			etudiant.setLibNationalite(nationaliteDTO.getLibNat());
//
//			// Adresse
//			adresse.setNumeroEtudiant(etudiant.getNumeroEtudiant());
//			adresse.setLibAd1(adresseFixe.getLibAd1());
//			adresse.setLibAd2(adresseFixe.getLibAd2());
//			adresse.setLibAd3(adresseFixe.getLibAd3());
//			adresse.setNumTelPortable(coordonneesDTO.getNumTelPortable());
//			adresse.setNumTel(adresseFixe.getNumTel());
//			adresse.setEmail(coordonneesDTO.getEmail());
//			if("100".equals(paysDTO.getCodePay()))
//			{
//				adresse.setCodePostal(communeDTO.getCodePostal());
//				adresse.setCodeCommune(communeDTO.getCodeCommune());
//				adresse.setCodPay(paysDTO.getCodePay());
//				adresse.setLibPay(paysDTO.getLibPay());
//			}
//			else
//			{
//				adresse.setCodePostal("");
//				adresse.setCodeCommune("");
//				adresse.setCodPay(paysDTO.getCodePay());
//				adresse.setLibPay(paysDTO.getLibPay());
//			}
//
//			transfert.setNumeroEtudiant(etudiant.getNumeroEtudiant());
//			etudiant.setAdresse(adresse);
//			etudiant.setTransferts(transfert);
//
//			return etudiant;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public EtudiantRef getCurrentEtudiantIne(String ine, Date dateNaissance) {
//		EtudiantRef etudiant = new EtudiantRef();
//		AdresseRef adresse = new AdresseRef();
//		Transferts transfert = new Transferts();
//		// appel au WS RIMBAUS
//		if (logger.isDebugEnabled()) {
//			logger.debug("Je suis dans le WS RIMBAUS - AUTH Apogee");
//		}
//		etudiant.setFrom("WSRIMBAUS");
//
//		/*
//		 * String ineSansCle = ine.substring(0, ine.length()-1); String cleIne =
//		 * ine.substring(ine.length()-1, ine.length());
//		 */
//
//		if (logger.isDebugEnabled()) {
//			/*
//			 * logger.debug("ineSansCle --> "+ ineSansCle);
//			 * logger.debug("cleIne --> "+ cleIne);
//			 */
//			logger.debug("Ine --> " + ine);
//			logger.debug("dateNaissance --> " + dateNaissance);
//		}
//		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//
//		// Recuperation des infos de l'etudiant dans Apogee
//		InfoAdmEtu infoAdmEtuDTO;
//		try {
//			if (logger.isDebugEnabled()) {
//				logger.debug("ine --> " + ine);
//			}
//			IdentifiantEtudiant identifiantEtudiant = rta
//					.recupererIdentifiantsEtudiantByINE(ine);
//			if( identifiantEtudiant==null)
//			{
//				logger.debug("ine invalide  on inconnue--> ");
//				return null;
//			}
//
//			infoAdmEtuDTO = rta.recupererInfosAdmEtu(identifiantEtudiant.getCodeEtu());
//
//			logger.debug("infoAdmEtuDTO --> " + infoAdmEtuDTO);
//			logger.debug("dateNaissance --> " + dateNaissance);
//			logger.debug("dateNaissance 2 --> "
//					+ infoAdmEtuDTO.getDateNaissance().getTime());
//
//			if (dateFormat.format(dateNaissance).equals(
//					dateFormat.format(infoAdmEtuDTO.getDateNaissance()
//							.getTime()))) {
//				if (logger.isDebugEnabled()) {
//					logger.debug("Compare date OK");
//				}
//				CodeLibelle[] listeBlocagesDTO = infoAdmEtuDTO
//						.getListeBlocages();
//				Coordonnees coordonneesDTO = rta
//						.recupererAdressesEtudiant(infoAdmEtuDTO.getNumEtu()
//								.toString());
//				Adresse adresseFixe = coordonneesDTO.getAdresseFixe();
//				Commune communeDTO = adresseFixe.getCommune();
//				Pays paysDTO = adresseFixe.getPays();
//				Pays nationaliteDTO = infoAdmEtuDTO.getNationalite();
//
//				if (listeBlocagesDTO != null) {
//					if (listeBlocagesDTO.length == 0) {
//						etudiant.setInterdit(false);
//						if (logger.isDebugEnabled()) {
//							logger.debug("Interdit a FALSE");
//						}
//					} else if (listeBlocagesDTO.length > 0) {
//						etudiant.setInterdit(true);
//						for (CodeLibelle b : listeBlocagesDTO) {
//							etudiant.getListeBlocagesDTO().add(
//									new TrBlocageDTO(b.getCode(), b
//											.getLibelle()));
//						}
//						if (logger.isDebugEnabled()) {
//							logger.debug("Interdit a TRUE");
//						}
//					}
//				} else {
//					etudiant.setInterdit(false);
//					if (logger.isDebugEnabled()) {
//						logger.debug("Interdit a FALSE");
//					}
//				}
//
//				// Etat civil
//				etudiant.setNumeroEtudiant(infoAdmEtuDTO.getNumEtu().toString());
//				etudiant.setNumeroIne(infoAdmEtuDTO.getNumeroINE() + ""
//						+ infoAdmEtuDTO.getCleINE());
//				etudiant.setNomPatronymique(infoAdmEtuDTO.getNomPatronymique());
//				etudiant.setNomUsuel(infoAdmEtuDTO.getNomUsuel());
//				etudiant.setPrenom1(infoAdmEtuDTO.getPrenom1());
//				etudiant.setPrenom2(infoAdmEtuDTO.getPrenom2());
//				etudiant.setDateNaissance(infoAdmEtuDTO.getDateNaissance()
//						.getTime());
//				etudiant.setLibNationalite(nationaliteDTO.getLibNat());
//
//				// Adresse
//				adresse.setNumeroEtudiant(etudiant.getNumeroEtudiant());
//				adresse.setLibAd1(adresseFixe.getLibAd1());
//				adresse.setLibAd2(adresseFixe.getLibAd2());
//				adresse.setLibAd3(adresseFixe.getLibAd3());
//				adresse.setNumTelPortable(coordonneesDTO.getNumTelPortable());
//				adresse.setNumTel(adresseFixe.getNumTel());
//				adresse.setEmail(coordonneesDTO.getEmail());
//				if("100".equals(paysDTO.getCodePay()))
//				{
//					adresse.setCodePostal(communeDTO.getCodePostal());
//					adresse.setCodeCommune(communeDTO.getCodeCommune());
//					adresse.setCodPay(paysDTO.getCodePay());
//					adresse.setLibPay(paysDTO.getLibPay());
//				}
//				else
//				{
//					adresse.setCodePostal("");
//					adresse.setCodeCommune("");
//					adresse.setCodPay(paysDTO.getCodePay());
//					adresse.setLibPay(paysDTO.getLibPay());
//				}
//
//				// Transferts
//				transfert.setNumeroEtudiant(etudiant.getNumeroEtudiant());
//				etudiant.setAdresse(adresse);
//				etudiant.setTransferts(transfert);
//				return etudiant;
//			} else {
//				if (logger.isDebugEnabled()) {
//					logger.debug("Compare date FAUX !!!");
//				}
//				etudiant = null;
//			}
//			return etudiant;
//
//		} catch (Exception e) {
//			logger.debug("erreur WS");
//			logger.trace(e);
//			return null;
//		}
//	}
//
//	public List<TrCommuneDTO> getCommunes(String codePostal) {
//		// appel au WS RIMBAUS
//		List<TrCommuneDTO> listTrCommuneDTO = null;
//		Commune[] listeCommunes;
//		try {
//			listeCommunes = rta.recupererCommune(codePostal, null, null);
//			if (listeCommunes.length > 0) {
//				listTrCommuneDTO = new ArrayList<TrCommuneDTO>();
//				for (int i = 0; i < listeCommunes.length; i++)
//					listTrCommuneDTO
//							.add(new TrCommuneDTO(listeCommunes[i]
//									.getCodeCommune(), listeCommunes[i]
//									.getLibCommune()));
//			}
//		} catch (Exception e) {
//			logger.error(e);
//			listTrCommuneDTO = null;
//		}
//		return listTrCommuneDTO;
//	}
//
//	@Override
//	public List<TrPaysDTO> getListePays() {
//		// appel au WS RIMBAUS
//		List<TrPaysDTO> listTrPaysDTO = null;
//		Pays[] listePays;
//		try {
//			listePays = rta.recupererPays(null, null);
//			if (listePays.length > 0) {
//				listTrPaysDTO = new ArrayList<TrPaysDTO>();
//				for (int i = 0; i < listePays.length; i++)
//					listTrPaysDTO.add(new TrPaysDTO(listePays[i].getCodePay(),
//							listePays[i].getLibPay(),listePays[i].getLibNat()));
//			}
//		} catch (Exception e) {
//			logger.error(e);
//			listTrPaysDTO = null;
//		}
//		return listTrPaysDTO;
//	}
//
//	@Override
//	public TrPaysDTO getPaysByCodePays(String codePays) {
//		// appel au WS RIMBAUS
//		TrPaysDTO trPaysDTO = null;
//		Pays[] pays;
//		try {
//			pays = rta.recupererPays(codePays, null);
//			trPaysDTO = new TrPaysDTO(pays[0].getCodePay(), pays[0].getLibPay());
//
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		return trPaysDTO;
//	}
//
//	public List<TrDepartementDTO> getListeDepartements() {
//		// appel au WS RIMBAUS
//		List<TrDepartementDTO> listTrDepartementDTO = null;
//		Departement[] listeDepartements;
//		try {
//			listeDepartements = rta.recupererDepartement(null, null);
//			if (listeDepartements.length > 0) {
//				listTrDepartementDTO = new ArrayList<TrDepartementDTO>();
//				for (int i = 0; i < listeDepartements.length; i++)
//					listTrDepartementDTO.add(new TrDepartementDTO(
//							listeDepartements[i].getCodeDept(),
//							listeDepartements[i].getLibDept()));
//			}
//		} catch (Exception e) {
//			logger.error(e);
//			listTrDepartementDTO = null;
//		}
//		return listTrDepartementDTO;
//	}
//
//	public List<TrEtablissementDTO> getListeEtablissements(
//			String typeEtablissement, String dept) {
//		// appel au WS RIMBAUS
//
//		logger.info("+++++ getListeEtablissements "+typeEtablissement+","+dept);
//
//		List<TrEtablissementDTO> listTrEtablissementDTO = null;
//		Etablissement[] listeEtablissements;
//		try {
//			listeEtablissements = rta.recupererEtablissement(typeEtablissement,
//					null, dept, "1");
//			if (listeEtablissements.length > 0) {
//				listTrEtablissementDTO = new ArrayList<TrEtablissementDTO>();
//				for (int i = 0; i < listeEtablissements.length; i++)
//					listTrEtablissementDTO.add(new TrEtablissementDTO(
//							listeEtablissements[i].getCodeEtb(),
//							listeEtablissements[i].getLibEtb()));
//			}
//		} catch (Exception e) {
//			logger.error(e);
//			listTrEtablissementDTO = null;
//		}
//		return listTrEtablissementDTO;
//
//	}
//
//	public TrEtablissementDTO getEtablissementByRne(String rne) {
//		// appel au WS RIMBAUS
//		logger.info("getEtablissementByRne "+ rne);
//
//		TrEtablissementDTO trEtablissement = null;
//		Etablissement[] typeEtablissementDTO;
//		try {
//			typeEtablissementDTO = rta.recupererEtablissement(null, rne, null,
//					"1");
//			if (typeEtablissementDTO.length > 0) {
//				trEtablissement = new TrEtablissementDTO(
//						typeEtablissementDTO[0].getCodeEtb(),
//						typeEtablissementDTO[0].getLibEtb(),
//						typeEtablissementDTO[0].getDepartement().getCodeDept(),
//						typeEtablissementDTO[0].getDepartement().getLibDept(),
//						typeEtablissementDTO[0].getAcademie().getLibAcd()
//						,typeEtablissementDTO[0].getLibEtb(),"","","","","");
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		return trEtablissement;
//	}
//
//	public TrEtablissementDTO getEtablissementByDepartement(String dep) {
//		// appel au WS RIMBAUS
//		TrEtablissementDTO trEtablissement = null;
//		Etablissement[] typeEtablissementDTO;
//		try {
//			typeEtablissementDTO = rta.recupererEtablissement(null, null, dep,
//					"1");
//			if (typeEtablissementDTO.length > 0) {
//				trEtablissement = new TrEtablissementDTO(
//						typeEtablissementDTO[0].getCodeEtb(),
//						typeEtablissementDTO[0].getLibEtb(),
//						typeEtablissementDTO[0].getDepartement().getCodeDept(),
//						typeEtablissementDTO[0].getDepartement().getLibDept(),
//						typeEtablissementDTO[0].getAcademie().getLibAcd()
//						,typeEtablissementDTO[0].getLibEtb(),"","","","","");
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		return trEtablissement;
//	}
//
//	@Override
//	public TrBac getBaccalaureat(String supannEtuId) {
//		// Recuperation des infos de l'etudiant dans Apogee
//		InfoAdmEtu infoAdmEtuDTO;
//		IndBac[] indBacDTO;
//		try {
//			infoAdmEtuDTO = rta.recupererInfosAdmEtu(supannEtuId);
//			indBacDTO = infoAdmEtuDTO.getListeBacs();
//
//			TrBac infosBac = new TrBac(indBacDTO[0].getCodBac(),
//					indBacDTO[0].getLibelleBac(), indBacDTO[0]
//							.getDepartementBac().getLibDept(), indBacDTO[0]
//							.getEtbBac().getLibEtb(),
//					indBacDTO[0].getAnneeObtentionBac(), indBacDTO[0]
//							.getDepartementBac().getLibAca());
//			return infosBac;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public TrResultatVdiVetDTO getSessionsResultats(String supannEtuId, String source) {
//
//		TrResultatVdiVetDTO trResultatVdiVetDTO;
//		List<ResultatEtape> listResultatEtape = new ArrayList<ResultatEtape>();
//		List<ResultatSession> listResultatSession;
//		ResultatEtape re;
//		ResultatSession rs;
//
//		try {
//
//			rimbaustransfert.etudiant.ResultatEtape[] resultatEtapes = rta
//					.getSessionsResultats(supannEtuId);
//
//			if (logger.isDebugEnabled()) {
//				logger.debug("resultatEtapes.length -----> "
//						+ resultatEtapes.length);
//			}
//
//			int max=MAX_SESSIONS_RESULTAT_DEPART;
//			if("A".equals(source))
//				max=MAX_SESSIONS_RESULTAT_ACCUEIL;
//
//			if(resultatEtapes.length<max)
//				max=resultatEtapes.length;
//
//			for (int i = 0; i < max; i++) {
//				rimbaustransfert.etudiant.ResultatEtape resultatEtape = resultatEtapes[i];
//
//				listResultatSession = new ArrayList<ResultatSession>();
//
//				rimbaustransfert.etudiant.ResultatSession[] resultatSessions = resultatEtape
//						.getSessions();
//				Vector<String> session= new Vector<String>();
//
//				for (int k = 0; k < resultatSessions.length; k++) {
//					rimbaustransfert.etudiant.ResultatSession resultatSession = resultatSessions[k];
//
//					rs = new ResultatSession(resultatSession.getLibSession(),
//							resultatSession.getResultat(),
//							resultatSession.getMention());
//					listResultatSession.add(rs);
//
//					session.add(resultatSession.getLibSession());
//				}
//
//				if(!session.contains("Session 1"))
//				{
//					rs = new ResultatSession("Session 1",
//							"",
//							"");
//					listResultatSession.add(rs);
//
//				}
//
//				if(!session.contains("Session 2"))
//				{
//					rs = new ResultatSession("Session 2",
//							"",
//							"");
//					listResultatSession.add(rs);
//				}
//
//				re = new ResultatEtape(resultatEtape.getAnnee(),
//						resultatEtape.getLibEtape(), listResultatSession);
//				listResultatEtape.add(re);
//			}
//
//			trResultatVdiVetDTO = new TrResultatVdiVetDTO();
//			trResultatVdiVetDTO.setEtapes(listResultatEtape);
//
//		} catch (Exception e) {
//			logger.error(e);
//			//renvoi un résultat vide
//			trResultatVdiVetDTO = new TrResultatVdiVetDTO();
//			trResultatVdiVetDTO.setEtapes(new ArrayList<ResultatEtape>());
//		}
//
//		return trResultatVdiVetDTO;
//	}
//
//	@Override
//	public IndOpi getInfosOpi(String numeroEtudiant) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("getInfosOpi(String numeroEtudiant)");
//		}
//		VoeuxIns voeuxIns = new VoeuxIns();
//		IndOpi indOpi = new IndOpi();
//		// Recuperation des infos de l'etudiant dans Apogee
//		InfoAdmEtu infoAdmEtuDTO;
//		try {
//			infoAdmEtuDTO = rta.recupererInfosAdmEtu(numeroEtudiant);
//			//Coordonnees coordonneesDTO = rta
//			//		.recupererAdressesEtudiant(numeroEtudiant);
//			//Adresse adresseFixe = coordonneesDTO.getAdresseFixe();
//			//Commune communeDTO = adresseFixe.getCommune();
//			//Pays paysDTO = adresseFixe.getPays();
//			//Pays nationaliteDTO = infoAdmEtuDTO.getNationalite();
//			//IndBac[] IndBacDTO = infoAdmEtuDTO.getListeBacs();
//
//			/* OPI */
//			/* IND_OPI */
//			indOpi.setCodPayNat(infoAdmEtuDTO.getNationalite().getCodePay());
//			indOpi.setCodEtb(infoAdmEtuDTO.getEtbPremiereInscUniv()
//					.getCodeEtb());
//			indOpi.setCodNneIndOpi(infoAdmEtuDTO.getNumeroINE());
//			indOpi.setCodCleNneIndOpi(infoAdmEtuDTO.getCleINE());
//			indOpi.setDateNaiIndOpi(infoAdmEtuDTO.getDateNaissance().getTime());
//			indOpi.setTemDateNaiRelOpi(infoAdmEtuDTO
//					.getTemoinDateNaissEstimee());
//			indOpi.setDaaEntEtbOpi(infoAdmEtuDTO.getAnneePremiereInscUniv());
//			indOpi.setLibNomPatIndOpi(infoAdmEtuDTO.getNomPatronymique());
//			indOpi.setLibNomUsuIndOpi(infoAdmEtuDTO.getNomUsuel());
//			indOpi.setLibPr1IndOpi(infoAdmEtuDTO.getPrenom1());
//			indOpi.setLibPr2IndOpi(infoAdmEtuDTO.getPrenom2());
//			indOpi.setLibPr3IndOpi("");
//			indOpi.setLibVilNaiEtuOpi(infoAdmEtuDTO.getLibVilleNaissance());
//			/* Code pays ou dÃƒÂ©partement de naissance */
//
//			if (infoAdmEtuDTO.getPaysNaissance().getCodePay() != null && !"100".equals(infoAdmEtuDTO.getPaysNaissance().getCodePay())) {
//				indOpi.setCodDepPayNai(infoAdmEtuDTO.getPaysNaissance().getCodePay());
//				indOpi.setCodTypDepPayNai("P");
//			} else
//			if (infoAdmEtuDTO.getDepartementNaissance().getCodeDept() != null) {
//				indOpi.setCodDepPayNai(infoAdmEtuDTO.getDepartementNaissance()
//						.getCodeDept());
//				indOpi.setCodTypDepPayNai("D");
//			} else {
//				indOpi.setCodDepPayNai("");
//				indOpi.setCodTypDepPayNai("");
//			}
//			indOpi.setDaaEnsSupOpi(infoAdmEtuDTO.getAnneePremiereInscEnsSup());
//			indOpi.setCodSexEtuOpi(infoAdmEtuDTO.getSexe());
//			// TODO
//			// indOpi.setDaaEtrSup(infoAdmEtuDTO.getAnneePremiereInscEtr());
//
//			/* OPI_BAC */
//			IndBac[] listeBacs = infoAdmEtuDTO.getListeBacs();
//			if(listeBacs.length>0)
//			{
//				indOpi.setCodBac((listeBacs[0].getCodBac()==null)?"":listeBacs[0].getCodBac());
//				if (listeBacs[0].getEtbBac() != null)
//					indOpi.setCodEtbBac(listeBacs[0].getEtbBac().getCodeEtb());
//				else
//					indOpi.setCodEtbBac("");
//				if (listeBacs[0].getDepartementBac() != null)
//					indOpi.setCodDep(listeBacs[0].getDepartementBac().getCodeDept());
//				else
//					indOpi.setCodDep("");
//				if (listeBacs[0].getMentionBac() != null)
//					indOpi.setCodMnb(listeBacs[0].getMentionBac().getCodMention());
//				else
//					indOpi.setCodMnb("");
//				indOpi.setDaabacObtOba((listeBacs[0].getAnneeObtentionBac()==null)?"":listeBacs[0].getAnneeObtentionBac());
//				if (listeBacs[0].getTypeEtbBac() != null)
//					indOpi.setCodTpe(listeBacs[0].getTypeEtbBac().getCodTypeEtb());
//				else
//					indOpi.setCodTpe("");
//			}
//			else
//			{
//				indOpi.setCodBac("");
//				indOpi.setCodEtbBac("");
//				indOpi.setCodDep("");
//				indOpi.setCodMnb("");
//				indOpi.setDaabacObtOba("");
//				indOpi.setCodTpe("");
//			}
//
//			// indOpi.setOpiBac(opiBac);
//			indOpi.setVoeux(voeuxIns);
//			return indOpi;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public String getComposante(String supannEtuId) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("String getComposante(String supannEtuId, String annee)");
//			logger.debug("supannEtuId --> " + supannEtuId);
//		}
//		try {
//			String composante = rta.recupererDerniereComposante(supannEtuId).getCode();
//
//			if (logger.isDebugEnabled()) {
//				logger.debug("composante = --> " + composante);
//			}
//			if (composante != null)
//				return composante;
//			else
//				return "Lille 1";
//		} catch (Exception e) {
//			logger.error(e);
//			return "Inconnue";
//		}
//	}
//
//	public List<String> getForcerBlocageListSplit() {
//		return forcerBlocageListSplit;
//	}
//
//	public void setForcerBlocageListSplit(List<String> forcerBlocageListSplit) {
//		this.forcerBlocageListSplit = forcerBlocageListSplit;
//	}
//
//	@Override
//	public List<OffreDeFormationsDTO> getOffreDeFormation(String rne, Integer annee) {
//		// TODO Auto-generated method stub
//		if (logger.isDebugEnabled()) {
//			logger.debug("getOffreDeFormation(String rne,Integer annee)");
//		}
//		List<OffreDeFormationsDTO> odfs = new ArrayList<OffreDeFormationsDTO>();
//		//OffreFormationMetierServiceInterface offreFormation = new OffreFormationMetierServiceInterfaceProxy();
//
//		try
//		{
//			OffreFormation[] ofs;
//
//			/*if(annee.intValue()==2013)
//			{
//				ofs = rta.recupererOffreFormation(rne, "2013");
//				if (ofs==null)
//					ofs = rta.recupererOffreFormation(rne, "2012");
//			}
//			else*/
//				ofs = rta.recupererOffreFormation(rne, ""+annee);
//
//			if(ofs!=null) {
//				for (int i = 0; i < ofs.length; i++) {
//					OffreFormation of = ofs[i];
//					odfs.add(new OffreDeFormationsDTO(
//							of.getRne(),
//							annee,//of.getAnnee(),
//							of.getCodTypDip(),
//							of.getLibTypDip(),
//							of.getCodeDiplome(),
//							of.getCodeVersionDiplome(),
//							of.getCodeEtape(),
//							of.getCodeVersionEtape(),
//							of.getLibVersionDiplome(),
//							of.getLibVersionEtape(),//((annee.intValue()==2013)?"Test2013":"")+
//							of.getCodeComposante(),
//							of.getLibComposante(),
//							of.getCodeCentreGestion(),
//							of.getLibCentreGestion(),
//							of.getCodeNiveau(),
//							of.getLibNiveau(),
//							//Mofification Farid AIT KARRA
//							"oui",
//							"oui"
//					));
//
//				}
//			}
//
//			return odfs;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public List<IndOpi> synchroOpi(List<IndOpi> listeSynchroScolarite) {
//		return dssApo.synchroOpi(listeSynchroScolarite);
//	}
//
//	@Override
//	public List<TrBac> recupererBacOuEquWS(String codeBac) {
//		logger.warn("+++++ recupererBacOuEquWS()"+codeBac);
//		try {
//			List<TrBac> lBac = new ArrayList<TrBac>();
//			Bac[] tabBacouEquiv = rta.recupererListeBac(codeBac);
//
//			for(int i=0;i<tabBacouEquiv.length;i++)
//				lBac.add(new TrBac(tabBacouEquiv[i].getCodBac(), tabBacouEquiv[i].getLibelleBac()));
//			logger.warn("----> recupererBacOuEquWS()"+lBac);
//			return lBac;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//
//	}
//
//	@Override
//	public List<PersonnelComposante> recupererComposante(String uid, String diplayName, String mail, String source, Integer annee) {
//		try
//		{
//		Composante[] comp = rta.recupererComposantes();
//
//		if(comp.length!=0)
//		{
//			List<PersonnelComposante> pc = new ArrayList<PersonnelComposante>();
//			pc.add(new PersonnelComposante(uid, codeComposanteInconnue, source, annee, diplayName, mail, "Inconnue",0,"oui","oui","oui","oui","oui","oui","non","non"));
//			for(int i=0;i<comp.length;i++)
//			{
//				pc.add(new PersonnelComposante(uid, comp[i].getCode(), source, annee, diplayName, mail, comp[i].getLibelle(),0,"oui","oui","oui","oui","oui","oui","non","non"));
//			}
//			return pc;
//		}
//		else
//			return null;
//		}catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//
//		}
//
////	@Override
////	public String getNumeroEtudiantByIne(String ine, Date dateNaissance) {
////		// TODO Auto-generated method stub
////		// pour l'application Accueil
////		return null;
////	}
//
//	@Override
//	public Integer getAuthEtu(String ine, Date dateNaissanceApogee) {
//		// TODO Auto-generated method stub
//		// pour l'application Accueil
//		// 0 : Correcte
//		// 1 INE ok Date ko
//		// 2 introuvable
//
//		return null;
//	}
//
//	@Override
//	public List<org.esupportail.transferts.domain.beans.Composante> recupererListeComposantes(Integer annee, String source) {
//
//		if (logger.isDebugEnabled())
//			logger.debug("public List<Composante> recupererListeComposantes(Integer annee, String source)-->"+annee+"-----"+source);
//
//		try
//		{
//		Composante[] comp = rta.recupererComposantes();
//
//		if(comp.length!=0)
//		{
//			List<org.esupportail.transferts.domain.beans.Composante> pc = new ArrayList<org.esupportail.transferts.domain.beans.Composante>();
//			pc.add(new org.esupportail.transferts.domain.beans.Composante(codeComposanteInconnue, source, annee, "Inconnue", "non"));
//			for(int i=0;i<comp.length;i++)
//			{
//				pc.add(new org.esupportail.transferts.domain.beans.Composante(comp[i].getCode(), source, annee, comp[i].getLibelle(),"non"));
//			}
//			return pc;
//		}
//		else
//			return null;
//		}catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//
//	}
//
//	@Override
//	public List<CGE> recupererListeCGE(
//			Integer annee, String source) {
//		if (logger.isDebugEnabled())
//			logger.debug("public List<CGE> recupererListeCGE(Integer annee, String source)-->"+annee+"-----"+source);
//
//		try
//		{
//		Composante[] comp = rta.recupererComposantes();
//
//		if(comp.length!=0)
//		{
//			List<CGE> pc = new ArrayList<CGE>();
//			pc.add(new CGE(codeComposanteInconnue, source, annee, "Inconnue", "non"));
//			for(int i=0;i<comp.length;i++)
//			{
//				if (logger.isDebugEnabled())
//					logger.debug("cge[i].getCodCge() ----- cge[i].getLibCge()-->"+comp[i].getCode()+"-----"+comp[i].getLibelle());
//				pc.add(new CGE(comp[i].getCode(), source, annee, comp[i].getLibelle(),"non"));
//			}
//			return pc;
//		}
//		else
//			return null;
//		}catch (Exception e) {
//			logger.error(e);
//			return null;
//		}	}
//
//	@Override
//	public Map<String, String> getEtapePremiereAndCodeCgeAndLibCge(
//			String supannEtuId) {
//		if (logger.isDebugEnabled()) {
//			logger.debug("public Map<String,String> getEtapePremiereAndCodeCgeAndLibCge(String supannEtuId)");
//			logger.debug("supannEtuId --> "+supannEtuId);
//		}
//
//		try {
//			String libelleDiplomePrincipal = rta.recupererDiplomePrincipal(supannEtuId);
//
//			Composante composanteRimbaus=rta.recupererDerniereComposante(supannEtuId);
//
//			String composante = composanteRimbaus.getCode();
//
//			String libComposante = composanteRimbaus.getLibelle();
//
//			Map<String, String> map = new HashMap<String, String>();
//
//			map.put("libWebVet", libelleDiplomePrincipal);
//			map.put("codeCGE", composante);
//			map.put("libCGE", libComposante);
//			map.put("codeComposante", composante);
//			map.put("libComposante", libComposante);
//
//			if (logger.isDebugEnabled()) {
//				logger.debug("----------------------> "+"libWebVet "+ libelleDiplomePrincipal);
//				logger.debug("----------------------> "+"codeCGE "+ composante);
//				logger.debug("----------------------> "+"libCGE "+ libComposante);
//				logger.debug("----------------------> "+"codeComposante "+ composante);
//				logger.debug("----------------------> "+"libComposante "+ libComposante);
//			}
//
//			return map;
//		} catch (Exception e) {
//			logger.error(e);
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("libWebVet", "Inconnue");
//			map.put("codeCGE", "Inconnue");
//			map.put("libCGE", "Inconnue");
//			return map;
//		}
//	}
//
////	@Override
////	public Integer getCleIndByIne(String ine) {
////		// TODO Auto-generated method stub
////		return null;
////	}
////
////	@Override
////	public Integer getCleIndByNumeroEtudiant(String numeroEtudiant) {
////		// TODO Auto-generated method stub
////		return null;
////	}
//
//	@Override
//	public org.esupportail.transferts.domain.beans.IdentifiantEtudiant getIdentifiantEtudiantByIne(String codNneIndOpi,
//			String codCleNneIndOpi) {
//		// appel au WS AMUE
//		if (logger.isDebugEnabled())
//			logger.debug("Je suis dans le WS RIMBAUS");
//
//		if (logger.isDebugEnabled())
//		{
//			logger.debug("public Integer getCleIndByCodAndCle(String codNneIndOpi, String codCleNneIndOpi) ");
//			logger.debug("codNneIndOpi --> "+ codNneIndOpi);
//			logger.debug("codCleNneIndOpi --> "+ codCleNneIndOpi);
//		}
//
//		try {
//			IdentifiantEtudiant identifiantEtudiantRimbaus = rta
//					.recupererIdentifiantsEtudiantByINE(codNneIndOpi+codCleNneIndOpi);
//			if( identifiantEtudiantRimbaus==null)
//			{
//				logger.debug("ine invalide  on inconnue--> ");
//				return null;
//			}
//			org.esupportail.transferts.domain.beans.IdentifiantEtudiant ie = new org.esupportail.transferts.domain.beans.IdentifiantEtudiant(Integer.valueOf(identifiantEtudiantRimbaus.getCodeEtu()), Integer.valueOf(identifiantEtudiantRimbaus.getCodeEtu()), identifiantEtudiantRimbaus.getNumINE());
//			return ie;
//		} catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public TrInfosAdmEtu getInfosAdmEtu(String supannEtuId) {
//		// Recuperation des infos de l'etudiant dans Apogee
//		InfoAdmEtu infoAdmEtuDTO;
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("Je suis dans le WS RIMBAUS");
//			logger.debug("getInfosAdmEtu(String supannEtuId)" + supannEtuId);
//		}
//		try
//		{
//			infoAdmEtuDTO = rta.recupererInfosAdmEtu(supannEtuId);
//			TrInfosAdmEtu trInfosAdmEtu = new TrInfosAdmEtu(infoAdmEtuDTO.getPaysNaissance().getCodePay(), infoAdmEtuDTO.getPaysNaissance().getLibNat());
//			return trInfosAdmEtu;
//		}catch (Exception e) {
//			logger.error(e);
//			return null;
//		}
//	}
//
//	@Override
//	public List<EtudiantRef> recupererListeEtudiants(String myAnnee, String codeDiplome, String versionDiplome, String codeEtape, String versionEtape)
//	{
//		return null;
//	}
//
//}