package com.topcoder.service.facade.direct.ejb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.topcoder.management.project.ContestSale;
import com.topcoder.management.project.DesignComponents;
import com.topcoder.management.project.PersistenceException;
import com.topcoder.management.project.Project;
import com.topcoder.management.project.ProjectCategory;
import com.topcoder.management.project.ProjectManager;
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
import com.topcoder.management.project.ValidationException;
import com.topcoder.search.builder.filter.Filter;

public class MockProjectManager implements ProjectManager {

    public MockProjectManager() {
    }

    public boolean checkContestPermission(long arg0, boolean arg1, long arg2) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkProjectPermission(long arg0, boolean arg1, long arg2) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public ContestSale createContestSale(ContestSale arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void createProject(Project arg0, String arg1) throws PersistenceException, ValidationException {
        // TODO Auto-generated method stub

    }

    public void editContestSale(ContestSale arg0) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    public ProjectCategory[] getAllProjectCategories() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectPropertyType[] getAllProjectPropertyTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectStatus[] getAllProjectStatuses() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ProjectType[] getAllProjectTypes() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getAllTcDirectProjects() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getAllTcDirectProjects(String arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SoftwareCapacityData> getCapacity(int arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public ContestSale getContestSale(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<ContestSale> getContestSales(long arg0) throws PersistenceException {
        if (arg0 == 101) {
            throw new PersistenceException("for test");
        }
        List<ContestSale> sales = new ArrayList<ContestSale>();
        ContestSale sale = new ContestSale();
        sales.add(sale);
        return sales;
    }

    public List<DesignComponents> getDesignComponents(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getDesignContestId(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getDevelopmentContestId(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public long getForumId(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public Project getProject(long id) throws PersistenceException {
        Project project = new Project();
        ProjectSpec projectSpec = new ProjectSpec();
        projectSpec.setProjectSpecId(id);
        project.setProjectSpec(projectSpec);
        return project;
    }

    public List<Long> getProjectIdByTcDirectProject(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getProjects(long[] arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public Project[] getProjectsByCreateDate(int arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public SaleStatus getSaleStatus(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public SaleType getSaleType(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getScorecardId(long arg0, int arg1) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<SimplePipelineData> getSimplePipelineData(long arg0, Date arg1, Date arg2, boolean arg3)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestData(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectContestData> getSimpleProjectContestDataByUser(String arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public List<SimpleProjectPermissionData> getSimpleProjectPermissionDataForUser(long arg0)
            throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public long getTcDirectProject(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return 0;
    }

    public Project[] getUserProjects(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean hasContestPermission(long arg0, long arg1) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isDevOnly(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean removeContestSale(long arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    public Project[] searchProjects(Filter arg0) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    public void updateProject(Project arg0, String arg1, String arg2) throws PersistenceException,
            ValidationException {
        // TODO Auto-generated method stub

    }

}
