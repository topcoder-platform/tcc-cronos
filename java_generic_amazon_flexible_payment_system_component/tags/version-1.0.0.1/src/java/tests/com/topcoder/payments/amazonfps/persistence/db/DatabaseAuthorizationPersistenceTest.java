/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.payments.amazonfps.persistence.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.JUnit4TestAdapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.topcoder.commons.utils.JDBCUtility;
import com.topcoder.payments.amazonfps.AmazonFlexiblePaymentSystemComponentConfigurationException;
import com.topcoder.payments.amazonfps.AuthorizationNotFoundException;
import com.topcoder.payments.amazonfps.TestHelper;
import com.topcoder.payments.amazonfps.model.Authorization;
import com.topcoder.payments.amazonfps.persistence.AuthorizationPersistenceException;
import com.topcoder.util.idgenerator.IDGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * <p>
 * Unit tests for {@link DatabaseAuthorizationPersistence} class.
 * </p>
 *
 * @author KennyAlive
 * @version 1.0
 */
public class DatabaseAuthorizationPersistenceTest {
    /**
     * Constant for configuration namespace for accuracy tests.
     */
    private static final String CONFIGURATION = "PersistenceTest";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_1 = "DatabaseAuthorizationPersistenceFailure1";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_2 = "DatabaseAuthorizationPersistenceFailure2";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_3 = "PersistenceFailure";
    /**
     * Constant for configuration namespace for failure tests.
     */
    private static final String FAILURE_CONFIGURATION_4 = "PersistenceFailure2";

    /**
     * Constant used for testing. Represents authorized amount left.
     */
    private static final BigDecimal AMOUNT_LEFT = BigDecimal.valueOf(120.25);
    /**
     * Constant used for testing. Represents authorized fixed future amount.
     */
    private static final BigDecimal FIXED_FUTURE_AMOUNT = BigDecimal.valueOf(25.0);
    /**
     * Constant used for testing. Represents token id.
     */
    private static final String TOKEN_ID = "token";

    /**
     * The {@code MockBaseDatabasePersistence} instance used for getting database connection
     * for testing, since this mock class exposes public getConnection() method which is what we need.
     */
    private MockBaseDatabasePersistence testConnectionProvider;

    /**
     * Represents the Connection used in tests.
     */
    private Connection connection;

    /**
     * The {@code DatabaseAuthorizationPersistence} instance used for testing.
     */
    private DatabaseAuthorizationPersistence instance;


    /**
     * Creates a suite with all test methods for JUnix3.x runner.
     *
     * @return a test suite
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(DatabaseAuthorizationPersistenceTest.class);
    }

    /**
     * Sets up the test environment.
     *
     * @throws Exception to JUnit
     */
    @Before
    public void setUp() throws Exception {
        testConnectionProvider = new MockBaseDatabasePersistence();
        testConnectionProvider.configure(TestHelper.getConfiguration(CONFIGURATION));

        connection = testConnectionProvider.createConnection();
        TestHelper.clearDB(connection);

        instance = new DatabaseAuthorizationPersistence();
    }

    /**
     * Cleans up the test environment.
     *
     * @throws Exception to JUnit
     */
    @After
    public void tearDown() throws Exception {
        TestHelper.clearDB(connection);
        connection.close();
        connection = null;
    }

    /**
     * Accuracy test for {@code DatabaseAuthorizationPersistence} constructor. <br/>
     * Check that the fields are initialized to correct default values.
     */
    @Test
    public void test_constructorDatabaseAuthorizationPersistence() {
        assertNull("The authorization id generator should be null",
                TestHelper.getField(instance, "authorizationIdGenerator"));
    }

    //-------------------------------------------------------------------------
    // configure() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code configure} method. <br/>
     * Check that fields are initialized according to configuration.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_configure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        IDGenerator idGenerator =
                (IDGenerator) TestHelper.getField(instance, "authorizationIdGenerator");

