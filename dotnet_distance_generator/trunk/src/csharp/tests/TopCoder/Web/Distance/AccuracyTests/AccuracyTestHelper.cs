using System;
using System.Collections.Generic;
using System.Text;
using System.IO;
using TopCoder.Web.Distance;
using TopCoder.Web.Distance.Data;

namespace TopCoder.Web.Distance.AccuracyTests
{
/**
 * Helper class for accuracy tests
 *
 * @author aksonov
 *
 */
    public static class AccuracyTestHelper
    {
        public static string ACCURACY_TESTS_PATH = "../../test_files/accuracy/";
        public static string[] MEMBER_COUNTRIES = new string[] { "Poland",
			"Spain", "India", "Ukraine", "Poland" };
        public static string[] MEMBER_HANDLES = new string[] { "tomek",
			"Tomy", "Sputnik", "Vovik", "slex" };
        public static int[] MEMBER_ALGO_RATINGS = new int[] { 3276, 4252,
			1872, 0, 1900 };
        public static int[] MEMBER_DEV_RATINGS = new int[] { 0, 1001, 2001,
			1599, 1200 };
        public static int[] MEMBER_DESIGN_RATINGS = new int[] { 0, 0, 1002,
			1502, 1702 };
        public static int[] MEMBER_GEO = new int[] { 0, 256, 257, -1, -1 };
        public static int[] MEMBER_IDS = new int[] { 144400, 8398526,
			7576932, 10190739, 8382018 };

        private static IMemberDataAccess access = new FlatFileMemberDataAccess(
                ACCURACY_TESTS_PATH);

        public static IMemberDataAccess GetTestDataAccess()
        {
            return access;
        }

        public static CompetitionTypes GetAlgoritmEnum()
        {
            return CompetitionTypes.Algorithm;
        }

        public static CompetitionTypes GetAlgoritmDevEnum()
        {
            return CompetitionTypes.Algorithm | CompetitionTypes.Development;
        }

        public static CompetitionTypes GetAllEnum()
        {
            return CompetitionTypes.Algorithm | CompetitionTypes.Development | CompetitionTypes.Design;
        }

        public static DistanceTypes GetAllDistanceEnum()
        {
            return DistanceTypes.Country | DistanceTypes.Overlap | DistanceTypes.Rating;
        }

        public static DistanceTypes GetGeographicalType()
        {
            return DistanceTypes.Country;
        }

        public static DistanceTypes GetGeographicalAndRatingType()
        {
            return DistanceTypes.Country | DistanceTypes.Rating;
        }

        public static string GetContents(string id)
        {
            return File.ReadAllText(ACCURACY_TESTS_PATH + "results/" + id + ".xml");
        }
    }
}
