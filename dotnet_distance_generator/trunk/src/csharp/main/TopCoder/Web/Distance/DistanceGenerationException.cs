/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Runtime.Serialization;

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// This exception is used to represent errors that might occur during
    /// the generation of distance. For example, it can be thrown if a failure
    /// to retrieve data occurs. This exception is thrown by the methods of
    /// implementations of the <see cref="IDistanceGenerator"/> interface.
    /// </summary>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [Serializable]
    public class DistanceGenerationException : ApplicationException
    {
        /// <summary>
        /// Creates a new, empty exception.
        /// </summary>
        public DistanceGenerationException()
        {
        }

        /// <summary>
        /// Creates a new exception containing the given message.
        /// </summary>
        /// <param name="message">The message the exception will contain.</param>
        public DistanceGenerationException(string message) : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception containing the given message and cause.
        /// </summary>
        /// <param name="message">The message the exception will contain</param>
        /// <param name="cause">The cause of the exception</param>
        public DistanceGenerationException(string message, Exception cause) : base(message, cause)
        {
        }

        /// <summary>
        /// Deserialization constructor.
        /// </summary>
        /// <param name="info">The serialization information used in deserialization</param>
        /// <param name="context">The streaming context used in deserialization</param>
        protected DistanceGenerationException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}
