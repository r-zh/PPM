package cn.edu.sdu.sc.spepms.system.creation.controllers.lab;

import play.mvc.Result;
import cn.edu.sdu.sc.spepms.framework.wireframe.Wireframe;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;
import cn.edu.sdu.sc.spepms.system.creation.views.html.lab.index;

public class LabController extends SecuredController {

    public static Result index() {
        Wireframe.current().setShowBusinessMenu(true);
        return ok(index.render());
    }

}
