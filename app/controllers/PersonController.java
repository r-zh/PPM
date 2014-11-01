package controllers;

import play.mvc.Result;
import views.html.teacherHome;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;

public class PersonController extends SecuredController{

	public static Result view(){
		return ok(teacherHome.render(getCurrentUser()));
	}
}
