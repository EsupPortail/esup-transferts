package org.esupportail.transferts.services.auth;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * @author Farid AIT KARRA (Universite de d'Artois) - 2011
 * 
 */ 
public class IpAddressInterceptor extends AbstractPhaseInterceptor<Message> implements InitializingBean {  

	@SuppressWarnings("unused")
	private final Logger logger = new LoggerImpl(this.getClass());
	private String allowedList;
	private String deniedList;
	private List<String> allowedListSplit = new ArrayList<String>();
	private List<String> deniedListSplit = new ArrayList<String>();

	public IpAddressInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(allowedList, "property allowedList of class "
				+ this.getClass().getName() + " can not be null");
		
		if(this.allowedList!=null && this.allowedList!="" && ((this.allowedList.split(",")).length>1))
		{
			String[] tokens = this.allowedList.split(",");
			for(int i=0; i<tokens.length; i++)
				this.allowedListSplit.add(tokens[i]);
		}
		else
			this.allowedListSplit.add(this.allowedList);	
		
		if(deniedList!=null && ((deniedList.split(",")).length>1))
		{
			String[] tokens = deniedList.split(",");
			for(int i=0; i<tokens.length; i++)
				this.deniedListSplit.add(tokens[i]);
		}
		else
			this.deniedListSplit.add(this.deniedList);
	}	
	
	public void handleMessage(Message message) 
	{
		if (logger.isDebugEnabled()) {
			logger.debug("Liste des adresses IP's autorisées " + allowedList);
			logger.debug("Liste des adresses IP's interdites " + deniedList);
		}
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);
		String ipAddress = request.getRemoteAddr(); 
		// récupération de l'adresse IP du client
		if (logger.isDebugEnabled()) {
			logger.debug("Adresse IP du client "+ipAddress);
		}
		// Vérification de(s) adresse(s) IP interdite(s) 
		for (String deniedIpAddress : deniedListSplit) {
			if (deniedIpAddress.equals(ipAddress)) {
				throw new Fault(new IllegalAccessException("L'adresse IP " + ipAddress + " n'est pas autorisé à accéder au WebService. Veuillez contacter l'administrateur système."));
			}
		}
		// Vérification de(s) adresse(s) IP authorisée(s) 
		if (!allowedListSplit.isEmpty()) {
			boolean contains = false;
			for (String allowedIpAddress : allowedListSplit) {
				if (allowedIpAddress.equals(ipAddress)) {
					contains = true;
					break;
				}
			}
			if (!contains) {
				throw new Fault(new IllegalAccessException("L'adresse IP " + ipAddress + " n'est pas autorisé à accéder au WebService. Veuillez contacter l'administrateur système."));
			}
		}
	}

	public void setDeniedList(String deniedList) {
		this.deniedList = deniedList;
	}

	public String getDeniedList() {
		return deniedList;
	}

	public void setAllowedList(String allowedList) {
		this.allowedList = allowedList;
	}

	public void setAllowedList2(String allowedList) {
		this.allowedList = allowedList;
	}	
	
	public String getAllowedList() {
		return allowedList;
	}

	public void setAllowedListSplit(List<String> allowedListSplit) {
		this.allowedListSplit = allowedListSplit;
	}

	public List<String> getAllowedListSplit() {
		return allowedListSplit;
	}

	public void setDeniedListSplit(List<String> deniedListSplit) {
		this.deniedListSplit = deniedListSplit;
	}

	public List<String> getDeniedListSplit() {
		return deniedListSplit;
	}
}