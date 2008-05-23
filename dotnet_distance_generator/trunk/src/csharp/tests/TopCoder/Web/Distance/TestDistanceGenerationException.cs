/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using NUnit.Framework;

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// Unit test for <see cref="DistanceGenerationException"/> class.
    /// Verifies that instances of the class could be created by the different constructors.
    /// </summary>
    /// <author>hotblue</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture, CoverageExclude]
    public class TestDistanceGenerationException
    {
        /// <summary>
        /// The standard error message.
        /// </summary>
        private const string standardMessage = "Error in the application.";

        /// <summary>
        /// The message string used for testing.
        /// </summary>
        private const string message = "error message";

        /// <summary>
        /// The Exception instance used for testing.
        /// </summary>
        private Exception cause = new Exception("inner exception");

        /// <summary>
        /// The <see cref="DistanceGenerationException"/> instance used for testing.
        /// </summary>
        private DistanceGenerationException exception = null;

        /// <summary>
        /// Tests that the custom exception has the correct superclass.
        /// DistanceGenerationException should have ApplicationException as its base class.
        /// </summary>
        [Test]
        public void TestClassDefinition()
        {
            Assert.AreEqual(typeof(ApplicationException), typeof(DistanceGenerationException).BaseType,
                "DistanceGenerationException should extend ApplicationException.");
        }

        /// <summary>
        /// Tests the <c>DistanceGenerationException()</c> constructor.
        /// An instance is expected to be created with the standard message and a null inner exception.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            exception = new DistanceGenerationException();
            Assert.IsNotNull(exception, "The DistanceGenerationException() constructor failed.");
            Assert.AreEqual(standardMessage, exception.Message, "Incorrect message.");
            Assert.IsNull(exception.InnerException, "Incorrect InnerException.");
        }

        /// <summary>
        /// Tests the <c>DistanceGenerationException(string)</c> constructor.
        /// An instance is expected to be created with the correct message and a null inner exception.
        /// </summary>
        [Test]
        public void TestCtorString()
        {
            exception = new DistanceGenerationException(message);
            Assert.IsNotNull(exception, "The DistanceGenerationException(string) constructor failed.");
            Assert.AreEqual(message, exception.Message, "Incorrect message.");
            Assert.IsNull(exception.InnerException, "Incorrect InnerException.");
        }

        /// <summary>
        /// Tests the <c>DistanceGenerationException(string, Exception)</c> constructor.
        /// An instance is expected to be created with the correct message and inner exception.
        /// </summary>
        [Test]
        public void TestCtorStringException()
        {
            exception = new DistanceGenerationException(message, cause);
            Assert.IsNotNull(exception, "The DistanceGenerationException(string, Exception) constructor failed.");
            Assert.AreEqual(message, exception.Message, "Incorrect message.");
            Assert.AreEqual(cause, exception.InnerException, "Incorrect inner exception.");
        }

        /// <summary>
        /// Tests the <c>DistanceGenerationException(SerializationInfo, StreamingContext)</c> constructor.
        /// The exception should be serialized then deserialized, and the deserialized exception
        /// should have same message as the original exception.
        /// </summary>
        [Test]
        public void TestCtorSerializationInfoStreamingContext()
        {
            // Stream for serialization.
            using (Stream stream = new MemoryStream())
            {
                // Serialize the instance.
                DistanceGenerationException original = new DistanceGenerationException(message, cause);
                BinaryFormatter formatter = new BinaryFormatter();
                formatter.Serialize(stream, original);

                // Deserialize the instance.
                stream.Seek(0, SeekOrigin.Begin);
                DistanceGenerationException deserialized =
                    formatter.Deserialize(stream) as DistanceGenerationException;

                // Verify the instance.
                Assert.IsNotNull(deserialized, "DistanceGenerationException not deserialized.");
                Assert.AreEqual(original.Message, deserialized.Message, "Message does not match.");
                Assert.AreEqual(original.InnerException.Message, deserialized.InnerException.Message,
                    "InnerException does not match.");
            }
        }
    }
}