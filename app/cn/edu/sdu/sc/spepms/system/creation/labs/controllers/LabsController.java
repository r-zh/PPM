package cn.edu.sdu.sc.spepms.system.creation.labs.controllers;

import play.mvc.Result;
import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.creation.labs.views.html.*;

public class LabsController extends SecuredController {

    /**
     * @return实验室概况
     */
    public static Result index() {
        return ok(index.render());
    }

    public static Result view() {
        return ok(view.render());
    }
}
