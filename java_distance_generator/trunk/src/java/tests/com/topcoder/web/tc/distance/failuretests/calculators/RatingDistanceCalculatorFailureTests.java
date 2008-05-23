/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.failuretests.calculators;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for RatingDistanceCalculator.
 * </p>
 *
 * @author ivern, TheCois
 * @version 1.0
 */
public class RatingDistanceCalculatorFailureTests extends TestCase {
    /**
     * <p>
     * A RatingDistanceCalculator instance for testing.
     * </p>
     */
    private RatingDistanceCalculator calculator;
    
    /**
     * <p>
     * A MemberDataAccess instance for testing.
     * </p>
     */
    private MemberDataAccess memberDataAccess;
    
    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        calculator = new RatingDistanceCalculator();
        memberDataAccess = new FlatFileMemberDataAccess("test_files");
    }
    
        /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        calculator = null;
        memberDataAccess = null;
    }
    
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(RatingDistanceCalculatorFailureTests.class);
    }
    
    /**
     * <p>
     * Tests RatingDistanceCalculator#getDistance(Member,List&lt;Member&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a null coder, and expects an
     * IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDistanceNullCoder() {
        try {
            ArrayList<Member> members = new ArrayList<Member>();
            members.add(memberDataAccess.getMember(156859));
            calculator.getDistance(null, members, EnumSet.noneOf(CompetitionType.class));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests RatingDistanceCalculator#getDistance(Member,List&lt;Member&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a null member list, and expects an
     * IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDistanceNullMembers() {
        try {
            calculator.getDistance(memberDataAccess.getMember(144400),
                                   null,
                                   EnumSet.noneOf(CompetitionType.class));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests RatingDistanceCalculator#getDistance(Member,List&lt;Member&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a member list containing a null element,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDistanceNullMembersElement() {
        try {
            ArrayList<Member> members = new ArrayList<Member>();
            members.add(null);
            calculator.getDistance(memberDataAccess.getMember(144400),
                                   members,
                                   EnumSet.noneOf(CompetitionType.class));
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests RatingDistanceCalculator#getDistance(Member,List&lt;Member&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a null competition types set, and expects an
     * IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGetDistanceNullCompetitionTypes() {
        try {
            ArrayList<Member> members = new ArrayList<Member>();
            members.add(memberDataAccess.getMember(156859));
            calculator.getDistance(memberDataAccess.getMember(144400),
                                   members,
                                   null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
}