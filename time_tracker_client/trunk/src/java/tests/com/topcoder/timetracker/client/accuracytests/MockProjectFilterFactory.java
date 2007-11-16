package com.topcoder.timetracker.client.accuracytests;

import java.util.Date;

import com.topcoder.search.builder.filter.Filter;
import com.topcoder.timetracker.project.EntryType;
import com.topcoder.timetracker.project.ProjectFilterFactory;
import com.topcoder.timetracker.project.StringMatchType;

public class MockProjectFilterFactory implements ProjectFilterFactory {

    public Filter createClientIdFilter(long clientId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createCompanyIdFilter(long companyId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createContainsEntryFilter(long entryId, EntryType type) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createContainsProjectManagerFilter(long managerId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createContainsProjectWorkerFilter(long workerId) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createEndDateFilter(Date rangeStart, Date rangeEnd) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createIsActiveFilter(boolean isActive) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createNameFilter(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createStartDateFilter(Date rangeStart, Date rangeEnd) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createCreationDateFilter(Date rangeStart, Date rangeEnd) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createCreationUserFilter(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createModificationDateFilter(Date rangeStart, Date rangeEnd) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createModificationUserFilter(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createNameFilter(StringMatchType matchType, String name) {
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createModificationUserFilter(StringMatchType matchType, String username){
        // TODO Auto-generated method stub
        return null;
    }

    public Filter createCreationUserFilter(StringMatchType matchType, String username){
        // TODO Auto-generated method stub
        return null;
    }
}
