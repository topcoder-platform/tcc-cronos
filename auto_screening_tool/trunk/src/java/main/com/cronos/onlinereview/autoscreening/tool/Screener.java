/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool;

import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.topcoder.servlet.request.FileUpload;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.collection.typesafeenum.Enum;
import com.topcoder.util.config.Property;
import com.topcoder.util.objectfactory.ObjectFactory;
import com.topcoder.util.objectfactory.impl.ConfigManagerSpecificationFactory;

/**
 * <p>
 * This is the main workhorse of the component to automatically screen
 * submissions.
 * </p>
 * <p>
 * It is responsible for retrieving the screening tasks from the data source,
 * and determining the screening task to utilize. It will then perform screening
 * on the chosen task; the rules of the screening is based on the project
 * related to the Screening task and the Project category.
 * </p>
 * <p>
 * The screener is also responsible for logging the screening results and for
 * updating the screening status from PENDING to SCREENING, etc.
 * </p>
 * <p>
 * Thread Safety: A single thread is expected to access this, so thread safety
 * is not required.
 * </p>
 * @author ShindouHikaru, urtks
 * @version 1.0
 */
public class Screener {

    /**
     * <p>
     * This is the context key under which the submission to screen will be
     * stored. It is expected to be used by any of the ScreeningRule
     * implementations that need to directly access the submission in its
     * uploaded file state.
     * </p>
     */
    public static final String SUBMISSION_KEY = "screening_submission";

    /**
     * Represents 'connection factory namespace' property in the configuration.
     */
    private static final String OBJECT_FACTORY_NAMESPACE_CONFIG_PROPERTY = "objectFactoryNamespace";

    /**
     * Represents 'class' sub property in the configuration.
     */
    private static final String CLASS_CONFIG_PROPERTY = "class";

    /**
     * Represents 'identifier' sub property in the configuration.
     */
    private static final String IDENTIFIER_CONFIG_PROPERTY = "identifier";

    /**
     * Represents 'screening task DAO' base property in the configuration.
     */
    private static final String SCREENING_TASK_DAO_CONFIG_PROPERTY = "screeningTaskDAO";

    /**
     * Represents 'screening response logger' base property in the
     * configuration.
     */
    private static final String RESPONSE_LOGGER_CONFIG_PROPERTY = "screeningResponseLogger";

    /**
     * Represents 'screening task chooser' base property in the configuration.
     */
    private static final String TASK_CHOOSER_CONFIG_PROPERTY = "taskChooser";

    /**
     * Represents 'file upload' base property in the configuration.
     */
    private static final String FILE_UPLOAD_CONFIG_PROPERTY = "fileUpload";

    /**
     * Represents 'project rules' base property in the configuration.
     */
    private static final String PROJECT_RULES_CONFIG_PROPERTY = "projectRules";

    /**
     * Represents 'project category rules' base property in the configuration.
     */
    private static final String CATEGORY_RULES_CONFIG_PROPERTY = "categoryRules";

    /**
     * Represents 'id' sub property in the configuration.
     */
    private static final String ID_CONFIG_PROPERTY = "id";

    /**
     * Represents 'screening logic list' sub property in the configuration.
     */
    private static final String SCREENING_LOGIC_LIST_CONFIG_PROPERTY = "screeningLogicList";

    /**
     * Represents 'screening logic' sub property in the configuration.
     */
    private static final String SCREENING_LOGIC_CONFIG_PROPERTY = "screeningLogic";

    /**
     * Represents 'rule class' sub property in the configuration.
     */
    private static final String RULE_CLASS_CONFIG_PROPERTY = "ruleClass";

    /**
     * Represents 'rule identifier' sub property in the configuration.
     */
    private static final String RULE_IDENTIFIER_CONFIG_PROPERTY = "ruleIdentifier";

    /**
     * Represents 'succeed severity' sub property in the configuration.
     */
    private static final String SUCCEED_SEVERITY_CONFIG_PROPERTY = "succeedSeverity";

