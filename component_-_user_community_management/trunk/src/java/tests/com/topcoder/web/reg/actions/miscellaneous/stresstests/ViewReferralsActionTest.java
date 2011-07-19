/**
 * 
 */
package com.topcoder.web.reg.actions.miscellaneous.stresstests;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import com.topcoder.shared.dataAccess.DataAccess;
import com.topcoder.shared.dataAccess.DataAccessConstants;
import com.topcoder.shared.security.SimpleUser;
import com.topcoder.shared.security.User;
import com.topcoder.web.common.security.BasicAuthentication;
import com.topcoder.web.reg.actions.miscellaneous.ReferralData;
import com.topcoder.web.reg.actions.miscellaneous.ViewReferralsAction;

/**
 * Stress tests for <code>ViewReferralsAction</code>.
 *
 * @author moon.river
 * @version 1.0
 */
public class ViewReferralsActionTest extends TestCase {

    /**
     * The number of referrals.
     */
    private static final int N = 1000;

    /**
     * The data source.
     */
    private DataSource ds;

    /**
     * The instance to test.
     */
    private ViewReferralsAction action;

    /**
     * @throws java.lang.Exception
     */
    protected void setUp() throws Exception {
        super.setUp();

        ds = new MockDataSource();
        tearDown();

        DataAccess dataAccess = new DataAccess(ds);
        action = new ViewReferralsAction();
        action.setDataAccess(dataAccess);

        action.setSession(new HashMap<String, Object>());
        action.setAuthenticationSessionKey("key");
        action.getSession().put("key", new BasicAuthentication() {
            public User getActiveUser() {
                User user = new SimpleUser(900000000, "test", "test");
                return user;
            }
        });
        DataAccessConstants.COMMAND = "COMMAND";
        DataAccessConstants.INPUT_DELIMITER = "@";
    }

    /**
     * @throws java.lang.Exception
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        // delete all the data
        Connection conn = ds.getConnection();
        PreparedStatement ps = null;
        try {

            ps = conn.prepareStatement("delete from coder_referral");
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement("delete from rating where coder_id>=900000000");
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement("delete from coder where coder_id>=900000000");
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement("delete from user where user_id>=900000000");
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Prepare data.
     * @param n number of data
     * @throws Exception to JUnit
     */
    private void prepareData(int n) throws Exception {
        Connection conn = ds.getConnection();
        PreparedStatement ps = null;
        try {

            // first create the user whose referral list is going to be retrieved
            ps = conn.prepareStatement("insert into user (user_id, status, handle) values (?, 'A', 'root')");
            ps.setLong(1, 900000000);
            ps.executeUpdate();
            ps.close();

            ps = conn.prepareStatement("insert into coder (coder_id) values (?)");
            ps.setLong(1, 900000000);
            ps.executeUpdate();
            ps.close();

            // then create N referral coders
            for (int i = 1; i <= n; i++) {
                ps = conn.prepareStatement("insert into user (user_id, status, handle) values (?, 'A', ?)");
                ps.setLong(1, 900000000 + i);
                ps.setString(2, "900000000" + i);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement("insert into coder (coder_id) values (?)");
                ps.setLong(1, 900000000 + i);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement("insert into coder_referral (coder_id, referral_id, reference_id) values (?, 40, ?)");
                ps.setLong(1, 900000000 + i);
                ps.setLong(2, 900000000);
                ps.executeUpdate();
                ps.close();

                ps = conn.prepareStatement("insert into rating (coder_id, rating) values (?, ?)");
                ps.setLong(1, 900000000 + i);
                ps.setInt(2, i);
                ps.executeUpdate();
                ps.close();
            }
        } catch (Exception e) {
            fail(e.getMessage());
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    /**
     * Test method for {@link com.topcoder.web.reg.actions.miscellaneous.ViewReferralsAction#execute()}.
     * @throws Exception to JUnit
     */
    public void testExecuteN() throws Exception {
        this.prepareData(N);
        // now try to get the referral list
        action.execute();

        // get the list and check
        long start = System.currentTimeMillis();
        List<ReferralData> users = action.getReferrals();
        long end = System.currentTimeMillis();
        System.err.println("Run get referrals took " + (end - start) + "ms for " + N + " referrals.");

        assertEquals("There should be " + N + " data", N, users.size());

        for (int i = 1; i <= N; i++) {
            ReferralData data = users.get(i - 1);
            assertEquals("The id is wrong", 900000000 + i, data.getCoderId());
            assertEquals("The rating is wrong", i, data.getRating());
            assertEquals("The handle is wrong", "900000000" + i, data.getHandle());
        }
    }

    /**
     * Test method for {@link com.topcoder.web.reg.actions.miscellaneous.ViewReferralsAction#execute()}.
     * @throws Exception to JUnit
     */
    public void testExecute10N() throws Exception {
        this.prepareData(10 * N);
        // now try to get the referral list
        action.execute();

        // get the list and check
        long start = System.currentTimeMillis();
        List<ReferralData> users = action.getReferrals();
        long end = System.currentTimeMillis();
        System.err.println("Run get referrals took " + (end - start) + "ms for " + 10 * N + " referrals.");

        assertEquals("There should be " + 10 * N + " data", 10 * N, users.size());

        for (int i = 1; i <= 10 * N; i++) {
            ReferralData data = users.get(i - 1);
            assertEquals("The id is wrong", 900000000 + i, data.getCoderId());
            assertEquals("The rating is wrong", i, data.getRating());
            assertEquals("The handle is wrong", "900000000" + i, data.getHandle());
        }
    }

}
