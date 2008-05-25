package com.topcoder.web.tc.distance.accuracytests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.EnumSet;

import com.topcoder.web.tc.distance.CompetitionType;
import com.topcoder.web.tc.distance.DistanceType;
import com.topcoder.web.tc.distance.data.FlatFileMemberDataAccess;
import com.topcoder.web.tc.distance.data.MemberDataAccess;

/**
 * Helper class for accuracy tests
 *
 * @author aksonov
 *
 */
public class TestHelper {
    public static String ACCURACY_TESTS_PATH = "test_files/accuracy/";
    public static final String[] MEMBER_COUNTRIES = new String[] { "Poland",
            "Spain", "India", "Ukraine", "Poland" };
    public static final String[] MEMBER_HANDLES = new String[] { "tomek",
            "Tomy", "Sputnik", "Vovik", "slex" };
    public static final int[] MEMBER_ALGO_RATINGS = new int[] { 3276, 4252,
            1872, 0, 1900 };
    public static final int[] MEMBER_DEV_RATINGS = new int[] { 0, 1001, 2001,
            1599, 1200 };
    public static final int[] MEMBER_DESIGN_RATINGS = new int[] { 0, 0, 1002,
            1502, 1702 };
    public static final int[] MEMBER_GEO = new int[] { 0, 256, 257, -1, -1 };
    public static final int[] MEMBER_IDS = new int[] { 144400, 8398526,
            7576932, 10190739, 8382018 };

    private static MemberDataAccess access = new FlatFileMemberDataAccess(
            ACCURACY_TESTS_PATH);


    /**
     * Private constructor
     */
    private TestHelper() {
    }

    public static MemberDataAccess getTestDataAccess() {
        return access;
    }

    public static EnumSet<CompetitionType> getAlgoritmEnum() {
        return EnumSet.of(CompetitionType.ALGORITHM);
    }

    public static EnumSet<CompetitionType> getAlgoritmDevEnum() {
        return EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT);
    }

    public static EnumSet<CompetitionType> getAllEnum() {
        return EnumSet.of(CompetitionType.ALGORITHM,
                CompetitionType.DEVELOPMENT, CompetitionType.DESIGN);
    }

    public static EnumSet<DistanceType> getGeographicalType(){
        return EnumSet.of(DistanceType.GEOGRAPHICAL);
    }

    public static EnumSet<DistanceType> getGeographicalAndRatingType(){
        return EnumSet.of(DistanceType.GEOGRAPHICAL, DistanceType.RATING);
    }

    public static String getContents(String id) {
        // ...checks on aFile are elided
        StringBuffer contents = new StringBuffer();

        try {
            // use buffering, reading one line at a time
            // FileReader always assumes default encoding is OK!
            BufferedReader input = new BufferedReader(new FileReader(
                    ACCURACY_TESTS_PATH + "results/" + id + ".xml"));
            try {
                String line = null; // not declared within while loop
                /*
                 * readLine is a bit quirky : it returns the content of a line
                 * MINUS the newline. it returns null only for the END of the
                 * stream. it returns an empty String if two newlines appear in
                 * a row.
                 */
                while ((line = input.readLine()) != null) {
                    contents.append(line);
                    contents.append(System.getProperty("line.separator"));
                }
            } finally {
                input.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return contents.toString();
    }
    public static void writeContents(String id, String xml) {

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(
                    ACCURACY_TESTS_PATH + "results/" + id + ".xml"));

            output.write(xml);
            output.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
