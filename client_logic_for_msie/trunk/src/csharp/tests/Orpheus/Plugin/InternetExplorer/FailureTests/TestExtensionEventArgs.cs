using System;
using NUnit.Framework;
using SHDocVw;
using TopCoder.Util.ConfigurationManager;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for ExtensionEventArgs class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestExtensionEventArgs
    {
        /// <summary>
        /// The MsieClientLogic instance used to test.
        /// </summary>
        private MsieClientLogic logic = null;

        /// <summary>
        /// The ConfigManager instance used to test.
        /// </summary>
        private ConfigManager cm = ConfigManager.GetInstance();
        /// <summary>
        /// SetUp the test environment for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            cm.Clear(false);
            cm.LoadFile(@"../../test_files/failure/client_logic_for_msie.xml");
            cm.LoadFile(@"../../test_files/failure/object_factory.xml");
            logic = new MsieClientLogic(new WebBrowserClass());
        }
        /// <summary>
        /// TearDown the test environment for each test case.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            cm.Clear(false);
        }

        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context)</c> method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionEventArgs_NullEventName()
        {
            new ExtensionEventArgs(null, logic);
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context)</c> method with
        /// empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestExtensionEventArgs_EmptyEventName()
        {
            new ExtensionEventArgs(" ", logic);
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context)</c> method with
        /// null MsieClientLogic context
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionEventArgs_NullContext()
        {
            new ExtensionEventArgs("name", null);
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters)</c>
        /// method with null string eventName
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionEventArgs2_NullEventName()
        {
            new ExtensionEventArgs(null, logic, new object[] { });
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters)</c>
        /// method with empty string eventName
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestExtensionEventArgs3_EmptyEventName()
        {
            new ExtensionEventArgs(" ", logic, new object[] { });
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters)</c>
        /// method with null MsieClientLogic context
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionEventArgs2_NullContext()
        {
            new ExtensionEventArgs("name", null, new object[] { });
        }
        /// <summary>
        /// Tests <c>ExtensionEventArgs(string eventName, MsieClientLogic context, object[] parameters)</c>
        /// method with null object[] parameters
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionEventArgs_NullParameters()
        {
            new ExtensionEventArgs("name", logic, null);
        }
    }
}
