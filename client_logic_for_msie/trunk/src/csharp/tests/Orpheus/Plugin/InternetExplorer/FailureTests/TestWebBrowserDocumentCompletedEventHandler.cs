using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.EventsManagers.Handlers;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for WebBrowserDocumentCompletedEventHandler class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestWebBrowserDocumentCompletedEventHandler
    {
        /// <summary>
        /// Tests <c>WebBrowserDocumentCompletedEventHandler(string configurationNamespace)</c>
        /// method with null string configurationNamespace
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestWebBrowserDocumentCompletedEventHandler_NullConfigurationNamespace()
        {
            new WebBrowserDocumentCompletedEventHandler(null);
        }
        /// <summary>
        /// Tests <c>WebBrowserDocumentCompletedEventHandler(string configurationNamespace)</c>
        /// method with empty string configurationNamespace
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestWebBrowserDocumentCompletedEventHandler_EmptyConfigurationNamespace()
        {
            new WebBrowserDocumentCompletedEventHandler(" ");
        }

        /// <summary>
        /// Tests <c>HandleEvent(object sender, ExtensionEventArgs args)</c> method with null ExtensionEventArgs args
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestHandleEvent_NullArgs()
        {
            new WebBrowserDocumentCompletedEventHandler().HandleEvent(new object(), null);
        }
    }
}
