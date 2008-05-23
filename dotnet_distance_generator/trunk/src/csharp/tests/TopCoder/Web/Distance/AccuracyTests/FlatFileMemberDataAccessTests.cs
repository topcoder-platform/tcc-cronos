using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;


namespace TopCoder.Web.Distance.AccuracyTests
{
    [TestFixture, CoverageExclude]
    public class FlatFileMemberDataAccessTests
    {
        private IMemberDataAccess dataAccess = null;

        [SetUp]
        protected void SetUp()
        {
            dataAccess = AccuracyTestHelper.GetTestDataAccess();
        }

        [Test]
        public void TestGetMember()
        {
            for (int i = 0; i < 1; i++)
            {
                Member member = dataAccess.GetMember(AccuracyTestHelper.MEMBER_IDS[i]);
                Assert.IsNotNull(member, "Member should not be null");
                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_COUNTRIES[i], member.Country, "Countries should be equal: " + member);

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_HANDLES[i], member.Handle, "Handle should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_ALGO_RATINGS[i], member
                                .GetRating(CompetitionTypes.Algorithm), "Algorithm rating should be equal");
                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_DEV_RATINGS[i], member
                                .GetRating(CompetitionTypes.Development), "Dev rating should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_DESIGN_RATINGS[i], member
                                .GetRating(CompetitionTypes.Design), "Design ratings should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_GEO[i], member
                                .GeographicalDistance, "Geo should be equal");

            }
            IList<Member> members = dataAccess.GetRelatedMembers(AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum());
            for (int i = 1; i < 5; i++)
            {
                Member member = members[i - 1];
                Assert.IsNotNull(member, "Member should not be null");
                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_COUNTRIES[i], member.Country, "Countries should be equal: " + member);

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_HANDLES[i], member.Handle, "Handle should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_ALGO_RATINGS[i], member
                                .GetRating(CompetitionTypes.Algorithm), "Algorithm rating should be equal" + member.Handle);
                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_DEV_RATINGS[i], member
                                .GetRating(CompetitionTypes.Development), "Dev rating should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_DESIGN_RATINGS[i], member
                                .GetRating(CompetitionTypes.Design), "Design ratings should be equal");

                Assert.AreEqual(
                        AccuracyTestHelper.MEMBER_GEO[i], member
                                .GeographicalDistance, "Geo should be equal");

            }
        }
    }
}
