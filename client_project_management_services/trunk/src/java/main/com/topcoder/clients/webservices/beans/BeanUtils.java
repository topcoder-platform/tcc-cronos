/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.clients.webservices.beans;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.ejb.SessionContext;

import com.topcoder.clients.manager.ClientManager;
import com.topcoder.clients.manager.CompanyManager;
import com.topcoder.clients.manager.ManagerException;
import com.topcoder.clients.manager.ProjectManager;
import com.topcoder.clients.model.AuditableEntity;
import com.topcoder.clients.model.Client;
import com.topcoder.clients.model.ClientStatus;
import com.topcoder.clients.model.Company;
import com.topcoder.clients.model.Project;
import com.topcoder.clients.model.ProjectStatus;
import com.topcoder.clients.webservices.ClientProjectManagementServicesException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetrievalException;
import com.topcoder.clients.webservices.usermapping.UserMappingRetriever;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.security.auth.module.UserProfilePrincipal;
import com.topcoder.util.errorhandling.ExceptionUtils;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogManager;
import com.topcoder.util.objectfactory.InvalidClassSpecificationException;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigurationObjectSpecificationFactory;
import com.topcoder.util.objectfactory.impl.IllegalReferenceException;
import com.topcoder.util.objectfactory.impl.SpecificationConfigurationException;

/**
 * <p>
 *  Utility class for any common functionality in bean class.
 * </p>
 *
 * <p>
 *  <strong>Thread-safety:</strong>
 *  This class is thread safety because it does not contain any mutable property.
 * </p>
 *
 * @author  Mafy, CaDenza
 * @version 1.0
 */
final class BeanUtils {

    /**
     * Represents class name of ClientManager.
     */
    private static final String CLIENT_MANAGER_CLASS_NAME = ClientManager.class.getName();

    /**
     * Represents class name of ProjectManager.
     */
    private static final String PROJECT_MANAGER_CLASS_NAME = ProjectManager.class.getName();

    /**
     * Represents class name of CompanyManager.
     */
    private static final String COMPANY_MANAGER_CLASS_NAME = CompanyManager.class.getName();

    /**
     * Private constructor.
     */
    private BeanUtils() {
    }

    /**
     * Perform logging for any entrance into method. It has DEBUG level.
     *
     * @param logger
     *      The logger instance.
     * @param verbose
     *      Verbose flag.
     * @param method
     *      The name of method.
     * @param params
     *      The passing argument in given method.
     *
     * @return current enter time.
     */
    static Date logEnter(Log logger, boolean verbose, String method, Object[] params) {
        Date date = new Date();
        if (logger != null) {
            if (verbose) {
                // Logging method entry and its timestamp.
                logger.log(Level.DEBUG, "Entering[" + date + "]: " + method);

                // Logging method argument.
                for (int i = 0; i < params.length; i++) {
                    if (params[i] instanceof AuditableEntity) {
                        logger.log(Level.DEBUG, "--->Parameter[" + i + "]: "
                                + extractEntityPropertyForLogging((AuditableEntity) params[i]));
                    } else {
                        logger.log(Level.DEBUG, "---->Parameter[" + i + "]: " + params[i]);
                    }
                }
            } else {
                // Logging method entry.
                logger.log(Level.DEBUG, "Entering: " + method);
            }
        }
        return date;
    }

    /**
     * This method will extract entity's property to be used
     * in logging while entering method.
     *
     * @param entity
     *      The entity that will be extracted.
     * @return string representation for entity's property.
     */
    private static String extractEntityPropertyForLogging(AuditableEntity entity) {
        String result = null;
        if (entity == null) {
            result =  "null";
        } else {
            if (entity instanceof Client) {
                result = formatClient((Client) entity);
            } else if (entity instanceof Project) {
                result = formatProject((Project) entity);
            } else if (entity instanceof ClientStatus) {
                result = formatClientStatus((ClientStatus) entity);
            } else if (entity instanceof ProjectStatus) {
                result = formatProjectStatus((ProjectStatus) entity);
            } else if (entity instanceof Company) {
                result = formatCompany((Company) entity);
            }
        }
        return result;
    }

