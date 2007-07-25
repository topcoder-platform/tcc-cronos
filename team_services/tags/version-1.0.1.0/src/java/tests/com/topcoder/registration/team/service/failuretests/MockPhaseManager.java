package com.topcoder.registration.team.service.failuretests;

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
        super();
        // TODO Auto-generated constructor stub
    }

    public void updatePhases(Project arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub
        
    }

    public Project getPhases(long arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getPhases(long[] arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean canStart(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public void start(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub
        
    }

    public boolean canEnd(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public void end(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub
        
    }

    public boolean canCancel(Phase arg0) throws PhaseManagementException {
        // TODO Auto-generated method stub
        return false;
    }

    public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
        // TODO Auto-generated method stub
        
    }

    public void registerHandler(PhaseHandler arg0, PhaseType arg1, PhaseOperationEnum arg2) {
        // TODO Auto-generated method stub
        
    }

    public PhaseHandler unregisterHandler(PhaseType arg0, PhaseOperationEnum arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public PhaseHandler[] getAllHandlers() {
        // TODO Auto-generated method stub
        return null;
    }

    public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setPhaseValidator(PhaseValidator arg0) {
        // TODO Auto-generated method stub
        
    }

    public PhaseValidator getPhaseValidator() {
        // TODO Auto-generated method stub
        return null;
    }

}
