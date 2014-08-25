package cn.edu.sdu.sc.spepms.system.creation.controllers.project;

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
import cn.edu.sdu.sc.spepms.system.creation.models.ProjectJoiner;
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

        List<CreationProject> creationProjects = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(studentHome.render(creationProjects));
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
        List<CreationProject> creationProjects = JPA.em().createQuery("select a from CreationProject a, ProjectJoiner b where b.userId=? and a.id=b.projectId group by a.id", CreationProject.class).setParameter(1,ContextUtil.getCurrentUserId()).getResultList();
        return ok(studentHome.render(creationProjects));
    }

    @Transactional
    public static Result check() {
        List<CreationProject> creationProjects = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(check.render(creationProjects));
    }

    //项目详细信息
    @Transactional
    public static Result details(Long creationProjectId) {
        CreationProject creationProject = JPA.em().createQuery("from CreationProject where id =?", CreationProject.class).setParameter(1,creationProjectId).getSingleResult();
        List<ProjectJoiner> projectJoiners=JPA.em().createQuery("from ProjectJoiner where projectId =?", ProjectJoiner.class).setParameter(1,creationProjectId).getResultList();
        return ok(details.render(creationProject,projectJoiners));
    }

    // 通过该项目
    @Transactional
    public static Result pass(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setPassed(true);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.controllers.project.routes.ProjectController.details(creationProjectId));
    }

    // 禁止该项目
    @Transactional
    public static Result unPass(Long creationProjectId) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, creationProjectId);
        creationProject.setPassed(false);
        JPA.em().merge(creationProject);
        return redirect(cn.edu.sdu.sc.spepms.system.creation.controllers.project.routes.ProjectController.details(creationProjectId));
    }

    /**
     * @param Id
     * @return
     * 废弃该项目
     */
    @Transactional
    public static Result trashed(Long Id) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, Id);
        creationProject.setTrashed(true);
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

        CreationProject creationProject=JPA.em().find(CreationProject.class, creationProjectId);
        List<ProjectJoiner> projectJoiners=JPA.em().createQuery("from ProjectJoiner where projectId =?", ProjectJoiner.class).setParameter(1,creationProjectId).getResultList();
        creationProject.setCurrentNumber(projectJoiners.size());
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,projectJoiners));
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
    public static Result delete(Long joinerId) {
        ProjectJoiner projectJoiner=JPA.em().createQuery("from ProjectJoiner where id =?", ProjectJoiner.class).setParameter(1,joinerId).getSingleResult();
        JPA.em().remove(projectJoiner);
        CreationProject creationProject=JPA.em().find(CreationProject.class, projectJoiner.getProjectId());
        List<ProjectJoiner> projectJoiners=JPA.em().createQuery("from ProjectJoiner where projectId =?", ProjectJoiner.class).setParameter(1,projectJoiner.getProjectId()).getResultList();
        creationProject.setCurrentNumber(projectJoiners.size());
        JPA.em().merge(creationProject);
        return ok(details.render(creationProject,projectJoiners));
    }
}
