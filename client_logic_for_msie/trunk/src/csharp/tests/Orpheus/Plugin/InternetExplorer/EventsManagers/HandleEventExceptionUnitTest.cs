/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * HandleEventExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// Unit Test for <see cref="HandleEventException"/> class.
    /// <see cref="HandleEventException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class HandleEventExceptionUnitTest
    {
        /// <summary>
        /// <see cref="HandleEventException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(HandleEventException)),
                "The HandleEventException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor HandleEventException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new HandleEventException(),
                "Failed to create instance of HandleEventException.");
        }

        /// <summary>
        /// Test ctor HandleEventException(string message), message is null, allowed,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            HandleEventException exception = new HandleEventException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(HandleEventException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor HandleEventException(string message), message is string.Empty, allowed
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            HandleEventException exception = new HandleEventException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor HandleEventException(string message), message is meanful string,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            HandleEventException exception = new HandleEventException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test HandleEventException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            HandleEventException exception = new HandleEventException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(HandleEventException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test HandleEventException(string message, Exception cause),
        /// with message is string.Empty, allowed,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            HandleEventException exception = new HandleEventException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test HandleEventException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            HandleEventException exception = new HandleEventException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test HandleEventException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="HandleEventException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            HandleEventException exception = new HandleEventException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of HandleEventException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test HandleEventException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            HandleEventException exception =
                new HandleEventException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            HandleEventException expected = (HandleEventException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
