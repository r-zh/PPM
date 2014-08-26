package cn.edu.sdu.sc.spepms.system.creation.controllers.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.utils.ContextUtil;
import cn.edu.sdu.sc.spepms.system.common.views.html.*;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectJoinerForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.views.html.project.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;


public class ProjectController extends SecuredController {
    //创新项目发布
    public static Result addProject() {
        //System.out.println(request().username());
        return ok(add.render());
    }

    //信息录入数据库
    @Transactional
    public static Result saveProject() {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        CreationProject creationProject=new CreationProject();
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
        //return ok(studentHome.render(getCurrentUser().getCreationProjects()));
        return redirect(cn.edu.sdu.sc.spepms.system.creation.controllers.project.routes.ProjectController.index());
    }

    @Transactional
    public static Result index() {
        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(index.render(creationProject));
    }

    /**
     * @return显示个人所报的项目
     */
    @Transactional
    public static Result personHome() {
        //System.out.println(ContextUtil.getCurrentUserId());
        return ok(studentHome.render(getCurrentUser().getCreationProjects()));
    }

    /**
     * @return审核该项目
     */
    @Transactional
    public static Result check() {
        List<CreationProject> creationProjects = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(check.render(creationProjects));
    }

    //项目详细信息
    @Transactional
    public static Result details(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        return ok(details.render(creationProject,getCurrentUser()));
    }

    // 审核通过该项目
    @Transactional
    public static Result pass(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.APPROVED);;
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,getCurrentUser()));
    }

    // 审核不通过该项目
    @Transactional
    public static Result unPass(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.UNDER_APPROVAL);
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,getCurrentUser()));
    }

    // 项目提交审核
    @Transactional
    public static Result toCheck(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.UNDER_APPROVAL);
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,getCurrentUser()));
    }
    /**
     * @param Id
     * @return
     * 废弃该项目
     */
    @Transactional
    public static Result trashed(Long Id) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, Id);
        creationProject.setStatus(CreationProject.Status.KILLED);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.controllers.project.routes.ProjectController.check());
    }

    /**
     * @param creationProjectId
     * 报名该项目
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
        CreationProject creationProject=JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.getMembers().add(getCurrentUser());
        //creationProject.getMembers().get(0).getName();
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.controllers.project.routes.ProjectController.details(creationProjectId));
        //return ok(details.render(creationProject,getCurrentUser()));
    }

    /**
     * @param projectId
     * 填写报名人员情况
     * @return
     */
    @Transactional
    public static Result joiners(Long projectId) {
        return ok(joiners.render(projectId));
    }

    /**
     * @param projectId
     * 删除报名的学生
     * @return
     */
    @Transactional
    public static Result delete(Long creationProjectId) {
        CreationProject creationProject=JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.getMembers().remove(getCurrentUser());
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,getCurrentUser()));
    }
}
