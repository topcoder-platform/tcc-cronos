/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.util.Date;

/**
 * <p>
 * Accuracy tests for <code>TimeTrackerBean</code>.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
  */
public class TimeTrackerBeanTestAcc extends BaseBaseTestCase {
    /**
     * <p>
     * The id of bean defaults to -1. The hash code of two fresh beans should equal.
     * </p>
     */
    public void testHashCode1() {
        assertEquals("", new DummyTimeTrackerBean().hashCode(), this.getTimeTrackerBean().hashCode());
    }
    /**
     * <p>
     * One bean's id is -1, another is 1. The hash code should not equal.
     * </p>
     */
    public void testHashCode2() {
        TimeTrackerBean bean = new DummyTimeTrackerBean();
        bean.setId(1);
        assertTrue("The hash code should not equal", bean.hashCode() != this.getTimeTrackerBean().hashCode());
    }
    /**
     * <p>
     * Test constructor.
     * </p>
     */
    public void testTimeTrackerBean() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertNotNull("The bean should be instantiated", bean);
        assertNull("Creation date is initially null", bean.getCreationDate());
        assertNull("Modification date is initially null", bean.getModificationDate());
        assertNull("Creation user is initially null", bean.getCreationUser());
        assertNull("Modification user is initially null", bean.getModificationUser());
        assertEquals("The id is initially -1", -1 , bean.getId());
        assertFalse("The changed status is initially false", bean.isChanged());
    }

    /**
     * <p>
     * Assert methods related to creation date.
     * </p>
     */
    private void assertCreationDate() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertNull("Creation date is initially null", bean.getCreationDate());

        bean.setCreationDate(null);
        assertNull("Creation date should be set as null", bean.getCreationDate());

        Date current = new Date();
        bean.setCreationDate(current);

        Date creationDate = bean.getCreationDate();

        assertEquals("The creation date should be set as current", current, creationDate);

        current.setTime(current.getTime() - ONEDAY);

        assertEquals("The creation date should not change", creationDate, bean.getCreationDate());

        bean.getCreationDate().setTime(current.getTime());

        assertEquals("The creation date should not change", creationDate, bean.getCreationDate());
    }
    /**
     * <p>
     * Test method <code>getCreationDate()</code>.
     * </p>
     */
    public void testGetCreationDate() {
        this.assertCreationDate();
    }

    /**
     * <p>
     * Test method <code>setCreationDate()</code>.
     * </p>
     */
    public void testSetCreationDate() {
        this.assertCreationDate();
    }
    /**
     * <p>
     * Assert methods related to modification date.
     * </p>
     */
    private void assertModificationDate() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertNull("Modification date is initially null", bean.getModificationDate());

        bean.setModificationDate(null);
        assertNull("Modification date should be set as null", bean.getModificationDate());

        Date current = new Date();
        bean.setModificationDate(current);

        Date modificationDate = bean.getModificationDate();

        assertEquals("The modification date should be set as current", current, modificationDate);

        current.setTime(current.getTime() - ONEDAY);

        assertEquals("The modification date should not change", modificationDate, bean.getModificationDate());

        bean.getModificationDate().setTime(current.getTime());

        assertEquals("The modification date should not change", modificationDate, bean.getModificationDate());
    }
    /**
     * <p>
     * Test method <code>getModificationDate()</code>.
     * </p>
     */
    public void testGetModificationDate() {
        this.assertModificationDate();
    }

    /**
     * <p>
     * Test method <code>setModificationDate()</code>.
     * </p>
     */
    public void testSetModificationDate() {
        this.assertModificationDate();
    }
    /**
     * <p>
     * Assert methods related to creation user.
     * </p>
     */
    private void assertCreationUser() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertNull("Creation user is initially null", bean.getCreationUser());

        bean.setCreationUser(null);
        assertNull("Creation user should be set as null", bean.getCreationUser());

        bean.setCreationUser(" ");
        assertEquals("Creation user should be set as empty", " ", bean.getCreationUser());

        bean.setCreationUser(this.getStringWithLength65());
        assertEquals("Creation user should be set as the string with length 65", 65, bean.getCreationUser().length());

        bean.setCreationUser("creationUser");
        assertEquals("Creation user should be set as 'creationUser'", "creationUser", bean.getCreationUser());

    }
    /**
     * <p>
     * Test method <code>getCreationUser()</code>.
     * </p>
     */
    public void testGetCreationUser() {
        this.assertCreationUser();
    }

    /**
     * <p>
     * Test method <code>setCreationUser()</code>.
     * </p>
     */
    public void testSetCreationUser() {
        this.assertCreationUser();
    }
    /**
     * <p>
     * Assert methods related to modification user.
     * </p>
     */
    private void assertModificationUser() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertNull("Modification user is initially null", bean.getModificationUser());

        bean.setModificationUser(null);
        assertNull("Modification user should be set as null", bean.getModificationUser());

        bean.setModificationUser(" ");
        assertEquals("Modification user should be set as empty", " ", bean.getModificationUser());

        bean.setModificationUser(this.getStringWithLength65());
        assertEquals("Modification user should be set as the string with length 65",
            65, bean.getModificationUser().length());

        bean.setModificationUser("modificationUser");
        assertEquals("Modification user should be set as 'modificationUser'",
            "modificationUser", bean.getModificationUser());

    }
    /**
     * <p>
     * Test method <code>getModificationUser()</code>.
     * </p>
     */
    public void testGetModificationUser() {
        this.assertModificationUser();
    }

    /**
     * <p>
     * Test method <code>setModificationUser()</code>.
     * </p>
     */
    public void testSetModificationUser() {
        this.assertModificationUser();
    }

    /**
     * <p>
     * Assert the methods related to id.
     * </p>
     */
    private void assertId() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertEquals("The id is initially -1", -1 , bean.getId());

        bean.setId(Long.MAX_VALUE);
        assertEquals("The id should be set as " + Long.MAX_VALUE, Long.MAX_VALUE , bean.getId());

        bean.setId(Long.MIN_VALUE);
        assertEquals("The id should be set as " + Long.MIN_VALUE, Long.MIN_VALUE , bean.getId());
    }
    /**
     * <p>
     * Test method <code>getId()</code>.
     * </p>
     */
    public void testGetId() {
        this.assertId();
    }

    /**
     * <p>
     * Test method <code>setId()</code>.
     * </p>
     */
    public void testSetId() {
        this.assertId();
    }

    /**
     * <p>
     * Assert the methods related to changed.
     * </p>
     */
    private void assertChanged() {
        TimeTrackerBean bean = this.getTimeTrackerBean();
        assertFalse("The changed status is initially false", bean.isChanged());

        bean.setChanged(true);
        assertTrue("The changed status should be set as true", bean.isChanged());
    }
    /**
     * <p>
     * Test method <code>isChanged()</code>.
     * </p>
     */
    public void testIsChanged() {
        this.assertChanged();
    }

    /**
     * <p>
     * Test method <code>setChanged()</code>.
     * </p>
     */
    public void testSetChanged() {
        this.assertChanged();
    }

    /**
     * <p>
     * Test method <code>equals()</code>.
     * </p>
     */
    public void testEqualsObject() {
        assertFalse("Should not equal null", this.getTimeTrackerBean().equals(null));
        assertFalse("Should not equal a string object", this.getTimeTrackerBean().equals("string"));

        TimeTrackerBean bean1 = this.getTimeTrackerBean();
        TimeTrackerBean bean2 = new DummyTimeTrackerBean();
        assertTrue("The fresh bean should equal", bean1.equals(bean1));
        assertTrue("The fresh bean should equal", bean1.equals(bean2));

        bean1.setId(1);
        bean2.setId(2);
        assertFalse("The bean with id 1 should not equal bean with id 2", bean1.equals(bean2));
        assertFalse("The bean with id 2 should not equal bean with id 1", bean2.equals(bean1));

        PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
        assertTrue("The beans with same id 1 should equal", bean1.equals(paymentTerm));
        assertTrue("The beans with same id 1 should equal", paymentTerm.equals(bean1));
    }

}
