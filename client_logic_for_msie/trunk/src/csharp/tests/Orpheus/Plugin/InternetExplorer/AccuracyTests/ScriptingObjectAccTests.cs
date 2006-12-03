/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Persistence;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests
{
    /// <summary>
    /// Accuracy tests for <c>ScriptingObject</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class ScriptingObjectAccTests
    {
        /// <summary>
        /// The ScriptingObject instance to test.
        /// </summary>
        private ScriptingObject scripting;

        /// <summary>
        /// Represents the MsieClientLogic instance used in test case.
        /// </summary>
        private MsieClientLogic mcl;

        MockExtensionEventHandler handler;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();

            handler = new MockExtensionEventHandler();
            mcl = new MsieClientLogic(new WebBrowserClass());
            scripting = new ScriptingObject(mcl);
        }

        /// <summary>
        /// Clean up for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            AccuracyHelper.ClearConfiguration();
        }

        /// <summary>
        /// Accuracy test of the <code>LoggedIn()</code> method.
        /// The parameter should be passed properly.
        /// </summary>
        [Test]
        public void TestLoggedIn()
        {
            mcl.EventsManager.AddEventHandler("LoggedIn",
                                              new ExtensionEventHandlerDelegate(handler.HandleEvent));

            scripting.LoggedIn();

            // check event parameters
            Assert.AreEqual("LoggedIn", handler.Args.EventName, "Invalid event name");
            Assert.AreEqual(mcl, handler.Args.Context, "Invalid context.");
            Assert.AreEqual(scripting, handler.Sender, "Invalid sender.");
        }

        /// <summary>
        /// Accuracy test of the <code>LoggedOut()</code> method.
        /// The parameter should be passed properly.
        /// </summary>
        [Test]
        public void TestLoggedOut()
        {
            mcl.EventsManager.AddEventHandler("LoggedOut",
                                              new ExtensionEventHandlerDelegate(handler.HandleEvent));

            scripting.LoggedOut();

            // check event parameters
            Assert.AreEqual("LoggedOut", handler.Args.EventName, "Invalid event name");
            Assert.AreEqual(mcl, handler.Args.Context, "Invalid context.");
            Assert.AreEqual(scripting, handler.Sender, "Invalid sender.");
        }

        /// <summary>
        /// Accuracy test of the <code>PollMessages()</code> method.
        /// The parameter should be passed properly.
        /// </summary>
        [Test]
        public void TestPollMessages()
        {
            mcl.EventsManager.AddEventHandler("PollUpdates",
                                              new ExtensionEventHandlerDelegate(handler.HandleEvent));

            scripting.PollMessages();

            // check event parameters
            Assert.AreEqual("PollUpdates", handler.Args.EventName, "Invalid event name");
            Assert.AreEqual(mcl, handler.Args.Context, "Invalid context.");
            Assert.AreEqual(scripting, handler.Sender, "Invalid sender.");
        }

        /// <summary>
        /// Accuracy test of the <code>SetWorkingGame()</code> method.
        /// The parameter should be passed properly.
        /// </summary>
        [Test]
        public void TestSetWorkingGame()
        {
            mcl.EventsManager.AddEventHandler("WorkingGameChanged",
                                              new ExtensionEventHandlerDelegate(handler.HandleEvent));

            scripting.SetWorkingGame(1);

            // check event parameters
            Assert.AreEqual("WorkingGameChanged", handler.Args.EventName, "Invalid event name");
            Assert.AreEqual(mcl, handler.Args.Context, "Invalid context.");
            Assert.AreEqual(scripting, handler.Sender, "Invalid sender.");
        }

        /// <summary>
        /// Accuracy test of the <code>GetWorkingGame()</code> method.
        /// The game id should be retrieved properly.
        /// </summary>
        [Test]
        public void TestGetWorkingGame()
        {
            scripting.SetWorkingGame(1);
            Assert.AreEqual(1, scripting.GetWorkingGame(),
                            "Not the expected game id.");
        }

        /// <summary>
        /// Accuracy test of the <code>SetCurrentTarget()</code> method.
        /// The game id should be retrieved properly.
        /// </summary>
        [Test]
        public void TestSetCurrentTarget()
        {
            string hash = "123";
            RegistryPersistence persistence = new RegistryPersistence();
            scripting.SetCurrentTarget(hash, 1);

            Assert.AreEqual(hash, persistence["hash"], "Not the expected hash value.");
            Assert.AreEqual("1", persistence["sequence"], "Not the expected sequence value.");
        }


    }
}
