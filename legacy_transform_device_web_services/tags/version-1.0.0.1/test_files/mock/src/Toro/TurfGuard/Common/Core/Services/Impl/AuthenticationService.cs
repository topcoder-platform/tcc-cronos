/*!
 * ===========================================================================
 * AuthenticationService.cs
 * Copyright (c) 2009 The Toro Company All Rights Reserved
 * 
 * Author:      Todd Gardner
 * Created:     2009-12-11
 * Description: Implementation of basic authentication service.
 *
 * ===========================================================================
 */

using Toro.TurfGuard.Common.Core.Domain.Model;

namespace Toro.TurfGuard.Common.Core.Services.Impl
{
    // Todo: Move to Infrastructure project and make calls to LDAP service.
    public class AuthenticationService : IAuthenticationService
    {
        public bool PasswordMatches(User user, string password)
        {
            return user.Password.Equals(password);
        }
    }
}
