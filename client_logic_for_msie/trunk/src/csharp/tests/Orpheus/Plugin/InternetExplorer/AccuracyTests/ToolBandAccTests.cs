/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using System;
using System.Drawing;
using NUnit.Framework;
using MsHtmHstInterop;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>ToolBand</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class ToolBandAccTests
    {
        /// <summary>
        /// The ToolBand instance to test.
        /// </summary>
        private ToolBand toolBand;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            toolBand = new ToolBand();
        }

        /// <summary>
        /// Accuracy test of the inheritance.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(toolBand is WebBrowserSite, "Should implement IDeskBand interface.");
            Assert.IsTrue(toolBand is WebBrowserSite, "Should extend from WebBrowserSite class.");
        }

        /// <summary>
        /// Accuracy test of the <code>GetBandInfo()</code> method.
        /// </summary>
        [Test]
        public void TestGetBandInfo()
        {
            toolBand.Title = "test";
            toolBand.IntegralSize = new Size(50, 50);
            toolBand.MinSize = new Size(10, 10);
            toolBand.MaxSize = new Size(100, 100);

            // get desk band info
            DESKBANDINFO info = new DESKBANDINFO();
			info.wszTitle = new ushort[100];
            toolBand.GetBandInfo(1, 1, ref info);

            Assert.AreEqual((ushort) 't', info.wszTitle[0], "Not the expected title.");
            Assert.AreEqual(new Size(50, 50), toolBand.IntegralSize, "Not the expected integral size.");
            Assert.AreEqual(new Size(10, 10), toolBand.MinSize, "Not the expected min size.");
            Assert.AreEqual(new Size(100, 100), toolBand.MaxSize, "Not the expected max size.");
        }

        /// <summary>
        /// Accuracy test of the <code>IntegralSize</code> property.
        /// </summary>
        [Test]
        public void TestIntegralSize()
        {
            Size size = new Size(50, 50);
            toolBand.IntegralSize = size;
            Assert.AreEqual(size, toolBand.IntegralSize,
                            "Value is not set properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>MinSize</code> property.
        /// </summary>
        [Test]
        public void TestMinSize()
        {
            Size size = new Size(50, 50);
            toolBand.MinSize = size;
            Assert.AreEqual(size, toolBand.MinSize,
                            "Value is not set properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>MaxSize</code> property.
        /// </summary>
        [Test]
        public void TestMaxSize()
        {
            Size size = new Size(50, 50);
            toolBand.MaxSize = size;
            Assert.AreEqual(size, toolBand.MaxSize,
                            "Value is not set properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>Title</code> property.
        /// </summary>
        [Test]
        public void TestTitle()
        {
            toolBand.Title = "test";
            Assert.AreEqual("test", toolBand.Title,
                            "Value is not set properly.");
        }
    }
}