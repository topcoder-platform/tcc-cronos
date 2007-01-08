/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 */

using Microsoft.Win32;
using NUnit.Framework;
using Orpheus.Plugin.InternetExplorer.Persistence;

namespace Orpheus.Plugin.InternetExplorer.AccuracyTests.Persistence
{
    /// <summary>
    /// Accuracy tests for <c>RegistrytPersistence</c> class.
    ///
    /// <author>tuenm</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    /// </summary>
    [TestFixture]
    public class RegistryPersistenceAccTests
    {
        /// <summary>
        /// The registry key used to save the save.
        /// </summary>
        private const string APPLICATION_KEY = "Software\\Orpheus";

        /// <summary>
        /// The RegistrytPersistence to test.
        /// </summary>
        private RegistryPersistence persistence;

        /// <summary>
        /// Set up for each test case.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            CleanUpRegistry();
            persistence = new RegistryPersistence();
        }

        /// <summary>
        /// Tear down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            CleanUpRegistry();
        }

        /// <summary>
        /// Accuracy test of the inheritance.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(persistence is IPersistence,
                          "RegistrytPersistence should implement IPersistence.");
        }

        /// <summary>
        /// Accuracy test of the indexer getter.
        /// </summary>
        [Test]
        public void TestIndexerGet()
        {
            // create a key
            persistence["key"] = "value";

            Assert.AreEqual("value", persistence["key"],
                            "Not the expected value.");
        }

        /// <summary>
        /// Accuracy test of the indexer getter.
        /// </summary>
        [Test]
        public void TestIndexerSet()
        {
            // create a key
            persistence["key1"] = "value1";
            persistence["key2"] = "value2";

            Assert.AreEqual("value1", persistence["key1"], "Not the expected value.");
            Assert.AreEqual("value2", persistence["key2"], "Not the expected value.");
        }

        /// <summary>
        /// Clean up the Orpheus subkey in the registry.
        /// </summary>
        private void CleanUpRegistry()
        {
            try
            {
                Registry.CurrentUser.DeleteSubKeyTree(APPLICATION_KEY);
            }
            catch
            {
                // ignore
            }
        }
    }
}