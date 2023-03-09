package org.esupportail.transferts.web.controllers;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.TrEtablissementDTO;
import org.primefaces.event.ItemSelectEvent;
import org.primefaces.model.chart.*;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StatistiqueController extends AbstractContextAwareController implements Serializable {  

	/**
	 * 
	 */
	private static final long serialVersionUID = 711226465339167551L;
	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(StatistiqueController.class);
	private PieChartModel pieModelDepart;  
	private PieChartModel pieModelAccueil;  
	private HorizontalBarChartModel categoryModelDetailsDepartAndArrive;  
	private BarChartModel categoryModelTotalDepartAndArrive;

	public StatistiqueController() {
	}  

	public final static synchronized String getInstance() {
		//une fois que l'instanciation est faite.
		return null;
	}

	public void itemSelect(ItemSelectEvent event) {  
		List<ChartSeries> allSeries = categoryModelDetailsDepartAndArrive.getSeries();
		ChartSeries selChartSeries = allSeries.get(event.getSeriesIndex());

		String rne = null;
		int j=0;
		for (Object mapKey : selChartSeries.getData().keySet()) {
			if(j==event.getItemIndex())
				rne=(String) mapKey;
			j++;
		}				    

		if (logger.isDebugEnabled()) {
			logger.debug("itemIndex -->"+event.getItemIndex());
			logger.debug("seriesIndex -->"+event.getSeriesIndex());
			logger.debug("Type de transferts -->"+selChartSeries.getLabel());
			logger.debug("Nombre total d'etablissments -->"+selChartSeries.getData().keySet().size());
			logger.debug("Rne -->"+rne);
		}
		String summary;
		String detail;

		TrEtablissementDTO etab = getDomainServiceScolarite().getEtablissementByRne(rne);

		if(etab!=null)
		{
			if (logger.isDebugEnabled())
				logger.debug("etab -->"+etab.toString());
			summary = etab.getLibEtb(); 
			detail = etab.getLibAcademie();
		}
		else
		{
			summary = "Etablissement inconnu"; 
			detail = "Etablissement inconnu";
		}
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);  
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}  

	private void categoryModelDetailsDepartAndArrive() {  
		categoryModelDetailsDepartAndArrive = new HorizontalBarChartModel();  

		ChartSeries statDepart = new ChartSeries();  
		statDepart.setLabel("Depart");  
		ChartSeries statArrive = new ChartSeries();  
		statArrive.setLabel("Arrive");  

		Map<String, Long> listeStatistiquesEtabDepart = getDomainService().getStatistiquesTransfert("D", getSessionController().getCurrentAnnee());
		Map<String, Long> listeStatistiquesEtabArrive = getDomainService().getStatistiquesTransfertAccueil("A", getSessionController().getCurrentAnnee());

		if (logger.isDebugEnabled())
		{
			logger.debug("####################### size listeStatistiquesEtabDepart -->"+listeStatistiquesEtabDepart.size());
			logger.debug("####################### size listeStatistiquesEtabArrive -->"+listeStatistiquesEtabArrive.size());
		}

		Set<String> intersect = new HashSet<String>(listeStatistiquesEtabDepart.keySet());  
		intersect.retainAll(listeStatistiquesEtabArrive.keySet()); 

		Set<String> diff1 = new HashSet<String>(listeStatistiquesEtabDepart.keySet());  
		diff1.removeAll(listeStatistiquesEtabArrive.keySet()); 

		Set<String> diff2 = new HashSet<String>(listeStatistiquesEtabArrive.keySet());  
		diff2.removeAll(listeStatistiquesEtabDepart.keySet());

		Set<String> union = new HashSet<String>(diff1);  
		union.addAll(diff2); 

		for(String s : intersect)
		{
			if (logger.isDebugEnabled())
				logger.debug("########## intersect ############# -->"+s);

			for (String mapKey : listeStatistiquesEtabDepart.keySet()) 
				statDepart.set(s, listeStatistiquesEtabDepart.get(s));	

			for (String mapKey : listeStatistiquesEtabArrive.keySet()) 
				statArrive.set(s, listeStatistiquesEtabArrive.get(s));	
		}

		for(String s : union)
		{
			if (logger.isDebugEnabled())
				logger.debug("########## union ############# -->"+s);

			if(listeStatistiquesEtabDepart.get(s)==null)
				statDepart.set(s, 0);
			else
				statDepart.set(s, listeStatistiquesEtabDepart.get(s));	

			if(listeStatistiquesEtabArrive.get(s)==null)
				statArrive.set(s, 0);
			else
				statArrive.set(s, listeStatistiquesEtabArrive.get(s));	
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("####################### size depart -->"+statDepart.getData().size());
			logger.debug("####################### size arrive -->"+statArrive.getData().size());
		}
		categoryModelDetailsDepartAndArrive.addSeries(statDepart);  
		categoryModelDetailsDepartAndArrive.addSeries(statArrive);   
		
        Axis xAxis = categoryModelDetailsDepartAndArrive.getAxis(AxisType.X);
        xAxis.setLabel("Nombre de demandes de transferts");
        xAxis.setMin(0);
        xAxis.setMax(500);
         
        Axis yAxis = categoryModelDetailsDepartAndArrive.getAxis(AxisType.Y);
        yAxis.setLabel("Liste des établissements");  
	} 	

	private void createCategoryModelTotalDepartAndArrive() {  
		categoryModelTotalDepartAndArrive = new BarChartModel();  

		categoryModelTotalDepartAndArrive.setTitle("Total");
		categoryModelTotalDepartAndArrive.setLegendPosition("ne");
         
        Axis xAxis = categoryModelTotalDepartAndArrive.getAxis(AxisType.X);
        xAxis.setLabel("Gender");
         
        Axis yAxis = categoryModelTotalDepartAndArrive.getAxis(AxisType.Y);
        yAxis.setLabel("Type de ");
        yAxis.setMin(0);
        yAxis.setMax(2000);
		
		ChartSeries totalDepart = new ChartSeries();
		ChartSeries totalAccueil = new ChartSeries();
		ChartSeries totalOPI = new ChartSeries();
		totalDepart.setLabel("Depart");
		totalAccueil.setLabel("Accueil");
		totalOPI.setLabel("OPI");

		Long nbDepart = getDomainService().getStatistiquesNombreTotalTransfertDepart(getSessionController().getCurrentAnnee());
		Long nbAccueil = getDomainService().getStatistiquesNombreTotalTransfertAccueil(getSessionController().getCurrentAnnee());
		Long nbOPI = getDomainService().getStatistiquesNombreTotalTransfertOPI(getSessionController().getCurrentAnnee());

		totalDepart.set("", nbDepart);  
		totalAccueil.set("", nbAccueil);  
		totalOPI.set("", nbOPI);  

		categoryModelTotalDepartAndArrive.addSeries(totalDepart);  
		categoryModelTotalDepartAndArrive.addSeries(totalAccueil);  
		categoryModelTotalDepartAndArrive.addSeries(totalOPI); 
	}  	

	public PieChartModel getPieModelDepart() {  
		createPieModelDepart();
		return pieModelDepart;  
	}  

	private void createPieModelDepart() {  
		pieModelDepart = new PieChartModel();  

		int demandesTransfertsByEnCoursAndAnnee = getDomainService().getDemandesTransfertsByEnCoursAndAnnee(getSessionController().getCurrentAnnee(), "D").intValue();
		int demandesTransfertsByAvisSaisieAndAnnee = getDomainService().getDemandesTransfertsByAvisSaisieAndAnnee(getSessionController().getCurrentAnnee(), "D").intValue();
		int demandesTransfertsByTerminerAndAnnee = getDomainService().getDemandesTransfertsByTerminerAndAnnee(getSessionController().getCurrentAnnee(), "D").intValue();

		pieModelDepart.set("Non traite", demandesTransfertsByEnCoursAndAnnee);
		pieModelDepart.set("En cours de traitement", demandesTransfertsByAvisSaisieAndAnnee);
		pieModelDepart.set("Traite", demandesTransfertsByTerminerAndAnnee);
		pieModelDepart.setTitle("Transferts départ"+getSessionController().getCurrentAnnee()+"/"+getSessionController().getCurrentAnnee()+1);
		pieModelDepart.setLegendPosition("w");

	}

	private void createPieModelAccueil() {  
		pieModelAccueil = new PieChartModel();  

		int demandesTransfertsByEnCoursAndAnnee = getDomainService().getDemandesTransfertsByEnCoursAndAnnee(getSessionController().getCurrentAnnee(), "A").intValue();
		int demandesTransfertsByAvisSaisieAndAnnee = getDomainService().getDemandesTransfertsByAvisSaisieAndAnnee(getSessionController().getCurrentAnnee(), "A").intValue();
		int demandesTransfertsByTerminerAndAnnee = getDomainService().getDemandesTransfertsByTerminerAndAnnee(getSessionController().getCurrentAnnee(), "A").intValue();

		if(demandesTransfertsByEnCoursAndAnnee==0)
			pieModelAccueil.set("Non traite", 1);  
		else
			pieModelAccueil.set("Non traite", demandesTransfertsByEnCoursAndAnnee);

		if(demandesTransfertsByAvisSaisieAndAnnee==0)
			pieModelAccueil.set("En cours de traitement", 1);  
		else
			pieModelAccueil.set("En cours de traitement", demandesTransfertsByAvisSaisieAndAnnee);

		if(demandesTransfertsByTerminerAndAnnee==0)
			pieModelAccueil.set("Traite", 1);
		else
			pieModelAccueil.set("Traite", demandesTransfertsByTerminerAndAnnee);
	}	

	public PieChartModel getPieModelAccueil() {
		createPieModelAccueil();
		return pieModelAccueil;
	}

	public void setPieModelAccueil(PieChartModel pieModelAccueil) {
		this.pieModelAccueil = pieModelAccueil;
	}

	public BarChartModel getCategoryModelTotalDepartAndArrive() {
		createCategoryModelTotalDepartAndArrive();
		return categoryModelTotalDepartAndArrive;
	}

	public void setCategoryModelTotalDepartAndArrive(BarChartModel categoryModelTotalDepartAndArrive) {
		this.categoryModelTotalDepartAndArrive = categoryModelTotalDepartAndArrive;
	}

	public CartesianChartModel getCategoryModelDetailsDepartAndArrive() {
		categoryModelDetailsDepartAndArrive();
		return categoryModelDetailsDepartAndArrive;
	}

	public void setCategoryModelDetailsDepartAndArrive(HorizontalBarChartModel categoryModelDetailsDepartAndArrive) {
		this.categoryModelDetailsDepartAndArrive = categoryModelDetailsDepartAndArrive;
	}
}  