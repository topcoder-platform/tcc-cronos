/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.controlpanel.clientassociations;

import java.util.List;

import javax.naming.InitialContext;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.hibernate.Session;

import com.topcoder.controlpanel.clientassociations.dao.hibernate.Client;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClient;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.CompClientPK;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.HibernateHelper;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClient;
import com.topcoder.controlpanel.clientassociations.dao.hibernate.UserClientPK;

/**
 * <p>
 * This class demonstrates common usage of this component.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class Demo extends DBTestCase {

    /**
     * <p>
     * Represents the <code>ClientAssociationServiceRemote</code> instance that related to the Stateless Session Bean.
     * </p>
     */
    private ClientAssociationServiceRemote cas;

    /**
     * <p>
     * Aggregates all tests in this class.
     * </p>
     *
     * @return test suite aggregating all tests.
     */
    public static Test suite() {
        return new TestSuite(Demo.class);
    }

    /**
     * <p>
     * Setup the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        cleanDatabase();
        prepareDatabase();
    }

    /**
     * <p>
     * Prepares the database for demonstration.
     * </p>
     */
    private void prepareDatabase() {
        // prepared data.
        Session session = HibernateHelper.getSessionFactory().openSession();

        // clients
        session.save(new Client(1, "Client1"));
        session.save(new Client(2, "Client2"));
        session.save(new Client(3, "Client3"));

        // component-client relation
        session.save(new CompClient(new CompClientPK(1l, 1)));
        session.save(new CompClient(new CompClientPK(2l, 1)));
        session.save(new CompClient(new CompClientPK(3l, 3)));

        // user-client relation
        session.save(new UserClient(new UserClientPK(1l, 2), 1, null));
        session.save(new UserClient(new UserClientPK(2l, 3), 0, null));

        session.flush();

        session.close();
    }

    /**
     * <p>
     * Teardown the testing environment.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    @Override
    protected void tearDown() throws Exception {
        cleanDatabase();

        super.tearDown();
    }

    /**
     * <p>
     * Demonstrates the common usage of this component.
     * </p>
     *
     * @throws Exception
     *             pass any unexpected exception to JUnit.
     */
    public void testDemo() throws Exception {
        // create a new context
        InitialContext ctx = new InitialContext();
        // look up the session bean
        cas = (ClientAssociationServiceRemote) ctx.lookup("ClientAssociationServiceStatelessSessionBean");

        // see setup what kinds of data will be prepared.

        // assign a component
        cas.assignComponent(4, 1);
        // as we can expect, the comp_client table now has 4 records

        // calling the above method twice would cause exception though since the record
        // already exists, this also applies to the assignUser method

        // assign a user
        cas.assignUser(2l, 1, true);
        // the user_client table now has 3 records
        // however if you do this, you'll get an exception since the client doesn't exist, this also applies to the
        // other mutating methods
        try {
            cas.assignUser(2, 4, false);
        } catch (ClientAssociationServiceException e) {
            e.printStackTrace();
        }

        // get components by client
        // this returns a list with 3 elements: [1,2,4]
        List<Long> compIds = cas.getComponentsByClient(1);

        System.out.println("The related component ids:" + compIds.toString());

        // or we could get components by user (get components from all clients to which
        // the user is assigned)
        // this returns a list with 4 elements: [1,2,3,4]
        compIds = cas.getComponentsByUser(2);

        System.out.println("The following components are related to the user:" + compIds.toString());

        // check if a user is an admin user
        // this will return true
        boolean isAdmin = cas.isAdminUser(1, 2);

        try {
            isAdmin = cas.isAdminUser(3, 1);
        } catch (ClientAssociationServiceException e) {
            // however this causes exception since there is no association between user 3 and
            // client 1
            e.printStackTrace();
        }

        // remove a component
        cas.removeComponent(1, 1);
        // now the comp_client table has 3 records again.

        // usage of the other operations are just similar so no duplication is provided
        // here
    }
}