    /**
     * Represents 'fail severity' sub property in the configuration.
     */
    private static final String FAIL_SEVERITY_CONFIG_PROPERTY = "failSeverity";

    /**
     * Represents 'error severity' sub property in the configuration.
     */
    private static final String ERROR_SEVERITY_CONFIG_PROPERTY = "errorSeverity";

    /**
     * Represents 'succeed code' sub property in the configuration.
     */
    private static final String SUCCEED_CODE_CONFIG_PROPERTY = "succeedCode";

    /**
     * Represents 'fail code' sub property in the configuration.
     */
    private static final String FAIL_CODE_CONFIG_PROPERTY = "failCode";

    /**
     * Represents 'error code' sub property in the configuration.
     */
    private static final String ERROR_CODE_CONFIG_PROPERTY = "errorCode";

    /**
     * Represents the prefix of the operator name.
     */
    private static final String OPERATOR_PREFIX = "Auto Screening Tool:";

    /**
     * <p>
     * This is the DAO from which the available tasks are retrieved from. It may
     * not be null.
     * </p>
     */
    private final ScreeningTaskDAO screeningTaskDAO;

    /**
     * <p>
     * This is the response logger that is used to log the screening results
     * after a ScreeningLogic has been executed. It may not be null.
     * </p>
     */
    private final ScreeningResponseLogger responseLogger;

    /**
     * <p>
     * This is a strategy that is used to determine which task this screener
     * will take on next. It may not be null.
     * </p>
     */
    private final ScreeningTaskChooser taskChooser;

    /**
     * <p>
     * This is the instance of FileUpload that is used to retrieve the file from
     * the File System Server. This is done by retrieving the identifier of the
     * uploading submission from the ScreeningTask and providing it to the
     * FileUpload.
     * </p>
     */
    private final FileUpload fileUpload;

    /**
     * <p>
     * This is a mapping of the Project ids to the list of ScreeningLogic that
     * should be performed on the submission. No null keys or values are
     * allowed. The keys are long ids, and the value is an array of
     * ScreeningLogic[].
     * </p>
     */
    private final Map projectLogic = new HashMap();

    /**
     * <p>
     * This is a mapping of the Project category ids to the list of
     * ScreeningLogic that should be performed on the submission. No null keys
     * or values are allowed. The keys are long ids, and the value is an array
     * of ScreeningLogic[].
     * </p>
     */
    private final Map categoryLogic = new HashMap();

    /**
     * <p>
     * This is the id of the screener that is used to uniquely identify the
     * screener among different screener instances.
     * </p>
     */
    private final long screenerId;

    /**
     * <p>
     * Initializes the Screener according to the configuration parameters that
     * are provided.
     * </p>
     * @param screenerId
     *            The id of this screener.
     * @param namespace
     *            The namespace to utilize.
     * @throws IllegalArgumentException
     *             if screenerId is <= 0, or namespace is null or an empty
     *             String.
     * @throws ConfigurationException
     *             if a required property is not present, an exception occurs
     *             during initialization, or the configuration property contains
     *             an invalid/unexpected value.
     */
    public Screener(long screenerId, String namespace) throws ConfigurationException {
        if (screenerId <= 0) {
            throw new IllegalArgumentException("screenerId should be > 0.");
        }
        if (namespace == null) {
            throw new IllegalArgumentException("namespace should not be null.");
        }
        if (namespace.trim().length() == 0) {
            throw new IllegalArgumentException("namespace should not be empty (trimmed).");
        }

        // assign the screener id
        this.screenerId = screenerId;

        // create objectFactory
        ObjectFactory objectFactory = createObjectFactory(namespace);

        // create screeningTaskDAO
        screeningTaskDAO = (ScreeningTaskDAO) createWidget(namespace,
            SCREENING_TASK_DAO_CONFIG_PROPERTY, objectFactory, ScreeningTaskDAO.class);
        // create responseLogger
        responseLogger = (ScreeningResponseLogger) createWidget(namespace,
            RESPONSE_LOGGER_CONFIG_PROPERTY, objectFactory, ScreeningResponseLogger.class);
        // create taskChooser
        taskChooser = (ScreeningTaskChooser) createWidget(namespace, TASK_CHOOSER_CONFIG_PROPERTY,
            objectFactory, ScreeningTaskChooser.class);
        // create fileUpload
        fileUpload = (FileUpload) createWidget(namespace, FILE_UPLOAD_CONFIG_PROPERTY,
            objectFactory, FileUpload.class);

        // fill the projectLogic map
        fillScreeningLogics(namespace, PROJECT_RULES_CONFIG_PROPERTY, objectFactory, projectLogic);
        // fill the categoryLogic map
        fillScreeningLogics(namespace, CATEGORY_RULES_CONFIG_PROPERTY, objectFactory, categoryLogic);
    }

