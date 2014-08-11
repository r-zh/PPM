package controllers;


import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.creation.forms.PublishProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.PublishedProject;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render());
    }

    public static Result studentHome() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(studentHome.render());
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
    }
    
    public static Result dummySavePublishedProject() {
        // 从提交的表单中获取数据
        Form<PublishProjectForm> form = Form.form(PublishProjectForm.class).bindFromRequest();
        PublishProjectForm data = form.get();
        
        // 往PublishedProject表中添加一条记录
        PublishedProject p = new PublishedProject();
        p.setDescription(data.getName());
        JPA.em().persist(p);
        
        // 查找PublishedProject表里面的所有记录
        List<PublishedProject> publishedProjectList = JPA.em().createQuery("from PublishedProject", PublishedProject.class).getResultList();
        
        // 更新某条记录
        Long id = 1L;
        PublishedProject someP = JPA.em().find(PublishedProject.class, id);
        someP.setDescription(data.getName());
        JPA.em().merge(someP);
        
        // 删除，需要确认一下是那一个
        JPA.em().remove(id);
        JPA.em().remove(someP);
        
        
        
        return null;
    }

}
