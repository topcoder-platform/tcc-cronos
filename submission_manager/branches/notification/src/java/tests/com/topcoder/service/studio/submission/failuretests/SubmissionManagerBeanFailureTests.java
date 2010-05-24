/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.service.studio.submission.failuretests;

import java.lang.reflect.Field;
import java.util.Properties;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import com.topcoder.service.studio.submission.BaseTestCase;
import com.topcoder.service.studio.submission.EntityNotFoundException;
import com.topcoder.service.studio.submission.NumberOfSubmissionsExceededException;
import com.topcoder.service.studio.submission.Prize;
import com.topcoder.service.studio.submission.Submission;
import com.topcoder.service.studio.submission.SubmissionManagementConfigurationException;
import com.topcoder.service.studio.submission.SubmissionManagementException;
import com.topcoder.service.studio.submission.SubmissionManager;
import com.topcoder.service.studio.submission.SubmissionManagerBean;
import com.topcoder.service.studio.submission.SubmissionPayment;
import com.topcoder.service.studio.submission.SubmissionReview;

/**
 * Failure test for class {@link SubmissionManagerBean}.
 *
 * @author liuliquan
 * @version 1.2
 */
public class SubmissionManagerBeanFailureTests extends TestCase {

    /**
     * <p>
     * Represents the prefix part sql for insert submission with the rank field.
     * </p>
     *
     * @since 1.2
     */
    private static final String INSERT_SUBMISSION_WITH_RANK_PREFIX = "INSERT INTO submission (submission_id," +
        " submission_status_id, submitter_id, contest_id, create_date, original_file_name, system_file_name," +
        " submission_type_id, mime_type_id, submission_date, height, width, modify_date," +
        " or_submission_id, path_id,rank) VALUES ";

    /**
     * Represents instance for test.
     */
    private SubmissionManager manager;

    /**
     * Sets up test environment.
     *
     * @throws Exception
     *             to JUnit
     */
    protected void setUp() throws Exception {
        Properties env = new Properties();
        env.setProperty(Context.SECURITY_PRINCIPAL, "admin");
        env.setProperty(Context.SECURITY_CREDENTIALS, "password");
        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        InitialContext ctx = new InitialContext(env);
        manager = (SubmissionManager) ctx.lookup("remote/SubmissionManagerBean");
        super.setUp();
    }

