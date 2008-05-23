/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using NUnit.Framework;

using TopCoder.Web.Distance;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;

namespace TopCoder.Web.Distance.FailureTests.DistanceCalculators
{
    /// <summary>
    /// Failure tests for <code>GeographicalDistanceCalculator</code>.
    /// </summary>
    ///
    /// <author>ivern</author>
    /// <author>TheCois</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2008 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class GeographicalDistanceCalculatorFailureTests
    {
        /// <summary>
        /// Represents the <code>GeographicalDistanceCalculator</code> instance used for testing.
        /// </summary>
        private IDistanceCalculator distance;

        /// <summary>
        /// Represents the <code>MemberDataAccess</code> instance used for testing.
        /// </summary>
        private IMemberDataAccess dataAccess;

        /// <summary>
        /// Represents the <code>Member</code> instance used for testing.
        /// </summary>
        private Member member;

        /// <summary>
        /// Represents the <code>IList&lt;Member&gt;</code> instance used for testing.
        /// </summary>
        private IList<Member> relatedMembers;

        /// <summary>
        /// Sets up the test environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            distance = new GeographicalDistanceCalculator();

            dataAccess = new FlatFileMemberDataAccess(@"..\..\test_files\failure\");

            member = dataAccess.GetMember(144400);

            relatedMembers = new List<Member>();
            relatedMembers.Add(dataAccess.GetMember(156859));
            relatedMembers.Add(dataAccess.GetMember(277356));
        }

        /// <summary>
        /// Cleans-up the test environment after running each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            relatedMembers.Clear();
        }

        /// <summary>
        /// Test the <code>CalculateDistance</code> method with null <code>member</code>.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCalculateDistanceWithNullMember()
        {
            distance.CalculateDistance(null, relatedMembers, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test the <code>CalculateDistance</code> method with null <code>relatedMembers</code>.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCalculateDistanceWithNullRelatedMembers()
        {
            distance.CalculateDistance(member, null, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test the <code>CalculateDistance</code> method with empty <code>relatedMembers</code>.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCalculateDistanceWithEmptyRelatedMembers()
        {
            distance.CalculateDistance(member, new List<Member>(), CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Test the <code>CalculateDistance</code> method with a null element in <code>relatedMembers</code>.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCalculateDistanceWithNullElementInRelatedMembers()
        {
            relatedMembers.Add(null);

            distance.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
        }
    }
}
