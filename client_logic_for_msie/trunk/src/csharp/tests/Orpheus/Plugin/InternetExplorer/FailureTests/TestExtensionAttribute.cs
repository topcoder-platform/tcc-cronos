using System;
using NUnit.Framework;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for ExtensionAttribute class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestExtensionAttribute
    {
        /// <summary>
        /// Tests <c>ExtensionAttribute(string name)</c> method with null string name
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestExtensionAttribute_NullName()
        {
            new ExtensionAttribute(null);
        }
        /// <summary>
        /// Tests <c>ExtensionAttribute(string name)</c> method with empty string name
        /// ArgumentException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentException))]
        public void TestExtensionAttribute_EmptyName()
        {
            new ExtensionAttribute(" ");
        }
    }
}
