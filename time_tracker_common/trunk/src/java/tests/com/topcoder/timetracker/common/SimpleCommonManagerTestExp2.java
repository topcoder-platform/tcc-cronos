/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.idgenerator.IDsExhaustedException;

/**
 * <p>
 * This test case contains failure tests for <code>SimpleCommonManager</code>.
 * </p>
 *
 * <p>
 * Constructors exceptions related to recent days are tested.
 * </p>
 *
 * <p>
 * All public methods are tested.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 3.1
 */
public class SimpleCommonManagerTestExp2 extends BaseTestCase {
    /**
     * <p>
     * Given recentDays is zero, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_ZeroRecentDays() throws Exception {
        try {
            new SimpleCommonManager(this.getDao(), 0);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                   "The recent days must be positive or equal -1, but is '0'") >= 0);
        }
    }

    /**
     * <p>
     * Given recentDays is -5, <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManager_NegativeRecentDays() throws Exception {
        try {
            new SimpleCommonManager(this.getDao(), -5);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().indexOf(
                   "The recent days must be positive or equal -1, but is '-5'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace is missing the recentDays,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_MissRecentDays() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_6");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("Missed property 'recent_days'") >= 0);
        }
    }

    /**
     * <p>
     * Given namespace have an empty recentDays,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_EmptyRecentDays() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_7");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The value for property 'recent_days' is empty") >= 0);
        }
    }

    /**
     * <p>
     * RecentDays configured is -4,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_InvalidRecentDays1() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_8");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getMessage().indexOf("The recent days must be positive or equal -1, but is '-4'") >= 0);
        }
    }

    /**
     * <p>
     * RecentDays configured is 9999999999999999 which is greater than <code>Integer.MAX_VALUE</code>,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_InvalidRecentDays2() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_9");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
            assertTrue(e.getMessage().indexOf("The recent days configured is not valid number format") >= 0);
        }
    }

    /**
     * <p>
     * RecentDays configured is 1234abcd,
     * <code>CommonManagerConfigurationException</code> is expected.
     * </p>
     */
    public void testSimpleCommonManager_Ctor_InvalidRecentDays3() {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_10");
            fail("CommonManagerConfigurationException is expected");
        } catch (CommonManagerConfigurationException e) {
            assertTrue(e.getCause() instanceof NumberFormatException);
            assertTrue(e.getMessage().indexOf("The recent days configured is not valid number format") >= 0);
        }
    }

    /**
     * <p>
     * The id sequence has been exhausted,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_IDGeneratorExhausted1() throws Exception {
        this.setupDB();
        Connection con = null;
        Statement st = null;
        try {
            con = this.getConnection();
            st = con.createStatement();
            st.executeUpdate("update id_sequences set exhausted = 1 where name = 'PaymentTermGenerator'");
            con.commit();
            this.getManager().addPaymentTerm(this.getPaymentTermWithOutId());
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof IDsExhaustedException);
        } finally {
            this.closeStatement(st);
            this.closeConnection(con);
        }
    }

    /**
     * <p>
     * The id sequence has been exhausted,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_IDGeneratorExhausted2() throws Exception {
        this.setupDB();
        this.updateIDGenerator(Long.MAX_VALUE);
        try {
            this.getManager().addPaymentTerm(this.getPaymentTermWithOutId());
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof IDsExhaustedException);
        }
    }

    /**
     * <p>
     * The id returned by <code>IDGenerator</code> is not positive,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_NonPositiveIDGenerated() throws Exception {
        this.setupDB();
        this.updateIDGenerator(-1);
        try {
            this.getManager().addPaymentTerm(this.getPaymentTermWithOutId());
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage().indexOf("The id '-1' returned by IDGenerator is not positive") >= 0);
        }
    }
    /**
     * <p>
     * The <code>PaymentTerm</code> to be added already exist,
     * <code>DuplicatePaymentTermException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_Duplicate() throws Exception {
        try {
            //Note: we have set next_block_start = -1 in previous method and IDGenerator generate -1.
            IDGeneratorFactory.getIDGenerator("PaymentTermGenerator").getNextID(); //Will generate 0
            this.getManager().addPaymentTerm(this.getPaymentTermWithOutId()); //Will generate 1
            fail("DuplicatePaymentTermException is expected");
        } catch (DuplicatePaymentTermException e) {
            assertTrue(e.getMessage().indexOf("There has a PaymentTerm with id '1' already been added") >= 0);
        } finally {
            //Note: the block_size is 3, so the IDGenerator will query DB again at next time when the next_block_start
            //has been reset as 11
            this.updateIDGenerator(11);
        }
    }
    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13")
                .addPaymentTerm(this.getPaymentTermWithOutId()); //Will generate 11
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14")
                .addPaymentTerm(this.getPaymentTermWithOutId()); //Will generate 12
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        } finally {
            IDGeneratorFactory.getIDGenerator("PaymentTermGenerator").getNextID(); //Will generate 13
            //Note: the block_size is 3, so the IDGenerator will query DB again at next time when the next_block_start
            //has been reset as 11
            this.updateIDGenerator(11);
        }
    }
    /**
     * <p>
     * The <code>PaymentTerm</code> to be added is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_Null() throws Exception {
        try {
            this.getManager().addPaymentTerm(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("PaymentTerm to be added should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be added is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_NullCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser(null);
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be added should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be added is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_EmptyCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser(" ");
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be added should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be added is length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_ExceedCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setCreationUser(this.getStringWithLength65());
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be added should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Modification user of <code>PaymentTerm</code> to be added is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_EmptyModificationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setModificationUser(" ");
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The modification user of PaymentTerm to be added should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Modification user of <code>PaymentTerm</code> to be added is with length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_ExceedModificationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setModificationUser(this.getStringWithLength65());
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The modification user of PaymentTerm to be added should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be added is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_NullDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription(null);
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be added should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be added is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_EmptyDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription(" ");
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be added should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be added is with length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_ExceedDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setDescription(this.getStringWithLength65());
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be added should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Term of <code>PaymentTerm</code> to be added is non-positive,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm_NonPositiveTerm() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithOutId();
            paymentTerm.setTerm(Integer.MIN_VALUE);
            this.getManager().addPaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The term of PaymentTerm to be added should be positive") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13")
                .updatePaymentTerm(this.getPaymentTermWithId(1));
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14")
                .updatePaymentTerm(this.getPaymentTermWithId(1));
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * The <code>PaymentTerm</code> to be updated does not exist in data store,
     * <code>PaymentTermNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NotExist() throws Exception {
        try {
            this.getManager().updatePaymentTerm(this.getPaymentTermWithId(11));
            fail("PaymentTermNotFoundException is expected");
        } catch (PaymentTermNotFoundException e) {
            assertTrue(e.getMessage()
                    .indexOf("There does not exist a PaymentTerm with id '11'") >= 0);
        }
    }
    /**
     * <p>
     * The <code>PaymentTerm</code> to be updated is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_Null() throws Exception {
        try {
            this.getManager().updatePaymentTerm(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("PaymentTerm to be updated should not be null") >= 0);
        }
    }

    /**
     * <p>
     * The <code>PaymentTerm</code> to be updated has non-positive id,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NotPositiveId() throws Exception {
        try {
            this.getManager().updatePaymentTerm(this.getPaymentTermWithId(-3));
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to be updated should be positive, but is '-3'") >= 0);
        }
    }

    /**
     * <p>
     * Creation date of <code>PaymentTerm</code> to be updated is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NullCreationDate() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setCreationDate(null);
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation date of PaymentTerm to be updated should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be updated is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NullCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setCreationUser(null);
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be updated should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be updated is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_EmptyCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setCreationUser(" ");
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be updated should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Creation user of <code>PaymentTerm</code> to be updated is length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_ExceedCreationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setCreationUser(this.getStringWithLength65());
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation user of PaymentTerm to be updated should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Creation date exceeds current date,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_CreationDateExceedsCurrentDate() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setCreationDate(new Date(new Date().getTime() + ONEDAY));
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The creation date of PaymentTerm to be updated must not exceed current date") >= 0);
        }
    }

    /**
     * <p>
     * Modification user of <code>PaymentTerm</code> to be updated is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NullModificationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setModificationUser(null);
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The modification user of PaymentTerm to be updated should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Modification user of <code>PaymentTerm</code> to be updated is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_EmptyModificationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setModificationUser(" ");
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The modification user of PaymentTerm to be updated should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Modification user of <code>PaymentTerm</code> to be updated is with length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_ExceedModificationUser() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setModificationUser(this.getStringWithLength65());
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The modification user of PaymentTerm to be updated should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be updated is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NullDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setDescription(null);
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be updated should not be null") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be updated is empty,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_EmptyDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setDescription(" ");
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be updated should not be empty") >= 0);
        }
    }

    /**
     * <p>
     * Description of <code>PaymentTerm</code> to be updated is with length exceed 64,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_ExceedDescription() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setDescription(this.getStringWithLength65());
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The description of PaymentTerm to be updated should"
                            + " not be with length greater than 64") >= 0);
        }
    }

    /**
     * <p>
     * Term of <code>PaymentTerm</code> to be updated is non-positive,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_NonPositiveTerm() throws Exception {
        try {
            PaymentTerm paymentTerm = this.getPaymentTermWithId(1);
            paymentTerm.setTerm(Integer.MIN_VALUE);
            this.getManager().updatePaymentTerm(paymentTerm);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The term of PaymentTerm to be updated should be positive") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerm_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").deletePaymentTerm(1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerm_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").deletePaymentTerm(1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * <code>PaymentTerm</code> with id 16 does not exist in the data store,
     * <code>PaymentTermNotFoundException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerm_NotExist() throws Exception {
        try {
            this.getManager().deletePaymentTerm(16);
            fail("PaymentTermNotFoundException is expected");
        } catch (PaymentTermNotFoundException e) {
            assertTrue(e.getMessage()
                    .indexOf("There does not exist a PaymentTerm with id '16'") >= 0);
        }
    }

    /**
     * <p>
     * <code>PaymentTerm</code> with id 16 does not exist in the data store,
     * <code>PaymentTermNotFoundException</code> is expected.
     * And the connection should be rolled back.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_NotExist() throws Exception {
        try {
            this.getManager().deletePaymentTerms(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
            fail("PaymentTermNotFoundException is expected");
        } catch (PaymentTermNotFoundException e) {
            assertTrue(e.getMessage()
                    .indexOf("There does not exist a PaymentTerm with id '11'") >= 0);
            //Connection should be rolled back, that is, no entry should be deleted
            PaymentTerm[] paymentTerms = this.getManager().retrieveAllPaymentTerms();
            assertEquals("The length of paymentTerms should be 10", 10, paymentTerms.length);
            for (int i = 1; i <= 10; i++) {
                this.assertPaymentTerm(paymentTerms[i - 1], i);
            }
        }
    }

    /**
     * <p>
     * Delete a <code>PaymentTerm</code> with non-positive id -1,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerm_NonPositiveId() throws Exception {
        try {
            this.getManager().deletePaymentTerm(-1);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to be deleted should be positive, but is '-1'") >= 0);
        }
    }

    /**
     * <p>
     * The ids array to be deleted is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_NullIdsArray() throws Exception {
        try {
            this.getManager().deletePaymentTerms(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to be deleted should not be null") >= 0);
        }
    }

    /**
     * <p>
     * The ids array to be deleted contains non-positive value,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_IdsArrayContainsNonPositive() throws Exception {
        try {
            this.getManager().deletePaymentTerms(new long[]{34, 45, 6, -43});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to be deleted should be positive, but is '-43'") >= 0);
        }
    }

    /**
     * <p>
     * Delete an existing <code>PaymentTerm</code> twice,
     * <code>PaymentTermNotFoundException</code> is expected.
     * And the connection should be rolled back.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_Twice() throws Exception {
        try {
            this.getManager().deletePaymentTerms(new long[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10});
            fail("PaymentTermNotFoundException is expected");
        } catch (PaymentTermNotFoundException e) {
            assertTrue(e.getMessage()
                    .indexOf("Cannot delete twice PaymentTerm with id '10'") >= 0);
            //Connection should be rolled back, that is, no entry should be deleted
            PaymentTerm[] paymentTerms = this.getManager().retrieveAllPaymentTerms();
            assertEquals("The length of paymentTerms should be 10", 10, paymentTerms.length);
            for (int i = 1; i <= 10; i++) {
                this.assertPaymentTerm(paymentTerms[i - 1], i);
            }
        }
    }
    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").deletePaymentTerms(new long[]{1, 2});
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").deletePaymentTerms(new long[]{1, 2});
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAllPaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").deleteAllPaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAllPaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").deleteAllPaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerm_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrievePaymentTerm(1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerm_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrievePaymentTerm(1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrievePaymentTerms(new long[]{1, 2});
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrievePaymentTerms(new long[]{1, 2});
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }

    /**
     * <p>
     * The ids array to be retrieved is null,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms_NullIdsArray() throws Exception {
        try {
            this.getManager().retrievePaymentTerms(null);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to retrieve should not be null") >= 0);
        }
    }

    /**
     * <p>
     * The ids array to be retrieved contains non-positive value,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms_IdsArrayContainsNonPositive() throws Exception {
        try {
            this.getManager().retrievePaymentTerms(new long[]{34, 45, 6, -43});
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The id of PaymentTerm to retrieve should be positive, but is '-43'") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllPaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrieveAllPaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllPaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrieveAllPaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveActivePaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrieveActivePaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveActivePaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrieveActivePaymentTerms();
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrieveRecentlyCreatedPaymentTerms(-1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrieveRecentlyCreatedPaymentTerms(-1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * Recent days is -2,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms_ErrorRecentDays() throws Exception {
        try {
            this.getManager().retrieveRecentlyCreatedPaymentTerms(-2);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The recent days must be positive or equal -1") >= 0);
        }
    }

    /**
     * <p>
     * Connection url points to a non-exist database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyModifiedPaymentTerms_NotExistDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_13").retrieveRecentlyModifiedPaymentTerms(-1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getMessage()
                    .indexOf("DBConnectionException occurs when creating the database connection") >= 0);
        }
    }
    /**
     * <p>
     * Connection url points to an empty database,
     * <code>PaymentTermDAOException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyModifiedPaymentTerms_EmptyDatabase() throws Exception {
        try {
            new SimpleCommonManager("SimpleCommonManager_Error_14").retrieveRecentlyModifiedPaymentTerms(-1);
            fail("PaymentTermDAOException is expected");
        } catch (PaymentTermDAOException e) {
            assertTrue(e.getCause() instanceof SQLException);
        }
    }
    /**
     * <p>
     * Recent days is -2,
     * <code>IllegalArgumentException</code> is expected.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyModifiedPaymentTerms_ErrorRecentDays() throws Exception {
        try {
            this.getManager().retrieveRecentlyModifiedPaymentTerms(-2);
            fail("IllegalArgumentException is expected");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage()
                    .indexOf("The recent days must be positive or equal -1") >= 0);
        }
    }
}
