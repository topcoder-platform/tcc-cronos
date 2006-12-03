/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultExtensionEventsManagerUnitTest.cs
 */

using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Factories;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
using Orpheus.Plugin.InternetExplorer.Mock;
using MsHtmHstInterop;
using SHDocVw;


namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// Unit Test for <c>DefaultExtensionEventsManager</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultExtensionEventsManagerUnitTest
    {
        /// <summary>
        /// An instance of <c>DefaultExtensionEventsManager</c> to perform test on.
        /// </summary>
        private DefaultExtensionEventsManager tester;

        /// <summary>
        /// The mock handler
        /// </summary>
        private MockExtensionEventHandler mockHandler;

        /// <summary>
        /// The delegate instance used in Add/Remove operation.
        /// </summary>
        private ExtensionEventHandlerDelegate @delegate;

        /// <summary>
        /// The args used in FireEvent.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            mockHandler = new MockExtensionEventHandler();
            @delegate = new ExtensionEventHandlerDelegate(mockHandler.HandleEvent);
            args = new ExtensionEventArgs("Mock", new MsieClientLogic(new WebBrowserClass()));
            tester = new DefaultExtensionEventsManager();
        }

        /// <summary>
        /// Tear Down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Test const DefaultConfigurationNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers",
                DefaultExtensionEventsManager.DefaultConfigurationNamespace,
                "DefaultConfigurationNamespace should be " +
                "'Orpheus.Plugin.InternetExplorer.EventsManagers'");
        }

        /// <summary>
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'TopCoder.Util.ObjectFactory'.
        /// </summary>
        [Test]
        public void TestDefaultObjectFactoryNamespace()
        {
            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                DefaultExtensionEventsManager.DefaultObjectFactoryNamespace,
                "DefaultObjectFactoryNamespace should be 'TopCoder.Util.ObjectFactory'");
        }

        /// <summary>
        /// Test inheritacne,
        /// DefaultExtensionEventsManager should implement IExtensionEventsManager.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IExtensionEventsManager,
                "DefaultExtensionEventsManager should implement IExtensionEventsManager.");
        }

        /// <summary>
        /// Test cotr DefaultExtensionEventsManager(),
        /// an instance should be created, with the default namespace.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester,
                "an instance should be created, with the default namespace.");
            Assert.AreEqual(0, tester.GetEventHandlers("empty").Length,
                "The created handlers is not correct.");
            Assert.AreEqual(2, tester.GetEventHandlers("test").Length,
                "The created handlers is not correct.");
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(string configurationNamespace,
        /// string objectFactoryNamepsace), when configurationNamespace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new DefaultExtensionEventsManager(null,
                DefaultExtensionEventsManager.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(string configurationNamespace,
        /// string objectFactoryNamepsace), when configurationNamespace is empty,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmptyl()
        {
            new DefaultExtensionEventsManager(" \r \t \n",
                DefaultExtensionEventsManager.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(string configurationNamespace,
        /// string objectFactoryNamepsace), when objectFactoryNamepsace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ObjectFactoryNamepsaceIsNull()
        {
            new DefaultExtensionEventsManager(
                DefaultExtensionEventsManager.DefaultConfigurationNamespace, null);
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(string configurationNamespace,
        /// string objectFactoryNamepsace), when objectFactoryNamepsace is null,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ObjectFactoryNamepsaceIsEmpty()
        {
            new DefaultExtensionEventsManager(
                DefaultExtensionEventsManager.DefaultConfigurationNamespace, " \r \t \n");

            Assert.AreEqual(0, tester.GetEventHandlers("empty").Length,
                "The created handlers is not correct.");
            Assert.AreEqual(2, tester.GetEventHandlers("test").Length,
                "The created handlers is not correct.");
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(string configurationNamespace,
        /// string objectFactoryNamepsace), when the namespace are correct,
        /// handler dicitonary should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            DefaultExtensionEventsManager manager = new DefaultExtensionEventsManager(
                DefaultExtensionEventsManager.DefaultConfigurationNamespace,
                DefaultExtensionEventsManager.DefaultObjectFactoryNamespace);

            Assert.AreEqual(0, manager.GetEventHandlers("empty").Length,
                "The created handlers is not correct.");
            Assert.AreEqual(2, manager.GetEventHandlers("test").Length,
                "The created handlers is not correct.");
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(IExtensionEventHandlerFactory
        /// extensionEventHandlerFactory), when extensionEventHandlerFactory is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_ExtensionEventHandlerFactory()
        {
            new DefaultExtensionEventsManager(null);
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventsManager(IExtensionEventHandlerFactory
        /// extensionEventHandlerFactory), when extensionEventHandlerFactory is valid,
        /// instance should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            IExtensionEventHandlerFactory factory = new DefaultExtensionEventHandlerFactory();
            DefaultExtensionEventsManager manager = new DefaultExtensionEventsManager(factory);
            Assert.IsNotNull(manager, "Failed ot create.");

            Assert.AreEqual(0, manager.GetEventHandlers("empty").Length,
                "The created handlers is not correct.");
            Assert.AreEqual(2, manager.GetEventHandlers("test").Length,
                "The created handlers is not correct.");
        }

        /// <summary>
        /// Test AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventName is Null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestAddEventHandler_EventNameIsNull()
        {
            tester.AddEventHandler(null, @delegate);
        }

        /// <summary>
        /// Test AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventName is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestAddEventHandler_EventNameIsEmpty()
        {
            tester.AddEventHandler(" \r \t \n", @delegate);
        }

        /// <summary>
        /// Test AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventHandler is Null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestAddEventHandler_EventHandlerIsNull()
        {
            tester.AddEventHandler("Mock", null);
        }

        /// <summary>
        /// Test AddEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when argument are valide, eventHandler will be added under the eventName.
        /// </summary>
        [Test]
        public void TestAddEventHandler()
        {
            const string Name = "Mock";
            Assert.IsTrue(tester.AddEventHandler(Name, @delegate),
                "Should be added");

            Assert.AreEqual(1, tester.GetEventHandlers(Name).Length,
                "Only one should be existed");
            Assert.AreEqual(@delegate, tester.GetEventHandlers(Name)[0],
                "Failed to add correctly.");

            // add again
            Assert.IsTrue(tester.AddEventHandler(Name, @delegate),
                "Should be added");

            Assert.AreEqual(2, tester.GetEventHandlers(Name).Length,
                "Two should be existed");
            Assert.AreEqual(@delegate, tester.GetEventHandlers(Name)[0],
                "Failed to add correctly.");
            Assert.AreEqual(@delegate, tester.GetEventHandlers(Name)[1],
                "Failed to add correctly.");
        }

        /// <summary>
        /// Test RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestRemoveEventHandler_EventNameIsNull()
        {
            tester.RemoveEventHandler(null, @delegate);
        }

        /// <summary>
        /// Test RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventName is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestRemoveEventHandler_EventNameIsEmpty()
        {
            tester.RemoveEventHandler(" \r \t \n", @delegate);
        }

        /// <summary>
        /// Test RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventHandler is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestRemoveEventHandler_EventHandlerIsNull()
        {
            tester.RemoveEventHandler("Mock", null);
        }

        /// <summary>
        /// Test RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when eventHandler not existed, false is returned.
        /// </summary>
        [Test]
        public void TestRemoveEventHandler_False()
        {
            Assert.IsFalse(tester.RemoveEventHandler("Mock", @delegate),
                "Handler not existed, false returned");
        }

        /// <summary>
        /// Test RemoveEventHandler(string eventName, ExtensionEventHandlerDelegate eventHandler),
        /// when argument are valide, eventHandler will be removed.
        /// </summary>
        [Test]
        public void TestRemoveEventHandler()
        {
            // first add two for test
            const string Name = "Mock";
            ExtensionEventHandlerDelegate another = new ExtensionEventHandlerDelegate(
                new HttpRequestUserInterfaceEventHandler().HandleEvent);
            Assert.IsTrue(tester.AddEventHandler(Name, @delegate),
                "Should be added");
            Assert.IsTrue(tester.AddEventHandler(Name, another),
                "Should be added");

            Assert.AreEqual(2, tester.GetEventHandlers(Name).Length,
                "Only one should be existed");

            Assert.IsTrue(tester.RemoveEventHandler(Name, @delegate),
                "Should be removed");

            Assert.AreEqual(1, tester.GetEventHandlers(Name).Length,
                "Should only one left");
            Assert.AreEqual(another, tester.GetEventHandlers(Name)[0],
                "Failed to add correctly.");

            Assert.IsTrue(tester.RemoveEventHandler(Name, another),
                "Should be removed");

            Assert.AreEqual(0, tester.GetEventHandlers(Name).Length,
                "no one left");
        }

        /// <summary>
        /// Test GetEventHandlers(string eventName),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestGetEventHandlers_EventNameIsNull()
        {
            tester.GetEventHandlers(null);
        }

        /// <summary>
        /// Test GetEventHandlers(string eventName),
        /// when eventName is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestGetEventHandlers_EventNameIsEmpty()
        {
            tester.GetEventHandlers(" \r \t \n ");
        }

        /// <summary>
        /// Test GetEventHandlers(string eventName),
        /// when eventName is not existed, empty array returned.
        /// </summary>
        [Test]
        public void TestGetEventHandlers_NoSuchEvent()
        {
            Assert.AreEqual(0, tester.GetEventHandlers("Mock").Length,
                "Empty array is expected.");
        }

        /// <summary>
        /// Test GetEventHandlers(string eventName),
        /// when eventName is not existed, empty array returned.
        /// </summary>
        [Test]
        public void TestGetEventHandlers()
        {
            // first add two for test
            const string Name = "Mock";
            ExtensionEventHandlerDelegate another = new ExtensionEventHandlerDelegate(
                new HttpRequestUserInterfaceEventHandler().HandleEvent);
            Assert.IsTrue(tester.AddEventHandler(Name, @delegate),
                "Should be added");
            Assert.IsTrue(tester.AddEventHandler(Name, another),
                "Should be added");

            Assert.AreEqual(2, tester.GetEventHandlers(Name).Length,
                "Only one should be existed");
            Assert.AreEqual(@delegate, tester.GetEventHandlers(Name)[0],
                "Get result is not correct");
            Assert.AreEqual(another, tester.GetEventHandlers(Name)[1],
                "Get result is not correct");


            Assert.IsTrue(tester.RemoveEventHandler(Name, @delegate),
                "Should be removed");
            Assert.AreEqual(1, tester.GetEventHandlers(Name).Length,
                "Should only one left");
            Assert.AreEqual(another, tester.GetEventHandlers(Name)[0],
                "Failed to add correctly.");

            Assert.IsTrue(tester.RemoveEventHandler(Name, another),
                "Should be removed");

            Assert.AreEqual(0, tester.GetEventHandlers(Name).Length,
                "no one left");
        }

        /// <summary>
        /// Test FireEvent(string eventName, object sender, ExtensionEventArgs args),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestFireEvent_EventNameIsNull()
        {
            tester.FireEvent(null, this, args);
        }

        /// <summary>
        /// Test FireEvent(string eventName, object sender, ExtensionEventArgs args),
        /// when eventName is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestFireEvent_EventNameIsEmpty()
        {
            tester.FireEvent(" \t \r \n ", this, args);
        }

        /// <summary>
        /// Test FireEvent(string eventName, object sender, ExtensionEventArgs args),
        /// when sender is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestFireEvent_SenderIsNull()
        {
            tester.FireEvent(args.EventName, null, args);
        }

        /// <summary>
        /// Test FireEvent(string eventName, object sender, ExtensionEventArgs args),
        /// when args is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestFireEvent_ArgsIsNull()
        {
            tester.FireEvent(args.EventName, this, null);
        }

        /// <summary>
        /// Test FireEvent(string eventName, object sender, ExtensionEventArgs args),
        /// when all arguments are valid, the event handlers will be fired.
        /// </summary>
        [Test]
        public void TestFireEvent()
        {
            MockExtensionEventHandler anotherMock = new MockExtensionEventHandler();
            ExtensionEventHandlerDelegate another = new ExtensionEventHandlerDelegate(anotherMock.HandleEvent);
            tester.AddEventHandler(args.EventName, @delegate);
            tester.AddEventHandler(args.EventName, another);

            tester.FireEvent(args.EventName, this, args);

            Assert.AreEqual(mockHandler.Args, args, "Not fired correctly");
            Assert.AreEqual(mockHandler.Sender, this, "Not fired correctly");
            Assert.AreEqual(anotherMock.Args, args, "Not fired correctly");
            Assert.AreEqual(anotherMock.Sender, this, "Not fired correctly");
        }
    }
}
