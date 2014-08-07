package controllers;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import play.*;
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

}
