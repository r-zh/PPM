package cn.edu.sdu.sc.spepms.system.common.controllers;

import play.db.jpa.Transactional;
import play.mvc.Result;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.profile;;

public class UserController extends SecuredController {

    /**
     * 个人主页信息
     * @return
     */
    @Transactional
    public static Result view(){
        User user=getCurrentUser();
        return ok(profile.render(getCurrentUser(),getCurrentUser().getBanks()));
    }
}