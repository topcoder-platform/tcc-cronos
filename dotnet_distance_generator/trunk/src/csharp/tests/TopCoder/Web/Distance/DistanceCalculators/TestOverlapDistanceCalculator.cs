/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Globalization;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;


namespace TopCoder.Web.Distance.DistanceCalculators
{
    /// <summary>
    /// Unit tests for the <c>OverlapDistanceCalculator</c> class.
    /// This test fixture contains tests that validate the different methods
    /// of the <c>OverlapDistanceCalculator</c> class under normal and
    /// failure conditions.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestOverlapDistanceCalculator
    {
        /// <summary>
        /// An instance of <see cref="OverlapDistanceCalculator"/>
        /// used in the tests.
        /// </summary>
        private OverlapDistanceCalculator calculator;

        /// <summary>
        /// An instance of <see cref="Member"/> used in the tests.
        /// </summary>
        private Member member;

        /// <summary>
        /// A list of <see cref="Member"/> instances used in the tests.
        /// </summary>
        private IList<Member> relatedMembers;

        /// <summary>
        /// The ratings.
        /// </summary>
        private Dictionary<CompetitionTypes, int> ratings;

        /// <summary>
        /// Sets-up the test environment prior to running each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            calculator = new OverlapDistanceCalculator();

            ratings = new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1800);

            member = new Member(1975, "ivern", ratings, "US", "topcoder.jpg", 0, 0);

            relatedMembers = new List<Member>();

            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 234, 20));
            relatedMembers.Add(new Member(1989, "topc", ratings, "US", "topc.jpg", 234, 15));
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
        /// Tests that the class definition of <see cref="OverlapDistanceCalculator"/>
        /// is correct. The class is expected to implement the <see cref="IDistanceCalculator"/>
        /// interface.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.IsTrue(typeof(IDistanceCalculator).IsAssignableFrom(
                typeof(OverlapDistanceCalculator)),
                "OverlapDistanceCalculator should implement IDistanceCalculator.");
        }

        /// <summary>
        /// Tests the <c>OverlapDistanceCalculator()</c> constructor.
        /// An instance of the class is expected to be created with no exceptions thrown.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(calculator, "Instance should have been created.");
        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed a null member. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCalculateDistanceWithNullMember()
        {
            calculator.CalculateDistance(null, relatedMembers, CompetitionTypes.Algorithm);
        }



        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed a null list of related members. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCalculateDistanceWithNullRelatedMembers()
        {
            calculator.CalculateDistance(member, null, CompetitionTypes.Algorithm);
        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed a list of related members that is empty.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCalculateDistanceWithEmptyListOfRelatedMembers()
        {
            relatedMembers.Clear();
            calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
        }

        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed a list of related members that contains a null element.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCalculateDistanceWithNullElementInRelatedMembers()
        {
            relatedMembers.Add(null);
            calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// </summary>
        [Test]
        public void TestCalculateDistance()
        {
            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // For first member, overlap distance should be 1 - ((20 - 15) / 20) = 0.75
            Assert.AreEqual("0.75", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

            // For second member, overlap distance should be 1 - ((15 - 15) / 20) = 1.0
            Assert.AreEqual("1.00", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// In this test, some members have a wildly different overlap than others.
        /// </summary>
        [Test]
        public void TestCalculateDistance2()
        {
            relatedMembers.Clear();
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 234, 100));
            relatedMembers.Add(new Member(1989, "topc", ratings, "US", "topc.jpg", 234, 1800));
            relatedMembers.Add(new Member(1995, "shai", ratings, "US", "shai.jpg", 234, 900));

            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // For first member, overlap distance should be 1 - ((100 - 100) / 1800) = 1.00
            Assert.AreEqual("1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

            // For second member, overlap distance should be 1 - ((1800 - 100) / 1800) = 0.055555 ~ 0.06
            Assert.AreEqual("0.06", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

            // For third member, overlap distance should be 1 - ((900 - 100) / 1800) = 0.555555 ~ 0.56
            Assert.AreEqual("0.56", results[2].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 2.");

        }
    }
}
