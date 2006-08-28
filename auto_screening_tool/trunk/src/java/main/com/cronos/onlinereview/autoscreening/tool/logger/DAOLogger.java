/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.logger;

import java.util.Arrays;
import java.util.Date;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningLogic;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponse;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseDAO;
import com.cronos.onlinereview.autoscreening.tool.ScreeningResponseLogger;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;

/**
 * <p>
 * This implementation of the ScreeningResponseLogger allows the screening logic
 * to be logged into a datastore. The logic for actually accessing the datastore
 * is delegated to a Data Access Object.
 * </p>
 * <p>
 * For this release, an Informix DAO will be provided.
 * </p>
 * <p>
 * Thread Safety: This class is not required to be thread safe.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class DAOLogger implements ScreeningResponseLogger {

    /**
     * Represents the default error message.
     */
    private static final String DEFAULT_ERROR_MESSAGE = "An exception was thrown during the screening.";

    /**
     * <p>
     * This is the screening response DAO to be used to store the results of
     * logging within the datastore.
     * </p>
     */
    private final ScreeningResponseDAO screeningResponseDAO;

    /**
     * <p>
     * This is the identifier of the screener which is currently utilizing this
     * logger. It is expected to be changed only once by the screener prior to
     * this logger being placed into service.
     * </p>
     */
    private String operator;

    /**
     * <p>
     * Creates a DAOLogger for use.
     * </p>
     * @param screeningResponseDAO
     *            The dao to store the logged responses in.
     * @throws IllegalArgumentException
     *             if the screeningResponseDAO is null.
     */
    public DAOLogger(ScreeningResponseDAO screeningResponseDAO) {
        if (screeningResponseDAO == null) {
            throw new IllegalArgumentException("screeningResponseDAO should not be null");
        }

        this.screeningResponseDAO = screeningResponseDAO;
    }

    /**
     * <p>
     * This translates the provided rule results and task into a suitable
     * ScreeningResponse, and then stores that response in the data store
     * provided by the DAO.
     * </p>
     * @param screeningTask
     *            The screening task that was screened.
     * @param screeningLogic
     *            The screening logic which was used to perform the screening.
     * @param ruleResult
     *            The result of performing the screening.
     * @throws IllegalArgumentException
     *             if any of the parameters are null, or ruleResult contains
     *             null elements.
     * @throws ScreeningException
     *             if any error occurs when logging the screening response.
     */
    public void logResponse(ScreeningTask screeningTask, ScreeningLogic screeningLogic,
        RuleResult[] ruleResult) {

        // validate arguments
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (screeningLogic == null) {
            throw new IllegalArgumentException("screeningLogic should not be null.");
        }
        if (ruleResult == null) {
            throw new IllegalArgumentException("ruleResult should not be null.");
        }
        if (Arrays.asList(ruleResult).contains(null)) {
            throw new IllegalArgumentException("ruleResult should not contain null elements.");
        }

        // check if operator is already set
        if (operator == null) {
            throw new ScreeningException(
                "The operator name of this DAOLogger instance has not been specified. "
                    + "Use setOperator() to set the operator name.");
        }

        try {
            // enumerate each rule result
            for (int i = 0; i < ruleResult.length; ++i) {
                ScreeningResponse screeningResponse = new ScreeningResponse();

                // set screening task id
                screeningResponse.setScreeningTaskId(screeningTask.getId());

                // set response id and detail message
                if (ruleResult[i].isSuccessful()) {
                    // if the response code is not given, just ignore this
                    // response
                    if (screeningLogic.getSuccessResponseCode() == Long.MIN_VALUE) {
                        continue;
                    }

                    // success
                    screeningResponse.setResponseCodeId(screeningLogic.getSuccessResponseCode());
                    screeningResponse.setDetailMessage(ruleResult[i].getMessage());
                } else if (ruleResult[i].getError() != null) {
                    // if the response code is not given, just ignore this
                    // response
                    if (screeningLogic.getErrorResponseCode() == Long.MIN_VALUE) {
                        continue;
                    }

                    // error
                    screeningResponse.setResponseCodeId(screeningLogic.getErrorResponseCode());

                    // get the message of the Throwable instance
                    String errorMessage = ruleResult[i].getError().getMessage();
                    screeningResponse.setDetailMessage(errorMessage == null ? DEFAULT_ERROR_MESSAGE
                        : errorMessage);
                } else {
                    // if the response code is not given, just ignore this
                    // response
                    if (screeningLogic.getFailureResponseCode() == Long.MIN_VALUE) {
                        continue;
                    }

                    // failure
                    screeningResponse.setResponseCodeId(screeningLogic.getFailureResponseCode());
                    screeningResponse.setDetailMessage(ruleResult[i].getMessage());
                }

                Date now = new Date();
                screeningResponse.setCreateDate(now);
                screeningResponse.setCreateUser(operator);
                screeningResponse.setModificationDate(now);
                screeningResponse.setModificationUser(operator);

                // persist the screening response
                screeningResponseDAO.createScreeningResponse(screeningResponse);
            }
        } catch (Throwable e) {
            // unchecked exception may throw when calling the setXXX methods,
            // so 'catch Throwable' is used here.
            throw new ScreeningException("Unable to create the screening responses to database.", e);
        }
    }

    /**
     * <p>
     * This method is called to provide the logger with information as to the
     * screener who is performing the screening and using this logger.
     * </p>
     * @param operator
     *            The id of the screener that is performing the logging.
     * @throws IllegalArgumentException
     *             if the operator is null or an empty String.
     */
    public void setOperator(String operator) {
        if (operator == null) {
            throw new IllegalArgumentException("operator should not be null.");
        }
        if (operator.trim().length() == 0) {
            throw new IllegalArgumentException("operator should not be empty (trimmed).");
        }

        this.operator = operator;
    }

    /**
     * <p>
     * Retrieves the identifier of the screener which is currently utilizing
     * this logger.
     * </p>
     * @return the identifier of the screener which is currently utilizing this
     *         logger.
     */
    public String getOperator() {
        return operator;
    }
}
