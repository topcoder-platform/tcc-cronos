/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using NUnit.Framework;

namespace TopCoder.Web.Distance
{
    /// <summary>
    /// Unit tests for the <c>Helper</c> class. This test fixture contains tests
    /// that validate the different methods of the <c>Helper</c> class.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [TestFixture, CoverageExclude]
    public class TestHelper
    {


        /// <summary>
        /// A list of string instances used in the tests.
        /// </summary>
        private IList<string> testList;

        /// <summary>
        /// Sets-up the test environment prior to running each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            testList = new List<string>();
            testList.Add(string.Empty);
        }

        /// <summary>
        /// Cleans-up the test environment after running each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            testList.Clear();
        }

        /// <summary>
        /// Tests the <c>ValidateNotNull(string, object)</c> method when passed a null value.
        /// An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestValidateNotNullWithNull()
        {
            Helper.ValidateNotNull("test", null);
        }


        /// <summary>
        /// Tests the <c>ValidateNotNull(string, object)</c> method when passed a non-null value.
        /// No exception should be thrown.
        /// </summary>
        [Test]
        public void TestValidateNotNullWithNonNull()
        {
            Helper.ValidateNotNull("test", string.Empty);
        }



        /// <summary>
        /// Tests the <c>ValidateArgument(string, bool, string)</c> method when
        /// the error condition given is false. No exception should be thrown.
        /// </summary>
        [Test]
        public void TestValidateArgumentWhenErrorConditionIsFalse()
        {
            Helper.ValidateArgument("test", false, "test message");
        }

        /// <summary>
        /// Tests the <c>ValidateArgument(string, bool, string)</c> method when
        /// the error condition given is true. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestValidateArgumentWhenErrorConditionIsTrue()
        {
            Helper.ValidateArgument("test", true, "test message");
        }




        /// <summary>
        /// Tests the <c>ValidateList&lt;T&gt;(string, bool, string)</c> method when
        /// the list given is null. An <c>ArgumentNullException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestValidateListWithNullList()
        {
            Helper.ValidateList<string>("test", null, true);
        }

        /// <summary>
        /// Tests the <c>ValidateList&lt;T&gt;(string, bool, string)</c> method when
        /// the list given contains a null. An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestValidateListWithNullElementInList()
        {
            testList.Add(null);
            Helper.ValidateList<string>("test", testList, true);
        }



        /// <summary>
        /// Tests the <c>ValidateList&lt;T&gt;(string, bool, string)</c> method when
        /// the non-empty list given contains valid elements.
        /// No exception should be thrown.
        /// </summary>
        [Test]
        public void TestValidateListWithValid()
        {
            Helper.ValidateList<string>("test", testList, true);
        }


        /// <summary>
        /// Tests the <c>ValidateList&lt;T&gt;(string, bool, string)</c> method when
        /// the list given is empty and an empty list is allowed. No exception should be thrown.
        /// </summary>
        [Test]
        public void TestValidateListWithEmptyListAllowed()
        {
            testList.Clear();
            Helper.ValidateList<string>("test", testList, true);
        }



        /// <summary>
        /// Tests the <c>ValidateList&lt;T&gt;(string, bool, string)</c> method when
        /// the list given is empty and an empty list is not allowed.
        /// An <c>ArgumentException</c> is expected to be thrown.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestValidateListWithEmptyListNotAllowed()
        {
            testList.Clear();
            Helper.ValidateList<string>("test", testList, false);
        }
    }
}
