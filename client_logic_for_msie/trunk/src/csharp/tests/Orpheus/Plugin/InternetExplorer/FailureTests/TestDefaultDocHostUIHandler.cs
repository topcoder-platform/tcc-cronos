using System;
using NUnit.Framework;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for DefaultDocHostUIHandler class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestDefaultDocHostUIHandler
    {
        /// <summary>
        /// Tests <c>DefaultDocHostUIHandler(MsieClientLogic context)</c> method with null MsieClientLogic context
        /// ArgumentNullException should be thrown.
        /// </summary>
        [Test, ExpectedException(typeof (ArgumentNullException))]
        public void TestDefaultDocHostUIHandler_NullContext()
        {
            new DefaultDocHostUIHandler(null);
        }
    }
}
