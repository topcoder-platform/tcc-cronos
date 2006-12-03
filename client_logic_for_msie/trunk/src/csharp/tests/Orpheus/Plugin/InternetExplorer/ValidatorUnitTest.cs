/*
 * Copyright (C) 2006 TopCoder Inc., All Rights Reserved.
 *
 * ValidatorUnitTest.cs
 */

using System;
using NUnit.Framework;

namespace Orpheus.Plugin.InternetExplorer
{
    /// <summary>
    /// Unit Test for Validator class.
    /// </summary>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (C) 2006 TopCoder Inc., All Rights Reserved.</copyright>
    [TestFixture]
    public class ValidatorUnitTest
    {
        /// <summary>
        /// Test ValidateNull(object obj, string name),
        /// when obj is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void TestValidateNull_ObjIsNull()
        {
            Validator.ValidateNull(null, "null");
        }


        /// <summary>
        /// Test ValidateNull(object obj, string name),
        /// when obj is not null, no exception thrown.
        /// </summary>
        [Test]
        public void TestValidateNull_ObjIsNotNull()
        {
            Validator.ValidateNull(new object(), null);
        }

        /// <summary>
        /// Test ValidateNullOrEmptyString(string str, string name),
        /// when str is null, ArgumentNullException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentNullException))]
        public void Test_StrIsNull()
        {
            Validator.ValidateNullOrEmptyString(null, "null");
        }

        /// <summary>
        /// Test ValidateNullOrEmptyString(string str, string name),
        /// when str is emtpy, ArgumentException is expected.
        /// </summary>
        [Test, ExpectedException(typeof(ArgumentException))]
        public void Test_StrIsEmpty()
        {
            Validator.ValidateNullOrEmptyString(" \r \t \n ", "empty");
        }

        /// <summary>
        /// Test ValidateNullOrEmptyString(string str, string name),
        /// when str is not empty nor null, no exception thrown.
        /// </summary>
        [Test]
        public void Test_StrIsNotEmpty()
        {
            Validator.ValidateNullOrEmptyString("h", null);
        }
    }
}
