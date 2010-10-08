/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.cronos.onlinereview.review.statistics.stresstests;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.topcoder.configuration.ConfigurationObject;
import com.topcoder.configuration.persistence.XMLFilePersistence;
import com.topcoder.management.review.data.Comment;
import com.topcoder.management.review.data.EvaluationType;
import com.topcoder.management.review.data.Review;
import com.topcoder.util.config.ConfigManager;

/**
 * Helper class for test.
 *
 * @author extra
 * @version 1.0
 */
public class StressTestHelper {
    /**
     * The test file folder.
     */
    public static final String TEST_PATH = "test_files/stress/";

    /**
     * Get configuration object from XML default file.
     *
     * @param fileName
     *            The configuration file name
     * @throws Exception
     *             for JUnit.
     * @return the configuration object.
     */
    public static ConfigurationObject load(String fileName) throws Exception {
        return new XMLFilePersistence().loadFile("name", new File(TEST_PATH + fileName));
    }

    /**
     * Clear the config.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void clear() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();
        for (Iterator iterator = cm.getAllNamespaces(); iterator.hasNext();) {
            String namespace = (String) iterator.next();
            if ("com.topcoder.util.log".equals(namespace)) {
                continue;
            }
            cm.removeNamespace(namespace);
        }
    }

    /**
     * Init the config.
     *
     * @throws Exception
     *             to JUnit
     */
    public static void init() throws Exception {
        clear();

        ConfigManager cm = ConfigManager.getInstance();

        // load config files.
        cm.add(new File(TEST_PATH + "ConnectionFactory.xml").getAbsolutePath());
        cm.add(new File(TEST_PATH + "PhaseManager.xml").getAbsolutePath());
        cm.add(new File(TEST_PATH + "ProjectManager.xml").getAbsolutePath());
        cm.add(new File(TEST_PATH + "ReviewManager.xml").getAbsolutePath());
        cm.add(new File(TEST_PATH + "SearchBundleManager.xml").getAbsolutePath());
    }

    /**
     * Create review, nReview should be between [0,2],nSubmission should be between [0,2].
     *
     * @param nReview
     *            the number of review
     * @param nSubmission
     *            the number of submission
     * @return the created review
     */
    public static Review createReview(int nReview, int nSubmission) {
        // review, submission, row
        int[][][] values = new int[][][] {{{1, 2, 4, 2 }, {0, 2, 1, 1 }, {0, 1, 2, 0 } },
            {{3, 3, 3, 3 }, {4, 4, 4, 4 }, {2, 2, 2, 2 } }, {{1, 1, 1, 1 }, {0, 0, 0, 0 }, {0, 1, 2, 0 } } };

        Review review = new Review();
        for (int n = 0; n < 4; n++) {
            List<Comment> comments = new ArrayList<Comment>();
            for (int c = 0; c < values[nReview][nSubmission][n]; c++) {
                Comment comment = new Comment();
                EvaluationType evaluationType = null;
                if (n < 3) {
                    evaluationType = new EvaluationType(n + 1, "type[" + (n + 1) + "]");

                } else {
                    evaluationType = new EvaluationType(6, "type[6]");
                }
                comment.setEvaluationType(evaluationType);

                comments.add(comment);
            }

            review.addComments(comments.toArray(new Comment[comments.size()]));
        }
        return review;
    }
}
