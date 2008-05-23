/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Xml;
using System.Collections.Generic;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.XmlGenerators
{
    /// <summary>
    /// Unit tests for the <c>DefaultXmlGenerator</c> class.
    /// This test fixture contains tests that validate the different methods
    /// of the <c>DefaultXmlGenerator</c> class under normal and
    /// failure conditions.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestDefaultXmlGenerator
    {
        /// <summary>
        /// An instance of <see cref="DefaultXmlGenerator"/>
        /// used in the tests.
        /// </summary>
        private DefaultXmlGenerator generator;

        /// <summary>
        /// An instance of <see cref="Member"/> used in the tests.
        /// </summary>
        private Member member;

        /// <summary>
        /// A list of <see cref="Member"/> instances used in the tests.
        /// </summary>
        private IList<Member> relatedMembers;

        /// <summary>
        /// A list of distances for each member.
        /// </summary>
        private IList<float> distances;

        /// <summary>
        /// Sets-up the test environment prior to running each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            generator = new DefaultXmlGenerator();

            Dictionary<CompetitionTypes, int> ratings =
                new Dictionary<CompetitionTypes,int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);

            member = new Member(1975, "ivern", ratings, "US", "ivern.jpg", 0, 0);

            relatedMembers = new List<Member>();
            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1750);
            ratings.Add(CompetitionTypes.Design, 1000);
            ratings.Add(CompetitionTypes.Development, 1000);
            relatedMembers.Add(new Member(1981, "mess", ratings, "US", "mess.jpg", 10, 10));

            ratings =
                new Dictionary<CompetitionTypes, int>();
            ratings.Add(CompetitionTypes.Algorithm, 1000);
            ratings.Add(CompetitionTypes.Design, 2000);
            ratings.Add(CompetitionTypes.Development, 2000);
            relatedMembers.Add(new Member(1999, "topc", ratings, "US", "topc.jpg", 20, 20));

            distances = new List<float>();
            distances.Add(0.5f);
            distances.Add(0.37f);
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
        /// Tests that the class definition of <see cref="DefaultXmlGenerator"/>
        /// is correct. The class is expected to implement the <see cref="IXmlGenerator"/>
        /// interface.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.IsTrue(typeof(IXmlGenerator).IsAssignableFrom(
                typeof(DefaultXmlGenerator)),
                "DefaultXmlGenerator should implement IXmlGenerator.");
        }

        /// <summary>
        /// Tests the <c>DefaultXmlGenerator()</c> constructor.
        /// An instance of the class is expected to be created with no exceptions thrown.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(generator, "Instance should have been created.");
        }


        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed a null member. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullMember()
        {
            generator.GenerateXml(null, relatedMembers, distances);
        }



        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed a null list of related members. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullRelatedMembers()
        {
            generator.GenerateXml(member, null, distances);
        }


        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed a null list of distances. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullDistances()
        {
            generator.GenerateXml(member, relatedMembers, null);
        }

        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed a list of related members that is empty.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateXmlWithEmptyListOfRelatedMembers()
        {
            relatedMembers.Clear();
            generator.GenerateXml(member, relatedMembers, distances);
        }

        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed a list of related members that contains a null element.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateXmlWithNullElementInRelatedMembers()
        {
            relatedMembers.Add(null);
            generator.GenerateXml(member, relatedMembers, distances);
        }

        /// <summary>
        /// Tests the <c>GenerateXml(Member, IList&lt;Member&gt;, IList&lt;float&gt;)</c> method
        /// when passed valid values. The XML should be generated.
        /// </summary>
        [Test]
        public void TestGenerateXml()
        {
            // Calculate only for one competition type.
            string results = generator.GenerateXml(member, relatedMembers, distances);
            Assert.IsNotNull(results, "Should not be null.");
            XmlDocument xdoc = new XmlDocument();
            xdoc.LoadXml(results);

            XmlDocument xdocExpected = new XmlDocument();
            xdocExpected.Load("../../test_files/ExpectedOutput1.xml");

            Assert.AreEqual(xdocExpected.OuterXml, xdoc.OuterXml,
                "Incorrect output.");

            // Generate when one of the distances is negative.
            distances[0] = -1;
            results = generator.GenerateXml(member, relatedMembers, distances);
            Assert.IsNotNull(results, "Should not be null.");

            xdoc = new XmlDocument();
            xdoc.LoadXml(results);

            xdocExpected = new XmlDocument();
            xdocExpected.Load("../../test_files/ExpectedOutput2.xml");
            Assert.AreEqual(xdocExpected.OuterXml, xdoc.OuterXml,
                "Incorrect output.");
        }
    }
}
