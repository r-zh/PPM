package cn.edu.sdu.sc.spepms.system.common.controllers;

import play.db.jpa.JPA;
import play.mvc.Controller;
import play.mvc.Security;
import cn.edu.sdu.sc.spepms.system.common.models.User;
import cn.edu.sdu.sc.spepms.system.common.utils.ContextUtil;

@Security.Authenticated(Secured.class)
public class SecuredController extends Controller {

    protected static String getCurrentUsername() {
        return ContextUtil.getCurrentUsername();
    }

    protected static Long getCurrentUserId() {
        return ContextUtil.getCurrentUserId();
    }

    /**
     * Get current user from database according to current user id in session
     * 
     * <p>
     * Note: this method requires database access, thus @Transactional is needed.
     * </p>
     * 
     * @return
     */
    protected static User getCurrentUser() {
        return JPA.em().find(User.class, ContextUtil.getCurrentUserId());
    }

}
