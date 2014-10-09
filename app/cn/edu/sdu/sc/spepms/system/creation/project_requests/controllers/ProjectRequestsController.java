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

        ProjectRequest projectRequest = new ProjectRequest();
        projectRequest.setCreatedBy(ContextUtil.getCurrentUserId());
        projectRequest.setCreatedOn(new Date());
        projectRequest.setUpdatedBy(ContextUtil.getCurrentUserId());
        projectRequest.setUpdatedOn(new Date());
        projectRequest.setStatus(ProjectRequest.Status.NEW);

        projectRequest.setName(data.getName());
        projectRequest.setCategory(data.getCategory());
        projectRequest.setBillable(data.getBillable());
        projectRequest.setRewardMethod(data.getRewardMethod());
        projectRequest.setRewardAmount(data.getRewardAmount());
        projectRequest.setDescription(data.getDescription());
        projectRequest.setApplicableFrom(data.getApplicableFrom());
        projectRequest.setApplicableTo(data.getApplicableTo());
        projectRequest.setContactInfo(data.getContactInfo());
        projectRequest.setNumber(data.getNumber());

        projectRequest.setProcess("0%");
        projectRequest.setOrganizer(getCurrentUser());
        projectRequest.setCurrentNumber(0);
        JPA.em().persist(projectRequest);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes.ProjectRequestsController.personHome());
    }

    /**
     * 再次编辑该项目
     * @return
     */
    @Transactional
    public static Result edit(Long projectRequestId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        return ok(edit.render(projectRequest,getCurrentUser()));
    }

    /**
     * @return信息更新
     * 
     */
    @Transactional
    public static Result reSave(Long projectRequestId) {
        Form<ProjectRequestForm> form = Form.form(ProjectRequestForm.class).bindFromRequest();
        ProjectRequestForm data = form.get();

        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        projectRequest.setUpdatedBy(ContextUtil.getCurrentUserId());
        projectRequest.setUpdatedOn(new Date());
        projectRequest.setStatus(ProjectRequest.Status.NEW);

        projectRequest.setName(data.getName());
        projectRequest.setCategory(data.getCategory());
        projectRequest.setBillable(data.getBillable());
        projectRequest.setRewardMethod(data.getRewardMethod());
        projectRequest.setRewardAmount(data.getRewardAmount());
        projectRequest.setDescription(data.getDescription());
        projectRequest.setApplicableFrom(data.getApplicableFrom());
        projectRequest.setApplicableTo(data.getApplicableTo());
        projectRequest.setContactInfo(data.getContactInfo());
        projectRequest.setNumber(data.getNumber());
        JPA.em().persist(projectRequest);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.project_requests.controllers.routes.ProjectRequestsController.personHome());
    }

    /**
     * @return首页
     */
    @Transactional
    public static Result index() {
        List<ProjectRequest> projectRequest = JPA.em().createQuery("from ProjectRequest", ProjectRequest.class).getResultList();
        return ok(index.render(projectRequest));
    }

    /**
     * 显示个人所报的项目
     */
    @Transactional
    public static Result personHome() {
        List<ProjectRequest> projectRequest = JPA.em().createQuery("from ProjectRequest where organizer_Id=?").setParameter(1, getCurrentUserId()).getResultList();
        projectRequest.addAll(getCurrentUser().getProjectRequests());
        return ok(studentHome.render(projectRequest));
    }

    /**
     * 审核该项目
     * @return
     */
    @Transactional
    public static Result list() {
        List<ProjectRequest> projectRequests = JPA.em().createQuery("from ProjectRequest", ProjectRequest.class).getResultList();
        return ok(list.render(projectRequests));
    }

    /**
     * @param projectRequestId
     *  项目详细信息
     */
    @Transactional
    public static Result view(Long projectRequestId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        return ok(view.render(projectRequest, getCurrentUser()));
    }

    /**
     * @param projectRequestId
     * 审核通过该项目
     */
    @Transactional
    public static Result approve(Long projectRequestId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        projectRequest.setStatus(ProjectRequest.Status.APPROVED);;
        JPA.em().merge(projectRequest);
        return ok();
    }

    /**
     * @param projectRequestId
     * @return
     * 审核不通过该项目,回到审核项目列表页面
     */
    @Transactional
    public static Result reject(Long projectRequestId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        projectRequest.setStatus(ProjectRequest.Status.KILLED);
        JPA.em().merge(projectRequest);
        return ok();
    }

    // 项目提交审核
    @Transactional
    public static Result submit(Long projectId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectId);
        projectRequest.setStatus(ProjectRequest.Status.UNDER_APPROVAL);
        JPA.em().merge(projectRequest);
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
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, Id);
        projectRequest.setStatus(ProjectRequest.Status.KILLED);
        JPA.em().merge(projectRequest);
        return ok();
}

    /**
     * 报名该项目
     * 
     * @param projectRequestId
     * @return
     */
    @Transactional
    public static Result join(Long projectRequestId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        projectRequest.getMembers().add(getCurrentUser());
        projectRequest.setCurrentNumber(projectRequest.getMembers().size());
        JPA.em().merge(projectRequest);
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
    public static Result leave(Long projectRequestId,Long userId) {
        ProjectRequest projectRequest = JPA.em().find(ProjectRequest.class, projectRequestId);
        User user=JPA.em().find(User.class, userId);
        projectRequest.getMembers().remove(user);
        projectRequest.setCurrentNumber(projectRequest.getMembers().size());
        JPA.em().merge(projectRequest);
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
