/*
 * Copyright (C) 2007-2009 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.management.project.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.topcoder.db.connectionfactory.DBConnectionFactory;
import com.topcoder.db.connectionfactory.DBConnectionFactoryImpl;
import com.topcoder.management.project.BillingProjectConfigType;
import com.topcoder.management.project.BillingProjectConfiguration;
import com.topcoder.management.project.ConfigurationException;
import com.topcoder.management.project.ContestSale;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectPersistence;
import com.topcoder.management.project.ProjectPropertyType;
import com.topcoder.management.project.ProjectSpec;
import com.topcoder.management.project.ProjectStatus;
import com.topcoder.management.project.ProjectType;
import com.topcoder.management.project.SaleStatus;
import com.topcoder.management.project.SaleType;
import com.topcoder.management.project.SimplePipelineData;
import com.topcoder.management.project.SimpleProjectContestData;
import com.topcoder.management.project.SimpleProjectPermissionData;
import com.topcoder.management.project.SoftwareCapacityData;
import com.topcoder.management.project.persistence.Helper.DataType;
import com.topcoder.management.project.persistence.logging.LogMessage;
import com.topcoder.util.config.ConfigManager;
import com.topcoder.util.config.ConfigManagerException;
import com.topcoder.util.idgenerator.IDGenerationException;
import com.topcoder.util.idgenerator.IDGenerator;
import com.topcoder.util.idgenerator.IDGeneratorFactory;
import com.topcoder.util.log.Level;
import com.topcoder.util.log.Log;
import com.topcoder.util.sql.databaseabstraction.CustomResultSet;
import com.topcoder.util.sql.databaseabstraction.InvalidCursorStateException;

/**
 * <p>
 * This class contains operations to create/update/retrieve project instances
 * from the Informix database. It also provides methods to retrieve database
 * look up table values. It implements the ProjectPersistence interface to
 * provide a plug-in persistence for Informix database. It is used by the
 * ProjectManagerImpl class. The constructor takes a namespace parameter to
 * initialize database connection.
 * </p>
 * <p>
 * Note that in this class, delete operation is not supported. To delete a
 * project, its status is set to 'Deleted'. Create and update operations here
 * work on the project and its related items as well. It means creating/updating
 * a project would involve creating/updating its properties.
 * </p>
 * <p>
 * This abstract class does not manage the connection itself. It contains three
 * abstract methods which should be implemented by concrete subclass to manage
 * the connection:
 * <ul>
 * <li><code>openConnection()</code> is used to acquire and open the
 * connection.</li>
 * <li><code>closeConnection()</code> is used to dispose the connection if
 * the queries are executed successfully.</li>
 * <li><code>closeConnectionOnError()</code> is used to handle the error if
 * the SQL operation fails.</li>
 * </ul>
 * The transaction handling logic should be implemented in subclasses while the
 * <code>Statement</code>s and <code>ResultSet</code>s are handled in this
 * abstract class.
 * </p>
 * 
 * <p>
 * Module Contest Service Software Contest Sales Assembly change: new methods added to support creating/updating/query contest
 * sale for software contest.
 * </p>
 *
 * <p>
 * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
 *      - added persist for project_spec.
 * </p>
 * 
 * Version 1.1.1 (Spec Reviews Finishing Touch v1.0) Change Notes:
 *  - Changed the way now spec status is queried.
 * <p>
 * Version 1.1.2 (Cockpit Pipeline Release Assembly 1 v1.0) Change Notes:
 *  - Introduced method to retrieve SimplePipelineData for given date range.
 * </p>
 * <p>
 * Version 1.1.3 (Cockpit Release Assembly 6) Change Notes:
 *  - Defined project property for confidentiality type.
 *  - Defined values for confidentiality types.
 *  - Depending on the confidentiality type now the project role terms are stored in different ways.
 * </p>
 * <p>
 * Version 1.2 (Cockpit Pipeline Release Assembly 2 - Capacity) changelog:
 *     - added service that retrieves a list of capacity data (date, number of scheduled contests) starting from
 *       tomorrow for a given contest type
 * </p>
 * <p>
 * Version 1.2.1 Cockpit Release Assembly 10
 *     - Change three getSimpleProjectContestData methods, to include the submission_end_date
 * </p>
 * <p>
 * Changes in v1.2.2 - Cockpit Release Assembly 11
 * Add method getDesignComponents to get design components.
 * </p>
 * <p>
 * Version 1.2.3 (Cockpit Contest Eligibility) changelog:
 *     - Create terms for private contest
 * </p>
 * <p>
 * Version 1.2.5 (Cockpit Spec Review - Stage 2 v1.0) changelog:
 *    - update the getSimpleProjectContestData methods for spec review backend update.
 * </p>

 * <p>
 * Version 1.2.4 (Cockpit Pipeline Manager Widget Release Assembly V1.0) Change Notes:
 * - streamline the software contest's status
 * - Detail visits https://www.topcoder.com/bugs/browse/TCCC-1504
 * </p>
 * <p>
 * Thread Safety: This class is thread safe because it is immutable.
 * </p>
 * 
 * [BUGR-2038]: the new logic for returned contest 'status' will be
 * if status is 'active' in db, and there is a row in contest_sale, then returned/shown status will be 'Scheduled',
 * if status is 'active' in db, and there is no row in contest_sale, then returned/shwon status will be 'Draft",
 * otherwise, show 'status' from db. 
 * 
 *  [ Cockpit Release Assembly - Contest Repost and New Version v1.0]
 *  update the query used in the method getDevelopmentContestId, to remove the constraint on the project status.
 *
 *  <p>
 *  Version 1.3 - Direct Search Assembly
 *  - add payment information into getSimpleProjectContestData functions.
 *  </p>
 *
 * <p>
 * Thread Safety: This class is thread safe because it is immutable.
 * </p>
 *
 * @author tuenm, urtks, bendlund, fuyun, snow01, pulky, murphydog, waits, BeBetter
 * @version 1.3
 */
public abstract class AbstractInformixProjectPersistence implements ProjectPersistence {

    /**
     * private constant representing the active status id for a project
     *
     * @since 1.2
     */
    private static final String ACTIVE_PROJECT_STATUS_ID = "1";

    private static final com.topcoder.util.log.Log log = com.topcoder.util.log.LogManager
            .getLog(AbstractInformixProjectPersistence.class.getName());

	/**
     * <p>
     * Represents the default value for Project Id sequence name. It is used to
     * create id generator for project. This value will be overridden by
     * 'ProjectIdSequenceName' configuration parameter if it exist.
     * </p>
     */
    public static final String PROJECT_ID_SEQUENCE_NAME = "project_id_seq";

	/**
     * <p>
     * Represents the default value for Contest Sale Id sequence name. It is used to
     * create id generator for contest_sale. This value will be overridden by
     * 'ContestSaleIdSequenceName' configuration parameter if it exist.
     * </p>
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public static final String CONTEST_SALE_ID_SEQUENCE_NAME = "contest_sale_id_seq";

    /**
     * <p>
     * Represents the default value for project audit id sequence name. It is
     * used to create id generator for project audit. This value will be
     * overridden by 'ProjectAuditIdSequenceName' configuration parameter if it
     * exist.
     * </p>
     */
    public static final String PROJECT_AUDIT_ID_SEQUENCE_NAME = "project_audit_id_seq";

    /**
     * <p>
     * Represents the default value for project spec id sequence name. It is
     * used to create id generator for project spec. This value will be
     * overridden by 'ProjectSpecIdSequenceName' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    public static final String PROJECT_SPEC_ID_SEQUENCE_NAME = "PROJECT_SPEC_ID_SEQ";


	/**
     * <p>
     * Represents the default value for public confidentiality submitter_terms_id.  This value will be
     * overridden by 'public_submitter_terms_id' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long PUBLIC_SUBMITTER_TERMS_ID = 20703;
	
	/**
     * <p>
     * Represents the default value for public confidentiality reviewer_terms_id.  This value will be
     * overridden by 'public_reviewer_terms_id' configuration parameter if it
     * exist.
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long PUBLIC_REVIEWER_TERMS_ID = 20704;
    
    /**
     * <p>
     * Represents the default value for standard cca confidentiality terms_id. 
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long STANDARD_CCA_TERMS_ID = 20713;
    

    /**
     * <p>
     * Represents the default value for standard cca confidentiality terms_id. 
     * </p>
     * 
     * @since 1.1.2
     */
    public static final long MANAGER_TERMS_ID = 20794;


	/**
     * <p>
     * Represents the default value for  submitter_role_id.  This value will be
     * overridden by 'submitter_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int SUBMITTER_ROLE_ID = 1;

	/**
     * <p>
     * Represents the default value for  accuracy_reviewer_role_id.  This value will be
     * overridden by 'accuracy_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int ACCURACY_REVIEWER_ROLE_ID = 5;

	/**
     * <p>
     * Represents the default value for  failure_reviewer_role_id.  This value will be
     * overridden by 'failure_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int FAILURE_REVIEWER_ROLE_ID = 6;

	/**
     * <p>
     * Represents the default value for  stress_reviewer_role_id.  This value will be
     * overridden by 'stress_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int STRESS_REVIEWER_ROLE_ID = 7;;

	/**
     * <p>
     * Represents the default value for  reviewer_role_id.  This value will be
     * overridden by 'reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int REVIEWER_ROLE_ID = 4;

	/**
     * <p>
     * Represents the default value for  primary_screener_role_id.  This value will be
     * overridden by 'primary_screener_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int PRIMARY_SCREENER_ROLE_ID = 2;

	/**
     * <p>
     * Represents the default value for  aggregator_role_id.  This value will be
     * overridden by 'aggregator_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int AGGREGATOR_ROLE_ID = 8;

	/**
     * <p>
     * Represents the default value for  final_reviewer_role_id.  This value will be
     * overridden by 'final_reviewer_role_id' configuration parameter if it
     * exist.
     * </p>
     * 
     */
    public static final int FINAL_REVIEWER_ROLE_ID = 9;
    /**
     * <p>
     * Represents the default value for screenner_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int SCREENER_ROLE_ID = 3;
    /**
     * <p>
     * Represents the default value for final_reviewer_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int APPROVER_ROLE_ID = 10;
    /**
     * <p>
     * Represents the default value for designer_role_id. 
     * </p>
     * @since 1.2.1
     */
    public static final int DESIGNER_ROLE_ID = 11;
    /**
     * <p>
     * Represents the default value for observer_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int OBSERVER_ROLE_ID = 12;
    /**
     * <p>
     * Represents the default value for manager_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int MANAGER_ROLE_ID = 13;
    /**
     * <p>
     * Represents the default value for deactivated_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int DEACTIVATED_ROLE_ID = 86;
    /**
     * <p>
     * Represents the default value for team_captain_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int TEAM_CAPTAIN_ROLE_ID = 1001;
    /**
     * <p>
     * Represents the default value for free_agent_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int FREE_AGENT_ROLE_ID = 1002;
    /**
     * <p>
     * Represents the default value for payment_manager_role_id. 
     * @since 1.2.1
     * </p>
     */
    public static final int PAYMENT_MANAGER_ROLE_ID = 1003;

     /**
     * private constant representing the project_read permission
     *
     */
    private static final long PROJECT_READ_PERMISSION_ID = 1;

    /**
     * private constant representing the project_read permission
     *
     */
    private static final long PROJECT_WRITE_PERMISSION_ID = 2;

     /**
     * private constant representing the contest_read permission
     *
     */
    private static final long CONTEST_READ_PERMISSION_ID = 4;

    /**
     * private constant representing the contest_read permission
     *
     */
    private static final long CONTEST_WRITE_PERMISSION_ID = 5;
    /**
     * <p>
     * Represents the all roles' id
     * @since 1.2.1
     * </p>
     */
    public static final int[] ALL_ROLES_ID = new int[] {SUBMITTER_ROLE_ID, PRIMARY_SCREENER_ROLE_ID,
        SCREENER_ROLE_ID, REVIEWER_ROLE_ID, ACCURACY_REVIEWER_ROLE_ID, FAILURE_REVIEWER_ROLE_ID,
        STRESS_REVIEWER_ROLE_ID, AGGREGATOR_ROLE_ID, FINAL_REVIEWER_ROLE_ID,
        APPROVER_ROLE_ID, DESIGNER_ROLE_ID, OBSERVER_ROLE_ID, MANAGER_ROLE_ID, DEACTIVATED_ROLE_ID,
        TEAM_CAPTAIN_ROLE_ID, FREE_AGENT_ROLE_ID, PAYMENT_MANAGER_ROLE_ID};
    

	/**
	 * <p>
	 * DEV ID
	 * </p>
	 */
	public static final long PROJECT_CATEGORY_DEVELOPMENT = 2;

    /**
     * Represents the name of connection name parameter in configuration.
     */
    private static final String CONNECTION_NAME_PARAMETER = "ConnectionName";

    /**
     * Represents the name of connection factory namespace parameter in
     * configuration.
     */
    private static final String CONNECTION_FACTORY_NAMESPACE_PARAMETER = "ConnectionFactoryNS";

    /**
     * Represents the name of project id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_ID_SEQUENCE_NAME_PARAMETER = "ProjectIdSequenceName";

    /**
     * Represents the name of contest sale id sequence name parameter in
     * configuration.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String CONTEST_SALE_ID_SEQUENCE_NAME_PARAMETER = "ContestSaleIdSequenceName";

    /**
     * Represents the name of project audit id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER = "ProjectAuditIdSequenceName";

    /**
     * Represents the name of project audit id sequence name parameter in
     * configuration.
     */
    private static final String PROJECT_SPEC_ID_SEQUENCE_NAME_PARAMETER = "ProjectSpecIdSequenceName";
    
    /**
     * Represents the project info property for Confidentiality Type.
     * 
     * @sine 1.1.2
     */
    private static final String PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY = "Confidentiality Type";

    /**
     * Represents the project info property for Billing Project.
     */
    private static final String PROJECT_INFO_BILLING_PROJECT_PROPERTY = "Billing Project";
    
    /**
     * Represents the public confidentiality type value.
     * 
     * @sine 1.1.2
     */
    private static final String CONFIDENTIALITY_TYPE_PUBLIC = "public";
    
	/**
     * Represents the name of public_submitter_terms_id parameter in
     * configuration.
     * 
     * Updated for Version 1.1.2
     */
    private static final String PUBLIC_SUBMITTER_TERMS_ID_PARAMETER = "public_submitter_terms_id";
    
    /**
     * Represents the name of public_reviewer_terms_id parameter in
     * configuration.
     * 
     * Updated for Version 1.1.2
     */
    private static final String PUBLIC_REVIEWER_TERMS_ID_PARAMETER = "public_reviewer_terms_id";

	/**
     * Represents the name of standard_cca_reviewer_terms_id parameter in
     * configuration.
     * 
     * @since 1.1.2
     */
    private static final String STANDARD_CCA_REVIEWER_TERMS_ID_PARAMETER = "standard_cca_reviewer_terms_id";
    
    /**
     * Represents the name of standard_cca_submitter_terms_id parameter in
     * configuration.
     * 
     * @since 1.1.2
     */
    private static final String STANDARD_CCA_SUBMITTER_TERMS_ID_PARAMETER = "standard_cca_submitter_terms_id";

	/**
     * Represents the name of submitter_role_id parameter in
     * configuration.
     */
    private static final String SUBMITTER_ROLE_ID_PARAMETER = "submitter_role_id";

	/**
     * Represents the name of accuracy_reviewer_role_id parameter in
     * configuration.
     */
    private static final String ACCURACY_REVIEWER_ROLE_ID_PARAMETER = "accuracy_reviewer_role_id";

	/**
     * Represents the name of failure_reviewer_role_id parameter in
     * configuration.
     */
    private static final String FAILURE_REVIEWER_ROLE_ID_PARAMETER = "failure_reviewer_role_id";

	/**
     * Represents the name of stress_reviewer_role_id parameter in
     * configuration.
     */
    private static final String STRESS_REVIEWER_ROLE_ID_PARAMETER = "stress_reviewer_role_id";

	/**
     * Represents the name of reviewer_role_id parameter in
     * configuration.
     */
    private static final String REVIEWER_ROLE_ID_PARAMETER = "reviewer_role_id";

	/**
     * Represents the name of primary_screener_role_id  parameter in
     * configuration.
     */
    private static final String PRIMARY_SCREENER_ROLE_ID_PARAMETER = "primary_screener_role_id";

	/**
     * Represents the name of aggregator_role_id  parameter in
     * configuration.
     */
    private static final String AGGREGATOR_ROLE_ID_PARAMETER = "aggregator_role_id";

	/**
     * Represents the name of final_reviewer_role_id  parameter in
     * configuration.
     */
    private static final String FINAL_REVIEWER_ROLE_ID_PARAMETER = "final_reviewer_role_id";


    /**
     * Represents the sql statement to query all project types.
     */
    private static final String QUERY_ALL_PROJECT_TYPES_SQL = "SELECT "
            + "project_type_id, name, description FROM project_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the SQL statement to query all billing project configuration types.
     */
    private static final String QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_SQL = "SELECT "
            + "client_billing_config_type_id, name, description FROM client_billing_config_type_lu";

    /**
     * Represents the column types for the result set which is returned by executing the SQL
     * statement to query all billing project configuration types.
     */
    private static final DataType[] QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the SQL statement to query the billing project configurations for a billing
     * project.
     */
    private static final String QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL = "SELECT "
            + "client_billing_config_type_id, value FROM client_billing_config where client_billing_id=?";

    /**
     * Represents the column types for the result set which is returned by executing the SQL
     * statement to query billing project configurations for the specified billing project id.
     */
    private static final DataType[] QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query the sale status via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_SALE_STATUS_BY_ID_SQL = "SELECT "
            + "sale_status_id, sale_status_desc FROM sale_status_lu WHERE sale_status_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the sale status via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_SALE_STATUS_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query the sale type via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_SALE_TYPE_BY_ID_SQL = "SELECT "
            + "sale_type_id, sale_type_name FROM sale_type_lu WHERE sale_type_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the sale type via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_SALE_TYPE_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query all project categories.
     */
    private static final String QUERY_ALL_PROJECT_CATEGORIES_SQL = "SELECT "
            + "category.project_category_id, category.name, category.description, "
            + "type.project_type_id, type.name, type.description "
            + "FROM project_category_lu AS category "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id = type.project_type_id";

    /**
     * Represents the sql statement to query all project contest data.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1 & 1.1.3]
     *      - fetch tc project and s/w project permission.
     *      - whenever join with s/w project and user_permission_grant, add is_studio=0 constraint.
     * </p>
     * 
     * Updated for Spec Reviews Finishing Touch v1.0 (Version 1.1.1)
     *  - Changed the way now spec status is queried.
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     */

		private static final String QUERY_ALL_SIMPLE_PROJECT_CONTEST = " select p.project_id as contest_id, "
			+ " (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph "
			+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
			+ " (select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
			+ " (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id) as start_date, "
			+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
			+ "  pcl.name as contest_type, psl.name as status, "
			+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
			+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
			// fixed forum post
            		+ " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
            		+ "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
            		+ "             and pi.project_id =  p.project_id) as num_for, "
			+ " tc_direct_project_id as project_id, tcd.name, tcd.description, tcd.user_id, "
			+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
			+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
			+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "
			+ " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0 "
            + " ),0)) as cperm, "

            + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=tcd.project_id  "
            + " ),0)) as pperm, "

            /* Added in cockpit R 10 */
            + " (select scheduled_end_time from project_phase ph "
            + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
            /* R 10 end*/

            /* updated by Cockpit Spec Review - Stage 2  - start */
            /* sepc review project id*/
            + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
            /* sepc review user */
            + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
            + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
            /* reviewing status check */
            + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
            + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
            + " and linkp.dest_project_Id = p.project_id) as sprs,"
             /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.review_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
            /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "
            // contest fee/ price sum
           + " (select sum(cast(nvl(value, '0') as DECIMAL(10,2))) from project_info where project_id = p.project_id and project_info_type_id in (30,31,33,35,36,37,38,39)) as contest_fee "


            + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
            + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "

                                                 // dont show spec review project
            + "		and p.project_status_id != 3 and p.project_category_id != 27 ";



    /**
     * Represents the sql statement to query all project contest data for a tc project id.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1 & 1.1.3]
     *      - fetch tc project and s/w project permission.
     *      - whenever join with s/w project and user_permission_grant, add is_studio=0 constraint.
     * </p>
     * 
     * Updated for Spec Reviews Finishing Touch v1.0
     *  - Changed the way now spec status is queried.
     *
     *  Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     */
	private static final String QUERY_ALL_SIMPLE_PROJECT_CONTEST_BY_PID = " select p.project_id as contest_id, "
	+		" (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph " 
	+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
	+ "(select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
	+ "(select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id) as start_date, "
	+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
	+ "  pcl.name as contest_type, psl.name as status, "
	+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
	+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
	// fixed forum post
    + " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
    + "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
    + "             and pi.project_id =  p.project_id) as num_for, "
	+ " tc_direct_project_id as project_id , tcd.name, tcd.description, tcd.user_id, "
	+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
	+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
	+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "
	
	
    + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
    + " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0"
    + " ),0)) as cperm, "

    + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
    + " from user_permission_grant as upg  where resource_id=tcd.project_id"
    + " ),0)) as pperm, "
    /* Added in cockpit R 10 */
    + " (select scheduled_end_time from project_phase ph "
    + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
    /* R 10 end*/

    /* updated by Cockpit Spec Review - Stage 2  - start */
    /* sepc review project id*/
    + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
    /* sepc review user */
    + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
    + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
    /* reviewing status check */
    + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
    + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
    + " and linkp.dest_project_Id = p.project_id) as sprs,"
   /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.review_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
         /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "

           // contest fee/ price sum
           + " (select sum(cast(nvl(value, '0') as DECIMAL(10,2))) from project_info where project_id = p.project_id  and project_info_type_id in (30,31,33,35,36,37,38,39)) as contest_fee "

    + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
    + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "
                                      // dont show spec review project
    + " and p.project_status_id != 3 and p.project_category_id != 27 "
    + " and p.tc_direct_project_id= ";

    /**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project categories.
     */
    private static final DataType[] QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

