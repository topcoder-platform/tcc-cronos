/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionAttributeUnitTest.cs
 */
using System;
using NUnit.Framework;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit test for ExtensionAttribute class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ExtensionAttributeUnitTest
    {
        /// <summary>
        /// The name for tester.
        /// </summary>
        private const string NAME = "Test";

        /// <summary>
        /// Represents the instance to perform test on.
        /// </summary>
        private ExtensionAttribute tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            tester = new ExtensionAttribute(NAME);
        }

        /// <summary>
        /// Test ctor ExtensionAttribute(string name),
        /// when name is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor_NameIsNull()
        {
            new ExtensionAttribute(null);
        }

        /// <summary>
        /// Test ctor ExtensionAttribute(string name),
        /// when name is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor_NameIsEmpty()
        {
            new ExtensionAttribute("  \r \t ");
        }

        /// <summary>
        /// Test ctor ExtensionAttribute(string name),
        /// when name is valid string, instance with the given name should be created.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester, "Failed to create instance");
            Assert.AreEqual(NAME, tester.Name, "Failed to initialize the name");
        }

        /// <summary>
        /// Test property Name Getter,
        /// it should be the name given in constructor.
        /// </summary>
        [Test]
        public void TestName_Getter()
        {
            string s = "TestName_Getter";

            Assert.AreEqual(s, new ExtensionAttribute(s).Name,
                "it should be the name given in constructor.");
        }
    }
}
