/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.accuracytests;

import java.util.Date;

import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.SimpleCommonManager;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;

/**
 * Accuracy tests for the method: SimpleCommonManager.
 *
 * @author kinfkong
 * @version 3.1
 */
public class SimpleCommonManagerAccuracyTest extends DBTestBase {

    /**
     * Represents the configuration namespace for SimpleCommonManager.
     */
    private static final String NAMESPACE = "com.topcoder.timetracker.common.SimpleCommonManager";

    /**
     * Represents the namespace of the configuration for DatabasePaymentTermDAO.
     */
    private static final String DAONAMESPACE =
        "com.topcoder.trimetracker.common.persistence.DatabasePaymentTermDAO";

    /**
     * Represents the instance of SimpleCommonManager for accuracy tests.
     */
    private SimpleCommonManager scm = null;

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    public void setUp() throws Exception {
        super.setUp();
        scm = new SimpleCommonManager();
    }

    /**
     * Clears the test environment.
     *
     * @throws Exception to JUnit
     */
    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Tests the constructor: SimpleCommonManager().
     *
     */
    public void testSimpleCommonManager() {
        assertNotNull("The instance of SimpleCommonManager cannot be created.", scm);
    }

    /**
     * Tests the constructor: SimpleCommonManager(String).
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManagerString() throws Exception {
        assertNotNull("The instance of SimpleCommonManager cannot be created.", new SimpleCommonManager(
            NAMESPACE));
    }

    /**
     * Tests the constructor: SimpleCommonManager(PaymentTermDAO, int).
     *
     * @throws Exception to JUnit
     */
    public void testSimpleCommonManagerPaymentTermDAOInt() throws Exception {
        PaymentTermDAO paymentTermDAO = new DatabasePaymentTermDAO(DAONAMESPACE);
        assertNotNull("The instance of SimpleCommonManager cannot be created.", new SimpleCommonManager(
            paymentTermDAO, -1));
    }

    /**
     * Tests the method: addPaymentTerm(PaymentTerm).
     *
     * @throws Exception to JUnit
     */
    public void testAddPaymentTerm() throws Exception {
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
        scm.addPaymentTerm(paymentTerm);

        assertFalse("Should not be changed", paymentTerm.isChanged());
        assertTrue("The id should be set", paymentTerm.getId() != -1);

        // exist payment in the database
        assertTrue("The payment is not added to the database.", existsRecord(paymentTerm));

    }

    /**
     * Tests the method: UpdatePaymentTerm(PaymentTerm).
     *
     * @throws Exceptiont to JUnit
     */
    public void testUpdatePaymentTerm_NotChanged() throws Exception {
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
        scm.addPaymentTerm(paymentTerm);

        Thread.sleep(100);
        paymentTerm.setModificationDate(new Date());
        // update it
        scm.updatePaymentTerm(paymentTerm);

        // exist payment in the database
        assertTrue("The payment is not updated.", existsRecord(paymentTerm));
    }

    /**
     * Tests the method: UpdatePaymentTerm(PaymentTerm).
     *
     * @throws Exception to JUnit
     */
    public void testUpdatePaymentTerm_Changed() throws Exception {
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
        scm.addPaymentTerm(paymentTerm);

        Thread.sleep(100);

        // change the payment term
        paymentTerm.setTerm(123);
        paymentTerm.setModificationDate(new Date());

        // update it
        scm.updatePaymentTerm(paymentTerm);

        // exist payment in the database
        assertTrue("The payment is not updated.", existsRecord(paymentTerm));
    }

