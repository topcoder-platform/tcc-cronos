/*
 * Copyright (C) 2010 TopCoder Inc., All Rights Reserved.
 */
package hr.leads.services.ejb;

import hr.leads.services.model.jpa.BusinessUnit;
import hr.leads.services.model.jpa.GeneralQuestionResponse;
import junit.framework.TestCase;

/**
 * <p>
 * Dummy class, use only for testing ReportSortingComparator class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
class ReportDummyType {
    /**
     * <p>
     * Represents the id.
     * </p>
     */
    private Long id;

    /**
     * <p>
     * Represents the primitive id.
     * </p>
     */
    private long primitiveId;

    /**
     * <p>
     * Represents the businessUnit.
     * </p>
     */
    private BusinessUnit businessUnit;

    /**
     * <p>
     * Represents the general question response.
     * </p>
     */
    private GeneralQuestionResponse generalQuestionResponse;

    /**
     *<p>
     *Creates the instance.
     *</p>
     */
    ReportDummyType() {
        // do nothing
    }

    /**
     * <p>
     * Gets the id.
     * <p>
     * @return the id
     */
    Long getId() {
        return id;
    }

    /**
     * <p>
     * Sets the id.
     * </p>
     * @param id the id to set
     */
    void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Gets the primitive id.
     * </p>
     * @return the primitiveId
     */
    long getPrimitiveId() {
        return primitiveId;
    }

    /**
     * <p>
     * Sets the primitive id.
     * </p>
     * @param primitiveId the primitiveId to set
     */
    void setPrimitiveId(long primitiveId) {
        this.primitiveId = primitiveId;
    }

    /**
     * <p>
     * Sets the business unit.
     * </p>
     * @param businessUnit the businessUnit to set
     */
    public void setBusinessUnit(BusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    /**
     * <p>
     * Gets the business unit.
     * </p>
     *
     * @return the businessUnit
     */
    public BusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    /**
     * <p>
     * Sets the generalQuestionResponse.
     * </p>
     * @param generalQuestionResponse the generalQuestionResponse to set
     */
    public void setGeneralQuestionResponse(GeneralQuestionResponse generalQuestionResponse) {
        this.generalQuestionResponse = generalQuestionResponse;
    }

    /**
     * <p>
     * Gets the generalQuestionResponse.
     * </p>
     * @return the generalQuestionResponse
     */
    public GeneralQuestionResponse getGeneralQuestionResponse() {
        return generalQuestionResponse;
    }


}

