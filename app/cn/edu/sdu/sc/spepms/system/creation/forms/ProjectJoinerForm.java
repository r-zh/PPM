package cn.edu.sdu.sc.spepms.system.creation.forms;

public class ProjectJoinerForm {

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
    private String studentId;

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
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