    /**
     * Failure test for method initialize(). With unitName not configured,
     * SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_NullUnitName() {
        Object unitName = null;
        Object logger = "myLogger";
        Object entityManager = new MockEntityManager();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With unitName empty string,
     * SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_EmptyName() {
        Object unitName = "\r\n ";
        Object logger = "myLogger";
        Object entityManager = new MockEntityManager();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With unitName not string,
     * SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_UnitNameNotString() {
        Object unitName = new Object();
        Object logger = "myLogger";
        Object entityManager = new MockEntityManager();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With logger not configured,
     * SubmissionManagementConfigurationException should not be thrown.
     */
    public void testInitialize_NullLogger() {
        Object unitName = "submission_manager";
        Object logger = null;
        Object entityManager = new MockEntityManager();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            // can be empty
        } catch (SubmissionManagementConfigurationException e) {
            fail("SubmissionManagementConfigurationException should not be thrown.");
        }
    }

    /**
     * Failure test for method initialize(). With logger not string,
     * SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_LoggerNotString() {
        Object unitName = "submission_manager";
        Object logger = new Object();
        Object entityManager = new MockEntityManager();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With entityManager not configured,
     * SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_NullEntityManager() {
        Object unitName = "submission_manager";
        Object logger = "myLogger";
        Object entityManager = null;
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method initialize(). With entityManager not correct
     * type, SubmissionManagementConfigurationException should be thrown.
     */
    public void testInitialize_EntityManagerInvalidType() {
        Object unitName = "submission_manager";
        Object logger = "myLogger";
        Object entityManager = new Object();
        SessionContext sessionContext = new MockSessionContext(unitName, logger, entityManager);
        SubmissionManagerBean bean = new SubmissionManagerBean();

        setPrivateField(bean, sessionContext);
        try {
            bean.initialize();
            fail("SubmissionManagementConfigurationException should be thrown.");
        } catch (SubmissionManagementConfigurationException e) {
            // expected
        }
    }

    /**
     * Failure test for method updateSubmission(Submission). With null
     * submission, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmission_Null() throws Exception {
        try {
            manager.updateSubmission((Submission) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method updateSubmission(Submission). With submission not
     * exist in database, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmission_NotExist() throws Exception {
        Submission submission = new Submission();
        submission.setSubmissionId(1l);
        submission.setRank(10);
        try {
            manager.updateSubmission(submission);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        }
    }

    /**
     * Failure test for method addPrize(Prize). With null price,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddPrize_Null() throws Exception {
        try {
            manager.addPrize((Prize) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method updatePrize(Prize). With null price,
     * IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdatePrize_Null() throws Exception {
        try {
            manager.updatePrize((Prize) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method addPrizeToSubmission(long, long). Price not
     * found, EntityNotFoundException should be thrown.
     */
    public void testAddPrizeToSubmission() {
        try {
            manager.addPrizeToSubmission(1l, 2l);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method removePrizeFromSubmission(long, long). Price not
     * found, EntityNotFoundException should be thrown.
     */
    public void testRemovePrizeFromSubmission() {
        try {
            manager.removePrizeFromSubmission(1l, 2l);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method addSubmissionPayment(SubmissionPayment). The
     * submission not found, EntityNotFoundException should be thrown.
     */
    public void testAddSubmissionPayment() {
        SubmissionPayment payment = new SubmissionPayment();
        Submission submission = new Submission();
        submission.setRank(1);
        submission.setSubmissionId(1l);
        payment.setSubmission(submission);
        try {
            manager.addSubmissionPayment(payment);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method addSubmissionPayment(SubmissionPayment). With
     * null payment, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddSubmissionPayment_Null() throws Exception {
        try {
            manager.addSubmissionPayment((SubmissionPayment) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method updateSubmissionPayment(SubmissionPayment). The
     * submission not found, EntityNotFoundException should be thrown.
     */
    public void testUpdateSubmissionPayment() {
        SubmissionPayment payment = new SubmissionPayment();
        Submission submission = new Submission();
        submission.setRank(1);
        submission.setSubmissionId(1l);
        payment.setSubmission(submission);
        try {
            manager.addSubmissionPayment(payment);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method updateSubmissionPayment(SubmissionPayment). With
     * null payment, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmissionPayment_Null() throws Exception {
        try {
            manager.updateSubmissionPayment((SubmissionPayment) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method addSubmissionReview(SubmissionReview). The
     * submission not found, EntityNotFoundException should be thrown.
     */
    public void testAddSubmissionReview() {
        SubmissionReview review = new SubmissionReview();
        Submission submission = new Submission();
        submission.setRank(1);
        submission.setSubmissionId(1l);
        review.setSubmission(submission);
        try {
            manager.addSubmissionReview(review);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method addSubmissionReview(SubmissionReview). With null
     * payment, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testAddSubmissionReview_Null() throws Exception {
        try {
            manager.addSubmissionReview((SubmissionReview) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method updateSubmissionReview(SubmissionReview). The
     * submission not found, EntityNotFoundException should be thrown.
     */
    public void testUpdateSubmissionReview() {
        SubmissionReview review = new SubmissionReview();
        Submission submission = new Submission();
        submission.setRank(1);
        submission.setSubmissionId(1l);
        review.setSubmission(submission);
        try {
            manager.addSubmissionReview(review);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Failure test for method updateSubmissionReview(SubmissionReview). With
     * null payment, IllegalArgumentException should be thrown.
     *
     * @throws Exception
     *             to JUnit
     */
    public void testUpdateSubmissionReview_Null() throws Exception {
        try {
            manager.updateSubmissionReview((SubmissionReview) null);
            fail("IllegalArgumentException should be thrown");
        } catch (EJBException e) {
            if (!(e.getCause() instanceof IllegalArgumentException)) {
                fail("IllegalArgumentException should be thrown");
            }
        }
    }

    /**
     * Failure test for method getSubmissionPrizes(long submissionId). The
     * submission not found, EntityNotFoundException should be thrown.
     */
    public void testGetSubmissionPrizes() {
        try {
            manager.getSubmissionPrizes(1l);
            fail("EntityNotFoundException should be thrown");
        } catch (EntityNotFoundException e) {
            // expected
        } catch (SubmissionManagementException e) {
            fail("EntityNotFoundException should be thrown");
        }
    }

    /**
     * Test method <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>.
     * Given submission id is not found.
     * @since 1.2
     * @throws Exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_SubmissionNotFound() throws Exception {

        BaseTestCase.executeScriptFile("/clean.sql");
        BaseTestCase.executeScriptFile("/prepare.sql");

        try {

        	BaseTestCase.executeSQL(new String[] {
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
                });

            try {
                manager.setSubmissionMilestonePrize(Long.MAX_VALUE, 1);
                fail("The submission does not exist");
            } catch (EntityNotFoundException enfe) {
                // excepted
            }
        } finally {
            BaseTestCase.executeScriptFile("/clean.sql");
        }
    }

    /**
     * Test method <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>.
     * Given milestone id is not found.
     * @since 1.2
     * @throws Exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_MilestoneNotFound() throws Exception {

        BaseTestCase.executeScriptFile("/clean.sql");
        BaseTestCase.executeScriptFile("/prepare.sql");

        try {

        	BaseTestCase.executeSQL(new String[] {
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
                });

            try {
                manager.setSubmissionMilestonePrize(1, Long.MAX_VALUE);
                fail("The milestone does not exist");
            } catch (EntityNotFoundException enfe) {
                // excepted
            }
        } finally {
            BaseTestCase.executeScriptFile("/clean.sql");
        }
    }

    /**
     * Test method <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>.
     * Given submission id is not milestone type.
     * @since 1.2
     * @throws Exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_NotMilestoneSubmission() throws Exception {

        BaseTestCase.executeScriptFile("/clean.sql");
        BaseTestCase.executeScriptFile("/prepare.sql");

        try {

        	BaseTestCase.executeSQL(new String[] {
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 1, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
                });

            try {
                manager.setSubmissionMilestonePrize(1, 1);
                fail("The submission 76 does not exist");
            } catch (EJBException ee) {
                assertTrue(ee.getCause() instanceof IllegalArgumentException);
                // excepted
            }
        } finally {
            BaseTestCase.executeScriptFile("/clean.sql");
        }
    }

    /**
     * Test method <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>.
     * The milestone can not associate any more submission.
     * @since 1.2
     * @throws Exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_Exceed() throws Exception {

        BaseTestCase.executeScriptFile("/clean.sql");
        BaseTestCase.executeScriptFile("/prepare.sql");

        try {
        	BaseTestCase.executeSQL(new String[] {
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(2, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)",
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(3, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
                });

            try {
                manager.setSubmissionMilestonePrize(1, 1);
                manager.setSubmissionMilestonePrize(2, 1);
                manager.setSubmissionMilestonePrize(3, 1);
                fail("The max submission nubmer of milestonePrize is 2");
            } catch (NumberOfSubmissionsExceededException ne) {
                // excepted
            }
        } finally {
            BaseTestCase.executeScriptFile("/clean.sql");
        }
    }

    /**
     * Test method <code>{@link SubmissionManagerBean#setSubmissionMilestonePrize(long, long)}</code>.
     * The submission is already associated with milestone.
     * @since 1.2
     * @throws Exception to JUnit.
     */
    public void testSetSubmissionMilestonePrize_AlreadyAssociated() throws Exception {

        BaseTestCase.executeScriptFile("/clean.sql");
        BaseTestCase.executeScriptFile("/prepare.sql");

        try {
        	BaseTestCase.executeSQL(new String[] {
                    INSERT_SUBMISSION_WITH_RANK_PREFIX +
                    "(1, 1, 1, 1, '2008-03-16 09:00:00', 'test.jar', 'test.jar', 2, 1," +
                    " '2008-03-16 09:00:00', 1, 1, '2008-03-16 09:00:00', 1, 1,1)"
                });

            try {
                manager.setSubmissionMilestonePrize(1, 1);
                manager.setSubmissionMilestonePrize(1, 1);
                fail("The submission 1 has been associated with milestonePrize 1");
            } catch (EJBException ie) {
                assertTrue(ie.getCause() instanceof IllegalArgumentException);
                // excepted
            }
        } finally {
            BaseTestCase.executeScriptFile("/clean.sql");
        }
    }

    /**
     * Sets the private field sessionContext of the bean.
     *
     * @param obj
     *            the bean.
     * @param value
     *            the sessionContext
     */
    private void setPrivateField(Object obj, Object value) {
        try {
            Field field = SubmissionManagerBean.class.getDeclaredField("sessionContext");
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        } catch (Exception e) {
            // ignore
        }
    }
}
