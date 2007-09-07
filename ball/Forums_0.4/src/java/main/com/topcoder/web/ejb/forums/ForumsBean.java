package com.topcoder.web.ejb.forums;

import com.jivesoftware.base.*;
import com.jivesoftware.forum.*;
import com.topcoder.shared.util.DBMS;
import com.topcoder.shared.util.logging.Logger;
import com.topcoder.web.ejb.BaseEJB;
import com.topcoder.web.forums.ForumConstants;
import com.topcoder.web.forums.model.TCAuthToken;

import javax.ejb.EJBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * This class handles interaction with the Jive database.
 * Please update the code if you know of a way to use Jive objects remotely, instead of using primitives.
 * Keep in mind that most Jive proxy objects are not serializable.
 *
 * @author mtong
 */

public class ForumsBean extends BaseEJB {

    private static Logger log = Logger.getLogger(ForumsBean.class);
    private static ForumFactory forumFactory = ForumFactory.getInstance(new TCAuthToken(1));

    public String[] getCategoryNames() {
        ForumFactory forumFactory = ForumFactory.getInstance(AuthFactory.getAnonymousAuthToken());
        Iterator it = forumFactory.getRootForumCategory().getCategories();
        ArrayList listNames = new ArrayList();
        while (it.hasNext()) {
            listNames.add(((ForumCategory) it.next()).getName());
        }
        return (String[]) listNames.toArray(new String[listNames.size()]);
    }

    public int getThreadMessageCount(int threadID) {
        return this.selectInt("jivemessage",
                "count(*)",
                new String[]{"threadid"},
                new String[]{String.valueOf(threadID)},
                DBMS.FORUMS_DATASOURCE_NAME).intValue();
    }

    public void closeCategory(long categoryID) throws ForumCategoryNotFoundException, UnauthorizedException {
        ForumCategory category = forumFactory.getForumCategory(categoryID);
        category.setProperty(ForumConstants.PROPERTY_ARCHIVAL_STATUS, String.valueOf(ForumConstants.PROPERTY_ARCHIVAL_STATUS_CLOSED));
    }

    public boolean canReadCategory(long userID, long categoryID) throws ForumCategoryNotFoundException {
        ForumFactory userForumFactory = ForumFactory.getInstance(new TCAuthToken(userID));
        ForumCategory category = userForumFactory.getForumCategory(categoryID);
        return category.isAuthorized(ForumPermissions.READ_FORUM);
    }

    public void createCategoryWatch(long userID, long categoryID) throws ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException {
        WatchManager watchManager = forumFactory.getWatchManager();
        User user = forumFactory.getUserManager().getUser(userID);
        ForumCategory category = forumFactory.getForumCategory(categoryID);
        if (!watchManager.isWatched(user, category)) {
            watchManager.createWatch(user, category);
        }
    }

    public void createCategoryWatches(long userID, long[] categoryIDs) throws ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException {
        WatchManager watchManager = forumFactory.getWatchManager();
        User user = forumFactory.getUserManager().getUser(userID);
        for (int i = 0; i < categoryIDs.length; i++) {
            ForumCategory category = forumFactory.getForumCategory(categoryIDs[i]);
            if (!watchManager.isWatched(user, category)) {
                watchManager.createWatch(user, category);
            }
        }
    }

    public void deleteCategoryWatch(long userID, long categoryID) throws ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException {
        WatchManager watchManager = forumFactory.getWatchManager();
        User user = forumFactory.getUserManager().getUser(userID);
        ForumCategory category = forumFactory.getForumCategory(categoryID);
        Watch watch = watchManager.getWatch(user, category);
        if (watch != null) {
            watchManager.deleteWatch(watch);
        }
    }

    public void deleteOrphanedAttachments() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DBMS.getConnection(DBMS.FORUMS_DATASOURCE_NAME);
            ps = conn.prepareStatement(
                    "delete from jiveattachment where objectid is null");
            ps.executeUpdate();
            log.info("Successfully deleted orphaned attachments.");
        } catch (SQLException e) {
            DBMS.printSqlException(true, e);
            throw new EJBException(e.getMessage());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        } finally {
            close(ps);
            close(conn);
        }
    }
    
    public String getUserPassword(long userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBMS.getConnection(DBMS.FORUMS_DATASOURCE_NAME);
            ps = conn.prepareStatement(
                    "select passwordhash from jiveuser where userid = ?");
            ps.setLong(1, userID);
            rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
        } catch (SQLException e) {
            DBMS.printSqlException(true, e);
            throw new EJBException(e.getMessage());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
    }
    
    public ArrayList<String> getTimezones() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBMS.getConnection(DBMS.FORUMS_DATASOURCE_NAME);
            ps = conn.prepareStatement(
                    "select timezone_desc from timezone");
            rs = ps.executeQuery();
            
            ArrayList<String> timezones = new ArrayList<String>();
            while (rs.next()) {
                timezones.add(rs.getString(1));
            }
            return timezones;
        } catch (SQLException e) {
            DBMS.printSqlException(true, e);
            throw new EJBException(e.getMessage());
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        } finally {
            close(rs);
            close(ps);
            close(conn);
        }
    }

    private void logException(Exception e, String msg) {
        log.info("*** " + msg + ": " + e);
        StackTraceElement[] ste = e.getStackTrace();
        for (int i = 0; i < ste.length; i++) {
            log.info(ste[i]);
        }
    }
}

class JiveForumCategoryComparator implements Comparator {
    private String sortField;
    private int sortOrder;

    public JiveForumCategoryComparator(String sortField, int sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public final int compare(Object o1, Object o2) {
        ForumCategory c1 = (ForumCategory) o1;
        ForumCategory c2 = (ForumCategory) o2;

        int retVal = 0;
        if (sortField.equals("id")) {
            retVal = String.valueOf(c1.getID()).compareTo(String.valueOf(c2.getID()));
        } else if (sortField.equals("name")) {
            retVal = String.valueOf(c1.getName()).compareTo(String.valueOf(c2.getName()));
        }
        if (sortOrder == ResultFilter.DESCENDING) {
            retVal = -retVal;
        }
        return retVal;
    }
}

class JiveGroupComparator implements Comparator {
    private String sortField;
    private int sortOrder;

    public JiveGroupComparator(String sortField, int sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    public final int compare(Object o1, Object o2) {
        Group g1 = (Group) o1;
        Group g2 = (Group) o2;

        int retVal = 0;
        if (sortField.equals("id")) {
            retVal = String.valueOf(g1.getID()).compareTo(String.valueOf(g2.getID()));
        } else if (sortField.equals("name")) {
            retVal = g1.getName().compareTo(g2.getName());
        } else if (sortField.equals("description")) {
            retVal = g1.getDescription().compareTo(g2.getDescription());
        }
        if (sortOrder == ResultFilter.DESCENDING) {
            retVal = -retVal;
        }
        return retVal;
    }
}
