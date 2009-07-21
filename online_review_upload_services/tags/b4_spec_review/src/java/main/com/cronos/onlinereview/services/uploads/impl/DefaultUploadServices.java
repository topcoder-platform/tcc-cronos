/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.services.uploads.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cronos.onlinereview.autoscreening.management.ScreeningTaskAlreadyExistsException;
import com.cronos.onlinereview.external.ConfigException;
import com.cronos.onlinereview.external.ExternalUser;
import com.cronos.onlinereview.external.RetrievalException;
import com.cronos.onlinereview.external.UserRetrieval;
import com.cronos.onlinereview.external.impl.DBUserRetrieval;
import com.cronos.onlinereview.services.uploads.ConfigurationException;
import com.cronos.onlinereview.services.uploads.InvalidProjectException;
import com.cronos.onlinereview.services.uploads.InvalidProjectPhaseException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionException;
import com.cronos.onlinereview.services.uploads.InvalidSubmissionStatusException;
import com.cronos.onlinereview.services.uploads.InvalidUserException;
import com.cronos.onlinereview.services.uploads.ManagersProvider;
import com.cronos.onlinereview.services.uploads.PersistenceException;
import com.cronos.onlinereview.services.uploads.UploadServices;
import com.cronos.onlinereview.services.uploads.UploadServicesException;
import com.topcoder.db.connectionfactory.DBConnectionException;
import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.db.connectionfactory.UnknownConnectionException;
import com.topcoder.management.deliverable.Submission;
import com.topcoder.management.deliverable.SubmissionStatus;
import com.topcoder.management.deliverable.Upload;
import com.topcoder.management.deliverable.UploadManager;
import com.topcoder.management.deliverable.UploadStatus;
import com.topcoder.management.deliverable.UploadType;
import com.topcoder.management.deliverable.persistence.UploadPersistenceException;
import com.topcoder.management.deliverable.search.SubmissionFilterBuilder;
import com.topcoder.management.deliverable.search.UploadFilterBuilder;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.project.Project;
import com.topcoder.management.resource.NotificationType;
import com.topcoder.management.resource.Resource;
import com.topcoder.management.resource.ResourceManager;
import com.topcoder.management.resource.ResourceRole;
import com.topcoder.management.resource.persistence.ResourcePersistenceException;
import com.topcoder.management.resource.search.ResourceFilterBuilder;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.search.builder.SearchBuilderException;
import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.OrFilter;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.UnknownNamespaceException;
import com.topcoder.util.log.Level;

/**
 * <p>
 * This is the default implementation of <code>UploadServices</code> interface. It manages different type of
 * upload. It used all managers from <code>ManagerProvider</code> to perform several operations. All the methods
 * are logged. It's possible to construct the instance through configuration and <code>ObjectFactory</code> and
 * set via constructor.
 * </p>
 * <p>
 * A sample configuration file that can be used is given below.
 * 
 * <pre>
 *  &lt;Config name=&quot;com.cronos.onlinereview.services.uploads.impl.DefaultUploadServices&quot;&gt;
 *      &lt;Property name=&quot;objectFactoryNamespace&quot;&gt;
 *          &lt;Value&gt;myObjectFactory&lt;/Value&gt;
 *      &lt;/Property&gt;
 *      &lt;Property name=&quot;managersProviderIdentifier&quot;&gt;
 *          &lt;Value&gt;managersProvider&lt;/Value&gt;
 *      &lt;/Property&gt;
 *  &lt;/Config&gt;
 * </pre>
 * 
 * </p>
 * <p>
 * Thread safety: the thread safety is completely relied to the managers implementations because it's impossible to
 * change the other variables.
 * </p>
 * 
 * @author fabrizyo, cyberjag
 * @version 1.0
 */
public class DefaultUploadServices implements UploadServices {

    /**
     * This member variable is a string constant that defines the name of the configuration namespace which the
     * parameters for database connection factory are stored under.
     */
    public static final String DB_CONNECTION_NAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

    /**
     * <p>
     * Represents the default namespace for this class used to load the configuration with
     * <code>ConfigManager</code>.
     * </p>
     */
    public static final String DEFAULT_NAMESPACE = DefaultUploadServices.class.getName();

    /**
     * Namespace used for the config details.
     */
    private final String namespace;

    /**
     * <p>
     * Represents the logger to log all operations, exceptions, etc. It is initialized statically.
     * </p>
     */
    private static final com.topcoder.util.log.Log LOG = com.topcoder.util.log.LogManager
            .getLog(DefaultUploadServices.class.getName());

    /**
     * <p>
     * It contains all the managers used in this class. When you meet a Manager you must use the related getter
     * methods of this <code>ManagersProvider</code>. It is defined in constructor and cannot be
     * <code>null</code>.
     * </p>
     */
    private final ManagersProvider managersProvider;

    /**
     * <p>
     * Creates <code>DefaultUploadServices</code> with the specified managersProvider.
     * </p>
     * 
     * @param managersProvider
     *            the provider of managers used by this class
     * @throws IllegalArgumentException
     *             if managersProvider argument is <code>null</code>
     */
    public DefaultUploadServices(ManagersProvider managersProvider) {
        Helper.checkNull(managersProvider, "managersProvider", LOG);
        this.managersProvider = managersProvider;
        this.namespace = DEFAULT_NAMESPACE;
    }

    /**
     * <p>
     * Creates <code>DefaultUploadServices</code> using the configuration with default namespace.
     * </p>
     * 
     * @throws ConfigurationException
     *             If any error occurs during accessing configuration. If bad configuration is detected.
     */
    public DefaultUploadServices() throws ConfigurationException {
        this(DEFAULT_NAMESPACE);
    }

