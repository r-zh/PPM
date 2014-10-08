package cn.edu.sdu.sc.spepms.system.creation.project_requests.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import cn.edu.sdu.sc.spepms.system.common.models.AuditableModel;
import cn.edu.sdu.sc.spepms.system.common.models.User;

/**
 * @author tonyzhou
 *
 */
@Entity
public class ProjectRequest extends AuditableModel {

    public enum Type {
        COMPETITION,//竞赛
        EXTERNAL,//外部项目
        PERSONAL,//个人兴趣
        RESEARCH//教师科研
    }

    public enum Status {
        NEW,//草稿
        UNDER_APPROVAL,//待审核
        APPROVED,//审核通过
        IN_PROGRESS,//报名进行中
        COMPLETED,//报名结束
        KILLED//废弃
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
    @Enumerated(EnumType.STRING)
    private Type category;

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
    private Integer rewardAmount;

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
    private Integer number;

    /**
     * 已报人数
     */
    private Integer currentNumber;

    private boolean trashed=false;
    
    @ManyToMany
    @JoinTable(name = "ProjectRequest_Users", 
    joinColumns = {@JoinColumn(name = "ProjectRequest_Id", referencedColumnName ="Id")}, 
    inverseJoinColumns = {@JoinColumn(name = "Members_Id", referencedColumnName = "Id")})
    private List<User> members;

    private String process;

    //项目需求申请人
    @ManyToOne
    private User organizer;

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public boolean hasMember(User user) {
        return members.contains(user);
    }

    public Integer getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(Integer currentNumber) {
        this.currentNumber = currentNumber;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

	public Type getCategory() {
        return category;
    }

    public void setCategory(Type category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

    public Integer getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Integer rewardAmount) {
        this.rewardAmount = rewardAmount;
    }

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

}