    /**
     * Logging while exit method. It has DEBUG level.
     *
     * @param logger
     *      The logger instance.
     * @param verbose
     *      The verbose flag.
     * @param method
     *      The name of method.
     * @param timeEnter
     *      The enter time.
     * @param returnValue
     *      The return value of method.
     */
    static void logExit(Log logger, boolean verbose, String method, Date timeEnter, Object returnValue) {
        if (logger != null) {
            if (verbose && (timeEnter != null)) {
                long timeExecution = (new Date()).getTime() - timeEnter.getTime();
                logger.log(Level.DEBUG, "Exiting: " + method
                        + ",Time-execution=" + timeExecution + " ms"
                        + ",Return-value=" + ((returnValue instanceof AuditableEntity)
                        ? extractEntityPropertyForLogging((AuditableEntity) returnValue) : returnValue));
            } else {
                logger.log(Level.DEBUG, "Exiting: " + method);
            }
        }
    }

    /**
     * Logging any failure. It has WARN level.
     *
     * @param <T>
     *      The generic exception.
     * @param logger
     *      The logger instance.
     * @param e
     *      The inner failure.
     * @return inner failure after it is logged.
     */
    static <T extends Throwable> T logException(Log logger, T e) {
        if (logger != null) {
            logger.log(Level.WARN, e, e.getMessage());
        }
        return e;
    }

    /**
     * Perform validation Caller. If it is fail to be authenticated,
     * current user is restricted perform any operation.
     *
     * @param method
     *      The name of method.
     * @param ctx
     *      Session context instance.
     * @param adminRole
     *      The admin role.
     * @param clientProjectUserRole
     *      The client and project user role.
     * @param entity
     *      The entity to be performed.
     * @param logger
     *      The logger instance.
     * @param usePrincipal
     *      Flag whether authentication using principal.
     * @param userMappingRetriever
     *      UserMapping retriever instance.
     *
     * @throws IllegalArgumentException
     *      if entity is null.
     * @throws IllegalStateException
     *      if session context or user mapping retriever is null.
     * @throws AuthorizationFailedException
     *      if current user is restricted to perform any operation.
     */
    static void validateCaller(String method, SessionContext ctx, String adminRole,
        String clientProjectUserRole, AuditableEntity entity, Log logger,
        boolean usePrincipal, UserMappingRetriever userMappingRetriever)
        throws AuthorizationFailedException {
        checkState("SessionContext", ctx);
        checkState("UserMappingRetriever", userMappingRetriever);
        ExceptionUtils.checkNull(entity, null, null, "Passing entity cannot be null.");

        try {
            boolean auth = false;
            if ((adminRole != null) && (adminRole.trim().length() != 0)) {
                auth = ctx.isCallerInRole(adminRole);
            }

            if (!auth) {
                if ((clientProjectUserRole != null) && (clientProjectUserRole.trim().length() != 0)) {
                    if (ctx.isCallerInRole(clientProjectUserRole)) {
                        if (usePrincipal) { // handle non create operation.
                            UserProfilePrincipal principal = (UserProfilePrincipal) ctx.getCallerPrincipal();
                            long userId = principal.getUserId();

                            // User operates on some project.
                            if (entity instanceof Project) {
                                List<Long> validUserOnProject =
                                    userMappingRetriever.getValidUsersForProject((Project) entity);
                                auth = validUserOnProject.contains(Long.valueOf(userId));
                            }

                            // User operates on some client
                            if (entity instanceof Client) {
                                List<Long> validUserOnClient =
                                    userMappingRetriever.getValidUsersForClient((Client) entity);
                                auth = validUserOnClient.contains(Long.valueOf(userId));
                            }
                        } else {
                            auth = true;
                        }
                    }
                }
            }

            if (!auth) {
                throw new AuthorizationFailedException("Not has authorization to perform operation: " + method);
            }
        } catch (ClassCastException e) {
            throw new AuthorizationFailedException("Unexpected instance class has received.", e);
        } catch (UserMappingRetrievalException e) {
            throw new AuthorizationFailedException("Fail in user mapping retrieval.", e);
        }
    }

