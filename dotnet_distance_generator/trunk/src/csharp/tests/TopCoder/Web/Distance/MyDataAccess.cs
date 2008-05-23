/*
 * Copyright (c) 2008, TopCoder, Inc. All rights reserved.
 */

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// Provides member data access from the test_files folder
    /// using the FlatFileMemberDataAccess. This is only used in tests.
    /// </summary>
    /// <author>hotblue</author>
    /// <copyright>Copyright (c) 2008, TopCoder, Inc. All rights reserved.</copyright>
    /// <version>1.0</version>
    [CoverageExclude]
    public class MyDataAccess : FlatFileMemberDataAccess
    {
        /// <summary>
        /// Constructor.
        /// </summary>
        public MyDataAccess()
            : base("../../test_files/")
        {
        }
    }
}
