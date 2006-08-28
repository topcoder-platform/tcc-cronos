/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.topcoder.file.directoryvalidator.ValidationLogger;

/**
 * <p>
 * This is a ValidationLogger that is used by the DirectoryStructureRule.
 * </p>
 * <p>
 * It will generate a RuleResult for each rule validation result that it
 * encounters. It will create an invalid RuleResult for each call to the
 * validateFailed method.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
class ValidationLoggerImpl implements ValidationLogger {

    /**
     * <p>
     * This is a list of ruleResults that have been generated upon each call to
     * validateFailed. It is initially empty, but may be populated by non-null
     * RuleResult objects.
     * </p>
     */
    private final List ruleResults = new ArrayList();

    /**
     * Create ValidationLoggerImpl instance.
     */
    public ValidationLoggerImpl() {
    }

    /**
     * <p>
     * This method will be called by the DirectoryManager if the file
     * successfully passes the validator's validation.
     * </p>
     * @param file
     *            the file under validation currently.
     * @param msg
     *            the message associated to the Validator.
     */
    public void validateSucceeded(File file, String msg) {
        // do nothing, since we only concern about the failure
    }

    /**
     * <p>
     * This method will be called by the DirectoryManager if the file fails to
     * pass the validator's validation.
     * </p>
     * @param file
     *            the file under validation currently.
     * @param msg
     *            the message associated with the validator.
     * @throws IllegalArgumentException
     *             if any parameter is null or msg is an empty trimmed String.
     */
    public void validateFailed(File file, String msg) {
        if (file == null) {
            throw new IllegalArgumentException("file should not be null.");
        }
        if (msg == null) {
            throw new IllegalArgumentException("msg should not be null.");
        }
        if (msg.trim().length() == 0) {
            throw new IllegalArgumentException("msg should not be empty (trimmed).");
        }

        // create the ruleResult and add it to the list
        ruleResults.add(new RuleResult(false, "File or directory [" + file.getAbsolutePath()
            + "] failed to pass the validation: " + msg));
    }

    /**
     * <p>
     * Calculate the total validations.
     * </p>
     * <p>
     * This method is here to conform to the ValidationLogger interface, but
     * isn't necessary for the purpose of this component.
     * </p>
     */
    public void logFinalReport() {
        // do nothing.
    }

    /**
     * <p>
     * Reset the state of the ValidationLogging, this method will be called
     * before the logging begins and after the logging finishes in the
     * DirectoryManager.create(File xmlFile) method to ensure the overall
     * progress collected in the logger is correct.
     * </p>
     * <p>
     * This method is here to conform to the ValidationLogger interface, but
     * isn't necessary for the purpose of this component.
     * </p>
     */
    public void reset() {
        // since we need to retrieve the rule results, nothing is done here.
    }

    /**
     * <p>
     * Retrieves any RuleResults containing the validation messages for any
     * directory structure violations that were encountered. If the validation
     * was successful, then an empty Array is returned.
     * </p>
     * @return any RuleResults containing the validation messages for any
     *         directory structure violations that were encountered.
     */
    public RuleResult[] getRuleResults() {
        return (RuleResult[]) ruleResults.toArray(new RuleResult[0]);
    }
}
