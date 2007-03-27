/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.common.accuracytests;

import java.util.Date;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.timetracker.common.PaymentTerm;
import com.topcoder.timetracker.common.PaymentTermDAO;
import com.topcoder.timetracker.common.persistence.DatabasePaymentTermDAO;

/**
 * Accuracy tests for the class: DatabasePaymentTermDAO.
 *
 * @author kinfkong
 * @version 3.1
 */
public class DatabasePaymentTermDAOAccuracyTest extends DBTestBase {

  /**
   * Represents the instance of PaymentTermDAO for accuracy tests.
   */
  private PaymentTermDAO ptd = null;

  /**
   * Represents the namespace of the configuration for DatabasePaymentTermDAO.
   */
  private static final String DAONAMESPACE = "com.topcoder.trimetracker.common.persistence.DatabasePaymentTermDAO";

  /**
   * Represents the namespace of the DB Connection factory.
   */
  private static final String DBCONNECTIONNAMESPACE = "com.topcoder.db.connectionfactory.DBConnectionFactoryImpl";

  /**
   * Sets up the test environment.
   *
   * @throws Exception
   *             to JUnit
   */
  public void setUp() throws Exception {
    super.setUp();

    ptd = new DatabasePaymentTermDAO(DAONAMESPACE);
  }

  /**
   * Clears the test environment.
   *
   * @throws Exception
   *             to JUnit
   */
  public void tearDown() throws Exception {
    super.tearDown();
  }

  /**
   * Tests the constructor: DatabasePaymentTerm(String).
   *
   */
  public void testDatabasePaymentTermDAOString() {
    assertNotNull("The instance of DatabasePaymentTerm cannot be created.",
        ptd);
  }

  /**
   * Tests the constroctor: DatabasePaymentTerm(DBConnectionFactory, String,
   * String).
   *
   * @throws Exception
   *             to JUnit
   */
  public void testDatabasePaymentTermDAODBConnectionFactoryStringString()
      throws Exception {
    // creat the DBConnectionFactory
    DBConnectionFactory factory = new DBConnectionFactoryImpl(
        DBCONNECTIONNAMESPACE);

    assertNotNull("The instance of DatabasePaymentTerm cannot be created.",
        new DatabasePaymentTermDAO(factory, "Informix", "ids"));
  }

  /**
   * Tests the method: addPaymentTerm(PaymentTerm).
   *
   * @throws Exception
   *             to JUnit
   */
  public void testAddPaymentTerm() throws Exception {
    PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
    ptd.addPaymentTerm(paymentTerm);

    assertFalse("Should not be changed", paymentTerm.isChanged());
    assertTrue("The id should be set", paymentTerm.getId() != -1);

    // exist payment in the database
    assertTrue("The payment is not added to the database.",
        existsRecord(paymentTerm));

  }

  /**
   * Tests the method: UpdatePaymentTerm(PaymentTerm).
   *
   * @throws Exceptiont
   *             to JUnit
   */
  public void testUpdatePaymentTerm_NotChanged() throws Exception {
    PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
    ptd.addPaymentTerm(paymentTerm);

    Thread.sleep(100);
    paymentTerm.setModificationDate(new Date());
    // update it
    ptd.updatePaymentTerm(paymentTerm);

    // exist payment in the database
    assertTrue("The payment is not updated.", existsRecord(paymentTerm));
  }

  /**
   * Tests the method: UpdatePaymentTerm(PaymentTerm).
   *
   * @throws Exception
   *             to JUnit
   */
  public void testUpdatePaymentTerm_Changed() throws Exception {
    PaymentTerm paymentTerm = getDefaultPaymentTerm(1);
    ptd.addPaymentTerm(paymentTerm);

    Thread.sleep(100);

    // change the payment term
    paymentTerm.setTerm(123);
    paymentTerm.setModificationDate(new Date());

    // update it
    ptd.updatePaymentTerm(paymentTerm);

    // exist payment in the database
    assertTrue("The payment is not updated.", existsRecord(paymentTerm));
  }

  /**
   * Tests the method: retrievePaymentTerm().
   *
   * @throws Exception
   *             to JUnit
   */
  public void testRetrievePaymentTerm_Exists() throws Exception {
    // add the payment term
    PaymentTerm paymentTerm = getDefaultPaymentTerm(1);

    ptd.addPaymentTerm(paymentTerm);

    PaymentTerm pt = ptd.retrievePaymentTerm(paymentTerm.getId());
    assertTrue("The method retrievePaymentTerm does not work properly.",
        equalPayments(pt, paymentTerm));

  }