    /**
     * Create an instance of ObjectFactory from the given configuration
     * namespace.
     * @param namespace
     *            the configuration namespace
     * @return an instance of ObjectFactory created
     * @throws ConfigurationException
     *             if any error occurs during the creation of ObjectFactory
     *             instance from configuration
     */
    private ObjectFactory createObjectFactory(String namespace) throws ConfigurationException {

        String objectFactoryNamespace = ConfigUtils.getStringValue(namespace,
            OBJECT_FACTORY_NAMESPACE_CONFIG_PROPERTY, true);

        // just catch all exceptions, this makes the component more solid and
        // elegant, especially when the objectFactory throws unchecked
        // exceptions (like IllegalArgumentExcepetion, NullReferenceException,
        // etc.)
        try {
            return new ObjectFactory(new ConfigManagerSpecificationFactory(objectFactoryNamespace));
        } catch (Throwable e) {
            throw new ConfigurationException(
                "Unable to create an ObjectFactory instance using the namespace ["
                    + objectFactoryNamespace + "].", e);
        }
    }

    /**
     * Create an instance of widgetType from the given property and
     * configuration namespace. A widget is screeningTaskDAO,
     * screeningResponseLogger, taskChooser, etc.
     * @param namespace
     *            the configuration namespace
     * @param widgetName
     *            the name of the property that contains the widget's name and
     *            identifier
     * @param objectFactory
     *            the object factory used to create the instance
     * @param widgetType
     *            the type of the instance
     * @return an instance of widgetType created
     * @throws ConfigurationException
     *             if any error occurs during the creation of the widgetType
     *             instance from configuration
     */
    private Object createWidget(String namespace, String widgetName, ObjectFactory objectFactory,
        Class widgetType) throws ConfigurationException {

        String className = ConfigUtils.getStringValue(namespace, widgetName + "."
            + CLASS_CONFIG_PROPERTY, true);
        String identifier = ConfigUtils.getStringValue(namespace, widgetName + "."
            + IDENTIFIER_CONFIG_PROPERTY, false);

        Object widget;

        // just catch all exceptions, this makes the component more solid and
        // elegant, especially when the objectFactory throws unchecked
        // exceptions (like IllegalArgumentExcepetion, NullReferenceException,
        // etc.)
        try {
            widget = identifier == null ? objectFactory.createObject(className) : objectFactory
                .createObject(className, identifier);
        } catch (Throwable e) {
            throw new ConfigurationException("Unable to create an instance using name ["
                + className + "] and identifier [" + identifier + "].", e);
        }

        // check if the created instance is of correct type.
        if (!widgetType.isInstance(widget)) {
            throw new ConfigurationException("The instance created using name [" + className
                + "] is not of type [" + widgetType.getName() + "]");
        }

        return widget;
    }

