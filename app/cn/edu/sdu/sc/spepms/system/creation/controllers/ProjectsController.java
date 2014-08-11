package cn.edu.sdu.sc.spepms.system.creation.controllers;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.creation.forms.PublishProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.PublishedProject;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.mvc.*;
import cn.edu.sdu.sc.spepms.system.creation.views.html.*;


public class ProjectsController extends Controller{

	  public static Result publishProject() {
	        Wireframe.current().setShowBusinessMenu(true);
	        return ok(publishProject.render());
	    }

}
