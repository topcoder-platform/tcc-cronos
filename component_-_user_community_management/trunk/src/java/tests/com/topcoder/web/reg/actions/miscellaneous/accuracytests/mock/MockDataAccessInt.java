/*
 * Copyright (C) 2011 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.web.reg.actions.miscellaneous.accuracytests.mock;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.mockrunner.mock.jdbc.MockResultSet;
import com.mockrunner.mock.jdbc.MockResultSetMetaData;
import com.topcoder.shared.dataAccess.DataAccessInt;
import com.topcoder.shared.dataAccess.Request;
import com.topcoder.shared.dataAccess.RequestInt;
import com.topcoder.shared.dataAccess.resultSet.ResultSetContainer;

/**
 * Mock DataAccessInt implementation.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public class MockDataAccessInt implements DataAccessInt {
    /**
     * <p>
     * Get the data.
     * </p>
     *
     * @param arg
     *            the request
     * @return data the test data.
     * @throws Exception
     *             to jUnit
     */
    public Map<String, ResultSetContainer> getData(RequestInt arg) throws Exception {
        Request request = (Request) arg;
        Map<String, ResultSetContainer> resultData = new HashMap<String, ResultSetContainer>();
        if ("referral_list".equals(request.getContentHandle())) {
            MockResultSetMetaData meta = new MockResultSetMetaData();
            AddMeta(meta, 1, "coder_id", Types.BIGINT);
            AddMeta(meta, 2, "rating", Types.INTEGER);
            AddMeta(meta, 3, "handle", Types.VARCHAR);
            AddMeta(meta, 4, "member_since", Types.TIMESTAMP);
            meta.setColumnCount(4);

            MockResultSet rs = new MockResultSet("test");
            rs.setResultSetMetaData(meta);

            rs.addRow(new Object[] { 1, 2, "mess", new Timestamp(100000) });
            rs.addRow(new Object[] { 2, 3, "tc", new Timestamp(200000) });

            ResultSetContainer container = new ResultSetContainer(rs);
            resultData.put("referral_list", container);

        } else if ("member_profile".equals(request.getContentHandle())) {
            String cr = request.getProperty("cr");
            MockResultSet rs = new MockResultSet("test");
            MockResultSetMetaData meta = new MockResultSetMetaData();

            if ("0".equals(cr)) {
            } else if ("1".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                rs.addRow(new Object[] { 12 });
                meta.setColumnCount(1);
            } else if ("2".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13 });
                meta.setColumnCount(2);
            } else if ("3".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14 });
                meta.setColumnCount(3);
            } else if ("4".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15 });
                meta.setColumnCount(4);
            } else if ("5".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16 });
                meta.setColumnCount(5);
            } else if ("6".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17 });
                meta.setColumnCount(6);
            } else if ("7".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18 });
                meta.setColumnCount(7);
            } else if ("8".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19 });
                meta.setColumnCount(8);
            } else if ("9".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19, 20 });
                meta.setColumnCount(9);
            } else if ("10".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19, 20, 21 });
                meta.setColumnCount(10);
            } else if ("11".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                AddMeta(meta, 11, "test_scenarios_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22 });
                meta.setColumnCount(11);
            } else if ("12".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                AddMeta(meta, 11, "test_scenarios_rating", Types.INTEGER);
                AddMeta(meta, 12, "ui_prototype_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 });
                meta.setColumnCount(12);
            } else if ("13".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                AddMeta(meta, 11, "test_scenarios_rating", Types.INTEGER);
                AddMeta(meta, 12, "ui_prototype_rating", Types.INTEGER);
                AddMeta(meta, 13, "ria_build_rating", Types.INTEGER);
                rs.addRow(new Object[] { 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24 });
                meta.setColumnCount(13);
            } else if ("14".equals(cr)) {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                AddMeta(meta, 11, "test_scenarios_rating", Types.INTEGER);
                AddMeta(meta, 12, "ui_prototype_rating", Types.INTEGER);
                AddMeta(meta, 13, "ria_build_rating", Types.INTEGER);
                rs.addRow(new Object[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 });
                meta.setColumnCount(13);
            } else {
                AddMeta(meta, 1, "rating", Types.INTEGER);
                AddMeta(meta, 2, "hs_rating", Types.INTEGER);
                AddMeta(meta, 3, "mm_rating", Types.INTEGER);
                AddMeta(meta, 4, "design_rating", Types.INTEGER);
                AddMeta(meta, 5, "development_rating", Types.INTEGER);
                AddMeta(meta, 6, "concept_rating", Types.INTEGER);
                AddMeta(meta, 7, "spec_rating", Types.INTEGER);
                AddMeta(meta, 8, "arch_rating", Types.INTEGER);
                AddMeta(meta, 9, "assembly_rating", Types.INTEGER);
                AddMeta(meta, 10, "test_rating", Types.INTEGER);
                AddMeta(meta, 11, "test_scenarios_rating", Types.INTEGER);
                AddMeta(meta, 12, "ui_prototype_rating", Types.INTEGER);
                AddMeta(meta, 13, "ria_build_rating", Types.INTEGER);
                meta.setColumnCount(13);
                rs.addRow(new Object[] { 3, 8, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0 });
            }

            rs.setResultSetMetaData(meta);
            ResultSetContainer container = new ResultSetContainer(rs);
            resultData.put("Coder_Data", container);
        } else if ("rating_distribution_graph".equals(request.getContentHandle())) {
            MockResultSetMetaData meta = new MockResultSetMetaData();
            meta.setColumnType(1, Types.VARCHAR);
            meta.setColumnType(2, Types.VARCHAR);
            meta.setColumnType(3, Types.VARCHAR);
            meta.setColumnCount(3);
            MockResultSet rs = new MockResultSet("test");
            rs.setResultSetMetaData(meta);

            rs.addRow(new Object[] { "test1", "test2", "test3" });

            ResultSetContainer container = new ResultSetContainer(rs);
            resultData.put("Rating_Distribution_Graph", container);
        } else if ("card_profile_info".equals(request.getContentHandle())) {
            MockResultSetMetaData meta = new MockResultSetMetaData();
            AddMeta(meta, 1, "handle", Types.VARCHAR);
            AddMeta(meta, 2, "image_path", Types.VARCHAR);
            AddMeta(meta, 3, "algorithm_rating", Types.VARCHAR);
            AddMeta(meta, 4, "highest_rating", Types.VARCHAR);
            AddMeta(meta, 5, "rank", Types.VARCHAR);
            AddMeta(meta, 6, "percentile", Types.DOUBLE);
            AddMeta(meta, 7, "member_since", Types.DATE);
            AddMeta(meta, 8, "last_match", Types.DATE);
            AddMeta(meta, 9, "best_div1", Types.VARCHAR);
            AddMeta(meta, 10, "best_div2", Types.VARCHAR);
            AddMeta(meta, 11, "num_competitions", Types.VARCHAR);
            AddMeta(meta, 12, "high_school_rating", Types.VARCHAR);
            AddMeta(meta, 13, "marathon_rating", Types.VARCHAR);
            AddMeta(meta, 14, "design_rating", Types.VARCHAR);
            AddMeta(meta, 15, "highest_rating", Types.VARCHAR);
            AddMeta(meta, 16, "development_rating", Types.VARCHAR);
            AddMeta(meta, 17, "conceptualization_rating", Types.VARCHAR);
            AddMeta(meta, 19, "specification_rating", Types.VARCHAR);
            AddMeta(meta, 20, "architecture_rating", Types.VARCHAR);
            AddMeta(meta, 21, "assembly_rating", Types.VARCHAR);
            AddMeta(meta, 22, "test_suites_rating", Types.VARCHAR);
            AddMeta(meta, 23, "test_scenarios_rating", Types.VARCHAR);
            AddMeta(meta, 24, "ui_prototype_rating", Types.VARCHAR);
            AddMeta(meta, 18, "ria_build_rating", Types.VARCHAR);
            meta.setColumnCount(24);
            MockResultSet rs = new MockResultSet("test");
            rs.setResultSetMetaData(meta);

            rs.addRow(new Object[] { "tc", "tc.jpg", "910", "1000", "12", 1.21d, new Date(10000), new Date(200000),
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16" });

            ResultSetContainer container = new ResultSetContainer(rs);
            resultData.put("card_profile_info", container);
        }
        return resultData;
    }

    /**
     * Adds meta data.
     *
     * @param meta
     *            the MockResultSetMetaData instance.
     * @param column
     *            the column index.
     * @param value
     *            the value.
     * @param types
     *            the type.
     */
    private void AddMeta(MockResultSetMetaData meta, int column, String value, int types) {
        meta.setColumnLabel(column, value);
        meta.setColumnType(column, types);
    }
}