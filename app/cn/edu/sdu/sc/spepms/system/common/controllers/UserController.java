package cn.edu.sdu.sc.spepms.system.common.controllers;

import javax.persistence.NoResultException;

import org.apache.commons.lang3.StringUtils;

import play.Logger;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Http.Cookie;
import play.mvc.Result;
import cn.edu.sdu.sc.spepms.system.common.forms.LoginForm;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.views.html.profile;
import cn.edu.sdu.sc.spepms.system.common.views.html.auth.login;
import cn.edu.sdu.sc.spepms.system.common.utils.ContextUtil;
import cn.edu.sdu.sc.spepms.system.common.controllers.SecuredController;

public class UserController extends SecuredController {

    /**
     * 个人主页信息
     * @return
     */
    @Transactional
    public static Result view(){
        return ok(profile.render(getCurrentUser()));
    }
}