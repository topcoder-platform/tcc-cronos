/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.tc.distance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import junit.framework.TestCase;

import com.topcoder.web.tc.distance.calculators.GeographicalDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.MatchOverlapDistanceCalculator;
import com.topcoder.web.tc.distance.calculators.RatingDistanceCalculator;
import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.data.Member;
import com.topcoder.web.tc.distance.data.MemberDataAccess;
import com.topcoder.web.tc.distance.data.MemberTestHelper;
import com.topcoder.web.tc.distance.weighting.EqualWeighting;

/**
 * <p>
 * Unit test cases for <code>DefaultDistanceGenerator</code> class.
 * </p>
 *
 * @author romanoTC
 * @version 1.0
 */
public class DefaultDistanceGeneratorTest extends TestCase {

    /**
     * Constant MemberDataAccess used in testing.
     */
    private static final MemberDataAccess MEMBER_DATA_ACCESS = new FlatFileMemberDataAccess("test_files");

    /**
     * Failure test for constructor <br>
     * Cause: memberDataAccess argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullMemberDataAccess() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();

        try {
            new DefaultDistanceGenerator(null, new EqualWeighting(), Arrays.asList(calculator));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [memberDataAccess] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: distanceWeighting argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullDistanceWeighting() {
        DistanceCalculator calculator = new GeographicalDistanceCalculator();

        try {
            new DefaultDistanceGenerator(MEMBER_DATA_ACCESS, null, Arrays.asList(calculator));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distanceWeighting] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: distanceCalculators argument is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullDistanceCalculators() {
        try {
            new DefaultDistanceGenerator(MEMBER_DATA_ACCESS, new EqualWeighting(), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distanceCalculators] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: distanceCalculators argument is empty. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_EmptyDistanceCalculators() {
        try {
            new DefaultDistanceGenerator(MEMBER_DATA_ACCESS, new EqualWeighting(), new ArrayList<DistanceCalculator>());

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distanceCalculators] argument cannot be empty.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for constructor <br>
     * Cause: distanceCalculators argument contains null elements. <br>
     * Exception: IllegalArgumentException.
     */
    public void testCtor_NullDistanceCalculatorsElement() {
        List<DistanceCalculator> list = new ArrayList<DistanceCalculator>();
        list.add(null);

        try {
            new DefaultDistanceGenerator(MEMBER_DATA_ACCESS, new EqualWeighting(), list);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect",
                "The [distanceCalculators] argument cannot have null elements.", ex.getMessage());
        }
    }

    /**
     * Accuracy test for constructor <br>
     * Target: assert the created object is not null. Results will be asserted later.
     */
    public void testCtor() {
        assertNotNull("Should not be null", getDefaultDistanceGenerator());
    }

    /**
     * Accuracy test for writeResult <br>
     * Target: assert the XML string is correctly returned.
     */
    public void testWriteResult() {
        String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<Distance xmlns=\"http://www.topcoder.com/Distance\">"
            + "<map><coder><coder_id>100</coder_id><handle>handle1</handle>"
            + "<rating>100</rating><image>image1</image><overlap>0</overlap>"
            + "<country>USA</country><distance>0.0</distance></coder><coder><coder_id>200</coder_id>"
            + "<handle>handle2</handle><rating>200</rating><image>image2</image><overlap>0</overlap>"
            + "<country>Russia</country><distance>0.785</distance></coder></map></Distance>";

        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        Member fromMember = MemberTestHelper.getMember(100, "handle1",
            new CompetitionType[] {CompetitionType.ALGORITHM}, new int[] {100}, "USA", "image1");

        Member member = MemberTestHelper.getMember(200, "handle2", new CompetitionType[] {CompetitionType.ALGORITHM},
            new int[] {200}, "Russia", "image2");

        List<Double> memberResults = new ArrayList<Double>();
        memberResults.add(0.785);

        String result = generator.writeResult(fromMember, Arrays.asList(member), memberResults);

        assertEquals("XML result is incorrect", expected, result);
    }

    /**
     * Failure test for writeResult <br>
     * Cause: argument fromMember is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWriteResult_NullFromMember() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        Member member = MemberTestHelper.getMember(200, "handle2", new CompetitionType[] {CompetitionType.ALGORITHM},
            new int[] {200}, "Russia", "image2");

        List<Double> memberResults = new ArrayList<Double>();
        memberResults.add(0.785);

        try {
            generator.writeResult(null, Arrays.asList(member), memberResults);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [fromMember] argument cannot be null.",
                ex.getMessage());
        }
    }

    /**
     * Failure test for writeResult <br>
     * Cause: argument members is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWriteResult_NullMembers() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        Member fromMember = MemberTestHelper.getMember(100, "handle1",
            new CompetitionType[] {CompetitionType.ALGORITHM}, new int[] {100}, "USA", "image1");

        List<Double> memberResults = new ArrayList<Double>();
        memberResults.add(0.785);

        try {
            generator.writeResult(fromMember, null, memberResults);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [members] argument cannot be null.", ex.getMessage());
        }
    }

    /**
     * Failure test for writeResult <br>
     * Cause: argument memberResults is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testWriteResult_NullMemberResults() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        Member fromMember = MemberTestHelper.getMember(100, "handle1",
            new CompetitionType[] {CompetitionType.ALGORITHM}, new int[] {100}, "USA", "image1");

        Member member = MemberTestHelper.getMember(200, "handle2", new CompetitionType[] {CompetitionType.ALGORITHM},
            new int[] {200}, "Russia", "image2");

        try {
            generator.writeResult(fromMember, Arrays.asList(member), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [memberResults] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for generateDistance <br>
     * Cause: argument coderId is negative. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGenerateDistance_InvalidCoderId() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        try {
            generator
                .generateDistance(-1, EnumSet.of(DistanceType.GEOGRAPHICAL), EnumSet.of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [coderId] argument cannot be negative.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for generateDistance <br>
     * Cause: argument distanceTypes is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGenerateDistance_NullDistanceTypes() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        try {
            generator.generateDistance(10, null, EnumSet.of(CompetitionType.ALGORITHM));

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [distanceTypes] argument cannot be null.", ex
                .getMessage());
        }
    }

    /**
     * Failure test for generateDistance <br>
     * Cause: argument compTypes is null. <br>
     * Exception: IllegalArgumentException.
     */
    public void testGenerateDistance_NullCompTypes() {
        DefaultDistanceGenerator generator = getDefaultDistanceGenerator();

        try {
            generator.generateDistance(10, EnumSet.of(DistanceType.GEOGRAPHICAL), null);

            fail("IllegalArgumentException is expected.");
        } catch (IllegalArgumentException ex) {
            assertEquals("Exception message is incorrect", "The [compTypes] argument cannot be null.", ex.getMessage());
        }
    }

    /**
     * Accuracy test for generateDistance <br>
     * Target: assert the XML string is correctly returned bases on the given user.
     *
     * @throws Exception to JUnit.
     */
    public void testGenerateDistance() throws Exception {
        List<DistanceCalculator> distanceCalculators = new ArrayList<DistanceCalculator>();
        distanceCalculators.add(new RatingDistanceCalculator());
        distanceCalculators.add(new MatchOverlapDistanceCalculator());
        distanceCalculators.add(new GeographicalDistanceCalculator());
        MemberDataAccess dataAccess = new FlatFileMemberDataAccess("test_files/");
        DistanceGenerator generator = new DefaultDistanceGenerator(dataAccess, new EqualWeighting(),
            distanceCalculators);

        String xml = generator.generateDistance(144400, EnumSet.of(DistanceType.RATING), EnumSet
            .of(CompetitionType.ALGORITHM));
        assertNotNull("The result XML cannot be null", xml);

        // Assert the first handle is tomek's
        assertTrue("The first handle should be tomek's", xml.startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
            + "<Distance xmlns=\"http://www.topcoder.com/Distance\"><map><coder>"
            + "<coder_id>144400</coder_id><handle>tomek</handle><rating>3276</rating>"));

        // Assert other coders are also present
        assertTrue("Sputnik should be present", xml.contains("<handle>Sputnik</handle>"));
        assertTrue("Sputnik should be present", xml.contains("<handle>Sputnik</handle>"));
        assertTrue("Tomy should be present", xml.contains("<handle>Tomy</handle>"));
        assertTrue("Vovik should be present", xml.contains("<handle>Vovik</handle>"));
        assertTrue("PaulJefferys should be present", xml.contains("<handle>PaulJefferys</handle>"));

        System.out.println(xml);
    }

    /**
     * Return a constant DefaultDistanceGenerator object.
     *
     * @return a constant DefaultDistanceGenerator object.
     */
    private DefaultDistanceGenerator getDefaultDistanceGenerator() {
        List<DistanceCalculator> list = new ArrayList<DistanceCalculator>();
        list.add(new RatingDistanceCalculator());

        DefaultDistanceGenerator generator = new DefaultDistanceGenerator(MEMBER_DATA_ACCESS, new EqualWeighting(),
            list);
        return generator;
    }

}
