/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ClientLogicExtensionExceptionUnitTest.cs
 */
using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="ClientLogicExtensionException"/> class.
    /// <see cref="ClientLogicExtensionException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ClientLogicExtensionExceptionUnitTest
    {
        /// <summary>
        /// <see cref="ClientLogicExtensionException"/> should inherit from
        /// <see cref="ApplicationException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(ClientLogicExtensionException)),
                "The ClientLogicExtensionException class should inherit from ApplicationException.");
        }

        /// <summary>
        /// Test ctor ClientLogicExtensionException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new ClientLogicExtensionException(),
                "Failed to create instance of ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor ClientLogicExtensionException(string message), message is null, allowed,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            ClientLogicExtensionException exception = new ClientLogicExtensionException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(ClientLogicExtensionException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor ClientLogicExtensionException(string message), message is string.Empty, allowed
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            ClientLogicExtensionException exception = new ClientLogicExtensionException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor ClientLogicExtensionException(string message), message is meanful string,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            ClientLogicExtensionException exception = new ClientLogicExtensionException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ClientLogicExtensionException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            ClientLogicExtensionException exception = new ClientLogicExtensionException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(ClientLogicExtensionException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ClientLogicExtensionException(string message, Exception cause), with message is string.Empty, allowed,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            ClientLogicExtensionException exception = new ClientLogicExtensionException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ClientLogicExtensionException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            ClientLogicExtensionException exception = new ClientLogicExtensionException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ClientLogicExtensionException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="ClientLogicExtensionException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            ClientLogicExtensionException exception = new ClientLogicExtensionException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ClientLogicExtensionException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ClientLogicExtensionException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            ClientLogicExtensionException exception =
                new ClientLogicExtensionException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            ClientLogicExtensionException expected = (ClientLogicExtensionException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
