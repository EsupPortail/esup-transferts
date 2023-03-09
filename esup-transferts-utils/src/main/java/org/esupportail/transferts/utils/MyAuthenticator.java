package org.esupportail.transferts.utils;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;

public class MyAuthenticator extends Authenticator {

	/**
	 * 
	 */
	private static final Logger logger = new LoggerImpl(MyAuthenticator.class);
	private String indentifiant;
	private String password;
	
	public MyAuthenticator(String indentifiant, String password)
	{
		if (logger.isDebugEnabled())
			logger.debug("######################### Je suis dans MyAuthenticator du package utils ################################");
		this.indentifiant=indentifiant;
		this.password=password;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		String promptString = getRequestingPrompt();
		String hostname = getRequestingHost();
		InetAddress ipaddr = getRequestingSite();
		int port = getRequestingPort();
		if (logger.isDebugEnabled())
		{
			logger.debug("promptString-->"+promptString);
			logger.debug("hostname-->"+hostname);
			logger.debug("ipaddr-->"+ipaddr);
			logger.debug("port-->"+port);
		}
		return new PasswordAuthentication(this.getIndentifiant(), this.getPassword().toCharArray());
	}

	public String getIndentifiant() {
		return indentifiant;
	}

	public void setIndentifiant(String indentifiant) {
		this.indentifiant = indentifiant;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
