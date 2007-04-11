/**
 * Copyright (C) 2005 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.timetracker.report.accuracytests;

import com.topcoder.search.builder.filter.AndFilter;
import com.topcoder.search.builder.filter.EqualToFilter;
import com.topcoder.search.builder.filter.Filter;
import com.topcoder.search.builder.filter.GreaterThanOrEqualToFilter;
import com.topcoder.search.builder.filter.InFilter;
import com.topcoder.search.builder.filter.LessThanOrEqualToFilter;

import com.topcoder.timetracker.report.informix.InformixFilter;

import junit.framework.TestCase;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;


/**
 * The test of InformixFilter.
 *
 * @author brain_cn
 * @version 1.0
 */
public class InformixFilterAccuracyTests extends TestCase {
    /**
     * Test method for 'getFilterCompanies(long[])'
     */
    public void testGetFilterCompanies() {
        long[] idCompanies = new long[] { 1, 2, 3, 4 };
        Filter result = InformixFilter.getFilterCompanies(idCompanies);

        assertTrue("expect InFilter", result instanceof InFilter);

        // Verify result
        InFilter inFilter = (InFilter) result;
        assertEquals("incorrect name", "company id", inFilter.getName());

        List list = inFilter.getList();
        assertEquals("incorrect size", idCompanies.length, list.size());

        for (int i = 0; i < idCompanies.length; i++) {
            assertTrue("Miss company id" + idCompanies[i], list.contains(new Long(idCompanies[i])));
        }
    }

    /**
     * Test method for 'getFilterProjects(long[])'
     */
    public void testGetFilterProjects() {
        long[] idProjects = new long[] { 1, 2, 3, 4 };
        Filter result = InformixFilter.getFilterProjects(idProjects);

        assertTrue("expect InFilter", result instanceof InFilter);

        // Verify result
        InFilter inFilter = (InFilter) result;
        assertEquals("incorrect name", "project id", inFilter.getName());

        List list = inFilter.getList();
        assertEquals("incorrect size", idProjects.length, list.size());

        for (int i = 0; i < idProjects.length; i++) {
            assertTrue("Miss Project id" + idProjects[i], list.contains(new Long(idProjects[i])));
        }
    }

    /**
     * Test method for 'getFilterClients(long[])'
     */
    public void testGetFilterClients() {
        long[] idClients = new long[] { 1, 2, 3, 4 };
        Filter result = InformixFilter.getFilterClients(idClients);

        assertTrue("expect InFilter", result instanceof InFilter);

        // Verify result
        InFilter inFilter = (InFilter) result;
        assertEquals("incorrect name", "client id", inFilter.getName());

        List list = inFilter.getList();
        assertEquals("incorrect size", idClients.length, list.size());

        for (int i = 0; i < idClients.length; i++) {
            assertTrue("Miss Client id" + idClients[i], list.contains(new Long(idClients[i])));
        }
    }

//    /**
//     * Test method for 'getFilterEntryDate(Date, Date)'.  both from and to are null. expect all filter with value is 1.
//     *
//     * @throws Exception to JUnit
//     */
//    public void testGetFilterEntryDate_1() throws Exception {
//        Date from = null;
//        Date to = null;
//
//        Filter result = InformixFilter.getFilterEntryDate(from, to);
//
//        assertTrue("expect EqualToFilter", result instanceof EqualToFilter);
//
//        EqualToFilter filter = (EqualToFilter) result;
//        assertEquals("incorrect name", "all", filter.getName());
//        assertEquals("incorrect value", new Long(1), filter.getValue());
//    }

    /**
     * Test method for 'getFilterEntryDate(Date, Date)'.  from is null. expect LessThanOrEqualToFilter with to as
     * value.
     *
     * @throws Exception to JUnit
     */
    public void testGetFilterEntryDate_2() throws Exception {
        String d2 = "2007-04-15 12:02:02";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date from = null;
        Date to = formatter.parse(d2);

        Filter result = InformixFilter.getFilterEntryDate(from, to);

        assertTrue("expect LessThanOrEqualToFilter", result instanceof LessThanOrEqualToFilter);

        LessThanOrEqualToFilter filter = (LessThanOrEqualToFilter) result;
        assertEquals("incorrect name", "entry date", filter.getName());
        assertEquals("incorrect value", d2, filter.getValue());
    }