        assertNotNull("The authorization id generator should not be null", idGenerator);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * Set null configuration.
     *
     * @throws Exception to JUnit
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_configureFailure_1() throws Exception {
        instance.configure(null);
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * ID generator name is not specified.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_1));
    }

    /**
     * Failure test for {@code configure} method. <br/>
     * ID generator name is empty.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AmazonFlexiblePaymentSystemComponentConfigurationException.class)
    public void test_configureFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_2));
    }

    //-------------------------------------------------------------------------
    // createAuthorization() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code createAuthorization} method. <br/>
     * Create authorization in persistence and check that is was created successfully.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createAuthorization_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        // set authorization parameters
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(false);

        // before authorization is created in persistence id parameter is 0
        assertEquals("Authorization id should be zero", 0L, authorization.getId());

        // create authorization in persistence
        instance.createAuthorization(authorization);

        // check that authorization id was updated
        assertTrue("Authorization id should be positive", authorization.getId() > 0L);

        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM authorizations",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Boolean.class, String.class,
                                Double.class, Double.class, Boolean.class, Date.class},
                Exception.class);

        assertEquals("There should be 1 authorization", 1, result.length);

        assertEquals("Authorization id should be correct",
                Long.valueOf(authorization.getId()), result[0][0]);

        assertEquals("Mutliple use flag should be correct",
                Boolean.valueOf(authorization.isMultipleUseAuthorization()), result[0][1]);

        assertEquals("Token id should be correct", authorization.getTokenId(), result[0][2]);

        assertEquals("Amount left should be correct",
                Double.valueOf(authorization.getAuthorizedAmountLeft().doubleValue()), result[0][3]);

        assertEquals("Fixed future amount should be correct",
                Double.valueOf(authorization.getAuthorizedFixedFutureAmount().doubleValue()), result[0][4]);

        assertEquals("Cancelled flag should be correct",
                Boolean.valueOf(authorization.isCancelled()), result[0][5]);
    }

    /**
     * Accuracy test for {@code createAuthorization} method. <br/>
     * Create many authorizations in persistence. Check that result as expected.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_createAuthorization_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Set<Long> ids = new HashSet<Long>();
        Map<Long, String> tokenIds = new HashMap<Long, String>();

        Authorization authorization = new Authorization();

        // create authorization with varying parameters
        final int count = 50;
        for (int i = 0; i < count; i++) {
            String tokenId = "tokenid" + i;
            authorization.setTokenId(tokenId);
            authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(i));
            authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(2 * i));
            instance.createAuthorization(authorization);
            assertFalse("authorization id should be unique", ids.contains(authorization.getId()));

            ids.add(authorization.getId());
            tokenIds.put(authorization.getId(), tokenId);
        }

        // retrieve 'authorizations' table content for validation
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM authorizations",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Boolean.class, String.class, Double.class, Double.class,
                    Boolean.class, Date.class}, Exception.class);

        // validate records count, ids existence, amount and token id
        assertEquals("Authorization count should be correct", count, result.length);
        for (int i = 0; i < count; i++) {
            Long id = (Long) result[i][0];
            assertTrue("Authorization id should exist", ids.contains(id));
            assertEquals("Authorization token id should be correct", tokenIds.get(id), result[i][2]);
            assertEquals("Authorization amount should be correct", Double.valueOf(i), result[i][3]);
        }
    }

    /**
     * Failure test for {@code createAuthorization} method. <br/>
     * Pass null authorization instance.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_createAuthorizationFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createAuthorization(null);
    }

    /**
     * Failure test for {@code createAuthorization} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_createAuthorizationFailure_2() throws Exception {
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);

        instance.createAuthorization(authorization);
    }

    /**
     * Failure test for {@code createAuthorization} method. <br/>
     * Pass object with null fields which will cause SQL error
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_createAuthorizationFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.createAuthorization(new Authorization());
    }

    /**
     * Failure test for {@code createAuthorization} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_createAuthorizationFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
        instance.createAuthorization(new Authorization());
    }

    /**
     * Failure test for {@code createAuthorization} method. <br/>
     * Incorrect id generator name.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_createAuthorizationFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_4));
        instance.createAuthorization(new Authorization());
    }

    //-------------------------------------------------------------------------
    // updateAuthorization() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code updateAuthorization} method. <br/>
     * At first create 'empty' record, then update it and then check result
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_updateAuthorization_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        // create empty authorization record in persistence
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(false);
        authorization.setTokenId("");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(0));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(0));
        authorization.setCancelled(false);
        instance.createAuthorization(authorization);
        Long id = authorization.getId();

        // update authorization in persistence
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);

        instance.updateAuthorization(authorization);

        // retrieve 'authorizations' table content for validation
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM authorizations",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Boolean.class, String.class, Double.class, Double.class,
                    Boolean.class, Date.class}, Exception.class);

        // validate updated record
        assertEquals("There should be 1 authorization", 1, result.length);
        assertEquals("Authorization id should be correct", id, result[0][0]);
        assertEquals("Mutliple use flag should be correct", Boolean.TRUE, result[0][1]);
        assertEquals("Token id should be correct", authorization.getTokenId(), result[0][2]);
        assertEquals("Amount left should be correct",
                Double.valueOf(AMOUNT_LEFT.doubleValue()), result[0][3]);
        assertEquals("Fixed future amount should be correct",
                Double.valueOf(FIXED_FUTURE_AMOUNT.doubleValue()), result[0][4]);
        assertEquals("Cancelled flag should be correct", Boolean.TRUE, result[0][5]);
    }

    /**
     * Accuracy test for {@code updateAuthorization} method. <br/>
     * Create a few records and update some of them
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_updateAuthorization_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();

        final int updateCount = 2;
        long[] updateIds = new long[2];

        // create authorization with varying parameters
        final int count = 5;
        for (int i = 0; i < count; i++) {
            String tokenId = "tokenid" + i;
            authorization.setTokenId(tokenId);
            authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(i));
            authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(0));
            instance.createAuthorization(authorization);
            if (i < updateCount) {
                updateIds[i] = authorization.getId();
            }
        }

        // set cancelled flag for the first updateCount records
        for (int i = 0; i < updateCount; i++) {
            authorization = instance.getAuthorization(updateIds[i]);
            authorization.setCancelled(true);
            instance.updateAuthorization(authorization);
        }

        // retrieve 'authorizations' table content for validation
        Object[][] result = JDBCUtility.executeQuery(connection, "SELECT * FROM authorizations",
                new int[0], new Object[0],
                new Class<?>[] {Long.class, Boolean.class, String.class, Double.class, Double.class,
                    Boolean.class, Date.class}, Exception.class);

        // validate cancelled flag: the first updateCount should be true, the others should be false
        for (int i = 0; i < count; i++) {
            Boolean cancelled = (i < updateCount);
            assertEquals("Cancelled flag should be correct", cancelled, result[i][5]);
        }
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Pass null authorization instance.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updateAuthorizationFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.updateAuthorization(null);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Authorization id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updateAuthorizationFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setId(0);
        instance.updateAuthorization(authorization);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Authorization id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_updateAuthorizationFailure_3() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setId(-2);
        instance.updateAuthorization(authorization);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_updateAuthorizationFailure_4() throws Exception {
        Authorization authorization = new Authorization();
        authorization.setId(1L);
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);

        instance.updateAuthorization(authorization);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Pass not existed authorization id.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationNotFoundException.class)
    public void test_updateAuthorizationFailure_5() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);
        instance.createAuthorization(authorization);

        authorization.setId(authorization.getId() + 1);
        instance.updateAuthorization(authorization);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Set authorization amount field to null, this will generate SQL error.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_updateAuthorizationFailure_6() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);
        instance.createAuthorization(authorization);

        authorization.setAuthorizedAmountLeft(null);
        instance.updateAuthorization(authorization);
    }

    /**
     * Failure test for {@code updateAuthorization} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_updateAuthorizationFailure_7() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(false);
        authorization.setTokenId("");
        authorization.setAuthorizedAmountLeft(BigDecimal.valueOf(0));
        authorization.setAuthorizedFixedFutureAmount(BigDecimal.valueOf(0));
        authorization.setCancelled(false);
        instance.createAuthorization(authorization);

        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
        instance.updateAuthorization(authorization);
    }

    //-------------------------------------------------------------------------
    // getAuthorization() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getAuthorization} method. <br/>
     * Null should be returned if authorization doesn't exist.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAuthorization_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(false);
        instance.createAuthorization(authorization);

        authorization = instance.getAuthorization(authorization.getId() + 1);
        assertNull("Authorization should be null", authorization);
    }

    /**
     * Accuracy test for {@code getAuthorization} method. <br/>
     * Request existed authorization, check that correct object is returned.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAuthorization_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);
        instance.createAuthorization(authorization);
        long id = authorization.getId();

        authorization = instance.getAuthorization(id);
        assertNotNull("Authorization should not be null", authorization);

        assertEquals("Id should be correct", id, authorization.getId());
        assertEquals("Multiple use flag should be correct", true, authorization.isMultipleUseAuthorization());
        assertEquals("Token id should be correct", TOKEN_ID, authorization.getTokenId());
        assertEquals("Amount left should be correct", AMOUNT_LEFT, authorization.getAuthorizedAmountLeft());
        assertEquals("Amount left should be correct",
                FIXED_FUTURE_AMOUNT, authorization.getAuthorizedFixedFutureAmount());
        assertEquals("Cancelled flag should be correct", true, authorization.isCancelled());
    }

    /**
     * Failure test for {@code getAuthorization} method. <br/>
     * Authorization id is zero.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getAuthorizationFailure_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getAuthorization(0L);
    }

    /**
     * Failure test for {@code getAuthorization} method. <br/>
     * Authorization id is negative.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalArgumentException.class)
    public void test_getAuthorizationFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));
        instance.getAuthorization(-11L);
    }

    /**
     * Failure test for {@code getAuthorization} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_getAuthorizationFailure_3() throws Exception {
        instance.getAuthorization(1L);
    }

    /**
     * Failure test for {@code getAuthorization} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_getAuthorizationFailure_4() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
        instance.getAuthorization(1L);
    }

    //-------------------------------------------------------------------------
    // getAllAuthorizations() tests
    //-------------------------------------------------------------------------
    /**
     * Accuracy test for {@code getAllAuthorizations} method. <br/>
     * Empty list should be returned if there are no authorizations.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllAuthorizations_1() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        List<Authorization> result = instance.getAllAuthorizations();
        assertTrue("List should be empty", result.isEmpty());
    }

    /**
     * Accuracy test for {@code getAllAuthorizations} method. <br/>
     * Request all authorizations, check that result is correct.
     *
     * @throws Exception to JUnit
     */
    @Test
    public void test_getAllAuthorizations_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(CONFIGURATION));

        Authorization authorization = new Authorization();
        authorization.setMultipleUseAuthorization(true);
        authorization.setTokenId(TOKEN_ID);
        authorization.setAuthorizedAmountLeft(AMOUNT_LEFT);
        authorization.setAuthorizedFixedFutureAmount(FIXED_FUTURE_AMOUNT);
        authorization.setCancelled(true);
        instance.createAuthorization(authorization);
        long id1 = authorization.getId();

        final String testToken = "TEST_TOKEN";
        authorization.setMultipleUseAuthorization(false);
        authorization.setTokenId(testToken);
        authorization.setCancelled(false);
        instance.createAuthorization(authorization);
        long id2 = authorization.getId();

        List<Authorization> result = instance.getAllAuthorizations();

        assertEquals("The should be 2 authorizations", 2, result.size());
        assertNotNull("The first authorization should not be null", result.get(0));
        assertNotNull("The second authorization should not be null", result.get(1));

        if (result.get(0).getId() != id1) {
            result.set(0, result.set(1, result.get(0))); // swap
        }

        authorization = result.get(0);
        assertEquals("Id should be correct", id1, authorization.getId());
        assertEquals("Multiple use flag should be correct", true, authorization.isMultipleUseAuthorization());
        assertEquals("Token id should be correct", TOKEN_ID, authorization.getTokenId());
        assertEquals("Amount left should be correct", AMOUNT_LEFT, authorization.getAuthorizedAmountLeft());
        assertEquals("Amount left should be correct",
                FIXED_FUTURE_AMOUNT, authorization.getAuthorizedFixedFutureAmount());
        assertEquals("Cancelled flag should be correct", true, authorization.isCancelled());

        authorization = result.get(1);
        assertEquals("Id should be correct", id2, authorization.getId());
        assertEquals("Multiple use flag should be correct", false, authorization.isMultipleUseAuthorization());
        assertEquals("Token id should be correct", testToken, authorization.getTokenId());
        assertEquals("Amount left should be correct", AMOUNT_LEFT, authorization.getAuthorizedAmountLeft());
        assertEquals("Amount left should be correct",
                FIXED_FUTURE_AMOUNT, authorization.getAuthorizedFixedFutureAmount());
        assertEquals("Cancelled flag should be correct", false, authorization.isCancelled());
    }

    /**
     * Failure test for {@code getAllAuthorizations} method. <br/>
     * Persistence instance is not configured.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = IllegalStateException.class)
    public void test_getAllAuthorizationsFailure_1() throws Exception {
        instance.getAllAuthorizations();
    }

    /**
     * Failure test for {@code getAllAuthorizations} method. <br/>
     * Incorrect connection parameters.
     *
     * @throws Exception to JUnit
     */
    @Test (expected = AuthorizationPersistenceException.class)
    public void test_getAllAuthorizationsFailure_2() throws Exception {
        instance.configure(TestHelper.getConfiguration(FAILURE_CONFIGURATION_3));
        instance.getAllAuthorizations();
    }
}
