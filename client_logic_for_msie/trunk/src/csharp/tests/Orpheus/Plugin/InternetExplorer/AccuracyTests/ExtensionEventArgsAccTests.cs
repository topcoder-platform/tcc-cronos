/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using NUnit.Framework;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>ExtensionEventArgs</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class ExtensionEventArgsAccTests
    {
        /// <summary>
        /// Represents the ExtensionEventArgs instance to test.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// Represents the MsieClientLogic instance used in test cases.
        /// </summary>
        private MsieClientLogic mcl;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();
            mcl = new MsieClientLogic(new WebBrowserClass());
            args = new ExtensionEventArgs("test", mcl);
        }

        /// <summary>
        /// Test that this class should extend EventArgs class.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsNotNull(args is EventArgs,
                             "Should extend EventArgs class.");
        }

        /// <summary>
        /// Test the <code>EventName</code> property.
        /// </summary>
        [Test]
        public void TestName()
        {
            Assert.AreEqual("test", args.EventName,
                            "Name property is not set correctly.");
        }

        /// <summary>
        /// Test the <code>Context</code> property.
        /// </summary>
        [Test]
        public void TestContext()
        {
            Assert.AreEqual(mcl, args.Context,
                            "Context property is not set correctly.");
        }

        /// <summary>
        /// Test the <code>Context</code> property.
        /// </summary>
        [Test]
        public void TestParameters()
        {
            object[] parameters = new object[] {1, "test"};
            args = new ExtensionEventArgs("test", mcl, parameters);
            Assert.AreEqual(parameters, args.Parameters,
                            "Parameters property is not set correctly.");
        }
    }
}