    /**
     * Performing CREATE operation for defined entity.
     *
     * @param <T>
     *      The generic class that extends AuditableEntity.
     * @param user
     *      The current username.
     * @param entity
     *      The entity to be created.
     * @param clientProject
     *      The related client project.
     * @param clientManager
     *      The Client Manager instance,
     * @param projectManager
     *      The Project Manager instance.
     * @param companyManager
     *      The Company Manager instance.
     * @return entity after performed.
     *
     * @throws ClientProjectManagementServicesException
     *      if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    static <T extends AuditableEntity> T createOperation(String user, T entity, Client clientProject,
        ClientManager clientManager, ProjectManager projectManager, CompanyManager companyManager)
        throws ClientProjectManagementServicesException {

        ExceptionUtils.checkNull(entity, null, null, "Entity to be performed cannot be null.");
        Date currDate = new Date();
        entity.setCreateUsername(user);
        entity.setCreateDate(currDate);
        entity.setModifyUsername(user);
        entity.setModifyDate(currDate);

        try {
            if (entity instanceof Client) {
                checkState(CLIENT_MANAGER_CLASS_NAME, clientManager);
                entity = (T) clientManager.createClient((Client) entity);
            } else if (entity instanceof Company) {
                checkState(COMPANY_MANAGER_CLASS_NAME, companyManager);
                entity = (T) companyManager.createCompany((Company) entity);
            } else if (entity instanceof Project) {
                if (clientProject.getId() <= 0) { // New client instance.
                    clientProject.setCreateDate(currDate);
                    clientProject.setCreateUsername(user);
                    clientProject.setModifyDate(currDate);
                    clientProject.setModifyUsername(user);
                }

                checkState(PROJECT_MANAGER_CLASS_NAME, projectManager);
                entity = (T) projectManager.createProject((Project) entity, clientProject);
            } else {
                throw new ClientProjectManagementServicesException("Not defined entity to be created: " + entity);
            }
            return entity;
        } catch (ManagerException e) {
            throw new ClientProjectManagementServicesException("Fail to create entity.", e);
        } catch (IllegalArgumentException e) {
            throw new ClientProjectManagementServicesException("Received invalid argument.", e);
        }
    }

    /**
     * Performing UPDATE operation for passing entity.
     *
     * @param <T>
     *      The generic class that extends AuditableEntity.
     * @param user
     *      The current username.
     * @param entity
     *      The passing entity to be updated.
     * @param clientManager
     *      The client manager instance.
     * @param projectManager
     *      The project manager instance.
     * @param companyManager
     *      The company manager instance.
     * @return updated entity.
     *
     * @throws ClientProjectManagementServicesException
     *      if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    static <T extends AuditableEntity> T updateOperation(String user, T entity,
        ClientManager clientManager, ProjectManager projectManager, CompanyManager companyManager)
        throws ClientProjectManagementServicesException {

        ExceptionUtils.checkNull(entity, null, null, "Entity to be performed cannot be null.");
        Date currDate = new Date();
        entity.setModifyUsername(user);
        entity.setModifyDate(currDate);

        try {
            if (entity instanceof Client) {
                checkState(CLIENT_MANAGER_CLASS_NAME, clientManager);
                entity = (T) clientManager.updateClient((Client) entity);
            } else if (entity instanceof Company) {
                checkState(COMPANY_MANAGER_CLASS_NAME, companyManager);
                entity = (T) companyManager.updateCompany((Company) entity);
            } else if (entity instanceof Project) {
                checkState(PROJECT_MANAGER_CLASS_NAME, projectManager);
                entity = (T) projectManager.updateProject((Project) entity);
            } else {
                throw new ClientProjectManagementServicesException("Not defined entity to be updated: " + entity);
            }
            return entity;
        } catch (ManagerException e) {
            throw new ClientProjectManagementServicesException("Fail to update entity.", e);
        } catch (IllegalArgumentException e) {
            throw new ClientProjectManagementServicesException("Receive invalid argument.", e);
        }
    }

    /**
     * Performing DELETE operation for given entity.
     *
     * @param <T>
     *      The generic class that extends AuditableEntity.
     * @param user
     *      The current username.
     * @param entity
     *      The passing entity to be deleted.
     * @param clientManager
     *      The client manager instance.
     * @param projectManager
     *      The project manager instance.
     * @param companyManager
     *      The company manager instance.
     * @return deleted entity.
     *
     * @throws ClientProjectManagementServicesException
     *      if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    static <T extends AuditableEntity> T deleteOperation(String user, T entity,
        ClientManager clientManager, ProjectManager projectManager, CompanyManager companyManager)
        throws ClientProjectManagementServicesException {

        ExceptionUtils.checkNull(entity, null, null, "Entity to be performed cannot be null.");
        Date currDate = new Date();
        entity.setModifyUsername(user);
        entity.setModifyDate(currDate);

        try {
            if (entity instanceof Client) {
                checkState(CLIENT_MANAGER_CLASS_NAME, clientManager);
                entity = (T) clientManager.deleteClient((Client) entity);
            } else if (entity instanceof Company) {
                checkState(COMPANY_MANAGER_CLASS_NAME, companyManager);
                entity = (T) companyManager.deleteCompany((Company) entity);
            } else if (entity instanceof Project) {
                checkState(PROJECT_MANAGER_CLASS_NAME, projectManager);
                entity = (T) projectManager.deleteProject((Project) entity);
            } else {
                throw new ClientProjectManagementServicesException("Not defined entity to be deleted: " + entity);
            }
            return entity;
        } catch (ManagerException e) {
            throw new ClientProjectManagementServicesException("Fail to delete entity.", e);
        } catch (IllegalArgumentException e) {
            throw new ClientProjectManagementServicesException("Receive invalid argument.", e);
        }
    }

    /**
     * Get current user from given session context.
     *
     * @param sessionContext
     *      The available session context.
     *
     * @return current user.
     *
     * @throws IllegalStateException
     *      if session context is null, missing principal, or user does not available.
     */
    protected static String getCurrentUser(SessionContext sessionContext) {
        checkState("sessionContext", sessionContext);
        Principal principal = (Principal) checkState("principal", sessionContext.getCallerPrincipal());
        return (String) checkState("current user", principal.getName());
    }

