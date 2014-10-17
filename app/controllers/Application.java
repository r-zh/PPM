package controllers;

import java.util.List;

import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.addArticle;
import views.html.index;
import views.html.publishProject;
import views.html.showProject;
import views.html.teacherHome;
import views.html.userList;
import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.forms.RegisterForm;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.studentHome;
import cn.edu.sdu.sc.spepms.system.creation.project_requests.forms.ProjectRequestForm;
import cn.edu.sdu.sc.spepms.system.creation.project_requests.models.ProjectRequest;

public class Application extends Controller {

    @Transactional
    public static Result index() {
        return ok(index.render());
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
        List<ProjectRequest> creationProject = JPA.em().createQuery("from ProjectRequest", ProjectRequest.class).getResultList();
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
        Form<ProjectRequestForm> form = Form.form(ProjectRequestForm.class)
                .bindFromRequest();
        ProjectRequestForm data = form.get();

        // 往PublishedProject表中添加一条记录
        ProjectRequest p = new ProjectRequest();
        p.setDescription(data.getName());
        JPA.em().persist(p);

        // 查找PublishedProject表里面的所有记录
        List<ProjectRequest> publishedProjectList = JPA.em()
                .createQuery("from PublishedProject", ProjectRequest.class)
                .getResultList();

        // 更新某条记录
        Long id = 1L;
        ProjectRequest someP = JPA.em().find(ProjectRequest.class, id);
        someP.setDescription(data.getName());
        JPA.em().merge(someP);

        // 删除，需要确认一下是那一个
        JPA.em().remove(id);
        JPA.em().remove(someP);

        return null;
    }
}
