/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using NUnit.Framework;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// <para>Unit tests for <see cref="InvalidDataInputException"/> class.</para>
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture, CoverageExclude]
    public class InvalidDataInputExceptionUnitTest
    {
        /// <summary>
        /// Represents the Message string for test.
        /// </summary>
        private const string MESSAGE = "Message";

        /// <summary>
        /// Represents the Exception instance for test.
        /// </summary>
        private readonly Exception cause = new Exception("innerException");

        /// <summary>
        /// <para>Tests <see cref="InvalidDataInputException()"/>.</para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtor()
        {
            InvalidDataInputException instance =
                new InvalidDataInputException();
            Assert.IsTrue(instance != null, "a new instance should have been created");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException()"/>
        /// to make sure base class is valid.
        /// </para>
        ///
        /// <para>The exception should have the correct base class.</para>
        /// </summary>
        [Test]
        public void TestBaseClass()
        {
            InvalidDataInputException e =
                new InvalidDataInputException();
            UnitTestHelper.AssertType<DeviceServiceException>(e);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string)"/>,
        /// by passing a <c>null</c> reference.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessage_Null()
        {
            new InvalidDataInputException(null);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string)"/>,
        /// by passing an error message.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessage_Valid()
        {
            Exception e = new InvalidDataInputException(MESSAGE);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string, Exception)"/>,
        /// by passing <c>null</c> references.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null3()
        {
            new InvalidDataInputException(null, null);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string, Exception)"/>,
        /// by passing an error message and a <c>null</c> reference.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null1()
        {
            Exception e = new InvalidDataInputException(MESSAGE, null);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string, Exception)"/>,
        /// by passing a <c>null</c> reference and an inner exception.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null2()
        {
            Exception e = new InvalidDataInputException(null, cause);
            Assert.AreEqual(cause, e.InnerException, "e.InnerException should be equal to cause");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(string, Exception)"/>,
        /// by passing an error message and an inner exception.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Valid()
        {
            Exception e = new InvalidDataInputException(MESSAGE, cause);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
            Assert.AreEqual(cause, e.InnerException, "e.InnerException should be equal to cause");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="InvalidDataInputException(SerializationInfo, StreamingContext)"/>.
        /// </para>
        ///
        /// <para>
        /// Deserialized instance should have same Message and InnerException.Message as it
        /// before serialization.
        /// </para>
        /// </summary>
        [Test]
        public void TestCtorInfoContext()
        {
            // stream for serialization
            using (Stream stream = new MemoryStream())
            {
                // serialize the instance
                InvalidDataInputException serial =
                    new InvalidDataInputException(MESSAGE, cause);
                BinaryFormatter formatter = new BinaryFormatter();
                formatter.Serialize(stream, serial);

                // deserialize the instance.
                stream.Seek(0, SeekOrigin.Begin);
                InvalidDataInputException deserial =
                    formatter.Deserialize(stream) as InvalidDataInputException;

                // verify the instance
                Assert.IsFalse(serial == deserial, "Instance not deserialized");
                Assert.AreEqual(serial.Message, deserial.Message, "Message doesn't match");
                Assert.AreEqual(serial.InnerException.Message, deserial.InnerException.Message,
                    "InnerException doesn't match");
            }
        }
    }
}

