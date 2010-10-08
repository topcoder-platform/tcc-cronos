package com.cronos.onlinereview.review.statistics.accuracytests;

import java.util.Date;

import com.topcoder.date.workdays.DefaultWorkdays;
import com.topcoder.date.workdays.Workdays;
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

	public boolean canCancel(Phase arg0) throws PhaseManagementException {
		
		return false;
	}

	public boolean canEnd(Phase arg0) throws PhaseManagementException {
		
		return false;
	}

	public boolean canStart(Phase arg0) throws PhaseManagementException {
		
		return false;
	}

	public void cancel(Phase arg0, String arg1) throws PhaseManagementException {
		

	}

	public void end(Phase arg0, String arg1) throws PhaseManagementException {
		

	}

	public PhaseHandler[] getAllHandlers() {
		
		return null;
	}

	public PhaseStatus[] getAllPhaseStatuses() throws PhaseManagementException {
		
		return null;
	}

	public PhaseType[] getAllPhaseTypes() throws PhaseManagementException {
		
		return null;
	}

	public HandlerRegistryInfo[] getHandlerRegistrationInfo(PhaseHandler arg0) {
		
		return null;
	}

	public PhaseValidator getPhaseValidator() {
		
		return null;
	}

	public Project getPhases(long arg0) throws PhaseManagementException {
		Workdays workday = new DefaultWorkdays();
		Project project = new Project(new Date(), workday);
		Phase phase1 = new Phase(project, 3);
		phase1.setPhaseType(new PhaseType(3, "phase1"));
		phase1.setId(2l);
		phase1.setScheduledEndDate(new Date());
		phase1.setScheduledStartDate(new Date());
		phase1.setActualStartDate(new Date());
		project.addPhase(phase1);
		Phase phase2 = new Phase(project, 13);
		phase2.setPhaseType(new PhaseType(13, "phase1"));
		phase2.setId(2l);
		phase2.setScheduledEndDate(new Date());
		phase2.setScheduledStartDate(new Date());
		phase2.setActualStartDate(new Date());
		project.addPhase(phase2);
		Phase phase3 = new Phase(project, 14);
		phase3.setId(2l);
		phase3.setPhaseType(new PhaseType(14, "phase1"));
		phase3.setScheduledEndDate(new Date());
		phase3.setScheduledStartDate(new Date());
		phase3.setActualStartDate(new Date());
		project.addPhase(phase3);
		Phase phase4 = new Phase(project, 17);
		phase4.setPhaseType(new PhaseType(17, "phase1"));
		phase4.setScheduledEndDate(new Date());
		phase4.setScheduledStartDate(new Date());
		phase4.setActualStartDate(new Date());
		phase4.setId(2l);
		project.addPhase(phase4);
		Phase phase5 = new Phase(project, 18);
		phase5.setPhaseType(new PhaseType(18, "phase1"));
		phase5.setScheduledEndDate(new Date());
		phase5.setScheduledStartDate(new Date());
		phase5.setActualStartDate(new Date());
		phase5.setId(2l);
		project.addPhase(phase5);
		Phase phase6 = new Phase(project, 10);
		phase6.setPhaseType(new PhaseType(10, "phase1"));
		phase6.setScheduledEndDate(new Date());
		phase6.setScheduledStartDate(new Date());
		phase6.setActualStartDate(new Date());
		phase6.setId(2l);
		project.addPhase(phase6);
		Phase phase7 = new Phase(project, 16);
		phase7.setPhaseType(new PhaseType(16, "phase1"));
		phase7.setId(2l);
		phase7.setScheduledEndDate(new Date());
		phase7.setScheduledStartDate(new Date());
		phase7.setActualStartDate(new Date());
		project.addPhase(phase7);
		Phase phase8 = new Phase(project, 17);
		phase8.setPhaseType(new PhaseType(17, "phase1"));
		phase8.setId(2l);
		phase8.setScheduledEndDate(new Date());
		phase8.setScheduledStartDate(new Date());
		phase8.setActualStartDate(new Date());
		project.addPhase(phase8);
		return project;
	}

	public Project[] getPhases(long[] arg0) throws PhaseManagementException {
		
		return null;
	}

	public void registerHandler(PhaseHandler arg0, PhaseType arg1,
			PhaseOperationEnum arg2) {
		

	}

	public void setPhaseValidator(PhaseValidator arg0) {
		

	}

	public void start(Phase arg0, String arg1) throws PhaseManagementException {
		

	}

	public PhaseHandler unregisterHandler(PhaseType arg0,
			PhaseOperationEnum arg1) {
		
		return null;
	}

	public void updatePhases(Project arg0, String arg1)
			throws PhaseManagementException {
		

	}

}
