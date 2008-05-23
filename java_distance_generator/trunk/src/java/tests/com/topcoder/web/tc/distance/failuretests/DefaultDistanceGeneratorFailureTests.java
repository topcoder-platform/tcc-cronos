/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance.failuretests;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DefaultDistanceGenerator;
import com.topcoder.web.tc.distance.DistanceCalculator;
import com.topcoder.web.tc.distance.DistanceCalculatorException;
import com.topcoder.web.tc.distance.DistanceGeneratorException;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.DistanceWeighting;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;
import com.topcoder.web.tc.distance.weighting.EqualWeighting;

import com.topcoder.web.tc.distance.failuretests.mocks.MockDefaultDistanceGenerator;
import com.topcoder.web.tc.distance.failuretests.mocks.MockMemberDataAccess;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Test;

/**
 * <p>
 * Failure test cases for DefaultDistanceGenerator.
 * </p>
 *
 * @author ivern, TheCois
 * @version 1.0
 */
public class DefaultDistanceGeneratorFailureTests extends TestCase {
    /**
     * <p>
     * A DefaultDistanceGenerator instance for testing.
     * </p>
     */
    private DefaultDistanceGenerator defaultDistanceGenerator;
    
    /**
     * <p>
     * A MemberDataAccess instance for testing.
     * </p>
     */
    private MemberDataAccess memberDataAccess;
    
    /**
     * <p>
     * A working MemberDataAccess instance for testing.
     * </p>
     */
    private MemberDataAccess workingMemberDataAccess;
    
    /**
     * <p>
     * A DistanceWeighting instance for testing.
     * </p>
     */
    private DistanceWeighting distanceWeighting;
    
    /**
     * <p>
     * A List of DistanceCalculator for testing.
     * </p>
     */
    private List<DistanceCalculator> distanceCalculatorList;
    
    /**
     * <p>
     * An EnumSet of DistanceType for testing.
     * </p>
     */
    private EnumSet<DistanceType> distanceTypes;
    
    /**
     * <p>
     * An EnumSet of CompetitionType for testing.
     * </p>
     */
    private EnumSet<CompetitionType> competitionTypes;
    
    /**
     * <p>
     * A Member for testing.
     * </p>
     */
    private Member member;
    
    /**
     * <p>
     * A List of Members for testing.
     * </p>
     */
    private List<Member> memberList;
    
    /**
     * <p>
     * A List of Members Results for testing.
     * </p>
     */
    private List<Double> memberResultList;
    
    /**
     * <p>
     * A MockDefaultDistanceGenerator for testing.
     * </p>
     */
    private MockDefaultDistanceGenerator mockDefaultDistanceGenerator;

    /**
     * <p>
     * Setup test environment.
     * </p>
     *
     */
    protected void setUp() {
        memberDataAccess = new MockMemberDataAccess();
        workingMemberDataAccess = new FlatFileMemberDataAccess("test_files");
        
        distanceWeighting = new EqualWeighting();
        distanceCalculatorList = new ArrayList<DistanceCalculator>();
        distanceCalculatorList.add(new RatingDistanceCalculator());
        
        defaultDistanceGenerator =
            new DefaultDistanceGenerator(workingMemberDataAccess, distanceWeighting, distanceCalculatorList);
        
        distanceTypes = EnumSet.noneOf(DistanceType.class);
        competitionTypes = EnumSet.noneOf(CompetitionType.class);
        
        member = workingMemberDataAccess.getMember(144400);
        
        memberList = new ArrayList<Member>();
        memberList.add(workingMemberDataAccess.getMember(156859));
        memberList.add(workingMemberDataAccess.getMember(277356));
        
        memberResultList = new ArrayList<Double>();
        memberResultList.add(1.0);
        memberResultList.add(0.5);
        
        mockDefaultDistanceGenerator =
            new MockDefaultDistanceGenerator(workingMemberDataAccess, distanceWeighting, distanceCalculatorList);
    }

    /**
     * <p>
     * Tears down test environment.
     * </p>
     *
     */
    protected void tearDown() {
        memberDataAccess = null;
        workingMemberDataAccess = null;
        
        distanceWeighting = null;
        distanceCalculatorList = null;
        
        defaultDistanceGenerator = null;
        
        distanceTypes = null;
        competitionTypes = null;

        member = null;
        memberList = null;
        memberResultList = null;
        
        mockDefaultDistanceGenerator = null;
    }
    
