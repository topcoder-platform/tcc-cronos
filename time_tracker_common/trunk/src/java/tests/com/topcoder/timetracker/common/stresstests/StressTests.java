/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Iterator;

import junit.framework.TestCase;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;
import com.topcoder.util.config.ConfigManager;

/**
 * <p>
 * This is the stress test for <code>DatabasePaymentTermDAO</code>. Since <code>SimpleCommonManager</code>
 * will delegate most of the method calls to this class in current implementation, no stress test is performed
 * on <code>SimpleCommonManager</code>.
 * </p>
 *
 * @author restarter
 * @version 3.1
 */
public class StressTests extends TestCase {

    /**
     * <p>
     * Times to run the cases.
     * </p>
     */
    private static final int TIMES = 10;

    /**
     * <p>
     * The PaymentTerm instance created for stress test.
     * </p>
     */
    private PaymentTerm paymentTerm = new PaymentTerm();

    /**
     * <p>
     * DatabasePaymentTermDAO instance created for stress test.
     * </p>
     */
    private DatabasePaymentTermDAO dao;

    /**
     * <p>
     * The DBConnectionFactory instance for stress test.
     * </p>
     */
    private DBConnectionFactory connectionFactory;

    /**
     * <p>
     * Loads the configurations used in the stress tests.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void setUp() throws Exception {
        Date date = new Date();
        paymentTerm.setCreationUser("creator");
        paymentTerm.setModificationUser("modifier");
        paymentTerm.setCreationDate(date);
        paymentTerm.setModificationDate(date);
        paymentTerm.setTerm(10);
        paymentTerm.setDescription("description");
        paymentTerm.setActive(true);
        ConfigManager cm = ConfigManager.getInstance();
        cm.add("stresstests/stresstests.xml");
        cm.add("DBConnectionFactory.xml");
        connectionFactory =
            new DBConnectionFactoryImpl("com.topcoder.db.connectionfactory.DBConnectionFactoryImpl");
        initDB();

        dao =
            new DatabasePaymentTermDAO("com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO");

    }

    /**
     * <p>
     * unLoads all the namespaces.
     * </p>
     *
     * @throws Exception to JUnit
     */
    protected void tearDown() throws Exception {
        ConfigManager cm = ConfigManager.getInstance();

        for (Iterator it = cm.getAllNamespaces(); it.hasNext();) {
            cm.removeNamespace((String) it.next());
        }
        cleanDB();
    }

