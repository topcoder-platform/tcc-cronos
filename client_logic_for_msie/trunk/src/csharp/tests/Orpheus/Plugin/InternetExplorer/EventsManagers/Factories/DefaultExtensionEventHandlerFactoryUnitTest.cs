/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * DefaultExtensionEventHandlerFactoryUnitTest.cs
 */

using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Factories
{
    /// <summary>
    /// Unit Test for <c>DefaultExtensionEventHandlerFactory</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class DefaultExtensionEventHandlerFactoryUnitTest
    {
        /// <summary>
        /// An instance of <c>DefaultExtensionEventHandlerFactory</c> to perform test on.
        /// </summary>
        private DefaultExtensionEventHandlerFactory tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            tester = new DefaultExtensionEventHandlerFactory();
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
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Factories'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Factories",
                DefaultExtensionEventHandlerFactory.DefaultConfigurationNamespace,
                "DefaultConfigurationNamespace should be " +
                "'Orpheus.Plugin.InternetExplorer.EventsManagers.Factories'");
        }

        /// <summary>
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'TopCoder.Util.ObjectFactory'.
        /// </summary>
        [Test]
        public void TestDefaultObjectFactoryNamespace()
        {
            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                DefaultExtensionEventHandlerFactory.DefaultObjectFactoryNamespace,
                "DefaultObjectFactoryNamespace should be 'TopCoder.Util.ObjectFactory'");
        }

        /// <summary>
        /// Test ctor DefaultExtensionEventHandlerFactory(),
        /// when the config file is valid,
        /// </summary>
        public void TestCtor1()
        {
            Assert.IsNotNull(tester, "Failed to create the instance");
            Assert.AreEqual(DefaultExtensionEventHandlerFactory.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(tester, "configurationNamespace"),
                "The configurationNamespace is not set to default");
            Assert.AreEqual(DefaultExtensionEventHandlerFactory.DefaultObjectFactoryNamespace,
                TestHelper.GetFieldValue(tester, "objectFactoryNamespace"),
                "The objectFactoryNamespace is not set to default");
        }

        /// <summary>
        /// Test DefaultExtensionEventHandlerFactory(string configurationNamespace,
        /// string objectFactoryNamespace), when configurationNamespace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new DefaultExtensionEventHandlerFactory(null,
                DefaultExtensionEventHandlerFactory.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test DefaultExtensionEventHandlerFactory(string configurationNamespace,
        /// string objectFactoryNamespace), when configurationNamespace is empty,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmpty()
        {
            new DefaultExtensionEventHandlerFactory("  \r \t \n ",
                DefaultExtensionEventHandlerFactory.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test DefaultExtensionEventHandlerFactory(string configurationNamespace,
        /// string objectFactoryNamespace), when objectFactoryNamespace is null,
        /// ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ObjectFactoryNamespaceIsNull()
        {
            new DefaultExtensionEventHandlerFactory(
                DefaultExtensionEventHandlerFactory.DefaultConfigurationNamespace, null);
        }

        /// <summary>
        /// Test DefaultExtensionEventHandlerFactory(string configurationNamespace,
        /// string objectFactoryNamespace), when objectFactoryNamespace is empty,
        /// ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ObjectFactoryNamespaceIsEmpty()
        {
            new DefaultExtensionEventHandlerFactory(
                DefaultExtensionEventHandlerFactory.DefaultConfigurationNamespace, "  \r \t \n ");
        }

        /// <summary>
        /// Test CreateHandlers(string eventName),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCreateHandlers_EventNameIsNull()
        {
            tester.CreateHandlers(null);
        }

        /// <summary>
        /// Test CreateHandlers(string eventName),
        /// when eventName is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCreateHandlers_EventNameIsEmpty()
        {
            tester.CreateHandlers(" \r \n \t  ");
        }

        /// <summary>
        /// Test CreateHandlers(string eventName),
        /// when eventName is not existed in config file, EventHandlerCreationException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(EventHandlerCreationException))]
        public void TestCreateHandlers_EventNameIsNotExisted()
        {
            tester.CreateHandlers("NotExisted");
        }

        /// <summary>
        /// Test CreateHandlers(string eventName),
        /// when eventName don't contain handlers, empty array is returned.
        /// </summary>
        [Test]
        public void TestCreateHandlers_Empty()
        {
            Assert.AreEqual(0, tester.CreateHandlers("empty").Length,
                "when eventName don't contain handlers, empty array is returned.");
        }

        /// <summary>
        /// Test CreateHandlers(string eventName),
        /// when eventName contain handlers, the create handler should returned.
        /// </summary>
        [Test]
        public void TestCreateHandlers()
        {
            IExtensionEventHandler[] handlers = tester.CreateHandlers("test");
            Assert.AreEqual(2, handlers.Length, "There should 2 handlers");
            Assert.IsTrue(handlers[0] is PollingEventHandler, "Failed to create handler correctly");
            Assert.IsTrue(handlers[1] is HttpRequestUserInterfaceEventHandler,
                "Failed to create handler correctly");
        }
    }
}
