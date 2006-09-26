/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

package com.cronos.onlinereview.autoscreening.tool.rules;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.cronos.onlinereview.autoscreening.tool.RuleResult;
import com.cronos.onlinereview.autoscreening.tool.ScreeningException;
import com.cronos.onlinereview.autoscreening.tool.ScreeningTask;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;

/**
 * This class contains utility methods used only by the rules package of this
 * component. This class is package-visible so that all methods are solely used
 * from this package.
 * @author urtks
 * @version 1.0
 */
final class RuleUtils {
    /**
     * Represents the size of the buffer when copying the uploaded file to the
     * temporary directory.
     */
    static final int BUFFER_SIZE = 8 * 1024;

    /**
     * Private constructor to prevent this class be initialized.
     */
    private RuleUtils() {
    }

    /**
     * Deletes the dir and all files and subdirectories under the dir. If a
     * deletion fails, the method does not stop but continues attempting to
     * delete other files.
     * @param dir
     *            the directory to delete
     * @return true if all deletions were successful.
     */
    static boolean deleteDir(File dir) {
        boolean success = true;

        // check if dir is a directory
        if (dir.isDirectory()) {

            // try to delete all files and subdirectories under the dir
            File[] subDirs = dir.listFiles();
            for (int i = 0; i < subDirs.length; i++) {
                success = success && deleteDir(subDirs[i]);
            }
        }

        // The directory is now empty so delete it
        return success && dir.delete();
    }

    /**
     * Recursively searches all the files in the specified directory and return
     * the files that are accepted by the given fileFilter.
     * @param dir
     *            the dir to search from
     * @param fileFilter
     *            the file filter
     * @return an array of File that represents the files in the specified
     *         directory and accepted by the given fileFilter.
     */
    static File[] listAllFiles(File dir, FileFilter fileFilter) {
        if (!dir.isDirectory()) {
            return new File[0];
        }

        ArrayList fileList = new ArrayList();

        // enumerate all the sub directories
        File[] subDirs = dir.listFiles();
        for (int i = 0; i < subDirs.length; ++i) {
            if (subDirs[i].isFile() && (fileFilter == null || fileFilter.accept(subDirs[i]))) {
                fileList.add(subDirs[i]);
            }

            if (subDirs[i].isDirectory()) {
                fileList.addAll(Arrays.asList(listAllFiles(subDirs[i], fileFilter)));
            }
        }

        return (File[]) fileList.toArray(new File[0]);
    }

    /**
     * Gets the stack trace string of a Throwable exception.
     * @param e
     *            the throwable exception
     * @return the string that represents the stack trace
     */
    static String getStackTrace(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);

        e.printStackTrace(pw);

        pw.close();
        return sw.toString();
    }

    /**
     * Close an input stream silently.
     * @param in
     *            the input stream
     */
    static void closeStreamSilently(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Close an output stream silently.
     * @param out
     *            the output stream
     */
    static void closeStreamSilently(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * Performs the additional logging before a screening operation begins.
     * @param log
     *            the Log instance to add logging information to
     * @param screeningTask
     *            the screening task to begin
     */
    static void beginScreeningLogging(Log log, ScreeningTask screeningTask) {

        log.log(Level.INFO, "Perform screening task with id [" + screeningTask.getId()
            + "] by screener with id [" + screeningTask.getScreenerId() + "].");
    }

    /**
     * Performs the additional logging after a screening operation finished.
     * @param log
     *            the Log instance to add logging information to
     * @param ruleResults
     *            the rule results of the screening operation
     */
    static void endScreeningLogging(Log log, RuleResult[] ruleResults) {

        // enumerate each rule result
        for (int i = 0; i < ruleResults.length; ++i) {

            // do the logging according to the rule result type
            if (ruleResults[i].isSuccessful()) {
                log.log(Level.INFO, "Success: " + ruleResults[i].getMessage());
            } else if (ruleResults[i].getError() != null) {
                log
                    .log(Level.FATAL, "Error: "
                        + RuleUtils.getStackTrace(ruleResults[i].getError()));
            } else {
                log.log(Level.WARN, "Failure: " + ruleResults[i].getMessage());
            }
        }
    }

    /**
     * Reads the content of a file into a string.
     * @param file
     *            the file to read
     * @return a String instance that represents the content of the file, or
     *         null if unable to get the content for any reason.
     * @throws IOException
     *             if a problem occurs when reading from the file
     */
    static String readToEnd(File file) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            // create a buffer
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }

            return out.toString();
        } finally {
            RuleUtils.closeStreamSilently(in);
        }
    }

    /**
     * Reads from the input stream and writes the content to the given file.
     * @param file
     *            the file to save content to
     * @param in
     *            the input stream
     * @throws IOException
     *             if a problem occurs when saving data to the file
     */
    static void saveToFile(File file, InputStream in) throws IOException {

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);

            // create a buffer
            byte[] buf = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        } finally {
            RuleUtils.closeStreamSilently(in);
            RuleUtils.closeStreamSilently(out);
        }
    }

    /**
     * Get the property with the given key in the screening context.
     * @param context
     *            the screening context
     * @param key
     *            the key of the property
     * @param type
     *            the type of the property
     * @return the property object associated with the given key
     * @throws ScreeningException
     *             if the property does not exist or is not of correct type
     */
    static Object getContextProperty(Map context, String key, Class type) {
        // get the property object using the given key
        Object object = context.get(key);

        // check if the property object exists
        if (object == null) {
            throw new ScreeningException("The property [" + key
                + "] does not exist in the screening context or its value is null.");
        }

        // check if the property object is of the correct type
        if (!type.isInstance(object)) {
            throw new ScreeningException("The property [" + key + "] should be of type "
                + type.getName() + ".");
        }

        return object;
    }
}