    /**
     * <p>
     * Stress test for <code>addPaymentTerm(PaymentTerm)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm() throws Exception {
        long current = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        System.out.println("Run addPaymentTerm(PaymentTerm) for " + TIMES + " times in "
            + (System.currentTimeMillis() - current) + " ms.");

        // retrieve all the PaymentTerms and check the field
        PaymentTerm[] payments = dao.retrieveAllPaymentTerms();
        boolean indices[] = new boolean[TIMES + 1];

        // the ids from 1~TIMES should occur
        for (int i = 0; i < TIMES; i++) {
            indices[(int) payments[i].getId()] = true;
        }
        int last;
        for (last = 1; last <= TIMES; last++) {
            if (!indices[last]) {
                break;
            }
        }
        assertEquals("all the ids from 1~" + TIMES + " should occur.", last, TIMES + 1);

    }

    /**
     * <p>
     * Stress test for <code>updatePaymentTerm(PaymentTerm)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        PaymentTerm[] payments = dao.retrieveAllPaymentTerms();

        // the ids from 1~TIMES should occur
        for (int i = 0; i < TIMES; i++) {
            payments[i].setActive(true);
            payments[i].setCreationUser("creationUser" + i);
            payments[i].setModificationUser("modificationUser" + i);
            payments[i].setDescription("description" + i);
            payments[i].setModificationDate(new Date());
        }

        long current = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            dao.updatePaymentTerm(payments[i]);
        }

        System.out.println("Run updatePaymentTerm(PaymentTerm) for " + TIMES + " times in "
            + (System.currentTimeMillis() - current) + " ms.");

        // retrieve all the PaymentTerms and check the field
        PaymentTerm[] paymentsUpdated = dao.retrieveAllPaymentTerms();

        // the ids from 1~TIMES should occur
        for (int i = 0; i < TIMES; i++) {
            assertEquals("the Id retrieved is not corrected.", payments[i], paymentsUpdated[i]);
            assertTrue("the active retrieved is not corrected.", payments[i].isActive());
            assertEquals("the creationUser retrieved is not corrected.", payments[i].getCreationUser(),
                paymentsUpdated[i].getCreationUser());
            assertEquals("the modificationUser retrieved is not corrected.", payments[i]
                .getModificationUser(), paymentsUpdated[i].getModificationUser());
            assertEquals("the description retrieved is not corrected.", payments[i].getDescription(),
                paymentsUpdated[i].getDescription());
        }
    }

    /**
     * <p>
     * Stress test for <code>updatePaymentTerm(PaymentTerm)</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerm() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int i = 0; i < TIMES; i++) {
            PaymentTerm payment = dao.retrievePaymentTerm(i + 1);
            assertEquals("the Id retrieved is not corrected.", i + 1, payment.getId());
            assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                payment.getCreationUser());
            assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                .getModificationUser(), payment.getModificationUser());
            assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(), payment
                .getDescription());
        }

        System.out.println("Run retrievePaymentTerm(long) for " + TIMES + " times in "
            + (System.currentTimeMillis() - current) + " ms.");

    }

    /**
     * <p>
     * Stress test for <code>retrievePaymentTerms(long[])</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms() throws Exception {
        long[] id1 = new long[TIMES / 2];
        long[] id2 = new long[TIMES / 2];
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        for (int i = 0; i < TIMES / 2; i++) {
            id1[i] = i * 2 + 2;
            id2[i] = i * 2 + 1;
        }
        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            PaymentTerm[] payments = dao.retrievePaymentTerms(id1);

            for (int i = 0; i < id1.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i * 2 + 2, payments[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments[i].getDescription());
            }
            PaymentTerm[] payments2 = dao.retrievePaymentTerms(id2);

            for (int i = 0; i < id2.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i * 2 + 1, payments2[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments2[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments2[i].getDescription());
            }
        }

        System.out.println("Run retrievePaymentTerm(long[]) for " + 10 * TIMES + " records in "
            + (System.currentTimeMillis() - current) + " ms.");

    }

    /**
     * <p>
     * Stress test for <code>retrieveAllPaymentTerms()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllPaymentTerms() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            PaymentTerm[] payments = dao.retrieveAllPaymentTerms();

            for (int i = 0; i < payments.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i + 1, payments[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments[i].getDescription());
            }
        }

        System.out.println("Run retrieveAllPaymentTerms() for " + 10 * TIMES + " records in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>retrieveActivePaymentTerms()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveActivePaymentTerms() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            PaymentTerm[] payments = dao.retrieveActivePaymentTerms();

            for (int i = 0; i < payments.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i + 1, payments[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments[i].getDescription());
                assertTrue("the active retrieved is not corrected.", payments[i].isActive());
            }
        }

        System.out.println("Run retrieveActivePaymentTerms() for " + 10 * TIMES + " records in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>retrieveRecentlyCreatedPaymentTerms()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            PaymentTerm[] payments = dao.retrieveRecentlyCreatedPaymentTerms(-1);

            for (int i = 0; i < payments.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i + 1, payments[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments[i].getDescription());
            }
        }

        System.out.println("Run retrieveRecentlyCreatedPaymentTerms() for " + 10 * TIMES + " records in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>retrieveRecentlyModifiedPaymentTerms()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyModifiedPaymentTerms() throws Exception {
        for (int i = 0; i < TIMES; i++) {
            dao.addPaymentTerm(paymentTerm);
        }

        // retrieve all the PaymentTerms and check the field
        long current = System.currentTimeMillis();
        for (int j = 0; j < 10; j++) {
            PaymentTerm[] payments = dao.retrieveRecentlyModifiedPaymentTerms(-1);
            for (int i = 0; i < payments.length; i++) {
                assertEquals("the Id retrieved is not corrected.", i + 1, payments[i].getId());
                assertEquals("the creationUser retrieved is not corrected.", paymentTerm.getCreationUser(),
                    payments[i].getCreationUser());
                assertEquals("the modificationUser retrieved is not corrected.", paymentTerm
                    .getModificationUser(), payments[i].getModificationUser());
                assertEquals("the description retrieved is not corrected.", paymentTerm.getDescription(),
                    payments[i].getDescription());
            }
        }

        System.out.println("Run retrieveRecentlyModifiedPaymentTerms() for " + 10 * TIMES + " records in "
            + (System.currentTimeMillis() - current) + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>deletePaymentTerm(long)</code>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerm() throws Exception {

        long sum = 0;
        for (int j = 0; j < TIMES; j++) {
            dao.addPaymentTerm(paymentTerm);
            dao.deletePaymentTerm(paymentTerm.getId());
            long current = System.currentTimeMillis();
            assertEquals("some item is not deleted.", 0, dao.retrieveAllPaymentTerms().length);
            sum += System.currentTimeMillis() - current;
        }

        System.out.println("Run retrieveRecentlyModifiedPaymentTerms() for " + TIMES + " records in " + sum
            + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>deletePaymentTerms(long[])</code>
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeletePaymentTerms() throws Exception {
        long[] id1 = new long[TIMES / 2];
        long[] id2 = new long[TIMES / 2];

        for (int i = 0; i < TIMES / 2; i++) {
            id1[i] = i * 2 + 2;
            id2[i] = i * 2 + 1;
        }

        long sum = 0;
        for (int j = 0; j < 10; j++) {
            initDB();
            for (int i = 0; i < TIMES; i++) {
                dao.addPaymentTerm(paymentTerm);
            }
            long current = System.currentTimeMillis();
            dao.deletePaymentTerms(id1);
            dao.deletePaymentTerms(id2);
            sum += System.currentTimeMillis() - current;
        }

        System.out.println("Run retrievePaymentTerm(long[]) for " + 10 * TIMES + " records in " + sum
            + " ms.");
    }

    /**
     * <p>
     * Stress test for <code>deleteAllPaymentTerms()</code>.
     * </p>
     *
     * @throws Exception to JUnit
     */
    public void testDeleteAllPaymentTerms() throws Exception {
        long sum = 0;

        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < TIMES; i++) {
                dao.addPaymentTerm(paymentTerm);
            }
            long current = System.currentTimeMillis();
            dao.deleteAllPaymentTerms();
            assertEquals("some item is not deleted.", 0, dao.retrieveAllPaymentTerms().length);
            sum += System.currentTimeMillis() - current;
        }

        System.out.println("Run retrieveActivePaymentTerms() for " + 10 * TIMES + " records in " + sum
            + " ms.");
    }

    /**
     * <p>
     * Initializes the database. It inserts into the tables for IDGenerator necessary record.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void initDB() throws Exception {
        cleanDB();
        Connection con = connectionFactory.createConnection();
        PreparedStatement statement =
            con.prepareStatement("INSERT INTO id_sequences (name, next_block_start, block_size, exhausted)"
                + "VALUES ('sequence', 1, 1, 0)");
        statement.execute();
        statement.close();
        con.close();
    }

    /**
     * <p>
     * Removes all records from the database.
     * </p>
     *
     * @throws Exception to JUnit
     */
    private void cleanDB() throws Exception {
        Connection con = connectionFactory.createConnection("funny");
        PreparedStatement statement1 = con.prepareStatement("delete from payment_terms");
        PreparedStatement statement2 = con.prepareStatement("delete from id_sequences");
        statement1.execute();
        statement2.execute();
        statement2.close();
        statement1.close();
        con.close();
    }
}