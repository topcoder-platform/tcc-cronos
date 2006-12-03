/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ClientLogicExtensionException.cs
 */

using System;
using System.Runtime.Serialization;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Base exception for the exceptions thrown by this component.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class ClientLogicExtensionException : ApplicationException
    {
        /// <summary>
        /// Creates a new exception.
        /// </summary>
        public ClientLogicExtensionException()
            : base()
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message.
        /// </summary>
        /// <param name="message">Exception message.</param>
        public ClientLogicExtensionException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message and cause
        /// </summary>
        /// <param name="message">Exception message.</param>
        /// <param name="cause">Exception cause.</param>
        public ClientLogicExtensionException(string message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary>
        /// Seriaization constructor.
        /// </summary>
        /// <param name="info">Serialization info.</param>
        /// <param name="context">Streaming context</param>
        protected ClientLogicExtensionException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}
