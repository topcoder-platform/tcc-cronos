/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ExtensionEventArgsUnitTest.cs
 */
using System;
using NUnit.Framework;
using MsHtmHstInterop;
using SHDocVw;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <c>ExtensionEventArgs</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ExtensionEventArgsUnitTest
    {
        /// <summary>
        /// The event name for tester.
        /// </summary>
        private const string EVENT_NAME = "eventName";

        /// <summary>
        /// The context for tester.
        /// </summary>
        private MsieClientLogic context;

        /// <summary>
        /// The parameters for tester.
        /// </summary>
        private object[] parameters;

        /// <summary>
        /// The instance of <c>ExtensionEventArgs</c> to perform test on.
        /// </summary>
        private ExtensionEventArgs tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TestHelper.LoadConfigFile();

            context = new MsieClientLogic(new WebBrowserClass());
            parameters = new object[] {"a" , 1};
            tester = new ExtensionEventArgs(EVENT_NAME, context);
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
        /// ExtensionEventArgs should extend from EventArgs.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is EventArgs,
                "ExtensionEventArgs should extend from EventArgs.");
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor1_EventNameIsNull()
        {
            new ExtensionEventArgs(null, context);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context),
        /// when eventName is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor1_EventNameIsEmpty()
        {
            new ExtensionEventArgs(" \r \t \n  ", context);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context),
        /// when context is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor1_ContextIsNull()
        {
            new ExtensionEventArgs(EVENT_NAME, null);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context),
        /// when both arguments are valid, instance with the give argument should be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(tester,
                "when both arguments are valid, instance with the give argument should be created.");
            Assert.AreEqual(EVENT_NAME, tester.EventName, "Failed to set event name");
            Assert.AreEqual(context, tester.Context, "Failed to set context");
            Assert.AreEqual(0, tester.Parameters.Length, "Failed to set parameters");
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters),
        /// when eventName is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_EventNameIsNull()
        {
            new ExtensionEventArgs(null, context, parameters);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters),
        /// when eventName is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestCtor2_EventNameIsEmpty()
        {
            new ExtensionEventArgs(" \r \t \n ", context, parameters);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters),
        /// when context is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ContextIsNull()
        {
            new ExtensionEventArgs(EVENT_NAME, null, parameters);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters),
        /// when context is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestCtor2_ParamtersIsNull()
        {
            new ExtensionEventArgs(EVENT_NAME, context, null);
        }

        /// <summary>
        /// Test ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters),
        /// when all arguments are valid, instance should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            ExtensionEventArgs args = new ExtensionEventArgs(EVENT_NAME, context, parameters);

            Assert.IsNotNull(args,
                "when all arguments are valid, instance should be created.");
            Assert.AreEqual(EVENT_NAME, args.EventName, "Failed to set event name");
            Assert.AreEqual(context, args.Context, "Failed to set context");
            Assert.AreEqual(parameters, args.Parameters, "Failed to set parameters");
        }

        /// <summary>
        /// Test property EventName Getter,
        /// the event name set in constructor should be returned.
        /// </summary>
        [Test]
        public void TestEventName_Getter()
        {
            Assert.AreEqual(EVENT_NAME, tester.EventName,
                "the event name set in constructor should be returned.");
        }

        /// <summary>
        /// Test property Context Getter,
        /// the context set in constructor should be returned.
        /// </summary>
        [Test]
        public void TestContext_Getter()
        {
            Assert.AreEqual(context, tester.Context,
                "the context set in constructor should be returned.");
        }

        /// <summary>
        /// Test property Parameters Getter,
        /// the parameters set in constructor should be returned.
        /// </summary>
        [Test]
        public void TestParameters_Getter()
        {
            ExtensionEventArgs args = new ExtensionEventArgs(EVENT_NAME, context, parameters);
            Assert.AreEqual(parameters, args.Parameters,
                "the parameters set in constructor should be returned.");
        }
    }
}
