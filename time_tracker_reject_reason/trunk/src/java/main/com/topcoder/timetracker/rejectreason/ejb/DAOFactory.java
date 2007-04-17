/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.rejectreason.ejb;

import com.topcoder.db.connectionfactory.DBConnectionFactory;

import com.topcoder.timetracker.audit.AuditManager;
import com.topcoder.timetracker.rejectreason.RejectReasonDAOException;
import com.topcoder.timetracker.rejectreason.informix.DbRejectEmailDAO;
import com.topcoder.timetracker.rejectreason.informix.DbRejectReasonDAO;

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
    /** The JNDI name for Connection Factory key used by Object Factory. */
    private static final String CONNECTION_FACTORY_KEY_NAME = "db_connection_factory_key";

    /** The JNDI name for connection name. */
    private static final String CONNECTION_NAME_NAME = "connection_name";

    /** The JNDI name for environment variables. */
    private static final String ENV_JNDI_NAME = "java:comp/env";

    /** The JNDI name for object factory namespace. */
    private static final String OF_NAMESPACE_JNDI_NAME = "of_namespace";

    /** The JNDI name for Object Factory key of AuditManager. */
    private static final String AUDIT_MANAGER_KEY_JNDI_NAME = "audit_manager_key";

    /** The JNDI name for id generator name. */
    private static final String ID_GENERATOR_NAME_JNDI_NAME = "id_generator_name";

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

            // Lookup Connection Factory key and connection name from JNDI initial context.
            String factoryKey = lookupObject(context, CONNECTION_FACTORY_KEY_NAME, String.class).toString();
            String connName = lookupObject(context, CONNECTION_NAME_NAME, String.class).toString();

            // Get namespace of Object Factory, can't be null or empty
            String ofNamespace = lookupObject(context, OF_NAMESPACE_JNDI_NAME, String.class).toString();

            if (ofNamespace.trim().length() == 0) {
                throw new BaseException("The environment variable '" + OF_NAMESPACE_JNDI_NAME + "' can't be empty.");
            }

            // Get Object Factory key for AuditManager, can't be null or empty
            String auditManagerKey = lookupObject(context, AUDIT_MANAGER_KEY_JNDI_NAME, String.class).toString();

            if (auditManagerKey.trim().length() == 0) {
                throw new BaseException("The environment variable '" + AUDIT_MANAGER_KEY_JNDI_NAME
                    + "' can't be empty.");
            }

            // Get id generator name, can't be null or empty
            String idGeneratorName = lookupObject(context, ID_GENERATOR_NAME_JNDI_NAME, String.class).toString();

            if (idGeneratorName.trim().length() == 0) {
                throw new BaseException("The environment variable '" + ID_GENERATOR_NAME_JNDI_NAME
                    + "' can't be empty.");
            }

            // Create a new Connection Factory and AuditManager instance through Object Factory
            ObjectFactory factory = new ObjectFactory(new ConfigManagerSpecificationFactory(ofNamespace));
            Object object = factory.createObject(factoryKey);

            if (!(object instanceof DBConnectionFactory)) {
                throw new BaseException("The type of connection factory configured should be DBConnectionFactory.");
            }

            DBConnectionFactory connectionFactory = (DBConnectionFactory) object;

            object = factory.createObject(auditManagerKey);

            if (!(object instanceof AuditManager)) {
                throw new BaseException("The AuditManager configured should be an instance of AuditManager.");
            }

            AuditManager auditManager = (AuditManager) object;

            // Create a new DAO instance and return
            if (emailDAO) {
                return new DbRejectEmailDAO(connectionFactory, connName, auditManager, idGeneratorName);
            } else {
                return new DbRejectReasonDAO(connectionFactory, connName, auditManager, idGeneratorName);
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