    /**
     * Tests the method: retrievePaymentTerm().
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerm_Exists() throws Exception {
        // add the payment term
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);

        scm.addPaymentTerm(paymentTerm);

        PaymentTerm pt = scm.retrievePaymentTerm(paymentTerm.getId());
        assertTrue("The method retrievePaymentTerm does not work properly.", equalPayments(pt, paymentTerm));

    }

    /**
     * Tests the method: retrievePaymentTerm().
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerm_NotExist() throws Exception {
        // add the payment term
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);

        scm.addPaymentTerm(paymentTerm);

        PaymentTerm pt = scm.retrievePaymentTerm(paymentTerm.getId() + 3);
        assertNull("The payment does not exist.", pt);
    }

    /**
     * Tests the method: retrievePaymentTerms().
     *
     * @throws Exception to JUnit
     */
    public void testRetrievePaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] = getDefaultPaymentTerm(i + 1);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        // retrieve 5 of them, but 2 of which cannot be found
        long[] ids = new long[5];
        for (int i = 0; i < 10; i += 2) {
            ids[i / 2] = paymentTerms[i].getId();
        }

        long id1;
        long id2;
        while (true) {
            id1 = (long) (Math.random() * 1000);
            id2 = (long) (Math.random() * 1000);
            int i;
            for (i = 0; i < paymentTerms.length; i++) {
                if (id1 == paymentTerms[i].getId() || id2 == paymentTerms[i].getId()) {
                    break;
                }
            }
            if (i < paymentTerms.length) {
                continue;
            } else {
                break;
            }

        }

        ids[0] = id1;
        ids[1] = id2;
        PaymentTerm[] terms = scm.retrievePaymentTerms(ids);
        assertEquals("Should contains 3 elements.", 3, terms.length);
        for (int i = 0; i < terms.length; i++) {
            assertEquals("The retrievePaymentTerms method does not work properly.", (i + 2) * 2 + 1, terms[i]
                .getTerm());
        }
    }

    /**
     * Tests the method: retrievePaymentTerms.
     *
     * @throws Exception to JUnit
     *
     */
    public void testRetrievePaymentTerms_NotExist() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] = getDefaultPaymentTerm(i + 1);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        // retrieve 5 of them, but all of them do not exist
        long[] ids = new long[5];

        for (int j = 0; j < ids.length; j++) {
            long id;
            while (true) {
                id = (long) (Math.random() * 1000);
                int i;
                for (i = 0; i < paymentTerms.length; i++) {
                    if (id == paymentTerms[i].getId()) {
                        break;
                    }
                }
                if (i < paymentTerms.length) {
                    continue;
                } else {
                    break;
                }
            }
            ids[j] = id;
        }

        PaymentTerm[] terms = scm.retrievePaymentTerms(ids);
        assertEquals("An empty array should be returned", 0, terms.length);
    }

    /**
     * Tests the method: retrieveAllPaymentTerms().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveAllPaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] = getDefaultPaymentTerm(i + 1);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        // check it
        PaymentTerm[] terms = scm.retrieveAllPaymentTerms();
        assertEquals("Should contains 10 elements", 10, terms.length);
        boolean[] flags = new boolean[11];
        for (int i = 1; i <= 10; i++) {
            flags[i] = false;
        }
        for (int i = 0; i < terms.length; i++) {
            int term = terms[i].getTerm();
            if (flags[term]) {
                fail("The method retrieveAllPaymentTerms does not work properly.");
            }
            flags[term] = true;
        }
        for (int i = 1; i <= 10; i++) {
            if (flags[i] == false) {
                fail("The method retrieveAllPaymentTErms does not work properly.");
            }
        }
    }

    /**
     * Tests the method: retrieveActivePaymentTerms.
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveActivePaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(), new Date(), "reviewer", "reviewer", "for test", i + 1,
                    i % 2 == 0, false);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveActivePaymentTerms();

        assertEquals("Should contain 5 elements.", 5, terms.length);

        for (int i = 0; i < terms.length; i++) {
            if ((terms[i].getTerm() - 1) % 2 != 0) {
                fail("The method retrieveActivePaymentTerms does not work properly.");
            }
        }
    }

    /**
     * Tests the method retrieveRecentlyCreatedPaymentTerms().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyCreatedPaymentTerms(1);
        assertEquals("Should contain 5 elements.", 5, terms.length);

        for (int i = 0; i < terms.length; i++) {
            if ((terms[i].getTerm() - 1) % 2 == 1) {
                fail("The method retrieveRecentlyCreatedPaymentTerms does not work properly.");
            }
        }

    }

    /**
     * Tests the method retrieveRecentlyCreatedPaymentTerms().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms_MinusOne() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyCreatedPaymentTerms(-1);
        assertEquals("Should contain 10 elements.", 10, terms.length);

    }

    /**
     * Tests the method retrieveRecentlyCreatedPaymentTerms().
     *
     * @throws Exception to JUnit
     */
    public void testRetrieveRecentlyCreatedPaymentTerms_Default() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyCreatedPaymentTerms();
        assertEquals("Should contain 10 elements.", 10, terms.length);

    }

    /**
     * Tests the method: retrieveRecentlyModifiedPaymentTerms().
     *
     * @throws Exception to JUnit
     *
     */
    public void testRetrieveRecentlyModifiedPaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyModifiedPaymentTerms(1);
        assertEquals("Should contain 5 elements.", 5, terms.length);

        for (int i = 0; i < terms.length; i++) {
            if ((terms[i].getTerm() - 1) % 2 == 1) {
                fail("The method retrieveRecentlyModifiedPaymentTerms does not work properly.");
            }
        }
    }

    /**
     * Tests the method: retrieveRecentlyModifiedPaymentTerms().
     *
     * @throws Exception to JUnit
     *
     */
    public void testRetrieveRecentlyModifiedPaymentTerms_MinusOne() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyModifiedPaymentTerms(-1);
        assertEquals("Should contain 10 elements.", 10, terms.length);

    }

    /**
     * Tests the method: retrieveRecentlyModifiedPaymentTerms().
     *
     * @throws Exception to JUnit
     *
     */
    public void testRetrieveRecentlyModifiedPaymentTerms_Default() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        PaymentTermDAO ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] =
                getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), new Date(
                    new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)), "reviewer", "reviewer", "for test",
                    i + 1, i % 2 == 0, false);
            ptd.addPaymentTerm(paymentTerms[i]);
        }

        PaymentTerm[] terms = scm.retrieveRecentlyModifiedPaymentTerms();
        assertEquals("Should contain 10 elements.", 10, terms.length);

    }

    /**
     * Tests the method: deletePaymentTerm().
     *
     * @throws Exception to JUnit
     *
     */
    public void testDeletePaymentTerm() throws Exception {
        // create the payment term
        PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
        scm.addPaymentTerm(paymentTerm);
        scm.deletePaymentTerm(paymentTerm.getId());

        assertFalse("The payment term should be removed.", existsRecord(paymentTerm));

    }

    /**
     * Tests the method: deletePaymentTerms.
     *
     * @throws Exception to JUnit
     *
     */
    public void testDeletePaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] = getDefaultPaymentTerm(i + 1);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        // delete five of them
        long[] ids = new long[5];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = paymentTerms[i * 2].getId();
        }

        scm.deletePaymentTerms(ids);

        for (int i = 0; i < 10; i += 2) {
            assertFalse("The payment term should be removed.", existsRecord(paymentTerms[i]));
        }
    }

    /**
     * Tests the method: deleteAllPaymentTerms().
     *
     * @throws Exception to JUnit
     *
     */
    public void testDeleteAllPaymentTerms() throws Exception {
        // create the payment terms
        PaymentTerm[] paymentTerms = new PaymentTerm[10];
        for (int i = 0; i < 10; i++) {
            paymentTerms[i] = getDefaultPaymentTerm(i + 1);
            scm.addPaymentTerm(paymentTerms[i]);
        }

        scm.deleteAllPaymentTerms();

        PaymentTerm[] terms = scm.retrieveAllPaymentTerms();

        assertEquals("Should contain no payment terms", 0, terms.length);
    }

    /**
     * Gets the PaymentTerm instance with specified fields.
     *
     * @param id the id
     * @param creationDate the creation date
     * @param modificationDate the modification date
     * @param creationUser the creation user
     * @param modificationUser the modification user
     * @param description the description
     * @param term the term
     * @param active active
     * @param changed changed
     *
     * @return an PaymentTerm instance with specified fields
     */
    private PaymentTerm getPaymentTerm(long id, Date creationDate, Date modificationDate,
        String creationUser, String modificationUser, String description, int term, boolean active,
        boolean changed) {
        PaymentTerm paymentTerm = new PaymentTerm();
        paymentTerm.setId(id);
        paymentTerm.setCreationDate(creationDate);
        paymentTerm.setModificationDate(modificationDate);
        paymentTerm.setCreationUser(creationUser);
        paymentTerm.setModificationUser(modificationUser);
        paymentTerm.setDescription(description);
        paymentTerm.setActive(active);
        paymentTerm.setChanged(changed);
        paymentTerm.setTerm(term);
        return paymentTerm;
    }

    /**
     * Gets the default paymentTerm with specified term.
     *
     * @param term the term
     *
     * @return the default paymentTerm with specified term
     *
     * @throws Excetion to JUnit
     */
    private PaymentTerm getDefaultPaymentTerm(int term) throws Exception {
        Date date = new Date();
        return getPaymentTerm(-1, date, date, "reviewer", "reviewer", "for tests", term, false, false);
    }
}
