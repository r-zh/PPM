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
        creationProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        creationProject.setUpdatedOn(new Date());
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
        creationProject.setProcess("0%");
        List<User> members = new ArrayList<User>();
        members.add(getCurrentUser());
        creationProject.setMembers(members);
        creationProject.setCurrentNumber(members.size());
        JPA.em().persist(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.personHome());
    }

    /**
     * 再次编辑该项目
     * @return
     */
    @Transactional
    public static Result edit(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        return ok(edit.render(creationProject,getCurrentUser()));
    }

    /**
     * @return信息更新
     * 
     */
    @Transactional
    public static Result reSave(Long creationProjectId) {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        creationProject.setUpdatedOn(new Date());
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
        JPA.em().persist(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.personHome());
    }

    /**
     * @return首页
     */
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
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.list());

    }

    /**
     * @param creationProjectId
     * @return
     * 审核不通过该项目,回到审核项目列表页面
     */
    @Transactional
    public static Result reject(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setStatus(CreationProject.Status.KILLED);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.projects.controllers.routes.ProjectsController.list());
    }

    // 项目提交审核
    @Transactional
    public static Result submit(Long projectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, projectId);
        creationProject.setStatus(CreationProject.Status.UNDER_APPROVAL);
        JPA.em().merge(creationProject);
        return ok();
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
        return ok();
}

    /**
     * 报名该项目
     * 
     * @param creationProjectId
     * @return
     */
    @Transactional
    public static Result join(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.getMembers().add(getCurrentUser());
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return ok();
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
    public static Result leave(Long creationProjectId,Long userId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        User user=JPA.em().find(User.class, userId);
        creationProject.getMembers().remove(user);
        creationProject.setCurrentNumber(creationProject.getMembers().size());
        JPA.em().merge(creationProject);
        return ok();
    }

    /**
     * 查看项目进度
     * @return
     */
    @Transactional
    public static Result process(){
        List<CreationProject> processingProjects = JPA.em()
                .createQuery("from CreationProject", CreationProject.class)
                .getResultList();
        return ok(process.render(processingProjects));
    }

}
