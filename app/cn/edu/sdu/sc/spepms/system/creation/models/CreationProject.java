package cn.edu.sdu.sc.spepms.system.creation.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import cn.edu.sdu.sc.spepms.system.common.models.AuditableModel;
import cn.edu.sdu.sc.spepms.system.common.models.User;

/**
 * @author tonyzhou
 *
 */
@Entity
public class CreationProject extends AuditableModel {
    
    
    public enum Status {
        NEW,
        UNDER_APPROVAL,
        APPROVED,
        IN_PROGRESS,
        COMPLETED,
        KILLED
    }
    
    @Enumerated(EnumType.STRING)
    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * 名称
     */
    private String name;

    /**
     * 类别（外部合作、竞赛发布）
     */
    private String category;

    /**
     * 性质（有尝、无报酬）
     */
    private String billable;

    /**
     * 报酬形式
     */
    private String rewardMethod;

    /**
     * 报酬额度
     */
    private long rewardAmount;

    /**
     * 项目需求介绍
     */
    private String description;

    /**
     * 时间范围（开始）
     */
    private String applicableFrom;

    /**
     * 时间范围（结束）
     */
    private String applicableTo;

    /**
     * 联系咨询方式
     */
    private String contactInfo;

    /**
     * 人数上限
     */
    private long number;

    /**
     * 已报人数
     */
    private long currentNumber;

    /**
     * 审核是否通过
     */
    private boolean passed=false;

    private boolean trashed=false;
    
    @ManyToMany
    @JoinTable(name = "CreationProject_Users", 
    joinColumns = {@JoinColumn(name = "CreationProject_Id", referencedColumnName ="Id")}, 
    inverseJoinColumns = {@JoinColumn(name = "Members_Id", referencedColumnName = "Id")})
    private List<User> members;
    
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public boolean hasMember(User user) {
        return members.contains(user);
    }

    public long getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(long currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    public void setPassed(boolean flag){
        passed=flag;
    }

    public boolean getPassed(){
        return passed;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBillable() {
        return billable;
    }

    public void setBillable(String billable) {
        this.billable = billable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicableFrom() {
        return applicableFrom;
    }

    public void setApplicableFrom(String applicableFrom) {
        this.applicableFrom = applicableFrom;
    }

    public String getApplicableTo() {
        return applicableTo;
    }

    public void setApplicableTo(String applicableTo) {
        this.applicableTo = applicableTo;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getRewardMethod() {
        return rewardMethod;
    }

    public void setRewardMethod(String rewardMethod) {
        this.rewardMethod = rewardMethod;
    }

    public long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }
}