    /**
     * Fills the projectLogic map or categoryLogic map using the ScreeningLogics
     * created from the configuration.
     * @param namespace
     *            the configuration namespace
     * @param basePropertyName
     *            the base property name that contains configuration for the
     *            ScreeningLogic map
     * @param objectFactory
     *            the object factory used to create the objects
     * @param logicsMap
     *            the ScreeningLogic map
     * @throws ConfigurationException
     *             if any error occurs when filling the ScreeningLogic map from
     *             the configuration
     */
    private void fillScreeningLogics(String namespace, String basePropertyName,
        ObjectFactory objectFactory, Map logicsMap) throws ConfigurationException {

        // get base property object
        Property baseProperty = ConfigUtils.getPropertyObject(namespace, basePropertyName, false);
        // do nothing if the property does not exist
        if (baseProperty == null) {
            return;
        }

        // enumerate all the property names under the base property
        Enumeration iter = baseProperty.propertyNames();
        while (iter.hasMoreElements()) {

            // get the 'rule' property object
            Property ruleProperty = baseProperty.getProperty((String) iter.nextElement());

            // get 'rule' id
            Long ruleId = ConfigUtils.getLongValue(ruleProperty, ID_CONFIG_PROPERTY, true);

            // get all the ScreeningLogic names
            String[] logicNames = ConfigUtils.getStringValues(ruleProperty,
                SCREENING_LOGIC_LIST_CONFIG_PROPERTY);
            // create the ScreeningLogic array to store the created instances
            ScreeningLogic[] screeningLogics = new ScreeningLogic[logicNames.length];

            // get the screening logic property object
            Property logicProperty = ConfigUtils.getPropertyObject(ruleProperty,
                SCREENING_LOGIC_CONFIG_PROPERTY, true);
            // enumerate each screening logic name
            for (int i = 0; i < logicNames.length; ++i) {

                screeningLogics[i] = createScreeningLogic(ConfigUtils.getPropertyObject(
                    logicProperty, logicNames[i], true), objectFactory);
            }

            // try to put the rule into the map
            if (logicsMap.containsKey(ruleId)) {
                throw new ConfigurationException(
                    "Duplicate screening rule ids are found in property [" + basePropertyName
                        + "] under namespace [" + namespace + "].");
            } else {
                logicsMap.put(ruleId, screeningLogics);
            }
        }
    }

    /**
     * Create an instance of ScreeningLogic from the given configuration
     * property object.
     * @param property
     *            the configuration property object
     * @param objectFactory
     *            the object factory used to create the instance
     * @return an instance of ScreeningLogic created
     * @throws ConfigurationException
     *             if any error occurs during the creation of ScreeningLogic
     *             instance from configuration
     */
    private ScreeningLogic createScreeningLogic(Property property, ObjectFactory objectFactory)
        throws ConfigurationException {

        // create screening rule
        ScreeningRule screeningRule = createScreeningRule(property, objectFactory);

        // get response levels and codes
        ResponseLevel successSeverity = getResponseLevel(property, SUCCEED_SEVERITY_CONFIG_PROPERTY);
        ResponseLevel failureSeverity = getResponseLevel(property, FAIL_SEVERITY_CONFIG_PROPERTY);
        ResponseLevel errorSeverity = getResponseLevel(property, ERROR_SEVERITY_CONFIG_PROPERTY);
        Long successCode = ConfigUtils.getLongValue(property, SUCCEED_CODE_CONFIG_PROPERTY, false);
        Long failureCode = ConfigUtils.getLongValue(property, FAIL_CODE_CONFIG_PROPERTY, false);
        Long errorCode = ConfigUtils.getLongValue(property, ERROR_CODE_CONFIG_PROPERTY, false);

        ScreeningLogic screeningLogic = new ScreeningLogic(screeningRule, successSeverity,
            failureSeverity, errorSeverity, (successCode != null ? successCode.longValue()
                : Long.MIN_VALUE),
            (failureCode != null ? failureCode.longValue() : Long.MIN_VALUE), (errorCode != null
                ? errorCode.longValue() : Long.MIN_VALUE));

        return screeningLogic;
    }

