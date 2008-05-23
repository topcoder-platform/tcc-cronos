using System;
using System.Collections.Generic;
using System.Text;
using NUnit.Framework;
using TopCoder.Web.Distance.Data;
using TopCoder.Web.Distance.DistanceCalculators;


namespace TopCoder.Web.Distance.AccuracyTests
{
    [TestFixture, CoverageExclude]
    public class RatingDistanceCalculatorTests
    {
        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestBothDefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(
                AccuracyTestHelper.GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(AccuracyTestHelper.MEMBER_IDS[0],
                    AccuracyTestHelper.GetAllEnum()),
                    AccuracyTestHelper.GetAlgoritmEnum());

            Assert.AreEqual((float)((4252.0 - 3276.0) / 4252.0), list[0]);
        }


        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestDefinedUndefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(
                AccuracyTestHelper.GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(AccuracyTestHelper.MEMBER_IDS[0],
                AccuracyTestHelper.GetAllEnum()),
                AccuracyTestHelper.GetAlgoritmEnum());

            Assert.AreEqual(1.0f, list[2]);
        }


        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestUndefinedDefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            //System.Console.WriteLine(AccuracyTestHelper
            //       .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]).RatingDistance);
            Assert.AreEqual((float)(1872.0/ 1900.0), list[1]);
        }

        /// <summary>
        /// Tests for both undefined distances
        /// </summary>
        [Test]
        public void TestBothUndefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.AreEqual(0.0f, list[0]);
        }



        /// <summary>
        /// Tests for zero max distance
        /// </summary>
        [Test]
        public void TestZeroMaxDistance()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156859),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156859, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.IsTrue(0.0f<=list[1]);
        }

        /// <summary>
        /// Tests for undef max distance
        /// </summary>
        [Test]
        public void TestUndefMaxDistance()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156860),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156860, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmEnum());

            Assert.IsTrue(0.0f<=list[1]);
        }

        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestAlgDevBothDefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.AreEqual((float)(((4252.0 - 3276.0) / 4252.0 + 1001.0 / 2001.0) / 2.0), list[0]);
        }

        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestAllBothDefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(
                AccuracyTestHelper.GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                    AccuracyTestHelper.MEMBER_IDS[0],
                    AccuracyTestHelper.GetAllEnum()),
                    AccuracyTestHelper.GetAlgoritmDevEnum() | CompetitionTypes.Design);

            Assert.AreEqual((float)(((4252.0 - 3276.0) / 4252.0 + 1001.0 / 2001.0 + 0.0) / 3.0), list[0]);
        }

        /// <summary>
        /// Tests for both defined distances
        /// </summary>
        [Test]
        public void TestAlgDevBothDefined2()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.AreEqual((float)(((3276 - 1872.0) / 4252.0 + 1.0) / 2.0), list[1]);
        }

        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestAlgDevDefinedUndefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[0]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[0], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.AreEqual((float)((1.0 + 1599.0 / 2001.0) / 2.0), list[2]);
        }


        /// <summary>
        /// Tests for defined/undefined distances
        /// </summary>
        [Test]
        public void TestAlgDevUndefinedDefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            //System.Console.WriteLine(AccuracyTestHelper
            //       .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]).RatingDistance);
            Assert.AreEqual((float)((1872.0 / 1900.0 + 1.0)/2.0), list[1]);
        }

        /// <summary>
        /// Tests for both undefined distances
        /// </summary>
        [Test]
        public void TestAlgDevBothUndefined()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(AccuracyTestHelper.MEMBER_IDS[4]),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            AccuracyTestHelper.MEMBER_IDS[4], AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.AreEqual((float)(1001.0/2001.0/2.0), list[0]);
        }

        /// <summary>
        /// Tests for zero max distance
        /// </summary>
        [Test]
        public void TestAlgDevZeroMaxDistance()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156859),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156859, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.IsTrue(0.0f <= list[1]);
        }

        /// <summary>
        /// Tests for undef max distance
        /// </summary>
        [Test]
        public void TestAlgDevUndefMaxDistance()
        {
            IDistanceCalculator calculator = new RatingDistanceCalculator();
            IList<float> list = calculator.CalculateDistance(AccuracyTestHelper
                    .GetTestDataAccess().GetMember(156860),
                    AccuracyTestHelper.GetTestDataAccess().GetRelatedMembers(
                            156860, AccuracyTestHelper.GetAllEnum()), AccuracyTestHelper
                            .GetAlgoritmDevEnum());

            Assert.IsTrue(0.0f <= list[1]);
        }


    }
}
