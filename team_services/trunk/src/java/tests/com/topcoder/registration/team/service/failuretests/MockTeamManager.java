package com.topcoder.registration.team.service.failuretests;

import com.topcoder.management.team.InvalidPositionException;
import com.topcoder.management.team.InvalidTeamException;
import com.topcoder.management.team.PositionRemovalException;
import com.topcoder.management.team.Team;
import com.topcoder.management.team.TeamHeader;
import com.topcoder.management.team.TeamManager;
import com.topcoder.management.team.TeamPersistenceException;
import com.topcoder.management.team.TeamPosition;
import com.topcoder.management.team.UnknownEntityException;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;

public class MockTeamManager implements TeamManager {

    public MockTeamManager() {
    }

    public void createTeam(TeamHeader arg0, long arg1) throws InvalidTeamException {
        if (arg0.getTeamId() == 1001) {
            throw new TeamPersistenceException("");
        }
    }

    public void updateTeam(TeamHeader arg0, long arg1) throws InvalidTeamException {
        if (arg0.getTeamId() == 1001) {
            throw new TeamPersistenceException("test");
        }
    }

    public void removeTeam(long arg0, long arg1) throws UnknownEntityException {
        // TODO Auto-generated method stub
        
    }

    public Team getTeam(long arg0) {
        if (arg0 == 1000) {
            return null;
        }
        MockTeam team = new MockTeam();
        TeamHeader header = new TeamHeader();
        if (arg0 == 1001) {
            header.setCaptainResourceId(1006);
            header.setProjectId(1005);
            team.setTeamHeader(header);
            return team;
        }
        if (arg0 == 1002) {
            TeamPosition position = new TeamPosition();
            position.setMemberResourceId(1006);
            TeamPosition[] positions = new TeamPosition[]{position};
            team.setPositions(positions);
            return team;
        }

        header.setCaptainResourceId(1007);
        header.setProjectId(1005);
        team.setTeamHeader(header);
        return team;
    }

    public TeamHeader[] findTeams(long arg0) {
        if (arg0 == 1001) {
            throw new TeamPersistenceException("");
        }
        return null;
    }

    public TeamHeader[] findTeams(long[] arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public TeamHeader[] findTeams(Filter arg0) {
        if (arg0 instanceof EqualToFilter) {
            throw new TeamPersistenceException("");
        }
        return null;
    }

    public Team getTeamFromPosition(long arg0) {
        if (arg0 == 1001) {
            return null;
        }
        if (arg0 == 1002) {
            throw new TeamPersistenceException("");
        }

        return null;
    }

    public void addPosition(long arg0, TeamPosition arg1, long arg2) throws InvalidPositionException {
        // TODO Auto-generated method stub
        
    }

    public void updatePosition(TeamPosition arg0, long arg1) throws InvalidPositionException {
        // TODO Auto-generated method stub
        
    }

    public void removePosition(long arg0, long arg1) throws UnknownEntityException, PositionRemovalException {
        // TODO Auto-generated method stub
        
    }

    public TeamPosition getPosition(long arg0) {
        if (arg0 == 1001) {
            return null;
        }
        if (arg0 == 1002) {
            throw new TeamPersistenceException("");
        }
        return null;
    }

    public TeamPosition[] findPositions(Filter arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}
