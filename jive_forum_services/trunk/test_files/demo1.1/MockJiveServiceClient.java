/*
 * Copyright (C) 2008 TopCoder Inc., All Rights Reserved.
 *
 * MockJiveServiceClient.java
 */
import com.topcoder.forum.service.CategoryConfiguration;
import com.topcoder.forum.service.EntityType;
import com.topcoder.forum.service.JiveForumManagementException;
import com.topcoder.forum.service.UserRole;
import com.topcoder.forum.service.ejb.JiveForumServiceRemote;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 * <p>Demonstrate the client usage of Jive service.</p>
 *
 * @author TCSDEVELOPER
 * @version 1.1
 * @since 1.1
 */
public class MockJiveServiceClient {
    /**
     * <p>Demonstrate the client usage.</p>
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        try {
            // It needs 'jndi.properties' in classpath
            InitialContext ctx = new InitialContext();

            JiveForumServiceRemote service = (JiveForumServiceRemote) ctx.lookup("JiveForumService/remote");

            // add a watch, note that the watch is just stored in-memory
            long userId = 123;
            long entityId = 456;
            service.watch(userId, entityId, EntityType.FORUM);

            // test if user is watch a entity
            // true should be returned
            boolean watching1 = service.isWatched(userId, entityId, EntityType.FORUM);
            System.out.println("Watching: " + watching1);

            // false should be returned
            boolean watching2 = service.isWatched(userId, entityId, EntityType.FORUM_THREAD);
            System.out.println("Watching: " + watching2);

            // false should be returned
            boolean watching3 = service.isWatched(userId, 789, EntityType.FORUM);
            System.out.println("Watching: " + watching3);

            // set user role
            long categoryId = 3;
            service.setUserRole(userId, categoryId, UserRole.NO_ACCESS);

            // get user role
            // UserRole.NO_ACCESS should be returned
            UserRole role = service.getUserRole(userId, categoryId);
            System.out.println("Role: " + role);

            // create category
            CategoryConfiguration categoryConfig = new CategoryConfiguration();
            categoryConfig.setName("Test");
            categoryConfig.setDescription("Test Desc");
            categoryConfig.setRootCategoryId(10);
            categoryConfig.setComponentId(1);
            categoryConfig.setVersionText("v1.0");
            categoryConfig.setVersionId(1);
            categoryConfig.setTemplateCategoryId(1);

            long newCatId = service.createCategory(categoryConfig);
            System.out.println("New category id: " + newCatId);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JiveForumManagementException e) {
            e.printStackTrace();
        }
    }
}
