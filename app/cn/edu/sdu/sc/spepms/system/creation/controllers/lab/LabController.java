package cn.edu.sdu.sc.spepms.system.creation.controllers.lab;

import java.util.List;

import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.creation.forms.ProjectForm;
import cn.edu.sdu.sc.spepms.system.creation.models.CreationProject;
import cn.edu.sdu.sc.spepms.system.creation.views.html.lab.*;
import play.*;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

public class LabController extends Controller {

    public static Result index(){
        Wireframe.current().setShowBusinessMenu(true);
        return ok(index.render());
    }

}