    /**
     * Get the ResponseLevel enum from the specified sub property under the
     * given configuration property object.
     * @param property
     *            the configuration property object
     * @param subPropertyName
     *            the name of the sub property
     * @return one of the ResponseLevel enum
     * @throws ConfigurationException
     *             if any error occurs when getting the ResponseLevel from the
     *             configuration
     */
    private ResponseLevel getResponseLevel(Property property, String subPropertyName)
        throws ConfigurationException {

        // get the string that represents the ResponseLevel
        String levelString = ConfigUtils.getStringValue(property, subPropertyName, true);

        // get the ResponseLevel
        ResponseLevel level = (ResponseLevel) Enum.getEnumByStringValue(levelString.toLowerCase(),
            ResponseLevel.class);

        // check whether the string is valid
        if (level == null) {
            throw new ConfigurationException("The response level string [" + levelString
                + "] of sub property [" + subPropertyName + "] under property ["
                + property.getName() + "] should be one of ["
                + Enum.getEnumList(ResponseLevel.class).toString() + "]");
        }

        return level;
    }

    /**
     * Create an instance of ScreeningRule from the given configuration property
     * object.
     * @param property
     *            the configuration property object
     * @param objectFactory
     *            the object factory used to create the instance
     * @return an instance of ScreeningRule created
     * @throws ConfigurationException
     *             if any error occurs during the creation of ScreeningRule
     *             instance from configuration
     */
    private ScreeningRule createScreeningRule(Property property, ObjectFactory objectFactory)
        throws ConfigurationException {

        // get the class name and identifier of the screening rule
        String className = ConfigUtils.getStringValue(property, RULE_CLASS_CONFIG_PROPERTY, true);
        String identifier = ConfigUtils.getStringValue(property, RULE_IDENTIFIER_CONFIG_PROPERTY,
            false);

        // just catch all exceptions, this makes the component more solid and
        // elegant, especially when the objectFactory throws unchecked
        // exceptions (like IllegalArgumentExcepetion, NullReferenceException,
        // etc.)
        try {
            return (ScreeningRule) objectFactory.createObject(className, identifier);
        } catch (Throwable e) {
            throw new ConfigurationException(
                "Unable to create a ScreeningRule instance using class name [" + className
                    + "] and identifier [" + identifier + "].", e);
        }
    }

    /**
     * <p>
     * This method should be called to signal to the Screener that it is being
     * placed into service. This will clear any entries in the database.
     * </p>
     * @throws ScreeningException
     *             if a problem occurs during screening.
     */
    public void initialize() {

        try {
            // get the screening tasks with 'screening' status and the same
            // screener id as this screener
            ScreeningTask[] screeningTasks = screeningTaskDAO.loadScreeningTasks(new Long(
                screenerId), ScreeningStatus.SCREENING);

            // resume these screening tasks back to 'pending' status
            for (int i = 0; i < screeningTasks.length; ++i) {

                // roll back the screening information about screeningTasks[i]
                screeningTasks[i].setScreenerId(Long.MIN_VALUE);
                screeningTasks[i].setStartTimestamp(null);
                screeningTasks[i].setModificationUser("Auto Screening Tool:" + screenerId);
                screeningTasks[i].setModificationDate(new Date());
                screeningTasks[i].setScreeningStatus(ScreeningStatus.PENDING);

                screeningTaskDAO.updateScreeningTask(screeningTasks[i]);
            }
        } catch (DAOException e) {
            throw new ScreeningException("Unable to initialize this screener.", e);
        }
    }

