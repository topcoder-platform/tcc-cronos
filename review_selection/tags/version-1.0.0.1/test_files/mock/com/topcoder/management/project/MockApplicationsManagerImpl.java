package com.topcoder.management.project;

public class MockApplicationsManagerImpl implements ApplicationsManager {
    private ReviewApplication application11;
    private ReviewApplication application12;
    private ReviewApplication application13;
    private ReviewApplication application21;
    private ReviewApplication application22;
    private ReviewApplication application23;
    private ReviewApplication application31;

    public MockApplicationsManagerImpl() {
        application11 = new ReviewApplication();
        application11.setProjectId(1);
        application11.setAcceptPrimary(false);
        application12 = new ReviewApplication();
        application12.setProjectId(3);
        application12.setAcceptPrimary(false);
        application13 = new ReviewApplication();
        application13.setProjectId(3);
        application13.setAcceptPrimary(true);

        application21 = new ReviewApplication();
        application21.setProjectId(2);
        application21.setAcceptPrimary(false);
        application22 = new ReviewApplication();
        application22.setProjectId(2);
        application22.setAcceptPrimary(false);
        application23 = new ReviewApplication();
        application23.setProjectId(2);
        application23.setAcceptPrimary(false);

        application31 = new ReviewApplication();
        application31.setProjectId(3);
        application31.setAcceptPrimary(false);
    }

    public ReviewApplication create(ReviewApplication reviewApplication) {
        return null;
    }

    public boolean delete(long id) {
        return false;
    }

    public ReviewApplication[] getAllApplications(long projectId) {
        if(projectId == 1) {
            return new ReviewApplication[] {application11, application12, application13};
        } else if (projectId == 2) {
            return new ReviewApplication[] {application21, application22, application23};
        } else if (projectId == 3) {
            return new ReviewApplication[] {application31};
        } else {
            return new ReviewApplication[] {};
        }
    }

    public ReviewApplication[] getPrimaryApplications(long projectId) {
        return null;
    }

    public ReviewApplication[] getSecondaryApplications(long projectId) {
        return null;
    }

    public ReviewApplication retrieve(long id) {
        return null;
    }

    public ReviewApplication update(ReviewApplication reviewApplication) {
        return null;
    }

}