    /**
     * Check state of given object.
     *
     * @param name
     *      The name of object to be checked.
     * @param obj
     *      The passing object to be checked.
     * @return valid object.
     *
     * @throws IllegalStateException if passing object is null.
     */
    protected static Object checkState(String name, Object obj) {
        if (obj == null) {
            throw new IllegalStateException("Property '"
                    + name + "' has null state. Check your configuration again !!!");
        }
        return obj;
    }

    /**
     * Create ClientManager, ProjectManager, or CompanyManager based on given configuration.
     *
     * @param <T>
     *      The generic class.
     * @param clazz
     *      The class name of manager.
     * @param file
     *      The location of file configuration.
     * @param namespace
     *      The namespace configuration of manager instance.
     * @param token
     *      The name of token to be used as key to create manager instance using object factory.
     *
     * @return Manager instance.
     *
     * @throws ServiceBeanConfigurationException if any failure occurs.
     */
    @SuppressWarnings("unchecked")
    static <T extends Object> T createManager(Class<T> clazz, String file, String namespace, String token) {
        try {
            ExceptionUtils.checkNullOrEmpty(file, null, null, "Invalid file to configure: " + clazz.getName());
            ExceptionUtils.checkNullOrEmpty(namespace, null, null, "Namespace cannot be null or empty string.");

            // create an ConfigurationFileManager from the given file:
            ConfigurationFileManager configurationFileManager = new ConfigurationFileManager(file);

            // create an ConfigurationObject using the given namespace:
            ConfigurationObject configurationObject = configurationFileManager.getConfiguration(namespace);
            ExceptionUtils.checkNull(configurationObject, null, null,
                    "Not found '" + namespace + "' configuration in configuration-file-manager.");

            ConfigurationObject config = configurationObject.getChild(namespace);
            ExceptionUtils.checkNull(config, null, null, "Not Found child: " + namespace);

            // get the needed token value from the created configurationObject:
            String tokenValue = (String) config.getPropertyValue(token);
            ExceptionUtils.checkNullOrEmpty(tokenValue, null, null, "Invalid configuration property: " + token);

            // create an ConfigurationObjectSpecificationFactory from the created configurationObject.
            // create an ObjectFactory from the created configurationObjectSpecificationFactory.
            ObjectFactory objectFactory = new ObjectFactory(new ConfigurationObjectSpecificationFactory(config));

            // create the needed dependency using the created objectFactory and the
            // retrieved token value:
            T retObject = (T) objectFactory.createObject(tokenValue);
            if (!clazz.isAssignableFrom(retObject.getClass())) {
                throw new ServiceBeanConfigurationException("Expected '"
                        + clazz.getName() + "', but found '" + retObject.getClass().getName() + "'.");
            }
            return retObject;
        } catch (ConfigurationParserException e) {
            throw new ServiceBeanConfigurationException("Error configuration to be parsed.", e);
        } catch (NamespaceConflictException e) {
            throw new ServiceBeanConfigurationException("Namespace conflict", e);
        } catch (UnrecognizedFileTypeException e) {
            throw new ServiceBeanConfigurationException("File type is not defined.", e);
        } catch (IOException e) {
            throw new ServiceBeanConfigurationException("Any error in Input/output mechanism.", e);
        } catch (UnrecognizedNamespaceException e) {
            throw new ServiceBeanConfigurationException("Not defined namespace.", e);
        } catch (IllegalReferenceException e) {
            throw new ServiceBeanConfigurationException("Illegal references.", e);
        } catch (SpecificationConfigurationException e) {
            throw new ServiceBeanConfigurationException("Connfiguration specification is invalid.", e);
        } catch (ConfigurationAccessException e) {
            throw new ServiceBeanConfigurationException("Configuration access is fail.", e);
        } catch (InvalidClassSpecificationException e) {
            throw new ServiceBeanConfigurationException("Class specification is invalid.", e);
        } catch (IllegalArgumentException e) {
            throw new ServiceBeanConfigurationException("Any invalid argument is received.", e);
        }
    }

