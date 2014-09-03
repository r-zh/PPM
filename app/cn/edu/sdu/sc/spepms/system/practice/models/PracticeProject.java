package cn.edu.sdu.sc.spepms.system.practice.models;

import java.math.BigDecimal;
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
public class PracticeProject extends AuditableModel {

    /**
     * 项目状态
     *
     */
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

    /**
     * 名称
     */
    private String name;

    /**
     * 实训项目背景
     */
    private String background;

    /**
     * 实训目标
     */
    private String goal;

    /**
     * 系统功能
     */
    private String function;

    /**
     * 实训环境要求
     */
    private String enviroment;

    /**
     * 技术方案
     */
    private String technology;

    /**
     * 实训计划
     */
    private String plan;

    /**
     * 实训人员考核评价标准说明
     */
    private String standard;

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

    private boolean trashed=false;
    
    /**
     * 实训中心成员
     */
    @OneToMany
    @JoinTable(name = "PracticeProjectMember", 
    joinColumns = {@JoinColumn(name = "PracticeProject_Id", referencedColumnName ="Id")}, 
    inverseJoinColumns = {@JoinColumn(name = "PracticeProjectMember_Id", referencedColumnName = "Id")})
    private List<PracticeProjectMember> members;

    private String process;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<PracticeProjectMember> getMembers() {
        return members;
    }

    public void setMembers(List<PracticeProjectMember> members) {
        this.members = members;
    }

    public boolean hasMember(User user) {
        return members.contains(user);
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getEnviroment() {
        return enviroment;
    }

    public void setEnviroment(String enviroment) {
        this.enviroment = enviroment;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}
