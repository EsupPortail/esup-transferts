package org.esupportail.transferts.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.esupportail.transferts.dao.DaoService;
import org.esupportail.transferts.dao.JPADaoServiceImpl;
import org.esupportail.transferts.domain.DomainService;
import org.esupportail.transferts.domain.DomainServiceApogeeImpl;
import org.esupportail.transferts.domain.DomainServiceImpl;
import org.esupportail.transferts.domain.DomainServiceScolarite;
import org.esupportail.transferts.domain.beans.AccueilAnnee;
import org.esupportail.transferts.domain.beans.AccueilResultat;
import org.esupportail.transferts.domain.beans.DatasExterne;
import org.esupportail.transferts.domain.beans.EtudiantRef;
import org.esupportail.transferts.domain.beans.Fichier;
import org.esupportail.transferts.domain.beans.PersonnelComposante;
import org.esupportail.transferts.domain.beans.SituationUniversitaire;
import org.esupportail.transferts.domain.beans.TrBac;
import org.esupportail.transferts.domain.beans.User;
import org.esupportail.transferts.services.auth.Authenticator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:META-INF/testApplicationContext.xml")
public class AdministrationControllerTest  {

	@Autowired
	DomainService domainService; 

	@Autowired
	DomainServiceScolarite domainServiceScolarite;

	private EtudiantRef etudiantDepart;
	private EtudiantRef etudiantAccueil;
	
	EtudiantRef currentDemandeTransferts;
	
	List<EtudiantRef> listeEtudiants;

	@Before
	public void setUp() throws Exception {
		this.etudiantDepart=new EtudiantRef();
		
		this.etudiantAccueil=new EtudiantRef();
		etudiantAccueil.setNumeroEtudiant("2005030459J");
		etudiantAccueil.setAnnee(2014);
		etudiantAccueil.setSource("A");
	}

	@After
	public void tearDown() throws Exception {
	}

	
	//@Test
	public void stringToByte() 
	{
		this.etudiantAccueil.setNumeroEtudiant("0905061794V");
		
		System.out.println("===>getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.getEtudiantAccueil().getNumeroEtudiant(), this.getEtudiantAccueil().getAnnee(), this.getEtudiantAccueil().getSource());"
				+this.getEtudiantAccueil().getNumeroEtudiant()+"-----"+this.getEtudiantAccueil().getAnnee()+"-----"+this.getEtudiantAccueil().getSource()+"<===");		
		
		this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.getEtudiantAccueil().getNumeroEtudiant(), this.getEtudiantAccueil().getAnnee(), this.getEtudiantAccueil().getSource());
	    //String example = "This is an example";
	    byte[] bytes = this.currentDemandeTransferts.getTransferts().getFichier().getImg();
	    String test = bytes.toString();
	    
	    //System.out.println("Text : " + example);
	    System.out.println("###################################################################################################################");
	    System.out.println("Text [Byte Format] : " + bytes);
	    System.out.println("###################################################################################################################");
	    System.out.println("Text [Byte Format] : " + bytes.toString());
	    String s = Arrays.toString(bytes);
	    System.out.println("###################################################################################################################");
	    System.out.println("Text Decryted : " + s);
	    System.out.println("Text Decryted getBytes : " + s.getBytes());
		
	}
	