    /**
     * <p>
     * Return all tests.
     * </p>
     *
     * @return all tests
     */
    public static Test suite() {
        return new TestSuite(DefaultDistanceGeneratorFailureTests.class);
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#DefaultDistanceGenerator(MemberDataAccess,DistanceWeighting,List&lt;DistanceCalculator&gt;)
     * for failure.  It tests the case that when the constructor is called with a null weight member data access object,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorNullMemberDataAccess() {
        try {
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(null, distanceWeighting, distanceCalculatorList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#DefaultDistanceGenerator(MemberDataAccess,DistanceWeighting,List&lt;DistanceCalculator&gt;)
     * for failure.  It tests the case that when the constructor is called with a null distance weighting,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorNullDistanceWeighting() {
        try {
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(memberDataAccess, null, distanceCalculatorList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#DefaultDistanceGenerator(MemberDataAccess,DistanceWeighting,List&lt;DistanceCalculator&gt;)
     * for failure.  It tests the case that when the constructor is called with a null distance calculator list,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorNullDistanceCalculatorsList() {
        try {
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(memberDataAccess, distanceWeighting, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#DefaultDistanceGenerator(MemberDataAccess,DistanceWeighting,List&lt;DistanceCalculator&gt;)
     * for failure.  It tests the case that when the constructor is called with an empty distance calculator list,
     * and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorEmptyDistanceCalculatorsList() {
        try {
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(memberDataAccess, distanceWeighting, new ArrayList<DistanceCalculator>());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#DefaultDistanceGenerator(MemberDataAccess,DistanceWeighting,List&lt;DistanceCalculator&gt;)
     * for failure.  It tests the case that when the constructor is called with a distance calculator list containing
     * null values, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testConstructorDistanceCalculatorsListWithNullValues() {
        try {
            distanceCalculatorList.add(null);
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(memberDataAccess, distanceWeighting, distanceCalculatorList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#generateDistance(long,EnumSet&lt;DistanceType&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a coder id of zero, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateDistanceZeroCoderId() {
        try {
            defaultDistanceGenerator.generateDistance(0, distanceTypes, competitionTypes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#generateDistance(long,EnumSet&lt;DistanceType&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a negative coder id, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateDistanceNegativeCoderId() {
        try {
            defaultDistanceGenerator.generateDistance(-1, distanceTypes, competitionTypes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#generateDistance(long,EnumSet&lt;DistanceType&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method is called with a null distance types set, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateDistanceNullDistanceTypes() {
        try {
            defaultDistanceGenerator.generateDistance(1, null, competitionTypes);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#generateDistance(long,EnumSet&lt;DistanceType&gt;,EnumSet&lt;CompetitionType&gt;)
     * for failure.  It tests the case that when the method throws a DistanceGeneratorException due to an exception
     * occurring during processing.
     * </p>
     * @throws Exception to JUnit
     */
    public void testGenerateDistanceDistanceGeneratorException() {
        try {
            DefaultDistanceGenerator ddg =
                new DefaultDistanceGenerator(memberDataAccess, distanceWeighting, distanceCalculatorList);
            ddg.generateDistance(1, distanceTypes, competitionTypes);
            fail("DistanceGeneratorException expected.");
        } catch (DistanceGeneratorException dge) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with a null from member, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultNullFromMember() {
        try {
            mockDefaultDistanceGenerator.writeResult(null, memberList, memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with a null member list, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultNullMembers() {
        try {
            mockDefaultDistanceGenerator.writeResult(member, null, memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with an empty member list, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultEmptyMembers() {
        try {
            mockDefaultDistanceGenerator.writeResult(member, new ArrayList<Member>(), memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with a member list containing null
     * values, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultMembersWithNullValues() {
        try {
            memberList.add(null);
            mockDefaultDistanceGenerator.writeResult(member, memberList, memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with a null member results list, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultNullMemberResults() {
        try {
            mockDefaultDistanceGenerator.writeResult(member, memberList, null);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with an empty member results list, and expects
     *  an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultEmptyMemberResults() {
        try {
            mockDefaultDistanceGenerator.writeResult(member, memberList, new ArrayList<Double>());
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with a member results list
     * containing null values, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultMemberResultsWithNullValues() {
        try {
            memberResultList.add(null);
            mockDefaultDistanceGenerator.writeResult(member, memberList, memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    /**
     * <p>
     * Tests DefaultDistanceGenerator#writeResult(Member,List&lt;Member&gt;,List&lt;Double&gt;)
     * for failure.  It tests the case that when the method is called with differently sizes member
     * and member results lists, and expects an IllegalArgumentException.
     * </p>
     * @throws Exception to JUnit
     */
    public void testWriteResultMemberResultsDifferentlySized() {
        try {
            memberResultList.add(3.0);
            mockDefaultDistanceGenerator.writeResult(member, memberList, memberResultList);
            fail("IllegalArgumentException expected.");
        } catch (IllegalArgumentException iae) {
            // Good.
        }
    }
    
    // Skipping creation of a test case to trigger a DistanceGenerationException in writeResult, this exception will
    // only happen if XML generation fails, which is very difficult to reproduce as it'll require either making the schema
    // impossible to find, breaking it, or otherwise interfering with Java's XML libraries.  Since the schema resides on a
    // TopCoder server, it's out of scope for these tests to meddle with it.
}
