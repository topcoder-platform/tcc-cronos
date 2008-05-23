    using System;
    using System.Collections.Generic;
    using System.Text;
    using NUnit.Framework;
    using TopCoder.Web.Distance.Data;
    using TopCoder.Web.Distance.DistanceCalculators;


namespace TopCoder.Web.Distance.AccuracyTests
{
    [TestFixture, CoverageExclude]
    public class OverlapDistanceCalculatorTests
    {
        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestBothDefined()
        {
            IDistanceCalculator calculator = new OverlapDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.AreEqual((float)(1.0 - (33.0 - 19.0) / 49.0), list[0]);
        }

        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestBothDefined2()
        {
            IDistanceCalculator calculator = new OverlapDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.AreEqual((float)(1.0 - (49.0 - 19.0) / 49.0), list[1]);
        }

        /// <summary>
        /// Tests for zero max distance
        /// </summary>
        [Test]
        public void TestZeroMaxDistance()
        {
            IDistanceCalculator calculator = new OverlapDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156859),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156859, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.AreEqual(1.0f, list[1]);
        }
    }
}