//	@Test
	public void getAllDemandesTransfertsByAnnee() 
	{
		System.out.println("===>#######################################################################################################################################<===");
		System.out.println("===>public void getAllDemandesTransfertsByAnnee()<===");	

		System.out.println("===>getDomainService().getAllDemandesTransfertsByAnnee(this.getEtudiantAccueil().getAnnee(), this.getEtudiantAccueil().getSource());"
							+this.getEtudiantAccueil().getNumeroEtudiant()+"-----"+this.getEtudiantAccueil().getAnnee()+"-----"+this.getEtudiantAccueil().getSource()+"<===");
		
		listeEtudiants = getDomainService().getAllDemandesTransfertsByAnnee(this.getEtudiantAccueil().getAnnee(), "A");

		for(EtudiantRef etu : listeEtudiants)
		{
//			System.out.println("etu.getNomPatronymique()===>"+etu.getNomPatronymique()+"<===");
//			System.out.println("etu.getAdresse()===>"+etu.getAdresse().toString()+"<===");
//			System.out.println("etu.getAdresse().getEmail()===>"+etu.getAdresse().getEmail()+"<===");
//			System.out.println("etu.getAccueil().getSituationUniversitaire()===>"+etu.getAccueil().getSituationUniversitaire()+"<===");
//			if(etu.getTransferts().getFichier()!=null)
//				System.out.println("etu.getTransferts().getFichier().getMd5()===>"+etu.getTransferts().getFichier().getMd5()+"<===");
//			else
//				System.out.println("etu.getTransferts().getFichier().getMd5()===>null<===");
//			System.out.println("etu===>"+etu.toString()+"<===");			
		}
	}

	@Test
	public void goToCurrentDemandeTransfertsAccueil() 
	{
		System.out.println("===>#######################################################################################################################################<===");
		System.out.println("===>public String goToCurrentDemandeTransfertsAccueil()<===");

		System.out.println("===>getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.getEtudiantAccueil().getNumeroEtudiant(), this.getEtudiantAccueil().getAnnee(), this.getEtudiantAccueil().getSource());"
				+this.getEtudiantAccueil().getNumeroEtudiant()+"-----"+this.getEtudiantAccueil().getAnnee()+"-----"+this.getEtudiantAccueil().getSource()+"<===");		
		this.currentDemandeTransferts = getDomainService().getDemandeTransfertByAnneeAndNumeroEtudiantAndSource(this.getEtudiantAccueil().getNumeroEtudiant(), this.getEtudiantAccueil().getAnnee(), this.getEtudiantAccueil().getSource());

		if (this.currentDemandeTransferts != null) 
		{
			System.out.println("aaaaa===>if (this.currentDemandeTransferts != null)<===");

			List<DatasExterne> listeDatasEterneNiveau2 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(this.currentDemandeTransferts.getNumeroIne(), 2);

			List<DatasExterne> listeDatasEterneNiveau3 = getDomainService().getAllDatasExterneByIdentifiantAndNiveau(this.currentDemandeTransferts.getNumeroIne(), 3);

			PersonnelComposante droitPC = getDomainService().getDroitPersonnelComposanteByUidAndSourceAndAnneeAndCodeComposante("farid.aitkarra",
					"A", 
					2014, 
					this.currentDemandeTransferts.getTransferts().getOdf().getCodeComposante());					

			Fichier file=null;
			//
			if(this.currentDemandeTransferts.getTransferts().getFichier()!=null)
				file = getDomainService().getFichierByIdAndAnneeAndFrom(this.currentDemandeTransferts.getTransferts().getFichier().getMd5(),this.getEtudiantAccueil().getAnnee(), this.currentDemandeTransferts.getSource());
			
			if(file!=null && file.getNom().equals("ETABLISSEMENT_PARTENAIRE"))
			{
				file = getDomainService().getFichierDefautByAnneeAndFrom(2014, "A");
				this.currentDemandeTransferts.getTransferts().setFichier(file);
			}

		
			

			List<TrBac> listeBacDTO = getDomainServiceScolarite().recupererBacOuEquWS(this.currentDemandeTransferts.getAccueil().getCodeBac());
			
			System.out.println("file===>"+file+"<===");
			
			System.out.println("this.currentDemandeTransferts===>"+this.currentDemandeTransferts.toString()+"<===");
			
			System.out.println("this.currentDemandeTransferts===>"+this.currentDemandeTransferts.toString()+"<===");	
		}
	}		

	//@Test
	//	public void testGetInfosEtudiant()
	//	{
	//		System.out.println("getDomainServiceScolarite().getEtablissementByDepartement(059)===>"+getDomainServiceScolarite().getEtablissementByDepartement("059")+"<===");
	//	}

	//	@Test
	//	public void testGetUser()
	//	{
	//		User user = null;
	//		try {
	//			user = authenticator.getUser();
	//		} catch (Exception e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		System.out.println("user===>"+user+"<===");
	//	}

	public DomainService getDomainService() {
		return domainService;
	}

	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}

	public DomainServiceScolarite getDomainServiceScolarite() {
		return domainServiceScolarite;
	}

	public void setDomainServiceScolarite(
			DomainServiceScolarite domainServiceScolarite) {
		this.domainServiceScolarite = domainServiceScolarite;
	}

	public EtudiantRef getCurrentDemandeTransferts() {
		return currentDemandeTransferts;
	}

	public void setCurrentDemandeTransferts(EtudiantRef currentDemandeTransferts) {
		this.currentDemandeTransferts = currentDemandeTransferts;
	}

	public EtudiantRef getEtudiantDepart() {
		return etudiantDepart;
	}

	public void setEtudiantDepart(EtudiantRef etudiantDepart) {
		this.etudiantDepart = etudiantDepart;
	}

	public EtudiantRef getEtudiantAccueil() {
		return etudiantAccueil;
	}

	public void setEtudiantAccueil(EtudiantRef etudiantAccueil) {
		this.etudiantAccueil = etudiantAccueil;
	}

	public List<EtudiantRef> getListeEtudiants() {
		return listeEtudiants;
	}

	public void setListeEtudiants(List<EtudiantRef> listeEtudiants) {
		this.listeEtudiants = listeEtudiants;
	}
}