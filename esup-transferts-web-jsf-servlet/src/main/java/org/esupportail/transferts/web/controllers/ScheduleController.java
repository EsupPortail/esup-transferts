package org.esupportail.transferts.web.controllers;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.transferts.domain.beans.Fermeture;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ManagedBean
@ViewScoped
public class ScheduleController extends AbstractContextAwareController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1445412231455212157L;

	/**
	 * A logger.
	 */
	private static final Logger logger = new LoggerImpl(ScheduleController.class);

	private transient ScheduleModel eventModel;

	private transient ScheduleEvent event = new DefaultScheduleEvent();

	private List<Fermeture> listeFermetures;
	
	private String source;
	
	private Integer anneeEnCours;

//	@PostConstruct
	public void init() {
		if (logger.isDebugEnabled())
			logger.debug("===>@PostConstruct---public void init() {<===");
		eventModel = new DefaultScheduleModel();
		listeFermetures = getFermetures();

		if(listeFermetures!=null)
		{
			for(Fermeture f : listeFermetures)
			{
				if (logger.isDebugEnabled())
					logger.debug("fermetures---f.getIdScheduler()===>"+f.getIdScheduler()+"<===");
				ScheduleEvent e = new DefaultScheduleEvent(f.getTitre(), f.getDateDebut(), f.getDateFin());
				e.setId(f.getIdScheduler());
				eventModel.getEvents().add(e);
			}

			if (logger.isDebugEnabled()) {
				for (int i = 0; i < eventModel.getEventCount(); i++) {
					logger.debug("eventModel---eventModel.getEvents().get(i).getId()===>" + eventModel.getEvents().get(i).getId() + "<===");
				}
			}
		}
	}

	public String goToFermetureAppliSchedulerDepart() 
	{
		if (logger.isDebugEnabled())
			logger.debug("goToFermetureAppliSchedulerDepart");
		setSource("D");
		this.init();
		return "goToFermetureAppli";
	}		

	public String goToFermetureAppliSchedulerAccueil() 
	{
		if (logger.isDebugEnabled())
			logger.debug("goToFermetureAppliSchedulerAccueil");
		setSource("A");
		this.init();
		return "goToFermetureAppli";
	}	
	
	public void addPeriodeFermeture(){
		if (logger.isDebugEnabled())
			logger.debug("===> public void addPeriodeFermeture(){<===");
		List<Fermeture> l1 = new ArrayList<Fermeture>();

		if (logger.isDebugEnabled())
			logger.debug("eventModel---getEventCount===>"+eventModel.getEventCount()+"<===");
		for(int i = 0;i<eventModel.getEventCount();i++){
			if (logger.isDebugEnabled())
				logger.debug("eventModel---eventModel.getEvents().get(i).getId()===>"+eventModel.getEvents().get(i).getId()+"<===");
			Fermeture tmp = new Fermeture();
			tmp.setIdScheduler(eventModel.getEvents().get(i).getId());
			tmp.setSource(this.getSource());
			tmp.setAnnee(this.getAnneeEnCours());
			tmp.setTitre(eventModel.getEvents().get(i).getTitle());
			tmp.setDateDebut(eventModel.getEvents().get(i).getStartDate());
			tmp.setDateFin(eventModel.getEvents().get(i).getEndDate());
			tmp.setAllDay(eventModel.getEvents().get(i).isAllDay());

			if (logger.isDebugEnabled()) {
				logger.debug("l1---eventModel.getEvents().get(i).getId()===>" + eventModel.getEvents().get(i).getId() + "<===");
				logger.debug("===>#####################################################################################################################<===");
			}
			l1.add(tmp);
		}    	
		listeFermetures = getDomainService().addPeriodeFermetures(l1);
	}

	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}

	private Date today1Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.HOUR, 1);
		t.set(Calendar.MINUTE, 0);
		return t.getTime();
	}

	private Date today2Pm() {
		Calendar t = (Calendar) today().clone();
		t.set(Calendar.AM_PM, Calendar.PM);
		t.set(Calendar.HOUR, 2);
		t.set(Calendar.MINUTE, 0);
		return t.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) {
		if(event.getId() == null)
			eventModel.addEvent(event);
		else
			eventModel.updateEvent(event);
		this.addPeriodeFermeture();
		event = new DefaultScheduleEvent();
	}

	public void deleteEvent(ActionEvent actionEvent) {
		if (logger.isDebugEnabled())
			logger.debug("===>public void deleteEvent(ActionEvent actionEvent) {<===");
		if(event.getId()!= null)
		{
			if (logger.isDebugEnabled())
				logger.debug("===>event"+event.getTitle()+"<===");
			eventModel.deleteEvent(event);
		}

		if(event.getId()!=null)
		{
			getDomainService().deletePeriodeFermeture(event.getId());
			event = new DefaultScheduleEvent();
		}
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		Date du = (Date) selectEvent.getObject();
		Date au = (Date) du.clone();
//		du.setTime(System.currentTimeMillis());
//		au.setTime(1400);
		au.setTime(au.getTime() + 86340000);
//		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject() , (Date) selectEvent.getObject());
		event = new DefaultScheduleEvent("", du , au);
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		eventModel.updateEvent(event.getScheduleEvent());
		this.addPeriodeFermeture();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}


	public List<Fermeture> getFermetures() {
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date());
		int year = c.get(Calendar.YEAR); //A vérifier!!!!

		if (logger.isDebugEnabled()) {
			logger.debug("date et heure===>" + c + "<===");
			logger.debug("annee===>" + year + "<===");
			logger.debug("getSource()===>" + getSource() + "<===");
		}

		this.setAnneeEnCours(year);
		
		return getDomainService().getListeFermeturesBySourceAndAnnee(getSource(), year);
	}


	public void setFermetures(List<Fermeture> fermetures) {
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getAnneeEnCours() {
		return anneeEnCours;
	}

	public void setAnneeEnCours(Integer anneeEnCours) {
		this.anneeEnCours = anneeEnCours;
	}
}