    /**
     * <p>
     * This method will make the screener perform a single iteration of
     * screening logic.
     * </p>
     * <p>
     * Generally speaking this method does the following things:
     * <ol>
     * <li>Checking the datastore for any available screening tasks.</li>
     * <li>Choosing an appropriate screening task and informing the datastore
     * that it is going to screen the given task.</li>
     * <li>Retrieving the submission from the File System Server for actual
     * screening. (Using File Upload component)</li>
     * <li>Determining the correct set of screening rules (in the form of
     * Screening Logic) to use depending on the project type/category of the
     * submission.</li>
     * <li>Logging the screening result.</li>
     * <li>Updating the datastore on the status of the screening.</li>
     * </ol>
     * </p>
     * @throws ScreeningException
     *             if a problem occurs during screening.
     */
    public void screen() {

        try {
            // select a pending screening task
            ScreeningTask selectedTask = selectPendingScreeningTask();
            // check if a screening task has been selected successfully
            if (selectedTask == null) {
                // simply return if no pending tasks are available now
                return;
            }

            // get the screening logics that should be performed for this
            // screening task
            ScreeningLogic[] screeningLogics = getScreeningLogics(selectedTask);

            // get the initial screening context
            Map context = createInitialScreeningContext(selectedTask);

            // initializes the screening status
            ScreeningStatus screeningStatus = ScreeningStatus.PASSED;

            // set the operator of responseLogger
            responseLogger.setOperator(OPERATOR_PREFIX + screenerId);

            // enumerate each screening logic, do the screening
            for (int i = 0; i < screeningLogics.length; ++i) {
                // do the screening using screeningLogics[i]
                RuleResult[] ruleResults = screeningLogics[i].screen(selectedTask, context);

                // log the response
                responseLogger.logResponse(selectedTask, screeningLogics[i], ruleResults);

                // update the screening status
                screeningStatus = updateScreeningStatus(screeningLogics[i], ruleResults,
                    screeningStatus);
            }
            // do clean up for the screening logics all together reversely
            for (int i = screeningLogics.length - 1; i >= 0; --i) {
                screeningLogics[i].cleanup(selectedTask, context);
            }

            // update the screening status to selectedTask
            selectedTask.setScreeningStatus(screeningStatus);
            selectedTask.setModificationDate(new Date());
            selectedTask.setModificationUser(OPERATOR_PREFIX + screenerId);

            // persist the updated screening task to the database
            screeningTaskDAO.updateScreeningTask(selectedTask);
        } catch (DAOException e) {
            throw new ScreeningException("Unable to perform screening operation.", e);
        }
    }

    /**
     * Selects one of the pending screening tasks to screen.
     * @return A ScreeningTask instance that is selected to screen, or null if
     *         no one can be selected successfully
     * @throws ScreeningException
     *             if any error occurs when selecting the pending screening
     *             tasks
     */
    private ScreeningTask selectPendingScreeningTask() {

        try {
            // get the pending screening tasks. it's an array list
            List pendingTasks = new LinkedList(Arrays.asList(screeningTaskDAO.loadScreeningTasks(
                null, ScreeningStatus.PENDING)));

            ScreeningTask selectedTask = null;

            // if there are pending screening tasks and no one is selected
            // successfully, try to selected one of the tasks
            while (pendingTasks.size() != 0 && selectedTask == null) {

                // choose one of the tasks using taskChooser
                selectedTask = taskChooser.chooseScreeningTask((ScreeningTask[]) pendingTasks
                    .toArray(new ScreeningTask[] {}));
                // remove it from pendingTasks array list
                pendingTasks.remove(selectedTask);

                // get current timestamp
                Date now = new Date();
                // update the selected task
                selectedTask.setScreenerId(screenerId);
                selectedTask.setStartTimestamp(now);
                selectedTask.setModificationUser(OPERATOR_PREFIX + screenerId);
                selectedTask.setModificationDate(now);
                selectedTask.setScreeningStatus(ScreeningStatus.SCREENING);

                // try to update the selected screening task
                try {
                    screeningTaskDAO.updateScreeningTask(selectedTask);
                } catch (UpdateFailedException e) {
                    // undo the selecting, since probable this task has been
                    // selected by others
                    selectedTask = null;
                }
            }

            return selectedTask;
        } catch (DAOException e) {
            throw new ScreeningException("Unable to select pending screening tasks.", e);
        }
    }

