package cn.edu.sdu.sc.spepms.system.creation.controllers.project;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.*;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.models.ProjectJoiner;
import cn.edu.sdu.sc.spepms.system.creation.views.html.project.*;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.index;
import views.html.userList;
public class ProjectController extends Controller {
    //创新项目发布
    public static Result addProject() {
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

    public static Result showCreationProject() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(showCreationProject.render());
    }

    public static Result applyCreationProject() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(applyCreationProject.render());
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
        //projectJoiner.setUserName(creationProject.getName());
        //projectJoiner.setStudentId(creationProject.getRewardMethod());
        JPA.em().persist(projectJoiner);
        return ok(details.render(creationProject));
    }

    //报名人员情况
    @Transactional
    public static Result joiners(Long projectJoinerId) {
        List<ProjectJoiner> projectJoiners=JPA.em().createQuery("from ProjectJoiner where projectId =?", ProjectJoiner.class).setParameter(1,projectJoinerId).getResultList();
        return ok(joiners.render(projectJoiners));
    }
}
