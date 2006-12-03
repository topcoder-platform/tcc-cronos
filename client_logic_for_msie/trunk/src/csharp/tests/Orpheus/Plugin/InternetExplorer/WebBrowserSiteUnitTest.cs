/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserSiteUnitTest.cs
 */

using System;
using System.Windows.Forms;
using NUnit.Framework;
using MsHtmHstInterop;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit test for <c>WebBrowserSite</c> class.
    /// Only failure test existed.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class WebBrowserSiteUnitTest
    {
        /// <summary>
        /// An instance of WebBrowserSite to perform test on.
        /// </summary>
        private WebBrowserSite tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            tester = new WebBrowserSite();
        }

        /// <summary>
        /// Test property Host, when not set, return null.
        /// </summary>
        [Test]
        public void TestHost()
        {
            Assert.IsNull(tester.Host,
                "when not set, return null.");
        }

        /// <summary>
        /// Test property Site, when not set, return null.
        /// </summary>
        [Test]
        public void TestSite()
        {
            Assert.IsNull(tester.Site,
                "when not set, return null.");
        }

        /// <summary>
        /// WebBrowserSite should extended from UserControl and implemented with IObjectWithSite.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(tester is UserControl,
                "ToolBand  should extend from WebBrowserSite.");

            Assert.IsTrue(tester is IObjectWithSite,
                "ToolBand  should implement IDeskBand .");
        }

        /// <summary>
        /// Test ctor WebBrowserSite(),
        /// instance should be created always.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester, "instance should be created always.");
        }

        /// <summary>
        /// Test GetSite(ref Guid riid, out IntPtr ppvSite),
        /// just placeholder.
        /// </summary>
        [Test]
        public void TestGetSite()
        {
            // placeholder.
        }
    }
}
