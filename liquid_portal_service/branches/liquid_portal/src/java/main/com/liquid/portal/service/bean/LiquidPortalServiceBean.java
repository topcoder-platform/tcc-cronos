/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.liquid.portal.service.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.jboss.ws.annotation.EndpointConfig;

import com.liquid.portal.service.CompetitionData;
import com.liquid.portal.service.CreateCompetitonResult;
import com.liquid.portal.service.LiquidPortalIllegalArgumentException;
import com.liquid.portal.service.LiquidPortalServiceConfigurationException;
import com.liquid.portal.service.LiquidPortalServiceException;
import com.liquid.portal.service.ProvisionUserResult;
import com.liquid.portal.service.RegisterUserResult;
import com.liquid.portal.service.Result;
import com.liquid.portal.service.Warning;
import com.topcoder.catalog.entity.Status;
import com.topcoder.clients.dao.DAOConfigurationException;
import com.topcoder.clients.dao.DAOException;
import com.topcoder.clients.dao.ProjectDAO;
import com.topcoder.clients.model.Project;
import com.topcoder.configuration.ConfigurationAccessException;
import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.ConfigurationFileManager;
import com.topcoder.configuration.persistence.ConfigurationParserException;
import com.topcoder.configuration.persistence.NamespaceConflictException;
import com.topcoder.configuration.persistence.UnrecognizedFileTypeException;
import com.topcoder.configuration.persistence.UnrecognizedNamespaceException;
import com.topcoder.message.email.AddressException;
import com.topcoder.message.email.EmailEngine;
import com.topcoder.message.email.SendingException;
import com.topcoder.message.email.TCSEmailMessage;
import com.topcoder.service.facade.contest.ContestServiceException;
import com.topcoder.service.facade.contest.ContestServiceFacade;
import com.topcoder.service.permission.Permission;
import com.topcoder.service.permission.PermissionServiceException;
import com.topcoder.service.permission.PermissionType;
import com.topcoder.service.pipeline.CapacityData;
import com.topcoder.service.pipeline.ContestPipelineServiceException;
import com.topcoder.service.pipeline.PipelineServiceFacade;
import com.topcoder.service.project.AuthorizationFailedFault;
import com.topcoder.service.project.CompetionType;
import com.topcoder.service.project.IllegalArgumentFault;
import com.topcoder.service.project.PersistenceFault;
import com.topcoder.service.project.ProjectData;
import com.topcoder.service.project.ProjectNotFoundFault;
import com.topcoder.service.project.ProjectService;
import com.topcoder.service.project.SoftwareCompetition;
import com.topcoder.service.project.StudioCompetition;
import com.topcoder.service.project.UserNotFoundFault;
import com.topcoder.service.studio.ContestData;
import com.topcoder.service.studio.IllegalArgumentWSException;
import com.topcoder.service.studio.PersistenceException;
import com.topcoder.service.studio.UserNotAuthorizedException;
import com.topcoder.service.user.User;
import com.topcoder.service.user.UserInfo;
import com.topcoder.service.user.UserService;
import com.topcoder.service.user.UserServiceException;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.file.DocumentGenerator;
import com.topcoder.util.file.DocumentGeneratorConfigurationException;
import com.topcoder.util.file.DocumentGeneratorFactory;
import com.topcoder.util.file.Template;
import com.topcoder.util.file.TemplateDataFormatException;
import com.topcoder.util.file.TemplateFormatException;
import com.topcoder.util.file.templatesource.TemplateSourceException;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogManager;

/**
 * <p>
 * This class provides an implementation of the LiquidPortalService via its
 * Local and Remote interfaces. It uses various services to process liquid
 * service requests. It uses Logging Wrapper to log events.
 * </p>
 * <p>
 * <b>Thread Safety:</b> This bean is mutable and not thread-safe as it deals
 * with non-thread-safe entities. However, in the context of being used in a
 * container, it is thread-safe.
 * </p>
 *
 * @author argolite, TCSDEVELOPER
 * @version 1.0
 */
@Stateless
@WebService(endpointInterface = "com.liquid.portal.service.LiquidPortalService")
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
@DeclareRoles({"Cockpit User", "Cockpit Administrator", "Liquid Administrator" })
@RolesAllowed({"Cockpit User", "Cockpit Administrator" })
@TransactionManagement(TransactionManagementType.CONTAINER)
@SuppressWarnings("unused")
public class LiquidPortalServiceBean implements LiquidPortalServiceLocal, LiquidPortalServiceRemote {
    /**
     * <p>
     * Represents the pipeline service facade.
     * </p>
     * <p>
     * It is injected by the container. It is used in the business methods.
     * </p>
     */
    @EJB(name = "ejb/pipelineServiceFacade")
    private PipelineServiceFacade pipelineServiceFacade;

    /**
     * <p>
     * Represents the contest service facade.
     * </p>
     * <p>
     * It is injected by the container. It is used in the business methods.
     * </p>
     */
    @EJB(name = "ejb/contestServiceFacade")
    private ContestServiceFacade contestServiceFacade;

    /**
     * <p>
     * Represents the user service.
     * </p>
     * <p>
     * It is injected by the container. It is used in the business methods.
     * </p>
     */
    @EJB(name = "ejb/userService")
    private UserService userService;

    /**
     * <p>
     * Represents the project service.
     * </p>
     * <p>
     * It is injected by the container. It is used in the business methods.
     * </p>
     */
    @EJB(name = "ejb/projectService")
    private ProjectService projectService;

    /**
     * <p>
     * Represents the billing project DAO.
     * </p>
     * <p>
     * It is injected by the container. It is used in the business methods.
     * </p>
     */
    @EJB(name = "ejb/billingProjectDAO")
    private ProjectDAO billingProjectDAO;

    /**
     * <p>
     * Represents the configuration file name.
     * </p>
     * <p>
     * It is injected by the container. It is used in the initialize method.
     * </p>
     */
    @Resource(name = "configurationFileName")
    private String configurationFileName;

    /**
     * <p>
     * Represents the configuration namespace.
     * </p>
     * <p>
     * It is injected by the container. It is used in the initialize method.
     * </p>
     */
    @Resource(name = "configurationNamespace")
    private String configurationNamespace;

    /**
     * <p>
     * Represents the notus observer of terms ID.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long notusObserverTermsId;

    /**
     * <p>
     * Represents the IDs of notus eligible groups.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long[] notusEligibilityGroupIds;

    /**
     * <p>
     * Represents the full control permission ID.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long fullControlPermissionTypeId;

    /**
     * <p>
     * Represents the mapping of project categories names to their IDs.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private Map<String, Integer> projectCategories;

    /**
     * <p>
     * Represents the mapping of studio contest type names to their IDs.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * <p>
     */
    private Map<String, Integer> studioContestTypes;

    /**
     * <p>
     * Represents the ID of the inactive studio contest status.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long inactiveStudioContestStatusId;

    /**
     * <p>
     * Represents the ID of the inactive software contest status.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long inactiveSoftwareContestStatusId;

    /**
     * <p>
     * Represents the ID of the deleted software contest status.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long deletedSoftwareContestStatusId;

    /**
     * <p>
     * Represents the notus client ID.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private long notusClientId;

    /**
     * <p>
     * Represents the billing account property key.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private String billingAccountPropertyKey;

    /**
     * <p>
     * Represents the reason account property key.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the business methods.
     * </p>
     */
    private String deleteReasonPropertyKey;

    /**
     * <p>
     * It will be used to log all method entries and exists, and all errors.
     * </p>
     * <p>
     * It is set in the initialize method. It is accessed in all business
     * methods.
     * </p>
     */
    private Log logger;

    /**
     * <p>
     * Represents the email address for JIRA.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the
     * sendJiraNotification method.
     * </p>
     */
    private String jiraEmailAddress;

    /**
     * <p>
     * Represents the subject of the email message to send to JIRA.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the
     * sendJiraNotification method.
     * </p>
     */
    private String jiraEmailSubject;

