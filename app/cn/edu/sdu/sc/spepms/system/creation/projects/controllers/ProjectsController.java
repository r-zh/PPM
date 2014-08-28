package cn.edu.sdu.sc.spepms.system.creation.projects.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Result;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.utils.ContextUtil;
import cn.edu.sdu.sc.spepms.system.common.views.html.studentHome;
import cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes;
import cn.edu.sdu.sc.spepms.system.creation.projects.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.projects.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.projects.views.html.*;

public class ProjectsController extends SecuredController {

    // 创新项目发布
    public static Result add() {
        // System.out.println(request().username());
        return ok(add.render());
    }

    // 信息录入数据库
    @Transactional
    public static Result save() {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        CreationProject creationProject = new CreationProject();
        creationProject.setCreatedBy(ContextUtil.getCurrentUserId());
        creationProject.setCreatedOn(new Date());
        creationProject.setStatus(CreationProject.Status.NEW);

        creationProject.setName(data.getName());
        creationProject.setCategory(data.getCategory());
        creationProject.setBillable(data.getBillable());
        creationProject.setRewardMethod(data.getRewardMethod());
        creationProject.setRewardAmount(data.getRewardAmount());
        creationProject.setDescription(data.getDescription());
        creationProject.setApplicableFrom(data.getApplicableFrom());
        creationProject.setApplicableTo(data.getApplicableTo());
        creationProject.setContactInfo(data.getContactInfo());
        creationProject.setNumber(data.getNumber());
        List<User> members = new ArrayList<User>();
        members.add(getCurrentUser());
        creationProject.setMembers(members);
        creationProject.setCurrentNumber(members.size());
        JPA.em().persist(creationProject);
        /*
        List<ProjectMember> pmembers = new ArrayList<ProjectMember>();
        ProjectMember pmember = new ProjectMember();
        pmember.setUser(getCurrentUser());
        pmember.setShenfen();
        pmembers.add(pmember);
        creationProject.setProjectMembers(pmembers);
        */
        // return ok(studentHome.render(getCurrentUser().getCreationProjects()));
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.index());
    }

    @Transactional
    public static Result index() {
        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(index.render(creationProject));
    }

    /**
     * 显示个人所报的项目
     * 
     * @return
     */
    @Transactional
    public static Result personHome() {
        // System.out.println(ContextUtil.getCurrentUserId());
        return ok(studentHome.render(getCurrentUser().getCreationProjects()));
    }

    /**
     * 审核该项目
     * 
     * @return
     */
    @Transactional
    public static Result list() {
        List<CreationProject> creationProjects = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(list.render(creationProjects));
    }

    // 项目详细信息
    @Transactional
    public static Result view(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        return ok(view.render(creationProject, getCurrentUser()));
    }

    // 审核通过该项目
    @Transactional
    public static Result approve(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.APPROVED);;
        JPA.em().merge(creationProject);
        return ok(view.render(creationProject, getCurrentUser()));
    }

    // 审核不通过该项目
    @Transactional
    public static Result reject(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.UNDER_APPROVAL);
        JPA.em().merge(creationProject);
        return ok(view.render(creationProject, getCurrentUser()));
    }

    // 项目提交审核
    @Transactional
    public static Result submit(Long projectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, projectId);
        creationProject.setStatus(CreationProject.Status.UNDER_APPROVAL);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.view(creationProject.getId()));
        //return ok(view.render(creationProject, getCurrentUser()));
    }

    /**
     * 废弃该项目
     * 
     * @param Id
     * @return
     */
    @Transactional
    public static Result kill(Long Id) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, Id);
        creationProject.setStatus(CreationProject.Status.KILLED);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.list());
    }

    /**
     * 报名该项目
     * 
     * @param creationProjectId
     * @return
     */
    @Transactional
    public static Result join(Long creationProjectId) {
        /*TODO 这里遇到的问题是能不能替别人报名，如果可以为别人报名，会不会降低安全性，先设置为只能自己报名
        Form<ProjectJoinerForm> form = Form.form(ProjectJoinerForm.class).bindFromRequest();
        ProjectJoinerForm data = form.get();
        ProjectJoiner projectJoiner=new ProjectJoiner();
        projectJoiner.setProjectId(creationProjectId);
        projectJoiner.setUserName(data.getUserName());
        projectJoiner.setStudentId(data.getStudentId());
        //User user=JPA.em().find(User.class, ContextUtil.getCurrentUserId());
        //projectJoiner.setStudentId(user.getStudentId());
        //projectJoiner.setUserId(ContextUtil.getCurrentUserId());
        JPA.em().persist(projectJoiner);
         User user=new User();
         user.setName(data.getUserName());
         */
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.getMembers().add(getCurrentUser());
        // creationProject.getMembers().get(0).getName();
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.view(creationProjectId));
    }

    /**
     * 填写报名人员情况
     * 
     * @param projectId
     * @return
     */
    @Transactional
    public static Result applicate(Long projectId) {
        return ok(applicate.render(projectId));
    }

    /**
     * 删除报名的学生
     * 
     * @param projectId
     * @return
     */
    @Transactional
    public static Result leave(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.getMembers().remove(getCurrentUser());
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return ok(view.render(creationProject, getCurrentUser()));
    }

}
