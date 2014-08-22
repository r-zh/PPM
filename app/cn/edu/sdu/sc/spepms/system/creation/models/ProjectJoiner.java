package cn.edu.sdu.sc.spepms.system.creation.models;

import javax.persistence.Entity;

import cn.edu.sdu.sc.spepms.system.common.models.BaseModel;

@Entity
public class ProjectJoiner extends BaseModel{

    /*
     *参与者的项目序号*/
    private Long projectId;

    /*
     *参与者的序号*/
    private Long userId;

    /*
     *参与者的名称*/
    private String userName;

    /*
     *参与者的学号*/
    private long studentId;

    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public long getStudentId() {
        return studentId;
    }
    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
