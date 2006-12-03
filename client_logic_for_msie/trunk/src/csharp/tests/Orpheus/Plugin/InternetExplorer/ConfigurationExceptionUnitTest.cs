/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ConfigurationExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="ConfigurationException"/> class.
    /// <see cref="ConfigurationException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ConfigurationExceptionUnitTest
    {
        /// <summary>
        /// <see cref="ConfigurationException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(ConfigurationException)),
                "The ConfigurationException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor ConfigurationException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new ConfigurationException(),
                "Failed to create instance of ConfigurationException.");
        }

        /// <summary>
        /// Test ctor ConfigurationException(string message), message is null, allowed,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            ConfigurationException exception = new ConfigurationException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(ConfigurationException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor ConfigurationException(string message), message is string.Empty, allowed
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            ConfigurationException exception = new ConfigurationException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor ConfigurationException(string message), message is meanful string,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            ConfigurationException exception = new ConfigurationException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ConfigurationException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            ConfigurationException exception = new ConfigurationException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(ConfigurationException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ConfigurationException(string message, Exception cause), with message is string.Empty, allowed,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            ConfigurationException exception = new ConfigurationException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ConfigurationException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            ConfigurationException exception = new ConfigurationException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ConfigurationException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="ConfigurationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            ConfigurationException exception = new ConfigurationException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of ConfigurationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ConfigurationException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            ConfigurationException exception = new ConfigurationException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            ConfigurationException expected = (ConfigurationException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
