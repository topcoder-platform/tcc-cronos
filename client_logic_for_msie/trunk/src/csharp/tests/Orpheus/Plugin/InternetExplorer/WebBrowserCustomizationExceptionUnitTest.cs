/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * WebBrowserCustomizationExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="WebBrowserCustomizationException"/> class.
    /// <see cref="WebBrowserCustomizationException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class WebBrowserCustomizationExceptionUnitTest
    {
        /// <summary>
        /// <see cref="WebBrowserCustomizationException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(WebBrowserCustomizationException)),
                "The WebBrowserCustomizationException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor WebBrowserCustomizationException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new WebBrowserCustomizationException(),
                "Failed to create instance of WebBrowserCustomizationException.");
        }

        /// <summary>
        /// Test ctor WebBrowserCustomizationException(string message), message is null, allowed,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            WebBrowserCustomizationException exception = new WebBrowserCustomizationException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(WebBrowserCustomizationException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor WebBrowserCustomizationException(string message), message is string.Empty, allowed
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            WebBrowserCustomizationException exception = new WebBrowserCustomizationException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor WebBrowserCustomizationException(string message), message is meanful string,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            WebBrowserCustomizationException exception = new WebBrowserCustomizationException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserCustomizationException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            WebBrowserCustomizationException exception = new WebBrowserCustomizationException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(WebBrowserCustomizationException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserCustomizationException(string message, Exception cause),
        /// with message is string.Empty, allowed,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            WebBrowserCustomizationException exception =
                new WebBrowserCustomizationException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserCustomizationException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            WebBrowserCustomizationException exception =
                new WebBrowserCustomizationException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserCustomizationException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="WebBrowserCustomizationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            WebBrowserCustomizationException exception =
                new WebBrowserCustomizationException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of WebBrowserCustomizationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test WebBrowserCustomizationException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            WebBrowserCustomizationException exception =
                new WebBrowserCustomizationException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            WebBrowserCustomizationException expected = (WebBrowserCustomizationException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