    /**
     * <p>
     * Represents the session context of this bean.
     * </p>
     *
     * <p>
     * It is a resource injected by the EJB container and will not be null while client calls are being executed.
     * </p>
     *
     * <p>
     * It is not frozen since it will be set by the EJB container when required. However, it does not compromise
     * thread safety since the context will not be set while the bean is executing client calls.
     * </p>
     */
    @Resource
    private SessionContext sessionContext;

    /**
     * <p>
     * Represents the email address for the sender of the email to JIRA.
     * </p>
     * <p>
     * It is set in the initialize method it is used in the sendJiraNotification
     * method.
     * </p>
     */
    private String jiraEmailSender;

    /**
     * <p>
     * Represents the template name of the message to use to send to JIRA.
     * </p>
     * <p>
     * It is set in the initialize method. It is used in the
     * sendJiraNotification method.
     * </p>
     */
    private String jiraEmailMessageTemplateName;

    /**
     * <p>
     * Represents the document generator.
     * </p>
     * <p>
     * It is set in the initialize method and used in the sendJiraNotification method Once set, it
     * will not change. Once set, it can't be null.
     * </p>
     */
    private DocumentGenerator documentGenerator;

    /**
     * <p>
     * Default Empty constructor.
     * </p>
     */
    public LiquidPortalServiceBean() {
    }

    /**
     * <p>
     * Configures the log and other configuration parameters.
     * </p>
     *
     * @throws LiquidPortalServiceConfigurationException
     *             If can't create the file manager or extract the configuration
     *             from it, or if the configuration does not have required
     *             properties, or if there is an error while accessing these
     *             properties
     */
    @PostConstruct
    protected void initialize() {
        final String methodName = "initialize";
        String logName = null;
        try {
            // Create new instance of ConfigurationFileManager:
            ConfigurationFileManager cfm = new ConfigurationFileManager(configurationFileName);
            // Get ConfigurationObject with key configNamespace and extract properties
            ConfigurationObject config = cfm.getConfiguration(configurationNamespace);
            if (config == null) {
                throw new LiquidPortalServiceConfigurationException("Cannot found ConfigurationObject with namespace:"
                        + configurationNamespace);
            }
            ConfigurationObject configObject = config.getChild(configurationNamespace);
            if (configObject == null) {
                throw new LiquidPortalServiceConfigurationException("Cannot found ConfigurationObject with namespace:"
                        + configurationNamespace);
            }

            // Get properties from configurationObject as per CS 3.2.1
            notusObserverTermsId = getConfigurationLongValue("notusObserverTermsId", configObject);
            fullControlPermissionTypeId = getConfigurationLongValue("fullControlPermissionTypeId", configObject);
            inactiveStudioContestStatusId = getConfigurationLongValue("inactiveStudioContestStatusId", configObject);
            inactiveSoftwareContestStatusId = getConfigurationLongValue("inactiveSoftwareContestStatusId",
                    configObject);
            deletedSoftwareContestStatusId = getConfigurationLongValue("deletedSoftwareContestStatusId",
                    configObject);
            notusClientId = getConfigurationLongValue("notusClientId", configObject);
            billingAccountPropertyKey = getConfigurationValue("billingAccountPropertyKey", configObject);
            deleteReasonPropertyKey = getConfigurationValue("deleteReasonPropertyKey", configObject);
            jiraEmailAddress = getConfigurationValue("jiraEmailAddress", configObject);
            jiraEmailSubject = getConfigurationValue("jiraEmailSubject", configObject);
            jiraEmailSender = getConfigurationValue("jiraEmailSender", configObject);
            jiraEmailMessageTemplateName = getConfigurationValue("jiraEmailMessageTemplateName", configObject);

            // Create a new instance of Log
            logName = getConfigurationValue("logName", configObject);
            logger = LogManager.getLogWithExceptions(logName);

            // Assemble notusEligibilityGroupIds into array of longs
            Object[] groupIds = configObject.getPropertyValues("notusEligibilityGroupIds");
            if (groupIds == null) {
                throw new LiquidPortalServiceConfigurationException(
                        "The 'notusEligibilityGroupIds' property should be configed.");
            }
            notusEligibilityGroupIds = new long[groupIds.length];
            try {
                for (int i = 0; i < groupIds.length; i++) {
                    notusEligibilityGroupIds[i] = Long.parseLong((String) groupIds[i]);
                }
            } catch (ClassCastException e) {
                throw new LiquidPortalServiceConfigurationException(
                        "The value of 'notusEligibilityGroupIds' property should be String.", e);
            } catch (NumberFormatException e) {
                throw new LiquidPortalServiceConfigurationException(
                        "The value of 'notusEligibilityGroupIds' property should be integer.", e);
            }

            // Parse projectCategories into name,key values, create
            // HashMap<String,Integer> and add each key/value entry
            projectCategories = getConfigurationMapValue("projectCategories", configObject);

            // Parse studioContestTypes into name,key values, create
            // HashMap<String,Integer> and add each key/value entry
            studioContestTypes = getConfigurationMapValue("studioContestTypes", configObject);
            ConfigurationObject documentGeneratorConfigObject = config.getChild("document_generator_configuration");
            if (documentGeneratorConfigObject == null) {
                throw new LiquidPortalServiceConfigurationException("Failed to get child configuration object "
                        + " : document_generator_configuration");
            }
            documentGenerator = DocumentGeneratorFactory.getDocumentGenerator(documentGeneratorConfigObject);
        } catch (DocumentGeneratorConfigurationException e) {
            throw logError(new LiquidPortalServiceConfigurationException(
                    "Error occurs when creating DocumentGenerator", e), methodName);
        } catch (IOException e) {
            throw logError(new LiquidPortalServiceConfigurationException("Failed creating configuration manager with "
                    + configurationFileName, e), methodName);
        } catch (ConfigurationParserException e) {
            throw logError(new LiquidPortalServiceConfigurationException("Failed creating configuration manager with "
                    + configurationFileName, e), methodName);
        } catch (NamespaceConflictException e) {
            throw logError(new LiquidPortalServiceConfigurationException("Namespace conflict within "
                    + configurationFileName, e), methodName);
        } catch (UnrecognizedFileTypeException e) {
            throw logError(new LiquidPortalServiceConfigurationException("File type not recognized: "
                    + configurationFileName, e), methodName);
        } catch (IllegalArgumentException e) {
            // configurationFileName is null/empty, or configurationNamespace is null/empty
            throw logError(new LiquidPortalServiceConfigurationException(
                    "configurationFileName or configurationNamespace is null or empty", e), methodName);
        } catch (ConfigurationAccessException e) {
            throw logError(new LiquidPortalServiceConfigurationException(
                    "Failed when accessing the configuration file" + configurationFileName, e), methodName);
        } catch (UnrecognizedNamespaceException e) {
            throw logError(new LiquidPortalServiceConfigurationException(
                    "Namespace not recognized : " + configurationNamespace, e), methodName);
        } catch (LogException e) {
            throw logError(new LiquidPortalServiceConfigurationException(
                    "Failed creating logger with logger name:" + logName, e), methodName);
        } catch (LiquidPortalServiceConfigurationException e) {
            throw logError(e, methodName);
        }
    }

