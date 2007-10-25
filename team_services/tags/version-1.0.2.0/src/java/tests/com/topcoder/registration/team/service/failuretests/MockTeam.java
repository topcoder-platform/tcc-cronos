package com.topcoder.registration.team.service.failuretests;

import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamPosition;

public class MockTeam implements Team {

    private TeamHeader header = null;
    private TeamPosition[] positions = null;
    public MockTeam() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TeamHeader getTeamHeader() {
        return header;
    }

    public void setTeamHeader(TeamHeader teamHeader) {
        this.header = teamHeader;

    }

    public TeamPosition[] getPositions() {
        if (positions != null) {
            return positions;
        }
        TeamPosition position = new TeamPosition();
        position.setMemberResourceId(1);
        return new TeamPosition[]{position};
    }

    public void setPositions(TeamPosition[] positions) {
        this.positions = positions;

    }

}