    /**
     * Test method for 'getFilterEntryDate(Date, Date)'.  to is null. expect GreaterThanOrEqualToFilter with from as
     * value.
     *
     * @throws Exception to JUnit
     */
    public void testGetFilterEntryDate_3() throws Exception {
        String d1 = "2007-03-15 12:02:02";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date from = formatter.parse(d1);
        Date to = null;

        Filter result = InformixFilter.getFilterEntryDate(from, to);

        assertTrue("expect GreaterThanOrEqualToFilter", result instanceof GreaterThanOrEqualToFilter);

        GreaterThanOrEqualToFilter filter = (GreaterThanOrEqualToFilter) result;
        assertEquals("incorrect name", "entry date", filter.getName());
        assertEquals("incorrect value", d1, filter.getValue());
    }

    /**
     * Test method for 'getFilterEntryDate(Date, Date)'.  both to and from are valid. expect AndFilter.
     *
     * @throws Exception to JUnit
     */
    public void testGetFilterEntryDate_4() throws Exception {
        String d1 = "2007-03-15 12:02:02";
        String d2 = "2007-04-15 12:02:02";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date from = formatter.parse(d1);
        Date to = formatter.parse(d2);

        Filter result = InformixFilter.getFilterEntryDate(from, to);

        assertTrue("expect AndFilter", result instanceof AndFilter);

        AndFilter filter = (AndFilter) result;
        List filters = filter.getFilters();

        // Verify filter 1
        assertTrue("expect GreaterThanOrEqualToFilter", filters.get(0) instanceof GreaterThanOrEqualToFilter);

        GreaterThanOrEqualToFilter filter1 = (GreaterThanOrEqualToFilter) filters.get(0);
        assertEquals("incorrect name", "entry date", filter1.getName());
        assertEquals("incorrect value", d1, filter1.getValue());

        // Verify filter 2
        assertTrue("expect LessThanOrEqualToFilter", filters.get(1) instanceof LessThanOrEqualToFilter);

        LessThanOrEqualToFilter filter2 = (LessThanOrEqualToFilter) filters.get(1);
        assertEquals("incorrect name", "entry date", filter2.getName());
        assertEquals("incorrect value", d2, filter2.getValue());
    }

    /**
     * Test method for 'getFilterType(long)'
     */
    public void testGetFilterType() {
        long type = 1;
        Filter result = InformixFilter.getFilterType(type);

        assertTrue("expect EqualToFilter", result instanceof EqualToFilter);

        EqualToFilter filter = (EqualToFilter) result;
        assertEquals("incorrect name", "type", filter.getName());
        assertEquals("incorrect value", new Long(type), filter.getValue());
    }

    /**
     * Test method for 'getFilterStatus(long)'
     */
    public void testGetFilterStatus() {
        long status = 1;
        Filter result = InformixFilter.getFilterStatus(status);

        assertTrue("expect EqualToFilter", result instanceof EqualToFilter);

        EqualToFilter filter = (EqualToFilter) result;
        assertEquals("incorrect name", "status", filter.getName());
        assertEquals("incorrect value", new Long(status), filter.getValue());
    }

    /**
     * Test method for 'getFilterUsernames(String[])'
     */
    public void testGetFilterUsernames() {
        String[] userNames = new String[] { "user1", "user2", "user3" };
        Filter result = InformixFilter.getFilterUsernames(userNames);

        assertTrue("expect InFilter", result instanceof InFilter);

        // Verify result
        InFilter inFilter = (InFilter) result;
        assertEquals("incorrect name", "username", inFilter.getName());

        List list = inFilter.getList();
        assertEquals("incorrect size", userNames.length, list.size());

        for (int i = 0; i < userNames.length; i++) {
            assertTrue("Miss Client id" + userNames[i], list.contains(userNames[i]));
        }
    }
}