    /**
     * <p>
     * Creates the user in TopCoder site with the passed-in information. Returns
     * the non-null result of the action.
     * </p>
     *
     * @param user
     *            the User instance with the user info
     * @param termsAgreedDate
     *            the date of the terms agreement
     * @return the result of the registration attempt
     * @throws LiquidPortalIllegalArgumentException
     *             If argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>user is null -- 2000</li>
     *             <li>user.firstName is null/empty -- 2001</li>
     *             <li>user.lastName is null/empty -- 2002</li>
     *             <li>user.emailAddress is null/empty -- 2003</li>
     *             <li>user.password is null/empty -- 2004</li>
     *             <li>user.handle is null/empty -- 2005</li>
     *             <li>user.phone is null/empty -- 2006</li>
     *             <li>termsAgreedDate is null -- 2007</li>
     *             <li>termsAgreedDate in the future -- 2008</li>
     *             </ul>
     * @throws HandleCreationException
     *             If unable to create the user handle
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @RolesAllowed({"Liquid Administrator" })
    public RegisterUserResult registerUser(User user, Date termsAgreedDate)
            throws LiquidPortalIllegalArgumentException, LiquidPortalServiceException {
    	//TODO 2009 and 2010
        final String methodName = "registerUser";
        logEntrance(methodName);

        // check the argument
        checkUser(user, methodName);
        if (termsAgreedDate == null) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_TERMSAGREEDDATE_NULL), methodName);
        }
        if (termsAgreedDate.compareTo(new Date()) > 0) {
            // date is in the future
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_TERMSAGREEDDATE_IN_FUTURE), methodName);
        }

        RegisterUserResult result;
        List<Warning> warnings = new ArrayList<Warning>();

        try {
            // register user
            long userId = userService.registerUser(user);
            result = new RegisterUserResult();
            result.setUserId(userId);
        } catch (UserServiceException e) {
            sessionContext.setRollbackOnly();
            // can not create the handle
            throw logError(new LiquidPortalServiceException("can not create the handle", e), methodName);
        } catch (IllegalArgumentException e) {
            sessionContext.setRollbackOnly();
            // can not create the handle
            throw logError(new LiquidPortalServiceException("can not create the handle", e), methodName);
        }

        // add user to notusEligibilityGroup
        addUserToNotusEligibilityGroup(user, warnings, methodName);
        // add user to terms group
        addUserToTermsGroup(user, termsAgreedDate, warnings, methodName);
        result.setWarnings(warnings);

        logExit(methodName);
        return result;
    }

    /**
     * <p>
     * Ensurer the passed-in handle is valid, and it's in the correct groups.
     * Provides non-null result with warnings if it is not.
     * </p>
     *
     * @param user
     *            the user info to validate force
     * @param force
     *            If true, it will ignore warnings and add user to terms &
     *            eligibility groups
     * @return the validation result
     * @throws LiquidPortalIllegalArgumentException
     *             If argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>user is null -- 2000</li>
     *             <li>user.firstName is null/empty -- 2001</li>
     *             <li>user.lastName is null/empty -- 2002</li>
     *             <li>user.emailAddress is null/empty -- 2003</li>
     *             <li>user.handle is null/empty -- 2005</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given user handle
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Result validateUser(UserInfo user, boolean force) throws LiquidPortalIllegalArgumentException, LiquidPortalServiceException {
        final String methodName = "validateUser";
        logEntrance(methodName);

        // check the argument
        checkUserInfo(user, methodName);

        UserInfo savedUserInfo = getUserInfo(user.getHandle(), methodName);

        // compare saveUserInfo with passed-in userInfo
        boolean demoMatched = savedUserInfo.getFirstName().equals(user.getFirstName())
                && savedUserInfo.getLastName().equals(user.getLastName())
                && savedUserInfo.getEmailAddress().equals(user.getEmailAddress());
        Result result = new Result();
        List<Warning> warnings = new ArrayList<Warning>();
        if (!demoMatched) {
            // demographic information doesn't match (Email, First or Last Name)
            warnings.add(getWarning("user info doesn't math with persisted user info", 5002, null));
        }

        if (force || demoMatched) {
            // add user to notusEligibilityGroup
            addUserToNotusEligibilityGroup(user, warnings, methodName);
            // add user to terms group
            addUserToTermsGroup(user, new Date(), warnings, methodName);
        }

        result.setWarnings(warnings);
        logExit(methodName);
        return result;
    }

    /**
     * <p>
     * Sets up the user with the correct permissions to be able to create
     * competitions as needed. Returns the non-null result of the action.
     * </p>
     *
     * @param hasAccountAccess
     *            flag whether the user has account level access
     * @param cockpitProjectNames
     *            the names of all cockpit projects that the user has access to
     * @param requestorHandle
     *            the TopCoder handle of the user that requested the update
     * @param billingProjectIds
     *            the IDs of the billing projects that user can charge against
     *            when creating competitions
     * @param userHandle
     *            the handle of the user getting setup
     * @return the provisioning result
     * @throws LiquidPortalIllegalArgumentException
     *             It argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>requestorHandle is null/empty -- 3000</li>
     *             <li>userHandle is null/empty -- 3001</li>
     *             <li>cockpitProjectNames is null/empty -- 3002</li>
     *             <li>cockpitProjectNames contains null/empty entries -- 3003</li>
     *             <li>billingProjectIds is null/empty -- 3004</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given requester or user handle
     * @throws InvalidHandleException
     *             If the handle is invalid
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public ProvisionUserResult provisionUser(String requestorHandle, String userHandle, boolean hasAccountAccess,
            String[] cockpitProjectNames, long[] billingProjectIds) throws LiquidPortalIllegalArgumentException,
            LiquidPortalServiceException {
        final String methodName = "provisionUser";
        logEntrance(methodName);

        if (!checkString(requestorHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTORHANDLE_NULL_EMPTY), methodName);
        }
        if (!checkString(userHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USERHANDLE_NULL_EMPTY), methodName);
        }
        if (!checkArray(cockpitProjectNames)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_COCKPITPROJECTNAMES_NULL_EMPTY), methodName);
        }
        if (!checkStringArray(cockpitProjectNames)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_COCKPITPROJECTNAMES_CONTAINS_NULL_EMPTY), methodName);
        }
        if (!checkArray(billingProjectIds)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_BILLINGPROJECTIDS_NULL_EMPTY), methodName);
        }

        UserInfo requestorUserInfo = getUserInfo(requestorHandle, methodName);
        UserInfo userInfo = getUserInfo(userHandle, methodName);

        // ensure requestorUserInfo has Notus Eligibility groups
        if (!userBelongNotusElibilityGroups(requestorUserInfo)) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("requestor handle is not in Notus Eligibility Groups", 5004), methodName);
        }

        // ensure userInfo has Notus Eligibility groups
        if (!userBelongNotusElibilityGroups(userInfo)) {
            sessionContext.setRollbackOnly();
            throw logError(
                    new LiquidPortalServiceException("user handle is not in Notus Eligibility Groups", 5011),
                    methodName);
        }

        try {
            // get full control permission type which type id is
            // fullControlPermissionTypeId
            PermissionType type = getFullControlPermissionType();
            if (type == null) {
                // can not get the full control permission type
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException("Can not get the full control permission type"),
                        methodName);
            }

            List<Permission> permissions = new ArrayList<Permission>();
            List<ProjectData> cockpitProjects = new ArrayList<ProjectData>();

            for (String projectName :  cockpitProjectNames)
            {
                List<ProjectData> tcProjects = projectService.getProjectsByName(projectName);
                if (tcProjects != null && tcProjects.size() > 0)
                {
                    for (ProjectData project : tcProjects) {
                        if (hasAccountAccess) {
                            // the project is in the submitted list, add the user to the project
                            permissions.add(getPermission(userInfo, project, type));
                        } else {
                            // the project is not in the submitted list, remove the user's permission
                            List<Permission> perms = contestServiceFacade.getPermissions(userInfo.getUserId(), project
                                    .getProjectId());
                            for (Permission perm : perms) {
                                // set PermissionType to null to remove the permission
                                perm.setPermissionType(null);
                            }
                            permissions.addAll(perms);
                        }
                    }
                    cockpitProjects.addAll(tcProjects);
                }
            }
            
            contestServiceFacade.updatePermissions(permissions.toArray(new Permission[0]));

            // get billing projects
            List<Project> billingProjects = billingProjectDAO.getProjectsByClientId(notusClientId);
            for (Project proj : billingProjects) {
                if (hasAccountAccess || longInArray(proj.getId(), billingProjectIds)) {
                    // add user to billing project
                    billingProjectDAO.addUserToBillingProjects(userHandle, new long[] { proj.getId() });
                } else {
                    // remove user from billing project
                    billingProjectDAO.removeUserFromBillingProjects(userHandle, new long[] { proj.getId() });
                }
            }

            ProvisionUserResult result = new ProvisionUserResult();
            // assign the cockpitProjects & billingProjects to it
            result.setCockpitProjects(cockpitProjects);
            result.setBillingProjects(billingProjects);

            String[] targetCockpitProjectNames = new String[cockpitProjects.size()];
            long[] targetBillingProjectIds = new long[billingProjects.size()];
            for (int i = 0; i < cockpitProjects.size(); i++) {
                targetCockpitProjectNames[i] = cockpitProjects.get(i).getName();
            }
            for (int i = 0; i < billingProjects.size(); i++) {
                targetBillingProjectIds[i] = billingProjects.get(i).getId();
            }

            // add warning to it if any specified cockpit project name or billing project id is invalid
            List<Warning> warnings = new ArrayList<Warning>();
            for (String name : cockpitProjectNames) {
                if (!stringInArray(name, targetCockpitProjectNames)) {
                    warnings.add(getWarning("Cockpit project name '" + name + "' is invalid", 5006, null));
                }
            }
            for (long id : billingProjectIds) {
                if (!longInArray(id, targetBillingProjectIds)) {
                    warnings.add(getWarning("Billing Project id '" + id + "' is invalid", 5007, null));
                }
            }
            result.setWarnings(warnings);
            logExit(methodName);
            return result;
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when provision user:" + userHandle, e),
                    methodName);
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when provision user:" + userHandle, e),
                    methodName);
        } catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when provision user:" + userHandle, e),
                    methodName);
        } catch (ProjectNotFoundFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when provision user:" + userHandle, e),
                    methodName);
        } catch (IllegalArgumentFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when provision user:" + userHandle, e),
                    methodName);
        }
    }

    /**
     * <p>
     * Creates a new competition. It checks the user has permissions to perform
     * this task. Returns the non-null result of the action.
     * </p>
     *
     * @param requestorHandle
     *            the TopCoder handle of the user that requested the new
     *            competition
     * @param competitionData
     *            the data about the competition to create
     * @param supportHandles
     *            the handles that will perform contest support, such as forum
     *            monitoring
     * @return the competition creation result
     * @throws LiquidPortalIllegalArgumentException
     *             It argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>requestorHandle is null/empty -- 3000</li>
     *             <li>competitionData is null -- 4000</li>
     *             <li>supportHandles is null -- 3005</li>
     *             <li>supportHandles contains null/empty elements -- 3006</li>
     *             <li>contestTypeName is null/empty -- 4001</li>
     *             <li>subContestTypeName is null/empty and contestTypeName =
     *             "studio" -- 4002</li>
     *             <li>contestName is null/empty -- 4003</li>
     *             <li>cockpitProjectName is null/empty -- 4004</li>
     *             <li>requestedStartDate is null -- 4005</li>
     *             <li>requestedStartDate in the past -- 4006</li>
     *             <li>contestTypeName is not in projectCategories and
     *             contestTypeName != "studio" -- 4007</li>
     *             <li>subContestTypeName is not in studioContestTypes and
     *             contestTypeName = "studio" -- 4008</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given requester handle or any of the
     *             supportHandles handles
     * @throws InvalidHandleException
     *             If the handle is invalid
     * @throws ActionNotPermittedException
     *             If the user with the handle does not have the permissions to
     *             perform this action
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public CreateCompetitonResult createCompetition(String requestorHandle, CompetitionData competitionData,
            String[] supportHandles) throws LiquidPortalIllegalArgumentException, LiquidPortalServiceException {
        final String methodName = "createCompetition";
        logEntrance(methodName);

        // check argument
        if (!checkString(requestorHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTORHANDLE_NULL_EMPTY), methodName);
        }
        if (competitionData == null) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_COMPETITIONDATA_NULL), methodName);
        }
        if (supportHandles == null) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_SUPPORTHANDLES_NULL), methodName);
        }
        if (!checkStringArray(supportHandles)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_SUPPORTHANDLES_CONTAINS_NULL_EMPTY), methodName);
        }
        if (!checkString(competitionData.getContestTypeName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_CONTESTTYPENAME_NULL_EMPTY), methodName);
        }
        if (competitionData.getContestTypeName().equals("studio")
                && !checkString(competitionData.getSubContestTypeName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_SUBCONTESTTYPENAME_NULL_EMPTY), methodName);
        }
        if (!checkString(competitionData.getContestName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_CONTESTNAME_NULL_EMPTY), methodName);
        }
        if (!checkString(competitionData.getCockpitProjectName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_COCKPITPROJECTNAME_NULL_EMPTY), methodName);
        }
        if (competitionData.getRequestedStartDate() == null) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTEDSTARTDATE_NULL), methodName);
        }
        if (competitionData.getRequestedStartDate().compareTo(new Date()) < 0) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTEDSTARTDATE_IN_PAST), methodName);
        }
        if (competitionData.getContestTypeName().equals("studio")
                && !studioContestTypes.containsKey(competitionData.getSubContestTypeName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_INVALID_SUBCONTESTTYPENAME), methodName);
        }
        if (!competitionData.getContestTypeName().equals("studio")
                && !projectCategories.containsKey(competitionData.getContestTypeName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_INVALID_CONTESTTYPENAME), methodName);
        }

        List<UserInfo> supportInfos = new ArrayList<UserInfo>();
        // check if support handles exist and in Notus Eligibility groups
        for (String supportHandle : supportHandles) {
            UserInfo user = getUserInfo(supportHandle, methodName);
            if (!userBelongNotusElibilityGroups(user)) {
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException(
                        "support handle is not in Notus Eligibility Groups", 5004), methodName);
            }
        }

        try {
            // get user info
            UserInfo requestorInfo = getUserInfo(requestorHandle, methodName);
            // ensure requestorInfo has Notus Eligibility Groups
            if (userBelongNotusElibilityGroups(requestorInfo)) {
                // requester does not have Notus Eligibility Groups
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException(
                        "requestor handle is not in Notus Eligibility Groups", 5011), methodName);
            }

            // check billing project permission
            if (!billingProjectDAO.checkClientProjectPermission(requestorHandle,
                    competitionData.getBillingProjectId())) {
                // have no permissions
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException(requestorHandle
                        + " has no permission to perform the action", 5003), methodName);
            }

            ProjectData proj;
            List<Warning> warnings = new ArrayList<Warning>();
            // get full control permission type which type id is fullControlPermissionTypeId
            PermissionType type = getFullControlPermissionType();
            if (type == null) {
                // can not get the full control permission type
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException("Can not get the full control permission type"),
                        methodName);
            }

            try {
                // get cockpit project
                proj = projectService.getProjectByName(competitionData.getCockpitProjectName(), notusClientId);
            } catch (ProjectNotFoundFault e) {
                // the cockpit project doesn't exist
                proj = null;
            }

            if (proj != null) {
                // ensure the requestor has the cockpit project permissions
                if (!checkPermission(contestServiceFacade
                        .getPermissions(requestorInfo.getUserId(), proj.getProjectId()))) {
                    sessionContext.setRollbackOnly();
                    throw logError(new LiquidPortalServiceException(String.format(
                            "User {0} does not has permission to project {1}", requestorHandle, proj.getName()), 5003),
                            methodName);
                }
            } else {
                ProjectData projectData = new ProjectData();
                projectData.setName(competitionData.getCockpitProjectName());
                projectData.setProjectId(competitionData.getBillingProjectId());
                proj = projectService.createProject(projectData);
                contestServiceFacade.updatePermissions(new Permission[] { getPermission(requestorInfo, projectData,
                        type) });
                // add waring, project had to be created
                warnings.add(getWarning(String.format("Project <{0}> does not exist", competitionData
                        .getCockpitProjectName()), 5008, null));
            }

            CreateCompetitonResult result = new CreateCompetitonResult();
            if (competitionData.getContestTypeName().equals("studio")) {
                // get capacity full dates
                List<CapacityData> capacityDatas = pipelineServiceFacade.getCapacityFullDates(studioContestTypes
                        .get(competitionData.getSubContestTypeName()), true);
                // create studio competition with nextAvaliableStartDate, name, contest type etc
                ContestData data = new ContestData();
                data.setBillingProject(competitionData.getBillingProjectId());
                data.setName(competitionData.getContestName());
                data.setProjectId(proj.getProjectId());
                StudioCompetition comp = new StudioCompetition(data);
                comp.setType(CompetionType.STUDIO);
                XMLGregorianCalendar nextAvailableStartDate = getNextAvailableStartDate(competitionData
                        .getRequestedStartDate(), capacityDatas, competitionData.getAutoReschedule(), methodName);
                comp.setStartTime(nextAvailableStartDate);
                result.setCompetition(contestServiceFacade.createContest(comp, proj.getProjectId()));
            } else {
                // get capacity full dates
                List<CapacityData> capacityDatas = pipelineServiceFacade.getCapacityFullDates(projectCategories
                        .get(competitionData.getContestTypeName()), false);
                // create with nextAvailableStartDate, name, project category etc
                SoftwareCompetition comp = new SoftwareCompetition();
                comp.setType(CompetionType.SOFTWARE);
                XMLGregorianCalendar nextAvailableStartDate = getNextAvailableStartDate(competitionData
                        .getRequestedStartDate(), capacityDatas, competitionData.getAutoReschedule(), methodName);
                comp.setStartTime(nextAvailableStartDate);
                result.setCompetition(contestServiceFacade.createSoftwareContest(comp, proj.getProjectId()));
            }

            for (UserInfo supportInfo : supportInfos) {
                updatePermission(getPermission(supportInfo, proj, type), supportInfo, warnings);
            }
            result.setWarnings(warnings);
            logExit(methodName);
            return result;
        } catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (DAOConfigurationException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (IllegalArgumentWSException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (AuthorizationFailedFault e) {
            // have no permissions
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    requestorHandle + " has no permission to perform the action", 5003, e), methodName);
        } catch (IllegalArgumentFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (ContestPipelineServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when creating competition", e),
                    methodName);
        } catch (LiquidPortalServiceException e) {
            sessionContext.setRollbackOnly();
            throw e;
        }
    }

    /**
     * <p>
     * Sets up the project with the list of handles that can access the project.
     * Returns the non-null result of the action.
     * </p>
     *
     * @param requestorHandle
     *            the TopCoder handle of the user that requested the project
     *            provisioning
     * @param cockpitProjectName
     *            the name of the project to provision
     * @param handles
     *            the handles of the users to be given full control of the
     *            cockpit project
     * @return the provisioning result
     * @throws LiquidPortalIllegalArgumentException
     *             It argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>requestorHandle is null/empty -- 3000</li>
     *             <li>cockpitProjectName is null/empty -- 4004</li>
     *             <li>handles is null/empty -- 3007</li>
     *             <li>handles contains null/empty elements -- 3008</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given requester handle or any of the
     *             handles
     * @throws InvalidHandleException
     *             If the handle is invalid
     * @throws ActionNotPermittedException
     *             If the user with the handle does not have the permissions to
     *             perform this action
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Result provisionProject(String requestorHandle, String cockpitProjectName, String[] handles)
            throws LiquidPortalIllegalArgumentException,
            LiquidPortalServiceException {
        final String methodName = "provisionProject";
        logEntrance(methodName);

        if (!checkString(requestorHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTORHANDLE_NULL_EMPTY), methodName);
        }
        if (!checkString(cockpitProjectName)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_COCKPITPROJECTNAME_NULL_EMPTY), methodName);
        }
        if (!checkArray(handles)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_HANDLES_NULL_EMPTY), methodName);
        }
        if (!checkStringArray(handles)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_HANDLES_CONTAINS_NULL_EMPTY), methodName);
        }

        // check the handles argument
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for (String handle : handles) {
            UserInfo userInfo = getUserInfo(handle, methodName);
            if (!userBelongNotusElibilityGroups(userInfo)) {
                sessionContext.setRollbackOnly();
                throw logError(
                        new LiquidPortalServiceException("handle: " + handle
                                + " is not in Notus Eligibility Groups", 5004),  methodName);
            }
            userInfos.add(userInfo);
        }

        // check the requestorHandle argument
        UserInfo requestorInfo = getUserInfo(requestorHandle, methodName);
        if (!userBelongNotusElibilityGroups(requestorInfo)) {
            // user does not have notus eligibility groups
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "requestor handle is not in Notus Eligibility Groups", 5011), methodName);
        }

        try {
            // get cockpit project
            ProjectData proj = projectService.getProjectByName(cockpitProjectName, notusClientId);
            if (proj == null) {
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException(
                        "Can not find project:" + cockpitProjectName), methodName);
            }
            if (!billingProjectDAO.checkClientProjectPermission(requestorHandle, proj.getProjectId())) {
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException("requestor doesn't have permissions", 5003), methodName);
            }

            // get full control permission type
            PermissionType type = getFullControlPermissionType();
            if (type == null) {
                // can not get the full control permission type
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException("Can not get the full control permission type"),
                        methodName);
            }

            List<Warning> warnings = new ArrayList<Warning>();
            for (UserInfo userInfo : userInfos) {
                updatePermission(getPermission(userInfo, proj, type), userInfo, warnings);
            }
            Result result = new Result();
            result.setWarnings(warnings);
            logExit(methodName);
            return result;
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when provision project", e), methodName);
        } catch (ProjectNotFoundFault e) {
            // project does not exist
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "Can not find project:" + cockpitProjectName, e), methodName);
        } catch (AuthorizationFailedFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("requestor doesn't have permissions", 5003, e), methodName);
        } catch (IllegalArgumentFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when provision project", e), methodName);
        } catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when provision project", e), methodName);
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when provision project", e), methodName);
        }
    }

    /**
     * <p>
     * Deletes the given contest.
     * </p>
     *
     * @param contestId
     *            the ID of the contest to delet
     * @param requestorHandle
     *            the TopCoder handle of the user that requested the contest
     *            deletion
     * @param reason
     *            the reason for the deletion
     * @param isStudio
     *            flag whether the contest is a studio competition
     * @throws LiquidPortalIllegalArgumentException
     *             It argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>requestorHandle is null/empty -- 3000</li>
     *             <li>reason is empty -- 3009</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given requester handle
     * @throws ContestNotFoundException
     *             If unable to find contest with the given ID
     * @throws ActionNotPermittedException
     *             If the user with the handle does not have the permissions to
     *             perform this action
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteCompetition(String requestorHandle, long contestId, boolean isStudio, String reason)
            throws LiquidPortalIllegalArgumentException, LiquidPortalServiceException {
        final String methodName = "deleteCompetition";
        logEntrance(methodName);

        if (!checkString(requestorHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTORHANDLE_NULL_EMPTY), methodName);
        }
        if (reason != null && reason.trim().length() == 0) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REASON_EMPTY), methodName);
        }

        // check the requestorHandle argument
        UserInfo requestorInfo = getUserInfo(requestorHandle, methodName);
        if (!userBelongNotusElibilityGroups(requestorInfo)) {
            // user does not have notus eligibility groups
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    "requestor handle is not in Notus Eligibility Groups", 5004), methodName);
        }

        try {
            if (isStudio) {
                StudioCompetition comp = contestServiceFacade.getContest(contestId);
                if (comp == null) {
                    sessionContext.setRollbackOnly();
                    throw logError(new LiquidPortalServiceException("can not find contest with contestId:"
                            + contestId, 5010), methodName);
                }
                // check billing account permission for requester
                if (!billingProjectDAO.checkClientProjectPermission(requestorHandle, comp.getContestData()
                        .getBillingProject())) {
                    sessionContext.setRollbackOnly();
                    throw logError(new LiquidPortalServiceException(
                            "the user has no permissions for the billing account associated to the competition", 5003),
                            methodName);
                }
                if (!checkPermission(contestServiceFacade.getPermissions(requestorInfo.getUserId(), comp.getProject()
                        .getProjectId()))) {
                    sessionContext.setRollbackOnly();
                    throw logError(new LiquidPortalServiceException(String.format(
                            "User {0} does not has permission to project {1}", requestorHandle, comp.getProject()
                                    .getName()), 5003), methodName);
                }
                contestServiceFacade.deleteContest(contestId);
            } else {
                // get software competition
                SoftwareCompetition comp = contestServiceFacade.getSoftwareContestByProjectId(contestId);
                // check permission
                if (!checkPermission(contestServiceFacade.getPermissions(requestorInfo.getUserId(), comp.getProject()
                        .getProjectId()))) {
                    sessionContext.setRollbackOnly();
                    throw logError(new LiquidPortalServiceException(String.format(
                            "User {0} does not has permission to project {1}", requestorHandle, comp.getProject()
                                    .getName()), 5003), methodName);
                }
                // mark software contest as deleted
                comp.setStatus(Status.DELETED);
                // use <Requestor Handle>: <Reason> as the explanation
                comp.setProjectHeaderReason(requestorHandle + ": " + reason);
                contestServiceFacade.updateSoftwareContest(comp, comp.getProject().getProjectId());
            }
            logExit(methodName);
        } catch (com.topcoder.service.studio.ContestNotFoundException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("can not find contest with contestId:" + contestId, 5010,
                    e), methodName);
        } catch (PersistenceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when deleting competition", e),
                    methodName);
        } catch (IllegalArgumentWSException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when deleting competition", e),
                    methodName);
        } catch (UserNotAuthorizedException e) {
            // have no permissions
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    requestorHandle + " has no permission to perform the action", 5003, e), methodName);
        } catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when deleting competition", e),
                    methodName);
        } catch (ContestServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when deleting competition", e),
                    methodName);
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when deleting competition", e),
                    methodName);
        }
    }

    /**
     * <p>
     * Removes the user with the given handle.
     * </p>
     *
     * @param requestorHandle
     *            the TopCoder handle of the user that requested the delete
     * @param userHandle
     *            the handle of the user to be decommisioned
     * @throws LiquidPortalIllegalArgumentException
     *             It argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>requestorHandle is null/empty -- 3000</li>
     *             <li>userHandle is null/empty -- 3001</li>
     *             </ul>
     * @throws HandleNotFoundException
     *             If unable to find the given requester or user handle
     * @throws ActionNotPermittedException
     *             If the user with the handle does not have the permissions to
     *             perform this action
     * @throws LiquidPortalServiceException
     *             If an error occurs while performing the operation
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void decommissionUser(String requestorHandle, String userHandle)
            throws LiquidPortalIllegalArgumentException,
            LiquidPortalServiceException {
        final String methodName = "decommissionUser";
        logEntrance(methodName);

        if (!checkString(requestorHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_REQUESTORHANDLE_NULL_EMPTY), methodName);
        }
        if (!checkString(userHandle)) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USERHANDLE_NULL_EMPTY), methodName);
        }

        // check the requestorHandle argument
        UserInfo requestorInfo = getUserInfo(requestorHandle, methodName);
        if (!userBelongNotusElibilityGroups(requestorInfo)) {
            // user does not have notus eligibility groups
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("requestor handle is not in Notus Eligibility Groups", 5004), methodName);
        }
        // check the userHandle argument
        UserInfo userInfo = getUserInfo(userHandle, methodName);
        if (!userBelongNotusElibilityGroups(userInfo)) {
            // user does not have notus eligibility groups
            sessionContext.setRollbackOnly();
            throw logError(
                    new LiquidPortalServiceException("user handle is not in Notus Eligibility Groups", 5011),
                    methodName);
        }

        try {
            // get cockpit projects
            List<ProjectData> cockpitProjects = projectService.getProjectsForUser(notusClientId);
            // For each cockpit project that is associated with Notus Account remove user
            List<Permission> permList = new ArrayList<Permission>();
            for (ProjectData proj : cockpitProjects) {
                // get all the permissions that the user own for a given project
                List<Permission> permissions = contestServiceFacade.getPermissions(userInfo.getUserId(), proj
                        .getProjectId());
                for (Permission perm : permissions) {
                    // set PermissionType to null to remove the premission
                    perm.setPermissionType(null);
                }
                // to remove the permissions
                permList.addAll(permissions);
            }
            // remove permissions for userHandle
            contestServiceFacade.updatePermissions(permList.toArray(new Permission[0]));

            // get billing projects
            List<Project> billingProjects = billingProjectDAO.getProjectsByClientId(notusClientId);
            // remove userHandle from billing projects
            long[] billingProjectIds = new long[billingProjects.size()];
            for (int i = 0; i < billingProjects.size(); i++) {
                billingProjectIds[i] = billingProjects.get(i).getId();
            }
            billingProjectDAO.removeUserFromBillingProjects(userHandle, billingProjectIds);

            // Remove user from Notus Eligibility groups
            userService.removeUserFromGroups(userHandle, notusEligibilityGroupIds);
            // Remove user from Notus Observer Terms
            userService.removeUserTerm(userHandle, notusObserverTermsId);
            logExit(methodName);
        } catch (UserServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when decommission user", e), methodName);
        } catch (PersistenceFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when decommission user", e), methodName);
        } catch (UserNotFoundFault e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when decommission user", e), methodName);
        } catch (AuthorizationFailedFault e) {
            // have no permissions
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException(
                    requestorHandle + " has no permission to perform the action", 5003, e), methodName);
        } catch (PermissionServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when decommission user", e), methodName);
        } catch (DAOException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("Error occurs when decommission user", e), methodName);
        }
    }

    /**
     * <p>
     * Send a jira notification that the given user cannot be added.
     * </p>
     *
     * @param handle
     *            the handle we could not add to terms and groups
     * @throws LiquidPortalServiceException
     *             If there is an error during the creation and sending of the
     *             notification
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void sendJiraNotification(String handle) throws LiquidPortalServiceException {
        String methodName = "sendJiraNotification";
        logEntrance(methodName);

        try {
            Template template = documentGenerator.getTemplate(jiraEmailMessageTemplateName);
            String data = "<DATA><HANDLE>" + handle + "</HANDLE></DATA>";
            String message = documentGenerator.applyTemplate(template, data);
            TCSEmailMessage emailMessage = new TCSEmailMessage();
            emailMessage.setFromAddress(jiraEmailSender);
            emailMessage.setBody(message);
            emailMessage.setToAddress(jiraEmailAddress, TCSEmailMessage.TO);
            emailMessage.setSubject(jiraEmailSubject);
            EmailEngine.send(emailMessage);

            logExit(methodName);
        } catch (TemplateSourceException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        } catch (TemplateFormatException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        } catch (TemplateDataFormatException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        } catch (AddressException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        } catch (ConfigManagerException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        } catch (SendingException e) {
            throw logError(new LiquidPortalServiceException(
                    "Error occurs when sending JIRA notification to handle:" + handle, e), methodName);
        }
    }

    /**
     * <p>
     * Gets a required property form the configuration object.
     * </p>
     *
     * @param tokenKey
     *            the name of the property
     * @param configObject
     *            the configuration object
     * @return the value of the property
     * @throws LiquidPortalServiceConfigurationException
     *             If any error occurs when getting the property, or the value
     *             of property is null/empty.
     */
    private static String getConfigurationValue(String tokenKey, ConfigurationObject configObject) {
        String token;
        try {
            token = (String) configObject.getPropertyValue(tokenKey);
            checkString(tokenKey, token);
        } catch (ClassCastException e) {
            throw new LiquidPortalServiceConfigurationException("The '" + tokenKey + "' should be String.", e);
        } catch (ConfigurationAccessException e) {
            throw new LiquidPortalServiceConfigurationException("Error occurs when accessing configuration file", e);
        }

        return token;
    }

