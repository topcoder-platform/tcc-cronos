/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * TestObjectEventHandlerUnitTest.cs
 */

using System;
using NUnit.Framework;
using TopCoder.Util.Hash.Algorithm;
using MsHtmHstInterop;
using Mshtml;
using SHDocVw;
using Orpheus.Plugin.InternetExplorer.Mock;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers
{
    /// <summary>
    /// Unit test for <c>TestObjectEventHandler</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class TestObjectEventHandlerUnitTest
    {
        /// <summary>
        /// The event name of args to handle.
        /// </summary>
        private const string EVENT_NAME = "TestObject";

        /// <summary>
        /// Represents the instance to perform test on.
        /// </summary>
        private TestObjectEventHandler tester;

        /// <summary>
        /// The argument used in handle event.
        /// </summary>
        private ExtensionEventArgs args;

        /// <summary>
        /// The context used in args.
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
            args = new ExtensionEventArgs(EVENT_NAME, context);

            tester = new TestObjectEventHandler();
        }

        /// <summary>
        /// Tear down for each each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            TestHelper.ClearNamespace();
        }

        /// <summary>
        /// Test inheritance,
        /// TestObjectEventHandler should implement IExtensionEventHandler.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IExtensionEventHandler,
                "TestObjectEventHandler should implement IExtensionEventHandler");
        }

        /// <summary>
        /// Test const DefaultConfigurationNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.
        /// </summary>
        [Test]
        public void TestDefaultConfigurationNamespace()
        {
            Assert.AreEqual("Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers",
                TestObjectEventHandler.DefaultConfigurationNamespace,
                "should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'");
        }

        /// <summary>
        /// Test const DefaultObjectFactoryNamespace,
        /// it should be 'Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers'.
        /// </summary>
        [Test]
        public void TestDefaultObjectFactoryNamespace()
        {
            Assert.AreEqual("TopCoder.Util.ObjectFactory",
                TestObjectEventHandler.DefaultObjectFactoryNamespace,
                "should be 'TopCoder.Util.ObjectFactory'");
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(),
        /// an instance with the default namespace should be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester,
                "an instance with the default namespace should be created.");
            Assert.AreEqual(TestObjectEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(tester, "configurationNamespace"),
                "configurationNamespace should be set to default namespace");
            Assert.IsNotNull(TestHelper.GetFieldValue(tester, "hashAlgorithm"),
                "hashAlgorithm should be initialized");
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace),
        /// when configurationNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ConfigurationNamespaceIsNull()
        {
            new TestObjectEventHandler(null, TestObjectEventHandler.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace),
        /// when configurationNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ConfigurationNamespaceIsEmpty()
        {
            new TestObjectEventHandler(" \t\r\n ", TestObjectEventHandler.DefaultObjectFactoryNamespace);
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace),
        /// when objectFactoryNamespace is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ObjectFactoryNamespaceIsNull()
        {
            new TestObjectEventHandler(TestObjectEventHandler.DefaultConfigurationNamespace, null);
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace),
        /// when objectFactoryNamespace is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_ObjectFactoryNamespaceIsEmpty()
        {
            new TestObjectEventHandler(TestObjectEventHandler.DefaultConfigurationNamespace, " \r\t \n");
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace),
        /// an instance with the namespaces given.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            TestObjectEventHandler handler = new TestObjectEventHandler(
                TestObjectEventHandler.DefaultConfigurationNamespace,
                TestObjectEventHandler.DefaultObjectFactoryNamespace);

            Assert.IsNotNull(handler,
                "an instance with the default namespace should be created.");
            Assert.AreEqual(TestObjectEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "configurationNamespace should be set to default namespace");
            Assert.IsNotNull(TestHelper.GetFieldValue(handler, "hashAlgorithm"),
                "hashAlgorithm should be initialized");
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(HashAlgorithm hashAlgorithm),
        /// when hashAlgorithm is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor3_HashAlgorithmIsNull()
        {
            new TestObjectEventHandler(null);
        }

        /// <summary>
        /// Test ctor TestObjectEventHandler(HashAlgorithm hashAlgorithm),
        /// when hashAlgorithm is valid, instance should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            HashAlgorithm algorithm = new MD5Algorithm();
            TestObjectEventHandler handler = new TestObjectEventHandler(algorithm);


            Assert.IsNotNull(handler,
                "an instance with the default namespace should be created.");
            Assert.AreEqual(TestObjectEventHandler.DefaultConfigurationNamespace,
                TestHelper.GetFieldValue(handler, "configurationNamespace"),
                "configurationNamespace should be set to default namespace");
            Assert.AreEqual(algorithm, TestHelper.GetFieldValue(handler, "hashAlgorithm"),
                "hashAlgorithm should be initialized");
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// when sender is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestHandleEvent_SenderIsNull()
        {
            tester.HandleEvent(null, args);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// when args is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestHandleEvent_ArgsIsNull()
        {
            tester.HandleEvent(this, null);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// since the context don't existed, HandleEventException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(HandleEventException))]
        public void TestHandleEvent_Failed()
        {
            tester.HandleEvent(this, args);
        }

        /// <summary>
        /// Test HandleEvent(object sender, ExtensionEventArgs args),
        /// the object should be tested.
        /// </summary>
        [Test]
        public void TestHandleHandle()
        {
            BrowserForm form = new BrowserForm();
            MsieClientLogic context = new MsieClientLogic(form.GetWebBrowserClass());

            TestObjectEventHandler handler = new TestObjectEventHandler();

            IHTMLElement element = (form.GetWebBrowserClass().Document as IHTMLDocument2).createElement("B");
            element.innerText = "12 34 Ab cD";
            ExtensionEventArgs args = new ExtensionEventArgs("TestObject", context, new object[] {element});
            args.Context.Persistence["hash"] = "ef73781effc5774100f87fe2f437a435";
            handler.HandleEvent(this, args);
        }
    }
}