    /**
     * <p>
     * Creates <code>DefaultUploadServices</code> using the configuration with specified namespace.
     * </p>
     * 
     * @param namespace
     *            the namespace to load configuration
     * @throws ConfigurationException
     *             If any error occurs during accessing configuration. If bad configuration is detected.
     * @throws IllegalArgumentException
     *             if namespace is <code>null</code> or trim to empty
     */
    public DefaultUploadServices(String namespace) throws ConfigurationException {
        Helper.checkString(namespace, "namespace", LOG);
        ManagersProvider provider = (ManagersProvider) Helper.createObject(namespace,
                "managersProviderIdentifier", "DefaultManagerProvider", LOG, ManagersProvider.class, null);
        if (provider == null) {
            provider = new DefaultManagersProvider();
        }
        this.managersProvider = provider;
        this.namespace = namespace;
        Helper.logFormat(LOG, Level.INFO, "ManagersProvider created using ObjectFactory");
    }

    /**
     * <p>
     * Adds a new submission for an user in a particular project.
     * </p>
     * <p>
     * If the project allows multiple submissions for users, it will add the new submission and return. If multiple
     * submission are not allowed for the project firstly, it will add the new submission, secondly mark previous
     * submissions as deleted and then return.
     * </p>
     * 
     * @param projectId
     *            the project's id
     * @param userId
     *            the user's id
     * @param filename
     *            the file name to use
     * @return the id of the new submission
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws UploadServicesException
     *             if some other exception occurs in the process (wrap it)
     * @throws InvalidProjectException
     *             if the project does not exist
     * @throws InvalidProjectPhaseException
     *             if neither Submission or Screening phase are opened
     * @throws InvalidUserException
     *             if the user does not exist or has not the submitter role
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     */
    public long uploadSubmission(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadServices#uploadSubmission(long, long, String)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the submitter role
        Resource resource = null;
        try {
        	resource = getResource(projectId, userId, new String[] {"Submitter"});
        } catch (InvalidUserException e) {
        	Helper.logFormat(LOG, Level.INFO, "Creating submitter role for user: {0} in project {1}",
                    new Object[] {userId, projectId});
			try {
				resource = managersProvider.getResourceManager().getResource(addSubmitter(projectId, userId));
			} catch (ResourcePersistenceException e1) {
				Helper.logFormat(LOG, Level.ERROR, e, "Failed to create submitter for user: {0} and project {1}.",
	                    new Object[] {userId, projectId});
	            throw new UploadServicesException("Failed to create submitter for user: " + userId + " and project "
	                    + projectId + ".", e);
			}
		}

        try {
            com.topcoder.project.phases.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);
            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Submission" or "Screening"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null) {
                    Helper.logFormat(LOG, Level.INFO, "Looping through phase {0} of the project.",
                            new Object[] {phase.getPhaseType().getName()});
                }
                if (phase.getPhaseType() != null
                        && ("Submission".equals(phase.getPhaseType().getName()) || "Screening".equals(phase
                                .getPhaseType().getName()))) {
                    Helper.logFormat(LOG, Level.INFO, "Current status for the phase {0} is {1} of the project.",
                            new Object[] {phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});
                    // check if submission or screening phase are open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {
                        // create a new Submission
                        Submission submission = new Submission();

                        Helper.logFormat(LOG, Level.INFO,
                                "Current status for the phase {0} is {1} of the project.", new Object[] {
                                        phase.getPhaseType().getName(), phase.getPhaseStatus().getName()});
                        // iterate over all SubmissionStatuses, get the SubmissionStatus with name "Active"
                        // and set to submission
                        SubmissionStatus[] submissionStatus = managersProvider.getUploadManager()
                                .getAllSubmissionStatuses();
                        for (SubmissionStatus status : submissionStatus) {
                            if ("Active".equals(status.getName())) {
                                submission.setSubmissionStatus(status);
                                break;
                            }
                        }

                        Upload upload = createUpload(projectId, resource.getId(), filename, "Submission");
                        Helper.logFormat(LOG, Level.INFO,
                                "Upload created for the  projectId {0}, userId {1} with filename {2}.",
                                new Object[] {projectId, userId, filename});

                        String operator = "" + userId;
                        // persist the upload
                        managersProvider.getUploadManager().createUpload(upload, operator);

                        Helper.logFormat(LOG, Level.INFO,
                                "Created submission Upload for project {0}, user {1} with file name {2}.",
                                new Object[] {projectId, userId, filename});

                        // set the upload.
                        submission.setUpload(upload);

                        // persist the submission with uploadManager.createSubmission with the useId as
                        // operator
                        managersProvider.getUploadManager().createSubmission(submission, operator);

                        Helper.logFormat(LOG, Level.INFO, "Created submission for project {0}, user {1}.",
                                new Object[] {projectId, userId});

                        // associate the submission with the submitter resource
                        resource.addSubmission(submission.getId());

                        Helper.logFormat(LOG, Level.INFO, "Added submission {0} to resource.",
                                new Object[] {submission.getId()});

                        // Persist the resource using ResourceManager#updateResource
                        managersProvider.getResourceManager().updateResource(resource, operator);

                        Helper.logFormat(LOG, Level.INFO, "Updated resource using the operator {0}.",
                                new Object[] {operator});

                        // initiate the screening
                        managersProvider.getScreeningManager().initiateScreening(upload.getId(), operator);
                        Helper.logFormat(LOG, Level.INFO,
                                "Initiated screening for submission {0} using operator {1}.", new Object[] {
                                        submission.getId(), operator});

                        // If the project DOESN'T allow multiple submissions hence its property "Allow
                        // multiple submissions" will be false
                        Boolean allow = Boolean.parseBoolean((String) project
                                .getProperty("Allow multiple submissions"));

                        if (!allow) {
                            deletePreviousSubmissions(userId, resource, submissionStatus);
                            Helper.logFormat(LOG, Level.INFO,
                                    "Marked previous submissions for deletion for the user {0}.",
                                    new Object[] {userId});
                        }

                        return submission.getId();
                    }

                } // end of Submission or Screening
            } // end of for loop
            Helper.logFormat(LOG, Level.ERROR, "Failed to upload submission for the projectId {0}, userId {1}",
                    new Object[] {project.getId(), userId});
            throw new InvalidProjectException("Failed to upload submission for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new UploadServicesException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (com.cronos.onlinereview.autoscreening.management.PersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (ScreeningTaskAlreadyExistsException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new UploadServicesException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload submission for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new UploadServicesException("Failed to upload submission for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper
                    .logFormat(LOG, Level.DEBUG,
                            "Exited DefaultUploadServices#uploadSubmission(long, long, String)");
        }
    }

    /**
     * <p>
     * Adds a new final fix upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     * 
     * @param projectId
     *            the project's id
     * @param userId
     *            the user's id
     * @param filename
     *            the file name to use
     * @return the id of the created final fix submission.
     * @throws InvalidProjectException
     *             if the project does not exist
     * @throws InvalidProjectPhaseException
     *             if Final Fix phase is not opened
     * @throws InvalidUserException
     *             if the user does not exist or she/he is not winner submitter
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws UploadServicesException
     *             if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     */
    public long uploadFinalFix(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadServices#uploadFinalFix(long, long, String)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the submitter role
        Resource resource = getResource(projectId, userId, new String[] {"Submitter"});

        // Check that the user is the winner
        Long winnerId = new Long(Long.parseLong(project.getProperty("Winner External Reference ID")));
        if (winnerId != userId) {
            throw new InvalidUserException("The given user is not the winner", userId);
        }

        try {
            com.topcoder.project.phases.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);
            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Final Fix"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null && ("Final Fix".equals(phase.getPhaseType().getName()))) {
                    // check if final fix is open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {

                        // create a new Upload
                        Upload upload = createUpload(projectId, resource.getId(), filename, "Final Fix");

                        String operator = "" + userId;

                        UploadManager uploadManager = managersProvider.getUploadManager();

                        // persist the upload
                        uploadManager.createUpload(upload, operator);

                        Helper.logFormat(LOG, Level.INFO,
                                "Created final fix Upload for project {0}, user {1} with file name {2}.",
                                new Object[] {projectId, userId, filename});

                        // delete previous final fixes
                        Filter filterProject = UploadFilterBuilder.createProjectIdFilter(project.getId());
                        Filter filterResource = UploadFilterBuilder.createResourceIdFilter(resource.getId());
                        Filter filterType = UploadFilterBuilder.createUploadTypeIdFilter(upload.getUploadType()
                                .getId());

                        Filter filter = new AndFilter(Arrays.asList(new Filter[] {filterProject, filterResource,
                                filterType}));

                        Upload[] uploads = uploadManager.searchUploads(filter);
                        Upload oldUpload = (uploads.length != 0) ? uploads[uploads.length - 1] : null;
                        if (oldUpload != null) {
                            oldUpload.setUploadStatus(getUploadStatusByName("Deleted"));
                            uploadManager.updateUpload(oldUpload, operator);
                        }

                        Helper.logFormat(LOG, Level.INFO,
                                "Marked previous final fixes for deletion for the user {0}.",
                                new Object[] {userId});

                        return upload.getId();

                    }
                    Helper.logFormat(LOG, Level.ERROR,
                            "The 'Final Fix' phase is not OPEN for phaseId {0}, userId {1}", new Object[] {
                                    phase.getId(), userId});
                    throw new InvalidProjectPhaseException("The 'Final Fix' phase is not OPEN", phase.getId());
                } // end of if Final Fix

            } // end of for loop
            Helper.logFormat(LOG, Level.ERROR, "Failed to upload final fix for the projectId {0}",
                    new Object[] {project.getId()});
            throw new InvalidProjectException("Failed to upload final fix for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new UploadServicesException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload final fix for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload final fix for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadServices#uploadFinalFix(long, long, String)");
        }
    }

    /**
     * <p>
     * Adds a new test case upload for an user in a particular project. This submission always overwrite the
     * previous ones.
     * </p>
     * 
     * @param projectId
     *            the project's id
     * @param userId
     *            the user's id
     * @param filename
     *            the file name to use
     * @return the id of the created test cases submission
     * @throws InvalidProjectException
     *             if the project does not exist
     * @throws InvalidUserException
     *             if the user does not exist or has not the reviewer role
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws UploadServicesException
     *             if some other exception occurs in the process (wrap it)
     * @throws IllegalArgumentException
     *             if any id is &lt; 0, if filename is <code>null</code> or trim to empty
     */
    public long uploadTestCases(long projectId, long userId, String filename) throws UploadServicesException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadServices#uploadTestCases(long, long, String)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Helper.checkString(filename, "filename", LOG);

        // check if the project exists
        Project project = getProject(projectId);

        // check that the user exists and has the reviewer role
        Resource resource = getResource(projectId, userId, new String[] {"Accuracy Reviewer", "Failure Reviewer",
                "Stress Reviewer"});

        try {
            // check that the Review phase is open for the project
            com.topcoder.project.phases.Project projectPhases = managersProvider.getPhaseManager().getPhases(
                    projectId);

            Phase[] phases = projectPhases.getAllPhases();
            // iterate over the phases to find if the type is "Review"
            for (Phase phase : phases) {
                if (phase.getPhaseType() != null && ("Review".equals(phase.getPhaseType().getName()))) {
                    // check if final fix is open checking its the status
                    if (PhaseStatus.OPEN.getName().equals(phase.getPhaseStatus().getName())) {
                        // create a new Upload
                        Upload upload = createUpload(projectId, resource.getId(), filename, "Test Case");

                        String operator = "" + userId;
                        // persist the upload
                        managersProvider.getUploadManager().createUpload(upload, operator);
                        Helper.logFormat(LOG, Level.INFO,
                                "Created test cases Upload for project {0}, user {1} with file name {2}.",
                                new Object[] {projectId, userId, filename});

                        // delete the previous submissions
                        Filter filterProject = UploadFilterBuilder.createProjectIdFilter(project.getId());
                        Filter filterResource = UploadFilterBuilder.createResourceIdFilter(resource.getId());
                        Filter filterStatus = UploadFilterBuilder
                                .createUploadStatusIdFilter(getUploadStatusByName("Active").getId());
                        Filter filterType = UploadFilterBuilder.createUploadTypeIdFilter(upload.getUploadType()
                                .getId());

                        Filter filter = new AndFilter(Arrays.asList(new Filter[] {filterProject, filterResource,
                                filterStatus, filterType}));

                        Upload[] uploads = managersProvider.getUploadManager().searchUploads(filter);
                        Upload oldUpload = (uploads.length != 0) ? uploads[0] : null;

                        if (oldUpload != null) {
                            oldUpload.setUploadStatus(getUploadStatusByName("Deleted"));
                            managersProvider.getUploadManager().updateUpload(oldUpload, operator);
                        }

                        Helper
                                .logFormat(LOG, Level.INFO,
                                        "Marked previous test cases for deletion for the user {0}.",
                                        new Object[] {userId});

                        return upload.getId();
                    }

                    Helper.logFormat(LOG, Level.ERROR, "The 'Review' phase is not OPEN for phaseId {0}",
                            new Object[] {phase.getId()});
                    throw new InvalidProjectPhaseException("The 'Review' phase is not OPEN", phase.getId());
                } // end of if Review

            } // end of for loop
            Helper.logFormat(LOG, Level.ERROR, "Failed to upload test case for the projectId {0}",
                    new Object[] {project.getId()});
            throw new InvalidProjectException("Failed to upload test case for the project", project.getId());
        } catch (PhaseManagementException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new UploadServicesException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to upload testcases for user {0} and project {1}.",
                    new Object[] {userId, projectId});
            throw new PersistenceException("Failed to upload testcases for user " + userId + " and project "
                    + projectId + ".", e);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadServices#uploadTestCases(long, long, String)");
        }
    }

    /**
     * <p>
     * Sets the status of a existing submission.
     * </p>
     * 
     * @param submissionId
     *            the submission's id
     * @param submissionStatusId
     *            the submission status id
     * @param operator
     *            the operator which execute the operation
     * @throws InvalidSubmissionException
     *             if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *             if the submission status does not exist
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws IllegalArgumentException
     *             if any id is &lt; 0 or if operator is null or trim to empty
     */
    public void setSubmissionStatus(long submissionId, long submissionStatusId, String operator)
            throws InvalidSubmissionException, InvalidSubmissionStatusException, PersistenceException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadServices#"
                + "setSubmissionStatus(long, long, String)");
        Helper.checkId(submissionId, "submissionId", LOG);
        Helper.checkId(submissionStatusId, "submissionStatusId", LOG);
        Helper.checkString(operator, "operator", LOG);

        try {
            Submission submission = managersProvider.getUploadManager().getSubmission(submissionId);
            if (submission == null) {
                Helper.logFormat(LOG, Level.ERROR, "Failed to get submission with the given Id {0}",
                        new Object[] {submissionId});
                throw new InvalidSubmissionException("Failed to get submission with the given Id", submissionId);
            }

            SubmissionStatus[] submissionStatus = managersProvider.getUploadManager().getAllSubmissionStatuses();
            // iterate over statuses and check which status has the submissionStatusId defined
            for (SubmissionStatus status : submissionStatus) {
                if (status.getId() == submissionStatusId) {
                    submission.setSubmissionStatus(status);
                    managersProvider.getUploadManager().updateSubmission(submission, operator);
                    Helper.logFormat(LOG, Level.INFO, "Updated submission {0} using operator {1}.", new Object[] {
                            submission.getId(), operator});
                    return;
                }
            }

            Helper.logFormat(LOG, Level.ERROR, "Failed to get submission status with the given id {0}",
                    new Object[] {submissionStatusId});
            throw new InvalidSubmissionStatusException("Failed to get submission status with the given id",
                    submissionStatusId);
        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e,
                    "Failed to get the submission from persistence, submissionId - {0}, submissionStatusId - {1}",
                    new Object[] {submissionId, submissionStatusId});
            throw new PersistenceException("Failed to get the submission from the persistence", e);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG,
                    "Exited DefaultUploadServices#setSubmissionStatus(long, long, String)");
        }
    }

    /**
     * Gets the <code>Project</code> from the persistence.
     * 
     * @param projectId
     *            the project id to use
     * @return the <code>Project</code>
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws InvalidProjectException
     *             if the project does not exist
     */
    private Project getProject(long projectId) throws PersistenceException, InvalidProjectException {
        Project project;
        try {
            project = managersProvider.getProjectManager().getProject(projectId);
        } catch (com.topcoder.management.project.PersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to get the project with Id {0}",
                    new Object[] {projectId});
            throw new PersistenceException("Failed to get the project with Id " + projectId + ".", e);
        }
        if (project == null) {
            Helper.logFormat(LOG, Level.ERROR, "Project does not exist - {0}", new Object[] {projectId});
            throw new InvalidProjectException("Project does not exist", projectId);
        }
        return project;
    }

    /**
     * Gets the resource role id for the given role.
     * 
     * @param roles
     *            the roles to use
     * @return the resource role id or zero
     * @throws UploadServicesException
     *             if any while executing.
     */
    private Long[] getSubmitterRoleId(String[] roles) throws UploadServicesException {
        ResourceManager manager = managersProvider.getResourceManager();
        ResourceRole[] resourceRoles;
        try {
            resourceRoles = manager.getAllResourceRoles();
        } catch (ResourcePersistenceException e) {
            throw new UploadServicesException(e.getMessage(), e);
        }
        List<Long> resourceRolesIds = new ArrayList<Long>();
        for (ResourceRole resourceRole : resourceRoles) {
            for (String role : roles) {
                if (role.equals(resourceRole.getName())) {
                    // if matched return the resourceRoleId
                    resourceRolesIds.add(resourceRole.getId());
                }
            }
        }
        return resourceRolesIds.toArray(new Long[resourceRolesIds.size()]);
    }

    /**
     * Gets the <code>Resource</code> associated with the project and user with the given role.
     * 
     * @param projectId
     *            the project id to use
     * @param userId
     *            the user's id
     * @param roles
     *            the roles for filtering
     * @return the <code>Resource</code>
     * @throws UploadServicesException
     */
    private Resource getResource(long projectId, long userId, String[] roles) throws UploadServicesException {
        ResourceManager manager = managersProvider.getResourceManager();
        Long[] submitterRoleIds = getSubmitterRoleId(roles);
        if (submitterRoleIds.length == 0) {
            Helper.logFormat(LOG, Level.ERROR, "There is no submitterRoleId for the given userId {0}",
                    new Object[] {userId});
            throw new InvalidUserException("There is no submitterRoleId for the given user", userId);
        }

        Filter[] filters = new Filter[submitterRoleIds.length];

        for (int i = 0; i < filters.length; i++) {
            filters[i] = ResourceFilterBuilder.createResourceRoleIdFilter(submitterRoleIds[i]);
        }

        Filter submitterRoleIdFilter = new OrFilter(Arrays.asList(filters));

        // create the filter for searching resources
        AndFilter filter = new AndFilter(Arrays.asList(new Filter[] {submitterRoleIdFilter,
                ResourceFilterBuilder.createProjectIdFilter(projectId),
                ResourceFilterBuilder.createExtensionPropertyNameFilter("External Reference ID"),
                ResourceFilterBuilder.createExtensionPropertyValueFilter("" + userId)}));

        Resource[] resources;
        try {
            resources = manager.searchResources(filter);
        } catch (SearchBuilderException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "SearchBuilderException : There is no resource for the given userId {0}", userId);
            throw new InvalidUserException("SearchBuilderException : There is no resource for the given user", userId);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "ResourcePersistenceException : Problem retrieving the resource for the given userId {0}",
                    userId);
            throw new InvalidUserException("Problem retrieving the resource for the given user", userId);
        }

        if (resources.length != 1) {
            Helper.logFormat(LOG, Level.ERROR, "There is no resource for the given userId {0}, resources: {1}",
                    new Object[] {userId, Arrays.asList(resources)});
            throw new InvalidUserException("There is no resource for the given user", userId);
        }

        return resources[0];
    }

    /**
     * Gets the upload status by name.
     * 
     * @param name
     *            the name of the status.
     * @return the UploadStatus for the given name
     * @throws UploadPersistenceException
     *             if any.
     */
    private UploadStatus getUploadStatusByName(String name) throws UploadPersistenceException {
        UploadStatus[] uploadStatus = managersProvider.getUploadManager().getAllUploadStatuses();
        for (UploadStatus status : uploadStatus) {
            if (name.equals(status.getName())) {
                return status;
            }
        }
        return null;
    }

    /**
     * Creates the <code>Upload</code> and set the required attributes.
     * 
     * @param projectId
     *            the project id to use
     * @param resourceId
     *            the user's id
     * @param filename
     *            the filename to set as parameter
     * @param uploadType
     *            the type of upload
     * @return the created <code>Upload</code> instance
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     */
    private Upload createUpload(long projectId, long resourceId, String filename, String uploadType)
            throws PersistenceException {
        // create a new Upload
        Upload upload = new Upload();

        // iterate over all UploadStatuses, get the UploadStatus with name "Active" and set to
        // Upload
        try {
            UploadStatus[] uploadStatus = managersProvider.getUploadManager().getAllUploadStatuses();
            for (UploadStatus status : uploadStatus) {
                if ("Active".equals(status.getName())) {
                    upload.setUploadStatus(status);
                    break;
                }
            }

            // iterate over all UploadTypes, get the UploadType with name uploadType and set to
            // Upload
            UploadType[] uploadTypes = managersProvider.getUploadManager().getAllUploadTypes();
            for (UploadType type : uploadTypes) {
                if (uploadType.equals(type.getName())) {
                    upload.setUploadType(type);
                    break;
                }
            }

            // set the owner as userId
            upload.setOwner(resourceId);

            // set the projectId
            upload.setProject(projectId);

            // file name have to be passed
            upload.setParameter(filename);

            return upload;

        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e,
                    "Failed to create Upload properly with projectId {0} and userId {1}", new Object[] {projectId,
                            resourceId});
            throw new PersistenceException("Failed to create upload properly.", e);
        }

    }

    /**
     * Deletes the previous submissions for the given user.
     * 
     * @param userId
     *            the user's id
     * @param resource
     *            the resource to create the filter
     * @param submissionStatus
     *            the submission status to get the Deleted submission status
     * @throws PersistenceException
     *             if some error occurs in persistence layer
     * @throws InvalidSubmissionException
     *             if the submission does not exist
     * @throws InvalidSubmissionStatusException
     *             if the submission status does not exist
     * @throws UploadServicesException
     *             if some other exception occurs in the process (wrap it)
     */
    private void deletePreviousSubmissions(long userId, Resource resource, SubmissionStatus[] submissionStatus)
            throws UploadServicesException {
        try {
            // Change previous submissions status to "Deleted"
            Filter prevSubFilter = SubmissionFilterBuilder.createResourceIdFilter(resource.getId());

            Submission[] prevSubmissions = managersProvider.getUploadManager().searchSubmissions(prevSubFilter);

            // set the statuses of these submission to "Deleted"
            long delSubStatusId = 0;
            for (SubmissionStatus status : submissionStatus) {
                if ("Deleted".equals(status.getName())) {
                    delSubStatusId = status.getId();
                    break;
                }
            }
            String operator = "" + userId;
            for (Submission prevSubmission : prevSubmissions) {
                // set delSubStatusId to submissions using the method in this class
                setSubmissionStatus(prevSubmission.getId(), delSubStatusId, operator);

                // persist the submissions
                managersProvider.getUploadManager().updateSubmission(prevSubmission, operator);
            }
        } catch (UploadPersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to delete previous submissions for userId {0}",
                    new Object[] {userId});
            throw new PersistenceException("Failed to delete previous submissions.", e);
        } catch (SearchBuilderException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to delete previous submissions for userId {0}",
                    new Object[] {userId});
            throw new UploadServicesException("Failed to delete previous submissions.", e);
        }

    }

    /**
     * Adds the given user as a new submitter to the given project id. If the user is already added returns the the
     * id.
     * 
     * @param projectId
     *            the project to which the user needs to be added
     * @param userId
     *            the user to be added
     * @return the added resource id
     * @throws InvalidProjectException
     *             if the project id is unknown
     * @throws InvalidUserException
     *             if the user id is unknown
     * @throws InvalidProjectPhaseException
     *             if the phase of the project is not Registration.
     * @throws UploadServicesException
     *             if any error occurs from UploadServices
     * @throws IllegalArgumentException
     *             if any id is &lt; 0
     */
    public long addSubmitter(long projectId, long userId) throws InvalidProjectException, UploadServicesException,
            InvalidUserException, InvalidProjectPhaseException {
        Helper.logFormat(LOG, Level.DEBUG, "Entered DefaultUploadServices#addSubmitter(long, long)");
        Helper.checkId(projectId, "projectId", LOG);
        Helper.checkId(userId, "userId", LOG);
        Resource resource = null;
        try {
            resource = getResource(projectId, userId, new String[] {"Submitter"});
            return resource.getId();
        } catch (InvalidUserException e) {
            // ignore
        }        
        if (resource != null) {
            return resource.getId();
        }
        try {
            Project project = managersProvider.getProjectManager().getProject(projectId);
            LOG.log(Level.INFO, "Project successfully retrieved for the project id : " + projectId);
            // Obtain the instance of the Resource Manager
            ResourceManager resourceManager = managersProvider.getResourceManager();
            UserRetrieval userRetrieval = new DBUserRetrieval(DB_CONNECTION_NAMESPACE);

            // Get info about user with the specified userId
            ExternalUser user = userRetrieval.retrieveUser(userId);
            LOG.log(Level.INFO, "User information successfully retrieved for the user id : " + userId);
            // If there is no user with such handle, indicate an error
            if (user == null) {
                Helper.logFormat(LOG, Level.ERROR, "Failed to get the user details for the userId {0}",
                        new Object[] {userId});
                throw new InvalidUserException("The user id  is not found.", userId);
            }
            // Get all types of resource roles
            ResourceRole[] resourceRoles = resourceManager.getAllResourceRoles();

            ResourceRole submitterRole = null;
            for (int i = 0; i < resourceRoles.length && submitterRole == null; i++) {
                ResourceRole role = resourceRoles[i];
                if ("Submitter".equals(role.getName())) {
                    submitterRole = role;
                }
            }

            // Get all types of notifications
            NotificationType[] types = resourceManager.getAllNotificationTypes();
            long timelineNotificationId = Long.MIN_VALUE;

            // get the id for the timelineNotification
            for (int i = 0; i < types.length; ++i) {
                if (types[i].getName().equals("Timeline Notification")) {
                    timelineNotificationId = types[i].getId();
                    break;
                }
            }

            // HashSet used to identify resource of new user
            Set<Long> newSubmitters = new HashSet<Long>();
            resource = new Resource();

            // Set resource properties
            resource.setProject(new Long(project.getId()));
            resource.setResourceRole(submitterRole);
            resource.setProperty("Handle", user.getHandle());
            resource.setProperty("Payment", null);
            resource.setProperty("Payment Status", "No");
            // Set resource properties copied from external user
            resource.setProperty("External Reference ID", Long.toString(userId));

            resource.setProperty("Registration Date", new Date().toString());

            // Save the resource in the persistence level
            resourceManager.updateResource(resource, Long.toString(userId));
            LOG.log(Level.INFO, "Resource successfully persisted into" + " to the DB with the id : "
                    + resource.getId());
            newSubmitters.add(new Long(user.getId()));

            // Populate project_result and component_inquiry for new submitters
            populateProjectResult(project, newSubmitters);

            // Update all the time line notifications if the timelineNotificationId is retrieved properly
            if ("On".equals(project.getProperty("Timeline Notification"))
                    && timelineNotificationId != Long.MIN_VALUE) {
                resourceManager.addNotifications(new long[] {userId}, project.getId(), timelineNotificationId,
                        Long.toString(userId));
            }
            return resource.getId();
        } catch (com.topcoder.management.project.PersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to get the project for the projectId {0}",
                    new Object[] {projectId});
            throw new InvalidProjectException("Exception occurred while getting the project.", e, projectId);
        } catch (ResourcePersistenceException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "ResourcePersistenceException occurred while "
                    + "creating the resource for userId : {0} and projectId : {1}.", new Object[] {userId,
                    projectId});
            throw new UploadServicesException(
                    "ResourcePersistenceException occurred while creating the resource.", e);
        } catch (ConfigException e) {
            Helper.logFormat(LOG, Level.ERROR, e,
                    "Config exception occurred while getting the user information for userId {0}.",
                    new Object[] {userId});
            throw new UploadServicesException("Config exception occurred while getting the user information.", e);
        } catch (RetrievalException e) {
            Helper.logFormat(LOG, Level.ERROR, e, "Failed to get the user information for the userId {0}",
                    new Object[] {userId});
            throw new InvalidUserException("Exception occurred while getting the user information.", e, userId);
        } finally {
            Helper.logFormat(LOG, Level.DEBUG, "Exited DefaultUploadServices#addSubmitter(long, long)");
        }
    }

    /**
     * Populate project_result and component_inquiry for new submitters.
     * 
     * @param project
     *            the project
     * @param newSubmitters
     *            new submitters external ids.
     * @throws UploadServicesException
     *             if error occurs
     */
    private void populateProjectResult(Project project, Collection newSubmitters) throws UploadServicesException {
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement existStmt = null;
        PreparedStatement existCIStmt = null;
        PreparedStatement ratingStmt = null;
        PreparedStatement reliabilityStmt = null;
        PreparedStatement componentInquiryStmt = null;
        long categoryId = project.getProjectCategory().getId();
        // Only design/development/assembly will modify the project result table.
        if (categoryId != 1 && categoryId != 2 && categoryId != 14) {
            // design/development/assembly project need project_result
            return;
        }
        LOG.log(Level.INFO, "Populating the project result table.");
        try {
            DBConnectionFactory dbconn;
            dbconn = new DBConnectionFactoryImpl(DB_CONNECTION_NAMESPACE);
            conn = dbconn.createConnection();
            long projectId = project.getId();
            // retrieve and update component_inquiry_id
            long componentInquiryId = getNextComponentInquiryId(conn, newSubmitters.size());
            long componentId = getProjectLongValue(project, "Component ID");
            long phaseId = 111 + project.getProjectCategory().getId();
            LOG.log(Level.DEBUG, "calculated phaseId for Project: " + projectId + " phaseId: " + phaseId);
            long version = getProjectLongValue(project, "Version ID");

            // add reliability_ind and old_reliability
            ps = conn.prepareStatement("INSERT INTO project_result "
                    + "(project_id, user_id, rating_ind, valid_submission_ind, old_rating, old_reliability) "
                    + "values (?, ?, ?, ?, ?, ?)");

            componentInquiryStmt = conn
                    .prepareStatement("INSERT INTO component_inquiry "
                            + "(component_inquiry_id, component_id, user_id, project_id, phase, tc_user_id, agreed_to_terms, rating, version, create_time) "
                            + "values (?, ?, ?, ?, ?, ?, 1, ?, ?, current)");

            existStmt = conn.prepareStatement("SELECT 1 FROM PROJECT_RESULT WHERE user_id = ? and project_id = ?");

            existCIStmt = conn
                    .prepareStatement("SELECT 1 FROM component_inquiry WHERE user_id = ? and project_id = ?");

            ratingStmt = conn.prepareStatement("SELECT rating from user_rating where user_id = ? and phase_id = "
                    + "(select 111+project_category_id from project where project_id = ?)");

            reliabilityStmt = conn
                    .prepareStatement("SELECT rating from user_reliability where user_id = ? and phase_id = "
                            + "(select 111+project_category_id from project where project_id = ?)");

            for (Iterator iter = newSubmitters.iterator(); iter.hasNext();) {
                String userId = iter.next().toString();

                // Check if projectResult exist
                existStmt.clearParameters();
                existStmt.setString(1, userId);
                existStmt.setLong(2, projectId);
                boolean existPR = existStmt.executeQuery().next();

                // Check if component_inquiry exist
                existCIStmt.clearParameters();
                existCIStmt.setString(1, userId);
                existCIStmt.setLong(2, projectId);
                boolean existCI = existCIStmt.executeQuery().next();

                // Retrieve oldRating
                double oldRating = 0;
                ResultSet rs = null;
                if (!existPR || !existCI) {
                    ratingStmt.clearParameters();
                    ratingStmt.setString(1, userId);
                    ratingStmt.setLong(2, projectId);
                    rs = ratingStmt.executeQuery();

                    if (rs.next()) {
                        oldRating = rs.getLong(1);
                    }
                    close(rs);
                }

                double oldReliability = 0;
                if (!existPR) {
                    // Retrieve Reliability
                    reliabilityStmt.clearParameters();
                    reliabilityStmt.setString(1, userId);
                    reliabilityStmt.setLong(2, projectId);
                    rs = reliabilityStmt.executeQuery();

                    if (rs.next()) {
                        oldReliability = rs.getDouble(1);
                    }
                    close(rs);

                    // add project_result
                    ps.setLong(1, projectId);
                    ps.setString(2, userId);
                    ps.setLong(3, 0);
                    ps.setLong(4, 0);

                    if (oldRating == 0) {
                        ps.setNull(5, Types.DOUBLE);
                    } else {
                        ps.setDouble(5, oldRating);
                    }

                    if (oldReliability == 0) {
                        ps.setNull(6, Types.DOUBLE);
                    } else {
                        ps.setDouble(6, oldReliability);
                    }
                    ps.addBatch();
                }

                // add component_inquiry
                // only design, development and assembly contests needs a component_inquiry entry
                if (!existCI && componentId > 0) {
                    LOG.log(Level.INFO, "adding component_inquiry for projectId: " + projectId + " userId: "
                            + userId);
                    componentInquiryStmt.setLong(1, componentInquiryId++);
                    componentInquiryStmt.setLong(2, componentId);
                    componentInquiryStmt.setString(3, userId);
                    componentInquiryStmt.setLong(4, projectId);
                    // assembly contest must have phaseId set to null
                    if (categoryId == 14) {
                        componentInquiryStmt.setNull(5, Types.INTEGER);
                    } else {
                        componentInquiryStmt.setLong(5, phaseId);
                    }
                    componentInquiryStmt.setString(6, userId);
                    componentInquiryStmt.setDouble(7, oldRating);
                    componentInquiryStmt.setLong(8, version);
                    componentInquiryStmt.addBatch();
                }
            }
            ps.executeBatch();
            componentInquiryStmt.executeBatch();
        } catch (UnknownConnectionException e) {
            throw new UploadServicesException("Failed to create connection", e);
        } catch (ConfigurationException e) {
            throw new UploadServicesException("Failed to config for DBNamespace", e);
        } catch (SQLException e) {
            throw new UploadServicesException("Failed to populate project_result", e);
        } catch (DBConnectionException e) {
            throw new UploadServicesException("Failed to return DBConnection", e);
        } catch (com.topcoder.db.connectionfactory.ConfigurationException e) {
            throw new UploadServicesException("Failed to return DBConnection", e);
        } finally {
            close(componentInquiryStmt);
            close(ps);
            close(existStmt);
            close(existCIStmt);
            close(ratingStmt);
            close(reliabilityStmt);
            close(conn);
        }
    }

    /**
     * Retrieve and update next ComponentInquiryId.
     * 
     * @param conn
     *            the connection
     * @param count
     *            the count of new submitters
     * @return next component_inquiry_id
     * @throws UploadServicesException
     *             if any exception while getting the values
     */
    private long getNextComponentInquiryId(Connection conn, int count) throws UploadServicesException {
        LOG.log(Level.INFO, "Getting the next component inquiry id.");
        String tableName = getPropertyValue("component_inquiry.tablename", "sequence_object");
        String nameField = getPropertyValue("component_inquiry.name", "name");
        String currentValueField = getPropertyValue("component_inquiry.current_value", "current_value");
        String getNextID = "SELECT max(" + currentValueField + ") FROM " + tableName + " WHERE " + nameField
                + " = 'main_sequence'";
        String updateNextID = "UPDATE " + tableName + " SET " + currentValueField + " = ? " + " WHERE "
                + nameField + " = 'main_sequence'" + " AND " + currentValueField + " = ? ";
        PreparedStatement getNextIDStmt = null;
        PreparedStatement updateNextIDStmt = null;
        ResultSet rs = null;

        try {
            getNextIDStmt = conn.prepareStatement(getNextID);
            updateNextIDStmt = conn.prepareStatement(updateNextID);
            while (true) {
                rs = getNextIDStmt.executeQuery();
                rs.next();
                long currentValue = rs.getLong(1);

                // Update the next value
                updateNextIDStmt.clearParameters();
                updateNextIDStmt.setLong(1, currentValue + count);
                updateNextIDStmt.setLong(2, currentValue);
                int ret = updateNextIDStmt.executeUpdate();
                if (ret > 0) {
                    return currentValue;
                }
            }
        } catch (SQLException e) {
            throw new UploadServicesException("Failed to retrieve next component_inquiry_id", e);
        } finally {
            close(rs);
            close(getNextIDStmt);
            close(updateNextIDStmt);
        }
    }

    /**
     * Return project property long value.
     * 
     * @param project
     *            the project object
     * @param name
     *            the property name
     * @return the long value, 0 if it does not exist
     */
    private static long getProjectLongValue(Project project, String name) {
        Object obj = project.getProperty(name);
        if (obj == null) {
            return 0;
        }
        return Long.parseLong(obj.toString());
    }

    /**
     * Close JDBC resource.
     * 
     * @param obj
     *            JDBC resource
     */
    private static void close(Object obj) {
        if (obj instanceof Connection) {
            try {
                ((Connection) obj).close();
            } catch (SQLException e) {
                // Ignore
            }
        } else if (obj instanceof Statement) {
            try {
                ((Statement) obj).close();
            } catch (SQLException e) {
                // Ignore
            }
        } else if (obj instanceof ResultSet) {
            try {
                ((ResultSet) obj).close();
            } catch (SQLException e) {
                // Ignore
            }
        }
    }

    /**
     * Return the property value of online_review namespace.
     * 
     * @param name
     *            the property name
     * @param defaultValue
     *            the default value
     * @return property value
     */
    private String getPropertyValue(String name, String defaultValue) {
        try {
            String value = ConfigManager.getInstance().getString(namespace, name);
            if (value != null && value.trim().length() > 0) {
                return value;
            }
        } catch (UnknownNamespaceException e) {
            // Ignore
        }
        return defaultValue;
    }
}
