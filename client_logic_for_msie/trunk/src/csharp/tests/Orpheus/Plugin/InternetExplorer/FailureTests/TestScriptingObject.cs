using System;
using NUnit.Framework;
using SHDocVw;
using TopCoder.Util.ConfigurationManager;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for ScriptingObject class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestScriptingObject
    {
        /// <summary>
        /// The MsieClientLogic instance used to test.
        /// </summary>
        private MsieClientLogic logic = null;

        /// <summary>
        /// The ScriptingObject instance used to test.
        /// </summary>
        private ScriptingObject so = null;

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
            so = new ScriptingObject(logic);
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
        /// Tests <c>ScriptingObject(MsieClientLogic context)</c> method with null MsieClientLogic context
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestScriptingObject_NullContext()
        {
            new ScriptingObject(null);
        }

        /// <summary>
        /// Tests <c>SetCurrentTarget(string hash, int sequence)</c> method with null string hash
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestSetCurrentTarget_NullHash()
        {
            so.SetCurrentTarget(null, 1);
        }
        /// <summary>
        /// Tests <c>SetCurrentTarget(string hash, int sequence)</c> method with empty string hash
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestSetCurrentTarget_EmptyHash()
        {
            so.SetCurrentTarget(" ", 1);
        }

        /// <summary>
        /// Tests <c>IsPopup(IHTMLWindow2 window)</c> method with null IHTMLWindow2 window
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestIsPopup_NullWindow()
        {
            so.IsPopup(null);
        }
    }
}