    /**
     * <p>
     * Gets a required property which is type of long from the configuration
     * object.
     * </p>
     *
     * @param tokenKey
     *            the name of the property
     * @param configObject
     *            the configuration object
     * @return the integer value of the property
     * @throws LiquidPortalServiceConfigurationException
     *             If any error occurs when getting the property, or the value
     *             of property is null/empty, or the value is not an integer
     */
    private static long getConfigurationLongValue(String tokenKey, ConfigurationObject configObject) {
        String token = getConfigurationValue(tokenKey, configObject);
        try {
            return Long.parseLong(token);
        } catch (NumberFormatException e) {
            throw new LiquidPortalServiceConfigurationException(
                    "The '" + tokenKey + "' property should be integer.", e);
        }
    }

    /**
     * <p>
     * Gets mapping record of a property from configuration object.
     * </p>
     * <p>
     * The format of value should be name::id, where name must be non empty
     * String, and id must be an integer.
     * </p>
     *
     * @param tokenKey
     *            the name of property
     * @param configObject
     *            the configuration object
     * @return the mapping record of the property
     * @throws LiquidPortalServiceConfigurationException
     *             If any error occurs when getting the property, or the value
     *             is wrong format
     */
    private static Map<String, Integer> getConfigurationMapValue(String tokenKey, ConfigurationObject configObject) {
        try {
            Object[] values = configObject.getPropertyValues(tokenKey);
            if (values == null) {
                throw new LiquidPortalServiceConfigurationException("The '" + tokenKey
                        + "' property should be configed.");
            }

            Map<String, Integer> map = new HashMap<String, Integer>();
            for (Object obj : values) {
                String value = (String) obj;
                checkString(tokenKey, value);
                String[] parts = value.split("::");
                if (parts.length != 2) {
                    throw new LiquidPortalServiceConfigurationException("The value of '" + tokenKey
                            + "' property should be 'name::id' format.");
                }
                checkString(tokenKey + ".name", parts[0]);
                Integer id = Integer.parseInt(parts[1]);
                map.put(parts[0], id);
            }
            return map;
        } catch (NumberFormatException e) {
            throw new LiquidPortalServiceConfigurationException("The '" + tokenKey
                    + ".id' property should be an integer.", e);
        } catch (ConfigurationAccessException e) {
            throw new LiquidPortalServiceConfigurationException("Error occurs when accessing configuration file", e);
        }
    }

