package cn.edu.sdu.sc.spepms.system.creation.controllers.project;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.utils.ContextUtil;
import cn.edu.sdu.sc.spepms.system.common.views.html.*;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.models.ProjectJoiner;
import cn.edu.sdu.sc.spepms.system.creation.views.html.project.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.index;

public class ProjectController extends SecuredController {
    //创新项目发布
    public static Result addProject() {
        //System.out.println(request().username());
    Wireframe.current().setShowBusinessMenu(true);
        return ok(add.render());
    }

    // 创新审核报名,展示数据库相关信息
    public static Result showProject() {
        Wireframe.current().setShowBusinessMenu(true);
        CreationProject creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getSingleResult();
        return ok(details.render(creationProject));
    }

    //信息录入数据库
    @Transactional
    public static Result saveProject() {
        Form<ProjectForm> form = Form.form(ProjectForm.class).bindFromRequest();
        ProjectForm data = form.get();

        CreationProject creationProject=new CreationProject();
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

    @Transactional
    public static Result check() {
        //Wireframe.current().setShowBusinessMenu(true);
        List<CreationProject> creationProjects = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(check.render(creationProjects));
    }

    //项目详细信息
    @Transactional
    public static Result details(Long Id) {
        Wireframe.current().setShowBusinessMenu(true);
        CreationProject creationProject = JPA.em().createQuery("from CreationProject where id =?", CreationProject.class).setParameter(1,Id).getSingleResult();
        return ok(details.render(creationProject));
    }

    // 通过该项目
    @Transactional
    public static Result pass(Long Id) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, Id);
        creationProject.setPassed(true);
        JPA.em().merge(creationProject);
        Wireframe.current().setShowBusinessMenu(true);
        return ok(details.render(creationProject));
    }

    // 禁止该项目
    @Transactional
    public static Result unPass(Long Id) {
        CreationProject creationProject = JPA.em().find(CreationProject.class, Id);
        creationProject.setPassed(false);
        JPA.em().merge(creationProject);
        Wireframe.current().setShowBusinessMenu(true);
        return ok(details.render(creationProject));
    }

    // 报名该项目
    @Transactional
    public static Result join(Long creationProjectId) {
        CreationProject creationProject=JPA.em().find(CreationProject.class, creationProjectId);
        ProjectJoiner projectJoiner=new ProjectJoiner();
        projectJoiner.setProjectId(creationProjectId);

        projectJoiner.setUserName(request().username());
        User user=JPA.em().find(User.class, ContextUtil.getCurrentUserId());

        projectJoiner.setStudentId(user.getStudentId());
        projectJoiner.setUserId(ContextUtil.getCurrentUserId());
        JPA.em().persist(projectJoiner);
        return ok(details.render(creationProject));
    }

    //报名人员情况
    @Transactional
    public static Result joiners(Long projectId) {
        List<ProjectJoiner> projectJoiners=JPA.em().createQuery("from ProjectJoiner where projectId =?", ProjectJoiner.class).setParameter(1,projectId).getResultList();
        return ok(joiners.render(projectJoiners));
    }
}