    /**
     * Create logger instance from given logger name.
     *
     * @param loggerName
     *      The name of logger. Can be any value.
     *
     * @return logger instance.
     */
    static Log createLogger(String loggerName) {
        Log logger;
        try {
            ExceptionUtils.checkNullOrEmpty(loggerName, null, null, null);
            logger = LogManager.getLog(loggerName.trim());
        } catch (IllegalArgumentException e) {
            logger = LogManager.getLog();
        }
        return logger;
    }

    /**
     * Format the client for log.
     * <p>
     * For a given client, the format will be [id, name]
     * </p>
     *
     * @param client
     *            the Client entity to format
     * @return a String format of Client
     */
    static String formatClient(Client client) {
        StringBuilder builder = new StringBuilder();
        builder.append(Client.class.getName() + "[");
        if (client == null) {
            builder.append("null");
        } else {
            builder.append("id=" + client.getId());
            builder.append(",name=" + client.getName());
            builder.append(",codeName=" + client.getCodeName());
            builder.append(",salesTax=" + client.getSalesTax());
            builder.append(",isDeleted=" + client.isDeleted());

            // format the company attribute
            builder.append(",company=" + formatCompany(client.getCompany()));

            // format the ClientStatus attribute
            builder.append(",clientStatus=" + formatClientStatus(client.getClientStatus()));
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Format a Company for log.
     * <p>
     * The main information of id, name and passcode will be format to String.
     * </p>
     *
     * @param company
     *            the company to format
     * @return String format of Company
     */
    static String formatCompany(Company company) {
        StringBuilder builder = new StringBuilder();
        builder.append(Company.class.getName() + "[");
        if (company == null) {
            builder.append("null");
        } else {
            builder.append("id=" + company.getId());
            builder.append(",name=" + company.getName());
            builder.append(",passcode=" + company.getPasscode());
            builder.append(",isDeleted=" + company.isDeleted());
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Format the ClientStatus for log.
     * <p>
     * </p>
     *
     * @param status
     *            the ClientStatus to format
     * @return a String format of ClientStauts
     */
    static String formatClientStatus(ClientStatus status) {
        StringBuilder builder = new StringBuilder();
        builder.append(ClientStatus.class.getName() + "[");
        if (status == null) {
            builder.append("null");
        } else {
            builder.append("id=" + status.getId());
            builder.append(",name=" + status.getName());
            builder.append(",description=" + status.getDescription());
            builder.append(",isDeleted=" + status.isDeleted());
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Format the Project entity for log.
     *
     * @param project
     *            the project entity to format
     * @return the String format of Project entity , containing main information
     */
    static String formatProject(Project project) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (project == null) {
            builder.append("null");
        } else {
            builder.append("id=" + project.getId());
            builder.append(",name=" + project.getName());
            builder.append(",description=" + project.getDescription());
            builder.append(",parentProjectId=" + project.getParentProjectId());
            builder.append(",salesTax=" + project.getSalesTax());

            // log the client.
            builder.append(",client=" + formatClient(project.getClient()));

            // log the company
            builder.append(",company=" + formatCompany(project.getCompany()));

            // log the child projects
            builder.append(",childProjects=" + formatProjectList(project.getChildProjects()));
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Format a list of Project for log.
     *
     * @param projects
     *            the list of Project to be formated
     * @return String format of Project
     */
    static String formatProjectList(List<Project> projects) {
        StringBuilder builder = new StringBuilder();
        builder.append("<");
        if (projects == null) {
            builder.append("null");
        } else {
            for (Project p : projects) {
                builder.append(formatProject(p));
                builder.append(" ");
            }
        }

        builder.append(">");
        return builder.toString();
    }

    /**
     * Format the ProjectStatus for log.
     *
     * @param status
     *            the ProjectStatus to format
     * @return a String format of ProjectStatus
     */
    static String formatProjectStatus(ProjectStatus status) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (status == null) {
            builder.append("null");
        } else {
            builder.append("id=" + status.getId());
            builder.append(",name=" + status.getName());
            builder.append(",description=" + status.getDescription());
            builder.append(",isDeleted=" + status.isDeleted());
        }
        builder.append("]");
        return builder.toString();
    }
}
