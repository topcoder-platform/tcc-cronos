package com.topcoder.service.facade.direct.stresstests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;

public class MockProjectLinkManager implements ProjectLinkManager {

    public MockProjectLinkManager() {
    }

    public void addProjectLink(long arg0, long arg1, long arg2) throws PersistenceException {
    }

    public void checkForCycle(long arg0) throws PersistenceException {
    }

    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        return new ProjectLinkType[0];
    }

    public ProjectLink[] getDestProjectLinks(long arg0) throws PersistenceException {
        return new ProjectLink[] {new ProjectLink()};
    }

    public ProjectLink[] getSourceProjectLinks(long arg0) throws PersistenceException {
        ProjectLink link = new ProjectLink();
        return new ProjectLink[] {link};
    }

    public void removeProjectLink(long arg0, long arg1, long arg2) throws PersistenceException {
    }

    public void updateProjectLinks(long arg0, long[] arg1, long[] arg2) throws PersistenceException {
    }

}