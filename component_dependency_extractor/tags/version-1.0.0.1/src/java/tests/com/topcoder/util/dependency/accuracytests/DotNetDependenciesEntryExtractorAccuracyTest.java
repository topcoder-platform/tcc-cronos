/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.util.dependency.accuracytests;

import java.util.List;

import junit.framework.TestCase;

import com.topcoder.util.dependency.DependenciesEntry;
import com.topcoder.util.dependency.extractor.DotNetDependenciesEntryExtractor;
import com.topcoder.util.log.Log;

/**
 * <p>
 * Accuracy test for class DotNetDependenciesEntryExtractor.
 * </p>
 *
 * @author telly12
 * @version 1.0
 */
public class DotNetDependenciesEntryExtractorAccuracyTest extends TestCase {

    /**
     * <p>
     * All the files.
     * </p>
     */
    private static List<String> entries;

    static {
        String zipFilePath = "test_files/accuracytests/BuildScript.zip";
        try {
            entries = AccuracyTestHelper.getEntries(zipFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test extract() method for all the java build.xml files.
     *
     * @throws Exception
     *             to test case
     */
    public void testExtract_All() throws Exception {
        String currentFilePath = "test_files/accuracytests/BuildScript/";
        Log logger = new MyLog("DotNetDependenciesEntryExtractor.accuracytests");
        DotNetDependenciesEntryExtractor extractor = new DotNetDependenciesEntryExtractor(logger);
        for (String entryPath : entries) {
            if (entryPath.startsWith("dotnet") && extractor.isSupportedFileType(currentFilePath + entryPath)) {
                try {
                    DependenciesEntry entry = extractor.extract(currentFilePath + entryPath);
                    assertNotNull("extract result", entry);
                } catch (Exception e) {
                    fail("Error should not occurs:" + e.getMessage());
                }
            }
        }
    }
}
