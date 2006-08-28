/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.Screener;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningRule;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.file.type.FileType;
import com.topcoder.file.type.MagicNumbers;
import com.topcoder.servlet.request.UploadedFile;
import com.topcoder.util.archiving.ArchiveUtility;
import com.topcoder.util.archiving.ZipArchiver;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.log.LogException;
import com.topcoder.util.log.LogFactory;

/**
 * <p>
 * This rule checks to see whether the file is a valid archive, and unzips it
 * for further rules to process.
 * </p>
 * <p>
 * All other rules use this one for locating the unzipped submission.
 * </p>
 * @author ShindouHikaru, TCSDEVELOPER
 * @version 1.0
 */
public class ArchiveFileRule implements ScreeningRule {

    /**
     * <p>
     * This is the context key under which the unzipped submission will be
     * stored.
     * </p>
     * <p>
     * Other rules may use this key to access the unzipped submission from the
     * context Map. The resulting value stored in the context Map is expected to
     * be the java.io.File object representing directory containing the unzipped
     * submission.
     * </p>
     */
    public static final String SUBMISSION_DIRECTORY_KEY = "unzipped_submission_directory";

    /**
     * Represents the suffix of the path of the unzipped submission directory.
     */
    private static final String SUBMISSION_DIRECTORY_SUFFIX = ".contents";

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
            LOG = LogFactory.getLogWithExceptions(ArchiveFileRule.class.getName());
        } catch (LogException e) {
            throw new ScreeningException("Unable to get a Log instance from name ["
                + ArchiveFileRule.class.getName() + "].", e);
        }
    }

    /**
     * <p>
     * The file extension that this rule will look for.
     * </p>
     */
    private final String archiveExtension;

    /**
     * <p>
     * This is a String that represents the path to the temporary directory to
     * use for storing the extracted file. It is optional, and if it is not
     * specified, then the default temporary directory as specified by the
     * system property java.io.tmpdir will be used.
     * </p>
     */
    private final String temporaryDir;

    /**
     * <p>
     * Constructs an ArchiveFileRule that will check for the given extension.
     * </p>
     * @param archiveExtension
     *            The archiveExtension to check for.
     * @throws IllegalArgumentException
     *             if the archiveExtension is null or an empty String.
     */
    public ArchiveFileRule(String archiveExtension) {
        this(archiveExtension, null);
    }

    /**
     * <p>
     * Constructs an ArchiveFileRule that will check for the given extension and
     * use the specified directory.
     * </p>
     * <p>
     * If tempDirectory is not specified, then the default temporary directory
     * as specified by the system property java.io.tmpdir will be used.
     * </p>
     * @param archiveExtension
     *            The archiveExtension to check for.
     * @param temporaryDir
     *            The temporary directory where to store the unzipped submission
     *            (may be null).
     * @throws IllegalArgumentException
     *             if archiveExtension is null, or archiveExtension or
     *             tempDirectory is an empty trimmed String.
     */
    public ArchiveFileRule(String archiveExtension, String temporaryDir) {
        if (archiveExtension == null) {
            throw new IllegalArgumentException("archiveExtension should not be null.");
        }
        if (archiveExtension.trim().length() == 0) {
            throw new IllegalArgumentException("archiveExtension should not be empty (trimmed).");
        }
        if (temporaryDir != null && temporaryDir.trim().length() == 0) {
            throw new IllegalArgumentException("temporaryDir should not be empty (trimmed).");
        }

        this.archiveExtension = archiveExtension.toLowerCase();
        this.temporaryDir = temporaryDir == null ? System.getProperty("java.io.tmpdir")
            : temporaryDir;
    }

    /**
     * <p>
     * Performs screening on the specified screening task. This implementation
     * will expect the context to contain the File object pointing to the root
     * directory of an unzipped submission.
     * </p>
     * <p>
     * Generally speaking this method does the following things:
     * <ol>
     * <li>Retrieve the uploaded file from the context using
     * Screener.SUBMISSION_KEY.</li>
     * <li>Save the uploaded file to the temporary directory.</li>
     * <li>Check if the file extension is correct.</li>
     * <li>Use MagicNumbers to determine whether file type is
     * "application/zip".</li>
     * <li>Use the archive utility to extract the files into a root directory.</li>
     * <li>Store the root directory in the context.</li>
     * <li>Log this method according to the CS section 1.3.3. </li>
     * </ol>
     * </p>
     * @return The results of screening.
     * @param screeningTask
     *            The screeningTask to screen.
     * @param context
     *            The context which allows additional screening information to
     *            be provided.
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

        File submissionFile = null;
        try {
            // try to get uploadedFile instance
            UploadedFile uploadedFile = (UploadedFile) RuleUtils.getContextProperty(context,
                Screener.SUBMISSION_KEY, UploadedFile.class);

            // get the temporary submission file
            submissionFile = saveUploadedFile(uploadedFile);

            // check file extension
            if (!submissionFile.getName().toLowerCase().endsWith(archiveExtension)) {
                return new RuleResult[] {new RuleResult(false, "The submission file ["
                    + submissionFile.getName() + "] does not have " + archiveExtension
                    + " as its extension.")};
            }

            // determine file type
            FileType type = new MagicNumbers().determineFileType(submissionFile);
            if (type == null || !type.getMime().equals("application/zip")) {
                return new RuleResult[] {new RuleResult(false, "The submission file ["
                    + submissionFile.getName() + "] is not a zip archive.")};
            }

            // create the contents directory for the unzip the submission file
            File contentsDir = new File(temporaryDir, submissionFile.getName()
                + SUBMISSION_DIRECTORY_SUFFIX);
            if (!contentsDir.exists() && !contentsDir.mkdir()) {
                throw new IOException("Unable to make the unzipped submission directory ["
                    + contentsDir.getAbsolutePath() + "].");
            }

            // unzip the submission file into the contents directory.
            ArchiveUtility archiveUtility = new ArchiveUtility(submissionFile, new ZipArchiver());
            archiveUtility.extractContents(contentsDir);

            // put the contentsDir to the screening context
            context.put(SUBMISSION_DIRECTORY_KEY, contentsDir);

            // success
            return new RuleResult[] {new RuleResult(true,
                "OK. The submission archive was successfully unzipped to ["
                    + contentsDir.getAbsoluteFile() + "].")};
        } catch (Throwable e) {
            // an error happened, return an error result.
            return new RuleResult[] {new RuleResult(e)};
        } finally {
            // try to delete the temporary submission file
            if (submissionFile != null) {
                if (submissionFile.exists() && !submissionFile.delete()) {
                    LOG.log(Level.WARN, "Unable to delete the temporary submission file ["
                        + submissionFile.getName() + "].");
                }
            }
        }
    }

    /**
     * Saves the temporary submission file get from the uploaded file into the
     * temporary directory, and returns a File instance that represents it.
     * @param uploadedFile
     *            the uploaded file
     * @return a File instance that represents the save submission file
     * @throws Exception
     *             any exception thrown in the method will be propagated to the
     *             caller.
     */
    private File saveUploadedFile(UploadedFile uploadedFile) throws Exception {

        InputStream in = null;
        FileOutputStream out = null;
        try {
            // get the name of the uploadedFile
            String fileName = uploadedFile.getRemoteFileName();

            // create a temporary submission file under tempDirectory
            File submissionFile = new File(temporaryDir, fileName);

            // save the uploaded file stream to the submission file
            in = uploadedFile.getInputStream();
            RuleUtils.saveToFile(submissionFile, in);

            return submissionFile;
        } finally {
            RuleUtils.closeStreamSilently(in);
            RuleUtils.closeStreamSilently(out);
        }
    }

    /**
     * <p>
     * This method will clean-up by deleting temporary file that was stored in
     * the context under SUBMISSION_DIRECTORY_KEY.
     * </p>
     * @param screeningTask
     *            The screeningTask for which cleanup must be performed.
     * @param context
     *            The screening context which may contain some resources to be
     *            released.
     * @throws IllegalArgumentException
     *             if either parameter is null.
     */
    public void cleanup(ScreeningTask screeningTask, Map context) {
        if (screeningTask == null) {
            throw new IllegalArgumentException("screeningTask should not be null.");
        }
        if (context == null) {
            throw new IllegalArgumentException("context should not be null.");
        }

        LOG.log(Level.INFO, "Cleanup screening task with id [" + screeningTask.getId()
            + "] by screener with id [" + screeningTask.getScreenerId() + "].");

        // get the submission directory
        File submissionDir;
        try {
            submissionDir = (File) RuleUtils.getContextProperty(context, SUBMISSION_DIRECTORY_KEY,
                File.class);
        } catch (ScreeningException e) {
            LOG.log(Level.FATAL, RuleUtils.getStackTrace(e));
            return;
        }

        // try to delete the submission directory
        if (submissionDir.exists() && !RuleUtils.deleteDir(submissionDir)) {
            LOG.log(Level.FATAL,
                "Failed to completely delete temporary unzipped submission directory ["
                    + submissionDir.getAbsolutePath() + "].");
            return;
        }

        // success
        LOG.log(Level.INFO, "OK. The temporary unzipped submission directory ["
            + submissionDir.getAbsolutePath() + "] was deleted successfully.");
    }
}
