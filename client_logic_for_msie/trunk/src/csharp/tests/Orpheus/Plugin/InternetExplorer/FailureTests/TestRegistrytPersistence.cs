using System;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.Persistence;
namespace Orpheus.Plugin.InternetExplorer.FailureTests
{
    /// <summary>
    /// Tests for RegistrytPersistence class.
    /// </summary>
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2006, TopCoder, Inc. All rights reserved.</copyright>
    [TestFixture]
    public class TestRegistrytPersistence
    {
        /// <summary>
        /// The RegistrytPersistence instance used to test.
        /// </summary>
        private RegistryPersistence reg = new RegistryPersistence();

        /// <summary>
        /// Test indexer this[string key] Getter with null key, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIndexerGetNullKey()
        {
            string index = reg[null];
        }

        /// <summary>
        /// Test indexer this[string key] Getter with null key, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestIndexerGetEmptyKey()
        {
            string index = reg[" "];
        }

        /// <summary>
        /// Test indexer this[string key] setter with null key, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIndexerSetNullKey()
        {
            reg[null] = "Software\\Orpheus";
        }

        /// <summary>
        /// Test indexer this[string key] setter with null key, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestIndexerSetEmptyKey()
        {
            reg[" "] = "Software\\Orpheus"; ;
        }
    }
}
