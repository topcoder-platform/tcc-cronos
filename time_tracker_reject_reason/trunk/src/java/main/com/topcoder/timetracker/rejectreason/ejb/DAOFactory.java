/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.timetracker.rejectreason.RejectEmailDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAO;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;

import com.topcoder.util.errorhandling.BaseException;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * This is a utility to get DAO instance. It contains helper method to get DAO implementations of this component.
 * Parameters that passed to the constructor of DAOs are got from JNDI context and Object Factory.
 *
 * @author TCSDEVELOPER
 * @version 3.2
 */
final class DAOFactory {
    /** The JNDI name for RejectEmailDAO key used by Object Factory. */
    private static final String REJECT_EMAIL_DAO = "reject_email_dao";

    /** The JNDI name for RejectReasonDAO key used by Object Factory. */
    private static final String REJECT_REASON_DAO = "reject_reason_dao";

    /** The JNDI name for environment variables. */
    private static final String ENV_JNDI_NAME = "java:comp/env";

    /** The JNDI name for object factory namespace. */
    private static final String OF_NAMESPACE_JNDI_NAME = "of_namespace";

    /**
     * Private constructor to avoid instantiation.
     */
    private DAOFactory() {
        // Do nothing
    }

    /**
     * Looks up object from given JNDI context with given key. the looked up object should be an instance of given
     * type.
     *
     * @param context the JNDI context to lookup.
     * @param key the key used to lookup JNDI context.
     * @param type the expected type of the looked up object.
     *
     * @return the object looked up from JNDI context.
     *
     * @throws BaseException if the object looked is null or if it's not an instance of given type.
     */
    private static Object lookupObject(Context context, String key, Class type)
        throws BaseException {
        Object object;

        try {
            object = context.lookup(key);

            if (object == null) {
                throw new BaseException("The object looked up from JNDI context with key '" + key + "' is null.");
            }

            if (!type.isInstance(object)) {
                throw new BaseException("The object looked up from JNDI context with key '" + key
                    + "' is not an instance of '" + type.getName() + "'.");
            }

            return object;
        } catch (NamingException e) {
            throw new BaseException("Unable to lookup object from JNDI context with key '" + key + "'.", e);
        }
    }

    /**
     * <p>
     * Gets a RejectEmailDAO instance or a RejectReasonDAO instance, depends on passed in parameter. This method wraps
     * any exception by BaseException.
     * </p>
     *
     * @param emailDAO determines if RejectEmailDAO is to be created.
     *
     * @return a RejectEmailDAO instance if emailDAO is true, otherwise a RejectReasonDAO instance.
     *
     * @throws BaseException if any error occurs.
     */
    static Object getDAO(boolean emailDAO) throws BaseException {
        try {
            // Get JNDI initial context.
            InitialContext initialContext = new InitialContext();

            // Lookup environment variables from JNDI initial context.
            Context context = (Context) lookupObject(initialContext, ENV_JNDI_NAME, Context.class);

            // Get namespace of Object Factory, can't be null or empty
            String ofNamespace = lookupObject(context, OF_NAMESPACE_JNDI_NAME, String.class).toString();

            if (ofNamespace.trim().length() == 0) {
                throw new BaseException("The environment variable '" + OF_NAMESPACE_JNDI_NAME + "' can't be empty.");
            }

            // Create a new Connection Factory and AuditManager instance through Object Factory
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(ofNamespace));

            // Create a new DAO instance and return
            if (emailDAO) {
                Object object = factory.createObject(REJECT_EMAIL_DAO);

                if (!(object instanceof RejectEmailDAO)) {
                    throw new BaseException("The DAO configured should be an instance of RejectEmailDAO.");
                }

                return object;
            } else {
                Object object = factory.createObject(REJECT_REASON_DAO);

                if (!(object instanceof RejectReasonDAO)) {
                    throw new BaseException("The DAO configured should be an instance of RejectReasonDAO.");
                }

                return object;
            }
        } catch (InvalidClassSpecificationException e) {
            throw new BaseException("Failed to create AuditManager through Object Factory.", e);
        } catch (SpecificationConfigurationException e) {
            throw new BaseException("Error occurred while getting properties from ConfigManager.", e);
        } catch (IllegalReferenceException e) {
            throw new BaseException("The Object Factory configuration for AuditManager is invalid somewhere.", e);
        } catch (RejectReasonDAOException e) {
            throw new BaseException("Error occurred while creating DbRejectReasonDAO object.", e);
        } catch (NamingException e) {
            throw new BaseException("Failed to get InitialContext.", e);
        }
    }
}
