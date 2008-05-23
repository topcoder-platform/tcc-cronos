/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using NUnit.Framework;

using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.XmlGenerators;

namespace TopCoder.Web.Distance.FailureTests.XmlGenerators
{
    /// <summary>
    /// Failure tests for <code>DefaultXmlGenerator</code>.
    /// </summary>
    ///
    /// <author>ivern</author>
    /// <author>TheCois</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2008 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultXmlGeneratorFailureTests
    {
        /// <summary>
        /// Represents the <code>XmlGenerator</code> instance used for testing.
        /// </summary>
        private IXmlGenerator xmlGenerator;

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
        /// Represents the <code>IList&lt;float&gt;</code> instance used for testing.
        /// </summary>
        private IList<float> distances;

        /// <summary>
        /// Sets up the test environment.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            xmlGenerator = new DefaultXmlGenerator();

            dataAccess = new FlatFileMemberDataAccess(@"..\..\test_files\failure\");

            member = dataAccess.GetMember(144400);

            relatedMembers = new List<Member>();
            relatedMembers.Add(dataAccess.GetMember(156859));
            relatedMembers.Add(dataAccess.GetMember(277356));

            distances = new List<float>();
            distances.Add(1.0f);
            distances.Add(2.0f);
        }

        /// <summary>
        /// Cleans-up the test environment after running each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            relatedMembers.Clear();
            distances.Clear();
        }

        /// <summary>
        /// Test the <code>GenerateXml</code> method with null <code>member</code>.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullMember()
        {
            xmlGenerator.GenerateXml(null, relatedMembers, distances);
        }

        /// <summary>
        /// Test the <code>GenerateXml</code> method with null <code>relatedMembers</code>.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullRelatedMembers()
        {
            xmlGenerator.GenerateXml(member, null, distances);
        }

        /// <summary>
        /// Test the <code>GenerateXml</code> method with null <code>distances</code>.
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGenerateXmlWithNullDistances()
        {
            xmlGenerator.GenerateXml(member, relatedMembers, null);
        }

        /// <summary>
        /// Test the <code>GenerateXml</code> method with null elements in <code>relatedMembers</code>.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateXmlWithNullElementsInMembers()
        {
            relatedMembers.Add(null);

            xmlGenerator.GenerateXml(member, relatedMembers, distances);
        }

        /// <summary>
        /// Test the <code>GenerateXml</code> method with a different number of related members than distances.
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGenerateXmlWithDifferentlySizedLists()
        {
            distances.Add(4.2f);

            xmlGenerator.GenerateXml(member, relatedMembers, distances);
        }
    }
}
