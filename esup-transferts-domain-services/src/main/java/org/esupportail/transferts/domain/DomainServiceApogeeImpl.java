/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.transferts.domain;

import com.googlecode.ehcache.annotations.Cacheable;
import fr.univartois.wsclient.apogee.administratif.AdministratifMetierServiceInterface;
import fr.univartois.wsclient.apogee.administratif.AdministratifMetierServiceInterfaceService;
import fr.univartois.wsclient.apogee.administratif.InsAdmEtpDTO3;
import fr.univartois.wsclient.apogee.etablissement.EtablissementCompletDTO2;
import fr.univartois.wsclient.apogee.etablissement.EtablissementMetierServiceInterface;
import fr.univartois.wsclient.apogee.etablissement.EtablissementMetierServiceInterfaceService;
import fr.univartois.wsclient.apogee.etudiant.*;
import fr.univartois.wsclient.apogee.geographie.*;
import fr.univartois.wsclient.apogee.geographie.CommuneDTO2;
import fr.univartois.wsclient.apogee.geographie.PaysDTO;
import fr.univartois.wsclient.apogee.offreformation.*;
import fr.univartois.wsclient.apogee.offreformation.ComposanteCentreGestionDTO;
import fr.univartois.wsclient.apogee.offreformation.DiplomeDTO3;
import fr.univartois.wsclient.apogee.offreformation.EtapeDTO3;
import fr.univartois.wsclient.apogee.offreformation.OffreFormationMetierServiceInterface;
import fr.univartois.wsclient.apogee.offreformation.OffreFormationMetierServiceInterfaceService;
import fr.univartois.wsclient.apogee.offreformation.SECritereDTO2;
import fr.univartois.wsclient.apogee.offreformation.VersionDiplomeDTO3;
import fr.univartois.wsclient.apogee.offreformation.VersionEtapeDTO3;
import fr.univartois.wsclient.apogee.opi.*;
import fr.univartois.wsclient.apogee.pedagogique.*;
import fr.univartois.wsclient.apogee.referentiel.CentreGestionDTO2;
import fr.univartois.wsclient.apogee.referentiel.ComposanteDTO3;
import fr.univartois.wsclient.apogee.referentiel.ReferentielMetierServiceInterface;
import fr.univartois.wsclient.apogee.referentiel.ReferentielMetierServiceInterfaceService;
import fr.univartois.wsclient.apogee.scolarite.BacOuEquDTO;
import fr.univartois.wsclient.apogee.scolarite.ScolariteMetierServiceInterface;
import fr.univartois.wsclient.apogee.scolarite.ScolariteMetierServiceInterfaceService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.*;

