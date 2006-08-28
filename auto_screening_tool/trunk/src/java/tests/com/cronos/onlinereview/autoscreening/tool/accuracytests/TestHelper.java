/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.autoscreening.tool.accuracytests;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * <p>Helper class for the accuracy test.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class TestHelper {
    /**
     * private constructor.
     */
    private TestHelper(){
        // empty
    }

    /**
     * Deletes the dir and all files and subdirectories under the dir. If a
     * deletion fails, the method does not stop but continues attempting to
     * delete other files.
     * @param dir
     *            the directory to delete
     * @return true if all deletions were successful.
     */
    public static boolean deleteDir(File dir) {
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
    public static File[] listAllFiles(File dir, FileFilter fileFilter) {
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
}