    /**
     * <p>
     * Checks object for null and throw
     * LiquidPortalServiceConfigurationException if it is.
     * </p>
     *
     * @param name
     *            of the object, to be used in messages.
     * @param data
     *            Object to check.
     * @throws LiquidPortalServiceConfigurationException
     *             when data parameter is null.
     */
    private static void checkNull(String name, Object data) {
        if (data == null) {
            throw new LiquidPortalServiceConfigurationException("The '" + name + "' property should be configed.");
        }
    }

    /**
     * <p>
     * Checks string for being null or empty.
     * </p>
     *
     * @param name
     *            of the string, to be used in messages.
     * @param data
     *            String to check.
     * @throws LiquidPortalServiceConfigurationException
     *             when data parameter is null or empty.
     */
    private static void checkString(String name, String data) {
        checkNull(name, data);
        if (data.trim().length() == 0) {
            throw new LiquidPortalServiceConfigurationException("The '" + name + "' property should not be empty.");
        }
    }

    /**
     * <p>
     * Checks string for being null or empty.
     * </p>
     *
     * @param data
     *            String to check.
     * @return true if string is not null or empty, false otherwise
     */
    private static boolean checkString(String data) {
        if (data == null) {
            return false;
        }
        if (data.trim().length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * Checks array for being null or empty.
     * </p>
     *
     * @param data
     *            Array to check.
     * @return true if array is not null or empty, false otherwise
     */
    private static boolean checkArray(Object[] data) {
        return (data != null && data.length != 0);
    }

    /**
     * <p>
     * Checks array or long for being null or empty.
     * </p>
     *
     * @param data
     *            Array to check.
     * @return true if array is not null or empty, false otherwise
     */
    private static boolean checkArray(long[] data) {
        return (data != null && data.length != 0);
    }

    /**
     * <p>
     * Checks a string array for containing null/empty elements.
     * </p>
     *
     * @param data
     *            String array to check
     * @return true if the array doesn't contains null/empty elements, false
     *         otherwise
     */
    private static boolean checkStringArray(String[] data) {
        for (String str : data) {
            if (str == null || str.trim().length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Log the exit of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logExit(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "[Exiting method " + this.getClass().getName() + "." + methodName + "]");
        }
    }

    /**
     * <p>
     * Log the entrance of a method.
     * </p>
     *
     * @param methodName
     *            the name of the method
     */
    private void logEntrance(String methodName) {
        if (logger != null) {
            logger.log(Level.INFO, "[Entering method " + this.getClass().getName() + "." + methodName + "]");
        }
    }

    /**
     * <p>
     * Logs the error.
     * </p>
     *
     * @param <T>
     *            the generic class type of error
     * @param error
     *            the error needs to be logged
     * @param methodName
     *            the method error occurred
     * @return the error exception
     */
    private <T extends Exception> T logError(T error, String methodName) {
        if (logger != null) {
            logger.log(Level.ERROR, "[Error in method " + this.getClass().getName() + "." + methodName + ": Details "
                    + error.getMessage() + "]", error);
        }
        return error;
    }

    /**
     * <p>
     * Get an instance of Warning with the given exception and message.
     * </p>
     *
     * @param message
     *            the given message
     * @param exception
     *            the given exception
     * @return the instance of Warning
     */
    private static Warning getWarning(String message, int errorCode, Throwable exception) {
        Warning warning = new Warning();
        warning.setMessage(message);
        warning.setErrorCode(errorCode);
        return warning;
    }

    /**
     * <p> Checks if a user is part of the Notus Eligibility group. </p>
     *
     * @param user
     *            the user to check
     * @return true if the user is part of the Notus Eligibility group, false
     *         otherwise
     */
    private boolean userBelongNotusElibilityGroups(UserInfo user) {
        if (user.getGroupIds() == null) {
            return false;
        }
        for (long id : notusEligibilityGroupIds) {
            boolean find = false;
            for (long gid : user.getGroupIds()) {
                if (id == gid) {
                    find = true;
                    break;
                }
            }
            if (!find) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>
     * Checks if a string array contains an element equals to a target string.
     * </p>
     *
     * @param string
     *            the target string
     * @param array
     *            the string array
     * @return true if string array contains an element equals to the target
     *         string, false otherwise
     */
    private boolean stringInArray(String string, String[] array) {
        for (String str : array) {
            if (str.equals(string)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Checks if a long array contains an element equals to a target value.
     * </p>
     *
     * @param val
     *            the target value
     * @param array
     *            the long array
     * @return true if long array contains an element equals to the target
     *         value, false otherwise
     */
    private static boolean longInArray(long val, long[] array) {
        for (long data : array) {
            if (val == data) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Check the UserInfo object.
     * </p>
     *
     * @param user
     *            the UserInfo object to be checked
     * @param methodName
     *            the method name which to check the object
     * @throws LiquidPortalIllegalArgumentException
     *             If the object is invalid, the error details are provided by
     *             the errorCode in the exception:
     *             <ul>
     *             <li>user is null -- 2000</li>
     *             <li>user.firstName is null/empty -- 2001</li>
     *             <li>user.lastName is null/empty -- 2002</li>
     *             <li>user.emailAddress is null/empty -- 2003</li>
     *             <li>user.handle is null/empty -- 2005</li>
     *             </ul>
     */
    private void checkUserInfo(UserInfo user, String methodName) throws LiquidPortalIllegalArgumentException {
        if (user == null) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_NULL), methodName);
        }
        if (!checkString(user.getFirstName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_FIRSTNAME_NULL_EMPTY), methodName);
        }
        if (!checkString(user.getLastName())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_LASTNAME_NULL_EMPTY), methodName);
        }
        if (!checkString(user.getEmailAddress())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_EMAIL_NULL_EMPTY), methodName);
        }
        if (!checkString(user.getHandle())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_HANDLE_NULL_EMPTY), methodName);
        }
    }

