/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * RegistrytPersistenceUnitTest.cs
 */
using System;
using NUnit.Framework;
using Microsoft.Win32;

namespace Orpheus.Plugin.InternetExplorer.Persistence
{
    /// <summary>
    /// Unit test for <c>RegistrytPersistence</c> class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class RegistrytPersistenceUnitTest
    {
        /// <summary>
        /// The registry key used to save the save.
        /// </summary>
        private const string APPLICATION_KEY = "Software\\Orpheus";

        /// <summary>
        /// The key  used in test.
        /// </summary>
        private const string Key = "test";

        /// <summary>
        /// The value used int test.
        /// </summary>
        private const string Value = "This is a value";

        /// <summary>
        /// The instance of <c>RegistrytPersistence</c> to peform test on.
        /// </summary>
        private RegistryPersistence tester;

        /// <summary>
        /// Set up for each test.
        /// </summary>
        [SetUp]
        public void SetUp()
        {
            TearDown();
            tester = new RegistryPersistence();
        }

        /// <summary>
        /// Tear down for each test.
        /// </summary>
        [TearDown]
        public void TearDown()
        {
            try
            {
                Registry.CurrentUser.DeleteSubKeyTree(APPLICATION_KEY);
            }
            catch(Exception)
            {
                // ignore
            }
        }

        /// <summary>
        /// Test inheritance,
        /// RegistrytPersistence should implement IPersistence.
        /// </summary>
        [Test]
        public void TestInheritance()
        {
            Assert.IsTrue(tester is IPersistence,
                "RegistrytPersistence should implement IPersistence.");
        }

        /// <summary>
        /// Test constructor RegistrytPersistence(),
        /// empty constructor, should be created always.
        /// </summary>
        [Test]
        public void TestCtor()
        {
            Assert.IsNotNull(tester, "empty constructor, should be created always.");
        }

        /// <summary>
        /// Test indexer this[string key] Getter,
        /// when key is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIndexer_Getter_KeyIsNull()
        {
            string v = tester[null];
        }

        /// <summary>
        /// Test indexer this[string key] Getter,
        /// when key is empty, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestIndexer_Getter_KeyIsEmpty()
        {
            string v = tester["  \r \t \n "];
        }

        /// <summary>
        /// Test indexer this[string key] Getter,
        /// when key is not existed, empty string is returned.
        /// </summary>
        [Test]
        public void TestIndexer_Getter_KeyNotExisted()
        {
            Console.WriteLine(tester["NotExisted"].Length);
            Assert.IsEmpty(tester["NotExisted"],
                "when key is not existed, empty string is returned.");
        }

        /// <summary>
        /// Test indexer this[string key] Getter,
        /// when the key existed, the value with the key should be returned.
        /// </summary>
        [Test, ExpectedException(typeof(PersistenceException))]
        public void TestIndexer_Getter_Failed()
        {
            // create a key with int type.
            RegistryKey regKey = Registry.CurrentUser.CreateSubKey(APPLICATION_KEY);
            regKey.SetValue(Key, 1);
            regKey.Close();

            // it is not string type, failed
            string v = tester[Key];
        }

        /// <summary>
        /// Test indexer this[string key] Getter,
        /// when the key existed, the value with the key should be returned.
        /// </summary>
        [Test]
        public void TestIndexer_Getter()
        {
            // first create the key
            tester[Key] = Value;

            // then get and check the value
            Assert.AreEqual(Value, tester[Key],
                "when the key existed, the value with the key should be returned.");
        }

        /// <summary>
        /// Test indexer this[string key Setter],
        /// when key is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIndexer_Setter_KeyIsNull()
        {
            tester[null] = Value;
        }

        /// <summary>
        /// Test indexer this[string key Setter],
        /// when key is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestIndexer_Setter_KeyIsEmpty()
        {
            tester["\r \t \n "] = Value;
        }

        /// <summary>
        /// Test indexer this[string key Setter],
        /// when value is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestIndexer_Setter_ValueIsNull()
        {
            tester[Key] = null;
        }

        /// <summary>
        /// Test indexer this[string key Setter],
        /// when value is empty string, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void TestIndexer_Setter_ValueIsEmpty()
        {
            tester[Key] = "\r \t \n ";
        }

        /// <summary>
        /// Test indexer this[string key Setter],
        /// Set the value in registry with the key.
        /// </summary>
        [Test]
        public void TestIndexer_Setter()
        {
            // first create the key
            tester[Key] = Value;

            // then get and check the value
            Assert.AreEqual(Value, tester[Key],
                "when the key existed, the value with the key should be returned.");

            // Test again
            tester[Value] = Key;

            // then get and check the value
            Assert.AreEqual(Key, tester[Value],
                "when the key existed, the value with the key should be returned.");
        }
    }
}
