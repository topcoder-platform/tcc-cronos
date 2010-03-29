/*
 * Copyright (c) 2010, TopCoder, Inc. All rights reserved
 */

using System;
using System.Runtime.Serialization;

namespace Toro.TurfGuard.WebService
{
    /// <summary>
    /// This exception extends <see cref="DeviceServiceException"/>.
    /// </summary>
    ///
    /// <remarks>
    /// It is thrown by the service constructor if anything goes wrong during construction. Such errors would be
    /// missing configuration, missing required parameters, etc.
    /// </remarks>
    ///
    /// <threadsafety>
    /// <para>
    /// This class is not thread safe.
    /// </para>
    /// </threadsafety>
    ///
    /// <author>argolite</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [Serializable]
    public class DeviceServiceConfigurationException : DeviceServiceException
    {
        /// <summary>
        /// Initializes a new instance of the
        /// <see cref="DeviceServiceConfigurationException"/> class.
        /// </summary>
        public DeviceServiceConfigurationException()
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="DeviceServiceConfigurationException"/>
        /// class with a specified error message.
        /// </summary>
        ///
        /// <param name="message">A message that describes the error.</param>
        public DeviceServiceConfigurationException(string message)
            : base(message)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="DeviceServiceConfigurationException"/>
        /// class with a specified error message and a reference to the inner exception
        /// that is the cause of this exception.
        /// </summary>
        ///
        /// <param name="message">The error message that explains the reason for the exception.</param>
        /// <param name="cause">The exception that is the cause of the current exception.</param>
        public DeviceServiceConfigurationException(string message, Exception cause)
            : base(message, cause)
        {
        }

        /// <summary>
        /// Initializes a new instance of the <see cref="DeviceServiceConfigurationException"/>
        /// class with serialized data.
        /// </summary>
        ///
        /// <param name="info">The object that holds the serialized object data.</param>
        /// <param name="context">The contextual information about the source or destination.</param>
        protected DeviceServiceConfigurationException(SerializationInfo info, StreamingContext context)
            : base(info, context)
        {
        }
    }
}

