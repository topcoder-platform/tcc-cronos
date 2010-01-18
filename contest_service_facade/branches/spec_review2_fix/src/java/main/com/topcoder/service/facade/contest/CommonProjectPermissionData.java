package com.topcoder.service.facade.contest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Bean class to hold permissions for project and their contests.
 * </p>
 * ----update in version 1.1-----
 * Add some comments to variable and methods.
 * Add the empty constructor based on the UML file.
 * @author TCSASSEMBLER, squarY
 * 
 * @since TCCC-1329
 * @version 1.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "commonProjectPermissionData", propOrder = { "contestId",
        "projectId", "studio", "pname", "cname", "pfull", "cfull",
        "pwrite", "cwrite", "pread", "cread" })
public class CommonProjectPermissionData {
    /**
     * Generated serial version id.
     */
    private static final long serialVersionUID = -6992488651979864257L;

    /**
     * Represents the contest id.
     */
    private Long contestId;

    /**
     * Represents the project id.
     */
    private Long projectId;

    /**
     * Represents the project name.
     */
    private String pname;

    /**
     * Represents the contest name.
     */
    private String cname;
    /**
     * Represents the pread attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer pread;
    /**
     * Represents the pwrite attribute of the CommonProjectPermissionData
     * entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private Integer pwrite;
    /**
     * Represents the pfull attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer pfull;
    /**
     * Represents the cread attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer cread;
    /**
     * Represents the cwrite attribute of the CommonProjectPermissionData
     * entity. It's set and accessed in the set/get methods. It can be any
     * value. The default value is null.
     */
    private Integer cwrite;
    /**
     * Represents the cfull attribute of the CommonProjectPermissionData entity.
     * It's set and accessed in the set/get methods. It can be any value. The
     * default value is null.
     */
    private Integer cfull;
    /**
     * Represents the studio attribute of the CommonProjectPermissionData
     * entity. The default value is false.
     */
    private boolean studio;

    /**
     * Get cfull
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cfull attribute of the CommonProjectPermissionData entity
     */
    public Integer getCfull() {
        return cfull;
    }

    /**
     * Set cfull
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cfull
     *            the cfull attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setCfull(Integer cfull) {
        this.cfull = cfull;
    }

    /**
     * Get cname
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cname attribute of the CommonProjectPermissionData entity
     */
    public String getCname() {
        return cname;
    }

    /**
     * Set cname
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cname
     *            the cname attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setCname(String cname) {
        this.cname = cname;
    }

    /**
     * Get contest id
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the contest id attribute of the CommonProjectPermissionData
     *         entity
     */
    public Long getContestId() {
        return contestId;
    }

    /**
     * Set contest id
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param contestId
     *            the contest id attribute to set to the
     *            CommonProjectPermissionData entity
     */
    public void setContestId(Long contestId) {
        this.contestId = contestId;
    }

    /**
     * Get cread
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cread attribute of the CommonProjectPermissionData entity
     */
    public Integer getCread() {
        return cread;
    }

    /**
     * Set cread
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cread
     *            the cread attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setCread(Integer cread) {
        this.cread = cread;
    }

    /**
     * Get cwrite
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the cwrite attribute of the CommonProjectPermissionData entity
     */
    public Integer getCwrite() {
        return cwrite;
    }

    /**
     * Set cwrite
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param cwrite
     *            the cwrite attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setCwrite(Integer cwrite) {
        this.cwrite = cwrite;
    }

    /**
     * Get pfull
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pfull attribute of the CommonProjectPermissionData entity
     */
    public Integer getPfull() {
        return pfull;
    }

    /**
     * Set pfull
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pfull
     *            the pfull attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setPfull(Integer pfull) {
        this.pfull = pfull;
    }

    /**
     * Get pname
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pname attribute of the CommonProjectPermissionData entity
     */
    public String getPname() {
        return pname;
    }

    /**
     * Set pname
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pname
     *            the pname attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setPname(String pname) {
        this.pname = pname;
    }

    /**
     * Get pread
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pread attribute of the CommonProjectPermissionData entity
     */
    public Integer getPread() {
        return pread;
    }

    /**
     * Set pread
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pread
     *            the pread attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setPread(Integer pread) {
        this.pread = pread;
    }

    /**
     * Get project id
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the project id attribute of the CommonProjectPermissionData
     *         entity
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Set project id
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param projectId
     *            the project id attribute to set to the
     *            CommonProjectPermissionData entity
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    /**
     * Get pwrite
     * 
     * Impl note: Get the namesake variable.
     * 
     * @return the pwrite attribute of the CommonProjectPermissionData entity
     */
    public Integer getPwrite() {
        return pwrite;
    }

    /**
     * Set pwrite
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param pwrite
     *            the pwrite attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setPwrite(Integer pwrite) {
        this.pwrite = pwrite;
    }

    /**
     * Set studio
     * 
     * Impl note: Set the namesake variable. This set method does not perform
     * any check on the argument.
     * 
     * @param studio
     *            the studio attribute to set to the CommonProjectPermissionData
     *            entity
     */
    public void setStudio(boolean studio) {
        this.studio = studio;
    }

    /**
     * Is studio
     * 
     * Impl note: Get the namesake variable.
     * 
     * @param studio
     *            the studio to use
     * @return the studio attribute of the CommonProjectPermissionData entity
     */
    public boolean isStudio() {
        return this.studio;
    }
    /**
     * The Empty constructor for CommonProjectPermissionData.
     * @since 1.1
     */
    public CommonProjectPermissionData(){
        
    }
}
