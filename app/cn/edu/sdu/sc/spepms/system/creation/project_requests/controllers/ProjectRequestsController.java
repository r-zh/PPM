package cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers;

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
import cn.edu.sdu.sc.spepms.system.creation.project_requests.forms.ProjectRequestForm;
import cn.edu.sdu.sc.spepms.system.creation.project_requests.models.ProjectRequest;
import cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes;
import cn.edu.sdu.sc.spepms.system.creation.project_requests.views.html.*;

public class ProjectRequestsController extends SecuredController {

    // 创新项目发布
    public static Result add() {
        return ok(add.render());
    }

    // 信息录入数据库
    @Transactional
    public static Result save() {
        Form<ProjectRequestForm> form = Form.form(ProjectRequestForm.class).bindFromRequest();
        ProjectRequestForm data = form.get();

        ProjectRequest creationProject = new ProjectRequest();
        creationProject.setCreatedBy(ContextUtil.getCurrentUserId());
        creationProject.setCreatedOn(new Date());
        creationProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        creationProject.setUpdatedOn(new Date());
        creationProject.setStatus(ProjectRequest.Status.NEW);

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
        return redirect(cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes.ProjectRequestsController.personHome());
    }

    /**
     * 再次编辑该项目
     * @return
     */
    @Transactional
    public static Result edit(Long creationProjectId) {
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
        return ok(edit.render(creationProject,getCurrentUser()));
    }

    /**
     * @return信息更新
     * 
     */
    @Transactional
    public static Result reSave(Long creationProjectId) {
        Form<ProjectRequestForm> form = Form.form(ProjectRequestForm.class).bindFromRequest();
        ProjectRequestForm data = form.get();

        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
        creationProject.setUpdatedBy(ContextUtil.getCurrentUserId());
        creationProject.setUpdatedOn(new Date());
        creationProject.setStatus(ProjectRequest.Status.NEW);

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
        return redirect(cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes.ProjectRequestsController.personHome());
    }

    /**
     * @return首页
     */
    @Transactional
    public static Result index() {
        List<ProjectRequest> creationProject = JPA.em().createQuery("from ProjectRequest", ProjectRequest.class).getResultList();
        return ok(index.render(creationProject));
    }

    /**
     * 显示个人所报的项目
     */
    @Transactional
    public static Result personHome() {
        return ok(studentHome.render(getCurrentUser().getProjectRequests()));
    }

    /**
     * 审核该项目
     * @return
     */
    @Transactional
    public static Result list() {
        List<ProjectRequest> creationProjects = JPA.em().createQuery("from ProjectRequest", ProjectRequest.class).getResultList();
        return ok(list.render(creationProjects));
    }

    /**
     * @param creationProjectId
     *  项目详细信息
     */
    @Transactional
    public static Result view(Long creationProjectId) {
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
        return ok(view.render(creationProject, getCurrentUser()));
    }

    /**
     * @param creationProjectId
     * 审核通过该项目
     */
    @Transactional
    public static Result approve(Long creationProjectId) {
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
        creationProject.setStatus(ProjectRequest.Status.APPROVED);;
        JPA.em().merge(creationProject);
        return ok();
    }

    /**
     * @param creationProjectId
     * @return
     * 审核不通过该项目,回到审核项目列表页面
     */
    @Transactional
    public static Result reject(Long creationProjectId) {
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
        creationProject.setStatus(ProjectRequest.Status.KILLED);
        JPA.em().merge(creationProject);
        return ok();
    }

    // 项目提交审核
    @Transactional
    public static Result submit(Long projectId) {
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, projectId);
        creationProject.setStatus(ProjectRequest.Status.UNDER_APPROVAL);
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
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, Id);
        creationProject.setStatus(ProjectRequest.Status.KILLED);
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
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
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
        ProjectRequest creationProject = JPA.em().find(ProjectRequest.class, creationProjectId);
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
        List<ProjectRequest> processingProjects = JPA.em()
                .createQuery("from ProjectRequest", ProjectRequest.class)
                .getResultList();
        return ok(process.render(processingProjects));
    }

}