/**
 * <p>
 * All tests for <code>ReportSortingComparator</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class ReportSortingComparatorTest extends TestCase {

    /**
     * <p>
     * Sets up the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /**
     * <p>
     * Tears down the test environment.
     * </p>
     *
     * @throws Exception to JUnit.
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * IllegalArgumentException should be thrown if the object type is null.
     * </p>
     */
    public void testReportSortingComparatorNullObjecType() {
        try {
            new ReportSortingComparator<Long>(null, new String[] {"something"});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * IllegalArgumentException should be thrown if the SortColumns is null.
     * </p>
     */
    public void testReportSortingComparatorNullSortColumns() {
        try {
            new ReportSortingComparator<Long>(Long.class, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * IllegalArgumentException should be thrown if the SortColumns is empty.
     * </p>
     */
    public void testReportSortingComparatorEmptySortColumns() {
        try {
            new ReportSortingComparator<Long>(Long.class, new String[] {});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * IllegalArgumentException should be thrown if the SortColumns contains null empty.
     * </p>
     */
    public void testReportSortingComparatorSortColumnsContainsNull() {
        try {
            new ReportSortingComparator<Long>(Long.class, new String[] {null});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * IllegalArgumentException should be thrown if the SortColumns contains empty.
     * </p>
     */
    public void testReportSortingComparatorSortColumnsContainsEmpty() {
        try {
            new ReportSortingComparator<Long>(Long.class, new String[] {"  "});
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // success
        }
    }


    /**
     * <p>
     * Tests constructor: {@link ReportSortingComparator#ReportSortingComparator(Class, String[])}.
     * </p>
     *
     * <p>
     * Accuracy test check if the instance successfully created.
     * </p>
     */
    public void testReportSortingComparator() {
        try {
            ReportSortingComparator<Long> instance = new ReportSortingComparator<Long>(
                    Long.class, new String[] {"something"});
            assertNotNull("the object cannot be null.", instance);

        } catch (IllegalArgumentException e) {
            fail("IllegalArgumentException should be thrown.");
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If both objects are null, should be equal.
     * </p>
     */
    public void testCompareNullCompare() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        int ret = comparator.compare(null, null);
        assertEquals("should be equal.", 0, ret);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If first object is null, should return positive.
     * </p>
     */
    public void testCompareNullCompareOneNull() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj2 = new ReportDummyType();
        obj2.setId(new Long(123));
        int ret = comparator.compare(null, obj2);
        assertTrue("should > 0.", ret > 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If second object is null, should return negative.
     * </p>
     */
    public void testCompareNullCompareSecondNull() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj2 = new ReportDummyType();
        obj2.setId(new Long(123));
        int ret = comparator.compare(obj2, null);
        assertTrue("should < 0.", ret < 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is Long, and return negative.
     * </p>
     */
    public void testCompareNullCompareLongTypeLess() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setId(new Long(12));
        obj2.setId(new Long(123));
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should < 0.", ret < 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is Long, and return 0.
     * </p>
     */
    public void testCompareNullCompareLongTypeEqual() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setId(new Long(123));
        obj2.setId(new Long(123));
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should == 0.", ret == 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is Long, and return positive.
     * </p>
     */
    public void testCompareNullCompareLongTypeLarger() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setId(new Long(123));
        obj2.setId(new Long(12));
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should > 0.", ret > 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is Long, and return negative.
     * </p>
     */
    public void testCompareNullComparePrimitiveLongTypeLess() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"primitiveId"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setPrimitiveId(12);
        obj2.setPrimitiveId(123);
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should < 0.", ret < 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is Long, and return 0.
     * </p>
     */
    public void testCompareNullComparePrimitiveLongTypeEqual() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"primitiveId"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setPrimitiveId(123);
        obj2.setPrimitiveId(123);
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should == 0.", ret == 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field type is long, and return positive.
     * </p>
     */
    public void testCompareNullComparePrimitiveLongTypeLarger() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"primitiveId"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setPrimitiveId(123);
        obj2.setPrimitiveId(12);
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should > 0.", ret > 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * If field not found, IllegalStateException should be thrown.
     * </p>
     */
    public void testCompareFieldNotFound() {
        try {
            ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                    ReportDummyType.class, new String[] {"notAfield"});
            ReportDummyType obj1 = new ReportDummyType();
            ReportDummyType obj2 = new ReportDummyType();
            obj1.setPrimitiveId(123);
            obj2.setPrimitiveId(12);
            comparator.compare(obj1, obj2);
            fail("IllegalStateException should be thrown.");
        } catch (IllegalStateException e) {
            // success
        }
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * Accuracy tests for null value.
     * </p>
     */
    public void testCompareNullValue() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"id"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        obj1.setId(null);
        obj2.setId(null);
        int ret = comparator.compare(obj1, obj2);

        assertTrue("should == 0.", ret == 0);

        obj1.setId(null);
        obj2.setId(new Long(123));
        ret = comparator.compare(obj1, obj2);

        assertTrue("should > 0.", ret > 0);

        obj1.setId(new Long(123));
        obj2.setId(null);
        ret = comparator.compare(obj1, obj2);

        assertTrue("should < 0.", ret < 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * Accuracy tests for named entity value.
     * </p>
     */
    public void testCompareNamedEntity() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"businessUnit"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();
        BusinessUnit businessUnit1 = new BusinessUnit();
        BusinessUnit businessUnit2 = new BusinessUnit();
        businessUnit1.setName("100");
        businessUnit2.setName("11");
        obj1.setBusinessUnit(businessUnit1);
        obj2.setBusinessUnit(businessUnit2);
        int ret = comparator.compare(obj1, obj2);
        assertTrue("should < 0.", ret < 0);
    }

    /**
     * <p>
     * Tests method: {@link ReportSortingComparator#compare(Object, Object)}.
     * </p>
     *
     * <p>
     * Accuracy tests for identifiable entity.
     * </p>
     */
    public void testCompareIdentifiableEntity() {
        ReportSortingComparator<ReportDummyType> comparator = new ReportSortingComparator<ReportDummyType>(
                ReportDummyType.class, new String[] {"generalQuestionResponse"});
        ReportDummyType obj1 = new ReportDummyType();
        ReportDummyType obj2 = new ReportDummyType();

        GeneralQuestionResponse generalQuestionResponse1 = new GeneralQuestionResponse();
        generalQuestionResponse1.setId(100);
        obj1.setGeneralQuestionResponse(generalQuestionResponse1);
        GeneralQuestionResponse generalQuestionResponse2 = new GeneralQuestionResponse();
        obj2.setGeneralQuestionResponse(generalQuestionResponse2);
        generalQuestionResponse2.setId(11);
        int ret = comparator.compare(obj1, obj2);
        assertTrue("should > 0.", ret > 0);
    }

}
