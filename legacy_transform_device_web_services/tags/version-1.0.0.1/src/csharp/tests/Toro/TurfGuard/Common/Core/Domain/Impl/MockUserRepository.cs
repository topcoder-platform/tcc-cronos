/*
 * Copyright (C) 2010 TopCoder Inc., All rights reserved.
 */

using System;
using System.Collections.Generic;
using System.Diagnostics;
using Toro.TurfGuard.Common.Core.Domain.Model;
using Toro.TurfGuard.WebService;

namespace Toro.TurfGuard.Common.Core.Domain.Impl
{
    /// <summary>
    /// <para>
    /// This class is a mock implementation of <see cref="IUserRepository"/> used in the unit tests.
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
    public class MockUserRepository : IUserRepository
    {
        /// <summary>
        /// Flag indicating whether to throw exception for unit tests.
        /// </summary>
        public static bool ThrowException;

        /// <summary>
        /// The name that the calling method should be when throwing exception.
        /// </summary>
        public static string CallingMethodForThrowException;

        /// <summary>
        /// The mock repository used for unit tests.
        /// </summary>
        public static IDictionary<string, User> Repository = new Dictionary<string, User>();

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="id">The id.</param>
        ///
        /// <returns>The user.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public User GetById(int id)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The entity.</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Save(User entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <returns>User array.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public User[] GetAll()
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="entity">The entity</param>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public void Delete(User entity)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// This method isn't implemented.
        /// </summary>
        ///
        /// <param name="username">The user name.</param>
        /// <param name="password">The password.</param>
        ///
        /// <returns>Whether user is valid.</returns>
        ///
        /// <exception cref="NotImplementedException">Always thrown since method isn't implemented.</exception>
        public bool ValidateUser(string username, string password)
        {
            throw new NotImplementedException();
        }

        /// <summary>
        /// Gets user for given <paramref name="username"/>.
        /// </summary>
        ///
        /// <param name="username">The user name.</param>
        ///
        /// <returns>The user for given <paramref name="username"/>.</returns>
        ///
        /// <exception cref="Exception">A test exception is thrown for unit testing if flag is set.</exception>
        public User GetUserByName(string username)
        {
            if (ThrowException && (new StackTrace()).GetFrame(1).GetMethod().Name == CallingMethodForThrowException)
            {
                throw new Exception("method failed.");
            }
            return Repository[username];
        }
    }
}
