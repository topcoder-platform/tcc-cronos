/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.util.log.LogManager;
import com.topcoder.web.common.TCWebException;

/**
 * <p>
 * Unit test case of {@link RetrieveCardDataAction}.
 * </p>
 *
 * @author mumujava
 * @version 1.0
 */
public class RetrieveCardDataActionTest extends TestCase {
    /**
     * <p>
     * The RetrieveCardDataAction instance to test.
     * </p>
     * */
    private RetrieveCardDataAction instance;

    /**
     * <p>
     * Sets up test environment.
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
        instance.setAuthenticationSessionKey("authenticationkey");
        Map<String, Object> session = new HashMap<String, Object>();
        session.put("authenticationkey", new MockBasicAuthentication());
        instance.setSession(session);
        DataAccessInt dataAccess = new MockDataAccessInt();
        instance.setDataAccess(dataAccess);
        DataAccessConstants.COMMAND = "COMMAND";
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     * @throws Exception
     *             to jUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        instance = null;
    }

    /**
     * <p>
     * Creates a test suite for the tests in this test case.
     * </p>
     *
     * @return a TestSuite for this test case.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite(RetrieveCardDataActionTest.class);
        return suite;
    }

    /**
     * <p>
     * Accuracy test for constructor RetrieveCardDataAction. It verifies
     * instance is correctly created.
     * </p>
     *
     * @throws Exception
     *             to Junit
     */
    public void test_RetrieveCardDataAction0() throws Exception {
        assertNotNull("The instance should not be null", instance);
    }

    /**
     * <p>
     * Accuracy test for method getXmlStream. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_getXmlStream() throws Exception {
        InputStream res = instance.getXmlStream();
        assertNull("Incorrect result", res);
    }

    /**
     * <p>
     * Accuracy test for method execute. The result should be correct.
     * </p>
     *
     * It verifies the result is correct.
     *
     * @throws Exception
     *             to Junit
     */
    public void test_execute() throws Exception {
        instance.setCoderId(100);
        String res = instance.execute();
        assertEquals("Incorrect result", "success", res);
        ByteArrayInputStream stream = (ByteArrayInputStream) instance.getXmlStream();
        assertEquals("Incorrect result", "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><memberStats><handle>hello-c"
            + "</handle><photo>/i/m/hello-c_big.jpg</photo><algorithmRating>910</algorithmRating><algorithmRatingMax>8"
            + "</algorithmRatingMax><rank>12</rank><percentile>1.20</percentile><memberSince>01.01.1970</memberSince>"
            + "<lastMatchDate>01.01.1970</lastMatchDate><bestDiv1>1</bestDiv1><bestDiv2>2</bestDiv2><competitions>3"
            + "</competitions><highSchoolRating>4</highSchoolRating><marathonRating>5</marathonRating><designRating>6"
            + "</designRating><developmentRating>9</developmentRating><conceptualizationRating>10"
            + "</conceptualizationRating><specificationRating>11</specificationRating><architectureRating>12"
            + "</architectureRating><assemblyRating>13</assemblyRating><testSuitesRating>14</testSuitesRating>"
            + "<testScenariosRating>15</testScenariosRating><uiPrototypeRating>16</uiPrototypeRating><riaBuildRating>17"
            + "</riaBuildRating><algorithmRatingDistribution><bucket>test1</bucket><bucket>test2</bucket><bucket>test3"
            + "</bucket></algorithmRatingDistribution></memberStats>",
                getStringFromStream(stream));
    }
    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects TCWebException if data access error occurs.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute2() throws Exception {
        MockDataAccessInt dataAccess = new MockDataAccessInt();
        dataAccess.setErrorFlag(true);
        instance.setDataAccess(dataAccess);
        instance.setCoderId(100);
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects TCWebException if the coder id is not positive.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute4() throws Exception {
        instance.setCoderId(-1);
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
    /**
     * Get a string from the stream.
     *
     * @param stream the stream
     * @return content of the stream
     * @throws Exception if any error occurs
     */
    public static String getStringFromStream(ByteArrayInputStream stream) throws Exception {
        StringBuilder sb = new StringBuilder();
        byte[] buffer = new byte[1024];
        while (true) {
            int size = stream.read(buffer);
            if (size < 0) {
                break;
            }
            sb.append(new String(buffer, 0, size));
        }
        return sb.toString();
    }

    /**
     * <p>
     * Test method for {@link setCoderId()}. It verifies the field is correct
     * set.
     * </p>
     *
     * @throws Exception
     *             to junit
     */
    public void test_setCoderId() throws Exception {
        instance.setCoderId(1);
        assertEquals("Incorrect value after set a new one", 1L, TestHelper.outject(
                RetrieveCardDataAction.class, instance, "coderId"));
    }

    /**
     * <p>
     * Failure test for method execute.
     *
     * Expects the TCWebException if the authentication is not injected.
     * </p>
     * @throws Exception to Junit
     */
    public void test_execute12() throws Exception {
        instance.setAuthenticationSessionKey("notexist");
        try {
            instance.execute();
            fail("Expects an Exception");
        } catch (TCWebException e) {
            // pass
        }
    }
}
