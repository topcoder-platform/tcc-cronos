/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Globalization;
using System.Collections.Generic;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;


namespace TopCoder.Web.Distance.DistanceCalculators
{
    /// <summary>
    /// Unit tests for the <c>GeographicalDistanceCalculator</c> class.
    /// This test fixture contains tests that validate the different methods
    /// of the <c>GeographicalDistanceCalculator</c> class under normal and
    /// failure conditions.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestGeographicalDistanceCalculator
    {
        /// <summary>
        /// An instance of <see cref="GeographicalDistanceCalculator"/>
        /// used in the tests.
        /// </summary>
        private GeographicalDistanceCalculator calculator;

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
            calculator = new GeographicalDistanceCalculator();

            ratings = new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1800);

            member = new Member(1975, "ivern", ratings, "US", "topcoder.jpg", 0, 0);

            relatedMembers = new List<Member>();

            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 20, 20));
            relatedMembers.Add(new Member(1989, "topc", ratings, "US", "topc.jpg", 15, 15));
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
        /// Tests that the class definition of <see cref="GeographicalDistanceCalculator"/>
        /// is correct. The class is expected to implement the <see cref="IDistanceCalculator"/>
        /// interface.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.IsTrue(typeof(IDistanceCalculator).IsAssignableFrom(
                typeof(GeographicalDistanceCalculator)),
                "GeographicalDistanceCalculator should implement IDistanceCalculator.");
        }

        /// <summary>
        /// Tests the <c>GeographicalDistanceCalculator()</c> constructor.
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
        public void TestCalculateDistanceKnownToKnown()
        {
            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // Distance should be (20 / 20) = 1.00
            Assert.AreEqual("1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

            // Distance should be (15 / 20) = 0.75
            Assert.AreEqual("0.75", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// </summary>
        [Test]
        public void TestCalculateDistanceKnownToUnknown()
        {
            member = new Member(1975, "ivern", ratings, "US", "topcoder.jpg", 100, 0);

            relatedMembers.Clear();
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", -1, 1800));

            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // Should be undefined
            Assert.AreEqual("-1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// </summary>
        [Test]
        public void TestCalculateDistanceUnknownToKnown()
        {
            member = new Member(1975, "ivern", ratings, "US", "topcoder.jpg", -1, 0);

            relatedMembers.Clear();
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 100, 1800));

            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // Should be undefined
            Assert.AreEqual("-1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

        }


        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// </summary>
        [Test]
        public void TestCalculateDistanceUnknownToUnknown()
        {
            member = new Member(1975, "ivern", ratings, "US", "topcoder.jpg", -1, 0);

            relatedMembers.Clear();
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", -1, 1800));

            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");

            // Should be undefined
            Assert.AreEqual("-1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");

        }
    }
}
