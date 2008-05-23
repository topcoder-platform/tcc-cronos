using System;
using System.Collections.Generic;
using System.Text;

namespace TopCoder.Web.Distance.Data
{
    /// <summary>
    /// This class is an immutable data transport object that holds all
    /// data necessary to calculate the required distances between two members.
    ///
    /// Members unrated in a particular competition type are considered
    /// to have rating zero.
    /// </summary>
    public class Member
    {
        /// <summary>
        /// The country of the member.
        /// </summary>
        private String country = null;

        /// <summary>
        /// The image for the member.
        /// </summary>
        private String image = null;

        /// <summary>
        /// The geographical distance in miles between this member and the related member.
        /// If this member was not retrieved as a related member, the distance will be zero.
        ///
        /// The units for this are irrelevant and will be uniform across all members.
        /// </summary>
        private int geoDistance = 0;

        /// <summary>
        /// The number of matches shared between this member and the related member.
        /// If this member was not retrieved as a related member, the overlap will be zero.
        /// </summary>
        private int matchOverlap = 0;

        /// <summary>
        /// The member's handle.
        /// </summary>
        private String handle = null;

        /// <summary>
        /// The member's ratings. All ratings should be included.
        /// </summary>
        private Dictionary<CompetitionTypes, int> ratings = null;

        /// <summary>
        /// The member's coder_id (From the database).
        /// </summary>
        private long id = 0;

        /// <summary>
        /// Member's maximum rating (of all current ratings).
        /// </summary>
        private int maxRating = Int32.MinValue;

        /// <summary>
        /// Gets the member's handle.
        /// </summary>
        public String Handle
        {
            get
            {
                return handle;
            }
        }

        /// <summary>
        /// Gets the member's highest rating regardless of competition type.
        /// </summary>
        public int MaxRating
        {
            get
            {
                return maxRating;
            }
        }

        /// <summary>
        /// Gets the member's coder_id, which uniquely identifies them.
        /// </summary>
        public long Id
        {
            get
            {
                return id;
            }
        }

        /// <summary>
        /// Gets the geographical distance between this member and the related
        /// member. If this member is the member distances are being generated for,
        /// the distance will be zero.
        /// </summary>
        public int GeographicalDistance
        {
            get
            {
                return geoDistance;
            }
        }

        /// <summary>
        /// Gets the number of matches shared between this member and the related member.
        /// If this member is the member distances are being generated for, the overlap
        /// will be zero.
        /// </summary>
        public int MatchOverlap
        {
            get
            {
                return matchOverlap;
            }
        }

        /// <summary>
        /// Gets the country for the member. May be empty string if no image available.
        /// </summary>
        public String Country
        {
            get
            {
                return country;
            }
        }

        /// <summary>
        /// Gets the image for the member. May be empty string if no image available.
        /// </summary>
        public String Image
        {
            get
            {
                return image;
            }
        }

        /// <summary>
        /// Gets the member's rating for a specific competition type.
        /// </summary>
        /// <param name="type">The type of competition desired.</param>
        /// <returns>The rating for the member in that type. Zero if unrated.</returns>
        /// <exception cref="ArgumentOutOfRangeException">If type is not a single CompetitionTypes flag.</exception>
        public int GetRating(CompetitionTypes type)
        {
            if (!Enum.IsDefined(typeof(CompetitionTypes), type))
            {
                throw new ArgumentOutOfRangeException("type", type, "Argument not of integral flag value.");
            }

            return ratings[type];
        }

        /// <summary>
        /// Creates a member instance. All parameters are required.
        /// </summary>
        /// <param name="id">The coder_id for the member.</param>
        /// <param name="handle">The handle for the member.</param>
        /// <param name="ratings">The ratings for the member.</param>
        /// <param name="country">The country for the member (empty if not applicable).</param>
        /// <param name="image">The image for the member (empty if not applicable).</param>
        internal Member(long id, String handle, Dictionary<CompetitionTypes, int> ratings,
            String country, String image)
        {
            this.id = id;
            this.handle = handle;
            this.country = country;
            this.image = image;

            this.ratings = new Dictionary<CompetitionTypes, int>();

            foreach (CompetitionTypes t in Enum.GetValues(typeof(CompetitionTypes)))
            {
                if (ratings.ContainsKey(t))
                {
                    this.ratings[t] = ratings[t];
                    if (ratings[t] > maxRating)
                    {
                        maxRating = ratings[t];
                    }
                }
                else
                {
                    this.ratings[t] = -1;
                }
            }
        }

        /// <summary>
        /// Creates a member instance. All parameters are required.
        /// </summary>
        /// <param name="id">The coder_id for the member.</param>
        /// <param name="handle">The handle for the member.</param>
        /// <param name="ratings">The ratings for the member.</param>
        /// <param name="country">The country for the member (empty if not applicable).</param>
        /// <param name="image">The image for the member (empty if not applicable).</param>
        /// <param name="geoDistance">The geographical distance between this member and a related member.</param>
        /// <param name="matchOverlap">The number of matches shared between this member and a related member.</param>
        internal Member(long id, String handle, Dictionary<CompetitionTypes, int> ratings,
            String country, String image, int geoDistance, int matchOverlap)
            : this(id, handle, ratings, country, image)
        {
            this.geoDistance = geoDistance;
            this.matchOverlap = matchOverlap;
        }

    }
}