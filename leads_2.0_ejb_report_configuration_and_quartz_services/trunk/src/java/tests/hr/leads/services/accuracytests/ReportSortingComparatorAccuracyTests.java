/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.accuracytests;

import hr.leads.services.ejb.ReportSortingComparator;
import hr.leads.services.model.jpa.EmployeeProfile;

import java.util.Comparator;

import junit.framework.TestCase;

/**
 * <p>
 * This class is the accuracy tests for class <code>ReportSortingComparator</code> .
 * </p>
 *
 * @author TopCoder
 * @version 1.0
 */
public class ReportSortingComparatorAccuracyTests extends TestCase {

    /**
     * <p>
     * Represents the ReportSortingComparator instance for the accuracy test.
     * </p>
     */
    private ReportSortingComparator<EmployeeProfile> comparator;

    /**
     * <p>
     * Represents the columns for sorting.
     * </p>
     */
    private String[] columns = new String[]{"cnum", "countryId", "logonId" };

    /**
     * <p>
     * Represents the employee file for accuracy test.
     * </p>
     */
    private EmployeeProfile profile1;

    /**
     * <p>
     * Represents the employee file for accuracy test.
     * </p>
     */
    private EmployeeProfile profile2;

    /**
     * Sets up the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void setUp() throws Exception {
        comparator = new ReportSortingComparator<EmployeeProfile>(EmployeeProfile.class, columns);
        createProfiles();
    }

    /**
     * Tears down the test environment.
     *
     * @throws Exception if any exception occurred
     */
    public void tearDown() throws Exception {
        comparator = null;
        profile1 = null;
        profile2 = null;
    }

    /**
     * <p>
     * Accuracy test for the constructor.
     * </p>
     */
    public void testCtor() {
        comparator = new ReportSortingComparator<EmployeeProfile>(EmployeeProfile.class, columns);

        assertNotNull(comparator);
        assertNotNull(comparator instanceof Comparator<?>);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy1() {
        int result = comparator.compare(null, profile2);

        assertEquals(1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy2() {
        int result = comparator.compare(profile1, null);

        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy3() {
        int result = comparator.compare(null, null);

        assertEquals(0, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy4() {
        int result = comparator.compare(profile1, profile1);

        assertEquals(0, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy5() {
        profile2.setCnum(profile1.getCnum());
        profile1.setCountryId(3L);

        int result = comparator.compare(profile1, profile2);

        assertEquals(1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy6() {
        profile2.setCnum(profile1.getCnum());
        profile2.setCountryId(profile1.getCountryId());
        profile2.setLogonId(profile1.getLogonId());

        int result = comparator.compare(profile1, profile2);

        assertEquals(0, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy7() {
        profile1.setCnum(null);

        int result = comparator.compare(profile1, profile2);

        assertEquals(1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy8() {
        profile2.setCnum(profile1.getCnum());
        profile2.setCountryId(profile1.getCountryId());
        profile2.setLogonId(null);

        int result = comparator.compare(profile1, profile2);

        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy9() {
        profile2.setCnum(profile1.getCnum());
        profile2.setCountryId(profile1.getCountryId());
        profile1.setLogonId(null);

        int result = comparator.compare(profile1, profile2);

        assertEquals(1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy10() {
        profile1.setCnum(null);
        profile2.setCnum(null);

        int result = comparator.compare(profile1, profile2);

        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.<br>
     * The result should be correct.
     * </p>
     */
    public void testCompareAccuracy11() {
        int result = comparator.compare(profile1, profile2);
        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy12() {
        profile2.setCnum(profile1.getCnum());
        int result = comparator.compare(profile1, profile2);

        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy13() {
        profile1.setCnum("1003");
        int result = comparator.compare(profile1, profile2);

        assertTrue(result > 0);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy14() {
        profile2.setCnum(profile1.getCnum());
        profile2.setCountryId(profile2.getCountryId());

        int result = comparator.compare(profile1, profile2);

        assertEquals(-1, result);
    }

    /**
     * <p>
     * Accuracy test for the method <code>compare()</code>.
     * </p>
     */
    public void testCompareAccuracy15() {
        profile2.setCnum(profile1.getCnum());
        profile2.setCountryId(profile1.getCountryId());
        profile2.setLogonId(profile1.getLogonId());

        int result = comparator.compare(profile1, profile2);

        assertEquals(0, result);
    }

    /**
     * Creates the employee profiles.
     */
    private void createProfiles() {
        profile1 = new EmployeeProfile();
        profile1.setCnum("1001");
        profile1.setCountryId(1L);
        profile1.setLogonId("id_cmu_001");

        profile2 = new EmployeeProfile();
        profile2.setCnum("1002");
        profile2.setCountryId(2L);
        profile2.setLogonId("id_cmu_002");
    }
}
