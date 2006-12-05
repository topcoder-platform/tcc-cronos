/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.administration;

import javax.ejb.EJBHome;
import javax.rmi.PortableRemoteObject;

import com.topcoder.naming.jndiutility.JNDIUtils;

/**
 * This class is an implementation of the Service Locator pattern. It is used to
 * lookup resources such as EJBHome. In future it may be used to look up other
 * resources such as JMS Destinations etc.<br/> Thread-Safety: This class is
 * not thread-safe. Instances should not be shared among threads. It can be made
 * thread-safe by synchronizing over the initialContext instance variable.
 * @author bose_java, KKD
 * @version 1.0
 */
public class ServiceLocator {

    /**
     * Creates a ServiceLocator instance.
     *
     */
    public ServiceLocator() {
    }

    /**
     * This method will lookup the EJBHome for the specified JNDI name. It will
     * narrow the reference to the specified className. The class name must be
     * an implementation of EJBHome.
     *
     *
     * @return the EJB Home corresponding to the homeName
     * @param jndiHomeName
     *            JDNI name of required EJB's home interface.
     * @param className
     *            class of required EJBHome.
     * @throws IllegalArgumentException
     *             if any argument is null, if jndiHomeName is empty string or
     *             if className is not of type EJBHome.
     * @throws ServiceLocatorException
     *             if any exception occur when retrieve RemoteHome
     */
    public EJBHome getRemoteHome(String jndiHomeName, Class className)
        throws ServiceLocatorException {
        Helper.checkString(jndiHomeName, "jndiHomeName");
        Helper.checkClassType(className, EJBHome.class, "className");

        try {
            Object objref = JNDIUtils.getObject(jndiHomeName);
            Object obj = PortableRemoteObject.narrow(objref, className);
            return (EJBHome) obj;
        } catch (Exception e) {
            throw new ServiceLocatorException(
                    "Failed to get remote home. caused by " + e.getMessage(), e);
        }

    }
}
