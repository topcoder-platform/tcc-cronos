/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ToolBandUnitTest.cs
 */

using System;
using System.Drawing;
using NUnit.Framework;
using MsHtmHstInterop;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit test for <c>ToolBand</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ToolBandUnitTest
    {
        /// <summary>
        /// An instance of ToolBand to perform test on.
        /// </summary>
        private ToolBand tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            tester = new ToolBand();
        }

        /// <summary>
        /// ToolBand  should extend from WebBrowserSite and implement IDeskBand.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(tester is WebBrowserSite,
                "ToolBand  should extend from WebBrowserSite.");

            Assert.IsTrue(tester is WebBrowserSite,
                "ToolBand  should implement IDeskBand .");
        }

        /// <summary>
        /// Test ctor ToolBand(),
        /// instance should be created always.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester, "instance should be created always.");
        }

        /// <summary>
        /// Test property Title,
        /// no check need.
        /// </summary>
        [Test]
        public void TestTitle()
        {
            const string Title = "New Title";

            tester.Title = Title;
            Assert.AreEqual(Title, tester.Title,
                "The title is not set correctly");
        }

        /// <summary>
        /// Test property MinSize,no check need.
        /// </summary>
        [Test]
        public void TestMinSize()
        {
            Size size = new Size(101, 11);
            tester.MinSize = size;
            Assert.AreEqual(size, tester.MinSize,
                "MinSize not set correrctly");
        }

        /// <summary>
        /// Test property MaxSize,no check need.
        /// </summary>
        [Test]
        public void TestMaxSize()
        {
            Size size = new Size(101, 11);
            tester.MaxSize = size;
            Assert.AreEqual(size, tester.MaxSize,
                "MaxSize not set correrctly");
        }

        /// <summary>
        /// Test property IntegralSize,no check need.
        /// </summary>
        [Test]
        public void TestIntegralSize()
        {
            Size size = new Size(101, 11);
            tester.IntegralSize = size;
            Assert.AreEqual(size, tester.IntegralSize,
                "IntegralSize not set correrctly");
        }

        /// <summary>
        /// Test GetBandInfo(uint dwBandID, uint dwViewMode, ref DESKBANDINFO dbi),
        /// the information should be filled into dbi.
        /// </summary>
        [Test]
        public void TestGetBandInfo()
        {
            tester.Title = "a";
            tester.MinSize = new Size(1, 1);
            tester.MaxSize = new Size(3, 3);
            tester.IntegralSize = new Size(2, 2);
            DESKBANDINFO dbi = new DESKBANDINFO();
            dbi.wszTitle = new ushort[100];
            tester.GetBandInfo(1, 1, ref dbi);

            Assert.AreEqual((ushort) 'a', dbi.wszTitle[0],
                "The bandinfo is not right");
            Assert.AreEqual(new Size(1, 1), tester.MinSize,
                "The bandinfo is not right");
            Assert.AreEqual(new Size(3, 3), tester.MaxSize,
                "The bandinfo is not right");
            Assert.AreEqual(new Size(2, 2), tester.IntegralSize,
                "The bandinfo is not right");
        }

        /// <summary>
        /// Test ShowDW(int bShow),
        /// simply call it.
        /// </summary>
        [Test]
        public void TestShowDW()
        {
            tester.ShowDW(0);
        }

        /// <summary>
        /// Test  CloseDW(uint bShow),
        /// simply call it.
        /// </summary>
        [Test]
        public void TestCloseDW()
        {
            tester.CloseDW(1);
        }

        /// <summary>
        /// Test ResizeBorderDW(ref tagRECT prcBorder, object punkToolbarSite, int fReserved),
        /// simply call it.
        /// </summary>
        [Test]
        public void TestResizeBorderDW()
        {
             tagRECT rect = new tagRECT();
            tester.ResizeBorderDW(ref rect, new object(), 1);
        }

        /// <summary>
        /// Test GetWindow(IntPtr ppWnd),
        /// simple call it.
        /// </summary>
        [Test]
        public void TestGetWindow()
        {
            IntPtr ptr = new IntPtr();
            tester.GetWindow(ptr);
        }

        /// <summary>
        /// Test ContextSensitiveHelp(int fEnterMode),
        /// simply call it.
        /// </summary>
        [Test]
        public void TestContextSensitiveHelp()
        {
            tester.ContextSensitiveHelp(0);
        }
    }
}
