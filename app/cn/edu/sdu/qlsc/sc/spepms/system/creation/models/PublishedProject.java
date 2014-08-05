package cn.edu.sdu.qlsc.sc.spepms.system.creation.models;

import java.util.Date;

import javax.persistence.Entity;

import cn.edu.sdu.qlsc.sc.spepms.system.common.models.BaseModel;

/**
 * 发布的项目
 * 
 * @author Peter Fu
 */
@Entity
public class PublishedProject extends BaseModel {

    /**
     * 类别（外部合作、竞赛发布）
     */
    private String category;

    /**
     * 性质（有尝、无报酬）
     */
    private Boolean billable;

    /**
     * 报酬形式及额度
     */
    private String rewardInfo;

    /**
     * 项目需求介绍
     */
    private String description;

    /**
     * 时间范围（开始）
     */
    private Date applicableFrom;

    /**
     * 时间范围（结束）
     */
    private Date applicableTo;

    /**
     * 联系咨询方式
     */
    private String contactInfo;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getBillable() {
        return billable;
    }

    public void setBillable(Boolean billable) {
        this.billable = billable;
    }

    public String getRewardInfo() {
        return rewardInfo;
    }

    public void setRewardInfo(String rewardInfo) {
        this.rewardInfo = rewardInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(Date applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public Date getApplicableTo() {
        return applicableTo;
    }

    public void setApplicableTo(Date applicableTo) {
        this.applicableTo = applicableTo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

}
