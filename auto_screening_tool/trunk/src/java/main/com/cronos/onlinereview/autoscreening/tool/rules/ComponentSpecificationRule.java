/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.file.type.FileType;
import com.topcoder.file.type.MagicNumbers;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This rule checks if a component specification exists.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ComponentSpecificationRule implements ScreeningRule {

    /**
     * An implementation of FileFilter that checks if a file is a component
     * specification.
     * @author ShindouHikaru, TCSDEVELOPER
     * @version 1.0
     */
    private static class ComponentSpecificationFileFilter implements FileFilter {

        /**
         * Checks if the given file is a component specification.
         * @param file
         *            the file to check
         * @return true, if the file is accepted as a component specification;
         *         otherwise, false.
         */
        public boolean accept(File file) {
            if (file == null || !file.isFile()) {
                return false;
            }

            // first check file name
            String fileName = file.getName().toLowerCase();
            if (fileName.indexOf("component") == -1 || fileName.indexOf("specification") == -1) {
                return false;
            }
            if (!fileName.endsWith(".rtf") && !fileName.endsWith(".doc")) {
                return false;
            }

            // then determine file type
            FileType type = null;
            try {
                type = new MagicNumbers().determineFileType(file);
            } catch (Throwable e) {
                return false;
            }
            return type != null && type.getMime().equals("application/rtf");
        }
    }

    /**
     * Indicates the path to start looking for component specification files in.
     */
    private static final String START_DIR = "docs";

    /**
     * Represents the Log instance for this class to log additional information
     * to.
     */
    private static final Log LOG;

    /**
     * Static constructor.
     */
    static {
        // try to create a Log instance
        try {
            LOG = LogFactory.getLogWithExceptions(ComponentSpecificationRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + ComponentSpecificationRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * Default constructor.
     * </p>
     */
    public ComponentSpecificationRule() {
        // empty
    }

    /**
     * <p>
     * Performs screening on the specified screening task. This implementation
     * will expect the context to contain the File object pointing to the root
     * directory of an unzipped submission.
     * </p>
     * <p>
     * Screening will be successful if a component specification exists.
     * Generally speaking this method does the following things:
     * <ol>
     * <li>Retrieve the unzipped directory from the context using
     * ArchiveFileRule.SUBMISSION_DIRECTORY_KEY.</li>
     * <li>Search for a 'docs' directory (if none was found or if not a
     * directory, then the RuleResult fails with suitable message).</li>
     * <li>Iterate over all files within the docs directory, looking for a file
     * that contains the words "component" and "specification" and ends in "rtf"
     * or "doc".</li>
     * <li>If any were found, use MagicNumbers component to determine that file
     * type is "application/rtf".</li>
     * <li>Log this method according to the CS section 1.3.3.</li>
     * </ol>
     * </p>
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public RuleResult[] screen(ScreeningTask screeningTask, Map context) {
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("context should not be null.");
        }

        RuleUtils.beginScreeningLogging(LOG, screeningTask);
        RuleResult[] ruleResults = screenWithoutLog(screeningTask, context);
        RuleUtils.endScreeningLogging(LOG, ruleResults);

        return ruleResults;
    }

    /**
     * Performs screening on the specified screening task without log
     * information.
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
     * @return The results of screening.
     */
    private RuleResult[] screenWithoutLog(ScreeningTask screeningTask, Map context) {

        try {
            // get the unzipped submission directory
            File submissionDir = (File) RuleUtils.getContextProperty(context,
                ArchiveFileRule.SUBMISSION_DIRECTORY_KEY, File.class);

            // get the start directory 'docs'
            File docsDir = new File(submissionDir, START_DIR);

            // get the files that meet the requirements
            File[] files = RuleUtils.listAllFiles(docsDir, new ComponentSpecificationFileFilter());

            // check if no component specifications are found
            if (files.length == 0) {
                return new RuleResult[] {new RuleResult(false,
                    "No component specification files are found in directory ["
                        + docsDir.getAbsolutePath() + "], or they are not of rtf type.")};
            }

            return new RuleResult[] {new RuleResult(true,
                "OK. Component specification files are found in directory ["
                    + docsDir.getAbsolutePath() + "].")};
        } catch (Throwable e) {
            return new RuleResult[] {new RuleResult(e)};
        }
    }

    /**
     * <p>
     * Cleans up any resources related to this screening rule.
     * </p>
     * <p>
     * This rule does not use any resources that need to be cleaned, and so this
     * method remains empty.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        // do nothing
    }
}
