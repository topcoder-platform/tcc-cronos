/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Unit test for <code>{@link SimpleProjectContestData}</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class SimpleProjectContestDataTest extends TestCase {
    /**
     * Represents the <code>SimpleProjectContestData</code> instance to test.
     */
    private SimpleProjectContestData contestData = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestData = new SimpleProjectContestData();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestData = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(SimpleProjectContestDataTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getContestId()} and
     * {@link SimpleProjectContestData#setContestId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestId() {
        // set the value to test
        contestData.setContestId(new Long(1));
        assertEquals("getContestId and setContestId failure occured", 1L, (long) contestData.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setContestId(Long)} and
     * {@link SimpleProjectContestData#getContestId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestId() {
        // set the value to test
        contestData.setContestId(1L);
        assertEquals("getContestId and setContestId failure occured", 1L, (long) contestData.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getProjectId()} and
     * {@link SimpleProjectContestData#setProjectId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getProjectId() {
        // set the value to test
        contestData.setProjectId(new Long(1));
        assertEquals("getProjectId and setProjectId failure occured", 1L, (long) contestData.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setProjectId(Long)} and
     * {@link SimpleProjectContestData#getProjectId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setProjectId() {
        // set the value to test
        contestData.setProjectId(1L);
        assertEquals("getProjectId and setProjectId failure occured", 1L, (long) contestData.getProjectId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getNum_reg()} and
     * {@link SimpleProjectContestData#setNum_reg(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getNum_reg() {
        // set the value to test
        contestData.setNum_reg(null);
        assertEquals("getNum_reg and setNum_reg failure occured", null, contestData.getNum_reg());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setNum_reg(Integer)} and
     * {@link SimpleProjectContestData#getNum_reg()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setNum_reg() {
        // set the value to test
        contestData.setNum_reg(1);
        assertEquals("getNum_reg and setNum_reg failure occured", 1, (int) contestData.getNum_reg());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getNum_sub()} and
     * {@link SimpleProjectContestData#setNum_sub(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getNum_sub() {
        // set the value to test
        contestData.setNum_sub(null);
        assertEquals("getNum_sub and setNum_sub failure occured", null, contestData.getNum_sub());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setNum_sub(Integer)} and
     * {@link SimpleProjectContestData#getNum_sub()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setNum_sub() {
        // set the value to test
        contestData.setNum_sub(1);
        assertEquals("getNum_sub and setNum_sub failure occured", 1, (int) contestData.getNum_sub());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getNum_for()} and
     * {@link SimpleProjectContestData#setNum_for(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getNum_for() {
        // set the value to test
        contestData.setNum_for(null);
        assertEquals("getNum_for and setNum_for failure occured", null, contestData.getNum_for());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setNum_for(Integer)} and
     * {@link SimpleProjectContestData#getNum_for()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setNum_for() {
        // set the value to test
        contestData.setNum_for(1);
        assertEquals("getNum_for and setNum_for failure occured", 1, (int) contestData.getNum_for());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getForumId()} and
     * {@link SimpleProjectContestData#setForumId(Integer)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getForumId() {
        // set the value to test
        contestData.setForumId(null);
        assertEquals("getForumId and setForumId failure occured", null, contestData.getForumId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setForumId(Integer)} and
     * {@link SimpleProjectContestData#getForumId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setForumId() {
        // set the value to test
        contestData.setForumId(1);
        assertEquals("getForumId and setForumId failure occured", 1, (int) contestData.getForumId());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getPname()} and
     * {@link SimpleProjectContestData#setPname(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPname() {
        // set the value to test
        contestData.setPname(null);
        assertEquals("getPname and setPname failure occured", null, contestData.getPname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setPname(String)} and
     * {@link SimpleProjectContestData#getPname()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPname() {
        // set the value to test
        contestData.setPname("test");
        assertEquals("getPname and setPname failure occured", "test", contestData.getPname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getCname()} and
     * {@link SimpleProjectContestData#setCname(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCname() {
        // set the value to test
        contestData.setCname(null);
        assertEquals("getCname and setCname failure occured", null, contestData.getCname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setCname(String)} and
     * {@link SimpleProjectContestData#getCname()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setCname() {
        // set the value to test
        contestData.setCname("test");
        assertEquals("getCname and setCname failure occured", "test", contestData.getCname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getSname()} and
     * {@link SimpleProjectContestData#setSname(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getSname() {
        // set the value to test
        contestData.setSname(null);
        assertEquals("getSname and setSname failure occured", null, contestData.getSname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setSname(String)} and
     * {@link SimpleProjectContestData#getSname()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setSname() {
        // set the value to test
        contestData.setSname("test");
        assertEquals("getSname and setSname failure occured", "test", contestData.getSname());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getDescription()} and
     * {@link SimpleProjectContestData#setDescription(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getDescription() {
        // set the value to test
        contestData.setDescription(null);
        assertEquals("getDescription and setDescription failure occured", null, contestData.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setDescription(String)} and
     * {@link SimpleProjectContestData#getDescription()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setDescription() {
        // set the value to test
        contestData.setDescription("test");
        assertEquals("getDescription and setDescription failure occured", "test", contestData.getDescription());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getContestType()} and
     * {@link SimpleProjectContestData#setContestType(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getContestType() {
        // set the value to test
        contestData.setContestType(null);
        assertEquals("getContestType and setContestType failure occured", null, contestData.getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setContestType(String)} and
     * {@link SimpleProjectContestData#getContestType()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setContestType() {
        // set the value to test
        contestData.setContestType("test");
        assertEquals("getContestType and setContestType failure occured", "test", contestData.getContestType());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getCreateUser()} and
     * {@link SimpleProjectContestData#setCreateUser(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCreateUser() {
        // set the value to test
        contestData.setCreateUser(null);
        assertEquals("getCreateUser and setCreateUser failure occured", null, contestData.getCreateUser());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setCreateUser(String)} and
     * {@link SimpleProjectContestData#getCreateUser()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setCreateUser() {
        // set the value to test
        contestData.setCreateUser("test");
        assertEquals("getCreateUser and setCreateUser failure occured", "test", contestData.getCreateUser());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getCperm()} and
     * {@link SimpleProjectContestData#setCperm(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getCperm() {
        // set the value to test
        contestData.setCperm(null);
        assertEquals("getCperm and setCperm failure occured", null, contestData.getCperm());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setCperm(String)} and
     * {@link SimpleProjectContestData#getCperm()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setCperm() {
        // set the value to test
        contestData.setCperm("test");
        assertEquals("getCperm and setCperm failure occured", "test", contestData.getCperm());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getPperm()} and
     * {@link SimpleProjectContestData#setPperm(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getPperm() {
        // set the value to test
        contestData.setPperm(null);
        assertEquals("getPperm and setPperm failure occured", null, contestData.getPperm());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setPperm(String)} and
     * {@link SimpleProjectContestData#getPperm()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setPperm() {
        // set the value to test
        contestData.setPperm("test");
        assertEquals("getPperm and setPperm failure occured", "test", contestData.getPperm());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getStartDate()} and
     * {@link SimpleProjectContestData#setStartDate(XMLGregorianCalendar)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getStartDate() {
        // set the value to test
        contestData.setStartDate(null);
        assertEquals("getStartDate and setStartDate failure occured", null, contestData.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setStartDate(XMLGregorianCalendar)} and
     * {@link SimpleProjectContestData#getStartDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setStartDate() {
        XMLGregorianCalendar startDate = getXMLGregorianCalendar(new Date());
        // set the value to test
        contestData.setStartDate(startDate);
        assertEquals("getStartDate and setStartDate failure occured", startDate, contestData.getStartDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#getEndDate()} and
     * {@link SimpleProjectContestData#setEndDate(XMLGregorianCalendar)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getEndDate() {
        // set the value to test
        contestData.setEndDate(null);
        assertEquals("getEndDate and setEndDate failure occured", null, contestData.getEndDate());
    }

    /**
     * <p>
     * Accuracy test for {@link SimpleProjectContestData#setEndDate(XMLGregorianCalendar)} and
     * {@link SimpleProjectContestData#getEndDate()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setEndDate() {
        XMLGregorianCalendar endDate = getXMLGregorianCalendar(new Date());
        // set the value to test
        contestData.setEndDate(endDate);
        assertEquals("getEndDate and setEndDate failure occured", endDate, contestData.getEndDate());
    }

    /**
     * <p>
     * Converts specified <code>Date</code> instance into
     * <code>XMLGregorianCalendar</code> instance.
     * </p>
     *
     * @param date
     *            a <code>Date</code> representing the date to be converted.
     * @return a <code>XMLGregorianCalendar</code> providing the converted value
     *         of specified date or <code>null</code> if specified
     *         <code>date</code> is <code>null</code> or if it can't be
     *         converted to calendar.
     */
    private static XMLGregorianCalendar getXMLGregorianCalendar(Date date) {
        if (date == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException ex) {
            return null;
        }
    }
}
