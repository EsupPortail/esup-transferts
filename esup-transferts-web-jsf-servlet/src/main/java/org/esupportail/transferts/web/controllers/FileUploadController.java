package org.esupportail.transferts.web.controllers;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.Fichier;
import org.esupportail.transferts.web.dataModel.SignatureDataModel;
import org.esupportail.transferts.web.utils.FileHashSum;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.activation.MimetypesFileTypeMap;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import java.io.*;

public class FileUploadController extends AbstractContextAwareController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6581188097632034681L;
	private static final int BUFFER_SIZE = 6124;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(FileUploadController.class);
	private String wsdl;
	private Fichier fichier = new Fichier();
	private transient UploadedFile file;
	private String source;
	private Fichier selectedFichier;
	private Fichier selectedFichierDelete;
	
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}	

	public String goToSignatureDepart(){
		setSource("D");
		return "goToSignature";
	}

	public String goToSignatureAccueil(){
		setSource("A");
		return "goToSignature";
	}	

	public void updateDefautFichier() 
	{
		if (this.selectedFichier != null) 
		{
			this.selectedFichier.setDefaut(true);
			getDomainService().updateDefautFichier(this.selectedFichier, getSessionController().getCurrentAnnee(), getSource());
			String summary = getString("ENREGISTREMENT.SIGNATURE_PAR_DEFAUT");
			String detail = getString("ENREGISTREMENT.SIGNATURE_PAR_DEFAUT");
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		} 
		else 
		{
			String summary = getString("ERREUR.SIGNATURE_PAR_DEFAUT");
			String detail = getString("ERREUR.SIGNATURE_PAR_DEFAUT");
			Severity severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
		}
	}	
	
	public String deleteFichier()
	{
		try{
			getDomainService().deleteFichier(selectedFichierDelete.getMd5(),getSessionController().getAnnee(), getSource());
			String summary = getString("SUPPRESSION.SIGNATURE_OK");
			String detail = getString("SUPPRESSION.SIGNATURE_OK");			
			Severity severity = FacesMessage.SEVERITY_INFO;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));			
		}
		catch (Exception e) {
			logger.error(e);
			if(e.getMessage().contains("org.hibernate.exception.ConstraintViolationException"))
			{
				String summary = getString("SUPPRESSION.SIGNATURE_ECHEC");
				String detail = getString("SUPPRESSION.SIGNATURE_ECHEC");
				Severity severity = FacesMessage.SEVERITY_ERROR;
				FacesContext context = FacesContext.getCurrentInstance();
				context.addMessage(null, new FacesMessage(severity, summary, detail));
				context.getExternalContext().getFlash().setKeepMessages(true);
			}
			else
			{
                if (logger.isDebugEnabled())
				    logger.debug(e.getMessage());
			}
		}
		return "goToSignature";
	}
	
	public Fichier getSelectedFichier() {
		return getDomainService().getFichierDefautByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource());
	}	

	public void handleMsg(FileUploadEvent event)
	{
		String summary = "Attention : ";
		String detail = "vous devez utiliser le bouton Envoyer le fichier sur le serveur. Votre signature n'a pas été enregistrée";
		Severity severity=FacesMessage.SEVERITY_WARN;
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));			
	}

	public void handleFileUpload() {
		if(file!=null)
		{
			String chemin = this.getTempPath() + file.getFileName();
			File result = new File(chemin);
			logger.debug("file.getFileName() --> " + file.getFileName());
			FileOutputStream fileOutputStream=null;
			try {
				fileOutputStream = new FileOutputStream(result);

				byte[] buffer = new byte[BUFFER_SIZE];

				int bulk;
				InputStream inputStream = file.getInputstream();
				while (true) {
					bulk = inputStream.read(buffer);
					if (bulk < 0) {
						break;
					}
					fileOutputStream.write(buffer, 0, bulk);
					fileOutputStream.flush();
				}
				fileOutputStream.close();
				inputStream.close();

				if (logger.isDebugEnabled()) {
					logger.debug("Libellé --> " + fichier.getLibelle());
					logger.debug("Result --> " + result.getAbsolutePath() + " // " + result.getName() + " // " + result.toString());
					logger.debug("MD5 --> " + FileHashSum.md5sum(result));
					logger.debug("CurrentAnnee --> "+ getSessionController().getCurrentAnnee());
					logger.debug("NOM --> " + result.getName());
					logger.debug("TAILLE --> " + result.getUsableSpace());
					logger.debug("CHEMIN --> " + result.getAbsolutePath());
					logger.debug("Mime Type of " + result.getName() + " is " +
							new MimetypesFileTypeMap().getContentType(result));
				}			
				String type = new MimetypesFileTypeMap().getContentType(result);
				if("image/jpeg".equalsIgnoreCase(type) || "image/gif".equalsIgnoreCase(type) || "application/octet-stream".equalsIgnoreCase(type))
				{
					
					fichier.setMd5(FileHashSum.md5sum(result));
					fichier.setNom(result.getName());
					fichier.setTaille(result.getUsableSpace());
					fichier.setAnnee(getSessionController().getCurrentAnnee());
					fichier.setFrom(getSource());
					fichier.setImg(file.getContents());

					this.getDomainService().addFichier(fichier);

					String summary = getString("ENREGISTREMENT.SIGNATURE");
					String detail = getString("ENREGISTREMENT.SIGNATURE");
					Severity severity=FacesMessage.SEVERITY_INFO;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));					
					this.fichier=new Fichier();
				}
				else
				{
					String summary = getString("ERREUR.ENREGISTREMENT_FORMAT_SIGNATURE", result.getName());
					String detail = getString("ERREUR.ENREGISTREMENT_FORMAT_SIGNATURE", result.getName());
					Severity severity=FacesMessage.SEVERITY_ERROR;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));					
				}

			} catch (IOException e) {
				logger.error(e);
				String summary = getString("ERREUR.ENREGISTREMENT_SIGNATURE", result.getName());
				String detail = getString("ERREUR.ENREGISTREMENT_SIGNATURE", result.getName());
				Severity severity=FacesMessage.SEVERITY_ERROR;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));				
			}
			finally {
				try {
					if(fileOutputStream!=null)
						fileOutputStream.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}
		else
		{
			String summary = getString("ERREUR.SELECTION_FICHIER");
			String detail = getString("ERREUR.SELECTION_FICHIER");
			Severity severity=FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity,summary, detail));			
		}
	} 	

	public SignatureDataModel getListeFichiers2() {
		if(getDomainService().getFichiersByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource())!=null)
			return new SignatureDataModel(getDomainService().getFichiersByAnneeAndFrom(getSessionController().getCurrentAnnee(), getSource()));
		else
			return null;
	}

	public void setWsdl(String wsdl) {
		this.wsdl = wsdl;
	}

	public String getWsdl() {
		return wsdl;
	}

	public void setFichier(Fichier fichier) {
		this.fichier = fichier;
	}

	public Fichier getFichier() {
		return fichier;
	}		

	public UploadedFile getFile() {  
		return file;  
	}  

	public void setFile(UploadedFile file) {  
		this.file = file;  
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setSelectedFichier(Fichier selectedFichier) {
		this.selectedFichier = selectedFichier;
	}

	public Fichier getSelectedFichierDelete() {
		return selectedFichierDelete;
	}

	public void setSelectedFichierDelete(Fichier selectedFichierDelete) {
		this.selectedFichierDelete = selectedFichierDelete;
	} 	
}  


