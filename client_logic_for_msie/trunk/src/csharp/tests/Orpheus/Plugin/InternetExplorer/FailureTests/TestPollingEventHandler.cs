using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for PollingEventHandler class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestPollingEventHandler
    {
        /// <summary>
        /// Tests <c>PollingEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestPollingEventHandler_NullConfigurationNamespace()
        {
            new PollingEventHandler(null, "ns");
        }
        /// <summary>
        /// Tests <c>PollingEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestPollingEventHandler_EmptyConfigurationNamespace()
        {
            new PollingEventHandler(" ", "ns");
        }
        /// <summary>
        /// Tests <c>PollingEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with null string objectFactoryNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestPollingEventHandler_NullObjectFactoryNamespace()
        {
            new PollingEventHandler("ns", null);
        }
        /// <summary>
        /// Tests <c>PollingEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with empty string objectFactoryNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestPollingEventHandler_EmptyObjectFactoryNamespace()
        {
            new PollingEventHandler("ns", " ");
        }
        /// <summary>
        /// Tests <c>PollingEventHandler(IRSSParser rssParser)</c> method with null IRSSParser rssParser
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestPollingEventHandler_NullRssParser()
        {
            new PollingEventHandler(null);
        }
    }
}
