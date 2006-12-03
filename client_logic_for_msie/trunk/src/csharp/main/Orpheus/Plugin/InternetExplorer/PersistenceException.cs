/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * PersistenceException.cs
 */

using System;
using System.Runtime.Serialization;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Exception to signal any problems with the persistence.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [Serializable]
    public class PersistenceException : ClientLogicExtensionException
    {
        /// <summary>
        /// Creates a new exception.
        /// </summary>
        public PersistenceException()
            : base()
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message.
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        public PersistenceException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Creates a new exception with the specified message and cause
        /// </summary>
        ///
        /// <param name="message">Exception message.</param>
        /// <param name="cause">Exception cause.</param>
        public PersistenceException(string message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary>
        /// Seriaization constructor.
        /// </summary>
        ///
        /// <param name="info">Serialization info.</param>
        /// <param name="context">Streaming context</param>
        protected PersistenceException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}