    /**
     * <p>
     * Check the User object.
     * </p>
     *
     * @param user
     *            the User object to be checked
     * @param methodName
     *            the method name which to check the object
     * @throws LiquidPortalIllegalArgumentException
     *             If argument is invalid, the error details are provided by the
     *             errorCode in the exception:
     *             <ul>
     *             <li>user is null -- 2000</li>
     *             <li>user.firstName is null/empty -- 2001</li>
     *             <li>user.lastName is null/empty -- 2002</li>
     *             <li>user.emailAddress is null/empty -- 2003</li>
     *             <li>user.password is null/empty -- 2004</li>
     *             <li>user.handle is null/empty -- 2005</li>
     *             <li>user.phone is null/empty -- 2006</li>
     *             </ul>
     */
    private void checkUser(User user, String methodName) throws LiquidPortalIllegalArgumentException {
        checkUserInfo(user, methodName);
        if (!checkString(user.getPassword())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_PASSWORD_NULL_EMPTY), methodName);
        }
        if (!checkString(user.getPhone())) {
            throw logError(new LiquidPortalIllegalArgumentException(
                    LiquidPortalIllegalArgumentException.EC_USER_PHONE_NULL_EMPTY), methodName);
        }
    }

    /**
     * <p>
     * Get a user info by handle.
     * </p>
     *
     * @param handle
     *            the handle name
     * @param methodName
     *            the method name which calls this method
     * @return an instance of UserInfo whose name is handle
     * @throws HandleNotFoundException
     *             if there is no such user info
     * @throws LiquidPortalServiceException
     *             if any error occurs
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private UserInfo getUserInfo(String handle, String methodName) throws LiquidPortalServiceException {
        try {
            // get user info for requester
            UserInfo userInfo = userService.getUserInfo(handle);
            if (userInfo == null) {
                sessionContext.setRollbackOnly();
                throw logError(new LiquidPortalServiceException("can not find handle:" + handle, 5002), methodName);
            }
            return userInfo;
        } catch (UserServiceException e) {
            sessionContext.setRollbackOnly();
            throw logError(new LiquidPortalServiceException("can not find handle:" + handle, 5002), methodName);
        }
    }

    /**
     * <p>
     * Get a PermissionType that has full control permission.
     * </p>
     *
     * @return the instance of PermissionType which has full control permission
     * @throws PermissionServiceException
     *             If any error occurs
     */
    private PermissionType getFullControlPermissionType() throws PermissionServiceException {
        PermissionType type = new PermissionType();
        type.setPermissionTypeId(PermissionType.PERMISSION_TYPE_PROJECT_FULL);
        type.setName("project_full");
        return type;
    }

    /**
     * <p>
     * Get an instance of Permission.
     * </p>
     *
     * @param userInfo
     *            the instance of UserInfo which has the permission
     * @param project
     *            the instance of ProjectData
     * @param type
     *            the type of the permission
     * @return an instance of Permission
     */
    private static Permission getPermission(UserInfo userInfo, ProjectData project, PermissionType type) {
        Permission permission = new Permission();
        permission.setResourceId(project.getProjectId());
        permission.setResourceName(project.getName());
        permission.setUserId(userInfo.getUserId());
        permission.setUserHandle(userInfo.getHandle());
        permission.setPermissionType(type);
        return permission;
    }

    /**
     * <p>
     * Find out next available start date.
     * </p>
     *
     * @param requestedStartDate
     *            the requested start date
     * @param capacityDates
     *            capacity date
     * @param autoSchedule
     *            if auto schedule enabled
     * @return next available start date
     * @throws DatatypeConfigurationException
     *             failed to create a DatatypeFactory
     */
    private XMLGregorianCalendar getNextAvailableStartDate(Date requestedStartDate, List<CapacityData> capacityDates,
            boolean autoSchedule, String methodName) throws LiquidPortalServiceException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(requestedStartDate);

        try {
            XMLGregorianCalendar requestDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

            if (!isContains(capacityDates, requestDate)) {
                return requestDate;
            } else if (autoSchedule) {
                while (true) {
                    // next date
                    requestDate.add(DatatypeFactory.newInstance().newDuration("p1d"));
                    if (!isContains(capacityDates, requestDate)) {
                        return requestDate;
                    }
                }
            } else {
                // date was unavailable
                throw logError(new LiquidPortalServiceException("Project start date is unavailable"), methodName);
            }
        } catch (DatatypeConfigurationException e) {
            throw logError(new LiquidPortalServiceException(e), methodName);
        }
    }

    /**
     * <p>
     * Check if the given requested date is in the capacity dates.
     * </p>
     *
     * @param capacityDates
     *            the capacity dates
     * @param requestDate
     *            the request date
     * @return true, if yes; false otherwise
     */
    private static boolean isContains(List<CapacityData> capacityDates, XMLGregorianCalendar requestDate) {
        for (CapacityData data : capacityDates) {
            if (data.getDate() == requestDate) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * Check if a user have permission to operate a project.
     * </p>
     *
     * @param permissions
     *            the permissions that the user has
     * @return true if the user have permission to operate the project, false
     *         otherwise
     */
    private boolean checkPermission(List<Permission> permissions) {
        if (permissions != null) {
            for (Permission permission : permissions) {
                if (permission.getPermissionType().getPermissionTypeId() == fullControlPermissionTypeId) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * <p>
     * Add user to notusEligibilityGroup. If can not add the user to the group, add a warning message
     * to warning list.
     * </p>
     *
     * @param userInfo the user info
     * @param warnings the warning list
     * @param methodName the name of the calling method
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void addUserToNotusEligibilityGroup(UserInfo userInfo, List<Warning> warnings, String methodName) throws
        LiquidPortalServiceException{
        try {
            userService.addUserToGroups(userInfo.getHandle(), notusEligibilityGroupIds);
        } catch (IllegalArgumentException e) {
            // notusEligibilityGroupIds is invalid
            logError(e, methodName);
            // send JIRA email
            sendJiraNotification(userInfo.getHandle());
            warnings.add(getWarning("Can not add user to notusEligibilityGroup", 5000, e));
        } catch (UserServiceException e) {
            // can not add user to the notusEligibilityGroupIds
            logError(e, methodName);
            // send JIRA email
            sendJiraNotification(userInfo.getHandle());
            warnings.add(getWarning("Can not add user to notusEligibilityGroup", 5001, e));
        }
    }

    /**
     * <p>
     * Add user to terms group. If can not add the user to the group, add a warning message
     * to warning list.
     * </p>
     *
     * @param userInfo the user info
     * @param termsAgreedDate the agreed date
     * @param warnings the warning list
     * @param methodName the name of the calling method
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void addUserToTermsGroup(UserInfo userInfo, Date termsAgreedDate,
            List<Warning> warnings, String methodName) throws LiquidPortalServiceException {
        // add user to terms group
        try {
            userService.addUserTerm(userInfo.getHandle(), notusObserverTermsId, termsAgreedDate);
        } catch (IllegalArgumentException e) {
            // notusObserverTermsId is invalid
            logError(e, methodName);
            // send JIRA email
            sendJiraNotification(userInfo.getHandle());
            warnings.add(getWarning("Can not add user to terms group", 5001, e));
        } catch (UserServiceException e) {
            // can not add user to the Terms groups
            logError(e, methodName);
            // send JIRA email
            sendJiraNotification(userInfo.getHandle());
            warnings.add(getWarning("Can not add user to terms group", 5001, e));
        }
    }

    /**
     * <p>
     * Call contest service facade to update Permission for a user. If error occurs, add a warning
     * to the warning list.
     * </p>
     *
     * @param permission the Permission needed to be updated
     * @param userInfo the user info
     * @param warnings the warning list
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private void updatePermission(Permission permission, UserInfo userInfo, List<Warning> warnings) {
        try {
            contestServiceFacade.updatePermissions(new Permission[] { permission });
        } catch (PermissionServiceException e) {
            warnings.add(getWarning(String.format("Failed to assign permission to user <{0}>",
                    userInfo.getHandle()), 5013, e));
        }
    }
}
