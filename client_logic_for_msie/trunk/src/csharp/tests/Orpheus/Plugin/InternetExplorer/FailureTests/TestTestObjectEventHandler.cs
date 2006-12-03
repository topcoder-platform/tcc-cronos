using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for TestObjectEventHandler class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestTestObjectEventHandler
    {
        /// <summary>
        /// Tests <c>TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestTestObjectEventHandler_NullConfigurationNamespace()
        {
            new TestObjectEventHandler(null, "ns");
        }
        /// <summary>
        /// Tests <c>TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestTestObjectEventHandler_EmptyConfigurationNamespace()
        {
            new TestObjectEventHandler(" ", "ns");
        }
        /// <summary>
        /// Tests <c>TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with null string objectFactoryNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestTestObjectEventHandler_NullObjectFactoryNamespace()
        {
            new TestObjectEventHandler("ns", null);
        }
        /// <summary>
        /// Tests <c>TestObjectEventHandler(string configurationNamespace, string objectFactoryNamespace)</c>
        /// method with empty string objectFactoryNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestTestObjectEventHandler_EmptyObjectFactoryNamespace()
        {
            new TestObjectEventHandler("ns", " ");
        }
        /// <summary>
        /// Tests <c>TestObjectEventHandler(HashAlgorithm hashAlgorithm)</c> method with null HashAlgorithm hashAlgorithm
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestTestObjectEventHandler_NullHashAlgorithm()
        {
            new TestObjectEventHandler(null);
        }
    }
}
