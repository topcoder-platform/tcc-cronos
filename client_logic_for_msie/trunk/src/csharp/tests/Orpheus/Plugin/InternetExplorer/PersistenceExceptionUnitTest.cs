/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * PersistenceExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="PersistenceException"/> class.
    /// <see cref="PersistenceException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class PersistenceExceptionUnitTest
    {
        /// <summary>
        /// <see cref="PersistenceException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(PersistenceException)),
                "The PersistenceException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor PersistenceException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new PersistenceException(),
                "Failed to create instance of PersistenceException.");
        }

        /// <summary>
        /// Test ctor PersistenceException(string message), message is null, allowed,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            PersistenceException exception = new PersistenceException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(PersistenceException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor PersistenceException(string message), message is string.Empty, allowed
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            PersistenceException exception = new PersistenceException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor PersistenceException(string message), message is meanful string,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            PersistenceException exception = new PersistenceException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test PersistenceException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            PersistenceException exception = new PersistenceException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(PersistenceException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test PersistenceException(string message, Exception cause), with message is string.Empty, allowed,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            PersistenceException exception = new PersistenceException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test PersistenceException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            PersistenceException exception = new PersistenceException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test PersistenceException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="PersistenceException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            PersistenceException exception = new PersistenceException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of PersistenceException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test PersistenceException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            PersistenceException exception = new PersistenceException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            PersistenceException expected = (PersistenceException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
