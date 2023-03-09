package org.esupportail.transferts.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.ReflectionUtils;
import sun.net.www.protocol.http.AuthCacheImpl;
import sun.net.www.protocol.http.AuthCacheValue;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by farid on 29/03/2016.
 */
public class Fonctions {

    /**
     * A logger.
     */
    private static final Logger logger = new LoggerImpl(Fonctions.class);
    public static final String MSG_ERREURS = "Le service est indisponible, aucune vérification n'est possible pour le moment.";

    public static Map<String, String> stringSplitToMap(String stringAsSplit, String split, boolean actif)
    {
        if(stringAsSplit!=null)
            if (logger.isDebugEnabled())
                logger.debug("Etablissement ajouté manuellement===>"+stringAsSplit+"<===");

        String[] tokens;
        Map <String, String> listEtabAjouteManuellement = new HashMap<String, String>();

        if(stringAsSplit!=null && stringAsSplit!="" && actif && ((stringAsSplit.split(split)).length>1))
        {
            tokens = stringAsSplit.split(split);
            for(int i=0; i<tokens.length; i++)
            {
                if (logger.isDebugEnabled())
                    logger.debug("tokens[n]===>"+tokens[i]+"<===");
                try{
                    listEtabAjouteManuellement.put(tokens[i].substring(0,tokens[i].indexOf('@')),tokens[i].substring(tokens[i].indexOf('@')+1));
                }catch(Exception e){
                    logger.error(e);
                    String summary = "Erreurs de syntaxe dans la liste des établissements ajoutés manuellements";
                    String detail = "Erreurs de syntaxe dans la liste des établissements ajoutés manuellements";
                    FacesMessage.Severity severity = FacesMessage.SEVERITY_WARN;
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage(severity, summary, detail));
                    context.getExternalContext().getFlash().setKeepMessages(true);
                    return null;
                }
            }
        }
        else if(stringAsSplit!=null && stringAsSplit!="" && actif && ((stringAsSplit.split(split)).length==1))
        {
            if (logger.isDebugEnabled())
                logger.debug("tokens===>"+stringAsSplit+"<===");
            try{
                listEtabAjouteManuellement.put(stringAsSplit.substring(0,stringAsSplit.indexOf('@')),stringAsSplit.substring(stringAsSplit.indexOf('@')+1));
            }catch(Exception e){
                logger.error(e);
                String summary = "Erreurs de syntaxe dans la liste des établissements ajoutés manuellements";
                String detail = "Erreurs de syntaxe dans la liste des établissements ajoutés manuellements";
                FacesMessage.Severity severity = FacesMessage.SEVERITY_WARN;
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage(severity, summary, detail));
                context.getExternalContext().getFlash().setKeepMessages(true);
                return null;
            }
        }
        else
            listEtabAjouteManuellement=null;

        return listEtabAjouteManuellement;
    }

    public static List<String> stringSplitToArrayList(String stringAsSplit, String split)
    {
        List<String> listString = new ArrayList<String>();
        if(stringAsSplit!=null && stringAsSplit!="" && ((stringAsSplit.split(split)).length>1))
        {
            String[] tokens = stringAsSplit.split(split);
            for(int i=0; i<tokens.length; i++)
                listString.add(tokens[i]);
        }
        else
            listString.add(stringAsSplit);

        return listString;
    }

    public static Class[] verifTypeOfClass(Object... params)
    {
        Class t[] = new Class[params.length];
        for(int i=0; i<params.length; i++)
        {
            if(params[i] instanceof Timestamp)
                t[i]= Date.class;
            else
                t[i]=params[i].getClass();

            logger.debug("Class t[]-->" + t);
            logger.debug("Class t[] ## " + i + " ##");
            logger.debug("Class t[]-->" + t[i].toString());
            logger.debug("Class t[]-->" + t[i].getName());
            logger.debug("Class t[]-->" + t[i].getClass());
        }
        return t;
    }

    public static Object[] appelWSAuth(String wsUrl, String wsLogin, String wsPwd, String maClass, String maMethode, String collection, Integer timeOutConnexionWs, Object... params)
    {
        if (logger.isDebugEnabled())
            logger.debug("===>appelWSAuth<===");
        Authenticator.setDefault(new MyAuthenticator(wsLogin, wsPwd));

        if (logger.isDebugEnabled()) {
            logger.debug("wsUrl===>" + wsUrl + "<===");
            logger.debug("wsLogin===>" + wsLogin + "<===");
            logger.debug("maClass===>" + maClass + "<===");
            logger.debug("maMethode===>" + maMethode + "<===");
            logger.debug("collection===>" + collection + "<===");
            logger.debug("timeOutConnexionWs.toString()===>" + timeOutConnexionWs.toString() + "<===");
        }

        Object tabReturn[] = new Object[2];
        List<Object> objectRetourList = new ArrayList<Object>();
        Object objectRetour = new Object();

        Integer online=0;

        Class cls=null;
        try {
            cls = Class.forName(maClass);
            if (logger.isDebugEnabled()){
                logger.debug("cls===>"+cls+"<===");
                logger.debug("params===>"+Arrays.toString(params)+"<===");
            }

            if (testUrl(wsUrl, timeOutConnexionWs))
            {
                if (logger.isDebugEnabled())
                    logger.debug("WebServices.appelWSPartenaires");
                try {
                    String address = wsUrl;
                    JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
                    factoryBean.setServiceClass(cls);
                    factoryBean.setAddress(address);
                    Object monService = factoryBean.create();

                    Class t[]=verifTypeOfClass(params);

                    Method m = ReflectionUtils.findMethod(cls, maMethode, t);

                    if (logger.isDebugEnabled()) {
                        logger.debug("WebServices.monService-->" + monService);
                        logger.debug("maMethode-->" + maMethode);
                        logger.debug("Method m===>"+m+"<===");
                    }

                    if("arrayList".equals(collection)) {
                        try {
                            objectRetourList = (List<Object>) ReflectionUtils.invokeMethod(m, monService, params);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }else {
                        try {
                            objectRetour = (Object) ReflectionUtils.invokeMethod(m, monService, params);
                        } catch (Exception e) {
                            logger.error(e);
                        }
                    }

                    if(logger.isDebugEnabled()) {
                        logger.debug("objectRetourList===>" + objectRetourList + "<===");
                        logger.debug("objectRetour===>" + objectRetour + "<===");
                    }
                    online=1;
                }
                catch (Exception e)
                {
                    logger.error(e);
                }
            }
            else
            {
                int codeErr = codeErreurHttp(wsUrl, timeOutConnexionWs);
                if (logger.isDebugEnabled())
                    logger.debug("codeErr===>"+codeErr+"<===");
            }

        }
        catch(Exception e )
        {
            logger.error(e);
        }
        AuthCacheValue.setAuthCache(new AuthCacheImpl());
        Authenticator.setDefault(null);

        if("arrayList".equals(collection))
            tabReturn[0]=objectRetourList;
        else
            tabReturn[0]=objectRetour;
        tabReturn[1]=online;
        return tabReturn;
    }

    public static Object[] soso(String maClass, Object... params)
    {
        Class cls=null;
        try {
            cls = Class.forName(maClass);
            if (logger.isDebugEnabled())
            {
                logger.debug("maClass===>" + maClass + "<===");
                logger.debug("cls===>" + cls + "<===");
            }
        }catch (Exception e){
            logger.error(e);
        }

        return null;
    }

    public static boolean testUrl(String host, Integer timeOutConnexionWs) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
            conn.setConnectTimeout(timeOutConnexionWs);
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
    }

    private static int codeErreurHttp(String host, Integer timeOutConnexionWs) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(host).openConnection();
            conn.setConnectTimeout(timeOutConnexionWs);
            conn.connect();
            return conn.getResponseCode();
        } catch (MalformedURLException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("MalformedURLException");
                logger.debug("host : " + host);
            }
            logger.error(e);
            return 0;
        } catch (IOException e) {
            if (logger.isDebugEnabled()) {
                logger.debug("IOException");
                logger.debug("host : " + host);
            }
            logger.error(e);
            return 0;
        }
    }
}
