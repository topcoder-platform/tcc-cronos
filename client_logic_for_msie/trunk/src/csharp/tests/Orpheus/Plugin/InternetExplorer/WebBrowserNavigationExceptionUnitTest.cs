/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserNavigationExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="WebBrowserNavigationException"/> class.
    /// <see cref="WebBrowserNavigationException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class WebBrowserNavigationExceptionUnitTest
    {
        /// <summary>
        /// <see cref="WebBrowserNavigationException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(WebBrowserNavigationException)),
                "The WebBrowserNavigationException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor WebBrowserNavigationException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new WebBrowserNavigationException(),
                "Failed to create instance of WebBrowserNavigationException.");
        }

        /// <summary>
        /// Test ctor WebBrowserNavigationException(string message), message is null, allowed,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            WebBrowserNavigationException exception = new WebBrowserNavigationException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(WebBrowserNavigationException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor WebBrowserNavigationException(string message), message is string.Empty, allowed
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            WebBrowserNavigationException exception = new WebBrowserNavigationException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor WebBrowserNavigationException(string message), message is meanful string,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            WebBrowserNavigationException exception = new WebBrowserNavigationException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserNavigationException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            WebBrowserNavigationException exception = new WebBrowserNavigationException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(WebBrowserNavigationException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserNavigationException(string message, Exception cause),
        /// with message is string.Empty, allowed,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            WebBrowserNavigationException exception = new WebBrowserNavigationException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserNavigationException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            WebBrowserNavigationException exception = new WebBrowserNavigationException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserNavigationException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="WebBrowserNavigationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            WebBrowserNavigationException exception = new WebBrowserNavigationException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserNavigationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserNavigationException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            WebBrowserNavigationException exception =
                new WebBrowserNavigationException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            WebBrowserNavigationException expected = (WebBrowserNavigationException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
