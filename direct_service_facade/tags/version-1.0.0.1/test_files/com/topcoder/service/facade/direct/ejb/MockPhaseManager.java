package com.topcoder.service.facade.direct.ejb;

import java.util.Date;

import com.topcoder.management.phase.HandlerRegistryInfo;
import com.topcoder.management.phase.PhaseHandler;
import com.topcoder.management.phase.PhaseManagementException;
import com.topcoder.management.phase.PhaseManager;
import com.topcoder.management.phase.PhaseOperationEnum;
import com.topcoder.management.phase.PhaseValidator;
import com.topcoder.project.phases.Phase;
import com.topcoder.project.phases.PhaseStatus;
import com.topcoder.project.phases.PhaseType;
import com.topcoder.project.phases.Project;

public class MockPhaseManager implements PhaseManager {

    public MockPhaseManager() {
    }

    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean canStart(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub

    }

    public void end(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub

    }

    public PhaseHandler[] getAllHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseValidator getPhaseValidator() {
        // TODO Auto-generated method stub
        return null;
    }

    public Project getPhases(long id) throws PhaseManagementException {
        Project project = new Project();
        Phase reviewPhase = new Phase();
        reviewPhase.setPhaseType(new PhaseType(3, "Review"));
        reviewPhase.setScheduledEndDate(new Date());

        if (id == 2) {
            reviewPhase.setPhaseStatus(PhaseStatus.OPEN);
        } else if (id == 3) {
            reviewPhase.setPhaseStatus(PhaseStatus.SCHEDULED);
        }

        project.addPhase(reviewPhase);
        return project;
    }

    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
        // TODO Auto-generated method stub

    }

    public void setPhaseValidator(PhaseValidator arg0) {
        // TODO Auto-generated method stub

    }

    public void start(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub

    }

    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub

    }

}
