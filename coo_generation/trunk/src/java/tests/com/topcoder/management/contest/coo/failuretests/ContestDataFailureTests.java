/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.contest.coo.failuretests;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.management.contest.coo.ContestData;

public class ContestDataFailureTests {

    /**
     * Instance of BaseDBConnector for testing.
     */
    private ContestData contestData;

    /**
     * Setting up environment.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        contestData = new ContestData();
    }

    /**
     * Method under test ContestData.setComponentName(String). Failure testing
     * of exception in case componentName is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetComponentNameFailureNull() throws Exception {
        try {
            contestData.setComponentName(null);
            Assert.fail("IllegalArgumentException is expected since componentName is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setComponentName(String). Failure testing
     * of exception in case componentName is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetComponentNameFailureEmpty() throws Exception {
        try {
            contestData.setComponentName("");
            Assert.fail("IllegalArgumentException is expected since componentName is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setComponentName(String). Failure testing
     * of exception in case componentName is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetComponentNameFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setComponentName("     ");
            Assert.fail("IllegalArgumentException is expected since componentName is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setContestEndDate(Date). Failure testing of
     * exception in case contestEndDate is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetContestEndDateFailureNull() throws Exception {
        try {
            contestData.setContestEndDate(null);
            Assert.fail("IllegalArgumentException is expected since contestEndDate is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDesignWinner(String). Failure testing of
     * exception in case designWinner is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDesignWinnerFailureEmpty() throws Exception {
        try {
            contestData.setDesignWinner("");
            Assert.fail("IllegalArgumentException is expected since designWinner is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDesignWinner(String). Failure testing of
     * exception in case designWinner is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDesignWinnerFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setDesignWinner("     ");
            Assert.fail("IllegalArgumentException is expected since designWinner is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDesignSecondPlace(String). Failure
     * testing of exception in case designSecondPlace is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDesignSecondPlaceFailureEmpty() throws Exception {
        try {
            contestData.setDesignSecondPlace("");
            Assert.fail("IllegalArgumentException is expected since designSecondPlace is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDesignSecondPlace(String). Failure
     * testing of exception in case designSecondPlace is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDesignSecondPlaceFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setDesignSecondPlace("     ");
            Assert.fail("IllegalArgumentException is expected since designSecondPlace is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDesignReviewers(List). Failure testing
     * of exception in case designReviewers is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDesignReviewersFailureNull() throws Exception {
        try {
            contestData.setDesignReviewers(Arrays.asList((String) null));
            Assert.fail("IllegalArgumentException is expected since designReviewers is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDevelopmentWinner(String). Failure
     * testing of exception in case developmentWinner is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDevelopmentWinnerFailureEmpty() throws Exception {
        try {
            contestData.setDevelopmentWinner("");
            Assert.fail("IllegalArgumentException is expected since developmentWinner is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDevelopmentWinner(String). Failure
     * testing of exception in case developmentWinner is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDevelopmentWinnerFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setDevelopmentWinner("     ");
            Assert.fail("IllegalArgumentException is expected since developmentWinner is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDevelopmentSecondPlace(String). Failure
     * testing of exception in case developmentSecondPlace is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDevelopmentSecondPlaceFailureEmpty() throws Exception {
        try {
            contestData.setDevelopmentSecondPlace("");
            Assert.fail("IllegalArgumentException is expected since developmentSecondPlace is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDevelopmentSecondPlace(String). Failure
     * testing of exception in case developmentSecondPlace is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDevelopmentSecondPlaceFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setDevelopmentSecondPlace("     ");
            Assert.fail("IllegalArgumentException is expected since developmentSecondPlace is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setDevelopmentReviewers(List). Failure
     * testing of exception in case developmentReviewers is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetDevelopmentReviewersFailureNull() throws Exception {
        try {
            contestData.setDevelopmentReviewers(Arrays.asList((String) null));
            Assert.fail("IllegalArgumentException is expected since developmentReviewers is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setSvnPath(String). Failure testing of
     * exception in case svnPath is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetSvnPathFailureNull() throws Exception {
        try {
            contestData.setSvnPath(null);
            Assert.fail("IllegalArgumentException is expected since svnPath is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setSvnPath(String). Failure testing of
     * exception in case svnPath is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetSvnPathFailureEmpty() throws Exception {
        try {
            contestData.setSvnPath("");
            Assert.fail("IllegalArgumentException is expected since svnPath is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setSvnPath(String). Failure testing of
     * exception in case svnPath is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetSvnPathFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setSvnPath("     ");
            Assert.fail("IllegalArgumentException is expected since svnPath is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setCategory(String). Failure testing of
     * exception in case category is null.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetCategoryFailureNull() throws Exception {
        try {
            contestData.setCategory(null);
            Assert.fail("IllegalArgumentException is expected since category is null");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setCategory(String). Failure testing of
     * exception in case category is empty.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetCategoryFailureEmpty() throws Exception {
        try {
            contestData.setCategory("");
            Assert.fail("IllegalArgumentException is expected since category is empty");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Method under test ContestData.setCategory(String). Failure testing of
     * exception in case category is empty trimmed.
     * @throws Exception wraps all exceptions
     */
    @Test
    public final void testSetCategoryFailureEmptyTrimmed() throws Exception {
        try {
            contestData.setCategory("     ");
            Assert.fail("IllegalArgumentException is expected since category is empty trimmed");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    @Test
    public final void testSetCategory() {
    }
}