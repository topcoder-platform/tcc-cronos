/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Runtime.Serialization;

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// This exception is thrown by IMemberDataAccess implementations, when errors
    /// occur in the member retrieval methods of the implementations. The constructors
    /// of this exception wrap those of the base class.
    /// </summary>
    [Serializable]
    public class MemberDataAccessException : ApplicationException
    {
        /// <summary>
        /// Creates a new, empty exception.
        /// </summary>
        public MemberDataAccessException() : base()
        {
        }

        /// <summary>
        /// Creates a new exception containing the given message.
        /// </summary>
        /// <param name="message">The message the exception will contain.</param>
        public MemberDataAccessException(string message) : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception containing the given message and cause.
        /// </summary>
        /// <param name="message">The message the exception will contain</param>
        /// <param name="cause">The cause of the exception</param>
        public MemberDataAccessException(string message, Exception cause) : base(message, cause)
        {
        }

        /// <summary>
        /// Deserialization constructor.
        /// </summary>
        /// <param name="info">The serialization information used in deserialization</param>
        /// <param name="ctx">The streaming context used in deserialization</param>
        protected MemberDataAccessException(SerializationInfo info, StreamingContext ctx)
            : base(info, ctx)
        {
        }
    }
}