  /**
   * Tests the method: retrievePaymentTerm().
   *
   * @throws Exception
   *             to JUnit
   */
  public void testRetrievePaymentTerm_NotExist() throws Exception {
    // add the payment term
    PaymentTerm paymentTerm = getDefaultPaymentTerm(1);

    ptd.addPaymentTerm(paymentTerm);

    PaymentTerm pt = ptd.retrievePaymentTerm(paymentTerm.getId() + 3);
    assertNull("The payment does not exist.", pt);
  }

  /**
   * Tests the method: retrievePaymentTerms().
   *
   * @throws Exception
   *             to JUnit
   */
  public void testRetrievePaymentTerms() throws Exception {
    // create the payment terms
    PaymentTerm[] paymentTerms = new PaymentTerm[10];
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getDefaultPaymentTerm(i + 1);
      ptd.addPaymentTerm(paymentTerms[i]);
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
        if (id1 == paymentTerms[i].getId()
            || id2 == paymentTerms[i].getId()) {
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
    PaymentTerm[] terms = ptd.retrievePaymentTerms(ids);
    assertEquals("Should contains 3 elements.", 3, terms.length);
    for (int i = 0; i < terms.length; i++) {
      assertEquals(
          "The retrievePaymentTerms method does not work properly.",
          (i + 2) * 2 + 1, terms[i].getTerm());
    }
  }

  /**
   * Tests the method: retrievePaymentTerms.
   *
   * @throws Exception
   *             to JUnit
   *
   */
  public void testRetrievePaymentTerms_NotExist() throws Exception {
    // create the payment terms
    PaymentTerm[] paymentTerms = new PaymentTerm[10];
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getDefaultPaymentTerm(i + 1);
      ptd.addPaymentTerm(paymentTerms[i]);
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

    PaymentTerm[] terms = ptd.retrievePaymentTerms(ids);
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
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    // check it
    PaymentTerm[] terms = ptd.retrieveAllPaymentTerms();
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
      paymentTerms[i] = getPaymentTerm(-1, new Date(), new Date(),
          "reviewer", "reviewer", "for test", i + 1, i % 2 == 0, false);
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    PaymentTerm[] terms = ptd.retrieveActivePaymentTerms();

    assertEquals("Should contain 5 elements.", 5, terms.length);

    for (int i = 0; i < terms.length; i++) {
      if ((terms[i].getTerm() - 1)% 2 != 0) {
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
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          "reviewer", "reviewer", "for test", i + 1, i % 2 == 0, false);
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    PaymentTerm[] terms = ptd.retrieveRecentlyCreatedPaymentTerms(1);
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
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          "reviewer", "reviewer", "for test", i + 1, i % 2 == 0, false);
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    PaymentTerm[] terms = ptd.retrieveRecentlyCreatedPaymentTerms(-1);
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
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          "reviewer", "reviewer", "for test", i + 1, i % 2 == 0, false);
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    PaymentTerm[] terms = ptd.retrieveRecentlyModifiedPaymentTerms(1);
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
    for (int i = 0; i < 10; i++) {
      paymentTerms[i] = getPaymentTerm(-1, new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          new Date(new Date().getTime() - (i % 2 == 0 ? 0 : 86405000)),
          "reviewer", "reviewer", "for test", i + 1, i % 2 == 0, false);
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    PaymentTerm[] terms = ptd.retrieveRecentlyModifiedPaymentTerms(-1);
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
    ptd.addPaymentTerm(paymentTerm);
    ptd.deletePaymentTerm(paymentTerm.getId());

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
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    // delete five of them
    long[] ids = new long[5];
    for (int i = 0; i < ids.length; i++) {
      ids[i] = paymentTerms[i * 2].getId();
    }

    ptd.deletePaymentTerms(ids);

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
      ptd.addPaymentTerm(paymentTerms[i]);
    }

    ptd.deleteAllPaymentTerms();

    PaymentTerm[] terms = ptd.retrieveAllPaymentTerms();

    assertEquals("Should contain no payment terms", 0, terms.length);
  }

  /**
   * Gets the PaymentTerm instance with specified fields.
   *
   * @param id
   *            the id
   * @param creationDate
   *            the creation date
   * @param modificationDate
   *            the modification date
   * @param creationUser
   *            the creation user
   * @param modificationUser
   *            the modification user
   * @param description
   *            the description
   * @param term
   *            the term
   * @param active
   *            active
   * @param changed
   *            changed
   *
   * @return an PaymentTerm instance with specified fields
   */
  private PaymentTerm getPaymentTerm(long id, Date creationDate,
      Date modificationDate, String creationUser,
      String modificationUser, String description, int term,
      boolean active, boolean changed) {
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
   * @param term
   *            the term
   *
   * @return the default paymentTerm with specified term
   *
   * @throws Excetion
   *             to JUnit
   */
  private PaymentTerm getDefaultPaymentTerm(int term) throws Exception {
    Date date = new Date();
    return getPaymentTerm(-1, date, date, "reviewer", "reviewer",
        "for tests", term, false, false);
  }

}
