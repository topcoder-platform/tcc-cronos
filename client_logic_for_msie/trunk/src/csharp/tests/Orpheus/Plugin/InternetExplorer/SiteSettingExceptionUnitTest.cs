/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * SiteSettingExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for <see cref="SiteSettingException"/> class.
    /// <see cref="SiteSettingException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class SiteSettingExceptionUnitTest
    {
        /// <summary>
        /// <see cref="SiteSettingException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(SiteSettingException)),
                "The SiteSettingException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor SiteSettingException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new SiteSettingException(),
                "Failed to create instance of SiteSettingException.");
        }

        /// <summary>
        /// Test ctor SiteSettingException(string message), message is null, allowed,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            SiteSettingException exception = new SiteSettingException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(SiteSettingException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor SiteSettingException(string message), message is string.Empty, allowed
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            SiteSettingException exception = new SiteSettingException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor SiteSettingException(string message), message is meanful string,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            SiteSettingException exception = new SiteSettingException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test SiteSettingException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            SiteSettingException exception = new SiteSettingException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(SiteSettingException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test SiteSettingException(string message, Exception cause), with message is string.Empty, allowed,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            SiteSettingException exception = new SiteSettingException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test SiteSettingException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            SiteSettingException exception = new SiteSettingException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test SiteSettingException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="SiteSettingException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            SiteSettingException exception = new SiteSettingException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of SiteSettingException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test SiteSettingException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            SiteSettingException exception = new SiteSettingException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            SiteSettingException expected = (SiteSettingException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
