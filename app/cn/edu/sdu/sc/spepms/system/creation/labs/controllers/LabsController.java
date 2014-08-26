package cn.edu.sdu.sc.spepms.system.creation.labs.controllers;

import play.mvc.Result;
import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.creation.labs.views.html.index;

public class LabsController extends SecuredController {

    public static Result index() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(index.render());
    }

}