import java.lang.Exception;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Farid AIT KARRA (Universite d'Artois) - 2012
 */
public class DomainServiceApogeeImpl implements DomainServiceScolarite {

	private static final long serialVersionUID = 1L;
	private static final String codeComposanteInconnue = "N.A";
	private List<String> forcerBlocageListSplit = new ArrayList<String>();
	private String user;
	private String password;

	private String urlEtudiantMetierService;
	private String urlAdministratifMetierService;
	private String urlGeographieMetierService;
	private String urlScolariteMetierService;
	private String urlPedagogiqueMetierService;
	private String urlEtablissementMetierService;
	private String urlOpiMetierService;
	private String urlReferentielMetierService;
	private String urlOffreFormationMetierService;
	private EtudiantMetierServiceInterface etudiantMetierService;
	private AdministratifMetierServiceInterface administratifMetierService;
	private GeographieMetierServiceInterface geographieMetierService;
	private ScolariteMetierServiceInterface scolariteMetierService;
	private PedagogiqueMetierServiceInterface pedagogiqueMetierService;
	private EtablissementMetierServiceInterface etablissementMetierService;
	private OpiMetierServiceInterface opiMetierService;
	private ReferentielMetierServiceInterface referentielMetierService;
	private OffreFormationMetierServiceInterface offreFormationMetierService;
	/**
	 * For Logging.
	 */
	private static final Logger logger = new LoggerImpl(DomainServiceApogeeImpl.class);

	/**
	 * Constructor.
	 */
	public DomainServiceApogeeImpl() {
		super();
	}

	public DomainServiceApogeeImpl(List<String> forcerBlocageListSplit) {
		this.forcerBlocageListSplit=forcerBlocageListSplit;
	}

	public DomainServiceApogeeImpl(List<String> forcerBlocageListSplit, String user, String password,
								   String urlEtudiantMetierService,String urlAdministratifMetierService,
								   String urlGeographieMetierService, String urlScolariteMetierService,
								   String urlPedagogiqueMetierService,String urlEtablissementMetierService,
								   String urlOpiMetierService,String urlReferentielMetierService,
								   String urlOffreFormationMetierService) {
		this.forcerBlocageListSplit=forcerBlocageListSplit;
		this.user=user;
		this.password=password;
		this.urlEtudiantMetierService = urlEtudiantMetierService;
		this.urlAdministratifMetierService = urlAdministratifMetierService;
		this.urlGeographieMetierService = urlGeographieMetierService;
		this.urlScolariteMetierService = urlScolariteMetierService;
		this.urlPedagogiqueMetierService = urlPedagogiqueMetierService;
		this.urlEtablissementMetierService = urlEtablissementMetierService;
		this.urlOpiMetierService = urlOpiMetierService;
		this.urlReferentielMetierService = urlReferentielMetierService;
		this.urlOffreFormationMetierService = urlOffreFormationMetierService;
	}

	@Override
	public EtudiantRef getCurrentEtudiant(String supannEtuId){
		logger.debug("Je suis dans le WS AMUE");
		AdresseRef adresse = new AdresseRef();
		Transferts transfert = new Transferts();
		InfosAccueil infosAccueil = new InfosAccueil();
		EtudiantRef etudiant = new EtudiantRef();
		etudiant.setFrom("WSAMUE");
		etudiant.setNumeroEtudiant(supannEtuId);
		try {
			// Recuperation des infos de l'etudiant dans Apogee
			InfoAdmEtuDTO3 infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(supannEtuId);

			logger.debug("Numero etudiant -->"+etudiant.getNumeroEtudiant());
			BlocageDTO[] listeBlocagesDTO = infoAdmEtuDTO.getListeBlocages() != null
					? infoAdmEtuDTO.getListeBlocages().getItem().toArray(new BlocageDTO[0]) : new BlocageDTO[0];

			logger.debug("listeBlocagesDTO -->"+listeBlocagesDTO.length);

			// Recup Adresses
			CoordonneesDTO2 coordonneesDTO = getEtudiantMetierService().recupererAdressesEtudiantV2(supannEtuId,null, null);
			logger.debug("coordonneesDTO -->"+coordonneesDTO.toString());

			AdresseDTO2 adresseFixe = coordonneesDTO.getAdresseFixe();
			logger.debug("adresseFixe -->"+adresseFixe.toString());

			fr.univartois.wsclient.apogee.etudiant.CommuneDTO2 communeDTO = adresseFixe.getCommune();
			if (logger.isDebugEnabled() && communeDTO!=null) logger.debug("communeDTO -->"+communeDTO.toString());
			else if(logger.isDebugEnabled() && communeDTO==null) logger.debug("communeDTO --> la commune est null");

			fr.univartois.wsclient.apogee.etudiant.PaysDTO paysDTO = adresseFixe.getPays();
			logger.debug("paysDTO -->"+paysDTO.toString());

			NationaliteDTO nationaliteDTO = infoAdmEtuDTO.getNationaliteDTO();
			logger.debug("nationaliteDTO -->"+nationaliteDTO);

			IndBacDTO[] indBac = infoAdmEtuDTO.getListeBacs() != null
					? infoAdmEtuDTO.getListeBacs().getItem().toArray(new IndBacDTO[0]) : new IndBacDTO[0];
			logger.debug("indBac -->"+indBac.length);

			infosAccueil.setAnneeBac(indBac[0].getAnneeObtentionBac());
			infosAccueil.setCodeBac(indBac[0].getCodBac());
			infosAccueil.setCodePaysNat(nationaliteDTO.getCodeNationalite());

			if(listeBlocagesDTO.length==0) {
				etudiant.setInterdit(false);
				logger.debug("Interdit a FALSE");
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("Interdit a TRUE");
					if(this.forcerBlocageListSplit!=null && !this.forcerBlocageListSplit.isEmpty())
						for (String s : this.forcerBlocageListSplit) logger.debug("forcerBlocageListSplit.get(i) = " + s);
				}
				int forcerInterdit = 0;
				for(BlocageDTO b : listeBlocagesDTO) {
					boolean forcer=false;
					if(this.forcerBlocageListSplit!=null && !this.forcerBlocageListSplit.isEmpty()) {
						for (String s : this.forcerBlocageListSplit) {
							if (b.getCodBlocage().equalsIgnoreCase(s)) {
								forcer = true;
								forcerInterdit = forcerInterdit + 1;
								break;
							}
						}
					}
					if(!forcer) etudiant.getListeBlocagesDTO().add(new TrBlocageDTO(b.getCodBlocage(), b.getLibBlocage()));
				}

				logger.debug("forcerInterdit -->" + forcerInterdit +"\nlisteBlocagesDTO.length -->" + listeBlocagesDTO.length);
				etudiant.setInterdit(forcerInterdit != listeBlocagesDTO.length);
			}

			//Etat civil
			etudiant.setNumeroIne(infoAdmEtuDTO.getNumeroINE());
			etudiant.setNomPatronymique(infoAdmEtuDTO.getNomPatronymique());
			etudiant.setNomUsuel(infoAdmEtuDTO.getNomUsuel());
			etudiant.setPrenom1(infoAdmEtuDTO.getPrenom1());
			etudiant.setPrenom2(infoAdmEtuDTO.getPrenom2());
			etudiant.setDateNaissance(infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime());
			etudiant.setLibNationalite(nationaliteDTO.getLibNationalite());

			//Adresse
			adresse.setNumeroEtudiant(etudiant.getNumeroEtudiant());
			adresse.setLibAd1(adresseFixe.getLibAd1());
			adresse.setLibAd2(adresseFixe.getLibAd2());
			adresse.setLibAd3(adresseFixe.getLibAd3());
			adresse.setNumTelPortable(coordonneesDTO.getNumTelPortable());
			adresse.setNumTel(adresseFixe.getNumTel());
			adresse.setEmail(coordonneesDTO.getEmail());
			if("100".equals(paysDTO.getCodPay())) {
				adresse.setCodePostal(communeDTO != null ? communeDTO.getCodePostal() : "");
				adresse.setCodeCommune(communeDTO != null ? communeDTO.getCodeInsee() : "");
				adresse.setCodPay(paysDTO.getCodPay());
				adresse.setLibPay(paysDTO.getLibPay());
			} else {
				adresse.setCodPay(paysDTO.getCodPay());
				adresse.setLibPay(paysDTO.getLibPay());
				adresse.setCodePostal("");
				adresse.setCodeCommune("");
			}
			transfert.setNumeroEtudiant(etudiant.getNumeroEtudiant());
			etudiant.setAdresse(adresse);
			etudiant.setTransferts(transfert);
			etudiant.setAccueil(infosAccueil);
			return etudiant;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public EtudiantRef getCurrentEtudiantIne(String ine, Date dateNaissance){
		EtudiantRef etudiant = new EtudiantRef();
		AdresseRef adresse = new AdresseRef();
		Transferts transfert = new Transferts();
		InfosAccueil infosAccueil = new InfosAccueil();
		// appel au WS AMUE
		logger.debug("Je suis dans le WS AMUE - AUTH Apogee");
		etudiant.setFrom("WSAMUE");

		String ineSansCle = ine.substring(0, ine.length()-1);
		String cleIne = ine.substring(ine.length()-1, ine.length());

		logger.debug("===>public EtudiantRef getCurrentEtudiantIne(String ine, Date dateNaissance)<==="
				+ "\nine===>"+ine+"<==="
				+ "\ndateNaissance ===>"+dateNaissance+"<==="
				+ "\n===>----------------------------------------------------<==="
				+ "\nineSansCle===>"+ineSansCle+"<==="
				+ "\ncleIne ===>"+cleIne+"<===");
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			IdentifiantsEtudiantDTO2 identifiantEtudiant = getEtudiantMetierService()
					.recupererIdentifiantsEtudiantV2(null, null, ine, null, null,
							null, null, null, null);

			// Recuperation des infos de l'etudiant dans Apogee
			logger.debug("ine===>" + ine + "<===");
			InfoAdmEtuDTO3 infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(String.valueOf(identifiantEtudiant.getCodEtu()));

			if(infoAdmEtuDTO != null)
				logger.debug("===>if(identifiantEtudiant!=null && infoAdmEtuDTO!=null)<==="
						+ "\nNumero etudiant dans la bdd scol===>"+String.valueOf(identifiantEtudiant.getCodEtu())+"<==="
						+ "\nDate de naissance dans la bdd scol ===>"+infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime()+"<===");

			if(dateFormat.format(dateNaissance).equals(dateFormat.format(infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime()))) {
				logger.debug("===>Compare date OK<===");

				BlocageDTO[] listeBlocagesDTO = infoAdmEtuDTO.getListeBlocages() != null
						? infoAdmEtuDTO.getListeBlocages().getItem().toArray(new BlocageDTO[0]) : new BlocageDTO[0];
				logger.debug("listeBlocagesDTO -->"+listeBlocagesDTO.length);

				// Recup Adresses
				CoordonneesDTO2 coordonneesDTO = getEtudiantMetierService().recupererAdressesEtudiantV2(String.valueOf(infoAdmEtuDTO.getNumEtu()),null,"N");
				logger.debug("coordonneesDTO -->"+coordonneesDTO.toString());

				AdresseDTO2 adresseFixe = coordonneesDTO.getAdresseFixe();
				logger.debug("adresseFixe -->"+adresseFixe.toString());

				fr.univartois.wsclient.apogee.etudiant.CommuneDTO2 communeDTO = adresseFixe.getCommune();
				if (logger.isDebugEnabled() && communeDTO!=null) logger.debug("communeDTO -->"+communeDTO.toString());
                else if(logger.isDebugEnabled() && communeDTO==null) logger.debug("communeDTO --> la commune est null");

				fr.univartois.wsclient.apogee.etudiant.PaysDTO paysDTO = adresseFixe.getPays();
				logger.debug("paysDTO -->"+paysDTO.toString());

				NationaliteDTO nationaliteDTO = infoAdmEtuDTO.getNationaliteDTO();
				logger.debug("nationaliteDTO -->"+nationaliteDTO.toString());

				IndBacDTO[] indBac = infoAdmEtuDTO.getListeBacs() != null
						? infoAdmEtuDTO.getListeBacs().getItem().toArray(new IndBacDTO[0]) : new IndBacDTO[0];
				logger.debug("indBac -->"+indBac.length);

				infosAccueil.setAnneeBac(indBac[0].getAnneeObtentionBac());
				infosAccueil.setCodeBac(indBac[0].getCodBac());
				infosAccueil.setCodePaysNat(nationaliteDTO.getCodeNationalite());

				if(listeBlocagesDTO.length == 0) {
					etudiant.setInterdit(false);
					logger.debug("Interdit a FALSE");
				} else {
					if (logger.isDebugEnabled()) {
						logger.debug("Interdit a TRUE");
						if(this.forcerBlocageListSplit!=null && !this.forcerBlocageListSplit.isEmpty())
							for (String s : this.forcerBlocageListSplit)
								logger.debug("this.forcerBlocageListSplit.get(i) --> " + s);
					}
					int forcerInterdit = 0;
					//etudiant.setListeBlocagesDTO(listeBlocagesDTO);
					for(BlocageDTO b : listeBlocagesDTO) {
						boolean forcer=false;
						if(this.forcerBlocageListSplit!=null && !this.forcerBlocageListSplit.isEmpty()) {
							for (String s : this.forcerBlocageListSplit) {
								if (b.getCodBlocage().equalsIgnoreCase(s)) {
									forcer = true;
									forcerInterdit = forcerInterdit + 1;
									break;
								}
							}
						}
						if(!forcer) etudiant.getListeBlocagesDTO().add(new TrBlocageDTO(b.getCodBlocage(), b.getLibBlocage()));
					}

					logger.debug("forcerInterdit -->" + forcerInterdit +"\nlisteBlocagesDTO.length -->" + listeBlocagesDTO.length);

					etudiant.setInterdit(forcerInterdit != listeBlocagesDTO.length);
				}

				//Etat civil
				etudiant.setNumeroEtudiant(String.valueOf(infoAdmEtuDTO.getNumEtu()));
				etudiant.setNumeroIne(infoAdmEtuDTO.getNumeroINE());
				etudiant.setNomPatronymique(infoAdmEtuDTO.getNomPatronymique());
				etudiant.setNomUsuel(infoAdmEtuDTO.getNomUsuel());
				etudiant.setPrenom1(infoAdmEtuDTO.getPrenom1());
				etudiant.setPrenom2(infoAdmEtuDTO.getPrenom2());
				etudiant.setDateNaissance(infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime());
				etudiant.setLibNationalite(nationaliteDTO.getLibNationalite());

				//Adresse
				adresse.setNumeroEtudiant(etudiant.getNumeroEtudiant());
				adresse.setLibAd1(adresseFixe.getLibAd1());
				adresse.setLibAd2(adresseFixe.getLibAd2());
				adresse.setLibAd3(adresseFixe.getLibAd3());
				adresse.setNumTelPortable(coordonneesDTO.getNumTelPortable());
				adresse.setNumTel(adresseFixe.getNumTel());
				adresse.setEmail(coordonneesDTO.getEmail());
				if("100".equals(paysDTO.getCodPay())) {
					adresse.setCodePostal(communeDTO.getCodePostal());
					adresse.setCodeCommune(communeDTO.getCodeInsee());
					adresse.setCodPay(paysDTO.getCodPay());
					adresse.setLibPay(paysDTO.getLibPay());
				} else {
					adresse.setCodePostal("");
					adresse.setCodeCommune("");
					adresse.setCodPay(paysDTO.getCodPay());
					adresse.setLibPay(paysDTO.getLibPay());
				}
				//Transferts
				transfert.setNumeroEtudiant(etudiant.getNumeroEtudiant());
				etudiant.setAdresse(adresse);
				etudiant.setTransferts(transfert);
				etudiant.setAccueil(infosAccueil);
				return etudiant;
			} else {
				logger.debug("Compare date FAUX !!!");
				etudiant = null;
			}
			return etudiant;

		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	//	public Integer getCleIndByCodAndCle(String codNneIndOpi, String codCleNneIndOpi) 
	public IdentifiantEtudiant getIdentifiantEtudiantByIne(String codNneIndOpi, String codCleNneIndOpi) {
		logger.debug("public Integer getCleIndByCodAndCle(String codNneIndOpi, String codCleNneIndOpi)"
				+ "\ncodNneIndOpi --> " + codNneIndOpi
				+ "\ncodCleNneIndOpi --> " + codCleNneIndOpi);

		logger.debug("ine --> " + codNneIndOpi);

		// Recuperation des infos de l'etudiant dans Apogee
		try {
			IdentifiantsEtudiantDTO2 identifiantEtudiant = getEtudiantMetierService().recupererIdentifiantsEtudiantV2(
					null, null, codNneIndOpi, null, null,
					null, null, null, null);

			return new IdentifiantEtudiant(identifiantEtudiant.getCodEtu(), identifiantEtudiant.getCodInd(), identifiantEtudiant.getNumeroINE());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public List<TrCommuneDTO> getCommunes(String codePostal){
		List<TrCommuneDTO> listTrCommuneDTO = null;

		try{
			CommuneDTO2[] listeCommunes = getGeographieMetierService()
					.recupererCommuneV2(codePostal, null, null, null)
					.toArray(new CommuneDTO2[0]);

			if(listeCommunes.length>0) {
				listTrCommuneDTO = new ArrayList<>();
				for (CommuneDTO2 c : listeCommunes) listTrCommuneDTO.add(new TrCommuneDTO(c.getCodeCommune(), c.getLibCommune()));
			}
		} catch (Exception e) {
			logger.error(e);
			listTrCommuneDTO = null;
		}
		return listTrCommuneDTO;
	}

	@Override
	public List<TrPaysDTO> getListePays(){
		List<TrPaysDTO> listTrPaysDTO = null;
		try{
			PaysDTO[] listePays = getGeographieMetierService().recupererPays(null,null)
					.toArray(new PaysDTO[0]);

			if(listePays.length>0) {
				listTrPaysDTO = new ArrayList<>();
				for (PaysDTO p : listePays) listTrPaysDTO.add(new TrPaysDTO(p.getCodePay(), p.getLibPay(), p.getLibNat()));
			}
		} catch (Exception e) {
			logger.error(e);
			listTrPaysDTO = null;
		}
		return listTrPaysDTO;
	}

	@Override
	public TrPaysDTO getPaysByCodePays(String codePays) {
		TrPaysDTO trPaysDTO = null;
		try {
			PaysDTO[] listePays = getGeographieMetierService().recupererPays(codePays, null).toArray(new PaysDTO[0]);
			trPaysDTO = new TrPaysDTO(listePays[0].getCodePay(), listePays[0].getLibPay(), listePays[0].getLibNat());
		} catch (Exception e) {
			logger.error(e);
		}
		return trPaysDTO;
	}

	public List<TrDepartementDTO> getListeDepartements() {
		List<TrDepartementDTO> listTrDepartementDTO = null;
		try{
			DepartementDTO[] listeDepartements = getGeographieMetierService().recupererDepartement(
					null, null).toArray(new DepartementDTO[0]);

			if(listeDepartements.length>0) {
				listTrDepartementDTO = new ArrayList<>();
				for (DepartementDTO d : listeDepartements) listTrDepartementDTO.add(new TrDepartementDTO(d.getCodeDept(), d.getLibDept()));
			}
		} catch (Exception e) {
			logger.error(e);
			listTrDepartementDTO = null;
		}
		return listTrDepartementDTO;
	}

	public List<TrEtablissementDTO> getListeEtablissements(String typeEtablissement, String dept) {
		logger.debug("===>Appel au WS AMUE<===\n getListeEtablissements(String typeEtablissement, String dept)===>" + typeEtablissement + "/" + dept + "<===");

		List<TrEtablissementDTO> listTrEtablissementDTO = null;
		try{
			EtablissementCompletDTO2[] listeEtablissements = getEtablissementMetierService().recupererEtablissementWSV2(
					typeEtablissement,null,null,dept,null,null,null).toArray(new EtablissementCompletDTO2[0]);

			if(listeEtablissements.length>0) {
				listTrEtablissementDTO = new ArrayList<>();
				for (EtablissementCompletDTO2 e : listeEtablissements) listTrEtablissementDTO.add(new TrEtablissementDTO(e.getCodeEtb(), e.getLibEtb()));
			}
		} catch (Exception e) {
			logger.error(e);
			listTrEtablissementDTO = null;
		}
		return listTrEtablissementDTO;
	}

	public TrEtablissementDTO getEtablissementByRne(String rne) {
		TrEtablissementDTO trEtablissement = null;
		try{
			EtablissementCompletDTO2[] etablissements = getEtablissementMetierService().recupererEtablissementWSV2(null,rne,null,
					null,null,null,null).toArray(new EtablissementCompletDTO2[0]);

			if(etablissements.length > 0) {
				trEtablissement = new TrEtablissementDTO(etablissements[0].getCodeEtb(),
						etablissements[0].getLibEtb(),
						etablissements[0].getDepartement().getCodeDept(),
						etablissements[0].getDepartement().getLibDept(),
						etablissements[0].getAcademie().getLibAcd(),
						etablissements[0].getLibOffEtb(),
						etablissements[0].getAdresse().getLibAd1Etb(),
						etablissements[0].getAdresse().getLibAd2Etb(),
						etablissements[0].getAdresse().getLibAd3Etb(),
						etablissements[0].getAdresse().getCodPosAdrEtb(),
						etablissements[0].getAdresse().getLibAchAdrEtb()
				);
			}
		} catch (Exception e) {
			logger.error(e);
		}
		return trEtablissement;
	}

	public TrEtablissementDTO getEtablissementByDepartement(String dep) {
		TrEtablissementDTO trEtablissement = null;
		try{
			EtablissementCompletDTO2[] etablissements = getEtablissementMetierService().recupererEtablissementWSV2(null,null,
					null,dep,null,null,null).toArray(new EtablissementCompletDTO2[0]);

			trEtablissement = etablissements.length > 0 ? new TrEtablissementDTO(etablissements[0].getCodeEtb(),
					etablissements[0].getLibEtb(),
					etablissements[0].getDepartement().getCodeDept(),
					etablissements[0].getDepartement().getLibDept(),
					etablissements[0].getAcademie().getLibAcd(),
					etablissements[0].getLibOffEtb(),
					etablissements[0].getAdresse().getLibAd1Etb(),
					etablissements[0].getAdresse().getLibAd2Etb(),
					etablissements[0].getAdresse().getLibAd3Etb(),
					etablissements[0].getAdresse().getCodPosAdrEtb(),
					etablissements[0].getAdresse().getLibAchAdrEtb())
					: null;

		} catch (Exception e) {
			logger.error(e);
		}
		return trEtablissement;
	}

	@Override
	public TrBac getBaccalaureat(String supannEtuId){
		try {
			InfoAdmEtuDTO3 infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(supannEtuId);
			IndBacDTO[] indBacDTO = infoAdmEtuDTO.getListeBacs() != null
					? infoAdmEtuDTO.getListeBacs().getItem().toArray(new IndBacDTO[0]) : new IndBacDTO[0];

			if (logger.isDebugEnabled())
				for (IndBacDTO b : indBacDTO) logger.debug("indBacDTO.length = " + indBacDTO.length
						+ "\nindBacDTO[i].getLibelleBac() --> " + b.getLibelleBac()
						+ "\nindBacDTO[i].getDepartementBac().getLibDept() --> " + b.getDepartementBac().getLibDept()
						+ "\nindBacDTO[i].getEtbBac().getLibEtb() --> " + b.getEtbBac().getLibEtb()
						+ "\nindBacDTO[i].getAnneeObtentionBac() --> " + b.getAnneeObtentionBac());

			String etabBac = "ETRANGER";

			if(indBacDTO[0]!=null && !"ETRANGER".equals(indBacDTO[0].getDepartementBac().getLibDept()))
				etabBac = this.getEtablissementByDepartement(indBacDTO[0].getDepartementBac().getCodeDept()).getLibAcademie();

			return indBacDTO[0] != null ? new TrBac(indBacDTO[0].getCodBac(),
					indBacDTO[0].getLibelleBac(),
					indBacDTO[0].getDepartementBac().getLibDept(),
					indBacDTO[0].getEtbBac().getLibEtb(),
					indBacDTO[0].getAnneeObtentionBac(),
					etabBac) : null;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public TrInfosAdmEtu getInfosAdmEtu(String supannEtuId){
		// Recuperation des infos de l'etudiant dans Apogee
		InfoAdmEtuDTO3 infoAdmEtuDTO;
		try {
			infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(supannEtuId);

			NationaliteDTO nationaliteDTO = infoAdmEtuDTO.getNationaliteDTO();

			return new TrInfosAdmEtu(nationaliteDTO.getCodeNationalite(), nationaliteDTO.getLibNationalite());
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public String getComposante(String supannEtuId){
		logger.debug("String getComposante(String supannEtuId) \nsupannEtuId --> "+supannEtuId);
		try {
			String ret="";
			InsAdmEtpDTO3[] insAdmEtpDTO = getAdministratifMetierService().recupererIAEtapesV3(supannEtuId,null,
					"ARE","ARE").toArray(new InsAdmEtpDTO3[0]);

			for(int i=0; i<insAdmEtpDTO.length;i++) {
				if("oui".equals(insAdmEtpDTO[i].getEtapePremiere()))
					ret=insAdmEtpDTO[i].getComposante().getCodComposante();

				logger.debug("----------------------> "+insAdmEtpDTO[i].getComposante().getLibComposante()
						+ "\n----------------------> "+insAdmEtpDTO[i].getTemoinPI()
						+ "\n----------------------> "+insAdmEtpDTO[i].getTemoinVae()
						+ "\n----------------------> "+insAdmEtpDTO[i].getEtatIae().getCodeEtatIAE()
						+ "\n----------------------> "+insAdmEtpDTO[i].getEtatIae().getLibEtatIAE()
						+ "\n----------------------> "+insAdmEtpDTO[i].getEtatIaa().getCodeEtatIAA()
						+ "\n----------------------> "+insAdmEtpDTO[i].getEtatIaa().getLibEtatIAA()
						+ "\n----------------------> "+insAdmEtpDTO[i].getEtapePremiere()
						+ "\n----------------------> "+insAdmEtpDTO[i].getCge().getCodeCGE()
						+ "\n----------------------> "+insAdmEtpDTO[i].getCge().getLibCGE()
						+ "\n----------------------> "+insAdmEtpDTO[i].getComposante().getCodComposante()
						+ "\n############################################################################################");
			}
			return ret;
		} catch (Exception e) {
			logger.error(e);
			return codeComposanteInconnue;
		}
	}

	@Override
	public Map<String,String> getEtapePremiereAndCodeCgeAndLibCge(String supannEtuId){
		logger.debug("public Map<String,String> getEtapePremiereAndCodeCgeAndLibCge(String supannEtuId)\nsupannEtuId --> "+supannEtuId);
		try {
			InsAdmEtpDTO3[] insAdmEtpDTO = getAdministratifMetierService().recupererIAEtapesV3(supannEtuId,null,
					"ARE","ARE").toArray(new InsAdmEtpDTO3[0]);

			Map<String, String> map = new HashMap<>();
			for (InsAdmEtpDTO3 ins : insAdmEtpDTO) {
				if ("oui".equals(ins.getEtapePremiere())) {
					map.put("libWebVet", ins.getEtape().getLibWebVet());
					map.put("codeCGE", ins.getCge().getCodeCGE());
					map.put("libCGE", ins.getCge().getLibCGE());
					map.put("codeComposante", ins.getComposante().getCodComposante());
					map.put("libComposante", ins.getComposante().getLibComposante());
				}
				logger.debug("----------------------> " + ins.getComposante().getLibComposante()
						+ "\n----------------------> " + ins.getTemoinPI()
						+ "\n----------------------> " + ins.getTemoinVae()
						+ "\n----------------------> " + ins.getEtatIae().getCodeEtatIAE()
						+ "\n----------------------> " + ins.getEtatIae().getLibEtatIAE()
						+ "\n----------------------> " + ins.getEtatIaa().getCodeEtatIAA()
						+ "\n----------------------> " + ins.getEtatIaa().getLibEtatIAA()
						+ "\n----------------------> " + ins.getEtapePremiere()
						+ "\n----------------------> " + ins.getCge().getCodeCGE()
						+ "\n----------------------> " + ins.getCge().getLibCGE()
						+ "\n----------------------> " + ins.getEtape().getLibWebVet());
			}
			return map;
		} catch (Exception e) {
			logger.error(e);
			Map<String, String> map = new HashMap<>();
			map.put("libWebVet", "Inconnue");
			map.put("codeCGE", "Inconnue");
			map.put("libCGE", "Inconnue");
			map.put("codeComposante", "Inconnue");
			map.put("libComposante", "Inconnue");
			return map;
		}
	}

	@Override
	public TrResultatVdiVetDTO getSessionsResultats(String supannEtuId, String source) {
		logger.debug("getSessionsResultats ----- supannEtuId===>" + supannEtuId+"<===\ngetSessionsResultats ----- source===>" + source+"<===");

		int max = MAX_SESSIONS_RESULTAT_DEPART;
		if("A".equals(source)) max = MAX_SESSIONS_RESULTAT_ACCUEIL;
		ContratPedagogiqueResultatVdiVetDTO2[] contratPedagogiqueResultatVdiVetDTO;
		try {
			contratPedagogiqueResultatVdiVetDTO = getPedagogiqueMetierService().recupererContratPedagogiqueResultatVdiVetV2(
					supannEtuId,"toutes","Apogee","AET","toutes",null,"E")
					.toArray(new ContratPedagogiqueResultatVdiVetDTO2[0]);

			ContratPedagogiqueResultatVdiVetDTO2[] tab = new ContratPedagogiqueResultatVdiVetDTO2[9];
			logger.debug("contratPedagogiqueResultatVdiVetDTO.length -----> " + contratPedagogiqueResultatVdiVetDTO.length);

			if("A".equals(source) && contratPedagogiqueResultatVdiVetDTO.length > max) {
				int compteur=max-1;
				int diff = contratPedagogiqueResultatVdiVetDTO.length - max;
				for(int i = contratPedagogiqueResultatVdiVetDTO.length-1;i>=0;i--) {
					if(diff <= i) {
						logger.debug("########## i ########### ----->"+i);
						tab[compteur]=contratPedagogiqueResultatVdiVetDTO[i];
					}
					logger.debug("########## compteur ########### ----->"+compteur);
					compteur--;
				}
				contratPedagogiqueResultatVdiVetDTO=tab;
			}
		} catch (Exception e) {
			logger.error(e);
			contratPedagogiqueResultatVdiVetDTO=null;
		}
		TrResultatVdiVetDTO trResultatVdiVetDTO;
		List<ResultatEtape> listResultatEtape = new ArrayList<>();
		List<ResultatSession> listResultatSession;
		ResultatSession r;

		int nb = 0;

		if(contratPedagogiqueResultatVdiVetDTO!=null) {
			for(int i=contratPedagogiqueResultatVdiVetDTO.length-1;i>=0;i--) {
				EtapeResVdiVetDTO2[] etapeResVdiVetDTO = contratPedagogiqueResultatVdiVetDTO[i].getEtapes() != null
						? contratPedagogiqueResultatVdiVetDTO[i].getEtapes().getItem().toArray(new EtapeResVdiVetDTO2[0])
						: new EtapeResVdiVetDTO2[0];
//				ResultatVdiDTO[] resultatVdiDTO = contratPedagogiqueResultatVdiVetDTO[i].getResultatVdi() == null
//						? null : contratPedagogiqueResultatVdiVetDTO[i].getResultatVdi().getItem().toArray(new ResultatVdiDTO[0]);

				logger.debug("Diplome --> "+ etapeResVdiVetDTO.length +"-----> " + etapeResVdiVetDTO.length);

				for (EtapeResVdiVetDTO2 etapeResVdiVetDTO2 : etapeResVdiVetDTO) {
					logger.debug("année --> " + etapeResVdiVetDTO2.getCodAnu() + "/" + (Integer.parseInt(etapeResVdiVetDTO2.getCodAnu()) + 1)
							+ "\netape --> " + etapeResVdiVetDTO2.getEtape().getLibEtp()
							+ "\netapeResVdiVetDTO[j].getCodTypIpe() --> " + etapeResVdiVetDTO2.getCodTypIpe());

					ResultatVetDTO[] resultatVetDTO = etapeResVdiVetDTO2.getResultatVet() != null
							? etapeResVdiVetDTO2.getResultatVet().getItem().toArray(new ResultatVetDTO[0])
							: new ResultatVetDTO[0];
					listResultatSession = new ArrayList<>();

					if (nb <= max) {
						logger.debug("Etape non diplomante");

						for (ResultatVetDTO v : resultatVetDTO) {
							nb++;
							r = new ResultatSession();

							if (v.getSession() != null) {
								logger.debug("session --> " + v.getSession().getLibSes()
										+ "\nNote version etape --> " + v.getNotVet());
								r.setLibSession(v.getSession().getLibSes());
							} else {
								r.setLibSession("");
							}

							if (v.getTypResultat() != null && "T".equals(v.getEtatDelib().getCodEtaAvc())) {
								logger.debug("resultat --> " + v.getTypResultat().getLibTre()
										+ "\nEtat deliberation TTTT --> " + v.getEtatDelib().getCodEtaAvc()
										+ "\nNature Resultat TTTTT --> " + v.getNatureResultat().getCodAdm());
								r.setResultat(v.getTypResultat().getLibTre());
							} else if (v.getTypResultat() != null && !"T".equals(v.getEtatDelib().getCodEtaAvc())) {
								logger.debug("resultat --> " + v.getTypResultat().getLibTre()
										+ "\nEtat deliberation --> " + v.getEtatDelib().getCodEtaAvc()
										+ "\nNature Resultat --> " + v.getNatureResultat().getCodAdm()
										+ "\nEn attente de resultat ETAPE --> " + v.getSession().getLibSes());
								r.setResultat("Non dispo");
							} else {
								r.setResultat("");
							}

							if (v.getMention() != null && "T".equals(v.getEtatDelib().getCodEtaAvc())) {
								logger.debug("mention --> " + v.getMention().getCodMen()
										+ "\nmention --> " + v.getMention().getLibMen());
								r.setMention(v.getMention().getCodMen());
							} else {
								r.setMention("");
							}

							listResultatSession.add(r);
						}
						if (resultatVetDTO.length != 2) {
							ResultatSession tmp = new ResultatSession();
							tmp.setLibSession("");
							tmp.setResultat("");
							listResultatSession.add(tmp);
						}
					} else {
						logger.debug("Aucun resultat ou aucun diplome !!!");
						r = new ResultatSession();
						r.setLibSession("");
						r.setResultat("");
						r.setMention("");
						listResultatSession.add(r);
						r = new ResultatSession();
						r.setLibSession("");
						r.setResultat("");
						r.setMention("");
						listResultatSession.add(r);
					}
					if (nb <= max) listResultatEtape.add(new ResultatEtape(etapeResVdiVetDTO2.getCodAnu()
							+ "/" + (Integer.parseInt(etapeResVdiVetDTO2.getCodAnu()) + 1),
							etapeResVdiVetDTO2.getEtape().getLibEtp(), listResultatSession));
				}
			}
			trResultatVdiVetDTO = new TrResultatVdiVetDTO();
			trResultatVdiVetDTO.setEtapes(listResultatEtape);
			return trResultatVdiVetDTO;
		} else {
			trResultatVdiVetDTO = new TrResultatVdiVetDTO();
			trResultatVdiVetDTO.setEtapes(listResultatEtape);
			return trResultatVdiVetDTO;
		}
	}

	@Override
	public IndOpi getInfosOpi(String numeroEtudiant) {
		logger.debug("getInfosOpi(String numeroEtudiant)");
		VoeuxIns voeuxIns = new VoeuxIns();
		IndOpi indOpi = new IndOpi();
		InfoAdmEtuDTO3 infoAdmEtuDTO;
		try {
			infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(numeroEtudiant);
			/*OPI*/
			indOpi.setCodPayNat(infoAdmEtuDTO.getNationaliteDTO().getCodeNationalite());
			indOpi.setCodEtb(infoAdmEtuDTO.getEtbPremiereInscUniv().getCodeEtb());
			indOpi.setCodNneIndOpi(infoAdmEtuDTO.getNumeroINE());
			indOpi.setCodCleNneIndOpi("");
			indOpi.setDateNaiIndOpi(infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime());
			indOpi.setTemDateNaiRelOpi(infoAdmEtuDTO.getTemoinDateNaissEstimee());
			indOpi.setDaaEntEtbOpi(infoAdmEtuDTO.getAnneePremiereInscUniv());
			indOpi.setLibNomPatIndOpi(infoAdmEtuDTO.getNomPatronymique());
			indOpi.setLibNomUsuIndOpi(infoAdmEtuDTO.getNomUsuel());
			indOpi.setLibPr1IndOpi(infoAdmEtuDTO.getPrenom1());
			indOpi.setLibPr2IndOpi(infoAdmEtuDTO.getPrenom2());
			indOpi.setLibPr3IndOpi("");
			indOpi.setLibVilNaiEtuOpi(infoAdmEtuDTO.getLibVilleNaissance());
			/*Code pays ou département de naissance*/
			if(infoAdmEtuDTO.getPaysNaissance().getCodPay()!= null && !"100".equals(infoAdmEtuDTO.getPaysNaissance().getCodPay())) {
				indOpi.setCodDepPayNai(infoAdmEtuDTO.getPaysNaissance().getCodPay());
				indOpi.setCodTypDepPayNai("P");
			} else if(infoAdmEtuDTO.getDepartementNaissance().getCodeDept()!=null) {
				indOpi.setCodDepPayNai(infoAdmEtuDTO.getDepartementNaissance().getCodeDept());
				indOpi.setCodTypDepPayNai("D");
			} else {
				indOpi.setCodDepPayNai("");
				indOpi.setCodTypDepPayNai("");
			}
			indOpi.setDaaEnsSupOpi(infoAdmEtuDTO.getAnneePremiereInscEnsSup());
			indOpi.setCodSexEtuOpi(infoAdmEtuDTO.getSexe());
			indOpi.setDaaEtrSup(infoAdmEtuDTO.getAnneePremiereInscEtr());

			/*OPI_BAC*/
			IndBacDTO[] listeBacs = infoAdmEtuDTO.getListeBacs() != null
					? infoAdmEtuDTO.getListeBacs().getItem().toArray(new IndBacDTO[0]) : new IndBacDTO[0];
			indOpi.setCodBac(listeBacs[0].getCodBac());
			indOpi.setCodEtbBac(listeBacs[0].getEtbBac().getCodeEtb());
			indOpi.setCodDep(listeBacs[0].getDepartementBac().getCodeDept());
			indOpi.setCodMnb(listeBacs[0].getMentionBac().getCodMention());
			indOpi.setDaabacObtOba(listeBacs[0].getAnneeObtentionBac());
			indOpi.setCodTpe(listeBacs[0].getTypeEtbBac().getCodTypeEtb());
			indOpi.setVoeux(voeuxIns);
			return indOpi;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public List<String> getForcerBlocageListSplit() {
		return forcerBlocageListSplit;
	}

	public void setForcerBlocageListSplit(List<String> forcerBlocageListSplit) {
		this.forcerBlocageListSplit = forcerBlocageListSplit;
	}

	@Cacheable(cacheName = "getOffreDeFormation")
	public List<OffreDeFormationsDTO> getOffreDeFormation(String rne, Integer annee) {
		logger.debug("public List<OffreDeFormationsDTO> getOffreDeFormation(String rne, Integer annee)-->"+rne+"-----"+annee);
		List<OffreDeFormationsDTO> odfs = new ArrayList<>();
		DiplomeDTO3[] diplomeDTO3;
		VersionEtapeDTO3[] versionEtapeDTO2;
		try {
			SECritereDTO2 se = new SECritereDTO2();
			se.setCodAnu(annee.toString());
			se.setTemOuvertRecrutement("O");
			se.setCodEtp("tous");
			se.setCodVrsVet("tous");
			se.setCodDip("tous");
			se.setCodVrsVdi("tous");
			se.setCodElp("aucun");

			diplomeDTO3 = getOffreFormationMetierService().recupererSEV3(se).toArray(new DiplomeDTO3[0]);

			for(DiplomeDTO3 ld : diplomeDTO3) {
				VersionDiplomeDTO3[] versionDiplomeDTO3 = ld.getListVersionDiplome() != null
						? ld.getListVersionDiplome().getItem().toArray(new VersionDiplomeDTO3[0]) : new VersionDiplomeDTO3[0];
				logger.debug("CodTypDip --> "+ ld.getTypeDiplome().getCodTypDip()
						+ "\nLibTypDip --> "+ ld.getTypeDiplome().getLibTypDip()
						+ "\nLibDip --> "+ ld.getLibDip()
						+ "\nType diplome --> "+ ld.getTypeDiplome().getLibTypDip()
						+ "\nCycle --> "+ ld.getCycle().getLibCycle()
						+ "\nNature --> "+ ld.getNatureDiplome().getLibNatureDip()
						+ "\nld.getTypeDiplome().getLibTypDip() --> "+ ld.getTypeDiplome().getLibTypDip());

				for(VersionDiplomeDTO3 lvd : versionDiplomeDTO3) {
					logger.debug("lvd.getCodCursusLmd --> "+lvd.getCodCursusLmd() +"\nlvd.getLibWebVdi() --> "+lvd.getLibWebVdi());
					EtapeDTO3[] etapeDTO3 = lvd.getOffreFormation().getListEtape() != null
							? lvd.getOffreFormation().getListEtape().getItem().toArray(new EtapeDTO3[0]) : new EtapeDTO3[0];
					for(EtapeDTO3 le : etapeDTO3) {
						ComposanteCentreGestionDTO[] ccgOri = le.getListComposanteCentreGestion() != null
								? le.getListComposanteCentreGestion().getItem().toArray(new ComposanteCentreGestionDTO[0])
								: new ComposanteCentreGestionDTO[0];

						for(int i=0;i<ccgOri.length;i++) {
							logger.debug("--> le.getListComposanteCentreGestion()[i].getCodCentreGestion()-->"+ccgOri[i].getCodCentreGestion()
									+"\n--> ccgOri[i].getLibCentreGestion()-->"+ccgOri[i].getLibCentreGestion());
							VersionEtapeDTO32[] versionEtapeDTO21 = le.getListVersionEtape() != null
									? le.getListVersionEtape().getItem().toArray(new VersionEtapeDTO32[0])
									: new VersionEtapeDTO32[0];

							for(VersionEtapeDTO32 ve : versionEtapeDTO21) {
								if(ccgOri[i].getCodComposante().equals(ve.getComposante().getCodComposante())) {
									logger.debug("ve.getLibWebVet() --> "+ve.getLibWebVet());

									Integer codeNiveau = ve.getCodSisDaaMin();
									String libNiveau = codeNiveau == 1 ? codeNiveau+"er année" : codeNiveau+"ème année";
									String codComp = ve.getComposante().getCodComposante();
									String libComp = ve.getComposante().getLibComposante();

									logger.debug("codComp-->"+codComp + "\nlibComp-->"+libComp);
									odfs.add(new OffreDeFormationsDTO(rne,
											annee,
											ld.getTypeDiplome().getCodTypDip(),
											ld.getTypeDiplome().getLibTypDip(),
											ld.getCodDip(),
											lvd.getCodVrsVdi(),
											le.getCodEtp(),
											String.valueOf(ve.getCodVrsVet()),
											lvd.getLibWebVdi(),
											ve.getLibWebVet(),
											codComp,
											libComp,
											ccgOri[i].getCodCentreGestion(),
											ccgOri[i].getLibCentreGestion(),
											codeNiveau,
											libNiveau,
											"oui",
											"oui"));
								}
							}
						}
					}
				}
			}
			return odfs;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<IndOpi> synchroOpi(List<IndOpi> listeOpis){
		List<IndOpi> listeErreurs = new ArrayList<>();
		logger.debug("public void synchroOpi(List<IndOpi> listeOpis)"
				+ "########################### APPEL DU WS EN MODE AUTHENTIFIE ##################################"
				+ "\n-->this.user -->"+this.user+"<--\n-->this.password -->"+this.password+"<--"
				+ "\n############################################################################################");
		try {
			for(IndOpi opi : listeOpis) {
				DonneesOpiDTO9 donneesOpiDTO = new DonneesOpiDTO9();

				/*Initialisation de l'objet DonneesOpiDTO2 d'apogee a partir de l'objet OPI de esup-transferts*/
				/*#################### MAJOpiAdresseDTO - Adresse Fixe #################### */
				logger.debug("########################### MAJOpiAdresseDTO - Adresse Fixe ###########################"
						+ "\nopi.getCodBdi() --> "+opi.getCodBdi() + "\nopi.getCodCom() --> "+opi.getCodCom()
						+ "\nopi.getCodPay() --> "+opi.getCodPay() + "\nopi.getLibAd1() --> "+opi.getLibAd1()
						+ "\nopi.getLibAd2() --> "+opi.getLibAd2() + "\nopi.getLibAd3() --> "+opi.getLibAd3()
						+ "\nopi.getLibAde() --> "+opi.getLibAde() + "\nopi.getNumTel() --> "+opi.getNumTel()
						+ "\n#################################################################################");
				MAJOpiAdresseDTO opiAdresseFixeDTO = new MAJOpiAdresseDTO();
				opiAdresseFixeDTO.setCodBdi(opi.getCodBdi());
				opiAdresseFixeDTO.setCodCom(opi.getCodCom());
				opiAdresseFixeDTO.setCodPay(opi.getCodPay());
				opiAdresseFixeDTO.setLib1(opi.getLibAd1());
				opiAdresseFixeDTO.setLib2(opi.getLibAd2());
				opiAdresseFixeDTO.setLib3(opi.getLibAd3());
				opiAdresseFixeDTO.setLibAde(opi.getLibAde());
				opiAdresseFixeDTO.setNumTel(opi.getNumTel());
				donneesOpiDTO.setAdresseFixe(opiAdresseFixeDTO);

				/*#################### MAJOpiAdresseDTO - Adresse Annuelle ####################*/
				MAJOpiAdresseDTO opiAdresseAnnuelleDTO = new MAJOpiAdresseDTO();
				logger.debug("#################### MAJOpiAdresseDTO - Adresse Annuelle ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				donneesOpiDTO.setAdresseAnnuelle(opiAdresseAnnuelleDTO);

				/*#################### MAJOpiIndDTO ####################*/
				DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

				logger.debug("########################### MAJOpiIndDTO ###########################"
						+ "\n########################### MAJEtatCivilDTO ###########################"
						+ "\n!!! OBLIGATOIRE !!! opi.getNumeroOpi() --> "+opi.getNumeroOpi()
						+ "\nopi.getCodCleNneIndOpi() --> "+opi.getCodCleNneIndOpi()
						+ "\nopi.getCodNneIndOpi() --> "+opi.getCodNneIndOpi()
						+ "\nopi.getCodSexEtuOpi() --> "+opi.getCodSexEtuOpi()
						+ "\n!!! OBLIGATOIRE !!! opi.getLibNomPatIndOpi() --> "+opi.getLibNomPatIndOpi()
						+ "\nopi.getLibNomUsuIndOpi() --> "+opi.getLibNomUsuIndOpi()
						+ "\n!!! OBLIGATOIRE !!! opi.getLibPr1IndOpi() --> "+opi.getLibPr1IndOpi()
						+ "\nopi.getLibPr2IndOpi() --> "+opi.getLibPr2IndOpi()
						+ "\nopi.getLibPr3IndOpi() --> "+opi.getLibPr3IndOpi()
						+ "\nopi.getCodDepPayNai() --> "+opi.getCodDepPayNai()
						+ "\nopi.getCodPayNat() --> "+opi.getCodPayNat()
						+ "\nopi.getCodTypDepPayNai() --> "+opi.getCodTypDepPayNai()
						+ "\n!!! OBLIGATOIRE !!! opi.getDateNaiIndOpi().getTime() --> "+dateFormat.format(opi.getDateNaiIndOpi().getTime())
						+ "\nopi.getLibVilNaiEtuOpi() --> "+opi.getLibVilNaiEtuOpi()
						+ "\n!!! OBLIGATOIRE !!! opi.getTemDateNaiRelOpi() --> "+opi.getTemDateNaiRelOpi()
						+ "\n#################################################################################");

				MAJOpiIndDTO6 indDTO = new MAJOpiIndDTO6();
				indDTO.setCodEtuOpi(opi.getCodEtuLpa());
				indDTO.setCodOpiIntEpo(opi.getNumeroOpi()); // OBLIGATOIRE

				/*#################### MAJEtatCivilDTO #################### */
				MAJEtatCivilDTO2 etatCivilDTO = new MAJEtatCivilDTO2();
				etatCivilDTO.setCodNneIndOpi(opi.getCodNneIndOpi());
				etatCivilDTO.setCodSexEtuOpi(opi.getCodSexEtuOpi());
				etatCivilDTO.setLibNomPatIndOpi(opi.getLibNomPatIndOpi()); // OBLIGATOIRE
				etatCivilDTO.setLibNomUsuIndOpi(opi.getLibNomUsuIndOpi());
				etatCivilDTO.setLibPr1IndOpi(opi.getLibPr1IndOpi()); // OBLIGATOIRE
				etatCivilDTO.setLibPr2IndOpi(opi.getLibPr2IndOpi());
				etatCivilDTO.setLibPr3IndOpi(opi.getLibPr3IndOpi());
				indDTO.setEtatCivil(etatCivilDTO);
				/*#################### MAJDonneesNaissanceDTO ####################*/
				MAJDonneesNaissanceDTO2 donneesNaissanceDTO = new MAJDonneesNaissanceDTO2();
				donneesNaissanceDTO.setCodDepPayNai(opi.getCodDepPayNai());
				donneesNaissanceDTO.setCodPayNat(opi.getCodPayNat());
				donneesNaissanceDTO.setCodTypDepPayNai(opi.getCodTypDepPayNai());
				/* Depuis le module gestionnaire via les WS */
				String str[]=dateFormat.format(opi.getDateNaiIndOpi().getTime()).split("-");
				/*Si date format dd-mm-yyyy*/
				String date = str[0]+str[1]+str[2];
				donneesNaissanceDTO.setDateNaiIndOpi(date); // OBLIGATOIRE
				donneesNaissanceDTO.setLibVilNaiEtuOpi(opi.getLibVilNaiEtuOpi());
				donneesNaissanceDTO.setTemDateNaiRelOpi(opi.getTemDateNaiRelOpi());	// OBLIGATOIRE
				indDTO.setDonneesNaissance(donneesNaissanceDTO);

				/*#################### MAJPremiereInscriptionDTO ####################*/
				MAJPremiereInscriptionDTO premiereInscriptionDTO = new MAJPremiereInscriptionDTO();
				logger.debug("#################### MAJPremiereInscriptionDTO ####################"
						+ "\nopi.getCodEtb() --> "+opi.getCodEtb() + "\nopi.getDaaEnsSupOpi() --> "+opi.getDaaEnsSupOpi()
						+ "\nopi.getDaaEntEtbOpi() --> "+opi.getDaaEntEtbOpi() + "\nopi.getDaaEtrSup() --> "+opi.getDaaEtrSup()
						+ "\n############################################################");
				premiereInscriptionDTO.setCodEtb(opi.getCodEtb());
				premiereInscriptionDTO.setDaaEnsSupOpi(opi.getDaaEnsSupOpi());
				premiereInscriptionDTO.setDaaEntEtbOpi(opi.getDaaEntEtbOpi());
				premiereInscriptionDTO.setDaaEtrSup(opi.getDaaEtrSup());
				indDTO.setPremiereInscription(premiereInscriptionDTO);

				/*#################### MAJDonneesPersonnellesDTO2 ####################*/
				MAJDonneesPersonnellesDTO3 donneesPersonnellesDTO = new MAJDonneesPersonnellesDTO3();
				logger.debug("#################### MAJDonneesPersonnellesDTO2 ####################"
						+ "\nopi.getAdrMailOpi() --> "+opi.getAdrMailOpi() + "\nopi.getNumTelPorOpi() --> "+opi.getNumTelPorOpi()
						+ "\n############################################################");
				donneesPersonnellesDTO.setAdrMailOpi(opi.getAdrMailOpi());
				donneesPersonnellesDTO.setNumTelPorOpi(opi.getNumTelPorOpi());
				indDTO.setDonneesPersonnelles(donneesPersonnellesDTO);

				/*#################### MAJPrgEchangeDTO ####################*/
				MAJPrgEchangeDTO prgEchangeDTO = new MAJPrgEchangeDTO();
				logger.debug("#################### MAJPrgEchangeDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				indDTO.setPrgEchange(prgEchangeDTO);

				/*#################### MAJDernierEtbFrequenteDTO ####################*/
				MAJDernierEtbFrequenteDTO dernierEtbFrequenteDTO = new MAJDernierEtbFrequenteDTO();
				logger.debug("#################### MAJDernierEtbFrequenteDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				indDTO.setDernierEtbFrequente(dernierEtbFrequenteDTO);

				/*#################### MAJSituationAnnPreDTO ####################*/
				MAJSituationAnnPreDTO situationAnnPreDTO = new MAJSituationAnnPreDTO();
				logger.debug("#################### MAJSituationAnnPreDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				indDTO.setSituationAnnPre(situationAnnPreDTO);

				/*#################### MAJDernierDiplObtDTO ####################*/
				MAJDernierDiplObtDTO dernierDiplObtDTO = new MAJDernierDiplObtDTO();
				logger.debug("#################### MAJDernierDiplObtDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				indDTO.setDernierDiplObt(dernierDiplObtDTO);

				/*#################### MAJInscriptionParalleleDTO ####################*/
				MAJInscriptionParalleleDTO2 inscriptionParalleleDTO = new MAJInscriptionParalleleDTO2();
				logger.debug("#################### MAJInscriptionParalleleDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				indDTO.setInscriptionParallele(inscriptionParalleleDTO);
				donneesOpiDTO.setIndividu(indDTO);

				/*#################### MAJOpiDacDTO ####################*/
				MAJOpiDacDTO opiDacDTO = new MAJOpiDacDTO();
				logger.debug("#################### MAJOpiDacDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				donneesOpiDTO.setDac(opiDacDTO);

				/*#################### MAJOpiBacDTO ####################*/
				MAJOpiBacDTO opiBacDTO = new MAJOpiBacDTO();
				opiBacDTO.setCodBac(opi.getCodBac());
				opiBacDTO.setCodEtb(opi.getCodEtbBac());
				opiBacDTO.setCodDep(opi.getCodDep());
				opiBacDTO.setCodMention(opi.getCodMnb());
				opiBacDTO.setDaaObtBacOba(opi.getDaabacObtOba());
				logger.debug("#################### MAJOpiBacDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				donneesOpiDTO.setBac(opiBacDTO);

				/*#################### MAJOpiVoeuDTO ####################*/
				MAJOpiVoeuDTO3 opiVoeuDTO = new MAJOpiVoeuDTO3();
				logger.debug("########################### MAJOpiVoeuDTO ###########################"
						+ "\nopi.getVoeux().getCodCge() --> "+opi.getVoeux().getCodCge()
						+ "\nopi.getVoeux().getCodCmp() --> "+opi.getVoeux().getCodCmp()
						+ "\nopi.getVoeux().getCodDemDos() --> "+opi.getVoeux().getCodDemDos()
						+ "\nopi.getVoeux().getCodDecVeu() --> "+opi.getVoeux().getCodDecVeu()
						+ "\nopi.getVoeux().getCodDip() --> "+opi.getVoeux().getCodDip()
						+ "\nopi.getVoeux().getCodEtp() --> "+opi.getVoeux().getCodEtp()
						+ "\nInteger.parseInt(opi.getVoeux().getCodVrsVet()) --> "+Integer.parseInt(opi.getVoeux().getCodVrsVet())
						+ "\nopi.getVoeux().getCodVrsVdi() --> "+opi.getVoeux().getCodVrsVdi()
						+ "\nInteger.parseInt(opi.getVoeux().getNumCls()) --> "+Integer.parseInt(opi.getVoeux().getNumCls())
						+ "\n#################################################################################");
				opiVoeuDTO.setCodCge(opi.getVoeux().getCodCge()); // !!! OBLIGATOIRE !!!
				opiVoeuDTO.setCodCmp(opi.getVoeux().getCodCmp());
				opiVoeuDTO.setCodDemDos(opi.getVoeux().getCodDemDos()); // !!! OBLIGATOIRE !!!
				opiVoeuDTO.setCodDecVeu(opi.getVoeux().getCodDecVeu());
				opiVoeuDTO.setCodDip(opi.getVoeux().getCodDip());
				opiVoeuDTO.setCodEtp(opi.getVoeux().getCodEtp()); // !!! OBLIGATOIRE !!!
				opiVoeuDTO.setCodVrsVet(Integer.parseInt(opi.getVoeux().getCodVrsVet())); // !!! OBLIGATOIRE !!!
				opiVoeuDTO.setCodVrsVdi(opi.getVoeux().getCodVrsVdi());
				opiVoeuDTO.setNumCls(Integer.parseInt(opi.getVoeux().getNumCls())); // !!! OBLIGATOIRE !!!

				/*#################### MAJTitreAccesExterneDTO ####################*/
				logger.debug("#################### MAJTitreAccesExterneDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				MAJTitreAccesExterneDTO titreAccesExterneDTO = new MAJTitreAccesExterneDTO();
				opiVoeuDTO.setTitreAccesExterne(titreAccesExterneDTO);
				opiVoeuDTO.setLibCmtJur("TRANSFERTS");

				/*#################### MAJConvocationDTO ####################*/
				MAJConvocationDTO convocationDTO = new MAJConvocationDTO();
				logger.debug("#################### MAJConvocationDTO ####################"
						+ "\nOBJET VIDE" + "\n############################################################");
				opiVoeuDTO.setConvocation(convocationDTO);
				TableauVoeu3 tabVoeu3 = new TableauVoeu3();
				tabVoeu3.getItem().add(opiVoeuDTO);
				donneesOpiDTO.setVoeux(tabVoeu3);

				MAJOpiDacDTO majOpiDacDTO = new MAJOpiDacDTO();
				majOpiDacDTO.setCodNif(1);
				donneesOpiDTO.setDac(majOpiDacDTO);
				try {
					getOpiMetierService().mettreajourDonneesOpiV9(donneesOpiDTO);
				} catch (Exception e) {
					listeErreurs.add(opi);
					logger.error(e);
				}
			}
		} catch (Exception e){
			logger.error(e);
			return null;
		}
		return listeErreurs;
	}

	public List<TrBac> recupererBacOuEquWS(String codeBac) {
		try {
			BacOuEquDTO[] tabBacouEquiv = getScolariteMetierService().recupererBacOuEquWS(codeBac, null)
					.toArray(new BacOuEquDTO[0]);

			List<TrBac> lBac = new ArrayList<>();
			for (BacOuEquDTO bacOuEquDTO : tabBacouEquiv)
				lBac.add(new TrBac(bacOuEquDTO.getCodBac(), bacOuEquDTO.getLibBac()));

			return lBac;
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}

	public List<PersonnelComposante> recupererComposante(String uid, String diplayName, String mail, String source, Integer annee) {
		try {
			ComposanteDTO3[] comp = getReferentielMetierService().recupererComposanteV2(null,null).toArray(new ComposanteDTO3[0]);

			if (comp.length != 0) {
				List<PersonnelComposante> pc = new ArrayList<>();
				pc.add(new PersonnelComposante(uid, codeComposanteInconnue, source, annee, diplayName, mail, "Inconnue",
						0, "oui", "oui", "oui", "oui",
						"oui", "oui", "non", "non"));
				for (ComposanteDTO3 composanteDTO3 : comp)
					pc.add(new PersonnelComposante(uid, composanteDTO3.getCodCmp(), source, annee, diplayName, mail, composanteDTO3.getLibCmp(),
							0, "oui", "oui", "oui", "oui",
							"oui", "oui", "non", "non"));

				return pc;
			} else {
				return null;
			}
		} catch (Exception e){
			logger.error(e);
			return null;
		}
	}

	@Override
	public Integer getAuthEtu(String ine, Date dateNaissance) {
		Integer retour;
		logger.debug("Je suis dans le WS AMUE - getAuthEtu");
		String ineSansCle = ine.substring(0, ine.length()-1);
		String cleIne = ine.substring(ine.length()-1, ine.length());

		logger.debug("ineSansCle --> "+ ineSansCle + "\ncleIne --> "+ cleIne + "\ndateNaissance --> "+ dateNaissance);
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			IdentifiantsEtudiantDTO2 identifiantEtudiant = getEtudiantMetierService().recupererIdentifiantsEtudiantV2(
					null,null,ine,null,null,null,null,null,null);

			// Recup infos à partir des identifiants
			InfoAdmEtuDTO3 infoAdmEtuDTO = getEtudiantMetierService().recupererInfosAdmEtuV3(String.valueOf(identifiantEtudiant.getCodEtu()));

			logger.debug("ine --> " + ine);

			retour = dateFormat.format(dateNaissance).equals(
					dateFormat.format(infoAdmEtuDTO.getDateNaissance().toGregorianCalendar().getTime()))
					? 0 : 1;
			logger.debug(retour == 0 ? "Compare date OK" : "Compare date FAUX !!!");

			return retour;

		} catch (Exception e) {
			logger.error(e);
			retour=2;
			return retour;
		}
	}

	@Override
	public List<Composante> recupererListeComposantes(Integer annee, String source) {
		logger.debug("public List<Composante> recupererListeComposantes(Integer annee, String source)-->"+annee+"-----"+source);

		List<Composante> pc = null;
		try {
			ComposanteDTO3[] comp = getReferentielMetierService().recupererComposanteV2(null,null).toArray(new ComposanteDTO3[0]);
			if (comp.length != 0) {
				pc = new ArrayList<>();
				pc.add(new Composante(codeComposanteInconnue, source, annee, "Inconnue", "non"));
				for (int i = 0; i < comp.length; i++)
					pc.add(new Composante(comp[i].getCodCmp(), source, annee, comp[i].getLibCmp(), "non"));
			}
		} catch(Exception e){
			logger.error(e);
		}

		return pc;
	}

	@Override
	public List<CGE> recupererListeCGE(Integer annee, String source) {
		logger.debug("public List<CGE> recupererListeCGE(Integer annee, String source)-->"+annee+"-----"+source);

		try {
			CentreGestionDTO2[] cge = getReferentielMetierService().recupererCGE(null,null).toArray(new CentreGestionDTO2[0]);

			if (cge.length != 0) {
				List<CGE> lCGE = new ArrayList<>();
				lCGE.add(new CGE(codeComposanteInconnue, source, annee, "Inconnue", "non"));
				for (CentreGestionDTO2 c : cge) {
					logger.debug("cge[i].getCodCge() -> " + c.getCodCge() + " - cge[i].getLibCge() -> " + c.getLibCge());
					lCGE.add(new CGE(c.getCodCge(), source, annee, c.getLibCge(), "non"));
				}
				return lCGE;
			} else {
				return null;
			}
		} catch (Exception e){
			logger.error(e);
			return null;
		}
	}

	@Override
	public List<EtudiantRef> recupererListeEtudiants(String myAnnee, String codeDiplome, String versionDiplome, String codeEtape, String versionEtape) {
		logger.debug("public EtudiantRef recupererListeEtudiants(myAnnee, codeDiplome, versionDiplome, codeEtape, versionEtape)=>"
				+myAnnee+"---"+codeDiplome+"---"+versionDiplome+"---"+codeEtape+"---"+versionEtape+"<===");

		List<EtudiantRef> listeEtu = new ArrayList<>();
		try{
			EtudiantCritereDTO etuCritere = new EtudiantCritereDTO();
			etuCritere.setAnnee(myAnnee);

			EtudiantCritereListeDTO diplome = new EtudiantCritereListeDTO();
			diplome.setCode(codeDiplome);
			diplome.getListVersion().add(versionDiplome);
			TableauDiplomes tabDiplomes = new TableauDiplomes();
			tabDiplomes.getItem().add(diplome);
			etuCritere.setListDiplomes(tabDiplomes);

			EtudiantCritereListeDTO etape = new EtudiantCritereListeDTO();
			etape.getListVersion().add(versionEtape);
			etape.setCode(codeEtape);
			TableauEtapes tabEtapes = new TableauEtapes();
			tabEtapes.getItem().add(etape);
			etuCritere.setListEtapes(tabEtapes);

			EtudiantDTO2[] listeEtuDto2 =  getEtudiantMetierService().recupererListeEtudiants(etuCritere).toArray(new EtudiantDTO2[0]);

			for(EtudiantDTO2 etuDto2 : listeEtuDto2) {
				EtudiantRef etu = new EtudiantRef();
				etu.setNumeroEtudiant(etuDto2.getCodEtu());
				listeEtu.add(etu);
			}
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
		return listeEtu;
	}

	/**************************************************************
	 *  ACCESSORS
	 *************************************************************/
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	private EtudiantMetierServiceInterface getEtudiantMetierService() throws Exception {
		return etudiantMetierService == null ? new EtudiantMetierServiceInterfaceService(
				new URL(this.urlEtudiantMetierService)).getEtudiantMetier()
				: etudiantMetierService;
	}

	private AdministratifMetierServiceInterface getAdministratifMetierService() throws Exception {
		return administratifMetierService == null ? new AdministratifMetierServiceInterfaceService(
				new URL(this.urlAdministratifMetierService)).getAdministratifMetier()
				: administratifMetierService;
	}
	private GeographieMetierServiceInterface getGeographieMetierService() throws Exception {
		return geographieMetierService == null ? new GeographieMetierServiceInterfaceService(
				new URL(this.urlGeographieMetierService)).getGeographieMetier()
				: geographieMetierService;
	}
	private ScolariteMetierServiceInterface getScolariteMetierService() throws Exception {
		return scolariteMetierService == null ? new ScolariteMetierServiceInterfaceService(
				new URL(this.urlScolariteMetierService)).getScolariteMetier()
				: scolariteMetierService;
	}
	private PedagogiqueMetierServiceInterface getPedagogiqueMetierService() throws Exception {
		return pedagogiqueMetierService == null ? new PedagogiqueMetierServiceInterfaceService(
				new URL(this.urlPedagogiqueMetierService)).getPedagogiqueMetier()
				: pedagogiqueMetierService;
	}
	private EtablissementMetierServiceInterface getEtablissementMetierService() throws Exception {
		return etablissementMetierService == null ? new EtablissementMetierServiceInterfaceService(
				new URL(this.urlEtablissementMetierService)).getEtablissementMetier()
				: etablissementMetierService;
	}
	private OffreFormationMetierServiceInterface getOffreFormationMetierService() throws Exception {
		return offreFormationMetierService == null ? new OffreFormationMetierServiceInterfaceService(
				new URL(this.urlOffreFormationMetierService)).getOffreFormationMetier()
				: offreFormationMetierService;
	}
	private OpiMetierServiceInterface getOpiMetierService() throws Exception {
		opiMetierService = new OpiMetierServiceInterfaceService(new URL(this.urlOpiMetierService)).getOpiMetier();

		return opiMetierService == null ? new OpiMetierServiceInterfaceService(
				new URL(this.urlOpiMetierService)).getOpiMetier()
				: opiMetierService;
	}
	private ReferentielMetierServiceInterface getReferentielMetierService() throws Exception {
		return referentielMetierService == null ? new ReferentielMetierServiceInterfaceService(
				new URL(this.urlReferentielMetierService)).getReferentielMetier()
				: referentielMetierService;
	}
}