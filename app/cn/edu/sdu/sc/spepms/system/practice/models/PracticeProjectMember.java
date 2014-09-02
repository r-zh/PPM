package cn.edu.sdu.sc.spepms.system.practice.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

import cn.edu.sdu.sc.spepms.system.common.models.AuditableModel;
import cn.edu.sdu.sc.spepms.system.common.models.User;

@Entity
public class PracticeProjectMember extends AuditableModel{

    @ManyToOne
    @JoinTable(name = "PracticeProjectMember", 
    joinColumns = {@JoinColumn(name = "PracticeProjectMember_Id", referencedColumnName ="Id")}, 
    inverseJoinColumns = {@JoinColumn(name = "PracticeProject_Id", referencedColumnName = "Id")})
    private PracticeProject practiceProject;

    @ManyToOne
    @JoinTable(name = "PracticeProjectMember", 
    joinColumns = {@JoinColumn(name = "PracticeProjectMember_Id", referencedColumnName ="Id")}, 
    inverseJoinColumns = {@JoinColumn(name = "User_Id", referencedColumnName = "Id")})
    private User member;

    public PracticeProject getPracticeProject() {
        return practiceProject;
    }

    public void setPracticeProject(PracticeProject practiceProject) {
        this.practiceProject = practiceProject;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User member) {
        this.member = member;
    }
}
