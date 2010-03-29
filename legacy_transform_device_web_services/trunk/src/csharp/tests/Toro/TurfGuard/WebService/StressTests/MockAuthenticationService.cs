/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;
using Toro.TurfGuard.Common.Core.Services;

namespace Toro.TurfGuard.WebService.StressTests
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IAuthenticationService"/>.
    /// </para>
    /// </summary>
    ///
    /// <author>assistant</author>
    /// <version>1.0</version>
    /// <copyright>Copyright (c) 2010, TopCoder, Inc. All rights reserved.</copyright>
    [CoverageExclude]
    public class MockAuthenticationService : IAuthenticationService
    {
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
            return true;
        }
    }
}
