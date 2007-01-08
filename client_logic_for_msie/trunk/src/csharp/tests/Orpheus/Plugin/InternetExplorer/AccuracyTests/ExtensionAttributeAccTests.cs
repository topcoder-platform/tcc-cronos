/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using NUnit.Framework;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>ExtensionAttribute</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class ExtensionAttributeAccTests
    {
        /// <summary>
        /// Represents the ExtensionAttribute instance to test.
        /// </summary>
        private ExtensionAttribute attribute;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            attribute = new ExtensionAttribute("test");
        }

        /// <summary>
        /// Test that this class should extend Attribute class.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsNotNull(attribute is Attribute,
                             "Should extend Attribute class.");
        }

        /// <summary>
        /// Test the <code>Name()</code> property.
        /// </summary>
        [Test]
        public void TestName()
        {
            Assert.AreEqual("test", attribute.Name,
                            "Name property is not set correctly.");
        }
    }
}