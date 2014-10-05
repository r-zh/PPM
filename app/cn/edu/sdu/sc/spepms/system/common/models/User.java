package cn.edu.sdu.sc.spepms.system.common.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import cn.edu.sdu.sc.spepms.system.creation.project_requests.models.ProjectRequest;
import cn.edu.sdu.sc.spepms.system.practice.models.PracticeProject;
import cn.edu.sdu.sc.spepms.system.practice.models.PracticeProjectMember;

@Entity
public class User extends AuditableModel {

    private String name;

    private String gender;

    private String personalId;

    private String birthday;

    private String email;

    private String hometown;

    private String password;

    private long studentId;

    private String phone;

    private String grade;

    private String major;

    private String lab;

    @ManyToMany
    @JoinTable(name = "ProjectRequest_Users", 
            joinColumns = {@JoinColumn(name = "Members_Id", referencedColumnName = "Id")}, 
            inverseJoinColumns = {@JoinColumn(name = "ProjectRequest_Id", referencedColumnName ="Id")})
    private List<ProjectRequest> projectRequests;

    @OneToMany
    @JoinTable(name = "PracticeProjectMember", 
            joinColumns = {@JoinColumn(name = "Member_Id", referencedColumnName = "Id")}, 
            inverseJoinColumns = {@JoinColumn(name = "PracticeProjectMember_Id", referencedColumnName ="Id")})
    private List<PracticeProjectMember> practiceProjects;

    public List<PracticeProjectMember> getPracticeProjects() {
        return practiceProjects;
    }

    public void setPracticeProjects(List<PracticeProjectMember> practiceProjects) {
        this.practiceProjects = practiceProjects;
    }

    public List<ProjectRequest> getProjectRequests() {
        return projectRequests;
    }

    public void setCreationProjects(List<ProjectRequest> projectRequests) {
        this.projectRequests = projectRequests;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLab() {
        return lab;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

}
