/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Factories;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.EventsManager
{
    /// <summary>
    /// Accuracy tests for <c>DefaultExtensionEventsManager</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class DefaultExtensionEventsManagerAccTests
    {
        /// <summary>
        /// The factory instance to test.
        /// </summary>
        private DefaultExtensionEventsManager manager;

        /// <summary>
        /// The mock handler used in test cases.
        /// </summary>
        private MockExtensionEventHandler handler;

        /// <summary>
        /// The event handler delegate used in test cases.
        /// </summary>
        private ExtensionEventHandlerDelegate handlerDelegate;

        /// <summary>
        /// The arguments delegate used in test cases.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            AccuracyHelper.LoadConfiguration();

            manager = new DefaultExtensionEventsManager();

            handler = new MockExtensionEventHandler();
            handlerDelegate = new ExtensionEventHandlerDelegate(handler.HandleEvent);
            args = new ExtensionEventArgs("Mock", new MsieClientLogic(new WebBrowserClass()));

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
        /// Accuracy test of namespace constants.
        /// </summary>
        [Test]
        public void TestNamespaces()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers",
                            DefaultExtensionEventsManager.DefaultConfigurationNamespace,
                            "Not the expected default namespace.");

            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                            DefaultExtensionEventsManager.DefaultObjectFactoryNamespace,
                            "Not the expected object factory namespace.");
        }

        /// <summary>
        /// Accuracy test of the default constructor.
        /// </summary>
        [Test]
        public void TestConstructor1()
        {
            // handlers should be loaded
            Assert.AreEqual(2, manager.GetEventHandlers("test").Length,
                            "The created handlers is not correct.");
        }

        /// <summary>
        /// Accuracy test of the constructor with the given namespaces.
        /// </summary>
        [Test]
        public void TestConstructor2()
        {
            manager = new DefaultExtensionEventsManager("Orpheus.Plugin.InternetExplorer.EventsManagers",
                                                        "TopCoder.Util.ObjectFactory");
            // handlers should be loaded
            Assert.AreEqual(2, manager.GetEventHandlers("test").Length,
                            "The created handlers is not correct.");
        }

        /// <summary>
        /// Accuracy test of the constructor with the given factory instance.
        /// </summary>
        [Test]
        public void TestConstructor3()
        {
            IExtensionEventHandlerFactory factory = new DefaultExtensionEventHandlerFactory();
            manager = new DefaultExtensionEventsManager(factory);

            // handlers should be loaded
            Assert.AreEqual(2, manager.GetEventHandlers("test").Length,
                            "The created handlers is not correct.");
        }

        /// <summary>
        /// Accuracy test of the <code>AddEventHandler()</code> method.
        /// </summary>
        [Test]
        public void TestAddEventHandler()
        {
            bool result = manager.AddEventHandler("testNew", handlerDelegate);

            Assert.IsTrue(result, "The result should be success.");

            Assert.AreEqual(1, manager.GetEventHandlers("testNew").Length,
                            "Handler is not added properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>RemoveEventHandler()</code> method.
        /// </summary>
        [Test]
        public void TestRemoveEventHandler()
        {
            manager.AddEventHandler("testNew", handlerDelegate);
            Assert.AreEqual(handlerDelegate, manager.GetEventHandlers("testNew")[0],
                            "Handler is not added properly.");

            bool result = manager.RemoveEventHandler("testNew", handlerDelegate);

            Assert.IsTrue(result, "The result should be success.");

            Assert.AreEqual(0, manager.GetEventHandlers("testNew").Length,
                            "Handler is not removed properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>GetEventHandlers()</code> method.
        /// </summary>
        [Test]
        public void TestGetEventHandlers()
        {
            manager.AddEventHandler("testNew", handlerDelegate);

            Assert.AreEqual(handlerDelegate, manager.GetEventHandlers("testNew")[0],
                            "Handlers are not retrieved properly.");

            manager.RemoveEventHandler("testNew", handlerDelegate);

            Assert.AreEqual(0, manager.GetEventHandlers("testNew").Length,
                            "Handlers are not retrieved properly.");
        }

        /// <summary>
        /// Accuracy test of the <code>FireEvent()</code> method.
        /// </summary>
        [Test]
        public void TestFireEvent()
        {
            manager.AddEventHandler(args.EventName, handlerDelegate);
            manager.FireEvent(args.EventName, this, args);

            Assert.AreEqual(this, handler.Sender, "Not the expected sender.");
            Assert.AreEqual(args, handler.Args, "Not the expected arguments.");
        }
    }
}