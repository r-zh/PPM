package cn.edu.sdu.sc.spepms.system.common.controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;
import cn.edu.sdu.sc.spepms.system.common.views.html.auth.onUnauthorized;

/**
 * Authenticator implementation
 * 
 * @author Peter Fu
 */
public class Secured extends Security.Authenticator {

    @Override
    public Result onUnauthorized(Context ctx) {
        return ok(onUnauthorized.render(!ctx.request().path().equals("/") && ctx.request().method().equals("GET")));
    }

}
