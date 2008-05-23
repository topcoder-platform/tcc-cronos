/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

using System.Collections.Generic;

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// The data access interface for retrieving member data.
    /// </summary>
    /// <threadsafety>Implementations of this interface may or may not be thread-safe</threadsafety>
    /// <author>TCSDESIGNER</author>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    public interface IMemberDataAccess
    {
        /// <summary>
        /// Retrieves a member by their coder_id. This method is intended
        /// to provide the details for the member from which distances are
        /// calculated.
        /// </summary>
        /// <param name="id">
        /// The identifier for the member desired.
        /// </param>
        /// <returns>
        /// A Member instance with all data populated.
        /// </returns>
        /// <exception cref="MemberDataAccessException">
        /// If errors occur during retrieval.
        /// </exception>
        Member GetMember(long id);

        /// <summary>
        /// Retrieves the members related to a specified member. The number of
        /// members returned will vary based on the input to the method, as
        /// well as the particular data retrieval implementation.
        /// </summary>
        /// <param name="id">
        /// The member of interest.
        /// </param>
        /// <param name="competitionType">
        /// The competition types to filter the data.
        /// </param>
        /// <returns>
        /// An unordered list of members related in some way
        /// to the requested member. The set selected will vary by the
        /// implementation of the interface.
        /// </returns>
        /// <exception cref="MemberDataAccessException">
        /// If errors occur during retrieval.
        /// </exception>
        IList<Member> GetRelatedMembers(long id, CompetitionTypes competitionType);

    }
}
