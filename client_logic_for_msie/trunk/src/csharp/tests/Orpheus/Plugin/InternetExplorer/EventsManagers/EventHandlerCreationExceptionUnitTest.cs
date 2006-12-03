/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * EventHandlerCreationExceptionUnitTest.cs
 */

using System;
using System.IO;
using NUnit.Framework;

using System.Runtime.Serialization.Formatters.Binary;

namespace Orpheus.Plugin.InternetExplorer.EventsManagers
{
    /// <summary>
    /// Unit Test for <see cref="EventHandlerCreationException"/> class.
    /// <see cref="EventHandlerCreationException"/> is a simple class, only test the constructors.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class EventHandlerCreationExceptionUnitTest
    {
        /// <summary>
        /// <see cref="EventHandlerCreationException"/> should inherit from
        /// <see cref="ClientLogicExtensionException"/> interface.
        /// </summary>
        [Test]
        public void TestInheritence()
        {
            Assert.IsTrue(typeof(ApplicationException).IsAssignableFrom(typeof(EventHandlerCreationException)),
                "The EventHandlerCreationException class should inherit from ClientLogicExtensionException.");
        }

        /// <summary>
        /// Test ctor EventHandlerCreationException(), an instance will be created.
        /// </summary>
        [Test]
        public void TestCtor1()
        {
            Assert.IsNotNull(new EventHandlerCreationException(),
                "Failed to create instance of EventHandlerCreationException.");
        }

        /// <summary>
        /// Test ctor EventHandlerCreationException(string message), message is null, allowed,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsNull()
        {
            EventHandlerCreationException exception = new EventHandlerCreationException(null);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is null.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(EventHandlerCreationException).Name) >= 0,
                "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor EventHandlerCreationException(string message), message is string.Empty, allowed
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2_MessageIsEmpty()
        {
            EventHandlerCreationException exception = new EventHandlerCreationException(string.Empty);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is empty string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test ctor EventHandlerCreationException(string message), message is meanful string,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor2()
        {
            EventHandlerCreationException exception = new EventHandlerCreationException("Unit Test");
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
        }

        /// <summary>
        /// Test EventHandlerCreationException(string message, Exception cause), with message is null, allowed,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsNull()
        {
            Exception cause = new Exception();
            EventHandlerCreationException exception = new EventHandlerCreationException(null, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is string.");
            Assert.IsTrue(exception.Message.IndexOf(typeof(EventHandlerCreationException).Name) >= 0,
                "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test EventHandlerCreationException(string message, Exception cause),
        /// with message is string.Empty, allowed,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_MessageIsEmpty()
        {
            Exception cause = new Exception();
            EventHandlerCreationException exception = new EventHandlerCreationException(string.Empty, cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is string.");
            Assert.AreEqual(string.Empty, exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test EventHandlerCreationException(string message, Exception cause), with cause is null, allowed,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3_CauseIsNull()
        {
            EventHandlerCreationException exception = new EventHandlerCreationException("Unit Test", null);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(null, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test EventHandlerCreationException(string message, Exception cause), with both arguments are meanful,
        /// instance of <see cref="EventHandlerCreationException"/> should be created.
        /// </summary>
        [Test]
        public void TestCtor3()
        {
            Exception cause = new Exception();
            EventHandlerCreationException exception = new EventHandlerCreationException("Unit Test", cause);
            Assert.IsNotNull(exception,
                "Failed to create instance of EventHandlerCreationException when message is string.");
            Assert.AreEqual("Unit Test", exception.Message, "The created exception is not correct.");
            Assert.AreEqual(cause, exception.InnerException, "The created exception is not correct.");
        }

        /// <summary>
        /// Test EventHandlerCreationException is serializable.
        /// </summary>
        [Test]
        public void TestSerializable()
        {
            BinaryFormatter bf = new BinaryFormatter();
            MemoryStream stream = new MemoryStream();
            EventHandlerCreationException exception =
                new EventHandlerCreationException("UnitTest", new Exception("msg"));
            bf.Serialize(stream, exception);

            stream.Flush();
            stream.Seek(0, SeekOrigin.Begin);

            EventHandlerCreationException expected = (EventHandlerCreationException) bf.Deserialize(stream);
            Assert.AreEqual(exception.Message, expected.Message, "Failed to deserialize");
            Assert.AreEqual(exception.InnerException.Message, expected.InnerException.Message,
                "Failed to deserialize");
        }
    }
}
