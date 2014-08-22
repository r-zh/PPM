package cn.edu.sdu.sc.spepms.system.creation.controllers.project;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.common.views.html.*;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.views.html.project.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

public class ProjectController extends SecuredController {
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

        CreationProject creatonProject=new CreationProject();
        creatonProject.setName(data.getName());
        creatonProject.setCategory(data.getCategory());
        creatonProject.setBillable(data.getBillable());
        creatonProject.setRewardMethod(data.getRewardMethod());
        creatonProject.setRewardAmount(data.getRewardAmount());
        creatonProject.setDescription(data.getDescription());
        creatonProject.setApplicableFrom(data.getApplicableFrom());
        creatonProject.setApplicableTo(data.getApplicableTo());
        creatonProject.setContactInfo(data.getContactInfo());
        creatonProject.setNumber(data.getNumber());

        JPA.em().persist(creatonProject);

        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(studentHome.render(creationProject));
    }

    public static Result index() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(index.render());
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
}
