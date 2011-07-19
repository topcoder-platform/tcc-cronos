/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.opensymphony.xwork2.ActionSupport;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.reg.actions.miscellaneous.RetrieveCardDataAction;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.AccuracyTestHelper;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock.MockDataAccessInt;

/**
 * <p>
 * Accuracy test for RetrieveCardDataAction class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class RetrieveCardDataActionAccuracyTests extends TestCase {
    /**
     * <p>
     * Represents the instance of RetrieveCardDataAction used in test.
     * </p>
     * */
    private RetrieveCardDataAction instance;

    /**
     * <p>
     * Creates the test suite.
     * </p>
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(RetrieveCardDataActionAccuracyTests.class);
        return suite;
    }

    /**
     * <p>
     * Set up for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        instance = new RetrieveCardDataAction();
        instance.setLog(LogManager.getLog());

        instance.setAuthenticationSessionKey("authKey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authKey", new MockAuthentication());

        instance.setSession(session);
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        DataAccessConstants.COMMAND = "COMMAND";
    }

    /**
     * <p>
     * Tear down for each test.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Accuracy test for RetrieveCardDataAction(). Instance should be created.
     * </p>
     *
     * @throws Exception
     *             to jUnit
     */
    public void testCtor() throws Exception {
        assertNotNull("The instance should not be null", instance);
        assertTrue("RetrieveCardDataAction should extend from BaseDataAccessUserCommunityManagementAction.",
            instance instanceof ActionSupport);
    }

    /**
     * Accuracy test for execute method.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testExecute() throws Exception {
        instance.setCoderId(20L);
        CardHelper.setUnlocked(20L, true);
        String result = instance.execute();
        assertEquals("The execute is inocrrect.", "success", result);

        assertEquals(
            "The execute is inocrrect.",
            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><memberStats><handle>tc"
                + "</handle><photo>tc.jpg</photo><algorithmRating>910</algorithmRating><algorithmRatingMax>7"
                + "</algorithmRatingMax><rank>12</rank><percentile>1.21</percentile><memberSince>01.01.1970</memberSince>"
                + "<lastMatchDate>01.01.1970</lastMatchDate><bestDiv1>1</bestDiv1><bestDiv2>2</bestDiv2><competitions>3"
                + "</competitions><highSchoolRating>4</highSchoolRating><marathonRating>5</marathonRating><designRating>6"
                + "</designRating><developmentRating>8</developmentRating><conceptualizationRating>9"
                + "</conceptualizationRating><specificationRating>11</specificationRating><architectureRating>12"
                + "</architectureRating><assemblyRating>13</assemblyRating><testSuitesRating>14</testSuitesRating>"
                + "<testScenariosRating>15</testScenariosRating><uiPrototypeRating>16</uiPrototypeRating><riaBuildRating>10"
                + "</riaBuildRating><algorithmRatingDistribution><bucket>test1</bucket><bucket>test2</bucket><bucket>test3"
                + "</bucket></algorithmRatingDistribution></memberStats>", toString(instance.getXmlStream()));
    }

    /**
     * Accuracy test for setCoderId(long coderId).
     *
     * @throws Exception
     *             to jUnit
     */
    public void testSetCoderId() throws Exception {
        instance.setCoderId(3);
        assertEquals("The setCoderId is incorrect.", 3L, AccuracyTestHelper.getFieldValue(RetrieveCardDataAction.class,
            instance, "coderId"));
    }

    /**
     * Accuracy test for getXmlStream(). Default to null.
     *
     * @throws Exception
     *             to jUnit
     */
    public void testGetXmlStream() throws Exception {
        InputStream res = instance.getXmlStream();
        assertNull("he getXmlStream is incorrect.", res);
    }

    /**
     * Gets the content in the stream.
     *
     * @param stream
     *            the input stream.
     * @return the string.
     *
     * @throws Exception
     *             to jUnit.
     */
    private static String toString(InputStream stream) throws Exception {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        int size = stream.read(buffer);
        while (size > 0) {
            sb.append(new String(buffer, 0, size));
            size = stream.read(buffer);
        }
        return sb.toString();
    }
}