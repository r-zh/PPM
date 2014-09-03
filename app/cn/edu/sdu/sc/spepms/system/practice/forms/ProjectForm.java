package cn.edu.sdu.sc.spepms.system.practice.forms;

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
public class ProjectForm extends AuditableModel {

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
