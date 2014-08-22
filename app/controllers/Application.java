package controllers;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.forms.RegisterForm;
import cn.edu.sdu.sc.spepms.system.common.models.BaseModel;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.studentHome;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        System.out.println(request().username());
        
        
        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(index.render(creationProject));
    }
    @Transactional
    public static Result registerSave() {
        Form<RegisterForm> form = Form.form(RegisterForm.class).bindFromRequest();
        RegisterForm data = form.get();

        User user = new User();
        user.setName(data.getName());
        user.setGender(data.getGender());
        user.setPersonalId(data.getPersonalId());

        JPA.em().persist(user);

        return ok();
    }
    
    @Transactional
    public static Result users() {
        List<User> users = JPA.em().createQuery("from User", User.class).getResultList();
        return ok(userList.render(users));
    }

    @Transactional
    public static Result studentHome() {
        Wireframe.current().setShowBusinessMenu(true);
        List<CreationProject> creationProject = JPA.em().createQuery("from CreationProject", CreationProject.class).getResultList();
        return ok(studentHome.render(creationProject));
    }

    public static Result teacherHome() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(teacherHome.render());
    }

    public static Result login() {
        return redirect(routes.Application.studentHome());
    }

    public static Result addArticle() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(addArticle.render());
    }

    public static Result saveArticle() {
        return redirect(routes.Application.index());
    }// 实训申请

    public static Result publishProject() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(publishProject.render());
    }// 实训审核报名

    public static Result showProject() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(showProject.render());
    }

    public static Result dummySavePublishedProject() {
        // 从提交的表单中获取数据
        Form<ProjectForm> form = Form.form(ProjectForm.class)
                .bindFromRequest();
        ProjectForm data = form.get();

        // 往PublishedProject表中添加一条记录
        CreationProject p = new CreationProject();
        p.setDescription(data.getName());
        JPA.em().persist(p);

        // 查找PublishedProject表里面的所有记录
        List<CreationProject> publishedProjectList = JPA.em()
                .createQuery("from PublishedProject", CreationProject.class)
                .getResultList();

        // 更新某条记录
        Long id = 1L;
        CreationProject someP = JPA.em().find(CreationProject.class, id);
        someP.setDescription(data.getName());
        JPA.em().merge(someP);

        // 删除，需要确认一下是那一个
        JPA.em().remove(id);
        JPA.em().remove(someP);

        return null;
    }
}
