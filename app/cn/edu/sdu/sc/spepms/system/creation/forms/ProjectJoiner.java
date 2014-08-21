package cn.edu.sdu.sc.spepms.system.creation.forms;

public class ProjectJoiner {

    /*
     *参与者的项目序号*/
    private Long projectId;

    /*
     *参与者的序号*/
    private Long userId;

    /*
     *参与者的名称*/
    private Long userName;

    /*
     *参与者的学号*/
    private Long studentId;

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
    public Long getUserName() {
        return userName;
    }
    public void setUserName(Long userName) {
        this.userName = userName;
    }
    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
