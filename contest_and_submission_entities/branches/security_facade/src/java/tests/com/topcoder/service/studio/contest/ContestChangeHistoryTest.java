/*
 * Copyright (C) 2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.contest;

import java.util.Date;

import javax.persistence.Query;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * <p>
 * Tests the functionality of {@link ContestChangeHistory} class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 * @since 1.2
 */
public class ContestChangeHistoryTest extends TestCase {

    /**
     * Represents the <code>ContestChangeHistory</code> instance to test.
     */
    private ContestChangeHistory contestChangeHistory = null;

    /**
     * <p>
     * Sets up test environment.
     * </p>
     */
    protected void setUp() {
        contestChangeHistory = new ContestChangeHistory();
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     */
    protected void tearDown() {
        contestChangeHistory = null;
    }

    /**
     * <p>
     * Returns all tests.
     * </p>
     *
     * @return Test suite of all tests of this class.
     */
    public static Test suite() {
        return new TestSuite(ContestChangeHistoryTest.class);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#ContestChangeHistory()} constructor.
     * </p>
     * <p>
     * Creates an instance and checks its instantiation.
     * </p>
     */
    public void test_accuracy_Config() {
        // check for null
        assertNotNull("ContestChangeHistory creation failed", contestChangeHistory);
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getContestId()} and {@link ContestChangeHistory#setContestId(Long)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getContestId() {
        // set the value to test
        contestChangeHistory.setContestId(new Long(1));
        assertEquals("getContestId and setContestId failure occured", new Long(1), contestChangeHistory.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setContestId(Long)} and {@link ContestChangeHistory#getContestId()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setContestId() {
        // set the value to test
        contestChangeHistory.setContestId(1L);
        assertEquals("getContestId and setContestId failure occured", 1L, (long) contestChangeHistory.getContestId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getTransactionId()} and
     * {@link ContestChangeHistory#setTransactionId(Long)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is new Long(1).
     * </p>
     */
    public void test_accuracy_getTransactionId() {
        // set the value to test
        contestChangeHistory.setTransactionId(new Long(1));
        assertEquals("getTransactionId and setTransactionId failure occured", new Long(1), contestChangeHistory
                .getTransactionId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setTransactionId(Long)} and
     * {@link ContestChangeHistory#getTransactionId()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setTransactionId() {
        // set the value to test
        contestChangeHistory.setTransactionId(1L);
        assertEquals("getTransactionId and setTransactionId failure occured", 1L, (long) contestChangeHistory
                .getTransactionId());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getTimestamp()} and {@link ContestChangeHistory#setTimestamp(Date)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getTimestamp() {
        // set the value to test
        contestChangeHistory.setTimestamp(null);
        assertEquals("getTimestamp and setTimestamp failure occured", null, contestChangeHistory.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setTimestamp(Date)} and {@link ContestChangeHistory#getTimestamp()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is Valid.
     * </p>
     */
    public void test_accuracy_setTimestamp() {
        // set the value to test
        Date date = new Date();
        contestChangeHistory.setTimestamp(date);
        assertEquals("getTimestamp and setTimestamp failure occured", date, contestChangeHistory.getTimestamp());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#isUserAdmin()} and
     * {@link ContestChangeHistory#setUserAdmin(boolean)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is false.
     * </p>
     */
    public void test_accuracy_isUserAdmin() {
        // set the value to test
        contestChangeHistory.setUserAdmin(false);
        assertEquals("isUserAdmin and setUserAdmin failure occured", false, contestChangeHistory.isUserAdmin());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setUserAdmin(boolean)} and
     * {@link ContestChangeHistory#isUserAdmin()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is true.
     * </p>
     */
    public void test_accuracy_setUserAdmin() {
        // set the value to test
        contestChangeHistory.setUserAdmin(true);
        assertEquals("isUserAdmin and setUserAdmin failure occured", true, contestChangeHistory.isUserAdmin());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getUserName()} and {@link ContestChangeHistory#setUserName(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getUserName() {
        // set the value to test
        contestChangeHistory.setUserName(null);
        assertEquals("getUserName and setUserName failure occured", null, contestChangeHistory.getUserName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setUserName(String)} and {@link ContestChangeHistory#getUserName()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setUserName() {
        // set the value to test
        contestChangeHistory.setUserName("test");
        assertEquals("getUserName and setUserName failure occured", "test", contestChangeHistory.getUserName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getOldData()} and {@link ContestChangeHistory#setOldData(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getOldData() {
        // set the value to test
        contestChangeHistory.setOldData(null);
        assertEquals("getOldData and setOldData failure occured", null, contestChangeHistory.getOldData());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setOldData(String)} and {@link ContestChangeHistory#getOldData()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setOldData() {
        // set the value to test
        contestChangeHistory.setOldData("test");
        assertEquals("getOldData and setOldData failure occured", "test", contestChangeHistory.getOldData());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getNewData()} and {@link ContestChangeHistory#setNewData(String)}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getNewData() {
        // set the value to test
        contestChangeHistory.setNewData(null);
        assertEquals("getNewData and setNewData failure occured", null, contestChangeHistory.getNewData());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setNewData(String)} and {@link ContestChangeHistory#getNewData()}
     * method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setNewData() {
        // set the value to test
        contestChangeHistory.setNewData("test");
        assertEquals("getNewData and setNewData failure occured", "test", contestChangeHistory.getNewData());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#getFieldName()} and
     * {@link ContestChangeHistory#setFieldName(String)} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is null.
     * </p>
     */
    public void test_accuracy_getFieldName() {
        // set the value to test
        contestChangeHistory.setFieldName(null);
        assertEquals("getFieldName and setFieldName failure occured", null, contestChangeHistory.getFieldName());
    }

    /**
     * <p>
     * Accuracy test for {@link ContestChangeHistory#setFieldName(String)} and
     * {@link ContestChangeHistory#getFieldName()} method.
     * </p>
     * <p>
     * Sets the value and expects the same while retrieving. Input value is "test".
     * </p>
     */
    public void test_accuracy_setFieldName() {
        // set the value to test
        contestChangeHistory.setFieldName("test");
        assertEquals("getFieldName and setFieldName failure occured", "test", contestChangeHistory.getFieldName());
    }

    /**
     * <p>
     * Persistence tests for the entity <code>{@link ContestChangeHistory}</code>.
     * </p>
     */
    public void test_persistence() {

        try {
            HibernateUtil.getManager().getTransaction().begin();
            ContestChangeHistory entity = new ContestChangeHistory();
            entity.setContestId(1L);
            entity.setFieldName("name");
            entity.setOldData("name1");
            entity.setNewData("name2");
            entity.setTimestamp(new Date());
            entity.setTransactionId(1L);
            entity.setUserName("me");

            // save the entity
            HibernateUtil.getManager().persist(entity);

            // load the persisted object
            Query query = HibernateUtil.getManager().createQuery("from ContestChangeHistory as c");
            ContestChangeHistory persisted = (ContestChangeHistory) query.getResultList().get(0);
            assertEquals("Failed to persist - contest id mismatch", entity.getContestId(), persisted.getContestId());
            assertEquals("Failed to persist - transaction id mismatch", entity.getTransactionId(), persisted
                    .getTransactionId());
            assertEquals("Failed to persist - old data mismatch", entity.getOldData(), persisted.getOldData());
            assertEquals("Failed to persist - new data mismatch", entity.getNewData(), persisted.getNewData());
            assertEquals("Failed to persist - timestamp mismatch", entity.getTimestamp(), persisted.getTimestamp());
            assertEquals("Failed to persist - user name mismatch", entity.getUserName(), persisted.getUserName());
            assertEquals("Failed to persist - field name mismatch", entity.getFieldName(), persisted.getFieldName());

            // update the entity
            entity.setNewData("new description");
            HibernateUtil.getManager().merge(entity);

            persisted = (ContestChangeHistory) query.getResultList().get(0);
            assertEquals("Failed to persist - new data mismatch", entity.getNewData(), persisted.getNewData());

            // delete the entity
            HibernateUtil.getManager().remove(entity);

        } finally {
            HibernateUtil.getManager().getTransaction().commit();
        }
    }
}
