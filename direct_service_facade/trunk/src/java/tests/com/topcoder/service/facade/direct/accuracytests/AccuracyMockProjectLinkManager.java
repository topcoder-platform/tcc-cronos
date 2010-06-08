package com.topcoder.service.facade.direct.accuracytests;

import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.link.ProjectLink;
import com.topcoder.management.project.link.ProjectLinkManager;
import com.topcoder.management.project.link.ProjectLinkType;


public class AccuracyMockProjectLinkManager implements ProjectLinkManager {
	private long[] arg1;

    public AccuracyMockProjectLinkManager() {
    }

    public void addProjectLink(long arg0, long arg1, long arg2) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void checkForCycle(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public ProjectLinkType[] getAllProjectLinkTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return new ProjectLinkType[0];
    }

    public ProjectLink[] getDestProjectLinks(long arg0) throws PersistenceException {
        ProjectLink link1 = new ProjectLink();
        ProjectLink link2 = new ProjectLink();
        link1.setDestProject(new Project());
        link2.setDestProject(new Project());
        return new ProjectLink[] {link1, link2};
    }

    public ProjectLink[] getSourceProjectLinks(long arg0) throws PersistenceException {
        ProjectLink link1 = new ProjectLink();
        link1.setSourceProject(new Project());
        ProjectLink link2 = new ProjectLink();
        link2.setSourceProject(new Project());
        return new ProjectLink[] {link1, link2};
    }

    public void removeProjectLink(long arg0, long arg1, long arg2) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public void updateProjectLinks(long arg0, long[] arg1, long[] arg2) throws PersistenceException {
    	this.arg1 = arg1;

    }
    
    public long[] getProjectIds() {
    	return this.arg1;
    }

}
