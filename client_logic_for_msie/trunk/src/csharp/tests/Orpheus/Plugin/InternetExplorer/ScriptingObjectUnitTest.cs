/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ScriptingObjectUnitTest.cs
 */

using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.Mock;
using MsHtmHstInterop;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Persistence;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <c>ScriptingObject</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ScriptingObjectUnitTest
    {
        /// <summary>
        /// Represents the instance to perform test on.
        /// </summary>
        private ScriptingObject tester;

        /// <summary>
        /// Represents the context to construct the scripting object.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            context = new MsieClientLogic(new WebBrowserClass());
            tester = new ScriptingObject(context);
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
        /// Test ctor ScriptingObject(MsieClientLogic context),
        /// when context is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor_ContextIsNull()
        {
            new ScriptingObject(null);
        }

        /// <summary>
        /// Test ctor ScriptingObject(MsieClientLogic context),
        /// when context is not null, instance with it should be created.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester,
                "when context is not null, instance with it should be created.");
            Assert.AreEqual(context, TestHelper.GetFieldValue(tester, "context"),
                "Context not set properly");
        }

        /// <summary>
        /// Test LoggedIn(),
        /// the event handler should be fired.
        /// </summary>
        [Test]
        public void TestLoggedIn()
        {
            MockExtensionEventHandler handler = new MockExtensionEventHandler();
            context.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDIN,
                new ExtensionEventHandlerDelegate(handler.HandleEvent));

            tester.LoggedIn();

            Assert.AreEqual(tester, handler.Sender, "Loggin handler should be fired");
            Assert.AreEqual(Helper.EVENT_LOGGEDIN, handler.Args.EventName, "Loggin handler should be fired");
            Assert.AreEqual(context, handler.Args.Context, "Loggin handler should be fired");
        }

        /// <summary>
        /// Test LoggedOut(),
        /// the event handler should be fired.
        /// </summary>
        [Test]
        public void TestLoggedOut()
        {
            MockExtensionEventHandler handler = new MockExtensionEventHandler();
            context.EventsManager.AddEventHandler(Helper.EVENT_LOGGEDOUT,
                new ExtensionEventHandlerDelegate(handler.HandleEvent));

            tester.LoggedOut();

            Assert.AreEqual(tester, handler.Sender, "Loggin handler should be fired");
            Assert.AreEqual(Helper.EVENT_LOGGEDOUT, handler.Args.EventName, "Loggin handler should be fired");
            Assert.AreEqual(context, handler.Args.Context, "Loggin handler should be fired");
        }

        /// <summary>
        /// Test PollMessages(),
        /// the event handler should be fired.
        /// </summary>
        [Test]
        public void TestPollMessages()
        {
            MockExtensionEventHandler handler = new MockExtensionEventHandler();
            context.EventsManager.AddEventHandler(Helper.EVENT_POLL_UPDATES,
                new ExtensionEventHandlerDelegate(handler.HandleEvent));

            tester.PollMessages();

            Assert.AreEqual(tester, handler.Sender, "Loggin handler should be fired");
            Assert.AreEqual(Helper.EVENT_POLL_UPDATES, handler.Args.EventName, "Loggin handler should be fired");
            Assert.AreEqual(context, handler.Args.Context, "Loggin handler should be fired");
        }

        /// <summary>
        /// Test SetWorkingGame(long gameId),
        /// gameid should be persistenced.
        /// the event handler should be fired.
        /// </summary>
        [Test]
        public void TestSetWorkingGame()
        {
            MockExtensionEventHandler handler = new MockExtensionEventHandler();
            context.EventsManager.AddEventHandler(Helper.EVENT_GAME_CHANGED,
                new ExtensionEventHandlerDelegate(handler.HandleEvent));

            tester.SetWorkingGame(1);

            Assert.AreEqual(1, tester.GetWorkingGame(),
                "Failed to persistence the game");

            Assert.AreEqual(tester, handler.Sender, "Loggin handler should be fired");
            Assert.AreEqual(Helper.EVENT_GAME_CHANGED, handler.Args.EventName, "Loggin handler should be fired");
            Assert.AreEqual(context, handler.Args.Context, "Loggin handler should be fired");
        }


        /// <summary>
        /// Test GetWorkingGame(),
        /// the game id persistenced should be retrieved.
        /// </summary>
        [Test]
        public void TestGetWorkingGame()
        {
            tester.SetWorkingGame(1);
            Assert.AreEqual(1, tester.GetWorkingGame(),
                "Failed to persistence the game");

            tester.SetWorkingGame(2);
            Assert.AreEqual(2, tester.GetWorkingGame(),
                "Failed to persistence the game");
        }

        /// <summary>
        /// Test SetCurrentTarget(string hash, int sequence),
        /// when hash is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestSetCurrentTarget_HashIsNull()
        {
            tester.SetCurrentTarget(null, 1);
        }

        /// <summary>
        /// Test SetCurrentTarget(string hash, int sequence),
        /// when hash is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestSetCurrentTarget_HashIsEmpty()
        {
            tester.SetCurrentTarget("  \t \n ", 1);
        }

        /// <summary>
        /// Test SetCurrentTarget(string hash, int sequence),
        /// the hash and sequence should be persistenced.
        /// </summary>
        [Test]
        public void TestSetCurrentTarget()
        {
            string hash = "abc";
            int seq = 2;

            tester.SetCurrentTarget(hash, seq);
            Assert.AreEqual(hash, new RegistryPersistence()[Helper.KEY_HASH],
                "hash is not correctly");
            Assert.AreEqual(seq.ToString(), new RegistryPersistence()[Helper.KEY_SEQUENCE],
                "sequence is not correctly");
        }

        /// <summary>
        /// Test IsPopup(IHTMLWindow2 window),
        /// when window is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIsPopup_WindowIsNull()
        {
            tester.IsPopup(null);
        }
    }
}
