/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */
package com.orpheus.auction.persistence.ejb;

import com.orpheus.auction.persistence.AuctionDAOFactory;
import com.orpheus.auction.persistence.PersistenceException;
import com.orpheus.auction.persistence.UnitTestHelper;

import junit.framework.TestCase;

import java.security.Identity;
import java.security.Principal;

import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.SessionContext;
import javax.ejb.TimerService;

import javax.transaction.UserTransaction;

import javax.xml.rpc.handler.MessageContext;


/**
 * <p>
 * Tests functionality and error cases of <code>AuctionBean</code> class.
 * </p>
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class AuctionBeanUnitTest extends TestCase {
    /** Represents the <code>AuctionBean</code> instance used for testing. */
    private AuctionBean bean;

    /** Represents the <code>SessionContext</code> instance used for testing. */
    private SessionContext context;

    /**
     * <p>
     * Sets up the test environment. The test instance is created. The test configuration namespace will be loaded.
     * </p>
     *
     * @throws Exception Exception to JUnit.
     */
    protected void setUp() throws Exception {
        UnitTestHelper.addConfig();
        bean = new AuctionBean();
        context = new MockSessionContext();
    }

    /**
     * <p>
     * Clears the test environment. The test configuration namespace will be cleared. The test data in the table will
     * be cleared.
     * </p>
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    protected void tearDown() throws Exception {
        UnitTestHelper.clearConfig();
    }

    /**
     * Accuracy test for the constructor <code>AuctionBean()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testAuctionBean_Accuracy() throws Exception {
        new AuctionBean();
    }

    /**
     * Accuracy test for the method <code>ejbCreate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbCreate_Accuracy() throws Exception {
        bean.ejbCreate();
    }

    /**
     * Accuracy test for the method <code>ejbRemove()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbRemove_Accuracy() throws Exception {
        bean.ejbRemove();
    }

    /**
     * Accuracy test for the method <code>ejbActivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbActivate_Accuracy() throws Exception {
        bean.ejbActivate();
    }

    /**
     * Accuracy test for the method <code>ejbPassivate()</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testEjbPassivate_Accuracy() throws Exception {
        bean.ejbPassivate();
    }

    /**
     * Accuracy test for the method <code>setSessionContext(SessionContext)</code>. No exception is expected.
     *
     * @throws Exception pass any unexpected exception to JUnit.
     */
    public void testSetSessionContext_Accuracy() throws Exception {
        bean.setSessionContext(context);
        assertEquals("The context should be set properly.", context,
            UnitTestHelper.getPrivateField(bean.getClass(), bean, "sessionContext"));
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when the given auction is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_NullAuction() throws Exception {
        try {
            bean.createAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>createAuction(AuctionDTO)</code> when there is any problem in the persistence layer,
     * PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.createAuction(UnitTestHelper.buildAuctionDTO(1, 5));
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>createAuction(AuctionDTO)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testCreateAuction_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.createAuction(UnitTestHelper.buildAuctionDTO(1, 5));
        assertEquals("The createAuction method of the AuctionDAO instance should be called.", true,
            dao.isCreateAuctionCalled());
    }

    /**
     * Test the method of <code>getAuction()</code> when there is any problem in the persistence layer,
     * PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.getAuction(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>getAuction()</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testGetAuction_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.getAuction(1);
        assertEquals("The getAuction method of the AuctionDAO instance should be called.", true,
            dao.isGetAuctionCalled());
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when the given auction is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_NullAuction() throws Exception {
        try {
            bean.updateAuction(null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateAuction(AuctionDTO)</code> when there is any problem in the persistence layer,
     * PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.updateAuction(UnitTestHelper.buildAuctionDTO(1, 5));
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>updateAuction(AuctionDTO)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateAuction_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.updateAuction(UnitTestHelper.buildAuctionDTO(1, 5));
        assertEquals("The updateAuction method of the AuctionDAO instance should be called.", true,
            dao.isUpdateAuctionCalled());
    }

    /**
     * Test the method of <code>updateBids(auctionId, BidDTO[])</code> when the given bids is null,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_NullBids() throws Exception {
        try {
            bean.updateBids(1, null);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(auctionId, BidDTO[])</code> when the given bids contain null element,
     * IllegalArgumentException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_ContainNullBid() throws Exception {
        BidDTO[] bids = new BidDTO[] {null};

        try {
            bean.updateBids(1, bids);
            fail("IllegalArgumentException should be thrown.");
        } catch (IllegalArgumentException e) {
            // good
        }
    }

    /**
     * Test the method of <code>updateBids(auctionId, BidDTO[])</code> when there is any problem in the persistence
     * layer, PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.updateBids(1, UnitTestHelper.buildBidDTOs(2));
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>updateBids(auctionId, BidDTO[])</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testUpdateBids_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.updateBids(1, UnitTestHelper.buildBidDTOs(2));
        assertEquals("The updateBids method of the AuctionDAO instance should be called.", true,
            dao.isUpdateBidsCalled());
    }

    /**
     * Test the method of <code>deleteAuction()</code> when there is any problem in the persistence layer,
     * PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.deleteAuction(1);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>deleteAuction()</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testDeleteAuction_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.deleteAuction(1);
        assertEquals("The deleteAuction method of the AuctionDAO instance should be called.", true,
            dao.isDeleteAuctionCalled());
    }

    /**
     * Test the method of <code>findAuctionsByDate(Date, Date)</code> when there is any problem in the persistence
     * layer, PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.findAuctionsByDate(null, null);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>findAuctionsByDate(Date, Date)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByDate_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.findAuctionsByDate(null, null);
        assertEquals("The findAuctionsByDate method of the AuctionDAO instance should be called.", true,
            dao.isFindAuctionsByDateCalled());
    }

    /**
     * Test the method of <code>findAuctionsByBidder(long, Date)</code> when there is any problem in the persistence
     * layer, PersistenceException is expected.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_PersistenceException() throws Exception {
        ExceptionAuctionDAO dao = new ExceptionAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.setSessionContext(context);

        try {
            bean.findAuctionsByBidder(1, null);
            fail("PersistenceException should be thrown.");
        } catch (PersistenceException e) {
            // good
        }

        assertTrue("sessionContext's RollbackOnly should be set.", context.getRollbackOnly());
    }

    /**
     * Accuracy test for the method of <code>findAuctionsByBidder(long, Date)</code>.
     *
     * @throws Exception any exception to JUnit.
     */
    public void testFindAuctionsByBidder_Accuracy() throws Exception {
        MockAuctionDAO dao = new MockAuctionDAO();
        UnitTestHelper.setPrivateField(AuctionDAOFactory.class, null, "auctionDAO", dao);
        bean.findAuctionsByBidder(1, null);
        assertEquals("The findAuctionsByBidder method of the AuctionDAO instance should be called.", true,
            dao.isFindAuctionsByBidderCalled());
    }

    /**
     * <p>
     * A mock class which extends SessionContext for testing.
     * </p>
     *
     * @author TCSDEVELOPER
     * @version 1.0
     */
    private class MockSessionContext implements SessionContext {
        /** Represents the RollbackOnly value. */
        private boolean rollbackOnly = false;

        /**
         * @see SessionContext#getEJBLocalObject()
         */
        public EJBLocalObject getEJBLocalObject() {
            return null;
        }

        /**
         * @see SessionContext#getEJBObject()
         */
        public EJBObject getEJBObject() {
            return null;
        }

        /**
         * @see SessionContext#getMessageContext()
         */
        public MessageContext getMessageContext() {
            return null;
        }

        /**
         * @see SessionContext#getBusinessObject(java.lang.Class)
         */
        public Object getBusinessObject(Class arg0) {
            return null;
        }

        /**
         * @see SessionContext#getEJBHome()
         */
        public EJBHome getEJBHome() {
            return null;
        }

        /**
         * @see SessionContext#getEJBLocalHome()
         */
        public EJBLocalHome getEJBLocalHome() {
            return null;
        }

        /**
         * @see SessionContext#getEnvironment()
         */
        public Properties getEnvironment() {
            return null;
        }

        /**
         * @see SessionContext#getCallerIdentity()
         */
        public Identity getCallerIdentity() {
            return null;
        }

        /**
         * @see SessionContext#getCallerPrincipal()
         */
        public Principal getCallerPrincipal() {
            return null;
        }

        /**
         * @see javax.ejb.SessionContext#isCallerInRole(Identity)
         */
        public boolean isCallerInRole(Identity arg0) {
            return false;
        }

        /**
         * @see javax.ejb.SessionContext#isCallerInRole(String)
         */
        public boolean isCallerInRole(String arg0) {
            return false;
        }

        /**
         * @see SessionContext#getUserTransaction()
         */
        public UserTransaction getUserTransaction() {
            return null;
        }

        /**
         * @see SessionContext#setRollbackOnly()
         */
        public void setRollbackOnly() {
            rollbackOnly = true;
        }

        /**
         * @see SessionContext#getRollbackOnly()
         */
        public boolean getRollbackOnly() {
            return rollbackOnly;
        }

        /**
         * @see SessionContext#getTimerService()
         */
        public TimerService getTimerService() {
            return null;
        }

        /**
         * @see SessionContext#lookup(String)
         */
        public Object lookup(String arg0) {
            return null;
        }
    }
}
