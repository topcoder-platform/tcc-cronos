/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultDocHostUIHandlerUnitTest.cs
 */
using System;
using System.IO;
using NUnit.Framework;

using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit test for <c>DefaultDocHostUIHandler</c>,
    /// this class is simple class, many methods are empty, simply call it.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultDocHostUIHandlerUnitTest
    {
        /// <summary>
        /// Represents the instance of context, used for DefaultDocHostUIHandler.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// The instance of <c>DefaultDocHostUIHandler</c> to perform test on.
        /// </summary>
        private DefaultDocHostUIHandler tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();
            context = new MsieClientLogic(new WebBrowserClass());
            tester = new DefaultDocHostUIHandler(context);
        }

        /// <summary>
        /// Tear down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Test inheritance,
        /// DefaultDocHostUIHandler should implement IDocHostUIHandler.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsNotNull(tester is IDocHostUIHandler,
                "DefaultDocHostUIHandler should implement IDocHostUIHandler.");
        }

        /// <summary>
        /// Test ctor DefaultDocHostUIHandler(),
        /// empty constructor, an instance should be created always.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester, "empty constructor, an instance should be created always.");
        }

        /// <summary>
        /// Test ctor DefaultDocHostUIHandler(MsieClientLogic context),
        /// when context is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ContextIsNull()
        {
            Assert.IsNotNull(new DefaultDocHostUIHandler(null),
                "empty constructor, an instance should be created always.");
        }

        /// <summary>
        /// Test ctor DefaultDocHostUIHandler(MsieClientLogic context),
        /// an instance with the given context should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            Assert.IsNotNull(new DefaultDocHostUIHandler(context),
                "empty constructor, an instance should be created always.");
        }

        /// <summary>
        /// Test EnableModeless(int fEnable),
        /// empty method, simply call it, nothing happend always.
        /// </summary>
        [Test]
        public void TestEnableModeless()
        {
            tester.EnableModeless(0);
        }

        /// <summary>
        /// Test FilterDataObject(IDataObject pDO, out IDataObject ppDORet),
        /// ppDORet will be set to null.
        /// </summary>
        [Test]
        public void TestFilterDataObject()
        {
            IDataObject pDO = null;
            IDataObject ppDORet;
            tester.FilterDataObject(pDO, out ppDORet);
            Assert.IsNull(ppDORet, "ppDORet will be set to null.");
        }

        /// <summary>
        /// Test GetDropTarget(IDropTarget pDropTarget, out IDropTarget ppDropTarget),
        /// ppDropTarget will be set to null.
        /// </summary>
        [Test]
        public void TestGetDropTarget()
        {
            IDropTarget pDropTarget = null;
            IDropTarget ppDropTarget;
            tester.GetDropTarget(pDropTarget, out ppDropTarget);
            Assert.IsNull(ppDropTarget, "ppDropTarget will be set to null..");
        }

        /// <summary>
        /// Test GetExternal(out object ppDispatch),
        /// ppDispatch should be set to script object.
        /// </summary>
        [Test]
        public void TestGetExternal()
        {
            object ppDispatch;
            tester.GetExternal(out ppDispatch);
            Assert.AreEqual(context.ScriptingObject, ppDispatch,
                "GetExternal is not set the scripting object correctly.");
        }

        /// <summary>
        /// Test GetHostInfo(ref _DOCHOSTUIINFO pInfo),
        /// empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestGetHostInfo()
        {
            _DOCHOSTUIINFO dhi = new _DOCHOSTUIINFO();
            tester.GetHostInfo(ref dhi);
        }

        /// <summary>
        /// Test GetOptionKeyPath(out string pchKey, uint dw),
        /// pchKey will be set to empty string.
        /// </summary>
        [Test]
        public void TestGetOptionKeyPath()
        {
            string opchKey;
            tester.GetOptionKeyPath(out opchKey, 1);
            Assert.AreEqual(string.Empty, opchKey, "pchKey will be set to empty string.");
        }

        /// <summary>
        /// Test HideUI(),
        /// empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestHideUI()
        {
            tester.HideUI();
        }

        /// <summary>
        /// Test OnDocWindowActivate(int fActivate)
        /// empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestOnDocWindowActivate()
        {
            tester.OnDocWindowActivate(0);
        }

        /// <summary>
        /// Test OnFrameWindowActivate(int fActivate)
        /// empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestOnFrameWindowActivate()
        {
            tester.OnFrameWindowActivate(0);
        }

        /// <summary>
        /// Test ResizeBorder(ref tagRECT prcBorder, IOleInPlaceUIWindow pUIWindow,
        /// int fRameWindow),empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestResizeBorder()
        {
            tagRECT rc = new tagRECT();
            tester.ResizeBorder(ref rc, null, 0);
        }

        /// <summary>
        /// Test ShowContextMenu(uint dwID, ref tagPOINT ppt, object pcmdtReserved,
        /// object pdispReserved), empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestShowContextMenu()
        {
            tagPOINT pt = new tagPOINT();
            tester.ShowContextMenu(1, ref pt, null, null);
        }

        /// <summary>
        /// Test ShowUI(uint dwID, IOleInPlaceActiveObject pActiveObject,
        /// IOleCommandTarget pCommandTarget, IOleInPlaceFrame pFrame,
        /// IOleInPlaceUIWindow pDoc), empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestShowUI()
        {
            tester.ShowUI(1, null, null, null, null);
        }

        /// <summary>
        /// Test TranslateAccelerator(ref tagMSG lpmsg, ref Guid pguidCmdGroup,
        /// uint nCmdID), empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestTranslateAccelerator()
        {
            tagMSG msg = new tagMSG();
            Guid guid = Guid.NewGuid();
            tester.TranslateAccelerator(ref msg, ref guid, 1);
        }

        /// <summary>
        /// Test TranslateUrl(uint dwTranslate, ref ushort pchURLIn,
        /// IntPtr ppchURLOut), empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestTranslateUrl()
        {
            ushort url = 1;
            IntPtr pt = new IntPtr();
            tester.TranslateUrl(1, ref url, pt);
        }

        /// <summary>
        /// Test UpdateUI(), empty method, nothing is done. simply call it.
        /// </summary>
        [Test]
        public void TestUpdateUI()
        {
            tester.UpdateUI();
        }

    }
}
