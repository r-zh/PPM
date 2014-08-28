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
import cn.edu.sdu.sc.spepms.system.common.views.html.auth.login;
import cn.edu.sdu.sc.spepms.system.common.views.html.profile;
/**
 * Authentication controller
 * 
 * @author Peter Fu
 */
public class AuthController extends SecuredController {

    private static final String COOKIE_KEY_REMEMBER_ME = "_remember_me";

    /**
     * Show login page
     * 
     * @return
     * @throws Throwable
     */
    public static Result login() throws Throwable {
        String redirectUrl = request().getQueryString("redirect_url");
        if (redirectUrl != null) {
            flash().put("redirectUrl", redirectUrl);
        }

        Cookie remember = request().cookies().get(COOKIE_KEY_REMEMBER_ME);

        if (remember != null && remember.value().indexOf("-") > 0) {
            Logger.debug("Remember cookie found");
            String sign = StringUtils.substring(remember.value(), 0, remember.value().indexOf("-"));
            String username = StringUtils.substring(remember.value(), remember.value().indexOf("-") + 1);
            if (Crypto.sign(username).equals(sign)) {
                session("username", username);

                // redirect to requested url
                return redirectToUrlOrHomepage(redirectUrl);
            }
            Logger.debug("Remember cookie timed out");
        }

        return ok(login.render(Form.form(LoginForm.class)));
    }

    /**
     * Authenticated user
     * 
     * @return
     */
    @Transactional
    public static Result authenticate() {
        Form<LoginForm> loginForm = Form.form(LoginForm.class).bindFromRequest();
        if (!loginForm.hasErrors()) {
            LoginForm login = loginForm.get();
            User user = null;
            try {
                user = JPA.em().createQuery("from User where name = :username", User.class).setParameter("username", login.getUsername()).getSingleResult();
            } catch (NoResultException e) {
                // ignore NoResultException here
            }

            if (user == null) {
                loginForm.reject("username error");
            } else if (!login.getPassword().equals(user.getPassword()) && false) {
                // TODO check password
                loginForm.reject("password error");
            } else {
                // Remember if needed
                if (login.isRemember()) {
                    // maxAge: the cookie expiration date in seconds, null for a transient cookie, a value less than zero for a cookie that expires now
                    response().setCookie(COOKIE_KEY_REMEMBER_ME, Crypto.sign(user.getName()) + "-" + user.getName(), 30 * 24 * 60 * 60);
                }

                session("username", user.getName());
                session("userId", user.getId() + "");
                flash().put("relogin", "authenticate");
                // redirect to requested url
                return redirectToUrlOrHomepage(loginForm.data().get("redirectUrl"));
            }
        }

        Logger.debug("login error: " + loginForm.errors().toString());
        flash().put("error", loginForm.globalError().message());
        flash().put("username", loginForm.data().get("username"));
        flash().put("password", loginForm.data().get("password"));
        flash().put("redirectUrl", loginForm.data().get("redirectUrl"));
        return redirect(cn.edu.sdu.sc.spepms.system.common.controllers.routes.AuthController.login());
    }

    private static Result redirectToUrlOrHomepage(String url) {
        if (StringUtils.isEmpty(url)) {
            return redirect(controllers.routes.Application.index());
        } else {
            return redirect(url);
        }
    }

    /**
     * Logout the system
     * 
     * @return
     */
    public static Result logout() {
        Logger.debug("AuthController.logout");

        // clear session
        session().clear();

        // remove 'rememberme' cookie
        response().discardCookie(COOKIE_KEY_REMEMBER_ME);

        flash("success", "You've been logged out");
        return redirect(cn.edu.sdu.sc.spepms.system.common.controllers.routes.AuthController.login());
    }

    
    /**
     * @return当前用户的详细页面
     */
    @Transactional
    public static Result view() {
        Logger.debug("AuthController.details");

        return ok(profile.render(getCurrentUser()));
    }
}
