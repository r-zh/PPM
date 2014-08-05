package cn.edu.sdu.qlsc.sc.spepms.system.common.models;

import java.util.Date;

import javax.persistence.MappedSuperclass;

/**
 * Adds basic audit fields about who and when this record was created/updated
 * 
 * @author Peter Fu
 */
@MappedSuperclass
public abstract class AuditableModel extends BaseModel {

    private Date createdOn;

    private Long createdBy;

    private Date updatedOn;

    private Long updatedBy;

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

}