	/**
     * Represents the column types for the result set which is returned by executing the sql statement to query all
     * project categories.
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *      - Added column type mapping for 2 new columns: tc project permission and contest permission.
     * </p>
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * <p>
     * Updated for Direct Search Assembly
     *      - Added column type for price sum/ contest fee
     * </p>
     */
    private static final DataType[] QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.DATE_TYPE,Helper.DATE_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
               Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE,Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.DOUBLE_TYPE};

    /**
     * Represents the sql statement to query all design components data for a user id.
     * @since 1.2.1
     */
    private static final String QUERY_ALL_DESIGN_COMPONENTS = "select p.project_id as project_id, " + 
        "(select (c.category_name ||' ' || pi.value || ' ' || cv.version_text) " + 
        "from comp_catalog cc, categories c, project_info pi, comp_versions cv " + 
        "where  c.category_id = cc.root_category_id and " + 
        "cc.component_id = (select pi.value from project_info pi " + 
        "where pi.project_info_type_id = 2 and pi.project_id = p.project_id )" + 
        "and project_id = p.project_id and " + 
        "project_info_type_id =6 " + 
        "and cv.comp_vers_id = (select pi.value from project_info pi " + 
        "where pi.project_info_type_id = 1 and pi.project_id = p.project_id) " + 
        ") as component_name, " + 
        "(select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  " + 
        "from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0 and user_id = ? " + 
        " ),0)) as cperm, " + 
        "(select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  " + 
        "from user_permission_grant as upg  where resource_id=tcd.project_id and user_id = ? " + 
        "),0)) as pperm " + 
        "from project p, project_category_lu pcl," + 
        " project_status_lu psl, tc_direct_project tcd , project_info pi " + 
        " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id " + 
        "and p.tc_direct_project_id = tcd.project_id " + 
        "and p.project_status_id != 3 and p.project_category_id = 1 " +
        "and p.project_id = pi.project_id and pi.project_info_type_id = 1 " +
        "and not exists (" + 
        "select 1 from project_info q1 , project q " + 
        " where q1.project_info_type_id = 1 and q1.project_id = q.project_id and q1.project_id <> p.project_id and q1.value = pi.value " +
        " and (q.project_status_id = 1 or q.project_status_id = 7)  and q.project_category_id == 2) ";
        


    /**
     * Represents  the column types for the result set which is returned by executing the sql statement
     * to query all design components data for a user id.
     * @since 1.2.1
     */
    private static final DataType[] QUERY_ALL_DESIGN_COMPONENTS_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
	Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};
    /**
     * Represents the sql statement to query all project statuses.
     */
    private static final String QUERY_ALL_PROJECT_STATUSES_SQL = "SELECT "
            + "project_status_id, name, description FROM project_status_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project statuses.
     */
    private static final DataType[] QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query all project property types.
     */
    private static final String QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL = "SELECT "
            + "project_info_type_id, name, description FROM project_info_type_lu";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project property types.
     */
    private static final DataType[] QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query projects.
     */
    private static final String QUERY_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name,"
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "WHERE project.project_id IN ";

    private static final String QUERY_PROJECTS_BY_CREATE_DATE_SQL = "SELECT "

			+ "  project.project_id, project_status_lu.project_status_id, project_status_lu.name, "
            + "                  project_category_lu.project_category_id, project_category_lu.name, project_type_lu.project_type_id, project_type_lu.name, "
            + "                  project.create_user, project.create_date, project.modify_user, project.modify_date, project_category_lu.description, "
			+ "                  pi1.value as project_name, pi2.value as project_version "
            + "              FROM project, "
            + "                   project_category_lu, "
            + "                   project_status_lu, "
            + "                   project_type_lu, "
			+ "                   project_info pi1, "
            + "                   project_info pi2 "
            + "             WHERE project.project_category_id = project_category_lu.project_category_id "
            + "               AND project.project_status_id = project_status_lu.project_status_id "
            + "               AND project_category_lu.project_type_id = project_type_lu.project_type_id "
			+ "               AND pi1.project_id = project.project_id AND pi1.project_info_type_id = 6 "
            + "               AND pi2.project_id = project.project_id AND pi2.project_info_type_id = 7 " 				 
            + "               AND (project.project_status_id != 3 and project.project_status_id != 2)"
			+ "			      AND date(project.create_date) > date(current) - ";

    
   


    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query projects.
     */
    private static final DataType[] QUERY_PROJECTS_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE};
    
    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query projects.
     */
    private static final DataType[] QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, 
		Helper.STRING_TYPE, Helper.STRING_TYPE};
    
    /**
     * Represents the sql statement to query tc direct projects.
     */
    private static final String QUERY_TC_DIRECT_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name,"
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "WHERE project.tc_direct_project_id is not null ";

   
    /**
     * Represents the sql statement to query tc direct projects.
     */
    private static final String QUERY_USER_TC_DIRECT_PROJECTS_SQL = "SELECT "
            + "project.project_id, status.project_status_id, status.name, "
            + "category.project_category_id, category.name, type.project_type_id, type.name,"
            + "project.create_user, project.create_date, project.modify_user, project.modify_date, category.description, "
            + "project.tc_direct_project_id "
            + "FROM project JOIN project_status_lu AS status "
            + "ON project.project_status_id=status.project_status_id "
            + "JOIN project_category_lu AS category "
            + "ON project.project_category_id=category.project_category_id "
            + "JOIN project_type_lu AS type "
            + "ON category.project_type_id=type.project_type_id "
            + "WHERE project.tc_direct_project_id is not null AND project.create_user = ?";  
    
    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_PROJECT_PROPERTIES_SQL = "SELECT "
            + "info.project_id, info_type.name, info.value "
            + "FROM project_info AS info "
            + "JOIN project_info_type_lu AS info_type "
            + "ON info.project_info_type_id=info_type.project_info_type_id "
            + "WHERE info.project_id IN ";

    /**
     * Represents the sql statement to query project properties.
     */
    private static final String QUERY_ONE_PROJECT_PROPERTIES_SQL = "SELECT "
            + "info.project_id, info_type.name, info.value "
            + "FROM project_info AS info "
            + "JOIN project_info_type_lu AS info_type "
            + "ON info.project_info_type_id=info_type.project_info_type_id "
            + "WHERE info.project_id = ?";
    
    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project properties.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTIES_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE };

    /**
     * Represents the sql statement to query project property ids.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_SQL = "SELECT "
            + "project_info_type_id FROM project_info WHERE project_id=?";

    /**
     * Represents the sql statement to query project property ids and values.
     */
    private static final String QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL = "SELECT "
            + "project_info_type_id, value FROM project_info WHERE project_id=?";


    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project property ids.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE};

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query project property ids and values.
     */
    private static final DataType[] QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES = new DataType[] {
    	Helper.LONG_TYPE, Helper.STRING_TYPE
    };

    /**
     * Represents the sql statement to create project.
     */
    private static final String CREATE_PROJECT_SQL = "INSERT INTO project "
            + "(project_id, project_status_id, project_category_id, "
            + "create_user, create_date, modify_user, modify_date, tc_direct_project_id) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT, ?)";

    /**
     * Represents the sql statement to query the contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_CONTEST_SALE_BY_ID_SQL = "SELECT "
            + "contest_sale_id, contest_id, sale_status_id, price, paypal_order_id, create_date, sale_reference_id, sale_type_id "
            + "FROM contest_sale WHERE contest_sale_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_CONTEST_SALE_BY_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE };

    

    /**
     * Represents the sql statement to query the contest sale via contest id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String QUERY_CONTEST_SALE_BY_CONTEST_ID_SQL = "SELECT "
            + "contest_sale_id, contest_id, sale_status_id, price, paypal_order_id, create_date, sale_reference_id, sale_type_id "
            + "FROM contest_sale WHERE contest_id=?";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query the contest sale via contest id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final DataType[] QUERY_CONTEST_SALE_BY_CONTEST_ID_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.DOUBLE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE };

    /**
     * Represents the sql statement to create contest sale.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String CREATE_CONTEST_SALE_SQL = "INSERT INTO contest_sale "
            + "(contest_sale_id, contest_id, sale_status_id, "
            + "price, paypal_order_id, sale_reference_id, sale_type_id) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    /**
     * Represents the sql statement to delete contest sale via id.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String DELETE_CONTEST_SALE_BY_ID_SQL = "DELETE FROM contest_sale "
            + "WHERE contest_sale_id=?";

    /**
     * Represents the sql statement to create project property.
     */
    private static final String CREATE_PROJECT_PROPERTY_SQL = "INSERT INTO project_info "
            + "(project_id, project_info_type_id, value, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to create project audit.
     */
    private static final String CREATE_PROJECT_AUDIT_SQL = "INSERT INTO project_audit "
            + "(project_audit_id, project_id, update_reason, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, ?, ?, CURRENT, ?, CURRENT)";

    /**
     * Represents the sql statement to update project.
     */
    private static final String UPDATE_PROJECT_SQL = "UPDATE project "
            + "SET project_status_id=?, project_category_id=?, modify_user=?, modify_date= ?, tc_direct_project_id=? "
            + "WHERE project_id=?";

    /**
     * Represents the sql statement to update contest sale.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private static final String UPDATE_CONTEST_SALE_SQL = "UPDATE contest_sale "
            + "SET contest_id=?, sale_status_id=?, price=?, paypal_order_id=?, sale_reference_id=?, sale_type_id=? "
            + "WHERE contest_sale_id=?";

    /**
     * Represents the sql statement to update project property.
     */
    private static final String UPDATE_PROJECT_PROPERTY_SQL = "UPDATE project_info "
            + "SET value=?, modify_user=?, modify_date=CURRENT "
            + "WHERE project_id=? AND project_info_type_id=?";

    /**
     * Represents the sql statement to delete project properties.
     */
    private static final String DELETE_PROJECT_PROPERTIES_SQL = "DELETE FROM project_info "
            + "WHERE project_id=? AND project_info_type_id IN ";


	    /**
     * Represents the sql statement to query project_spec for specified project_id.
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String QUERY_PROJECT_SPEC_SQL = "SELECT " 
        + " ps1.project_spec_id, " 
        + " ps1.version, "
        + " ps1.detailed_requirements, " 
        + " ps1.submission_deliverables, " 
        + " ps1.environment_setup_instruction,  "
        + " ps1.final_submission_guidelines, "
        + " ps1.create_user, " 
        + " ps1.create_date, "
        + " ps1.modify_user, "
        + " ps1.modify_date "
        + " FROM project_spec as ps1 " 
        + " WHERE ps1.project_id = ? "
        + " AND ps1.version = (SELECT max(ps2.version) FROM project_spec as ps2 WHERE ps2.project_id = ?)";

    /**
     * Represents the column types for the result set which is returned by
     * executing the sql statement to query all project categories.
     */
    private static final DataType[] QUERY_SIMPLE_PROJECT_PERMISSION_DATA_COLUMN_TYPES = new DataType[] {
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE};

    private static final DataType[] QUERY_PROJECT_SPEC_COLUMN_TYPES = new DataType[] {
        Helper.LONG_TYPE, Helper.LONG_TYPE, 
        Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.STRING_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE,
        Helper.STRING_TYPE, Helper.DATE_TYPE};
    
    /**
     * Represents the sql statement to create project spec.
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String CREATE_PROJECT_SPEC_SQL = "INSERT INTO project_spec "
            + "(project_spec_id, project_id, version, " 
            + "detailed_requirements, submission_deliverables, environment_setup_instruction, final_submission_guidelines, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, 1, " 
            + "?, ?, ?, ?, "
            + "?, CURRENT, ?, CURRENT)";
    
    /**
     * Represents the sql statement to update project spec.
     * 
     * Since the project_spec table maintains all the versions of the records, we just need to insert the entry with the next version.
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private static final String UPDATE_PROJECT_SPEC_SQL = "INSERT INTO project_spec "
            + "(project_spec_id, project_id, version, " 
            + "detailed_requirements, submission_deliverables, environment_setup_instruction, final_submission_guidelines, "
            + "create_user, create_date, modify_user, modify_date) "
            + "VALUES (?, ?, (select NVL(max(ps.version), 0)  + 1 from project_spec as ps where ps.project_id = ?), " 
            + "?, ?, ?, ?, "
            + "?, CURRENT, ?, CURRENT)";
    
    /**
     * Represents the column types mapping for retrieving simple pipeline data.
     * @since 1.1.1
     */
    private static final DataType[] QUERY_SIMPLE_PIPELINE_DATA_COLUMN_TYPES = new DataType[] { Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE,
            Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.DATE_TYPE, Helper.STRING_TYPE,
            Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE, Helper.DOUBLE_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE,
            Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.LONG_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE,
            Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE, Helper.STRING_TYPE};

    /**
     * Represents the sql statement to find the corresponding develop contest for the design contest.
     * 
     * @since 1.2.1
     */
    private static final String FIND_CORRESPONDING_DEVELOPMENT_CONTEST = "select q1.project_id from project p, project_info q1 "
        + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
        + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
        + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
        + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
        + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
        + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id and p.project_category_id = 2 "
        + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";

    private static final String FIND_CORRESPONDING_DESIGN_CONTEST = "select q1.project_id from project p, project_info q1 "
        + "join project_info p1 on p1.value = q1.value and p1.project_info_type_id = 1 and p1.project_id = ? "
        + "join project_info q2 on q2.project_id = q1.project_id and q2.project_info_type_id = 2 "
        + "join project_info p2 on p2.value = q2.value and p2.project_info_type_id = 2 and p2.project_id = ? "
        + "join project_info q3 on q3.project_id = q1.project_id and q3.project_info_type_id = 3 "
        + "join project_info p3 on p3.value = q3.value and p3.project_info_type_id = 3 and p3.project_id = ? "
        + "where q1.project_info_type_id = 1 and p.project_id = q1.project_id and p.project_category_id = 1 "
        + " and p.project_status_id in (1,2,7) and q1.project_id <> ?";


     /**
     * query to check if it is dev only
     */
     private static final String QUERY_IS_DEV_ONLY_SQL = "SELECT 'devonly' "

      + " from project_info q1 , project q, project_info pi "
      + "   where q1.project_info_type_id = 1 and q1.project_id = q.project_id and q1.project_id <> pi.project_id and q1.value = pi.value "
      + "   and pi.project_info_type_id = 1 "
      + "   and (q.project_status_id = 1 or q.project_status_id = 7) and q.project_category_id = 1 "
      + "     and pi.project_id = ? ";
   
    /**
     * query types for QUERY_IS_DEV_ONLY_SQL
     */
     private static final DataType[]QUERY_IS_DEV_ONLY_SQL_COLUMN_TYPES = new DataType[] { Helper.STRING_TYPE};


    /**
     *  get all project ids by tc direct project
     *
     */
    private static final String QUERY_PROJECT_ID_BY_TC_DIRECT = "select project_id from project where tc_direct_project_id = ? ";


    /**
     *  get tc direct project by project
     *
     */
    private static final String QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID = "select tc_direct_project_id from project where project_id = ? ";


    /**
     *  get all project ids by tc direct project data type
     *
     */
    private static final DataType[] QUERY_PROJECT_ID_BY_TC_DIRECT_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

    /**
     *  get tc direct project by project data type
     *
     */
    private static final DataType[] QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

    /**
     *  get all project ids by tc direct project
     *
     */
    private static final String QUERY_FORUM_ID = "select value from project_info where project_info_type_id = 4 and project_id = ? ";


    /**
     *  get all project ids by tc direct project data type
     *
     */
    private static final DataType[] QUERY_FORUM_ID_COLUMN_TYPES = new DataType[] {Helper.LONG_TYPE}; 

            
    /**
        * Represents the sql statement to insert the term of use.
        * @since 1.2.1
        */
    private static final String INSERT_PRIVATE_CONTEST_TERMS = "insert into "
        + "project_role_terms_of_use_xref (project_id, resource_role_id, terms_of_use_id) values (?, ?, ?)";
            
    /**
        * Represents the sql statement to select all term of use's id for a proejct.
        * @since 1.2.1
        */
    private static final String SELCT_PRIVATE_CONTEST_TERMS = 
        "select terms_of_use_id, resource_role_id  from client_terms_mapping where client_project_id = ?";

    /**
	 * 'final review' phase name
	 */
	private static final String PROJECT_PHASE_FINAL_REVIEW = "Final Review";

    /**
     * <p>
     * Represents the audit creation type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_CREATE_TYPE = 1;

    /**
     * <p>
     * Represents the audit deletion type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_DELETE_TYPE = 2;
    
    /**
     * <p>
     * Represents the audit update type.
     * </p>
     *
     * @since 1.1.2
     */
    private static final int AUDIT_UPDATE_TYPE = 3;
    
    /**
     * Represents the SQL statement to audit project info.
     * 
     * @since 1.1.2
     */
    private static final String PROJECT_INFO_AUDIT_INSERT_SQL = "INSERT INTO project_info_audit "
    	+ "(project_id, project_info_type_id, value, audit_action_type_id, action_date, action_user_id) "
    	+ "VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * <p>
     * The factory instance used to create connection to the database. It is
     * initialized in the constructor using DBConnectionFactory component and
     * never changed after that. It will be used in various persistence methods
     * of this project.
     * </p>
     */
    private final DBConnectionFactory factory;

    /**
     * <p>
     * Represents the database connection name that will be used by
     * DBConnectionFactory. This variable is initialized in the constructor and
     * never changed after that. It will be used in create/update/retrieve
     * method to create connection. This variable can be null, which mean
     * connection name is not defined in the configuration namespace and default
     * connection will be created.
     * </p>
     */
    private final String connectionName;


	private final String namespace;

    /**
     * <p>
     * Represents the IDGenerator for project table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in createProject() method to generate new Id for project..
     * </p>
     */
    private final IDGenerator projectIdGenerator;

    /**
     * <p>
     * Represents the IDGenerator for contest_sale table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in createContestSale() method to generate new Id for contest_sale.
     * </p>
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private final IDGenerator contestSaleIdGenerator;
    
    /**
     * <p>
     * Represents the IDGenerator for project audit table. This variable is
     * initialized in the constructor and never change after that. It will be
     * used in updateProject() method to store update reason.
     * </p>
     */
    private final IDGenerator projectAuditIdGenerator;

    /**
     * <p>
     * Represents the IDGenerator for project spec table. This variable is
     * initialized in the constructor and never change after that.
     * </p>
     * 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private final IDGenerator projectSpecIdGenerator;

    /**
     * <p>
     * Creates a new instance of <code>AbstractProjectPersistence</code> from
     * the settings in configuration.
     * </p>
     * <p>
     * The following parameters are configured.
     * <ul>
     * <li>ConnectionFactoryNS: The namespace that contains settings for DB
     * Connection Factory. It is required.</li>
     * <li>ConnectionName: The name of the connection that will be used by
     * DBConnectionFactory to create connection. If missing, default connection
     * will be created. It is optional.</li>
     * <li>ProjectIdSequenceName: The sequence name used to create Id generator
     * for project Id. If missing default value (project_id_seq) is used. It is
     * optional.</li>
     * <li>ProjectAuditIdSequenceName: The sequence name used to create Id
     * generator for project audit Id. If missing, default value
     * (project_audit_id_seq) is used. It is optional.</li>
     * </ul>
     * </p>
     * <p>
     * Configuration sample:
     *
     * <pre>
     * &lt;Config name=&quot;AbstractInformixProjectPersistence&quot;&gt;
     * &lt;!-- The DBConnectionFactory class name used to create DB Connection Factory, required --&gt;
     * &lt;Property name=&quot;ConnectionFactoryNS&quot;&gt;
     * &lt;Value&gt;com.topcoder.db.connectionfactory.DBConnectionFactoryImpl&lt;/Value&gt;
     * &lt;/Property&gt;
     * &lt;Property name=&quot;ConnectionName&quot;&gt;
     * &lt;Value&gt;informix_connection&lt;/Value&gt;
     * &lt;/Property&gt;
     * &lt;Property name=&quot;ProjectIdSequenceName&quot;&gt;
     * &lt;Value&gt;project_id_seq&lt;/Value&gt;
     * &lt;/Property&gt;
     * &lt;Property name=&quot;ProjectAuditIdSequenceName&quot;&gt;
     * &lt;Value&gt;project_audit_id_seq&lt;/Value&gt;
     * &lt;/Property&gt;
     * &lt;/Config&gt;
     * </pre>
     *
     * </p>
     * @param namespace The namespace to load configuration setting.
     * @throws IllegalArgumentException if the input is null or empty string.
     * @throws ConfigurationException if error occurs while loading
     *             configuration settings, or required configuration parameter
     *             is missing.
     * @throws PersistenceException if cannot initialize the connection to the
     *             database.
     */
    protected AbstractInformixProjectPersistence(String namespace)
        throws ConfigurationException, PersistenceException {
        Helper.assertStringNotNullNorEmpty(namespace, "namespace");

		this.namespace = namespace;


        // get the instance of ConfigManager
        ConfigManager cm = ConfigManager.getInstance();

        // get db connection factory namespace
        String factoryNamespace = Helper.getConfigurationParameterValue(cm,
                namespace, CONNECTION_FACTORY_NAMESPACE_PARAMETER, true, getLogger());

        // try to create a DBConnectionFactoryImpl instance from
        // factoryNamespace
        try {
            factory = new DBConnectionFactoryImpl(factoryNamespace);
        } catch (Exception e) {
            throw new ConfigurationException(
                    "Unable to create a new instance of DBConnectionFactoryImpl class"
                            + " from namespace [" + factoryNamespace + "].", e);
        }

        // get the connection name
        connectionName = Helper.getConfigurationParameterValue(cm, namespace,
                CONNECTION_NAME_PARAMETER, false, getLogger());

        // try to get project id sequence name
        String projectIdSequenceName = Helper.getConfigurationParameterValue(
                cm, namespace, PROJECT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project id sequence name is not provided
        if (projectIdSequenceName == null) {
            projectIdSequenceName = PROJECT_ID_SEQUENCE_NAME;
        }

        // try to get contest sale id sequence name
        String contestSaleIdSequenceName = Helper.getConfigurationParameterValue(
                cm, namespace, CONTEST_SALE_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if contest sale id sequence name is not provided
        if (contestSaleIdSequenceName == null) {
        	contestSaleIdSequenceName = CONTEST_SALE_ID_SEQUENCE_NAME;
        }

        // try to get project audit id sequence name
        String projectAuditIdSequenceName = Helper
                .getConfigurationParameterValue(cm, namespace,
                        PROJECT_AUDIT_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project audit id sequence name is not provided
        if (projectAuditIdSequenceName == null) {
            projectAuditIdSequenceName = PROJECT_AUDIT_ID_SEQUENCE_NAME;
        }
        
        //
        // try to get project spec id sequence name
        // since Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        String projectSpecIdSequenceName = Helper
                .getConfigurationParameterValue(cm, namespace,
                        PROJECT_SPEC_ID_SEQUENCE_NAME_PARAMETER, false, getLogger());
        // use default name if project spec id sequence name is not provided
        if (projectSpecIdSequenceName == null) {
            projectSpecIdSequenceName = PROJECT_SPEC_ID_SEQUENCE_NAME;
        }

        // try to get the IDGenerators
        try {
            projectIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The projectIdSequence [" + projectIdSequenceName+"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectIdSequenceName + "'.", e);
        }
        try {
            contestSaleIdGenerator = IDGeneratorFactory
                    .getIDGenerator(contestSaleIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The contestSaleIdSequence [" + contestSaleIdSequenceName+"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + contestSaleIdSequenceName + "'.", e);
        }
        try {
            projectAuditIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectAuditIdSequenceName);
        } catch (IDGenerationException e) {
        	getLogger().log(Level.ERROR, "The projectAuditIdSequence [" + projectAuditIdSequenceName +"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectAuditIdSequenceName + "'.", e);
        }
        
        //
        // create the instance of project spec id generator.
        // since Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        try {
            projectSpecIdGenerator = IDGeneratorFactory
                    .getIDGenerator(projectSpecIdSequenceName);
        } catch (IDGenerationException e) {
            getLogger().log(Level.ERROR, "The projectSpecIdSequence [" + projectSpecIdSequenceName +"] is invalid.");
            throw new PersistenceException("Unable to create IDGenerator for '"
                    + projectSpecIdSequenceName + "'.", e);
        }
    }

    /**
     * <p>
     * Creates the project in the database using the given project instance.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. The project's associating
     * scorecards are stored in 'project_scorecard' table. For the project, its
     * properties and associating scorecards, the operator parameter is used as
     * the creation/modification user and the creation date and modification
     * date will be the current date time when the project is created.
     * </p>
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is
     *             empty string.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public void createProject(Project project, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        Connection conn = null;

        // newId will contain the new generated Id for the project
        Long newId;
      
        
        getLogger().log(Level.INFO, new LogMessage(null, operator, 
        		"creating new project: " + project.getAllProperties()));

        
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (project.getId() > 0) {
                if (Helper.checkEntityExists("project", "project_id", project
                        .getId(), conn)) {
                	throw new PersistenceException(
                            "The project with the same id [" + project.getId()
                                    + "] already exists.");
                }
            }

            try {
                // generate id for the project
                newId = new Long(projectIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project"));
            } catch (IDGenerationException e) {
                throw new PersistenceException(
                        "Unable to generate id for the project.", e);
            }

            // create the project
            createProject(newId, project, operator, conn);
            
            closeConnection(conn);
        } catch (PersistenceException e) {
            /*project.setCreationUser(null);
            project.setCreationTimestamp(null);
            project.setModificationUser(null);
            project.setModificationTimestamp(null); */
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, operator, "Fails to create project " + project.getAllProperties(), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the newId when no exception occurred
        project.setId(newId.longValue());

        
    }

    /**
     * <p>
     * Update the given project instance into the database.
     * </p>
     * <p>
     * The project information is stored to 'project' table, while its
     * properties are stored in 'project_info' table. The project's associating
     * scorecards are stored in 'project_scorecard' table. All related items in
     * these tables will be updated. If items are removed from the project, they
     * will be deleted from the persistence. Likewise, if new items are added,
     * they will be created in the persistence. For the project, its properties
     * and associating scorecards, the operator parameter is used as the
     * modification user and the modification date will be the current date time
     * when the project is updated. An update reason is provided with this
     * method. Update reason will be stored in the persistence for future
     * references.
     * </p>
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator The modification user of this project.
     * @throws IllegalArgumentException if any input is null or the operator is
     *             empty string. Or project id is zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public void updateProject(Project project, String reason, String operator)
        throws PersistenceException {
        Helper.assertObjectNotNull(project, "project");
        Helper.assertLongPositive(project.getId(), "project id");
        Helper.assertObjectNotNull(reason, "reason");
        Helper.assertStringNotNullNorEmpty(operator, "operator");

        // modifyDate will contain the modify_date retrieved from database.
        Date modifyDate;

        Connection conn = null;
        
        getLogger().log(Level.INFO, new LogMessage(new Long(project.getId()), operator, 
        		"updating project: " + project.getAllProperties()));
        try {
            // create the connection
            conn = openConnection();

            // check whether the project id is already in the database
            if (!Helper.checkEntityExists("project", "project_id", project
                    .getId(), conn)) {
                throw new PersistenceException("The project id ["
                        + project.getId() + "] does not exist in the database.");
            }

            // update the project
            updateProject(project, reason, operator, conn);

            getLogger().log(Level.INFO, new LogMessage(new Long(project.getId()), operator,
                    "execute sql:" + "SELECT modify_date " + "FROM project WHERE project_id=?"));
            // get the modification date.
            modifyDate = (Date) Helper.doSingleValueQuery(conn,
                    "SELECT modify_date " + "FROM project WHERE project_id=?",
                    new Object[] {new Long(project.getId())},
                    Helper.DATE_TYPE);

            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, operator, "Fails to update project " + project.getAllProperties(), e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the modification user and date when no exception
        // occurred.
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
    }

    /**
     * Retrieves the project instance from the persistence given its id. The
     * project instance is retrieved with its related items, such as properties
     * and scorecards.
     * @return The project instance.
     * @param id The id of the project to be retrieved.
     * @throws IllegalArgumentException if the input id is less than or equal to
     *             zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project getProject(long id) throws PersistenceException {
        Helper.assertLongPositive(id, "id");

        Project[] projects = getProjects(new long[] {id});
        return projects.length == 0 ? null : projects[0];
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids ids The ids of the projects to be retrieved.
     * @return An array of project instances.
     * @throws PersistenceException
     * @throws IllegalArgumentException if the input id is less than or equal to
     *             zero.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project[] getProjects(long[] ids) throws PersistenceException {
        Helper.assertObjectNotNull(ids, "ids");

        // check if ids is empty
        if (ids.length == 0) {
            throw new IllegalArgumentException(
                    "Array 'ids' should not be empty.");
        }

        String idstring = "";
        // check if ids contains an id that is <= 0
        for (int i = 0; i < ids.length; ++i) {
        	idstring += ids[i] + ",";
            if (ids[i] <= 0) {
                throw new IllegalArgumentException(
                        "Array 'ids' contains an id that is <= 0.");
            }
        }

        Connection conn = null;

        getLogger().log(Level.INFO, "get projects with the ids: " + idstring.substring(0, idstring.length() - 1));
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjects(ids, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving projects with ids: " + idstring.substring(0, idstring.length() - 1), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } catch (ParseException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving projects with ids: " + idstring.substring(0, idstring.length() - 1), e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            
            throw new PersistenceException("Fails to retrieve projects", e);
        }
    }

    /**
     * <p>
     * Retrieves an array of project instance from the persistence whose
	 * create date is within current - days 
     * </p>
     * @param days last 'days' 
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public Project[] getProjectsByCreateDate(int days)
        throws PersistenceException {
       
        Connection conn = null;

        getLogger().log(Level.INFO, "get projects by create date: " + days);
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Project[] projects = getProjectsByCreateDate(days, conn);
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving by create date: " + days, e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectTypes method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project types
            ProjectType[] projectTypes = getAllProjectTypes(conn);
            closeConnection(conn);
            return projectTypes;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectCategory[] getAllProjectCategories()
        throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectCategories method."));
        try {
            // create the connection
            conn = openConnection();

            // get all categories
            ProjectCategory[] projectCategories = getAllProjectCategories(conn);

            closeConnection(conn);
            return projectCategories;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectCategories.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        Connection conn = null;
        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectStatuses method."));
        try {
            // create the connection
            conn = openConnection();

            // get all the project statuses
            ProjectStatus[] projectStatuses = getAllProjectStatuses(conn);
            closeConnection(conn);
            return projectStatuses;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectStatuses.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    public ProjectPropertyType[] getAllProjectPropertyTypes()
        throws PersistenceException {
    	getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getAllProjectPropertyTypes method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            ProjectPropertyType[] propertyTypes = getAllProjectPropertyTypes(conn);
            closeConnection(conn);
            return propertyTypes;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getAllProjectPropertyTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

	/**
     * Retrieve scorecard id with given project type and scorecard type.
	 *
     * @param projectTypeId   the project type id
     * @param scorecardTypeId the scorecard type id
     * @return the scorecard id
     */
    public long getScorecardId(long projectTypeId, int scorecardTypeId) throws PersistenceException
	{

		getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getScorecardId method."));
        Connection conn = null;
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // create the connection
            conn = openConnection();

            ps = conn.prepareStatement("SELECT scorecard_id FROM default_scorecard " +
                    "WHERE project_category_id = ? and scorecard_type_id = ? ");
            ps.setLong(1, projectTypeId);
            ps.setInt(2, scorecardTypeId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("scorecard_id");
            } else {
                throw new RuntimeException("Cannot find default scorecard id for project type: " +
                        projectTypeId + ", scorecard type: " + scorecardTypeId);
            }

        } catch (Exception e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,"Fail to getScorecardId.", e));
			if (rs != null)
			{
				try
				{
					rs.close();	
				}
				catch (Exception ee)
				{
				}
				
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception ee)
				{
				}
				
			}
            if (conn != null) {
                closeConnectionOnError(conn);
                conn = null;
            }
            throw new PersistenceException(e.getMessage());
        } finally {
            if (rs != null)
			{
				try
				{
					rs.close();	
				}
				catch (Exception ee)
				{
				}
				
			}
			if (ps != null)
			{
				try
				{
					ps.close();
				}
				catch (Exception ee)
				{
				}
				
			}
            if (conn != null) {
                closeConnection(conn);
            }
        }


	}

    /**
     * Returns the database connection name that will be used by DB Connection
     * Factory.
     * @return a possibly <code>null</code> string representing the connection name
     *         used in DB Connection Factory.
     */
    protected String getConnectionName() {
        return connectionName;
    }

    /**
     * returns the <code>DBConnectionFactory</code> instance used to create
     * connection to the database.
     * @return the <code>DBConnectionFactory</code> instance used to create
     *         connection to the database.
     */
    protected DBConnectionFactory getConnectionFactory() {
        return factory;
    }

    /**
     * <p>Return the getLogger().</p>
     * @return the <code>Log</code> instance used to take the log message
     */
    protected abstract Log getLogger();
    /**
     * <p>
     * Abstract method used to get an open connection from which to perform DB
     * operations.
     * </p>
     * <p>
     * The implementations in subclasses will get a connection and properly
     * prepare it for use, depending on the transaction management strategy used
     * in the subclass.
     * </p>
     * @return an open connection used for DB operation.
     * @throws PersistenceException if there's a problem getting the Connection
     */
    protected abstract Connection openConnection() throws PersistenceException;

    /**
     * <p>
     * Abstract method used to commit any transaction (if necessary in the
     * subclass) and close the given connection after an operation completes
     * successfully.
     * </p>
     * <p>
     * It is used by all public methods after the successful execution of DB
     * operation.
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection.
     * Depending on the transaction management strategy used in the subclass, a
     * transaction on the connection may be committed.
     * </p>
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs trying to close the
     *             connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnection(Connection connection)
        throws PersistenceException;

    /**
     * <p>
     * Abstract method used to rollback any transaction (if necessary in the
     * subclass) and close the given connection when an error occurs.
     * </p>
     * <p>
     * It is used by all public methods just before any exception is thrown if
     * fails to do DB operation..
     * </p>
     * <p>
     * The implementations in subclasses will close the given connection.
     * Depending on the transaction management strategy used in the subclass, a
     * transaction on the connection may be rolled back.
     * </p>
     * @param connection connection to close
     * @throws PersistenceException if any problem occurs closing the Connection
     * @throws IllegalArgumentException if the argument is <code>null</code>
     */
    protected abstract void closeConnectionOnError(Connection connection)
        throws PersistenceException;

    /**
     * Creates the project in the database using the given project instance.
     * <p>
     * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
     *      - added creation of project spec.
     * </p>
     * 
     * <p>
     * Updated for Version 1.1.2 -- from project property we determine whether standardCCA is required or not.
     * </p>
     * 
     * @param projectId The new generated project id
     * @param project The project instance to be created in the database.
     * @param operator The creation user of this project.
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProject(Long projectId, Project project,
            String operator, Connection conn) throws PersistenceException {

    	getLogger().log(Level.INFO, "insert record into project with id:" + projectId);
    	
    	//set tc_direct_project_id  
    	Long tcDirectProjectId;
        if(project.getTcDirectProjectId() > 0 ) {
        	tcDirectProjectId = new Long(project.getTcDirectProjectId());
        } else {
        	tcDirectProjectId = null;
        }
        
        // insert the project into database
        Object[] queryArgs = new Object[] {projectId,
            new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator,
            operator, tcDirectProjectId};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SQL, queryArgs);

        // get the creation date.
          // createDate will contain the create_date value retrieved from
        // database.
        Date    createDate = (Date) Helper.doSingleValueQuery(conn,
                    "SELECT create_date FROM project WHERE project_id=?",
                    new Object[] {projectId}, Helper.DATE_TYPE);

        // set the creation/modification user and date when no exception
        // occurred
        project.setCreationUser(operator);
        project.setCreationTimestamp(createDate);
        project.setModificationUser(operator);
        project.setModificationTimestamp(createDate);
        
        //
        // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        createProjectSpec(projectId, project.getProjectSpec(), operator, conn);
        
        Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));
        
        // get the property id - property value map from the project.
        Map idValueMap = makePropertyIdPropertyValueMap(project
                .getAllProperties(), conn, nameIdMap);
        
        // get the standard cca value from project property. 
        String value = (String) idValueMap.get(nameIdMap.get(PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY));

        // get the standard cca value from project property. 
        boolean standardCCA = (value != null && !value.equalsIgnoreCase(CONFIDENTIALITY_TYPE_PUBLIC));

        // get the billing project id
        long billingProjectId = new Long((String) idValueMap.get(nameIdMap.get(PROJECT_INFO_BILLING_PROJECT_PROPERTY)));

        //insert the term of use for the project
        createRoleTermOfUse(projectId, billingProjectId, project.getProjectCategory().getId(), standardCCA, conn);
        
        // create the project properties
        createProjectProperties(projectId, project, idValueMap, operator, conn);
    }

    /**
     * <p>
     * Persists the specified ProjectSpec instance for the specified projectId.
     * It basically creates a new row in table corresponding to ProjectSpec - version number for the row is choosen as 1.
     * </p>
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param projectSpec the project spec
     * @param operator the operator/user who is creating this project spec.
     * @param conn the db connection to be used to create this project spec
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private void createProjectSpec(Long projectId, ProjectSpec projectSpec, String operator, Connection conn) throws PersistenceException {
        // check whether the project spec id is already in the database
        if (projectSpec.getProjectSpecId() > 0) {
            if (Helper.checkEntityExists("project_spec", "project_spec_id", projectSpec
                    .getProjectSpecId(), conn)) {
                throw new PersistenceException(
                        "The projectSpec with the same id [" + projectSpec.getProjectSpecId()
                                + "] already exists.");
            }
        }
        
        Long newId = 0L;
        
        try {
            // generate id for the project spec
            newId = new Long(projectSpecIdGenerator.getNextID());
            getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project spec"));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for the project spec.", e);
        }
        
        getLogger().log(Level.INFO, "insert record into project_spec with id:" + newId);
        
        // insert the project spec into database
        Object[] queryArgs = new Object[] {newId,
            projectId, 
			// TODO replace &lt; to <, should not need manually do this
            projectSpec.getDetailedRequirements().replace("&lt;", "<"),
            projectSpec.getSubmissionDeliverables().replace("&lt;", "<"),
            projectSpec.getEnvironmentSetupInstructions().replace("&lt;", "<"),
            projectSpec.getFinalSubmissionGuidelines().replace("&lt;", "<"), 
            operator,
            operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_SPEC_SQL, queryArgs);
        
        // get the creation date.
        Date specCreateDate = (Date) Helper.doSingleValueQuery(conn,
                "SELECT create_date FROM project_spec WHERE project_spec_id=?",
                new Object[] {newId}, Helper.DATE_TYPE);
        
        // set the newId when no exception occurred
        projectSpec.setProjectSpecId(newId.longValue());

        // set the creation/modification user and date when no exception occurred
        projectSpec.setCreationUser(operator);
        projectSpec.setCreationTimestamp(specCreateDate);
        projectSpec.setModificationUser(operator);
        projectSpec.setModificationTimestamp(specCreateDate);
    }

    /**
     * Update the given project instance into the database.
     * 
     * <p>
     * Updated for Cockpit Launch Contest - Update for Spec Creation v1.0
     *      -- added update for project spec.
     * </p>
     * 
     * @param project The project instance to be updated into the database.
     * @param reason The update reason. It will be stored in the persistence for
     *            future references.
     * @param operator The modification user of this project.
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void updateProject(Project project, String reason, String operator,
            Connection conn) throws PersistenceException {
        Long projectId = new Long(project.getId());

        getLogger().log(Level.INFO, new LogMessage(projectId, operator,
        		"update project with projectId:" + projectId));
        // 
        Long tcDirectProjectId;
        if(project.getTcDirectProjectId() > 0 ) {
        	tcDirectProjectId = new Long(project.getTcDirectProjectId());
        } else {
        	tcDirectProjectId = null;
        }

         Timestamp modifyDate = new Timestamp(System.currentTimeMillis());
        // update the project type and project category
        Object[] queryArgs = new Object[] {
            new Long(project.getProjectStatus().getId()),
            new Long(project.getProjectCategory().getId()), operator, modifyDate, tcDirectProjectId, 
            projectId };
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SQL, queryArgs);

       // update the project object so this data's correct for audit purposes
        project.setModificationUser(operator);
        project.setModificationTimestamp(modifyDate);
        
        //
        // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
        //
        updateProjectSpec(project.getId(), project.getProjectSpec(), operator, conn);

        Map nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));        
        // get the property id - property value map from the project.
        Map idValueMap = makePropertyIdPropertyValueMap(project
                .getAllProperties(), conn, nameIdMap);
        // clean project role terms of use associations for the recently created project.
        cleanProjectRoleTermsOfUseAssociations(projectId, conn);        
        // get the standard cca value from project property. 
        String value = (String) idValueMap.get(nameIdMap.get(PROJECT_INFO_CONFIDENTIALITY_TYPE_PROPERTY));
        boolean standardCCA = (value != null && !value.equalsIgnoreCase(CONFIDENTIALITY_TYPE_PUBLIC));
        // get the standard cca value from project property.         
        long billingProjectId = new Long((String) idValueMap.get(nameIdMap.get(PROJECT_INFO_BILLING_PROJECT_PROPERTY)));        
        //insert the term of use for the project
        createRoleTermOfUse(projectId, billingProjectId, project.getProjectCategory().getId(), standardCCA, conn);
        
        // update the project properties
        updateProjectProperties(project, idValueMap, operator, conn);

        // create project audit record into project_audit table
        createProjectAudit(projectId, reason, operator, conn);
    }

    /**
     * <p>
     * Persists the specified ProjectSpec instance for the specified projectId.
     * It basically creates a new row in table corresponding to ProjectSpec - version number for the row is earlier version number + 1.
     * </p>
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param projectSpec the project spec
     * @param operator the operator/user who is creating this project spec.
     * @param conn the db connection to be used to create this project spec
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
    private void updateProjectSpec(Long projectId, ProjectSpec projectSpec, String operator, Connection conn) throws PersistenceException {
        Long newId = 0L;
        
        try {
            // generate id for the project spec
            newId = new Long(projectSpecIdGenerator.getNextID());
            getLogger().log(Level.INFO, new LogMessage(newId, operator, "generate id for new project spec"));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for the project spec.", e);
        }
        
        getLogger().log(Level.INFO, "insert record into project_spec with id:" + newId);
        
        // insert the project spec into database
        Object[] queryArgs = new Object[] {newId,
            projectId, 
            projectId,
			// TODO replace &lt; to <, should not need manually do this
            projectSpec.getDetailedRequirements().replace("&lt;", "<"),
            projectSpec.getSubmissionDeliverables().replace("&lt;", "<"),
            projectSpec.getEnvironmentSetupInstructions().replace("&lt;", "<"),
            projectSpec.getFinalSubmissionGuidelines().replace("&lt;", "<"), 
            operator,
            operator};
        Helper.doDMLQuery(conn, UPDATE_PROJECT_SPEC_SQL, queryArgs);
        
        // get the creation date.
        Date specCreateDate = (Date) Helper.doSingleValueQuery(conn,
                "SELECT create_date FROM project_spec WHERE project_spec_id=?",
                new Object[] {newId}, Helper.DATE_TYPE);
        
        // set the newId when no exception occurred
        projectSpec.setProjectSpecId(newId.longValue());

        // set the creation/modification user and date when no exception occurred
        projectSpec.setCreationUser(operator);
        projectSpec.setCreationTimestamp(specCreateDate);
        projectSpec.setModificationUser(operator);
        projectSpec.setModificationTimestamp(specCreateDate);
    }

    /**
     * Makes a property id-property value(String) map from property
     * name-property value map.
     * 
     * Refactored for 1.1.2
     * 
     * @param nameValueMap the property name-property value map
     * @param conn the database connection
     * @param nameIdMap property name to propery id map.
     * @return a property id-property value map
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn)
        throws PersistenceException {
        return makePropertyIdPropertyValueMap(nameValueMap, conn, null);
    }

    /**
     * Makes a property id-property value(String) map from property
     * name-property value map.
     * 
     * Refactored for 1.1.2
     * 
     * @param nameValueMap the property name-property value map
     * @param conn the database connection
     * @param nameIdMap property name to propery id map.
     * @return a property id-property value map
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map makePropertyIdPropertyValueMap(Map nameValueMap, Connection conn, Map nameIdMap)
        throws PersistenceException {
        Map idValueMap = new HashMap();

        // get the property name-property id map if not passed already.
        if (nameIdMap == null) {
            nameIdMap = makePropertyNamePropertyIdMap(getAllProjectPropertyTypes(conn));
        }

        // enumerate each property
        for (Iterator it = nameValueMap.entrySet().iterator(); it.hasNext();) {
            Entry entry = (Entry) it.next();

            // find the property id from property name
            Object propertyId = nameIdMap.get(entry.getKey());
            // check if the property id can be found
            if (propertyId == null) {
                throw new PersistenceException(
                        "Unable to find ProjectPropertyType name ["
                                + entry.getKey()
                                + "] in project_info_type_lu table.");
            }

            // put the property id-property value(String) pair into the map
            idValueMap.put(propertyId, entry.getValue().toString());
        }
        return idValueMap;
    }

    /**
     * Gets an array of all project property type in the persistence. The
     * project property types are stored in 'project_info_type_lu' table.
     * @param conn the database connection
     * @return An array of all scorecard assignments in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectPropertyType[] getAllProjectPropertyTypes(Connection conn)
        throws PersistenceException {
    	// find all project property types in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_PROPERTY_TYPES_SQL, new Object[] {},
                QUERY_ALL_PROJECT_PROPERTY_TYPES_COLUMN_TYPES);

        // create the ProjectPropertyType array.
        ProjectPropertyType[] propertyTypes = new ProjectPropertyType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectPropertyType class
            propertyTypes[i] = new ProjectPropertyType(((Long) row[0])
                    .longValue(), (String) row[1], (String) row[2]);
        }

        return propertyTypes;
    }
    
    /**
     * Build {@link Project} directly from the {@link CustomResultSet}
     * 
     * @param resultSet a {@link CustomResultSet} containing the data for build the {@link Project} instances. 
     * @return an array of {@link Project}
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public Project[] getProjects(CustomResultSet result) throws PersistenceException {
    	Connection conn = null;
        try {
        	conn = openConnection();
        	PreparedStatement ps = conn.prepareStatement(QUERY_ONE_PROJECT_PROPERTIES_SQL);
			int size = result.getRecordCount();
			Project[] projects = new Project[size];
			for (int i = 0; i < size; i++) {
				result.absolute(i + 1);
				
				// create the ProjectStatus object
			    ProjectStatus status = new ProjectStatus(result.getLong(2), result.getString(3));

			    // create the ProjectType object
			    ProjectType type = new ProjectType(result.getLong(6), result.getString(7));

			    // create the ProjectCategory object
			    ProjectCategory category = new ProjectCategory(result.getLong(4), result.getString(5), type);

			    // create a new instance of ProjectType class
			    projects[i] = new Project(result.getLong(1), category, status);

			    // assign the audit information
			    projects[i].setCreationUser(result.getString(8));
			    projects[i].setCreationTimestamp(result.getDate(9));
			    projects[i].setModificationUser(result.getString(10));
			    projects[i].setModificationTimestamp(result.getDate(11));
			    projects[i].setTcDirectProjectId(result.getLong(12));
			    
			    //
	            // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
	            //
	            ProjectSpec[] specs = getProjectSpecs(projects[i].getId(), conn);
	            if (specs != null && specs.length > 0) {
	                projects[i].setProjectSpec(specs[0]);
	            }
			    
			    ps.setLong(1, projects[i].getId());
			    ResultSet rs = ps.executeQuery();
			    while (rs.next()) {
			    	projects[i].setProperty(rs.getString(2), rs.getString(3));
			    }			    
			}
			return projects;
		} catch (InvalidCursorStateException icse) {
            throw new PersistenceException("cursor state is invalid.", icse);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage(), e);
		} finally {
			if (conn != null) {
				closeConnection(conn);
			}
		}
	}
    
    /**
     * <p>
     * Retrieves an array of project instance from the persistence given their
     * ids. The project instances are retrieved with their properties.
     * </p>
     * @param ids The ids of the projects to be retrieved.
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     * @throws ParseException if error occurred while parsing the result
     */
    private Project[] getProjects(long ids[], Connection conn)
        throws PersistenceException, ParseException {

        // build the id list string
        StringBuffer idListBuffer = new StringBuffer();
        idListBuffer.append('(');
        for (int i = 0; i < ids.length; ++i) {
            if (i != 0) {
                idListBuffer.append(',');
            }
            idListBuffer.append(ids[i]);
        }
        idListBuffer.append(')');
        // get the id list string
        String idList = idListBuffer.toString();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECTS_SQL + idList,
                new Object[] {}, QUERY_PROJECTS_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = getProjects(rows, conn);
        return projects;
    }

    /**
     * Gets an array of all project types in the persistence. The project types
     * are stored in 'project_type_lu' table.
     * @param conn the database connection
     * @return An array of all project types in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectType[] getAllProjectTypes(Connection conn)
        throws PersistenceException {
    	// find all project types in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_TYPES_SQL,
                new Object[] {}, QUERY_ALL_PROJECT_TYPES_COLUMN_TYPES);

        // create the ProjectType array.
        ProjectType[] projectTypes = new ProjectType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectType class
            projectTypes[i] = new ProjectType(((Long) row[0]).longValue(),
                    (String) row[1], (String) row[2]);
        }

        return projectTypes;
    }

    /**
     * Create the project properties in the database.
     * @param project The new generated project
     * @param idValueMap The property id - property value map
     * @param operator The creation user of this project
     * @param conn The database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProjectProperties(Long projectId, Project project, Map idValueMap,
            String operator, Connection conn) throws PersistenceException {

    	getLogger().log(Level.INFO, new LogMessage(projectId, operator,
    			"insert record into project_info with project id" + projectId));
        PreparedStatement preparedStatement = null;
        try {
            // prepare the statement.
            preparedStatement = conn
                    .prepareStatement(CREATE_PROJECT_PROPERTY_SQL);

            // enumerator each property in the idValueMap
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                // insert the project property into database
                Object[] queryArgs = new Object[] {projectId, entry.getKey(),
                        entry.getValue(), operator, operator };
                Helper.doDMLQuery(preparedStatement, queryArgs);
                
                auditProjectInfo(conn, projectId, project, AUDIT_CREATE_TYPE, (Long) entry.getKey(),
                		(String) entry.getValue());
            }

        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + CREATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }

    /**
     * Make an Id-Project map from Project[].
     * @param projects the Id-Project array
     * @return an Id-Project map
     */
    private Map makeIdProjectMap(Project[] projects) {
        Map map = new HashMap();

        for (int i = 0; i < projects.length; ++i) {
            map.put(new Long(projects[i].getId()), projects[i]);
        }
        return map;
    }

    /**
     * Update the project properties into the database.
     * @param project the project object
     * @param idValueMap the property id - property value map
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void updateProjectProperties(Project project, Map idValueMap,
            String operator, Connection conn) throws PersistenceException {
    	
    	Long projectId = project.getId();
    	
        // get old property ids and values from database
        Map<Long, String> propertyMap = getProjectPropertyIdsAndValues(projectId, conn);

        // create a property id-property value map that contains the properties
        // to insert
        Map createIdValueMap = new HashMap();

        PreparedStatement preparedStatement = null;
        try {
        	getLogger().log(Level.INFO, new LogMessage(projectId, operator,
             				"update project, update project_info with projectId:" + projectId));
        	 
            // prepare the statement.
            preparedStatement = conn.prepareStatement(UPDATE_PROJECT_PROPERTY_SQL);

            // enumerator each property id in the project object
            for (Iterator it = idValueMap.entrySet().iterator(); it.hasNext();) {
                Entry entry = (Entry) it.next();

                Long propertyId = (Long) entry.getKey();

                // check if the property in the project object already exists in
                // the database
                if (propertyMap.containsKey(propertyId)) {
                	// if the value hasn't been changed, we don't need to update anything
                	if (!propertyMap.get(propertyId).equals((String) entry.getValue())) {
                		// update the project property
                		Object[] queryArgs = new Object[] {entry.getValue(),
                    		operator, projectId, propertyId };
                    	Helper.doDMLQuery(preparedStatement, queryArgs);
                    
                    	auditProjectInfo(conn, project, AUDIT_UPDATE_TYPE, propertyId, (String) entry.getValue());
                	}
                	propertyMap.remove(propertyId);
                } else {
                    // add the entry to the createIdValueMap
                    createIdValueMap.put(propertyId, entry.getValue());
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Unable to create prepared statement ["
                            + UPDATE_PROJECT_PROPERTY_SQL + "].", e);
        } finally {
            Helper.closeStatement(preparedStatement);
        }

        // create the new properties
        createProjectProperties(project.getId(), project, createIdValueMap, operator, conn);

        // delete the remaining property ids that are not in the project object
        // any longer
        deleteProjectProperties(project, propertyMap.keySet(), conn);
    }

    /**
     * Gets all the property ids associated to this project.
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A set that contains the property ids
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Set getProjectPropertyIds(Long projectId, Connection conn)
        throws PersistenceException {
        Set idSet = new HashSet();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_SQL,
                new Object[] {projectId},
                QUERY_PROJECT_PROPERTY_IDS_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the set
            idSet.add(row[0]);
        }
        return idSet;
    }

    /**
     * Gets all the property ids and values associated to this project.
     * 
     * @param projectId The id of this project
     * @param conn The database connection
     * @return A map that contains the property values, keyed by id
     * 
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Map<Long, String> getProjectPropertyIdsAndValues(Long projectId, Connection conn)
    		throws PersistenceException {
    	
        Map<Long, String> idMap = new HashMap<Long, String>();

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_SQL,
                new Object[] {projectId},
                QUERY_PROJECT_PROPERTY_IDS_AND_VALUES_COLUMN_TYPES);

        // enumerator each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // add the id to the map
            idMap.put((Long) row[0], (String) row[1]);
        }
        
        return idMap;
    }

    /**
     * Delete the project properties from the database.
     * @param project the project object
     * @param propertyIdSet the Set that contains the property ids to delete
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void deleteProjectProperties(Project project, Set<Long> propertyIdSet, Connection conn)
    		throws PersistenceException {
    	
    	Long projectId = project.getId();
    	
        // check if the property id set is empty
        // do nothing if property id set is empty
        if (!propertyIdSet.isEmpty()) {

            // build the id list string
            StringBuffer idListBuffer = new StringBuffer();
            idListBuffer.append('(');
            int idx = 0;
            for (Long id : propertyIdSet) {
                if (idx++ != 0) {
                    idListBuffer.append(',');
                }
                idListBuffer.append(id);
            }
            idListBuffer.append(')');

            getLogger().log(Level.INFO, new LogMessage(projectId, null,
            		"delete records from project_info with projectId:" + projectId));
            
            // delete the properties whose id is in the set
            Helper.doDMLQuery(conn, DELETE_PROJECT_PROPERTIES_SQL
                    + idListBuffer.toString(), new Object[] {projectId});
            
            for (Long id : propertyIdSet) {
            	auditProjectInfo(conn, project, AUDIT_DELETE_TYPE, id, null);
            }
        }
    }

    /**
     * Create a project audit record into the database.
     * @param projectId The id of the project
     * @param reason The update reason
     * @param operator the modification user of this project
     * @param conn the database connection
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private void createProjectAudit(Long projectId, String reason,
            String operator, Connection conn) throws PersistenceException {

        Long auditId;
        try {
            // generate a new audit id
            auditId = new Long(projectAuditIdGenerator.getNextID());
            getLogger().log(Level.INFO,
            		new LogMessage(projectId, operator, "generate new id for the project audit."));
        } catch (IDGenerationException e) {
            throw new PersistenceException(
                    "Unable to generate id for project.", e);
        }
        getLogger().log(Level.INFO, "insert record into project_audit with projectId:" + projectId);
        // insert the update reason information to project_audit table
        Object[] queryArgs = new Object[] {auditId, projectId, reason,
            operator, operator};
        Helper.doDMLQuery(conn, CREATE_PROJECT_AUDIT_SQL, queryArgs);
    }

    /**
     * Make a property name - property id map from ProjectPropertyType[].
     * @param propertyTypes the ProjectPropertyType array
     * @return a property name - property id map
     * @throws PersistenceException if duplicate property type names are found
     */
    private Map makePropertyNamePropertyIdMap(
            ProjectPropertyType[] propertyTypes) throws PersistenceException {
        Map map = new HashMap();

        // enumerate each property type
        for (int i = 0; i < propertyTypes.length; ++i) {

            // check if the property name already exists
            if (map.containsKey(propertyTypes[i].getName())) {
                throw new PersistenceException(
                        "Duplicate project property type names ["
                                + propertyTypes[i].getName()
                                + "] found in project_info_type_lu table.");
            }

            // put the name-id pair to the map
            map.put(propertyTypes[i].getName(), new Long(propertyTypes[i]
                    .getId()));
        }
        return map;
    }

    /**
     * Gets an array of all project categories in the persistence. The project
     * categories are stored in 'project_category_lu' table.
     * @param conn the database connection
     * @return An array of all project categories in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectCategory[] getAllProjectCategories(Connection conn)
        throws PersistenceException {
    	// find all project categories in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_ALL_PROJECT_CATEGORIES_SQL, new Object[] {},
                QUERY_ALL_PROJECT_CATEGORIES_COLUMN_TYPES);

        // create the ProjectCategory array.
        ProjectCategory[] projectCategories = new ProjectCategory[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[3]).longValue(),
                    (String) row[4], (String) row[5]);

            // create a new instance of ProjectCategory class
            projectCategories[i] = new ProjectCategory(((Long) row[0])
                    .longValue(), (String) row[1], (String) row[2], type);
        }

        return projectCategories;
    }

    /**
     * Gets an array of all project statuses in the persistence. The project
     * statuses are stored in 'project_status_lu' table.
     * @param conn the database connection
     * @return An array of all project statuses in the persistence.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private ProjectStatus[] getAllProjectStatuses(Connection conn)
        throws PersistenceException {
    	// find all project statuses in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_PROJECT_STATUSES_SQL,
                new Object[] {}, QUERY_ALL_PROJECT_STATUSES_COLUMN_TYPES);

        // create the ProjectStatus array.
        ProjectStatus[] projectStatuses = new ProjectStatus[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of ProjectStatus class
            projectStatuses[i] = new ProjectStatus(((Long) row[0]).longValue(),
                    (String) row[1], (String) row[2]);
        }

        return projectStatuses;
    }

     /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param projectId the id of the project being audited
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Long projectId, Project project, int auditType,
    		long projectInfoTypeId, String value) throws PersistenceException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(PROJECT_INFO_AUDIT_INSERT_SQL);

            int index = 1;
            statement.setLong(index++, projectId);
            statement.setLong(index++, projectInfoTypeId);
            statement.setString(index++, value);
            statement.setInt(index++, auditType);
            statement.setTimestamp(index++, new Timestamp(project.getModificationTimestamp().getTime()));
            statement.setLong(index++, Long.parseLong(project.getModificationUser()));

            if (statement.executeUpdate() != 1) {
                throw new PersistenceException("Audit information was not successfully saved.");
            }
        } catch (SQLException e) {
            closeConnectionOnError(connection);
            throw new PersistenceException("Unable to insert project_info_audit record.", e);
        } finally {
            Helper.closeStatement(statement);
        }
    }
    
    /**
     * This method will audit project information. This information is generated when most project properties are
     * inserted, deleted, or edited.
     *
     * @param connection the connection to database
     * @param project the project being audited
     * @param auditType the audit type. Can be AUDIT_CREATE_TYPE, AUDIT_DELETE_TYPE, or AUDIT_UPDATE_TYPE
     * @param projectInfoTypeId the project info type id
     * @param value the project info value that we're changing to
     *
     * @throws PersistenceException if validation error occurs or any error occurs in the underlying layer
     *
     * @since 1.1.2
     */
    private void auditProjectInfo(Connection connection, Project project, int auditType, long projectInfoTypeId, String value)
			throws PersistenceException {
    	auditProjectInfo(connection, project.getId(), project, auditType, projectInfoTypeId, value);
    }

    
    /**
     * <p>
     * Retrieves an array of project instance from the persistence where tc direct 
     * project id is not null. The project instances are retrieved with their properties.
     * </p>
     * 
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getAllTcDirectProject() throws PersistenceException {		

        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            // find projects in the table.
            Object[][] rows = Helper.doQuery(conn, QUERY_TC_DIRECT_PROJECTS_SQL,
                    new Object[] {}, QUERY_PROJECTS_COLUMN_TYPES);

              
            Project[] projects = getProjects(rows, conn); 
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving all tc direct projects " , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
		
	}
	
	/**
     * <p>
     * Retrieves an array of project instance from the persistence given user.
     * The project instances are retrieved with their properties.
     * </p>
     * @param operator The id of create user.     
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
	public Project[] getAllTcDirectProject(String operator) throws PersistenceException {
//		 check if ids is empty
        if (operator == null  ||  operator.length() == 0) {
            throw new IllegalArgumentException("Create user should not be null.");
        }
		Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            // find projects in the table.
            Object[][] rows = Helper.doQuery(conn, QUERY_USER_TC_DIRECT_PROJECTS_SQL,
                    new Object[] {operator}, QUERY_PROJECTS_COLUMN_TYPES);
            
            Project[] projects = getProjects(rows, conn); 
            closeConnection(conn);
            return projects;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieving all tc direct projects " , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
		
	}

    /**
     * <p>
     * Creates a new contest sale and returns the created contest sale.
     * </p>
     *
     * @param contestSale the contest sale to create
     *
     * @return the created contest sale.
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSale createContestSale(ContestSale contestSale) throws PersistenceException {
        Helper.assertObjectNotNull(contestSale, "contestSale");

        Connection conn = null;

        // newId will contain the new generated Id for the contest sale
        Long newId;

        getLogger().log(Level.INFO, new LogMessage(null, null, "creating new contest sale"));
        
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (contestSale.getContestSaleId() > 0) {
                if (Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSale
                        .getContestSaleId(), conn)) {
                	throw new PersistenceException(
                            "The contest_sale with the same id [" + contestSale.getContestSaleId()
                                    + "] already exists.");
                }
            }

            try {
                // generate id for the contest sale
                newId = new Long(contestSaleIdGenerator.getNextID());
                getLogger().log(Level.INFO, new LogMessage(null, null, "generate id for new contest sale"));
            } catch (IDGenerationException e) {
                throw new PersistenceException(
                        "Unable to generate id for the contest sale.", e);
            }

            // create the contest sale
            Object[] queryArgs = new Object[] {newId, contestSale.getContestId(), contestSale.getStatus().getSaleStatusId(),
            		contestSale.getPrice(), contestSale.getPayPalOrderId(),
            		contestSale.getSaleReferenceId(), contestSale.getSaleType().getSaleTypeId()};
            Helper.doDMLQuery(conn, CREATE_CONTEST_SALE_SQL, queryArgs);
            
            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to create contest sale", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }

        // set the newId when no exception occurred
        contestSale.setContestSaleId(newId.longValue());

        return contestSale;
    }

    /**
     * <p>
     * Gets the sale status by given id.
     * </p>
     *
     * @param saleStatusId the given sale status id.
     *
     * @return the sale status by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public SaleStatus getSaleStatus(long saleStatusId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            return getSaleStatus(saleStatusId, conn);

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the sale status" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        finally {

            closeConnection(conn);
        }
    }


     /**
     * <p>
     * Gets the sale status by given id.
     * </p>
     *
     * @param saleStatusId the given sale status id.
     *
     * @return the sale status by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private SaleStatus getSaleStatus(long saleStatusId, Connection conn) throws PersistenceException {

            // get the sale status
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_SALE_STATUS_BY_ID_SQL, new Object[] {saleStatusId},
            		QUERY_SALE_STATUS_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of SaleStatus class
            SaleStatus saleStatus = new SaleStatus();
            saleStatus.setSaleStatusId(((Long) rows[0][0]));
            saleStatus.setDescription((String) rows[0][1]);


            return saleStatus;

    }

    /**
     * <p>
     * Gets the sale type by given id.
     * </p>
     *
     * @param saleTypeId the given sale type id.
     *
     * @return the sale type by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public SaleType getSaleType(long saleTypeId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            return getSaleType(saleTypeId, conn);

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the sale type" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } finally {
            closeConnection(conn);

        }

    }



    /**
     * <p>
     * Gets the sale type by given id.
     * </p>
     *
     * @param saleTypeId the given sale type id.
     *
     * @return the sale type by given id.
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    private SaleType getSaleType(long saleTypeId, Connection conn) throws PersistenceException {

            // get the sale type
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_SALE_TYPE_BY_ID_SQL, new Object[] {saleTypeId},
            		QUERY_SALE_TYPE_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of SaleType class
            SaleType saleType = new SaleType();
            saleType.setSaleTypeId(((Long) rows[0][0]));
            saleType.setDescription((String) rows[0][1]);

            return saleType;

    }

    /**
     * <p>
     * Gets contest sale by id, and return the retrieved contest sale. If
     * the contest sale doesn't exist, null is returned.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return the retrieved contest sale, or null if id doesn't exist
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public ContestSale getContestSale(long contestSaleId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_CONTEST_SALE_BY_ID_SQL, new Object[] {contestSaleId},
            		QUERY_CONTEST_SALE_BY_ID_COLUMN_TYPES);

            if (rows.length == 0) {
                return null;
            }

            // create a new instance of ContestSale class
            ContestSale contestSale = new ContestSale();
            contestSale.setContestSaleId(((Long) rows[0][0]));
            contestSale.setContestId((Long) rows[0][1]);
            contestSale.setStatus(this.getSaleStatus((Long) rows[0][2], conn));
            contestSale.setPrice((Double) rows[0][3]);
            contestSale.setPayPalOrderId((String) rows[0][4]);
            contestSale.setCreateDate((Date) rows[0][5]);
            contestSale.setSaleReferenceId((String) rows[0][6]);
            contestSale.setSaleType(this.getSaleType((Long) rows[0][7], conn));


            return contestSale;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the contest sale" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        finally {

            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Gets contest sales by contest id, and return the retrieved contest sales.
     * </p>
     *
     * @param contestId the contest id of the contest sale
     *
     * @return the retrieved contest sales, or empty if none exists
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public List<ContestSale> getContestSales(long contestId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_CONTEST_SALE_BY_CONTEST_ID_SQL, new Object[] {contestId},
            		QUERY_CONTEST_SALE_BY_CONTEST_ID_COLUMN_TYPES);

            List<ContestSale> ret = new ArrayList<ContestSale>();

            for (int i = 0; i < rows.length; i++) {
                // create a new instance of ContestSale class
                ContestSale contestSale = new ContestSale();
                contestSale.setContestSaleId(((Long) rows[i][0]));
                contestSale.setContestId((Long) rows[i][1]);
                contestSale.setStatus(this.getSaleStatus((Long) rows[i][2], conn));
                contestSale.setPrice((Double) rows[i][3]);
                contestSale.setPayPalOrderId((String) rows[i][4]);
                contestSale.setCreateDate((Date) rows[i][5]);
                contestSale.setSaleReferenceId((String) rows[i][6]);
                contestSale.setSaleType(this.getSaleType((Long) rows[i][7], conn));

                ret.add(contestSale);
            }


            return ret;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve the contest sales" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }

    /**
     * <p>
     * Updates the contest sale data.
     * </p>
     *
     * @param contestSale the contest sale to update
     *
     * @throws IllegalArgumentException if the arg is null.
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public void editContestSale(ContestSale contestSale) throws PersistenceException {
        Helper.assertObjectNotNull(contestSale, "contestSale");

        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "updating contest sale"));
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (!Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSale
                    .getContestSaleId(), conn)) {
                throw new PersistenceException("The contest_sale id ["
                        + contestSale.getContestSaleId() + "] does not exist in the database.");
            }

            // update the contest sale
            Object[] queryArgs = new Object[] {
                contestSale.getContestId(), contestSale.getStatus().getSaleStatusId(), contestSale.getPrice(), contestSale.getPayPalOrderId(),
                contestSale.getSaleReferenceId(), contestSale.getSaleType().getSaleTypeId(), 
                contestSale.getContestSaleId()};
            Helper.doDMLQuery(conn, UPDATE_CONTEST_SALE_SQL, queryArgs);

            closeConnection(conn);
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to update contest sale", e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * <p>
     * Removes contest sale, return true if the contest sale exists and
     * removed successfully, return false if it doesn't exist.
     * </p>
     *
     * @param contestSaleId the contest sale id
     *
     * @return true if the contest sale exists and removed successfully,
     *         return false if it doesn't exist
     *
     * @throws PersistenceException if any other error occurs.
     *
     * @since Module Contest Service Software Contest Sales Assembly
     */
    public boolean removeContestSale(long contestSaleId) throws PersistenceException {
        Connection conn = null;

        getLogger().log(Level.INFO, new LogMessage(null, null, "removing contest sale"));
        try {
            // create the connection
            conn = openConnection();

            // check whether the contest_sale_id is already in the database
            if (!Helper.checkEntityExists("contest_sale", "contest_sale_id", contestSaleId, conn)) {
                return false;
            }

            // remove the contest sale
            Object[] queryArgs = new Object[] {contestSaleId};
            Helper.doDMLQuery(conn, DELETE_CONTEST_SALE_BY_ID_SQL, queryArgs);

            closeConnection(conn);

            return true;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR,
        			new LogMessage(null, null, "Fails to remove contest sale", e));
        	if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
	/**
	 * Retrieves an array of project
	 * The project instances are retrieved with their properties.
	 * @param rows
	 * @param conn the database connection
	 * @return An array of project instances.
	 * @throws PersistenceException
	 */
	
	private Project[] getProjects(Object [][]rows, Connection conn) throws PersistenceException {
		
		// 	create the Project array.
        Project[] projects = new Project[rows.length];
        
        // if no tc direct project found
        if(projects.length == 0 ) {        	
            return projects;
        }

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus(((Long) row[1])
                    .longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(),
                    (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3])
                    .longValue(), (String) row[4], type);
            category.setDescription((String) row[11]);
            // create a new instance of ProjectType class
            projects[i] = new Project(((Long) row[0]).longValue(), category,
                    status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);
            projects[i].setTcDirectProjectId(((Long)row[12]).longValue());
            
            //
            // Added for Cockpit Launch Contest - Update for Spec Creation v1.0
            //
            ProjectSpec[] specs = getProjectSpecs(projects[i].getId(), conn);
            if (specs != null && specs.length > 0) {
                projects[i].setProjectSpec(specs[0]);
            }
        }

        // get the Id-Project map
        Map projectMap = makeIdProjectMap(projects);
        String ids = projectMap.keySet().toString();
        ids = ids.replace('[', '(');
        ids = ids.replace(']', ')');
        
        // find project properties in the table.
        rows = Helper.doQuery(conn, QUERY_PROJECT_PROPERTIES_SQL + ids ,
                new Object[] {}, QUERY_PROJECT_PROPERTIES_COLUMN_TYPES);

        // enumerate each row
        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // get the corresponding Project object
            Project project = (Project) projectMap.get(row[0]);

            // set the property to project
            project.setProperty((String) row[1], (String)row[2]);
        }         
        return projects;
        
	}
	
	/**
     * <p>
     * Gets the list of ProjectSpec for the specified projectId.
     * Though the method signature is to return list, but it essentially returns the ProjectSpec corresponding to the latest version id
     * for the specified projectId's spec.
     * </p>
     * 
     * @param projectId the project id for which project spec need to be persisted.
     * @param conn the db connection to be used to create this project spec
     * 
     * @return the list of ProjectSpec for the specified projectId.
     * @throws PersistenceException exception is thrown when there is some error in persisting 
     * @since Cockpit Launch Contest - Update for Spec Creation v1.0
     */
	private ProjectSpec[] getProjectSpecs(Long projectId, Connection conn) throws PersistenceException {
	     // get the project objects
        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn,
                QUERY_PROJECT_SPEC_SQL, new Object[] {projectId, projectId},
                QUERY_PROJECT_SPEC_COLUMN_TYPES);
        
        if (rows == null || rows.length == 0) {
            return null;
        }
        
        ProjectSpec[] specs = new ProjectSpec[rows.length];
        for (int i = 0; i < rows.length; i++) {
            ProjectSpec spec = new ProjectSpec();
         
            spec.setProjectSpecId((Long) rows[i][0]);
            spec.setProjectId(projectId);
            spec.setVersion((Long) rows[i][1]);
            spec.setDetailedRequirements((String) rows[i][2]);
            spec.setSubmissionDeliverables((String) rows[i][3]);
            spec.setEnvironmentSetupInstructions((String) rows[i][4]);
            spec.setFinalSubmissionGuidelines((String) rows[i][5]);
            spec.setCreationUser((String) rows[i][6]);
            spec.setCreationTimestamp((Date) rows[i][7]);
            spec.setModificationUser((String) rows[i][8]);
            spec.setModificationTimestamp((Date) rows[i][9]);
            
            specs[i] = spec;
        }
        
        return specs;
	}
	
	/**
     * <p>
     * Gets the full list of contests.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *     - project and contest permissions are also fetched now.
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     * Updated for Direct Search Assembly
     *     - add payment information.
     * </p>
     * 
     * @return the full list of contests.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
	public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestData method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					QUERY_ALL_SIMPLE_PROJECT_CONTEST, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			 List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				// if have phase, use phase as stutus, otherwise use project status
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                    		    ret[i].setPperm((String) rows[i][17]);
                		}

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }
                //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                    //show spec review status only if the original project has not been started, so it is draft or scheduled
                    if (ret[i].getSname().equalsIgnoreCase("Scheduled") || ret[i].getSname().equalsIgnoreCase("Draft")) {
                      //it is number of reviewer assign to spec review project
                        int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                        //no reviwer assigned yet
                        if (reviewerAssignToSepcReviewProject == 0) {
                            ret[i].setSpecReviewStatus("Assigning Reviewer");
                        } else {
                            //21, checking whether in 'reviewing' status
                            if (rows[i][21] != null) {
                                ret[i].setSpecReviewStatus("Reviewing");
                            } else {
                              //check next value
                                int requiredItems = ((Long)rows[i][22]).intValue();
                                //no required items now
                                if (requiredItems == 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    // if not in final review, or in FF but no review item
                                    if (rows[i][25] == null) {
                                        ret[i].setSpecReviewStatus("Spec Review Failed");
                                    }
                                    else {
                                        int notFixedItems = ((Long)rows[i][23]).intValue();
                                        if (notFixedItems == 0) {
                                            ret[i].setSpecReviewStatus("Spec Review Passed");
                                        } else {
                                            int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                            if (notFixedFinalItems > 0) {
                                                ret[i].setSpecReviewStatus("Spec Review Passed");
                                            } else {
                                                ret[i].setSpecReviewStatus("Spec Review Failed");
                                            }
                                        }
                                    }
                                }
                            }                            
                        }
                    }
                }

                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

				if (ret[i].getCperm() != null || ret[i].getPperm() != null)
				{
					result.add(ret[i]);
				}

			}
			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestData method."));

			return Arrays.asList(ret);
		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}

	/**
     * <p>
     * Gets the list of contest for specified tc project id.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
     *     - project and contest permissions are also fetched now.
     * </p>
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * Updated for Direct Search Assembly
     *     - add payment information.
     *
     * @param pid the specified tc project id for which to get the list of contest.
     * @return the list of contest for specified tc project id.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
	public List<SimpleProjectContestData> getSimpleProjectContestData(long pid) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestData(pid) method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					QUERY_ALL_SIMPLE_PROJECT_CONTEST_BY_PID+pid, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();


			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                     		    ret[i].setPperm((String) rows[i][17]);
                		}

                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

                if (ret[i].getCperm() != null || ret[i].getPperm() != null)
                {
                    result.add(ret[i]);
                }

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }

                 //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                } else {
                    //there is no sepc review project, so don't need to set the spec review status
                    continue;
                }
                
               //show spec review status only if the original project has not been started, so it is draft or scheduled
                if (!ret[i].getSname().equalsIgnoreCase("Scheduled") && !ret[i].getSname().equalsIgnoreCase("Draft")) {
                    continue;
                }

                //it is number of reviewer assign to spec review project
                int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                //no reviwer assigned yet
                if (reviewerAssignToSepcReviewProject == 0) {
                    ret[i].setSpecReviewStatus("Assigning Reviewer");
                } else {
                    if (rows[i][21] != null) {
                        ret[i].setSpecReviewStatus("Reviewing");
                        continue;
                    }
                    //check next value
                    int requiredItems = ((Long)rows[i][22]).intValue();
                    //no required items now
                    if (requiredItems == 0) {
                        ret[i].setSpecReviewStatus("Spec Review Passed");
                    } else {
                        // if not in final review, or in FF but no review item
                        if (rows[i][25] == null) {
                            ret[i].setSpecReviewStatus("Spec Review Failed");
                        }
                        else {
                            int notFixedItems = ((Long)rows[i][23]).intValue();
                            if (notFixedItems == 0) {
                                ret[i].setSpecReviewStatus("Spec Review Passed");
                            } else {
                                int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                if (notFixedFinalItems > 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    ret[i].setSpecReviewStatus("Spec Review Failed");
                                }
                            }
                        }
                    }
                }
            }

			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestData(pid) method."));
			return Arrays.asList(ret);

		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}
	
	/**
	 * <p>
	 * Gets the list of contest for specified user.
	 * </p>
	 * 
	 * <p>
	 * Updated for Cockpit Release Assembly 3 [RS: 1.1.1]
	 *     - project and contest permissions are also fetched now.
	 * </p>
	 * 
	 * Updated for Spec Reviews Finishing Touch v1.0
     *  - Changed the way now spec status is queried.
	 * 
     * Updated for Cockpit Release Assembly 10:
     * 	   - add set SubmissionEndDate
     *
     * Updated for Cockpit Spec Review - Stage 2(version 1.2.4).
     *
     * Updated for Direct Search Assembly
     *     - add payment information.
     *
     * @param createdUser the specified user for which to get the list of contest.
     * @return the list of contest for specified user.
     * @throws PersistenceException exception is thrown when there is error retrieving the list from persistence.
     * @throws ParseException exception is thrown when there is error in parsing the results retrieved from persistence.
     */
    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String createdUser) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getSimpleProjectContestDataByUser method."));

		Connection conn = null;
		try {
			// create the connection
			conn = openConnection();


			String qstr = 
			" select p.project_id as contest_id, "
			+		" (select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph " 
			+ " where ph.phase_status_id = 2 and ph.project_id=p.project_id)) as current_phase, "
			+ "(select value from project_info where project_id = p.project_id and project_info_type_id =6) as contest_name, "
			+ "(select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=p.project_id) as start_date, "
			+ " (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=p.project_id) as end_date, "
			+ "  pcl.name as contest_type, psl.name as status, "
			+ " (select count(resource_id) from resource r where r.project_id = p.project_id and resource_role_id = 1) as num_reg, "
			+ " (select count (distinct resource_id) from upload u where u.project_id = p.project_id and upload_status_id = 1 and upload_type_id = 1) as num_sub, "
			// fixed forum post
            		+ " (select count(messageid) from jivecategory c, jiveforum f, jivemessage m, project_info pi "
            		+ "        where pi.project_info_type_id =4 and c.categoryid = pi.value and c.categoryid = f.categoryid and m.forumid = f.forumid "
            		+ "             and pi.project_id =  p.project_id) as num_for, "
			+ " tc_direct_project_id as project_id, tcd.name, tcd.description, tcd.user_id, "
			+ "  (select value from project_info where project_id = p.project_id and project_info_type_id =4) as forum_id, "
			+ "  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end "
			+ "   from contest_sale c where p.project_id = c.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, "


			+ " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
			+ " from user_permission_grant as upg  where resource_id=p.project_id and is_studio=0 and user_id = " + createdUser
			+ " ),0)) as cperm, "

            + " (select name from permission_type where permission_type_id= NVL( (select max( permission_type_id)  "
            + " from user_permission_grant as upg  where resource_id=tcd.project_id and user_id = " + createdUser
            + " ),0)) as pperm, "

            /* Added in cockpit R 10 */
            + " (select scheduled_end_time from project_phase ph "
            + " where ph.phase_type_id = 2 and ph.project_id=p.project_id) as submission_end_date,"
            /* R 10 end*/

            /* updated by Cockpit Spec Review - Stage 2  - start */
            /* sepc review project id*/
            + "(select source_project_id from linked_project_xref where dest_project_id = p.project_id and link_type_id = 3) as srprojectId,"
            /* sepc review user */
            + "(select count(*) from resource r, linked_project_xref linkp where linkp.dest_project_Id = p.project_id"
            + " and linkp.link_type_id = 3 and r.project_id = linkp.source_project_id and r.resource_role_id = 4) as srr,"
            /* reviewing status check */
            + "(select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
            + " where ph.phase_type_id = 4 and ph.phase_status_id = 2 and ph.project_id = linkp.source_project_id and linkp.link_type_id = 3"
            + " and linkp.dest_project_Id = p.project_id) as sprs,"
            /* sepc review result to find the 'required' fixed item. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                    where r.review_id = ri.review_id "
            + "                          and ri.review_item_id = ric.review_item_id and comment_type_id = 3 "
            + "                          and r.resource_id = re.resource_id "
            + "                          and linkp.dest_project_Id = p.project_id "
            + "                          and linkp.link_type_id = 3 and re.resource_role_id = 4 "
            + "                          and re.project_id = linkp.source_project_id ) as srResult,"
            /* sepc review result to find the final fix 'fixed'. */
            + "(select count(*) from review r , review_item ri, review_item_comment ric, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ric.extra_info, '')) != 'FIXED' "
            + "                      and ric.comment_type_id = 3 and r.resource_id = re.resource_id and ri.review_item_id = ric.review_item_id "
            + "                      and linkp.dest_project_Id = p.project_id "
            + "                      and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                      and re.project_id = linkp.source_project_id) as srfresult, "
            /* sepc review result to find the final fix 'fixed' in response. */
            + "(select count(*) from review r , review_comment ri, resource re, linked_project_xref linkp "
            + "                 where r.review_id = ri.review_id "
            + "                     and upper(nvl(ri.extra_info, '')) == 'APPROVED' "
            + "                     and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
            + "                     and linkp.dest_project_Id = p.project_id "
            + "                     and linkp.link_type_id = 3 and re.resource_role_id = 9 "
            + "                     and re.project_id = linkp.source_project_id) as srfrresult, "
            /* updated by Cockpit Spec Review - Stage 2  - end */

           // check if phase is FF open/close and there is final review
           + " (select min(ph.phase_type_id) from project_phase ph, linked_project_xref linkp "
           + "    where ph.phase_type_id = 10 and ph.phase_status_id in (2, 3) and ph.project_id = linkp.source_project_id "
           + "             and linkp.link_type_id = 3 and linkp.dest_project_Id = p.project_id "
           + "     and exists  "
           + "     (select * from review r , review_comment ri, resource re, linked_project_xref linkp "
           + "               where r.review_id = ri.review_id "
           + "                   and ri.comment_type_id = 10 and r.resource_id = re.resource_id "
           + "                   and linkp.dest_project_Id = p.project_id "
           + "                   and linkp.link_type_id = 3 and re.resource_role_id = 9 "
           + "                   and re.project_id = linkp.source_project_id)) as hassrfr, "

           // contest fee/ price sum
           + " (select sum(cast(nvl(value, '0') as DECIMAL(10,2))) from project_info where project_id = p.project_id  and project_info_type_id in (30,31,33,35,36,37,38,39)) as contest_fee "

            + " from project p, project_category_lu pcl, project_status_lu psl, tc_direct_project tcd "
            + " where p.project_category_id = pcl.project_category_id and p.project_status_id = psl.project_status_id and p.tc_direct_project_id = tcd.project_id "
            + " and p.project_status_id != 3 "
            + " and (p.create_user = " + createdUser + " OR exists "
            + "     (select user_id from user_permission_grant upg where upg.user_id = " + createdUser
            + "      and ((upg.resource_id = p.project_id and is_studio = 0) "
            + "        OR upg.resource_id = tcd.project_id))) "
            // dont show spec review project
            + " and p.project_category_id != 27 ";

			// get the project objects
			// find projects in the table.
			Object[][] rows = Helper.doQuery(conn,
					qstr, new Object[] {},
					this.QUERY_ALL_SIMPLE_PROJECT_CONTEST_COLUMN_TYPES);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

			SimpleProjectContestData[] ret = new SimpleProjectContestData[rows.length];
			 SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			 List<SimpleProjectContestData> result = new ArrayList<SimpleProjectContestData>();

			for(int i=0;i<rows.length;i++)
			{
				ret[i]=new SimpleProjectContestData();
				ret[i].setContestId((Long)rows[i][0]);
				/* [BUGR-2038]: See comments at the class level. Status is either 'Scheduled'
				  or 'Draft' or from the DB as done previously.*/

				// try to use phase if not null
				if (rows[i][1] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][1]).trim());
				}
				// else for active, use 'newstatus'
				else if (rows[i][15] != null && ((String)rows[i][6]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName()))
				{
					ret[i].setSname(((String)rows[i][15]).trim());
				}
				// use status
				else
				{
					ret[i].setSname(((String)rows[i][6]).trim());
				}
				
				ret[i].setCname((String)rows[i][2]);
				if (rows[i][3] != null)
				{
					ret[i].setStartDate(myFmt.parse(rows[i][3].toString()));
				}
				if (rows[i][4] != null)
				{
					ret[i].setEndDate(myFmt.parse(rows[i][4].toString()));
				}
				
				ret[i].setType((String)rows[i][5]);
				ret[i].setNum_reg(new Integer(((Long)rows[i][7]).intValue()));
				ret[i].setNum_sub(new Integer(((Long)rows[i][8]).intValue()));
				ret[i].setNum_for(new Integer(((Long)rows[i][9]).intValue()));
				ret[i].setProjectId((Long)rows[i][10]);
				ret[i].setPname((String)rows[i][11]);
				ret[i].setDescription((String)rows[i][12]);
				ret[i].setCreateUser((String)rows[i][13]);
				if (rows[i][14] != null)
				{
					ret[i].setForumId(new Integer(((Long)rows[i][14]).intValue()));
				}
				
				if (rows[i][16] != null) {
				    ret[i].setCperm((String) rows[i][16]);
				}
				
				if (rows[i][17] != null) {
                    		   ret[i].setPperm((String) rows[i][17]);
                		}


                // set contest fee/ price sum
                ret[i].setContestFee((Double)rows[i][26]);

                if (ret[i].getCperm() != null || ret[i].getPperm() != null)
                {
                    result.add(ret[i]);
                }

                if (rows[i][18] != null) {
                    ret[i].setSubmissionEndDate(myFmt.parse(rows[i][18].toString()));
                }

                //it is the srprojectId
                if (rows[i][19] != null) {
                    ret[i].setSpecReviewProjectId((Long)rows[i][19]);
                } else {
                    //there is no sepc review project, so don't need to set the spec review status
                    continue;
                }
                
               //show spec review status only if the original project has not been started, so it is draft or scheduled
                if (!ret[i].getSname().equalsIgnoreCase("Scheduled") && !ret[i].getSname().equalsIgnoreCase("Draft")) {
                    continue;
                }

                //it is number of reviewer assign to spec review project
                int reviewerAssignToSepcReviewProject = ((Long)rows[i][20]).intValue();
                //no reviwer assigned yet
                if (reviewerAssignToSepcReviewProject == 0) {
                    ret[i].setSpecReviewStatus("Assigning Reviewer");
                } else {
                    if (rows[i][21] != null) {
                        ret[i].setSpecReviewStatus("Reviewing");
                        continue;
                    }
                    //check next value
                    int requiredItems = ((Long)rows[i][22]).intValue();
                    //no required items now
                    if (requiredItems == 0) {
                        ret[i].setSpecReviewStatus("Spec Review Passed");
                    } else {
                        // if not in final review, or in FF but no review item
                        if (rows[i][25] == null) {
                            ret[i].setSpecReviewStatus("Spec Review Failed");
                        }
                        else {
                            int notFixedItems = ((Long)rows[i][23]).intValue();
                            if (notFixedItems == 0) {
                                ret[i].setSpecReviewStatus("Spec Review Passed");
                            } else {
                                int notFixedFinalItems = ((Long)rows[i][24]).intValue();
                                if (notFixedFinalItems > 0) {
                                    ret[i].setSpecReviewStatus("Spec Review Passed");
                                } else {
                                    ret[i].setSpecReviewStatus("Spec Review Failed");
                                }
                            }
                        }
                    }
                }
            }

			closeConnection(conn);
	        getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getSimpleProjectContestDataByUser method."));

			return result;
		} catch (PersistenceException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw e;
		} catch (ParseException e) {
			getLogger().log(
					Level.ERROR,
					new LogMessage(null, null,
							"Fails to retrieving all tc direct projects ", e));
			if (conn != null) {
				closeConnectionOnError(conn);
			}
			throw new PersistenceException("Fails to retrieve all tc direct projects", e);
		}

	}
	
	/**
     * <p>
     * Gets the list of project their read/write/full permissions.
     * </p>
     * 
     * <p>
     * Updated for Cockpit Release Assembly 3 [RS: 1.1.3]
     *      - Added check for is_studio=0 whenever user_permission_grant is joined with project table.
     * </p>
     * 
     * @param createdUser
     *            the specified user for which to get the permission
     * @return the list of project their read/write/full permissions.
     * 
     * @throws PersistenceException exception if error during retrieval from database.
     * 
     * @since Cockpit Project Admin Release Assembly v1.0
     */
    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(long createdUser)
            throws PersistenceException {

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            String qstr = "select project_id as contest_id, " +
            		" (select pinfo.value from project_info as pinfo where pinfo.project_id = c.project_id and pinfo.project_info_type_id = 6) as name, "
                    + " tc_direct_project_id, "
                    + " ( select name from tc_direct_project p where c.tc_direct_project_id = p.project_id) as pname, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id= "
                    + createdUser
                    + " and permission_type_id=1 ) as pread, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id=  "
                    + createdUser
                    + " and permission_type_id=2 ) as pwrite, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.tc_direct_project_id  and user_id=  "
                    + createdUser
                    + " and permission_type_id=3 ) as pfull, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=4 ) as cread, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=5 ) as cwrite, "
                    + " (select count( *)  from user_permission_grant as upg  where resource_id=c.project_id and is_studio=0 and user_id=  "
                    + createdUser
                    + " and permission_type_id=6 ) as cfull "
                    + " from project c  "
                    + " where not c.tc_direct_project_id is null ";

            Object[][] rows = Helper.doQuery(conn, qstr, new Object[] {},
                    this.QUERY_SIMPLE_PROJECT_PERMISSION_DATA_COLUMN_TYPES);

            List<SimpleProjectPermissionData> result = new ArrayList<SimpleProjectPermissionData>();

            for (int i = 0; i < rows.length; i++) {

                SimpleProjectPermissionData c = new SimpleProjectPermissionData();
                c.setStudio(false);
                Object[] os = rows[i];
                
                // skip records that doesn't have tc project names.
                if (os[3] == null || os[3].equals("")) {
                    continue;
                }

                if (os[0] != null)
                    c.setContestId((Long) os[0]);
                if (os[1] != null)
                    c.setCname(os[1].toString());
                if (os[2] != null)
                    c.setProjectId((Long) os[2]);
                if (os[3] != null)
                    c.setPname(os[3].toString());

                if (createdUser < 0) {
                    // admin
                    c.setPfull(1);
                    c.setCfull(1);
                    result.add(c);
                    continue;
                }

                int pp = 0;
                if (os[4] != null) {
                    c.setPread(((Long) os[4]).intValue());
                    pp++;
                }
                if (os[5] != null) {
                    c.setPwrite(((Long) os[5]).intValue());
                    pp++;
                }
                if (os[6] != null) {
                    c.setPfull(((Long) os[6]).intValue());
                    pp++;
                }
                int cp = 0;
                if (os[7] != null) {
                    c.setCread(((Long) os[7]).intValue());
                    cp++;
                }
                if (os[8] != null) {
                    c.setCwrite(((Long) os[8]).intValue());
                    cp++;
                }
                if (os[9] != null) {
                    c.setCfull(((Long) os[9]).intValue());
                    cp++;
                }
                if (pp > 0 || cp > 0) {
                    result.add(c);
                }
            }

			closeConnection(conn);
            return result;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving all tc direct projects ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        } 
    }


    private static boolean isSpecReviewSubmitter(int roleId, long projectCategoryId) {
        return roleId == SUBMITTER_ROLE_ID && projectCategoryId == ProjectCategory.PROJECT_CATEGORY_SPEC_REVIEW;
    }
    /**
     * 1. if there are client terms defined, insert these terms.
     * 2. if CCA, insert CCA terms
     * 3. insert standard terms if a role is not defined in 1.
     *
     * @param projectId the project id to associate
     * @param billingProjectId the client project id
     * @param projectCategoryId the project category
     * @param standardCCA true for cca
     * @throws PersistenceException if any error occurs
     */
    private void createRoleTermOfUse(long projectId, long billingProjectId, long projectCategoryId, boolean standardCCA,
            Connection conn) throws PersistenceException {        
        PreparedStatement preparedStatement = null;
        try {
            //hold the role ids that has client's terms of use
            Set<Integer> privateTermOfUseRoleIds = new HashSet<Integer>();
            // build the statement            
            Object[][] rows = Helper.doQuery(conn, SELCT_PRIVATE_CONTEST_TERMS,
                new Object[] {billingProjectId}, new DataType[] {Helper.LONG_TYPE, Helper.LONG_TYPE});
                
            //1. step 1, insert client defined terms
            preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
            preparedStatement.setLong(1, projectId);


            boolean hasClientTerm = false;
            if (rows.length > 0) {

                hasClientTerm = true;
                for (int i = 0; i < rows.length; i++) {
                    Object[] os = rows[i];
                    // if resource role id is 0 or null, insert for all
                    if (os[1] == null || os[1].toString().equals("") || os[1].toString().equals("0")) {
                        for (int roleId : ALL_ROLES_ID) { 
                            // no submitter for spec review
                            if (!isSpecReviewSubmitter(roleId, projectCategoryId)) {
                                preparedStatement.setInt(2, roleId);
                                preparedStatement.setObject(3, os[0]);
                                preparedStatement.execute();
                                privateTermOfUseRoleIds.add(roleId);
                            }
                        }
                    } else { // otherwise insert for specified role                        
                        int roleId = ((Long)os[1]).intValue();
                         // no submitter for spec review
                        if (!isSpecReviewSubmitter(roleId, projectCategoryId)) {
                            preparedStatement.setObject(2, os[1]);
                            preparedStatement.setObject(3, os[0]);
                            preparedStatement.execute();
                            privateTermOfUseRoleIds.add(roleId);
                        }
                    }
                }
            }
            
            //2. insert cca terms
            if (standardCCA) {
                preparedStatement = conn.prepareStatement(INSERT_PRIVATE_CONTEST_TERMS);
                preparedStatement.setLong(1, projectId);
                preparedStatement.setLong(3, STANDARD_CCA_TERMS_ID);
                for (int roleId : ALL_ROLES_ID) {
                    // no manager or observer for cca, for spec review, no submitter
                    if (roleId != MANAGER_ROLE_ID && roleId != OBSERVER_ROLE_ID  
                            && !isSpecReviewSubmitter(roleId, projectCategoryId)) {
                        preparedStatement.setInt(2, roleId);
                        preparedStatement.execute(); 
                    }
                }
            } 

            //3. insert stardard terms
            if (!hasClientTerm)
            {
                createProjectRoleTermsOfUse(projectId, MANAGER_ROLE_ID, MANAGER_TERMS_ID, conn);  
            }
            // get the instance of ConfigManager
            ConfigManager cm = ConfigManager.getInstance();
            // always insert standard
            long submitterTermsId =  Long.parseLong(Helper.getConfigurationParameterValue(cm, namespace,
                PUBLIC_SUBMITTER_TERMS_ID_PARAMETER,
                getLogger(), Long.toString(PUBLIC_SUBMITTER_TERMS_ID)));
            int submitterRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    SUBMITTER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(SUBMITTER_ROLE_ID)));
            long reviewerTermsId = Long.parseLong(Helper.getConfigurationParameterValue(cm, namespace,
                    PUBLIC_REVIEWER_TERMS_ID_PARAMETER, getLogger(), Long.toString(PUBLIC_REVIEWER_TERMS_ID)));

            // no submitter for spec review and no any client term
            if (projectCategoryId != ProjectCategory.PROJECT_CATEGORY_SPEC_REVIEW 
                    && !hasClientTerm) {
                createProjectRoleTermsOfUse(projectId, submitterRoleId, submitterTermsId, conn);
            }

            if (projectCategoryId == PROJECT_CATEGORY_DEVELOPMENT) {
                int accuracyReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        ACCURACY_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(ACCURACY_REVIEWER_ROLE_ID)));
                int failureReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        FAILURE_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(FAILURE_REVIEWER_ROLE_ID)));
                int stressReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        STRESS_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(STRESS_REVIEWER_ROLE_ID)));
                // if it's a development project there are several reviewer roles
                if (!hasClientTerm) {
                    createProjectRoleTermsOfUse(projectId, accuracyReviewerRoleId, reviewerTermsId, conn);
                    createProjectRoleTermsOfUse(projectId, failureReviewerRoleId, reviewerTermsId, conn);
                    createProjectRoleTermsOfUse(projectId, stressReviewerRoleId, reviewerTermsId, conn);
                }
               
            } else {
                int reviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                        REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(REVIEWER_ROLE_ID)));
                // if it's not development there is a single reviewer role
                if (!hasClientTerm) {
                    createProjectRoleTermsOfUse(projectId, reviewerRoleId, reviewerTermsId, conn);
                }
            }

            // also add terms for the rest of the reviewer roles
            int primaryScreenerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    PRIMARY_SCREENER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(PRIMARY_SCREENER_ROLE_ID)));
            int aggregatorRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    AGGREGATOR_ROLE_ID_PARAMETER, getLogger(), Integer.toString(AGGREGATOR_ROLE_ID)));
            int finalReviewerRoleId = Integer.parseInt(Helper.getConfigurationParameterValue(cm, namespace,
                    FINAL_REVIEWER_ROLE_ID_PARAMETER, getLogger(), Integer.toString(FINAL_REVIEWER_ROLE_ID)));
            if (!privateTermOfUseRoleIds.contains(primaryScreenerRoleId)) {
                
            }
            if (!hasClientTerm) {
                createProjectRoleTermsOfUse(projectId, primaryScreenerRoleId, reviewerTermsId, conn);
                createProjectRoleTermsOfUse(projectId, aggregatorRoleId, reviewerTermsId, conn);  
                createProjectRoleTermsOfUse(projectId, finalReviewerRoleId, reviewerTermsId, conn);
            }

        } catch (ConfigurationException e) {
            throw new PersistenceException(e.getMessage(), e);
        } catch (SQLException e) {
            getLogger().log(
                    Level.ERROR, new LogMessage(null, null,
                            "Fails to create the private project role terms of use ", e));      
            throw new PersistenceException(e.getMessage(), e);
                  
        } finally {
            Helper.closeStatement(preparedStatement);
        }
    }


    /**
     * Private helper method to generate default Project Role Terms of Use associations for a given project.
     * 
     * Updated for version 1.1.2
     *
     * @param projectId the project id for the associations
     * @param projectCategoryId the project category id of the provided project id
     * @param conn the database connection
     * @param standardCCA whether standardCCA is chosen or normal/public.
     * 
     * @throws ConfigManagerException if Configuration Manager fails to retrieve the configurations
     * @throws PersistenceException if any errors occur during EJB lookup
     */
    private void cleanProjectRoleTermsOfUseAssociations(long projectId, Connection conn)throws PersistenceException {

        PreparedStatement ps = null;
        try {
            StringBuffer query = new StringBuffer(1024);
            query.append("delete ");
            query.append("from project_role_terms_of_use_xref ");
            query.append("where project_id = ? ");

            // delete all
            ps = conn.prepareStatement(query.toString());
            ps.setLong(1, projectId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage(), e);
        } finally {
			Helper.closeStatement(ps);
        }
        
    }


    /**
     * This method will create a project role terms of use association.
     *
     * @param projectId the project id to associate
     * @param resourceRoleId the role id to associate
     * @param termsOfUseId the terms of use id to associate
     * @param dataSource the datasource.
     * @throws PersistenceException if any error occurs
     */
    private void createProjectRoleTermsOfUse(long projectId, int resourceRoleId, long termsOfUseId, Connection conn)
            throws PersistenceException {

        PreparedStatement ps = null;

        try
        {
            StringBuffer query = new StringBuffer(1024);
            query.append("INSERT ");
            query.append("INTO project_role_terms_of_use_xref (project_id, resource_role_id, terms_of_use_id) ");
            query.append("VALUES (?, ?, ?)");

            ps = conn.prepareStatement(query.toString());
            ps.setLong(1, projectId);
            ps.setInt(2, resourceRoleId);
            ps.setLong(3, termsOfUseId);

            int rc = ps.executeUpdate();
            if (rc != 1) {
                throw(new PersistenceException("Wrong number of rows inserted into " +
                        "'project_role_terms_of_use_xref'. Inserted " + rc + ", " +
                        "should have inserted 1."));
            }
        }
        catch (SQLException e)
        {
            throw(new PersistenceException(e.getMessage()));
        }
         finally {
            Helper.closeStatement(ps);
        }
        
    }
    
    
    /**
     * Gets the list of simple pipeline data for specified user id and between specified start and end date.
     * 
     * @param userId
     *            the user id.
     * @param startDate
     *            the start of date range within which pipeline data for contests need to be fetched.
     * @param endDate
     *            the end of date range within which pipeline data for contests need to be fetched.
     * @param overdueContests
     *            whether to include overdue contests or not.
     * @return the list of simple pipeline data for specified user id and between specified start and end date.
     * @throws PersistenceException
     *             if error during retrieval from database.
     * @since 1.1.1             
     */
    public List<SimplePipelineData> getSimplePipelineData(long userId, Date startDate, Date endDate,
            boolean overdueContests) throws PersistenceException {
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();
            
            Calendar start = new GregorianCalendar();
            start.setTime(startDate);
            start.set(Calendar.HOUR_OF_DAY, 0);
            start.set(Calendar.MINUTE, 0);
            start.set(Calendar.SECOND, 0);
            startDate = start.getTime();

            Calendar end = new GregorianCalendar();
            end.setTime(endDate);
            end.set(Calendar.HOUR_OF_DAY, 23);
            end.set(Calendar.MINUTE, 59);
            end.set(Calendar.SECOND, 59);
            endDate = end.getTime();


            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            StringBuffer sb = new StringBuffer();
            
            sb.append(" select NVL(pipe.id,-1) as id,  ");
            sb.append("     c.project_id as contest_id, ");
            sb.append("     (select value from project_info where project_info_type_id = 6 and project_id = c.project_id) as cname, ");
            sb.append("     (select value from project_info where project_info_type_id = 7 and project_id = c.project_id) as cversion, ");
            sb.append("     p.project_id as project_id, ");
            sb.append("      NVL(p.name, 'None') as pname, ");
            sb.append("     ptl.name as contest_type_desc, ");
            sb.append("     pcl.name as category, ");
            sb.append("     psl.name as sname, ");
            sb.append("     (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id) as start_time, ");
            sb.append("     (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=c.project_id) as end_time, ");
            sb.append("     (select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id and ph.phase_type_id=2) as duration_start_time, ");
            sb.append("     (select max(nvl(actual_end_time, scheduled_end_time)) from project_phase ph where ph.project_id=c.project_id and ph.phase_type_id=2) as duration_end_time, ");
            sb.append("     c.create_date as creation_time, ");
            sb.append("     c.modify_date as modification_time, ");
            sb.append("  ");
            sb.append("     NVL((select unique cl.name ");
            sb.append("     from project_info as pi ");
            sb.append("     left outer join tt_project as ttp  ");
            sb.append("     on pi.value::DECIMAL(10,2) = ttp.project_id ");
            sb.append("     left outer join tt_client_project cpx ");
            sb.append("     on ttp.project_id = cpx.project_id   ");
            sb.append("     left outer join tt_client as cl ");
            sb.append("     on cpx.client_id = cl.client_id ");
            sb.append("     where pi.project_id = c.project_id and pi.project_info_type_id = 32), 'One Off') as client_name, ");
            sb.append("  ");
            sb.append("     (select value::DECIMAL(10,2) from project_info where project_info_type_id = 33 and project_id = c.project_id) as review_payment, ");
            sb.append("  ");
            sb.append("     NVL(pipe.specification_review_payment,0) as specification_review_payment, ");
            sb.append("  ");
            sb.append("     (select value::DECIMAL(10,2) from project_info where project_info_type_id = 16 and project_id = c.project_id) as tot_prize, ");
            sb.append("  ");
            sb.append("     (select value::DECIMAL(10,2) from project_info where project_info_type_id = 30 and project_id = c.project_id) as dr, ");
            sb.append("  ");
            sb.append("     (select value::DECIMAL(10,2) from project_info where project_info_type_id = 31 and project_id = c.project_id) as contest_fee, ");
            sb.append("  ");
 // not used and cause performance issue
/*            sb.append("     ccat.short_desc as short_desc, ");
            sb.append("  ");
            sb.append("     ccat.description as long_desc, ");
            sb.append("  ");
            sb.append("     (select value from project_info where project_info_type_id = 14 and project_id = c.project_id) as eligibility, ");
            sb.append("  "); */
         /*   sb.append("     (select max(ri.value) ");
            sb.append("     from resource as res ");
            sb.append("     join resource_role_lu as res_role ");
            sb.append("         on res.resource_role_id = res_role.resource_role_id ");
            sb.append("         and res.project_id = c.project_id ");
            sb.append("         and res_role.name = 'Manager' ");
            sb.append("     join resource_info ri ");
            sb.append("          on (res.resource_id = ri.resource_id and ri.resource_info_type_id = 2) ");   // get handle
            sb.append("      where res.project_id = c.project_id and ri.value != 'Applications' and ri.value != 'Components') ");
            sb.append("                 as manager, ");
            sb.append("  "); */
            // for now use creator as manager
            sb.append("         (select u.handle from project pp, user u ");
            sb.append("         where pp.create_user = u.user_id ");
            sb.append("         and pp.project_id = c.project_id) as manager, ");
            sb.append("  "); 
            sb.append("     NVL((select u.handle from spec_review sr, spec_review_reviewer_xref srr, user u ");
            sb.append("     where sr.spec_review_id = srr.spec_review_id and srr.review_user_id = u.user_id ");
            sb.append("  and sr.contest_id = c.project_id) , 'Reviewer') as reviewer, ");
            sb.append("  ");
            sb.append("         ('Architect') as architect, ");
            sb.append("  ");
            sb.append("         ('Salesperson') as sales_person, ");
            sb.append("      ");
            sb.append("     NVL(pipe.client_approval,0) as client_approval, ");
            sb.append("     NVL(pipe.pricing_approval,0) as pricing_approval, ");
            sb.append("     NVL(pipe.has_wiki_specification,0) as has_wiki_specification, ");
            sb.append("     NVL(pipe.passed_spec_review,0) as passed_spec_review, ");
            sb.append("     NVL(pipe.has_dependent_competitions,0) as has_dependent_competitions, ");
            sb.append("     NVL(pipe.was_reposted,0) as was_reposted, ");
            sb.append("     NVL(pipe.notes,'') as notes, ");
            sb.append("     (select name  ");
            sb.append("         from permission_type  ");
            sb.append("         where permission_type_id= NVL( (select max( permission_type_id)  ");
            sb.append("                         from user_permission_grant as upg   ");
            sb.append("                         where resource_id=p.project_id ");
            sb.append("                         and upg.user_id = ").append(userId).append("), ");
            sb.append("             case when exists (select u.user_id ");
            sb.append("                     from user as u ");
            sb.append("                     join user_role_xref as uref ");
            sb.append("                     on u.user_id = ").append(userId).append(" ");
            sb.append("                     and u.user_id = uref.login_id ");
            sb.append("                     join security_roles as sr ");
            sb.append("                     on uref.role_id = sr.role_id ");
            sb.append("                     and sr.description in ('Cockpit Administrator','Admin Super Role')) then 3 else 0 end)) as pperm, ");
            sb.append("     (select name  ");
            sb.append("         from permission_type  ");
            sb.append("         where permission_type_id = NVL( (select max( permission_type_id)  ");
            sb.append("                         from user_permission_grant as upg   ");
            sb.append("                         where resource_id=c.project_id   ");
            sb.append("                         and is_studio=0 ");
            sb.append("                         and upg.user_id = ").append(userId).append("),0)) as cperm,   ");
            sb.append(" ( select ptl.name from phase_type_lu ptl where phase_type_id = (select min(phase_type_id) from project_phase ph ");
			sb.append("    where ph.phase_status_id = 2 and ph.project_id=c.project_id)) as current_phase,  ");
            sb.append("  (select case when(count(*)>=1) then 'Scheduled' when(count(*)=0) then 'Draft' end  ");
	        sb.append("     from contest_sale cs where c.project_id = cs.contest_id and upper(psl.name)='ACTIVE' ) as newstatus, ");
            sb.append("  NVL((select unique ttp.name ");
            sb.append("     from project_info as pi ");
            sb.append("     left outer join tt_project as ttp  ");
            sb.append("     on pi.value::DECIMAL(10,2) = ttp.project_id ");
            sb.append("     left outer join tt_client_project cpx ");
            sb.append("     on ttp.project_id = cpx.project_id   ");
            sb.append("     left outer join tt_client as cl ");
            sb.append("     on cpx.client_id = cl.client_id ");
            sb.append("     where pi.project_id = c.project_id and pi.project_info_type_id = 32), '') as cpname, ");	
            sb.append("  (select 'Repost' from linked_project_xref where link_type_id = 5 and source_project_id = c.project_id) as repost ");
            sb.append(" from project as c ");
            sb.append(" join project_info as piccat ");
            sb.append("     on c.project_id = piccat.project_id ");
            sb.append("     and piccat.project_info_type_id = 2 ");
            sb.append(" join comp_catalog as ccat ");
            sb.append("     on piccat.value = ccat.component_id ");
            sb.append(" join project_category_lu as pcl ");
            sb.append("     on c.project_category_id = pcl.project_category_id ");
            sb.append(" join project_type_lu as ptl ");
            sb.append("     on pcl.project_type_id = ptl.project_type_id ");
            sb.append(" join project_status_lu as psl ");
            sb.append("     on c.project_status_id = psl.project_status_id ");
            sb.append(" left outer join tc_direct_project as p ");
            sb.append("     on c.tc_direct_project_id = p.project_id ");
            sb.append(" left outer join software_competition_pipeline_info as pipe ");
            sb.append("     on pipe.competition_id = c.project_id ");
            sb.append("     and pipe.component_id = ccat.component_id ");
            sb.append("  ");
            sb.append(" where (exists (select u.user_id ");
            sb.append("     from user as u ");
            sb.append("     join user_role_xref as uref ");
            sb.append("     on u.user_id = ").append(userId).append(" ");
            sb.append("     and u.user_id = uref.login_id ");
            sb.append("     join security_roles as sr ");
            sb.append("     on uref.role_id = sr.role_id ");
            sb.append("     and sr.description in ('Cockpit Administrator','Admin Super Role','TC Staff')) ");
            sb.append(" OR ");
            sb.append(" NVL( (select max( permission_type_id)  ");
            sb.append("     from user_permission_grant as upg   ");
            sb.append("     where resource_id=p.project_id and permission_type_id >= 2 ");
            sb.append("     and upg.user_id = ").append(userId).append("),0) > 0 ");
            sb.append(" OR ");
            sb.append(" NVL( (select max( permission_type_id)  ");
            sb.append("     from user_permission_grant as upg   ");
            sb.append("     where resource_id=c.project_id and permission_type_id >= 5  ");
            sb.append("     and is_studio=0 ");
            sb.append("     and upg.user_id = ").append(userId).append("),0) > 0 ");
            sb.append(" OR ");
            sb.append(" exists (select cp.contest_id ");
            sb.append("     from contest_sale as cp ");
            sb.append("     join tt_project as ttp ");
            sb.append("     on cp.sale_reference_id = ttp.po_box_number ");
            sb.append("     and cp.sale_type_id = 2 ");
            sb.append("     and cp.contest_id = c.project_id ");
             sb.append("        and ttp.project_id in ( ");
            sb.append("             SELECT distinct project_id FROM tt_project_worker ttw, tt_user_account u  ");
            sb.append("                 WHERE ttw.user_account_id = u.user_account_id and ");
            sb.append("                                           u.user_name = (select handle from user where user_id = ").append(userId).append(") ");
            sb.append("             union  ");
            sb.append("             SELECT distinct project_id FROM tt_project_manager ttm, tt_user_account u  ");
			 sb.append("                 WHERE ttm.user_account_id = u.user_account_id and ");
            sb.append("                                           u.user_name = (select handle from user where user_id = ").append(userId).append(") ");
            sb.append("        ) )  ");
            sb.append(" ) ");
 /*           // exclude contests that has eligibility
            sb.append(" AND NOT EXISTS (SELECT 'has_eligibility_constraints' FROM contest_eligibility ce  ");
            sb.append("           WHERE ce.is_studio = 0 AND ce.contest_id = c.project_id) "); */
            sb.append(" AND ");
            // not show inactive or deleted
            sb.append(" (c.project_status_id != 2 AND c.project_status_id != 3)  ");
            // dont show spec review project
            sb.append(" and c.project_category_id != 27 ");
            sb.append(" AND ");
            sb.append(" ( ");
            sb.append(" ((select min(nvl(actual_start_time, scheduled_start_time)) from project_phase ph where ph.project_id=c.project_id) BETWEEN to_date('")
                    .append(formatter.format(startDate))
                    .append("','%Y-%m-%d %H:%M:%S') AND to_date('")
                    .append(formatter.format(endDate))
                    .append("','%Y-%m-%d %H:%M:%S')) ");

            sb.append(" ) ");


            sb.append(" order by start_time ");

            Object[][] rows = Helper.doQuery(conn, sb.toString(), new Object[] {},
                    QUERY_SIMPLE_PIPELINE_DATA_COLUMN_TYPES);

            SimpleDateFormat myFmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

            List<SimplePipelineData> result = new ArrayList<SimplePipelineData>();

            for (int i = 0; i < rows.length; i++) {

                SimplePipelineData c = new SimplePipelineData();
                Object[] os = rows[i];

                if (os[0] != null)
                    c.setPipelineInfoId((Long) os[0]);
                if (os[1] != null)
                    c.setContestId((Long) os[1]);
                if (os[2] != null)
                    c.setCname(os[2].toString());
                if (os[3] != null)
                    c.setCversion(os[3].toString());
                if (os[4] != null)
                    c.setProjectId((Long) os[4]);
                if (os[5] != null)
                    c.setPname(os[5].toString());
                if (os[6] != null)
                    c.setContestType(os[6].toString());
                if (os[7] != null)
                    c.setContestCategory(os[7].toString());

                

          /*      if (os[8] != null)
                    c.setSname(os[8].toString());  */
                if (os[9] != null)
                    c.setStartDate(myFmt.parse(os[9].toString()));
                if (os[10] != null)
                    c.setEndDate(myFmt.parse(os[10].toString()));
                if (os[11] != null)
                    c.setDurationStartTime(myFmt.parse(os[11].toString()));
                if (os[12] != null)
                    c.setDurationEndTime(myFmt.parse(os[12].toString()));
                if (os[13] != null)
                    c.setCreateTime(myFmt.parse(os[13].toString()));
                if (os[14] != null)
                    c.setModifyTime(myFmt.parse(os[14].toString()));
                if (os[15] != null)
                    c.setClientName(os[15].toString());
                if (os[16] != null)
                    c.setReviewPayment((Double)os[16]);
                if (os[17] != null)
                    c.setSpecReviewPayment((Double)os[17]);
                if (os[18] != null)
                    c.setTotalPrize((Double)os[18]);
                if (os[19] != null)
                    c.setDr((Double)os[19]);
                if (os[20] != null)
                    c.setContestFee((Double)os[20]);
    /*            if (os[21] != null)
                    c.setShortDesc(os[21].toString());
                if (os[22] != null)
                    c.setLongDesc(os[22].toString());
                if (os[23] != null)
                    c.setEligibility(os[23].toString()); */
                if (os[21] != null)
                    c.setManager(os[21].toString());
                if (os[22] != null)
                    c.setReviewer(os[22].toString());
                if (os[23] != null)
                    c.setArchitect(os[23].toString());
                if (os[24] != null)
                    c.setSalesPerson(os[24].toString());
                if (os[25] != null)
                    c.setClientApproval((Long)os[25] == 1 ? true : false);
                if (os[26] != null)
                    c.setPricingApproval((Long)os[26] == 1 ? true : false);
                if (os[27] != null)
                    c.setHasWikiSpecification((Long)os[27] == 1 ? true : false);
                if (os[28] != null)
                    c.setPassedSpecReview((Long)os[28] == 1 ? true : false);
                if (os[29] != null)
                    c.setHasDependentCompetitions((Long)os[29] == 1 ? true : false);
                if (os[30] != null)
                    c.setWasReposted((Long)os[30] == 1 ? true : false);
                if (os[31] != null)
                    c.setNotes(os[31].toString());
                if (os[32] != null)
                    c.setPperm(os[32].toString());
                if (os[33] != null)
                    c.setCperm(os[33].toString());
                
                
				if (rows[i][34] != null) {
				 // any contest that has an open phase in Online Review
					c.setSname("Active");
				} else if (rows[i][35] != null && ((String)rows[i][8]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {
				    //scheduled or draft
					c.setSname(((String)rows[i][35]).trim());
				} else if(!((String)rows[i][8]).equalsIgnoreCase(ProjectStatus.ACTIVE.getName())) {

                    if (((String)rows[i][8]).equalsIgnoreCase(ProjectStatus.CANCELLED_CLIENT_REQUEST.getName()) 
                          || ((String)rows[i][8]).equalsIgnoreCase(ProjectStatus.CANCELLED_REQUIREMENTS_INFEASIBLE.getName()))
                    {
                        c.setSname("Cancelled");
                    }
					else 
                    {
                        c.setSname("Completed");
                    }
				}

                if (os[36] != null)
                    c.setCpname(os[36].toString());

                if (os[37] != null)
                    c.setWasReposted(Boolean.TRUE);

                
                result.add(c);
                
                
                
            }

            closeConnection(conn);
            return result;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving simple pipeline data.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
        catch (ParseException e) {
			getLogger().log(Level.ERROR, new LogMessage(null, null, "Fails to retrieving simple pipeline data.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw new PersistenceException("Fails to retrieving simple pipeline data.", e);
		}
    }

    /**
     * Retrieves a list of capacity data (date, number of scheduled contests) for the given contest type starting
     * from tomorrow.
     *
     * @param contestType the contest type
     *
     * @return the list of capacity data
     *
     * @throws PersistenceException if any error occurs during retrieval of information.
     *
     * @since 1.2
     */
    public List<SoftwareCapacityData> getCapacity(int contestType) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getCapacity(" + contestType + ") method."));

        Connection conn = null;

        StringBuffer queryBuffer = new StringBuffer();

        queryBuffer.append(" select");
        queryBuffer.append(" (select min(date(nvl(actual_start_time, scheduled_start_time))) ");
        queryBuffer.append(" from project_phase ph where ph.project_id=p.project_id) as start_date,");
        queryBuffer.append(" p.project_id");
        queryBuffer.append(" from project p, contest_sale c");
        queryBuffer.append(" where p.project_category_id = ").append(contestType);
        queryBuffer.append(" and p.project_status_id = ").append(ACTIVE_PROJECT_STATUS_ID);
        queryBuffer.append(" and p.project_id = c.contest_id");
        queryBuffer.append(" and (select min(date(nvl(actual_start_time, scheduled_start_time))) ");
        queryBuffer.append(" from project_phase ph where ph.project_id=p.project_id) > date(current)");
        queryBuffer.append(" AND NOT EXISTS (SELECT 'has_eligibility_constraints' FROM contest_eligibility ce ");
        queryBuffer.append("           WHERE ce.is_studio = 0 AND ce.contest_id = p.project_id) ");
        queryBuffer.append(" order by 1");

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                    queryBuffer.toString(), new Object[] {},
                    new DataType[] { Helper.DATE_TYPE, Helper.LONG_TYPE } );

            List<SoftwareCapacityData> capacityList = new ArrayList<SoftwareCapacityData>();
            getLogger().log(Level.INFO, new LogMessage(null,null,"Found "+rows.length + " records"));

            SoftwareCapacityData cap = null;
            String previous = "";
            for(int i=0;i<rows.length;i++)
            {
                Object[] os = rows[i];

                // new date
                if (!previous.equals(os[0].toString()))
                {

                    previous = os[0].toString();
                    cap = new SoftwareCapacityData();
                    cap.setDate((Date) os[0]);
                    cap.setNumScheduledContests(1);
                    cap.getContests().add((((Long)(os[1])).intValue()));
                    capacityList.add(cap);
                    
                }
                else
                {
                    cap.setNumScheduledContests(cap.getNumScheduledContests() + 1);
                    cap.getContests().add((((Long)(os[1])).intValue()));
                }
                
            }

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getCapacity method."));
            getLogger().log(Level.INFO, new LogMessage(null,null,"Got "+capacityList.size() + " capacities"));
            return capacityList;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null,
                            "Fails to retrieving capacity information ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

   /**
     * <p>
     * Retrieves an array of project instance from the persistence whose
	 * create date is within current - days 
     * </p>
     * @param days last 'days' 
     * @param conn the database connection
     * @return An array of project instances.
     * @throws PersistenceException if error occurred while accessing the
     *             database.
     */
    private Project[] getProjectsByCreateDate(int days, Connection conn)
        throws PersistenceException {

        // find projects in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_PROJECTS_BY_CREATE_DATE_SQL + days,
                new Object[] {}, QUERY_PROJECTS_BY_CREATE_DATE_COLUMN_TYPES);

        // create the Project array.
        Project[] projects = new Project[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create the ProjectStatus object
            ProjectStatus status = new ProjectStatus(((Long) row[1])
                    .longValue(), (String) row[2]);

            // create the ProjectType object
            ProjectType type = new ProjectType(((Long) row[5]).longValue(),
                    (String) row[6]);

            // create the ProjectCategory object
            ProjectCategory category = new ProjectCategory(((Long) row[3])
                    .longValue(), (String) row[4], type);
            category.setDescription((String) row[11]);
            // create a new instance of ProjectType class
            projects[i] = new Project(((Long) row[0]).longValue(), category,
                    status);

            // assign the audit information
            projects[i].setCreationUser((String) row[7]);
            projects[i].setCreationTimestamp((Date) row[8]);
            projects[i].setModificationUser((String) row[9]);
            projects[i].setModificationTimestamp((Date) row[10]);

			// here we only get project name and project version
			projects[i].setProperty("Project Name", (String) row[12]);
			projects[i].setProperty("Project Version", (String) row[13]);
        }

       
        return projects;
    }


     /**
     * Get all design components.
     *
     * @param userId
     *            The user id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public List<DesignComponents> getDesignComponents(long userId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDesignComponents() method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                    QUERY_ALL_DESIGN_COMPONENTS, new Object[] {userId, userId},
                    QUERY_ALL_DESIGN_COMPONENTS_COLUMN_TYPES );

            List<DesignComponents> designList = new ArrayList<DesignComponents>();
            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));

            DesignComponents designComponents = null;

            for(int i=0;i<rows.length;i++)
            {   
		        designComponents = new DesignComponents();
                {
                    designComponents.setProjectId((Long)rows[i][0]);
                    designComponents.setText(rows[i][1].toString());
                    designComponents.setCperm(rows[i][2] == null ? null : rows[i][2].toString());
                    designComponents.setPperm(rows[i][3] == null ? null : rows[i][3].toString());
                }

                if (designComponents.getCperm() != null || designComponents.getPperm() != null)
                {
                    designList.add(designComponents);
                }
                
            }

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignComponents method."));
            return designList;
        } catch (PersistenceException e) {
            getLogger().log(
                    Level.ERROR,
                    new LogMessage(null, null,
                            "Fails to retrieving design components information ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

     /**
     * Get corresponding development contest's id for the design contest.
     *
     * @param contestId
     *            The design contestId id
     * @throws PersistenceException
     *             if any other error occurs
     * @since 1.2.2
     */
    public long getDevelopmentContestId(long contestId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDevelopmentContestId method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                FIND_CORRESPONDING_DEVELOPMENT_CONTEST, 
                new Object[] {contestId, contestId, contestId, contestId},
                new DataType[] {Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));
            long rst = 0;
            if (rows.length != 0) {
                rst = ((Long)rows[0][0]).longValue();
            }
            getLogger().log(Level.INFO, new LogMessage(null, null, "The id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignComponents method."));
            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                "Fails to retrieving development contest's id ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }


    /**
     * Get corresponding design contest's id for the dev contest.
     *
     * @param contestId
     *            The dev contestId id
     * @throws PersistenceException
     *             if any other error occurs
     */
    public long getDesignContestId(long contestId) throws PersistenceException {

        getLogger().log(Level.INFO, new LogMessage(null,null,"Enter getDesignContestId() method."));

        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            // get the project objects
            Object[][] rows = Helper.doQuery(conn,
                FIND_CORRESPONDING_DESIGN_CONTEST, 
                new Object[] {contestId, contestId, contestId, contestId},
                new DataType[] {Helper.LONG_TYPE});

            getLogger().log(Level.INFO, new LogMessage(null, null, "Found "+rows.length + " records"));
            long rst = 0;
            if (rows.length != 0) {
                rst = ((Long)rows[0][0]).longValue();
            }
            getLogger().log(Level.INFO, new LogMessage(null, null, "The id is: " + rst));

            closeConnection(conn);
            getLogger().log(Level.INFO, new LogMessage(null,null,"Exit getDesignContestId method."));
            return rst;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null,
                "Fails to retrieving development contest's id ", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }
    
    

    /**
     * check contest permission, check if a user has permission (read or write) on a contest
     *
     * @param contestId the contest id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkContestPermission(long contestId, boolean readonly, long userId)  throws PersistenceException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where (resource_id = ").append(contestId).append(" and is_studio = 0 and permission_type_id >= ");
            queryBuffer.append(readonly ? CONTEST_READ_PERMISSION_ID : CONTEST_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")"); 
            queryBuffer.append(" or ");
            queryBuffer.append(" (resource_id = (select tc_direct_project_id from project where project_id = ").append(contestId).append(") and permission_type_id >= ");
            queryBuffer.append(readonly ? PROJECT_READ_PERMISSION_ID : PROJECT_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")"); 

            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query ");
        } catch (PersistenceException e) {
           
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }


    /**
     * check contest permission, check if a user has permission (read or write) on a project
     *
     * @param projectId the tc direct project id
     * @param readonly check read or write permission
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean checkProjectPermission(long tcprojectId, boolean readonly, long userId) throws PersistenceException
    {
        
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where  resource_id =  ").append(tcprojectId).append(" and permission_type_id >= ");
            queryBuffer.append(readonly ? PROJECT_READ_PERMISSION_ID : PROJECT_WRITE_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(" "); 
        
            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query " + e, e);
        } catch (PersistenceException e) {
            
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }



    /**
     * <p>
     * get project ids by tc direct id
     * </p>
     *
     * @tcDirectId tc direct project id
     *
     * @return list of project ids
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public List<Long> getProjectIdByTcDirectProject(long tcprojectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_PROJECT_ID_BY_TC_DIRECT, new Object[] {tcprojectId},
            		QUERY_PROJECT_ID_BY_TC_DIRECT_COLUMN_TYPES);

            List<Long> ret = new ArrayList<Long>();

            for (int i = 0; i < rows.length; i++) {

                ret.add((Long) rows[i][0]);
            }
            return ret;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }


    /**
     * <p>
     * get tc direct project id by project id
     * </p>
     *
     * @projectId project id
     *
     * @return tc direct project id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getTcDirectProject(long projectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID, new Object[] {projectId},
            		QUERY_TC_DIRECT_PROJECT_BY_PROJECT_ID_COLUMN_TYPES);

            if (rows.length > 0 && rows[0][0] != null)
            {
                return ((Long) rows[0][0]).longValue();
            }
            
            return 0;

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }
    
    

     /**
     * <p>
     * get forum id by project id
     * </p>
     *
     * @projectId project id
     *
     * @return forum id
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public long getForumId(long projectId) throws PersistenceException 
    {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_FORUM_ID, new Object[] {projectId},
            		QUERY_FORUM_ID_COLUMN_TYPES);


            if (rows.length > 0 && rows[0][0] != null)
            {
                return ((Long) rows[0][0]).longValue();
            }
            
            return 0;
        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to retrieve project ids by tc direct" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }


    /**
     * check if user has contest permission, it checks contest permission only (not project permission)
     *
     * @param contestId the contest id
     * @param userId user id
     *
     * @return true/false
     * @throws  PersistenceException
     *
     */
    public boolean hasContestPermission(long contestId, long userId)  throws PersistenceException
    {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try
        {
            conn = openConnection();
            
            StringBuffer queryBuffer = new StringBuffer();
            queryBuffer.append("select 'has permssion' from user_permission_grant ");
            queryBuffer.append(" where (resource_id = ").append(contestId).append(" and is_studio = 0 and permission_type_id >= ");
            queryBuffer.append(CONTEST_READ_PERMISSION_ID);  
            queryBuffer.append(" and user_id = ").append(userId).append(")");  

            preparedStatement = conn.prepareStatement(queryBuffer.toString());
         
            // execute the query and build the result into a list
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                return true;
            }

            return false;
        
        } catch (SQLException e) {
            throw new PersistenceException(
                    "Error occurs while executing query ");
        } catch (PersistenceException e) {
           
            throw new PersistenceException("There are errors while retrieving the information.", e);
        }
        finally {
            Helper.closeResultSet(resultSet);
            Helper.closeStatement(preparedStatement);
            if (conn != null) {
                closeConnectionOnError(conn);
            }
        }

    }


    /**
     * <p>
     * check if it is dev only 
     * </p>
     *
     * @projectId  project id
     *
     * @return boolean
     *
     * @throws PersistenceException if any other error occurs.
     *
     */
    public boolean isDevOnly(long projectId) throws PersistenceException {
        Connection conn = null;
        try {
            // create the connection
            conn = openConnection();

            // get the contest sale
            Object[][] rows = Helper.doQuery(conn,
            		QUERY_IS_DEV_ONLY_SQL, new Object[] {projectId},
            		QUERY_IS_DEV_ONLY_SQL_COLUMN_TYPES);

            if (rows.length > 0)
            {
                return false;
            }

            return true;

        } catch (PersistenceException e) {
        	getLogger().log(Level.ERROR, new LogMessage(null, null,
                  "Fails to check isDevOnly" , e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
         finally {

            closeConnection(conn);
        }
    }

     /**
     * Gets an array of all billing project configuration types in the persistence. The billing
     * project configuration types are stored in 'client_billing_config_type_lu' table.
     * 
     * @return An array of all BillingProjectConfigTypes in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfigType[] getAllBillingProjectConfigTypes() throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllBillingProjectConfigTypes method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            BillingProjectConfigType[] billingProjectConfigTypes = getAllBillingProjectConfigTypes(conn);
            closeConnection(conn);
            return billingProjectConfigTypes;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllBillingProjectConfigTypes.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all billing project configuration types in the persistence. The project
     * property types are stored in 'client_billing_config_type_lu' table.
     * 
     * @param conn the database connection
     * @return An array of all BillingProjectConfigTypes in the persistence.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    private BillingProjectConfigType[] getAllBillingProjectConfigTypes(Connection conn) throws PersistenceException {

        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_SQL, new Object[] {},
                QUERY_ALL_BILLING_PROJECT_CONFIG_TYPES_COLUMN_TYPES);

        BillingProjectConfigType[] billingProjectConfigTypes = new BillingProjectConfigType[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            // create a new instance of BillingProjectConfigType class
            billingProjectConfigTypes[i] = new BillingProjectConfigType(((Long) row[0]).longValue(), (String) row[1],
                    (String) row[2]);
        }

        return billingProjectConfigTypes;
    }

    /**
     * Gets an array of all the billing project configurations in the persistence. The billing
     * project configurations are stored in 'client_billing_config' table.
     * 
     * 
     * @param billingProjectId the id of the billing project.
     * @return an array of all BillingProjectConfiguration.
     * @throws PersistenceException if error occurred while accessing the database.
     */
    public BillingProjectConfiguration[] getAllBillingProjectConfigs(long billingProjectId) throws PersistenceException {
        getLogger().log(Level.INFO, new LogMessage(null, null, "Enter getAllBillingProjectConfigs method."));
        Connection conn = null;

        try {
            // create the connection
            conn = openConnection();

            BillingProjectConfiguration[] billingProjectConfigs = getAllBillingProjectConfigs(conn, billingProjectId);
            closeConnection(conn);
            return billingProjectConfigs;
        } catch (PersistenceException e) {
            getLogger().log(Level.ERROR, new LogMessage(null, null, "Fail to getAllBillingProjectConfigs.", e));
            if (conn != null) {
                closeConnectionOnError(conn);
            }
            throw e;
        }
    }

    /**
     * Gets an array of all the billing project configurations in the persistence. The billing
     * project configurations are stored in 'client_billing_config' table.
     * 
     * @param conn the database connection.
     * @param billingProjectId the ID of the billing project.
     * @return an array of retrieved BillingProjectConfigurations
     * @throws PersistenceException if any error occurred while accessing database.
     */
    private BillingProjectConfiguration[] getAllBillingProjectConfigs(Connection conn, long billingProjectId)
            throws PersistenceException {
        // find all billing project configs in the table.
        Object[][] rows = Helper.doQuery(conn, QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL,
                new Object[] { billingProjectId }, QUERY_ALL_BILLING_PROJECT_CONFIGS_SQL_COLUMN_TYPES);

        BillingProjectConfigType[] allTypes = getAllBillingProjectConfigTypes();

        BillingProjectConfiguration[] result = new BillingProjectConfiguration[rows.length];

        for (int i = 0; i < rows.length; ++i) {
            Object[] row = rows[i];

            long configTypeId = ((Long) row[0]).longValue();

            // create a new instance of ProjectPropertyType class
            result[i] = new BillingProjectConfiguration(getBillingProjectConfigType(allTypes, configTypeId),
                    (String) row[1]);
        }

        return result;
    }

    /**
     * Utility method to retrieve a BillingProjectConfigType from all the config types with a ID.
     * 
     * @param allTypes the array which contains all the config types.
     * @param typeId the id of the billing project config type.
     * @return the retrieved BillingProjectConfigType instance.
     */
    private BillingProjectConfigType getBillingProjectConfigType(BillingProjectConfigType[] allTypes, long typeId) {
        for (BillingProjectConfigType type : allTypes) {
            if (type.getId() == typeId) {
                return type;
            }
        }

        return null;
    }
}
