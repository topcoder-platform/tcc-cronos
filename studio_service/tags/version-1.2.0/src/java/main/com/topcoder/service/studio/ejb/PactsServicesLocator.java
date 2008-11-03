/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.ejb;

import com.topcoder.web.ejb.pacts.PactsClientServices;
import com.topcoder.web.ejb.pacts.PactsClientServicesHome;

import javax.ejb.CreateException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import java.lang.IllegalArgumentException;

/**
 * Locator for pacts services
 *
 * @author aisacovich
 * @version 1.0
 */
public class PactsServicesLocator {
    private static PactsClientServicesHome home;
	private static String providerUrl=null;

	public static void setProviderUrl(String providerUrl) {
		PactsServicesLocator.providerUrl=providerUrl;
		home=null;
	}

	public static String getProviderUrl() {
		return PactsServicesLocator.providerUrl;
	}

    public static PactsClientServices getService() throws NamingException, RemoteException, CreateException {
        if (providerUrl==null) {
			throw new IllegalArgumentException("It is mandatory to set up the provider URL before calling getService()");
		}
        PactsClientServicesHome h = home;
        if (h != null) {
            try {
                return h.create();
            } catch (Exception e) {
                //Let's create a new home
            }
        }
        h = getHome();
        PactsClientServices services = h.create();
        home = h;
        return services;
    }


    private static PactsClientServicesHome getHome() throws NamingException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        env.put(Context.PROVIDER_URL, providerUrl);
        env.put("jnp.disableDiscovery","true");//stop jboss from multicasting
        InitialContext ctx = new InitialContext(env);

        try {
            Object objRef =  ctx.lookup("com.topcoder.web.ejb.pacts.PactsClientServicesHome");
            if (Remote.class.isAssignableFrom(PactsClientServicesHome.class))
                return (PactsClientServicesHome) PortableRemoteObject.narrow(objRef, PactsClientServicesHome.class);
             else
                return (PactsClientServicesHome) objRef;
        } finally {
            ctx.close();
        }
    }
 }

