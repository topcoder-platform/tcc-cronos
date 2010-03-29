/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;

namespace Toro.TurfGuard.Common.Core.Services.Impl
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IAuthenticationService"/> used in the unit tests.
    /// </para>
    /// </summary>
    ///
    /// <threadsafety>
    /// <para>
    /// Thread safety is not important here since this class is only used for unit testing.
    /// </para>
    /// </threadsafety>
    ///
    /// <author>TCSDEVELOPER</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    public class MockAuthenticationService : IAuthenticationService
    {
        /// <summary>
        /// Flag indicating whether exception should be thrown in methods.
        /// </summary>
        public static bool ThrowException;

        /// <summary>
        /// Returns whether password matches for given user.
        /// </summary>
        ///
        /// <param name="user">The user.</param>
        /// <param name="password">The password.</param>
        ///
        /// <returns>Whether password matches for given user.</returns>
        ///
        /// <exception cref="Exception">Thrown for unit testing when flag is set.</exception>
        public bool PasswordMatches(User user, string password)
        {
            if (ThrowException)
            {
                throw new Exception("An error occurred.");
            }
            return user.Password.Equals(password);
        }
    }
}
