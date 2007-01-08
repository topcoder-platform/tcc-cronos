/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using System.IO;
using NUnit.Framework;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>DefaultDocHostUIHandler</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class DefaultDocHostUIHandlerAccTests
    {
        /// <summary>
        /// The DefaultDocHostUIHandler instance to test.
        /// </summary>
        private DefaultDocHostUIHandler handler;

        /// <summary>
        /// Represents the MsieClientLogic instance used in test cases.
        /// </summary>
        private MsieClientLogic mcl;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();
            mcl = new MsieClientLogic(new WebBrowserClass());
            handler = new DefaultDocHostUIHandler(mcl);
        }

        /// <summary>
        /// Tear down for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            AccuracyHelper.ClearConfiguration();
        }

        /// <summary>
        /// Test that this class should implement IDocHostUIHandler interface.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsNotNull(handler is IDocHostUIHandler,
                             "Should implement IDocHostUIHandler interface.");
        }

        /// <summary>
        /// Test the <code>GetExternal()</code> method.
        /// </summary>
        [Test]
        public void TestGetExternal()
        {
            object p = null;

            handler.GetExternal(out p);

            Assert.AreSame(p, mcl.ScriptingObject,
                           "Should implement IDocHostUIHandler interface.");
        }
    }
}