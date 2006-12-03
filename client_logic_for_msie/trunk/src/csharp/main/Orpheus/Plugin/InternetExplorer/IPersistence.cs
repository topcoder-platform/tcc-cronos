/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * IPersistence.cs
 */

using System;
using System.IO;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// This interface defines the contract that is required for any persistence mechanism
    /// to implement. A persistence mechanism is responsible for saving and retrieving
    /// simple values for a specified key. This component provides an implementation of
    /// this interface that stores per user the values in the registry.
    /// <br />
    ///
    /// <strong>Thread safety: </strong>Implementations of this interface are expected
    /// to be thread safe.
    /// </summary>
    ///
    /// <author>TCSDESIGNER</author>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    public interface IPersistence
    {
        /// <summary>
        /// This is the declaration of the indexer used to store or get a value
        /// based on a key.
        /// </summary>
        ///
        /// <param name="key">The key to get or store the value for.</param>
        /// <returns>the value for the key</returns>
        /// <exception cref="PersistenceException">should be thrown by implementations
        /// if anything goes wrong whlie getting or storing a value.</exception>
        string this[string key]
        {
            get;
            set;
        }
    }
}
