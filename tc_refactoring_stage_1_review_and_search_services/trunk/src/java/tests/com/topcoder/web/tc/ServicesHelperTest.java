/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.topcoder.json.object.JSONObject;
import com.topcoder.json.object.io.JSONDecoder;
import com.topcoder.json.object.io.StandardJSONDecoder;
import com.topcoder.web.tc.action.ContestServicesActionException;
import com.topcoder.web.tc.dto.AbstractContestsFilter;
import com.topcoder.web.tc.dto.ContestsFilter;
import com.topcoder.web.tc.dto.DateIntervalType;
import com.topcoder.web.tc.dto.ReviewOpportunitiesFilter;
import com.topcoder.web.tc.dto.UpcomingContestsFilter;

/**
 * <p>
 * Tests the ServicesHelper class.
 * </p>
 * @author duxiaoyang
 * @version 1.0
 */
public class ServicesHelperTest {

    /**
     * Represents the logger used for test.
     */
    private Logger logger = Logger.getLogger(getClass());

    /**
     * Represents the JSON decoder used to decode JSON string.
     */
    private JSONDecoder decoder = new StandardJSONDecoder();

    /**
     * <p>
     * Tests the checkNumber(int, String, String, Logger) method. It uses negative integer. IllegalArgumentException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNumber_Nagitive() {
        ServicesHelper.checkNumber(-5, "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkNumber(int, String, String, Logger) method. It uses 0 integer. IllegalArgumentException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckNumber_Zero() {
        ServicesHelper.checkNumber(0, "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkNumber(int, String, String, Logger) method. It uses valid integer. No exception should be thrown.
     * </p>
     */
    @Test
    public void testCheckNumber() {
        ServicesHelper.checkNumber(-1, "param", "method", logger);
        ServicesHelper.checkNumber(5, "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkString(String, String, String, Logger) method. It uses null string. IllegalArgumentException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckString_Null() {
        ServicesHelper.checkString(null, "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkString(String, String, String, Logger) method. It uses empty string. IllegalArgumentException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckString_Empty() {
        ServicesHelper.checkString(" ", "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkString(String, String, String, Logger) method. It uses valid string. No exception should be
     * thrown.
     * </p>
     */
    @Test
    public void testCheckString() {
        ServicesHelper.checkString("hello", "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkObject(Object, String, String, Logger) method. It uses null object. IllegalArgumentException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckObject_Null() {
        ServicesHelper.checkObject(null, "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkObject(Object, String, String, Logger) method. It uses valid object. No exception should be
     * thrown.
     * </p>
     */
    @Test
    public void testCheckObject() {
        ServicesHelper.checkObject(new Object(), "param", "method", logger);
    }

    /**
     * <p>
     * Tests the checkConfigObject(Object, String) method. It uses null object. ContestServicesConfigurationException is
     * expected to be thrown.
     * </p>
     */
    @Test(expected = ContestServicesConfigurationException.class)
    public void testCheckConfigObject_Null() {
        ServicesHelper.checkConfigObject(null, "config");
    }

    /**
     * <p>
     * Tests the checkConfigObject(Object, String) method. It uses valid object. No exception should be thrown.
     * </p>
     */
    @Test
    public void testCheckConfigObject() {
        ServicesHelper.checkConfigObject(new Object(), "config");
    }

    /**
     * <p>
     * Tests the logException(Logger, String, Exception) method. It verifies that the exception is logged correctly.
     * </p>
     */
    @Test
    public void testLogException() {
        ContestsServiceManagerException e = new ContestsServiceManagerException("error");
        ContestsServiceManagerException ex = ServicesHelper.logException(logger, "method", e);
        assertTrue("exception should be logged", ex.isLogged());
        assertEquals("error message should not change", "error", ex.getMessage());
        // no log should be performed for an exception which is already logged (can be seen in log file)
        ServicesHelper.logException(logger, "method", e);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses invalid sortingOrder field. ContestServicesActionException
     * is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParamFromJson_InvalidSortingOrder() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"abc\","
                + "\"pageNumber\":5,\"pageSize\":10}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.getParamFromJson(json);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses invalid pageNumber field. ContestServicesActionException
     * is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParamFromJson_InvalidPageNumber() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"ASCENDING\","
                + "\"pageNumber\":\"abc\",\"pageSize\":10}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.getParamFromJson(json);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses negative pageNumber field. ContestServicesActionException
     * is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParamFromJson_NegativePageNumber() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"ASCENDING\","
                + "\"pageNumber\":-5,\"pageSize\":10}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.getParamFromJson(json);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses invalid pageSize field. ContestServicesActionException is
     * expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParamFromJson_InvalidPageSize() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"ASCENDING\","
                + "\"pageNumber\":\"5\",\"pageSize\":\"abc\"}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.getParamFromJson(json);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses negative pageSize field. ContestServicesActionException is
     * expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testGetParamFromJson_NegativePageSize() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"ASCENDING\","
                + "\"pageNumber\":5,\"pageSize\":-10}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.getParamFromJson(json);
    }

    /**
     * <p>
     * Tests the getParamFromJson(JSONObject) method. It uses valid JSONObject and verifies that the parameters are
     * retrieved correctly. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testGetParamFromJson() throws Exception {
        String jsonString = "{\"columnName\":\"column\",\"sortingOrder\":\"ASCENDING\","
                + "\"pageNumber\":5,\"pageSize\":\"10\"}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        Map<String, Object> params = ServicesHelper.getParamFromJson(json);
        assertEquals("columnName should be returned", "column", params.get("columnName"));
        assertEquals("sortingOrder should be returned", SortingOrder.ASCENDING, params.get("sortingOrder"));
        assertEquals("pageNumber should be returned", 5, params.get("pageNumber"));
        assertEquals("pageSize should be returned", 10, params.get("pageSize"));

        json = new JSONObject();
        params = ServicesHelper.getParamFromJson(json);
        assertNull("columnName should be null", params.get("columnName"));
        assertNull("sortingOrder should be null", params.get("sortingOrder"));
        assertEquals("pageNumber should be -1", -1, params.get("pageNumber"));
        assertEquals("pageSize should be -1", -1, params.get("pageSize"));
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a date without intervalType
     * field. ContestServicesActionException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testSetFilterFields_NoDateIntervalType() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"}}}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, new ContestsFilter(), "yyyyMMdd");
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a date with invalid
     * intervalType field. ContestServicesActionException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testSetFilterFields_InvalidDateIntervalType() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"unknown\",\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"}}}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, new ContestsFilter(), "yyyyMMdd");
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a date with invalid date
     * field. ContestServicesActionException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testSetFilterFields_InvalidDateFormat() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"2011/06/14\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"}}}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, new ContestsFilter(), "yyyyMMdd");
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a date without firstDate
     * field while it is required. ContestServicesActionException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testSetFilterFields_MissingFirstDate() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BETWEEN_DATES\",\"secondDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"}}}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, new ContestsFilter(), "yyyyMMdd");
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a date without secondDate
     * field while it is required. ContestServicesActionException is expected to be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test(expected = ContestServicesActionException.class)
    public void testSetFilterFields_MissingSecondDate() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BETWEEN_DATES\",\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"}}}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, new ContestsFilter(), "yyyyMMdd");
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses an empty JSONObject and
     * verifies that the filter has no field set. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSetFilterFields_Null() throws Exception {
        String jsonString = "{}";
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ContestsFilter filter = new ContestsFilter();
        ServicesHelper.setFilterFields(json, filter, "yyyyMMdd");
        assertNull("no field should be set", filter.getType());
        assertNull("no field should be set", filter.getSubtype());
        assertNull("no field should be set", filter.getCatalog());
        assertNull("no field should be set", filter.getRegistrationStart());
        assertNull("no field should be set", filter.getSubmissionEnd());
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a valid JSONObject and
     * verifies that the fields are set correctly for AbstractContestsFilter. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSetFilterFields_Abstract() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"20110614\"}}}";
        AbstractContestsFilter filter = new AbstractContestsFilter() {
        };
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, filter, "yyyyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        assertEquals("contestName should be set", "contest", filter.getContestName());
        assertEquals("type should be set", 2, filter.getType().size());
        assertEquals("type should be set", "type1", filter.getType().get(0));
        assertEquals("type should be set", "type2", filter.getType().get(1));
        assertEquals("subtype should be set", 2, filter.getSubtype().size());
        assertEquals("subtype should be set", "subtype1", filter.getSubtype().get(0));
        assertEquals("subtype should be set", "subtype2", filter.getSubtype().get(1));
        assertEquals("catalog should be set", 2, filter.getCatalog().size());
        assertEquals("catalog should be set", "catalog1", filter.getCatalog().get(0));
        assertEquals("catalog should be set", "catalog2", filter.getCatalog().get(1));
        assertEquals("registrationStart should be set", DateIntervalType.BEFORE, filter.getRegistrationStart()
                .getIntervalType());
        assertEquals("registrationStart should be set", "20110614",
                format.format(filter.getRegistrationStart().getFirstDate()));
        assertNull("submissionEnd should not be set", filter.getSubmissionEnd());
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a valid JSONObject and
     * verifies that the fields are set correctly for ReviewOpportunitiesFilter. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSetFilterFields_ReviewOpportunitiesFilter() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"paymentStart\":500,\"paymentEnd\":\"1000\","
                + "\"reviewEndDate\":{\"intervalType\":\"ON\",\"firstDate\":\"20110614\"}}}";
        ReviewOpportunitiesFilter filter = new ReviewOpportunitiesFilter();
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, filter, "yyyyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        assertEquals("paymentStart should be set", 500, filter.getPaymentStart().intValue());
        assertEquals("paymentEnd should be set", 1000, filter.getPaymentEnd().intValue());
        assertEquals("reviewEndDate should be set", DateIntervalType.ON, filter.getReviewEndDate().getIntervalType());
        assertEquals("reviewEndDate should be set", "20110614", format.format(filter.getReviewEndDate().getFirstDate()));
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a valid JSONObject and
     * verifies that the fields are set correctly for ContestsFilter. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSetFilterFields_ContestsFilter() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"winnerHandle\":\"winner\","
                + "\"contestFinalization\":{\"intervalType\":\"BETWEEN_DATES\",\"firstDate\":\"20110614\",\"secondDate\":\"20110630\"}}}";
        ContestsFilter filter = new ContestsFilter();
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, filter, "yyyyMMdd");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        assertEquals("winnerHandle should be set", "winner", filter.getWinnerHandle());
        assertEquals("contestFinalization should be set", DateIntervalType.BETWEEN_DATES, filter
                .getContestFinalization().getIntervalType());
        assertEquals("contestFinalization should be set", "20110614",
                format.format(filter.getContestFinalization().getFirstDate()));
        assertEquals("contestFinalization should be set", "20110630",
                format.format(filter.getContestFinalization().getSecondDate()));
    }

    /**
     * <p>
     * Tests the setFilterFields(JSONObject, AbstractContestsFilter, String) method. It uses a valid JSONObject and
     * verifies that the fields are set correctly for UpcomingContestsFilter. No exception should be thrown.
     * </p>
     * @throws Exception
     *             to JUnit.
     */
    @Test
    public void testSetFilterFields_UpcomingContestsFilter() throws Exception {
        String jsonString = "{\"filter\":{\"contestName\":\"contest\",\"type\":[\"type1\",\"type2\"],"
                + "\"subtype\":[\"subtype1\",\"subtype2\"],\"catalog\":[\"catalog1\",\"catalog2\"],"
                + "\"registrationStart\":{\"intervalType\":\"BEFORE\",\"firstDate\":\"20110614\"},"
                + "\"submissionEnd\":{\"intervalType\":\"BEFORE_CURRENT_DATE\"},"
                + "\"prizeStart\":500,\"prizeEnd\":1000}}";
        UpcomingContestsFilter filter = new UpcomingContestsFilter();
        JSONObject json = (JSONObject) decoder.decode(jsonString);
        ServicesHelper.setFilterFields(json, filter, "yyyyMMdd");
        assertEquals("prizeStart should be set", 500, filter.getPrizeStart().intValue());
        assertEquals("prizeEnd should be set", 1000, filter.getPrizeEnd().intValue());
    }
}
