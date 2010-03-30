/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using NUnit.Framework;
using TopCoder.Util.ExceptionManager;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// <para>Unit tests for <see cref="DeviceServiceException"/> class.</para>
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture, CoverageExclude]
    public class DeviceServiceExceptionUnitTest
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
        /// <para>Tests <see cref="DeviceServiceException()"/>.</para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtor()
        {
            DeviceServiceException instance =
                new DeviceServiceException();
            Assert.IsTrue(instance != null, "a new instance should have been created");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException()"/>
        /// to make sure base class is valid.
        /// </para>
        ///
        /// <para>The exception should have the correct base class.</para>
        /// </summary>
        [Test]
        public void TestBaseClass()
        {
            DeviceServiceException e =
                new DeviceServiceException();
            UnitTestHelper.AssertType<BaseException>(e);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string)"/>,
        /// by passing a <c>null</c> reference.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessage_Null()
        {
            new DeviceServiceException(null);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string)"/>,
        /// by passing an error message.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessage_Valid()
        {
            Exception e = new DeviceServiceException(MESSAGE);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string, Exception)"/>,
        /// by passing <c>null</c> references.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null3()
        {
            new DeviceServiceException(null, null);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string, Exception)"/>,
        /// by passing an error message and a <c>null</c> reference.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null1()
        {
            Exception e = new DeviceServiceException(MESSAGE, null);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string, Exception)"/>,
        /// by passing a <c>null</c> reference and an inner exception.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Null2()
        {
            Exception e = new DeviceServiceException(null, cause);
            Assert.AreEqual(cause, e.InnerException, "e.InnerException should be equal to cause");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(string, Exception)"/>,
        /// by passing an error message and an inner exception.
        /// </para>
        ///
        /// <para>No exception should be thrown, as this is correct usage.</para>
        /// </summary>
        [Test]
        public void TestCtorMessageInner_Valid()
        {
            Exception e = new DeviceServiceException(MESSAGE, cause);
            Assert.AreEqual(MESSAGE, e.Message, "e.Message should be equal to " + MESSAGE);
            Assert.AreEqual(cause, e.InnerException, "e.InnerException should be equal to cause");
        }

        /// <summary>
        /// <para>
        /// Tests <see cref="DeviceServiceException(SerializationInfo, StreamingContext)"/>.
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
                DeviceServiceException serial =
                    new DeviceServiceException(MESSAGE, cause);
                BinaryFormatter formatter = new BinaryFormatter();
                formatter.Serialize(stream, serial);

                // deserialize the instance.
                stream.Seek(0, SeekOrigin.Begin);
                DeviceServiceException deserial =
                    formatter.Deserialize(stream) as DeviceServiceException;

                // verify the instance
                Assert.IsFalse(serial == deserial, "Instance not deserialized");
                Assert.AreEqual(serial.Message, deserial.Message, "Message doesn't match");
                Assert.AreEqual(serial.InnerException.Message, deserial.InnerException.Message,
                    "InnerException doesn't match");
            }
        }
    }
}

