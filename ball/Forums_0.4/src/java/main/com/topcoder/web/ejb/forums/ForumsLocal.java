package com.topcoder.web.ejb.forums;

import java.util.ArrayList;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

import com.jivesoftware.base.UnauthorizedException;
import com.jivesoftware.base.UserNotFoundException;
import com.jivesoftware.forum.ForumCategoryNotFoundException;

/**
 * @author mtong
 */
public interface ForumsLocal extends EJBLocalObject {

    public String[] getCategoryNames() throws EJBException;
    
    public int getThreadMessageCount(int threadID) throws EJBException;

    public void closeCategory(long categoryID) throws EJBException, ForumCategoryNotFoundException, UnauthorizedException;
    
    public boolean canReadCategory(long userID, long categoryID) throws EJBException, ForumCategoryNotFoundException;
    
    public void createCategoryWatch(long userID, long categoryID) throws EJBException, ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException;
    
    public void createCategoryWatches(long userID, long[] categoryIDs) throws EJBException, ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException;
    
    public void deleteCategoryWatch(long userID, long categoryID) throws EJBException, ForumCategoryNotFoundException, UnauthorizedException, UserNotFoundException;
    
    public void deleteOrphanedAttachments() throws EJBException;
    
    public String getUserPassword(long userID) throws EJBException;
    
    public ArrayList<String> getTimezones() throws EJBException;
}