    /**
     * Gets the screening logics associated with the given screening task.
     * @param screeningTask
     *            the screening task
     * @return an array of ScreeningLogic that represents the screening logics
     *         that should be performed for the given screening task.
     * @throws ScreeningException
     *             if unable to find the screening logics for the given
     *             screening task
     */
    private ScreeningLogic[] getScreeningLogics(ScreeningTask screeningTask) {

        Long projectId = new Long(screeningTask.getScreeningData().getProjectId());
        Long projectCategoryId = new Long(screeningTask.getScreeningData().getProjectCategoryId());

        // get screening logics for the project id of this screening task
        ScreeningLogic[] screeningLogics = (ScreeningLogic[]) projectLogic.get(projectId);
        if (screeningLogics == null) {
            // if not found, get screening logics for the project category id of
            // this screening task
            screeningLogics = (ScreeningLogic[]) categoryLogic.get(projectCategoryId);
        }
        if (screeningLogics == null) {
            // if still not found, throw an exception
            throw new ScreeningException(
                "Unable to find the screening logics for the selected screening task with taskId ["
                    + screeningTask.getId() + "] with projectId [" + projectId
                    + "] and projectCategoryId [" + projectCategoryId + "].");
        }

        return screeningLogics;
    }

    /**
     * Creates an initial screening context for the given screening task.
     * @param screeningTask
     *            the screening task
     * @return an instance of Map that represents the screening context
     * @throws ScreeningException
     *             if unable to get the uploaded file for the screening task
     */
    private Map createInitialScreeningContext(ScreeningTask screeningTask) {

        // get uploadedFile
        String fileIdentifier = screeningTask.getScreeningData().getFileIdentifier();

        UploadedFile uploadedFile;
        try {
            uploadedFile = fileUpload.getUploadedFile(fileIdentifier);
        } catch (Throwable e) {
            throw new ScreeningException("Unable to get the uploaded file [" + fileIdentifier
                + "].");
        }

        // create the screening context
        Map context = new HashMap();
        context.put(SUBMISSION_KEY, uploadedFile);

        return context;
    }

    /**
     * Update the screening status according to the screening logic and rule
     * results.
     * @param screeningLogic
     *            the screening logic
     * @param ruleResults
     *            the rule results
     * @param screeningStatus
     *            the screening status to update
     * @return the updated screening status
     */
    private ScreeningStatus updateScreeningStatus(ScreeningLogic screeningLogic,
        RuleResult[] ruleResults, ScreeningStatus screeningStatus) {

        // enumerate each rule result if screening status has not been set
        // ScreeningStatus.FAILED
        for (int j = 0; j < ruleResults.length && screeningStatus != ScreeningStatus.FAILED; ++j) {

            // get the responseLevel for ruleResults[j]
            ResponseLevel responseLevel;
            if (ruleResults[j].isSuccessful()) {
                // success
                responseLevel = screeningLogic.getSuccessLevel();
            } else if (ruleResults[j].getError() != null) {
                // error
                responseLevel = screeningLogic.getErrorLevel();
            } else {
                // failure
                responseLevel = screeningLogic.getFailureLevel();
            }

            // update screening status according to responseLevel
            if (responseLevel == ResponseLevel.WARN) {
                if (screeningStatus != ScreeningStatus.PASSED_WITH_WARNING) {
                    screeningStatus = ScreeningStatus.PASSED_WITH_WARNING;
                }
            } else if (responseLevel == ResponseLevel.FAIL) {
                screeningStatus = ScreeningStatus.FAILED;
            }
        }

        // return the updated screening status
        return screeningStatus;
    }
}
