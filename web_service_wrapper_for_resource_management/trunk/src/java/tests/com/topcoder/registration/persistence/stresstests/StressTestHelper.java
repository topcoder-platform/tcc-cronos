/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.registration.persistence.stresstests;

import java.util.Date;

import com.topcoder.registration.entities.AuditableRegistrationEntity;
import com.topcoder.registration.entities.CompositeRole;
import com.topcoder.registration.entities.Contest;
import com.topcoder.registration.entities.ContestRole;
import com.topcoder.registration.entities.ContestType;
import com.topcoder.registration.entities.Registrant;
import com.topcoder.registration.entities.Role;
import com.topcoder.registration.entities.User;

/**
 * The helper class for failure test.
 *
 * @author telly12
 * @version 1.0
 */
public class StressTestHelper {
    /**
     * <p>
     * The id.
     * </p>
     */
    private static long id = 1;
    /**
     * <p>
     * Create a new instance of <code>User</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>User</code>.
     */
    static User createUser(long... ids) {
        User user = new User();
        setAuditProperties(user);
        user.setHandle("member" + id);
        user.setEmail("email" + id + "@topcoder.com");
        user.setSuspended(id % 2 == 0);
        if (ids != null && ids.length > 0) {
            user.setId(ids[0]);
        }
        return user;
    }

    /**
     * <p>
     * Create a new instance of <code>Role</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>Role</code>.
     */
    static Role createRole(long... ids) {
        Role role = new Role();
        setAuditProperties(role);
        role.setName(id % 2 == 0 ? "developer" : "designer");
        if (ids != null && ids.length > 0) {
            role.setId(ids[0]);
        }
        return role;
    }

    /**
     * <p>
     * Create a new instance of <code>Registrant</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>Registrant</code>.
     */
    static Registrant createRegistrant(long... ids) {
        Registrant registrant = new Registrant();
        setAuditProperties(registrant);
        registrant.setContestId(id);
        registrant.setRole(createRole());
        registrant.setUser(createUser());
        if (ids != null && ids.length > 0) {
            registrant.setId(ids[0]);
        }
        return registrant;
    }

    /**
     * <p>
     * Create a new instance of <code>ContestType</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>ContestType</code>.
     */
    static ContestType createContestType(long... ids) {
        ContestType contestType = new ContestType();
        setAuditProperties(contestType);
        contestType.setName(id % 2 == 0 ? "JAVA" : ".NET");
        if (ids != null && ids.length > 0) {
            contestType.setId(ids[0]);
        }
        return contestType;
    }

    /**
     * <p>
     * Create a new instance of <code>Contest</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>Contest</code>.
     */
    static Contest createContest(long... ids) {
        Contest contest = new Contest();
        setAuditProperties(contest);

        contest.setLink("http://www.topcoder.com/moduel" + id);
        contest.setName("Registration Framework " + (id % 2 == 0 ? " Develop" : "Design"));
        contest.setRegistrationEnd(new Date());
        contest.setRegistrationStart(new Date());
        contest.setSourceName(id % 2 == 0 ? "Oracle" : "MYSQL");

        contest.setContestType(createContestType());

        contest.addContestRole(createContestRole());
        contest.addContestRole(createContestRole());

        contest.addRegistrant(createRegistrant());
        contest.addRegistrant(createRegistrant());

        if (ids != null && ids.length > 0) {
            contest.setId(ids[0]);
        }
        return contest;
    }

    /**
     * <p>
     * Create a new instance of <code>ContestRole</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>ContestRole</code>.
     */
    static ContestRole createContestRole(long... ids) {
        ContestRole contestRole = new ContestRole();
        setAuditProperties(contestRole);
        contestRole.setContestId(id);
        contestRole.setMaxRegistrants((int) (1 * id));
        contestRole.setMinRegistrants((int) (id % 2 + 1));
        contestRole.setRegistrationEndOffset(System.currentTimeMillis() / 2 / 2);
        contestRole.setRegistrationStartOffset(System.currentTimeMillis() / 2 / 2 / 2);
        contestRole.setRole(createRole());
        contestRole.setUnadvertised(true);
        if (ids != null && ids.length > 0) {
            contestRole.setId(ids[0]);
        }
        return contestRole;
    }

    /**
     * <p>
     * Create a new instance of <code>CompositeRole</code>.
     * </p>
     *
     * @param ids to overwrite the default id(which is auto increased)
     *
     * @return a new instance of <code>CompositeRole</code>.
     */
    static CompositeRole createCompositeRole(long... ids) {
        CompositeRole compositeRole = new CompositeRole();
        setAuditProperties(compositeRole);
        compositeRole.addRole(createRole());
        compositeRole.addRole(createRole());
        if (ids != null && ids.length > 0) {
            compositeRole.setId(ids[0]);
        }
        return compositeRole;
    }

    /**
     * <p>
     * Set the audit properties.
     * </p>
     *
     * @param entity <code>AuditableRegistrationEntity</code> to set its audit properties.
     */
    static void setAuditProperties(AuditableRegistrationEntity entity) {
        entity.setId(id);
        entity.setCreateDate(new Date());
        entity.setModifyDate(new Date());
        entity.setCreateUserName("createUser" + id);
        entity.setModifyUsername("modifyUser" + id);
        id++;
    }
}
