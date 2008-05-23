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
    /// Unit tests for the <c>RatingDistanceCalculator</c> class.
    /// This test fixture contains tests that validate the different methods
    /// of the <c>RatingDistanceCalculator</c> class under normal and
    /// failure conditions.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestRatingDistanceCalculator
    {
        /// <summary>
        /// An instance of <see cref="RatingDistanceCalculator"/>
        /// used in the tests.
        /// </summary>
        private RatingDistanceCalculator calculator;

        /// <summary>
        /// An instance of <see cref="Member"/> used in the tests.
        /// </summary>
        private Member member;

        /// <summary>
        /// A list of <see cref="Member"/> instances used in the tests.
        /// </summary>
        private IList<Member> relatedMembers;

        /// <summary>
        /// Sets-up the test environment prior to running each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            calculator = new RatingDistanceCalculator();

            Dictionary<CompetitionTypes, int> ratings =
                new Dictionary<CompetitionTypes,int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);

            member = new Member(1975, "ivern", ratings, "US", "ivern.jpg");

            relatedMembers = new List<Member>();
            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1750);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg"));

            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 2000);
            ratings.Add(CompetitionTypes.Development, 2000);
            relatedMembers.Add(new Member(1999, "topc", ratings, "US", "topc.jpg"));
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
        /// Tests that the class definition of <see cref="RatingDistanceCalculator"/>
        /// is correct. The class is expected to implement the <see cref="IDistanceCalculator"/>
        /// interface.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.IsTrue(typeof(IDistanceCalculator).IsAssignableFrom(
                typeof(RatingDistanceCalculator)),
                "RatingDistanceCalculator should implement IDistanceCalculator.");
        }

        /// <summary>
        /// Tests the <c>RatingDistanceCalculator()</c> constructor.
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
            // Calculate only for one competition type.
            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");
            Assert.AreEqual("0.43", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");
            Assert.AreEqual("0.00", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

            // Calculate for two types.
            results = calculator.CalculateDistance(member, relatedMembers,
                CompetitionTypes.Algorithm | CompetitionTypes.Development);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");
            Assert.AreEqual("0.21", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");
            Assert.AreEqual("0.25", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");
        }



        /// <summary>
        /// Tests the <c>CalculateDistance(Member, IList&lt;Member&gt;, CompetitionTypes)</c> method
        /// when passed valid values. The distances should be calculated for each related member.
        /// </summary>
        [Test]
        public void TestCalculateDistance2()
        {

            Dictionary<CompetitionTypes, int> ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);

            member = new Member(1975, "ivern", ratings, "US", "ivern.jpg");

            relatedMembers = new List<Member>();
            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg"));

            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Development, 2000);
            relatedMembers.Add(new Member(1999, "topc", ratings, "US", "topc.jpg"));

            // Calculate only for one competition type.
            IList<float> results = calculator.CalculateDistance(member, relatedMembers, CompetitionTypes.Algorithm);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");
            Assert.AreEqual("1.00", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");
            Assert.AreEqual("1.00", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

            // Calculate for two types.
            results = calculator.CalculateDistance(member, relatedMembers,
                CompetitionTypes.Algorithm | CompetitionTypes.Development);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");
            Assert.AreEqual("0.75", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");
            Assert.AreEqual("1.00", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");


            // Calculate for type that is not covered by the member.
            results = calculator.CalculateDistance(member, relatedMembers,
                CompetitionTypes.Development);
            Assert.IsNotNull(results, "Should not be null.");
            Assert.AreEqual(relatedMembers.Count, results.Count, "Incorrect count.");
            Assert.AreEqual("0.50", results[0].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 0.");
            Assert.AreEqual("1.00", results[1].ToString("0.00", CultureInfo.InvariantCulture),
                "Incorrect result at index 1.");

        }
    }
}
