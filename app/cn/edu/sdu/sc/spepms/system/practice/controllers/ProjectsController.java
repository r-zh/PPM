package cn.edu.sdu.sc.spepms.system.practice.controllers;

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
import cn.edu.sdu.sc.spepms.system.practice.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.practice.models.PracticeProject;
import cn.edu.sdu.sc.spepms.system.practice.views.html.*;

public class ProjectsController extends SecuredController{

    public static Result add(){
        return ok(add.render());
    }

    /**
     * 信息录入数据库
     */
    @Transactional
    public static Result save() {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        PracticeProject practiceProject = new PracticeProject();
        practiceProject.setCreatedBy(ContextUtil.getCurrentUserId());
        practiceProject.setCreatedOn(new Date());
        practiceProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        practiceProject.setUpdatedOn(new Date());
        practiceProject.setStatus(PracticeProject.Status.NEW);

        practiceProject.setName(data.getName());
        practiceProject.setBackground(data.getBackground());
        practiceProject.setGoal(data.getGoal());
        practiceProject.setFunction(data.getFunction());
        practiceProject.setEnviroment(data.getEnviroment());
        practiceProject.setTechnology(data.getTechnology());
        practiceProject.setPlan(data.getPlan());
        practiceProject.setFunction(data.getFunction());
        practiceProject.setStandard(data.getStandard());
        practiceProject.setContactInfo(data.getContactInfo());
        practiceProject.setNumber(data.getNumber());
        practiceProject.setProcess("0%");
        JPA.em().persist(practiceProject);
        return ok();
    }

    /**
     * @return信息更新
     * 
     */
    @Transactional
    public static Result reSave(Long practiceProjectId) {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        practiceProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        practiceProject.setUpdatedOn(new Date());
        practiceProject.setStatus(PracticeProject.Status.NEW);

        practiceProject.setName(data.getName());
        practiceProject.setBackground(data.getBackground());
        practiceProject.setGoal(data.getGoal());
        practiceProject.setFunction(data.getFunction());
        practiceProject.setEnviroment(data.getEnviroment());
        practiceProject.setTechnology(data.getTechnology());
        practiceProject.setPlan(data.getPlan());
        practiceProject.setFunction(data.getFunction());
        practiceProject.setStandard(data.getStandard());
        practiceProject.setContactInfo(data.getContactInfo());
        practiceProject.setNumber(data.getNumber());
        JPA.em().persist(practiceProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes.ProjectRequestsController.personHome());
    }

    /**
     * @param creationProjectId
     *  项目详细信息
     */
    @Transactional
    public static Result view(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        return ok(view.render(practiceProject, getCurrentUser()));
    }

    /**
     * 再次编辑该项目
     * @return
     */
    @Transactional
    public static Result edit(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        return ok(edit.render(practiceProject,getCurrentUser()));
    }

    /**
     * 审核项目
     */
    @Transactional
    public static Result list() {
        List<PracticeProject> practiceProjects = JPA.em().createQuery("from PracticeProject", PracticeProject.class).getResultList();
        return ok(list.render(practiceProjects));
    }

    /**
     * @param creationProjectId
     * 审核通过该项目
     */
    @Transactional
    public static Result approve(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        practiceProject.setStatus(PracticeProject.Status.APPROVED);;
        JPA.em().merge(practiceProject);
        return ok();
    }

    /**
     * @param creationProjectId
     * @return
     * 审核不通过该项目,回到审核项目列表页面
     */
    @Transactional
    public static Result reject(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        practiceProject.setStatus(PracticeProject.Status.KILLED);
        JPA.em().merge(practiceProject);
        return ok();
    }

    /**
     * 废弃该项目
     * 
     * @param Id
     * @return
     */
    @Transactional
    public static Result kill(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        practiceProject.setStatus(PracticeProject.Status.KILLED);
        JPA.em().merge(practiceProject);
        return ok();
}
    /**
     * @param projectId
     * 项目提交审核
     */
    @Transactional
    public static Result submit(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        practiceProject.setStatus(PracticeProject.Status.UNDER_APPROVAL);
        JPA.em().merge(practiceProject);
        return ok();
    }

    /**
     * @return首页
     */
    @Transactional
    public static Result index() {
        List<PracticeProject> practiceProjects = JPA.em().createQuery("from PracticeProject", PracticeProject.class).getResultList();
        return ok(index.render(practiceProjects));
    }

    /**
     * 报名该项目
     * 
     * @param creationProjectId
     * @return
     */
    @Transactional
    public static Result join(Long practiceProjectId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        //TODO:添加报名人员，但是遇到一些问题
        //practiceProject.getMembers().add(getCurrentUser());
        //practiceProject.setCurrentNumber(practiceProject.getMembers().size());
        JPA.em().merge(practiceProject);
        return ok();
    }

    /**
     * 删除报名的学生
     * 
     * @param projectId
     * @return
     */
    @Transactional
    public static Result leave(Long practiceProjectId,Long userId) {
        PracticeProject practiceProject = JPA.em().find(PracticeProject.class, practiceProjectId);
        User user=JPA.em().find(User.class, userId);
        practiceProject.getMembers().remove(user);
        practiceProject.setCurrentNumber(practiceProject.getMembers().size());
        JPA.em().merge(practiceProject);
        return ok();
    }
}
